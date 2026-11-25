package org.jetbrains.kotlin.daemon.common;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PerfUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J(\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/SimplePerfCountersWithTotal;", "Lorg/jetbrains/kotlin/daemon/common/SimplePerfCounters;", "totalRef", "Lorg/jetbrains/kotlin/daemon/common/PerfCounters;", "(Lorg/jetbrains/kotlin/daemon/common/PerfCounters;)V", "getTotalRef", "()Lorg/jetbrains/kotlin/daemon/common/PerfCounters;", "addMeasurement", "", "time", "", "thread", "threadUser", "memory", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SimplePerfCountersWithTotal extends SimplePerfCounters {
    private final PerfCounters totalRef;

    public SimplePerfCountersWithTotal(PerfCounters totalRef) {
        Intrinsics.checkNotNullParameter(totalRef, "totalRef");
        this.totalRef = totalRef;
    }

    public final PerfCounters getTotalRef() {
        return this.totalRef;
    }

    @Override // org.jetbrains.kotlin.daemon.common.SimplePerfCounters, org.jetbrains.kotlin.daemon.common.PerfCounters
    public void addMeasurement(long time, long thread, long threadUser, long memory) {
        super.addMeasurement(time, thread, threadUser, memory);
        this.totalRef.addMeasurement(time, thread, threadUser, memory);
    }
}
