package org.jetbrains.kotlin.daemon.common;

import java.util.concurrent.atomic.AtomicLong;
import kotlin.Metadata;

/* JADX INFO: compiled from: PerfUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J(\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\nH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\fR\u0014\u0010\u000f\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\fR\u0014\u0010\u0011\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\fR\u0014\u0010\u0013\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\f¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/SimplePerfCounters;", "Lorg/jetbrains/kotlin/daemon/common/PerfCounters;", "()V", "_count", "Ljava/util/concurrent/atomic/AtomicLong;", "_memory", "_threadTime", "_threadUserTime", "_time", "count", "", "getCount", "()J", "memory", "getMemory", "threadTime", "getThreadTime", "threadUserTime", "getThreadUserTime", "time", "getTime", "addMeasurement", "", "thread", "threadUser", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class SimplePerfCounters implements PerfCounters {
    private final AtomicLong _count = new AtomicLong(0);
    private final AtomicLong _time = new AtomicLong(0);
    private final AtomicLong _threadTime = new AtomicLong(0);
    private final AtomicLong _threadUserTime = new AtomicLong(0);
    private final AtomicLong _memory = new AtomicLong(0);

    @Override // org.jetbrains.kotlin.daemon.common.PerfCounters
    public long getCount() {
        return this._count.get();
    }

    @Override // org.jetbrains.kotlin.daemon.common.PerfCounters
    public long getTime() {
        return this._time.get();
    }

    @Override // org.jetbrains.kotlin.daemon.common.PerfCounters
    public long getThreadTime() {
        return this._threadTime.get();
    }

    @Override // org.jetbrains.kotlin.daemon.common.PerfCounters
    public long getThreadUserTime() {
        return this._threadUserTime.get();
    }

    @Override // org.jetbrains.kotlin.daemon.common.PerfCounters
    public long getMemory() {
        return this._memory.get();
    }

    @Override // org.jetbrains.kotlin.daemon.common.PerfCounters
    public void addMeasurement(long time, long thread, long threadUser, long memory) {
        this._count.incrementAndGet();
        this._time.addAndGet(time);
        this._threadTime.addAndGet(thread);
        this._threadUserTime.addAndGet(threadUser);
        this._memory.addAndGet(memory);
    }
}
