package gnu.trove;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/* JADX INFO: loaded from: classes11.dex */
public class TFloatHashSet extends TFloatHash {
    public TFloatHashSet() {
    }

    public TFloatHashSet(int i) {
        super(i);
    }

    public TFloatHashSet(int i, float f) {
        super(i, f);
    }

    public TFloatHashSet(float[] fArr) {
        this(fArr.length);
        addAll(fArr);
    }

    public TFloatHashSet(TFloatHashingStrategy tFloatHashingStrategy) {
        super(tFloatHashingStrategy);
    }

    public TFloatHashSet(int i, TFloatHashingStrategy tFloatHashingStrategy) {
        super(i, tFloatHashingStrategy);
    }

    public TFloatHashSet(int i, float f, TFloatHashingStrategy tFloatHashingStrategy) {
        super(i, f, tFloatHashingStrategy);
    }

    public TFloatHashSet(float[] fArr, TFloatHashingStrategy tFloatHashingStrategy) {
        this(fArr.length, tFloatHashingStrategy);
        addAll(fArr);
    }

    public TFloatIterator iterator() {
        return new TFloatIterator(this);
    }

    public boolean add(float f) {
        int iInsertionIndex = insertionIndex(f);
        if (iInsertionIndex < 0) {
            return false;
        }
        byte b = this._states[iInsertionIndex];
        this._set[iInsertionIndex] = f;
        this._states[iInsertionIndex] = 1;
        postInsertHook(b == 0);
        return true;
    }

    @Override // gnu.trove.THash
    protected void rehash(int i) {
        int iCapacity = capacity();
        float[] fArr = this._set;
        byte[] bArr = this._states;
        this._set = new float[i];
        this._states = new byte[i];
        while (true) {
            int i2 = iCapacity - 1;
            if (iCapacity <= 0) {
                return;
            }
            if (bArr[i2] == 1) {
                float f = fArr[i2];
                int iInsertionIndex = insertionIndex(f);
                this._set[iInsertionIndex] = f;
                this._states[iInsertionIndex] = 1;
            }
            iCapacity = i2;
        }
    }

    public float[] toArray() {
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

    @Override // gnu.trove.THash
    public void clear() {
        super.clear();
        float[] fArr = this._set;
        byte[] bArr = this._states;
        if (bArr == null) {
            return;
        }
        int length = fArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return;
            }
            fArr[i] = 0.0f;
            bArr[i] = 0;
            length = i;
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TFloatHashSet)) {
            return false;
        }
        final TFloatHashSet tFloatHashSet = (TFloatHashSet) obj;
        if (tFloatHashSet.size() != size()) {
            return false;
        }
        return forEach(new TFloatProcedure() { // from class: gnu.trove.TFloatHashSet.1
            @Override // gnu.trove.TFloatProcedure
            public final boolean execute(float f) {
                return tFloatHashSet.contains(f);
            }
        });
    }

    public int hashCode() {
        HashProcedure hashProcedure = new HashProcedure();
        forEach(hashProcedure);
        return hashProcedure.getHashCode();
    }

    private final class HashProcedure implements TFloatProcedure {
        private int h;

        HashProcedure() {
        }

        public int getHashCode() {
            return this.h;
        }

        @Override // gnu.trove.TFloatProcedure
        public final boolean execute(float f) {
            this.h += TFloatHashSet.this._hashingStrategy.computeHashCode(f);
            return true;
        }
    }

    public boolean remove(float f) {
        int iIndex = index(f);
        if (iIndex < 0) {
            return false;
        }
        removeAt(iIndex);
        return true;
    }

    public boolean containsAll(float[] fArr) {
        int length = fArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return true;
            }
            if (!contains(fArr[i])) {
                return false;
            }
            length = i;
        }
    }

    public boolean addAll(float[] fArr) {
        int length = fArr.length;
        boolean z = false;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return z;
            }
            if (add(fArr[i])) {
                z = true;
            }
            length = i;
        }
    }

    public boolean removeAll(float[] fArr) {
        int length = fArr.length;
        boolean z = false;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return z;
            }
            if (remove(fArr[i])) {
                z = true;
            }
            length = i;
        }
    }

    public boolean retainAll(float[] fArr) {
        Arrays.sort(fArr);
        float[] fArr2 = this._set;
        byte[] bArr = this._states;
        boolean z = false;
        if (fArr2 != null) {
            int length = fArr2.length;
            while (true) {
                length--;
                if (length <= 0) {
                    break;
                }
                if (bArr[length] == 1 && Arrays.binarySearch(fArr, fArr2[length]) < 0) {
                    remove(fArr2[length]);
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
            add(objectInputStream.readFloat());
            i = i2;
        }
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        forEach(new TFloatProcedure() { // from class: gnu.trove.TFloatHashSet.2
            @Override // gnu.trove.TFloatProcedure
            public boolean execute(float f) {
                if (sb.length() != 0) {
                    sb.append(',').append(StringUtil.SPACE);
                }
                sb.append(f);
                return true;
            }
        });
        sb.append(']');
        sb.insert(0, '[');
        return sb.toString();
    }
}
