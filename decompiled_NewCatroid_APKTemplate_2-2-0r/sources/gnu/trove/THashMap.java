package gnu.trove;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes11.dex */
public class THashMap<K, V> extends TObjectHash<K> implements Map<K, V> {
    protected transient V[] _values;

    public THashMap() {
    }

    public THashMap(TObjectHashingStrategy<K> tObjectHashingStrategy) {
        super(tObjectHashingStrategy);
    }

    public THashMap(int i) {
        super(i);
    }

    public THashMap(int i, TObjectHashingStrategy<K> tObjectHashingStrategy) {
        super(i, tObjectHashingStrategy);
    }

    public THashMap(int i, float f) {
        super(i, f);
    }

    public THashMap(int i, float f, TObjectHashingStrategy<K> tObjectHashingStrategy) {
        super(i, f, tObjectHashingStrategy);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public THashMap(Map<K, V> map) {
        this(map.size());
        putAll(map);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public THashMap(Map<K, V> map, TObjectHashingStrategy<K> tObjectHashingStrategy) {
        this(map.size(), tObjectHashingStrategy);
        putAll(map);
    }

    @Override // gnu.trove.TObjectHash, gnu.trove.THash
    public THashMap<K, V> clone() {
        THashMap<K, V> tHashMap = (THashMap) super.clone();
        tHashMap._values = (V[]) ((Object[]) this._values.clone());
        return tHashMap;
    }

    @Override // gnu.trove.TObjectHash, gnu.trove.THash
    protected int setUp(int i) {
        int up = super.setUp(i);
        this._values = (V[]) (i == -1 ? EMPTY_OBJECT_ARRAY : new Object[up]);
        return up;
    }

    @Override // java.util.Map
    public V put(K k, V v) {
        V v2;
        if (k == null) {
            throw new NullPointerException("null keys not supported");
        }
        int iInsertionIndex = insertionIndex(k);
        boolean z = iInsertionIndex < 0;
        if (z) {
            iInsertionIndex = (-iInsertionIndex) - 1;
            v2 = this._values[iInsertionIndex];
        } else {
            v2 = null;
        }
        Object obj = this._set[iInsertionIndex];
        this._set[iInsertionIndex] = k;
        this._values[iInsertionIndex] = v;
        if (!z) {
            postInsertHook(obj == null);
        }
        return v2;
    }

    @Override // java.util.Map
    public boolean equals(Object obj) {
        if (!(obj instanceof Map)) {
            return false;
        }
        Map map = (Map) obj;
        if (map.size() != size()) {
            return false;
        }
        return forEachEntry(new EqProcedure(map));
    }

    @Override // java.util.Map
    public int hashCode() {
        HashProcedure hashProcedure = new HashProcedure();
        forEachEntry(hashProcedure);
        return hashProcedure.getHashCode();
    }

    private final class HashProcedure implements TObjectObjectProcedure<K, V> {
        private int h;

        HashProcedure() {
        }

        public int getHashCode() {
            return this.h;
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // gnu.trove.TObjectObjectProcedure
        public final boolean execute(K k, V v) {
            this.h += THashMap.this._hashingStrategy.computeHashCode((T) k) ^ (v == null ? 0 : v.hashCode());
            return true;
        }
    }

    private static final class EqProcedure<K, V> implements TObjectObjectProcedure<K, V> {
        private final Map<K, V> _otherMap;

        EqProcedure(Map<K, V> map) {
            this._otherMap = map;
        }

        @Override // gnu.trove.TObjectObjectProcedure
        public final boolean execute(K k, V v) {
            V v2 = this._otherMap.get(k);
            return v2 == v || (v2 != null && v2.equals(v));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean forEachKey(TObjectProcedure<K> tObjectProcedure) {
        return forEach(tObjectProcedure);
    }

    public boolean forEachValue(TObjectProcedure<V> tObjectProcedure) {
        V[] vArr = this._values;
        Object[] objArr = this._set;
        int length = vArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return true;
            }
            Object obj = objArr[i];
            if (obj != null && obj != REMOVED && !tObjectProcedure.execute(vArr[i])) {
                return false;
            }
            length = i;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean forEachEntry(TObjectObjectProcedure<K, V> tObjectObjectProcedure) {
        Object[] objArr = this._set;
        V[] vArr = this._values;
        int length = objArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return true;
            }
            Object obj = objArr[i];
            if (obj != null && obj != REMOVED && !tObjectObjectProcedure.execute(objArr[i], vArr[i])) {
                return false;
            }
            length = i;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean retainEntries(TObjectObjectProcedure<K, V> tObjectObjectProcedure) {
        Object[] objArr = this._set;
        V[] vArr = this._values;
        stopCompactingOnRemove();
        boolean z = false;
        try {
            int length = objArr.length;
            while (true) {
                int i = length - 1;
                if (length > 0) {
                    Object obj = objArr[i];
                    if (obj != null && obj != REMOVED && !tObjectObjectProcedure.execute(objArr[i], vArr[i])) {
                        removeAt(i);
                        z = true;
                    }
                    length = i;
                } else {
                    startCompactingOnRemove(z);
                    return z;
                }
            }
        } catch (Throwable th) {
            startCompactingOnRemove(z);
            throw th;
        }
    }

    public void transformValues(TObjectFunction<V, V> tObjectFunction) {
        V[] vArr = this._values;
        Object[] objArr = this._set;
        int length = vArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return;
            }
            Object obj = objArr[i];
            if (obj != null && obj != REMOVED) {
                vArr[i] = tObjectFunction.execute(vArr[i]);
            }
            length = i;
        }
    }

    @Override // gnu.trove.THash
    protected void rehash(int i) {
        int length = this._set.length;
        Object[] objArr = this._set;
        V[] vArr = this._values;
        this._set = new Object[i];
        this._values = (V[]) new Object[i];
        while (true) {
            int i2 = length - 1;
            if (length <= 0) {
                return;
            }
            Object obj = objArr[i2];
            if (obj != null && obj != REMOVED) {
                Object obj2 = objArr[i2];
                int iInsertionIndex = insertionIndex(obj2);
                if (iInsertionIndex < 0) {
                    throwObjectContractViolation(this._set[(-iInsertionIndex) - 1], obj2);
                }
                this._set[iInsertionIndex] = obj2;
                this._values[iInsertionIndex] = vArr[i2];
            }
            length = i2;
        }
    }

    @Override // java.util.Map
    public V get(Object obj) {
        int iIndex = index(obj);
        if (iIndex < 0) {
            return null;
        }
        return this._values[iIndex];
    }

    @Override // gnu.trove.THash
    public void clear() {
        if (size() == 0) {
            return;
        }
        super.clear();
        Object[] objArr = this._set;
        V[] vArr = this._values;
        int length = objArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return;
            }
            objArr[i] = null;
            vArr[i] = null;
            length = i;
        }
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        int iIndex = index(obj);
        if (iIndex < 0) {
            return null;
        }
        V v = this._values[iIndex];
        removeAt(iIndex);
        return v;
    }

    @Override // gnu.trove.TObjectHash, gnu.trove.THash
    protected void removeAt(int i) {
        this._values[i] = null;
        super.removeAt(i);
    }

    @Override // java.util.Map
    public Collection<V> values() {
        return new ValueView();
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        return new KeyView();
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        return new EntryView();
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        V v;
        Object[] objArr = this._set;
        V[] vArr = this._values;
        if (obj == null) {
            int length = vArr.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    return false;
                }
                Object obj2 = objArr[i];
                if (obj2 != null && obj2 != REMOVED && obj == vArr[i]) {
                    return true;
                }
                length = i;
            }
        } else {
            int length2 = vArr.length;
            while (true) {
                int i2 = length2 - 1;
                if (length2 <= 0) {
                    return false;
                }
                Object obj3 = objArr[i2];
                if (obj3 != null && obj3 != REMOVED && (obj == (v = vArr[i2]) || obj.equals(v))) {
                    return true;
                }
                length2 = i2;
            }
        }
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        return contains(obj);
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        ensureCapacity(map.size());
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    protected class ValueView extends THashMap<K, V>.MapBackedView<V> {
        protected ValueView() {
            super();
        }

        @Override // gnu.trove.THashMap.MapBackedView, java.util.Set, java.util.Collection, java.lang.Iterable
        public Iterator<V> iterator() {
            return new THashIterator<V>(THashMap.this) { // from class: gnu.trove.THashMap.ValueView.1
                @Override // gnu.trove.THashIterator
                protected V objectAtIndex(int i) {
                    return THashMap.this._values[i];
                }
            };
        }

        @Override // gnu.trove.THashMap.MapBackedView
        public boolean containsElement(V v) {
            return THashMap.this.containsValue(v);
        }

        @Override // gnu.trove.THashMap.MapBackedView
        public boolean removeElement(V v) {
            V v2;
            V[] vArr = THashMap.this._values;
            Object[] objArr = THashMap.this._set;
            int length = vArr.length;
            boolean z = false;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    return z;
                }
                Object obj = objArr[i];
                if ((obj != null && obj != TObjectHash.REMOVED && v == vArr[i]) || ((v2 = vArr[i]) != null && v2.equals(v))) {
                    THashMap.this.removeAt(i);
                    z = true;
                }
                length = i;
            }
        }
    }

    protected class EntryView extends THashMap<K, V>.MapBackedView<Map.Entry<K, V>> {
        EntryView() {
            super();
        }

        private final class EntryIterator extends THashIterator<Map.Entry<K, V>> {
            EntryIterator(THashMap<K, V> tHashMap) {
                super(tHashMap);
            }

            @Override // gnu.trove.THashIterator
            public THashMap<K, V>.Entry objectAtIndex(int i) {
                return new Entry(THashMap.this._set[i], THashMap.this._values[i], i);
            }
        }

        @Override // gnu.trove.THashMap.MapBackedView, java.util.Set, java.util.Collection, java.lang.Iterable
        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator(THashMap.this);
        }

        @Override // gnu.trove.THashMap.MapBackedView
        public boolean removeElement(Map.Entry<K, V> entry) {
            int iIndex = THashMap.this.index(keyForEntry(entry));
            if (iIndex < 0) {
                return false;
            }
            Object objValueForEntry = valueForEntry(entry);
            if (objValueForEntry != THashMap.this._values[iIndex] && (objValueForEntry == null || !objValueForEntry.equals(THashMap.this._values[iIndex]))) {
                return false;
            }
            THashMap.this.removeAt(iIndex);
            return true;
        }

        @Override // gnu.trove.THashMap.MapBackedView
        public boolean containsElement(Map.Entry<K, V> entry) {
            Object obj = THashMap.this.get(keyForEntry(entry));
            V value = entry.getValue();
            return value == obj || (obj != null && obj.equals(value));
        }

        protected V valueForEntry(Map.Entry<K, V> entry) {
            return entry.getValue();
        }

        protected K keyForEntry(Map.Entry<K, V> entry) {
            return entry.getKey();
        }
    }

    private abstract class MapBackedView<E> implements Set<E> {
        public abstract boolean containsElement(E e);

        @Override // java.util.Set, java.util.Collection, java.lang.Iterable
        public abstract Iterator<E> iterator();

        public abstract boolean removeElement(E e);

        MapBackedView() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Set, java.util.Collection
        public boolean contains(Object obj) {
            return containsElement(obj);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Set, java.util.Collection
        public boolean remove(Object obj) {
            return removeElement(obj);
        }

        @Override // java.util.Set, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            Iterator<?> it = collection.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            Iterator<?> it = collection.iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (remove(it.next())) {
                    z = true;
                }
            }
            return z;
        }

        @Override // java.util.Set, java.util.Collection
        public void clear() {
            THashMap.this.clear();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public int size() {
            return THashMap.this.size();
        }

        @Override // java.util.Set, java.util.Collection
        public Object[] toArray() {
            Object[] objArr = new Object[size()];
            Iterator<E> it = iterator();
            int i = 0;
            while (it.hasNext()) {
                objArr[i] = it.next();
                i++;
            }
            return objArr;
        }

        @Override // java.util.Set, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            int size = size();
            if (tArr.length < size) {
                tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), size));
            }
            Iterator<E> it = iterator();
            for (int i = 0; i < size; i++) {
                tArr[i] = it.next();
            }
            if (tArr.length > size) {
                tArr[size] = null;
            }
            return tArr;
        }

        @Override // java.util.Set, java.util.Collection
        public boolean isEmpty() {
            return THashMap.this.isEmpty();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean addAll(Collection<? extends E> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Set, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            Iterator<E> it = iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (!collection.contains(it.next())) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }
    }

    protected class KeyView extends THashMap<K, V>.MapBackedView<K> {
        KeyView() {
            super();
        }

        @Override // gnu.trove.THashMap.MapBackedView, java.util.Set, java.util.Collection, java.lang.Iterable
        public Iterator<K> iterator() {
            return new TObjectHashIterator(THashMap.this);
        }

        @Override // gnu.trove.THashMap.MapBackedView
        public boolean removeElement(K k) {
            return THashMap.this.remove(k) != null;
        }

        @Override // gnu.trove.THashMap.MapBackedView
        public boolean containsElement(K k) {
            return THashMap.this.contains(k);
        }
    }

    final class Entry implements Map.Entry<K, V> {
        private final int index;
        private final K key;
        private V val;

        Entry(K k, V v, int i) {
            this.key = k;
            this.val = v;
            this.index = i;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.val;
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            if (THashMap.this._values[this.index] != this.val) {
                throw new ConcurrentModificationException();
            }
            THashMap.this._values[this.index] = v;
            V v2 = this.val;
            this.val = v;
            return v2;
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this._size);
        SerializationProcedure serializationProcedure = new SerializationProcedure(objectOutputStream);
        if (!forEachEntry(serializationProcedure)) {
            throw serializationProcedure.exception;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        int i = objectInputStream.readInt();
        setUp(i);
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return;
            }
            put(objectInputStream.readObject(), objectInputStream.readObject());
            i = i2;
        }
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        forEachEntry(new TObjectObjectProcedure<K, V>() { // from class: gnu.trove.THashMap.1
            @Override // gnu.trove.TObjectObjectProcedure
            public boolean execute(K k, V v) {
                if (sb.length() != 0) {
                    sb.append(',').append(StringUtil.SPACE);
                }
                StringBuilder sb2 = sb;
                if (k == this) {
                    k = (K) "(this Map)";
                }
                sb2.append(k);
                sb.append('=');
                StringBuilder sb3 = sb;
                if (v == this) {
                    v = (V) "(this Map)";
                }
                sb3.append(v);
                return true;
            }
        });
        sb.append('}');
        sb.insert(0, '{');
        return sb.toString();
    }
}
