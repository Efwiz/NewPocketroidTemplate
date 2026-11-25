package org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans;

import java.util.Comparator;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface BooleanComparator extends Comparator<Boolean> {
    int compare(boolean z, boolean z2);

    @Override // java.util.Comparator
    default Comparator<Boolean> reversed() {
        return BooleanComparators.oppositeComparator(this);
    }

    @Override // java.util.Comparator
    @Deprecated
    default int compare(Boolean bool, Boolean bool2) {
        return compare(bool.booleanValue(), bool2.booleanValue());
    }

    default BooleanComparator thenComparing(BooleanComparator booleanComparator) {
        return new BooleanComparator$$ExternalSyntheticLambda0(this, booleanComparator);
    }

    static /* synthetic */ int lambda$thenComparing$e8be742d$1(BooleanComparator _this, BooleanComparator booleanComparator, boolean z, boolean z2) {
        int iCompare = _this.compare(z, z2);
        return iCompare == 0 ? booleanComparator.compare(z, z2) : iCompare;
    }

    @Override // java.util.Comparator
    default Comparator<Boolean> thenComparing(Comparator<? super Boolean> comparator) {
        return comparator instanceof BooleanComparator ? thenComparing((BooleanComparator) comparator) : super.thenComparing(comparator);
    }
}
