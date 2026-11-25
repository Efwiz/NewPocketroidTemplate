package javaslang.collection;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NoSuchElementException;
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
public final class HashMap<K, V> implements Serializable, Map<K, V> {
    private static final HashMap<?, ?> EMPTY = new HashMap<>(HashArrayMappedTrie.empty());
    private static final long serialVersionUID = 1;
    private final HashArrayMappedTrie<K, V> trie;

    /* JADX INFO: renamed from: $r8$lambda$sUZQVfQ4XNh1oZg9vIcD2TcdV-o, reason: not valid java name */
    public static /* synthetic */ java.util.HashMap m2887$r8$lambda$sUZQVfQ4XNh1oZg9vIcD2TcdVo() {
        return new java.util.HashMap();
    }

    static /* synthetic */ Tuple2 lambda$toJavaMap$5(Tuple2 tuple2) {
        return tuple2;
    }

    private HashMap(HashArrayMappedTrie<K, V> hashArrayMappedTrie) {
        this.trie = hashArrayMappedTrie;
    }

    public static <K, V> HashMap<K, V> empty() {
        return (HashMap<K, V>) EMPTY;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [javaslang.collection.HashArrayMappedTrie] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    public static <K, V> HashMap<K, V> ofEntries(Iterable<? extends Tuple2<? extends K, ? extends V>> iterable) {
        Objects.requireNonNull(iterable, "entries is null");
        if (iterable instanceof HashMap) {
            return (HashMap) iterable;
        }
        HashArrayMappedTrie hashArrayMappedTrieEmpty = HashArrayMappedTrie.empty();
        ?? Put = hashArrayMappedTrieEmpty;
        for (Tuple2<? extends K, ? extends V> tuple2 : iterable) {
            Put = Put.put(tuple2._1, tuple2._2);
        }
        return wrap(Put);
    }

    @Override // javaslang.collection.Map
    public <K2, V2> HashMap<K2, V2> bimap(final Function<? super K, ? extends K2> function, final Function<? super V, ? extends V2> function2) {
        Objects.requireNonNull(function, "keyMapper is null");
        Objects.requireNonNull(function2, "valueMapper is null");
        return ofEntries(iterator().map(new Function() { // from class: javaslang.collection.HashMap$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Tuple2 tuple2 = (Tuple2) obj;
                return Tuple.of(function.apply(tuple2._1), function2.apply(tuple2._2));
            }
        }));
    }

    @Override // javaslang.collection.Map
    public boolean containsKey(K k) {
        return this.trie.containsKey(k);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: distinct */
    public HashMap<K, V> m6348distinct() {
        return (HashMap) Maps.distinct(this);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: distinctBy */
    public HashMap<K, V> m6349distinctBy(Comparator<? super Tuple2<K, V>> comparator) {
        return (HashMap) Maps.distinctBy(this, new HashMap$$ExternalSyntheticLambda0(this), comparator);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: distinctBy */
    public <U> HashMap<K, V> m6350distinctBy(Function<? super Tuple2<K, V>, ? extends U> function) {
        return (HashMap) Maps.distinctBy(this, new HashMap$$ExternalSyntheticLambda0(this), function);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: drop */
    public HashMap<K, V> m6351drop(long j) {
        return (HashMap) Maps.drop(this, new HashMap$$ExternalSyntheticLambda0(this), new HashMap$$ExternalSyntheticLambda1(), j);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: dropRight */
    public HashMap<K, V> m6352dropRight(long j) {
        return (HashMap) Maps.dropRight(this, new HashMap$$ExternalSyntheticLambda0(this), new HashMap$$ExternalSyntheticLambda1(), j);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: dropUntil */
    public HashMap<K, V> m6353dropUntil(Predicate<? super Tuple2<K, V>> predicate) {
        return (HashMap) Maps.dropUntil(this, new HashMap$$ExternalSyntheticLambda0(this), predicate);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: dropWhile */
    public HashMap<K, V> m6354dropWhile(Predicate<? super Tuple2<K, V>> predicate) {
        return (HashMap) Maps.dropWhile(this, new HashMap$$ExternalSyntheticLambda0(this), predicate);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: filter */
    public HashMap<K, V> m6355filter(Predicate<? super Tuple2<K, V>> predicate) {
        return (HashMap) Maps.filter(this, new HashMap$$ExternalSyntheticLambda0(this), predicate);
    }

    @Override // javaslang.collection.Map
    public <K2, V2> HashMap<K2, V2> flatMap(final BiFunction<? super K, ? super V, ? extends Iterable<Tuple2<K2, V2>>> biFunction) {
        Objects.requireNonNull(biFunction, "mapper is null");
        return (HashMap) foldLeft(empty(), new BiFunction() { // from class: javaslang.collection.HashMap$$ExternalSyntheticLambda2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return HashMap.lambda$flatMap$2(biFunction, (HashMap) obj, (Tuple2) obj2);
            }
        });
    }

    static /* synthetic */ HashMap lambda$flatMap$2(BiFunction biFunction, HashMap hashMap, Tuple2 tuple2) {
        java.util.Iterator it = ((Iterable) biFunction.apply(tuple2._1, tuple2._2)).iterator();
        while (it.hasNext()) {
            hashMap = hashMap.put((Tuple2) it.next());
        }
        return hashMap;
    }

    @Override // javaslang.collection.Map
    public Option<V> get(K k) {
        return this.trie.get(k);
    }

    @Override // javaslang.collection.Map
    public <C> Map<C, HashMap<K, V>> groupBy(Function<? super Tuple2<K, V>, ? extends C> function) {
        return Maps.groupBy(this, new HashMap$$ExternalSyntheticLambda0(this), function);
    }

    @Override // javaslang.collection.Map
    public Iterator<HashMap<K, V>> grouped(long j) {
        return Maps.grouped(this, new HashMap$$ExternalSyntheticLambda0(this), j);
    }

    @Override // javaslang.collection.Traversable
    public Tuple2<K, V> head() {
        if (isEmpty()) {
            throw new NoSuchElementException("head of empty HashMap");
        }
        return iterator().next();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: init */
    public HashMap<K, V> m6356init() {
        if (this.trie.isEmpty()) {
            throw new UnsupportedOperationException("init of empty HashMap");
        }
        return remove(((Tuple2) last())._1);
    }

    @Override // javaslang.collection.Map
    public Option<HashMap<K, V>> initOption() {
        return Maps.initOption(this);
    }

    @Override // javaslang.collection.Traversable, javaslang.Value
    public boolean isEmpty() {
        return this.trie.isEmpty();
    }

    @Override // javaslang.collection.Map, javaslang.collection.Traversable, javaslang.Value, java.lang.Iterable
    public Iterator<Tuple2<K, V>> iterator() {
        return this.trie.iterator();
    }

    @Override // javaslang.collection.Map
    public Set<K> keySet() {
        return HashSet.ofAll(iterator().map(new Function() { // from class: javaslang.collection.HashMap$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Tuple2) obj)._1();
            }
        }));
    }

    @Override // javaslang.collection.Map
    public <K2, V2> HashMap<K2, V2> map(final BiFunction<? super K, ? super V, Tuple2<K2, V2>> biFunction) {
        Objects.requireNonNull(biFunction, "mapper is null");
        return (HashMap) foldLeft(empty(), new BiFunction() { // from class: javaslang.collection.HashMap$$ExternalSyntheticLambda6
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ((HashMap) obj).put((Tuple2) ((Tuple2) obj2).map(biFunction));
            }
        });
    }

    @Override // javaslang.collection.Map
    public <V2> HashMap<K, V2> mapValues(final Function<? super V, ? extends V2> function) {
        Objects.requireNonNull(function, "valueMapper is null");
        return (HashMap<K, V2>) map((BiFunction) new BiFunction() { // from class: javaslang.collection.HashMap$$ExternalSyntheticLambda9
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Tuple.of(obj, function.apply(obj2));
            }
        });
    }

    @Override // javaslang.collection.Map
    public HashMap<K, V> merge(Map<? extends K, ? extends V> map) {
        return (HashMap) Maps.merge(this, new HashMap$$ExternalSyntheticLambda0(this), map);
    }

    @Override // javaslang.collection.Map
    public <U extends V> HashMap<K, V> merge(Map<? extends K, U> map, BiFunction<? super V, ? super U, ? extends V> biFunction) {
        return (HashMap) Maps.merge(this, new HashMap$$ExternalSyntheticLambda0(this), map, biFunction);
    }

    @Override // javaslang.collection.Map
    public Tuple2<HashMap<K, V>, HashMap<K, V>> partition(Predicate<? super Tuple2<K, V>> predicate) {
        return Maps.partition(this, new HashMap$$ExternalSyntheticLambda0(this), predicate);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: peek */
    public HashMap<K, V> m6358peek(Consumer<? super Tuple2<K, V>> consumer) {
        return (HashMap) Maps.peek(this, consumer);
    }

    @Override // javaslang.collection.Map
    public HashMap<K, V> put(K k, V v) {
        return new HashMap<>(this.trie.put(k, v));
    }

    @Override // javaslang.collection.Map
    public HashMap<K, V> put(Tuple2<? extends K, ? extends V> tuple2) {
        return (HashMap) Maps.put(this, tuple2);
    }

    @Override // javaslang.collection.Map
    public HashMap<K, V> remove(K k) {
        HashArrayMappedTrie<K, V> hashArrayMappedTrieRemove = this.trie.remove(k);
        return hashArrayMappedTrieRemove.size() == this.trie.size() ? this : wrap(hashArrayMappedTrieRemove);
    }

    @Override // javaslang.collection.Map
    public HashMap<K, V> removeAll(Iterable<? extends K> iterable) {
        Objects.requireNonNull(iterable, "keys is null");
        HashArrayMappedTrie<K, V> hashArrayMappedTrieRemove = this.trie;
        java.util.Iterator<? extends K> it = iterable.iterator();
        while (it.hasNext()) {
            hashArrayMappedTrieRemove = hashArrayMappedTrieRemove.remove(it.next());
        }
        return hashArrayMappedTrieRemove.size() == this.trie.size() ? this : wrap(hashArrayMappedTrieRemove);
    }

    @Override // javaslang.collection.Map
    public HashMap<K, V> replace(Tuple2<K, V> tuple2, Tuple2<K, V> tuple3) {
        return (HashMap) Maps.replace(this, tuple2, tuple3);
    }

    @Override // javaslang.collection.Map
    public HashMap<K, V> replaceAll(Tuple2<K, V> tuple2, Tuple2<K, V> tuple3) {
        return (HashMap) Maps.replaceAll(this, tuple2, tuple3);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: retainAll */
    public HashMap<K, V> m6359retainAll(Iterable<? extends Tuple2<K, V>> iterable) {
        Objects.requireNonNull(iterable, "elements is null");
        HashArrayMappedTrie hashArrayMappedTrieEmpty = HashArrayMappedTrie.empty();
        for (Tuple2<K, V> tuple2 : iterable) {
            if (contains(tuple2)) {
                hashArrayMappedTrieEmpty = hashArrayMappedTrieEmpty.put(tuple2._1, tuple2._2);
            }
        }
        return wrap(hashArrayMappedTrieEmpty);
    }

    @Override // javaslang.collection.Map
    public HashMap<K, V> scan(Tuple2<K, V> tuple2, BiFunction<? super Tuple2<K, V>, ? super Tuple2<K, V>, ? extends Tuple2<K, V>> biFunction) {
        return (HashMap) Maps.scan(this, new HashMap$$ExternalSyntheticLambda1(), tuple2, biFunction);
    }

    @Override // javaslang.collection.Map, javaslang.collection.Traversable
    public int size() {
        return this.trie.size();
    }

    @Override // javaslang.collection.Map
    public Iterator<HashMap<K, V>> sliding(long j) {
        return Maps.sliding(this, new HashMap$$ExternalSyntheticLambda0(this), j);
    }

    @Override // javaslang.collection.Map
    public Iterator<HashMap<K, V>> sliding(long j, long j2) {
        return Maps.sliding(this, new HashMap$$ExternalSyntheticLambda0(this), j, j2);
    }

    @Override // javaslang.collection.Map
    public Tuple2<HashMap<K, V>, HashMap<K, V>> span(Predicate<? super Tuple2<K, V>> predicate) {
        return Maps.span(this, new HashMap$$ExternalSyntheticLambda0(this), predicate);
    }

    @Override // javaslang.collection.Map, javaslang.collection.Traversable
    public HashMap<K, V> tail() {
        if (this.trie.isEmpty()) {
            throw new UnsupportedOperationException("tail of empty HashMap");
        }
        return remove((Object) head()._1);
    }

    @Override // javaslang.collection.Map
    public Option<HashMap<K, V>> tailOption() {
        return Maps.tailOption(this);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: take */
    public HashMap<K, V> m6360take(long j) {
        return (HashMap) Maps.take(this, new HashMap$$ExternalSyntheticLambda0(this), j);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: takeRight */
    public HashMap<K, V> m6361takeRight(long j) {
        return (HashMap) Maps.takeRight(this, new HashMap$$ExternalSyntheticLambda0(this), j);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: takeUntil */
    public HashMap<K, V> m6362takeUntil(Predicate<? super Tuple2<K, V>> predicate) {
        return (HashMap) Maps.takeUntil(this, new HashMap$$ExternalSyntheticLambda0(this), predicate);
    }

    @Override // javaslang.collection.Map
    /* JADX INFO: renamed from: takeWhile */
    public HashMap<K, V> m6363takeWhile(Predicate<? super Tuple2<K, V>> predicate) {
        return (HashMap) Maps.takeWhile(this, new HashMap$$ExternalSyntheticLambda0(this), predicate);
    }

    @Override // javaslang.collection.Map
    public java.util.HashMap<K, V> toJavaMap() {
        return (java.util.HashMap) toJavaMap(new Supplier() { // from class: javaslang.collection.HashMap$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                return HashMap.m2887$r8$lambda$sUZQVfQ4XNh1oZg9vIcD2TcdVo();
            }
        }, new Function() { // from class: javaslang.collection.HashMap$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return HashMap.lambda$toJavaMap$5((Tuple2) obj);
            }
        });
    }

    @Override // javaslang.collection.Map
    public Seq<V> values() {
        return (Seq<V>) map(new Function() { // from class: javaslang.collection.HashMap$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Tuple2) obj)._2();
            }
        });
    }

    public int hashCode() {
        return this.trie.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof HashMap) {
            return this.trie.equals(((HashMap) obj).trie);
        }
        return false;
    }

    private Object readResolve() {
        return isEmpty() ? EMPTY : this;
    }

    @Override // javaslang.Value
    public String stringPrefix() {
        return "HashMap";
    }

    @Override // javaslang.Value
    public String toString() {
        return mkString(stringPrefix() + "(", ", ", Constant.AFTER_QUTO);
    }

    private static <K, V> HashMap<K, V> wrap(HashArrayMappedTrie<K, V> hashArrayMappedTrie) {
        return hashArrayMappedTrie.isEmpty() ? empty() : new HashMap<>(hashArrayMappedTrie);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public HashMap<K, V> createFromEntries(Iterable<Tuple2<K, V>> iterable) {
        return ofEntries(iterable);
    }
}
