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
import org.jetbrains.kotlin.daemon.common.CompilationResultCategory;
import org.jetbrains.kotlin.daemon.common.CompilationResults;
import org.jetbrains.kotlin.daemon.common.CompileIterationResult;

/* JADX INFO: compiled from: CompileIterationICReporter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u001e\u0010\b\u001a\u00020\u00072\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\rH\u0016J&\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/daemon/report/CompileIterationICReporter;", "Lorg/jetbrains/kotlin/build/report/ICReporterBase;", "Lorg/jetbrains/kotlin/build/report/RemoteICReporter;", "compilationResults", "Lorg/jetbrains/kotlin/daemon/common/CompilationResults;", "(Lorg/jetbrains/kotlin/daemon/common/CompilationResults;)V", "flush", "", "report", CrashHianalyticsData.MESSAGE, "Lkotlin/Function0;", "", "severity", "Lorg/jetbrains/kotlin/build/report/ICReporter$ReportSeverity;", "reportCompileIteration", "incremental", "", "sourceFiles", "", "Ljava/io/File;", "exitCode", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompileIterationICReporter extends ICReporterBase implements RemoteICReporter {
    private final CompilationResults compilationResults;

    @Override // org.jetbrains.kotlin.build.report.RemoteReporter
    public void flush() {
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void report(Function0<String> message, ICReporter.ReportSeverity severity) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(severity, "severity");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CompileIterationICReporter(CompilationResults compilationResults) {
        super(null, 1, null);
        Intrinsics.checkNotNullParameter(compilationResults, "compilationResults");
        this.compilationResults = compilationResults;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void reportCompileIteration(boolean incremental, Collection<? extends File> sourceFiles, ExitCode exitCode) throws RemoteException {
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        Intrinsics.checkNotNullParameter(exitCode, "exitCode");
        this.compilationResults.add(CompilationResultCategory.IC_COMPILE_ITERATION.getCode(), new CompileIterationResult(sourceFiles, exitCode.toString()));
    }
}
