package org.jetbrains.kotlin.incremental.classpathDiff;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.stage.StageActivity;
import org.jetbrains.kotlin.build.report.BuildReporter;
import org.jetbrains.kotlin.build.report.ICReporter;
import org.jetbrains.kotlin.build.report.ICReporterKt;
import org.jetbrains.kotlin.build.report.metrics.BuildAttribute;
import org.jetbrains.kotlin.build.report.metrics.BuildMetrics;
import org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter;
import org.jetbrains.kotlin.build.report.metrics.GcMetric;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildPerformanceMetric;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildTime;
import org.jetbrains.kotlin.cli.common.ExitCode;

/* JADX INFO: compiled from: ClasspathSnapshotBuildReporter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002B\u0019\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0006¢\u0006\u0002\u0010\u0007J\u0011\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0096\u0001J\u0019\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0096\u0001J\u0019\u0010\u0011\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0012H\u0096\u0001J\u001d\u0010\u0013\u001a\u00020\t2\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0015H\u0096\u0001J\u0011\u0010\u0016\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u0004H\u0096\u0001J\u0019\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u0012H\u0096\u0001J\u0019\u0010\u001a\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u0012H\u0096\u0001J\u0019\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0096\u0001J\u0011\u0010\u001e\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u0003H\u0096\u0001J\u0015\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0015H\u0096\u0001J\u001e\u0010 \u001a\u00020\t2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000e0\"2\u0006\u0010#\u001a\u00020$H\u0016J'\u0010%\u001a\u00020\t2\u0006\u0010&\u001a\u00020'2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020*0)2\u0006\u0010+\u001a\u00020,H\u0096\u0001J\u001f\u0010-\u001a\u00020\t2\f\u0010.\u001a\b\u0012\u0004\u0012\u00020*0/2\u0006\u00100\u001a\u00020\u000eH\u0096\u0001J\u001f\u00101\u001a\u00020\t2\f\u0010.\u001a\b\u0012\u0004\u0012\u00020*0/2\u0006\u00102\u001a\u00020\u000eH\u0096\u0001J'\u00103\u001a\u00020\t2\f\u0010.\u001a\b\u0012\u0004\u0012\u00020*0/2\u0006\u00104\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u000eH\u0096\u0001J\u001e\u00105\u001a\u00020\t2\b\b\u0002\u00106\u001a\u0002072\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000e0\"J\u0019\u00108\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0096\u0001J\u0011\u00109\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u0003H\u0096\u0001R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006:"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ClasspathSnapshotBuildReporter;", "Lorg/jetbrains/kotlin/build/report/ICReporter;", "Lorg/jetbrains/kotlin/build/report/metrics/BuildMetricsReporter;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildTime;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildPerformanceMetric;", "buildReporter", "Lorg/jetbrains/kotlin/build/report/BuildReporter;", "(Lorg/jetbrains/kotlin/build/report/BuildReporter;)V", "addAttribute", "", "attribute", "Lorg/jetbrains/kotlin/build/report/metrics/BuildAttribute;", "addGcMetric", "metric", "", "value", "Lorg/jetbrains/kotlin/build/report/metrics/GcMetric;", "addMetric", "", "addMetrics", "metrics", "Lorg/jetbrains/kotlin/build/report/metrics/BuildMetrics;", "addTimeMetric", "addTimeMetricMs", "time", "durationMs", "addTimeMetricNs", "durationNs", "endGcMetric", "name", "endMeasure", "getMetrics", "report", CrashHianalyticsData.MESSAGE, "Lkotlin/Function0;", "severity", "Lorg/jetbrains/kotlin/build/report/ICReporter$ReportSeverity;", "reportCompileIteration", "incremental", "", "sourceFiles", "", "Ljava/io/File;", "exitCode", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", "reportMarkDirty", "affectedFiles", "", "reason", "reportMarkDirtyClass", "classFqName", "reportMarkDirtyMember", "scope", "reportVerboseWithLimit", StageActivity.STYLE_MAX_LENGTH, "", "startGcMetric", "startMeasure", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClasspathSnapshotBuildReporter implements ICReporter, BuildMetricsReporter<GradleBuildTime, GradleBuildPerformanceMetric> {
    private final BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildReporter;

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addAttribute(BuildAttribute attribute) {
        Intrinsics.checkNotNullParameter(attribute, "attribute");
        this.buildReporter.addAttribute(attribute);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addGcMetric(String metric, GcMetric value) {
        Intrinsics.checkNotNullParameter(metric, "metric");
        Intrinsics.checkNotNullParameter(value, "value");
        this.buildReporter.addGcMetric(metric, value);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addMetric(GradleBuildPerformanceMetric metric, long value) {
        Intrinsics.checkNotNullParameter(metric, "metric");
        this.buildReporter.addMetric(metric, value);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addMetrics(BuildMetrics<GradleBuildTime, GradleBuildPerformanceMetric> metrics) {
        Intrinsics.checkNotNullParameter(metrics, "metrics");
        this.buildReporter.addMetrics(metrics);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addTimeMetric(GradleBuildPerformanceMetric metric) {
        Intrinsics.checkNotNullParameter(metric, "metric");
        this.buildReporter.addTimeMetric(metric);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addTimeMetricMs(GradleBuildTime time, long durationMs) {
        Intrinsics.checkNotNullParameter(time, "time");
        this.buildReporter.addTimeMetricMs(time, durationMs);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addTimeMetricNs(GradleBuildTime time, long durationNs) {
        Intrinsics.checkNotNullParameter(time, "time");
        this.buildReporter.addTimeMetricNs(time, durationNs);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void endGcMetric(String name2, GcMetric value) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        this.buildReporter.endGcMetric(name2, value);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void endMeasure(GradleBuildTime time) {
        Intrinsics.checkNotNullParameter(time, "time");
        this.buildReporter.endMeasure(time);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public BuildMetrics<GradleBuildTime, GradleBuildPerformanceMetric> getMetrics() {
        return this.buildReporter.getMetrics();
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void reportCompileIteration(boolean incremental, Collection<? extends File> sourceFiles, ExitCode exitCode) {
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        Intrinsics.checkNotNullParameter(exitCode, "exitCode");
        this.buildReporter.reportCompileIteration(incremental, sourceFiles, exitCode);
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void reportMarkDirty(Iterable<? extends File> affectedFiles, String reason) {
        Intrinsics.checkNotNullParameter(affectedFiles, "affectedFiles");
        Intrinsics.checkNotNullParameter(reason, "reason");
        this.buildReporter.reportMarkDirty(affectedFiles, reason);
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void reportMarkDirtyClass(Iterable<? extends File> affectedFiles, String classFqName) {
        Intrinsics.checkNotNullParameter(affectedFiles, "affectedFiles");
        Intrinsics.checkNotNullParameter(classFqName, "classFqName");
        this.buildReporter.reportMarkDirtyClass(affectedFiles, classFqName);
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void reportMarkDirtyMember(Iterable<? extends File> affectedFiles, String scope, String name2) {
        Intrinsics.checkNotNullParameter(affectedFiles, "affectedFiles");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(name2, "name");
        this.buildReporter.reportMarkDirtyMember(affectedFiles, scope, name2);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void startGcMetric(String name2, GcMetric value) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        this.buildReporter.startGcMetric(name2, value);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void startMeasure(GradleBuildTime time) {
        Intrinsics.checkNotNullParameter(time, "time");
        this.buildReporter.startMeasure(time);
    }

    public ClasspathSnapshotBuildReporter(BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> buildReporter) {
        Intrinsics.checkNotNullParameter(buildReporter, "buildReporter");
        this.buildReporter = buildReporter;
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void report(final Function0<String> message, ICReporter.ReportSeverity severity) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(severity, "severity");
        this.buildReporter.report(new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ClasspathSnapshotBuildReporter.report.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                return "[ClasspathSnapshot] " + message.invoke();
            }
        }, severity);
    }

    public static /* synthetic */ void reportVerboseWithLimit$default(ClasspathSnapshotBuildReporter classpathSnapshotBuildReporter, int i, Function0 function0, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 1000;
        }
        classpathSnapshotBuildReporter.reportVerboseWithLimit(i, function0);
    }

    public final void reportVerboseWithLimit(final int maxLength, final Function0<String> message) {
        Intrinsics.checkNotNullParameter(message, "message");
        ICReporterKt.debug(this, new Function0<String>() { // from class: org.jetbrains.kotlin.incremental.classpathDiff.ClasspathSnapshotBuildReporter.reportVerboseWithLimit.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                String strInvoke = message.invoke();
                int i = maxLength;
                String str = strInvoke;
                if (str.length() <= i) {
                    return str;
                }
                StringBuilder sb = new StringBuilder();
                String strSubstring = str.substring(0, i);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                return sb.append(strSubstring).append("... (string too long, showing ").append(i).append(" / ").append(str.length()).append(" chars)").toString();
            }
        });
    }
}
