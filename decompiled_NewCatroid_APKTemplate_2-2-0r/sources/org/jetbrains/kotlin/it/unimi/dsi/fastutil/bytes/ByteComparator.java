package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import java.util.Comparator;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface ByteComparator extends Comparator<Byte> {
    int compare(byte b, byte b2);

    @Override // java.util.Comparator
    default Comparator<Byte> reversed() {
        return ByteComparators.oppositeComparator(this);
    }

    @Override // java.util.Comparator
    @Deprecated
    default int compare(Byte b, Byte b2) {
        return compare(b.byteValue(), b2.byteValue());
    }

    default ByteComparator thenComparing(ByteComparator byteComparator) {
        return new ByteComparator$$ExternalSyntheticLambda0(this, byteComparator);
    }

    static /* synthetic */ int lambda$thenComparing$6e387fbf$1(ByteComparator _this, ByteComparator byteComparator, byte b, byte b2) {
        int iCompare = _this.compare(b, b2);
        return iCompare == 0 ? byteComparator.compare(b, b2) : iCompare;
    }

    @Override // java.util.Comparator
    default Comparator<Byte> thenComparing(Comparator<? super Byte> comparator) {
        return comparator instanceof ByteComparator ? thenComparing((ByteComparator) comparator) : super.thenComparing(comparator);
    }
}
