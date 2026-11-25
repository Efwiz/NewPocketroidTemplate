package gnu.trove;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes11.dex */
public class THashSet<E> extends TObjectHash<E> implements Set<E> {
    public THashSet() {
    }

    public THashSet(TObjectHashingStrategy<E> tObjectHashingStrategy) {
        super(tObjectHashingStrategy);
    }

    public THashSet(int i) {
        super(i);
    }

    public THashSet(int i, TObjectHashingStrategy<E> tObjectHashingStrategy) {
        super(i, tObjectHashingStrategy);
    }

    public THashSet(int i, float f) {
        super(i, f);
    }

    public THashSet(int i, float f, TObjectHashingStrategy<E> tObjectHashingStrategy) {
        super(i, f, tObjectHashingStrategy);
    }

    public THashSet(Collection<? extends E> collection) {
        this(collection.size());
        addAll(collection);
    }

    public THashSet(Collection<? extends E> collection, TObjectHashingStrategy<E> tObjectHashingStrategy) {
        this(collection.size(), tObjectHashingStrategy);
        addAll(collection);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean add(E e) {
        if (e == null) {
            throw new NullPointerException("Null elements are not allowed in THashSet");
        }
        int iInsertionIndex = insertionIndex(e);
        if (iInsertionIndex < 0) {
            return false;
        }
        Object obj = this._set[iInsertionIndex];
        this._set[iInsertionIndex] = e;
        postInsertHook(obj == null);
        return true;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean equals(Object obj) {
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set = (Set) obj;
        if (set.size() != size()) {
            return false;
        }
        return containsAll(set);
    }

    @Override // java.util.Set, java.util.Collection
    public int hashCode() {
        HashProcedure hashProcedure = new HashProcedure();
        forEach(hashProcedure);
        return hashProcedure.getHashCode();
    }

    private final class HashProcedure implements TObjectProcedure<E> {
        private int h;

        HashProcedure() {
        }

        public int getHashCode() {
            return this.h;
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // gnu.trove.TObjectProcedure
        public final boolean execute(E e) {
            this.h += THashSet.this._hashingStrategy.computeHashCode((T) e);
            return true;
        }
    }

    @Override // gnu.trove.THash
    protected void rehash(int i) {
        int iCapacity = capacity();
        Object[] objArr = this._set;
        this._set = new Object[i];
        while (true) {
            int i2 = iCapacity - 1;
            if (iCapacity <= 0) {
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
            }
            iCapacity = i2;
        }
    }

    @Override // java.util.Set, java.util.Collection
    public Object[] toArray() {
        Object[] objArr = new Object[size()];
        forEach(new ToObjectArrayProcedure(objArr));
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

    @Override // gnu.trove.THash
    public void clear() {
        super.clear();
        Object[] objArr = this._set;
        int length = objArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return;
            }
            objArr[i] = null;
            length = i;
        }
    }

    @Override // java.util.Set, java.util.Collection
    public boolean remove(Object obj) {
        int iIndex = index(obj);
        if (iIndex < 0) {
            return false;
        }
        removeAt(iIndex);
        return true;
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        return new TObjectHashIterator(this);
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
    public boolean addAll(Collection<? extends E> collection) {
        int size = collection.size();
        ensureCapacity(size);
        Iterator<? extends E> it = collection.iterator();
        boolean z = false;
        while (true) {
            int i = size - 1;
            if (size <= 0) {
                return z;
            }
            if (add(it.next())) {
                z = true;
            }
            size = i;
        }
    }

    @Override // java.util.Set, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        int size = collection.size();
        Iterator<?> it = collection.iterator();
        boolean z = false;
        while (true) {
            int i = size - 1;
            if (size <= 0) {
                return z;
            }
            if (remove(it.next())) {
                z = true;
            }
            size = i;
        }
    }

    @Override // java.util.Set, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        int size = size();
        Iterator<E> it = iterator();
        boolean z = false;
        while (true) {
            int i = size - 1;
            if (size <= 0) {
                return z;
            }
            if (!collection.contains(it.next())) {
                it.remove();
                z = true;
            }
            size = i;
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this._size);
        SerializationProcedure serializationProcedure = new SerializationProcedure(objectOutputStream);
        if (!forEach(serializationProcedure)) {
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
            add(objectInputStream.readObject());
            i = i2;
        }
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        forEach(new TObjectProcedure<E>() { // from class: gnu.trove.THashSet.1
            @Override // gnu.trove.TObjectProcedure
            public boolean execute(E e) {
                if (sb.length() != 0) {
                    sb.append(',').append(StringUtil.SPACE);
                }
                StringBuilder sb2 = sb;
                if (e == this) {
                    e = (E) "(this set)";
                }
                sb2.append(e);
                return true;
            }
        });
        sb.append(']');
        sb.insert(0, '[');
        return sb.toString();
    }
}
