package org.jetbrains.kotlin.daemon.report;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.build.report.ICReporter;
import org.jetbrains.kotlin.build.report.RemoteBuildReporter;
import org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporterImpl;
import org.jetbrains.kotlin.build.report.metrics.DoNothingBuildMetricsReporter;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildPerformanceMetric;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildTime;
import org.jetbrains.kotlin.daemon.common.CompilationResultCategory;
import org.jetbrains.kotlin.daemon.common.CompilationResults;
import org.jetbrains.kotlin.daemon.common.CompilerServicesFacadeBase;
import org.jetbrains.kotlin.daemon.common.IncrementalCompilationOptions;
import org.jetbrains.kotlin.daemon.common.ReportCategory;
import org.jetbrains.kotlin.daemon.common.ReportSeverity;

/* JADX INFO: compiled from: getICReporter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a*\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t\u001a\u001c\u0010\n\u001a\u00020\u000b*\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0000¨\u0006\u0010"}, d2 = {"getBuildReporter", "Lorg/jetbrains/kotlin/build/report/RemoteBuildReporter;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildTime;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildPerformanceMetric;", "servicesFacade", "Lorg/jetbrains/kotlin/daemon/common/CompilerServicesFacadeBase;", "compilationResults", "Lorg/jetbrains/kotlin/daemon/common/CompilationResults;", "compilationOptions", "Lorg/jetbrains/kotlin/daemon/common/IncrementalCompilationOptions;", "getSeverity", "Lorg/jetbrains/kotlin/build/report/ICReporter$ReportSeverity;", "Lorg/jetbrains/kotlin/daemon/common/ReportSeverity;", "mapErrorToWarning", "", "mapInfoToWarning", "kotlin-daemon"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class GetICReporterKt {

    /* JADX INFO: compiled from: getICReporter.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[CompilationResultCategory.values().length];
            try {
                iArr[CompilationResultCategory.IC_COMPILE_ITERATION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CompilationResultCategory.BUILD_REPORT_LINES.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[CompilationResultCategory.VERBOSE_BUILD_REPORT_LINES.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[CompilationResultCategory.BUILD_METRICS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ReportSeverity.values().length];
            try {
                iArr2[ReportSeverity.ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[ReportSeverity.WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[ReportSeverity.INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[ReportSeverity.DEBUG.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public static final RemoteBuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> getBuildReporter(CompilerServicesFacadeBase servicesFacade, CompilationResults compilationResults, IncrementalCompilationOptions compilationOptions) {
        Intrinsics.checkNotNullParameter(servicesFacade, "servicesFacade");
        Intrinsics.checkNotNullParameter(compilationResults, "compilationResults");
        Intrinsics.checkNotNullParameter(compilationOptions, "compilationOptions");
        File rootProjectDir = compilationOptions.getRootProjectDir();
        ArrayList arrayList = new ArrayList();
        if (ArraysKt.contains(compilationOptions.getReportCategories(), Integer.valueOf(ReportCategory.IC_MESSAGE.getCode()))) {
            arrayList.add(new DebugMessagesICReporter(servicesFacade, rootProjectDir, getSeverity(ReportSeverity.INSTANCE.fromCode(compilationOptions.getReportSeverity()), true, true)));
        }
        Integer[] requestedCompilationResults = compilationOptions.getRequestedCompilationResults();
        HashSet hashSet = new HashSet();
        for (Integer num : requestedCompilationResults) {
            CompilationResultCategory compilationResultCategory = (CompilationResultCategory) ArraysKt.getOrNull(CompilationResultCategory.values(), num.intValue());
            if (compilationResultCategory != null) {
                hashSet.add(compilationResultCategory);
            }
        }
        HashSet hashSet2 = hashSet;
        Iterator it = hashSet2.iterator();
        while (it.hasNext()) {
            int i = WhenMappings.$EnumSwitchMapping$0[((CompilationResultCategory) it.next()).ordinal()];
            if (i == 1) {
                arrayList.add(new CompileIterationICReporter(compilationResults));
            } else if (i == 2) {
                arrayList.add(new BuildReportICReporter(compilationResults, rootProjectDir, false, 4, null));
            } else if (i == 3) {
                arrayList.add(new BuildReportICReporter(compilationResults, rootProjectDir, true));
            }
        }
        boolean zContains = hashSet2.contains(CompilationResultCategory.BUILD_METRICS);
        return new RemoteBuildReporter<>(new CompositeICReporter(arrayList), new RemoteBuildMetricsReporterAdapter(zContains ? new BuildMetricsReporterImpl() : DoNothingBuildMetricsReporter.INSTANCE, zContains, compilationResults));
    }

    public static final ICReporter.ReportSeverity getSeverity(ReportSeverity reportSeverity, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(reportSeverity, "<this>");
        int i = WhenMappings.$EnumSwitchMapping$1[reportSeverity.ordinal()];
        if (i == 1) {
            if (z) {
                return ICReporter.ReportSeverity.WARNING;
            }
            throw new IllegalArgumentException("No mapping exists for `ReportSeverity.ERROR`. Add `ICReporter.ReportSeverity.ERROR` and remove mapErrorToWarning, or set mapErrorToWarning = true.");
        }
        if (i == 2) {
            return ICReporter.ReportSeverity.WARNING;
        }
        if (i != 3) {
            if (i == 4) {
                return ICReporter.ReportSeverity.DEBUG;
            }
            throw new NoWhenBranchMatchedException();
        }
        if (z2) {
            return ICReporter.ReportSeverity.WARNING;
        }
        return ICReporter.ReportSeverity.INFO;
    }
}
