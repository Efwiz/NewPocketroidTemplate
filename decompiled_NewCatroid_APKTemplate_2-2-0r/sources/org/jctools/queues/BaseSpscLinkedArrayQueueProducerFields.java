package org.jctools.queues;

import org.jctools.util.UnsafeAccess;

/* JADX INFO: compiled from: BaseSpscLinkedArrayQueue.java */
/* JADX INFO: loaded from: classes14.dex */
abstract class BaseSpscLinkedArrayQueueProducerFields<E> extends BaseSpscLinkedArrayQueueL2Pad<E> {
    private static final long P_INDEX_OFFSET = UnsafeAccess.fieldOffset(BaseSpscLinkedArrayQueueProducerFields.class, "producerIndex");
    protected long producerIndex;

    BaseSpscLinkedArrayQueueProducerFields() {
    }

    final void soProducerIndex(long j) {
        UnsafeAccess.UNSAFE.putOrderedLong(this, P_INDEX_OFFSET, j);
    }

    @Override // org.jctools.queues.IndexedQueueSizeUtil.IndexedQueue
    public final long lvProducerIndex() {
        return UnsafeAccess.UNSAFE.getLongVolatile(this, P_INDEX_OFFSET);
    }
}
