package org.jetbrains.kotlin.one.util.streamex;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;

/* JADX INFO: loaded from: classes2.dex */
interface Internals {
    public static final Function<int[], Integer> UNBOX_INT = new Function() { // from class: org.jetbrains.kotlin.one.util.streamex.Internals$$ExternalSyntheticLambda1
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return Integer.valueOf(((int[]) obj)[0]);
        }
    };
    public static final Function<long[], Long> UNBOX_LONG = new Function() { // from class: org.jetbrains.kotlin.one.util.streamex.Internals$$ExternalSyntheticLambda2
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return Long.valueOf(((long[]) obj)[0]);
        }
    };
    public static final Function<double[], Double> UNBOX_DOUBLE = new Function() { // from class: org.jetbrains.kotlin.one.util.streamex.Internals$$ExternalSyntheticLambda3
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return Double.valueOf(((double[]) obj)[0]);
        }
    };
    public static final Object NONE = new Object();
    public static final Set<Collector.Characteristics> NO_CHARACTERISTICS = EnumSet.noneOf(Collector.Characteristics.class);
    public static final Set<Collector.Characteristics> UNORDERED_CHARACTERISTICS = EnumSet.of(Collector.Characteristics.UNORDERED);
    public static final Set<Collector.Characteristics> UNORDERED_ID_CHARACTERISTICS = EnumSet.of(Collector.Characteristics.UNORDERED, Collector.Characteristics.IDENTITY_FINISH);
    public static final Set<Collector.Characteristics> ID_CHARACTERISTICS = EnumSet.of(Collector.Characteristics.IDENTITY_FINISH);

    static /* synthetic */ boolean lambda$alwaysTrue$7(Object obj) {
        return true;
    }

    public static class CancelException extends Error {
        CancelException() {
            super(null, null, false, false);
        }
    }

    public static class ArrayCollection extends AbstractCollection<Object> {
        private final Object[] arr;

        ArrayCollection(Object[] objArr) {
            this.arr = objArr;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator<Object> iterator() {
            return Arrays.asList(this.arr).iterator();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return this.arr.length;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public Object[] toArray() {
            return this.arr;
        }
    }

    public static abstract class CloneableSpliterator<T, S extends CloneableSpliterator<T, ?>> implements Cloneable, Spliterator<T> {
        S doClone() {
            try {
                return (S) clone();
            } catch (CloneNotSupportedException unused) {
                throw new InternalError();
            }
        }
    }

    static <T> Predicate<T> alwaysTrue() {
        return new Predicate() { // from class: org.jetbrains.kotlin.one.util.streamex.Internals$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Internals.lambda$alwaysTrue$7(obj);
            }
        };
    }

    static <A> Predicate<A> finished(Collector<?, A, ?> collector) {
        if (collector instanceof CancellableCollector) {
            return ((CancellableCollector) collector).finished();
        }
        return null;
    }
}
