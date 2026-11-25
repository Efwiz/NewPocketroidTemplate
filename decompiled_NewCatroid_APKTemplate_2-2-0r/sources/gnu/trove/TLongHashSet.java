package gnu.trove;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/* JADX INFO: loaded from: classes11.dex */
public class TLongHashSet extends TLongHash {
    public TLongHashSet() {
    }

    public TLongHashSet(int i) {
        super(i);
    }

    public TLongHashSet(int i, float f) {
        super(i, f);
    }

    public TLongHashSet(long[] jArr) {
        this(jArr.length);
        addAll(jArr);
    }

    public TLongHashSet(TLongHashingStrategy tLongHashingStrategy) {
        super(tLongHashingStrategy);
    }

    public TLongHashSet(int i, TLongHashingStrategy tLongHashingStrategy) {
        super(i, tLongHashingStrategy);
    }

    public TLongHashSet(int i, float f, TLongHashingStrategy tLongHashingStrategy) {
        super(i, f, tLongHashingStrategy);
    }

    public TLongHashSet(long[] jArr, TLongHashingStrategy tLongHashingStrategy) {
        this(jArr.length, tLongHashingStrategy);
        addAll(jArr);
    }

    public TLongIterator iterator() {
        return new TLongIterator(this);
    }

    public boolean add(long j) {
        int iInsertionIndex = insertionIndex(j);
        if (iInsertionIndex < 0) {
            return false;
        }
        byte b = this._states[iInsertionIndex];
        this._set[iInsertionIndex] = j;
        this._states[iInsertionIndex] = 1;
        postInsertHook(b == 0);
        return true;
    }

    @Override // gnu.trove.THash
    protected void rehash(int i) {
        int iCapacity = capacity();
        long[] jArr = this._set;
        byte[] bArr = this._states;
        this._set = new long[i];
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
                this._states[iInsertionIndex] = 1;
            }
            iCapacity = i2;
        }
    }

    public long[] toArray() {
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

    @Override // gnu.trove.THash
    public void clear() {
        super.clear();
        long[] jArr = this._set;
        byte[] bArr = this._states;
        if (bArr == null) {
            return;
        }
        int length = jArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return;
            }
            jArr[i] = 0;
            bArr[i] = 0;
            length = i;
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TLongHashSet)) {
            return false;
        }
        final TLongHashSet tLongHashSet = (TLongHashSet) obj;
        if (tLongHashSet.size() != size()) {
            return false;
        }
        return forEach(new TLongProcedure() { // from class: gnu.trove.TLongHashSet.1
            @Override // gnu.trove.TLongProcedure
            public final boolean execute(long j) {
                return tLongHashSet.contains(j);
            }
        });
    }

    public int hashCode() {
        HashProcedure hashProcedure = new HashProcedure();
        forEach(hashProcedure);
        return hashProcedure.getHashCode();
    }

    private final class HashProcedure implements TLongProcedure {
        private int h;

        HashProcedure() {
        }

        public int getHashCode() {
            return this.h;
        }

        @Override // gnu.trove.TLongProcedure
        public final boolean execute(long j) {
            this.h += TLongHashSet.this._hashingStrategy.computeHashCode(j);
            return true;
        }
    }

    public boolean remove(long j) {
        int iIndex = index(j);
        if (iIndex < 0) {
            return false;
        }
        removeAt(iIndex);
        return true;
    }

    public boolean containsAll(long[] jArr) {
        int length = jArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return true;
            }
            if (!contains(jArr[i])) {
                return false;
            }
            length = i;
        }
    }

    public boolean addAll(long[] jArr) {
        int length = jArr.length;
        boolean z = false;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return z;
            }
            if (add(jArr[i])) {
                z = true;
            }
            length = i;
        }
    }

    public boolean removeAll(long[] jArr) {
        int length = jArr.length;
        boolean z = false;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return z;
            }
            if (remove(jArr[i])) {
                z = true;
            }
            length = i;
        }
    }

    public boolean retainAll(long[] jArr) {
        Arrays.sort(jArr);
        long[] jArr2 = this._set;
        byte[] bArr = this._states;
        boolean z = false;
        if (jArr2 != null) {
            int length = jArr2.length;
            while (true) {
                length--;
                if (length <= 0) {
                    break;
                }
                if (bArr[length] == 1 && Arrays.binarySearch(jArr, jArr2[length]) < 0) {
                    remove(jArr2[length]);
                    z = true;
                }
            }
        }
        return z;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this._size);
        SerializationProcedure serializationProcedure = new SerializationProcedure(objectOutputStream);
        if (!forEach(serializationProcedure)) {
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
            add(objectInputStream.readLong());
            i = i2;
        }
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        forEach(new TLongProcedure() { // from class: gnu.trove.TLongHashSet.2
            @Override // gnu.trove.TLongProcedure
            public boolean execute(long j) {
                if (sb.length() != 0) {
                    sb.append(',').append(StringUtil.SPACE);
                }
                sb.append(j);
                return true;
            }
        });
        sb.append(']');
        sb.insert(0, '[');
        return sb.toString();
    }
}
