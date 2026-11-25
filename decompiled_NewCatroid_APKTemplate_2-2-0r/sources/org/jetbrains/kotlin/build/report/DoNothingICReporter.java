package org.jetbrains.kotlin.build.report;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.ExitCode;

/* JADX INFO: compiled from: ICReporter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u0016J&\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u001e\u0010\u0012\u001a\u00020\u00042\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00142\u0006\u0010\u0015\u001a\u00020\u0007H\u0016J\u001e\u0010\u0016\u001a\u00020\u00042\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00142\u0006\u0010\u0017\u001a\u00020\u0007H\u0016J&\u0010\u0018\u001a\u00020\u00042\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00142\u0006\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u0007H\u0016¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/build/report/DoNothingICReporter;", "Lorg/jetbrains/kotlin/build/report/ICReporter;", "()V", "report", "", CrashHianalyticsData.MESSAGE, "Lkotlin/Function0;", "", "severity", "Lorg/jetbrains/kotlin/build/report/ICReporter$ReportSeverity;", "reportCompileIteration", "incremental", "", "sourceFiles", "", "Ljava/io/File;", "exitCode", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", "reportMarkDirty", "affectedFiles", "", "reason", "reportMarkDirtyClass", "classFqName", "reportMarkDirtyMember", "scope", "name", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DoNothingICReporter implements ICReporter {
    public static final DoNothingICReporter INSTANCE = new DoNothingICReporter();

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void report(Function0<String> message, ICReporter.ReportSeverity severity) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(severity, "severity");
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void reportCompileIteration(boolean incremental, Collection<? extends File> sourceFiles, ExitCode exitCode) {
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        Intrinsics.checkNotNullParameter(exitCode, "exitCode");
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void reportMarkDirty(Iterable<? extends File> affectedFiles, String reason) {
        Intrinsics.checkNotNullParameter(affectedFiles, "affectedFiles");
        Intrinsics.checkNotNullParameter(reason, "reason");
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void reportMarkDirtyClass(Iterable<? extends File> affectedFiles, String classFqName) {
        Intrinsics.checkNotNullParameter(affectedFiles, "affectedFiles");
        Intrinsics.checkNotNullParameter(classFqName, "classFqName");
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void reportMarkDirtyMember(Iterable<? extends File> affectedFiles, String scope, String name2) {
        Intrinsics.checkNotNullParameter(affectedFiles, "affectedFiles");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    private DoNothingICReporter() {
    }
}
