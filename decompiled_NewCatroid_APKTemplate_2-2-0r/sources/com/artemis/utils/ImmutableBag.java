package com.artemis.utils;

/* JADX INFO: loaded from: classes8.dex */
public interface ImmutableBag<E> extends Iterable<E> {
    boolean contains(E e);

    E get(int i);

    boolean isEmpty();

    int size();
}
