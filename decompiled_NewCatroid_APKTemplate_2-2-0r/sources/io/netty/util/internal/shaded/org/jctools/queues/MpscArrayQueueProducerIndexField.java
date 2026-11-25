package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

/* JADX INFO: compiled from: MpscArrayQueue.java */
/* JADX INFO: loaded from: classes11.dex */
abstract class MpscArrayQueueProducerIndexField<E> extends MpscArrayQueueL1Pad<E> {
    private static final long P_INDEX_OFFSET = UnsafeAccess.fieldOffset(MpscArrayQueueProducerIndexField.class, "producerIndex");
    private volatile long producerIndex;

    MpscArrayQueueProducerIndexField(int i) {
        super(i);
    }

    @Override // io.netty.util.internal.shaded.org.jctools.queues.IndexedQueueSizeUtil.IndexedQueue
    public final long lvProducerIndex() {
        return this.producerIndex;
    }

    final boolean casProducerIndex(long j, long j2) {
        return UnsafeAccess.UNSAFE.compareAndSwapLong(this, P_INDEX_OFFSET, j, j2);
    }
}
