package org.jetbrains.kotlin.fir.resolve.inference;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousFunction;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitExtensionReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext;
import org.jetbrains.kotlin.fir.resolve.substitution.ChainedSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.SubstitutorsKt;
import org.jetbrains.kotlin.fir.resolve.transformers.FirCallCompletionResultsWriterTransformer;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStubType;
import org.jetbrains.kotlin.fir.types.ConeStubTypeForChainInference;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeTypeVariable;
import org.jetbrains.kotlin.fir.types.ConeTypeVariableTypeConstructor;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionMode;
import org.jetbrains.kotlin.resolve.calls.inference.model.BuilderInferencePosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.resolve.calls.inference.model.InitialConstraint;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: FirBuilderInferenceSession.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0098\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\u0010\nJ+\u0010\u0018\u001a\u00020\u0019\"\f\b\u0000\u0010\u001a*\u00020\u001b*\u00020\u000e2\u0006\u0010\u001c\u001a\u0002H\u001a2\u0006\u0010\u001d\u001a\u00020\u000fH\u0016¢\u0006\u0002\u0010\u001eJ\u000e\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020\u0015J\u001c\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#0\r2\u0006\u0010$\u001a\u00020\u0011H\u0002J\b\u0010%\u001a\u00020&H\u0002J\u001c\u0010'\u001a\u000e\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020\t0\u00072\u0006\u0010)\u001a\u00020\"H\u0016J\u0010\u0010*\u001a\u00020&2\u0006\u0010+\u001a\u00020\"H\u0002J\b\u0010,\u001a\u00020#H\u0016J.\u0010-\u001a\u0010\u0012\u0004\u0012\u00020.\u0012\u0004\u0012\u00020/\u0018\u00010\u00072\u0006\u0010\u0002\u001a\u0002002\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u000204H\u0016J(\u00105\u001a\u00020#2\u0006\u0010+\u001a\u00020\"2\u0006\u00106\u001a\u00020\u00112\u0006\u00107\u001a\u00020&2\u0006\u00108\u001a\u00020#H\u0002J\u0010\u00109\u001a\u00020#2\u0006\u0010:\u001a\u00020;H\u0016J#\u0010<\u001a\u00020#\"\f\b\u0000\u0010\u001a*\u00020\u001b*\u00020\u000e2\u0006\u0010\u001c\u001a\u0002H\u001aH\u0016¢\u0006\u0002\u0010=J#\u0010>\u001a\u00020#\"\f\b\u0000\u0010\u001a*\u00020\u001b*\u00020\u000e2\u0006\u0010\u001c\u001a\u0002H\u001aH\u0002¢\u0006\u0002\u0010=J\u0010\u0010?\u001a\u00020\u00192\u0006\u0010@\u001a\u00020&H\u0002J\f\u0010A\u001a\u00020#*\u00020/H\u0002J\f\u0010B\u001a\u00020#*\u00020\u000eH\u0002J\f\u0010B\u001a\u00020#*\u00020\u000fH\u0002J\f\u0010C\u001a\u00020#*\u00020\u000fH\u0002R \u0010\u000b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006D²\u0006\n\u0010E\u001a\u00020&X\u008a\u0084\u0002"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/FirBuilderInferenceSession;", "Lorg/jetbrains/kotlin/fir/resolve/inference/FirInferenceSessionForChainedResolve;", "lambda", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;", "resolutionContext", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "stubsForPostponedVariables", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeVariable;", "Lorg/jetbrains/kotlin/fir/types/ConeStubType;", "(Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousFunction;Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;Ljava/util/Map;)V", "commonCalls", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "currentConstraintStorage", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "getCurrentConstraintStorage", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "lambdaImplicitReceivers", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitExtensionReceiverValue;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "addCompletedCall", "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;", "call", "candidate", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;)V", "addLambdaImplicitReceiver", "receiver", "buildCommonSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl;", "", "initialStorage", "createNonFixedTypeToVariableSubstitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "createSyntheticStubTypes", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "system", "getResultingSubstitutor", "commonSystem", "hasSyntheticTypeVariables", "inferPostponedVariables", "Lorg/jetbrains/kotlin/fir/types/ConeTypeVariableTypeConstructor;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/resolve/inference/ResolvedLambdaAtom;", "constraintSystemBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "completionMode", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionMode;", "integrateConstraints", "storage", "nonFixedToVariablesSubstitutor", "shouldIntegrateAllConstraints", "isSyntheticTypeVariable", "typeVariable", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "shouldRunCompletion", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;)Z", "skipCall", "updateCalls", "substitutor", "containsStubType", "hasPostponed", "isSuitableForBuilderInference", "resolve", "resultingSubstitutor"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirBuilderInferenceSession extends FirInferenceSessionForChainedResolve {
    private final List<Pair<FirStatement, Candidate>> commonCalls;
    private final FirAnonymousFunction lambda;
    private List<ImplicitExtensionReceiverValue> lambdaImplicitReceivers;
    private final FirSession session;
    private final Map<ConeTypeVariable, ConeStubType> stubsForPostponedVariables;

    private final <T extends FirResolvable & FirStatement> boolean skipCall(T call) {
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession
    public boolean hasSyntheticTypeVariables() {
        return false;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession
    public boolean isSyntheticTypeVariable(TypeVariableMarker typeVariable) {
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public FirBuilderInferenceSession(FirAnonymousFunction lambda, ResolutionContext resolutionContext, Map<ConeTypeVariable, ? extends ConeStubType> stubsForPostponedVariables) {
        super(resolutionContext);
        Intrinsics.checkNotNullParameter(lambda, "lambda");
        Intrinsics.checkNotNullParameter(resolutionContext, "resolutionContext");
        Intrinsics.checkNotNullParameter(stubsForPostponedVariables, "stubsForPostponedVariables");
        this.lambda = lambda;
        this.stubsForPostponedVariables = stubsForPostponedVariables;
        this.session = resolutionContext.getSession();
        this.commonCalls = new ArrayList();
        this.lambdaImplicitReceivers = new ArrayList();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession
    public ConstraintStorage getCurrentConstraintStorage() {
        return ConstraintStorage.Empty.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession
    public <T extends FirResolvable & FirStatement> boolean shouldRunCompletion(T call) {
        Intrinsics.checkNotNullParameter(call, "call");
        Candidate candidate = getCandidate(call);
        NewConstraintSystemImpl system = candidate.getSystem();
        if (system.getHasContradiction() || !isSuitableForBuilderInference(candidate)) {
            return true;
        }
        ConstraintStorage constraintStorageCurrentStorage = system.getBuilder().currentStorage();
        if (hasPostponed(call)) {
            return true;
        }
        Set<TypeConstructorMarker> setKeySet = constraintStorageCurrentStorage.getNotFixedTypeVariables().keySet();
        if ((setKeySet instanceof Collection) && setKeySet.isEmpty()) {
            return true;
        }
        for (TypeConstructorMarker typeConstructorMarker : setKeySet) {
            TypeVariableMarker typeVariableMarker = constraintStorageCurrentStorage.getAllTypeVariables().get(typeConstructorMarker);
            if (typeVariableMarker == null || !constraintStorageCurrentStorage.getPostponedTypeVariables().contains(typeVariableMarker)) {
                if (!getComponents().getCallCompleter().getCompleter().getVariableFixationFinder().isTypeVariableHasProperConstraint(system, typeConstructorMarker)) {
                    return false;
                }
            }
        }
        return true;
    }

    private final boolean isSuitableForBuilderInference(Candidate candidate) {
        FirTypeRef typeRef;
        ConeKotlinType coneType;
        FirTypeRef typeRef2;
        ConeKotlinType coneType2;
        FirExpression chosenExtensionReceiver = candidate.getChosenExtensionReceiver();
        FirExpression dispatchReceiver = candidate.getDispatchReceiver();
        if (chosenExtensionReceiver == null && dispatchReceiver == null) {
            return false;
        }
        if (dispatchReceiver != null && (typeRef2 = dispatchReceiver.getTypeRef()) != null && (coneType2 = FirTypeUtilsKt.getConeType(typeRef2)) != null && containsStubType(coneType2)) {
            return true;
        }
        if (chosenExtensionReceiver == null || (typeRef = chosenExtensionReceiver.getTypeRef()) == null || (coneType = FirTypeUtilsKt.getConeType(typeRef)) == null || !containsStubType(coneType)) {
            return false;
        }
        return FirBuilderInferenceSessionKt.hasBuilderInferenceAnnotation(candidate.getSymbol().getFir(), this.session);
    }

    private final boolean containsStubType(ConeKotlinType coneKotlinType) {
        return ConeTypeUtilsKt.contains(coneKotlinType, new Function1<ConeKotlinType, Boolean>() { // from class: org.jetbrains.kotlin.fir.resolve.inference.FirBuilderInferenceSession.containsStubType.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ConeKotlinType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it instanceof ConeStubTypeForChainInference);
            }
        });
    }

    private final boolean hasPostponed(FirStatement firStatement) {
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        ConstraintSystemCompleterKt.processAllContainingCallCandidates(firStatement, false, new Function1<Candidate, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.inference.FirBuilderInferenceSession.hasPostponed.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Candidate candidate) {
                invoke2(candidate);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Candidate it) {
                Intrinsics.checkNotNullParameter(it, "it");
                Ref.BooleanRef booleanRef2 = booleanRef;
                booleanRef2.element = booleanRef2.element || this.hasPostponed(it);
            }
        });
        return booleanRef.element;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean hasPostponed(Candidate candidate) {
        List<PostponedResolvedAtom> postponedAtoms = candidate.getPostponedAtoms();
        if ((postponedAtoms instanceof Collection) && postponedAtoms.isEmpty()) {
            return false;
        }
        Iterator<T> it = postponedAtoms.iterator();
        while (it.hasNext()) {
            if (!((PostponedResolvedAtom) it.next()).getAnalyzed()) {
                return true;
            }
        }
        return false;
    }

    public final void addLambdaImplicitReceiver(ImplicitExtensionReceiverValue receiver) {
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        this.lambdaImplicitReceivers.add(receiver);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSessionForChainedResolve, org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession
    public <T extends FirResolvable & FirStatement> void addCompletedCall(T call, Candidate candidate) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        if (skipCall(call)) {
            return;
        }
        this.commonCalls.add(TuplesKt.to(call, candidate));
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession
    public Map<ConeTypeVariableTypeConstructor, ConeKotlinType> inferPostponedVariables(ResolvedLambdaAtom lambda, ConstraintSystemBuilder constraintSystemBuilder, ConstraintSystemCompletionMode completionMode) {
        Intrinsics.checkNotNullParameter(lambda, "lambda");
        Intrinsics.checkNotNullParameter(constraintSystemBuilder, "constraintSystemBuilder");
        Intrinsics.checkNotNullParameter(completionMode, "completionMode");
        Pair<NewConstraintSystemImpl, Boolean> pairBuildCommonSystem = buildCommonSystem(constraintSystemBuilder.currentStorage());
        final NewConstraintSystemImpl newConstraintSystemImplComponent1 = pairBuildCommonSystem.component1();
        boolean zBooleanValue = pairBuildCommonSystem.component2().booleanValue();
        Lazy lazy = LazyKt.lazy(new Function0<ConeSubstitutor>() { // from class: org.jetbrains.kotlin.fir.resolve.inference.FirBuilderInferenceSession$inferPostponedVariables$resultingSubstitutor$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ConeSubstitutor invoke() {
                return this.this$0.getResultingSubstitutor(newConstraintSystemImplComponent1);
            }
        });
        if (zBooleanValue) {
            updateCalls(inferPostponedVariables$lambda$2(lazy));
            return null;
        }
        NewConstraintSystemImpl newConstraintSystemImplAsConstraintSystemCompleterContext = newConstraintSystemImplComponent1.asConstraintSystemCompleterContext();
        ConstraintSystemCompleter completer = getComponents().getCallCompleter().getCompleter();
        NewConstraintSystemImpl newConstraintSystemImpl = newConstraintSystemImplAsConstraintSystemCompleterContext;
        List<Pair<FirResolvable, Candidate>> partiallyResolvedCalls = getPartiallyResolvedCalls();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(partiallyResolvedCalls, 10));
        Iterator<T> it = partiallyResolvedCalls.iterator();
        while (it.hasNext()) {
            Object first = ((Pair) it.next()).getFirst();
            Intrinsics.checkNotNull(first, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirStatement");
            arrayList.add((FirStatement) first);
        }
        completer.complete(newConstraintSystemImpl, completionMode, arrayList, getComponents().getSession().getBuiltinTypes().getUnitType().getType(), getResolutionContext(), true, new Function1<PostponedResolvedAtom, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.inference.FirBuilderInferenceSession.inferPostponedVariables.2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(PostponedResolvedAtom postponedResolvedAtom) {
                invoke2(postponedResolvedAtom);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(PostponedResolvedAtom it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                throw new IllegalStateException("Shouldn't be called in complete constraint system mode".toString());
            }
        });
        if (completionMode == ConstraintSystemCompletionMode.FULL) {
            constraintSystemBuilder.substituteFixedVariables(inferPostponedVariables$lambda$2(lazy));
        }
        updateCalls(inferPostponedVariables$lambda$2(lazy));
        Map fixedTypeVariables = newConstraintSystemImplComponent1.getFixedTypeVariables();
        Intrinsics.checkNotNull(fixedTypeVariables, "null cannot be cast to non-null type kotlin.collections.Map<org.jetbrains.kotlin.fir.types.ConeTypeVariableTypeConstructor, org.jetbrains.kotlin.fir.types.ConeKotlinType>");
        return fixedTypeVariables;
    }

    private static final ConeSubstitutor inferPostponedVariables$lambda$2(Lazy<? extends ConeSubstitutor> lazy) {
        return lazy.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession
    public Map<TypeConstructorMarker, ConeStubType> createSyntheticStubTypes(NewConstraintSystemImpl system) {
        Intrinsics.checkNotNullParameter(system, "system");
        return MapsKt.emptyMap();
    }

    private final Pair<NewConstraintSystemImpl, Boolean> buildCommonSystem(ConstraintStorage initialStorage) {
        NewConstraintSystemImpl newConstraintSystemImplCreateConstraintSystem = InferenceComponentsKt.getInferenceComponents(getComponents().getSession()).createConstraintSystem();
        ConeSubstitutor coneSubstitutorCreateNonFixedTypeToVariableSubstitutor = createNonFixedTypeToVariableSubstitutor();
        boolean z = !integrateConstraints(newConstraintSystemImplCreateConstraintSystem, initialStorage, coneSubstitutorCreateNonFixedTypeToVariableSubstitutor, false);
        Iterator<Pair<FirStatement, Candidate>> it = this.commonCalls.iterator();
        while (it.hasNext()) {
            if (integrateConstraints(newConstraintSystemImplCreateConstraintSystem, it.next().component2().getSystem().asReadOnlyStorage(), coneSubstitutorCreateNonFixedTypeToVariableSubstitutor, false)) {
                z = false;
            }
        }
        Iterator<Pair<FirResolvable, Candidate>> it2 = getPartiallyResolvedCalls().iterator();
        while (it2.hasNext()) {
            if (integrateConstraints(newConstraintSystemImplCreateConstraintSystem, it2.next().component2().getSystem().asReadOnlyStorage(), coneSubstitutorCreateNonFixedTypeToVariableSubstitutor, true)) {
                z = false;
            }
        }
        return TuplesKt.to(newConstraintSystemImplCreateConstraintSystem, Boolean.valueOf(z));
    }

    private final ConeSubstitutor createNonFixedTypeToVariableSubstitutor() {
        ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(getComponents().getSession());
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<ConeTypeVariable, ConeStubType> entry : this.stubsForPostponedVariables.entrySet()) {
            linkedHashMap.put(entry.getValue().getConstructor(), entry.getKey().getDefaultType());
        }
        return typeContext.typeSubstitutorByTypeConstructor((Map<TypeConstructorMarker, ? extends KotlinTypeMarker>) linkedHashMap);
    }

    private final boolean integrateConstraints(NewConstraintSystemImpl commonSystem, ConstraintStorage storage, ConeSubstitutor nonFixedToVariablesSubstitutor, boolean shouldIntegrateAllConstraints) {
        Iterator<T> it = storage.getNotFixedTypeVariables().values().iterator();
        while (it.hasNext()) {
            org.jetbrains.kotlin.resolve.calls.inference.InferenceUtilsKt.registerTypeVariableIfNotPresent(commonSystem, ((VariableWithConstraints) it.next()).getTypeVariable());
        }
        boolean z = false;
        TypeSubstitutorMarker typeSubstitutorMarkerBuildAbstractResultingSubstitutor = org.jetbrains.kotlin.resolve.calls.inference.InferenceUtilsKt.buildAbstractResultingSubstitutor(storage, commonSystem, false);
        Intrinsics.checkNotNull(typeSubstitutorMarkerBuildAbstractResultingSubstitutor, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor");
        ConeSubstitutor coneSubstitutor = (ConeSubstitutor) typeSubstitutorMarkerBuildAbstractResultingSubstitutor;
        for (InitialConstraint initialConstraint : storage.getInitialConstraints()) {
            if (!(initialConstraint.getPosition() instanceof BuilderInferencePosition) && integrateConstraintToSystem(commonSystem, initialConstraint, coneSubstitutor, nonFixedToVariablesSubstitutor, storage.getFixedTypeVariables())) {
                z = true;
            }
        }
        if (shouldIntegrateAllConstraints) {
            for (Map.Entry<TypeConstructorMarker, KotlinTypeMarker> entry : storage.getFixedTypeVariables().entrySet()) {
                TypeConstructorMarker key = entry.getKey();
                KotlinTypeMarker value = entry.getValue();
                TypeVariableMarker typeVariableMarker = (TypeVariableMarker) MapsKt.getValue(storage.getAllTypeVariables(), key);
                org.jetbrains.kotlin.resolve.calls.inference.InferenceUtilsKt.registerTypeVariableIfNotPresent(commonSystem, typeVariableMarker);
                Intrinsics.checkNotNull(typeVariableMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeTypeVariable");
                commonSystem.addEqualityConstraint(((ConeTypeVariable) typeVariableMarker).getDefaultType(), value, BuilderInferencePosition.INSTANCE);
                z = true;
            }
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ConeSubstitutor getResultingSubstitutor(NewConstraintSystemImpl commonSystem) {
        ConeSubstitutor coneSubstitutorCreateNonFixedTypeToVariableSubstitutor = createNonFixedTypeToVariableSubstitutor();
        TypeSubstitutorMarker typeSubstitutorMarkerBuildCurrentSubstitutor = commonSystem.buildCurrentSubstitutor();
        Intrinsics.checkNotNull(typeSubstitutorMarkerBuildCurrentSubstitutor, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor");
        ChainedSubstitutor chainedSubstitutor = new ChainedSubstitutor(coneSubstitutorCreateNonFixedTypeToVariableSubstitutor, (ConeSubstitutor) typeSubstitutorMarkerBuildCurrentSubstitutor);
        ConeInferenceContext typeContext = getResolutionContext().getTypeContext();
        Collection<ConeStubType> collectionValues = this.stubsForPostponedVariables.values();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionValues, 10));
        Iterator<T> it = collectionValues.iterator();
        while (it.hasNext()) {
            arrayList.add(((ConeStubType) it.next()).getConstructor());
        }
        return SubstitutorsKt.replaceStubsAndTypeVariablesToErrors(chainedSubstitutor, typeContext, arrayList);
    }

    private final void updateCalls(ConeSubstitutor substitutor) {
        FirTransformerUtilKt.transformSingle(this.lambda, new FirStubTypeTransformer(substitutor), null);
        for (ImplicitExtensionReceiverValue implicitExtensionReceiverValue : this.lambdaImplicitReceivers) {
            implicitExtensionReceiverValue.updateTypeInBuilderInference(substitutor.substituteOrSelf(implicitExtensionReceiverValue.getType()));
        }
        FirCallCompletionResultsWriterTransformer firCallCompletionResultsWriterTransformerCreateCompletionResultsWriter$default = FirCallCompleter.createCompletionResultsWriter$default(getComponents().getCallCompleter(), substitutor, null, 2, null);
        Iterator<Pair<FirResolvable, Candidate>> it = getPartiallyResolvedCalls().iterator();
        while (it.hasNext()) {
            FirTransformerUtilKt.transformSingle(it.next().component1(), firCallCompletionResultsWriterTransformerCreateCompletionResultsWriter$default, null);
        }
    }
}
