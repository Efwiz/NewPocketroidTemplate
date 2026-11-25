package org.jctools.queues;

import org.jctools.util.UnsafeAccess;

/* JADX INFO: compiled from: SpscArrayQueue.java */
/* JADX INFO: loaded from: classes14.dex */
abstract class SpscArrayQueueProducerIndexFields<E> extends SpscArrayQueueL1Pad<E> {
    protected static final long P_INDEX_OFFSET = UnsafeAccess.fieldOffset(SpscArrayQueueProducerIndexFields.class, "producerIndex");
    protected long producerIndex;
    protected long producerLimit;

    SpscArrayQueueProducerIndexFields(int i) {
        super(i);
    }

    @Override // org.jctools.queues.IndexedQueueSizeUtil.IndexedQueue
    public final long lvProducerIndex() {
        return UnsafeAccess.UNSAFE.getLongVolatile(this, P_INDEX_OFFSET);
    }

    protected final void soProducerIndex(long j) {
        UnsafeAccess.UNSAFE.putOrderedLong(this, P_INDEX_OFFSET, j);
    }
}
