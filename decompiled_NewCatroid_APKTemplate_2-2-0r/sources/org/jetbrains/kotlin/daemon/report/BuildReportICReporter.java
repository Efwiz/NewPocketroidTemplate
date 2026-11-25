package org.jetbrains.kotlin.daemon.report;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.build.report.ICReporter;
import org.jetbrains.kotlin.build.report.ICReporterBase;
import org.jetbrains.kotlin.build.report.RemoteICReporter;
import org.jetbrains.kotlin.cli.common.ExitCode;
import org.jetbrains.kotlin.com.google.common.annotations.VisibleForTesting;
import org.jetbrains.kotlin.daemon.common.CompilationResultCategory;
import org.jetbrains.kotlin.daemon.common.CompilationResults;

/* JADX INFO: compiled from: BuildReportICReporter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B!\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u001e\u0010\u0013\u001a\u00020\u00122\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\f0\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J&\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\b2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00060\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u001e\u0010\u001e\u001a\u00020\u00122\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00060 2\u0006\u0010!\u001a\u00020\fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\f0\u000bj\b\u0012\u0004\u0012\u00020\f`\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u000e\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\f0\u000fj\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\f`\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/daemon/report/BuildReportICReporter;", "Lorg/jetbrains/kotlin/build/report/ICReporterBase;", "Lorg/jetbrains/kotlin/build/report/RemoteICReporter;", "compilationResults", "Lorg/jetbrains/kotlin/daemon/common/CompilationResults;", "rootDir", "Ljava/io/File;", "isVerbose", "", "(Lorg/jetbrains/kotlin/daemon/common/CompilationResults;Ljava/io/File;Z)V", "icLogLines", "Ljava/util/ArrayList;", "", "Lkotlin/collections/ArrayList;", "recompilationReason", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "flush", "", "report", CrashHianalyticsData.MESSAGE, "Lkotlin/Function0;", "severity", "Lorg/jetbrains/kotlin/build/report/ICReporter$ReportSeverity;", "reportCompileIteration", "incremental", "sourceFiles", "", "exitCode", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", "reportMarkDirty", "affectedFiles", "", "reason", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
@VisibleForTesting
public final class BuildReportICReporter extends ICReporterBase implements RemoteICReporter {
    private final CompilationResults compilationResults;
    private final ArrayList<String> icLogLines;
    private final boolean isVerbose;
    private final HashMap<File, String> recompilationReason;

    public /* synthetic */ BuildReportICReporter(CompilationResults compilationResults, File file, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(compilationResults, file, (i & 4) != 0 ? false : z);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BuildReportICReporter(CompilationResults compilationResults, File file, boolean z) {
        super(file);
        Intrinsics.checkNotNullParameter(compilationResults, "compilationResults");
        this.compilationResults = compilationResults;
        this.isVerbose = z;
        this.icLogLines = new ArrayList<>();
        this.recompilationReason = new HashMap<>();
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void report(Function0<String> message, ICReporter.ReportSeverity severity) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(severity, "severity");
        if (severity != ICReporter.ReportSeverity.DEBUG || this.isVerbose) {
            this.icLogLines.add(message.invoke());
        }
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void reportCompileIteration(boolean incremental, Collection<? extends File> sourceFiles, ExitCode exitCode) {
        String str;
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        Intrinsics.checkNotNullParameter(exitCode, "exitCode");
        if (incremental) {
            this.icLogLines.add("Compile iteration:");
            for (File file : sourceFiles) {
                String str2 = this.recompilationReason.get(file);
                if (str2 == null || (str = " <- " + str2) == null) {
                    str = "";
                }
                this.icLogLines.add("  " + relativeOrAbsolute(file) + str);
            }
            this.recompilationReason.clear();
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // org.jetbrains.kotlin.build.report.RemoteReporter
    public void flush() throws RemoteException {
        this.compilationResults.add(CompilationResultCategory.BUILD_REPORT_LINES.getCode(), this.icLogLines);
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporterBase, org.jetbrains.kotlin.build.report.ICReporter
    public void reportMarkDirty(Iterable<? extends File> affectedFiles, String reason) {
        Intrinsics.checkNotNullParameter(affectedFiles, "affectedFiles");
        Intrinsics.checkNotNullParameter(reason, "reason");
        Iterator<? extends File> it = affectedFiles.iterator();
        while (it.hasNext()) {
            this.recompilationReason.put(it.next(), reason);
        }
    }
}
