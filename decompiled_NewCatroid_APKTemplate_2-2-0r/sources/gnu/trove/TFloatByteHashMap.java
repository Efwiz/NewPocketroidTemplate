package gnu.trove;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* JADX INFO: loaded from: classes11.dex */
public class TFloatByteHashMap extends TFloatHash {
    protected transient byte[] _values;

    public TFloatByteHashMap() {
    }

    public TFloatByteHashMap(int i) {
        super(i);
    }

    public TFloatByteHashMap(int i, float f) {
        super(i, f);
    }

    public TFloatByteHashMap(TFloatHashingStrategy tFloatHashingStrategy) {
        super(tFloatHashingStrategy);
    }

    public TFloatByteHashMap(int i, TFloatHashingStrategy tFloatHashingStrategy) {
        super(i, tFloatHashingStrategy);
    }

    public TFloatByteHashMap(int i, float f, TFloatHashingStrategy tFloatHashingStrategy) {
        super(i, f, tFloatHashingStrategy);
    }

    @Override // gnu.trove.TFloatHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
    public Object clone() {
        TFloatByteHashMap tFloatByteHashMap = (TFloatByteHashMap) super.clone();
        byte[] bArr = this._values;
        tFloatByteHashMap._values = bArr == null ? null : (byte[]) bArr.clone();
        return tFloatByteHashMap;
    }

    public TFloatByteIterator iterator() {
        return new TFloatByteIterator(this);
    }

    @Override // gnu.trove.TFloatHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
    protected int setUp(int i) {
        int up = super.setUp(i);
        this._values = i == -1 ? null : new byte[up];
        return up;
    }

    public byte put(float f, byte b) {
        byte b2;
        boolean z;
        int iInsertionIndex = insertionIndex(f);
        if (iInsertionIndex < 0) {
            iInsertionIndex = (-iInsertionIndex) - 1;
            b2 = this._values[iInsertionIndex];
            z = false;
        } else {
            b2 = 0;
            z = true;
        }
        byte b3 = this._states[iInsertionIndex];
        this._set[iInsertionIndex] = f;
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
        float[] fArr = this._set;
        byte[] bArr = this._values;
        byte[] bArr2 = this._states;
        this._set = new float[i];
        this._values = new byte[i];
        this._states = new byte[i];
        while (true) {
            int i2 = iCapacity - 1;
            if (iCapacity <= 0) {
                return;
            }
            if (bArr2[i2] == 1) {
                float f = fArr[i2];
                int iInsertionIndex = insertionIndex(f);
                this._set[iInsertionIndex] = f;
                this._values[iInsertionIndex] = bArr[i2];
                this._states[iInsertionIndex] = 1;
            }
            iCapacity = i2;
        }
    }

    public byte get(float f) {
        int iIndex = index(f);
        if (iIndex < 0) {
            return (byte) 0;
        }
        return this._values[iIndex];
    }

    @Override // gnu.trove.THash
    public void clear() {
        super.clear();
        float[] fArr = this._set;
        byte[] bArr = this._values;
        if (bArr == null) {
            return;
        }
        byte[] bArr2 = this._states;
        int length = fArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return;
            }
            fArr[i] = 0.0f;
            bArr[i] = 0;
            bArr2[i] = 0;
            length = i;
        }
    }

    public byte remove(float f) {
        int iIndex = index(f);
        if (iIndex < 0) {
            return (byte) 0;
        }
        byte b = this._values[iIndex];
        removeAt(iIndex);
        return b;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TFloatByteHashMap)) {
            return false;
        }
        TFloatByteHashMap tFloatByteHashMap = (TFloatByteHashMap) obj;
        if (tFloatByteHashMap.size() != size()) {
            return false;
        }
        return forEachEntry(new EqProcedure(tFloatByteHashMap));
    }

    public int hashCode() {
        HashProcedure hashProcedure = new HashProcedure();
        forEachEntry(hashProcedure);
        return hashProcedure.getHashCode();
    }

    private final class HashProcedure implements TFloatByteProcedure {
        private int h;

        HashProcedure() {
        }

        public int getHashCode() {
            return this.h;
        }

        @Override // gnu.trove.TFloatByteProcedure
        public final boolean execute(float f, byte b) {
            this.h += TFloatByteHashMap.this._hashingStrategy.computeHashCode(f) ^ HashFunctions.hash((int) b);
            return true;
        }
    }

    private static final class EqProcedure implements TFloatByteProcedure {
        private final TFloatByteHashMap _otherMap;

        private static boolean eq(byte b, byte b2) {
            return b == b2;
        }

        EqProcedure(TFloatByteHashMap tFloatByteHashMap) {
            this._otherMap = tFloatByteHashMap;
        }

        @Override // gnu.trove.TFloatByteProcedure
        public final boolean execute(float f, byte b) {
            return this._otherMap.index(f) >= 0 && eq(b, this._otherMap.get(f));
        }
    }

    @Override // gnu.trove.TFloatHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
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

    public float[] keys() {
        float[] fArr = new float[size()];
        float[] fArr2 = this._set;
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
                    fArr[i] = fArr2[i2];
                    i++;
                }
                length = i2;
            }
        }
        return fArr;
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

    public boolean containsKey(float f) {
        return contains(f);
    }

    public boolean forEachKey(TFloatProcedure tFloatProcedure) {
        return forEach(tFloatProcedure);
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

    public boolean forEachEntry(TFloatByteProcedure tFloatByteProcedure) {
        byte[] bArr = this._states;
        float[] fArr = this._set;
        byte[] bArr2 = this._values;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                if (bArr[i] == 1 && !tFloatByteProcedure.execute(fArr[i], bArr2[i])) {
                    return false;
                }
                length = i;
            }
        }
        return true;
    }

    public boolean retainEntries(TFloatByteProcedure tFloatByteProcedure) {
        byte[] bArr = this._states;
        float[] fArr = this._set;
        byte[] bArr2 = this._values;
        boolean z = false;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                length--;
                if (length <= 0) {
                    break;
                }
                if (bArr[length] == 1 && !tFloatByteProcedure.execute(fArr[length], bArr2[length])) {
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

    public boolean increment(float f) {
        return adjustValue(f, (byte) 1);
    }

    public boolean adjustValue(float f, byte b) {
        int iIndex = index(f);
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
            put(objectInputStream.readFloat(), objectInputStream.readByte());
            i = i2;
        }
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        forEachEntry(new TFloatByteProcedure() { // from class: gnu.trove.TFloatByteHashMap.1
            @Override // gnu.trove.TFloatByteProcedure
            public boolean execute(float f, byte b) {
                if (sb.length() != 0) {
                    sb.append(',').append(StringUtil.SPACE);
                }
                sb.append(f);
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
