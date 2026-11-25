package org.jctools.queues;

import org.jctools.util.UnsafeAccess;

/* JADX INFO: compiled from: MpscArrayQueue.java */
/* JADX INFO: loaded from: classes14.dex */
abstract class MpscArrayQueueConsumerIndexField<E> extends MpscArrayQueueL2Pad<E> {
    private static final long C_INDEX_OFFSET = UnsafeAccess.fieldOffset(MpscArrayQueueConsumerIndexField.class, "consumerIndex");
    protected long consumerIndex;

    MpscArrayQueueConsumerIndexField(int i) {
        super(i);
    }

    protected final long lpConsumerIndex() {
        return this.consumerIndex;
    }

    @Override // org.jctools.queues.IndexedQueueSizeUtil.IndexedQueue
    public final long lvConsumerIndex() {
        return UnsafeAccess.UNSAFE.getLongVolatile(this, C_INDEX_OFFSET);
    }

    protected void soConsumerIndex(long j) {
        UnsafeAccess.UNSAFE.putOrderedLong(this, C_INDEX_OFFSET, j);
    }
}
