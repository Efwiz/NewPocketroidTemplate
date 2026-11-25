package gnu.trove;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* JADX INFO: loaded from: classes11.dex */
public class TIntFloatHashMap extends TIntHash {
    protected transient float[] _values;

    public TIntFloatHashMap() {
    }

    public TIntFloatHashMap(int i) {
        super(i);
    }

    public TIntFloatHashMap(int i, float f) {
        super(i, f);
    }

    public TIntFloatHashMap(TIntHashingStrategy tIntHashingStrategy) {
        super(tIntHashingStrategy);
    }

    public TIntFloatHashMap(int i, TIntHashingStrategy tIntHashingStrategy) {
        super(i, tIntHashingStrategy);
    }

    public TIntFloatHashMap(int i, float f, TIntHashingStrategy tIntHashingStrategy) {
        super(i, f, tIntHashingStrategy);
    }

    @Override // gnu.trove.TIntHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
    public Object clone() {
        TIntFloatHashMap tIntFloatHashMap = (TIntFloatHashMap) super.clone();
        float[] fArr = this._values;
        tIntFloatHashMap._values = fArr == null ? null : (float[]) fArr.clone();
        return tIntFloatHashMap;
    }

    public TIntFloatIterator iterator() {
        return new TIntFloatIterator(this);
    }

    @Override // gnu.trove.TIntHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
    protected int setUp(int i) {
        int up = super.setUp(i);
        this._values = i == -1 ? null : new float[up];
        return up;
    }

    public float put(int i, float f) {
        float f2;
        boolean z;
        int iInsertionIndex = insertionIndex(i);
        if (iInsertionIndex < 0) {
            iInsertionIndex = (-iInsertionIndex) - 1;
            f2 = this._values[iInsertionIndex];
            z = false;
        } else {
            f2 = 0.0f;
            z = true;
        }
        byte b = this._states[iInsertionIndex];
        this._set[iInsertionIndex] = i;
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
        int[] iArr = this._set;
        float[] fArr = this._values;
        byte[] bArr = this._states;
        this._set = new int[i];
        this._values = new float[i];
        this._states = new byte[i];
        while (true) {
            int i2 = iCapacity - 1;
            if (iCapacity <= 0) {
                return;
            }
            if (bArr[i2] == 1) {
                int i3 = iArr[i2];
                int iInsertionIndex = insertionIndex(i3);
                this._set[iInsertionIndex] = i3;
                this._values[iInsertionIndex] = fArr[i2];
                this._states[iInsertionIndex] = 1;
            }
            iCapacity = i2;
        }
    }

    public float get(int i) {
        int iIndex = index(i);
        if (iIndex < 0) {
            return 0.0f;
        }
        return this._values[iIndex];
    }

    @Override // gnu.trove.THash
    public void clear() {
        super.clear();
        int[] iArr = this._set;
        float[] fArr = this._values;
        if (fArr == null) {
            return;
        }
        byte[] bArr = this._states;
        int length = iArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return;
            }
            iArr[i] = 0;
            fArr[i] = 0.0f;
            bArr[i] = 0;
            length = i;
        }
    }

    public float remove(int i) {
        int iIndex = index(i);
        if (iIndex < 0) {
            return 0.0f;
        }
        float f = this._values[iIndex];
        removeAt(iIndex);
        return f;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TIntFloatHashMap)) {
            return false;
        }
        TIntFloatHashMap tIntFloatHashMap = (TIntFloatHashMap) obj;
        if (tIntFloatHashMap.size() != size()) {
            return false;
        }
        return forEachEntry(new EqProcedure(tIntFloatHashMap));
    }

    public int hashCode() {
        HashProcedure hashProcedure = new HashProcedure();
        forEachEntry(hashProcedure);
        return hashProcedure.getHashCode();
    }

    private final class HashProcedure implements TIntFloatProcedure {
        private int h;

        HashProcedure() {
        }

        public int getHashCode() {
            return this.h;
        }

        @Override // gnu.trove.TIntFloatProcedure
        public final boolean execute(int i, float f) {
            this.h += TIntFloatHashMap.this._hashingStrategy.computeHashCode(i) ^ HashFunctions.hash(f);
            return true;
        }
    }

    private static final class EqProcedure implements TIntFloatProcedure {
        private final TIntFloatHashMap _otherMap;

        private static boolean eq(float f, float f2) {
            return f == f2;
        }

        EqProcedure(TIntFloatHashMap tIntFloatHashMap) {
            this._otherMap = tIntFloatHashMap;
        }

        @Override // gnu.trove.TIntFloatProcedure
        public final boolean execute(int i, float f) {
            return this._otherMap.index(i) >= 0 && eq(f, this._otherMap.get(i));
        }
    }

    @Override // gnu.trove.TIntHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
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

    public int[] keys() {
        int[] iArr = new int[size()];
        int[] iArr2 = this._set;
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

    public boolean containsKey(int i) {
        return contains(i);
    }

    public boolean forEachKey(TIntProcedure tIntProcedure) {
        return forEach(tIntProcedure);
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

    public boolean forEachEntry(TIntFloatProcedure tIntFloatProcedure) {
        byte[] bArr = this._states;
        int[] iArr = this._set;
        float[] fArr = this._values;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                if (bArr[i] == 1 && !tIntFloatProcedure.execute(iArr[i], fArr[i])) {
                    return false;
                }
                length = i;
            }
        }
        return true;
    }

    public boolean retainEntries(TIntFloatProcedure tIntFloatProcedure) {
        byte[] bArr = this._states;
        int[] iArr = this._set;
        float[] fArr = this._values;
        boolean z = false;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                length--;
                if (length <= 0) {
                    break;
                }
                if (bArr[length] == 1 && !tIntFloatProcedure.execute(iArr[length], fArr[length])) {
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

    public boolean increment(int i) {
        return adjustValue(i, 1.0f);
    }

    public boolean adjustValue(int i, float f) {
        int iIndex = index(i);
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
            put(objectInputStream.readInt(), objectInputStream.readFloat());
            i = i2;
        }
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        forEachEntry(new TIntFloatProcedure() { // from class: gnu.trove.TIntFloatHashMap.1
            @Override // gnu.trove.TIntFloatProcedure
            public boolean execute(int i, float f) {
                if (sb.length() != 0) {
                    sb.append(',').append(StringUtil.SPACE);
                }
                sb.append(i);
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
