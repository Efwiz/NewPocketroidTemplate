package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;

/* JADX INFO: compiled from: TowerUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0019\u0010\r\u001a\u0002H\u000e\"\b\b\u0000\u0010\u000e*\u00020\u000f*\u0002H\u000e¢\u0006\u0002\u0010\u0010\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0015\u0010\u0003\u001a\u00020\u0004*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0005\"\u0015\u0010\u0006\u001a\u00020\u0004*\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\b\"\u0015\u0010\t\u001a\u00020\u0004*\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u0011"}, d2 = {"INAPPLICABLE_STATUSES", "", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "isInapplicable", "", "(Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;)Z", "isSynthesized", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)Z", "requiresExtensionReceiver", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateWithBoundDispatchReceiver;", "getRequiresExtensionReceiver", "(Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateWithBoundDispatchReceiver;)Z", "forceResolution", "C", "Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;)Lorg/jetbrains/kotlin/resolve/calls/tower/Candidate;", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TowerUtilsKt {
    private static final Set<CandidateApplicability> INAPPLICABLE_STATUSES = SetsKt.setOf((Object[]) new CandidateApplicability[]{CandidateApplicability.INAPPLICABLE, CandidateApplicability.INAPPLICABLE_ARGUMENTS_MAPPING_ERROR, CandidateApplicability.INAPPLICABLE_WRONG_RECEIVER});

    public static final boolean isSynthesized(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        return (callableDescriptor instanceof CallableMemberDescriptor) && ((CallableMemberDescriptor) callableDescriptor).getKind() == CallableMemberDescriptor.Kind.SYNTHESIZED;
    }

    public static final boolean getRequiresExtensionReceiver(CandidateWithBoundDispatchReceiver candidateWithBoundDispatchReceiver) {
        Intrinsics.checkNotNullParameter(candidateWithBoundDispatchReceiver, "<this>");
        return candidateWithBoundDispatchReceiver.getDescriptor().getExtensionReceiverParameter() != null;
    }

    public static final boolean isInapplicable(CandidateApplicability candidateApplicability) {
        Intrinsics.checkNotNullParameter(candidateApplicability, "<this>");
        return INAPPLICABLE_STATUSES.contains(candidateApplicability);
    }

    public static final <C extends Candidate> C forceResolution(C c) {
        Intrinsics.checkNotNullParameter(c, "<this>");
        c.getResultingApplicability();
        return c;
    }
}
