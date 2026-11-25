package gnu.trove;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* JADX INFO: loaded from: classes11.dex */
public class TObjectIntHashMap<K> extends TObjectHash<K> {
    protected transient int[] _values;

    public TObjectIntHashMap() {
    }

    public TObjectIntHashMap(int i) {
        super(i);
    }

    public TObjectIntHashMap(int i, float f) {
        super(i, f);
    }

    public TObjectIntHashMap(TObjectHashingStrategy<K> tObjectHashingStrategy) {
        super(tObjectHashingStrategy);
    }

    public TObjectIntHashMap(int i, TObjectHashingStrategy<K> tObjectHashingStrategy) {
        super(i, tObjectHashingStrategy);
    }

    public TObjectIntHashMap(int i, float f, TObjectHashingStrategy<K> tObjectHashingStrategy) {
        super(i, f, tObjectHashingStrategy);
    }

    public TObjectIntIterator<K> iterator() {
        return new TObjectIntIterator<>(this);
    }

    @Override // gnu.trove.TObjectHash, gnu.trove.THash
    protected int setUp(int i) {
        int up = super.setUp(i);
        this._values = i == -1 ? null : new int[up];
        return up;
    }

    public int put(K k, int i) {
        int i2;
        boolean z;
        int iInsertionIndex = insertionIndex(k);
        if (iInsertionIndex < 0) {
            iInsertionIndex = (-iInsertionIndex) - 1;
            i2 = this._values[iInsertionIndex];
            z = false;
        } else {
            i2 = 0;
            z = true;
        }
        Object obj = this._set[iInsertionIndex];
        this._set[iInsertionIndex] = k;
        this._values[iInsertionIndex] = i;
        if (z) {
            postInsertHook(obj == null);
        }
        return i2;
    }

    @Override // gnu.trove.THash
    protected void rehash(int i) {
        int iCapacity = capacity();
        Object[] objArr = this._set;
        int[] iArr = this._values;
        this._set = new Object[i];
        this._values = new int[i];
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
                this._values[iInsertionIndex] = iArr[i2];
            }
            iCapacity = i2;
        }
    }

    public int get(K k) {
        int iIndex = index(k);
        if (iIndex < 0) {
            return 0;
        }
        return this._values[iIndex];
    }

    @Override // gnu.trove.THash
    public void clear() {
        super.clear();
        Object[] objArr = this._set;
        int[] iArr = this._values;
        int length = objArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return;
            }
            objArr[i] = null;
            iArr[i] = 0;
            length = i;
        }
    }

    public int remove(K k) {
        int iIndex = index(k);
        if (iIndex < 0) {
            return 0;
        }
        int i = this._values[iIndex];
        removeAt(iIndex);
        return i;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TObjectIntHashMap)) {
            return false;
        }
        TObjectIntHashMap tObjectIntHashMap = (TObjectIntHashMap) obj;
        if (tObjectIntHashMap.size() != size()) {
            return false;
        }
        return forEachEntry(new EqProcedure(tObjectIntHashMap));
    }

    private static final class EqProcedure<K> implements TObjectIntProcedure<K> {
        private final TObjectIntHashMap<K> _otherMap;

        private static boolean eq(int i, int i2) {
            return i == i2;
        }

        EqProcedure(TObjectIntHashMap<K> tObjectIntHashMap) {
            this._otherMap = tObjectIntHashMap;
        }

        @Override // gnu.trove.TObjectIntProcedure
        public final boolean execute(K k, int i) {
            return this._otherMap.index(k) >= 0 && eq(i, this._otherMap.get(k));
        }
    }

    public int hashCode() {
        HashProcedure hashProcedure = new HashProcedure();
        forEachEntry(hashProcedure);
        return hashProcedure.getHashCode();
    }

    private final class HashProcedure implements TObjectIntProcedure<K> {
        private int h;

        HashProcedure() {
        }

        public int getHashCode() {
            return this.h;
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // gnu.trove.TObjectIntProcedure
        public boolean execute(K k, int i) {
            this.h += TObjectIntHashMap.this._hashingStrategy.computeHashCode((T) k) ^ HashFunctions.hash(i);
            return true;
        }
    }

    @Override // gnu.trove.TObjectHash, gnu.trove.THash
    protected void removeAt(int i) {
        this._values[i] = 0;
        super.removeAt(i);
    }

    public int[] getValues() {
        int[] iArr = new int[size()];
        int[] iArr2 = this._values;
        Object[] objArr = this._set;
        int length = objArr.length;
        int i = 0;
        while (true) {
            int i2 = length - 1;
            if (length <= 0) {
                return iArr;
            }
            Object obj = objArr[i2];
            if (obj != null && obj != REMOVED) {
                iArr[i] = iArr2[i2];
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

    public boolean containsValue(int i) {
        Object[] objArr = this._set;
        int[] iArr = this._values;
        int length = objArr.length;
        while (true) {
            int i2 = length - 1;
            if (length <= 0) {
                return false;
            }
            Object obj = objArr[i2];
            if (obj != null && obj != REMOVED && i == iArr[i2]) {
                return true;
            }
            length = i2;
        }
    }

    public boolean containsKey(K k) {
        return contains(k);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean forEachKey(TObjectProcedure<K> tObjectProcedure) {
        return forEach(tObjectProcedure);
    }

    public boolean forEachValue(TIntProcedure tIntProcedure) {
        Object[] objArr = this._set;
        int[] iArr = this._values;
        int length = objArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return true;
            }
            Object obj = objArr[i];
            if (obj != null && obj != REMOVED && !tIntProcedure.execute(iArr[i])) {
                return false;
            }
            length = i;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean forEachEntry(TObjectIntProcedure<K> tObjectIntProcedure) {
        Object[] objArr = this._set;
        int[] iArr = this._values;
        int length = objArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return true;
            }
            Object obj = objArr[i];
            if (obj != null && obj != REMOVED && !tObjectIntProcedure.execute(objArr[i], iArr[i])) {
                return false;
            }
            length = i;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean retainEntries(TObjectIntProcedure<K> tObjectIntProcedure) {
        Object[] objArr = this._set;
        int[] iArr = this._values;
        stopCompactingOnRemove();
        boolean z = false;
        try {
            int length = objArr.length;
            while (true) {
                int i = length - 1;
                if (length > 0) {
                    Object obj = objArr[i];
                    if (obj != null && obj != REMOVED && !tObjectIntProcedure.execute(objArr[i], iArr[i])) {
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

    public void transformValues(TIntFunction tIntFunction) {
        Object[] objArr = this._set;
        int[] iArr = this._values;
        int length = objArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return;
            }
            Object obj = objArr[i];
            if (obj != null && obj != REMOVED) {
                iArr[i] = tIntFunction.execute(iArr[i]);
            }
            length = i;
        }
    }

    public boolean increment(K k) {
        return adjustValue(k, 1);
    }

    public boolean adjustValue(K k, int i) {
        int iIndex = index(k);
        if (iIndex < 0) {
            return false;
        }
        int[] iArr = this._values;
        iArr[iIndex] = iArr[iIndex] + i;
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
            put(objectInputStream.readObject(), objectInputStream.readInt());
            i = i2;
        }
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        forEachEntry(new TObjectIntProcedure<K>() { // from class: gnu.trove.TObjectIntHashMap.1
            @Override // gnu.trove.TObjectIntProcedure
            public boolean execute(K k, int i) {
                if (sb.length() != 0) {
                    sb.append(',').append(StringUtil.SPACE);
                }
                StringBuilder sb2 = sb;
                if (k == this) {
                    k = (K) "(this Map)";
                }
                sb2.append(k);
                sb.append('=');
                sb.append(i);
                return true;
            }
        });
        sb.append('}');
        sb.insert(0, '{');
        return sb.toString();
    }
}
