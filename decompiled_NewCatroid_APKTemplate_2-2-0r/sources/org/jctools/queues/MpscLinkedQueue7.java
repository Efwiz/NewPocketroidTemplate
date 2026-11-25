package org.jctools.queues;

import com.google.android.gms.internal.cast.zzrj$$ExternalSyntheticBackportWithForwarding0;
import org.jctools.util.UnsafeAccess;

/* JADX INFO: loaded from: classes14.dex */
public class MpscLinkedQueue7<E> extends MpscLinkedQueue<E> {
    @Override // org.jctools.queues.MpscLinkedQueue
    protected final LinkedQueueNode<E> xchgProducerNode(LinkedQueueNode<E> linkedQueueNode) {
        LinkedQueueNode<E> linkedQueueNode2;
        do {
            linkedQueueNode2 = this.producerNode;
        } while (!zzrj$$ExternalSyntheticBackportWithForwarding0.m(UnsafeAccess.UNSAFE, this, P_NODE_OFFSET, linkedQueueNode2, linkedQueueNode));
        return linkedQueueNode2;
    }
}
