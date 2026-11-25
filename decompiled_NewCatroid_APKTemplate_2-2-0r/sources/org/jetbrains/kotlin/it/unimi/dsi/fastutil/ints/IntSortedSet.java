package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.util.SortedSet;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface IntSortedSet extends IntBidirectionalIterable, IntSet, SortedSet<Integer> {
    @Override // java.util.SortedSet
    IntComparator comparator();

    int firstInt();

    IntSortedSet headSet(int i);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntBidirectionalIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
    IntBidirectionalIterator iterator();

    int lastInt();

    IntSortedSet subSet(int i, int i2);

    IntSortedSet tailSet(int i);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
    default IntSpliterator spliterator() {
        return IntSpliterators.asSpliteratorFromSorted(iterator(), Size64.sizeOf(this), 341, comparator());
    }

    @Override // java.util.SortedSet
    @Deprecated
    default IntSortedSet subSet(Integer num, Integer num2) {
        return subSet(num.intValue(), num2.intValue());
    }

    @Override // java.util.SortedSet
    @Deprecated
    default IntSortedSet headSet(Integer num) {
        return headSet(num.intValue());
    }

    @Override // java.util.SortedSet
    @Deprecated
    default IntSortedSet tailSet(Integer num) {
        return tailSet(num.intValue());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.SortedSet
    @Deprecated
    default Integer first() {
        return Integer.valueOf(firstInt());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.SortedSet
    @Deprecated
    default Integer last() {
        return Integer.valueOf(lastInt());
    }
}
