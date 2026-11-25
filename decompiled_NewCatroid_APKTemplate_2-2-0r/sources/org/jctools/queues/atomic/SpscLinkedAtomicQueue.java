package org.jctools.queues.atomic;

import org.jctools.queues.MessagePassingQueue;

/* JADX INFO: loaded from: classes14.dex */
public class SpscLinkedAtomicQueue<E> extends BaseLinkedAtomicQueue<E> {
    @Override // org.jctools.queues.atomic.BaseLinkedAtomicQueue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ int capacity() {
        return super.capacity();
    }

    @Override // org.jctools.queues.atomic.BaseLinkedAtomicQueue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ int drain(MessagePassingQueue.Consumer consumer) {
        return super.drain(consumer);
    }

    @Override // org.jctools.queues.atomic.BaseLinkedAtomicQueue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ int drain(MessagePassingQueue.Consumer consumer, int i) {
        return super.drain(consumer, i);
    }

    @Override // org.jctools.queues.atomic.BaseLinkedAtomicQueue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ void drain(MessagePassingQueue.Consumer consumer, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        super.drain(consumer, waitStrategy, exitCondition);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jctools.queues.atomic.BaseLinkedAtomicQueue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ boolean relaxedOffer(Object obj) {
        return super.relaxedOffer(obj);
    }

    @Override // org.jctools.queues.atomic.BaseLinkedAtomicQueue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ Object relaxedPeek() {
        return super.relaxedPeek();
    }

    @Override // org.jctools.queues.atomic.BaseLinkedAtomicQueue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ Object relaxedPoll() {
        return super.relaxedPoll();
    }

    @Override // org.jctools.queues.atomic.BaseLinkedAtomicQueue, java.util.AbstractCollection
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public SpscLinkedAtomicQueue() {
        LinkedQueueAtomicNode<E> linkedQueueAtomicNodeNewNode = newNode();
        spProducerNode(linkedQueueAtomicNodeNewNode);
        spConsumerNode(linkedQueueAtomicNodeNewNode);
        linkedQueueAtomicNodeNewNode.soNext(null);
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public boolean offer(E e) {
        if (e == null) {
            throw null;
        }
        LinkedQueueAtomicNode<E> linkedQueueAtomicNodeNewNode = newNode(e);
        lpProducerNode().soNext(linkedQueueAtomicNodeNewNode);
        spProducerNode(linkedQueueAtomicNodeNewNode);
        return true;
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public E poll() {
        return (E) relaxedPoll();
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public E peek() {
        return (E) relaxedPeek();
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public int fill(MessagePassingQueue.Supplier<E> supplier) {
        long j = 0;
        do {
            fill(supplier, 4096);
            j += 4096;
        } while (j <= 2147479551);
        return (int) j;
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public int fill(MessagePassingQueue.Supplier<E> supplier, int i) {
        if (i == 0) {
            return 0;
        }
        LinkedQueueAtomicNode<E> linkedQueueAtomicNodeNewNode = newNode(supplier.get());
        int i2 = 1;
        LinkedQueueAtomicNode<E> linkedQueueAtomicNode = linkedQueueAtomicNodeNewNode;
        while (i2 < i) {
            LinkedQueueAtomicNode<E> linkedQueueAtomicNodeNewNode2 = newNode(supplier.get());
            linkedQueueAtomicNode.soNext(linkedQueueAtomicNodeNewNode2);
            i2++;
            linkedQueueAtomicNode = linkedQueueAtomicNodeNewNode2;
        }
        lpProducerNode().soNext(linkedQueueAtomicNodeNewNode);
        spProducerNode(linkedQueueAtomicNode);
        return i;
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public void fill(MessagePassingQueue.Supplier<E> supplier, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        LinkedQueueAtomicNode<E> linkedQueueAtomicNode = this.producerNode;
        while (exitCondition.keepRunning()) {
            int i = 0;
            while (i < 4096) {
                LinkedQueueAtomicNode<E> linkedQueueAtomicNodeNewNode = newNode(supplier.get());
                linkedQueueAtomicNode.soNext(linkedQueueAtomicNodeNewNode);
                this.producerNode = linkedQueueAtomicNodeNewNode;
                i++;
                linkedQueueAtomicNode = linkedQueueAtomicNodeNewNode;
            }
        }
    }
}
