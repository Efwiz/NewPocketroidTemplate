package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import java.util.ListIterator;

/* JADX INFO: loaded from: classes5.dex */
public interface DoubleListIterator extends DoubleBidirectionalIterator, ListIterator<Double> {
    default void set(double d) {
        throw new UnsupportedOperationException();
    }

    default void add(double d) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Iterator, java.util.ListIterator
    default void remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    @Deprecated
    default void set(Double d) {
        set(d.doubleValue());
    }

    @Override // java.util.ListIterator
    @Deprecated
    default void add(Double d) {
        add(d.doubleValue());
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterator, java.util.PrimitiveIterator.OfDouble, java.util.Iterator
    @Deprecated
    default Double next() {
        return super.next();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator
    @Deprecated
    default Double previous() {
        return super.previous();
    }
}
