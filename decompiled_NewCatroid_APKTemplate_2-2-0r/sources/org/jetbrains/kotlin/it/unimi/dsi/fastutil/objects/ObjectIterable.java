package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

/* JADX INFO: loaded from: classes5.dex */
public interface ObjectIterable<K> extends Iterable<K> {
    @Override // java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
    ObjectIterator<K> iterator();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
    default ObjectSpliterator<K> spliterator() {
        return ObjectSpliterators.asSpliteratorUnknownSize(iterator(), 0);
    }
}
