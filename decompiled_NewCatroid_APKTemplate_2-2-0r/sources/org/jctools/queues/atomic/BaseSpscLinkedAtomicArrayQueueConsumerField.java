package org.jctools.queues.atomic;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/* JADX INFO: compiled from: BaseSpscLinkedAtomicArrayQueue.java */
/* JADX INFO: loaded from: classes14.dex */
abstract class BaseSpscLinkedAtomicArrayQueueConsumerField<E> extends BaseSpscLinkedAtomicArrayQueueConsumerColdFields<E> {
    private static final AtomicLongFieldUpdater<BaseSpscLinkedAtomicArrayQueueConsumerField> C_INDEX_UPDATER = AtomicLongFieldUpdater.newUpdater(BaseSpscLinkedAtomicArrayQueueConsumerField.class, "consumerIndex");
    protected volatile long consumerIndex;

    BaseSpscLinkedAtomicArrayQueueConsumerField() {
    }

    final void soConsumerIndex(long j) {
        C_INDEX_UPDATER.lazySet(this, j);
    }

    @Override // org.jctools.queues.IndexedQueueSizeUtil.IndexedQueue
    public final long lvConsumerIndex() {
        return this.consumerIndex;
    }
}
