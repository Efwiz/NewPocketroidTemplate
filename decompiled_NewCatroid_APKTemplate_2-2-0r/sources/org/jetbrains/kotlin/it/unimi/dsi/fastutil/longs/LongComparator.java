package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.util.Comparator;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface LongComparator extends Comparator<Long> {
    int compare(long j, long j2);

    @Override // java.util.Comparator
    default Comparator<Long> reversed() {
        return LongComparators.oppositeComparator(this);
    }

    @Override // java.util.Comparator
    @Deprecated
    default int compare(Long l, Long l2) {
        return compare(l.longValue(), l2.longValue());
    }

    default LongComparator thenComparing(LongComparator longComparator) {
        return new LongComparator$$ExternalSyntheticLambda0(this, longComparator);
    }

    static /* synthetic */ int lambda$thenComparing$3d6e68ef$1(LongComparator _this, LongComparator longComparator, long j, long j2) {
        int iCompare = _this.compare(j, j2);
        return iCompare == 0 ? longComparator.compare(j, j2) : iCompare;
    }

    @Override // java.util.Comparator
    default Comparator<Long> thenComparing(Comparator<? super Long> comparator) {
        return comparator instanceof LongComparator ? thenComparing((LongComparator) comparator) : super.thenComparing(comparator);
    }
}
