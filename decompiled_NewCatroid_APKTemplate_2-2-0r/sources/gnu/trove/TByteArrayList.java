package gnu.trove;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

/* JADX INFO: loaded from: classes11.dex */
public class TByteArrayList implements Serializable, Cloneable {
    protected static final int DEFAULT_CAPACITY = 4;
    protected transient byte[] _data;
    protected transient int _pos;

    public TByteArrayList() {
    }

    public TByteArrayList(int i) {
        this._data = new byte[i];
        this._pos = 0;
    }

    public TByteArrayList(byte[] bArr) {
        this(Math.max(bArr.length, 4));
        add(bArr);
    }

    public void ensureCapacity(int i) {
        if (this._data == null) {
            this._data = new byte[Math.max(4, i)];
        }
        byte[] bArr = this._data;
        if (i > bArr.length) {
            byte[] bArr2 = new byte[Math.max(bArr.length << 1, i)];
            byte[] bArr3 = this._data;
            System.arraycopy(bArr3, 0, bArr2, 0, bArr3.length);
            this._data = bArr2;
        }
    }

    public int size() {
        return this._pos;
    }

    public boolean isEmpty() {
        return this._pos == 0;
    }

    public void trimToSize() {
        byte[] bArr = this._data;
        if (bArr == null || bArr.length <= size()) {
            return;
        }
        int size = size();
        byte[] bArr2 = new byte[size];
        toNativeArray(bArr2, 0, size);
        this._data = bArr2;
    }

    public void add(byte b) {
        ensureCapacity(this._pos + 1);
        byte[] bArr = this._data;
        int i = this._pos;
        this._pos = i + 1;
        bArr[i] = b;
    }

    public void add(byte[] bArr) {
        add(bArr, 0, bArr.length);
    }

    public void add(byte[] bArr, int i, int i2) {
        ensureCapacity(this._pos + i2);
        System.arraycopy(bArr, i, this._data, this._pos, i2);
        this._pos += i2;
    }

    public void insert(int i, byte b) {
        int i2 = this._pos;
        if (i == i2) {
            add(b);
            return;
        }
        ensureCapacity(i2 + 1);
        byte[] bArr = this._data;
        System.arraycopy(bArr, i, bArr, i + 1, this._pos - i);
        this._data[i] = b;
        this._pos++;
    }

    public void insert(int i, byte[] bArr) {
        insert(i, bArr, 0, bArr.length);
    }

    public void insert(int i, byte[] bArr, int i2, int i3) {
        int i4 = this._pos;
        if (i == i4) {
            add(bArr, i2, i3);
            return;
        }
        ensureCapacity(i4 + i3);
        byte[] bArr2 = this._data;
        System.arraycopy(bArr2, i, bArr2, i + i3, this._pos - i);
        System.arraycopy(bArr, i2, this._data, i, i3);
        this._pos += i3;
    }

    public byte get(int i) {
        if (i >= this._pos) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        return this._data[i];
    }

    public byte getQuick(int i) {
        return this._data[i];
    }

    public void set(int i, byte b) {
        if (i < 0 || i >= this._pos) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        this._data[i] = b;
    }

    public byte getSet(int i, byte b) {
        if (i < 0 || i >= this._pos) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        byte[] bArr = this._data;
        byte b2 = bArr[i];
        bArr[i] = b;
        return b2;
    }

    public void set(int i, byte[] bArr) {
        set(i, bArr, 0, bArr.length);
    }

    public void set(int i, byte[] bArr, int i2, int i3) {
        if (i < 0 || i + i3 > this._pos) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        System.arraycopy(this._data, i, bArr, i2, i3);
    }

    public void setQuick(int i, byte b) {
        this._data[i] = b;
    }

    public void clear() {
        this._data = null;
        this._pos = 0;
    }

    public void clear(int i) {
        this._data = new byte[i];
        this._pos = 0;
    }

    public void reset() {
        fill((byte) 0);
        this._pos = 0;
    }

    public void resetQuick() {
        this._pos = 0;
    }

    public byte remove(int i) {
        byte b = get(i);
        remove(i, 1);
        return b;
    }

    public void remove(int i, int i2) {
        int i3;
        if (i < 0 || i >= (i3 = this._pos)) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        if (i == 0) {
            byte[] bArr = this._data;
            System.arraycopy(bArr, i2, bArr, 0, i3 - i2);
        } else if (i3 - i2 != i) {
            byte[] bArr2 = this._data;
            int i4 = i + i2;
            System.arraycopy(bArr2, i4, bArr2, i, i3 - i4);
        }
        this._pos -= i2;
    }

    public void transformValues(TByteFunction tByteFunction) {
        int i = this._pos;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return;
            }
            byte[] bArr = this._data;
            bArr[i2] = tByteFunction.execute(bArr[i2]);
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
        byte[] bArr = this._data;
        byte b = bArr[i];
        bArr[i] = bArr[i2];
        bArr[i2] = b;
    }

    public Object clone() {
        TByteArrayList tByteArrayList = null;
        byte[] bArr = null;
        try {
            TByteArrayList tByteArrayList2 = (TByteArrayList) super.clone();
            try {
                byte[] bArr2 = this._data;
                if (bArr2 != null) {
                    bArr = (byte[]) bArr2.clone();
                }
                tByteArrayList2._data = bArr;
                return tByteArrayList2;
            } catch (CloneNotSupportedException unused) {
                tByteArrayList = tByteArrayList2;
                return tByteArrayList;
            }
        } catch (CloneNotSupportedException unused2) {
        }
    }

    public byte[] toNativeArray() {
        return toNativeArray(0, this._pos);
    }

    public byte[] toNativeArray(int i, int i2) {
        byte[] bArr = new byte[i2];
        toNativeArray(bArr, i, i2);
        return bArr;
    }

    public void toNativeArray(byte[] bArr, int i, int i2) {
        if (i2 == 0) {
            return;
        }
        if (i < 0 || i >= this._pos) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        System.arraycopy(this._data, i, bArr, 0, i2);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TByteArrayList)) {
            return false;
        }
        TByteArrayList tByteArrayList = (TByteArrayList) obj;
        if (tByteArrayList.size() != size()) {
            return false;
        }
        int i = this._pos;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return true;
            }
            if (this._data[i2] != tByteArrayList._data[i2]) {
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
            iHash += HashFunctions.hash((int) this._data[i2]);
            i = i2;
        }
    }

    public boolean forEach(TByteProcedure tByteProcedure) {
        for (int i = 0; i < this._pos; i++) {
            if (!tByteProcedure.execute(this._data[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean forEachDescending(TByteProcedure tByteProcedure) {
        int i = this._pos;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return true;
            }
            if (!tByteProcedure.execute(this._data[i2])) {
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

    public void fill(byte b) {
        if (isEmpty()) {
            return;
        }
        Arrays.fill(this._data, 0, this._pos, b);
    }

    public void fill(int i, int i2, byte b) {
        if (i2 > this._pos) {
            ensureCapacity(i2);
            this._pos = i2;
        }
        if (isEmpty()) {
            return;
        }
        Arrays.fill(this._data, i, i2, b);
    }

    public int binarySearch(byte b) {
        return binarySearch(b, 0, this._pos);
    }

    public int binarySearch(byte b, int i, int i2) {
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        if (i2 > this._pos) {
            throw new ArrayIndexOutOfBoundsException(i2);
        }
        int i3 = i2 - 1;
        while (i <= i3) {
            int i4 = (i + i3) >> 1;
            byte b2 = this._data[i4];
            if (b2 < b) {
                i = i4 + 1;
            } else {
                if (b2 <= b) {
                    return i4;
                }
                i3 = i4 - 1;
            }
        }
        return -(i + 1);
    }

    public int indexOf(byte b) {
        return indexOf(0, b);
    }

    public int indexOf(int i, byte b) {
        while (i < this._pos) {
            if (this._data[i] == b) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public int lastIndexOf(byte b) {
        return lastIndexOf(this._pos, b);
    }

    public int lastIndexOf(int i, byte b) {
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return -1;
            }
            if (this._data[i2] == b) {
                return i2;
            }
            i = i2;
        }
    }

    public boolean contains(byte b) {
        return lastIndexOf(b) >= 0;
    }

    public TByteArrayList grep(TByteProcedure tByteProcedure) {
        TByteArrayList tByteArrayList = new TByteArrayList();
        for (int i = 0; i < this._pos; i++) {
            if (tByteProcedure.execute(this._data[i])) {
                tByteArrayList.add(this._data[i]);
            }
        }
        return tByteArrayList;
    }

    public TByteArrayList inverseGrep(TByteProcedure tByteProcedure) {
        TByteArrayList tByteArrayList = new TByteArrayList();
        for (int i = 0; i < this._pos; i++) {
            if (!tByteProcedure.execute(this._data[i])) {
                tByteArrayList.add(this._data[i]);
            }
        }
        return tByteArrayList;
    }

    public byte max() {
        if (size() == 0) {
            throw new IllegalStateException("cannot find maximum of an empty list");
        }
        byte[] bArr = this._data;
        int i = this._pos;
        byte bMax = bArr[i - 1];
        int i2 = i - 1;
        while (true) {
            int i3 = i2 - 1;
            if (i2 <= 0) {
                return bMax;
            }
            bMax = (byte) Math.max((int) bMax, (int) this._data[this._pos]);
            i2 = i3;
        }
    }

    public byte min() {
        if (size() == 0) {
            throw new IllegalStateException("cannot find minimum of an empty list");
        }
        byte[] bArr = this._data;
        int i = this._pos;
        byte bMin = bArr[i - 1];
        int i2 = i - 1;
        while (true) {
            int i3 = i2 - 1;
            if (i2 <= 0) {
                return bMin;
            }
            bMin = (byte) Math.min((int) bMin, (int) this._data[this._pos]);
            i2 = i3;
        }
    }

    public String toString() {
        final StringBuffer stringBuffer = new StringBuffer("{");
        forEach(new TByteProcedure() { // from class: gnu.trove.TByteArrayList.1
            @Override // gnu.trove.TByteProcedure
            public boolean execute(byte b) {
                stringBuffer.append((int) b);
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
        this._data = new byte[i];
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return;
            }
            add(objectInputStream.readByte());
            i = i2;
        }
    }
}
