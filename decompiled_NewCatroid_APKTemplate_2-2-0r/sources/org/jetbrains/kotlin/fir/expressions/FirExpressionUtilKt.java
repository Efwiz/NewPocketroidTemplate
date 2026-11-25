package org.jetbrains.kotlin.fir.expressions;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirExpressionRef;
import org.jetbrains.kotlin.fir.FirPureAbstractElement;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.expressions.builder.FirConstExpressionBuilderKt;
import org.jetbrains.kotlin.fir.expressions.builder.FirErrorExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirErrorLoopBuilder;
import org.jetbrains.kotlin.fir.expressions.impl.FirBlockImpl;
import org.jetbrains.kotlin.fir.expressions.impl.FirNoReceiverExpression;
import org.jetbrains.kotlin.fir.expressions.impl.FirResolvedArgumentList;
import org.jetbrains.kotlin.fir.expressions.impl.FirSingleExpressionBlock;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeProjectionWithVariance;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirErrorTypeRefBuilder;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.TransformData;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.types.ConstantValueKind;

/* JADX INFO: compiled from: FirExpressionUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000à\u0001\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0004\u001a;\u0010:\u001a\u00020\u0002\"\u0004\b\u0000\u0010;2\b\u0010<\u001a\u0004\u0018\u00010=2\f\u0010>\u001a\b\u0012\u0004\u0012\u0002H;0?2\b\u0010@\u001a\u0004\u0018\u0001H;2\u0006\u0010A\u001a\u00020B¢\u0006\u0002\u0010C\u001a$\u0010D\u001a\u00020E2\b\u0010<\u001a\u0004\u0018\u00010=2\u0006\u0010A\u001a\u00020B2\n\b\u0002\u0010F\u001a\u0004\u0018\u00010\u000f\u001a\u0018\u0010G\u001a\u00020H2\b\u0010<\u001a\u0004\u0018\u00010=2\u0006\u0010A\u001a\u00020B\u001a*\u0010I\u001a\u00020J*\u00020)2\u0018\u0010K\u001a\u0014\u0012\u0004\u0012\u00020M\u0012\u0004\u0012\u00020N\u0012\u0004\u0012\u00020J0LH\u0086\bø\u0001\u0000\u001a-\u0010O\u001a\u0002H;\"\b\b\u0000\u0010;*\u00020P*\u00020Q2\u0012\u0010R\u001a\u000e\u0012\u0004\u0012\u0002H;\u0012\u0004\u0012\u00020P0S¢\u0006\u0002\u0010T\u001a\f\u0010U\u001a\u0004\u0018\u00010\u000e*\u00020\u0002\u001a\f\u0010V\u001a\u0004\u0018\u00010W*\u00020\u0002\u001a\u0010\u0010X\u001a\b\u0012\u0002\b\u0003\u0018\u00010Y*\u00020\u0002\u001a8\u0010Z\u001a\u00020Q\"\u0004\b\u0000\u0010[*\u00020Q2\f\u0010\\\u001a\b\u0012\u0004\u0012\u0002H[0]2\u0018\u0010^\u001a\u0014\u0012\u0004\u0012\u00020_\u0012\n\u0012\b\u0012\u0004\u0012\u0002H[0`0S\u001a\u0010\u0010a\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0002\u001a\u001a\u0010b\u001a\u00020J*\u00020\u00022\f\u0010c\u001a\b\u0012\u0004\u0012\u00020\u00020dH\u0002\u001a\n\u0010e\u001a\u00020\u0002*\u00020\u0002\u001a\f\u0010f\u001a\u0004\u0018\u00010\u0003*\u00020\u0012\u001a\n\u0010g\u001a\u00020\u0002*\u00020\u0002\"\u001b\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u0016\u0010\u0006\u001a\u00020\u0002*\u00020\u00078Æ\u0002¢\u0006\u0006\u001a\u0004\b\b\u0010\t\"\u001c\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00078Æ\u0002¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f\"\u0017\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011\"\u0017\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\u00128F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0013\"\u001b\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00128F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016\"\u0015\u0010\u0017\u001a\u00020\u0002*\u00020\u00128F¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019\"\u0018\u0010\u001a\u001a\u0004\u0018\u00010\u001b*\u00020\u00078Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001d\"\u001e\u0010\u001e\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0001*\u00020\u00078Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001f\u0010\f\"\u0017\u0010 \u001a\u0004\u0018\u00010\u0002*\u00020\u00128F¢\u0006\u0006\u001a\u0004\b!\u0010\u0019\"\u0015\u0010\"\u001a\u00020\u0002*\u00020\u00128F¢\u0006\u0006\u001a\u0004\b#\u0010\u0019\"\u0015\u0010$\u001a\u00020%*\u00020&8F¢\u0006\u0006\u001a\u0004\b$\u0010'\"\u0016\u0010(\u001a\u00020%*\u00020)8Æ\u0002¢\u0006\u0006\u001a\u0004\b(\u0010*\"6\u0010+\u001a\"\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020-\u0018\u00010,j\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020-\u0018\u0001`.*\u00020\u00078Æ\u0002¢\u0006\u0006\u001a\u0004\b/\u00100\"\u0018\u00101\u001a\u0004\u0018\u000102*\u0002038Æ\u0002¢\u0006\u0006\u001a\u0004\b4\u00105\"\u0018\u00106\u001a\u0004\u0018\u000107*\u0002038Æ\u0002¢\u0006\u0006\u001a\u0004\b8\u00109\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006h"}, d2 = {"allReceiverExpressions", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;", "getAllReceiverExpressions", "(Lorg/jetbrains/kotlin/fir/expressions/FirQualifiedAccessExpression;)Ljava/util/List;", "argument", "Lorg/jetbrains/kotlin/fir/expressions/FirCall;", "getArgument", "(Lorg/jetbrains/kotlin/fir/expressions/FirCall;)Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "arguments", "getArguments", "(Lorg/jetbrains/kotlin/fir/expressions/FirCall;)Ljava/util/List;", "calleeReference", "Lorg/jetbrains/kotlin/fir/references/FirReference;", "Lorg/jetbrains/kotlin/fir/FirElement;", "getCalleeReference", "(Lorg/jetbrains/kotlin/fir/FirElement;)Lorg/jetbrains/kotlin/fir/references/FirReference;", "Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;", "(Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;)Lorg/jetbrains/kotlin/fir/references/FirReference;", "contextReceiverArguments", "getContextReceiverArguments", "(Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;)Ljava/util/List;", "dispatchReceiver", "getDispatchReceiver", "(Lorg/jetbrains/kotlin/fir/expressions/FirVariableAssignment;)Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "dynamicVararg", "Lorg/jetbrains/kotlin/fir/expressions/FirVarargArgumentsExpression;", "getDynamicVararg", "(Lorg/jetbrains/kotlin/fir/expressions/FirCall;)Lorg/jetbrains/kotlin/fir/expressions/FirVarargArgumentsExpression;", "dynamicVarargArguments", "getDynamicVarargArguments", "explicitReceiver", "getExplicitReceiver", "extensionReceiver", "getExtensionReceiver", "isBound", "", "Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;", "(Lorg/jetbrains/kotlin/fir/expressions/FirCallableReferenceAccess;)Z", "isCalleeDynamic", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "(Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;)Z", "resolvedArgumentMapping", "Ljava/util/LinkedHashMap;", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lkotlin/collections/LinkedHashMap;", "getResolvedArgumentMapping", "(Lorg/jetbrains/kotlin/fir/expressions/FirCall;)Ljava/util/LinkedHashMap;", "unexpandedClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getUnexpandedClassId", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;)Lorg/jetbrains/kotlin/name/ClassId;", "unexpandedConeClassLikeType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "getUnexpandedConeClassLikeType", "(Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;)Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "buildConstOrErrorExpression", ExifInterface.GPS_DIRECTION_TRUE, FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "value", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/types/ConstantValueKind;Ljava/lang/Object;Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;)Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "buildErrorExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorExpression;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "buildErrorLoop", "Lorg/jetbrains/kotlin/fir/expressions/FirErrorLoop;", "forAllReifiedTypeParameters", "", "block", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/types/FirTypeProjectionWithVariance;", "replaceFirstStatement", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "factory", "Lkotlin/Function1;", "(Lorg/jetbrains/kotlin/fir/expressions/FirBlock;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "toReference", "toResolvedCallableReference", "Lorg/jetbrains/kotlin/fir/references/FirResolvedNamedReference;", "toResolvedCallableSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "transformStatementsIndexed", "D", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "dataProducer", "", "Lorg/jetbrains/kotlin/fir/visitors/TransformData;", "unwrapAndFlattenArgument", "unwrapAndFlattenArgumentTo", SchemaSymbols.ATTVAL_LIST, "", "unwrapArgument", "unwrapLValue", "unwrapSmartcastExpression", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirExpressionUtilKt {
    public static final ConeClassLikeType getUnexpandedConeClassLikeType(FirAnnotation firAnnotation) {
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        FirTypeRef annotationTypeRef = firAnnotation.getAnnotationTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (type instanceof ConeClassLikeType) {
            return (ConeClassLikeType) type;
        }
        return null;
    }

    public static final <T> FirExpression buildConstOrErrorExpression(KtSourceElement ktSourceElement, ConstantValueKind<T> kind, T t, ConeDiagnostic diagnostic) {
        FirConstExpression firConstExpressionBuildConstExpression$default;
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        if (t != null && (firConstExpressionBuildConstExpression$default = FirConstExpressionBuilderKt.buildConstExpression$default(ktSourceElement, kind, t, null, false, 8, null)) != null) {
            return firConstExpressionBuildConstExpression$default;
        }
        FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
        firErrorExpressionBuilder.setSource(ktSourceElement);
        firErrorExpressionBuilder.setDiagnostic(diagnostic);
        return firErrorExpressionBuilder.mo6809build();
    }

    public static final List<FirExpression> getArguments(FirCall firCall) {
        Intrinsics.checkNotNullParameter(firCall, "<this>");
        return firCall.getArgumentList().getArguments();
    }

    public static final FirExpression getArgument(FirCall firCall) {
        Intrinsics.checkNotNullParameter(firCall, "<this>");
        return (FirExpression) CollectionsKt.first((List) firCall.getArgumentList().getArguments());
    }

    public static final boolean isCalleeDynamic(FirFunctionCall firFunctionCall) {
        Intrinsics.checkNotNullParameter(firFunctionCall, "<this>");
        FirNamedFunctionSymbol resolvedNamedFunctionSymbol$default = FirReferenceUtilsKt.toResolvedNamedFunctionSymbol$default(firFunctionCall.getCalleeReference(), false, 1, null);
        return Intrinsics.areEqual(resolvedNamedFunctionSymbol$default != null ? resolvedNamedFunctionSymbol$default.getOrigin() : null, FirDeclarationOrigin.DynamicScope.INSTANCE);
    }

    public static final LinkedHashMap<FirExpression, FirValueParameter> getResolvedArgumentMapping(FirCall firCall) {
        Intrinsics.checkNotNullParameter(firCall, "<this>");
        FirArgumentList argumentList = firCall.getArgumentList();
        if (argumentList instanceof FirResolvedArgumentList) {
            return ((FirResolvedArgumentList) argumentList).getMapping();
        }
        return null;
    }

    public static final FirResolvedNamedReference toResolvedCallableReference(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        FirReference reference = toReference(firExpression);
        if (reference != null) {
            return FirReferenceUtilsKt.getResolved(reference);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final FirReference toReference(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        if (firExpression instanceof FirWrappedArgumentExpression) {
            return toResolvedCallableReference(((FirWrappedArgumentExpression) firExpression).getExpression());
        }
        if (firExpression instanceof FirSmartCastExpression) {
            return toReference(((FirSmartCastExpression) firExpression).getOriginalExpression());
        }
        if (firExpression instanceof FirDesugaredAssignmentValueReferenceExpression) {
            return toReference(((FirDesugaredAssignmentValueReferenceExpression) firExpression).getExpressionRef().getValue());
        }
        if (firExpression instanceof FirResolvable) {
            return ((FirResolvable) firExpression).getCalleeReference();
        }
        return null;
    }

    public static final FirCallableSymbol<?> toResolvedCallableSymbol(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        FirResolvedNamedReference resolvedCallableReference = toResolvedCallableReference(firExpression);
        FirBasedSymbol<?> resolvedSymbol = resolvedCallableReference != null ? resolvedCallableReference.getResolvedSymbol() : null;
        if (resolvedSymbol instanceof FirCallableSymbol) {
            return (FirCallableSymbol) resolvedSymbol;
        }
        return null;
    }

    public static /* synthetic */ FirErrorExpression buildErrorExpression$default(KtSourceElement ktSourceElement, ConeDiagnostic coneDiagnostic, FirElement firElement, int i, Object obj) {
        if ((i & 4) != 0) {
            firElement = null;
        }
        return buildErrorExpression(ktSourceElement, coneDiagnostic, firElement);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <D> FirBlock transformStatementsIndexed(FirBlock firBlock, FirTransformer<? super D> transformer, Function1<? super Integer, ? extends TransformData<? extends D>> dataProducer) {
        Object value;
        Intrinsics.checkNotNullParameter(firBlock, "<this>");
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        Intrinsics.checkNotNullParameter(dataProducer, "dataProducer");
        int i = 0;
        if (!(firBlock instanceof FirBlockImpl)) {
            if (firBlock instanceof FirSingleExpressionBlock) {
                TransformData<? extends D> transformDataInvoke = dataProducer.invoke(0);
                TransformData.Data data = transformDataInvoke instanceof TransformData.Data ? (TransformData.Data) transformDataInvoke : null;
                if (data != null && (value = data.getValue()) != null) {
                    firBlock.transformStatements(transformer, value);
                }
            }
        } else {
            ListIterator<FirStatement> listIterator = ((FirBlockImpl) firBlock).getStatements().listIterator();
            while (listIterator.hasNext()) {
                Object next = listIterator.next();
                Intrinsics.checkNotNull(next, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.FirPureAbstractElement");
                FirPureAbstractElement firPureAbstractElement = (FirPureAbstractElement) next;
                int i2 = i + 1;
                TransformData<? extends D> transformDataInvoke2 = dataProducer.invoke(Integer.valueOf(i));
                if (!(transformDataInvoke2 instanceof TransformData.Data)) {
                    if (!Intrinsics.areEqual(transformDataInvoke2, TransformData.Nothing.INSTANCE)) {
                        throw new NoWhenBranchMatchedException();
                    }
                } else {
                    FirElement firElementTransform = firPureAbstractElement.transform(transformer, ((TransformData.Data) transformDataInvoke2).getValue());
                    if (firElementTransform != firPureAbstractElement) {
                        listIterator.set(firElementTransform);
                    }
                }
                i = i2;
            }
        }
        return firBlock;
    }

    public static final <T extends FirStatement> T replaceFirstStatement(FirBlock firBlock, Function1<? super T, ? extends FirStatement> factory) {
        Intrinsics.checkNotNullParameter(firBlock, "<this>");
        Intrinsics.checkNotNullParameter(factory, "factory");
        if (!(firBlock instanceof FirBlockImpl)) {
            throw new IllegalArgumentException(("replaceFirstStatement should not be called for " + Reflection.getOrCreateKotlinClass(firBlock.getClass()).getSimpleName()).toString());
        }
        FirBlockImpl firBlockImpl = (FirBlockImpl) firBlock;
        FirStatement firStatement = firBlockImpl.getStatements().get(0);
        Intrinsics.checkNotNull(firStatement, "null cannot be cast to non-null type T of org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt.replaceFirstStatement");
        T t = (T) firStatement;
        firBlockImpl.getStatements().set(0, factory.invoke(t));
        return t;
    }

    public static final FirExpression unwrapArgument(FirExpression firExpression) {
        FirExpression expression;
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        FirWrappedArgumentExpression firWrappedArgumentExpression = firExpression instanceof FirWrappedArgumentExpression ? (FirWrappedArgumentExpression) firExpression : null;
        return (firWrappedArgumentExpression == null || (expression = firWrappedArgumentExpression.getExpression()) == null) ? firExpression : expression;
    }

    public static final List<FirExpression> unwrapAndFlattenArgument(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        unwrapAndFlattenArgumentTo(firExpression, listCreateListBuilder);
        return CollectionsKt.build(listCreateListBuilder);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final void unwrapAndFlattenArgumentTo(FirExpression firExpression, List<FirExpression> list) {
        FirAnnotationContainer firAnnotationContainerUnwrapArgument = unwrapArgument(firExpression);
        if (!(firAnnotationContainerUnwrapArgument instanceof FirArrayLiteral ? true : firAnnotationContainerUnwrapArgument instanceof FirFunctionCall)) {
            if (!(firAnnotationContainerUnwrapArgument instanceof FirVarargArgumentsExpression)) {
                list.add(firAnnotationContainerUnwrapArgument);
                return;
            }
            Iterator<T> it = ((FirVarargArgumentsExpression) firAnnotationContainerUnwrapArgument).getArguments().iterator();
            while (it.hasNext()) {
                unwrapAndFlattenArgumentTo((FirExpression) it.next(), list);
            }
            return;
        }
        Intrinsics.checkNotNull(firAnnotationContainerUnwrapArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirCall");
        Iterator<T> it2 = ((FirCall) firAnnotationContainerUnwrapArgument).getArgumentList().getArguments().iterator();
        while (it2.hasNext()) {
            unwrapAndFlattenArgumentTo((FirExpression) it2.next(), list);
        }
    }

    public static final FirExpression getExplicitReceiver(FirVariableAssignment firVariableAssignment) {
        Intrinsics.checkNotNullParameter(firVariableAssignment, "<this>");
        FirQualifiedAccessExpression firQualifiedAccessExpressionUnwrapLValue = unwrapLValue(firVariableAssignment);
        if (firQualifiedAccessExpressionUnwrapLValue != null) {
            return firQualifiedAccessExpressionUnwrapLValue.getExplicitReceiver();
        }
        return null;
    }

    public static final FirExpression getDispatchReceiver(FirVariableAssignment firVariableAssignment) {
        FirExpression dispatchReceiver;
        Intrinsics.checkNotNullParameter(firVariableAssignment, "<this>");
        FirQualifiedAccessExpression firQualifiedAccessExpressionUnwrapLValue = unwrapLValue(firVariableAssignment);
        return (firQualifiedAccessExpressionUnwrapLValue == null || (dispatchReceiver = firQualifiedAccessExpressionUnwrapLValue.getDispatchReceiver()) == null) ? FirNoReceiverExpression.INSTANCE : dispatchReceiver;
    }

    public static final FirExpression getExtensionReceiver(FirVariableAssignment firVariableAssignment) {
        FirExpression extensionReceiver;
        Intrinsics.checkNotNullParameter(firVariableAssignment, "<this>");
        FirQualifiedAccessExpression firQualifiedAccessExpressionUnwrapLValue = unwrapLValue(firVariableAssignment);
        return (firQualifiedAccessExpressionUnwrapLValue == null || (extensionReceiver = firQualifiedAccessExpressionUnwrapLValue.getExtensionReceiver()) == null) ? FirNoReceiverExpression.INSTANCE : extensionReceiver;
    }

    public static final FirReference getCalleeReference(FirVariableAssignment firVariableAssignment) {
        Intrinsics.checkNotNullParameter(firVariableAssignment, "<this>");
        return toReference(firVariableAssignment.getLValue());
    }

    public static final List<FirExpression> getContextReceiverArguments(FirVariableAssignment firVariableAssignment) {
        List<FirExpression> contextReceiverArguments;
        Intrinsics.checkNotNullParameter(firVariableAssignment, "<this>");
        FirQualifiedAccessExpression firQualifiedAccessExpressionUnwrapLValue = unwrapLValue(firVariableAssignment);
        return (firQualifiedAccessExpressionUnwrapLValue == null || (contextReceiverArguments = firQualifiedAccessExpressionUnwrapLValue.getContextReceiverArguments()) == null) ? CollectionsKt.emptyList() : contextReceiverArguments;
    }

    public static final FirQualifiedAccessExpression unwrapLValue(FirVariableAssignment firVariableAssignment) {
        FirExpressionRef<FirExpression> expressionRef;
        Intrinsics.checkNotNullParameter(firVariableAssignment, "<this>");
        FirExpression lValue = firVariableAssignment.getLValue();
        FirQualifiedAccessExpression firQualifiedAccessExpression = lValue instanceof FirQualifiedAccessExpression ? (FirQualifiedAccessExpression) lValue : null;
        if (firQualifiedAccessExpression != null) {
            return firQualifiedAccessExpression;
        }
        FirDesugaredAssignmentValueReferenceExpression firDesugaredAssignmentValueReferenceExpression = lValue instanceof FirDesugaredAssignmentValueReferenceExpression ? (FirDesugaredAssignmentValueReferenceExpression) lValue : null;
        FirExpression value = (firDesugaredAssignmentValueReferenceExpression == null || (expressionRef = firDesugaredAssignmentValueReferenceExpression.getExpressionRef()) == null) ? null : expressionRef.getValue();
        if (value instanceof FirQualifiedAccessExpression) {
            return (FirQualifiedAccessExpression) value;
        }
        return null;
    }

    public static final FirReference getCalleeReference(FirElement firElement) {
        FirReference calleeReference;
        Intrinsics.checkNotNullParameter(firElement, "<this>");
        FirResolvable firResolvable = firElement instanceof FirResolvable ? (FirResolvable) firElement : null;
        if (firResolvable != null && (calleeReference = firResolvable.getCalleeReference()) != null) {
            return calleeReference;
        }
        FirVariableAssignment firVariableAssignment = firElement instanceof FirVariableAssignment ? (FirVariableAssignment) firElement : null;
        if (firVariableAssignment != null) {
            return getCalleeReference(firVariableAssignment);
        }
        return null;
    }

    public static final FirExpression unwrapSmartcastExpression(FirExpression firExpression) {
        Intrinsics.checkNotNullParameter(firExpression, "<this>");
        return firExpression instanceof FirSmartCastExpression ? ((FirSmartCastExpression) firExpression).getOriginalExpression() : firExpression;
    }

    public static final boolean isBound(FirCallableReferenceAccess firCallableReferenceAccess) {
        Intrinsics.checkNotNullParameter(firCallableReferenceAccess, "<this>");
        if (Intrinsics.areEqual(firCallableReferenceAccess.getDispatchReceiver(), FirNoReceiverExpression.INSTANCE) && Intrinsics.areEqual(firCallableReferenceAccess.getExtensionReceiver(), FirNoReceiverExpression.INSTANCE)) {
            return false;
        }
        FirCallableSymbol resolvedCallableSymbol$default = FirReferenceUtilsKt.toResolvedCallableSymbol$default(firCallableReferenceAccess.getCalleeReference(), false, 1, null);
        return resolvedCallableSymbol$default == null || !resolvedCallableSymbol$default.getRawStatus().isStatic();
    }

    public static final List<FirExpression> getAllReceiverExpressions(FirQualifiedAccessExpression firQualifiedAccessExpression) {
        Intrinsics.checkNotNullParameter(firQualifiedAccessExpression, "<this>");
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        List list = listCreateListBuilder;
        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(list, firQualifiedAccessExpression.getDispatchReceiver());
        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(list, firQualifiedAccessExpression.getExtensionReceiver());
        listCreateListBuilder.addAll(firQualifiedAccessExpression.getContextReceiverArguments());
        return CollectionsKt.build(listCreateListBuilder);
    }

    public static final void forAllReifiedTypeParameters(FirFunctionCall firFunctionCall, Function2<? super ConeKotlinType, ? super FirTypeProjectionWithVariance, Unit> block) {
        ConeKotlinType coneTypeOrNull;
        Intrinsics.checkNotNullParameter(firFunctionCall, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        FirNamedFunctionSymbol resolvedNamedFunctionSymbol$default = FirReferenceUtilsKt.toResolvedNamedFunctionSymbol$default(firFunctionCall.getCalleeReference(), false, 1, null);
        if (resolvedNamedFunctionSymbol$default == null) {
            return;
        }
        for (Pair pair : CollectionsKt.zip(resolvedNamedFunctionSymbol$default.getTypeParameterSymbols(), firFunctionCall.getTypeArguments())) {
            FirTypeParameterSymbol firTypeParameterSymbol = (FirTypeParameterSymbol) pair.component1();
            FirTypeProjection firTypeProjection = (FirTypeProjection) pair.component2();
            if (firTypeParameterSymbol.isReified() && (firTypeProjection instanceof FirTypeProjectionWithVariance) && (coneTypeOrNull = FirTypeUtilsKt.getConeTypeOrNull(((FirTypeProjectionWithVariance) firTypeProjection).getTypeRef())) != null) {
                block.invoke(coneTypeOrNull, firTypeProjection);
            }
        }
    }

    public static final ClassId getUnexpandedClassId(FirAnnotation firAnnotation) {
        ConeClassLikeLookupTag lookupTag;
        Intrinsics.checkNotNullParameter(firAnnotation, "<this>");
        FirTypeRef annotationTypeRef = firAnnotation.getAnnotationTypeRef();
        FirResolvedTypeRef firResolvedTypeRef = annotationTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) annotationTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        ConeClassLikeType coneClassLikeType = type instanceof ConeClassLikeType ? (ConeClassLikeType) type : null;
        if (coneClassLikeType == null || (lookupTag = coneClassLikeType.getLookupTag()) == null) {
            return null;
        }
        return lookupTag.getClassId();
    }

    public static final FirVarargArgumentsExpression getDynamicVararg(FirCall firCall) {
        Intrinsics.checkNotNullParameter(firCall, "<this>");
        Object objFirstOrNull = CollectionsKt.firstOrNull((List<? extends Object>) firCall.getArgumentList().getArguments());
        if (objFirstOrNull instanceof FirVarargArgumentsExpression) {
            return (FirVarargArgumentsExpression) objFirstOrNull;
        }
        return null;
    }

    public static final List<FirExpression> getDynamicVarargArguments(FirCall firCall) {
        Intrinsics.checkNotNullParameter(firCall, "<this>");
        Object objFirstOrNull = CollectionsKt.firstOrNull((List<? extends Object>) firCall.getArgumentList().getArguments());
        FirVarargArgumentsExpression firVarargArgumentsExpression = objFirstOrNull instanceof FirVarargArgumentsExpression ? (FirVarargArgumentsExpression) objFirstOrNull : null;
        if (firVarargArgumentsExpression != null) {
            return firVarargArgumentsExpression.getArguments();
        }
        return null;
    }

    public static final FirErrorLoop buildErrorLoop(KtSourceElement ktSourceElement, ConeDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        FirErrorLoopBuilder firErrorLoopBuilder = new FirErrorLoopBuilder();
        firErrorLoopBuilder.setSource(ktSourceElement);
        firErrorLoopBuilder.setDiagnostic(diagnostic);
        FirErrorLoop firErrorLoopMo6809build = firErrorLoopBuilder.mo6809build();
        FirBlock block = firErrorLoopMo6809build.getBlock();
        FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
        firErrorTypeRefBuilder.setDiagnostic(diagnostic);
        block.replaceTypeRef(firErrorTypeRefBuilder.mo6809build());
        return firErrorLoopMo6809build;
    }

    public static final FirErrorExpression buildErrorExpression(KtSourceElement ktSourceElement, ConeDiagnostic diagnostic, FirElement firElement) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
        firErrorExpressionBuilder.setSource(ktSourceElement);
        firErrorExpressionBuilder.setDiagnostic(diagnostic);
        boolean z = firElement instanceof FirExpression;
        firErrorExpressionBuilder.setExpression(z ? (FirExpression) firElement : null);
        if (Boolean.valueOf(z).booleanValue()) {
            firElement = null;
        }
        firErrorExpressionBuilder.setNonExpressionElement(firElement);
        return firErrorExpressionBuilder.mo6809build();
    }
}
