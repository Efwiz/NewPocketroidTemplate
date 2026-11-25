package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface ByteCollection extends ByteIterable, Collection<Byte> {
    boolean add(byte b);

    boolean contains(byte b);

    @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterable
    ByteIterator iterator();

    boolean rem(byte b);

    byte[] toByteArray();

    @Override // java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList, java.util.List
    default ByteSpliterator spliterator() {
        return ByteSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 320);
    }

    @Override // java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection
    @Deprecated
    default boolean add(Byte b) {
        return add(b.byteValue());
    }

    @Deprecated
    default boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        return contains(((Byte) obj).byteValue());
    }

    @Deprecated
    default boolean remove(Object obj) {
        if (obj == null) {
            return false;
        }
        return rem(((Byte) obj).byteValue());
    }

    @Override // java.util.Collection
    @Deprecated
    default boolean removeIf(final Predicate<? super Byte> predicate) {
        BytePredicate bytePredicate;
        if (predicate instanceof BytePredicate) {
            bytePredicate = (BytePredicate) predicate;
        } else {
            bytePredicate = new BytePredicate() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.BytePredicate
                public final boolean test(byte b) {
                    return predicate.test(Byte.valueOf(SafeMath.safeIntToByte(b)));
                }
            };
        }
        return removeIf(bytePredicate);
    }

    default boolean removeIf(BytePredicate bytePredicate) {
        Objects.requireNonNull(bytePredicate);
        ByteIterator it = iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (bytePredicate.test(it.nextByte())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.Collection
    @Deprecated
    default Stream<Byte> stream() {
        return super.stream();
    }

    @Override // java.util.Collection
    @Deprecated
    default Stream<Byte> parallelStream() {
        return super.parallelStream();
    }
}
