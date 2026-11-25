package org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans;

import com.google.android.gms.cast.MediaError;
import java.util.Set;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface BooleanSet extends BooleanCollection, Set<Boolean> {
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterable
    BooleanIterator iterator();

    boolean remove(boolean z);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList, java.util.List
    default BooleanSpliterator spliterator() {
        return BooleanSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), MediaError.DetailedErrorCode.DASH_NETWORK);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection, java.util.Collection
    @Deprecated
    default boolean remove(Object obj) {
        return super.remove(obj);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection, java.util.Collection
    @Deprecated
    default boolean add(Boolean bool) {
        return super.add(bool);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection, java.util.Collection
    @Deprecated
    default boolean contains(Object obj) {
        return super.contains(obj);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection
    @Deprecated
    default boolean rem(boolean z) {
        return remove(z);
    }
}
