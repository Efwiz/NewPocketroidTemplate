package org.jetbrains.kotlin.build.report;

import kotlin.Metadata;
import org.jetbrains.kotlin.build.report.metrics.DoNothingBuildMetricsReporter;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildPerformanceMetric;
import org.jetbrains.kotlin.build.report.metrics.GradleBuildTime;

/* JADX INFO: compiled from: BuildReporter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/build/report/DoNothingBuildReporter;", "Lorg/jetbrains/kotlin/build/report/BuildReporter;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildTime;", "Lorg/jetbrains/kotlin/build/report/metrics/GradleBuildPerformanceMetric;", "()V", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DoNothingBuildReporter extends BuildReporter<GradleBuildTime, GradleBuildPerformanceMetric> {
    public static final DoNothingBuildReporter INSTANCE = new DoNothingBuildReporter();

    private DoNothingBuildReporter() {
        super(DoNothingICReporter.INSTANCE, DoNothingBuildMetricsReporter.INSTANCE);
    }
}
