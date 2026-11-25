package org.jetbrains.kotlin.resolve.calls.components.candidate;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallComponents;
import org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom;
import org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower;
import org.jetbrains.kotlin.types.TypeSubstitutor;

/* JADX INFO: compiled from: SimpleResolutionCandidate.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\b\u0016\u0018\u00002\u00020\u0001B9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eJ\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001dH\u0016J\u000e\u0010$\u001a\b\u0012\u0004\u0012\u00020\u001d0%H\u0016R\u0014\u0010\b\u001a\u00020\tX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\f\u001a\u0004\u0018\u00010\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u001e\u001a\u0004\u0018\u00010\u00018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 ¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/candidate/SimpleResolutionCandidate;", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "callComponents", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "resolutionCallbacks", "Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "baseSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/MutableResolvedCallAtom;", "knownTypeParametersResultingSubstitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "(Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;Lorg/jetbrains/kotlin/resolve/calls/model/MutableResolvedCallAtom;Lorg/jetbrains/kotlin/types/TypeSubstitutor;)V", "getBaseSystem", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "getCallComponents", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "getKnownTypeParametersResultingSubstitutor", "()Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "getResolutionCallbacks", "()Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", "getResolvedCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/MutableResolvedCallAtom;", "getScopeTower", "()Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "subResolvedAtoms", "", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "variableCandidateIfInvoke", "getVariableCandidateIfInvoke", "()Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "addResolvedKtPrimitive", "", "resolvedAtom", "getSubResolvedAtoms", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class SimpleResolutionCandidate extends ResolutionCandidate {
    private final ConstraintStorage baseSystem;
    private final KotlinCallComponents callComponents;
    private final TypeSubstitutor knownTypeParametersResultingSubstitutor;
    private final KotlinResolutionCallbacks resolutionCallbacks;
    private final MutableResolvedCallAtom resolvedCall;
    private final ImplicitScopeTower scopeTower;
    private List<ResolvedAtom> subResolvedAtoms;

    public /* synthetic */ SimpleResolutionCandidate(KotlinCallComponents kotlinCallComponents, KotlinResolutionCallbacks kotlinResolutionCallbacks, ImplicitScopeTower implicitScopeTower, ConstraintStorage constraintStorage, MutableResolvedCallAtom mutableResolvedCallAtom, TypeSubstitutor typeSubstitutor, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(kotlinCallComponents, kotlinResolutionCallbacks, implicitScopeTower, constraintStorage, mutableResolvedCallAtom, (i & 32) != 0 ? null : typeSubstitutor);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate
    public KotlinCallComponents getCallComponents() {
        return this.callComponents;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate
    public KotlinResolutionCallbacks getResolutionCallbacks() {
        return this.resolutionCallbacks;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate
    public ImplicitScopeTower getScopeTower() {
        return this.scopeTower;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate
    protected ConstraintStorage getBaseSystem() {
        return this.baseSystem;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate
    public MutableResolvedCallAtom getResolvedCall() {
        return this.resolvedCall;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate
    public TypeSubstitutor getKnownTypeParametersResultingSubstitutor() {
        return this.knownTypeParametersResultingSubstitutor;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimpleResolutionCandidate(KotlinCallComponents callComponents, KotlinResolutionCallbacks resolutionCallbacks, ImplicitScopeTower scopeTower, ConstraintStorage baseSystem, MutableResolvedCallAtom resolvedCall, TypeSubstitutor typeSubstitutor) {
        super(null);
        Intrinsics.checkNotNullParameter(callComponents, "callComponents");
        Intrinsics.checkNotNullParameter(resolutionCallbacks, "resolutionCallbacks");
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(baseSystem, "baseSystem");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        this.callComponents = callComponents;
        this.resolutionCallbacks = resolutionCallbacks;
        this.scopeTower = scopeTower;
        this.baseSystem = baseSystem;
        this.resolvedCall = resolvedCall;
        this.knownTypeParametersResultingSubstitutor = typeSubstitutor;
        this.subResolvedAtoms = new ArrayList();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate
    public ResolutionCandidate getVariableCandidateIfInvoke() {
        return getCallComponents().getStatelessCallbacks().getVariableCandidateIfInvoke(getResolvedCall().getAtom());
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate
    public List<ResolvedAtom> getSubResolvedAtoms() {
        return this.subResolvedAtoms;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate
    public void addResolvedKtPrimitive(ResolvedAtom resolvedAtom) {
        Intrinsics.checkNotNullParameter(resolvedAtom, "resolvedAtom");
        this.subResolvedAtoms.add(resolvedAtom);
    }
}
