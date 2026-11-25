package org.jetbrains.kotlin.build.report.metrics;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: GcMetrics.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0006J\u000e\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u0000J\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00100\u000fJ\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00100\u000fJ\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u000fJ\u0006\u0010\u0013\u001a\u00020\u0014R*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/build/report/metrics/GcMetrics;", "Ljava/io/Serializable;", "()V", "myGcMetrics", "Ljava/util/HashMap;", "", "Lorg/jetbrains/kotlin/build/report/metrics/GcMetric;", "Lkotlin/collections/HashMap;", ProductAction.ACTION_ADD, "", "metric", "value", "addAll", "gcMetrics", "asGcCountMap", "", "", "asGcTimeMap", "asMap", "isEmpty", "", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class GcMetrics implements Serializable {
    private final HashMap<String, GcMetric> myGcMetrics = new HashMap<>();

    public final void addAll(GcMetrics gcMetrics) {
        GcMetric gcMetricPlus;
        Intrinsics.checkNotNullParameter(gcMetrics, "gcMetrics");
        for (Map.Entry<String, GcMetric> entry : gcMetrics.myGcMetrics.entrySet()) {
            String key = entry.getKey();
            GcMetric value = entry.getValue();
            GcMetric gcMetric = this.myGcMetrics.get(key);
            HashMap<String, GcMetric> map = this.myGcMetrics;
            if (gcMetric != null && (gcMetricPlus = gcMetric.plus(value)) != null) {
                value = gcMetricPlus;
            }
            map.put(key, value);
        }
    }

    public final void add(String metric, GcMetric value) {
        Intrinsics.checkNotNullParameter(metric, "metric");
        Intrinsics.checkNotNullParameter(value, "value");
        this.myGcMetrics.put(metric, value);
    }

    public final Map<String, Long> asGcCountMap() {
        HashMap<String, GcMetric> map = this.myGcMetrics;
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(map.size()));
        Iterator<T> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap.put(entry.getKey(), Long.valueOf(((GcMetric) entry.getValue()).getCount()));
        }
        return linkedHashMap;
    }

    public final Map<String, Long> asGcTimeMap() {
        HashMap<String, GcMetric> map = this.myGcMetrics;
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(map.size()));
        Iterator<T> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap.put(entry.getKey(), Long.valueOf(((GcMetric) entry.getValue()).getTime()));
        }
        return linkedHashMap;
    }

    public final Map<String, GcMetric> asMap() {
        return this.myGcMetrics;
    }

    public final boolean isEmpty() {
        return this.myGcMetrics.isEmpty();
    }
}
