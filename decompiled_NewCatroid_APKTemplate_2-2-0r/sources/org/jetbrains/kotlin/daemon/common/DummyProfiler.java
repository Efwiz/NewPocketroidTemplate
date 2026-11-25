package org.jetbrains.kotlin.daemon.common;

import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;

/* JADX INFO: compiled from: PerfUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u00060\u0004H\u0016J\b\u0010\u0007\u001a\u00020\u0006H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/DummyProfiler;", "Lorg/jetbrains/kotlin/daemon/common/Profiler;", "()V", "getCounters", "", "", "Lorg/jetbrains/kotlin/daemon/common/PerfCounters;", "getTotalCounters", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DummyProfiler implements Profiler {
    @Override // org.jetbrains.kotlin.daemon.common.Profiler
    public Map<Object, PerfCounters> getCounters() {
        return MapsKt.mapOf(TuplesKt.to(null, new SimplePerfCounters()));
    }

    @Override // org.jetbrains.kotlin.daemon.common.Profiler
    public PerfCounters getTotalCounters() {
        return new SimplePerfCounters();
    }
}
