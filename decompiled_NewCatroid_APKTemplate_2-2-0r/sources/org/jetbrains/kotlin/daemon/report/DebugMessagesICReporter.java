package org.jetbrains.kotlin.daemon.report;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.rmi.RemoteException;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.build.report.ICReporter;
import org.jetbrains.kotlin.build.report.ICReporterBase;
import org.jetbrains.kotlin.build.report.RemoteICReporter;
import org.jetbrains.kotlin.cli.common.ExitCode;
import org.jetbrains.kotlin.com.google.common.annotations.VisibleForTesting;
import org.jetbrains.kotlin.daemon.common.CompilerServicesFacadeBase;
import org.jetbrains.kotlin.daemon.common.CompilerServicesFacadeBaseKt;
import org.jetbrains.kotlin.daemon.common.ReportCategory;

/* JADX INFO: compiled from: DebugMessagesICReporter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\n\u001a\u00020\u000bH\u0016J\u001e\u0010\f\u001a\u00020\u000b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\bH\u0016J&\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00060\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/daemon/report/DebugMessagesICReporter;", "Lorg/jetbrains/kotlin/build/report/ICReporterBase;", "Lorg/jetbrains/kotlin/build/report/RemoteICReporter;", "servicesFacade", "Lorg/jetbrains/kotlin/daemon/common/CompilerServicesFacadeBase;", "rootDir", "Ljava/io/File;", "reportSeverity", "Lorg/jetbrains/kotlin/build/report/ICReporter$ReportSeverity;", "(Lorg/jetbrains/kotlin/daemon/common/CompilerServicesFacadeBase;Ljava/io/File;Lorg/jetbrains/kotlin/build/report/ICReporter$ReportSeverity;)V", "flush", "", "report", CrashHianalyticsData.MESSAGE, "Lkotlin/Function0;", "", "severity", "reportCompileIteration", "incremental", "", "sourceFiles", "", "exitCode", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
@VisibleForTesting
public final class DebugMessagesICReporter extends ICReporterBase implements RemoteICReporter {
    private final ICReporter.ReportSeverity reportSeverity;
    private final CompilerServicesFacadeBase servicesFacade;

    @Override // org.jetbrains.kotlin.build.report.RemoteReporter
    public void flush() {
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void reportCompileIteration(boolean incremental, Collection<? extends File> sourceFiles, ExitCode exitCode) {
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        Intrinsics.checkNotNullParameter(exitCode, "exitCode");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DebugMessagesICReporter(CompilerServicesFacadeBase servicesFacade, File file, ICReporter.ReportSeverity reportSeverity) {
        super(file);
        Intrinsics.checkNotNullParameter(servicesFacade, "servicesFacade");
        Intrinsics.checkNotNullParameter(reportSeverity, "reportSeverity");
        this.servicesFacade = servicesFacade;
        this.reportSeverity = reportSeverity;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void report(Function0<String> message, ICReporter.ReportSeverity severity) throws RemoteException {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(severity, "severity");
        if (severity.getLevel() < this.reportSeverity.getLevel()) {
            return;
        }
        CompilerServicesFacadeBaseKt.report$default(this.servicesFacade, ReportCategory.IC_MESSAGE, DebugMessagesICReporterKt.getSeverity(severity), message.invoke(), null, 8, null);
    }
}
