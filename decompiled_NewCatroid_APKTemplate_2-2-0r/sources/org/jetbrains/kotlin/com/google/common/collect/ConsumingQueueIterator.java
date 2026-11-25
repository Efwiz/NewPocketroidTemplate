package org.jetbrains.kotlin.com.google.common.collect;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;
import org.jetbrains.kotlin.com.google.common.base.Preconditions;

/* JADX INFO: loaded from: classes3.dex */
class ConsumingQueueIterator<T> extends AbstractIterator<T> {
    private final Queue<T> queue;

    ConsumingQueueIterator(T... tArr) {
        ArrayDeque arrayDeque = new ArrayDeque(tArr.length);
        this.queue = arrayDeque;
        Collections.addAll(arrayDeque, tArr);
    }

    ConsumingQueueIterator(Queue<T> queue) {
        this.queue = (Queue) Preconditions.checkNotNull(queue);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.AbstractIterator
    public T computeNext() {
        return this.queue.isEmpty() ? endOfData() : this.queue.remove();
    }
}
