package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import java.util.ListIterator;

/* JADX INFO: loaded from: classes5.dex */
public interface ShortListIterator extends ShortBidirectionalIterator, ListIterator<Short> {
    default void set(short s) {
        throw new UnsupportedOperationException();
    }

    default void add(short s) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Iterator, java.util.ListIterator
    default void remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    @Deprecated
    default void set(Short sh) {
        set(sh.shortValue());
    }

    @Override // java.util.ListIterator
    @Deprecated
    default void add(Short sh) {
        add(sh.shortValue());
    }

    @Override // java.util.Iterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterator
    @Deprecated
    default Short next() {
        return super.next();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator
    @Deprecated
    default Short previous() {
        return super.previous();
    }
}
