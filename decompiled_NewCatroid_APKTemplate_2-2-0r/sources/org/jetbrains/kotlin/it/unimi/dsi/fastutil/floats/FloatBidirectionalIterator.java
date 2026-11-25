package org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats;

import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

/* JADX INFO: loaded from: classes5.dex */
public interface FloatBidirectionalIterator extends FloatIterator, ObjectBidirectionalIterator<Float> {
    float previousFloat();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator
    @Deprecated
    default Float previous() {
        return Float.valueOf(previousFloat());
    }
}
