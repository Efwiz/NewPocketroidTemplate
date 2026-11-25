package org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface BooleanCollection extends BooleanIterable, Collection<Boolean> {
    boolean add(boolean z);

    boolean contains(boolean z);

    @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterable
    BooleanIterator iterator();

    boolean rem(boolean z);

    boolean[] toBooleanArray();

    @Override // java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList, java.util.List
    default BooleanSpliterator spliterator() {
        return BooleanSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 320);
    }

    @Override // java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection
    @Deprecated
    default boolean add(Boolean bool) {
        return add(bool.booleanValue());
    }

    @Deprecated
    default boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        return contains(((Boolean) obj).booleanValue());
    }

    @Deprecated
    default boolean remove(Object obj) {
        if (obj == null) {
            return false;
        }
        return rem(((Boolean) obj).booleanValue());
    }

    @Override // java.util.Collection
    @Deprecated
    default boolean removeIf(final Predicate<? super Boolean> predicate) {
        BooleanPredicate booleanPredicate;
        if (predicate instanceof BooleanPredicate) {
            booleanPredicate = (BooleanPredicate) predicate;
        } else {
            booleanPredicate = new BooleanPredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanPredicate
                public final boolean test(boolean z) {
                    return predicate.test(Boolean.valueOf(z));
                }
            };
        }
        return removeIf(booleanPredicate);
    }

    default boolean removeIf(BooleanPredicate booleanPredicate) {
        Objects.requireNonNull(booleanPredicate);
        BooleanIterator it = iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (booleanPredicate.test(it.nextBoolean())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }
}
