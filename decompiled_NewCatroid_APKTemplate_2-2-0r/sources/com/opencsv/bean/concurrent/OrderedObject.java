package com.opencsv.bean.concurrent;

/* JADX INFO: loaded from: classes10.dex */
public class OrderedObject<E> {
    private final E element;
    private final long ordinal;

    OrderedObject(long j, E e) {
        this.ordinal = j;
        this.element = e;
    }

    long getOrdinal() {
        return this.ordinal;
    }

    public E getElement() {
        return this.element;
    }
}
