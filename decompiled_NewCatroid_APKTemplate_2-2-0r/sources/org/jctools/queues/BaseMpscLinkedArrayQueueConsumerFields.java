package org.jctools.queues;

import org.jctools.util.UnsafeAccess;

/* JADX INFO: compiled from: BaseMpscLinkedArrayQueue.java */
/* JADX INFO: loaded from: classes14.dex */
abstract class BaseMpscLinkedArrayQueueConsumerFields<E> extends BaseMpscLinkedArrayQueuePad2<E> {
    private static final long C_INDEX_OFFSET = UnsafeAccess.fieldOffset(BaseMpscLinkedArrayQueueConsumerFields.class, "consumerIndex");
    protected E[] consumerBuffer;
    protected long consumerIndex;
    protected long consumerMask;

    BaseMpscLinkedArrayQueueConsumerFields() {
    }

    @Override // org.jctools.queues.IndexedQueueSizeUtil.IndexedQueue
    public final long lvConsumerIndex() {
        return UnsafeAccess.UNSAFE.getLongVolatile(this, C_INDEX_OFFSET);
    }

    final void soConsumerIndex(long j) {
        UnsafeAccess.UNSAFE.putOrderedLong(this, C_INDEX_OFFSET, j);
    }
}
