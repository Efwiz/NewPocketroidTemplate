package org.jctools.queues.atomic;

import java.util.concurrent.atomic.AtomicReferenceArray;
import org.jctools.queues.MessagePassingQueue;
import org.jctools.util.Pow2;

/* JADX INFO: loaded from: classes14.dex */
public class SpscUnboundedAtomicArrayQueue<E> extends BaseSpscLinkedAtomicArrayQueue<E> {
    @Override // org.jctools.queues.MessagePassingQueue
    public int capacity() {
        return -1;
    }

    @Override // org.jctools.queues.atomic.BaseSpscLinkedAtomicArrayQueue, org.jctools.queues.QueueProgressIndicators
    public /* bridge */ /* synthetic */ long currentConsumerIndex() {
        return super.currentConsumerIndex();
    }

    @Override // org.jctools.queues.atomic.BaseSpscLinkedAtomicArrayQueue, org.jctools.queues.QueueProgressIndicators
    public /* bridge */ /* synthetic */ long currentProducerIndex() {
        return super.currentProducerIndex();
    }

    @Override // org.jctools.queues.atomic.BaseSpscLinkedAtomicArrayQueue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ int drain(MessagePassingQueue.Consumer consumer) {
        return super.drain(consumer);
    }

    @Override // org.jctools.queues.atomic.BaseSpscLinkedAtomicArrayQueue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ int drain(MessagePassingQueue.Consumer consumer, int i) {
        return super.drain(consumer, i);
    }

    @Override // org.jctools.queues.atomic.BaseSpscLinkedAtomicArrayQueue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ void drain(MessagePassingQueue.Consumer consumer, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        super.drain(consumer, waitStrategy, exitCondition);
    }

    @Override // org.jctools.queues.atomic.BaseSpscLinkedAtomicArrayQueue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ int fill(MessagePassingQueue.Supplier supplier) {
        return super.fill(supplier);
    }

    @Override // org.jctools.queues.atomic.BaseSpscLinkedAtomicArrayQueue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ int fill(MessagePassingQueue.Supplier supplier, int i) {
        return super.fill(supplier, i);
    }

    @Override // org.jctools.queues.atomic.BaseSpscLinkedAtomicArrayQueue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ void fill(MessagePassingQueue.Supplier supplier, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        super.fill(supplier, waitStrategy, exitCondition);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jctools.queues.atomic.BaseSpscLinkedAtomicArrayQueue, java.util.Queue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ boolean offer(Object obj) {
        return super.offer(obj);
    }

    @Override // org.jctools.queues.atomic.BaseSpscLinkedAtomicArrayQueue, java.util.Queue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ Object peek() {
        return super.peek();
    }

    @Override // org.jctools.queues.atomic.BaseSpscLinkedAtomicArrayQueue, java.util.Queue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ Object poll() {
        return super.poll();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jctools.queues.atomic.BaseSpscLinkedAtomicArrayQueue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ boolean relaxedOffer(Object obj) {
        return super.relaxedOffer(obj);
    }

    @Override // org.jctools.queues.atomic.BaseSpscLinkedAtomicArrayQueue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ Object relaxedPeek() {
        return super.relaxedPeek();
    }

    @Override // org.jctools.queues.atomic.BaseSpscLinkedAtomicArrayQueue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ Object relaxedPoll() {
        return super.relaxedPoll();
    }

    @Override // org.jctools.queues.atomic.BaseSpscLinkedAtomicArrayQueue, java.util.AbstractCollection
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public SpscUnboundedAtomicArrayQueue(int i) {
        int iMax = Math.max(Pow2.roundToPowerOfTwo(i), 16);
        long j = iMax - 1;
        AtomicReferenceArray<E> atomicReferenceArrayAllocate = LinkedAtomicArrayQueueUtil.allocate(iMax + 1);
        this.producerBuffer = atomicReferenceArrayAllocate;
        this.producerMask = j;
        this.consumerBuffer = atomicReferenceArrayAllocate;
        this.consumerMask = j;
        this.producerBufferLimit = j - 1;
    }

    @Override // org.jctools.queues.atomic.BaseSpscLinkedAtomicArrayQueue
    final boolean offerColdPath(AtomicReferenceArray<E> atomicReferenceArray, long j, long j2, int i, E e, MessagePassingQueue.Supplier<? extends E> supplier) {
        long j3 = ((j + 1) / 4) + j2;
        if (LinkedAtomicArrayQueueUtil.lvElement(atomicReferenceArray, LinkedAtomicArrayQueueUtil.calcElementOffset(j3, j)) == null) {
            this.producerBufferLimit = j3 - 1;
            writeToQueue(atomicReferenceArray, e == null ? supplier.get() : e, j2, i);
            return true;
        }
        if (LinkedAtomicArrayQueueUtil.lvElement(atomicReferenceArray, LinkedAtomicArrayQueueUtil.calcElementOffset(j2 + 1, j)) == null) {
            writeToQueue(atomicReferenceArray, e == null ? supplier.get() : e, j2, i);
            return true;
        }
        AtomicReferenceArray<E> atomicReferenceArrayAllocate = LinkedAtomicArrayQueueUtil.allocate((int) (2 + j));
        this.producerBuffer = atomicReferenceArrayAllocate;
        this.producerBufferLimit = (j + j2) - 1;
        linkOldToNew(j2, atomicReferenceArray, i, atomicReferenceArrayAllocate, i, e == null ? supplier.get() : e);
        return true;
    }
}
