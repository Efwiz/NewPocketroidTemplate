package org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans;

import java.util.ListIterator;

/* JADX INFO: loaded from: classes5.dex */
public interface BooleanListIterator extends BooleanBidirectionalIterator, ListIterator<Boolean> {
    default void set(boolean z) {
        throw new UnsupportedOperationException();
    }

    default void add(boolean z) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Iterator, java.util.ListIterator
    default void remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    @Deprecated
    default void set(Boolean bool) {
        set(bool.booleanValue());
    }

    @Override // java.util.ListIterator
    @Deprecated
    default void add(Boolean bool) {
        add(bool.booleanValue());
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterator, java.util.Iterator
    @Deprecated
    default Boolean next() {
        return super.next();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanBidirectionalIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator
    @Deprecated
    default Boolean previous() {
        return super.previous();
    }
}
