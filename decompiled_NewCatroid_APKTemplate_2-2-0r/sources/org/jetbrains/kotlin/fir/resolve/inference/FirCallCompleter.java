package org.jetbrains.kotlin.fir.resolve.inference;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.CopyUtilsKt;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponent;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponentKt;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTowerDataContext;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.builder.FirContextReceiverBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirValueParameterBuilder;
import org.jetbrains.kotlin.fir.expressions.FirAnonymousFunctionExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirLambdaArgumentExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.resolve.FirRegularTowerDataContexts;
import org.jetbrains.kotlin.fir.resolve.FirTowerDataMode;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.ResolveUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.CallableReferenceResolutionKt;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.resolve.calls.FirNamedReferenceWithCandidate;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.FirAnonymousFunctionReturnExpressionInfo;
import org.jetbrains.kotlin.fir.resolve.inference.model.ConeArgumentConstraintPosition;
import org.jetbrains.kotlin.fir.resolve.inference.model.ConeExpectedTypeConstraintPosition;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.transformers.FirCallCompletionResultsWriterTransformer;
import org.jetbrains.kotlin.fir.resolve.transformers.InvocationKindTransformerKt;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.BodyResolveContext;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformer;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformerDispatcher;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirImplicitTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilderKt;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionMode;
import org.jetbrains.kotlin.resolve.calls.inference.components.PostponedArgumentInputTypesResolver;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl;
import org.jetbrains.kotlin.types.TypeApproximatorConfiguration;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.MarkerExtensionsKt;
import org.jetbrains.kotlin.types.model.StubTypeMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: FirCallCompleter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u00012B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J \u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J)\u0010\u0019\u001a\u0002H\u001a\"\f\b\u0000\u0010\u001a*\u00020\u001b*\u00020\u001c2\u0006\u0010\u001d\u001a\u0002H\u001a2\u0006\u0010\u0017\u001a\u00020\u0018¢\u0006\u0002\u0010\u001eJ\u0018\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\b\b\u0002\u0010#\u001a\u00020$J\u000e\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(J\u0016\u0010)\u001a\u00020\u00122\u0006\u0010*\u001a\u00020+2\u0006\u0010\u0013\u001a\u00020\u0014JE\u0010,\u001a\u00020\u0012\"\f\b\u0000\u0010\u001a*\u00020\u001b*\u00020\u001c2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010-\u001a\u00020.2\u0006\u0010\u001d\u001a\u0002H\u001a2\u0006\u0010\u0015\u001a\u00020\u00162\n\b\u0002\u0010/\u001a\u0004\u0018\u00010&¢\u0006\u0002\u00100J\f\u00101\u001a\u00020\u0016*\u00020\u0016H\u0002R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/FirCallCompleter;", "", "transformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;", "components", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer$BodyResolveTransformerComponents;", "(Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer$BodyResolveTransformerComponents;)V", "completer", "Lorg/jetbrains/kotlin/fir/resolve/inference/ConstraintSystemCompleter;", "getCompleter", "()Lorg/jetbrains/kotlin/fir/resolve/inference/ConstraintSystemCompleter;", "inferenceSession", "Lorg/jetbrains/kotlin/fir/resolve/inference/FirInferenceSession;", "getInferenceSession", "()Lorg/jetbrains/kotlin/fir/resolve/inference/FirInferenceSession;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "addConstraintFromExpectedType", "", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "initialType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "resolutionMode", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "completeCall", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "call", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;)Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;", "createCompletionResultsWriter", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirCallCompletionResultsWriterTransformer;", "substitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "mode", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirCallCompletionResultsWriterTransformer$Mode;", "createPostponedArgumentsAnalyzer", "Lorg/jetbrains/kotlin/fir/resolve/inference/PostponedArgumentsAnalyzer;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "prepareLambdaAtomForFactoryPattern", "atom", "Lorg/jetbrains/kotlin/fir/resolve/inference/ResolvedLambdaAtom;", "runCompletionForCall", "completionMode", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionMode;", "analyzer", "(Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionMode;Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/resolve/inference/PostponedArgumentsAnalyzer;)V", "approximateLambdaInputType", "LambdaAnalyzerImpl", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirCallCompleter {
    private final ConstraintSystemCompleter completer;
    private final FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents components;
    private final FirSession session;
    private final FirAbstractBodyResolveTransformerDispatcher transformer;

    /* JADX INFO: compiled from: FirCallCompleter.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ConstraintSystemCompletionMode.values().length];
            try {
                iArr[ConstraintSystemCompletionMode.FULL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ConstraintSystemCompletionMode.PARTIAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ConstraintSystemCompletionMode.UNTIL_FIRST_LAMBDA.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public FirCallCompleter(FirAbstractBodyResolveTransformerDispatcher transformer, FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents components) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        Intrinsics.checkNotNullParameter(components, "components");
        this.transformer = transformer;
        this.components = components;
        this.session = components.getSession();
        this.completer = new ConstraintSystemCompleter(components, transformer.getContext());
    }

    private final FirInferenceSession getInferenceSession() {
        return this.transformer.getContext().getInferenceSession();
    }

    public final ConstraintSystemCompleter getCompleter() {
        return this.completer;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <T extends FirResolvable & FirStatement> T completeCall(T call, ResolutionMode resolutionMode) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(resolutionMode, "resolutionMode");
        FirResolvedTypeRef firResolvedTypeRefTypeFromCallee = ResolveUtilsKt.typeFromCallee(this.components, call);
        FirReference calleeReference = call.getCalleeReference();
        FirNamedReferenceWithCandidate firNamedReferenceWithCandidate = calleeReference instanceof FirNamedReferenceWithCandidate ? (FirNamedReferenceWithCandidate) calleeReference : null;
        if (firNamedReferenceWithCandidate == null) {
            return call;
        }
        Candidate candidate = firNamedReferenceWithCandidate.getCandidate();
        ConeKotlinType coneKotlinTypeInitialTypeOfCandidate = ResolveUtilsKt.initialTypeOfCandidate(firResolvedTypeRefTypeFromCallee, candidate);
        if (call instanceof FirExpression) {
            FirResolvedTypeRef firResolvedTypeRefResolvedTypeFromPrototype$default = CopyUtilsKt.resolvedTypeFromPrototype$default(firResolvedTypeRefTypeFromCallee, coneKotlinTypeInitialTypeOfCandidate, null, 2, null);
            ((FirExpression) call).replaceTypeRef(firResolvedTypeRefResolvedTypeFromPrototype$default);
            FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(this.session);
            if (lookupTracker != null) {
                FirLookupTrackerComponentKt.recordTypeResolveAsLookup(lookupTracker, firResolvedTypeRefResolvedTypeFromPrototype$default, call.getSource(), this.components.getContext().getFile().getSource());
            }
        }
        addConstraintFromExpectedType(candidate, coneKotlinTypeInitialTypeOfCandidate, resolutionMode);
        ConstraintSystemCompletionMode constraintSystemCompletionModeComputeCompletionMode = CompletionModeCalculatorKt.computeCompletionMode(candidate, InferenceComponentsKt.getInferenceComponents(this.session), resolutionMode, coneKotlinTypeInitialTypeOfCandidate);
        PostponedArgumentsAnalyzer postponedArgumentsAnalyzerCreatePostponedArgumentsAnalyzer = createPostponedArgumentsAnalyzer(this.transformer.getResolutionContext());
        if (call instanceof FirFunctionCall) {
            InvocationKindTransformerKt.replaceLambdaArgumentInvocationKinds((FirFunctionCall) call, this.session);
        }
        int i = WhenMappings.$EnumSwitchMapping$0[constraintSystemCompletionModeComputeCompletionMode.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                throw new IllegalStateException();
            }
            runCompletionForCall(candidate, constraintSystemCompletionModeComputeCompletionMode, call, coneKotlinTypeInitialTypeOfCandidate, postponedArgumentsAnalyzerCreatePostponedArgumentsAnalyzer);
            if (resolutionMode instanceof ResolutionMode.ContextDependent.Delegate) {
                if (!(getInferenceSession() instanceof FirDelegatedPropertyInferenceSession)) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                getInferenceSession().addPartiallyResolvedCall(call);
            }
        } else {
            if (getInferenceSession().shouldRunCompletion(call)) {
                runCompletionForCall(candidate, constraintSystemCompletionModeComputeCompletionMode, call, coneKotlinTypeInitialTypeOfCandidate, postponedArgumentsAnalyzerCreatePostponedArgumentsAnalyzer);
                TypeSubstitutorMarker typeSubstitutorMarkerBuildAbstractResultingSubstitutor$default = org.jetbrains.kotlin.resolve.calls.inference.InferenceUtilsKt.buildAbstractResultingSubstitutor$default(candidate.getSystem().asReadOnlyStorage(), TypeComponentsKt.getTypeContext(this.session), false, 2, null);
                Intrinsics.checkNotNull(typeSubstitutorMarkerBuildAbstractResultingSubstitutor$default, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor");
                T t = (T) ((FirResolvable) FirTransformerUtilKt.transformSingle(call, new FirCallCompletionResultsWriterTransformer(this.session, (ConeSubstitutor) typeSubstitutorMarkerBuildAbstractResultingSubstitutor$default, this.components.getReturnTypeCalculator(), TypeComponentsKt.getTypeApproximator(this.session), this.components.getDataFlowAnalyzer(), this.components.getIntegerLiteralAndOperatorApproximationTransformer(), this.components.getSamResolver(), this.components.getContext(), null, 256, null), null));
                getInferenceSession().addCompletedCall(t, candidate);
                return t;
            }
            getInferenceSession().addPartiallyResolvedCall(call);
        }
        return call;
    }

    private final void addConstraintFromExpectedType(Candidate candidate, ConeKotlinType initialType, ResolutionMode resolutionMode) {
        if (resolutionMode instanceof ResolutionMode.WithExpectedType) {
            ResolutionMode.WithExpectedType withExpectedType = (ResolutionMode.WithExpectedType) resolutionMode;
            FirResolvedTypeRef expectedTypeRef = withExpectedType.getExpectedTypeRef();
            FirResolvedTypeRef firResolvedTypeRef = expectedTypeRef instanceof FirResolvedTypeRef ? expectedTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            ConeKotlinType coneKotlinType = type instanceof ConeKotlinType ? type : null;
            if (coneKotlinType == null) {
                return;
            }
            NewConstraintSystemImpl system = candidate.getSystem();
            if (!withExpectedType.getShouldBeStrictlyEnforced() || withExpectedType.getExpectedTypeMismatchIsReportedInChecker()) {
                ConstraintSystemBuilderKt.addSubtypeConstraintIfCompatible(system, initialType, coneKotlinType, ConeExpectedTypeConstraintPosition.INSTANCE);
                return;
            }
            if (withExpectedType.getFromCast()) {
                if (FirCallCompleterKt.isFunctionForExpectTypeFromCastFeature(candidate)) {
                    system.addSubtypeConstraint(initialType, coneKotlinType, ConeExpectedTypeConstraintPosition.INSTANCE);
                }
            } else if (!CallableReferenceResolutionKt.isUnitOrFlexibleUnit(coneKotlinType) || !withExpectedType.getMayBeCoercionToUnitApplied()) {
                system.addSubtypeConstraint(initialType, coneKotlinType, ConeExpectedTypeConstraintPosition.INSTANCE);
            } else {
                if (system.getNotFixedTypeVariables().isEmpty()) {
                    return;
                }
                if (ConeBuiltinTypeUtilsKt.isUnit(coneKotlinType)) {
                    ConstraintSystemBuilderKt.addEqualityConstraintIfCompatible(system, initialType, coneKotlinType, ConeExpectedTypeConstraintPosition.INSTANCE);
                } else {
                    ConstraintSystemBuilderKt.addSubtypeConstraintIfCompatible(system, initialType, coneKotlinType, ConeExpectedTypeConstraintPosition.INSTANCE);
                }
            }
        }
    }

    public static /* synthetic */ void runCompletionForCall$default(FirCallCompleter firCallCompleter, Candidate candidate, ConstraintSystemCompletionMode constraintSystemCompletionMode, FirResolvable firResolvable, ConeKotlinType coneKotlinType, PostponedArgumentsAnalyzer postponedArgumentsAnalyzer, int i, Object obj) {
        if ((i & 16) != 0) {
            postponedArgumentsAnalyzer = null;
        }
        firCallCompleter.runCompletionForCall(candidate, constraintSystemCompletionMode, firResolvable, coneKotlinType, postponedArgumentsAnalyzer);
    }

    public final <T extends FirResolvable & FirStatement> void runCompletionForCall(final Candidate candidate, final ConstraintSystemCompletionMode completionMode, T call, ConeKotlinType initialType, final PostponedArgumentsAnalyzer analyzer) {
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(completionMode, "completionMode");
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(initialType, "initialType");
        if (analyzer == null) {
            analyzer = createPostponedArgumentsAnalyzer(this.transformer.getResolutionContext());
        }
        this.completer.complete(candidate.getSystem().asConstraintSystemCompleterContext(), completionMode, CollectionsKt.listOf(call), initialType, this.transformer.getResolutionContext(), (32 & 32) != 0 ? false : false, new Function1<PostponedResolvedAtom, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.inference.FirCallCompleter.runCompletionForCall.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(PostponedResolvedAtom postponedResolvedAtom) {
                invoke2(postponedResolvedAtom);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(PostponedResolvedAtom it) {
                Intrinsics.checkNotNullParameter(it, "it");
                analyzer.analyze(candidate.getSystem(), it, candidate, completionMode);
            }
        });
    }

    public final void prepareLambdaAtomForFactoryPattern(ResolvedLambdaAtom atom, Candidate candidate) {
        Intrinsics.checkNotNullParameter(atom, "atom");
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        ConeTypeVariableForLambdaReturnType coneTypeVariableForLambdaReturnType = new ConeTypeVariableForLambdaReturnType(atom.getReference(), PostponedArgumentInputTypesResolver.TYPE_VARIABLE_NAME_FOR_LAMBDA_RETURN_TYPE);
        NewConstraintSystemImpl builder = candidate.getSystem().getBuilder();
        builder.registerVariable(coneTypeVariableForLambdaReturnType);
        ConeKotlinType expectedType = atom.getExpectedType();
        Intrinsics.checkNotNull(expectedType);
        KotlinTypeMarker kotlinTypeMarkerSafeSubstitute = MarkerExtensionsKt.safeSubstitute(builder.buildCurrentSubstitutor(), builder, expectedType);
        Intrinsics.checkNotNull(kotlinTypeMarkerSafeSubstitute, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeClassLikeType");
        ConeClassLikeType coneClassLikeType = (ConeClassLikeType) kotlinTypeMarkerSafeSubstitute;
        int length = coneClassLikeType.getTypeArguments().length;
        ConeClassLikeLookupTag lookupTag = coneClassLikeType.getLookupTag();
        ConeTypeProjection[] coneTypeProjectionArr = new ConeTypeProjection[length];
        int i = 0;
        while (i < length) {
            coneTypeProjectionArr[i] = i != length + (-1) ? coneClassLikeType.getTypeArguments()[i] : coneTypeVariableForLambdaReturnType.getDefaultType();
            i++;
        }
        ConeClassLikeTypeImpl coneClassLikeTypeImpl = new ConeClassLikeTypeImpl(lookupTag, coneTypeProjectionArr, ConeTypeUtilsKt.isNullable(coneClassLikeType), coneClassLikeType.getAttributes());
        builder.addSubtypeConstraint(coneClassLikeTypeImpl, coneClassLikeType, new ConeArgumentConstraintPosition(atom.getReference()));
        atom.replaceExpectedType(coneClassLikeTypeImpl, coneTypeVariableForLambdaReturnType.getDefaultType());
        atom.replaceTypeVariableForLambdaReturnType(coneTypeVariableForLambdaReturnType);
    }

    public static /* synthetic */ FirCallCompletionResultsWriterTransformer createCompletionResultsWriter$default(FirCallCompleter firCallCompleter, ConeSubstitutor coneSubstitutor, FirCallCompletionResultsWriterTransformer.Mode mode, int i, Object obj) {
        if ((i & 2) != 0) {
            mode = FirCallCompletionResultsWriterTransformer.Mode.Normal;
        }
        return firCallCompleter.createCompletionResultsWriter(coneSubstitutor, mode);
    }

    public final FirCallCompletionResultsWriterTransformer createCompletionResultsWriter(ConeSubstitutor substitutor, FirCallCompletionResultsWriterTransformer.Mode mode) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        Intrinsics.checkNotNullParameter(mode, "mode");
        return new FirCallCompletionResultsWriterTransformer(this.session, substitutor, this.components.getReturnTypeCalculator(), TypeComponentsKt.getTypeApproximator(this.session), this.components.getDataFlowAnalyzer(), this.components.getIntegerLiteralAndOperatorApproximationTransformer(), this.components.getSamResolver(), this.components.getContext(), mode);
    }

    public final PostponedArgumentsAnalyzer createPostponedArgumentsAnalyzer(ResolutionContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return new PostponedArgumentsAnalyzer(context, new LambdaAnalyzerImpl(), InferenceComponentsKt.getInferenceComponents(this.session), this.transformer.getComponents().getCallResolver());
    }

    /* JADX INFO: compiled from: FirCallCompleter.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\\\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\n2\b\u0010\f\u001a\u0004\u0018\u00010\b2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000e2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/FirCallCompleter$LambdaAnalyzerImpl;", "Lorg/jetbrains/kotlin/fir/resolve/inference/LambdaAnalyzer;", "(Lorg/jetbrains/kotlin/fir/resolve/inference/FirCallCompleter;)V", "analyzeAndGetLambdaReturnArguments", "Lorg/jetbrains/kotlin/fir/resolve/inference/ReturnArgumentsAnalysisResult;", "lambdaAtom", "Lorg/jetbrains/kotlin/fir/resolve/inference/ResolvedLambdaAtom;", "receiverType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "contextReceivers", "", "parameters", "expectedReturnType", "stubsForPostponedVariables", "", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "Lorg/jetbrains/kotlin/types/model/StubTypeMarker;", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class LambdaAnalyzerImpl implements LambdaAnalyzer {
        public LambdaAnalyzerImpl() {
        }

        /* JADX WARN: Code duplicated, block: B:7:0x0040  */
        @Override // org.jetbrains.kotlin.fir.resolve.inference.LambdaAnalyzer
        public ReturnArgumentsAnalysisResult analyzeAndGetLambdaReturnArguments(ResolvedLambdaAtom lambdaAtom, ConeKotlinType receiverType, List<? extends ConeKotlinType> contextReceivers, List<? extends ConeKotlinType> parameters, ConeKotlinType expectedReturnType, Map<TypeVariableMarker, ? extends StubTypeMarker> stubsForPostponedVariables, Candidate candidate) {
            boolean z;
            FirValueParameter value;
            FirValueParameter firValueParameterBuild;
            List<? extends ConeKotlinType> listPlus;
            FirResolvedTypeRef noExpectedType;
            FirResolvedTypeRef firResolvedTypeRefResolvedTypeFromPrototype$default;
            FirTypeRef returnTypeRef;
            Intrinsics.checkNotNullParameter(lambdaAtom, "lambdaAtom");
            Intrinsics.checkNotNullParameter(contextReceivers, "contextReceivers");
            Intrinsics.checkNotNullParameter(parameters, "parameters");
            Intrinsics.checkNotNullParameter(stubsForPostponedVariables, "stubsForPostponedVariables");
            Intrinsics.checkNotNullParameter(candidate, "candidate");
            FirAnonymousFunction atom = lambdaAtom.getReference();
            if (atom.getValueParameters().isEmpty()) {
                z = parameters.size() == 1;
            }
            LinkedHashMap<FirExpression, FirValueParameter> argumentMapping = candidate.getArgumentMapping();
            if (argumentMapping == null) {
                value = null;
                break;
            }
            Iterator<Map.Entry<FirExpression, FirValueParameter>> it = argumentMapping.entrySet().iterator();
            do {
                if (!it.hasNext()) {
                    value = null;
                    break;
                }
                Map.Entry<FirExpression, FirValueParameter> next = it.next();
                FirExpression key = next.getKey();
                value = next.getValue();
                FirLambdaArgumentExpression firLambdaArgumentExpression = key instanceof FirLambdaArgumentExpression ? (FirLambdaArgumentExpression) key : null;
                FirExpression expression = firLambdaArgumentExpression != null ? firLambdaArgumentExpression.getExpression() : null;
                FirAnonymousFunctionExpression firAnonymousFunctionExpression = expression instanceof FirAnonymousFunctionExpression ? (FirAnonymousFunctionExpression) expression : null;
                if ((firAnonymousFunctionExpression != null ? firAnonymousFunctionExpression.getAnonymousFunction() : null) != atom) {
                    value = null;
                }
            } while (value == null);
            ClassMembersKt.setMatchingParameterFunctionType(atom, (value == null || (returnTypeRef = value.getReturnTypeRef()) == null) ? null : FirTypeUtilsKt.getConeType(returnTypeRef));
            if (z) {
                Name name2 = StandardNames.IMPLICIT_LAMBDA_PARAMETER_NAME;
                ConeKotlinType coneKotlinType = (ConeKotlinType) CollectionsKt.single((List) parameters);
                FirCallCompleter firCallCompleter = FirCallCompleter.this;
                FirValueParameterBuilder firValueParameterBuilder = new FirValueParameterBuilder();
                firValueParameterBuilder.setResolvePhase(FirResolvePhase.BODY_RESOLVE);
                KtSourceElement source = lambdaAtom.getReference().getSource();
                firValueParameterBuilder.setSource(source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.ItLambdaParameter.INSTANCE, 0, 0, 6, null) : null);
                firValueParameterBuilder.setContainingFunctionSymbol(atom.getSymbol());
                firValueParameterBuilder.setModuleData(FirModuleDataKt.getModuleData(firCallCompleter.session));
                firValueParameterBuilder.setOrigin(FirDeclarationOrigin.Source.INSTANCE);
                firValueParameterBuilder.setReturnTypeRef(TypeUtilsKt.toFirResolvedTypeRef$default(firCallCompleter.approximateLambdaInputType(coneKotlinType), null, null, 3, null));
                firValueParameterBuilder.setName(name2);
                firValueParameterBuilder.setSymbol(new FirValueParameterSymbol(name2));
                firValueParameterBuilder.setDefaultValue(null);
                firValueParameterBuilder.setCrossinline(false);
                firValueParameterBuilder.setNoinline(false);
                firValueParameterBuilder.setVararg(false);
                firValueParameterBuild = firValueParameterBuilder.mo6809build();
            } else {
                firValueParameterBuild = null;
            }
            FirResolvedTypeRef firResolvedTypeRefResolvedTypeFromPrototype$default2 = expectedReturnType != null ? CopyUtilsKt.resolvedTypeFromPrototype$default(atom.getReturnTypeRef(), expectedReturnType, null, 2, null) : null;
            if (receiverType != null && !lambdaAtom.getCoerceFirstParameterToExtensionReceiver()) {
                FirReceiverParameter receiverParameter = atom.getReceiverParameter();
                if (receiverParameter != null) {
                    ConeKotlinType coneKotlinTypeApproximateLambdaInputType = FirCallCompleter.this.approximateLambdaInputType(receiverType);
                    KtSourceElement source2 = receiverParameter.getSource();
                    receiverParameter.replaceTypeRef(CopyUtilsKt.resolvedTypeFromPrototype(receiverParameter.getTypeRef(), coneKotlinTypeApproximateLambdaInputType, source2 != null ? KtSourceElementKt.fakeElement$default(source2, KtFakeSourceElementKind.ImplicitTypeRef.INSTANCE, 0, 0, 6, null) : null));
                }
            } else {
                atom.replaceReceiverParameter(null);
            }
            if (!contextReceivers.isEmpty()) {
                List<? extends ConeKotlinType> list = contextReceivers;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                for (ConeKotlinType coneKotlinType2 : list) {
                    FirContextReceiverBuilder firContextReceiverBuilder = new FirContextReceiverBuilder();
                    FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
                    firResolvedTypeRefBuilder.setType(coneKotlinType2);
                    firContextReceiverBuilder.setTypeRef(firResolvedTypeRefBuilder.mo6809build());
                    arrayList.add(firContextReceiverBuilder.build());
                }
                atom.replaceContextReceivers(arrayList);
            }
            FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(FirCallCompleter.this.session);
            KtSourceElement source3 = FirCallCompleter.this.components.getFile().getSource();
            if (!lambdaAtom.getCoerceFirstParameterToExtensionReceiver()) {
                listPlus = parameters;
            } else {
                if (receiverType == null) {
                    throw new IllegalStateException("Coercion to extension receiver while no receiver present".toString());
                }
                listPlus = CollectionsKt.plus((Collection) CollectionsKt.listOf(receiverType), (Iterable) parameters);
            }
            List<FirValueParameter> valueParameters = atom.getValueParameters();
            FirCallCompleter firCallCompleter2 = FirCallCompleter.this;
            int i = 0;
            for (Object obj : valueParameters) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                FirValueParameter firValueParameter = (FirValueParameter) obj;
                ConeKotlinType coneKotlinTypeApproximateLambdaInputType2 = firCallCompleter2.approximateLambdaInputType(listPlus.get(i));
                if (firValueParameter.getReturnTypeRef() instanceof FirImplicitTypeRef) {
                    KtSourceElement source4 = firValueParameter.getSource();
                    firResolvedTypeRefResolvedTypeFromPrototype$default = TypeUtilsKt.toFirResolvedTypeRef$default(coneKotlinTypeApproximateLambdaInputType2, source4 != null ? KtSourceElementKt.fakeElement$default(source4, KtFakeSourceElementKind.ImplicitReturnTypeOfLambdaValueParameter.INSTANCE, 0, 0, 6, null) : null, null, 2, null);
                } else {
                    firResolvedTypeRefResolvedTypeFromPrototype$default = CopyUtilsKt.resolvedTypeFromPrototype$default(firValueParameter.getReturnTypeRef(), coneKotlinTypeApproximateLambdaInputType2, null, 2, null);
                }
                FirResolvedTypeRef firResolvedTypeRef = firResolvedTypeRefResolvedTypeFromPrototype$default;
                firValueParameter.replaceReturnTypeRef(firResolvedTypeRef);
                if (lookupTracker != null) {
                    FirLookupTrackerComponentKt.recordTypeResolveAsLookup(lookupTracker, firResolvedTypeRef, firValueParameter.getSource(), source3);
                }
                i = i2;
            }
            atom.replaceValueParameters(CollectionsKt.plus((Collection) atom.getValueParameters(), (Iterable) CollectionsKt.listOfNotNull(firValueParameterBuild)));
            if (firResolvedTypeRefResolvedTypeFromPrototype$default2 == null) {
                noExpectedType = FirCallCompleter.this.components.getNoExpectedType();
            } else {
                if (lookupTracker != null) {
                    FirLookupTrackerComponentKt.recordTypeResolveAsLookup(lookupTracker, firResolvedTypeRefResolvedTypeFromPrototype$default2, atom.getSource(), source3);
                }
                noExpectedType = firResolvedTypeRefResolvedTypeFromPrototype$default2;
            }
            atom.replaceReturnTypeRef(noExpectedType);
            FirBuilderInferenceSession firBuilderInferenceSession = stubsForPostponedVariables.isEmpty() ? null : new FirBuilderInferenceSession(atom, FirCallCompleter.this.transformer.getResolutionContext(), stubsForPostponedVariables);
            BodyResolveContext context = FirCallCompleter.this.transformer.getContext();
            FirAnonymousFunctionSymbol symbol = atom.getSymbol();
            FirCallCompleter firCallCompleter3 = FirCallCompleter.this;
            FirTowerDataContext anonymousFunctionContext = context.getSpecialTowerDataContexts().getAnonymousFunctionContext(symbol);
            if (anonymousFunctionContext != null) {
                FirTowerDataMode towerDataMode = context.getTowerDataMode();
                try {
                    FirRegularTowerDataContexts firRegularTowerDataContextsReplaceAndSetActiveRegularContext = context.getRegularTowerDataContexts().replaceAndSetActiveRegularContext(anonymousFunctionContext);
                    FirRegularTowerDataContexts regularTowerDataContexts = context.getRegularTowerDataContexts();
                    context.setRegularTowerDataContexts(firRegularTowerDataContextsReplaceAndSetActiveRegularContext);
                    try {
                        if (firBuilderInferenceSession != null) {
                            BodyResolveContext context2 = firCallCompleter3.transformer.getContext();
                            FirInferenceSession inferenceSession = context2.getInferenceSession();
                            context2.setInferenceSession(firBuilderInferenceSession);
                            try {
                                context2.setInferenceSession(inferenceSession);
                            } catch (Throwable th) {
                                context2.setInferenceSession(inferenceSession);
                                throw th;
                            }
                        }
                        context.setRegularTowerDataContexts(regularTowerDataContexts);
                        context.setTowerDataMode(towerDataMode);
                    } catch (Throwable th2) {
                        context.setRegularTowerDataContexts(regularTowerDataContexts);
                        throw th2;
                    }
                } catch (Throwable th3) {
                    context.setTowerDataMode(towerDataMode);
                    throw th3;
                }
            } else if (firBuilderInferenceSession != null) {
                BodyResolveContext context3 = firCallCompleter3.transformer.getContext();
                FirInferenceSession inferenceSession2 = context3.getInferenceSession();
                context3.setInferenceSession(firBuilderInferenceSession);
                try {
                    context3.setInferenceSession(inferenceSession2);
                } catch (Throwable th4) {
                    context3.setInferenceSession(inferenceSession2);
                    throw th4;
                }
            }
            FirCallCompleter.this.transformer.getContext().dropContextForAnonymousFunction(atom);
            Collection<FirAnonymousFunctionReturnExpressionInfo> collectionReturnExpressionsOfAnonymousFunction = FirCallCompleter.this.components.getDataFlowAnalyzer().returnExpressionsOfAnonymousFunction(atom);
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionReturnExpressionsOfAnonymousFunction, 10));
            Iterator<T> it2 = collectionReturnExpressionsOfAnonymousFunction.iterator();
            while (it2.hasNext()) {
                arrayList2.add(((FirAnonymousFunctionReturnExpressionInfo) it2.next()).getExpression());
            }
            return new ReturnArgumentsAnalysisResult(arrayList2, firBuilderInferenceSession);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ConeKotlinType approximateLambdaInputType(ConeKotlinType coneKotlinType) {
        ConeKotlinType coneKotlinTypeApproximateToSuperType = TypeComponentsKt.getTypeApproximator(this.session).approximateToSuperType(coneKotlinType, (TypeApproximatorConfiguration) TypeApproximatorConfiguration.FinalApproximationAfterResolutionAndInference.INSTANCE);
        return coneKotlinTypeApproximateToSuperType == null ? coneKotlinType : coneKotlinTypeApproximateToSuperType;
    }
}
