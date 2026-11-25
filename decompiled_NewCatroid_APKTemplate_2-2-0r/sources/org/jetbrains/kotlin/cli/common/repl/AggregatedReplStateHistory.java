package org.jetbrains.kotlin.cli.common.repl;

import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.AbstractList;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AggregatedReplState.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\b\u0002\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00040\u00032\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00040\u00060\u0005B+\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00010\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ$\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u00062\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00010\u0006H\u0002J\b\u0010\u0016\u001a\u00020\u0013H\u0002J#\u0010\u0017\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00040\u00062\u0006\u0010\u0018\u001a\u00020\u000fH\u0096\u0002J\u001c\u0010\u0019\u001a\u0016\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0004\u0018\u00010\u0006H\u0016J$\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u001c2\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0004H\u0016J\u000e\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001fH\u0016J\u0016\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001c0\u001f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/AggregatedReplStateHistory;", "T1", "T2", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageHistory;", "Lkotlin/Pair;", "Lkotlin/collections/AbstractList;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplHistoryRecord;", "history1", "history2", "lock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "(Lorg/jetbrains/kotlin/cli/common/repl/IReplStageHistory;Lorg/jetbrains/kotlin/cli/common/repl/IReplStageHistory;Ljava/util/concurrent/locks/ReentrantReadWriteLock;)V", "getLock", "()Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "size", "", "getSize", "()I", "assertSameId", "", "r1", "r2", "assertSameSize", "get", "index", "pop", "push", "id", "Lorg/jetbrains/kotlin/cli/common/repl/ILineId;", "item", "reset", "", "resetTo", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class AggregatedReplStateHistory<T1, T2> extends AbstractList<ReplHistoryRecord<? extends Pair<? extends T1, ? extends T2>>> implements IReplStageHistory<Pair<? extends T1, ? extends T2>> {
    private final IReplStageHistory<T1> history1;
    private final IReplStageHistory<T2> history2;
    private final ReentrantReadWriteLock lock;

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof ReplHistoryRecord) {
            return contains((ReplHistoryRecord<? extends Pair<? extends Object, ? extends Object>>) obj);
        }
        return false;
    }

    public /* bridge */ boolean contains(ReplHistoryRecord<? extends Pair<? extends Object, ? extends Object>> replHistoryRecord) {
        return super.contains(replHistoryRecord);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int indexOf(Object obj) {
        if (obj instanceof ReplHistoryRecord) {
            return indexOf((ReplHistoryRecord<? extends Pair<? extends Object, ? extends Object>>) obj);
        }
        return -1;
    }

    public /* bridge */ int indexOf(ReplHistoryRecord<? extends Pair<? extends Object, ? extends Object>> replHistoryRecord) {
        return super.indexOf(replHistoryRecord);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int lastIndexOf(Object obj) {
        if (obj instanceof ReplHistoryRecord) {
            return lastIndexOf((ReplHistoryRecord<? extends Pair<? extends Object, ? extends Object>>) obj);
        }
        return -1;
    }

    public /* bridge */ int lastIndexOf(ReplHistoryRecord<? extends Pair<? extends Object, ? extends Object>> replHistoryRecord) {
        return super.lastIndexOf(replHistoryRecord);
    }

    public /* synthetic */ AggregatedReplStateHistory(IReplStageHistory iReplStageHistory, IReplStageHistory iReplStageHistory2, ReentrantReadWriteLock reentrantReadWriteLock, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(iReplStageHistory, iReplStageHistory2, (i & 4) != 0 ? new ReentrantReadWriteLock() : reentrantReadWriteLock);
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageHistory
    public ReentrantReadWriteLock getLock() {
        return this.lock;
    }

    public AggregatedReplStateHistory(IReplStageHistory<T1> history1, IReplStageHistory<T2> history2, ReentrantReadWriteLock lock) {
        Intrinsics.checkNotNullParameter(history1, "history1");
        Intrinsics.checkNotNullParameter(history2, "history2");
        Intrinsics.checkNotNullParameter(lock, "lock");
        this.history1 = history1;
        this.history2 = history2;
        this.lock = lock;
    }

    @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection
    public int getSize() {
        return Math.min(this.history1.size(), this.history2.size());
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageHistory
    public void push(ILineId id2, Pair<? extends T1, ? extends T2> item) {
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(item, "item");
        ReentrantReadWriteLock lock = getLock();
        ReentrantReadWriteLock.ReadLock lock2 = lock.readLock();
        int i = 0;
        int readHoldCount = lock.getWriteHoldCount() == 0 ? lock.getReadHoldCount() : 0;
        for (int i2 = 0; i2 < readHoldCount; i2++) {
            lock2.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            assertSameSize();
            this.history1.push(id2, item.getFirst());
            this.history2.push(id2, item.getSecond());
            Unit unit = Unit.INSTANCE;
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
        } finally {
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            writeLock.unlock();
        }
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public ReplHistoryRecord<Pair<T1, T2>> get(int index) {
        ReentrantReadWriteLock.ReadLock lock = getLock().readLock();
        lock.lock();
        try {
            assertSameSize();
            ReplHistoryRecord<? extends T1> replHistoryRecord = (ReplHistoryRecord) this.history1.get(index);
            ReplHistoryRecord<? extends T2> replHistoryRecord2 = (ReplHistoryRecord) this.history2.get(index);
            assertSameId(replHistoryRecord, replHistoryRecord2);
            return new ReplHistoryRecord<>(replHistoryRecord.getId(), TuplesKt.to(replHistoryRecord.getItem(), replHistoryRecord2.getItem()));
        } finally {
            lock.unlock();
        }
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageHistory
    public ReplHistoryRecord<Pair<T1, T2>> pop() {
        ReentrantReadWriteLock lock = getLock();
        ReentrantReadWriteLock.ReadLock lock2 = lock.readLock();
        int i = 0;
        int readHoldCount = lock.getWriteHoldCount() == 0 ? lock.getReadHoldCount() : 0;
        for (int i2 = 0; i2 < readHoldCount; i2++) {
            lock2.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            assertSameSize();
            ReplHistoryRecord<? extends T1> replHistoryRecordPop = this.history1.pop();
            ReplHistoryRecord<? extends T2> replHistoryRecordPop2 = this.history2.pop();
            if (replHistoryRecordPop == null && replHistoryRecordPop2 == null) {
                while (i < readHoldCount) {
                    lock2.lock();
                    i++;
                }
                writeLock.unlock();
                return null;
            }
            if (replHistoryRecordPop == null || replHistoryRecordPop2 == null) {
                throw new IllegalStateException("Aggregated history mismatch: " + replHistoryRecordPop + " vs " + replHistoryRecordPop2);
            }
            assertSameId(replHistoryRecordPop, replHistoryRecordPop2);
            ReplHistoryRecord<Pair<T1, T2>> replHistoryRecord = new ReplHistoryRecord<>(replHistoryRecordPop.getId(), TuplesKt.to(replHistoryRecordPop.getItem(), replHistoryRecordPop2.getItem()));
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            writeLock.unlock();
            return replHistoryRecord;
        } catch (Throwable th) {
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            writeLock.unlock();
            throw th;
        }
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageHistory
    public Iterable<ILineId> reset() {
        ReentrantReadWriteLock lock = getLock();
        ReentrantReadWriteLock.ReadLock lock2 = lock.readLock();
        int i = 0;
        int readHoldCount = lock.getWriteHoldCount() == 0 ? lock.getReadHoldCount() : 0;
        for (int i2 = 0; i2 < readHoldCount; i2++) {
            lock2.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            assertSameSize();
            List list = CollectionsKt.toList(this.history1.reset());
            List list2 = CollectionsKt.toList(this.history2.reset());
            if (!Intrinsics.areEqual(list, list2)) {
                throw new IllegalStateException("Aggregated history reset lines mismatch: " + list + " != " + list2);
            }
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            writeLock.unlock();
            return list;
        } catch (Throwable th) {
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            writeLock.unlock();
            throw th;
        }
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageHistory
    public Iterable<ILineId> resetTo(ILineId id2) {
        Intrinsics.checkNotNullParameter(id2, "id");
        ReentrantReadWriteLock lock = getLock();
        ReentrantReadWriteLock.ReadLock lock2 = lock.readLock();
        int i = 0;
        int readHoldCount = lock.getWriteHoldCount() == 0 ? lock.getReadHoldCount() : 0;
        for (int i2 = 0; i2 < readHoldCount; i2++) {
            lock2.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            assertSameSize();
            List list = CollectionsKt.toList(this.history1.resetTo(id2));
            List list2 = CollectionsKt.toList(this.history2.resetTo(id2));
            if (!Intrinsics.areEqual(list, list2)) {
                throw new IllegalStateException("Aggregated history reset lines mismatch: " + list + " != " + list2);
            }
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            writeLock.unlock();
            return list;
        } catch (Throwable th) {
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            writeLock.unlock();
            throw th;
        }
    }

    private final void assertSameSize() {
        if (this.history1.size() != this.history2.size()) {
            throw new IllegalStateException("Aggregated history sizes mismatch: " + this.history1.size() + " != " + this.history2.size());
        }
    }

    private final void assertSameId(ReplHistoryRecord<? extends T1> r1, ReplHistoryRecord<? extends T2> r2) {
        if (!Intrinsics.areEqual(r1.getId(), r2.getId())) {
            throw new IllegalStateException("Aggregated history mismatch: " + r1.getId() + " != " + r2.getId());
        }
    }
}
