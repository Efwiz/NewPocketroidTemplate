package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;

/* JADX INFO: loaded from: classes5.dex */
public interface ByteBidirectionalIterator extends ByteIterator, ObjectBidirectionalIterator<Byte> {
    byte previousByte();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator
    @Deprecated
    default Byte previous() {
        return Byte.valueOf(previousByte());
    }
}
