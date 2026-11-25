package org.jetbrains.kotlin.cli.common.repl;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* JADX INFO: compiled from: ReplState.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001c\n\u0002\b\u0003\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00030\u0002J\u0010\u0010\b\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0003H\u0016J\u0010\u0010\t\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0003H&J\u001d\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u000fJ\u000e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\u0011H&J\u0016\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\r0\u00112\u0006\u0010\f\u001a\u00020\rH&J\u0018\u0010\u0013\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00032\u0006\u0010\f\u001a\u00020\rH\u0016R\u0012\u0010\u0004\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0014À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/IReplStageHistory;", ExifInterface.GPS_DIRECTION_TRUE, "", "Lorg/jetbrains/kotlin/cli/common/repl/ReplHistoryRecord;", "lock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "getLock", "()Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "peek", "pop", "push", "", "id", "Lorg/jetbrains/kotlin/cli/common/repl/ILineId;", "item", "(Lorg/jetbrains/kotlin/cli/common/repl/ILineId;Ljava/lang/Object;)V", "reset", "", "resetTo", "verifiedPop", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IReplStageHistory<T> extends List<ReplHistoryRecord<? extends T>>, KMappedMarker {
    ReentrantReadWriteLock getLock();

    ReplHistoryRecord<T> pop();

    void push(ILineId id2, T item);

    Iterable<ILineId> reset();

    Iterable<ILineId> resetTo(ILineId id2);

    default ReplHistoryRecord<T> peek() {
        ReentrantReadWriteLock.ReadLock lock = getLock().readLock();
        lock.lock();
        try {
            return (ReplHistoryRecord) CollectionsKt.lastOrNull((List) this);
        } finally {
            lock.unlock();
        }
    }

    default ReplHistoryRecord<T> verifiedPop(ILineId id2) {
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
            ReplHistoryRecord replHistoryRecord = (ReplHistoryRecord) CollectionsKt.lastOrNull((List) this);
            ReplHistoryRecord<T> replHistoryRecordPop = Intrinsics.areEqual(replHistoryRecord != null ? replHistoryRecord.getId() : null, id2) ? pop() : null;
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            return replHistoryRecordPop;
        } finally {
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            writeLock.unlock();
        }
    }
}
