package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface LongCollection extends LongIterable, Collection<Long> {
    boolean add(long j);

    boolean contains(long j);

    @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
    LongIterator iterator();

    boolean rem(long j);

    long[] toLongArray();

    @Override // java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
    default LongSpliterator spliterator() {
        return LongSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 320);
    }

    @Override // java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
    @Deprecated
    default boolean add(Long l) {
        return add(l.longValue());
    }

    @Deprecated
    default boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        return contains(((Long) obj).longValue());
    }

    @Deprecated
    default boolean remove(Object obj) {
        if (obj == null) {
            return false;
        }
        return rem(((Long) obj).longValue());
    }

    @Override // java.util.Collection
    @Deprecated
    default boolean removeIf(final Predicate<? super Long> predicate) {
        java.util.function.LongPredicate longPredicate;
        if (predicate instanceof java.util.function.LongPredicate) {
            longPredicate = (java.util.function.LongPredicate) predicate;
        } else {
            longPredicate = new java.util.function.LongPredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection$$ExternalSyntheticLambda0
                @Override // java.util.function.LongPredicate
                public final boolean test(long j) {
                    return predicate.test(Long.valueOf(j));
                }
            };
        }
        return removeIf(longPredicate);
    }

    default boolean removeIf(java.util.function.LongPredicate longPredicate) {
        Objects.requireNonNull(longPredicate);
        LongIterator it = iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (longPredicate.test(it.nextLong())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.Collection
    @Deprecated
    default Stream<Long> stream() {
        return super.stream();
    }

    @Override // java.util.Collection
    @Deprecated
    default Stream<Long> parallelStream() {
        return super.parallelStream();
    }
}
