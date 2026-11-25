package javaslang;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javaslang.collection.Iterator;
import javaslang.collection.Queue;
import javaslang.collection.Stream;

/* JADX INFO: loaded from: classes11.dex */
public interface Value<T> extends Iterable<T> {
    T get();

    boolean isEmpty();

    boolean isSingleValued();

    @Override // java.lang.Iterable
    Iterator<T> iterator();

    String stringPrefix();

    String toString();

    @Override // java.lang.Iterable
    default void forEach(Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer, "action is null");
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            consumer.accept(it.next());
        }
    }

    default List<T> toJavaList() {
        return (List) ValueModule.toJavaCollection(this, new ArrayList());
    }

    default <K, V, MAP extends Map<K, V>> MAP toJavaMap(Supplier<MAP> supplier, Function<? super T, ? extends Tuple2<? extends K, ? extends V>> function) {
        Objects.requireNonNull(function, "f is null");
        MAP map = supplier.get();
        if (!isEmpty()) {
            if (isSingleValued()) {
                Tuple2<? extends K, ? extends V> tuple2Apply = function.apply(get());
                map.put(tuple2Apply._1, tuple2Apply._2);
            } else {
                Iterator<T> it = iterator();
                while (it.hasNext()) {
                    Tuple2<? extends K, ? extends V> tuple2Apply2 = function.apply(it.next());
                    map.put(tuple2Apply2._1, tuple2Apply2._2);
                }
            }
        }
        return map;
    }

    default <SET extends Set<T>> SET toJavaSet(Supplier<SET> supplier) {
        return (SET) ValueModule.toJavaCollection(this, supplier.get());
    }

    default javaslang.collection.List<T> toList() {
        return (javaslang.collection.List) ValueModule.toTraversable(this, javaslang.collection.List.empty(), new Function() { // from class: javaslang.Value$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return javaslang.collection.List.of(obj);
            }
        }, new Function() { // from class: javaslang.Value$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return javaslang.collection.List.ofAll((Iterable) obj);
            }
        });
    }

    default Queue<T> toQueue() {
        return (Queue) ValueModule.toTraversable(this, Queue.empty(), new Function() { // from class: javaslang.Value$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Queue.of(obj);
            }
        }, new Function() { // from class: javaslang.Value$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Queue.ofAll((Iterable) obj);
            }
        });
    }

    default Stream<T> toStream() {
        return (Stream) ValueModule.toTraversable(this, Stream.empty(), new Function() { // from class: javaslang.Value$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Stream.of(obj);
            }
        }, new Function() { // from class: javaslang.Value$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Stream.ofAll((Iterable) obj);
            }
        });
    }
}
