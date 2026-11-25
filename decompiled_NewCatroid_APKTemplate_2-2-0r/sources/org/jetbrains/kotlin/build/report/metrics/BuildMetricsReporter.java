package org.jetbrains.kotlin.build.report.metrics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.DurationKt;
import org.jetbrains.kotlin.build.report.metrics.BuildPerformanceMetric;
import org.jetbrains.kotlin.build.report.metrics.BuildTime;

/* JADX INFO: compiled from: BuildMetricsReporter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0010\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\u00020\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&J\u0018\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH&J\u001d\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00028\u00012\u0006\u0010\r\u001a\u00020\u0010H&¢\u0006\u0002\u0010\u0011J\u001c\u0010\u0012\u001a\u00020\u00072\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0014H&J\u0015\u0010\u0015\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00028\u0001H&¢\u0006\u0002\u0010\u0016J\u001d\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00028\u00002\u0006\u0010\u0019\u001a\u00020\u0010H\u0016¢\u0006\u0002\u0010\u001aJ\u001d\u0010\u001b\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00028\u00002\u0006\u0010\u001c\u001a\u00020\u0010H&¢\u0006\u0002\u0010\u001aJ\u0018\u0010\u001d\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH&J\u0015\u0010\u001f\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00028\u0000H&¢\u0006\u0002\u0010 J\u0014\u0010!\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0014H&J\u0018\u0010\"\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH&J\u0015\u0010#\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00028\u0000H&¢\u0006\u0002\u0010 ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006$À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/build/report/metrics/BuildMetricsReporter;", "B", "Lorg/jetbrains/kotlin/build/report/metrics/BuildTime;", "P", "Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetric;", "", "addAttribute", "", "attribute", "Lorg/jetbrains/kotlin/build/report/metrics/BuildAttribute;", "addGcMetric", "metric", "", "value", "Lorg/jetbrains/kotlin/build/report/metrics/GcMetric;", "addMetric", "", "(Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetric;J)V", "addMetrics", "metrics", "Lorg/jetbrains/kotlin/build/report/metrics/BuildMetrics;", "addTimeMetric", "(Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetric;)V", "addTimeMetricMs", "time", "durationMs", "(Lorg/jetbrains/kotlin/build/report/metrics/BuildTime;J)V", "addTimeMetricNs", "durationNs", "endGcMetric", "name", "endMeasure", "(Lorg/jetbrains/kotlin/build/report/metrics/BuildTime;)V", "getMetrics", "startGcMetric", "startMeasure", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface BuildMetricsReporter<B extends BuildTime, P extends BuildPerformanceMetric> {
    void addAttribute(BuildAttribute attribute);

    void addGcMetric(String metric, GcMetric value);

    void addMetric(P metric, long value);

    void addMetrics(BuildMetrics<B, P> metrics);

    void addTimeMetric(P metric);

    void addTimeMetricNs(B time, long durationNs);

    void endGcMetric(String name2, GcMetric value);

    void endMeasure(B time);

    BuildMetrics<B, P> getMetrics();

    void startGcMetric(String name2, GcMetric value);

    void startMeasure(B time);

    default void addTimeMetricMs(B time, long durationMs) {
        Intrinsics.checkNotNullParameter(time, "time");
        addTimeMetricNs(time, durationMs * ((long) DurationKt.NANOS_IN_MILLIS));
    }
}
