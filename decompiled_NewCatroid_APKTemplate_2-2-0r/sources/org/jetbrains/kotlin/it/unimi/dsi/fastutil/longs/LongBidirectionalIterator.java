package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

/* JADX INFO: loaded from: classes5.dex */
public interface LongBidirectionalIterator extends LongIterator, ObjectBidirectionalIterator<Long> {
    long previousLong();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator
    @Deprecated
    default Long previous() {
        return Long.valueOf(previousLong());
    }
}
