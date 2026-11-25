package org.jetbrains.kotlin.daemon.common;

import kotlin.Metadata;

/* JADX INFO: compiled from: PerfUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J0\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u0012\u0010\b\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\u0005R\u0012\u0010\n\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005R\u0012\u0010\f\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0012À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/PerfCounters;", "", "count", "", "getCount", "()J", "memory", "getMemory", "threadTime", "getThreadTime", "threadUserTime", "getThreadUserTime", "time", "getTime", "addMeasurement", "", "thread", "threadUser", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface PerfCounters {
    void addMeasurement(long time, long thread, long threadUser, long memory);

    long getCount();

    long getMemory();

    long getThreadTime();

    long getThreadUserTime();

    long getTime();

    static /* synthetic */ void addMeasurement$default(PerfCounters perfCounters, long j, long j2, long j3, long j4, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addMeasurement");
        }
        if ((i & 1) != 0) {
            j = 0;
        }
        if ((i & 2) != 0) {
            j2 = 0;
        }
        if ((i & 4) != 0) {
            j3 = 0;
        }
        if ((i & 8) != 0) {
            j4 = 0;
        }
        perfCounters.addMeasurement(j, j2, j3, j4);
    }
}
