package gnu.trove;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* JADX INFO: loaded from: classes11.dex */
public class TByteIntHashMap extends TByteHash {
    protected transient int[] _values;

    public TByteIntHashMap() {
    }

    public TByteIntHashMap(int i) {
        super(i);
    }

    public TByteIntHashMap(int i, float f) {
        super(i, f);
    }

    public TByteIntHashMap(TByteHashingStrategy tByteHashingStrategy) {
        super(tByteHashingStrategy);
    }

    public TByteIntHashMap(int i, TByteHashingStrategy tByteHashingStrategy) {
        super(i, tByteHashingStrategy);
    }

    public TByteIntHashMap(int i, float f, TByteHashingStrategy tByteHashingStrategy) {
        super(i, f, tByteHashingStrategy);
    }

    @Override // gnu.trove.TByteHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
    public Object clone() {
        TByteIntHashMap tByteIntHashMap = (TByteIntHashMap) super.clone();
        int[] iArr = this._values;
        tByteIntHashMap._values = iArr == null ? null : (int[]) iArr.clone();
        return tByteIntHashMap;
    }

    public TByteIntIterator iterator() {
        return new TByteIntIterator(this);
    }

    @Override // gnu.trove.TByteHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
    protected int setUp(int i) {
        int up = super.setUp(i);
        this._values = i == -1 ? null : new int[up];
        return up;
    }

    public int put(byte b, int i) {
        int i2;
        boolean z;
        int iInsertionIndex = insertionIndex(b);
        if (iInsertionIndex < 0) {
            iInsertionIndex = (-iInsertionIndex) - 1;
            i2 = this._values[iInsertionIndex];
            z = false;
        } else {
            i2 = 0;
            z = true;
        }
        byte b2 = this._states[iInsertionIndex];
        this._set[iInsertionIndex] = b;
        this._states[iInsertionIndex] = 1;
        this._values[iInsertionIndex] = i;
        if (z) {
            postInsertHook(b2 == 0);
        }
        return i2;
    }

    @Override // gnu.trove.THash
    protected void rehash(int i) {
        int iCapacity = capacity();
        byte[] bArr = this._set;
        int[] iArr = this._values;
        byte[] bArr2 = this._states;
        this._set = new byte[i];
        this._values = new int[i];
        this._states = new byte[i];
        while (true) {
            int i2 = iCapacity - 1;
            if (iCapacity <= 0) {
                return;
            }
            if (bArr2[i2] == 1) {
                byte b = bArr[i2];
                int iInsertionIndex = insertionIndex(b);
                this._set[iInsertionIndex] = b;
                this._values[iInsertionIndex] = iArr[i2];
                this._states[iInsertionIndex] = 1;
            }
            iCapacity = i2;
        }
    }

    public int get(byte b) {
        int iIndex = index(b);
        if (iIndex < 0) {
            return 0;
        }
        return this._values[iIndex];
    }

    @Override // gnu.trove.THash
    public void clear() {
        super.clear();
        byte[] bArr = this._set;
        int[] iArr = this._values;
        if (iArr == null) {
            return;
        }
        byte[] bArr2 = this._states;
        int length = bArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return;
            }
            bArr[i] = 0;
            iArr[i] = 0;
            bArr2[i] = 0;
            length = i;
        }
    }

    public int remove(byte b) {
        int iIndex = index(b);
        if (iIndex < 0) {
            return 0;
        }
        int i = this._values[iIndex];
        removeAt(iIndex);
        return i;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TByteIntHashMap)) {
            return false;
        }
        TByteIntHashMap tByteIntHashMap = (TByteIntHashMap) obj;
        if (tByteIntHashMap.size() != size()) {
            return false;
        }
        return forEachEntry(new EqProcedure(tByteIntHashMap));
    }

    public int hashCode() {
        HashProcedure hashProcedure = new HashProcedure();
        forEachEntry(hashProcedure);
        return hashProcedure.getHashCode();
    }

    private final class HashProcedure implements TByteIntProcedure {
        private int h;

        HashProcedure() {
        }

        public int getHashCode() {
            return this.h;
        }

        @Override // gnu.trove.TByteIntProcedure
        public final boolean execute(byte b, int i) {
            this.h += TByteIntHashMap.this._hashingStrategy.computeHashCode(b) ^ HashFunctions.hash(i);
            return true;
        }
    }

    private static final class EqProcedure implements TByteIntProcedure {
        private final TByteIntHashMap _otherMap;

        private static boolean eq(int i, int i2) {
            return i == i2;
        }

        EqProcedure(TByteIntHashMap tByteIntHashMap) {
            this._otherMap = tByteIntHashMap;
        }

        @Override // gnu.trove.TByteIntProcedure
        public final boolean execute(byte b, int i) {
            return this._otherMap.index(b) >= 0 && eq(i, this._otherMap.get(b));
        }
    }

    @Override // gnu.trove.TByteHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
    protected void removeAt(int i) {
        this._values[i] = 0;
        super.removeAt(i);
    }

    public int[] getValues() {
        int[] iArr = new int[size()];
        int[] iArr2 = this._values;
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
                    iArr[i] = iArr2[i2];
                    i++;
                }
                length = i2;
            }
        }
        return iArr;
    }

    public byte[] keys() {
        byte[] bArr = new byte[size()];
        byte[] bArr2 = this._set;
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

    public boolean containsValue(int i) {
        byte[] bArr = this._states;
        int[] iArr = this._values;
        if (bArr == null) {
            return false;
        }
        int length = bArr.length;
        while (true) {
            int i2 = length - 1;
            if (length <= 0) {
                return false;
            }
            if (bArr[i2] == 1 && i == iArr[i2]) {
                return true;
            }
            length = i2;
        }
    }

    public boolean containsKey(byte b) {
        return contains(b);
    }

    public boolean forEachKey(TByteProcedure tByteProcedure) {
        return forEach(tByteProcedure);
    }

    public boolean forEachValue(TIntProcedure tIntProcedure) {
        byte[] bArr = this._states;
        int[] iArr = this._values;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                if (bArr[i] == 1 && !tIntProcedure.execute(iArr[i])) {
                    return false;
                }
                length = i;
            }
        }
        return true;
    }

    public boolean forEachEntry(TByteIntProcedure tByteIntProcedure) {
        byte[] bArr = this._states;
        byte[] bArr2 = this._set;
        int[] iArr = this._values;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                if (bArr[i] == 1 && !tByteIntProcedure.execute(bArr2[i], iArr[i])) {
                    return false;
                }
                length = i;
            }
        }
        return true;
    }

    public boolean retainEntries(TByteIntProcedure tByteIntProcedure) {
        byte[] bArr = this._states;
        byte[] bArr2 = this._set;
        int[] iArr = this._values;
        boolean z = false;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                length--;
                if (length <= 0) {
                    break;
                }
                if (bArr[length] == 1 && !tByteIntProcedure.execute(bArr2[length], iArr[length])) {
                    removeAt(length);
                    z = true;
                }
            }
        }
        return z;
    }

    public void transformValues(TIntFunction tIntFunction) {
        byte[] bArr = this._states;
        int[] iArr = this._values;
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
                iArr[i] = tIntFunction.execute(iArr[i]);
            }
            length = i;
        }
    }

    public boolean increment(byte b) {
        return adjustValue(b, 1);
    }

    public boolean adjustValue(byte b, int i) {
        int iIndex = index(b);
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

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        int i = objectInputStream.readInt();
        setUp(i);
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return;
            }
            put(objectInputStream.readByte(), objectInputStream.readInt());
            i = i2;
        }
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        forEachEntry(new TByteIntProcedure() { // from class: gnu.trove.TByteIntHashMap.1
            @Override // gnu.trove.TByteIntProcedure
            public boolean execute(byte b, int i) {
                if (sb.length() != 0) {
                    sb.append(',').append(StringUtil.SPACE);
                }
                sb.append((int) b);
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
