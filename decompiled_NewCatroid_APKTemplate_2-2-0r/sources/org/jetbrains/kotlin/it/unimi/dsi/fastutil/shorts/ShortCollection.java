package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface ShortCollection extends ShortIterable, Collection<Short> {
    boolean add(short s);

    boolean contains(short s);

    @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
    ShortIterator iterator();

    boolean rem(short s);

    short[] toShortArray();

    @Override // java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
    default ShortSpliterator spliterator() {
        return ShortSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 320);
    }

    @Override // java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
    @Deprecated
    default boolean add(Short sh) {
        return add(sh.shortValue());
    }

    @Deprecated
    default boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        return contains(((Short) obj).shortValue());
    }

    @Deprecated
    default boolean remove(Object obj) {
        if (obj == null) {
            return false;
        }
        return rem(((Short) obj).shortValue());
    }

    @Override // java.util.Collection
    @Deprecated
    default boolean removeIf(final Predicate<? super Short> predicate) {
        ShortPredicate shortPredicate;
        if (predicate instanceof ShortPredicate) {
            shortPredicate = (ShortPredicate) predicate;
        } else {
            shortPredicate = new ShortPredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortPredicate
                public final boolean test(short s) {
                    return predicate.test(Short.valueOf(SafeMath.safeIntToShort(s)));
                }
            };
        }
        return removeIf(shortPredicate);
    }

    default boolean removeIf(ShortPredicate shortPredicate) {
        Objects.requireNonNull(shortPredicate);
        ShortIterator it = iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (shortPredicate.test(it.nextShort())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.Collection
    @Deprecated
    default Stream<Short> stream() {
        return super.stream();
    }

    @Override // java.util.Collection
    @Deprecated
    default Stream<Short> parallelStream() {
        return super.parallelStream();
    }
}
