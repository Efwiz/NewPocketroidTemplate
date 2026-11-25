package org.jctools.queues;

import java.util.Iterator;
import org.jctools.util.PortableJvmInfo;
import org.jctools.util.UnsafeRefArrayAccess;

/* JADX INFO: loaded from: classes14.dex */
abstract class BaseSpscLinkedArrayQueue<E> extends BaseSpscLinkedArrayQueueProducerColdFields<E> implements MessagePassingQueue<E>, QueueProgressIndicators {
    private static final Object JUMP = new Object();

    abstract boolean offerColdPath(E[] eArr, long j, long j2, long j3, E e, MessagePassingQueue.Supplier<? extends E> supplier);

    BaseSpscLinkedArrayQueue() {
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

    protected final void soNext(E[] eArr, E[] eArr2) {
        UnsafeRefArrayAccess.soElement(eArr, LinkedArrayQueueUtil.nextArrayOffset(eArr), eArr2);
    }

    protected final E[] lvNextArrayAndUnlink(E[] eArr) {
        long jNextArrayOffset = LinkedArrayQueueUtil.nextArrayOffset(eArr);
        E[] eArr2 = (E[]) ((Object[]) UnsafeRefArrayAccess.lvElement(eArr, jNextArrayOffset));
        UnsafeRefArrayAccess.soElement(eArr, jNextArrayOffset, null);
        return eArr2;
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
            E[] eArr = this.producerBuffer;
            long j = this.producerIndex;
            long j2 = this.producerMask;
            long jCalcElementOffset = CircularArrayOffsetCalculator.calcElementOffset(j, j2);
            if (j < this.producerBufferLimit) {
                writeToQueue(eArr, supplier.get(), j, jCalcElementOffset);
            } else if (!offerColdPath(eArr, j2, j, jCalcElementOffset, null, supplier)) {
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
        E[] eArr = this.producerBuffer;
        long j = this.producerIndex;
        long j2 = this.producerMask;
        long jCalcElementOffset = CircularArrayOffsetCalculator.calcElementOffset(j, j2);
        if (j < this.producerBufferLimit) {
            writeToQueue(eArr, e, j, jCalcElementOffset);
            return true;
        }
        return offerColdPath(eArr, j2, j, jCalcElementOffset, e, null);
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public E poll() {
        E[] eArr = this.consumerBuffer;
        long j = this.consumerIndex;
        long jCalcElementOffset = CircularArrayOffsetCalculator.calcElementOffset(j, this.consumerMask);
        E e = (E) UnsafeRefArrayAccess.lvElement(eArr, jCalcElementOffset);
        boolean z = e == JUMP;
        if (e == null || z) {
            if (z) {
                return newBufferPoll(eArr, j);
            }
            return null;
        }
        soConsumerIndex(j + 1);
        UnsafeRefArrayAccess.soElement(eArr, jCalcElementOffset, null);
        return e;
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public E peek() {
        E[] eArr = this.consumerBuffer;
        long j = this.consumerIndex;
        E e = (E) UnsafeRefArrayAccess.lvElement(eArr, CircularArrayOffsetCalculator.calcElementOffset(j, this.consumerMask));
        return e == JUMP ? newBufferPeek(eArr, j) : e;
    }

    final void linkOldToNew(long j, E[] eArr, long j2, E[] eArr2, long j3, E e) {
        UnsafeRefArrayAccess.soElement(eArr2, j3, e);
        soNext(eArr, eArr2);
        UnsafeRefArrayAccess.soElement(eArr, j2, JUMP);
        soProducerIndex(j + 1);
    }

    final void writeToQueue(E[] eArr, E e, long j, long j2) {
        UnsafeRefArrayAccess.soElement(eArr, j2, e);
        soProducerIndex(j + 1);
    }

    private E newBufferPeek(E[] eArr, long j) {
        E[] eArrLvNextArrayAndUnlink = lvNextArrayAndUnlink(eArr);
        this.consumerBuffer = eArrLvNextArrayAndUnlink;
        long length = LinkedArrayQueueUtil.length(eArrLvNextArrayAndUnlink) - 2;
        this.consumerMask = length;
        return (E) UnsafeRefArrayAccess.lvElement(eArrLvNextArrayAndUnlink, CircularArrayOffsetCalculator.calcElementOffset(j, length));
    }

    private E newBufferPoll(E[] eArr, long j) {
        E[] eArrLvNextArrayAndUnlink = lvNextArrayAndUnlink(eArr);
        this.consumerBuffer = eArrLvNextArrayAndUnlink;
        long length = LinkedArrayQueueUtil.length(eArrLvNextArrayAndUnlink) - 2;
        this.consumerMask = length;
        long jCalcElementOffset = CircularArrayOffsetCalculator.calcElementOffset(j, length);
        E e = (E) UnsafeRefArrayAccess.lvElement(eArrLvNextArrayAndUnlink, jCalcElementOffset);
        if (e == null) {
            throw new IllegalStateException("new buffer must have at least one element");
        }
        soConsumerIndex(j + 1);
        UnsafeRefArrayAccess.soElement(eArrLvNextArrayAndUnlink, jCalcElementOffset, null);
        return e;
    }
}
