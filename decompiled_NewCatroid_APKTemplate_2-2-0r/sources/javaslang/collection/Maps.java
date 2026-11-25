package javaslang.collection;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.control.Option;

/* JADX INFO: loaded from: classes11.dex */
final class Maps {

    @FunctionalInterface
    interface OfEntries<K, V, M extends Map<K, V>> extends Function<Iterable<Tuple2<K, V>>, M> {
    }

    static <K, V, M extends Map<K, V>> M distinct(M m) {
        return m;
    }

    static <K, V, M extends Map<K, V>> M distinctBy(M m, OfEntries<K, V, M> ofEntries, Comparator<? super Tuple2<K, V>> comparator) {
        Objects.requireNonNull(comparator, "comparator is null");
        return ofEntries.apply(m.iterator().distinctBy(comparator));
    }

    static <K, V, U, M extends Map<K, V>> M distinctBy(M m, OfEntries<K, V, M> ofEntries, Function<? super Tuple2<K, V>, ? extends U> function) {
        Objects.requireNonNull(function, "keyExtractor is null");
        return ofEntries.apply(m.iterator().distinctBy(function));
    }

    static <K, V, M extends Map<K, V>> M drop(M m, OfEntries<K, V, M> ofEntries, Supplier<M> supplier, long j) {
        if (j <= 0) {
            return m;
        }
        if (j >= m.size()) {
            return supplier.get();
        }
        return ofEntries.apply(m.iterator().drop(j));
    }

    static <K, V, M extends Map<K, V>> M dropRight(M m, OfEntries<K, V, M> ofEntries, Supplier<M> supplier, long j) {
        if (j <= 0) {
            return m;
        }
        if (j >= m.size()) {
            return supplier.get();
        }
        return ofEntries.apply(m.iterator().dropRight(j));
    }

    static <K, V, M extends Map<K, V>> M dropUntil(M m, OfEntries<K, V, M> ofEntries, Predicate<? super Tuple2<K, V>> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return (M) dropWhile(m, ofEntries, predicate.negate());
    }

    static <K, V, M extends Map<K, V>> M dropWhile(M m, OfEntries<K, V, M> ofEntries, Predicate<? super Tuple2<K, V>> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return ofEntries.apply(m.iterator().dropWhile(predicate));
    }

    static <K, V, M extends Map<K, V>> M filter(M m, OfEntries<K, V, M> ofEntries, Predicate<? super Tuple2<K, V>> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return ofEntries.apply(m.iterator().filter(predicate));
    }

    static <K, V, C, M extends Map<K, V>> Map<C, M> groupBy(M m, OfEntries<K, V, M> ofEntries, Function<? super Tuple2<K, V>, ? extends C> function) {
        return Collections.groupBy(m, function, ofEntries);
    }

    static <K, V, M extends Map<K, V>> Iterator<M> grouped(M m, OfEntries<K, V, M> ofEntries, long j) {
        return sliding(m, ofEntries, j, j);
    }

    static <K, V, M extends Map<K, V>> Option<M> initOption(M m) {
        return m.isEmpty() ? Option.none() : Option.some(m.init());
    }

    static <K, V, M extends Map<K, V>> M merge(M m, OfEntries<K, V, M> ofEntries, Map<? extends K, ? extends V> map) {
        Objects.requireNonNull(map, "that is null");
        if (m.isEmpty()) {
            return ofEntries.apply(Map.narrow(map));
        }
        return map.isEmpty() ? m : (M) map.foldLeft(m, new BiFunction() { // from class: javaslang.collection.Maps$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Maps.lambda$merge$3((Map) obj, (Tuple2) obj2);
            }
        });
    }

    static /* synthetic */ Map lambda$merge$3(Map map, Tuple2 tuple2) {
        return !map.containsKey(tuple2._1) ? put(map, tuple2) : map;
    }

    static <K, V, U extends V, M extends Map<K, V>> M merge(M m, OfEntries<K, V, M> ofEntries, Map<? extends K, U> map, final BiFunction<? super V, ? super U, ? extends V> biFunction) {
        Objects.requireNonNull(map, "that is null");
        Objects.requireNonNull(biFunction, "collisionResolution is null");
        if (m.isEmpty()) {
            return ofEntries.apply(Map.narrow(map));
        }
        return map.isEmpty() ? m : (M) map.foldLeft(m, new BiFunction() { // from class: javaslang.collection.Maps$$ExternalSyntheticLambda3
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Maps.lambda$merge$5(biFunction, (Map) obj, (Tuple2) obj2);
            }
        });
    }

    static /* synthetic */ Map lambda$merge$5(final BiFunction biFunction, Map map, Tuple2 tuple2) {
        T1 t1 = tuple2._1;
        final T2 t2 = tuple2._2;
        return map.put(t1, map.get(t1).map(new Function() { // from class: javaslang.collection.Maps$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return biFunction.apply(obj, t2);
            }
        }).getOrElse(t2));
    }

    static <K, V, M extends Map<K, V>> Tuple2<M, M> partition(M m, OfEntries<K, V, M> ofEntries, Predicate<? super Tuple2<K, V>> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        Tuple2<Iterator<Tuple2<K, V>>, Iterator<Tuple2<K, V>>> tuple2Partition = m.iterator().partition(predicate);
        return Tuple.of(ofEntries.apply(tuple2Partition._1), ofEntries.apply(tuple2Partition._2));
    }

    static <K, V, M extends Map<K, V>> M peek(M m, Consumer<? super Tuple2<K, V>> consumer) {
        Objects.requireNonNull(consumer, "action is null");
        if (!m.isEmpty()) {
            consumer.accept((Object) m.head());
        }
        return m;
    }

    static <K, V, M extends Map<K, V>> M put(M m, Tuple2<? extends K, ? extends V> tuple2) {
        Objects.requireNonNull(tuple2, "entry is null");
        return (M) m.put(tuple2._1, tuple2._2);
    }

    static <K, V, M extends Map<K, V>> M replace(M m, Tuple2<K, V> tuple2, Tuple2<K, V> tuple3) {
        Objects.requireNonNull(tuple2, "currentElement is null");
        Objects.requireNonNull(tuple3, "newElement is null");
        return m.containsKey(tuple2._1) ? m.remove(tuple2._1).put(tuple3) : m;
    }

    static <K, V, M extends Map<K, V>> M replaceAll(M m, Tuple2<K, V> tuple2, Tuple2<K, V> tuple3) {
        return (M) replace(m, tuple2, tuple3);
    }

    static <K, V, M extends Map<K, V>> M scan(M m, Supplier<M> supplier, Tuple2<K, V> tuple2, BiFunction<? super Tuple2<K, V>, ? super Tuple2<K, V>, ? extends Tuple2<K, V>> biFunction) {
        Objects.requireNonNull(biFunction, "operation is null");
        return (M) Collections.scanLeft(m, tuple2, biFunction, supplier.get(), new BiFunction() { // from class: javaslang.collection.Maps$$ExternalSyntheticLambda2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Maps.put((Map) obj, (Tuple2) obj2);
            }
        }, Function.identity());
    }

    static <K, V, M extends Map<K, V>> Iterator<M> sliding(M m, OfEntries<K, V, M> ofEntries, long j) {
        return sliding(m, ofEntries, j, 1L);
    }

    static <K, V, M extends Map<K, V>> Iterator<M> sliding(M m, OfEntries<K, V, M> ofEntries, long j, long j2) {
        return (Iterator<M>) m.iterator().sliding(j, j2).map(ofEntries);
    }

    static <K, V, M extends Map<K, V>> Tuple2<M, M> span(M m, OfEntries<K, V, M> ofEntries, Predicate<? super Tuple2<K, V>> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        Tuple2<Iterator<Tuple2<K, V>>, Iterator<Tuple2<K, V>>> tuple2Span = m.iterator().span(predicate);
        return Tuple.of(ofEntries.apply(tuple2Span._1), ofEntries.apply(tuple2Span._2));
    }

    static <K, V, M extends Map<K, V>> Option<M> tailOption(M m) {
        return m.isEmpty() ? Option.none() : Option.some(m.tail());
    }

    static <K, V, M extends Map<K, V>> M take(M m, OfEntries<K, V, M> ofEntries, long j) {
        return j >= ((long) m.size()) ? m : ofEntries.apply(m.iterator().take(j));
    }

    static <K, V, M extends Map<K, V>> M takeRight(M m, OfEntries<K, V, M> ofEntries, long j) {
        return j >= ((long) m.size()) ? m : ofEntries.apply(m.iterator().takeRight(j));
    }

    static <K, V, M extends Map<K, V>> M takeUntil(M m, OfEntries<K, V, M> ofEntries, Predicate<? super Tuple2<K, V>> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        return (M) takeWhile(m, ofEntries, predicate.negate());
    }

    static <K, V, M extends Map<K, V>> M takeWhile(M m, OfEntries<K, V, M> ofEntries, Predicate<? super Tuple2<K, V>> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        M mApply = ofEntries.apply(m.iterator().takeWhile(predicate));
        return mApply.size() == m.size() ? m : mApply;
    }
}
