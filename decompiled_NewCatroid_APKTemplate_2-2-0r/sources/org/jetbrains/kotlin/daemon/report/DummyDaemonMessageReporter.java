package org.jetbrains.kotlin.daemon.report;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.daemon.common.ReportSeverity;

/* JADX INFO: compiled from: DaemonMessageReporter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/daemon/report/DummyDaemonMessageReporter;", "Lorg/jetbrains/kotlin/daemon/report/DaemonMessageReporter;", "()V", "report", "", "severity", "Lorg/jetbrains/kotlin/daemon/common/ReportSeverity;", CrashHianalyticsData.MESSAGE, "", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class DummyDaemonMessageReporter implements DaemonMessageReporter {
    public static final DummyDaemonMessageReporter INSTANCE = new DummyDaemonMessageReporter();

    @Override // org.jetbrains.kotlin.daemon.report.DaemonMessageReporter
    public void report(ReportSeverity severity, String message) {
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(message, "message");
    }

    private DummyDaemonMessageReporter() {
    }
}
