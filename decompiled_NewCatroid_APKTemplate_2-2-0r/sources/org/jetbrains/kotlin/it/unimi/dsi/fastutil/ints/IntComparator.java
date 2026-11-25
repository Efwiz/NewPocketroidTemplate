package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.util.Comparator;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface IntComparator extends Comparator<Integer> {
    int compare(int i, int i2);

    @Override // java.util.Comparator
    default Comparator<Integer> reversed() {
        return IntComparators.oppositeComparator(this);
    }

    @Override // java.util.Comparator
    @Deprecated
    default int compare(Integer num, Integer num2) {
        return compare(num.intValue(), num2.intValue());
    }

    default IntComparator thenComparing(IntComparator intComparator) {
        return new IntComparator$$ExternalSyntheticLambda0(this, intComparator);
    }

    static /* synthetic */ int lambda$thenComparing$931d6fed$1(IntComparator _this, IntComparator intComparator, int i, int i2) {
        int iCompare = _this.compare(i, i2);
        return iCompare == 0 ? intComparator.compare(i, i2) : iCompare;
    }

    @Override // java.util.Comparator
    default Comparator<Integer> thenComparing(Comparator<? super Integer> comparator) {
        return comparator instanceof IntComparator ? thenComparing((IntComparator) comparator) : super.thenComparing(comparator);
    }
}
