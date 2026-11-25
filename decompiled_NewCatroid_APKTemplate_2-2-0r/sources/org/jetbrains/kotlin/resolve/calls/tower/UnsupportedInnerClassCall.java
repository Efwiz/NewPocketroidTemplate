package org.jetbrains.kotlin.resolve.calls.tower;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ImplicitScopeTower.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/UnsupportedInnerClassCall;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ResolutionDiagnostic;", CrashHianalyticsData.MESSAGE, "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnsupportedInnerClassCall extends ResolutionDiagnostic {
    private final String message;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UnsupportedInnerClassCall(String message) {
        super(CandidateApplicability.K1_IMPOSSIBLE_TO_GENERATE);
        Intrinsics.checkNotNullParameter(message, "message");
        this.message = message;
    }

    public final String getMessage() {
        return this.message;
    }
}
