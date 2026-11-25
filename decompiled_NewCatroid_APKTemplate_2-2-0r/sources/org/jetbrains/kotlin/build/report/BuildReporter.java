package org.jetbrains.kotlin.build.report;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.build.report.metrics.BuildAttribute;
import org.jetbrains.kotlin.build.report.metrics.BuildMetrics;
import org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter;
import org.jetbrains.kotlin.build.report.metrics.BuildPerformanceMetric;
import org.jetbrains.kotlin.build.report.metrics.BuildTime;
import org.jetbrains.kotlin.build.report.metrics.GcMetric;
import org.jetbrains.kotlin.cli.common.ExitCode;

/* JADX INFO: compiled from: BuildReporter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\b\b\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\u00020\u00052\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u0006B!\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006¢\u0006\u0002\u0010\tJ\u0011\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0096\u0001J\u0019\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0096\u0001J\u001e\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00028\u00012\u0006\u0010\u0015\u001a\u00020\u0018H\u0096\u0001¢\u0006\u0002\u0010\u0019J\u001d\u0010\u001a\u001a\u00020\u000f2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u001cH\u0096\u0001J\u0016\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00028\u0001H\u0096\u0001¢\u0006\u0002\u0010\u001eJ\u001e\u0010\u001f\u001a\u00020\u000f2\u0006\u0010 \u001a\u00028\u00002\u0006\u0010!\u001a\u00020\u0018H\u0096\u0001¢\u0006\u0002\u0010\"J\u001e\u0010#\u001a\u00020\u000f2\u0006\u0010 \u001a\u00028\u00002\u0006\u0010$\u001a\u00020\u0018H\u0096\u0001¢\u0006\u0002\u0010\"J\u0019\u0010%\u001a\u00020\u000f2\u0006\u0010&\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0096\u0001J\u0016\u0010'\u001a\u00020\u000f2\u0006\u0010 \u001a\u00028\u0000H\u0096\u0001¢\u0006\u0002\u0010(J\u0015\u0010)\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u001cH\u0096\u0001J\u001f\u0010*\u001a\u00020\u000f2\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00140,2\u0006\u0010-\u001a\u00020.H\u0096\u0001J'\u0010/\u001a\u00020\u000f2\u0006\u00100\u001a\u0002012\f\u00102\u001a\b\u0012\u0004\u0012\u000204032\u0006\u00105\u001a\u000206H\u0096\u0001J\u001f\u00107\u001a\u00020\u000f2\f\u00108\u001a\b\u0012\u0004\u0012\u000204092\u0006\u0010:\u001a\u00020\u0014H\u0096\u0001J\u001f\u0010;\u001a\u00020\u000f2\f\u00108\u001a\b\u0012\u0004\u0012\u000204092\u0006\u0010<\u001a\u00020\u0014H\u0096\u0001J'\u0010=\u001a\u00020\u000f2\f\u00108\u001a\b\u0012\u0004\u0012\u000204092\u0006\u0010>\u001a\u00020\u00142\u0006\u0010&\u001a\u00020\u0014H\u0096\u0001J\u0019\u0010?\u001a\u00020\u000f2\u0006\u0010&\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0096\u0001J\u0016\u0010@\u001a\u00020\u000f2\u0006\u0010 \u001a\u00028\u0000H\u0096\u0001¢\u0006\u0002\u0010(R \u0010\b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0007\u001a\u00020\u0005X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006A"}, d2 = {"Lorg/jetbrains/kotlin/build/report/BuildReporter;", "B", "Lorg/jetbrains/kotlin/build/report/metrics/BuildTime;", "P", "Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetric;", "Lorg/jetbrains/kotlin/build/report/ICReporter;", "Lorg/jetbrains/kotlin/build/report/metrics/BuildMetricsReporter;", "icReporter", "buildMetricsReporter", "(Lorg/jetbrains/kotlin/build/report/ICReporter;Lorg/jetbrains/kotlin/build/report/metrics/BuildMetricsReporter;)V", "getBuildMetricsReporter", "()Lorg/jetbrains/kotlin/build/report/metrics/BuildMetricsReporter;", "getIcReporter", "()Lorg/jetbrains/kotlin/build/report/ICReporter;", "addAttribute", "", "attribute", "Lorg/jetbrains/kotlin/build/report/metrics/BuildAttribute;", "addGcMetric", "metric", "", "value", "Lorg/jetbrains/kotlin/build/report/metrics/GcMetric;", "addMetric", "", "(Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetric;J)V", "addMetrics", "metrics", "Lorg/jetbrains/kotlin/build/report/metrics/BuildMetrics;", "addTimeMetric", "(Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetric;)V", "addTimeMetricMs", "time", "durationMs", "(Lorg/jetbrains/kotlin/build/report/metrics/BuildTime;J)V", "addTimeMetricNs", "durationNs", "endGcMetric", "name", "endMeasure", "(Lorg/jetbrains/kotlin/build/report/metrics/BuildTime;)V", "getMetrics", "report", CrashHianalyticsData.MESSAGE, "Lkotlin/Function0;", "severity", "Lorg/jetbrains/kotlin/build/report/ICReporter$ReportSeverity;", "reportCompileIteration", "incremental", "", "sourceFiles", "", "Ljava/io/File;", "exitCode", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", "reportMarkDirty", "affectedFiles", "", "reason", "reportMarkDirtyClass", "classFqName", "reportMarkDirtyMember", "scope", "startGcMetric", "startMeasure", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class BuildReporter<B extends BuildTime, P extends BuildPerformanceMetric> implements ICReporter, BuildMetricsReporter<B, P> {
    private final BuildMetricsReporter<B, P> buildMetricsReporter;
    private final ICReporter icReporter;

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addAttribute(BuildAttribute attribute) {
        Intrinsics.checkNotNullParameter(attribute, "attribute");
        this.buildMetricsReporter.addAttribute(attribute);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addGcMetric(String metric, GcMetric value) {
        Intrinsics.checkNotNullParameter(metric, "metric");
        Intrinsics.checkNotNullParameter(value, "value");
        this.buildMetricsReporter.addGcMetric(metric, value);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addMetric(P metric, long value) {
        Intrinsics.checkNotNullParameter(metric, "metric");
        this.buildMetricsReporter.addMetric(metric, value);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addMetrics(BuildMetrics<B, P> metrics) {
        Intrinsics.checkNotNullParameter(metrics, "metrics");
        this.buildMetricsReporter.addMetrics(metrics);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addTimeMetric(P metric) {
        Intrinsics.checkNotNullParameter(metric, "metric");
        this.buildMetricsReporter.addTimeMetric(metric);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addTimeMetricMs(B time, long durationMs) {
        Intrinsics.checkNotNullParameter(time, "time");
        this.buildMetricsReporter.addTimeMetricMs(time, durationMs);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addTimeMetricNs(B time, long durationNs) {
        Intrinsics.checkNotNullParameter(time, "time");
        this.buildMetricsReporter.addTimeMetricNs(time, durationNs);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void endGcMetric(String name2, GcMetric value) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        this.buildMetricsReporter.endGcMetric(name2, value);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void endMeasure(B time) {
        Intrinsics.checkNotNullParameter(time, "time");
        this.buildMetricsReporter.endMeasure(time);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public BuildMetrics<B, P> getMetrics() {
        return this.buildMetricsReporter.getMetrics();
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void report(Function0<String> message, ICReporter.ReportSeverity severity) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(severity, "severity");
        this.icReporter.report(message, severity);
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void reportCompileIteration(boolean incremental, Collection<? extends File> sourceFiles, ExitCode exitCode) {
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        Intrinsics.checkNotNullParameter(exitCode, "exitCode");
        this.icReporter.reportCompileIteration(incremental, sourceFiles, exitCode);
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void reportMarkDirty(Iterable<? extends File> affectedFiles, String reason) {
        Intrinsics.checkNotNullParameter(affectedFiles, "affectedFiles");
        Intrinsics.checkNotNullParameter(reason, "reason");
        this.icReporter.reportMarkDirty(affectedFiles, reason);
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void reportMarkDirtyClass(Iterable<? extends File> affectedFiles, String classFqName) {
        Intrinsics.checkNotNullParameter(affectedFiles, "affectedFiles");
        Intrinsics.checkNotNullParameter(classFqName, "classFqName");
        this.icReporter.reportMarkDirtyClass(affectedFiles, classFqName);
    }

    @Override // org.jetbrains.kotlin.build.report.ICReporter
    public void reportMarkDirtyMember(Iterable<? extends File> affectedFiles, String scope, String name2) {
        Intrinsics.checkNotNullParameter(affectedFiles, "affectedFiles");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(name2, "name");
        this.icReporter.reportMarkDirtyMember(affectedFiles, scope, name2);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void startGcMetric(String name2, GcMetric value) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        this.buildMetricsReporter.startGcMetric(name2, value);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void startMeasure(B time) {
        Intrinsics.checkNotNullParameter(time, "time");
        this.buildMetricsReporter.startMeasure(time);
    }

    public BuildReporter(ICReporter icReporter, BuildMetricsReporter<B, P> buildMetricsReporter) {
        Intrinsics.checkNotNullParameter(icReporter, "icReporter");
        Intrinsics.checkNotNullParameter(buildMetricsReporter, "buildMetricsReporter");
        this.icReporter = icReporter;
        this.buildMetricsReporter = buildMetricsReporter;
    }

    protected ICReporter getIcReporter() {
        return this.icReporter;
    }

    protected BuildMetricsReporter<B, P> getBuildMetricsReporter() {
        return this.buildMetricsReporter;
    }
}
