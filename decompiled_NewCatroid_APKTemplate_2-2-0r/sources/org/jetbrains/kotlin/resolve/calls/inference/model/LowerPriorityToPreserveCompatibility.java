package org.jetbrains.kotlin.resolve.calls.inference.model;

import kotlin.Metadata;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: ConstraintPositionAndErrors.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/LowerPriorityToPreserveCompatibility;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "needToReportWarning", "", "(Z)V", "getNeedToReportWarning", "()Z", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LowerPriorityToPreserveCompatibility extends ConstraintSystemError {
    private final boolean needToReportWarning;

    public final boolean getNeedToReportWarning() {
        return this.needToReportWarning;
    }

    public LowerPriorityToPreserveCompatibility(boolean z) {
        super(CandidateApplicability.RESOLVED_NEED_PRESERVE_COMPATIBILITY, null);
        this.needToReportWarning = z;
    }
}
