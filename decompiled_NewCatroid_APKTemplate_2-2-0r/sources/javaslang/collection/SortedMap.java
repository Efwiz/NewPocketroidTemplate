package javaslang.collection;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javaslang.Tuple2;

/* JADX INFO: loaded from: classes11.dex */
public interface SortedMap<K, V> extends Map<K, V> {
    public static final long serialVersionUID = 1;

    @Override // javaslang.collection.Map
    <K2, V2> SortedMap<K2, V2> bimap(Function<? super K, ? extends K2> function, Function<? super V, ? extends V2> function2);

    @Override // javaslang.collection.Map
    SortedMap<K, V> distinct();

    @Override // javaslang.collection.Map
    SortedMap<K, V> distinctBy(Comparator<? super Tuple2<K, V>> comparator);

    @Override // javaslang.collection.Map
    <U> SortedMap<K, V> distinctBy(Function<? super Tuple2<K, V>, ? extends U> function);

    @Override // javaslang.collection.Map
    SortedMap<K, V> drop(long j);

    @Override // javaslang.collection.Map
    SortedMap<K, V> dropRight(long j);

    @Override // javaslang.collection.Map
    SortedMap<K, V> dropUntil(Predicate<? super Tuple2<K, V>> predicate);

    @Override // javaslang.collection.Map
    SortedMap<K, V> dropWhile(Predicate<? super Tuple2<K, V>> predicate);

    @Override // javaslang.collection.Map
    SortedMap<K, V> filter(Predicate<? super Tuple2<K, V>> predicate);

    @Override // javaslang.collection.Map
    <K2, V2> SortedMap<K2, V2> flatMap(BiFunction<? super K, ? super V, ? extends Iterable<Tuple2<K2, V2>>> biFunction);

    @Override // javaslang.collection.Map
    SortedMap<K, V> init();

    @Override // javaslang.collection.Map
    SortedSet<K> keySet();

    @Override // javaslang.collection.Map
    <K2, V2> SortedMap<K2, V2> map(BiFunction<? super K, ? super V, Tuple2<K2, V2>> biFunction);

    @Override // javaslang.collection.Map
    <V2> SortedMap<K, V2> mapValues(Function<? super V, ? extends V2> function);

    @Override // javaslang.collection.Map
    SortedMap<K, V> merge(Map<? extends K, ? extends V> map);

    @Override // javaslang.collection.Map
    <U extends V> SortedMap<K, V> merge(Map<? extends K, U> map, BiFunction<? super V, ? super U, ? extends V> biFunction);

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: peek */
    SortedMap<K, V> mo6357peek(Consumer<? super Tuple2<K, V>> consumer);

    @Override // javaslang.collection.Map
    SortedMap<K, V> put(K k, V v);

    @Override // javaslang.collection.Map
    SortedMap<K, V> put(Tuple2<? extends K, ? extends V> tuple2);

    @Override // javaslang.collection.Map
    SortedMap<K, V> remove(K k);

    @Override // javaslang.collection.Map
    SortedMap<K, V> removeAll(Iterable<? extends K> iterable);

    @Override // javaslang.collection.Map
    SortedMap<K, V> replace(Tuple2<K, V> tuple2, Tuple2<K, V> tuple3);

    @Override // javaslang.collection.Map
    SortedMap<K, V> replaceAll(Tuple2<K, V> tuple2, Tuple2<K, V> tuple3);

    @Override // javaslang.collection.Map
    SortedMap<K, V> retainAll(Iterable<? extends Tuple2<K, V>> iterable);

    @Override // javaslang.collection.Map
    SortedMap<K, V> scan(Tuple2<K, V> tuple2, BiFunction<? super Tuple2<K, V>, ? super Tuple2<K, V>, ? extends Tuple2<K, V>> biFunction);

    @Override // javaslang.collection.Map, javaslang.collection.Traversable
    SortedMap<K, V> tail();

    @Override // javaslang.collection.Map
    SortedMap<K, V> take(long j);

    @Override // javaslang.collection.Map
    SortedMap<K, V> takeRight(long j);

    @Override // javaslang.collection.Map
    SortedMap<K, V> takeUntil(Predicate<? super Tuple2<K, V>> predicate);

    @Override // javaslang.collection.Map
    SortedMap<K, V> takeWhile(Predicate<? super Tuple2<K, V>> predicate);

    @Override // javaslang.collection.Map
    java.util.SortedMap<K, V> toJavaMap();

    static /* synthetic */ NoSuchElementException lambda$last$0() {
        return new NoSuchElementException("last on empty SortedMap");
    }

    @Override // javaslang.collection.Traversable
    default Tuple2<K, V> last() {
        return max().getOrElseThrow(new Supplier() { // from class: javaslang.collection.SortedMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return SortedMap.lambda$last$0();
            }
        });
    }
}
