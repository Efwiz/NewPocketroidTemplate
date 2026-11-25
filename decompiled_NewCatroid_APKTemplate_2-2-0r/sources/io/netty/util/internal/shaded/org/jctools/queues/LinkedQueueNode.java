package io.netty.util.internal.shaded.org.jctools.queues;

import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: loaded from: classes11.dex */
final class LinkedQueueNode<E> {
    private static final long NEXT_OFFSET = UnsafeAccess.fieldOffset(LinkedQueueNode.class, ES6Iterator.NEXT_METHOD);
    private volatile LinkedQueueNode<E> next;
    private E value;

    LinkedQueueNode() {
        this(null);
    }

    LinkedQueueNode(E e) {
        spValue(e);
    }

    public E getAndNullValue() {
        E eLpValue = lpValue();
        spValue(null);
        return eLpValue;
    }

    public E lpValue() {
        return this.value;
    }

    public void spValue(E e) {
        this.value = e;
    }

    public void soNext(LinkedQueueNode<E> linkedQueueNode) {
        UnsafeAccess.UNSAFE.putOrderedObject(this, NEXT_OFFSET, linkedQueueNode);
    }

    public LinkedQueueNode<E> lvNext() {
        return this.next;
    }
}
