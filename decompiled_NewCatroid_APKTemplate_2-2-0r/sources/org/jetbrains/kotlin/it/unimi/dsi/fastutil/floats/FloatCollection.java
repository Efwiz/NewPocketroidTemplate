package org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface FloatCollection extends FloatIterable, Collection<Float> {
    boolean add(float f);

    boolean contains(float f);

    @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterable
    FloatIterator iterator();

    boolean rem(float f);

    float[] toFloatArray();

    @Override // java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList, java.util.List
    default FloatSpliterator spliterator() {
        return FloatSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 320);
    }

    @Override // java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection
    @Deprecated
    default boolean add(Float f) {
        return add(f.floatValue());
    }

    @Deprecated
    default boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        return contains(((Float) obj).floatValue());
    }

    @Deprecated
    default boolean remove(Object obj) {
        if (obj == null) {
            return false;
        }
        return rem(((Float) obj).floatValue());
    }

    @Override // java.util.Collection
    @Deprecated
    default boolean removeIf(final Predicate<? super Float> predicate) {
        FloatPredicate floatPredicate;
        if (predicate instanceof FloatPredicate) {
            floatPredicate = (FloatPredicate) predicate;
        } else {
            floatPredicate = new FloatPredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatPredicate
                public final boolean test(float f) {
                    return predicate.test(Float.valueOf(SafeMath.safeDoubleToFloat(f)));
                }
            };
        }
        return removeIf(floatPredicate);
    }

    default boolean removeIf(FloatPredicate floatPredicate) {
        Objects.requireNonNull(floatPredicate);
        FloatIterator it = iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (floatPredicate.test(it.nextFloat())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.Collection
    @Deprecated
    default Stream<Float> stream() {
        return super.stream();
    }

    @Override // java.util.Collection
    @Deprecated
    default Stream<Float> parallelStream() {
        return super.parallelStream();
    }
}
