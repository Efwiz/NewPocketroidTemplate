package org.jetbrains.kotlin.build.report.metrics;

import com.google.firebase.database.core.ServerValues;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: GcMetrics.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\u0011\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0000H\u0086\u0002J\u0013\u0010\u0014\u001a\u00020\u00002\b\u0010\u0013\u001a\u0004\u0018\u00010\u0000H\u0086\u0002J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/build/report/metrics/GcMetric;", "Ljava/io/Serializable;", "time", "", "count", "(JJ)V", "getCount", "()J", "getTime", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "minus", ServerValues.NAME_OP_INCREMENT, "plus", "toString", "", "kotlin-build-statistics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class GcMetric implements Serializable {
    private final long count;
    private final long time;

    public static /* synthetic */ GcMetric copy$default(GcMetric gcMetric, long j, long j2, int i, Object obj) {
        if ((i & 1) != 0) {
            j = gcMetric.time;
        }
        if ((i & 2) != 0) {
            j2 = gcMetric.count;
        }
        return gcMetric.copy(j, j2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final long getTime() {
        return this.time;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final long getCount() {
        return this.count;
    }

    public final GcMetric copy(long time, long count) {
        return new GcMetric(time, count);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GcMetric)) {
            return false;
        }
        GcMetric gcMetric = (GcMetric) other;
        return this.time == gcMetric.time && this.count == gcMetric.count;
    }

    public int hashCode() {
        return (Long.hashCode(this.time) * 31) + Long.hashCode(this.count);
    }

    public String toString() {
        return "GcMetric(time=" + this.time + ", count=" + this.count + ')';
    }

    public GcMetric(long j, long j2) {
        this.time = j;
        this.count = j2;
    }

    public final long getTime() {
        return this.time;
    }

    public final long getCount() {
        return this.count;
    }

    public final GcMetric minus(GcMetric increment) {
        Intrinsics.checkNotNullParameter(increment, "increment");
        return new GcMetric(this.time - increment.time, this.count - increment.count);
    }

    public final GcMetric plus(GcMetric increment) {
        return new GcMetric(this.time + (increment != null ? increment.time : 0L), this.count + (increment != null ? increment.count : 0L));
    }
}
