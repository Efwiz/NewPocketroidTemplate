package org.jetbrains.kotlin.build.report.metrics;

import androidx.exifinterface.media.ExifInterface;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.DurationKt;
import org.jetbrains.kotlin.build.report.metrics.BuildTime;

/* JADX INFO: compiled from: BuildTimes.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010$\n\u0002\b\u0002\u0018\u0000 \u0014*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003:\u0001\u0014B\u0005¢\u0006\u0002\u0010\u0004J\u0014\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0000J\u001b\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00028\u00002\u0006\u0010\u000e\u001a\u00020\u0007¢\u0006\u0002\u0010\u000fJ\u001b\u0010\u0010\u001a\u00020\n2\u0006\u0010\r\u001a\u00028\u00002\u0006\u0010\u0011\u001a\u00020\u0007¢\u0006\u0002\u0010\u000fJ\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00070\u0013R*\u0010\u0005\u001a\u001e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00070\u0006j\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u0007`\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/build/report/metrics/BuildTimes;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/build/report/metrics/BuildTime;", "Ljava/io/Serializable;", "()V", "buildTimesNs", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "addAll", "", "other", "addTimeMs", "buildTime", "timeMs", "(Lorg/jetbrains/kotlin/build/report/metrics/BuildTime;J)V", "addTimeNs", "timeNs", "asMapMs", "", "Companion", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuildTimes<T extends BuildTime> implements Serializable {
    public static final long serialVersionUID = 0;
    private final HashMap<T, Long> buildTimesNs = new HashMap<>();

    public final void addAll(BuildTimes<T> other) {
        Intrinsics.checkNotNullParameter(other, "other");
        for (Map.Entry<T, Long> entry : other.buildTimesNs.entrySet()) {
            addTimeNs(entry.getKey(), entry.getValue().longValue());
        }
    }

    public final void addTimeNs(T buildTime, long timeNs) {
        Intrinsics.checkNotNullParameter(buildTime, "buildTime");
        HashMap<T, Long> map = this.buildTimesNs;
        map.put(buildTime, Long.valueOf(map.getOrDefault(buildTime, 0L).longValue() + timeNs));
    }

    public final void addTimeMs(T buildTime, long timeMs) {
        Intrinsics.checkNotNullParameter(buildTime, "buildTime");
        addTimeNs(buildTime, timeMs * ((long) DurationKt.NANOS_IN_MILLIS));
    }

    public final Map<T, Long> asMapMs() {
        HashMap<T, Long> map = this.buildTimesNs;
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(map.size()));
        Iterator<T> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            linkedHashMap.put(entry.getKey(), Long.valueOf(((Number) entry.getValue()).longValue() / ((long) DurationKt.NANOS_IN_MILLIS)));
        }
        return linkedHashMap;
    }
}
