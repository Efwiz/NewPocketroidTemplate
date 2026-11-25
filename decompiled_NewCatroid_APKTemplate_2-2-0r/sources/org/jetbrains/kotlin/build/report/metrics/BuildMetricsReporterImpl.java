package org.jetbrains.kotlin.build.report.metrics;

import java.io.Serializable;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.build.report.metrics.BuildPerformanceMetric;
import org.jetbrains.kotlin.build.report.metrics.BuildTime;

/* JADX INFO: compiled from: BuildMetricsReporterImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000e\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u00052\u00020\u0006B\u0005¢\u0006\u0002\u0010\u0007J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0018\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u0016H\u0016J\u001d\u0010\u001e\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00028\u00012\u0006\u0010\u001d\u001a\u00020\u000eH\u0016¢\u0006\u0002\u0010\u001fJ\u001c\u0010 \u001a\u00020\u00182\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\"H\u0016J\u0015\u0010#\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010$J\u001d\u0010%\u001a\u00020\u00182\u0006\u0010&\u001a\u00028\u00002\u0006\u0010'\u001a\u00020\u000eH\u0016¢\u0006\u0002\u0010(J\u0018\u0010)\u001a\u00020\u00182\u0006\u0010*\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u0016H\u0016J\u0015\u0010+\u001a\u00020\u00182\u0006\u0010&\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010,J\u0014\u0010-\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\"H\u0016J\u0018\u0010.\u001a\u00020\u00182\u0006\u0010*\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u0016H\u0016J\u0015\u0010/\u001a\u00020\u00182\u0006\u0010&\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010,R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\f\u001a\u001e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u000e0\rj\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u000e`\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u0014\u001a\u001e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\rj\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u0016`\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/build/report/metrics/BuildMetricsReporterImpl;", "B", "Lorg/jetbrains/kotlin/build/report/metrics/BuildTime;", "P", "Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetric;", "Lorg/jetbrains/kotlin/build/report/metrics/BuildMetricsReporter;", "Ljava/io/Serializable;", "()V", "myBuildAttributes", "Lorg/jetbrains/kotlin/build/report/metrics/BuildAttributes;", "myBuildMetrics", "Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetrics;", "myBuildTimeStartNs", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "myBuildTimes", "Lorg/jetbrains/kotlin/build/report/metrics/BuildTimes;", "myGcMetrics", "Lorg/jetbrains/kotlin/build/report/metrics/GcMetrics;", "myGcPerformance", "", "Lorg/jetbrains/kotlin/build/report/metrics/GcMetric;", "addAttribute", "", "attribute", "Lorg/jetbrains/kotlin/build/report/metrics/BuildAttribute;", "addGcMetric", "metric", "value", "addMetric", "(Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetric;J)V", "addMetrics", "metrics", "Lorg/jetbrains/kotlin/build/report/metrics/BuildMetrics;", "addTimeMetric", "(Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetric;)V", "addTimeMetricNs", "time", "durationNs", "(Lorg/jetbrains/kotlin/build/report/metrics/BuildTime;J)V", "endGcMetric", "name", "endMeasure", "(Lorg/jetbrains/kotlin/build/report/metrics/BuildTime;)V", "getMetrics", "startGcMetric", "startMeasure", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class BuildMetricsReporterImpl<B extends BuildTime, P extends BuildPerformanceMetric> implements Serializable, BuildMetricsReporter<B, P> {
    private final HashMap<B, Long> myBuildTimeStartNs = new HashMap<>();
    private final HashMap<String, GcMetric> myGcPerformance = new HashMap<>();
    private final BuildTimes<B> myBuildTimes = new BuildTimes<>();
    private final BuildPerformanceMetrics<P> myBuildMetrics = new BuildPerformanceMetrics<>();
    private final BuildAttributes myBuildAttributes = new BuildAttributes();
    private final GcMetrics myGcMetrics = new GcMetrics();

    /* JADX INFO: compiled from: BuildMetricsReporterImpl.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ValueType.values().length];
            try {
                iArr[ValueType.NANOSECONDS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ValueType.MILLISECONDS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ValueType.TIME.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void startMeasure(B time) {
        Intrinsics.checkNotNullParameter(time, "time");
        if (this.myBuildTimeStartNs.containsKey(time)) {
            throw new IllegalStateException((time + " was restarted before it finished").toString());
        }
        this.myBuildTimeStartNs.put(time, Long.valueOf(System.nanoTime()));
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void endMeasure(B time) {
        Intrinsics.checkNotNullParameter(time, "time");
        Long lRemove = this.myBuildTimeStartNs.remove(time);
        if (lRemove == null) {
            throw new IllegalStateException((time + " finished before it started").toString());
        }
        this.myBuildTimes.addTimeNs(time, System.nanoTime() - lRemove.longValue());
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void startGcMetric(String name2, GcMetric value) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        if (this.myGcPerformance.containsKey(name2)) {
            throw new IllegalStateException((name2 + " was restarted before it finished").toString());
        }
        this.myGcPerformance.put(name2, value);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void endGcMetric(String name2, GcMetric value) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        GcMetric gcMetricRemove = this.myGcPerformance.remove(name2);
        if (gcMetricRemove == null) {
            throw new IllegalStateException((name2 + " finished before it started").toString());
        }
        this.myGcMetrics.add(name2, value.minus(gcMetricRemove));
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addTimeMetricNs(B time, long durationNs) {
        Intrinsics.checkNotNullParameter(time, "time");
        this.myBuildTimes.addTimeNs(time, durationNs);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addMetric(P metric, long value) {
        Intrinsics.checkNotNullParameter(metric, "metric");
        this.myBuildMetrics.add(metric, value);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addTimeMetric(P metric) {
        Intrinsics.checkNotNullParameter(metric, "metric");
        int i = WhenMappings.$EnumSwitchMapping$0[metric.getType().ordinal()];
        if (i == 1) {
            this.myBuildMetrics.add(metric, System.nanoTime());
        } else if (i == 2) {
            this.myBuildMetrics.add(metric, System.currentTimeMillis());
        } else {
            if (i != 3) {
                throw new IllegalStateException(("Unable to add time metric for '" + metric.getType() + "' type").toString());
            }
            this.myBuildMetrics.add(metric, System.currentTimeMillis());
        }
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addGcMetric(String metric, GcMetric value) {
        Intrinsics.checkNotNullParameter(metric, "metric");
        Intrinsics.checkNotNullParameter(value, "value");
        this.myGcMetrics.add(metric, value);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addAttribute(BuildAttribute attribute) {
        Intrinsics.checkNotNullParameter(attribute, "attribute");
        BuildAttributes.add$default(this.myBuildAttributes, attribute, 0, 2, null);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public BuildMetrics<B, P> getMetrics() {
        return new BuildMetrics<>(this.myBuildTimes, this.myBuildMetrics, this.myBuildAttributes, this.myGcMetrics);
    }

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildMetricsReporter
    public void addMetrics(BuildMetrics<B, P> metrics) {
        Intrinsics.checkNotNullParameter(metrics, "metrics");
        this.myBuildAttributes.addAll(metrics.getBuildAttributes());
        this.myBuildTimes.addAll(metrics.getBuildTimes());
        this.myBuildMetrics.addAll(metrics.getBuildPerformanceMetrics());
        this.myGcMetrics.addAll(metrics.getGcMetrics());
    }
}
