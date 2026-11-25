package org.jetbrains.kotlin.daemon.common;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PerfUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u0001H\u0016J \u0010\u0006\u001a\u00020\u00072\b\u0010\u0005\u001a\u0004\u0018\u00010\u00012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0016J\u0016\u0010\t\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0012\u0004\u0012\u00020\u000b0\nH&J\b\u0010\f\u001a\u00020\u000bH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/Profiler;", "", "beginMeasure", "", "", "obj", "endMeasure", "", "startState", "getCounters", "", "Lorg/jetbrains/kotlin/daemon/common/PerfCounters;", "getTotalCounters", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface Profiler {
    default void endMeasure(Object obj, List<Long> startState) {
        Intrinsics.checkNotNullParameter(startState, "startState");
    }

    Map<Object, PerfCounters> getCounters();

    PerfCounters getTotalCounters();

    default List<Long> beginMeasure(Object obj) {
        return CollectionsKt.emptyList();
    }
}
