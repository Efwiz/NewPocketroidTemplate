package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import com.google.android.gms.cast.MediaError;
import java.util.Set;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface ShortSet extends ShortCollection, Set<Short> {
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
    ShortIterator iterator();

    boolean remove(short s);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
    default ShortSpliterator spliterator() {
        return ShortSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), MediaError.DetailedErrorCode.DASH_NETWORK);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, java.util.Collection
    @Deprecated
    default boolean remove(Object obj) {
        return super.remove(obj);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, java.util.Collection
    @Deprecated
    default boolean add(Short sh) {
        return super.add(sh);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, java.util.Collection
    @Deprecated
    default boolean contains(Object obj) {
        return super.contains(obj);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
    @Deprecated
    default boolean rem(short s) {
        return remove(s);
    }
}
