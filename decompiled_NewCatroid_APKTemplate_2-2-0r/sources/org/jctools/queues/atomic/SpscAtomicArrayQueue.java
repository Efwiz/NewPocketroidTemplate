package org.jctools.queues.atomic;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.jctools.queues.MessagePassingQueue;

/* JADX INFO: loaded from: classes14.dex */
public class SpscAtomicArrayQueue<E> extends SpscAtomicArrayQueueL3Pad<E> {
    @Override // org.jctools.queues.atomic.AtomicReferenceArrayQueue, java.util.AbstractQueue, java.util.AbstractCollection, java.util.Collection, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    @Override // org.jctools.queues.atomic.AtomicReferenceArrayQueue, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public /* bridge */ /* synthetic */ Iterator iterator() {
        return super.iterator();
    }

    @Override // org.jctools.queues.atomic.AtomicReferenceArrayQueue, java.util.AbstractCollection
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public SpscAtomicArrayQueue(int i) {
        super(Math.max(i, 4));
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public boolean offer(E e) {
        if (e == null) {
            throw null;
        }
        AtomicReferenceArray<E> atomicReferenceArray = this.buffer;
        int i = this.mask;
        long j = this.producerIndex;
        if (j >= this.producerLimit && !offerSlowPath(atomicReferenceArray, i, j)) {
            return false;
        }
        soElement(atomicReferenceArray, calcElementOffset(j, i), e);
        soProducerIndex(j + 1);
        return true;
    }

    private boolean offerSlowPath(AtomicReferenceArray<E> atomicReferenceArray, int i, long j) {
        long j2 = ((long) this.lookAheadStep) + j;
        if (lvElement(atomicReferenceArray, calcElementOffset(j2, i)) != null) {
            return lvElement(atomicReferenceArray, calcElementOffset(j, i)) == null;
        }
        this.producerLimit = j2;
        return true;
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public E poll() {
        long j = this.consumerIndex;
        int iCalcElementOffset = calcElementOffset(j);
        AtomicReferenceArray<E> atomicReferenceArray = this.buffer;
        E e = (E) lvElement(atomicReferenceArray, iCalcElementOffset);
        if (e == null) {
            return null;
        }
        soElement(atomicReferenceArray, iCalcElementOffset, null);
        soConsumerIndex(j + 1);
        return e;
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public E peek() {
        return (E) lvElement(this.buffer, calcElementOffset(this.consumerIndex));
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
        AtomicReferenceArray<E> atomicReferenceArray = this.buffer;
        int i2 = this.mask;
        long j = this.consumerIndex;
        for (int i3 = 0; i3 < i; i3++) {
            long j2 = ((long) i3) + j;
            int iCalcElementOffset = calcElementOffset(j2, i2);
            Object objLvElement = lvElement(atomicReferenceArray, iCalcElementOffset);
            if (objLvElement == null) {
                return i3;
            }
            soElement(atomicReferenceArray, iCalcElementOffset, null);
            soConsumerIndex(j2 + 1);
            consumer.accept(objLvElement);
        }
        return i;
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public int fill(MessagePassingQueue.Supplier<E> supplier, int i) {
        int i2;
        int i3;
        AtomicReferenceArray<E> atomicReferenceArray = this.buffer;
        int i4 = this.mask;
        int i5 = this.lookAheadStep;
        long j = this.producerIndex;
        int i6 = 0;
        while (i6 < i) {
            long j2 = ((long) i6) + j;
            if (lvElement(atomicReferenceArray, calcElementOffset(((long) i5) + j2, i4)) == null) {
                int iMin = Math.min(i5, i - i6);
                int i7 = 0;
                while (i7 < iMin) {
                    long j3 = ((long) i7) + j2;
                    soElement(atomicReferenceArray, calcElementOffset(j3, i4), supplier.get());
                    soProducerIndex(j3 + 1);
                    i7++;
                    i6 = i6;
                    i5 = i5;
                }
                i2 = i5;
                i3 = i6 + (iMin - 1);
            } else {
                i2 = i5;
                int i8 = i6;
                int iCalcElementOffset = calcElementOffset(j2, i4);
                if (lvElement(atomicReferenceArray, iCalcElementOffset) != null) {
                    return i8;
                }
                soElement(atomicReferenceArray, iCalcElementOffset, supplier.get());
                soProducerIndex(j2 + 1);
                i3 = i8;
            }
            i6 = i3 + 1;
            i5 = i2;
        }
        return i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jctools.queues.MessagePassingQueue
    public void drain(MessagePassingQueue.Consumer<E> consumer, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        AtomicReferenceArray<E> atomicReferenceArray = this.buffer;
        int i = this.mask;
        long j = this.consumerIndex;
        int iIdle = 0;
        while (exitCondition.keepRunning()) {
            for (int i2 = 0; i2 < 4096; i2++) {
                int iCalcElementOffset = calcElementOffset(j, i);
                Object objLvElement = lvElement(atomicReferenceArray, iCalcElementOffset);
                if (objLvElement == null) {
                    iIdle = waitStrategy.idle(iIdle);
                } else {
                    j++;
                    soElement(atomicReferenceArray, iCalcElementOffset, null);
                    soConsumerIndex(j);
                    consumer.accept(objLvElement);
                    iIdle = 0;
                }
            }
        }
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public void fill(MessagePassingQueue.Supplier<E> supplier, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        AtomicReferenceArray<E> atomicReferenceArray = this.buffer;
        int i = this.mask;
        int i2 = this.lookAheadStep;
        long j = this.producerIndex;
        while (true) {
            int iIdle = 0;
            while (exitCondition.keepRunning()) {
                if (lvElement(atomicReferenceArray, calcElementOffset(((long) i2) + j, i)) == null) {
                    for (int i3 = 0; i3 < i2; i3++) {
                        int iCalcElementOffset = calcElementOffset(j, i);
                        j++;
                        soElement(atomicReferenceArray, iCalcElementOffset, supplier.get());
                        soProducerIndex(j);
                    }
                } else {
                    int iCalcElementOffset2 = calcElementOffset(j, i);
                    if (lvElement(atomicReferenceArray, iCalcElementOffset2) != null) {
                        iIdle = waitStrategy.idle(iIdle);
                    } else {
                        j++;
                        soElement(atomicReferenceArray, iCalcElementOffset2, supplier.get());
                        soProducerIndex(j);
                    }
                }
            }
            return;
        }
    }
}
