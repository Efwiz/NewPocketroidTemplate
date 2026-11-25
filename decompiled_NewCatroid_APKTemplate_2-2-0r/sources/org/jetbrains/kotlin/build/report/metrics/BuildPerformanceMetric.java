package org.jetbrains.kotlin.build.report.metrics;

import java.io.Serializable;
import java.util.List;
import kotlin.Metadata;

/* JADX INFO: compiled from: BuildPerformanceMetric.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u00012\u00020\u0002J\u0010\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0000\u0018\u00010\u0004H&J\u000e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00000\u0004H&J\n\u0010\u0006\u001a\u0004\u0018\u00010\u0000H&J\b\u0010\u0007\u001a\u00020\bH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetric;", "Lorg/jetbrains/kotlin/build/report/metrics/BuildTime;", "Ljava/io/Serializable;", "children", "", "getAllMetrics", "getParent", "getType", "Lorg/jetbrains/kotlin/build/report/metrics/ValueType;", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface BuildPerformanceMetric extends Serializable, BuildTime {
    @Override // org.jetbrains.kotlin.build.report.metrics.BuildTime
    List<BuildPerformanceMetric> children();

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildTime
    List<BuildPerformanceMetric> getAllMetrics();

    @Override // org.jetbrains.kotlin.build.report.metrics.BuildTime
    BuildPerformanceMetric getParent();

    ValueType getType();
}
