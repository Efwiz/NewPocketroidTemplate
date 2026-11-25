package gnu.trove;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* JADX INFO: loaded from: classes11.dex */
public class TObjectByteHashMap<K> extends TObjectHash<K> {
    protected transient byte[] _values;

    public TObjectByteHashMap() {
    }

    public TObjectByteHashMap(int i) {
        super(i);
    }

    public TObjectByteHashMap(int i, float f) {
        super(i, f);
    }

    public TObjectByteHashMap(TObjectHashingStrategy<K> tObjectHashingStrategy) {
        super(tObjectHashingStrategy);
    }

    public TObjectByteHashMap(int i, TObjectHashingStrategy<K> tObjectHashingStrategy) {
        super(i, tObjectHashingStrategy);
    }

    public TObjectByteHashMap(int i, float f, TObjectHashingStrategy<K> tObjectHashingStrategy) {
        super(i, f, tObjectHashingStrategy);
    }

    public TObjectByteIterator<K> iterator() {
        return new TObjectByteIterator<>(this);
    }

    @Override // gnu.trove.TObjectHash, gnu.trove.THash
    protected int setUp(int i) {
        int up = super.setUp(i);
        this._values = i == -1 ? null : new byte[up];
        return up;
    }

    public byte put(K k, byte b) {
        byte b2;
        boolean z;
        int iInsertionIndex = insertionIndex(k);
        if (iInsertionIndex < 0) {
            iInsertionIndex = (-iInsertionIndex) - 1;
            b2 = this._values[iInsertionIndex];
            z = false;
        } else {
            b2 = 0;
            z = true;
        }
        Object obj = this._set[iInsertionIndex];
        this._set[iInsertionIndex] = k;
        this._values[iInsertionIndex] = b;
        if (z) {
            postInsertHook(obj == null);
        }
        return b2;
    }

    @Override // gnu.trove.THash
    protected void rehash(int i) {
        int iCapacity = capacity();
        Object[] objArr = this._set;
        byte[] bArr = this._values;
        this._set = new Object[i];
        this._values = new byte[i];
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
                this._values[iInsertionIndex] = bArr[i2];
            }
            iCapacity = i2;
        }
    }

    public byte get(K k) {
        int iIndex = index(k);
        if (iIndex < 0) {
            return (byte) 0;
        }
        return this._values[iIndex];
    }

    @Override // gnu.trove.THash
    public void clear() {
        super.clear();
        Object[] objArr = this._set;
        byte[] bArr = this._values;
        int length = objArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return;
            }
            objArr[i] = null;
            bArr[i] = 0;
            length = i;
        }
    }

    public byte remove(K k) {
        int iIndex = index(k);
        if (iIndex < 0) {
            return (byte) 0;
        }
        byte b = this._values[iIndex];
        removeAt(iIndex);
        return b;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TObjectByteHashMap)) {
            return false;
        }
        TObjectByteHashMap tObjectByteHashMap = (TObjectByteHashMap) obj;
        if (tObjectByteHashMap.size() != size()) {
            return false;
        }
        return forEachEntry(new EqProcedure(tObjectByteHashMap));
    }

    private static final class EqProcedure<K> implements TObjectByteProcedure<K> {
        private final TObjectByteHashMap<K> _otherMap;

        private static boolean eq(byte b, byte b2) {
            return b == b2;
        }

        EqProcedure(TObjectByteHashMap<K> tObjectByteHashMap) {
            this._otherMap = tObjectByteHashMap;
        }

        @Override // gnu.trove.TObjectByteProcedure
        public final boolean execute(K k, byte b) {
            return this._otherMap.index(k) >= 0 && eq(b, this._otherMap.get(k));
        }
    }

    public int hashCode() {
        HashProcedure hashProcedure = new HashProcedure();
        forEachEntry(hashProcedure);
        return hashProcedure.getHashCode();
    }

    private final class HashProcedure implements TObjectByteProcedure<K> {
        private int h;

        HashProcedure() {
        }

        public int getHashCode() {
            return this.h;
        }

        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // gnu.trove.TObjectByteProcedure
        public boolean execute(K k, byte b) {
            this.h += TObjectByteHashMap.this._hashingStrategy.computeHashCode((T) k) ^ HashFunctions.hash((int) b);
            return true;
        }
    }

    @Override // gnu.trove.TObjectHash, gnu.trove.THash
    protected void removeAt(int i) {
        this._values[i] = 0;
        super.removeAt(i);
    }

    public byte[] getValues() {
        byte[] bArr = new byte[size()];
        byte[] bArr2 = this._values;
        Object[] objArr = this._set;
        int length = objArr.length;
        int i = 0;
        while (true) {
            int i2 = length - 1;
            if (length <= 0) {
                return bArr;
            }
            Object obj = objArr[i2];
            if (obj != null && obj != REMOVED) {
                bArr[i] = bArr2[i2];
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

    public boolean containsValue(byte b) {
        Object[] objArr = this._set;
        byte[] bArr = this._values;
        int length = objArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return false;
            }
            Object obj = objArr[i];
            if (obj != null && obj != REMOVED && b == bArr[i]) {
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

    public boolean forEachValue(TByteProcedure tByteProcedure) {
        Object[] objArr = this._set;
        byte[] bArr = this._values;
        int length = objArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return true;
            }
            Object obj = objArr[i];
            if (obj != null && obj != REMOVED && !tByteProcedure.execute(bArr[i])) {
                return false;
            }
            length = i;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean forEachEntry(TObjectByteProcedure<K> tObjectByteProcedure) {
        Object[] objArr = this._set;
        byte[] bArr = this._values;
        int length = objArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return true;
            }
            Object obj = objArr[i];
            if (obj != null && obj != REMOVED && !tObjectByteProcedure.execute(objArr[i], bArr[i])) {
                return false;
            }
            length = i;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean retainEntries(TObjectByteProcedure<K> tObjectByteProcedure) {
        Object[] objArr = this._set;
        byte[] bArr = this._values;
        stopCompactingOnRemove();
        boolean z = false;
        try {
            int length = objArr.length;
            while (true) {
                int i = length - 1;
                if (length > 0) {
                    Object obj = objArr[i];
                    if (obj != null && obj != REMOVED && !tObjectByteProcedure.execute(objArr[i], bArr[i])) {
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

    public void transformValues(TByteFunction tByteFunction) {
        Object[] objArr = this._set;
        byte[] bArr = this._values;
        int length = objArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return;
            }
            Object obj = objArr[i];
            if (obj != null && obj != REMOVED) {
                bArr[i] = tByteFunction.execute(bArr[i]);
            }
            length = i;
        }
    }

    public boolean increment(K k) {
        return adjustValue(k, (byte) 1);
    }

    public boolean adjustValue(K k, byte b) {
        int iIndex = index(k);
        if (iIndex < 0) {
            return false;
        }
        byte[] bArr = this._values;
        bArr[iIndex] = (byte) (bArr[iIndex] + b);
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
            put(objectInputStream.readObject(), objectInputStream.readByte());
            i = i2;
        }
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        forEachEntry(new TObjectByteProcedure<K>() { // from class: gnu.trove.TObjectByteHashMap.1
            @Override // gnu.trove.TObjectByteProcedure
            public boolean execute(K k, byte b) {
                if (sb.length() != 0) {
                    sb.append(',').append(StringUtil.SPACE);
                }
                StringBuilder sb2 = sb;
                if (k == this) {
                    k = (K) "(this Map)";
                }
                sb2.append(k);
                sb.append('=');
                sb.append((int) b);
                return true;
            }
        });
        sb.append('}');
        sb.insert(0, '{');
        return sb.toString();
    }
}
