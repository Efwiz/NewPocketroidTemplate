package gnu.trove;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* JADX INFO: loaded from: classes11.dex */
public class TObjectFloatHashMap<K> extends TObjectHash<K> {
    protected transient float[] _values;

    public TObjectFloatHashMap() {
    }

    public TObjectFloatHashMap(int i) {
        super(i);
    }

    public TObjectFloatHashMap(int i, float f) {
        super(i, f);
    }

    public TObjectFloatHashMap(TObjectHashingStrategy<K> tObjectHashingStrategy) {
        super(tObjectHashingStrategy);
    }

    public TObjectFloatHashMap(int i, TObjectHashingStrategy<K> tObjectHashingStrategy) {
        super(i, tObjectHashingStrategy);
    }

    public TObjectFloatHashMap(int i, float f, TObjectHashingStrategy<K> tObjectHashingStrategy) {
        super(i, f, tObjectHashingStrategy);
    }

    public TObjectFloatIterator<K> iterator() {
        return new TObjectFloatIterator<>(this);
    }

    @Override // gnu.trove.TObjectHash, gnu.trove.THash
    protected int setUp(int i) {
        int up = super.setUp(i);
        this._values = i == -1 ? null : new float[up];
        return up;
    }

    public float put(K k, float f) {
        float f2;
        boolean z;
        int iInsertionIndex = insertionIndex(k);
        if (iInsertionIndex < 0) {
            iInsertionIndex = (-iInsertionIndex) - 1;
            f2 = this._values[iInsertionIndex];
            z = false;
        } else {
            f2 = 0.0f;
            z = true;
        }
        Object obj = this._set[iInsertionIndex];
        this._set[iInsertionIndex] = k;
        this._values[iInsertionIndex] = f;
        if (z) {
            postInsertHook(obj == null);
        }
        return f2;
    }

    @Override // gnu.trove.THash
    protected void rehash(int i) {
        int iCapacity = capacity();
        Object[] objArr = this._set;
        float[] fArr = this._values;
        this._set = new Object[i];
        this._values = new float[i];
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
                this._values[iInsertionIndex] = fArr[i2];
            }
            iCapacity = i2;
        }
    }

    public float get(K k) {
        int iIndex = index(k);
        if (iIndex < 0) {
            return 0.0f;
        }
        return this._values[iIndex];
    }

    @Override // gnu.trove.THash
    public void clear() {
        super.clear();
        Object[] objArr = this._set;
        float[] fArr = this._values;
        int length = objArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return;
            }
            objArr[i] = null;
            fArr[i] = 0.0f;
            length = i;
        }
    }

    public float remove(K k) {
        int iIndex = index(k);
        if (iIndex < 0) {
            return 0.0f;
        }
        float f = this._values[iIndex];
        removeAt(iIndex);
        return f;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TObjectFloatHashMap)) {
            return false;
        }
        TObjectFloatHashMap tObjectFloatHashMap = (TObjectFloatHashMap) obj;
        if (tObjectFloatHashMap.size() != size()) {
            return false;
        }
        return forEachEntry(new EqProcedure(tObjectFloatHashMap));
    }

    private static final class EqProcedure<K> implements TObjectFloatProcedure<K> {
        private final TObjectFloatHashMap<K> _otherMap;

        private static boolean eq(float f, float f2) {
            return f == f2;
        }

        EqProcedure(TObjectFloatHashMap<K> tObjectFloatHashMap) {
            this._otherMap = tObjectFloatHashMap;
        }

        @Override // gnu.trove.TObjectFloatProcedure
        public final boolean execute(K k, float f) {
            return this._otherMap.index(k) >= 0 && eq(f, this._otherMap.get(k));
        }
    }

    public int hashCode() {
        HashProcedure hashProcedure = new HashProcedure();
        forEachEntry(hashProcedure);
        return hashProcedure.getHashCode();
    }

    private final class HashProcedure implements TObjectFloatProcedure<K> {
        private int h;

        HashProcedure() {
        }

        public int getHashCode() {
            return this.h;
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // gnu.trove.TObjectFloatProcedure
        public boolean execute(K k, float f) {
            this.h += TObjectFloatHashMap.this._hashingStrategy.computeHashCode((T) k) ^ HashFunctions.hash(f);
            return true;
        }
    }

    @Override // gnu.trove.TObjectHash, gnu.trove.THash
    protected void removeAt(int i) {
        this._values[i] = 0.0f;
        super.removeAt(i);
    }

    public float[] getValues() {
        float[] fArr = new float[size()];
        float[] fArr2 = this._values;
        Object[] objArr = this._set;
        int length = objArr.length;
        int i = 0;
        while (true) {
            int i2 = length - 1;
            if (length <= 0) {
                return fArr;
            }
            Object obj = objArr[i2];
            if (obj != null && obj != REMOVED) {
                fArr[i] = fArr2[i2];
                i++;
            }
            length = i2;
        }
    }

    public Object[] keys() {
        Object[] objArr = new Object[size()];
        Object[] objArr2 = this._set;
        int length = objArr2.length;
        int i = 0;
        while (true) {
            int i2 = length - 1;
            if (length <= 0) {
                return objArr;
            }
            Object obj = objArr2[i2];
            if (obj != null && obj != REMOVED) {
                objArr[i] = objArr2[i2];
                i++;
            }
            length = i2;
        }
    }

    public boolean containsValue(float f) {
        Object[] objArr = this._set;
        float[] fArr = this._values;
        int length = objArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return false;
            }
            Object obj = objArr[i];
            if (obj != null && obj != REMOVED && f == fArr[i]) {
                return true;
            }
            length = i;
        }
    }

    public boolean containsKey(K k) {
        return contains(k);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean forEachKey(TObjectProcedure<K> tObjectProcedure) {
        return forEach(tObjectProcedure);
    }

    public boolean forEachValue(TFloatProcedure tFloatProcedure) {
        Object[] objArr = this._set;
        float[] fArr = this._values;
        int length = objArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return true;
            }
            Object obj = objArr[i];
            if (obj != null && obj != REMOVED && !tFloatProcedure.execute(fArr[i])) {
                return false;
            }
            length = i;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean forEachEntry(TObjectFloatProcedure<K> tObjectFloatProcedure) {
        Object[] objArr = this._set;
        float[] fArr = this._values;
        int length = objArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return true;
            }
            Object obj = objArr[i];
            if (obj != null && obj != REMOVED && !tObjectFloatProcedure.execute(objArr[i], fArr[i])) {
                return false;
            }
            length = i;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean retainEntries(TObjectFloatProcedure<K> tObjectFloatProcedure) {
        Object[] objArr = this._set;
        float[] fArr = this._values;
        stopCompactingOnRemove();
        boolean z = false;
        try {
            int length = objArr.length;
            while (true) {
                int i = length - 1;
                if (length > 0) {
                    Object obj = objArr[i];
                    if (obj != null && obj != REMOVED && !tObjectFloatProcedure.execute(objArr[i], fArr[i])) {
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

    public void transformValues(TFloatFunction tFloatFunction) {
        Object[] objArr = this._set;
        float[] fArr = this._values;
        int length = objArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return;
            }
            Object obj = objArr[i];
            if (obj != null && obj != REMOVED) {
                fArr[i] = tFloatFunction.execute(fArr[i]);
            }
            length = i;
        }
    }

    public boolean increment(K k) {
        return adjustValue(k, 1.0f);
    }

    public boolean adjustValue(K k, float f) {
        int iIndex = index(k);
        if (iIndex < 0) {
            return false;
        }
        float[] fArr = this._values;
        fArr[iIndex] = fArr[iIndex] + f;
        return true;
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
            put(objectInputStream.readObject(), objectInputStream.readFloat());
            i = i2;
        }
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        forEachEntry(new TObjectFloatProcedure<K>() { // from class: gnu.trove.TObjectFloatHashMap.1
            @Override // gnu.trove.TObjectFloatProcedure
            public boolean execute(K k, float f) {
                if (sb.length() != 0) {
                    sb.append(',').append(StringUtil.SPACE);
                }
                StringBuilder sb2 = sb;
                if (k == this) {
                    k = (K) "(this Map)";
                }
                sb2.append(k);
                sb.append('=');
                sb.append(f);
                return true;
            }
        });
        sb.append('}');
        sb.insert(0, '{');
        return sb.toString();
    }
}
