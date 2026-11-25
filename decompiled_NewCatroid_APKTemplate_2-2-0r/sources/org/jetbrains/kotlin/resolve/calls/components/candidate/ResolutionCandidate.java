package org.jetbrains.kotlin.resolve.calls.components.candidate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.konan.util.DependencyDownloader;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.resolve.calls.components.ClassicTypeSystemContextForCSKt;
import org.jetbrains.kotlin.resolve.calls.components.KotlinResolutionCallbacks;
import org.jetbrains.kotlin.resolve.calls.inference.NewConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintStorage;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl;
import org.jetbrains.kotlin.resolve.calls.model.CompatibilityWarning;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallComponents;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCallDiagnostic;
import org.jetbrains.kotlin.resolve.calls.model.KotlinDiagnosticsHolder;
import org.jetbrains.kotlin.resolve.calls.model.MutableResolvedCallAtom;
import org.jetbrains.kotlin.resolve.calls.model.ResolutionPart;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom;
import org.jetbrains.kotlin.resolve.calls.tower.Candidate;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicabilityKt;
import org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTower;
import org.jetbrains.kotlin.resolve.calls.tower.ImplicitScopeTowerKt;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;

/* JADX INFO: compiled from: ResolutionCandidate.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000´\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\b\u0004¢\u0006\u0002\u0010\u0003J\u0010\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020\u0001H\u0016J\u0010\u0010I\u001a\u00020G2\u0006\u0010J\u001a\u00020\u0014H\u0016J\u0010\u0010K\u001a\u00020G2\u0006\u0010L\u001a\u00020MH&J\b\u0010N\u001a\u0004\u0018\u00010OJ\u000e\u0010P\u001a\b\u0012\u0004\u0012\u00020M0\u0013H&J\u0006\u0010Q\u001a\u00020RJ\"\u0010S\u001a\u00020\u00182\u0006\u0010T\u001a\u00020)2\u0006\u0010U\u001a\u00020\u00182\b\b\u0002\u0010V\u001a\u00020;H\u0002J\u0010\u0010W\u001a\u00020G2\u0006\u0010U\u001a\u00020\u0018H\u0002J\b\u0010X\u001a\u00020YH\u0016R\u0014\u0010\u0004\u001a\u0004\u0018\u00010\u0005X¤\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u000e\u001a\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0017\u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0019R\u0014\u0010\u001a\u001a\u0004\u0018\u00010\u001bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00140\u001fX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0010\u0010\"\u001a\u0004\u0018\u00010#X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010$\u001a\u00020%X¦\u0004¢\u0006\u0006\u001a\u0004\b&\u0010'R\u001a\u0010(\u001a\b\u0012\u0004\u0012\u00020)0\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b*\u0010\u0016R\u0012\u0010+\u001a\u00020,X¦\u0004¢\u0006\u0006\u001a\u0004\b-\u0010.R\u0017\u0010/\u001a\b\u0012\u0004\u0012\u00020\r008F¢\u0006\u0006\u001a\u0004\b1\u00102R\u0014\u00103\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b4\u00105R\u0012\u00106\u001a\u000207X¦\u0004¢\u0006\u0006\u001a\u0004\b8\u00109R\u000e\u0010:\u001a\u00020;X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010<\u001a\u00020;8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b=\u0010>R\u0014\u0010?\u001a\u00020\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b@\u00105R\u0014\u0010A\u001a\u0004\u0018\u00010\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\bB\u0010CR\u0018\u0010D\u001a\u00020\u0018*\u00020\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bD\u0010E\u0082\u0001\u0002Z[¨\u0006\\"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinDiagnosticsHolder;", "()V", "baseSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "getBaseSystem", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintStorage;", "callComponents", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "getCallComponents", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallComponents;", "currentApplicability", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "diagnostics", "", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "getDiagnostics", "()Ljava/util/List;", "isSuccessful", "", "()Z", "knownTypeParametersResultingSubstitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "getKnownTypeParametersResultingSubstitutor", "()Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "mutableDiagnostics", "Ljava/util/ArrayList;", "getMutableDiagnostics", "()Ljava/util/ArrayList;", "newSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl;", "resolutionCallbacks", "Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", "getResolutionCallbacks", "()Lorg/jetbrains/kotlin/resolve/calls/components/KotlinResolutionCallbacks;", "resolutionSequence", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolutionPart;", "getResolutionSequence", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/MutableResolvedCallAtom;", "getResolvedCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/MutableResolvedCallAtom;", "resultingApplicabilities", "", "getResultingApplicabilities", "()[Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "resultingApplicability", "getResultingApplicability", "()Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "getScopeTower", "()Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "step", "", "stepCount", "getStepCount", "()I", "variableApplicability", "getVariableApplicability", "variableCandidateIfInvoke", "getVariableCandidateIfInvoke", "()Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "isSuccessOrSuccessWithError", "(Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;)Z", "addCompatibilityWarning", "", "other", "addDiagnostic", "diagnostic", "addResolvedKtPrimitive", "resolvedAtom", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "getResultingSubstitutor", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "getSubResolvedAtoms", "getSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/NewConstraintSystem;", "processPart", DependencyDownloader.TMP_SUFFIX, "stopOnFirstError", "startWorkIndex", "processParts", "toString", "", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/CallableReferenceResolutionCandidate;", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/SimpleResolutionCandidate;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ResolutionCandidate implements KotlinDiagnosticsHolder, Candidate {
    private CandidateApplicability currentApplicability;
    private final List<KotlinCallDiagnostic> diagnostics;
    private final ArrayList<KotlinCallDiagnostic> mutableDiagnostics;
    private NewConstraintSystemImpl newSystem;
    private int step;

    public /* synthetic */ ResolutionCandidate(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract void addResolvedKtPrimitive(ResolvedAtom resolvedAtom);

    protected abstract ConstraintStorage getBaseSystem();

    public abstract KotlinCallComponents getCallComponents();

    public abstract TypeSubstitutor getKnownTypeParametersResultingSubstitutor();

    public abstract KotlinResolutionCallbacks getResolutionCallbacks();

    public abstract MutableResolvedCallAtom getResolvedCall();

    public abstract ImplicitScopeTower getScopeTower();

    public abstract List<ResolvedAtom> getSubResolvedAtoms();

    public abstract ResolutionCandidate getVariableCandidateIfInvoke();

    private ResolutionCandidate() {
        ArrayList<KotlinCallDiagnostic> arrayList = new ArrayList<>();
        this.mutableDiagnostics = arrayList;
        this.diagnostics = arrayList;
        this.currentApplicability = CandidateApplicability.RESOLVED;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.Candidate
    public boolean isSuccessful() {
        processParts(true);
        Comparable comparableMinOrNull = ArraysKt.minOrNull(getResultingApplicabilities());
        Intrinsics.checkNotNull(comparableMinOrNull);
        return isSuccessOrSuccessWithError((CandidateApplicability) comparableMinOrNull) && !getSystem().getHasContradiction();
    }

    private final boolean isSuccessOrSuccessWithError(CandidateApplicability candidateApplicability) {
        return candidateApplicability.compareTo(CandidateApplicability.RESOLVED_LOW_PRIORITY) >= 0;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.Candidate
    public CandidateApplicability getResultingApplicability() {
        processParts(false);
        CandidateApplicability candidateApplicability = (CandidateApplicability) ArraysKt.minOrNull(getResultingApplicabilities());
        return candidateApplicability == null ? CandidateApplicability.RESOLVED : candidateApplicability;
    }

    public List<ResolutionPart> getResolutionSequence() {
        return getResolvedCall().getAtom().getCallKind().getResolutionSequence();
    }

    protected final ArrayList<KotlinCallDiagnostic> getMutableDiagnostics() {
        return this.mutableDiagnostics;
    }

    public final CallableDescriptor getDescriptor() {
        return getResolvedCall().get_candidateDescriptor();
    }

    public final List<KotlinCallDiagnostic> getDiagnostics() {
        return this.diagnostics;
    }

    public final CandidateApplicability[] getResultingApplicabilities() {
        return new CandidateApplicability[]{this.currentApplicability, ImplicitScopeTowerKt.getResultApplicabilityForConstraintErrors(getSystem().getErrors()), getVariableApplicability()};
    }

    private final CandidateApplicability getVariableApplicability() {
        CandidateApplicability resultingApplicability;
        ResolutionCandidate variableCandidateIfInvoke = getVariableCandidateIfInvoke();
        return (variableCandidateIfInvoke == null || (resultingApplicability = variableCandidateIfInvoke.getResultingApplicability()) == null) ? CandidateApplicability.RESOLVED : resultingApplicability;
    }

    private final int getStepCount() {
        Iterator<T> it = getResolutionSequence().iterator();
        int iWorkCount = 0;
        while (it.hasNext()) {
            iWorkCount += ((ResolutionPart) it.next()).workCount(this);
        }
        return iWorkCount;
    }

    public final TypeSubstitutorMarker getResultingSubstitutor() {
        NewConstraintSystemImpl newConstraintSystemImpl = this.newSystem;
        if (newConstraintSystemImpl != null) {
            return newConstraintSystemImpl.buildCurrentSubstitutor();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.KotlinDiagnosticsHolder
    public void addDiagnostic(KotlinCallDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        this.mutableDiagnostics.add(diagnostic);
        this.currentApplicability = (CandidateApplicability) ComparisonsKt.minOf(diagnostic.getCandidateApplicability(), this.currentApplicability);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.Candidate
    public void addCompatibilityWarning(Candidate other) {
        Intrinsics.checkNotNullParameter(other, "other");
        if ((other instanceof ResolutionCandidate) && this != other && getClass() == other.getClass()) {
            addDiagnostic(new CompatibilityWarning(((ResolutionCandidate) other).getDescriptor()));
        }
    }

    public String toString() {
        String strRender = DescriptorRenderer.COMPACT.render(getResolvedCall().get_candidateDescriptor());
        CandidateApplicability candidateApplicability = (CandidateApplicability) ArraysKt.minOrNull(getResultingApplicabilities());
        return ((candidateApplicability == null || CandidateApplicabilityKt.isSuccess(candidateApplicability)) ? "OK" : "FAIL") + '(' + new StringBuilder().append(this.step).append('/').append(getStepCount()).toString() + "): " + strRender;
    }

    public final NewConstraintSystem getSystem() {
        if (this.newSystem == null) {
            this.newSystem = ClassicTypeSystemContextForCSKt.NewConstraintSystemImpl(getCallComponents().getConstraintInjector(), getCallComponents().getBuiltIns(), getCallComponents().getKotlinTypeRefiner(), getCallComponents().getLanguageVersionSettings());
            if (getBaseSystem() != null) {
                NewConstraintSystemImpl newConstraintSystemImpl = this.newSystem;
                Intrinsics.checkNotNull(newConstraintSystemImpl);
                ConstraintStorage baseSystem = getBaseSystem();
                Intrinsics.checkNotNull(baseSystem);
                newConstraintSystemImpl.addOtherSystem(baseSystem);
            }
        }
        NewConstraintSystemImpl newConstraintSystemImpl2 = this.newSystem;
        Intrinsics.checkNotNull(newConstraintSystemImpl2);
        return newConstraintSystemImpl2;
    }

    private final void processParts(boolean stopOnFirstError) {
        if ((!stopOnFirstError || this.step <= 0) && this.step != getStepCount()) {
            int i = this.step;
            int i2 = 0;
            while (i > 0) {
                int iWorkCount = getResolutionSequence().get(i2).workCount(this);
                if (i < iWorkCount) {
                    break;
                }
                i2++;
                i -= iWorkCount;
            }
            if (i2 < getResolutionSequence().size()) {
                if (processPart(getResolutionSequence().get(i2), stopOnFirstError, i)) {
                    return;
                } else {
                    i2++;
                }
            }
            while (i2 < getResolutionSequence().size()) {
                if (processPart$default(this, getResolutionSequence().get(i2), stopOnFirstError, 0, 4, null)) {
                    return;
                } else {
                    i2++;
                }
            }
            if (this.step == getStepCount()) {
                getResolvedCall().setAnalyzedResults(getSubResolvedAtoms());
            }
        }
    }

    static /* synthetic */ boolean processPart$default(ResolutionCandidate resolutionCandidate, ResolutionPart resolutionPart, boolean z, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: processPart");
        }
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return resolutionCandidate.processPart(resolutionPart, z, i);
    }

    private final boolean processPart(ResolutionPart part, boolean stopOnFirstError, int startWorkIndex) {
        int iWorkCount = part.workCount(this);
        while (startWorkIndex < iWorkCount) {
            if (stopOnFirstError && !CandidateApplicabilityKt.isSuccess(this.currentApplicability)) {
                return true;
            }
            part.process(this, startWorkIndex);
            this.step++;
            startWorkIndex++;
        }
        return false;
    }
}
