package gnu.trove;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* JADX INFO: loaded from: classes11.dex */
public class TIntLongHashMap extends TIntHash {
    protected transient long[] _values;

    public TIntLongHashMap() {
    }

    public TIntLongHashMap(int i) {
        super(i);
    }

    public TIntLongHashMap(int i, float f) {
        super(i, f);
    }

    public TIntLongHashMap(TIntHashingStrategy tIntHashingStrategy) {
        super(tIntHashingStrategy);
    }

    public TIntLongHashMap(int i, TIntHashingStrategy tIntHashingStrategy) {
        super(i, tIntHashingStrategy);
    }

    public TIntLongHashMap(int i, float f, TIntHashingStrategy tIntHashingStrategy) {
        super(i, f, tIntHashingStrategy);
    }

    @Override // gnu.trove.TIntHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
    public Object clone() {
        TIntLongHashMap tIntLongHashMap = (TIntLongHashMap) super.clone();
        long[] jArr = this._values;
        tIntLongHashMap._values = jArr == null ? null : (long[]) jArr.clone();
        return tIntLongHashMap;
    }

    public TIntLongIterator iterator() {
        return new TIntLongIterator(this);
    }

    @Override // gnu.trove.TIntHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
    protected int setUp(int i) {
        int up = super.setUp(i);
        this._values = i == -1 ? null : new long[up];
        return up;
    }

    public long put(int i, long j) {
        long j2;
        boolean z;
        int iInsertionIndex = insertionIndex(i);
        if (iInsertionIndex < 0) {
            iInsertionIndex = (-iInsertionIndex) - 1;
            j2 = this._values[iInsertionIndex];
            z = false;
        } else {
            j2 = 0;
            z = true;
        }
        byte b = this._states[iInsertionIndex];
        this._set[iInsertionIndex] = i;
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
        int[] iArr = this._set;
        long[] jArr = this._values;
        byte[] bArr = this._states;
        this._set = new int[i];
        this._values = new long[i];
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
                this._values[iInsertionIndex] = jArr[i2];
                this._states[iInsertionIndex] = 1;
            }
            iCapacity = i2;
        }
    }

    public long get(int i) {
        int iIndex = index(i);
        if (iIndex < 0) {
            return 0L;
        }
        return this._values[iIndex];
    }

    @Override // gnu.trove.THash
    public void clear() {
        super.clear();
        int[] iArr = this._set;
        long[] jArr = this._values;
        if (jArr == null) {
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
            jArr[i] = 0;
            bArr[i] = 0;
            length = i;
        }
    }

    public long remove(int i) {
        int iIndex = index(i);
        if (iIndex < 0) {
            return 0L;
        }
        long j = this._values[iIndex];
        removeAt(iIndex);
        return j;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TIntLongHashMap)) {
            return false;
        }
        TIntLongHashMap tIntLongHashMap = (TIntLongHashMap) obj;
        if (tIntLongHashMap.size() != size()) {
            return false;
        }
        return forEachEntry(new EqProcedure(tIntLongHashMap));
    }

    public int hashCode() {
        HashProcedure hashProcedure = new HashProcedure();
        forEachEntry(hashProcedure);
        return hashProcedure.getHashCode();
    }

    private final class HashProcedure implements TIntLongProcedure {
        private int h;

        HashProcedure() {
        }

        public int getHashCode() {
            return this.h;
        }

        @Override // gnu.trove.TIntLongProcedure
        public final boolean execute(int i, long j) {
            this.h += TIntLongHashMap.this._hashingStrategy.computeHashCode(i) ^ HashFunctions.hash(j);
            return true;
        }
    }

    private static final class EqProcedure implements TIntLongProcedure {
        private final TIntLongHashMap _otherMap;

        private static boolean eq(long j, long j2) {
            return j == j2;
        }

        EqProcedure(TIntLongHashMap tIntLongHashMap) {
            this._otherMap = tIntLongHashMap;
        }

        @Override // gnu.trove.TIntLongProcedure
        public final boolean execute(int i, long j) {
            return this._otherMap.index(i) >= 0 && eq(j, this._otherMap.get(i));
        }
    }

    @Override // gnu.trove.TIntHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
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

    public boolean containsKey(int i) {
        return contains(i);
    }

    public boolean forEachKey(TIntProcedure tIntProcedure) {
        return forEach(tIntProcedure);
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

    public boolean forEachEntry(TIntLongProcedure tIntLongProcedure) {
        byte[] bArr = this._states;
        int[] iArr = this._set;
        long[] jArr = this._values;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                if (bArr[i] == 1 && !tIntLongProcedure.execute(iArr[i], jArr[i])) {
                    return false;
                }
                length = i;
            }
        }
        return true;
    }

    public boolean retainEntries(TIntLongProcedure tIntLongProcedure) {
        byte[] bArr = this._states;
        int[] iArr = this._set;
        long[] jArr = this._values;
        boolean z = false;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                length--;
                if (length <= 0) {
                    break;
                }
                if (bArr[length] == 1 && !tIntLongProcedure.execute(iArr[length], jArr[length])) {
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

    public boolean increment(int i) {
        return adjustValue(i, 1L);
    }

    public boolean adjustValue(int i, long j) {
        int iIndex = index(i);
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
            put(objectInputStream.readInt(), objectInputStream.readLong());
            i = i2;
        }
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        forEachEntry(new TIntLongProcedure() { // from class: gnu.trove.TIntLongHashMap.1
            @Override // gnu.trove.TIntLongProcedure
            public boolean execute(int i, long j) {
                if (sb.length() != 0) {
                    sb.append(',').append(StringUtil.SPACE);
                }
                sb.append(i);
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
