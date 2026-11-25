package org.jetbrains.kotlin.resolve.calls.model;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.calls.components.candidate.CallableReferenceResolutionCandidate;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: ResolutionAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\u001e\u0010\u0017\u001a\u00020\u00182\b\u0010\f\u001a\u0004\u0018\u00010\u000b2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aR\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\"\u0010\f\u001a\u0004\u0018\u00010\u000b2\b\u0010\n\u001a\u0004\u0018\u00010\u000b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallableReferenceArgumentAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponedResolvedAtom;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCallableReferenceAtom;", "atom", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCallArgument;", "expectedType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "(Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCallArgument;Lorg/jetbrains/kotlin/types/UnwrappedType;)V", "getAtom", "()Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCallArgument;", "<set-?>", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/CallableReferenceResolutionCandidate;", "candidate", "getCandidate", "()Lorg/jetbrains/kotlin/resolve/calls/components/candidate/CallableReferenceResolutionCandidate;", "completed", "", "getCompleted", "()Z", "setCompleted", "(Z)V", "getExpectedType", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "setAnalyzedResults", "", "subResolvedAtoms", "", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedAtom;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ResolvedCallableReferenceArgumentAtom extends PostponedResolvedAtom implements ResolvedCallableReferenceAtom {
    private final CallableReferenceKotlinCallArgument atom;
    private CallableReferenceResolutionCandidate candidate;
    private boolean completed;
    private final UnwrappedType expectedType;

    @Override // org.jetbrains.kotlin.resolve.calls.model.ResolvedAtom
    public CallableReferenceKotlinCallArgument getAtom() {
        return this.atom;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.PostponedResolvedAtomMarker
    public UnwrappedType getExpectedType() {
        return this.expectedType;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ResolvedCallableReferenceArgumentAtom(CallableReferenceKotlinCallArgument atom, UnwrappedType unwrappedType) {
        super(null);
        Intrinsics.checkNotNullParameter(atom, "atom");
        this.atom = atom;
        this.expectedType = unwrappedType;
    }

    public final CallableReferenceResolutionCandidate getCandidate() {
        return this.candidate;
    }

    public final boolean getCompleted() {
        return this.completed;
    }

    public final void setCompleted(boolean z) {
        this.completed = z;
    }

    public final void setAnalyzedResults(CallableReferenceResolutionCandidate candidate, List<? extends ResolvedAtom> subResolvedAtoms) {
        Intrinsics.checkNotNullParameter(subResolvedAtoms, "subResolvedAtoms");
        this.candidate = candidate;
        setAnalyzedResults(subResolvedAtoms);
    }
}
