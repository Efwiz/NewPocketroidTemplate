package org.jetbrains.kotlin.com.google.common.collect;

import java.lang.Comparable;

/* JADX INFO: loaded from: classes3.dex */
abstract class AbstractRangeSet<C extends Comparable> implements RangeSet<C> {
    @Override // org.jetbrains.kotlin.com.google.common.collect.RangeSet
    public abstract boolean encloses(Range<C> range);

    @Override // org.jetbrains.kotlin.com.google.common.collect.RangeSet
    public abstract Range<C> rangeContaining(C c);

    AbstractRangeSet() {
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.RangeSet
    public boolean contains(C c) {
        return rangeContaining(c) != null;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.RangeSet
    public boolean isEmpty() {
        return asRanges().isEmpty();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.RangeSet
    public void add(Range<C> range) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.RangeSet
    public void remove(Range<C> range) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.RangeSet
    public void clear() {
        remove(Range.all());
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.RangeSet
    public boolean enclosesAll(RangeSet<C> rangeSet) {
        return enclosesAll(rangeSet.asRanges());
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.RangeSet
    public void addAll(RangeSet<C> rangeSet) {
        addAll(rangeSet.asRanges());
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.RangeSet
    public void removeAll(RangeSet<C> rangeSet) {
        removeAll(rangeSet.asRanges());
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.RangeSet
    public boolean intersects(Range<C> range) {
        return !subRangeSet(range).isEmpty();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.RangeSet
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof RangeSet) {
            return asRanges().equals(((RangeSet) obj).asRanges());
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.RangeSet
    public final int hashCode() {
        return asRanges().hashCode();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.RangeSet
    public final String toString() {
        return asRanges().toString();
    }
}
