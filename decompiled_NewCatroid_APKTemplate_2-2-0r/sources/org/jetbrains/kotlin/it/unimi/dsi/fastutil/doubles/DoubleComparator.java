package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import java.util.Comparator;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface DoubleComparator extends Comparator<Double> {
    int compare(double d, double d2);

    @Override // java.util.Comparator
    default Comparator<Double> reversed() {
        return DoubleComparators.oppositeComparator(this);
    }

    @Override // java.util.Comparator
    @Deprecated
    default int compare(Double d, Double d2) {
        return compare(d.doubleValue(), d2.doubleValue());
    }

    default DoubleComparator thenComparing(DoubleComparator doubleComparator) {
        return new DoubleComparator$$ExternalSyntheticLambda0(this, doubleComparator);
    }

    static /* synthetic */ int lambda$thenComparing$f8e9881b$1(DoubleComparator _this, DoubleComparator doubleComparator, double d, double d2) {
        int iCompare = _this.compare(d, d2);
        return iCompare == 0 ? doubleComparator.compare(d, d2) : iCompare;
    }

    @Override // java.util.Comparator
    default Comparator<Double> thenComparing(Comparator<? super Double> comparator) {
        return comparator instanceof DoubleComparator ? thenComparing((DoubleComparator) comparator) : super.thenComparing(comparator);
    }
}
