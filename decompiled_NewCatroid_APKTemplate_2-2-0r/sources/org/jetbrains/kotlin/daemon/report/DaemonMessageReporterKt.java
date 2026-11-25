package org.jetbrains.kotlin.daemon.report;

import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.daemon.common.CompilationOptions;
import org.jetbrains.kotlin.daemon.common.CompilerServicesFacadeBase;
import org.jetbrains.kotlin.daemon.common.ReportCategory;
import org.jetbrains.kotlin.daemon.common.ReportSeverity;

/* JADX INFO: compiled from: DaemonMessageReporter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u0006"}, d2 = {"DaemonMessageReporter", "Lorg/jetbrains/kotlin/daemon/report/DaemonMessageReporter;", "servicesFacade", "Lorg/jetbrains/kotlin/daemon/common/CompilerServicesFacadeBase;", "compilationOptions", "Lorg/jetbrains/kotlin/daemon/common/CompilationOptions;", "kotlin-daemon"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DaemonMessageReporterKt {
    public static final DaemonMessageReporter DaemonMessageReporter(CompilerServicesFacadeBase servicesFacade, CompilationOptions compilationOptions) {
        Intrinsics.checkNotNullParameter(servicesFacade, "servicesFacade");
        Intrinsics.checkNotNullParameter(compilationOptions, "compilationOptions");
        if (ArraysKt.contains(compilationOptions.getReportCategories(), Integer.valueOf(ReportCategory.DAEMON_MESSAGE.getCode()))) {
            return new DaemonMessageReporterImpl(servicesFacade, ReportSeverity.INSTANCE.fromCode(compilationOptions.getReportSeverity()));
        }
        return DummyDaemonMessageReporter.INSTANCE;
    }
}
