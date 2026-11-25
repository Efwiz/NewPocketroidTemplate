package org.jetbrains.kotlin.daemon.common;

import java.lang.management.ThreadMXBean;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PerfUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0019\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0096\bJ!\u0010\f\u001a\u00020\r2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0096\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/WallAndThreadAndMemoryTotalProfiler;", "Lorg/jetbrains/kotlin/daemon/common/TotalProfiler;", "withGC", "", "(Z)V", "getWithGC", "()Z", "beginMeasure", "", "", "obj", "", "endMeasure", "", "startState", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WallAndThreadAndMemoryTotalProfiler extends TotalProfiler {
    private final boolean withGC;

    public WallAndThreadAndMemoryTotalProfiler(boolean z) {
        this.withGC = z;
    }

    public final boolean getWithGC() {
        return this.withGC;
    }

    @Override // org.jetbrains.kotlin.daemon.common.Profiler
    public List<Long> beginMeasure(Object obj) {
        boolean withGC = getWithGC();
        ThreadMXBean threadMXBean = getThreadMXBean();
        Intrinsics.checkNotNullExpressionValue(threadMXBean, "<get-threadMXBean>(...)");
        if (withGC) {
            System.gc();
        }
        Runtime runtime = Runtime.getRuntime();
        return CollectionsKt.listOf((Object[]) new Long[]{Long.valueOf(runtime.totalMemory() - runtime.freeMemory()), Long.valueOf(System.nanoTime()), Long.valueOf(threadMXBean.isCurrentThreadCpuTimeSupported() ? threadMXBean.getCurrentThreadCpuTime() : 0L), Long.valueOf(threadMXBean.isCurrentThreadCpuTimeSupported() ? threadMXBean.getCurrentThreadUserTime() : 0L)});
    }

    @Override // org.jetbrains.kotlin.daemon.common.Profiler
    public void endMeasure(Object obj, List<Long> startState) {
        Intrinsics.checkNotNullParameter(startState, "startState");
        SimplePerfCounters total = getTotal();
        boolean withGC = getWithGC();
        ThreadMXBean threadMXBean = getThreadMXBean();
        Intrinsics.checkNotNullExpressionValue(threadMXBean, "<get-threadMXBean>(...)");
        long jLongValue = startState.get(0).longValue();
        long jLongValue2 = startState.get(1).longValue();
        long jLongValue3 = startState.get(2).longValue();
        long jLongValue4 = startState.get(3).longValue();
        long jNanoTime = System.nanoTime() - jLongValue2;
        long currentThreadCpuTime = (threadMXBean.isCurrentThreadCpuTimeSupported() ? threadMXBean.getCurrentThreadCpuTime() : 0L) - jLongValue3;
        long currentThreadUserTime = (threadMXBean.isCurrentThreadCpuTimeSupported() ? threadMXBean.getCurrentThreadUserTime() : 0L) - jLongValue4;
        if (withGC) {
            System.gc();
        }
        Runtime runtime = Runtime.getRuntime();
        total.addMeasurement(jNanoTime, currentThreadCpuTime, currentThreadUserTime, (runtime.totalMemory() - runtime.freeMemory()) - jLongValue);
    }
}
