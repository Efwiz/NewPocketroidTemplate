package org.jctools.queues;

import org.jctools.util.UnsafeAccess;

/* JADX INFO: compiled from: BaseSpscLinkedArrayQueue.java */
/* JADX INFO: loaded from: classes14.dex */
abstract class BaseSpscLinkedArrayQueueConsumerField<E> extends BaseSpscLinkedArrayQueueConsumerColdFields<E> {
    private static final long C_INDEX_OFFSET = UnsafeAccess.fieldOffset(BaseSpscLinkedArrayQueueConsumerField.class, "consumerIndex");
    protected long consumerIndex;

    BaseSpscLinkedArrayQueueConsumerField() {
    }

    final void soConsumerIndex(long j) {
        UnsafeAccess.UNSAFE.putOrderedLong(this, C_INDEX_OFFSET, j);
    }

    @Override // org.jctools.queues.IndexedQueueSizeUtil.IndexedQueue
    public final long lvConsumerIndex() {
        return UnsafeAccess.UNSAFE.getLongVolatile(this, C_INDEX_OFFSET);
    }
}
