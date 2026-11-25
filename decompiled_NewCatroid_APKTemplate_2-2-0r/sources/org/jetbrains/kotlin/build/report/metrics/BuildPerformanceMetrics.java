package org.jetbrains.kotlin.build.report.metrics;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.build.report.metrics.BuildPerformanceMetric;

/* JADX INFO: compiled from: BuildPerformanceMetrics.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\b\u0002\u0018\u0000 \u0012*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003:\u0001\u0012B\u0005¢\u0006\u0002\u0010\u0004J\u001d\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00028\u00002\b\b\u0002\u0010\f\u001a\u00020\u0007¢\u0006\u0002\u0010\rJ\u0014\u0010\u000e\u001a\u00020\n2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0000J\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00070\u0011R*\u0010\u0005\u001a\u001e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00070\u0006j\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0007`\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetrics;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetric;", "Ljava/io/Serializable;", "()V", "myBuildMetrics", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", ProductAction.ACTION_ADD, "", "metric", "value", "(Lorg/jetbrains/kotlin/build/report/metrics/BuildPerformanceMetric;J)V", "addAll", "other", "asMap", "", "Companion", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuildPerformanceMetrics<T extends BuildPerformanceMetric> implements Serializable {
    public static final long serialVersionUID = 0;
    private final HashMap<T, Long> myBuildMetrics = new HashMap<>();

    public final void addAll(BuildPerformanceMetrics<T> other) {
        Intrinsics.checkNotNullParameter(other, "other");
        for (Map.Entry<T, Long> entry : other.myBuildMetrics.entrySet()) {
            add(entry.getKey(), entry.getValue().longValue());
        }
    }

    public static /* synthetic */ void add$default(BuildPerformanceMetrics buildPerformanceMetrics, BuildPerformanceMetric buildPerformanceMetric, long j, int i, Object obj) {
        if ((i & 2) != 0) {
            j = 1;
        }
        buildPerformanceMetrics.add(buildPerformanceMetric, j);
    }

    public final void add(T metric, long value) {
        Intrinsics.checkNotNullParameter(metric, "metric");
        HashMap<T, Long> map = this.myBuildMetrics;
        map.put(metric, Long.valueOf(map.getOrDefault(metric, 0L).longValue() + value));
    }

    public final Map<T, Long> asMap() {
        return this.myBuildMetrics;
    }
}
