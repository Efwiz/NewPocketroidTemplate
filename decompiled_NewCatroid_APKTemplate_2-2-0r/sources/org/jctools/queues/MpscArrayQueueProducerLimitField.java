package org.jctools.queues;

import org.jctools.util.UnsafeAccess;

/* JADX INFO: compiled from: MpscArrayQueue.java */
/* JADX INFO: loaded from: classes14.dex */
abstract class MpscArrayQueueProducerLimitField<E> extends MpscArrayQueueMidPad<E> {
    private static final long P_LIMIT_OFFSET = UnsafeAccess.fieldOffset(MpscArrayQueueProducerLimitField.class, "producerLimit");
    private volatile long producerLimit;

    MpscArrayQueueProducerLimitField(int i) {
        super(i);
        this.producerLimit = i;
    }

    protected final long lvProducerLimit() {
        return this.producerLimit;
    }

    protected final void soProducerLimit(long j) {
        UnsafeAccess.UNSAFE.putOrderedLong(this, P_LIMIT_OFFSET, j);
    }
}
