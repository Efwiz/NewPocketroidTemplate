package gnu.trove;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* JADX INFO: loaded from: classes11.dex */
public class TObjectDoubleHashMap<K> extends TObjectHash<K> {
    protected transient double[] _values;

    public TObjectDoubleHashMap() {
    }

    public TObjectDoubleHashMap(int i) {
        super(i);
    }

    public TObjectDoubleHashMap(int i, float f) {
        super(i, f);
    }

    public TObjectDoubleHashMap(TObjectHashingStrategy<K> tObjectHashingStrategy) {
        super(tObjectHashingStrategy);
    }

    public TObjectDoubleHashMap(int i, TObjectHashingStrategy<K> tObjectHashingStrategy) {
        super(i, tObjectHashingStrategy);
    }

    public TObjectDoubleHashMap(int i, float f, TObjectHashingStrategy<K> tObjectHashingStrategy) {
        super(i, f, tObjectHashingStrategy);
    }

    public TObjectDoubleIterator<K> iterator() {
        return new TObjectDoubleIterator<>(this);
    }

    @Override // gnu.trove.TObjectHash, gnu.trove.THash
    protected int setUp(int i) {
        int up = super.setUp(i);
        this._values = i == -1 ? null : new double[up];
        return up;
    }

    public double put(K k, double d) {
        double d2;
        boolean z;
        int iInsertionIndex = insertionIndex(k);
        if (iInsertionIndex < 0) {
            iInsertionIndex = (-iInsertionIndex) - 1;
            d2 = this._values[iInsertionIndex];
            z = false;
        } else {
            d2 = 0.0d;
            z = true;
        }
        Object obj = this._set[iInsertionIndex];
        this._set[iInsertionIndex] = k;
        this._values[iInsertionIndex] = d;
        if (z) {
            postInsertHook(obj == null);
        }
        return d2;
    }

    @Override // gnu.trove.THash
    protected void rehash(int i) {
        int iCapacity = capacity();
        Object[] objArr = this._set;
        double[] dArr = this._values;
        this._set = new Object[i];
        this._values = new double[i];
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
                this._values[iInsertionIndex] = dArr[i2];
            }
            iCapacity = i2;
        }
    }

    public double get(K k) {
        int iIndex = index(k);
        if (iIndex < 0) {
            return 0.0d;
        }
        return this._values[iIndex];
    }

    @Override // gnu.trove.THash
    public void clear() {
        super.clear();
        Object[] objArr = this._set;
        double[] dArr = this._values;
        int length = objArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return;
            }
            objArr[i] = null;
            dArr[i] = 0.0d;
            length = i;
        }
    }

    public double remove(K k) {
        int iIndex = index(k);
        if (iIndex < 0) {
            return 0.0d;
        }
        double d = this._values[iIndex];
        removeAt(iIndex);
        return d;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TObjectDoubleHashMap)) {
            return false;
        }
        TObjectDoubleHashMap tObjectDoubleHashMap = (TObjectDoubleHashMap) obj;
        if (tObjectDoubleHashMap.size() != size()) {
            return false;
        }
        return forEachEntry(new EqProcedure(tObjectDoubleHashMap));
    }

    private static final class EqProcedure<K> implements TObjectDoubleProcedure<K> {
        private final TObjectDoubleHashMap<K> _otherMap;

        private static boolean eq(double d, double d2) {
            return d == d2;
        }

        EqProcedure(TObjectDoubleHashMap<K> tObjectDoubleHashMap) {
            this._otherMap = tObjectDoubleHashMap;
        }

        @Override // gnu.trove.TObjectDoubleProcedure
        public final boolean execute(K k, double d) {
            return this._otherMap.index(k) >= 0 && eq(d, this._otherMap.get(k));
        }
    }

    public int hashCode() {
        HashProcedure hashProcedure = new HashProcedure();
        forEachEntry(hashProcedure);
        return hashProcedure.getHashCode();
    }

    private final class HashProcedure implements TObjectDoubleProcedure<K> {
        private int h;

        HashProcedure() {
        }

        public int getHashCode() {
            return this.h;
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // gnu.trove.TObjectDoubleProcedure
        public boolean execute(K k, double d) {
            this.h += TObjectDoubleHashMap.this._hashingStrategy.computeHashCode((T) k) ^ HashFunctions.hash(d);
            return true;
        }
    }

    @Override // gnu.trove.TObjectHash, gnu.trove.THash
    protected void removeAt(int i) {
        this._values[i] = 0.0d;
        super.removeAt(i);
    }

    public double[] getValues() {
        double[] dArr = new double[size()];
        double[] dArr2 = this._values;
        Object[] objArr = this._set;
        int length = objArr.length;
        int i = 0;
        while (true) {
            int i2 = length - 1;
            if (length <= 0) {
                return dArr;
            }
            Object obj = objArr[i2];
            if (obj != null && obj != REMOVED) {
                dArr[i] = dArr2[i2];
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

    public boolean containsValue(double d) {
        Object[] objArr = this._set;
        double[] dArr = this._values;
        int length = objArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return false;
            }
            Object obj = objArr[i];
            if (obj != null && obj != REMOVED && d == dArr[i]) {
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

    public boolean forEachValue(TDoubleProcedure tDoubleProcedure) {
        Object[] objArr = this._set;
        double[] dArr = this._values;
        int length = objArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return true;
            }
            Object obj = objArr[i];
            if (obj != null && obj != REMOVED && !tDoubleProcedure.execute(dArr[i])) {
                return false;
            }
            length = i;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean forEachEntry(TObjectDoubleProcedure<K> tObjectDoubleProcedure) {
        Object[] objArr = this._set;
        double[] dArr = this._values;
        int length = objArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return true;
            }
            Object obj = objArr[i];
            if (obj != null && obj != REMOVED && !tObjectDoubleProcedure.execute(objArr[i], dArr[i])) {
                return false;
            }
            length = i;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean retainEntries(TObjectDoubleProcedure<K> tObjectDoubleProcedure) {
        Object[] objArr = this._set;
        double[] dArr = this._values;
        stopCompactingOnRemove();
        boolean z = false;
        try {
            int length = objArr.length;
            while (true) {
                int i = length - 1;
                if (length > 0) {
                    Object obj = objArr[i];
                    if (obj != null && obj != REMOVED && !tObjectDoubleProcedure.execute(objArr[i], dArr[i])) {
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

    public void transformValues(TDoubleFunction tDoubleFunction) {
        Object[] objArr = this._set;
        double[] dArr = this._values;
        int length = objArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return;
            }
            Object obj = objArr[i];
            if (obj != null && obj != REMOVED) {
                dArr[i] = tDoubleFunction.execute(dArr[i]);
            }
            length = i;
        }
    }

    public boolean increment(K k) {
        return adjustValue(k, 1.0d);
    }

    public boolean adjustValue(K k, double d) {
        int iIndex = index(k);
        if (iIndex < 0) {
            return false;
        }
        double[] dArr = this._values;
        dArr[iIndex] = dArr[iIndex] + d;
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
            put(objectInputStream.readObject(), objectInputStream.readDouble());
            i = i2;
        }
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        forEachEntry(new TObjectDoubleProcedure<K>() { // from class: gnu.trove.TObjectDoubleHashMap.1
            @Override // gnu.trove.TObjectDoubleProcedure
            public boolean execute(K k, double d) {
                if (sb.length() != 0) {
                    sb.append(',').append(StringUtil.SPACE);
                }
                StringBuilder sb2 = sb;
                if (k == this) {
                    k = (K) "(this Map)";
                }
                sb2.append(k);
                sb.append('=');
                sb.append(d);
                return true;
            }
        });
        sb.append('}');
        sb.insert(0, '{');
        return sb.toString();
    }
}
