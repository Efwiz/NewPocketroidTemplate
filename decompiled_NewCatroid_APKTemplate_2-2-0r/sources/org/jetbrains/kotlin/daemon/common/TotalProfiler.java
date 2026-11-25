package org.jetbrains.kotlin.daemon.common;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: PerfUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\f\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u000e\u0012\u0004\u0012\u00020\u000f0\rH\u0016J\b\u0010\u0010\u001a\u00020\u000fH\u0016R\u0019\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/TotalProfiler;", "Lorg/jetbrains/kotlin/daemon/common/Profiler;", "()V", "threadMXBean", "Ljava/lang/management/ThreadMXBean;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getThreadMXBean", "()Ljava/lang/management/ThreadMXBean;", "total", "Lorg/jetbrains/kotlin/daemon/common/SimplePerfCounters;", "getTotal", "()Lorg/jetbrains/kotlin/daemon/common/SimplePerfCounters;", "getCounters", "", "", "Lorg/jetbrains/kotlin/daemon/common/PerfCounters;", "getTotalCounters", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TotalProfiler implements Profiler {
    private final SimplePerfCounters total = new SimplePerfCounters();
    private final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

    public final SimplePerfCounters getTotal() {
        return this.total;
    }

    public final ThreadMXBean getThreadMXBean() {
        return this.threadMXBean;
    }

    @Override // org.jetbrains.kotlin.daemon.common.Profiler
    public Map<Object, PerfCounters> getCounters() {
        return MapsKt.emptyMap();
    }

    @Override // org.jetbrains.kotlin.daemon.common.Profiler
    public PerfCounters getTotalCounters() {
        return this.total;
    }
}
