package gnu.trove;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

/* JADX INFO: loaded from: classes11.dex */
public class TLongArrayList implements Serializable, Cloneable {
    protected static final int DEFAULT_CAPACITY = 4;
    protected transient long[] _data;
    protected transient int _pos;

    public TLongArrayList() {
    }

    public TLongArrayList(int i) {
        this._data = new long[i];
        this._pos = 0;
    }

    public TLongArrayList(long[] jArr) {
        this(Math.max(jArr.length, 4));
        add(jArr);
    }

    public void ensureCapacity(int i) {
        if (this._data == null) {
            this._data = new long[Math.max(4, i)];
        }
        long[] jArr = this._data;
        if (i > jArr.length) {
            long[] jArr2 = new long[Math.max(jArr.length << 1, i)];
            long[] jArr3 = this._data;
            System.arraycopy(jArr3, 0, jArr2, 0, jArr3.length);
            this._data = jArr2;
        }
    }

    public int size() {
        return this._pos;
    }

    public boolean isEmpty() {
        return this._pos == 0;
    }

    public void trimToSize() {
        long[] jArr = this._data;
        if (jArr == null || jArr.length <= size()) {
            return;
        }
        int size = size();
        long[] jArr2 = new long[size];
        toNativeArray(jArr2, 0, size);
        this._data = jArr2;
    }

    public void add(long j) {
        ensureCapacity(this._pos + 1);
        long[] jArr = this._data;
        int i = this._pos;
        this._pos = i + 1;
        jArr[i] = j;
    }

    public void add(long[] jArr) {
        add(jArr, 0, jArr.length);
    }

    public void add(long[] jArr, int i, int i2) {
        ensureCapacity(this._pos + i2);
        System.arraycopy(jArr, i, this._data, this._pos, i2);
        this._pos += i2;
    }

    public void insert(int i, long j) {
        int i2 = this._pos;
        if (i == i2) {
            add(j);
            return;
        }
        ensureCapacity(i2 + 1);
        long[] jArr = this._data;
        System.arraycopy(jArr, i, jArr, i + 1, this._pos - i);
        this._data[i] = j;
        this._pos++;
    }

    public void insert(int i, long[] jArr) {
        insert(i, jArr, 0, jArr.length);
    }

    public void insert(int i, long[] jArr, int i2, int i3) {
        int i4 = this._pos;
        if (i == i4) {
            add(jArr, i2, i3);
            return;
        }
        ensureCapacity(i4 + i3);
        long[] jArr2 = this._data;
        System.arraycopy(jArr2, i, jArr2, i + i3, this._pos - i);
        System.arraycopy(jArr, i2, this._data, i, i3);
        this._pos += i3;
    }

    public long get(int i) {
        if (i >= this._pos) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        return this._data[i];
    }

    public long getQuick(int i) {
        return this._data[i];
    }

    public void set(int i, long j) {
        if (i < 0 || i >= this._pos) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        this._data[i] = j;
    }

    public long getSet(int i, long j) {
        if (i < 0 || i >= this._pos) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        long[] jArr = this._data;
        long j2 = jArr[i];
        jArr[i] = j;
        return j2;
    }

    public void set(int i, long[] jArr) {
        set(i, jArr, 0, jArr.length);
    }

    public void set(int i, long[] jArr, int i2, int i3) {
        if (i < 0 || i + i3 > this._pos) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        System.arraycopy(this._data, i, jArr, i2, i3);
    }

    public void setQuick(int i, long j) {
        this._data[i] = j;
    }

    public void clear() {
        this._data = null;
        this._pos = 0;
    }

    public void clear(int i) {
        this._data = new long[i];
        this._pos = 0;
    }

    public void reset() {
        fill(0L);
        this._pos = 0;
    }

    public void resetQuick() {
        this._pos = 0;
    }

    public long remove(int i) {
        long j = get(i);
        remove(i, 1);
        return j;
    }

    public void remove(int i, int i2) {
        int i3;
        if (i < 0 || i >= (i3 = this._pos)) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        if (i == 0) {
            long[] jArr = this._data;
            System.arraycopy(jArr, i2, jArr, 0, i3 - i2);
        } else if (i3 - i2 != i) {
            long[] jArr2 = this._data;
            int i4 = i + i2;
            System.arraycopy(jArr2, i4, jArr2, i, i3 - i4);
        }
        this._pos -= i2;
    }

    public void transformValues(TLongFunction tLongFunction) {
        int i = this._pos;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return;
            }
            long[] jArr = this._data;
            jArr[i2] = tLongFunction.execute(jArr[i2]);
            i = i2;
        }
    }

    public void reverse() {
        reverse(0, this._pos);
    }

    public void reverse(int i, int i2) {
        if (i == i2) {
            return;
        }
        if (i > i2) {
            throw new IllegalArgumentException("from cannot be greater than to");
        }
        for (int i3 = i2 - 1; i < i3; i3--) {
            swap(i, i3);
            i++;
        }
    }

    public void shuffle(Random random) {
        int i = this._pos;
        while (true) {
            int i2 = i - 1;
            if (i <= 1) {
                return;
            }
            swap(i2, random.nextInt(i2));
            i = i2;
        }
    }

    private void swap(int i, int i2) {
        long[] jArr = this._data;
        long j = jArr[i];
        jArr[i] = jArr[i2];
        jArr[i2] = j;
    }

    public Object clone() {
        TLongArrayList tLongArrayList = null;
        long[] jArr = null;
        try {
            TLongArrayList tLongArrayList2 = (TLongArrayList) super.clone();
            try {
                long[] jArr2 = this._data;
                if (jArr2 != null) {
                    jArr = (long[]) jArr2.clone();
                }
                tLongArrayList2._data = jArr;
                return tLongArrayList2;
            } catch (CloneNotSupportedException unused) {
                tLongArrayList = tLongArrayList2;
                return tLongArrayList;
            }
        } catch (CloneNotSupportedException unused2) {
        }
    }

    public long[] toNativeArray() {
        return toNativeArray(0, this._pos);
    }

    public long[] toNativeArray(int i, int i2) {
        long[] jArr = new long[i2];
        toNativeArray(jArr, i, i2);
        return jArr;
    }

    public void toNativeArray(long[] jArr, int i, int i2) {
        if (i2 == 0) {
            return;
        }
        if (i < 0 || i >= this._pos) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        System.arraycopy(this._data, i, jArr, 0, i2);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TLongArrayList)) {
            return false;
        }
        TLongArrayList tLongArrayList = (TLongArrayList) obj;
        if (tLongArrayList.size() != size()) {
            return false;
        }
        int i = this._pos;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return true;
            }
            if (this._data[i2] != tLongArrayList._data[i2]) {
                return false;
            }
            i = i2;
        }
    }

    public int hashCode() {
        int i = this._pos;
        int iHash = 0;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return iHash;
            }
            iHash += HashFunctions.hash(this._data[i2]);
            i = i2;
        }
    }

    public boolean forEach(TLongProcedure tLongProcedure) {
        for (int i = 0; i < this._pos; i++) {
            if (!tLongProcedure.execute(this._data[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean forEachDescending(TLongProcedure tLongProcedure) {
        int i = this._pos;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return true;
            }
            if (!tLongProcedure.execute(this._data[i2])) {
                return false;
            }
            i = i2;
        }
    }

    public void sort() {
        if (isEmpty()) {
            return;
        }
        Arrays.sort(this._data, 0, this._pos);
    }

    public void sort(int i, int i2) {
        if (isEmpty()) {
            return;
        }
        Arrays.sort(this._data, i, i2);
    }

    public void fill(long j) {
        if (isEmpty()) {
            return;
        }
        Arrays.fill(this._data, 0, this._pos, j);
    }

    public void fill(int i, int i2, long j) {
        if (i2 > this._pos) {
            ensureCapacity(i2);
            this._pos = i2;
        }
        if (isEmpty()) {
            return;
        }
        Arrays.fill(this._data, i, i2, j);
    }

    public int binarySearch(long j) {
        return binarySearch(j, 0, this._pos);
    }

    public int binarySearch(long j, int i, int i2) {
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        if (i2 > this._pos) {
            throw new ArrayIndexOutOfBoundsException(i2);
        }
        int i3 = i2 - 1;
        while (i <= i3) {
            int i4 = (i + i3) >> 1;
            long j2 = this._data[i4];
            if (j2 < j) {
                i = i4 + 1;
            } else {
                if (j2 <= j) {
                    return i4;
                }
                i3 = i4 - 1;
            }
        }
        return -(i + 1);
    }

    public int indexOf(long j) {
        return indexOf(0, j);
    }

    public int indexOf(int i, long j) {
        while (i < this._pos) {
            if (this._data[i] == j) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public int lastIndexOf(long j) {
        return lastIndexOf(this._pos, j);
    }

    public int lastIndexOf(int i, long j) {
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return -1;
            }
            if (this._data[i2] == j) {
                return i2;
            }
            i = i2;
        }
    }

    public boolean contains(long j) {
        return lastIndexOf(j) >= 0;
    }

    public TLongArrayList grep(TLongProcedure tLongProcedure) {
        TLongArrayList tLongArrayList = new TLongArrayList();
        for (int i = 0; i < this._pos; i++) {
            if (tLongProcedure.execute(this._data[i])) {
                tLongArrayList.add(this._data[i]);
            }
        }
        return tLongArrayList;
    }

    public TLongArrayList inverseGrep(TLongProcedure tLongProcedure) {
        TLongArrayList tLongArrayList = new TLongArrayList();
        for (int i = 0; i < this._pos; i++) {
            if (!tLongProcedure.execute(this._data[i])) {
                tLongArrayList.add(this._data[i]);
            }
        }
        return tLongArrayList;
    }

    public long max() {
        if (size() == 0) {
            throw new IllegalStateException("cannot find maximum of an empty list");
        }
        long[] jArr = this._data;
        int i = this._pos;
        long jMax = jArr[i - 1];
        int i2 = i - 1;
        while (true) {
            int i3 = i2 - 1;
            if (i2 <= 0) {
                return jMax;
            }
            jMax = Math.max(jMax, this._data[this._pos]);
            i2 = i3;
        }
    }

    public long min() {
        if (size() == 0) {
            throw new IllegalStateException("cannot find minimum of an empty list");
        }
        long[] jArr = this._data;
        int i = this._pos;
        long jMin = jArr[i - 1];
        int i2 = i - 1;
        while (true) {
            int i3 = i2 - 1;
            if (i2 <= 0) {
                return jMin;
            }
            jMin = Math.min(jMin, this._data[this._pos]);
            i2 = i3;
        }
    }

    public String toString() {
        final StringBuffer stringBuffer = new StringBuffer("{");
        forEach(new TLongProcedure() { // from class: gnu.trove.TLongArrayList.1
            @Override // gnu.trove.TLongProcedure
            public boolean execute(long j) {
                stringBuffer.append(j);
                stringBuffer.append(", ");
                return true;
            }
        });
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(size());
        SerializationProcedure serializationProcedure = new SerializationProcedure(objectOutputStream);
        if (!forEach(serializationProcedure)) {
            throw serializationProcedure.exception;
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        int i = objectInputStream.readInt();
        this._data = new long[i];
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return;
            }
            add(objectInputStream.readLong());
            i = i2;
        }
    }
}
