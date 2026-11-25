package org.jctools.queues.atomic;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/* JADX INFO: compiled from: SpmcAtomicArrayQueue.java */
/* JADX INFO: loaded from: classes14.dex */
abstract class SpmcAtomicArrayQueueProducerIndexField<E> extends SpmcAtomicArrayQueueL1Pad<E> {
    private static final AtomicLongFieldUpdater<SpmcAtomicArrayQueueProducerIndexField> P_INDEX_UPDATER = AtomicLongFieldUpdater.newUpdater(SpmcAtomicArrayQueueProducerIndexField.class, "producerIndex");
    protected volatile long producerIndex;

    @Override // org.jctools.queues.IndexedQueueSizeUtil.IndexedQueue
    public final long lvProducerIndex() {
        return this.producerIndex;
    }

    protected final void soProducerIndex(long j) {
        P_INDEX_UPDATER.lazySet(this, j);
    }

    SpmcAtomicArrayQueueProducerIndexField(int i) {
        super(i);
    }
}
