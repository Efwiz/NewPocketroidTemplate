package org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans;

import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

/* JADX INFO: loaded from: classes5.dex */
public interface BooleanBidirectionalIterator extends BooleanIterator, ObjectBidirectionalIterator<Boolean> {
    boolean previousBoolean();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator
    @Deprecated
    default Boolean previous() {
        return Boolean.valueOf(previousBoolean());
    }
}
