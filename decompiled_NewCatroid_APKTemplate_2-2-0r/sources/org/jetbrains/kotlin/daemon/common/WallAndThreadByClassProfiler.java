package org.jetbrains.kotlin.daemon.common;

import java.lang.management.ThreadMXBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PerfUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0019\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u0005H\u0096\bJ!\u0010\u000e\u001a\u00020\u000f2\b\u0010\r\u001a\u0004\u0018\u00010\u00052\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0096\bJ\u0016\u0010\b\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u00120\u0011H\u0016R1\u0010\u0003\u001a\"\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u0006`\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/WallAndThreadByClassProfiler;", "Lorg/jetbrains/kotlin/daemon/common/TotalProfiler;", "()V", "counters", "Ljava/util/HashMap;", "", "Lorg/jetbrains/kotlin/daemon/common/SimplePerfCountersWithTotal;", "Lkotlin/collections/HashMap;", "getCounters", "()Ljava/util/HashMap;", "beginMeasure", "", "", "obj", "endMeasure", "", "startState", "", "Lorg/jetbrains/kotlin/daemon/common/PerfCounters;", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WallAndThreadByClassProfiler extends TotalProfiler {
    private final HashMap<Object, SimplePerfCountersWithTotal> counters = new HashMap<>();

    @Override // org.jetbrains.kotlin.daemon.common.TotalProfiler, org.jetbrains.kotlin.daemon.common.Profiler
    public final HashMap<Object, SimplePerfCountersWithTotal> getCounters() {
        return this.counters;
    }

    @Override // org.jetbrains.kotlin.daemon.common.TotalProfiler, org.jetbrains.kotlin.daemon.common.Profiler
    public Map<Object, PerfCounters> getCounters() {
        return this.counters;
    }

    @Override // org.jetbrains.kotlin.daemon.common.Profiler
    public List<Long> beginMeasure(Object obj) {
        ThreadMXBean threadMXBean = getThreadMXBean();
        Intrinsics.checkNotNullExpressionValue(threadMXBean, "<get-threadMXBean>(...)");
        return CollectionsKt.listOf((Object[]) new Long[]{Long.valueOf(System.nanoTime()), Long.valueOf(threadMXBean.isCurrentThreadCpuTimeSupported() ? threadMXBean.getCurrentThreadCpuTime() : 0L), Long.valueOf(threadMXBean.isCurrentThreadCpuTimeSupported() ? threadMXBean.getCurrentThreadUserTime() : 0L)});
    }

    @Override // org.jetbrains.kotlin.daemon.common.Profiler
    public void endMeasure(Object obj, List<Long> startState) {
        Class<?> cls;
        Intrinsics.checkNotNullParameter(startState, "startState");
        HashMap<Object, SimplePerfCountersWithTotal> counters = getCounters();
        String name2 = (obj == null || (cls = obj.getClass()) == null) ? null : cls.getName();
        SimplePerfCountersWithTotal simplePerfCountersWithTotal = counters.get(name2);
        if (simplePerfCountersWithTotal == null) {
            simplePerfCountersWithTotal = new SimplePerfCountersWithTotal(getTotal());
            counters.put(name2, simplePerfCountersWithTotal);
        }
        SimplePerfCountersWithTotal simplePerfCountersWithTotal2 = simplePerfCountersWithTotal;
        ThreadMXBean threadMXBean = getThreadMXBean();
        Intrinsics.checkNotNullExpressionValue(threadMXBean, "<get-threadMXBean>(...)");
        PerfCounters.addMeasurement$default(simplePerfCountersWithTotal2, System.nanoTime() - startState.get(0).longValue(), (threadMXBean.isCurrentThreadCpuTimeSupported() ? threadMXBean.getCurrentThreadCpuTime() : 0L) - startState.get(1).longValue(), (threadMXBean.isCurrentThreadCpuTimeSupported() ? threadMXBean.getCurrentThreadUserTime() : 0L) - startState.get(2).longValue(), 0L, 8, null);
    }
}
