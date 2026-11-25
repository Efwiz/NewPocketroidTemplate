package org.jctools.queues;

/* JADX INFO: compiled from: BaseSpscLinkedArrayQueue.java */
/* JADX INFO: loaded from: classes14.dex */
abstract class BaseSpscLinkedArrayQueueProducerColdFields<E> extends BaseSpscLinkedArrayQueueProducerFields<E> {
    protected E[] producerBuffer;
    protected long producerBufferLimit;
    protected long producerMask;

    BaseSpscLinkedArrayQueueProducerColdFields() {
    }
}
