package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import com.google.android.gms.cast.MediaError;
import java.util.Set;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface LongSet extends LongCollection, Set<Long> {
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
    LongIterator iterator();

    boolean remove(long j);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
    default LongSpliterator spliterator() {
        return LongSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), MediaError.DetailedErrorCode.DASH_NETWORK);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, java.util.Collection
    @Deprecated
    default boolean remove(Object obj) {
        return super.remove(obj);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, java.util.Collection
    @Deprecated
    default boolean add(Long l) {
        return super.add(l);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, java.util.Collection
    @Deprecated
    default boolean contains(Object obj) {
        return super.contains(obj);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
    @Deprecated
    default boolean rem(long j) {
        return remove(j);
    }
}
