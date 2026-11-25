package io.netty.util.internal.shaded.org.jctools.queues;

import com.google.android.gms.internal.cast.zzrj$$ExternalSyntheticBackportWithForwarding0;
import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;

/* JADX INFO: compiled from: BaseLinkedQueue.java */
/* JADX INFO: loaded from: classes11.dex */
abstract class BaseLinkedQueueProducerNodeRef<E> extends BaseLinkedQueuePad0<E> {
    static final long P_NODE_OFFSET = UnsafeAccess.fieldOffset(BaseLinkedQueueProducerNodeRef.class, "producerNode");
    private LinkedQueueNode<E> producerNode;

    BaseLinkedQueueProducerNodeRef() {
    }

    final void spProducerNode(LinkedQueueNode<E> linkedQueueNode) {
        this.producerNode = linkedQueueNode;
    }

    final LinkedQueueNode<E> lvProducerNode() {
        return (LinkedQueueNode) UnsafeAccess.UNSAFE.getObjectVolatile(this, P_NODE_OFFSET);
    }

    final boolean casProducerNode(LinkedQueueNode<E> linkedQueueNode, LinkedQueueNode<E> linkedQueueNode2) {
        return zzrj$$ExternalSyntheticBackportWithForwarding0.m(UnsafeAccess.UNSAFE, this, P_NODE_OFFSET, linkedQueueNode, linkedQueueNode2);
    }

    final LinkedQueueNode<E> lpProducerNode() {
        return this.producerNode;
    }
}
