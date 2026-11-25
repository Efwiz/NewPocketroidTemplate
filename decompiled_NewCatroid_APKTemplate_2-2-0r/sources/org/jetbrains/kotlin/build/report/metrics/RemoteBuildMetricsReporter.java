package org.jetbrains.kotlin.build.report.metrics;

import kotlin.Metadata;
import org.jetbrains.kotlin.build.report.RemoteReporter;
import org.jetbrains.kotlin.build.report.metrics.BuildPerformanceMetric;
import org.jetbrains.kotlin.build.report.metrics.BuildTime;

/* JADX INFO: compiled from: RemoteBuildMetricsReporter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u00052\u00020\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/build/report/metrics/RemoteBuildMetricsReporter;", "B", "Lorg/jetbrains/kotlin/build/report/metrics/BuildTime;", "P", "Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetric;", "Lorg/jetbrains/kotlin/build/report/metrics/BuildMetricsReporter;", "Lorg/jetbrains/kotlin/build/report/RemoteReporter;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface RemoteBuildMetricsReporter<B extends BuildTime, P extends BuildPerformanceMetric> extends RemoteReporter, BuildMetricsReporter<B, P> {
}
