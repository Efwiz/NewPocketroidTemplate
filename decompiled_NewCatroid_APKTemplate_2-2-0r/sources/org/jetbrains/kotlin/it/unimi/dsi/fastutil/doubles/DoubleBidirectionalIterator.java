package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

/* JADX INFO: loaded from: classes5.dex */
public interface DoubleBidirectionalIterator extends DoubleIterator, ObjectBidirectionalIterator<Double> {
    double previousDouble();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator
    @Deprecated
    default Double previous() {
        return Double.valueOf(previousDouble());
    }
}
