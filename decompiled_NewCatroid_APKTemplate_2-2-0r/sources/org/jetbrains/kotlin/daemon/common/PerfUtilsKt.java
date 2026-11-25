package org.jetbrains.kotlin.daemon.common;

import java.lang.management.ThreadMXBean;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PerfUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0010 \n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0017\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0086\b\u001a!\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\b\b\u0002\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0004H\u0086\b\u001a\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0086\b\u001a'\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0086\b\u001a1\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0086\b\u001a\u001f\u0010\u000f\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0086\b\u001a\u0011\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0086\b\u001a\r\u0010\u0011\u001a\u00020\u0002*\u00020\u0004H\u0086\b\u001a\r\u0010\u0012\u001a\u00020\u0002*\u00020\u0004H\u0086\b\u001a3\u0010\u0013\u001a\u0002H\u0014\"\u0004\b\u0000\u0010\u0014*\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00140\u0019H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u001a\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u001b"}, d2 = {"beginMeasureWallAndThreadTimes", "", "", "threadMXBean", "Ljava/lang/management/ThreadMXBean;", "beginMeasureWallAndThreadTimesAndMemory", "withGC", "", "beginMeasureWallTime", "endMeasureWallAndThreadTimes", "", "perfCounters", "Lorg/jetbrains/kotlin/daemon/common/PerfCounters;", "startState", "endMeasureWallAndThreadTimesAndMemory", "endMeasureWallTime", "usedMemory", "threadCpuTime", "threadUserTime", "withMeasure", "R", "Lorg/jetbrains/kotlin/daemon/common/Profiler;", "obj", "", "body", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/daemon/common/Profiler;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "daemon-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PerfUtilsKt {
    public static final <R> R withMeasure(Profiler profiler, Object obj, Function0<? extends R> body) {
        Intrinsics.checkNotNullParameter(profiler, "<this>");
        Intrinsics.checkNotNullParameter(body, "body");
        List<Long> listBeginMeasure = profiler.beginMeasure(obj);
        R rInvoke = body.invoke();
        profiler.endMeasure(obj, listBeginMeasure);
        return rInvoke;
    }

    public static final long threadCpuTime(ThreadMXBean threadMXBean) {
        Intrinsics.checkNotNullParameter(threadMXBean, "<this>");
        if (threadMXBean.isCurrentThreadCpuTimeSupported()) {
            return threadMXBean.getCurrentThreadCpuTime();
        }
        return 0L;
    }

    public static final long threadUserTime(ThreadMXBean threadMXBean) {
        Intrinsics.checkNotNullParameter(threadMXBean, "<this>");
        if (threadMXBean.isCurrentThreadCpuTimeSupported()) {
            return threadMXBean.getCurrentThreadUserTime();
        }
        return 0L;
    }

    public static final long usedMemory(boolean z) {
        if (z) {
            System.gc();
        }
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public static final List<Long> beginMeasureWallTime() {
        return CollectionsKt.listOf(Long.valueOf(System.nanoTime()));
    }

    public static final void endMeasureWallTime(PerfCounters perfCounters, List<Long> startState) {
        Intrinsics.checkNotNullParameter(perfCounters, "perfCounters");
        Intrinsics.checkNotNullParameter(startState, "startState");
        PerfCounters.addMeasurement$default(perfCounters, System.nanoTime() - startState.get(0).longValue(), 0L, 0L, 0L, 14, null);
    }

    public static final List<Long> beginMeasureWallAndThreadTimes(ThreadMXBean threadMXBean) {
        Intrinsics.checkNotNullParameter(threadMXBean, "threadMXBean");
        return CollectionsKt.listOf((Object[]) new Long[]{Long.valueOf(System.nanoTime()), Long.valueOf(threadMXBean.isCurrentThreadCpuTimeSupported() ? threadMXBean.getCurrentThreadCpuTime() : 0L), Long.valueOf(threadMXBean.isCurrentThreadCpuTimeSupported() ? threadMXBean.getCurrentThreadUserTime() : 0L)});
    }

    public static final void endMeasureWallAndThreadTimes(PerfCounters perfCounters, ThreadMXBean threadMXBean, List<Long> startState) {
        Intrinsics.checkNotNullParameter(perfCounters, "perfCounters");
        Intrinsics.checkNotNullParameter(threadMXBean, "threadMXBean");
        Intrinsics.checkNotNullParameter(startState, "startState");
        PerfCounters.addMeasurement$default(perfCounters, System.nanoTime() - startState.get(0).longValue(), (threadMXBean.isCurrentThreadCpuTimeSupported() ? threadMXBean.getCurrentThreadCpuTime() : 0L) - startState.get(1).longValue(), (threadMXBean.isCurrentThreadCpuTimeSupported() ? threadMXBean.getCurrentThreadUserTime() : 0L) - startState.get(2).longValue(), 0L, 8, null);
    }

    public static /* synthetic */ List beginMeasureWallAndThreadTimesAndMemory$default(boolean z, ThreadMXBean threadMXBean, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        Intrinsics.checkNotNullParameter(threadMXBean, "threadMXBean");
        if (z) {
            System.gc();
        }
        Runtime runtime = Runtime.getRuntime();
        return CollectionsKt.listOf((Object[]) new Long[]{Long.valueOf(runtime.totalMemory() - runtime.freeMemory()), Long.valueOf(System.nanoTime()), Long.valueOf(threadMXBean.isCurrentThreadCpuTimeSupported() ? threadMXBean.getCurrentThreadCpuTime() : 0L), Long.valueOf(threadMXBean.isCurrentThreadCpuTimeSupported() ? threadMXBean.getCurrentThreadUserTime() : 0L)});
    }

    public static /* synthetic */ void endMeasureWallAndThreadTimesAndMemory$default(PerfCounters perfCounters, boolean z, ThreadMXBean threadMXBean, List startState, int i, Object obj) {
        boolean z2 = (i & 2) != 0 ? false : z;
        Intrinsics.checkNotNullParameter(perfCounters, "perfCounters");
        Intrinsics.checkNotNullParameter(threadMXBean, "threadMXBean");
        Intrinsics.checkNotNullParameter(startState, "startState");
        long jLongValue = ((Number) startState.get(0)).longValue();
        long jLongValue2 = ((Number) startState.get(1)).longValue();
        long jLongValue3 = ((Number) startState.get(2)).longValue();
        long jLongValue4 = ((Number) startState.get(3)).longValue();
        long jNanoTime = System.nanoTime() - jLongValue2;
        long currentThreadCpuTime = (threadMXBean.isCurrentThreadCpuTimeSupported() ? threadMXBean.getCurrentThreadCpuTime() : 0L) - jLongValue3;
        long currentThreadUserTime = (threadMXBean.isCurrentThreadCpuTimeSupported() ? threadMXBean.getCurrentThreadUserTime() : 0L) - jLongValue4;
        if (z2) {
            System.gc();
        }
        Runtime runtime = Runtime.getRuntime();
        perfCounters.addMeasurement(jNanoTime, currentThreadCpuTime, currentThreadUserTime, (runtime.totalMemory() - runtime.freeMemory()) - jLongValue);
    }

    public static final void endMeasureWallAndThreadTimesAndMemory(PerfCounters perfCounters, boolean z, ThreadMXBean threadMXBean, List<Long> startState) {
        Intrinsics.checkNotNullParameter(perfCounters, "perfCounters");
        Intrinsics.checkNotNullParameter(threadMXBean, "threadMXBean");
        Intrinsics.checkNotNullParameter(startState, "startState");
        long jLongValue = startState.get(0).longValue();
        long jLongValue2 = startState.get(1).longValue();
        long jLongValue3 = startState.get(2).longValue();
        long jLongValue4 = startState.get(3).longValue();
        long jNanoTime = System.nanoTime() - jLongValue2;
        long currentThreadCpuTime = (threadMXBean.isCurrentThreadCpuTimeSupported() ? threadMXBean.getCurrentThreadCpuTime() : 0L) - jLongValue3;
        long currentThreadUserTime = (threadMXBean.isCurrentThreadCpuTimeSupported() ? threadMXBean.getCurrentThreadUserTime() : 0L) - jLongValue4;
        if (z) {
            System.gc();
        }
        Runtime runtime = Runtime.getRuntime();
        perfCounters.addMeasurement(jNanoTime, currentThreadCpuTime, currentThreadUserTime, (runtime.totalMemory() - runtime.freeMemory()) - jLongValue);
    }

    public static final List<Long> beginMeasureWallAndThreadTimesAndMemory(boolean z, ThreadMXBean threadMXBean) {
        Intrinsics.checkNotNullParameter(threadMXBean, "threadMXBean");
        if (z) {
            System.gc();
        }
        Runtime runtime = Runtime.getRuntime();
        return CollectionsKt.listOf((Object[]) new Long[]{Long.valueOf(runtime.totalMemory() - runtime.freeMemory()), Long.valueOf(System.nanoTime()), Long.valueOf(threadMXBean.isCurrentThreadCpuTimeSupported() ? threadMXBean.getCurrentThreadCpuTime() : 0L), Long.valueOf(threadMXBean.isCurrentThreadCpuTimeSupported() ? threadMXBean.getCurrentThreadUserTime() : 0L)});
    }
}
