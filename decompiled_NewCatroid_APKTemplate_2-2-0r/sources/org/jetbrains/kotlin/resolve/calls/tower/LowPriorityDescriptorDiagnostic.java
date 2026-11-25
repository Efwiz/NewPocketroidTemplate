package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;

/* JADX INFO: compiled from: ImplicitScopeTower.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/LowPriorityDescriptorDiagnostic;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ResolutionDiagnostic;", "()V", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LowPriorityDescriptorDiagnostic extends ResolutionDiagnostic {
    public static final LowPriorityDescriptorDiagnostic INSTANCE = new LowPriorityDescriptorDiagnostic();

    private LowPriorityDescriptorDiagnostic() {
        super(CandidateApplicability.RESOLVED_LOW_PRIORITY);
    }
}
