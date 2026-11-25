package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import com.google.android.gms.cast.MediaError;
import java.util.Set;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface DoubleSet extends DoubleCollection, Set<Double> {
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
    DoubleIterator iterator();

    boolean remove(double d);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
    default DoubleSpliterator spliterator() {
        return DoubleSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), MediaError.DetailedErrorCode.DASH_NETWORK);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, java.util.Collection
    @Deprecated
    default boolean remove(Object obj) {
        return super.remove(obj);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, java.util.Collection
    @Deprecated
    default boolean add(Double d) {
        return super.add(d);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, java.util.Collection
    @Deprecated
    default boolean contains(Object obj) {
        return super.contains(obj);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
    @Deprecated
    default boolean rem(double d) {
        return remove(d);
    }
}
