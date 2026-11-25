package gnu.trove;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* JADX INFO: loaded from: classes11.dex */
public class TIntDoubleHashMap extends TIntHash {
    protected transient double[] _values;

    public TIntDoubleHashMap() {
    }

    public TIntDoubleHashMap(int i) {
        super(i);
    }

    public TIntDoubleHashMap(int i, float f) {
        super(i, f);
    }

    public TIntDoubleHashMap(TIntHashingStrategy tIntHashingStrategy) {
        super(tIntHashingStrategy);
    }

    public TIntDoubleHashMap(int i, TIntHashingStrategy tIntHashingStrategy) {
        super(i, tIntHashingStrategy);
    }

    public TIntDoubleHashMap(int i, float f, TIntHashingStrategy tIntHashingStrategy) {
        super(i, f, tIntHashingStrategy);
    }

    @Override // gnu.trove.TIntHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
    public Object clone() {
        TIntDoubleHashMap tIntDoubleHashMap = (TIntDoubleHashMap) super.clone();
        double[] dArr = this._values;
        tIntDoubleHashMap._values = dArr == null ? null : (double[]) dArr.clone();
        return tIntDoubleHashMap;
    }

    public TIntDoubleIterator iterator() {
        return new TIntDoubleIterator(this);
    }

    @Override // gnu.trove.TIntHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
    protected int setUp(int i) {
        int up = super.setUp(i);
        this._values = i == -1 ? null : new double[up];
        return up;
    }

    public double put(int i, double d) {
        double d2;
        boolean z;
        int iInsertionIndex = insertionIndex(i);
        if (iInsertionIndex < 0) {
            iInsertionIndex = (-iInsertionIndex) - 1;
            d2 = this._values[iInsertionIndex];
            z = false;
        } else {
            d2 = 0.0d;
            z = true;
        }
        byte b = this._states[iInsertionIndex];
        this._set[iInsertionIndex] = i;
        this._states[iInsertionIndex] = 1;
        this._values[iInsertionIndex] = d;
        if (z) {
            postInsertHook(b == 0);
        }
        return d2;
    }

    @Override // gnu.trove.THash
    protected void rehash(int i) {
        int iCapacity = capacity();
        int[] iArr = this._set;
        double[] dArr = this._values;
        byte[] bArr = this._states;
        this._set = new int[i];
        this._values = new double[i];
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
                this._values[iInsertionIndex] = dArr[i2];
                this._states[iInsertionIndex] = 1;
            }
            iCapacity = i2;
        }
    }

    public double get(int i) {
        int iIndex = index(i);
        if (iIndex < 0) {
            return 0.0d;
        }
        return this._values[iIndex];
    }

    @Override // gnu.trove.THash
    public void clear() {
        super.clear();
        int[] iArr = this._set;
        double[] dArr = this._values;
        if (dArr == null) {
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
            dArr[i] = 0.0d;
            bArr[i] = 0;
            length = i;
        }
    }

    public double remove(int i) {
        int iIndex = index(i);
        if (iIndex < 0) {
            return 0.0d;
        }
        double d = this._values[iIndex];
        removeAt(iIndex);
        return d;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TIntDoubleHashMap)) {
            return false;
        }
        TIntDoubleHashMap tIntDoubleHashMap = (TIntDoubleHashMap) obj;
        if (tIntDoubleHashMap.size() != size()) {
            return false;
        }
        return forEachEntry(new EqProcedure(tIntDoubleHashMap));
    }

    public int hashCode() {
        HashProcedure hashProcedure = new HashProcedure();
        forEachEntry(hashProcedure);
        return hashProcedure.getHashCode();
    }

    private final class HashProcedure implements TIntDoubleProcedure {
        private int h;

        HashProcedure() {
        }

        public int getHashCode() {
            return this.h;
        }

        @Override // gnu.trove.TIntDoubleProcedure
        public final boolean execute(int i, double d) {
            this.h += TIntDoubleHashMap.this._hashingStrategy.computeHashCode(i) ^ HashFunctions.hash(d);
            return true;
        }
    }

    private static final class EqProcedure implements TIntDoubleProcedure {
        private final TIntDoubleHashMap _otherMap;

        private static boolean eq(double d, double d2) {
            return d == d2;
        }

        EqProcedure(TIntDoubleHashMap tIntDoubleHashMap) {
            this._otherMap = tIntDoubleHashMap;
        }

        @Override // gnu.trove.TIntDoubleProcedure
        public final boolean execute(int i, double d) {
            return this._otherMap.index(i) >= 0 && eq(d, this._otherMap.get(i));
        }
    }

    @Override // gnu.trove.TIntHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
    protected void removeAt(int i) {
        this._values[i] = 0.0d;
        super.removeAt(i);
    }

    public double[] getValues() {
        double[] dArr = new double[size()];
        double[] dArr2 = this._values;
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
                    dArr[i] = dArr2[i2];
                    i++;
                }
                length = i2;
            }
        }
        return dArr;
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

    public boolean containsValue(double d) {
        byte[] bArr = this._states;
        double[] dArr = this._values;
        if (bArr == null) {
            return false;
        }
        int length = bArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return false;
            }
            if (bArr[i] == 1 && d == dArr[i]) {
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

    public boolean forEachValue(TDoubleProcedure tDoubleProcedure) {
        byte[] bArr = this._states;
        double[] dArr = this._values;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                if (bArr[i] == 1 && !tDoubleProcedure.execute(dArr[i])) {
                    return false;
                }
                length = i;
            }
        }
        return true;
    }

    public boolean forEachEntry(TIntDoubleProcedure tIntDoubleProcedure) {
        byte[] bArr = this._states;
        int[] iArr = this._set;
        double[] dArr = this._values;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                if (bArr[i] == 1 && !tIntDoubleProcedure.execute(iArr[i], dArr[i])) {
                    return false;
                }
                length = i;
            }
        }
        return true;
    }

    public boolean retainEntries(TIntDoubleProcedure tIntDoubleProcedure) {
        byte[] bArr = this._states;
        int[] iArr = this._set;
        double[] dArr = this._values;
        boolean z = false;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                length--;
                if (length <= 0) {
                    break;
                }
                if (bArr[length] == 1 && !tIntDoubleProcedure.execute(iArr[length], dArr[length])) {
                    removeAt(length);
                    z = true;
                }
            }
        }
        return z;
    }

    public void transformValues(TDoubleFunction tDoubleFunction) {
        byte[] bArr = this._states;
        double[] dArr = this._values;
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
                dArr[i] = tDoubleFunction.execute(dArr[i]);
            }
            length = i;
        }
    }

    public boolean increment(int i) {
        return adjustValue(i, 1.0d);
    }

    public boolean adjustValue(int i, double d) {
        int iIndex = index(i);
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

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        int i = objectInputStream.readInt();
        setUp(i);
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return;
            }
            put(objectInputStream.readInt(), objectInputStream.readDouble());
            i = i2;
        }
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        forEachEntry(new TIntDoubleProcedure() { // from class: gnu.trove.TIntDoubleHashMap.1
            @Override // gnu.trove.TIntDoubleProcedure
            public boolean execute(int i, double d) {
                if (sb.length() != 0) {
                    sb.append(',').append(StringUtil.SPACE);
                }
                sb.append(i);
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
