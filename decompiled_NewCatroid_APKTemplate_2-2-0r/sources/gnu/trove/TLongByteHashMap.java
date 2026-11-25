package gnu.trove;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* JADX INFO: loaded from: classes11.dex */
public class TLongByteHashMap extends TLongHash {
    protected transient byte[] _values;

    public TLongByteHashMap() {
    }

    public TLongByteHashMap(int i) {
        super(i);
    }

    public TLongByteHashMap(int i, float f) {
        super(i, f);
    }

    public TLongByteHashMap(TLongHashingStrategy tLongHashingStrategy) {
        super(tLongHashingStrategy);
    }

    public TLongByteHashMap(int i, TLongHashingStrategy tLongHashingStrategy) {
        super(i, tLongHashingStrategy);
    }

    public TLongByteHashMap(int i, float f, TLongHashingStrategy tLongHashingStrategy) {
        super(i, f, tLongHashingStrategy);
    }

    @Override // gnu.trove.TLongHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
    public Object clone() {
        TLongByteHashMap tLongByteHashMap = (TLongByteHashMap) super.clone();
        byte[] bArr = this._values;
        tLongByteHashMap._values = bArr == null ? null : (byte[]) bArr.clone();
        return tLongByteHashMap;
    }

    public TLongByteIterator iterator() {
        return new TLongByteIterator(this);
    }

    @Override // gnu.trove.TLongHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
    protected int setUp(int i) {
        int up = super.setUp(i);
        this._values = i == -1 ? null : new byte[up];
        return up;
    }

    public byte put(long j, byte b) {
        byte b2;
        boolean z;
        int iInsertionIndex = insertionIndex(j);
        if (iInsertionIndex < 0) {
            iInsertionIndex = (-iInsertionIndex) - 1;
            b2 = this._values[iInsertionIndex];
            z = false;
        } else {
            b2 = 0;
            z = true;
        }
        byte b3 = this._states[iInsertionIndex];
        this._set[iInsertionIndex] = j;
        this._states[iInsertionIndex] = 1;
        this._values[iInsertionIndex] = b;
        if (z) {
            postInsertHook(b3 == 0);
        }
        return b2;
    }

    @Override // gnu.trove.THash
    protected void rehash(int i) {
        int iCapacity = capacity();
        long[] jArr = this._set;
        byte[] bArr = this._values;
        byte[] bArr2 = this._states;
        this._set = new long[i];
        this._values = new byte[i];
        this._states = new byte[i];
        while (true) {
            int i2 = iCapacity - 1;
            if (iCapacity <= 0) {
                return;
            }
            if (bArr2[i2] == 1) {
                long j = jArr[i2];
                int iInsertionIndex = insertionIndex(j);
                this._set[iInsertionIndex] = j;
                this._values[iInsertionIndex] = bArr[i2];
                this._states[iInsertionIndex] = 1;
            }
            iCapacity = i2;
        }
    }

    public byte get(long j) {
        int iIndex = index(j);
        if (iIndex < 0) {
            return (byte) 0;
        }
        return this._values[iIndex];
    }

    @Override // gnu.trove.THash
    public void clear() {
        super.clear();
        long[] jArr = this._set;
        byte[] bArr = this._values;
        if (bArr == null) {
            return;
        }
        byte[] bArr2 = this._states;
        int length = jArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return;
            }
            jArr[i] = 0;
            bArr[i] = 0;
            bArr2[i] = 0;
            length = i;
        }
    }

    public byte remove(long j) {
        int iIndex = index(j);
        if (iIndex < 0) {
            return (byte) 0;
        }
        byte b = this._values[iIndex];
        removeAt(iIndex);
        return b;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TLongByteHashMap)) {
            return false;
        }
        TLongByteHashMap tLongByteHashMap = (TLongByteHashMap) obj;
        if (tLongByteHashMap.size() != size()) {
            return false;
        }
        return forEachEntry(new EqProcedure(tLongByteHashMap));
    }

    public int hashCode() {
        HashProcedure hashProcedure = new HashProcedure();
        forEachEntry(hashProcedure);
        return hashProcedure.getHashCode();
    }

    private final class HashProcedure implements TLongByteProcedure {
        private int h;

        HashProcedure() {
        }

        public int getHashCode() {
            return this.h;
        }

        @Override // gnu.trove.TLongByteProcedure
        public final boolean execute(long j, byte b) {
            this.h += TLongByteHashMap.this._hashingStrategy.computeHashCode(j) ^ HashFunctions.hash((int) b);
            return true;
        }
    }

    private static final class EqProcedure implements TLongByteProcedure {
        private final TLongByteHashMap _otherMap;

        private static boolean eq(byte b, byte b2) {
            return b == b2;
        }

        EqProcedure(TLongByteHashMap tLongByteHashMap) {
            this._otherMap = tLongByteHashMap;
        }

        @Override // gnu.trove.TLongByteProcedure
        public final boolean execute(long j, byte b) {
            return this._otherMap.index(j) >= 0 && eq(b, this._otherMap.get(j));
        }
    }

    @Override // gnu.trove.TLongHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
    protected void removeAt(int i) {
        this._values[i] = 0;
        super.removeAt(i);
    }

    public byte[] getValues() {
        byte[] bArr = new byte[size()];
        byte[] bArr2 = this._values;
        byte[] bArr3 = this._states;
        if (bArr3 != null) {
            int length = bArr3.length;
            int i = 0;
            while (true) {
                int i2 = length - 1;
                if (length <= 0) {
                    break;
                }
                if (bArr3[i2] == 1) {
                    bArr[i] = bArr2[i2];
                    i++;
                }
                length = i2;
            }
        }
        return bArr;
    }

    public long[] keys() {
        long[] jArr = new long[size()];
        long[] jArr2 = this._set;
        byte[] bArr = this._states;
        if (bArr != null) {
            int length = bArr.length;
            int i = 0;
            while (true) {
                int i2 = length - 1;
                if (length <= 0) {
                    break;
                }
                if (bArr[i2] == 1) {
                    jArr[i] = jArr2[i2];
                    i++;
                }
                length = i2;
            }
        }
        return jArr;
    }

    public boolean containsValue(byte b) {
        byte[] bArr = this._states;
        byte[] bArr2 = this._values;
        if (bArr == null) {
            return false;
        }
        int length = bArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return false;
            }
            if (bArr[i] == 1 && b == bArr2[i]) {
                return true;
            }
            length = i;
        }
    }

    public boolean containsKey(long j) {
        return contains(j);
    }

    public boolean forEachKey(TLongProcedure tLongProcedure) {
        return forEach(tLongProcedure);
    }

    public boolean forEachValue(TByteProcedure tByteProcedure) {
        byte[] bArr = this._states;
        byte[] bArr2 = this._values;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                if (bArr[i] == 1 && !tByteProcedure.execute(bArr2[i])) {
                    return false;
                }
                length = i;
            }
        }
        return true;
    }

    public boolean forEachEntry(TLongByteProcedure tLongByteProcedure) {
        byte[] bArr = this._states;
        long[] jArr = this._set;
        byte[] bArr2 = this._values;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                if (bArr[i] == 1 && !tLongByteProcedure.execute(jArr[i], bArr2[i])) {
                    return false;
                }
                length = i;
            }
        }
        return true;
    }

    public boolean retainEntries(TLongByteProcedure tLongByteProcedure) {
        byte[] bArr = this._states;
        long[] jArr = this._set;
        byte[] bArr2 = this._values;
        boolean z = false;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                length--;
                if (length <= 0) {
                    break;
                }
                if (bArr[length] == 1 && !tLongByteProcedure.execute(jArr[length], bArr2[length])) {
                    removeAt(length);
                    z = true;
                }
            }
        }
        return z;
    }

    public void transformValues(TByteFunction tByteFunction) {
        byte[] bArr = this._states;
        byte[] bArr2 = this._values;
        if (bArr == null) {
            return;
        }
        int length = bArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return;
            }
            if (bArr[i] == 1) {
                bArr2[i] = tByteFunction.execute(bArr2[i]);
            }
            length = i;
        }
    }

    public boolean increment(long j) {
        return adjustValue(j, (byte) 1);
    }

    public boolean adjustValue(long j, byte b) {
        int iIndex = index(j);
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

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        int i = objectInputStream.readInt();
        setUp(i);
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return;
            }
            put(objectInputStream.readLong(), objectInputStream.readByte());
            i = i2;
        }
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        forEachEntry(new TLongByteProcedure() { // from class: gnu.trove.TLongByteHashMap.1
            @Override // gnu.trove.TLongByteProcedure
            public boolean execute(long j, byte b) {
                if (sb.length() != 0) {
                    sb.append(',').append(StringUtil.SPACE);
                }
                sb.append(j);
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
