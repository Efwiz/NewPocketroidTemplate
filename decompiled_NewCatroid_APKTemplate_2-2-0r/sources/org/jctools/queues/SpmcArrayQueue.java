package org.jctools.queues;

import org.jctools.util.PortableJvmInfo;
import org.jctools.util.UnsafeRefArrayAccess;

/* JADX INFO: loaded from: classes14.dex */
public class SpmcArrayQueue<E> extends SpmcArrayQueueL3Pad<E> {
    public SpmcArrayQueue(int i) {
        super(i);
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public boolean offer(E e) {
        if (e == null) {
            throw null;
        }
        E[] eArr = this.buffer;
        long j = this.mask;
        long jLvProducerIndex = lvProducerIndex();
        long jCalcElementOffset = calcElementOffset(jLvProducerIndex, j);
        if (UnsafeRefArrayAccess.lvElement(eArr, jCalcElementOffset) != null) {
            if (jLvProducerIndex - lvConsumerIndex() > j) {
                return false;
            }
            while (UnsafeRefArrayAccess.lvElement(eArr, jCalcElementOffset) != null) {
            }
        }
        UnsafeRefArrayAccess.spElement(eArr, jCalcElementOffset, e);
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

    private E removeElement(E[] eArr, long j, long j2) {
        long jCalcElementOffset = calcElementOffset(j, j2);
        E e = (E) UnsafeRefArrayAccess.lpElement(eArr, jCalcElementOffset);
        UnsafeRefArrayAccess.soElement(eArr, jCalcElementOffset, null);
        return e;
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public E peek() {
        E e;
        long j = this.mask;
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
            e = (E) UnsafeRefArrayAccess.lvElement(this.buffer, calcElementOffset(jLvConsumerIndex, j));
        } while (e == null);
        return e;
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public boolean relaxedOffer(E e) {
        if (e == null) {
            throw new NullPointerException("Null is not a valid element");
        }
        E[] eArr = this.buffer;
        long j = this.mask;
        long jLvProducerIndex = lvProducerIndex();
        long jCalcElementOffset = calcElementOffset(jLvProducerIndex, j);
        if (UnsafeRefArrayAccess.lvElement(eArr, jCalcElementOffset) != null) {
            return false;
        }
        UnsafeRefArrayAccess.spElement(eArr, jCalcElementOffset, e);
        soProducerIndex(jLvProducerIndex + 1);
        return true;
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public E relaxedPoll() {
        return poll();
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public E relaxedPeek() {
        return (E) UnsafeRefArrayAccess.lvElement(this.buffer, calcElementOffset(lvConsumerIndex(), this.mask));
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
        int iMin;
        E[] eArr = this.buffer;
        long j = this.mask;
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
        for (int i2 = 0; i2 < iMin; i2++) {
            consumer.accept(removeElement(eArr, jLvConsumerIndex + ((long) i2), j));
        }
        return iMin;
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public int fill(MessagePassingQueue.Supplier<E> supplier, int i) {
        E[] eArr = this.buffer;
        long j = this.mask;
        long j2 = this.producerIndex;
        for (int i2 = 0; i2 < i; i2++) {
            long jCalcElementOffset = calcElementOffset(j2, j);
            if (UnsafeRefArrayAccess.lvElement(eArr, jCalcElementOffset) != null) {
                return i2;
            }
            j2++;
            UnsafeRefArrayAccess.soElement(eArr, jCalcElementOffset, supplier.get());
            soProducerIndex(j2);
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
        E[] eArr = this.buffer;
        long j = this.mask;
        long j2 = this.producerIndex;
        int iIdle = 0;
        while (exitCondition.keepRunning()) {
            for (int i = 0; i < 4096; i++) {
                long jCalcElementOffset = calcElementOffset(j2, j);
                if (UnsafeRefArrayAccess.lvElement(eArr, jCalcElementOffset) != null) {
                    iIdle = waitStrategy.idle(iIdle);
                } else {
                    j2++;
                    UnsafeRefArrayAccess.soElement(eArr, jCalcElementOffset, supplier.get());
                    soProducerIndex(j2);
                    iIdle = 0;
                }
            }
        }
    }
}
