package org.jctools.queues.atomic;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/* JADX INFO: compiled from: SpscAtomicArrayQueue.java */
/* JADX INFO: loaded from: classes14.dex */
abstract class SpscAtomicArrayQueueConsumerIndexField<E> extends SpscAtomicArrayQueueL2Pad<E> {
    private static final AtomicLongFieldUpdater<SpscAtomicArrayQueueConsumerIndexField> C_INDEX_UPDATER = AtomicLongFieldUpdater.newUpdater(SpscAtomicArrayQueueConsumerIndexField.class, "consumerIndex");
    protected volatile long consumerIndex;

    SpscAtomicArrayQueueConsumerIndexField(int i) {
        super(i);
    }

    @Override // org.jctools.queues.IndexedQueueSizeUtil.IndexedQueue
    public final long lvConsumerIndex() {
        return this.consumerIndex;
    }

    protected final void soConsumerIndex(long j) {
        C_INDEX_UPDATER.lazySet(this, j);
    }
}
