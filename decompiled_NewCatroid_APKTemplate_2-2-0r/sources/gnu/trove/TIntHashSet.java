package gnu.trove;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/* JADX INFO: loaded from: classes11.dex */
public class TIntHashSet extends TIntHash {
    public TIntHashSet() {
    }

    public TIntHashSet(int i) {
        super(i);
    }

    public TIntHashSet(int i, float f) {
        super(i, f);
    }

    public TIntHashSet(int[] iArr) {
        this(iArr.length);
        addAll(iArr);
    }

    public TIntHashSet(TIntHashingStrategy tIntHashingStrategy) {
        super(tIntHashingStrategy);
    }

    public TIntHashSet(int i, TIntHashingStrategy tIntHashingStrategy) {
        super(i, tIntHashingStrategy);
    }

    public TIntHashSet(int i, float f, TIntHashingStrategy tIntHashingStrategy) {
        super(i, f, tIntHashingStrategy);
    }

    public TIntHashSet(int[] iArr, TIntHashingStrategy tIntHashingStrategy) {
        this(iArr.length, tIntHashingStrategy);
        addAll(iArr);
    }

    public TIntIterator iterator() {
        return new TIntIterator(this);
    }

    public boolean add(int i) {
        int iInsertionIndex = insertionIndex(i);
        if (iInsertionIndex < 0) {
            return false;
        }
        byte b = this._states[iInsertionIndex];
        this._set[iInsertionIndex] = i;
        this._states[iInsertionIndex] = 1;
        postInsertHook(b == 0);
        return true;
    }

    @Override // gnu.trove.THash
    protected void rehash(int i) {
        int iCapacity = capacity();
        int[] iArr = this._set;
        byte[] bArr = this._states;
        this._set = new int[i];
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
                this._states[iInsertionIndex] = 1;
            }
            iCapacity = i2;
        }
    }

    public int[] toArray() {
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

    @Override // gnu.trove.THash
    public void clear() {
        super.clear();
        int[] iArr = this._set;
        byte[] bArr = this._states;
        if (bArr == null) {
            return;
        }
        int length = iArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return;
            }
            iArr[i] = 0;
            bArr[i] = 0;
            length = i;
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TIntHashSet)) {
            return false;
        }
        final TIntHashSet tIntHashSet = (TIntHashSet) obj;
        if (tIntHashSet.size() != size()) {
            return false;
        }
        return forEach(new TIntProcedure() { // from class: gnu.trove.TIntHashSet.1
            @Override // gnu.trove.TIntProcedure
            public final boolean execute(int i) {
                return tIntHashSet.contains(i);
            }
        });
    }

    public int hashCode() {
        HashProcedure hashProcedure = new HashProcedure();
        forEach(hashProcedure);
        return hashProcedure.getHashCode();
    }

    private final class HashProcedure implements TIntProcedure {
        private int h;

        HashProcedure() {
        }

        public int getHashCode() {
            return this.h;
        }

        @Override // gnu.trove.TIntProcedure
        public final boolean execute(int i) {
            this.h += TIntHashSet.this._hashingStrategy.computeHashCode(i);
            return true;
        }
    }

    public boolean remove(int i) {
        int iIndex = index(i);
        if (iIndex < 0) {
            return false;
        }
        removeAt(iIndex);
        return true;
    }

    public boolean containsAll(int[] iArr) {
        int length = iArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return true;
            }
            if (!contains(iArr[i])) {
                return false;
            }
            length = i;
        }
    }

    public boolean addAll(int[] iArr) {
        int length = iArr.length;
        boolean z = false;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return z;
            }
            if (add(iArr[i])) {
                z = true;
            }
            length = i;
        }
    }

    public boolean removeAll(int[] iArr) {
        int length = iArr.length;
        boolean z = false;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return z;
            }
            if (remove(iArr[i])) {
                z = true;
            }
            length = i;
        }
    }

    public boolean retainAll(int[] iArr) {
        Arrays.sort(iArr);
        int[] iArr2 = this._set;
        byte[] bArr = this._states;
        boolean z = false;
        if (iArr2 != null) {
            int length = iArr2.length;
            while (true) {
                length--;
                if (length <= 0) {
                    break;
                }
                if (bArr[length] == 1 && Arrays.binarySearch(iArr, iArr2[length]) < 0) {
                    remove(iArr2[length]);
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
            add(objectInputStream.readInt());
            i = i2;
        }
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        forEach(new TIntProcedure() { // from class: gnu.trove.TIntHashSet.2
            @Override // gnu.trove.TIntProcedure
            public boolean execute(int i) {
                if (sb.length() != 0) {
                    sb.append(',').append(StringUtil.SPACE);
                }
                sb.append(i);
                return true;
            }
        });
        sb.append(']');
        sb.insert(0, '[');
        return sb.toString();
    }
}
