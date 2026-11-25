package org.jctools.queues.atomic;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.jctools.queues.IndexedQueueSizeUtil;
import org.jctools.queues.MessagePassingQueue;
import org.jctools.queues.MessagePassingQueueUtil;
import org.jctools.queues.QueueProgressIndicators;
import org.jctools.util.PortableJvmInfo;

/* JADX INFO: loaded from: classes14.dex */
abstract class BaseSpscLinkedAtomicArrayQueue<E> extends BaseSpscLinkedAtomicArrayQueueProducerColdFields<E> implements MessagePassingQueue<E>, QueueProgressIndicators {
    private static final Object JUMP = new Object();

    abstract boolean offerColdPath(AtomicReferenceArray<E> atomicReferenceArray, long j, long j2, int i, E e, MessagePassingQueue.Supplier<? extends E> supplier);

    BaseSpscLinkedAtomicArrayQueue() {
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, org.jctools.queues.MessagePassingQueue
    public final int size() {
        return IndexedQueueSizeUtil.size(this);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, org.jctools.queues.MessagePassingQueue
    public final boolean isEmpty() {
        return IndexedQueueSizeUtil.isEmpty(this);
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        return getClass().getName();
    }

    @Override // org.jctools.queues.QueueProgressIndicators
    public long currentProducerIndex() {
        return lvProducerIndex();
    }

    @Override // org.jctools.queues.QueueProgressIndicators
    public long currentConsumerIndex() {
        return lvConsumerIndex();
    }

    protected final void soNext(AtomicReferenceArray<E> atomicReferenceArray, AtomicReferenceArray<E> atomicReferenceArray2) {
        LinkedAtomicArrayQueueUtil.soElement(atomicReferenceArray, LinkedAtomicArrayQueueUtil.nextArrayOffset(atomicReferenceArray), atomicReferenceArray2);
    }

    protected final AtomicReferenceArray<E> lvNextArrayAndUnlink(AtomicReferenceArray<E> atomicReferenceArray) {
        int iNextArrayOffset = LinkedAtomicArrayQueueUtil.nextArrayOffset(atomicReferenceArray);
        AtomicReferenceArray<E> atomicReferenceArray2 = (AtomicReferenceArray) LinkedAtomicArrayQueueUtil.lvElement(atomicReferenceArray, iNextArrayOffset);
        LinkedAtomicArrayQueueUtil.soElement(atomicReferenceArray, iNextArrayOffset, null);
        return atomicReferenceArray2;
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
        return MessagePassingQueueUtil.drain(this, consumer);
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public int fill(MessagePassingQueue.Supplier<E> supplier) {
        int iCapacity = capacity();
        long j = 0;
        do {
            int iFill = fill(supplier, PortableJvmInfo.RECOMENDED_OFFER_BATCH);
            if (iFill == 0) {
                return (int) j;
            }
            j += (long) iFill;
        } while (j <= iCapacity);
        return (int) j;
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public int drain(MessagePassingQueue.Consumer<E> consumer, int i) {
        return MessagePassingQueueUtil.drain(this, consumer, i);
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public int fill(MessagePassingQueue.Supplier<E> supplier, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            AtomicReferenceArray<E> atomicReferenceArray = this.producerBuffer;
            long j = this.producerIndex;
            long j2 = this.producerMask;
            int iCalcElementOffset = LinkedAtomicArrayQueueUtil.calcElementOffset(j, j2);
            if (j < this.producerBufferLimit) {
                writeToQueue(atomicReferenceArray, supplier.get(), j, iCalcElementOffset);
            } else if (!offerColdPath(atomicReferenceArray, j2, j, iCalcElementOffset, null, supplier)) {
                return i2;
            }
        }
        return i;
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public void drain(MessagePassingQueue.Consumer<E> consumer, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        MessagePassingQueueUtil.drain(this, consumer, waitStrategy, exitCondition);
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public void fill(MessagePassingQueue.Supplier<E> supplier, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        while (exitCondition.keepRunning()) {
            while (fill(supplier, PortableJvmInfo.RECOMENDED_OFFER_BATCH) != 0 && exitCondition.keepRunning()) {
            }
            int iIdle = 0;
            while (exitCondition.keepRunning() && fill(supplier, PortableJvmInfo.RECOMENDED_OFFER_BATCH) == 0) {
                iIdle = waitStrategy.idle(iIdle);
            }
        }
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public boolean offer(E e) {
        if (e == null) {
            throw null;
        }
        AtomicReferenceArray<E> atomicReferenceArray = this.producerBuffer;
        long j = this.producerIndex;
        long j2 = this.producerMask;
        int iCalcElementOffset = LinkedAtomicArrayQueueUtil.calcElementOffset(j, j2);
        if (j < this.producerBufferLimit) {
            writeToQueue(atomicReferenceArray, e, j, iCalcElementOffset);
            return true;
        }
        return offerColdPath(atomicReferenceArray, j2, j, iCalcElementOffset, e, null);
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public E poll() {
        AtomicReferenceArray<E> atomicReferenceArray = this.consumerBuffer;
        long j = this.consumerIndex;
        int iCalcElementOffset = LinkedAtomicArrayQueueUtil.calcElementOffset(j, this.consumerMask);
        E e = (E) LinkedAtomicArrayQueueUtil.lvElement(atomicReferenceArray, iCalcElementOffset);
        boolean z = e == JUMP;
        if (e == null || z) {
            if (z) {
                return newBufferPoll(atomicReferenceArray, j);
            }
            return null;
        }
        soConsumerIndex(j + 1);
        LinkedAtomicArrayQueueUtil.soElement(atomicReferenceArray, iCalcElementOffset, null);
        return e;
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public E peek() {
        AtomicReferenceArray<E> atomicReferenceArray = this.consumerBuffer;
        long j = this.consumerIndex;
        E e = (E) LinkedAtomicArrayQueueUtil.lvElement(atomicReferenceArray, LinkedAtomicArrayQueueUtil.calcElementOffset(j, this.consumerMask));
        return e == JUMP ? newBufferPeek(atomicReferenceArray, j) : e;
    }

    final void linkOldToNew(long j, AtomicReferenceArray<E> atomicReferenceArray, int i, AtomicReferenceArray<E> atomicReferenceArray2, int i2, E e) {
        LinkedAtomicArrayQueueUtil.soElement(atomicReferenceArray2, i2, e);
        soNext(atomicReferenceArray, atomicReferenceArray2);
        LinkedAtomicArrayQueueUtil.soElement(atomicReferenceArray, i, JUMP);
        soProducerIndex(j + 1);
    }

    final void writeToQueue(AtomicReferenceArray<E> atomicReferenceArray, E e, long j, int i) {
        LinkedAtomicArrayQueueUtil.soElement(atomicReferenceArray, i, e);
        soProducerIndex(j + 1);
    }

    private E newBufferPeek(AtomicReferenceArray<E> atomicReferenceArray, long j) {
        AtomicReferenceArray<E> atomicReferenceArrayLvNextArrayAndUnlink = lvNextArrayAndUnlink(atomicReferenceArray);
        this.consumerBuffer = atomicReferenceArrayLvNextArrayAndUnlink;
        long length = LinkedAtomicArrayQueueUtil.length(atomicReferenceArrayLvNextArrayAndUnlink) - 2;
        this.consumerMask = length;
        return (E) LinkedAtomicArrayQueueUtil.lvElement(atomicReferenceArrayLvNextArrayAndUnlink, LinkedAtomicArrayQueueUtil.calcElementOffset(j, length));
    }

    private E newBufferPoll(AtomicReferenceArray<E> atomicReferenceArray, long j) {
        AtomicReferenceArray<E> atomicReferenceArrayLvNextArrayAndUnlink = lvNextArrayAndUnlink(atomicReferenceArray);
        this.consumerBuffer = atomicReferenceArrayLvNextArrayAndUnlink;
        long length = LinkedAtomicArrayQueueUtil.length(atomicReferenceArrayLvNextArrayAndUnlink) - 2;
        this.consumerMask = length;
        int iCalcElementOffset = LinkedAtomicArrayQueueUtil.calcElementOffset(j, length);
        E e = (E) LinkedAtomicArrayQueueUtil.lvElement(atomicReferenceArrayLvNextArrayAndUnlink, iCalcElementOffset);
        if (e == null) {
            throw new IllegalStateException("new buffer must have at least one element");
        }
        soConsumerIndex(j + 1);
        LinkedAtomicArrayQueueUtil.soElement(atomicReferenceArrayLvNextArrayAndUnlink, iCalcElementOffset, null);
        return e;
    }
}
