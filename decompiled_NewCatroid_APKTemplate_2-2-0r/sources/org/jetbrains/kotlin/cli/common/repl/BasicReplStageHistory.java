package org.jetbrains.kotlin.cli.common.repl;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: BasicReplState.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00040\u0003B\u000f\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\u0010\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0004H\u0016J\u001d\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0016J\u000e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00140\u0018H\u0016J\u0016\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00140\u00182\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u0018\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u0014\u0018\u00010\u001b2\u0006\u0010\u0013\u001a\u00020\u0014H\u0004R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/BasicReplStageHistory;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageHistory;", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplHistoryRecord;", "lock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "(Ljava/util/concurrent/locks/ReentrantReadWriteLock;)V", "currentGeneration", "Ljava/util/concurrent/atomic/AtomicInteger;", "getCurrentGeneration", "()Ljava/util/concurrent/atomic/AtomicInteger;", "currentLineNumber", "getCurrentLineNumber", "getLock", "()Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "pop", "push", "", "id", "Lorg/jetbrains/kotlin/cli/common/repl/ILineId;", "item", "(Lorg/jetbrains/kotlin/cli/common/repl/ILineId;Ljava/lang/Object;)V", "reset", "", "resetTo", "tryResetTo", "", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class BasicReplStageHistory<T> extends ArrayList<ReplHistoryRecord<? extends T>> implements IReplStageHistory<T> {
    private final AtomicInteger currentGeneration;
    private final AtomicInteger currentLineNumber;
    private final ReentrantReadWriteLock lock;

    /* JADX WARN: Multi-variable type inference failed */
    public BasicReplStageHistory() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public BasicReplStageHistory(ReentrantReadWriteLock lock) {
        Intrinsics.checkNotNullParameter(lock, "lock");
        this.lock = lock;
        this.currentLineNumber = new AtomicInteger(0);
        this.currentGeneration = new AtomicInteger(1);
    }

    public /* synthetic */ BasicReplStageHistory(ReentrantReadWriteLock reentrantReadWriteLock, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new ReentrantReadWriteLock() : reentrantReadWriteLock);
    }

    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof ReplHistoryRecord) {
            return contains((ReplHistoryRecord<? extends Object>) obj);
        }
        return false;
    }

    public /* bridge */ boolean contains(ReplHistoryRecord<? extends Object> replHistoryRecord) {
        return super.contains((Object) replHistoryRecord);
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageHistory
    public ReentrantReadWriteLock getLock() {
        return this.lock;
    }

    public /* bridge */ int getSize() {
        return super.size();
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public final /* bridge */ int indexOf(Object obj) {
        if (obj instanceof ReplHistoryRecord) {
            return indexOf((ReplHistoryRecord<? extends Object>) obj);
        }
        return -1;
    }

    public /* bridge */ int indexOf(ReplHistoryRecord<? extends Object> replHistoryRecord) {
        return super.indexOf((Object) replHistoryRecord);
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public final /* bridge */ int lastIndexOf(Object obj) {
        if (obj instanceof ReplHistoryRecord) {
            return lastIndexOf((ReplHistoryRecord<? extends Object>) obj);
        }
        return -1;
    }

    public /* bridge */ int lastIndexOf(ReplHistoryRecord<? extends Object> replHistoryRecord) {
        return super.lastIndexOf((Object) replHistoryRecord);
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public final /* bridge */ ReplHistoryRecord<T> remove(int i) {
        return (ReplHistoryRecord<T>) removeAt(i);
    }

    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ boolean remove(Object obj) {
        if (obj instanceof ReplHistoryRecord) {
            return remove((ReplHistoryRecord<? extends Object>) obj);
        }
        return false;
    }

    public /* bridge */ boolean remove(ReplHistoryRecord<? extends Object> replHistoryRecord) {
        return super.remove((Object) replHistoryRecord);
    }

    public /* bridge */ ReplHistoryRecord<Object> removeAt(int i) {
        return (ReplHistoryRecord) super.remove(i);
    }

    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ int size() {
        return getSize();
    }

    public final AtomicInteger getCurrentLineNumber() {
        return this.currentLineNumber;
    }

    public final AtomicInteger getCurrentGeneration() {
        return this.currentGeneration;
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageHistory
    public void push(ILineId id2, T item) {
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
            add(new ReplHistoryRecord(id2, item));
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

    @Override // org.jetbrains.kotlin.cli.common.repl.IReplStageHistory
    public ReplHistoryRecord<T> pop() {
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
            ReplHistoryRecord<T> replHistoryRecordRemove = isEmpty() ? null : remove(CollectionsKt.getLastIndex(this));
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            return replHistoryRecordRemove;
        } finally {
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            writeLock.unlock();
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
            BasicReplStageHistory<T> basicReplStageHistory = this;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(basicReplStageHistory, 10));
            Iterator<T> it = basicReplStageHistory.iterator();
            while (it.hasNext()) {
                arrayList.add(((ReplHistoryRecord) it.next()).getId());
            }
            clear();
            this.currentGeneration.incrementAndGet();
            this.currentLineNumber.set(0);
            ArrayList arrayList2 = arrayList;
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            return arrayList2;
        } finally {
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            writeLock.unlock();
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
            List<ILineId> listTryResetTo = tryResetTo(id2);
            if (listTryResetTo == null) {
                throw new NoSuchElementException("Cannot reset to non-existent line " + id2.getNo());
            }
            List<ILineId> list = listTryResetTo;
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

    protected final List<ILineId> tryResetTo(ILineId id2) {
        Intrinsics.checkNotNullParameter(id2, "id");
        BasicReplStageHistory<T> basicReplStageHistory = this;
        Iterator<ReplHistoryRecord<? extends T>> it = basicReplStageHistory.iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            }
            if (Intrinsics.areEqual(it.next().getId(), id2)) {
                break;
            }
            i++;
        }
        if (i < 0) {
            return null;
        }
        if (i < CollectionsKt.getLastIndex(basicReplStageHistory)) {
            int i2 = i + 1;
            List<ILineId> list = SequencesKt.toList(SequencesKt.map(SequencesKt.drop(CollectionsKt.asSequence(this), i2), new Function1<ReplHistoryRecord<? extends T>, ILineId>() { // from class: org.jetbrains.kotlin.cli.common.repl.BasicReplStageHistory$tryResetTo$removed$1
                @Override // kotlin.jvm.functions.Function1
                public final ILineId invoke(ReplHistoryRecord<? extends T> it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    return it2.getId();
                }
            }));
            removeRange(i2, size());
            this.currentGeneration.incrementAndGet();
            ILineId iLineId = (ILineId) CollectionsKt.lastOrNull((List) list);
            if (iLineId == null) {
                return list;
            }
            this.currentLineNumber.set(iLineId.getNo());
            return list;
        }
        this.currentGeneration.incrementAndGet();
        this.currentLineNumber.set(id2.getNo() + 1);
        return CollectionsKt.emptyList();
    }
}
