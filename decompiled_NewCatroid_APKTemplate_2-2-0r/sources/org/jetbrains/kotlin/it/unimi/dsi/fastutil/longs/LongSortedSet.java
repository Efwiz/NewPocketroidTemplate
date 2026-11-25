package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.util.Comparator;
import java.util.SortedSet;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface LongSortedSet extends LongBidirectionalIterable, LongSet, SortedSet<Long> {
    @Override // java.util.SortedSet
    Comparator<? super Long> comparator();

    long firstLong();

    LongSortedSet headSet(long j);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongBidirectionalIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
    LongBidirectionalIterator iterator();

    long lastLong();

    LongSortedSet subSet(long j, long j2);

    LongSortedSet tailSet(long j);

    /* JADX WARN: Type inference failed for: r4v0, types: [org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongComparator] */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
    default LongSpliterator spliterator() {
        return LongSpliterators.asSpliteratorFromSorted(iterator(), Size64.sizeOf(this), 341, comparator());
    }

    @Override // java.util.SortedSet
    @Deprecated
    default LongSortedSet subSet(Long l, Long l2) {
        return subSet(l.longValue(), l2.longValue());
    }

    @Override // java.util.SortedSet
    @Deprecated
    default LongSortedSet headSet(Long l) {
        return headSet(l.longValue());
    }

    @Override // java.util.SortedSet
    @Deprecated
    default LongSortedSet tailSet(Long l) {
        return tailSet(l.longValue());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.SortedSet
    @Deprecated
    default Long first() {
        return Long.valueOf(firstLong());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.SortedSet
    @Deprecated
    default Long last() {
        return Long.valueOf(lastLong());
    }
}
