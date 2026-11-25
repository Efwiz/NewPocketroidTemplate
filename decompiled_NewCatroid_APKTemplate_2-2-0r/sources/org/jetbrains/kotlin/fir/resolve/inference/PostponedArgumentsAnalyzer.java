package org.jetbrains.kotlin.fir.resolve.inference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.fir.CopyUtilsKt;
import org.jetbrains.kotlin.fir.FirCallResolver;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponent;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponentKt;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirCallableReferenceAccess;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirErrorNamedReference;
import org.jetbrains.kotlin.fir.references.builder.FirErrorNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.resolve.ResolveUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.ArgumentsKt;
import org.jetbrains.kotlin.fir.resolve.calls.CallableReferenceResolutionKt;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateFactoryKt;
import org.jetbrains.kotlin.fir.resolve.calls.CheckerSinkImpl;
import org.jetbrains.kotlin.fir.resolve.calls.FirErrorReferenceWithCandidate;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedReferenceError;
import org.jetbrains.kotlin.fir.resolve.inference.model.ConeLambdaArgumentConstraintPosition;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeTypeVariable;
import org.jetbrains.kotlin.fir.types.ConeTypeVariableTypeConstructor;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirErrorTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.resolve.calls.components.PostponedArgumentsAnalyzerContext;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionMode;
import org.jetbrains.kotlin.resolve.calls.inference.model.BuilderInferencePosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.MarkerExtensionsKt;
import org.jetbrains.kotlin.types.model.StubTypeMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: PostponedArgumentsAnalyzer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ&\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014J&\u0010\u0015\u001a\u00020\u00162\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014JD\u0010\u0019\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u0013\u001a\u00020\u00142\u0014\b\u0002\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001d0\u001cJ\u0018\u0010\u001e\u001a\u00020\f2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0016\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001d0\u001c*\u00020\u000eR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/PostponedArgumentsAnalyzer;", "", "resolutionContext", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "lambdaAnalyzer", "Lorg/jetbrains/kotlin/fir/resolve/inference/LambdaAnalyzer;", "components", "Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents;", "callResolver", "Lorg/jetbrains/kotlin/fir/FirCallResolver;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;Lorg/jetbrains/kotlin/fir/resolve/inference/LambdaAnalyzer;Lorg/jetbrains/kotlin/fir/resolve/inference/InferenceComponents;Lorg/jetbrains/kotlin/fir/FirCallResolver;)V", "analyze", "", "c", "Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzerContext;", "argument", "Lorg/jetbrains/kotlin/fir/resolve/inference/PostponedResolvedAtom;", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "completionMode", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionMode;", "analyzeLambda", "Lorg/jetbrains/kotlin/fir/resolve/inference/ReturnArgumentsAnalysisResult;", "lambda", "Lorg/jetbrains/kotlin/fir/resolve/inference/ResolvedLambdaAtom;", "applyResultsOfAnalyzedLambdaToCandidateSystem", "results", "substitute", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "processCallableReference", "atom", "Lorg/jetbrains/kotlin/fir/resolve/inference/ResolvedCallableReferenceAtom;", "createSubstituteFunctorForLambdaAnalysis", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PostponedArgumentsAnalyzer {
    private final FirCallResolver callResolver;
    private final InferenceComponents components;
    private final LambdaAnalyzer lambdaAnalyzer;
    private final ResolutionContext resolutionContext;

    public PostponedArgumentsAnalyzer(ResolutionContext resolutionContext, LambdaAnalyzer lambdaAnalyzer, InferenceComponents components, FirCallResolver callResolver) {
        Intrinsics.checkNotNullParameter(resolutionContext, "resolutionContext");
        Intrinsics.checkNotNullParameter(lambdaAnalyzer, "lambdaAnalyzer");
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(callResolver, "callResolver");
        this.resolutionContext = resolutionContext;
        this.lambdaAnalyzer = lambdaAnalyzer;
        this.components = components;
        this.callResolver = callResolver;
    }

    public final void analyze(PostponedArgumentsAnalyzerContext c, PostponedResolvedAtom argument, Candidate candidate, ConstraintSystemCompletionMode completionMode) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(completionMode, "completionMode");
        if (argument instanceof ResolvedLambdaAtom) {
            analyzeLambda(c, (ResolvedLambdaAtom) argument, candidate, completionMode);
        } else if (argument instanceof LambdaWithTypeVariableAsExpectedTypeAtom) {
            analyzeLambda(c, PostponedArgumentsAnalyzerKt.transformToResolvedLambda$default((LambdaWithTypeVariableAsExpectedTypeAtom) argument, c.getBuilder(), this.resolutionContext, null, null, 12, null), candidate, completionMode);
        } else if (argument instanceof ResolvedCallableReferenceAtom) {
            processCallableReference((ResolvedCallableReferenceAtom) argument, candidate);
        }
    }

    private final void processCallableReference(ResolvedCallableReferenceAtom atom, Candidate candidate) {
        FirErrorTypeRef firErrorTypeRefMo6809build;
        if (atom.getMightNeedAdditionalResolution()) {
            this.callResolver.resolveCallableReference(ConstraintSystemCompleterKt.getCsBuilder(candidate), atom);
        }
        FirCallableReferenceAccess reference = atom.getReference();
        atom.setAnalyzed(true);
        this.resolutionContext.getBodyResolveContext().dropCallableReferenceContext(reference);
        FirErrorNamedReference resultingReference = atom.getResultingReference();
        if (resultingReference == null) {
            FirErrorNamedReferenceBuilder firErrorNamedReferenceBuilder = new FirErrorNamedReferenceBuilder();
            firErrorNamedReferenceBuilder.setSource(reference.getSource());
            firErrorNamedReferenceBuilder.setDiagnostic(new ConeUnresolvedReferenceError(reference.getCalleeReference().getName()));
            resultingReference = firErrorNamedReferenceBuilder.build();
        }
        reference.replaceCalleeReference(resultingReference);
        ConeKotlinType resultingTypeForCallableReference = atom.getResultingTypeForCallableReference();
        if (resultingTypeForCallableReference == null) {
            if (resultingReference instanceof FirErrorReferenceWithCandidate) {
                FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
                firErrorTypeRefBuilder.setDiagnostic(((FirErrorReferenceWithCandidate) resultingReference).getDiagnostic());
                firErrorTypeRefMo6809build = firErrorTypeRefBuilder.mo6809build();
            } else {
                FirErrorTypeRefBuilder firErrorTypeRefBuilder2 = new FirErrorTypeRefBuilder();
                firErrorTypeRefBuilder2.setDiagnostic(new ConeUnresolvedReferenceError(reference.getCalleeReference().getName()));
                firErrorTypeRefMo6809build = firErrorTypeRefBuilder2.mo6809build();
            }
        } else {
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
            firResolvedTypeRefBuilder.setType(resultingTypeForCallableReference);
            firErrorTypeRefMo6809build = firResolvedTypeRefBuilder.mo6809build();
        }
        FirResolvedTypeRef firResolvedTypeRef = firErrorTypeRefMo6809build;
        reference.replaceTypeRef(firResolvedTypeRef);
        FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(this.resolutionContext.getSession());
        if (lookupTracker != null) {
            FirLookupTrackerComponentKt.recordTypeResolveAsLookup(lookupTracker, firResolvedTypeRef, reference.getSource(), this.resolutionContext.getBodyResolveComponents().getFile().getSource());
        }
    }

    public final ReturnArgumentsAnalysisResult analyzeLambda(PostponedArgumentsAnalyzerContext c, ResolvedLambdaAtom lambda, Candidate candidate, ConstraintSystemCompletionMode completionMode) {
        ConeClassLikeType coneClassLikeTypeAnalyzeLambda$substitute;
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(lambda, "lambda");
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(completionMode, "completionMode");
        if (lambda.getAnalyzed()) {
            return new ReturnArgumentsAnalysisResult(lambda.getReturnStatements(), null);
        }
        ConeClassLikeType type = this.components.getSession().getBuiltinTypes().getUnitType().getType();
        Map<TypeVariableMarker, StubTypeMarker> mapBindingStubsForPostponedVariables = c.bindingStubsForPostponedVariables();
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(mapBindingStubsForPostponedVariables.size()));
        Iterator<T> it = mapBindingStubsForPostponedVariables.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap.put(MarkerExtensionsKt.freshTypeConstructor((TypeVariableMarker) entry.getKey(), c), entry.getValue());
        }
        TypeSubstitutorMarker typeSubstitutorMarkerBuildCurrentSubstitutor = c.buildCurrentSubstitutor(linkedHashMap);
        ConeKotlinType receiver = lambda.getReceiver();
        ConeKotlinType coneKotlinTypeAnalyzeLambda$substitute = receiver != null ? analyzeLambda$substitute(typeSubstitutorMarkerBuildCurrentSubstitutor, c, receiver) : null;
        List<ConeKotlinType> contextReceivers = lambda.getContextReceivers();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(contextReceivers, 10));
        Iterator<T> it2 = contextReceivers.iterator();
        while (it2.hasNext()) {
            arrayList.add(analyzeLambda$substitute(typeSubstitutorMarkerBuildCurrentSubstitutor, c, (ConeKotlinType) it2.next()));
        }
        ArrayList arrayList2 = arrayList;
        List<ConeKotlinType> parameters = lambda.getParameters();
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(parameters, 10));
        Iterator<T> it3 = parameters.iterator();
        while (it3.hasNext()) {
            arrayList3.add(analyzeLambda$substitute(typeSubstitutorMarkerBuildCurrentSubstitutor, c, (ConeKotlinType) it3.next()));
        }
        ArrayList arrayList4 = arrayList3;
        ConeKotlinType returnType = lambda.getReturnType();
        ConeKotlinType coneKotlinType = returnType;
        if (c.canBeProper(coneKotlinType)) {
            coneClassLikeTypeAnalyzeLambda$substitute = analyzeLambda$substitute(typeSubstitutorMarkerBuildCurrentSubstitutor, c, returnType);
        } else {
            coneClassLikeTypeAnalyzeLambda$substitute = (ConeTypeUtilsKt.isMarkedNullable(returnType) || !c.hasUpperOrEqualUnitConstraint(coneKotlinType)) ? null : type;
        }
        ReturnArgumentsAnalysisResult returnArgumentsAnalysisResultAnalyzeAndGetLambdaReturnArguments = this.lambdaAnalyzer.analyzeAndGetLambdaReturnArguments(lambda, coneKotlinTypeAnalyzeLambda$substitute, arrayList2, arrayList4, coneClassLikeTypeAnalyzeLambda$substitute, mapBindingStubsForPostponedVariables, candidate);
        applyResultsOfAnalyzedLambdaToCandidateSystem(c, lambda, candidate, returnArgumentsAnalysisResultAnalyzeAndGetLambdaReturnArguments, completionMode, new AnonymousClass1(typeSubstitutorMarkerBuildCurrentSubstitutor, c));
        return returnArgumentsAnalysisResultAnalyzeAndGetLambdaReturnArguments;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ConeKotlinType analyzeLambda$substitute(TypeSubstitutorMarker typeSubstitutorMarker, PostponedArgumentsAnalyzerContext postponedArgumentsAnalyzerContext, ConeKotlinType coneKotlinType) {
        KotlinTypeMarker kotlinTypeMarkerSafeSubstitute = MarkerExtensionsKt.safeSubstitute(typeSubstitutorMarker, postponedArgumentsAnalyzerContext, coneKotlinType);
        Intrinsics.checkNotNull(kotlinTypeMarkerSafeSubstitute, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
        return TypeUtilsKt.independentInstance((ConeKotlinType) kotlinTypeMarkerSafeSubstitute);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.inference.PostponedArgumentsAnalyzer$analyzeLambda$1, reason: invalid class name */
    /* JADX INFO: compiled from: PostponedArgumentsAnalyzer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<ConeKotlinType, ConeKotlinType> {
        final /* synthetic */ PostponedArgumentsAnalyzerContext $c;
        final /* synthetic */ TypeSubstitutorMarker $currentSubstitutor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(TypeSubstitutorMarker typeSubstitutorMarker, PostponedArgumentsAnalyzerContext postponedArgumentsAnalyzerContext) {
            super(1);
            this.$currentSubstitutor = typeSubstitutorMarker;
            this.$c = postponedArgumentsAnalyzerContext;
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "analyzeLambda$substitute(Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;Lorg/jetbrains/kotlin/resolve/calls/components/PostponedArgumentsAnalyzerContext;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "substitute";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(Intrinsics.Kotlin.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final ConeKotlinType invoke(ConeKotlinType p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return PostponedArgumentsAnalyzer.analyzeLambda$substitute(this.$currentSubstitutor, this.$c, p0);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void applyResultsOfAnalyzedLambdaToCandidateSystem$default(PostponedArgumentsAnalyzer postponedArgumentsAnalyzer, PostponedArgumentsAnalyzerContext postponedArgumentsAnalyzerContext, ResolvedLambdaAtom resolvedLambdaAtom, Candidate candidate, ReturnArgumentsAnalysisResult returnArgumentsAnalysisResult, ConstraintSystemCompletionMode constraintSystemCompletionMode, Function1 function1, int i, Object obj) {
        if ((i & 32) != 0) {
            function1 = postponedArgumentsAnalyzer.createSubstituteFunctorForLambdaAnalysis(postponedArgumentsAnalyzerContext);
        }
        postponedArgumentsAnalyzer.applyResultsOfAnalyzedLambdaToCandidateSystem(postponedArgumentsAnalyzerContext, resolvedLambdaAtom, candidate, returnArgumentsAnalysisResult, constraintSystemCompletionMode, function1);
    }

    /* JADX WARN: Code duplicated, block: B:32:0x00ce  */
    public final void applyResultsOfAnalyzedLambdaToCandidateSystem(PostponedArgumentsAnalyzerContext c, ResolvedLambdaAtom lambda, Candidate candidate, ReturnArgumentsAnalysisResult results, ConstraintSystemCompletionMode completionMode, Function1<? super ConeKotlinType, ? extends ConeKotlinType> substitute) {
        List<FirStatement> statements;
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(lambda, "lambda");
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        Intrinsics.checkNotNullParameter(results, "results");
        Intrinsics.checkNotNullParameter(completionMode, "completionMode");
        Intrinsics.checkNotNullParameter(substitute, "substitute");
        Collection<FirExpression> collectionComponent1 = results.component1();
        FirInferenceSession inferenceSession = results.getInferenceSession();
        CheckerSinkImpl checkerSinkImpl = new CheckerSinkImpl(candidate, null, false, 6, null);
        ConstraintSystemBuilder builder = c.getBuilder();
        FirBlock body = lambda.getReference().getBody();
        FirStatement firStatement = (body == null || (statements = body.getStatements()) == null) ? null : (FirStatement) CollectionsKt.lastOrNull((List) statements);
        FirExpression firExpression = firStatement instanceof FirExpression ? (FirExpression) firStatement : null;
        FirTypeRef returnTypeRef = lambda.getReference().getReturnTypeRef();
        FirResolvedTypeRef firResolvedTypeRefResolvedTypeFromPrototype$default = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
        if (firResolvedTypeRefResolvedTypeFromPrototype$default == null) {
            firResolvedTypeRefResolvedTypeFromPrototype$default = CopyUtilsKt.resolvedTypeFromPrototype$default(returnTypeRef, substitute.invoke(lambda.getReturnType()), null, 2, null);
        }
        FirResolvedTypeRef firResolvedTypeRef = firResolvedTypeRefResolvedTypeFromPrototype$default;
        boolean zIsUnitOrFlexibleUnit = CallableReferenceResolutionKt.isUnitOrFlexibleUnit(firResolvedTypeRef.getType());
        boolean z = false;
        for (FirExpression firExpression2 : collectionComponent1) {
            boolean zAreEqual = Intrinsics.areEqual(firExpression2, firExpression);
            if (zAreEqual && zIsUnitOrFlexibleUnit && (inferenceSession instanceof FirBuilderInferenceSession)) {
                firExpression = firExpression;
                builder = builder;
            } else {
                boolean zAddSubsystemFromExpression = CandidateFactoryKt.addSubsystemFromExpression(c, firExpression2);
                if (zAreEqual && !zAddSubsystemFromExpression && (zIsUnitOrFlexibleUnit || ResolveUtilsKt.shouldReturnUnit(lambda.getReference(), collectionComponent1))) {
                    firExpression = firExpression;
                    builder = builder;
                } else {
                    if (!builder.getHasContradiction()) {
                        ArgumentsKt.resolveArgumentExpression(candidate, builder, firExpression2, firResolvedTypeRef.getType(), checkerSinkImpl, this.resolutionContext, false, false);
                    }
                    z = true;
                }
            }
            builder = builder;
            firExpression = firExpression;
        }
        ConstraintSystemBuilder constraintSystemBuilder = builder;
        if (!z && !zIsUnitOrFlexibleUnit) {
            constraintSystemBuilder.addSubtypeConstraint(this.components.getSession().getBuiltinTypes().getUnitType().getType(), firResolvedTypeRef.getType(), new ConeLambdaArgumentConstraintPosition(lambda.getReference()));
        }
        lambda.setAnalyzed(true);
        lambda.setReturnStatements(collectionComponent1);
        c.resolveForkPointsConstraints();
        if (inferenceSession != null) {
            Map<ConeTypeVariableTypeConstructor, ConeKotlinType> mapInferPostponedVariables = inferenceSession.inferPostponedVariables(lambda, constraintSystemBuilder, completionMode);
            if (mapInferPostponedVariables == null) {
                constraintSystemBuilder.removePostponedVariables();
                return;
            }
            for (Map.Entry<ConeTypeVariableTypeConstructor, ConeKotlinType> entry : mapInferPostponedVariables.entrySet()) {
                ConeTypeVariableTypeConstructor key = entry.getKey();
                ConeKotlinType value = entry.getValue();
                VariableWithConstraints variableWithConstraints = constraintSystemBuilder.currentStorage().getNotFixedTypeVariables().get(key);
                if (variableWithConstraints != null) {
                    TypeVariableMarker typeVariable = variableWithConstraints.getTypeVariable();
                    Intrinsics.checkNotNull(typeVariable, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeTypeVariable");
                    ConeTypeVariable coneTypeVariable = (ConeTypeVariable) typeVariable;
                    constraintSystemBuilder.unmarkPostponedVariable(coneTypeVariable);
                    constraintSystemBuilder.addSubtypeConstraint(value, MarkerExtensionsKt.defaultType(coneTypeVariable, c), BuilderInferencePosition.INSTANCE);
                }
            }
            c.removePostponedTypeVariablesFromConstraints(mapInferPostponedVariables.keySet());
        }
    }

    public final Function1<ConeKotlinType, ConeKotlinType> createSubstituteFunctorForLambdaAnalysis(final PostponedArgumentsAnalyzerContext postponedArgumentsAnalyzerContext) {
        Intrinsics.checkNotNullParameter(postponedArgumentsAnalyzerContext, "<this>");
        Map<TypeVariableMarker, StubTypeMarker> mapBindingStubsForPostponedVariables = postponedArgumentsAnalyzerContext.bindingStubsForPostponedVariables();
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(mapBindingStubsForPostponedVariables.size()));
        Iterator<T> it = mapBindingStubsForPostponedVariables.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap.put(MarkerExtensionsKt.freshTypeConstructor((TypeVariableMarker) entry.getKey(), postponedArgumentsAnalyzerContext), entry.getValue());
        }
        final TypeSubstitutorMarker typeSubstitutorMarkerBuildCurrentSubstitutor = postponedArgumentsAnalyzerContext.buildCurrentSubstitutor(linkedHashMap);
        return new Function1<ConeKotlinType, ConeKotlinType>() { // from class: org.jetbrains.kotlin.fir.resolve.inference.PostponedArgumentsAnalyzer.createSubstituteFunctorForLambdaAnalysis.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ConeKotlinType invoke(ConeKotlinType it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                KotlinTypeMarker kotlinTypeMarkerSafeSubstitute = MarkerExtensionsKt.safeSubstitute(typeSubstitutorMarkerBuildCurrentSubstitutor, postponedArgumentsAnalyzerContext, it2);
                Intrinsics.checkNotNull(kotlinTypeMarkerSafeSubstitute, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
                return (ConeKotlinType) kotlinTypeMarkerSafeSubstitute;
            }
        };
    }
}
