package org.jctools.queues.atomic;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.jctools.queues.MessagePassingQueue;
import org.jctools.util.PortableJvmInfo;
import org.jctools.util.RangeUtil;

/* JADX INFO: loaded from: classes14.dex */
public class MpmcAtomicArrayQueue<E> extends MpmcAtomicArrayQueueL3Pad<E> {
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

    public MpmcAtomicArrayQueue(int i) {
        super(RangeUtil.checkGreaterThanOrEqual(i, 2, "capacity"));
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public boolean offer(E e) {
        if (e == null) {
            throw null;
        }
        int i = this.mask;
        long j = i + 1;
        AtomicLongArray atomicLongArray = this.sequenceBuffer;
        long jLvConsumerIndex = Long.MIN_VALUE;
        while (true) {
            long jLvProducerIndex = lvProducerIndex();
            int iCalcSequenceOffset = calcSequenceOffset(jLvProducerIndex, i);
            long jLvSequence = lvSequence(atomicLongArray, iCalcSequenceOffset);
            if (jLvSequence < jLvProducerIndex) {
                long j2 = jLvProducerIndex - j;
                if (j2 >= jLvConsumerIndex) {
                    jLvConsumerIndex = lvConsumerIndex();
                    if (j2 >= jLvConsumerIndex) {
                        return false;
                    }
                }
                jLvSequence = jLvProducerIndex + 1;
            }
            if (jLvSequence <= jLvProducerIndex) {
                long j3 = 1 + jLvProducerIndex;
                if (casProducerIndex(jLvProducerIndex, j3)) {
                    soElement(this.buffer, calcElementOffset(jLvProducerIndex, i), e);
                    soSequence(atomicLongArray, iCalcSequenceOffset, j3);
                    return true;
                }
            }
        }
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public E poll() {
        AtomicLongArray atomicLongArray = this.sequenceBuffer;
        int i = this.mask;
        long jLvProducerIndex = -1;
        while (true) {
            long jLvConsumerIndex = lvConsumerIndex();
            int iCalcSequenceOffset = calcSequenceOffset(jLvConsumerIndex, i);
            long jLvSequence = lvSequence(atomicLongArray, iCalcSequenceOffset);
            long j = jLvConsumerIndex + 1;
            if (jLvSequence < j) {
                if (jLvConsumerIndex >= jLvProducerIndex) {
                    jLvProducerIndex = lvProducerIndex();
                    if (jLvConsumerIndex == jLvProducerIndex) {
                        return null;
                    }
                }
                jLvSequence = 2 + jLvConsumerIndex;
            }
            if (jLvSequence <= j && casConsumerIndex(jLvConsumerIndex, j)) {
                int iCalcElementOffset = calcElementOffset(jLvConsumerIndex, i);
                E e = (E) lpElement(this.buffer, iCalcElementOffset);
                soElement(this.buffer, iCalcElementOffset, null);
                soSequence(atomicLongArray, iCalcSequenceOffset, jLvConsumerIndex + ((long) i) + 1);
                return e;
            }
        }
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public E peek() {
        long jLvConsumerIndex;
        E e;
        do {
            jLvConsumerIndex = lvConsumerIndex();
            e = (E) lpElement(this.buffer, calcElementOffset(jLvConsumerIndex));
            if (e != null) {
                break;
            }
        } while (jLvConsumerIndex != lvProducerIndex());
        return e;
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public boolean relaxedOffer(E e) {
        if (e == null) {
            throw null;
        }
        int i = this.mask;
        AtomicLongArray atomicLongArray = this.sequenceBuffer;
        while (true) {
            long jLvProducerIndex = lvProducerIndex();
            int iCalcSequenceOffset = calcSequenceOffset(jLvProducerIndex, i);
            long jLvSequence = lvSequence(atomicLongArray, iCalcSequenceOffset);
            if (jLvSequence < jLvProducerIndex) {
                return false;
            }
            if (jLvSequence <= jLvProducerIndex) {
                long j = 1 + jLvProducerIndex;
                if (casProducerIndex(jLvProducerIndex, j)) {
                    soElement(this.buffer, calcElementOffset(jLvProducerIndex, i), e);
                    soSequence(atomicLongArray, iCalcSequenceOffset, j);
                    return true;
                }
            }
        }
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public E relaxedPoll() {
        AtomicLongArray atomicLongArray = this.sequenceBuffer;
        int i = this.mask;
        while (true) {
            long jLvConsumerIndex = lvConsumerIndex();
            int iCalcSequenceOffset = calcSequenceOffset(jLvConsumerIndex, i);
            long jLvSequence = lvSequence(atomicLongArray, iCalcSequenceOffset);
            long j = jLvConsumerIndex + 1;
            if (jLvSequence < j) {
                return null;
            }
            if (jLvSequence <= j && casConsumerIndex(jLvConsumerIndex, j)) {
                int iCalcElementOffset = calcElementOffset(jLvConsumerIndex, i);
                E e = (E) lpElement(this.buffer, iCalcElementOffset);
                soElement(this.buffer, iCalcElementOffset, null);
                soSequence(atomicLongArray, iCalcSequenceOffset, jLvConsumerIndex + ((long) i) + 1);
                return e;
            }
        }
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public E relaxedPeek() {
        return (E) lpElement(this.buffer, calcElementOffset(lvConsumerIndex()));
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

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jctools.queues.MessagePassingQueue
    public int drain(MessagePassingQueue.Consumer<E> consumer, int i) {
        long jLvConsumerIndex;
        int iCalcSequenceOffset;
        AtomicLongArray atomicLongArray = this.sequenceBuffer;
        int i2 = this.mask;
        AtomicReferenceArray<E> atomicReferenceArray = this.buffer;
        for (int i3 = 0; i3 < i; i3++) {
            while (true) {
                jLvConsumerIndex = lvConsumerIndex();
                iCalcSequenceOffset = calcSequenceOffset(jLvConsumerIndex, i2);
                long jLvSequence = lvSequence(atomicLongArray, iCalcSequenceOffset);
                long j = jLvConsumerIndex + 1;
                if (jLvSequence < j) {
                    return i3;
                }
                if (jLvSequence > j || !casConsumerIndex(jLvConsumerIndex, j)) {
                }
            }
            int iCalcElementOffset = calcElementOffset(jLvConsumerIndex, i2);
            Object objLpElement = lpElement(atomicReferenceArray, iCalcElementOffset);
            soElement(atomicReferenceArray, iCalcElementOffset, null);
            soSequence(atomicLongArray, iCalcSequenceOffset, jLvConsumerIndex + ((long) i2) + 1);
            consumer.accept(objLpElement);
        }
        return i;
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public int fill(MessagePassingQueue.Supplier<E> supplier, int i) {
        long jLvProducerIndex;
        int iCalcSequenceOffset;
        long j;
        AtomicLongArray atomicLongArray = this.sequenceBuffer;
        int i2 = this.mask;
        AtomicReferenceArray<E> atomicReferenceArray = this.buffer;
        for (int i3 = 0; i3 < i; i3++) {
            while (true) {
                jLvProducerIndex = lvProducerIndex();
                iCalcSequenceOffset = calcSequenceOffset(jLvProducerIndex, i2);
                long jLvSequence = lvSequence(atomicLongArray, iCalcSequenceOffset);
                if (jLvSequence < jLvProducerIndex) {
                    return i3;
                }
                if (jLvSequence <= jLvProducerIndex) {
                    j = 1 + jLvProducerIndex;
                    if (casProducerIndex(jLvProducerIndex, j)) {
                        break;
                    }
                }
            }
            soElement(atomicReferenceArray, calcElementOffset(jLvProducerIndex, i2), supplier.get());
            soSequence(atomicLongArray, iCalcSequenceOffset, j);
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
        while (true) {
            int iIdle = 0;
            while (exitCondition.keepRunning()) {
                if (fill(supplier, PortableJvmInfo.RECOMENDED_OFFER_BATCH) == 0) {
                    iIdle = waitStrategy.idle(iIdle);
                }
            }
            return;
        }
    }
}
