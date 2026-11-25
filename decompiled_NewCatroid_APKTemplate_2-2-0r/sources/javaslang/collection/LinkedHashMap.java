package javaslang.collection;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
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
public final class LinkedHashMap<K, V> implements Serializable, Map<K, V> {
    private static final LinkedHashMap<?, ?> EMPTY = new LinkedHashMap<>(Queue.empty(), HashMap.empty());
    private static final long serialVersionUID = 1;
    private final Queue<Tuple2<K, V>> list;
    private final HashMap<K, V> map;

    public static /* synthetic */ java.util.LinkedHashMap $r8$lambda$9jZrLfmN7qos7GZMvkzgNgs9WjU() {
        return new java.util.LinkedHashMap();
    }

    static /* synthetic */ Tuple2 lambda$toJavaMap$9(Tuple2 tuple2) {
        return tuple2;
    }

    private LinkedHashMap(Queue<Tuple2<K, V>> queue, HashMap<K, V> hashMap) {
        this.list = queue;
        this.map = hashMap;
    }

    public static <K, V> LinkedHashMap<K, V> empty() {
        return (LinkedHashMap<K, V>) EMPTY;
    }

    public static <K, V> LinkedHashMap<K, V> ofEntries(Iterable<? extends Tuple2<? extends K, ? extends V>> iterable) {
        Objects.requireNonNull(iterable, "entries is null");
        if (iterable instanceof LinkedHashMap) {
            return (LinkedHashMap) iterable;
        }
        HashMap hashMapEmpty = HashMap.empty();
        Queue queueEmpty = Queue.empty();
        for (Tuple2<? extends K, ? extends V> tuple2 : iterable) {
            hashMapEmpty = hashMapEmpty.put((Tuple2) tuple2);
            queueEmpty = queueEmpty.append(tuple2);
        }
        return wrap(queueEmpty, hashMapEmpty);
    }

    @Override // javaslang.collection.Map
    public <K2, V2> LinkedHashMap<K2, V2> bimap(final Function<? super K, ? extends K2> function, final Function<? super V, ? extends V2> function2) {
        Objects.requireNonNull(function, "keyMapper is null");
        Objects.requireNonNull(function2, "valueMapper is null");
        return ofEntries(iterator().map(new Function() { // from class: javaslang.collection.LinkedHashMap$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Tuple2 tuple2 = (Tuple2) obj;
                return Tuple.of(function.apply(tuple2._1), function2.apply(tuple2._2));
            }
        }));
    }

    @Override // javaslang.collection.Map
    public boolean containsKey(K k) {
        return this.map.containsKey(k);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: distinct */
    public LinkedHashMap<K, V> m6348distinct() {
        return (LinkedHashMap) Maps.distinct(this);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: distinctBy */
    public LinkedHashMap<K, V> m6349distinctBy(Comparator<? super Tuple2<K, V>> comparator) {
        return (LinkedHashMap) Maps.distinctBy(this, new LinkedHashMap$$ExternalSyntheticLambda0(this), comparator);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: distinctBy */
    public <U> LinkedHashMap<K, V> m6350distinctBy(Function<? super Tuple2<K, V>, ? extends U> function) {
        return (LinkedHashMap) Maps.distinctBy(this, new LinkedHashMap$$ExternalSyntheticLambda0(this), function);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: drop */
    public LinkedHashMap<K, V> m6351drop(long j) {
        return (LinkedHashMap) Maps.drop(this, new LinkedHashMap$$ExternalSyntheticLambda0(this), new LinkedHashMap$$ExternalSyntheticLambda4(), j);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: dropRight */
    public LinkedHashMap<K, V> m6352dropRight(long j) {
        return (LinkedHashMap) Maps.dropRight(this, new LinkedHashMap$$ExternalSyntheticLambda0(this), new LinkedHashMap$$ExternalSyntheticLambda4(), j);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: dropUntil */
    public LinkedHashMap<K, V> m6353dropUntil(Predicate<? super Tuple2<K, V>> predicate) {
        return (LinkedHashMap) Maps.dropUntil(this, new LinkedHashMap$$ExternalSyntheticLambda0(this), predicate);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: dropWhile */
    public LinkedHashMap<K, V> m6354dropWhile(Predicate<? super Tuple2<K, V>> predicate) {
        return (LinkedHashMap) Maps.dropWhile(this, new LinkedHashMap$$ExternalSyntheticLambda0(this), predicate);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: filter */
    public LinkedHashMap<K, V> m6355filter(Predicate<? super Tuple2<K, V>> predicate) {
        return (LinkedHashMap) Maps.filter(this, new LinkedHashMap$$ExternalSyntheticLambda0(this), predicate);
    }

    @Override // javaslang.collection.Map
    public <K2, V2> LinkedHashMap<K2, V2> flatMap(final BiFunction<? super K, ? super V, ? extends Iterable<Tuple2<K2, V2>>> biFunction) {
        Objects.requireNonNull(biFunction, "mapper is null");
        return (LinkedHashMap) foldLeft(empty(), new BiFunction() { // from class: javaslang.collection.LinkedHashMap$$ExternalSyntheticLambda3
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return LinkedHashMap.lambda$flatMap$2(biFunction, (LinkedHashMap) obj, (Tuple2) obj2);
            }
        });
    }

    static /* synthetic */ LinkedHashMap lambda$flatMap$2(BiFunction biFunction, LinkedHashMap linkedHashMap, Tuple2 tuple2) {
        java.util.Iterator it = ((Iterable) biFunction.apply(tuple2._1, tuple2._2)).iterator();
        while (it.hasNext()) {
            linkedHashMap = linkedHashMap.put((Tuple2) it.next());
        }
        return linkedHashMap;
    }

    @Override // javaslang.collection.Map
    public Option<V> get(K k) {
        return this.map.get(k);
    }

    @Override // javaslang.collection.Map
    public <C> Map<C, LinkedHashMap<K, V>> groupBy(Function<? super Tuple2<K, V>, ? extends C> function) {
        return Maps.groupBy(this, new LinkedHashMap$$ExternalSyntheticLambda0(this), function);
    }

    @Override // javaslang.collection.Map
    public Iterator<LinkedHashMap<K, V>> grouped(long j) {
        return Maps.grouped(this, new LinkedHashMap$$ExternalSyntheticLambda0(this), j);
    }

    @Override // javaslang.collection.Traversable
    public Tuple2<K, V> head() {
        return this.list.head();
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: init */
    public LinkedHashMap<K, V> m6356init() {
        if (isEmpty()) {
            throw new UnsupportedOperationException("init of empty LinkedHashMap");
        }
        return ofEntries(this.list.init());
    }

    @Override // javaslang.collection.Map
    public Option<LinkedHashMap<K, V>> initOption() {
        return Maps.initOption(this);
    }

    @Override // javaslang.collection.Traversable, javaslang.Value
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override // javaslang.collection.Map, javaslang.collection.Traversable, javaslang.Value, java.lang.Iterable
    public Iterator<Tuple2<K, V>> iterator() {
        return this.list.iterator();
    }

    @Override // javaslang.collection.Map
    public Set<K> keySet() {
        return LinkedHashSet.wrap(this);
    }

    @Override // javaslang.collection.Map
    public <K2, V2> LinkedHashMap<K2, V2> map(final BiFunction<? super K, ? super V, Tuple2<K2, V2>> biFunction) {
        Objects.requireNonNull(biFunction, "mapper is null");
        return (LinkedHashMap) foldLeft(empty(), new BiFunction() { // from class: javaslang.collection.LinkedHashMap$$ExternalSyntheticLambda11
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ((LinkedHashMap) obj).put((Tuple2) ((Tuple2) obj2).map(biFunction));
            }
        });
    }

    @Override // javaslang.collection.Map
    public <W> LinkedHashMap<K, W> mapValues(final Function<? super V, ? extends W> function) {
        Objects.requireNonNull(function, "mapper is null");
        return (LinkedHashMap<K, W>) map((BiFunction) new BiFunction() { // from class: javaslang.collection.LinkedHashMap$$ExternalSyntheticLambda10
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Tuple.of(obj, function.apply(obj2));
            }
        });
    }

    @Override // javaslang.collection.Map
    public LinkedHashMap<K, V> merge(Map<? extends K, ? extends V> map) {
        return (LinkedHashMap) Maps.merge(this, new LinkedHashMap$$ExternalSyntheticLambda0(this), map);
    }

    @Override // javaslang.collection.Map
    public <U extends V> LinkedHashMap<K, V> merge(Map<? extends K, U> map, BiFunction<? super V, ? super U, ? extends V> biFunction) {
        return (LinkedHashMap) Maps.merge(this, new LinkedHashMap$$ExternalSyntheticLambda0(this), map, biFunction);
    }

    @Override // javaslang.collection.Map
    public Tuple2<LinkedHashMap<K, V>, LinkedHashMap<K, V>> partition(Predicate<? super Tuple2<K, V>> predicate) {
        return Maps.partition(this, new LinkedHashMap$$ExternalSyntheticLambda0(this), predicate);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: peek */
    public LinkedHashMap<K, V> m6358peek(Consumer<? super Tuple2<K, V>> consumer) {
        return (LinkedHashMap) Maps.peek(this, consumer);
    }

    @Override // javaslang.collection.Map
    public LinkedHashMap<K, V> put(final K k, V v) {
        Queue<Tuple2<K, V>> queueFilter = this.list;
        HashMap<K, V> hashMapRemove = this.map;
        if (containsKey(k)) {
            queueFilter = queueFilter.filter(new Predicate() { // from class: javaslang.collection.LinkedHashMap$$ExternalSyntheticLambda9
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return LinkedHashMap.lambda$put$5(k, (Tuple2) obj);
                }
            });
            hashMapRemove = hashMapRemove.remove((Object) k);
        }
        return new LinkedHashMap<>(queueFilter.append(Tuple.of(k, v)), hashMapRemove.put((Object) k, (Object) v));
    }

    static /* synthetic */ boolean lambda$put$5(Object obj, Tuple2 tuple2) {
        return !Objects.equals(tuple2._1, obj);
    }

    @Override // javaslang.collection.Map
    public LinkedHashMap<K, V> put(Tuple2<? extends K, ? extends V> tuple2) {
        return (LinkedHashMap) Maps.put(this, tuple2);
    }

    @Override // javaslang.collection.Map
    public LinkedHashMap<K, V> remove(final K k) {
        return containsKey(k) ? wrap(this.list.removeFirst(new Predicate() { // from class: javaslang.collection.LinkedHashMap$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.equals(((Tuple2) obj)._1, k);
            }
        }), this.map.remove((Object) k)) : this;
    }

    @Override // javaslang.collection.Map
    public LinkedHashMap<K, V> removeAll(Iterable<? extends K> iterable) {
        Objects.requireNonNull(iterable, "keys is null");
        final HashSet hashSetOfAll = HashSet.ofAll(iterable);
        Queue<Tuple2<K, V>> queueFilter = this.list.filter(new Predicate() { // from class: javaslang.collection.LinkedHashMap$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return LinkedHashMap.lambda$removeAll$7(hashSetOfAll, (Tuple2) obj);
            }
        });
        return queueFilter.size() == size() ? this : wrap(queueFilter, this.map.m6355filter((Predicate) new Predicate() { // from class: javaslang.collection.LinkedHashMap$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return LinkedHashMap.lambda$removeAll$8(hashSetOfAll, (Tuple2) obj);
            }
        }));
    }

    static /* synthetic */ boolean lambda$removeAll$7(HashSet hashSet, Tuple2 tuple2) {
        return !hashSet.contains(tuple2._1);
    }

    static /* synthetic */ boolean lambda$removeAll$8(HashSet hashSet, Tuple2 tuple2) {
        return !hashSet.contains(tuple2._1);
    }

    @Override // javaslang.collection.Map
    public LinkedHashMap<K, V> replace(Tuple2<K, V> tuple2, Tuple2<K, V> tuple3) {
        Objects.requireNonNull(tuple2, "currentElement is null");
        Objects.requireNonNull(tuple3, "newElement is null");
        if (Objects.equals(tuple2, tuple3) || !contains(tuple2)) {
            return this;
        }
        Queue<Tuple2<K, V>> queueRemove = this.list;
        HashMap<K, V> hashMap = this.map;
        K k = tuple2._1;
        K k2 = tuple3._1;
        if (!Objects.equals(k, k2)) {
            Option<V> option = hashMap.get(k2);
            if (option.isDefined()) {
                queueRemove = queueRemove.remove(Tuple.of(k2, option.get()));
            }
        }
        return wrap(queueRemove.replace(tuple2, tuple3), hashMap.remove((Object) k).put((Tuple2) tuple3));
    }

    @Override // javaslang.collection.Map
    public LinkedHashMap<K, V> replaceAll(Tuple2<K, V> tuple2, Tuple2<K, V> tuple3) {
        return (LinkedHashMap) Maps.replaceAll(this, tuple2, tuple3);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: retainAll */
    public LinkedHashMap<K, V> m6359retainAll(Iterable<? extends Tuple2<K, V>> iterable) {
        Objects.requireNonNull(iterable, "elements is null");
        LinkedHashMap<K, V> linkedHashMapEmpty = empty();
        for (Tuple2<K, V> tuple2 : iterable) {
            if (contains(tuple2)) {
                linkedHashMapEmpty = linkedHashMapEmpty.put((Object) tuple2._1, (Object) tuple2._2);
            }
        }
        return linkedHashMapEmpty;
    }

    @Override // javaslang.collection.Map
    public LinkedHashMap<K, V> scan(Tuple2<K, V> tuple2, BiFunction<? super Tuple2<K, V>, ? super Tuple2<K, V>, ? extends Tuple2<K, V>> biFunction) {
        return (LinkedHashMap) Maps.scan(this, new LinkedHashMap$$ExternalSyntheticLambda4(), tuple2, biFunction);
    }

    @Override // javaslang.collection.Map, javaslang.collection.Traversable
    public int size() {
        return this.map.size();
    }

    @Override // javaslang.collection.Map
    public Iterator<LinkedHashMap<K, V>> sliding(long j) {
        return Maps.sliding(this, new LinkedHashMap$$ExternalSyntheticLambda0(this), j);
    }

    @Override // javaslang.collection.Map
    public Iterator<LinkedHashMap<K, V>> sliding(long j, long j2) {
        return Maps.sliding(this, new LinkedHashMap$$ExternalSyntheticLambda0(this), j, j2);
    }

    @Override // javaslang.collection.Map
    public Tuple2<LinkedHashMap<K, V>, LinkedHashMap<K, V>> span(Predicate<? super Tuple2<K, V>> predicate) {
        return Maps.span(this, new LinkedHashMap$$ExternalSyntheticLambda0(this), predicate);
    }

    @Override // javaslang.collection.Map, javaslang.collection.Traversable
    public LinkedHashMap<K, V> tail() {
        if (isEmpty()) {
            throw new UnsupportedOperationException("tail of empty LinkedHashMap");
        }
        return ofEntries(this.list.tail());
    }

    @Override // javaslang.collection.Map
    public Option<LinkedHashMap<K, V>> tailOption() {
        return Maps.tailOption(this);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: take */
    public LinkedHashMap<K, V> m6360take(long j) {
        return (LinkedHashMap) Maps.take(this, new LinkedHashMap$$ExternalSyntheticLambda0(this), j);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: takeRight */
    public LinkedHashMap<K, V> m6361takeRight(long j) {
        return (LinkedHashMap) Maps.takeRight(this, new LinkedHashMap$$ExternalSyntheticLambda0(this), j);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: takeUntil */
    public LinkedHashMap<K, V> m6362takeUntil(Predicate<? super Tuple2<K, V>> predicate) {
        return (LinkedHashMap) Maps.takeUntil(this, new LinkedHashMap$$ExternalSyntheticLambda0(this), predicate);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: takeWhile */
    public LinkedHashMap<K, V> m6363takeWhile(Predicate<? super Tuple2<K, V>> predicate) {
        return (LinkedHashMap) Maps.takeWhile(this, new LinkedHashMap$$ExternalSyntheticLambda0(this), predicate);
    }

    @Override // javaslang.collection.Map
    public java.util.LinkedHashMap<K, V> toJavaMap() {
        return (java.util.LinkedHashMap) toJavaMap(new Supplier() { // from class: javaslang.collection.LinkedHashMap$$ExternalSyntheticLambda12
            @Override // java.util.function.Supplier
            public final Object get() {
                return LinkedHashMap.$r8$lambda$9jZrLfmN7qos7GZMvkzgNgs9WjU();
            }
        }, new Function() { // from class: javaslang.collection.LinkedHashMap$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return LinkedHashMap.lambda$toJavaMap$9((Tuple2) obj);
            }
        });
    }

    @Override // javaslang.collection.Map
    public Seq<V> values() {
        return (Seq<V>) map(new Function() { // from class: javaslang.collection.LinkedHashMap$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Tuple2) obj)._2;
            }
        });
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof LinkedHashMap) {
            return this.list.equals(((LinkedHashMap) obj).list);
        }
        return false;
    }

    public int hashCode() {
        return this.list.hashCode();
    }

    private Object readResolve() {
        return isEmpty() ? EMPTY : this;
    }

    @Override // javaslang.Value
    public String stringPrefix() {
        return "LinkedHashMap";
    }

    @Override // javaslang.Value
    public String toString() {
        return mkString(stringPrefix() + "(", ", ", Constant.AFTER_QUTO);
    }

    private static <K, V> LinkedHashMap<K, V> wrap(Queue<Tuple2<K, V>> queue, HashMap<K, V> hashMap) {
        return queue.isEmpty() ? empty() : new LinkedHashMap<>(queue, hashMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LinkedHashMap<K, V> createFromEntries(Iterable<Tuple2<K, V>> iterable) {
        return ofEntries(iterable);
    }
}
