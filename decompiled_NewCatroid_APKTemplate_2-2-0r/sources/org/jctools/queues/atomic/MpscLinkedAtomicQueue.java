package org.jctools.queues.atomic;

import org.jctools.queues.MessagePassingQueue;

/* JADX INFO: loaded from: classes14.dex */
public final class MpscLinkedAtomicQueue<E> extends BaseLinkedAtomicQueue<E> {
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

    public MpscLinkedAtomicQueue() {
        LinkedQueueAtomicNode<E> linkedQueueAtomicNodeNewNode = newNode();
        spConsumerNode(linkedQueueAtomicNodeNewNode);
        xchgProducerNode(linkedQueueAtomicNodeNewNode);
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public final boolean offer(E e) {
        if (e == null) {
            throw null;
        }
        LinkedQueueAtomicNode<E> linkedQueueAtomicNodeNewNode = newNode(e);
        xchgProducerNode(linkedQueueAtomicNodeNewNode).soNext(linkedQueueAtomicNodeNewNode);
        return true;
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public final E poll() {
        LinkedQueueAtomicNode<E> linkedQueueAtomicNodeLpConsumerNode = lpConsumerNode();
        LinkedQueueAtomicNode<E> linkedQueueAtomicNodeLvNext = linkedQueueAtomicNodeLpConsumerNode.lvNext();
        if (linkedQueueAtomicNodeLvNext != null) {
            return getSingleConsumerNodeValue(linkedQueueAtomicNodeLpConsumerNode, linkedQueueAtomicNodeLvNext);
        }
        if (linkedQueueAtomicNodeLpConsumerNode != lvProducerNode()) {
            return getSingleConsumerNodeValue(linkedQueueAtomicNodeLpConsumerNode, spinWaitForNextNode(linkedQueueAtomicNodeLpConsumerNode));
        }
        return null;
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public final E peek() {
        LinkedQueueAtomicNode<E> linkedQueueAtomicNodeLpConsumerNode = lpConsumerNode();
        LinkedQueueAtomicNode<E> linkedQueueAtomicNodeLvNext = linkedQueueAtomicNodeLpConsumerNode.lvNext();
        if (linkedQueueAtomicNodeLvNext != null) {
            return linkedQueueAtomicNodeLvNext.lpValue();
        }
        if (linkedQueueAtomicNodeLpConsumerNode != lvProducerNode()) {
            return spinWaitForNextNode(linkedQueueAtomicNodeLpConsumerNode).lpValue();
        }
        return null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean remove(Object obj) {
        if (obj == null) {
            return false;
        }
        LinkedQueueAtomicNode<E> linkedQueueAtomicNodeLpConsumerNode = lpConsumerNode();
        LinkedQueueAtomicNode<E> nextConsumerNode = getNextConsumerNode(linkedQueueAtomicNodeLpConsumerNode);
        while (true) {
            LinkedQueueAtomicNode<E> linkedQueueAtomicNode = nextConsumerNode;
            LinkedQueueAtomicNode<E> linkedQueueAtomicNode2 = linkedQueueAtomicNodeLpConsumerNode;
            linkedQueueAtomicNodeLpConsumerNode = linkedQueueAtomicNode;
            if (linkedQueueAtomicNodeLpConsumerNode == null) {
                return false;
            }
            if (obj.equals(linkedQueueAtomicNodeLpConsumerNode.lpValue())) {
                LinkedQueueAtomicNode<E> nextConsumerNode2 = getNextConsumerNode(linkedQueueAtomicNodeLpConsumerNode);
                if (nextConsumerNode2 != null) {
                    linkedQueueAtomicNode2.soNext(nextConsumerNode2);
                } else {
                    linkedQueueAtomicNode2.soNext(null);
                    if (!casProducerNode(linkedQueueAtomicNodeLpConsumerNode, linkedQueueAtomicNode2)) {
                        linkedQueueAtomicNode2.soNext(spinWaitForNextNode(linkedQueueAtomicNodeLpConsumerNode));
                    }
                }
                linkedQueueAtomicNodeLpConsumerNode.soNext(null);
                linkedQueueAtomicNodeLpConsumerNode.spValue(null);
                return true;
            }
            nextConsumerNode = getNextConsumerNode(linkedQueueAtomicNodeLpConsumerNode);
        }
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
        xchgProducerNode(linkedQueueAtomicNode).soNext(linkedQueueAtomicNodeNewNode);
        return i;
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public void fill(MessagePassingQueue.Supplier<E> supplier, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        while (exitCondition.keepRunning()) {
            fill(supplier, 4096);
        }
    }

    private LinkedQueueAtomicNode<E> getNextConsumerNode(LinkedQueueAtomicNode<E> linkedQueueAtomicNode) {
        LinkedQueueAtomicNode<E> linkedQueueAtomicNodeLvNext = linkedQueueAtomicNode.lvNext();
        return (linkedQueueAtomicNodeLvNext != null || linkedQueueAtomicNode == lvProducerNode()) ? linkedQueueAtomicNodeLvNext : spinWaitForNextNode(linkedQueueAtomicNode);
    }

    private LinkedQueueAtomicNode<E> spinWaitForNextNode(LinkedQueueAtomicNode<E> linkedQueueAtomicNode) {
        LinkedQueueAtomicNode<E> linkedQueueAtomicNodeLvNext;
        do {
            linkedQueueAtomicNodeLvNext = linkedQueueAtomicNode.lvNext();
        } while (linkedQueueAtomicNodeLvNext == null);
        return linkedQueueAtomicNodeLvNext;
    }
}
