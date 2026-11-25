package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

/* JADX INFO: loaded from: classes5.dex */
public interface ShortBidirectionalIterator extends ObjectBidirectionalIterator<Short>, ShortIterator {
    short previousShort();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator
    @Deprecated
    default Short previous() {
        return Short.valueOf(previousShort());
    }
}
