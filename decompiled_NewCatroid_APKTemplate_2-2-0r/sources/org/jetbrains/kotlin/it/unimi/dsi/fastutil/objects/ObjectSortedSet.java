package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.util.SortedSet;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface ObjectSortedSet<K> extends ObjectBidirectionalIterable<K>, ObjectSet<K>, SortedSet<K> {
    @Override // java.util.SortedSet
    ObjectSortedSet<K> headSet(K k);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection
    ObjectBidirectionalIterator<K> iterator();

    @Override // java.util.SortedSet
    ObjectSortedSet<K> subSet(K k, K k2);

    @Override // java.util.SortedSet
    ObjectSortedSet<K> tailSet(K k);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection
    default ObjectSpliterator<K> spliterator() {
        return ObjectSpliterators.asSpliteratorFromSorted(iterator(), Size64.sizeOf(this), 85, comparator());
    }
}
