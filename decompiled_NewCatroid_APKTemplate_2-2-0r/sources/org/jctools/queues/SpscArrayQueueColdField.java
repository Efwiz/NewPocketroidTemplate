package org.jctools.queues;

/* JADX INFO: compiled from: SpscArrayQueue.java */
/* JADX INFO: loaded from: classes14.dex */
abstract class SpscArrayQueueColdField<E> extends ConcurrentCircularArrayQueue<E> {
    public static final int MAX_LOOK_AHEAD_STEP = Integer.getInteger("jctools.spsc.max.lookahead.step", 4096).intValue();
    protected final int lookAheadStep;

    SpscArrayQueueColdField(int i) {
        super(i);
        this.lookAheadStep = Math.min(capacity() / 4, MAX_LOOK_AHEAD_STEP);
    }
}
