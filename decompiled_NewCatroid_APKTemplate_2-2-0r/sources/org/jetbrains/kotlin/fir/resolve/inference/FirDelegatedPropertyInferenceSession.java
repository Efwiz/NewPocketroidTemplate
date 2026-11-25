package org.jetbrains.kotlin.fir.resolve.inference;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirResolvable;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.resolve.calls.Candidate;
import org.jetbrains.kotlin.fir.resolve.calls.CandidateFactoryKt;
import org.jetbrains.kotlin.fir.resolve.calls.FirNamedReferenceWithCandidate;
import org.jetbrains.kotlin.fir.resolve.calls.InferenceError;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionContext;
import org.jetbrains.kotlin.fir.resolve.inference.model.ConeFixVariableConstraintPosition;
import org.jetbrains.kotlin.fir.resolve.substitution.ChainedSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.NotFixedTypeToVariableSubstitutorForDelegateInference;
import org.jetbrains.kotlin.fir.resolve.substitution.SubstitutorsKt;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.BodyResolveContext;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeNullability;
import org.jetbrains.kotlin.fir.types.ConeStubType;
import org.jetbrains.kotlin.fir.types.ConeStubTypeConstructor;
import org.jetbrains.kotlin.fir.types.ConeStubTypeForChainInference;
import org.jetbrains.kotlin.fir.types.ConeStubTypeForSyntheticFixation;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeTypeVariable;
import org.jetbrains.kotlin.fir.types.ConeTypeVariableTypeConstructor;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionContext;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintSystemCompletionMode;
import org.jetbrains.kotlin.resolve.calls.inference.model.BuilderInferencePosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintSystemError;
import org.jetbrains.kotlin.resolve.calls.inference.model.InitialConstraint;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.VariableWithConstraints;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.MarkerExtensionsKt;
import org.jetbrains.kotlin.types.model.StubTypeMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;
import org.slf4j.Marker;

/* JADX INFO: compiled from: FirDelegatedPropertyInferenceSession.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¸\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ+\u0010\u001b\u001a\u00020\u001c\"\f\b\u0000\u0010\u001d*\u00020\u001e*\u00020\u001f2\u0006\u0010 \u001a\u0002H\u001d2\u0006\u0010!\u001a\u00020\"H\u0016¢\u0006\u0002\u0010#J#\u0010$\u001a\u00020\u001c\"\f\b\u0000\u0010\u001d*\u00020\u001e*\u00020\u001f2\u0006\u0010 \u001a\u0002H\u001dH\u0016¢\u0006\u0002\u0010%J\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u001e0'J\u0006\u0010(\u001a\u00020)J\b\u0010*\u001a\u00020+H\u0002J\u001c\u0010,\u001a\u000e\u0012\u0004\u0012\u00020.\u0012\u0004\u0012\u00020\u00160-2\u0006\u0010/\u001a\u00020\u000eH\u0016J\b\u00100\u001a\u00020)H\u0002J\u0018\u00101\u001a\u00020\u001c2\u0006\u00102\u001a\u00020\u00182\u0006\u00103\u001a\u000204H\u0016J\b\u00105\u001a\u000206H\u0016J.\u00107\u001a\u0010\u0012\u0004\u0012\u000208\u0012\u0004\u0012\u000209\u0018\u00010-2\u0006\u0010:\u001a\u00020;2\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020?H\u0016J(\u0010@\u001a\u00020\u001c2\u0006\u0010A\u001a\u00020\u000e2\u0006\u0010B\u001a\u00020\n2\u0006\u0010C\u001a\u00020)2\u0006\u0010D\u001a\u000206H\u0002J\u0010\u0010E\u001a\u00020\u001c2\u0006\u0010B\u001a\u00020\nH\u0002J\u0010\u0010F\u001a\u0002062\u0006\u00102\u001a\u00020\u0018H\u0016J\u001c\u0010G\u001a\u00020\u001c2\u0012\u0010H\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020I0-H\u0016J\u0010\u0010J\u001a\u00020\u001c2\u0006\u0010B\u001a\u00020\nH\u0002J#\u0010K\u001a\u000206\"\f\b\u0000\u0010\u001d*\u00020\u001e*\u00020\u001f2\u0006\u0010 \u001a\u0002H\u001dH\u0016¢\u0006\u0002\u0010LJ\f\u0010M\u001a\u000206*\u000209H\u0002J\f\u0010N\u001a\u000206*\u00020\u001fH\u0002J\f\u0010N\u001a\u000206*\u00020\"H\u0002R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00150\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006O"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/FirDelegatedPropertyInferenceSession;", "Lorg/jetbrains/kotlin/fir/resolve/inference/FirInferenceSessionForChainedResolve;", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "resolutionContext", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "postponedArgumentsAnalyzer", "Lorg/jetbrains/kotlin/fir/resolve/inference/PostponedArgumentsAnalyzer;", "(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;Lorg/jetbrains/kotlin/fir/resolve/inference/PostponedArgumentsAnalyzer;)V", "currentConstraintStorage", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "getCurrentConstraintStorage", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "currentConstraintSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl;", "getProperty", "()Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "resultingConstraintSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/NewConstraintSystem;", "stubTypesByTypeVariable", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeVariable;", "Lorg/jetbrains/kotlin/fir/types/ConeStubType;", "syntheticTypeVariableByTypeVariable", "Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;", "unitType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "addCompletedCall", "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "call", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;)V", "addPartiallyResolvedCall", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;)V", "completeCandidates", "", "createFinalSubstitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "createNonFixedTypeToVariableSubstitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/NotFixedTypeToVariableSubstitutorForDelegateInference;", "createSyntheticStubTypes", "", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "system", "createToSyntheticTypeVariableSubstitutor", "fixSyntheticTypeVariableWithNotEnoughInformation", "typeVariable", "completionContext", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionContext;", "hasSyntheticTypeVariables", "", "inferPostponedVariables", "Lorg/jetbrains/kotlin/fir/types/ConeTypeVariableTypeConstructor;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "lambda", "Lorg/jetbrains/kotlin/fir/resolve/inference/ResolvedLambdaAtom;", "constraintSystemBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "completionMode", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintSystemCompletionMode;", "integrateConstraints", "commonSystem", "storage", "nonFixedToVariablesSubstitutor", "shouldIntegrateAllConstraints", "integrateResolvedCall", "isSyntheticTypeVariable", "registerStubTypes", "map", "Lorg/jetbrains/kotlin/types/model/StubTypeMarker;", "registerSyntheticVariables", "shouldRunCompletion", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvable;)Z", "containsStubType", "hasPostponed", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDelegatedPropertyInferenceSession extends FirInferenceSessionForChainedResolve {
    private final NewConstraintSystemImpl currentConstraintSystem;
    private final PostponedArgumentsAnalyzer postponedArgumentsAnalyzer;
    private final FirProperty property;
    private NewConstraintSystem resultingConstraintSystem;
    private final Map<ConeTypeVariable, ConeStubType> stubTypesByTypeVariable;
    private final Map<TypeVariableMarker, ConeTypeVariable> syntheticTypeVariableByTypeVariable;
    private final ConeClassLikeType unitType;

    @Override // org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession
    public Map<ConeTypeVariableTypeConstructor, ConeKotlinType> inferPostponedVariables(ResolvedLambdaAtom lambda, ConstraintSystemBuilder constraintSystemBuilder, ConstraintSystemCompletionMode completionMode) {
        Intrinsics.checkNotNullParameter(lambda, "lambda");
        Intrinsics.checkNotNullParameter(constraintSystemBuilder, "constraintSystemBuilder");
        Intrinsics.checkNotNullParameter(completionMode, "completionMode");
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSessionForChainedResolve, org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession
    public void registerStubTypes(Map<TypeVariableMarker, ? extends StubTypeMarker> map) {
        Intrinsics.checkNotNullParameter(map, "map");
    }

    public final FirProperty getProperty() {
        return this.property;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirDelegatedPropertyInferenceSession(FirProperty property, ResolutionContext resolutionContext, PostponedArgumentsAnalyzer postponedArgumentsAnalyzer) {
        super(resolutionContext);
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(resolutionContext, "resolutionContext");
        Intrinsics.checkNotNullParameter(postponedArgumentsAnalyzer, "postponedArgumentsAnalyzer");
        this.property = property;
        this.postponedArgumentsAnalyzer = postponedArgumentsAnalyzer;
        this.currentConstraintSystem = InferenceComponentsKt.getInferenceComponents(getComponents().getSession()).createConstraintSystem();
        this.unitType = getComponents().getSession().getBuiltinTypes().getUnitType().getType();
        this.stubTypesByTypeVariable = new LinkedHashMap();
        this.syntheticTypeVariableByTypeVariable = new LinkedHashMap();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession
    public ConstraintStorage getCurrentConstraintStorage() {
        return this.currentConstraintSystem.currentStorage();
    }

    private final boolean containsStubType(ConeKotlinType coneKotlinType) {
        return ConeTypeUtilsKt.contains(coneKotlinType, new Function1<ConeKotlinType, Boolean>() { // from class: org.jetbrains.kotlin.fir.resolve.inference.FirDelegatedPropertyInferenceSession.containsStubType.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ConeKotlinType it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it instanceof ConeStubTypeForChainInference);
            }
        });
    }

    private final void integrateResolvedCall(ConstraintStorage storage) {
        registerSyntheticVariables(storage);
        integrateConstraints(this.currentConstraintSystem, storage, createToSyntheticTypeVariableSubstitutor(), false);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSessionForChainedResolve, org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession
    public <T extends FirResolvable & FirStatement> void addCompletedCall(T call, Candidate candidate) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(candidate, "candidate");
        getPartiallyResolvedCalls().add(TuplesKt.to(call, candidate));
        if (candidate.isSuccessful()) {
            integrateResolvedCall(candidate.getSystem().asReadOnlyStorage());
        }
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSessionForChainedResolve, org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession
    public <T extends FirResolvable & FirStatement> void addPartiallyResolvedCall(T call) {
        Intrinsics.checkNotNullParameter(call, "call");
        super.addPartiallyResolvedCall(call);
        if (getCandidate(call).isSuccessful()) {
            integrateResolvedCall(getCandidate(call).getSystem().currentStorage());
        }
    }

    /* JADX WARN: Code duplicated, block: B:32:0x0064  */
    /* JADX WARN: Code duplicated, block: B:59:0x00e9  */
    @Override // org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession
    public <T extends FirResolvable & FirStatement> boolean shouldRunCompletion(T call) {
        boolean z;
        boolean z2;
        FirTypeRef typeRef;
        ConeKotlinType coneType;
        FirTypeRef typeRef2;
        ConeKotlinType coneType2;
        Intrinsics.checkNotNullParameter(call, "call");
        FirReference calleeReference = call.getCalleeReference();
        FirNamedReferenceWithCandidate firNamedReferenceWithCandidate = calleeReference instanceof FirNamedReferenceWithCandidate ? (FirNamedReferenceWithCandidate) calleeReference : null;
        if (firNamedReferenceWithCandidate == null || firNamedReferenceWithCandidate.getCandidate().getSystem().getHasContradiction()) {
            return true;
        }
        FirExpression chosenExtensionReceiver = firNamedReferenceWithCandidate.getCandidate().getChosenExtensionReceiver();
        if ((chosenExtensionReceiver == null || (typeRef2 = chosenExtensionReceiver.getTypeRef()) == null || (coneType2 = FirTypeUtilsKt.getConeType(typeRef2)) == null) ? false : containsStubType(coneType2)) {
            z = true;
        } else {
            FirExpression dispatchReceiver = firNamedReferenceWithCandidate.getCandidate().getDispatchReceiver();
            if ((dispatchReceiver == null || (typeRef = dispatchReceiver.getTypeRef()) == null || (coneType = FirTypeUtilsKt.getConeType(typeRef)) == null) ? false : containsStubType(coneType)) {
                z = true;
            } else {
                z = false;
            }
        }
        if (!z) {
            return true;
        }
        NewConstraintSystemImpl system = getCandidate(call).getSystem();
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
            if (typeVariableMarker != null && constraintStorageCurrentStorage.getPostponedTypeVariables().contains(typeVariableMarker)) {
                z2 = true;
            } else {
                Intrinsics.checkNotNull(typeVariableMarker);
                if (isSyntheticTypeVariable(typeVariableMarker) || getComponents().getCallCompleter().getCompleter().getVariableFixationFinder().isTypeVariableHasProperConstraint(system, typeConstructorMarker)) {
                    z2 = true;
                } else {
                    z2 = false;
                }
            }
            if (!z2) {
                return false;
            }
        }
        return true;
    }

    private final boolean hasPostponed(FirStatement firStatement) {
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        ConstraintSystemCompleterKt.processAllContainingCallCandidates(firStatement, false, new Function1<Candidate, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.inference.FirDelegatedPropertyInferenceSession.hasPostponed.1
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

    private final NotFixedTypeToVariableSubstitutorForDelegateInference createNonFixedTypeToVariableSubstitutor() {
        ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(getComponents().getSession());
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<TypeVariableMarker, ConeTypeVariable> entry : this.syntheticTypeVariableByTypeVariable.entrySet()) {
            TypeVariableMarker key = entry.getKey();
            ConeTypeVariable value = entry.getValue();
            Object objDefaultType = MarkerExtensionsKt.defaultType(key, typeContext);
            Intrinsics.checkNotNull(objDefaultType, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
            linkedHashMap.put(value, (ConeKotlinType) objDefaultType);
        }
        return new NotFixedTypeToVariableSubstitutorForDelegateInference(linkedHashMap, typeContext);
    }

    private final ConeSubstitutor createToSyntheticTypeVariableSubstitutor() {
        ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(getComponents().getSession());
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<TypeVariableMarker, ConeTypeVariable> entry : this.syntheticTypeVariableByTypeVariable.entrySet()) {
            linkedHashMap.put(MarkerExtensionsKt.freshTypeConstructor(entry.getKey(), typeContext), entry.getValue().getDefaultType());
        }
        return typeContext.typeSubstitutorByTypeConstructor((Map<TypeConstructorMarker, ? extends KotlinTypeMarker>) linkedHashMap);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession
    public boolean hasSyntheticTypeVariables() {
        return !this.syntheticTypeVariableByTypeVariable.isEmpty();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession
    public boolean isSyntheticTypeVariable(TypeVariableMarker typeVariable) {
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        return CollectionsKt.contains(this.syntheticTypeVariableByTypeVariable.values(), typeVariable);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSessionForChainedResolve, org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession
    public void fixSyntheticTypeVariableWithNotEnoughInformation(TypeVariableMarker typeVariable, ConstraintSystemCompletionContext completionContext) {
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        Intrinsics.checkNotNullParameter(completionContext, "completionContext");
        ConeTypeVariable coneTypeVariable = (ConeTypeVariable) typeVariable;
        completionContext.fixVariable(typeVariable, new ConeStubTypeForSyntheticFixation(new ConeStubTypeConstructor(coneTypeVariable, false, true), ConeNullability.INSTANCE.create(ConeTypeUtilsKt.isMarkedNullable(coneTypeVariable.getDefaultType()))), new ConeFixVariableConstraintPosition(typeVariable));
    }

    public final List<FirResolvable> completeCandidates() {
        final NewConstraintSystemImpl newConstraintSystemImplCreateConstraintSystem = InferenceComponentsKt.getInferenceComponents(getComponents().getSession()).createConstraintSystem();
        List<Pair<FirResolvable, Candidate>> partiallyResolvedCalls = getPartiallyResolvedCalls();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = partiallyResolvedCalls.iterator();
        while (it.hasNext()) {
            Object first = ((Pair) it.next()).getFirst();
            if (CandidateFactoryKt.candidate((FirResolvable) first) == null) {
                first = null;
            }
            FirResolvable firResolvable = (FirResolvable) first;
            if (firResolvable != null) {
                arrayList.add(firResolvable);
            }
        }
        final ArrayList arrayList2 = arrayList;
        NotFixedTypeToVariableSubstitutorForDelegateInference notFixedTypeToVariableSubstitutorForDelegateInferenceCreateNonFixedTypeToVariableSubstitutor = createNonFixedTypeToVariableSubstitutor();
        for (Pair<FirResolvable, Candidate> pair : getPartiallyResolvedCalls()) {
            FirResolvable firResolvableComponent1 = pair.component1();
            Candidate candidateComponent2 = pair.component2();
            if (candidateComponent2.isSuccessful()) {
                integrateConstraints(newConstraintSystemImplCreateConstraintSystem, candidateComponent2.getSystem().asReadOnlyStorage(), notFixedTypeToVariableSubstitutorForDelegateInferenceCreateNonFixedTypeToVariableSubstitutor, CandidateFactoryKt.candidate(firResolvableComponent1) != null);
            }
        }
        BodyResolveContext bodyResolveContext = getResolutionContext().getBodyResolveContext();
        FirInferenceSession firInferenceSession = FirInferenceSession.INSTANCE.getDEFAULT();
        FirInferenceSession inferenceSession = bodyResolveContext.getInferenceSession();
        bodyResolveContext.setInferenceSession(firInferenceSession);
        try {
            getComponents().getCallCompleter().getCompleter().complete(newConstraintSystemImplCreateConstraintSystem.asConstraintSystemCompleterContext(), ConstraintSystemCompletionMode.FULL, arrayList2, this.unitType, getResolutionContext(), (32 & 32) != 0 ? false : false, new Function1<PostponedResolvedAtom, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.inference.FirDelegatedPropertyInferenceSession$completeCandidates$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(PostponedResolvedAtom postponedResolvedAtom) {
                    invoke2(postponedResolvedAtom);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(final PostponedResolvedAtom lambdaAtom) {
                    Intrinsics.checkNotNullParameter(lambdaAtom, "lambdaAtom");
                    FirDelegatedPropertyInferenceSession firDelegatedPropertyInferenceSession = this.this$0;
                    for (Object obj : CollectionsKt.asReversed(arrayList2)) {
                        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
                        ConstraintSystemCompleterKt.processAllContainingCallCandidates((FirStatement) ((FirElement) obj), true, new Function1<Candidate, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.inference.FirDelegatedPropertyInferenceSession$completeCandidates$1$1$containingCandidateForLambda$1$1
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
                            public final void invoke2(Candidate subCandidate) {
                                Intrinsics.checkNotNullParameter(subCandidate, "subCandidate");
                                if (subCandidate.getPostponedAtoms().contains(lambdaAtom)) {
                                    booleanRef.element = true;
                                }
                            }
                        });
                        if (booleanRef.element) {
                            this.this$0.postponedArgumentsAnalyzer.analyze(newConstraintSystemImplCreateConstraintSystem, lambdaAtom, firDelegatedPropertyInferenceSession.getCandidate((FirResolvable) obj), ConstraintSystemCompletionMode.FULL);
                            return;
                        }
                    }
                    throw new NoSuchElementException("Collection contains no element matching the predicate.");
                }
            });
            Unit unit = Unit.INSTANCE;
            bodyResolveContext.setInferenceSession(inferenceSession);
            Iterator<Pair<FirResolvable, Candidate>> it2 = getPartiallyResolvedCalls().iterator();
            while (it2.hasNext()) {
                Candidate candidateComponent3 = it2.next().component2();
                Iterator<ConstraintSystemError> it3 = newConstraintSystemImplCreateConstraintSystem.getErrors().iterator();
                while (it3.hasNext()) {
                    candidateComponent3.addDiagnostic(new InferenceError(it3.next()));
                }
            }
            this.resultingConstraintSystem = newConstraintSystemImplCreateConstraintSystem;
            return arrayList2;
        } catch (Throwable th) {
            bodyResolveContext.setInferenceSession(inferenceSession);
            throw th;
        }
    }

    public final ConeSubstitutor createFinalSubstitutor() {
        NotFixedTypeToVariableSubstitutorForDelegateInference notFixedTypeToVariableSubstitutorForDelegateInferenceCreateNonFixedTypeToVariableSubstitutor = createNonFixedTypeToVariableSubstitutor();
        ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(getComponents().getSession());
        NewConstraintSystem newConstraintSystem = this.resultingConstraintSystem;
        if (newConstraintSystem == null) {
            Intrinsics.throwUninitializedPropertyAccessException("resultingConstraintSystem");
            newConstraintSystem = null;
        }
        TypeSubstitutorMarker typeSubstitutorMarkerBuildAbstractResultingSubstitutor$default = org.jetbrains.kotlin.resolve.calls.inference.InferenceUtilsKt.buildAbstractResultingSubstitutor$default(newConstraintSystem.asReadOnlyStorage(), typeContext, false, 2, null);
        Intrinsics.checkNotNull(typeSubstitutorMarkerBuildAbstractResultingSubstitutor$default, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor");
        ChainedSubstitutor chainedSubstitutor = new ChainedSubstitutor(notFixedTypeToVariableSubstitutorForDelegateInferenceCreateNonFixedTypeToVariableSubstitutor, (ConeSubstitutor) typeSubstitutorMarkerBuildAbstractResultingSubstitutor$default);
        ConeInferenceContext coneInferenceContext = typeContext;
        Collection<ConeStubType> collectionValues = this.stubTypesByTypeVariable.values();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionValues, 10));
        Iterator<T> it = collectionValues.iterator();
        while (it.hasNext()) {
            arrayList.add(((ConeStubType) it.next()).getConstructor());
        }
        return SubstitutorsKt.replaceStubsAndTypeVariablesToErrors(chainedSubstitutor, coneInferenceContext, arrayList);
    }

    private final void registerSyntheticVariables(ConstraintStorage storage) {
        Iterator<VariableWithConstraints> it = storage.getNotFixedTypeVariables().values().iterator();
        while (it.hasNext()) {
            TypeVariableMarker typeVariable = it.next().getTypeVariable();
            Intrinsics.checkNotNull(typeVariable, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeTypeVariable");
            ConeTypeVariable coneTypeVariable = (ConeTypeVariable) typeVariable;
            Map<TypeVariableMarker, ConeTypeVariable> map = this.syntheticTypeVariableByTypeVariable;
            ConeTypeVariable coneTypeVariable2 = map.get(coneTypeVariable);
            if (coneTypeVariable2 == null) {
                coneTypeVariable2 = new ConeTypeVariable(Marker.ANY_NON_NULL_MARKER + coneTypeVariable.getTypeConstructor().getName(), null, 2, null);
                this.currentConstraintSystem.registerVariable(coneTypeVariable2);
                map.put(coneTypeVariable, coneTypeVariable2);
            }
            ConeTypeVariable coneTypeVariable3 = coneTypeVariable2;
            Map<ConeTypeVariable, ConeStubType> map2 = this.stubTypesByTypeVariable;
            if (map2.get(coneTypeVariable) == null) {
                map2.put(coneTypeVariable, new ConeStubTypeForChainInference(coneTypeVariable3, ConeNullability.INSTANCE.create(ConeTypeUtilsKt.isMarkedNullable(coneTypeVariable3.getDefaultType()))));
            }
        }
    }

    @Override // org.jetbrains.kotlin.fir.resolve.inference.FirInferenceSession
    public Map<TypeConstructorMarker, ConeStubType> createSyntheticStubTypes(NewConstraintSystemImpl system) {
        Intrinsics.checkNotNullParameter(system, "system");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        registerSyntheticVariables(system.currentStorage());
        for (TypeVariableMarker typeVariableMarker : system.getPostponedTypeVariables()) {
            Intrinsics.checkNotNull(typeVariableMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeTypeVariable");
            ConeTypeVariableTypeConstructor typeConstructor = ((ConeTypeVariable) typeVariableMarker).getTypeConstructor();
            ConeStubType coneStubType = this.stubTypesByTypeVariable.get(typeVariableMarker);
            Intrinsics.checkNotNull(coneStubType);
            linkedHashMap.put(typeConstructor, coneStubType);
        }
        return linkedHashMap;
    }

    private final void integrateConstraints(NewConstraintSystemImpl commonSystem, ConstraintStorage storage, ConeSubstitutor nonFixedToVariablesSubstitutor, boolean shouldIntegrateAllConstraints) {
        if (shouldIntegrateAllConstraints) {
            for (VariableWithConstraints variableWithConstraints : storage.getNotFixedTypeVariables().values()) {
                if (!isSyntheticTypeVariable(variableWithConstraints.getTypeVariable())) {
                    org.jetbrains.kotlin.resolve.calls.inference.InferenceUtilsKt.registerTypeVariableIfNotPresent(commonSystem, variableWithConstraints.getTypeVariable());
                }
            }
        }
        TypeSubstitutorMarker typeSubstitutorMarkerBuildAbstractResultingSubstitutor = org.jetbrains.kotlin.resolve.calls.inference.InferenceUtilsKt.buildAbstractResultingSubstitutor(storage, commonSystem, false);
        Intrinsics.checkNotNull(typeSubstitutorMarkerBuildAbstractResultingSubstitutor, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor");
        ConeSubstitutor coneSubstitutor = (ConeSubstitutor) typeSubstitutorMarkerBuildAbstractResultingSubstitutor;
        Iterator<InitialConstraint> it = storage.getInitialConstraints().iterator();
        while (it.hasNext()) {
            integrateConstraintToSystem(commonSystem, it.next(), coneSubstitutor, nonFixedToVariablesSubstitutor, storage.getFixedTypeVariables());
        }
        if (shouldIntegrateAllConstraints) {
            for (Map.Entry<TypeConstructorMarker, KotlinTypeMarker> entry : storage.getFixedTypeVariables().entrySet()) {
                TypeConstructorMarker key = entry.getKey();
                KotlinTypeMarker value = entry.getValue();
                TypeVariableMarker typeVariableMarker = (TypeVariableMarker) MapsKt.getValue(storage.getAllTypeVariables(), key);
                if (!isSyntheticTypeVariable(typeVariableMarker)) {
                    org.jetbrains.kotlin.resolve.calls.inference.InferenceUtilsKt.registerTypeVariableIfNotPresent(commonSystem, typeVariableMarker);
                    Intrinsics.checkNotNull(typeVariableMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeTypeVariable");
                    commonSystem.addEqualityConstraint(((ConeTypeVariable) typeVariableMarker).getDefaultType(), value, BuilderInferencePosition.INSTANCE);
                }
            }
        }
    }
}
