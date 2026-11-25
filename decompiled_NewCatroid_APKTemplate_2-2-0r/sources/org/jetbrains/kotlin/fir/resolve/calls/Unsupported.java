package org.jetbrains.kotlin.fir.resolve.calls;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;

/* JADX INFO: compiled from: ResolutionDiagnostic.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/Unsupported;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionDiagnostic;", CrashHianalyticsData.MESSAGE, "", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/KtSourceElement;)V", "getMessage", "()Ljava/lang/String;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Unsupported extends ResolutionDiagnostic {
    private final String message;
    private final KtSourceElement source;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Unsupported(String message, KtSourceElement ktSourceElement) {
        super(CandidateApplicability.K2_UNSUPPORTED);
        Intrinsics.checkNotNullParameter(message, "message");
        this.message = message;
        this.source = ktSourceElement;
    }

    public final String getMessage() {
        return this.message;
    }

    public final KtSourceElement getSource() {
        return this.source;
    }
}
