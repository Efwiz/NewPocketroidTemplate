package org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats;

import com.google.android.gms.cast.MediaError;
import java.util.Set;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface FloatSet extends FloatCollection, Set<Float> {
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterable
    FloatIterator iterator();

    boolean remove(float f);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList, java.util.List
    default FloatSpliterator spliterator() {
        return FloatSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), MediaError.DetailedErrorCode.DASH_NETWORK);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection, java.util.Collection
    @Deprecated
    default boolean remove(Object obj) {
        return super.remove(obj);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection, java.util.Collection
    @Deprecated
    default boolean add(Float f) {
        return super.add(f);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection, java.util.Collection
    @Deprecated
    default boolean contains(Object obj) {
        return super.contains(obj);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection
    @Deprecated
    default boolean rem(float f) {
        return remove(f);
    }
}
