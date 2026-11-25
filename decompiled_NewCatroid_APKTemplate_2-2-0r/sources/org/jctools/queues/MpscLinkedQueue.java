package org.jctools.queues;

import org.jctools.util.UnsafeAccess;

/* JADX INFO: loaded from: classes14.dex */
public abstract class MpscLinkedQueue<E> extends BaseLinkedQueue<E> {
    protected abstract LinkedQueueNode<E> xchgProducerNode(LinkedQueueNode<E> linkedQueueNode);

    @Override // org.jctools.queues.BaseLinkedQueue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ int capacity() {
        return super.capacity();
    }

    @Override // org.jctools.queues.BaseLinkedQueue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ int drain(MessagePassingQueue.Consumer consumer) {
        return super.drain(consumer);
    }

    @Override // org.jctools.queues.BaseLinkedQueue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ int drain(MessagePassingQueue.Consumer consumer, int i) {
        return super.drain(consumer, i);
    }

    @Override // org.jctools.queues.BaseLinkedQueue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ void drain(MessagePassingQueue.Consumer consumer, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        super.drain(consumer, waitStrategy, exitCondition);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jctools.queues.BaseLinkedQueue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ boolean relaxedOffer(Object obj) {
        return super.relaxedOffer(obj);
    }

    @Override // org.jctools.queues.BaseLinkedQueue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ Object relaxedPeek() {
        return super.relaxedPeek();
    }

    @Override // org.jctools.queues.BaseLinkedQueue, org.jctools.queues.MessagePassingQueue
    public /* bridge */ /* synthetic */ Object relaxedPoll() {
        return super.relaxedPoll();
    }

    @Override // org.jctools.queues.BaseLinkedQueue, java.util.AbstractCollection
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public static <E> MpscLinkedQueue<E> newMpscLinkedQueue() {
        if (UnsafeAccess.SUPPORTS_GET_AND_SET) {
            return new MpscLinkedQueue8();
        }
        return new MpscLinkedQueue7();
    }

    protected MpscLinkedQueue() {
        LinkedQueueNode<E> linkedQueueNodeNewNode = newNode();
        spConsumerNode(linkedQueueNodeNewNode);
        xchgProducerNode(linkedQueueNodeNewNode);
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public final boolean offer(E e) {
        if (e == null) {
            throw null;
        }
        LinkedQueueNode<E> linkedQueueNodeNewNode = newNode(e);
        xchgProducerNode(linkedQueueNodeNewNode).soNext(linkedQueueNodeNewNode);
        return true;
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public final E poll() {
        LinkedQueueNode<E> linkedQueueNodeLpConsumerNode = lpConsumerNode();
        LinkedQueueNode<E> linkedQueueNodeLvNext = linkedQueueNodeLpConsumerNode.lvNext();
        if (linkedQueueNodeLvNext != null) {
            return getSingleConsumerNodeValue(linkedQueueNodeLpConsumerNode, linkedQueueNodeLvNext);
        }
        if (linkedQueueNodeLpConsumerNode != lvProducerNode()) {
            return getSingleConsumerNodeValue(linkedQueueNodeLpConsumerNode, spinWaitForNextNode(linkedQueueNodeLpConsumerNode));
        }
        return null;
    }

    @Override // java.util.Queue, org.jctools.queues.MessagePassingQueue
    public final E peek() {
        LinkedQueueNode<E> linkedQueueNodeLpConsumerNode = lpConsumerNode();
        LinkedQueueNode<E> linkedQueueNodeLvNext = linkedQueueNodeLpConsumerNode.lvNext();
        if (linkedQueueNodeLvNext != null) {
            return linkedQueueNodeLvNext.lpValue();
        }
        if (linkedQueueNodeLpConsumerNode != lvProducerNode()) {
            return spinWaitForNextNode(linkedQueueNodeLpConsumerNode).lpValue();
        }
        return null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean remove(Object obj) {
        if (obj == null) {
            return false;
        }
        LinkedQueueNode<E> linkedQueueNodeLpConsumerNode = lpConsumerNode();
        LinkedQueueNode<E> nextConsumerNode = getNextConsumerNode(linkedQueueNodeLpConsumerNode);
        while (true) {
            LinkedQueueNode<E> linkedQueueNode = nextConsumerNode;
            LinkedQueueNode<E> linkedQueueNode2 = linkedQueueNodeLpConsumerNode;
            linkedQueueNodeLpConsumerNode = linkedQueueNode;
            if (linkedQueueNodeLpConsumerNode == null) {
                return false;
            }
            if (obj.equals(linkedQueueNodeLpConsumerNode.lpValue())) {
                LinkedQueueNode<E> nextConsumerNode2 = getNextConsumerNode(linkedQueueNodeLpConsumerNode);
                if (nextConsumerNode2 != null) {
                    linkedQueueNode2.soNext(nextConsumerNode2);
                } else {
                    linkedQueueNode2.soNext(null);
                    if (!casProducerNode(linkedQueueNodeLpConsumerNode, linkedQueueNode2)) {
                        linkedQueueNode2.soNext(spinWaitForNextNode(linkedQueueNodeLpConsumerNode));
                    }
                }
                linkedQueueNodeLpConsumerNode.soNext(null);
                linkedQueueNodeLpConsumerNode.spValue(null);
                return true;
            }
            nextConsumerNode = getNextConsumerNode(linkedQueueNodeLpConsumerNode);
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
        LinkedQueueNode<E> linkedQueueNodeNewNode = newNode(supplier.get());
        int i2 = 1;
        LinkedQueueNode<E> linkedQueueNode = linkedQueueNodeNewNode;
        while (i2 < i) {
            LinkedQueueNode<E> linkedQueueNodeNewNode2 = newNode(supplier.get());
            linkedQueueNode.soNext(linkedQueueNodeNewNode2);
            i2++;
            linkedQueueNode = linkedQueueNodeNewNode2;
        }
        xchgProducerNode(linkedQueueNode).soNext(linkedQueueNodeNewNode);
        return i;
    }

    @Override // org.jctools.queues.MessagePassingQueue
    public void fill(MessagePassingQueue.Supplier<E> supplier, MessagePassingQueue.WaitStrategy waitStrategy, MessagePassingQueue.ExitCondition exitCondition) {
        while (exitCondition.keepRunning()) {
            fill(supplier, 4096);
        }
    }

    private LinkedQueueNode<E> getNextConsumerNode(LinkedQueueNode<E> linkedQueueNode) {
        LinkedQueueNode<E> linkedQueueNodeLvNext = linkedQueueNode.lvNext();
        return (linkedQueueNodeLvNext != null || linkedQueueNode == lvProducerNode()) ? linkedQueueNodeLvNext : spinWaitForNextNode(linkedQueueNode);
    }

    private LinkedQueueNode<E> spinWaitForNextNode(LinkedQueueNode<E> linkedQueueNode) {
        LinkedQueueNode<E> linkedQueueNodeLvNext;
        do {
            linkedQueueNodeLvNext = linkedQueueNode.lvNext();
        } while (linkedQueueNodeLvNext == null);
        return linkedQueueNodeLvNext;
    }
}
