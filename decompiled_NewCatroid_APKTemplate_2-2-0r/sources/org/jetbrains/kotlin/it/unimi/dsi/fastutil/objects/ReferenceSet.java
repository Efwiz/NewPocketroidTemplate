package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.util.Set;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface ReferenceSet<K> extends ReferenceCollection<K>, Set<K> {
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection
    ObjectIterator<K> iterator();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceCollection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection
    default ObjectSpliterator<K> spliterator() {
        return ObjectSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 65);
    }
}
