package org.jetbrains.kotlin.build.report.metrics;

import androidx.exifinterface.media.ExifInterface;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BuildMetricsReporter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a*\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\b\b\u0001\u0010\u0004*\u00020\u0005*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00040\u0006\u001aQ\u0010\u0007\u001a\u0002H\b\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\b\b\u0001\u0010\u0004*\u00020\u0005\"\u0004\b\u0002\u0010\b*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00040\u00062\u0006\u0010\t\u001a\u0002H\u00022\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\b0\u000bH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\f\u001a*\u0010\r\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\b\b\u0001\u0010\u0004*\u00020\u0005*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00040\u0006\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u000e"}, d2 = {"endMeasureGc", "", "B", "Lorg/jetbrains/kotlin/build/report/metrics/BuildTime;", "P", "Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetric;", "Lorg/jetbrains/kotlin/build/report/metrics/BuildMetricsReporter;", "measure", ExifInterface.GPS_DIRECTION_TRUE, "time", "fn", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/build/report/metrics/BuildMetricsReporter;Lorg/jetbrains/kotlin/build/report/metrics/BuildTime;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "startMeasureGc", "kotlin-build-statistics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BuildMetricsReporterKt {
    public static final <B extends BuildTime, P extends BuildPerformanceMetric, T> T measure(BuildMetricsReporter<B, P> buildMetricsReporter, B time, Function0<? extends T> fn) {
        Intrinsics.checkNotNullParameter(buildMetricsReporter, "<this>");
        Intrinsics.checkNotNullParameter(time, "time");
        Intrinsics.checkNotNullParameter(fn, "fn");
        buildMetricsReporter.startMeasure(time);
        try {
            return fn.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            buildMetricsReporter.endMeasure(time);
            InlineMarker.finallyEnd(1);
        }
    }

    public static final <B extends BuildTime, P extends BuildPerformanceMetric> void startMeasureGc(BuildMetricsReporter<B, P> buildMetricsReporter) {
        Intrinsics.checkNotNullParameter(buildMetricsReporter, "<this>");
        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        Intrinsics.checkNotNullExpressionValue(garbageCollectorMXBeans, "getGarbageCollectorMXBeans(...)");
        for (GarbageCollectorMXBean garbageCollectorMXBean : garbageCollectorMXBeans) {
            String name2 = garbageCollectorMXBean.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            buildMetricsReporter.startGcMetric(name2, new GcMetric(garbageCollectorMXBean.getCollectionTime(), garbageCollectorMXBean.getCollectionCount()));
        }
    }

    public static final <B extends BuildTime, P extends BuildPerformanceMetric> void endMeasureGc(BuildMetricsReporter<B, P> buildMetricsReporter) {
        Intrinsics.checkNotNullParameter(buildMetricsReporter, "<this>");
        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        Intrinsics.checkNotNullExpressionValue(garbageCollectorMXBeans, "getGarbageCollectorMXBeans(...)");
        for (GarbageCollectorMXBean garbageCollectorMXBean : garbageCollectorMXBeans) {
            String name2 = garbageCollectorMXBean.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            buildMetricsReporter.endGcMetric(name2, new GcMetric(garbageCollectorMXBean.getCollectionTime(), garbageCollectorMXBean.getCollectionCount()));
        }
    }
}
