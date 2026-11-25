package org.jetbrains.kotlin.daemon.report;

import java.rmi.RemoteException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.build.report.metrics.BuildAttribute;
import org.jetbrains.kotlin.build.report.metrics.BuildMetrics;
import org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter;
import org.jetbrains.kotlin.build.report.metrics.GcMetric;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildPerformanceMetric;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildTime;
import org.jetbrains.kotlin.build.report.metrics.RemoteBuildMetricsReporter;
import org.jetbrains.kotlin.daemon.common.CompilationResultCategory;
import org.jetbrains.kotlin.daemon.common.CompilationResults;

/* JADX INFO: compiled from: RemoteBuildMetricsReporterAdapter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0004B)\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0096\u0001J\u0019\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0096\u0001J\u0019\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0015H\u0096\u0001J\u001d\u0010\u0016\u001a\u00020\f2\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0018H\u0096\u0001J\u0011\u0010\u0019\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0003H\u0096\u0001J\u0019\u0010\u001a\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u00022\u0006\u0010\u001c\u001a\u00020\u0015H\u0096\u0001J\u0019\u0010\u001d\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u0015H\u0096\u0001J\u0019\u0010\u001f\u001a\u00020\f2\u0006\u0010 \u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0096\u0001J\u0011\u0010!\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u0002H\u0096\u0001J\b\u0010\"\u001a\u00020\fH\u0016J\u0015\u0010#\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0018H\u0096\u0001J\u0019\u0010$\u001a\u00020\f2\u0006\u0010 \u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0096\u0001J\u0011\u0010%\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u0002H\u0096\u0001R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/daemon/report/RemoteBuildMetricsReporterAdapter;", "Lorg/jetbrains/kotlin/build/report/metrics/BuildMetricsReporter;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildTime;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildPerformanceMetric;", "Lorg/jetbrains/kotlin/build/report/metrics/RemoteBuildMetricsReporter;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "shouldReport", "", "compilationResults", "Lorg/jetbrains/kotlin/daemon/common/CompilationResults;", "(Lorg/jetbrains/kotlin/build/report/metrics/BuildMetricsReporter;ZLorg/jetbrains/kotlin/daemon/common/CompilationResults;)V", "addAttribute", "", "attribute", "Lorg/jetbrains/kotlin/build/report/metrics/BuildAttribute;", "addGcMetric", "metric", "", "value", "Lorg/jetbrains/kotlin/build/report/metrics/GcMetric;", "addMetric", "", "addMetrics", "metrics", "Lorg/jetbrains/kotlin/build/report/metrics/BuildMetrics;", "addTimeMetric", "addTimeMetricMs", "time", "durationMs", "addTimeMetricNs", "durationNs", "endGcMetric", "name", "endMeasure", "flush", "getMetrics", "startGcMetric", "startMeasure", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RemoteBuildMetricsReporterAdapter implements BuildMetricsReporter<GradleBuildTime, GradleBuildPerformanceMetric>, RemoteBuildMetricsReporter<GradleBuildTime, GradleBuildPerformanceMetric> {
    private final CompilationResults compilationResults;
    private final BuildMetricsReporter<GradleBuildTime, GradleBuildPerformanceMetric> delegate;
    private final boolean shouldReport;

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addAttribute(BuildAttribute attribute) {
        Intrinsics.checkNotNullParameter(attribute, "attribute");
        this.delegate.addAttribute(attribute);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addGcMetric(String metric, GcMetric value) {
        Intrinsics.checkNotNullParameter(metric, "metric");
        Intrinsics.checkNotNullParameter(value, "value");
        this.delegate.addGcMetric(metric, value);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addMetric(GradleBuildPerformanceMetric metric, long value) {
        Intrinsics.checkNotNullParameter(metric, "metric");
        this.delegate.addMetric(metric, value);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addMetrics(BuildMetrics<GradleBuildTime, GradleBuildPerformanceMetric> metrics) {
        Intrinsics.checkNotNullParameter(metrics, "metrics");
        this.delegate.addMetrics(metrics);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addTimeMetric(GradleBuildPerformanceMetric metric) {
        Intrinsics.checkNotNullParameter(metric, "metric");
        this.delegate.addTimeMetric(metric);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addTimeMetricMs(GradleBuildTime time, long durationMs) {
        Intrinsics.checkNotNullParameter(time, "time");
        this.delegate.addTimeMetricMs(time, durationMs);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addTimeMetricNs(GradleBuildTime time, long durationNs) {
        Intrinsics.checkNotNullParameter(time, "time");
        this.delegate.addTimeMetricNs(time, durationNs);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void endGcMetric(String name2, GcMetric value) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        this.delegate.endGcMetric(name2, value);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void endMeasure(GradleBuildTime time) {
        Intrinsics.checkNotNullParameter(time, "time");
        this.delegate.endMeasure(time);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public BuildMetrics<GradleBuildTime, GradleBuildPerformanceMetric> getMetrics() {
        return this.delegate.getMetrics();
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void startGcMetric(String name2, GcMetric value) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        this.delegate.startGcMetric(name2, value);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void startMeasure(GradleBuildTime time) {
        Intrinsics.checkNotNullParameter(time, "time");
        this.delegate.startMeasure(time);
    }

    public RemoteBuildMetricsReporterAdapter(BuildMetricsReporter<GradleBuildTime, GradleBuildPerformanceMetric> delegate, boolean z, CompilationResults compilationResults) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(compilationResults, "compilationResults");
        this.delegate = delegate;
        this.shouldReport = z;
        this.compilationResults = compilationResults;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.rmi.RemoteException */
    @Override // org.jetbrains.kotlin.build.report.RemoteReporter
    public void flush() throws RemoteException {
        if (this.shouldReport) {
            this.compilationResults.add(CompilationResultCategory.BUILD_METRICS.getCode(), this.delegate.getMetrics());
        }
    }
}
