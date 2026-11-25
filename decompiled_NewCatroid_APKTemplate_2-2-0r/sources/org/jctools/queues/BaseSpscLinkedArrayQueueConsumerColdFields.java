package org.jctools.queues;

/* JADX INFO: compiled from: BaseSpscLinkedArrayQueue.java */
/* JADX INFO: loaded from: classes14.dex */
abstract class BaseSpscLinkedArrayQueueConsumerColdFields<E> extends BaseSpscLinkedArrayQueuePrePad<E> {
    protected E[] consumerBuffer;
    protected long consumerMask;

    BaseSpscLinkedArrayQueueConsumerColdFields() {
    }
}
