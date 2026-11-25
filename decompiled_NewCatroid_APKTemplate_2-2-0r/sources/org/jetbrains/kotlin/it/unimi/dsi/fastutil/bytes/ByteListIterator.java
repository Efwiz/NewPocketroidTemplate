package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import java.util.ListIterator;

/* JADX INFO: loaded from: classes5.dex */
public interface ByteListIterator extends ByteBidirectionalIterator, ListIterator<Byte> {
    default void set(byte b) {
        throw new UnsupportedOperationException();
    }

    default void add(byte b) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Iterator, java.util.ListIterator
    default void remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    @Deprecated
    default void set(Byte b) {
        set(b.byteValue());
    }

    @Override // java.util.ListIterator
    @Deprecated
    default void add(Byte b) {
        add(b.byteValue());
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterator, java.util.Iterator
    @Deprecated
    default Byte next() {
        return super.next();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator
    @Deprecated
    default Byte previous() {
        return super.previous();
    }
}
