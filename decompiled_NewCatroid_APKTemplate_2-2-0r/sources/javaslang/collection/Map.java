package javaslang.collection;

import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javaslang.Function1;
import javaslang.Tuple2;
import javaslang.control.Option;

/* JADX INFO: loaded from: classes11.dex */
public interface Map<K, V> extends Serializable, Function1<K, V>, Traversable<Tuple2<K, V>> {
    public static final long serialVersionUID = 1;

    /* JADX INFO: renamed from: $r8$lambda$GS-ECQyPUrmJMrW7a_HjCuF6CYo, reason: not valid java name */
    static /* synthetic */ NoSuchElementException m2889$r8$lambda$GSECQyPUrmJMrW7a_HjCuF6CYo() {
        return new NoSuchElementException();
    }

    /* JADX WARN: Multi-variable type inference failed */
    static <K, V> Map<K, V> narrow(Map<? extends K, ? extends V> map) {
        return map;
    }

    <K2, V2> Map<K2, V2> bimap(Function<? super K, ? extends K2> function, Function<? super V, ? extends V2> function2);

    boolean containsKey(K k);

    Map<K, V> distinct();

    Map<K, V> distinctBy(Comparator<? super Tuple2<K, V>> comparator);

    <U> Map<K, V> distinctBy(Function<? super Tuple2<K, V>, ? extends U> function);

    Map<K, V> drop(long j);

    Map<K, V> dropRight(long j);

    Map<K, V> dropUntil(Predicate<? super Tuple2<K, V>> predicate);

    Map<K, V> dropWhile(Predicate<? super Tuple2<K, V>> predicate);

    Map<K, V> filter(Predicate<? super Tuple2<K, V>> predicate);

    <K2, V2> Map<K2, V2> flatMap(BiFunction<? super K, ? super V, ? extends Iterable<Tuple2<K2, V2>>> biFunction);

    Option<V> get(K k);

    <C> Map<C, ? extends Map<K, V>> groupBy(Function<? super Tuple2<K, V>, ? extends C> function);

    Iterator<? extends Map<K, V>> grouped(long j);

    Map<K, V> init();

    Option<? extends Map<K, V>> initOption();

    @Override // javaslang.collection.Traversable
    default boolean isTraversableAgain() {
        return true;
    }

    @Override // javaslang.collection.Traversable, javaslang.Value, java.lang.Iterable
    Iterator<Tuple2<K, V>> iterator();

    Set<K> keySet();

    <K2, V2> Map<K2, V2> map(BiFunction<? super K, ? super V, Tuple2<K2, V2>> biFunction);

    <V2> Map<K, V2> mapValues(Function<? super V, ? extends V2> function);

    Map<K, V> merge(Map<? extends K, ? extends V> map);

    <U extends V> Map<K, V> merge(Map<? extends K, U> map, BiFunction<? super V, ? super U, ? extends V> biFunction);

    Tuple2<? extends Map<K, V>, ? extends Map<K, V>> partition(Predicate<? super Tuple2<K, V>> predicate);

    /* JADX INFO: renamed from: peek */
    Map<K, V> mo6357peek(Consumer<? super Tuple2<K, V>> consumer);

    Map<K, V> put(K k, V v);

    Map<K, V> put(Tuple2<? extends K, ? extends V> tuple2);

    Map<K, V> remove(K k);

    Map<K, V> removeAll(Iterable<? extends K> iterable);

    Map<K, V> replace(Tuple2<K, V> tuple2, Tuple2<K, V> tuple3);

    Map<K, V> replaceAll(Tuple2<K, V> tuple2, Tuple2<K, V> tuple3);

    Map<K, V> retainAll(Iterable<? extends Tuple2<K, V>> iterable);

    Map<K, V> scan(Tuple2<K, V> tuple2, BiFunction<? super Tuple2<K, V>, ? super Tuple2<K, V>, ? extends Tuple2<K, V>> biFunction);

    int size();

    Iterator<? extends Map<K, V>> sliding(long j);

    Iterator<? extends Map<K, V>> sliding(long j, long j2);

    Tuple2<? extends Map<K, V>, ? extends Map<K, V>> span(Predicate<? super Tuple2<K, V>> predicate);

    @Override // javaslang.collection.Traversable
    Map<K, V> tail();

    Option<? extends Map<K, V>> tailOption();

    Map<K, V> take(long j);

    Map<K, V> takeRight(long j);

    Map<K, V> takeUntil(Predicate<? super Tuple2<K, V>> predicate);

    Map<K, V> takeWhile(Predicate<? super Tuple2<K, V>> predicate);

    java.util.Map<K, V> toJavaMap();

    Seq<V> values();

    @Override // javaslang.Function1, java.util.function.Function
    default V apply(K k) {
        return get(k).getOrElseThrow(new Supplier() { // from class: javaslang.collection.Map$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return Map.m2889$r8$lambda$GSECQyPUrmJMrW7a_HjCuF6CYo();
            }
        });
    }

    default boolean contains(final Tuple2<K, V> tuple2) {
        return ((Boolean) get(tuple2._1).map(new Function() { // from class: javaslang.collection.Map$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(Objects.equals(obj, tuple2._2));
            }
        }).getOrElse(false)).booleanValue();
    }

    @Override // javaslang.collection.Traversable
    default int length() {
        return size();
    }

    default <U> Seq<U> map(Function<? super Tuple2<K, V>, ? extends U> function) {
        Objects.requireNonNull(function, "mapper is null");
        return iterator().map(function).toStream();
    }

    @Override // java.lang.Iterable
    default Spliterator<Tuple2<K, V>> spliterator() {
        return Spliterators.spliterator(iterator(), length(), 1040);
    }
}
