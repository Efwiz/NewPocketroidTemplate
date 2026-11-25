package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKindKt;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponent;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirCheckNotNullCall;
import org.jetbrains.kotlin.fir.expressions.FirElvisExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirNamedArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirQualifiedAccessExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirSafeCallExpression;
import org.jetbrains.kotlin.fir.expressions.FirSmartCastExpression;
import org.jetbrains.kotlin.fir.expressions.FirSpreadArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirTryExpression;
import org.jetbrains.kotlin.fir.expressions.FirWhenExpression;
import org.jetbrains.kotlin.fir.expressions.FirWrappedArgumentExpression;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.resolve.ImplicitIntegerCoercionKt;
import org.jetbrains.kotlin.fir.resolve.ResolveUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.inference.LambdaWithTypeVariableAsExpectedTypeAtom;
import org.jetbrains.kotlin.fir.resolve.inference.PostponedArgumentsKt;
import org.jetbrains.kotlin.fir.resolve.inference.PostponedResolvedAtom;
import org.jetbrains.kotlin.fir.resolve.inference.model.ConeArgumentConstraintPosition;
import org.jetbrains.kotlin.fir.resolve.inference.model.ConeReceiverConstraintPosition;
import org.jetbrains.kotlin.fir.resolve.transformers.PhaseUtilsKt;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculator;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.types.ArrayUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeIntegerLiteralType;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeIntersector;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeTypeVariableType;
import org.jetbrains.kotlin.fir.types.ConeTypeVariableTypeConstructor;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.InferenceUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilderKt;
import org.jetbrains.kotlin.resolve.calls.inference.model.Constraint;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.SimpleConstraintSystemConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext;
import org.jetbrains.kotlin.types.model.TypeSystemContextHelpersKt;

/* JADX INFO: compiled from: Arguments.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0094\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a*\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005H\u0002\u001a \u0010\f\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0000\u001aR\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00052\b\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002\u001a*\u0010\u001d\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0005H\u0002\u001a0\u0010!\u001a\u00020\u00172\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\"\u001a\u00020\u00052\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&H\u0002\u001a\u001e\u0010'\u001a\u0004\u0018\u00010\u0005*\u00020(2\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u001cH\u0002\u001a\u0012\u0010)\u001a\u00020\u0005*\u00020\u00122\u0006\u0010\u001e\u001a\u00020\u001f\u001a6\u0010*\u001a\u0004\u0018\u00010\u0005*\u00020(2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010 \u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u001cH\u0002\u001a\u001c\u0010+\u001a\u00020\u0017*\u00020\u00052\u0006\u0010,\u001a\u00020-2\u0006\u0010\u001b\u001a\u00020.H\u0002\u001a,\u0010/\u001a\u00020\u0017*\u00020\u00122\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\u00100\u001a\u0004\u0018\u00010\u00052\u0006\u0010%\u001a\u00020&\u001a\u000e\u00101\u001a\u0004\u0018\u000102*\u00020\u0012H\u0002\u001a\u001a\u00103\u001a\u00020\u0005*\u00020(2\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u001c\u001a@\u00104\u001a\u0004\u0018\u00010\u0005*\u00020(2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u00105\u001a\u0002062\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002\u001a>\u00107\u001a\u00020\u000e*\u00020(2\u0006\u00105\u001a\u0002062\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0000\u001aD\u00108\u001a\u00020\u000e*\u00020(2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017\u001aF\u00109\u001a\u00020\u000e*\u00020(2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010:\u001a\u00020;2\b\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017H\u0002\u001aV\u0010<\u001a\u00020\u000e*\u00020(2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000b\u001a\u00020\u00052\b\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\b\b\u0002\u0010=\u001a\u00020\u0017\u001aN\u0010>\u001a\u00020\u000e*\u00020(2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\b\b\u0002\u0010=\u001a\u00020\u0017\u001aN\u0010?\u001a\u00020\u000e*\u00020(2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020@2\b\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\b\b\u0002\u0010=\u001a\u00020\u0017\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u0006A"}, d2 = {"SAM_LOOKUP_NAME", "Lorg/jetbrains/kotlin/name/Name;", "getSAM_LOOKUP_NAME", "()Lorg/jetbrains/kotlin/name/Name;", "argumentTypeWithCustomConversion", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "expectedType", "argumentType", "captureFromTypeParameterUpperBoundIfNeeded", "checkApplicabilityForArgumentType", "", "csBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "argument", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "argumentTypeBeforeCapturing", "position", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintPosition;", "isReceiver", "", "isDispatch", "sink", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "getExpectedTypeWithImplicintIntegerCoercion", "parameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "candidateExpectedType", "isSubtypeForSamConversion", "actualExpressionType", "classLikeExpectedFunctionType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "returnTypeCalculator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "captureTypeFromExpressionOrNull", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "getExpectedType", "getExpectedTypeWithSAMConversion", "hasSupertypeWithGivenClassId", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/types/model/TypeSystemCommonSuperTypesContext;", "isFunctional", "expectedFunctionType", "namedReferenceWithCandidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/FirNamedReferenceWithCandidate;", "prepareCapturedType", "prepareExpectedType", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "resolveArgument", "resolveArgumentExpression", "resolveBlockArgument", "block", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "resolvePlainArgumentType", "useNullableArgumentType", "resolvePlainExpressionArgument", "resolveSubCallArgument", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ArgumentsKt {
    private static final Name SAM_LOOKUP_NAME;

    static {
        Name nameSpecial = Name.special("<SAM-CONSTRUCTOR>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        SAM_LOOKUP_NAME = nameSpecial;
    }

    public static final Name getSAM_LOOKUP_NAME() {
        return SAM_LOOKUP_NAME;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void resolveArgumentExpression(Candidate candidate, ConstraintSystemBuilder csBuilder, FirExpression argument, ConeKotlinType coneKotlinType, CheckerSink sink, ResolutionContext context, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(candidate, "<this>");
        Intrinsics.checkNotNullParameter(csBuilder, "csBuilder");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(sink, "sink");
        Intrinsics.checkNotNullParameter(context, "context");
        if (argument instanceof FirFunctionCall ? true : argument instanceof FirWhenExpression ? true : argument instanceof FirTryExpression ? true : argument instanceof FirCheckNotNullCall ? true : argument instanceof FirElvisExpression) {
            resolveSubCallArgument$default(candidate, csBuilder, (FirResolvable) argument, coneKotlinType, sink, context, z, z2, false, 128, null);
            return;
        }
        if (argument instanceof FirSafeCallExpression) {
            FirStatement selector = ((FirSafeCallExpression) argument).getSelector();
            FirExpression firExpression = selector instanceof FirExpression ? (FirExpression) selector : null;
            FirExpression firExpressionUnwrapSmartcastExpression = firExpression != null ? FirExpressionUtilKt.unwrapSmartcastExpression(firExpression) : null;
            if (firExpressionUnwrapSmartcastExpression instanceof FirQualifiedAccessExpression) {
                resolveSubCallArgument(candidate, csBuilder, (FirResolvable) firExpressionUnwrapSmartcastExpression, coneKotlinType, sink, context, z, z2, true);
                return;
            } else {
                checkApplicabilityForArgumentType(csBuilder, argument, TypeConstructionUtilsKt.constructClassLikeType$default(StandardClassIds.INSTANCE.getUnit(), new ConeTypeProjection[0], false, null, 4, null), coneKotlinType != null ? coneKotlinType.getType() : null, SimpleConstraintSystemConstraintPosition.INSTANCE, false, false, sink, context);
                return;
            }
        }
        if (argument instanceof FirCallableReferenceAccess) {
            FirCallableReferenceAccess firCallableReferenceAccess = (FirCallableReferenceAccess) argument;
            if (firCallableReferenceAccess.getCalleeReference() instanceof FirResolvedNamedReference) {
                resolvePlainExpressionArgument$default(candidate, csBuilder, argument, coneKotlinType, sink, context, z, z2, false, 128, null);
                return;
            } else {
                PostponedArgumentsKt.preprocessCallableReference(candidate, firCallableReferenceAccess, coneKotlinType, context);
                return;
            }
        }
        if (argument instanceof FirAnonymousFunctionExpression) {
            PostponedArgumentsKt.preprocessLambdaArgument(candidate, csBuilder, (FirAnonymousFunctionExpression) argument, coneKotlinType, context, sink, (96 & 32) != 0 ? false : false, (96 & 64) != 0 ? null : null);
            return;
        }
        if (argument instanceof FirWrappedArgumentExpression) {
            resolveArgumentExpression(candidate, csBuilder, ((FirWrappedArgumentExpression) argument).getExpression(), coneKotlinType, sink, context, z, z2);
        } else if (argument instanceof FirBlock) {
            resolveBlockArgument(candidate, csBuilder, (FirBlock) argument, coneKotlinType, sink, context, z, z2);
        } else {
            resolvePlainExpressionArgument$default(candidate, csBuilder, argument, coneKotlinType, sink, context, z, z2, false, 128, null);
        }
    }

    private static final void resolveBlockArgument(Candidate candidate, ConstraintSystemBuilder constraintSystemBuilder, FirBlock firBlock, ConeKotlinType coneKotlinType, CheckerSink checkerSink, ResolutionContext resolutionContext, boolean z, boolean z2) {
        List<FirExpression> listReturnExpressions = UtilsKt.returnExpressions(firBlock);
        if (listReturnExpressions.isEmpty()) {
            checkApplicabilityForArgumentType(constraintSystemBuilder, firBlock, FirTypeUtilsKt.getConeType(firBlock.getTypeRef()), coneKotlinType != null ? coneKotlinType.getType() : null, SimpleConstraintSystemConstraintPosition.INSTANCE, false, false, checkerSink, resolutionContext);
            return;
        }
        Iterator<FirExpression> it = listReturnExpressions.iterator();
        while (it.hasNext()) {
            resolveArgumentExpression(candidate, constraintSystemBuilder, it.next(), coneKotlinType, checkerSink, resolutionContext, z, z2);
        }
    }

    public static /* synthetic */ void resolveSubCallArgument$default(Candidate candidate, ConstraintSystemBuilder constraintSystemBuilder, FirResolvable firResolvable, ConeKotlinType coneKotlinType, CheckerSink checkerSink, ResolutionContext resolutionContext, boolean z, boolean z2, boolean z3, int i, Object obj) {
        resolveSubCallArgument(candidate, constraintSystemBuilder, firResolvable, coneKotlinType, checkerSink, resolutionContext, z, z2, (i & 128) != 0 ? false : z3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void resolveSubCallArgument(Candidate candidate, ConstraintSystemBuilder csBuilder, FirResolvable argument, ConeKotlinType coneKotlinType, CheckerSink sink, ResolutionContext context, boolean z, boolean z2, boolean z3) {
        Intrinsics.checkNotNullParameter(candidate, "<this>");
        Intrinsics.checkNotNullParameter(csBuilder, "csBuilder");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(sink, "sink");
        Intrinsics.checkNotNullParameter(context, "context");
        if (!(argument instanceof FirExpression)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        Candidate candidate2 = CandidateFactoryKt.candidate(argument);
        if (candidate2 == null) {
            resolvePlainExpressionArgument(candidate, csBuilder, (FirExpression) argument, coneKotlinType, sink, context, z, z2, z3);
            return;
        }
        ReturnTypeCalculator returnTypeCalculator = context.getReturnTypeCalculator();
        FirDeclaration fir = candidate2.getSymbol().getFir();
        Intrinsics.checkNotNull(fir, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration");
        resolvePlainArgumentType(candidate, csBuilder, (FirExpression) argument, candidate2.getSubstitutor().substituteOrSelf(returnTypeCalculator.tryCalculateReturnType((FirCallableDeclaration) fir).getType()), coneKotlinType, sink, context, z, z2, z3);
    }

    public static /* synthetic */ void resolvePlainExpressionArgument$default(Candidate candidate, ConstraintSystemBuilder constraintSystemBuilder, FirExpression firExpression, ConeKotlinType coneKotlinType, CheckerSink checkerSink, ResolutionContext resolutionContext, boolean z, boolean z2, boolean z3, int i, Object obj) {
        resolvePlainExpressionArgument(candidate, constraintSystemBuilder, firExpression, coneKotlinType, checkerSink, resolutionContext, z, z2, (i & 128) != 0 ? false : z3);
    }

    public static final void resolvePlainExpressionArgument(Candidate candidate, ConstraintSystemBuilder csBuilder, FirExpression argument, ConeKotlinType coneKotlinType, CheckerSink sink, ResolutionContext context, boolean z, boolean z2, boolean z3) {
        Intrinsics.checkNotNullParameter(candidate, "<this>");
        Intrinsics.checkNotNullParameter(csBuilder, "csBuilder");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(sink, "sink");
        Intrinsics.checkNotNullParameter(context, "context");
        if (coneKotlinType == null) {
            return;
        }
        FirTypeRef typeRef = argument.getTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        ConeKotlinType coneKotlinType2 = !(type instanceof ConeKotlinType) ? null : type;
        if (coneKotlinType2 == null) {
            return;
        }
        resolvePlainArgumentType(candidate, csBuilder, argument, coneKotlinType2, coneKotlinType, sink, context, z, z2, z3);
    }

    public static final void resolvePlainArgumentType(Candidate candidate, ConstraintSystemBuilder csBuilder, FirExpression argument, ConeKotlinType argumentType, ConeKotlinType coneKotlinType, CheckerSink sink, ResolutionContext context, boolean z, boolean z2, boolean z3) {
        ConeKotlinType coneKotlinTypeArgumentTypeWithCustomConversion;
        Intrinsics.checkNotNullParameter(candidate, "<this>");
        Intrinsics.checkNotNullParameter(csBuilder, "csBuilder");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(argumentType, "argumentType");
        Intrinsics.checkNotNullParameter(sink, "sink");
        Intrinsics.checkNotNullParameter(context, "context");
        ConstraintPosition coneReceiverConstraintPosition = z ? new ConeReceiverConstraintPosition(argument) : new ConeArgumentConstraintPosition(argument);
        FirSession session = context.getSession();
        ConeKotlinType coneKotlinTypePrepareCapturedType = prepareCapturedType(candidate, argumentType, context);
        if (z3) {
            coneKotlinTypePrepareCapturedType = TypeUtilsKt.withNullability$default(coneKotlinTypePrepareCapturedType, ConeNullability.NULLABLE, TypeComponentsKt.getTypeContext(session), null, false, 12, null);
        }
        if (coneKotlinType == null || (coneKotlinTypeArgumentTypeWithCustomConversion = argumentTypeWithCustomConversion(session, context.getBodyResolveComponents().getScopeSession(), coneKotlinType, coneKotlinTypePrepareCapturedType)) == null) {
            coneKotlinTypeArgumentTypeWithCustomConversion = coneKotlinTypePrepareCapturedType;
        } else {
            candidate.getSubstitutor().substituteOrSelf(coneKotlinTypeArgumentTypeWithCustomConversion);
            candidate.setUsesFunctionConversion(true);
        }
        checkApplicabilityForArgumentType(csBuilder, argument, coneKotlinTypeArgumentTypeWithCustomConversion, coneKotlinType, coneReceiverConstraintPosition, z, z2, sink, context);
    }

    private static final ConeKotlinType argumentTypeWithCustomConversion(FirSession firSession, ScopeSession scopeSession, ConeKotlinType coneKotlinType, ConeKotlinType coneKotlinType2) {
        FirFunctionSymbol<?> firFunctionSymbolFindContributedInvokeSymbol;
        FunctionTypeKind functionTypeKind = FunctionalTypeUtilsKt.functionTypeKind(coneKotlinType, firSession);
        ConeLookupTagBasedType coneLookupTagBasedTypeCreateFunctionType$default = null;
        if (functionTypeKind == null || FunctionTypeKindKt.isBasicFunctionOrKFunction(functionTypeKind)) {
            return null;
        }
        ConeClassLikeType coneClassLikeTypeCustomFunctionTypeToSimpleFunctionType = FunctionalTypeUtilsKt.customFunctionTypeToSimpleFunctionType(coneKotlinType, firSession);
        ConeKotlinType coneKotlinTypeFindSubtypeOfBasicFunctionType = FunctionalTypeUtilsKt.findSubtypeOfBasicFunctionType(coneKotlinType2, firSession, coneClassLikeTypeCustomFunctionTypeToSimpleFunctionType);
        if (coneKotlinTypeFindSubtypeOfBasicFunctionType != null && (firFunctionSymbolFindContributedInvokeSymbol = FunctionalTypeUtilsKt.findContributedInvokeSymbol(coneKotlinTypeFindSubtypeOfBasicFunctionType, firSession, scopeSession, coneClassLikeTypeCustomFunctionTypeToSimpleFunctionType, false)) != null) {
            List<FirValueParameter> valueParameters = ((FirFunction) firFunctionSymbolFindContributedInvokeSymbol.getFir()).getValueParameters();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
            Iterator<T> it = valueParameters.iterator();
            while (it.hasNext()) {
                arrayList.add(FirTypeUtilsKt.getConeType(((FirValueParameter) it.next()).getReturnTypeRef()));
            }
            coneLookupTagBasedTypeCreateFunctionType$default = ResolveUtilsKt.createFunctionType$default(functionTypeKind, arrayList, null, FirTypeUtilsKt.getConeType(((FirFunction) firFunctionSymbolFindContributedInvokeSymbol.getFir()).getReturnTypeRef()), null, 16, null);
        }
        return coneLookupTagBasedTypeCreateFunctionType$default;
    }

    public static final ConeKotlinType prepareCapturedType(Candidate candidate, ConeKotlinType argumentType, ResolutionContext context) {
        Intrinsics.checkNotNullParameter(candidate, "<this>");
        Intrinsics.checkNotNullParameter(argumentType, "argumentType");
        Intrinsics.checkNotNullParameter(context, "context");
        ConeKotlinType coneKotlinTypeCaptureTypeFromExpressionOrNull = captureTypeFromExpressionOrNull(candidate, argumentType, context);
        return coneKotlinTypeCaptureTypeFromExpressionOrNull == null ? argumentType : coneKotlinTypeCaptureTypeFromExpressionOrNull;
    }

    private static final ConeKotlinType captureTypeFromExpressionOrNull(Candidate candidate, ConeKotlinType coneKotlinType, ResolutionContext resolutionContext) {
        ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(coneKotlinType, resolutionContext.getSession());
        ConeKotlinType coneKotlinType2 = null;
        if (coneKotlinTypeFullyExpandedType instanceof ConeIntersectionType) {
            ConeIntersectionType coneIntersectionType = (ConeIntersectionType) coneKotlinTypeFullyExpandedType;
            Collection<ConeKotlinType> intersectedTypes = coneIntersectionType.getIntersectedTypes();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intersectedTypes, 10));
            for (ConeKotlinType coneKotlinType3 : intersectedTypes) {
                ConeKotlinType coneKotlinTypeCaptureTypeFromExpressionOrNull = captureTypeFromExpressionOrNull(candidate, coneKotlinType3, resolutionContext);
                if (coneKotlinTypeCaptureTypeFromExpressionOrNull != null) {
                    coneKotlinType3 = coneKotlinTypeCaptureTypeFromExpressionOrNull;
                }
                arrayList.add(coneKotlinType3);
            }
            ArrayList arrayList2 = arrayList;
            if (Intrinsics.areEqual(arrayList2, coneIntersectionType.getIntersectedTypes())) {
                return null;
            }
            ArrayList arrayList3 = arrayList2;
            ConeKotlinType alternativeType = coneIntersectionType.getAlternativeType();
            if (alternativeType != null) {
                ConeKotlinType coneKotlinTypeCaptureTypeFromExpressionOrNull2 = captureTypeFromExpressionOrNull(candidate, alternativeType, resolutionContext);
                coneKotlinType2 = coneKotlinTypeCaptureTypeFromExpressionOrNull2 == null ? alternativeType : coneKotlinTypeCaptureTypeFromExpressionOrNull2;
            }
            return new ConeIntersectionType(arrayList3, coneKotlinType2);
        }
        if (((coneKotlinTypeFullyExpandedType instanceof ConeClassLikeType) || (coneKotlinTypeFullyExpandedType instanceof ConeFlexibleType)) && coneKotlinTypeFullyExpandedType.getTypeArguments().length != 0) {
            return TypeUtilsKt.captureFromArgumentsInternal(TypeComponentsKt.getTypeContext(resolutionContext.getSession()), coneKotlinTypeFullyExpandedType, CaptureStatus.FROM_EXPRESSION);
        }
        return null;
    }

    private static final void checkApplicabilityForArgumentType(ConstraintSystemBuilder constraintSystemBuilder, FirExpression firExpression, ConeKotlinType coneKotlinType, ConeKotlinType coneKotlinType2, ConstraintPosition constraintPosition, boolean z, boolean z2, CheckerSink checkerSink, ResolutionContext resolutionContext) {
        if (coneKotlinType2 == null) {
            return;
        }
        ConeKotlinType coneKotlinTypeCaptureFromTypeParameterUpperBoundIfNeeded = captureFromTypeParameterUpperBoundIfNeeded(coneKotlinType, coneKotlinType2, resolutionContext.getSession());
        if (z && z2) {
            if (ConeTypeUtilsKt.isNullable(coneKotlinType2) || !ConeTypeUtilsKt.isMarkedNullable(coneKotlinTypeCaptureFromTypeParameterUpperBoundIfNeeded)) {
                return;
            }
            checkerSink.reportDiagnostic(new InapplicableWrongReceiver(coneKotlinType2, coneKotlinTypeCaptureFromTypeParameterUpperBoundIfNeeded));
            return;
        }
        ConeKotlinType coneKotlinType3 = coneKotlinTypeCaptureFromTypeParameterUpperBoundIfNeeded;
        ConeKotlinType coneKotlinType4 = coneKotlinType2;
        if (ConstraintSystemBuilderKt.addSubtypeConstraintIfCompatible(constraintSystemBuilder, coneKotlinType3, coneKotlinType4, constraintPosition)) {
            return;
        }
        FirSmartCastExpression firSmartCastExpression = firExpression instanceof FirSmartCastExpression ? (FirSmartCastExpression) firExpression : null;
        if (firSmartCastExpression != null && !firSmartCastExpression.isStable() && ConstraintSystemBuilderKt.addSubtypeConstraintIfCompatible(constraintSystemBuilder, FirTypeUtilsKt.getConeType(firSmartCastExpression.getSmartcastType()), coneKotlinType4, constraintPosition)) {
            checkerSink.reportDiagnostic(new UnstableSmartCast(firSmartCastExpression, coneKotlinType2, InferenceUtilsKt.isTypeMismatchDueToNullability(TypeComponentsKt.getTypeContext(resolutionContext.getSession()), coneKotlinTypeCaptureFromTypeParameterUpperBoundIfNeeded, coneKotlinType2)));
            return;
        }
        if (!z) {
            checkerSink.reportDiagnostic(checkApplicabilityForArgumentType$subtypeError(firExpression, coneKotlinTypeCaptureFromTypeParameterUpperBoundIfNeeded, resolutionContext, constraintSystemBuilder, coneKotlinType2));
        } else if (ConstraintSystemBuilderKt.addSubtypeConstraintIfCompatible(constraintSystemBuilder, coneKotlinType3, TypeUtilsKt.withNullability$default(coneKotlinType2, ConeNullability.NULLABLE, TypeComponentsKt.getTypeContext(resolutionContext.getSession()), null, false, 12, null), constraintPosition)) {
            checkerSink.reportDiagnostic(new UnsafeCall(coneKotlinTypeCaptureFromTypeParameterUpperBoundIfNeeded));
        } else {
            constraintSystemBuilder.addSubtypeConstraint(coneKotlinType3, coneKotlinType4, constraintPosition);
            checkerSink.reportDiagnostic(new InapplicableWrongReceiver(coneKotlinType2, coneKotlinTypeCaptureFromTypeParameterUpperBoundIfNeeded));
        }
    }

    private static final ResolutionDiagnostic checkApplicabilityForArgumentType$subtypeError(FirExpression firExpression, ConeKotlinType coneKotlinType, ResolutionContext resolutionContext, ConstraintSystemBuilder constraintSystemBuilder, ConeKotlinType coneKotlinType2) {
        if (FirTypeUtilsKt.isNullLiteral(firExpression) && coneKotlinType2.getNullability() == ConeNullability.NOT_NULL) {
            return new NullForNotNullType(firExpression, coneKotlinType2);
        }
        if ((coneKotlinType instanceof ConeErrorType) || (coneKotlinType2 instanceof ConeErrorType)) {
            return ErrorTypeInArguments.INSTANCE;
        }
        return new ArgumentTypeMismatch(checkApplicabilityForArgumentType$subtypeError$tryGetConeTypeThatCompatibleWithKtType(constraintSystemBuilder, resolutionContext, coneKotlinType2), checkApplicabilityForArgumentType$subtypeError$tryGetConeTypeThatCompatibleWithKtType(constraintSystemBuilder, resolutionContext, coneKotlinType), firExpression, InferenceUtilsKt.isTypeMismatchDueToNullability(TypeComponentsKt.getTypeContext(resolutionContext.getSession()), coneKotlinType, coneKotlinType2));
    }

    private static final ConeKotlinType checkApplicabilityForArgumentType$subtypeError$tryGetConeTypeThatCompatibleWithKtType(ConstraintSystemBuilder constraintSystemBuilder, ResolutionContext resolutionContext, ConeKotlinType coneKotlinType) {
        ConeClassLikeType coneClassLikeType;
        ArrayList arrayList;
        if (!(coneKotlinType instanceof ConeTypeVariableType)) {
            return (!(coneKotlinType instanceof ConeIntegerLiteralType) || (coneClassLikeType = (ConeClassLikeType) CollectionsKt.firstOrNull(((ConeIntegerLiteralType) coneKotlinType).getPossibleTypes())) == null) ? coneKotlinType : coneClassLikeType;
        }
        ConeTypeVariableTypeConstructor lookupTag = ((ConeTypeVariableType) coneKotlinType).getLookupTag();
        VariableWithConstraints variableWithConstraints = constraintSystemBuilder.currentStorage().getNotFixedTypeVariables().get(lookupTag);
        List<Constraint> constraints = variableWithConstraints != null ? variableWithConstraints.getConstraints() : null;
        if (constraints != null) {
            ArrayList arrayList2 = new ArrayList();
            Iterator<T> it = constraints.iterator();
            while (it.hasNext()) {
                KotlinTypeMarker type = ((Constraint) it.next()).getType();
                ConeKotlinType coneKotlinType2 = type instanceof ConeKotlinType ? (ConeKotlinType) type : null;
                if (coneKotlinType2 != null) {
                    arrayList2.add(coneKotlinType2);
                }
            }
            arrayList = arrayList2;
        } else {
            arrayList = null;
        }
        ArrayList arrayList3 = arrayList;
        if (arrayList3 != null && !arrayList3.isEmpty()) {
            return ConeTypeIntersector.INSTANCE.intersectTypes(TypeComponentsKt.getTypeContext(resolutionContext.getSession()), arrayList3);
        }
        if (!(lookupTag instanceof ConeTypeVariableTypeConstructor)) {
            lookupTag = null;
        }
        TypeParameterMarker originalTypeParameter = lookupTag != null ? lookupTag.getOriginalTypeParameter() : null;
        ConeTypeParameterLookupTag coneTypeParameterLookupTag = originalTypeParameter instanceof ConeTypeParameterLookupTag ? (ConeTypeParameterLookupTag) originalTypeParameter : null;
        return coneTypeParameterLookupTag != null ? new ConeTypeParameterTypeImpl(coneTypeParameterLookupTag, ConeTypeUtilsKt.isNullable(coneKotlinType), coneKotlinType.getAttributes()) : coneKotlinType;
    }

    private static final ConeKotlinType prepareExpectedType(Candidate candidate, FirSession firSession, ScopeSession scopeSession, CallInfo callInfo, FirExpression firExpression, FirValueParameter firValueParameter, ResolutionContext resolutionContext) {
        ClassId classId;
        if (firValueParameter == null) {
            return null;
        }
        ConeKotlinType expectedType = getExpectedType(firExpression, firValueParameter);
        ConeKotlinType expectedTypeWithSAMConversion = getExpectedTypeWithSAMConversion(candidate, firSession, scopeSession, firExpression, expectedType, resolutionContext);
        if (expectedTypeWithSAMConversion != null) {
            FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(firSession);
            if (lookupTracker != null && (classId = ConeTypeUtilsKt.getClassId(ConeTypeUtilsKt.lowerBoundIfFlexible(FirTypeUtilsKt.getConeType(firValueParameter.getReturnTypeRef())))) != null) {
                ClassId classId2 = classId.isLocal() ? null : classId;
                if (classId2 != null) {
                    Name name2 = SAM_LOOKUP_NAME;
                    String strAsString = classId2.asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    lookupTracker.recordLookup(name2, strAsString, callInfo.getCallSite().getSource(), callInfo.getContainingFile().getSource());
                    Name shortClassName = classId2.getShortClassName();
                    Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
                    String strAsString2 = classId2.getPackageFqName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                    lookupTracker.recordLookup(shortClassName, strAsString2, callInfo.getCallSite().getSource(), callInfo.getContainingFile().getSource());
                }
            }
            expectedType = expectedTypeWithSAMConversion;
        } else {
            ConeKotlinType expectedTypeWithImplicintIntegerCoercion = getExpectedTypeWithImplicintIntegerCoercion(firSession, firExpression, firValueParameter, expectedType);
            if (expectedTypeWithImplicintIntegerCoercion != null) {
                expectedType = expectedTypeWithImplicintIntegerCoercion;
            }
        }
        return candidate.getSubstitutor().substituteOrSelf(expectedType);
    }

    private static final ConeKotlinType getExpectedTypeWithSAMConversion(Candidate candidate, FirSession firSession, ScopeSession scopeSession, FirExpression firExpression, ConeKotlinType coneKotlinType, ResolutionContext resolutionContext) {
        ConeKotlinType functionTypeForPossibleSamType;
        if (FunctionalTypeUtilsKt.isSomeFunctionType(coneKotlinType, firSession) || (functionTypeForPossibleSamType = resolutionContext.getBodyResolveComponents().getSamResolver().getFunctionTypeForPossibleSamType(coneKotlinType)) == null || !isFunctional(firExpression, firSession, scopeSession, functionTypeForPossibleSamType, resolutionContext.getReturnTypeCalculator())) {
            return null;
        }
        candidate.setUsesSAM(true);
        return functionTypeForPossibleSamType;
    }

    /* JADX WARN: Code duplicated, block: B:29:0x005e  */
    /* JADX WARN: Code duplicated, block: B:31:? A[RETURN, SYNTHETIC] */
    private static final ConeKotlinType getExpectedTypeWithImplicintIntegerCoercion(FirSession firSession, FirExpression firExpression, FirValueParameter firValueParameter, ConeKotlinType coneKotlinType) {
        ConeKotlinType coneKotlinType2;
        FirCallableSymbol resolvedCallableSymbol$default;
        ConeKotlinType resolvedReturnType;
        if (!FirLanguageSettingsComponentKt.getLanguageVersionSettings(firSession).supportsFeature(LanguageFeature.ImplicitSignedToUnsignedIntegerConversion) || !ImplicitIntegerCoercionKt.isMarkedWithImplicitIntegerCoercion(firValueParameter) || !ConeBuiltinTypeUtilsKt.isUnsignedTypeOrNullableUnsignedType(TypeExpansionUtilsKt.fullyExpandedType(coneKotlinType, firSession))) {
            return null;
        }
        if (ResolveUtilsKt.isIntegerLiteralOrOperatorCall(firExpression)) {
            resolvedReturnType = FirTypeUtilsKt.getConeType(firExpression.getTypeRef());
        } else {
            FirReference calleeReference = FirExpressionUtilKt.getCalleeReference(firExpression);
            if (calleeReference != null && (resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(calleeReference, false, 1, null)) != null) {
                if (!resolvedCallableSymbol$default.getRawStatus().isConst() || !ImplicitIntegerCoercionKt.isMarkedWithImplicitIntegerCoercion((FirCallableSymbol<?>) resolvedCallableSymbol$default)) {
                    resolvedCallableSymbol$default = null;
                }
                if (resolvedCallableSymbol$default != null) {
                    resolvedReturnType = resolvedCallableSymbol$default.getResolvedReturnType();
                }
                if (coneKotlinType2 != null) {
                    return TypeUtilsKt.withNullability$default(coneKotlinType2, coneKotlinType.getNullability(), TypeComponentsKt.getTypeContext(firSession), null, false, 12, null);
                }
                return null;
            }
            coneKotlinType2 = null;
            if (coneKotlinType2 != null) {
                return TypeUtilsKt.withNullability$default(coneKotlinType2, coneKotlinType.getNullability(), TypeComponentsKt.getTypeContext(firSession), null, false, 12, null);
            }
            return null;
        }
        coneKotlinType2 = resolvedReturnType;
        if (coneKotlinType2 != null) {
            return TypeUtilsKt.withNullability$default(coneKotlinType2, coneKotlinType.getNullability(), TypeComponentsKt.getTypeContext(firSession), null, false, 12, null);
        }
        return null;
    }

    public static final boolean isFunctional(FirExpression firExpression, FirSession session, ScopeSession scopeSession, ConeKotlinType coneKotlinType, ReturnTypeCalculator returnTypeCalculator) {
        Candidate candidate;
        List<PostponedResolvedAtom> postponedAtoms;
        boolean z;
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(returnTypeCalculator, "returnTypeCalculator");
        FirExpression firExpressionUnwrapArgument = FirExpressionUtilKt.unwrapArgument(firExpression);
        if (firExpressionUnwrapArgument instanceof FirAnonymousFunctionExpression ? true : firExpressionUnwrapArgument instanceof FirCallableReferenceAccess) {
            return true;
        }
        FirTypeRef typeRef = firExpression.getTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = typeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) typeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeKotlinType)) {
            type = null;
        }
        boolean z2 = false;
        if (type == null) {
            return false;
        }
        if (FunctionalTypeUtilsKt.isSomeFunctionType(type, session)) {
            return true;
        }
        ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible = coneKotlinType != null ? ConeTypeUtilsKt.lowerBoundIfFlexible(coneKotlinType) : null;
        ConeClassLikeType coneClassLikeType = coneSimpleKotlinTypeLowerBoundIfFlexible instanceof ConeClassLikeType ? (ConeClassLikeType) coneSimpleKotlinTypeLowerBoundIfFlexible : null;
        if (coneClassLikeType == null || (type instanceof ConeIntegerLiteralType)) {
            return false;
        }
        FirNamedReferenceWithCandidate firNamedReferenceWithCandidateNamedReferenceWithCandidate = namedReferenceWithCandidate(firExpression);
        if (firNamedReferenceWithCandidateNamedReferenceWithCandidate != null && (candidate = firNamedReferenceWithCandidateNamedReferenceWithCandidate.getCandidate()) != null && (postponedAtoms = candidate.getPostponedAtoms()) != null) {
            List<PostponedResolvedAtom> list = postponedAtoms;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    PostponedResolvedAtom postponedResolvedAtom = (PostponedResolvedAtom) it.next();
                    if ((postponedResolvedAtom instanceof LambdaWithTypeVariableAsExpectedTypeAtom) && Intrinsics.areEqual(TypeSystemContextHelpersKt.typeConstructor(((LambdaWithTypeVariableAsExpectedTypeAtom) postponedResolvedAtom).getExpectedType(), TypeComponentsKt.getTypeContext(session)), TypeSystemContextHelpersKt.typeConstructor(type, TypeComponentsKt.getTypeContext(session)))) {
                        z = true;
                        break;
                    }
                }
            } else {
                z = false;
                break;
            }
            if (z) {
                z2 = true;
            }
        }
        if (z2) {
            return true;
        }
        return isSubtypeForSamConversion(session, scopeSession, type, coneClassLikeType, returnTypeCalculator);
    }

    private static final boolean isSubtypeForSamConversion(FirSession firSession, ScopeSession scopeSession, ConeKotlinType coneKotlinType, ConeClassLikeType coneClassLikeType, ReturnTypeCalculator returnTypeCalculator) {
        FirFunctionSymbol<?> firFunctionSymbolFindContributedInvokeSymbol = FunctionalTypeUtilsKt.findContributedInvokeSymbol(coneKotlinType, firSession, scopeSession, coneClassLikeType, false);
        if (firFunctionSymbolFindContributedInvokeSymbol == null) {
            return false;
        }
        ConeClassLikeType coneClassLikeType2 = coneClassLikeType;
        ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible = ConeTypeUtilsKt.lowerBoundIfFlexible(FunctionalTypeUtilsKt.returnType(coneClassLikeType2, firSession));
        if ((!(ConeTypeUtilsKt.originalIfDefinitelyNotNullable(coneSimpleKotlinTypeLowerBoundIfFlexible) instanceof ConeTypeParameterType) && !AbstractTypeChecker.INSTANCE.isSubtypeOf(TypeComponentsKt.getTypeContext(firSession).newTypeCheckerState(false, true), (KotlinTypeMarker) returnTypeCalculator.tryCalculateReturnType((FirCallableDeclaration) firFunctionSymbolFindContributedInvokeSymbol.getFir()).getType(), (KotlinTypeMarker) coneSimpleKotlinTypeLowerBoundIfFlexible, false)) || ((FirFunction) firFunctionSymbolFindContributedInvokeSymbol.getFir()).getValueParameters().size() != coneClassLikeType.getTypeArguments().length - 1) {
            return false;
        }
        List<Pair> listZip = CollectionsKt.zip(((FirFunction) firFunctionSymbolFindContributedInvokeSymbol.getFir()).getValueParameters(), FunctionalTypeUtilsKt.valueParameterTypesIncludingReceiver(coneClassLikeType2, firSession));
        if (!(listZip instanceof Collection) || !listZip.isEmpty()) {
            for (Pair pair : listZip) {
                FirValueParameter firValueParameter = (FirValueParameter) pair.component1();
                ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible2 = ConeTypeUtilsKt.lowerBoundIfFlexible((ConeKotlinType) pair.component2());
                if (!(ConeTypeUtilsKt.originalIfDefinitelyNotNullable(coneSimpleKotlinTypeLowerBoundIfFlexible2) instanceof ConeTypeParameterType) && !AbstractTypeChecker.INSTANCE.isSubtypeOf(TypeComponentsKt.getTypeContext(firSession).newTypeCheckerState(false, true), (KotlinTypeMarker) FirTypeUtilsKt.getConeType(firValueParameter.getReturnTypeRef()), (KotlinTypeMarker) coneSimpleKotlinTypeLowerBoundIfFlexible2, false)) {
                    return false;
                }
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final FirNamedReferenceWithCandidate namedReferenceWithCandidate(FirExpression firExpression) {
        if (firExpression instanceof FirResolvable) {
            FirReference calleeReference = ((FirResolvable) firExpression).getCalleeReference();
            if (calleeReference instanceof FirNamedReferenceWithCandidate) {
                return (FirNamedReferenceWithCandidate) calleeReference;
            }
            return null;
        }
        if (!(firExpression instanceof FirSafeCallExpression)) {
            return null;
        }
        FirStatement selector = ((FirSafeCallExpression) firExpression).getSelector();
        FirExpression firExpression2 = selector instanceof FirExpression ? (FirExpression) selector : null;
        if (firExpression2 != null) {
            return namedReferenceWithCandidate(firExpression2);
        }
        return null;
    }

    public static final ConeKotlinType getExpectedType(FirExpression firExpression, FirValueParameter parameter) {
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        if (firExpression instanceof FirSpreadArgumentExpression ? true : firExpression instanceof FirNamedArgumentExpression ? false : parameter.getIsVararg()) {
            return ArrayUtilsKt.varargElementType(FirTypeUtilsKt.getConeType(parameter.getReturnTypeRef()));
        }
        return FirTypeUtilsKt.getConeType(parameter.getReturnTypeRef());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final ConeKotlinType captureFromTypeParameterUpperBoundIfNeeded(ConeKotlinType argumentType, ConeKotlinType expectedType, FirSession session) {
        ConeKotlinType coneKotlinType;
        ConeDefinitelyNotNullType coneDefinitelyNotNullTypeCreate$default;
        Intrinsics.checkNotNullParameter(argumentType, "argumentType");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        Intrinsics.checkNotNullParameter(session, "session");
        ClassId classId = ConeTypeUtilsKt.getClassId(ConeTypeUtilsKt.upperBoundIfFlexible(expectedType));
        if (classId == null) {
            return argumentType;
        }
        ConeSimpleKotlinType coneSimpleKotlinTypeLowerBoundIfFlexible = ConeTypeUtilsKt.lowerBoundIfFlexible(argumentType);
        Object obj = null;
        ConeTypeParameterType coneTypeParameterType = coneSimpleKotlinTypeLowerBoundIfFlexible instanceof ConeTypeParameterType ? (ConeTypeParameterType) coneSimpleKotlinTypeLowerBoundIfFlexible : null;
        if (coneTypeParameterType == null) {
            return argumentType;
        }
        FirTypeParameter firTypeParameter = (FirTypeParameter) coneTypeParameterType.getLookupTag().getTypeParameterSymbol().getFir();
        ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(session);
        List<FirResolvedTypeRef> resolvedBounds = firTypeParameter.getSymbol().getResolvedBounds();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(resolvedBounds, 10));
        Iterator<T> it = resolvedBounds.iterator();
        while (it.hasNext()) {
            arrayList.add(FirTypeUtilsKt.getConeType((FirResolvedTypeRef) it.next()));
        }
        Iterator it2 = arrayList.iterator();
        boolean z = false;
        Object obj2 = null;
        while (true) {
            if (!it2.hasNext()) {
                if (!z) {
                    break;
                }
                obj = obj2;
                break;
            }
            Object next = it2.next();
            if (hasSupertypeWithGivenClassId((ConeKotlinType) next, classId, typeContext)) {
                if (z) {
                    break;
                }
                z = true;
                obj2 = next;
            }
        }
        ConeKotlinType coneKotlinType2 = (ConeKotlinType) obj;
        if (coneKotlinType2 == null || (coneKotlinType = (ConeKotlinType) typeContext.captureFromExpression(coneKotlinType2)) == null) {
            return argumentType;
        }
        return (!(argumentType instanceof ConeDefinitelyNotNullType) || (coneDefinitelyNotNullTypeCreate$default = TypeUtilsKt.create$default(ConeDefinitelyNotNullType.INSTANCE, coneKotlinType, TypeComponentsKt.getTypeContext(session), false, 4, null)) == null) ? coneKotlinType : coneDefinitelyNotNullTypeCreate$default;
    }

    private static final boolean hasSupertypeWithGivenClassId(ConeKotlinType coneKotlinType, final ClassId classId, final TypeSystemCommonSuperTypesContext typeSystemCommonSuperTypesContext) {
        return typeSystemCommonSuperTypesContext.anySuperTypeConstructor(coneKotlinType, new Function1<SimpleTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.ArgumentsKt$hasSupertypeWithGivenClassId$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(SimpleTypeMarker it) {
                Intrinsics.checkNotNullParameter(it, "it");
                TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeSystemCommonSuperTypesContext.typeConstructor(it);
                return Boolean.valueOf((typeConstructorMarkerTypeConstructor instanceof ConeClassLikeLookupTag) && Intrinsics.areEqual(((ConeClassLikeLookupTag) typeConstructorMarkerTypeConstructor).getClassId(), classId));
            }
        });
    }

    public static final void resolveArgument(Candidate candidate, CallInfo callInfo, FirExpression argument, FirValueParameter firValueParameter, boolean z, CheckerSink sink, ResolutionContext context) {
        Intrinsics.checkNotNullParameter(candidate, "<this>");
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(sink, "sink");
        Intrinsics.checkNotNullParameter(context, "context");
        PhaseUtilsKt.ensureResolvedTypeDeclaration$default(argument.getTypeRef(), context.getSession(), (FirResolvePhase) null, 2, (Object) null);
        resolveArgumentExpression(candidate, candidate.getSystem().getBuilder(), argument, prepareExpectedType(candidate, context.getSession(), context.getBodyResolveComponents().getScopeSession(), callInfo, argument, firValueParameter, context), sink, context, z, false);
    }
}
