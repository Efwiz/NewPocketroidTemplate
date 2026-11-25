package org.jctools.queues;

import org.jctools.util.PortableJvmInfo;

/* JADX INFO: loaded from: classes14.dex */
public class MpscUnboundedArrayQueue<E> extends BaseMpscLinkedArrayQueue<E> {
    long p0;
    long p1;
    long p10;
    long p11;
    long p12;
    long p13;
    long p14;
    long p15;
    long p16;
    long p17;
    long p2;
    long p3;
    long p4;
    long p5;
    long p6;
    long p7;

    @Override // org.jctools.queues.BaseMpscLinkedArrayQueue
    protected long availableInQueue(long j, long j2) {
        return 2147483647L;
    }

    @Override // org.jctools.queues.BaseMpscLinkedArrayQueue, org.jctools.queues.MessagePassingQueue
    public int capacity() {
        return -1;
    }

    @Override // org.jctools.queues.BaseMpscLinkedArrayQueue
    protected long getCurrentBufferCapacity(long j) {
        return j;
    }

    public MpscUnboundedArrayQueue(int i) {
        super(i);
    }

    @Override // org.jctools.queues.BaseMpscLinkedArrayQueue, org.jctools.queues.MessagePassingQueue
    public int drain(MessagePassingQueue.Consumer<E> consumer) {
        return drain(consumer, 4096);
    }

    @Override // org.jctools.queues.BaseMpscLinkedArrayQueue, org.jctools.queues.MessagePassingQueue
    public int fill(MessagePassingQueue.Supplier<E> supplier) {
        long j = 0;
        do {
            int iFill = fill(supplier, PortableJvmInfo.RECOMENDED_OFFER_BATCH);
            if (iFill == 0) {
                return (int) j;
            }
            j += (long) iFill;
        } while (j <= 4096);
        return (int) j;
    }

    @Override // org.jctools.queues.BaseMpscLinkedArrayQueue
    protected int getNextBufferSize(E[] eArr) {
        return LinkedArrayQueueUtil.length(eArr);
    }
}
