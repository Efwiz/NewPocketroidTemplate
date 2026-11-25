package gnu.trove;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* JADX INFO: loaded from: classes11.dex */
public class TLongFloatHashMap extends TLongHash {
    protected transient float[] _values;

    public TLongFloatHashMap() {
    }

    public TLongFloatHashMap(int i) {
        super(i);
    }

    public TLongFloatHashMap(int i, float f) {
        super(i, f);
    }

    public TLongFloatHashMap(TLongHashingStrategy tLongHashingStrategy) {
        super(tLongHashingStrategy);
    }

    public TLongFloatHashMap(int i, TLongHashingStrategy tLongHashingStrategy) {
        super(i, tLongHashingStrategy);
    }

    public TLongFloatHashMap(int i, float f, TLongHashingStrategy tLongHashingStrategy) {
        super(i, f, tLongHashingStrategy);
    }

    @Override // gnu.trove.TLongHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
    public Object clone() {
        TLongFloatHashMap tLongFloatHashMap = (TLongFloatHashMap) super.clone();
        float[] fArr = this._values;
        tLongFloatHashMap._values = fArr == null ? null : (float[]) fArr.clone();
        return tLongFloatHashMap;
    }

    public TLongFloatIterator iterator() {
        return new TLongFloatIterator(this);
    }

    @Override // gnu.trove.TLongHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
    protected int setUp(int i) {
        int up = super.setUp(i);
        this._values = i == -1 ? null : new float[up];
        return up;
    }

    public float put(long j, float f) {
        float f2;
        boolean z;
        int iInsertionIndex = insertionIndex(j);
        if (iInsertionIndex < 0) {
            iInsertionIndex = (-iInsertionIndex) - 1;
            f2 = this._values[iInsertionIndex];
            z = false;
        } else {
            f2 = 0.0f;
            z = true;
        }
        byte b = this._states[iInsertionIndex];
        this._set[iInsertionIndex] = j;
        this._states[iInsertionIndex] = 1;
        this._values[iInsertionIndex] = f;
        if (z) {
            postInsertHook(b == 0);
        }
        return f2;
    }

    @Override // gnu.trove.THash
    protected void rehash(int i) {
        int iCapacity = capacity();
        long[] jArr = this._set;
        float[] fArr = this._values;
        byte[] bArr = this._states;
        this._set = new long[i];
        this._values = new float[i];
        this._states = new byte[i];
        while (true) {
            int i2 = iCapacity - 1;
            if (iCapacity <= 0) {
                return;
            }
            if (bArr[i2] == 1) {
                long j = jArr[i2];
                int iInsertionIndex = insertionIndex(j);
                this._set[iInsertionIndex] = j;
                this._values[iInsertionIndex] = fArr[i2];
                this._states[iInsertionIndex] = 1;
            }
            iCapacity = i2;
        }
    }

    public float get(long j) {
        int iIndex = index(j);
        if (iIndex < 0) {
            return 0.0f;
        }
        return this._values[iIndex];
    }

    @Override // gnu.trove.THash
    public void clear() {
        super.clear();
        long[] jArr = this._set;
        float[] fArr = this._values;
        if (fArr == null) {
            return;
        }
        byte[] bArr = this._states;
        int length = jArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return;
            }
            jArr[i] = 0;
            fArr[i] = 0.0f;
            bArr[i] = 0;
            length = i;
        }
    }

    public float remove(long j) {
        int iIndex = index(j);
        if (iIndex < 0) {
            return 0.0f;
        }
        float f = this._values[iIndex];
        removeAt(iIndex);
        return f;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TLongFloatHashMap)) {
            return false;
        }
        TLongFloatHashMap tLongFloatHashMap = (TLongFloatHashMap) obj;
        if (tLongFloatHashMap.size() != size()) {
            return false;
        }
        return forEachEntry(new EqProcedure(tLongFloatHashMap));
    }

    public int hashCode() {
        HashProcedure hashProcedure = new HashProcedure();
        forEachEntry(hashProcedure);
        return hashProcedure.getHashCode();
    }

    private final class HashProcedure implements TLongFloatProcedure {
        private int h;

        HashProcedure() {
        }

        public int getHashCode() {
            return this.h;
        }

        @Override // gnu.trove.TLongFloatProcedure
        public final boolean execute(long j, float f) {
            this.h += TLongFloatHashMap.this._hashingStrategy.computeHashCode(j) ^ HashFunctions.hash(f);
            return true;
        }
    }

    private static final class EqProcedure implements TLongFloatProcedure {
        private final TLongFloatHashMap _otherMap;

        private static boolean eq(float f, float f2) {
            return f == f2;
        }

        EqProcedure(TLongFloatHashMap tLongFloatHashMap) {
            this._otherMap = tLongFloatHashMap;
        }

        @Override // gnu.trove.TLongFloatProcedure
        public final boolean execute(long j, float f) {
            return this._otherMap.index(j) >= 0 && eq(f, this._otherMap.get(j));
        }
    }

    @Override // gnu.trove.TLongHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
    protected void removeAt(int i) {
        this._values[i] = 0.0f;
        super.removeAt(i);
    }

    public float[] getValues() {
        float[] fArr = new float[size()];
        float[] fArr2 = this._values;
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

    public boolean containsValue(float f) {
        byte[] bArr = this._states;
        float[] fArr = this._values;
        if (bArr == null) {
            return false;
        }
        int length = bArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return false;
            }
            if (bArr[i] == 1 && f == fArr[i]) {
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

    public boolean forEachValue(TFloatProcedure tFloatProcedure) {
        byte[] bArr = this._states;
        float[] fArr = this._values;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                if (bArr[i] == 1 && !tFloatProcedure.execute(fArr[i])) {
                    return false;
                }
                length = i;
            }
        }
        return true;
    }

    public boolean forEachEntry(TLongFloatProcedure tLongFloatProcedure) {
        byte[] bArr = this._states;
        long[] jArr = this._set;
        float[] fArr = this._values;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                if (bArr[i] == 1 && !tLongFloatProcedure.execute(jArr[i], fArr[i])) {
                    return false;
                }
                length = i;
            }
        }
        return true;
    }

    public boolean retainEntries(TLongFloatProcedure tLongFloatProcedure) {
        byte[] bArr = this._states;
        long[] jArr = this._set;
        float[] fArr = this._values;
        boolean z = false;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                length--;
                if (length <= 0) {
                    break;
                }
                if (bArr[length] == 1 && !tLongFloatProcedure.execute(jArr[length], fArr[length])) {
                    removeAt(length);
                    z = true;
                }
            }
        }
        return z;
    }

    public void transformValues(TFloatFunction tFloatFunction) {
        byte[] bArr = this._states;
        float[] fArr = this._values;
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
                fArr[i] = tFloatFunction.execute(fArr[i]);
            }
            length = i;
        }
    }

    public boolean increment(long j) {
        return adjustValue(j, 1.0f);
    }

    public boolean adjustValue(long j, float f) {
        int iIndex = index(j);
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

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        int i = objectInputStream.readInt();
        setUp(i);
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return;
            }
            put(objectInputStream.readLong(), objectInputStream.readFloat());
            i = i2;
        }
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        forEachEntry(new TLongFloatProcedure() { // from class: gnu.trove.TLongFloatHashMap.1
            @Override // gnu.trove.TLongFloatProcedure
            public boolean execute(long j, float f) {
                if (sb.length() != 0) {
                    sb.append(',').append(StringUtil.SPACE);
                }
                sb.append(j);
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
