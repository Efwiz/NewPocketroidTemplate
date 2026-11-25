package org.jctools.queues.atomic;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/* JADX INFO: compiled from: SpscAtomicArrayQueue.java */
/* JADX INFO: loaded from: classes14.dex */
abstract class SpscAtomicArrayQueueProducerIndexFields<E> extends SpscAtomicArrayQueueL1Pad<E> {
    private static final AtomicLongFieldUpdater<SpscAtomicArrayQueueProducerIndexFields> P_INDEX_UPDATER = AtomicLongFieldUpdater.newUpdater(SpscAtomicArrayQueueProducerIndexFields.class, "producerIndex");
    protected volatile long producerIndex;
    protected long producerLimit;

    SpscAtomicArrayQueueProducerIndexFields(int i) {
        super(i);
    }

    @Override // org.jctools.queues.IndexedQueueSizeUtil.IndexedQueue
    public final long lvProducerIndex() {
        return this.producerIndex;
    }

    protected final void soProducerIndex(long j) {
        P_INDEX_UPDATER.lazySet(this, j);
    }
}
