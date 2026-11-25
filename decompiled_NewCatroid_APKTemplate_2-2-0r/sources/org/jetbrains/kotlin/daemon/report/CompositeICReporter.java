package org.jetbrains.kotlin.daemon.report;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.build.report.ICReporter;
import org.jetbrains.kotlin.build.report.RemoteICReporter;
import org.jetbrains.kotlin.cli.common.ExitCode;

/* JADX INFO: compiled from: CompositeICReporter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u001e\u0010\u0007\u001a\u00020\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\fH\u0016J&\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u001e\u0010\u0015\u001a\u00020\u00062\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00120\u00032\u0006\u0010\u0017\u001a\u00020\nH\u0016J\u001e\u0010\u0018\u001a\u00020\u00062\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00120\u00032\u0006\u0010\u0019\u001a\u00020\nH\u0016J&\u0010\u001a\u001a\u00020\u00062\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00120\u00032\u0006\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\nH\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/daemon/report/CompositeICReporter;", "Lorg/jetbrains/kotlin/build/report/RemoteICReporter;", "reporters", "", "(Ljava/lang/Iterable;)V", "flush", "", "report", CrashHianalyticsData.MESSAGE, "Lkotlin/Function0;", "", "severity", "Lorg/jetbrains/kotlin/build/report/ICReporter$ReportSeverity;", "reportCompileIteration", "incremental", "", "sourceFiles", "", "Ljava/io/File;", "exitCode", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", "reportMarkDirty", "affectedFiles", "reason", "reportMarkDirtyClass", "classFqName", "reportMarkDirtyMember", "scope", "name", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompositeICReporter implements RemoteICReporter {
    private final Iterable<RemoteICReporter> reporters;

    /* JADX WARN: Multi-variable type inference failed */
    public CompositeICReporter(Iterable<? extends RemoteICReporter> reporters) {
        Intrinsics.checkNotNullParameter(reporters, "reporters");
        this.reporters = reporters;
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void report(Function0<String> message, ICReporter.ReportSeverity severity) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(severity, "severity");
        Iterator<RemoteICReporter> it = this.reporters.iterator();
        while (it.hasNext()) {
            it.next().report(message, severity);
        }
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void reportCompileIteration(boolean incremental, Collection<? extends File> sourceFiles, ExitCode exitCode) {
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        Intrinsics.checkNotNullParameter(exitCode, "exitCode");
        Iterator<RemoteICReporter> it = this.reporters.iterator();
        while (it.hasNext()) {
            it.next().reportCompileIteration(incremental, sourceFiles, exitCode);
        }
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void reportMarkDirtyClass(Iterable<? extends File> affectedFiles, String classFqName) {
        Intrinsics.checkNotNullParameter(affectedFiles, "affectedFiles");
        Intrinsics.checkNotNullParameter(classFqName, "classFqName");
        Iterator<RemoteICReporter> it = this.reporters.iterator();
        while (it.hasNext()) {
            it.next().reportMarkDirtyClass(affectedFiles, classFqName);
        }
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void reportMarkDirtyMember(Iterable<? extends File> affectedFiles, String scope, String name2) {
        Intrinsics.checkNotNullParameter(affectedFiles, "affectedFiles");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(name2, "name");
        Iterator<RemoteICReporter> it = this.reporters.iterator();
        while (it.hasNext()) {
            it.next().reportMarkDirtyMember(affectedFiles, scope, name2);
        }
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void reportMarkDirty(Iterable<? extends File> affectedFiles, String reason) {
        Intrinsics.checkNotNullParameter(affectedFiles, "affectedFiles");
        Intrinsics.checkNotNullParameter(reason, "reason");
        Iterator<RemoteICReporter> it = this.reporters.iterator();
        while (it.hasNext()) {
            it.next().reportMarkDirty(affectedFiles, reason);
        }
    }

    @Override // org.jetbrains.kotlin.build.report.RemoteReporter
    public void flush() {
        Iterator<RemoteICReporter> it = this.reporters.iterator();
        while (it.hasNext()) {
            it.next().flush();
        }
    }
}
