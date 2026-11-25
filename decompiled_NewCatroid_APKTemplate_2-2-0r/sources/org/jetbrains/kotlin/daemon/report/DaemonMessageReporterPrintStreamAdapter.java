package org.jetbrains.kotlin.daemon.report;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.PrintStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.daemon.common.ReportSeverity;

/* JADX INFO: compiled from: DaemonMessageReporter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/daemon/report/DaemonMessageReporterPrintStreamAdapter;", "Lorg/jetbrains/kotlin/daemon/report/DaemonMessageReporter;", "out", "Ljava/io/PrintStream;", "(Ljava/io/PrintStream;)V", "report", "", "severity", "Lorg/jetbrains/kotlin/daemon/common/ReportSeverity;", CrashHianalyticsData.MESSAGE, "", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DaemonMessageReporterPrintStreamAdapter implements DaemonMessageReporter {
    private final PrintStream out;

    public DaemonMessageReporterPrintStreamAdapter(PrintStream out) {
        Intrinsics.checkNotNullParameter(out, "out");
        this.out = out;
    }

    @Override // org.jetbrains.kotlin.daemon.report.DaemonMessageReporter
    public void report(ReportSeverity severity, String message) {
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(message, "message");
        this.out.print("[Kotlin compile daemon][" + severity + "] " + message);
    }
}
