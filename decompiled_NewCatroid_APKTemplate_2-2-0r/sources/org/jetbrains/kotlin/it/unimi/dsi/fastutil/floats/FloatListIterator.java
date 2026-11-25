package org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats;

import java.util.ListIterator;

/* JADX INFO: loaded from: classes5.dex */
public interface FloatListIterator extends FloatBidirectionalIterator, ListIterator<Float> {
    default void set(float f) {
        throw new UnsupportedOperationException();
    }

    default void add(float f) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Iterator, java.util.ListIterator
    default void remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    @Deprecated
    default void set(Float f) {
        set(f.floatValue());
    }

    @Override // java.util.ListIterator
    @Deprecated
    default void add(Float f) {
        add(f.floatValue());
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterator, java.util.Iterator
    @Deprecated
    default Float next() {
        return super.next();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator
    @Deprecated
    default Float previous() {
        return super.previous();
    }
}
