package org.jctools.queues;

import java.util.Iterator;
import org.jctools.util.PortableJvmInfo;

/* JADX INFO: loaded from: classes14.dex */
public class MpscCompoundQueue<E> extends MpscCompoundQueueConsumerQueueIndex<E> {
    long p01;
    long p02;
    long p03;
    long p04;
    long p05;
    long p06;
    long p07;
    long p10;
    long p11;
    long p12;
    long p13;
    long p14;
    long p15;
    long p16;
    long p17;

    public MpscCompoundQueue(int i) {
        this(i, PortableJvmInfo.CPUs);
    }

    public MpscCompoundQueue(int i, int i2) {
        super(i, i2);
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public boolean offer(E e) {
        if (e == null) {
            throw null;
        }
        int i = this.parallelQueuesMask;
        int id2 = (int) (Thread.currentThread().getId() & ((long) i));
        MpscArrayQueue<E>[] mpscArrayQueueArr = this.queues;
        if (mpscArrayQueueArr[id2].offer(e)) {
            return true;
        }
        return slowOffer(mpscArrayQueueArr, i, id2 + 1, e);
    }

    private boolean slowOffer(MpscArrayQueue<E>[] mpscArrayQueueArr, int i, int i2, E e) {
        int i3;
        int i4 = i + 1;
        int i5 = i2 + i4;
        do {
            i3 = 0;
            for (int i6 = i2; i6 < i5; i6++) {
                int iFailFastOffer = mpscArrayQueueArr[i6 & i].failFastOffer(e);
                if (iFailFastOffer == 0) {
                    return true;
                }
                i3 += iFailFastOffer;
            }
        } while (i3 != i4);
        return false;
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public E poll() {
        int i = this.consumerQueueIndex & this.parallelQueuesMask;
        int i2 = this.parallelQueues + i;
        E ePoll = null;
        while (i < i2) {
            ePoll = this.queues[this.parallelQueuesMask & i].poll();
            if (ePoll != null) {
                break;
            }
            i++;
        }
        this.consumerQueueIndex = i;
        return ePoll;
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public E peek() {
        int i = this.consumerQueueIndex & this.parallelQueuesMask;
        int i2 = this.parallelQueues + i;
        E ePeek = null;
        while (i < i2) {
            ePeek = this.queues[this.parallelQueuesMask & i].peek();
            if (ePeek != null) {
                break;
            }
            i++;
        }
        this.consumerQueueIndex = i;
        return ePeek;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, org.jctools.queues.MessagePassingQueue
    public int size() {
        int size = 0;
        for (MpscArrayQueue<E> mpscArrayQueue : this.queues) {
            size += mpscArrayQueue.size();
        }
        return size;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        return getClass().getName();
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public boolean relaxedOffer(E e) {
        if (e == null) {
            throw null;
        }
        int i = this.parallelQueuesMask;
        int id2 = (int) (Thread.currentThread().getId() & ((long) i));
        MpscArrayQueue<E>[] mpscArrayQueueArr = this.queues;
        if (mpscArrayQueueArr[id2].failFastOffer(e) == 0) {
            return true;
        }
        for (int i2 = id2 + 1; i2 < id2 + i + 1; i2++) {
            if (mpscArrayQueueArr[i2 & i].failFastOffer(e) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public E relaxedPoll() {
        int i = this.consumerQueueIndex & this.parallelQueuesMask;
        int i2 = this.parallelQueues + i;
        E eRelaxedPoll = null;
        while (i < i2) {
            eRelaxedPoll = this.queues[this.parallelQueuesMask & i].relaxedPoll();
            if (eRelaxedPoll != null) {
                break;
            }
            i++;
        }
        this.consumerQueueIndex = i;
        return eRelaxedPoll;
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public E relaxedPeek() {
        int i = this.consumerQueueIndex & this.parallelQueuesMask;
        int i2 = this.parallelQueues + i;
        E eRelaxedPeek = null;
        while (i < i2) {
            eRelaxedPeek = this.queues[this.parallelQueuesMask & i].relaxedPeek();
            if (eRelaxedPeek != null) {
                break;
            }
            i++;
        }
        this.consumerQueueIndex = i;
        return eRelaxedPeek;
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public int capacity() {
        return this.queues.length * this.queues[0].capacity();
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public int drain(MessagePassingQueue.Consumer<E> consumer) {
        return drain(consumer, capacity());
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
        for (int i2 = 0; i2 < i; i2++) {
            E eRelaxedPoll = relaxedPoll();
            if (eRelaxedPoll == null) {
                return i2;
            }
            consumer.accept(eRelaxedPoll);
        }
        return i;
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public int fill(MessagePassingQueue.Supplier<E> supplier, int i) {
        int i2 = this.parallelQueuesMask;
        int id2 = (int) (Thread.currentThread().getId() & ((long) i2));
        MpscArrayQueue<E>[] mpscArrayQueueArr = this.queues;
        int iFill = mpscArrayQueueArr[id2].fill(supplier, i);
        if (iFill == i) {
            return i;
        }
        for (int i3 = id2 + 1; i3 < id2 + i2 + 1; i3++) {
            iFill += mpscArrayQueueArr[i3 & i2].fill(supplier, i - iFill);
            if (iFill == i) {
                return i;
            }
        }
        return iFill;
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public void drain(MessagePassingQueue.Consumer<E> consumer, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        while (true) {
            int iIdle = 0;
            while (exitCondition.keepRunning()) {
                E eRelaxedPoll = relaxedPoll();
                if (eRelaxedPoll == null) {
                    iIdle = waitStrategy.idle(iIdle);
                } else {
                    consumer.accept(eRelaxedPoll);
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
