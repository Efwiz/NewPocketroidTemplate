package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.components.candidate.ResolutionCandidate;

/* JADX INFO: compiled from: ResolvedCallAtoms.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0014\u0010\f\u001a\u00020\r*\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000fH&J\f\u0010\u0010\u001a\u00020\u000f*\u00020\u0005H\u0016R\u0019\u0010\u0003\u001a\u00020\u0004*\u00020\u00058Ä\u0002X\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0019\u0010\b\u001a\u00020\t*\u00020\u00058Ä\u0002X\u0084\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/ResolutionPart;", "", "()V", "candidateDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;", "getCandidateDescriptor", "(Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;)Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "kotlinCall", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "getKotlinCall", "(Lorg/jetbrains/kotlin/resolve/calls/components/candidate/ResolutionCandidate;)Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "process", "", "workIndex", "", "workCount", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ResolutionPart {
    public abstract void process(ResolutionCandidate resolutionCandidate, int i);

    public int workCount(ResolutionCandidate resolutionCandidate) {
        Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
        return 1;
    }

    protected final CallableDescriptor getCandidateDescriptor(ResolutionCandidate resolutionCandidate) {
        Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
        return resolutionCandidate.getResolvedCall().get_candidateDescriptor();
    }

    protected final KotlinCall getKotlinCall(ResolutionCandidate resolutionCandidate) {
        Intrinsics.checkNotNullParameter(resolutionCandidate, "<this>");
        return resolutionCandidate.getResolvedCall().getAtom();
    }
}
