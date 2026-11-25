package gnu.trove;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* JADX INFO: loaded from: classes11.dex */
public class TIntObjectHashMap<V> extends THash implements TIntHashingStrategy {
    protected final TIntHashingStrategy _hashingStrategy;
    protected transient int[] _set;
    protected transient V[] _values;

    public TIntObjectHashMap() {
        this._hashingStrategy = this;
    }

    public TIntObjectHashMap(int i) {
        super(i);
        this._hashingStrategy = this;
    }

    public TIntObjectHashMap(int i, float f) {
        super(i, f);
        this._hashingStrategy = this;
    }

    public TIntObjectHashMap(TIntHashingStrategy tIntHashingStrategy) {
        this._hashingStrategy = tIntHashingStrategy;
    }

    public TIntObjectHashMap(int i, TIntHashingStrategy tIntHashingStrategy) {
        super(i);
        this._hashingStrategy = tIntHashingStrategy;
    }

    public TIntObjectHashMap(int i, float f, TIntHashingStrategy tIntHashingStrategy) {
        super(i, f);
        this._hashingStrategy = tIntHashingStrategy;
    }

    @Override // gnu.trove.THash
    public TIntObjectHashMap<V> clone() {
        TIntObjectHashMap<V> tIntObjectHashMap = (TIntObjectHashMap) super.clone();
        tIntObjectHashMap._values = (V[]) ((Object[]) (this._values == EMPTY_OBJECT_ARRAY ? EMPTY_OBJECT_ARRAY : this._values.clone()));
        tIntObjectHashMap._set = this._values == EMPTY_OBJECT_ARRAY ? null : (int[]) this._set.clone();
        return tIntObjectHashMap;
    }

    public TIntObjectIterator<V> iterator() {
        return new TIntObjectIterator<>(this);
    }

    @Override // gnu.trove.THash
    protected int setUp(int i) {
        int up = super.setUp(i);
        this._values = (V[]) (i == -1 ? EMPTY_OBJECT_ARRAY : new Object[up]);
        this._set = i == -1 ? null : new int[up];
        return up;
    }

    public boolean contains(int i) {
        return index(i) >= 0;
    }

    @Override // gnu.trove.THash
    protected int capacity() {
        return this._values.length;
    }

    public boolean forEach(TIntProcedure tIntProcedure) {
        int[] iArr = this._set;
        V[] vArr = this._values;
        int length = vArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return true;
            }
            if (isFull(vArr, i) && !tIntProcedure.execute(iArr[i])) {
                return false;
            }
            length = i;
        }
    }

    public V put(int i, V v) {
        boolean zIsFree;
        V v2;
        int iInsertionIndex = insertionIndex(i);
        boolean z = true;
        if (iInsertionIndex < 0) {
            iInsertionIndex = (-iInsertionIndex) - 1;
            zIsFree = false;
            v2 = (V) unwrapNull(this._values[iInsertionIndex]);
            z = false;
        } else {
            zIsFree = isFree(this._values, iInsertionIndex);
            v2 = null;
        }
        this._set[iInsertionIndex] = i;
        ((V[]) this._values)[iInsertionIndex] = wrapNull(v);
        if (z) {
            postInsertHook(zIsFree);
        }
        return v2;
    }

    @Override // gnu.trove.THash
    protected void rehash(int i) {
        int iCapacity = capacity();
        int[] iArr = this._set;
        V[] vArr = this._values;
        this._set = new int[i];
        this._values = (V[]) new Object[i];
        while (true) {
            int i2 = iCapacity - 1;
            if (iCapacity <= 0) {
                return;
            }
            if (isFull(vArr, i2)) {
                int i3 = iArr[i2];
                int iInsertionIndex = insertionIndex(i3);
                this._set[iInsertionIndex] = i3;
                this._values[iInsertionIndex] = vArr[i2];
            }
            iCapacity = i2;
        }
    }

    public V get(int i) {
        int iIndex = index(i);
        if (iIndex < 0) {
            return null;
        }
        return (V) unwrapNull(this._values[iIndex]);
    }

    private static <V> V unwrapNull(V v) {
        if (v == TObjectHash.NULL) {
            return null;
        }
        return v;
    }

    private static <V> V wrapNull(V v) {
        return v == null ? (V) TObjectHash.NULL : v;
    }

    @Override // gnu.trove.THash
    public void clear() {
        super.clear();
        int[] iArr = this._set;
        V[] vArr = this._values;
        int length = vArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return;
            }
            iArr[i] = 0;
            vArr[i] = null;
            length = i;
        }
    }

    public V remove(int i) {
        int iIndex = index(i);
        if (iIndex < 0) {
            return null;
        }
        V v = (V) unwrapNull(this._values[iIndex]);
        removeAt(iIndex);
        return v;
    }

    protected int index(int i) {
        int[] iArr = this._set;
        V[] vArr = this._values;
        if (vArr == EMPTY_OBJECT_ARRAY) {
            return -1;
        }
        int length = iArr.length;
        int iComputeHashCode = this._hashingStrategy.computeHashCode(i) & Integer.MAX_VALUE;
        int i2 = iComputeHashCode % length;
        if (!isFree(vArr, i2) && (isRemoved(vArr, i2) || iArr[i2] != i)) {
            int i3 = (iComputeHashCode % (length - 2)) + 1;
            while (true) {
                i2 -= i3;
                if (i2 < 0) {
                    i2 += length;
                }
                if (isFree(vArr, i2) || (!isRemoved(vArr, i2) && iArr[i2] == i)) {
                    break;
                }
            }
        }
        if (isFree(vArr, i2)) {
            return -1;
        }
        return i2;
    }

    protected int insertionIndex(int i) {
        if (this._values == EMPTY_OBJECT_ARRAY) {
            setUp(6);
        }
        V[] vArr = this._values;
        int[] iArr = this._set;
        int length = iArr.length;
        int iComputeHashCode = this._hashingStrategy.computeHashCode(i) & Integer.MAX_VALUE;
        int i2 = iComputeHashCode % length;
        if (isFree(vArr, i2)) {
            return i2;
        }
        if (!isFull(vArr, i2) || iArr[i2] != i) {
            int i3 = (iComputeHashCode % (length - 2)) + 1;
            int i4 = isRemoved(vArr, i2) ? i2 : -1;
            do {
                i2 -= i3;
                if (i2 < 0) {
                    i2 += length;
                }
                if (i4 == -1 && isRemoved(vArr, i2)) {
                    i4 = i2;
                }
                if (!isFull(vArr, i2)) {
                    break;
                }
            } while (iArr[i2] != i);
            if (isRemoved(vArr, i2)) {
                while (!isFree(vArr, i2) && (isRemoved(vArr, i2) || iArr[i2] != i)) {
                    i2 -= i3;
                    if (i2 < 0) {
                        i2 += length;
                    }
                }
            }
            if (!isFull(vArr, i2)) {
                return i4 == -1 ? i2 : i4;
            }
        }
        return (-i2) - 1;
    }

    static boolean isFull(Object[] objArr, int i) {
        Object obj = objArr[i];
        return (obj == null || obj == TObjectHash.REMOVED) ? false : true;
    }

    private static boolean isRemoved(Object[] objArr, int i) {
        return objArr[i] == TObjectHash.REMOVED;
    }

    private static boolean isFree(Object[] objArr, int i) {
        return objArr[i] == null;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TIntObjectHashMap)) {
            return false;
        }
        TIntObjectHashMap tIntObjectHashMap = (TIntObjectHashMap) obj;
        if (tIntObjectHashMap.size() != size()) {
            return false;
        }
        return forEachEntry(new EqProcedure(tIntObjectHashMap));
    }

    public int hashCode() {
        HashProcedure hashProcedure = new HashProcedure();
        forEachEntry(hashProcedure);
        return hashProcedure.getHashCode();
    }

    private final class HashProcedure implements TIntObjectProcedure<V> {
        private int h;

        HashProcedure() {
        }

        public int getHashCode() {
            return this.h;
        }

        @Override // gnu.trove.TIntObjectProcedure
        public final boolean execute(int i, V v) {
            this.h += TIntObjectHashMap.this._hashingStrategy.computeHashCode(i) ^ HashFunctions.hash(v);
            return true;
        }
    }

    private static final class EqProcedure<V> implements TIntObjectProcedure<V> {
        private final TIntObjectHashMap<V> _otherMap;

        EqProcedure(TIntObjectHashMap<V> tIntObjectHashMap) {
            this._otherMap = tIntObjectHashMap;
        }

        @Override // gnu.trove.TIntObjectProcedure
        public final boolean execute(int i, V v) {
            return this._otherMap.index(i) >= 0 && eq(v, this._otherMap.get(i));
        }

        private static boolean eq(Object obj, Object obj2) {
            return obj == obj2 || (obj != null && obj.equals(obj2));
        }
    }

    @Override // gnu.trove.THash
    protected void removeAt(int i) {
        ((V[]) this._values)[i] = TObjectHash.REMOVED;
        super.removeAt(i);
    }

    public Object[] getValues() {
        Object[] objArr = new Object[size()];
        V[] vArr = this._values;
        int length = vArr.length;
        int i = 0;
        while (true) {
            int i2 = length - 1;
            if (length <= 0) {
                return objArr;
            }
            if (isFull(vArr, i2)) {
                objArr[i] = unwrapNull(vArr[i2]);
                i++;
            }
            length = i2;
        }
    }

    public int[] keys() {
        int[] iArr = new int[size()];
        int[] iArr2 = this._set;
        V[] vArr = this._values;
        int length = vArr.length;
        int i = 0;
        while (true) {
            int i2 = length - 1;
            if (length <= 0) {
                return iArr;
            }
            if (isFull(vArr, i2)) {
                iArr[i] = iArr2[i2];
                i++;
            }
            length = i2;
        }
    }

    public boolean containsValue(V v) {
        V[] vArr = this._values;
        if (v == null) {
            int length = vArr.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    return false;
                }
                if (TObjectHash.NULL == vArr[i]) {
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
                Object objUnwrapNull = unwrapNull(vArr[i2]);
                if (isFull(vArr, i2) && (v == objUnwrapNull || v.equals(objUnwrapNull))) {
                    break;
                }
                length2 = i2;
            }
            return true;
        }
    }

    public boolean containsKey(int i) {
        return contains(i);
    }

    public boolean forEachKey(TIntProcedure tIntProcedure) {
        return forEach(tIntProcedure);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean forEachValue(TObjectProcedure<V> tObjectProcedure) {
        V[] vArr = this._values;
        int length = vArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return true;
            }
            if (isFull(vArr, i) && !tObjectProcedure.execute(unwrapNull(vArr[i]))) {
                return false;
            }
            length = i;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean forEachEntry(TIntObjectProcedure<V> tIntObjectProcedure) {
        int[] iArr = this._set;
        V[] vArr = this._values;
        int length = vArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return true;
            }
            if (isFull(vArr, i) && !tIntObjectProcedure.execute(iArr[i], unwrapNull(vArr[i]))) {
                return false;
            }
            length = i;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean retainEntries(TIntObjectProcedure<V> tIntObjectProcedure) {
        int[] iArr = this._set;
        V[] vArr = this._values;
        stopCompactingOnRemove();
        boolean z = false;
        try {
            int length = vArr.length;
            while (true) {
                int i = length - 1;
                if (length > 0) {
                    if (isFull(vArr, i) && !tIntObjectProcedure.execute(iArr[i], unwrapNull(vArr[i]))) {
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

    /* JADX WARN: Multi-variable type inference failed */
    public void transformValues(TObjectFunction<V, V> tObjectFunction) {
        Object[] objArr = this._values;
        int length = objArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return;
            }
            if (isFull(objArr, i)) {
                objArr[i] = wrapNull(tObjectFunction.execute(unwrapNull(objArr[i])));
            }
            length = i;
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
            put(objectInputStream.readInt(), objectInputStream.readObject());
            i = i2;
        }
    }

    @Override // gnu.trove.TIntHashingStrategy
    public final int computeHashCode(int i) {
        return HashFunctions.hash(i);
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        forEachEntry(new TIntObjectProcedure<V>() { // from class: gnu.trove.TIntObjectHashMap.1
            @Override // gnu.trove.TIntObjectProcedure
            public boolean execute(int i, V v) {
                if (sb.length() != 0) {
                    sb.append(',').append(StringUtil.SPACE);
                }
                sb.append(i);
                sb.append('=');
                StringBuilder sb2 = sb;
                if (v == this) {
                    v = (V) "(this Map)";
                }
                sb2.append(v);
                return true;
            }
        });
        sb.append('}');
        sb.insert(0, '{');
        return sb.toString();
    }
}
