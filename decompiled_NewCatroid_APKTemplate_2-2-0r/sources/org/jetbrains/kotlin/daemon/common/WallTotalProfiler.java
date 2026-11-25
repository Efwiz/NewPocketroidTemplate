package org.jetbrains.kotlin.daemon.common;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PerfUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0096\bJ!\u0010\b\u001a\u00020\t2\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0096\b¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/WallTotalProfiler;", "Lorg/jetbrains/kotlin/daemon/common/TotalProfiler;", "()V", "beginMeasure", "", "", "obj", "", "endMeasure", "", "startState", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WallTotalProfiler extends TotalProfiler {
    @Override // org.jetbrains.kotlin.daemon.common.Profiler
    public void endMeasure(Object obj, List<Long> startState) {
        Intrinsics.checkNotNullParameter(startState, "startState");
        PerfCounters.addMeasurement$default(getTotal(), System.nanoTime() - startState.get(0).longValue(), 0L, 0L, 0L, 14, null);
    }

    @Override // org.jetbrains.kotlin.daemon.common.Profiler
    public List<Long> beginMeasure(Object obj) {
        return CollectionsKt.listOf(Long.valueOf(System.nanoTime()));
    }
}
