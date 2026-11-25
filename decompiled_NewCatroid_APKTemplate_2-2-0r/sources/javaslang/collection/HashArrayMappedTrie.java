package javaslang.collection;

import javaslang.Tuple2;
import javaslang.control.Option;

/* JADX INFO: loaded from: classes11.dex */
interface HashArrayMappedTrie<K, V> extends Iterable<Tuple2<K, V>> {
    boolean containsKey(K k);

    Option<V> get(K k);

    boolean isEmpty();

    @Override // java.lang.Iterable
    Iterator<Tuple2<K, V>> iterator();

    Iterator<K> keysIterator();

    HashArrayMappedTrie<K, V> put(K k, V v);

    HashArrayMappedTrie<K, V> remove(K k);

    int size();

    static <K, V> HashArrayMappedTrie<K, V> empty() {
        return HashArrayMappedTrieModule.EmptyNode.instance();
    }
}
