package org.jctools.queues;

import org.jctools.util.UnsafeAccess;

/* JADX INFO: loaded from: classes14.dex */
public class MpscLinkedQueue8<E> extends MpscLinkedQueue<E> {
    @Override // org.jctools.queues.MpscLinkedQueue
    protected final LinkedQueueNode<E> xchgProducerNode(LinkedQueueNode<E> linkedQueueNode) {
        return (LinkedQueueNode) UnsafeAccess.UNSAFE.getAndSetObject(this, P_NODE_OFFSET, linkedQueueNode);
    }
}
