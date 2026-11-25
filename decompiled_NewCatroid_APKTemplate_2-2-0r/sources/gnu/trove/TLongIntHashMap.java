package gnu.trove;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/* JADX INFO: loaded from: classes11.dex */
public class TLongIntHashMap extends TLongHash {
    protected transient int[] _values;

    public TLongIntHashMap() {
    }

    public TLongIntHashMap(int i) {
        super(i);
    }

    public TLongIntHashMap(int i, float f) {
        super(i, f);
    }

    public TLongIntHashMap(TLongHashingStrategy tLongHashingStrategy) {
        super(tLongHashingStrategy);
    }

    public TLongIntHashMap(int i, TLongHashingStrategy tLongHashingStrategy) {
        super(i, tLongHashingStrategy);
    }

    public TLongIntHashMap(int i, float f, TLongHashingStrategy tLongHashingStrategy) {
        super(i, f, tLongHashingStrategy);
    }

    @Override // gnu.trove.TLongHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
    public Object clone() {
        TLongIntHashMap tLongIntHashMap = (TLongIntHashMap) super.clone();
        int[] iArr = this._values;
        tLongIntHashMap._values = iArr == null ? null : (int[]) iArr.clone();
        return tLongIntHashMap;
    }

    public TLongIntIterator iterator() {
        return new TLongIntIterator(this);
    }

    @Override // gnu.trove.TLongHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
    protected int setUp(int i) {
        int up = super.setUp(i);
        this._values = i == -1 ? null : new int[up];
        return up;
    }

    public int put(long j, int i) {
        int i2;
        boolean z;
        int iInsertionIndex = insertionIndex(j);
        if (iInsertionIndex < 0) {
            iInsertionIndex = (-iInsertionIndex) - 1;
            i2 = this._values[iInsertionIndex];
            z = false;
        } else {
            i2 = 0;
            z = true;
        }
        byte b = this._states[iInsertionIndex];
        this._set[iInsertionIndex] = j;
        this._states[iInsertionIndex] = 1;
        this._values[iInsertionIndex] = i;
        if (z) {
            postInsertHook(b == 0);
        }
        return i2;
    }

    @Override // gnu.trove.THash
    protected void rehash(int i) {
        int iCapacity = capacity();
        long[] jArr = this._set;
        int[] iArr = this._values;
        byte[] bArr = this._states;
        this._set = new long[i];
        this._values = new int[i];
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
                this._values[iInsertionIndex] = iArr[i2];
                this._states[iInsertionIndex] = 1;
            }
            iCapacity = i2;
        }
    }

    public int get(long j) {
        int iIndex = index(j);
        if (iIndex < 0) {
            return 0;
        }
        return this._values[iIndex];
    }

    @Override // gnu.trove.THash
    public void clear() {
        super.clear();
        long[] jArr = this._set;
        int[] iArr = this._values;
        if (iArr == null) {
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
            iArr[i] = 0;
            bArr[i] = 0;
            length = i;
        }
    }

    public int remove(long j) {
        int iIndex = index(j);
        if (iIndex < 0) {
            return 0;
        }
        int i = this._values[iIndex];
        removeAt(iIndex);
        return i;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TLongIntHashMap)) {
            return false;
        }
        TLongIntHashMap tLongIntHashMap = (TLongIntHashMap) obj;
        if (tLongIntHashMap.size() != size()) {
            return false;
        }
        return forEachEntry(new EqProcedure(tLongIntHashMap));
    }

    public int hashCode() {
        HashProcedure hashProcedure = new HashProcedure();
        forEachEntry(hashProcedure);
        return hashProcedure.getHashCode();
    }

    private final class HashProcedure implements TLongIntProcedure {
        private int h;

        HashProcedure() {
        }

        public int getHashCode() {
            return this.h;
        }

        @Override // gnu.trove.TLongIntProcedure
        public final boolean execute(long j, int i) {
            this.h += TLongIntHashMap.this._hashingStrategy.computeHashCode(j) ^ HashFunctions.hash(i);
            return true;
        }
    }

    private static final class EqProcedure implements TLongIntProcedure {
        private final TLongIntHashMap _otherMap;

        private static boolean eq(int i, int i2) {
            return i == i2;
        }

        EqProcedure(TLongIntHashMap tLongIntHashMap) {
            this._otherMap = tLongIntHashMap;
        }

        @Override // gnu.trove.TLongIntProcedure
        public final boolean execute(long j, int i) {
            return this._otherMap.index(j) >= 0 && eq(i, this._otherMap.get(j));
        }
    }

    @Override // gnu.trove.TLongHash, gnu.trove.TPrimitiveHash, gnu.trove.THash
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

    public boolean containsKey(long j) {
        return contains(j);
    }

    public boolean forEachKey(TLongProcedure tLongProcedure) {
        return forEach(tLongProcedure);
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

    public boolean forEachEntry(TLongIntProcedure tLongIntProcedure) {
        byte[] bArr = this._states;
        long[] jArr = this._set;
        int[] iArr = this._values;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                int i = length - 1;
                if (length <= 0) {
                    break;
                }
                if (bArr[i] == 1 && !tLongIntProcedure.execute(jArr[i], iArr[i])) {
                    return false;
                }
                length = i;
            }
        }
        return true;
    }

    public boolean retainEntries(TLongIntProcedure tLongIntProcedure) {
        byte[] bArr = this._states;
        long[] jArr = this._set;
        int[] iArr = this._values;
        boolean z = false;
        if (bArr != null) {
            int length = bArr.length;
            while (true) {
                length--;
                if (length <= 0) {
                    break;
                }
                if (bArr[length] == 1 && !tLongIntProcedure.execute(jArr[length], iArr[length])) {
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

    public boolean increment(long j) {
        return adjustValue(j, 1);
    }

    public boolean adjustValue(long j, int i) {
        int iIndex = index(j);
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
            put(objectInputStream.readLong(), objectInputStream.readInt());
            i = i2;
        }
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        forEachEntry(new TLongIntProcedure() { // from class: gnu.trove.TLongIntHashMap.1
            @Override // gnu.trove.TLongIntProcedure
            public boolean execute(long j, int i) {
                if (sb.length() != 0) {
                    sb.append(',').append(StringUtil.SPACE);
                }
                sb.append(j);
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
