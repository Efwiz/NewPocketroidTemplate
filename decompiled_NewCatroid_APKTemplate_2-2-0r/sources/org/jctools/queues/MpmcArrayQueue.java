package org.jctools.queues;

import org.jctools.util.PortableJvmInfo;
import org.jctools.util.RangeUtil;
import org.jctools.util.UnsafeRefArrayAccess;

/* JADX INFO: loaded from: classes14.dex */
public class MpmcArrayQueue<E> extends MpmcArrayQueueL3Pad<E> {
    public MpmcArrayQueue(int i) {
        super(RangeUtil.checkGreaterThanOrEqual(i, 2, "capacity"));
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public boolean offer(E e) {
        if (e == null) {
            throw null;
        }
        long j = this.mask;
        long j2 = j + 1;
        long[] jArr = this.sequenceBuffer;
        long jLvConsumerIndex = Long.MIN_VALUE;
        while (true) {
            long jLvProducerIndex = lvProducerIndex();
            long jCalcSequenceOffset = calcSequenceOffset(jLvProducerIndex, j);
            long jLvSequence = lvSequence(jArr, jCalcSequenceOffset);
            if (jLvSequence < jLvProducerIndex) {
                long j3 = jLvProducerIndex - j2;
                if (j3 >= jLvConsumerIndex) {
                    jLvConsumerIndex = lvConsumerIndex();
                    if (j3 >= jLvConsumerIndex) {
                        return false;
                    }
                }
                jLvSequence = jLvProducerIndex + 1;
            }
            if (jLvSequence <= jLvProducerIndex) {
                long j4 = jLvProducerIndex + 1;
                if (casProducerIndex(jLvProducerIndex, j4)) {
                    UnsafeRefArrayAccess.soElement(this.buffer, calcElementOffset(jLvProducerIndex, j), e);
                    soSequence(jArr, jCalcSequenceOffset, j4);
                    return true;
                }
            }
            j2 = j2;
        }
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public E poll() {
        long[] jArr = this.sequenceBuffer;
        long j = this.mask;
        long jLvProducerIndex = -1;
        while (true) {
            long jLvConsumerIndex = lvConsumerIndex();
            long jCalcSequenceOffset = calcSequenceOffset(jLvConsumerIndex, j);
            long jLvSequence = lvSequence(jArr, jCalcSequenceOffset);
            long j2 = jLvConsumerIndex + 1;
            if (jLvSequence < j2) {
                if (jLvConsumerIndex >= jLvProducerIndex) {
                    jLvProducerIndex = lvProducerIndex();
                    if (jLvConsumerIndex == jLvProducerIndex) {
                        return null;
                    }
                }
                jLvSequence = 2 + jLvConsumerIndex;
            }
            if (jLvSequence <= j2 && casConsumerIndex(jLvConsumerIndex, j2)) {
                long jCalcElementOffset = calcElementOffset(jLvConsumerIndex, j);
                E e = (E) UnsafeRefArrayAccess.lpElement(this.buffer, jCalcElementOffset);
                UnsafeRefArrayAccess.soElement(this.buffer, jCalcElementOffset, null);
                soSequence(jArr, jCalcSequenceOffset, jLvConsumerIndex + j + 1);
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
            e = (E) UnsafeRefArrayAccess.lpElement(this.buffer, calcElementOffset(jLvConsumerIndex));
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
        long j = this.mask;
        long[] jArr = this.sequenceBuffer;
        while (true) {
            long jLvProducerIndex = lvProducerIndex();
            long jCalcSequenceOffset = calcSequenceOffset(jLvProducerIndex, j);
            long jLvSequence = lvSequence(jArr, jCalcSequenceOffset);
            if (jLvSequence < jLvProducerIndex) {
                return false;
            }
            if (jLvSequence <= jLvProducerIndex) {
                long j2 = 1 + jLvProducerIndex;
                if (casProducerIndex(jLvProducerIndex, j2)) {
                    UnsafeRefArrayAccess.soElement(this.buffer, calcElementOffset(jLvProducerIndex, j), e);
                    soSequence(jArr, jCalcSequenceOffset, j2);
                    return true;
                }
            }
        }
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public E relaxedPoll() {
        long[] jArr = this.sequenceBuffer;
        long j = this.mask;
        while (true) {
            long jLvConsumerIndex = lvConsumerIndex();
            long jCalcSequenceOffset = calcSequenceOffset(jLvConsumerIndex, j);
            long jLvSequence = lvSequence(jArr, jCalcSequenceOffset);
            long j2 = jLvConsumerIndex + 1;
            if (jLvSequence < j2) {
                return null;
            }
            if (jLvSequence <= j2 && casConsumerIndex(jLvConsumerIndex, j2)) {
                long jCalcElementOffset = calcElementOffset(jLvConsumerIndex, j);
                E e = (E) UnsafeRefArrayAccess.lpElement(this.buffer, jCalcElementOffset);
                UnsafeRefArrayAccess.soElement(this.buffer, jCalcElementOffset, null);
                soSequence(jArr, jCalcSequenceOffset, jLvConsumerIndex + j + 1);
                return e;
            }
        }
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public E relaxedPeek() {
        return (E) UnsafeRefArrayAccess.lpElement(this.buffer, calcElementOffset(lvConsumerIndex()));
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
        long jCalcSequenceOffset;
        long[] jArr = this.sequenceBuffer;
        long j = this.mask;
        E[] eArr = this.buffer;
        for (int i2 = 0; i2 < i; i2++) {
            while (true) {
                jLvConsumerIndex = lvConsumerIndex();
                jCalcSequenceOffset = calcSequenceOffset(jLvConsumerIndex, j);
                long jLvSequence = lvSequence(jArr, jCalcSequenceOffset);
                long j2 = jLvConsumerIndex + 1;
                if (jLvSequence < j2) {
                    return i2;
                }
                if (jLvSequence > j2 || !casConsumerIndex(jLvConsumerIndex, j2)) {
                }
            }
            long jCalcElementOffset = calcElementOffset(jLvConsumerIndex, j);
            Object objLpElement = UnsafeRefArrayAccess.lpElement(eArr, jCalcElementOffset);
            UnsafeRefArrayAccess.soElement(eArr, jCalcElementOffset, null);
            soSequence(jArr, jCalcSequenceOffset, 1 + jLvConsumerIndex + j);
            consumer.accept(objLpElement);
        }
        return i;
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public int fill(MessagePassingQueue.Supplier<E> supplier, int i) {
        long jLvProducerIndex;
        long jCalcSequenceOffset;
        long j;
        long[] jArr = this.sequenceBuffer;
        long j2 = this.mask;
        E[] eArr = this.buffer;
        for (int i2 = 0; i2 < i; i2++) {
            while (true) {
                jLvProducerIndex = lvProducerIndex();
                jCalcSequenceOffset = calcSequenceOffset(jLvProducerIndex, j2);
                long jLvSequence = lvSequence(jArr, jCalcSequenceOffset);
                if (jLvSequence < jLvProducerIndex) {
                    return i2;
                }
                if (jLvSequence <= jLvProducerIndex) {
                    j = 1 + jLvProducerIndex;
                    if (casProducerIndex(jLvProducerIndex, j)) {
                        break;
                    }
                }
            }
            UnsafeRefArrayAccess.soElement(eArr, calcElementOffset(jLvProducerIndex, j2), supplier.get());
            soSequence(jArr, jCalcSequenceOffset, j);
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
