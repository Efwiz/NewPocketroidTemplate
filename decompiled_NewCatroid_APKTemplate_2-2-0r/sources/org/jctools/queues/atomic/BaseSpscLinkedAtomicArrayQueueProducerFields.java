package org.jctools.queues.atomic;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/* JADX INFO: compiled from: BaseSpscLinkedAtomicArrayQueue.java */
/* JADX INFO: loaded from: classes14.dex */
abstract class BaseSpscLinkedAtomicArrayQueueProducerFields<E> extends BaseSpscLinkedAtomicArrayQueueL2Pad<E> {
    private static final AtomicLongFieldUpdater<BaseSpscLinkedAtomicArrayQueueProducerFields> P_INDEX_UPDATER = AtomicLongFieldUpdater.newUpdater(BaseSpscLinkedAtomicArrayQueueProducerFields.class, "producerIndex");
    protected volatile long producerIndex;

    BaseSpscLinkedAtomicArrayQueueProducerFields() {
    }

    final void soProducerIndex(long j) {
        P_INDEX_UPDATER.lazySet(this, j);
    }

    @Override // org.jctools.queues.IndexedQueueSizeUtil.IndexedQueue
    public final long lvProducerIndex() {
        return this.producerIndex;
    }
}
