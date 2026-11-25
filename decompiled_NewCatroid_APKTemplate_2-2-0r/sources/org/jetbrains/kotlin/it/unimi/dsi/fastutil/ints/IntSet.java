package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import com.google.android.gms.cast.MediaError;
import java.util.Set;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface IntSet extends IntCollection, Set<Integer> {
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
    IntIterator iterator();

    boolean remove(int i);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
    default IntSpliterator spliterator() {
        return IntSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), MediaError.DetailedErrorCode.DASH_NETWORK);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection
    @Deprecated
    default boolean remove(Object obj) {
        return super.remove(obj);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection
    @Deprecated
    default boolean add(Integer num) {
        return super.add(num);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection
    @Deprecated
    default boolean contains(Object obj) {
        return super.contains(obj);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
    @Deprecated
    default boolean rem(int i) {
        return remove(i);
    }
}
