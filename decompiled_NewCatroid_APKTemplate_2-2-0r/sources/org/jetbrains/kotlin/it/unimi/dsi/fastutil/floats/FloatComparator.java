package org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats;

import java.util.Comparator;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface FloatComparator extends Comparator<Float> {
    int compare(float f, float f2);

    @Override // java.util.Comparator
    default Comparator<Float> reversed() {
        return FloatComparators.oppositeComparator(this);
    }

    @Override // java.util.Comparator
    @Deprecated
    default int compare(Float f, Float f2) {
        return compare(f.floatValue(), f2.floatValue());
    }

    default FloatComparator thenComparing(FloatComparator floatComparator) {
        return new FloatComparator$$ExternalSyntheticLambda0(this, floatComparator);
    }

    static /* synthetic */ int lambda$thenComparing$99a1156d$1(FloatComparator _this, FloatComparator floatComparator, float f, float f2) {
        int iCompare = _this.compare(f, f2);
        return iCompare == 0 ? floatComparator.compare(f, f2) : iCompare;
    }

    @Override // java.util.Comparator
    default Comparator<Float> thenComparing(Comparator<? super Float> comparator) {
        return comparator instanceof FloatComparator ? thenComparing((FloatComparator) comparator) : super.thenComparing(comparator);
    }
}
