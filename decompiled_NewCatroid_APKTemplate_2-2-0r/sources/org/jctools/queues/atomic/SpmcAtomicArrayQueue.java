package org.jctools.queues.atomic;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.jctools.queues.MessagePassingQueue;
import org.jctools.util.PortableJvmInfo;

/* JADX INFO: loaded from: classes14.dex */
public class SpmcAtomicArrayQueue<E> extends SpmcAtomicArrayQueueL3Pad<E> {
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

    public SpmcAtomicArrayQueue(int i) {
        super(i);
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public boolean offer(E e) {
        if (e == null) {
            throw null;
        }
        AtomicReferenceArray<E> atomicReferenceArray = this.buffer;
        int i = this.mask;
        long jLvProducerIndex = lvProducerIndex();
        int iCalcElementOffset = calcElementOffset(jLvProducerIndex, i);
        if (lvElement(atomicReferenceArray, iCalcElementOffset) != null) {
            if (jLvProducerIndex - lvConsumerIndex() > i) {
                return false;
            }
            while (lvElement(atomicReferenceArray, iCalcElementOffset) != null) {
            }
        }
        spElement(atomicReferenceArray, iCalcElementOffset, e);
        soProducerIndex(jLvProducerIndex + 1);
        return true;
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public E poll() {
        long jLvConsumerIndex;
        long jLvProducerIndexCache = lvProducerIndexCache();
        do {
            jLvConsumerIndex = lvConsumerIndex();
            if (jLvConsumerIndex >= jLvProducerIndexCache) {
                jLvProducerIndexCache = lvProducerIndex();
                if (jLvConsumerIndex >= jLvProducerIndexCache) {
                    return null;
                }
                svProducerIndexCache(jLvProducerIndexCache);
            }
        } while (!casConsumerIndex(jLvConsumerIndex, 1 + jLvConsumerIndex));
        return removeElement(this.buffer, jLvConsumerIndex, this.mask);
    }

    private E removeElement(AtomicReferenceArray<E> atomicReferenceArray, long j, int i) {
        int iCalcElementOffset = calcElementOffset(j, i);
        E e = (E) lpElement(atomicReferenceArray, iCalcElementOffset);
        soElement(atomicReferenceArray, iCalcElementOffset, null);
        return e;
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public E peek() {
        E e;
        int i = this.mask;
        long jLvProducerIndexCache = lvProducerIndexCache();
        do {
            long jLvConsumerIndex = lvConsumerIndex();
            if (jLvConsumerIndex >= jLvProducerIndexCache) {
                long jLvProducerIndex = lvProducerIndex();
                if (jLvConsumerIndex >= jLvProducerIndex) {
                    return null;
                }
                svProducerIndexCache(jLvProducerIndex);
            }
            e = (E) lvElement(this.buffer, calcElementOffset(jLvConsumerIndex, i));
        } while (e == null);
        return e;
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public boolean relaxedOffer(E e) {
        if (e == null) {
            throw new NullPointerException("Null is not a valid element");
        }
        AtomicReferenceArray<E> atomicReferenceArray = this.buffer;
        int i = this.mask;
        long jLvProducerIndex = lvProducerIndex();
        int iCalcElementOffset = calcElementOffset(jLvProducerIndex, i);
        if (lvElement(atomicReferenceArray, iCalcElementOffset) != null) {
            return false;
        }
        spElement(atomicReferenceArray, iCalcElementOffset, e);
        soProducerIndex(jLvProducerIndex + 1);
        return true;
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public E relaxedPoll() {
        return poll();
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public E relaxedPeek() {
        return (E) lvElement(this.buffer, calcElementOffset(lvConsumerIndex(), this.mask));
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public int drain(MessagePassingQueue.Consumer<E> consumer) {
        int iCapacity = capacity();
        int i = 0;
        while (i < iCapacity) {
            int iDrain = drain(consumer, PortableJvmInfo.RECOMENDED_POLL_BATCH);
            if (iDrain == 0) {
                break;
            }
            i += iDrain;
        }
        return i;
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public int fill(MessagePassingQueue.Supplier<E> supplier) {
        return fill(supplier, capacity());
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public int drain(MessagePassingQueue.Consumer<E> consumer, int i) {
        long jLvConsumerIndex;
        int i2;
        int iMin;
        AtomicReferenceArray<E> atomicReferenceArray = this.buffer;
        int i3 = this.mask;
        long jLvProducerIndexCache = lvProducerIndexCache();
        do {
            jLvConsumerIndex = lvConsumerIndex();
            if (jLvConsumerIndex >= jLvProducerIndexCache) {
                jLvProducerIndexCache = lvProducerIndex();
                if (jLvConsumerIndex >= jLvProducerIndexCache) {
                    return 0;
                }
                svProducerIndexCache(jLvProducerIndexCache);
            }
            iMin = Math.min((int) (jLvProducerIndexCache - jLvConsumerIndex), i);
        } while (!casConsumerIndex(jLvConsumerIndex, ((long) iMin) + jLvConsumerIndex));
        for (i2 = 0; i2 < iMin; i2++) {
            consumer.accept(removeElement(atomicReferenceArray, ((long) i2) + jLvConsumerIndex, i3));
        }
        return iMin;
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public int fill(MessagePassingQueue.Supplier<E> supplier, int i) {
        AtomicReferenceArray<E> atomicReferenceArray = this.buffer;
        int i2 = this.mask;
        long j = this.producerIndex;
        for (int i3 = 0; i3 < i; i3++) {
            int iCalcElementOffset = calcElementOffset(j, i2);
            if (lvElement(atomicReferenceArray, iCalcElementOffset) != null) {
                return i3;
            }
            j++;
            soElement(atomicReferenceArray, iCalcElementOffset, supplier.get());
            soProducerIndex(j);
        }
        return i;
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public void drain(MessagePassingQueue.Consumer<E> consumer, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        while (true) {
            int iIdle = 0;
            while (exitCondition.keepRunning()) {
                if (drain(consumer, PortableJvmInfo.RECOMENDED_POLL_BATCH) == 0) {
                    iIdle = waitStrategy.idle(iIdle);
                }
            }
            return;
        }
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public void fill(MessagePassingQueue.Supplier<E> supplier, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        AtomicReferenceArray<E> atomicReferenceArray = this.buffer;
        int i = this.mask;
        long j = this.producerIndex;
        int iIdle = 0;
        while (exitCondition.keepRunning()) {
            for (int i2 = 0; i2 < 4096; i2++) {
                int iCalcElementOffset = calcElementOffset(j, i);
                if (lvElement(atomicReferenceArray, iCalcElementOffset) != null) {
                    iIdle = waitStrategy.idle(iIdle);
                } else {
                    j++;
                    soElement(atomicReferenceArray, iCalcElementOffset, supplier.get());
                    soProducerIndex(j);
                    iIdle = 0;
                }
            }
        }
    }
}
