package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.util.ListIterator;

/* JADX INFO: loaded from: classes5.dex */
public interface LongListIterator extends LongBidirectionalIterator, ListIterator<Long> {
    default void set(long j) {
        throw new UnsupportedOperationException();
    }

    default void add(long j) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Iterator, java.util.ListIterator
    default void remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    @Deprecated
    default void set(Long l) {
        set(l.longValue());
    }

    @Override // java.util.ListIterator
    @Deprecated
    default void add(Long l) {
        add(l.longValue());
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterator, java.util.PrimitiveIterator.OfLong, java.util.Iterator
    @Deprecated
    default Long next() {
        return super.next();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongBidirectionalIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator
    @Deprecated
    default Long previous() {
        return super.previous();
    }
}
