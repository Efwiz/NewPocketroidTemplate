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
public final class LinkedHashSet<T> implements Serializable, Set<T> {
    private static final LinkedHashSet<?> EMPTY = new LinkedHashSet<>(LinkedHashMap.empty());
    private static final long serialVersionUID = 1;
    private final LinkedHashMap<T, Object> map;

    public static /* synthetic */ java.util.LinkedHashSet $r8$lambda$8t7les3oNGAWx5tdDX7k2wROC5E() {
        return new java.util.LinkedHashSet();
    }

    @Override // javaslang.collection.Traversable
    public boolean isTraversableAgain() {
        return true;
    }

    private LinkedHashSet(LinkedHashMap<T, Object> linkedHashMap) {
        this.map = linkedHashMap;
    }

    public static <T> LinkedHashSet<T> empty() {
        return (LinkedHashSet<T>) EMPTY;
    }

    static <T> LinkedHashSet<T> wrap(LinkedHashMap<T, Object> linkedHashMap) {
        return new LinkedHashSet<>(linkedHashMap);
    }

    @Override // javaslang.collection.Set
    public LinkedHashSet<T> add(T t) {
        return contains(t) ? this : new LinkedHashSet<>(this.map.put(t, t));
    }

    @Override // javaslang.collection.Set
    public boolean contains(T t) {
        return this.map.get(t).isDefined();
    }

    @Override // javaslang.collection.Traversable
    public T head() {
        if (this.map.isEmpty()) {
            throw new NoSuchElementException("head of empty set");
        }
        return iterator().next();
    }

    @Override // javaslang.collection.Set
    public LinkedHashSet<T> intersect(Set<? extends T> set) {
        Objects.requireNonNull(set, "elements is null");
        if (isEmpty() || set.isEmpty()) {
            return empty();
        }
        return retainAll(set);
    }

    @Override // javaslang.collection.Traversable, javaslang.Value
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override // javaslang.collection.Set, javaslang.collection.Traversable, javaslang.Value, java.lang.Iterable
    public Iterator<T> iterator() {
        return (Iterator<T>) this.map.iterator().map(new Function() { // from class: javaslang.collection.LinkedHashSet$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Tuple2) obj)._1;
            }
        });
    }

    @Override // javaslang.collection.Set, javaslang.collection.Traversable
    public int length() {
        return this.map.size();
    }

    @Override // javaslang.collection.Set
    public <U> LinkedHashSet<U> map(final Function<? super T, ? extends U> function) {
        Objects.requireNonNull(function, "mapper is null");
        if (isEmpty()) {
            return empty();
        }
        return new LinkedHashSet<>((LinkedHashMap) foldLeft(LinkedHashMap.empty(), new BiFunction() { // from class: javaslang.collection.LinkedHashSet$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return LinkedHashSet.lambda$map$5(function, (LinkedHashMap) obj, obj2);
            }
        }));
    }

    static /* synthetic */ LinkedHashMap lambda$map$5(Function function, LinkedHashMap linkedHashMap, Object obj) {
        Object objApply = function.apply(obj);
        return linkedHashMap.put(objApply, objApply);
    }

    @Override // javaslang.collection.Traversable
    public String mkString(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        return iterator().mkString(charSequence, charSequence2, charSequence3);
    }

    public LinkedHashSet<T> remove(T t) {
        LinkedHashMap<T, Object> linkedHashMapRemove = this.map.remove(t);
        return linkedHashMapRemove == this.map ? this : new LinkedHashSet<>(linkedHashMapRemove);
    }

    public LinkedHashSet<T> retainAll(Iterable<? extends T> iterable) {
        Objects.requireNonNull(iterable, "elements is null");
        LinkedHashMap linkedHashMapAddAll = addAll(LinkedHashMap.empty(), iterable);
        LinkedHashMap linkedHashMapEmpty = LinkedHashMap.empty();
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            T next = it.next();
            if (linkedHashMapAddAll.containsKey(next)) {
                linkedHashMapEmpty = linkedHashMapEmpty.put(next, next);
            }
        }
        if (linkedHashMapEmpty.isEmpty()) {
            return empty();
        }
        return linkedHashMapEmpty.size() == size() ? this : new LinkedHashSet<>(linkedHashMapEmpty);
    }

    @Override // javaslang.collection.Set, javaslang.collection.Traversable
    public LinkedHashSet<T> tail() {
        if (this.map.isEmpty()) {
            throw new UnsupportedOperationException("tail of empty set");
        }
        return remove(head());
    }

    @Override // javaslang.collection.Set
    public java.util.LinkedHashSet<T> toJavaSet() {
        return (java.util.LinkedHashSet) toJavaSet(new Supplier() { // from class: javaslang.collection.LinkedHashSet$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return LinkedHashSet.$r8$lambda$8t7les3oNGAWx5tdDX7k2wROC5E();
            }
        });
    }

    public int hashCode() {
        return this.map.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof LinkedHashSet) {
            return this.map.equals(((LinkedHashSet) obj).map);
        }
        return false;
    }

    @Override // javaslang.Value
    public String stringPrefix() {
        return "LinkedHashSet";
    }

    @Override // javaslang.Value
    public String toString() {
        return mkString(stringPrefix() + "(", ", ", Constant.AFTER_QUTO);
    }

    private static <T> LinkedHashMap<T, Object> addAll(LinkedHashMap<T, Object> linkedHashMap, Iterable<? extends T> iterable) {
        for (T t : iterable) {
            linkedHashMap = linkedHashMap.put(t, t);
        }
        return linkedHashMap;
    }

    private Object writeReplace() {
        return new SerializationProxy(this.map);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }

    private static final class SerializationProxy<T> implements Serializable {
        private static final long serialVersionUID = 1;
        private transient LinkedHashMap<T, Object> map;

        SerializationProxy(LinkedHashMap<T, Object> linkedHashMap) {
            this.map = linkedHashMap;
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeInt(this.map.size());
            Iterator<Tuple2<T, Object>> it = this.map.iterator();
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
            Map map = (LinkedHashMap<T, Object>) LinkedHashMap.empty();
            for (int i2 = 0; i2 < i; i2++) {
                Object object = objectInputStream.readObject();
                map = (LinkedHashMap<T, Object>) ((LinkedHashMap) map).put(object, object);
            }
            this.map = (LinkedHashMap<T, Object>) map;
        }

        private Object readResolve() {
            return this.map.isEmpty() ? LinkedHashSet.empty() : new LinkedHashSet(this.map);
        }
    }
}
