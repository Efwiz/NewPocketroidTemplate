package gnu.trove;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* JADX INFO: loaded from: classes11.dex */
public class TFloatObjectHashMap<V> extends THash implements TFloatHashingStrategy {
    protected final TFloatHashingStrategy _hashingStrategy;
    protected transient float[] _set;
    protected transient V[] _values;

    public TFloatObjectHashMap() {
        this._hashingStrategy = this;
    }

    public TFloatObjectHashMap(int i) {
        super(i);
        this._hashingStrategy = this;
    }

    public TFloatObjectHashMap(int i, float f) {
        super(i, f);
        this._hashingStrategy = this;
    }

    public TFloatObjectHashMap(TFloatHashingStrategy tFloatHashingStrategy) {
        this._hashingStrategy = tFloatHashingStrategy;
    }

    public TFloatObjectHashMap(int i, TFloatHashingStrategy tFloatHashingStrategy) {
        super(i);
        this._hashingStrategy = tFloatHashingStrategy;
    }

    public TFloatObjectHashMap(int i, float f, TFloatHashingStrategy tFloatHashingStrategy) {
        super(i, f);
        this._hashingStrategy = tFloatHashingStrategy;
    }

    @Override // gnu.trove.THash
    public TFloatObjectHashMap<V> clone() {
        TFloatObjectHashMap<V> tFloatObjectHashMap = (TFloatObjectHashMap) super.clone();
        tFloatObjectHashMap._values = (V[]) ((Object[]) (this._values == EMPTY_OBJECT_ARRAY ? EMPTY_OBJECT_ARRAY : this._values.clone()));
        tFloatObjectHashMap._set = this._values == EMPTY_OBJECT_ARRAY ? null : (float[]) this._set.clone();
        return tFloatObjectHashMap;
    }

    public TFloatObjectIterator<V> iterator() {
        return new TFloatObjectIterator<>(this);
    }

    @Override // gnu.trove.THash
    protected int setUp(int i) {
        int up = super.setUp(i);
        this._values = (V[]) (i == -1 ? EMPTY_OBJECT_ARRAY : new Object[up]);
        this._set = i == -1 ? null : new float[up];
        return up;
    }

    public boolean contains(float f) {
        return index(f) >= 0;
    }

    @Override // gnu.trove.THash
    protected int capacity() {
        return this._values.length;
    }

    public boolean forEach(TFloatProcedure tFloatProcedure) {
        float[] fArr = this._set;
        V[] vArr = this._values;
        int length = vArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return true;
            }
            if (isFull(vArr, i) && !tFloatProcedure.execute(fArr[i])) {
                return false;
            }
            length = i;
        }
    }

    public V put(float f, V v) {
        boolean zIsFree;
        V v2;
        int iInsertionIndex = insertionIndex(f);
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
        this._set[iInsertionIndex] = f;
        ((V[]) this._values)[iInsertionIndex] = wrapNull(v);
        if (z) {
            postInsertHook(zIsFree);
        }
        return v2;
    }

    @Override // gnu.trove.THash
    protected void rehash(int i) {
        int iCapacity = capacity();
        float[] fArr = this._set;
        V[] vArr = this._values;
        this._set = new float[i];
        this._values = (V[]) new Object[i];
        while (true) {
            int i2 = iCapacity - 1;
            if (iCapacity <= 0) {
                return;
            }
            if (isFull(vArr, i2)) {
                float f = fArr[i2];
                int iInsertionIndex = insertionIndex(f);
                this._set[iInsertionIndex] = f;
                this._values[iInsertionIndex] = vArr[i2];
            }
            iCapacity = i2;
        }
    }

    public V get(float f) {
        int iIndex = index(f);
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
        float[] fArr = this._set;
        V[] vArr = this._values;
        int length = vArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return;
            }
            fArr[i] = 0.0f;
            vArr[i] = null;
            length = i;
        }
    }

    public V remove(float f) {
        int iIndex = index(f);
        if (iIndex < 0) {
            return null;
        }
        V v = (V) unwrapNull(this._values[iIndex]);
        removeAt(iIndex);
        return v;
    }

    protected int index(float f) {
        float[] fArr = this._set;
        V[] vArr = this._values;
        if (vArr == EMPTY_OBJECT_ARRAY) {
            return -1;
        }
        int length = fArr.length;
        int iComputeHashCode = this._hashingStrategy.computeHashCode(f) & Integer.MAX_VALUE;
        int i = iComputeHashCode % length;
        if (!isFree(vArr, i) && (isRemoved(vArr, i) || fArr[i] != f)) {
            int i2 = (iComputeHashCode % (length - 2)) + 1;
            while (true) {
                i -= i2;
                if (i < 0) {
                    i += length;
                }
                if (isFree(vArr, i) || (!isRemoved(vArr, i) && fArr[i] == f)) {
                    break;
                }
            }
        }
        if (isFree(vArr, i)) {
            return -1;
        }
        return i;
    }

    protected int insertionIndex(float f) {
        if (this._values == EMPTY_OBJECT_ARRAY) {
            setUp(6);
        }
        V[] vArr = this._values;
        float[] fArr = this._set;
        int length = fArr.length;
        int iComputeHashCode = this._hashingStrategy.computeHashCode(f) & Integer.MAX_VALUE;
        int i = iComputeHashCode % length;
        if (isFree(vArr, i)) {
            return i;
        }
        if (!isFull(vArr, i) || fArr[i] != f) {
            int i2 = (iComputeHashCode % (length - 2)) + 1;
            int i3 = isRemoved(vArr, i) ? i : -1;
            do {
                i -= i2;
                if (i < 0) {
                    i += length;
                }
                if (i3 == -1 && isRemoved(vArr, i)) {
                    i3 = i;
                }
                if (!isFull(vArr, i)) {
                    break;
                }
            } while (fArr[i] != f);
            if (isRemoved(vArr, i)) {
                while (!isFree(vArr, i) && (isRemoved(vArr, i) || fArr[i] != f)) {
                    i -= i2;
                    if (i < 0) {
                        i += length;
                    }
                }
            }
            if (!isFull(vArr, i)) {
                return i3 == -1 ? i : i3;
            }
        }
        return (-i) - 1;
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
        if (!(obj instanceof TFloatObjectHashMap)) {
            return false;
        }
        TFloatObjectHashMap tFloatObjectHashMap = (TFloatObjectHashMap) obj;
        if (tFloatObjectHashMap.size() != size()) {
            return false;
        }
        return forEachEntry(new EqProcedure(tFloatObjectHashMap));
    }

    public int hashCode() {
        HashProcedure hashProcedure = new HashProcedure();
        forEachEntry(hashProcedure);
        return hashProcedure.getHashCode();
    }

    private final class HashProcedure implements TFloatObjectProcedure<V> {
        private int h;

        HashProcedure() {
        }

        public int getHashCode() {
            return this.h;
        }

        @Override // gnu.trove.TFloatObjectProcedure
        public final boolean execute(float f, V v) {
            this.h += TFloatObjectHashMap.this._hashingStrategy.computeHashCode(f) ^ HashFunctions.hash(v);
            return true;
        }
    }

    private static final class EqProcedure<V> implements TFloatObjectProcedure<V> {
        private final TFloatObjectHashMap<V> _otherMap;

        EqProcedure(TFloatObjectHashMap<V> tFloatObjectHashMap) {
            this._otherMap = tFloatObjectHashMap;
        }

        @Override // gnu.trove.TFloatObjectProcedure
        public final boolean execute(float f, V v) {
            return this._otherMap.index(f) >= 0 && eq(v, this._otherMap.get(f));
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

    public float[] keys() {
        float[] fArr = new float[size()];
        float[] fArr2 = this._set;
        V[] vArr = this._values;
        int length = vArr.length;
        int i = 0;
        while (true) {
            int i2 = length - 1;
            if (length <= 0) {
                return fArr;
            }
            if (isFull(vArr, i2)) {
                fArr[i] = fArr2[i2];
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

    public boolean containsKey(float f) {
        return contains(f);
    }

    public boolean forEachKey(TFloatProcedure tFloatProcedure) {
        return forEach(tFloatProcedure);
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
    public boolean forEachEntry(TFloatObjectProcedure<V> tFloatObjectProcedure) {
        float[] fArr = this._set;
        V[] vArr = this._values;
        int length = vArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return true;
            }
            if (isFull(vArr, i) && !tFloatObjectProcedure.execute(fArr[i], unwrapNull(vArr[i]))) {
                return false;
            }
            length = i;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean retainEntries(TFloatObjectProcedure<V> tFloatObjectProcedure) {
        float[] fArr = this._set;
        V[] vArr = this._values;
        stopCompactingOnRemove();
        boolean z = false;
        try {
            int length = vArr.length;
            while (true) {
                int i = length - 1;
                if (length > 0) {
                    if (isFull(vArr, i) && !tFloatObjectProcedure.execute(fArr[i], unwrapNull(vArr[i]))) {
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
            put(objectInputStream.readFloat(), objectInputStream.readObject());
            i = i2;
        }
    }

    @Override // gnu.trove.TFloatHashingStrategy
    public final int computeHashCode(float f) {
        return HashFunctions.hash(f);
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        forEachEntry(new TFloatObjectProcedure<V>() { // from class: gnu.trove.TFloatObjectHashMap.1
            @Override // gnu.trove.TFloatObjectProcedure
            public boolean execute(float f, V v) {
                if (sb.length() != 0) {
                    sb.append(',').append(StringUtil.SPACE);
                }
                sb.append(f);
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
