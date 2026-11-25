package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import java.util.Comparator;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface ShortComparator extends Comparator<Short> {
    int compare(short s, short s2);

    @Override // java.util.Comparator
    default Comparator<Short> reversed() {
        return ShortComparators.oppositeComparator(this);
    }

    @Override // java.util.Comparator
    @Deprecated
    default int compare(Short sh, Short sh2) {
        return compare(sh.shortValue(), sh2.shortValue());
    }

    default ShortComparator thenComparing(ShortComparator shortComparator) {
        return new ShortComparator$$ExternalSyntheticLambda0(this, shortComparator);
    }

    static /* synthetic */ int lambda$thenComparing$953dd6d$1(ShortComparator _this, ShortComparator shortComparator, short s, short s2) {
        int iCompare = _this.compare(s, s2);
        return iCompare == 0 ? shortComparator.compare(s, s2) : iCompare;
    }

    @Override // java.util.Comparator
    default Comparator<Short> thenComparing(Comparator<? super Short> comparator) {
        return comparator instanceof ShortComparator ? thenComparing((ShortComparator) comparator) : super.thenComparing(comparator);
    }
}
