package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface IntCollection extends IntIterable, Collection<Integer> {
    boolean add(int i);

    boolean addAll(IntCollection intCollection);

    boolean contains(int i);

    @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
    IntIterator iterator();

    boolean rem(int i);

    int[] toIntArray();

    @Override // java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
    default IntSpliterator spliterator() {
        return IntSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 320);
    }

    @Override // java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
    @Deprecated
    default boolean add(Integer num) {
        return add(num.intValue());
    }

    @Deprecated
    default boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        return contains(((Integer) obj).intValue());
    }

    @Deprecated
    default boolean remove(Object obj) {
        if (obj == null) {
            return false;
        }
        return rem(((Integer) obj).intValue());
    }

    @Override // java.util.Collection
    @Deprecated
    default boolean removeIf(final Predicate<? super Integer> predicate) {
        java.util.function.IntPredicate intPredicate;
        if (predicate instanceof java.util.function.IntPredicate) {
            intPredicate = (java.util.function.IntPredicate) predicate;
        } else {
            intPredicate = new java.util.function.IntPredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection$$ExternalSyntheticLambda0
                @Override // java.util.function.IntPredicate
                public final boolean test(int i) {
                    return predicate.test(Integer.valueOf(i));
                }
            };
        }
        return removeIf(intPredicate);
    }

    default boolean removeIf(java.util.function.IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        IntIterator it = iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (intPredicate.test(it.nextInt())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.Collection
    @Deprecated
    default Stream<Integer> stream() {
        return super.stream();
    }

    @Override // java.util.Collection
    @Deprecated
    default Stream<Integer> parallelStream() {
        return super.parallelStream();
    }
}
