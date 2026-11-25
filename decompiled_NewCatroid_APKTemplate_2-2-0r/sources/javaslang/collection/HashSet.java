package javaslang.collection;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import javaslang.Tuple2;

/* JADX INFO: loaded from: classes11.dex */
public final class HashSet<T> implements Serializable, Set<T> {
    private static final HashSet<?> EMPTY = new HashSet<>(HashArrayMappedTrie.empty());
    private static final long serialVersionUID = 1;
    private final HashArrayMappedTrie<T, T> tree;

    public static /* synthetic */ java.util.HashSet $r8$lambda$j35xzDqY00kBEc7DMQyJC7sG_u4() {
        return new java.util.HashSet();
    }

    @Override // javaslang.collection.Traversable
    public boolean isTraversableAgain() {
        return true;
    }

    private HashSet(HashArrayMappedTrie<T, T> hashArrayMappedTrie) {
        this.tree = hashArrayMappedTrie;
    }

    public static <T> HashSet<T> empty() {
        return (HashSet<T>) EMPTY;
    }

    public static <T> HashSet<T> ofAll(Iterable<? extends T> iterable) {
        Objects.requireNonNull(iterable, "elements is null");
        if (iterable instanceof HashSet) {
            return (HashSet) iterable;
        }
        HashArrayMappedTrie hashArrayMappedTrieAddAll = addAll(HashArrayMappedTrie.empty(), iterable);
        return hashArrayMappedTrieAddAll.isEmpty() ? empty() : new HashSet<>(hashArrayMappedTrieAddAll);
    }

    @Override // javaslang.collection.Set
    public HashSet<T> add(T t) {
        return contains(t) ? this : new HashSet<>(this.tree.put(t, t));
    }

    @Override // javaslang.collection.Set
    public boolean contains(T t) {
        return this.tree.get(t).isDefined();
    }

    @Override // javaslang.collection.Traversable
    public T head() {
        if (this.tree.isEmpty()) {
            throw new NoSuchElementException("head of empty set");
        }
        return iterator().next();
    }

    @Override // javaslang.collection.Set
    public HashSet<T> intersect(Set<? extends T> set) {
        Objects.requireNonNull(set, "elements is null");
        if (isEmpty() || set.isEmpty()) {
            return empty();
        }
        return retainAll(set);
    }

    @Override // javaslang.collection.Traversable, javaslang.Value
    public boolean isEmpty() {
        return this.tree.isEmpty();
    }

    @Override // javaslang.collection.Set, javaslang.collection.Traversable, javaslang.Value, java.lang.Iterable
    public Iterator<T> iterator() {
        return this.tree.keysIterator();
    }

    @Override // javaslang.collection.Set, javaslang.collection.Traversable
    public int length() {
        return this.tree.size();
    }

    @Override // javaslang.collection.Set
    public <U> HashSet<U> map(final Function<? super T, ? extends U> function) {
        Objects.requireNonNull(function, "mapper is null");
        if (isEmpty()) {
            return empty();
        }
        return new HashSet<>((HashArrayMappedTrie) foldLeft(HashArrayMappedTrie.empty(), new BiFunction() { // from class: javaslang.collection.HashSet$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return HashSet.lambda$map$5(function, (HashArrayMappedTrie) obj, obj2);
            }
        }));
    }

    static /* synthetic */ HashArrayMappedTrie lambda$map$5(Function function, HashArrayMappedTrie hashArrayMappedTrie, Object obj) {
        Object objApply = function.apply(obj);
        return hashArrayMappedTrie.put(objApply, objApply);
    }

    @Override // javaslang.collection.Traversable
    public String mkString(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        return iterator().mkString(charSequence, charSequence2, charSequence3);
    }

    public HashSet<T> remove(T t) {
        HashArrayMappedTrie<T, T> hashArrayMappedTrieRemove = this.tree.remove(t);
        return hashArrayMappedTrieRemove == this.tree ? this : new HashSet<>(hashArrayMappedTrieRemove);
    }

    public HashSet<T> retainAll(Iterable<? extends T> iterable) {
        Objects.requireNonNull(iterable, "elements is null");
        HashArrayMappedTrie hashArrayMappedTrieAddAll = addAll(HashArrayMappedTrie.empty(), iterable);
        HashArrayMappedTrie hashArrayMappedTrieEmpty = HashArrayMappedTrie.empty();
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            T next = it.next();
            if (hashArrayMappedTrieAddAll.containsKey(next)) {
                hashArrayMappedTrieEmpty = hashArrayMappedTrieEmpty.put(next, next);
            }
        }
        if (hashArrayMappedTrieEmpty.isEmpty()) {
            return empty();
        }
        return hashArrayMappedTrieEmpty.size() == size() ? this : new HashSet<>(hashArrayMappedTrieEmpty);
    }

    @Override // javaslang.collection.Set, javaslang.collection.Traversable
    public HashSet<T> tail() {
        if (this.tree.isEmpty()) {
            throw new UnsupportedOperationException("tail of empty set");
        }
        return remove(head());
    }

    @Override // javaslang.collection.Set
    public java.util.HashSet<T> toJavaSet() {
        return (java.util.HashSet) toJavaSet(new Supplier() { // from class: javaslang.collection.HashSet$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return HashSet.$r8$lambda$j35xzDqY00kBEc7DMQyJC7sG_u4();
            }
        });
    }

    public int hashCode() {
        return this.tree.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof HashSet) {
            return this.tree.equals(((HashSet) obj).tree);
        }
        return false;
    }

    @Override // javaslang.Value
    public String stringPrefix() {
        return "HashSet";
    }

    @Override // javaslang.Value
    public String toString() {
        return mkString(stringPrefix() + "(", ", ", Constant.AFTER_QUTO);
    }

    private static <T> HashArrayMappedTrie<T, T> addAll(HashArrayMappedTrie<T, T> hashArrayMappedTrie, Iterable<? extends T> iterable) {
        for (T t : iterable) {
            hashArrayMappedTrie = hashArrayMappedTrie.put(t, t);
        }
        return hashArrayMappedTrie;
    }

    private Object writeReplace() {
        return new SerializationProxy(this.tree);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }

    private static final class SerializationProxy<T> implements Serializable {
        private static final long serialVersionUID = 1;
        private transient HashArrayMappedTrie<T, T> tree;

        SerializationProxy(HashArrayMappedTrie<T, T> hashArrayMappedTrie) {
            this.tree = hashArrayMappedTrie;
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeInt(this.tree.size());
            Iterator<Tuple2<T, T>> it = this.tree.iterator();
            while (it.hasNext()) {
                objectOutputStream.writeObject(it.next()._1);
            }
        }

        private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
            objectInputStream.defaultReadObject();
            int i = objectInputStream.readInt();
            if (i < 0) {
                throw new InvalidObjectException("No elements");
            }
            HashArrayMappedTrie hashArrayMappedTrie = (HashArrayMappedTrie<T, T>) HashArrayMappedTrie.empty();
            for (int i2 = 0; i2 < i; i2++) {
                Object object = objectInputStream.readObject();
                hashArrayMappedTrie = (HashArrayMappedTrie<T, T>) hashArrayMappedTrie.put(object, object);
            }
            this.tree = (HashArrayMappedTrie<T, T>) hashArrayMappedTrie;
        }

        private Object readResolve() {
            return this.tree.isEmpty() ? HashSet.empty() : new HashSet(this.tree);
        }
    }
}
