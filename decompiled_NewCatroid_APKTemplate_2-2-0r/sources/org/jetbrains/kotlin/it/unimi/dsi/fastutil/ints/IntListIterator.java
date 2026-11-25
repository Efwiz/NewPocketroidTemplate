package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.util.ListIterator;

/* JADX INFO: loaded from: classes5.dex */
public interface IntListIterator extends IntBidirectionalIterator, ListIterator<Integer> {
    default void set(int i) {
        throw new UnsupportedOperationException();
    }

    default void add(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Iterator, java.util.ListIterator
    default void remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    @Deprecated
    default void set(Integer num) {
        set(num.intValue());
    }

    @Override // java.util.ListIterator
    @Deprecated
    default void add(Integer num) {
        add(num.intValue());
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt, java.util.Iterator
    @Deprecated
    default Integer next() {
        return super.next();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntBidirectionalIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator
    @Deprecated
    default Integer previous() {
        return super.previous();
    }
}
