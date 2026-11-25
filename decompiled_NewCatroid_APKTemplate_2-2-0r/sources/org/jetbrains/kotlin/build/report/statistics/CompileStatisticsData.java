package org.jetbrains.kotlin.build.report.statistics;

import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.build.report.metrics.BuildAttribute;
import org.jetbrains.kotlin.build.report.metrics.BuildPerformanceMetric;
import org.jetbrains.kotlin.build.report.metrics.BuildTime;

/* JADX INFO: compiled from: CompileStatisticsData.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00042\u00020\u0005J\u0014\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\b0\u0007H&J\b\u0010\t\u001a\u00020\nH\u0016J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\fH&J\u000e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\fH\u0016J\u000e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\n0\fH&J\b\u0010\u000f\u001a\u00020\bH&J\b\u0010\u0010\u001a\u00020\bH&J\u000f\u0010\u0011\u001a\u0004\u0018\u00010\u0012H&¢\u0006\u0002\u0010\u0013J\u0016\u0010\u0014\u001a\u0010\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007H&J\u0016\u0010\u0015\u001a\u0010\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007H&J\n\u0010\u0016\u001a\u0004\u0018\u00010\nH\u0016J\u000e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\n0\fH&J\n\u0010\u0018\u001a\u0004\u0018\u00010\nH&J\b\u0010\u0019\u001a\u00020\nH&J\n\u0010\u001a\u001a\u0004\u0018\u00010\nH&J\u000e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH&J\u0014\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020\b0\u0007H&J\n\u0010\u001f\u001a\u0004\u0018\u00010\nH&J\n\u0010 \u001a\u0004\u0018\u00010\nH&J\b\u0010!\u001a\u00020\bH&J\u000e\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u001cH&J\b\u0010$\u001a\u00020\nH&J\n\u0010%\u001a\u0004\u0018\u00010\nH&J\b\u0010&\u001a\u00020\nH\u0016J\b\u0010'\u001a\u00020\nH\u0016J\b\u0010(\u001a\u00020)H\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006*À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/build/report/statistics/CompileStatisticsData;", "B", "Lorg/jetbrains/kotlin/build/report/metrics/BuildTime;", "P", "Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetric;", "", "getBuildTimesMetrics", "", "", "getBuildUuid", "", "getChanges", "", "getCompiledSources", "getCompilerArguments", "getDurationMs", "getFinishTime", "getFromKotlinPlugin", "", "()Ljava/lang/Boolean;", "getGcCountMetrics", "getGcTimeMetrics", "getHostName", "getIcLogLines", "getKotlinLanguageVersion", "getKotlinVersion", "getLabel", "getNonIncrementalAttributes", "", "Lorg/jetbrains/kotlin/build/report/metrics/BuildAttribute;", "getPerformanceMetrics", "getProjectName", "getSkipMessage", "getStartTimeMs", "getTags", "Lorg/jetbrains/kotlin/build/report/statistics/StatTag;", "getTaskName", "getTaskResult", "getTimestamp", "getType", "getVersion", "", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CompileStatisticsData<B extends BuildTime, P extends BuildPerformanceMetric> {
    Map<B, Long> getBuildTimesMetrics();

    List<String> getChanges();

    List<String> getCompilerArguments();

    long getDurationMs();

    long getFinishTime();

    Boolean getFromKotlinPlugin();

    Map<String, Long> getGcCountMetrics();

    Map<String, Long> getGcTimeMetrics();

    List<String> getIcLogLines();

    String getKotlinLanguageVersion();

    String getKotlinVersion();

    String getLabel();

    Set<BuildAttribute> getNonIncrementalAttributes();

    Map<P, Long> getPerformanceMetrics();

    String getProjectName();

    String getSkipMessage();

    long getStartTimeMs();

    Set<StatTag> getTags();

    String getTaskName();

    String getTaskResult();

    default int getVersion() {
        return 4;
    }

    default String getBuildUuid() {
        return "Unset";
    }

    default String getHostName() {
        return "Unset";
    }

    default String getTimestamp() {
        String str = CompileStatisticsDataKt.formatter.format(Long.valueOf(getFinishTime()));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }

    default String getType() {
        return "TASK_DATA";
    }

    default List<String> getCompiledSources() {
        return CollectionsKt.emptyList();
    }
}
