package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

/* JADX INFO: loaded from: classes5.dex */
public interface IntBidirectionalIterator extends IntIterator, ObjectBidirectionalIterator<Integer> {
    int previousInt();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator
    @Deprecated
    default Integer previous() {
        return Integer.valueOf(previousInt());
    }
}
