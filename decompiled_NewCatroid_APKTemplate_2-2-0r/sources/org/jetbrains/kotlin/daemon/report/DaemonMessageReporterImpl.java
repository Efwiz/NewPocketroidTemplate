package org.jetbrains.kotlin.daemon.report;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.rmi.RemoteException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.daemon.common.CompilerServicesFacadeBase;
import org.jetbrains.kotlin.daemon.common.ReportCategory;
import org.jetbrains.kotlin.daemon.common.ReportSeverity;

/* JADX INFO: compiled from: DaemonMessageReporter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/daemon/report/DaemonMessageReporterImpl;", "Lorg/jetbrains/kotlin/daemon/report/DaemonMessageReporter;", "servicesFacade", "Lorg/jetbrains/kotlin/daemon/common/CompilerServicesFacadeBase;", "mySeverity", "Lorg/jetbrains/kotlin/daemon/common/ReportSeverity;", "(Lorg/jetbrains/kotlin/daemon/common/CompilerServicesFacadeBase;Lorg/jetbrains/kotlin/daemon/common/ReportSeverity;)V", "report", "", "severity", CrashHianalyticsData.MESSAGE, "", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class DaemonMessageReporterImpl implements DaemonMessageReporter {
    private final ReportSeverity mySeverity;
    private final CompilerServicesFacadeBase servicesFacade;

    public DaemonMessageReporterImpl(CompilerServicesFacadeBase servicesFacade, ReportSeverity mySeverity) {
        Intrinsics.checkNotNullParameter(servicesFacade, "servicesFacade");
        Intrinsics.checkNotNullParameter(mySeverity, "mySeverity");
        this.servicesFacade = servicesFacade;
        this.mySeverity = mySeverity;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // org.jetbrains.kotlin.daemon.report.DaemonMessageReporter
    public void report(ReportSeverity severity, String message) throws RemoteException {
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(message, "message");
        if (severity.getCode() <= this.mySeverity.getCode()) {
            this.servicesFacade.report(ReportCategory.DAEMON_MESSAGE.getCode(), severity.getCode(), message, null);
        }
    }
}
