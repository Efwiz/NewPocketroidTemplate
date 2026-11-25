package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface DoubleCollection extends DoubleIterable, Collection<Double> {
    boolean add(double d);

    boolean contains(double d);

    @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
    DoubleIterator iterator();

    boolean rem(double d);

    double[] toDoubleArray();

    @Override // java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
    default DoubleSpliterator spliterator() {
        return DoubleSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 320);
    }

    @Override // java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
    @Deprecated
    default boolean add(Double d) {
        return add(d.doubleValue());
    }

    @Deprecated
    default boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        return contains(((Double) obj).doubleValue());
    }

    @Deprecated
    default boolean remove(Object obj) {
        if (obj == null) {
            return false;
        }
        return rem(((Double) obj).doubleValue());
    }

    @Override // java.util.Collection
    @Deprecated
    default boolean removeIf(final Predicate<? super Double> predicate) {
        java.util.function.DoublePredicate doublePredicate;
        if (predicate instanceof java.util.function.DoublePredicate) {
            doublePredicate = (java.util.function.DoublePredicate) predicate;
        } else {
            doublePredicate = new java.util.function.DoublePredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection$$ExternalSyntheticLambda0
                @Override // java.util.function.DoublePredicate
                public final boolean test(double d) {
                    return predicate.test(Double.valueOf(d));
                }
            };
        }
        return removeIf(doublePredicate);
    }

    default boolean removeIf(java.util.function.DoublePredicate doublePredicate) {
        Objects.requireNonNull(doublePredicate);
        DoubleIterator it = iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (doublePredicate.test(it.nextDouble())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.Collection
    @Deprecated
    default Stream<Double> stream() {
        return super.stream();
    }

    @Override // java.util.Collection
    @Deprecated
    default Stream<Double> parallelStream() {
        return super.parallelStream();
    }
}
