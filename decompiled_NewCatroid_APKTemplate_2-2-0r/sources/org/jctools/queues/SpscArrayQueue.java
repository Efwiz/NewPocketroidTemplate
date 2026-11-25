package org.jctools.queues;

import org.jctools.util.UnsafeRefArrayAccess;

/* JADX INFO: loaded from: classes14.dex */
public class SpscArrayQueue<E> extends SpscArrayQueueL3Pad<E> {
    public SpscArrayQueue(int i) {
        super(Math.max(i, 4));
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public boolean offer(E e) {
        if (e == null) {
            throw null;
        }
        E[] eArr = this.buffer;
        long j = this.mask;
        long j2 = this.producerIndex;
        if (j2 >= this.producerLimit && !offerSlowPath(eArr, j, j2)) {
            return false;
        }
        UnsafeRefArrayAccess.soElement(eArr, calcElementOffset(j2, j), e);
        soProducerIndex(j2 + 1);
        return true;
    }

    private boolean offerSlowPath(E[] eArr, long j, long j2) {
        long j3 = ((long) this.lookAheadStep) + j2;
        if (UnsafeRefArrayAccess.lvElement(eArr, calcElementOffset(j3, j)) != null) {
            return UnsafeRefArrayAccess.lvElement(eArr, calcElementOffset(j2, j)) == null;
        }
        this.producerLimit = j3;
        return true;
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public E poll() {
        long j = this.consumerIndex;
        long jCalcElementOffset = calcElementOffset(j);
        E[] eArr = this.buffer;
        E e = (E) UnsafeRefArrayAccess.lvElement(eArr, jCalcElementOffset);
        if (e == null) {
            return null;
        }
        UnsafeRefArrayAccess.soElement(eArr, jCalcElementOffset, null);
        soConsumerIndex(j + 1);
        return e;
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public E peek() {
        return (E) UnsafeRefArrayAccess.lvElement(this.buffer, calcElementOffset(this.consumerIndex));
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public boolean relaxedOffer(E e) {
        return offer(e);
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public E relaxedPoll() {
        return poll();
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public E relaxedPeek() {
        return peek();
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public int drain(MessagePassingQueue.Consumer<E> consumer) {
        return drain(consumer, capacity());
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public int fill(MessagePassingQueue.Supplier<E> supplier) {
        return fill(supplier, capacity());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jctools.queues.MessagePassingQueue
    public int drain(MessagePassingQueue.Consumer<E> consumer, int i) {
        E[] eArr = this.buffer;
        long j = this.mask;
        long j2 = this.consumerIndex;
        for (int i2 = 0; i2 < i; i2++) {
            long j3 = ((long) i2) + j2;
            long jCalcElementOffset = calcElementOffset(j3, j);
            Object objLvElement = UnsafeRefArrayAccess.lvElement(eArr, jCalcElementOffset);
            if (objLvElement == null) {
                return i2;
            }
            UnsafeRefArrayAccess.soElement(eArr, jCalcElementOffset, null);
            soConsumerIndex(j3 + 1);
            consumer.accept(objLvElement);
        }
        return i;
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public int fill(MessagePassingQueue.Supplier<E> supplier, int i) {
        int i2;
        int i3;
        E[] eArr = this.buffer;
        long j = this.mask;
        int i4 = this.lookAheadStep;
        long j2 = this.producerIndex;
        int i5 = 0;
        while (i5 < i) {
            long j3 = ((long) i5) + j2;
            if (UnsafeRefArrayAccess.lvElement(eArr, calcElementOffset(((long) i4) + j3, j)) == null) {
                int iMin = Math.min(i4, i - i5);
                int i6 = 0;
                while (i6 < iMin) {
                    long j4 = ((long) i6) + j3;
                    UnsafeRefArrayAccess.soElement(eArr, calcElementOffset(j4, j), supplier.get());
                    soProducerIndex(j4 + 1);
                    i6++;
                    i5 = i5;
                    i4 = i4;
                }
                i2 = i4;
                i3 = i5 + (iMin - 1);
            } else {
                i2 = i4;
                int i7 = i5;
                long jCalcElementOffset = calcElementOffset(j3, j);
                if (UnsafeRefArrayAccess.lvElement(eArr, jCalcElementOffset) != null) {
                    return i7;
                }
                UnsafeRefArrayAccess.soElement(eArr, jCalcElementOffset, supplier.get());
                soProducerIndex(j3 + 1);
                i3 = i7;
            }
            i5 = i3 + 1;
            i4 = i2;
        }
        return i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jctools.queues.MessagePassingQueue
    public void drain(MessagePassingQueue.Consumer<E> consumer, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        E[] eArr = this.buffer;
        long j = this.mask;
        long j2 = this.consumerIndex;
        int iIdle = 0;
        while (exitCondition.keepRunning()) {
            for (int i = 0; i < 4096; i++) {
                long jCalcElementOffset = calcElementOffset(j2, j);
                Object objLvElement = UnsafeRefArrayAccess.lvElement(eArr, jCalcElementOffset);
                if (objLvElement == null) {
                    iIdle = waitStrategy.idle(iIdle);
                } else {
                    j2++;
                    UnsafeRefArrayAccess.soElement(eArr, jCalcElementOffset, null);
                    soConsumerIndex(j2);
                    consumer.accept(objLvElement);
                    iIdle = 0;
                }
            }
        }
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public void fill(MessagePassingQueue.Supplier<E> supplier, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        E[] eArr = this.buffer;
        long j = this.mask;
        int i = this.lookAheadStep;
        long j2 = this.producerIndex;
        while (true) {
            int iIdle = 0;
            while (exitCondition.keepRunning()) {
                if (UnsafeRefArrayAccess.lvElement(eArr, calcElementOffset(((long) i) + j2, j)) == null) {
                    for (int i2 = 0; i2 < i; i2++) {
                        long jCalcElementOffset = calcElementOffset(j2, j);
                        j2++;
                        UnsafeRefArrayAccess.soElement(eArr, jCalcElementOffset, supplier.get());
                        soProducerIndex(j2);
                    }
                } else {
                    long jCalcElementOffset2 = calcElementOffset(j2, j);
                    if (UnsafeRefArrayAccess.lvElement(eArr, jCalcElementOffset2) != null) {
                        iIdle = waitStrategy.idle(iIdle);
                    } else {
                        j2++;
                        UnsafeRefArrayAccess.soElement(eArr, jCalcElementOffset2, supplier.get());
                        soProducerIndex(j2);
                    }
                }
            }
            return;
        }
    }
}
