package gnu.trove;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* JADX INFO: loaded from: classes11.dex */
public class TDoubleLongHashMap extends TDoubleHash {
    protected transient long[] _values;

    public TDoubleLongHashMap() {
    }

    public TDoubleLongHashMap(int i) {
        super(i);
    }

    public TDoubleLongHashMap(int i, float f) {
        super(i, f);
    }

    public TDoubleLongHashMap(TDoubleHashingStrategy tDoubleHashingStrategy) {
        super(tDoubleHashingStrategy);
    }

    public TDoubleLongHashMap(int i, TDoubleHashingStrategy tDoubleHashingStrategy) {
        super(i, tDoubleHashingStrategy);
    }

    public TDoubleLongHashMap(int i, float f, TDoubleHashingStrategy tDoubleHashingStrategy) {
        super(i, f, tDoubleHashingStrategy);
    }

    @Override // gnu.trove.TDoubleHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
    public Object clone() {
        TDoubleLongHashMap tDoubleLongHashMap = (TDoubleLongHashMap) super.clone();
        long[] jArr = this._values;
        tDoubleLongHashMap._values = jArr == null ? null : (long[]) jArr.clone();
        return tDoubleLongHashMap;
    }

    public TDoubleLongIterator iterator() {
        return new TDoubleLongIterator(this);
    }

    @Override // gnu.trove.TDoubleHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
    protected int setUp(int i) {
        int up = super.setUp(i);
        this._values = i == -1 ? null : new long[up];
        return up;
    }

    public long put(double d, long j) {
        long j2;
        boolean z;
        int iInsertionIndex = insertionIndex(d);
        if (iInsertionIndex < 0) {
            iInsertionIndex = (-iInsertionIndex) - 1;
            j2 = this._values[iInsertionIndex];
            z = false;
        } else {
            j2 = 0;
            z = true;
        }
        byte b = this._states[iInsertionIndex];
        this._set[iInsertionIndex] = d;
        this._states[iInsertionIndex] = 1;
        this._values[iInsertionIndex] = j;
        if (z) {
            postInsertHook(b == 0);
        }
        return j2;
    }

    @Override // gnu.trove.THash
    protected void rehash(int i) {
        int iCapacity = capacity();
        double[] dArr = this._set;
        long[] jArr = this._values;
        byte[] bArr = this._states;
        this._set = new double[i];
        this._values = new long[i];
        this._states = new byte[i];
        while (true) {
            int i2 = iCapacity - 1;
            if (iCapacity <= 0) {
                return;
            }
            if (bArr[i2] == 1) {
                double d = dArr[i2];
                int iInsertionIndex = insertionIndex(d);
                this._set[iInsertionIndex] = d;
                this._values[iInsertionIndex] = jArr[i2];
                this._states[iInsertionIndex] = 1;
            }
            iCapacity = i2;
        }
    }

    public long get(double d) {
        int iIndex = index(d);
        if (iIndex < 0) {
            return 0L;
        }
        return this._values[iIndex];
    }

    @Override // gnu.trove.THash
    public void clear() {
        super.clear();
        double[] dArr = this._set;
        long[] jArr = this._values;
        if (jArr == null) {
            return;
        }
        byte[] bArr = this._states;
        int length = dArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return;
            }
            dArr[i] = 0.0d;
            jArr[i] = 0;
            bArr[i] = 0;
            length = i;
        }
    }

    public long remove(double d) {
        int iIndex = index(d);
        if (iIndex < 0) {
            return 0L;
        }
        long j = this._values[iIndex];
        removeAt(iIndex);
        return j;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TDoubleLongHashMap)) {
            return false;
        }
        TDoubleLongHashMap tDoubleLongHashMap = (TDoubleLongHashMap) obj;
        if (tDoubleLongHashMap.size() != size()) {
            return false;
        }
        return forEachEntry(new EqProcedure(tDoubleLongHashMap));
    }

    public int hashCode() {
        HashProcedure hashProcedure = new HashProcedure();
        forEachEntry(hashProcedure);
        return hashProcedure.getHashCode();
    }

    private final class HashProcedure implements TDoubleLongProcedure {
        private int h;

        HashProcedure() {
        }

        public int getHashCode() {
            return this.h;
        }

        @Override // gnu.trove.TDoubleLongProcedure
        public final boolean execute(double d, long j) {
            this.h += TDoubleLongHashMap.this._hashingStrategy.computeHashCode(d) ^ HashFunctions.hash(j);
            return true;
        }
    }

    private static final class EqProcedure implements TDoubleLongProcedure {
        private final TDoubleLongHashMap _otherMap;

        private static boolean eq(long j, long j2) {
            return j == j2;
        }

        EqProcedure(TDoubleLongHashMap tDoubleLongHashMap) {
            this._otherMap = tDoubleLongHashMap;
        }

        @Override // gnu.trove.TDoubleLongProcedure
        public final boolean execute(double d, long j) {
            return this._otherMap.index(d) >= 0 && eq(j, this._otherMap.get(d));
        }
    }

    @Override // gnu.trove.TDoubleHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
    protected void removeAt(int i) {
        this._values[i] = 0;
        super.removeAt(i);
    }

    public long[] getValues() {
        long[] jArr = new long[size()];
        long[] jArr2 = this._values;
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

    public double[] keys() {
        double[] dArr = new double[size()];
        double[] dArr2 = this._set;
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

    public boolean containsValue(long j) {
        byte[] bArr = this._states;
        long[] jArr = this._values;
        if (bArr == null) {
            return false;
        }
        int length = bArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return false;
            }
            if (bArr[i] == 1 && j == jArr[i]) {
                return true;
            }
            length = i;
        }
    }

    public boolean containsKey(double d) {
        return contains(d);
    }

    public boolean forEachKey(TDoubleProcedure tDoubleProcedure) {
        return forEach(tDoubleProcedure);
    }

    public boolean forEachValue(TLongProcedure tLongProcedure) {
        byte[] bArr = this._states;
        long[] jArr = this._values;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                if (bArr[i] == 1 && !tLongProcedure.execute(jArr[i])) {
                    return false;
                }
                length = i;
            }
        }
        return true;
    }

    public boolean forEachEntry(TDoubleLongProcedure tDoubleLongProcedure) {
        byte[] bArr = this._states;
        double[] dArr = this._set;
        long[] jArr = this._values;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                if (bArr[i] == 1 && !tDoubleLongProcedure.execute(dArr[i], jArr[i])) {
                    return false;
                }
                length = i;
            }
        }
        return true;
    }

    public boolean retainEntries(TDoubleLongProcedure tDoubleLongProcedure) {
        byte[] bArr = this._states;
        double[] dArr = this._set;
        long[] jArr = this._values;
        boolean z = false;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                length--;
                if (length <= 0) {
                    break;
                }
                if (bArr[length] == 1 && !tDoubleLongProcedure.execute(dArr[length], jArr[length])) {
                    removeAt(length);
                    z = true;
                }
            }
        }
        return z;
    }

    public void transformValues(TLongFunction tLongFunction) {
        byte[] bArr = this._states;
        long[] jArr = this._values;
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
                jArr[i] = tLongFunction.execute(jArr[i]);
            }
            length = i;
        }
    }

    public boolean increment(double d) {
        return adjustValue(d, 1L);
    }

    public boolean adjustValue(double d, long j) {
        int iIndex = index(d);
        if (iIndex < 0) {
            return false;
        }
        long[] jArr = this._values;
        jArr[iIndex] = jArr[iIndex] + j;
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
            put(objectInputStream.readDouble(), objectInputStream.readLong());
            i = i2;
        }
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        forEachEntry(new TDoubleLongProcedure() { // from class: gnu.trove.TDoubleLongHashMap.1
            @Override // gnu.trove.TDoubleLongProcedure
            public boolean execute(double d, long j) {
                if (sb.length() != 0) {
                    sb.append(',').append(StringUtil.SPACE);
                }
                sb.append(d);
                sb.append('=');
                sb.append(j);
                return true;
            }
        });
        sb.append('}');
        sb.insert(0, '{');
        return sb.toString();
    }
}
