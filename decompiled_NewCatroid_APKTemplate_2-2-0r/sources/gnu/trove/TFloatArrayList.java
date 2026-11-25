package gnu.trove;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

/* JADX INFO: loaded from: classes11.dex */
public class TFloatArrayList implements Serializable, Cloneable {
    protected static final int DEFAULT_CAPACITY = 4;
    protected transient float[] _data;
    protected transient int _pos;

    public TFloatArrayList() {
    }

    public TFloatArrayList(int i) {
        this._data = new float[i];
        this._pos = 0;
    }

    public TFloatArrayList(float[] fArr) {
        this(Math.max(fArr.length, 4));
        add(fArr);
    }

    public void ensureCapacity(int i) {
        if (this._data == null) {
            this._data = new float[Math.max(4, i)];
        }
        float[] fArr = this._data;
        if (i > fArr.length) {
            float[] fArr2 = new float[Math.max(fArr.length << 1, i)];
            float[] fArr3 = this._data;
            System.arraycopy(fArr3, 0, fArr2, 0, fArr3.length);
            this._data = fArr2;
        }
    }

    public int size() {
        return this._pos;
    }

    public boolean isEmpty() {
        return this._pos == 0;
    }

    public void trimToSize() {
        float[] fArr = this._data;
        if (fArr == null || fArr.length <= size()) {
            return;
        }
        int size = size();
        float[] fArr2 = new float[size];
        toNativeArray(fArr2, 0, size);
        this._data = fArr2;
    }

    public void add(float f) {
        ensureCapacity(this._pos + 1);
        float[] fArr = this._data;
        int i = this._pos;
        this._pos = i + 1;
        fArr[i] = f;
    }

    public void add(float[] fArr) {
        add(fArr, 0, fArr.length);
    }

    public void add(float[] fArr, int i, int i2) {
        ensureCapacity(this._pos + i2);
        System.arraycopy(fArr, i, this._data, this._pos, i2);
        this._pos += i2;
    }

    public void insert(int i, float f) {
        int i2 = this._pos;
        if (i == i2) {
            add(f);
            return;
        }
        ensureCapacity(i2 + 1);
        float[] fArr = this._data;
        System.arraycopy(fArr, i, fArr, i + 1, this._pos - i);
        this._data[i] = f;
        this._pos++;
    }

    public void insert(int i, float[] fArr) {
        insert(i, fArr, 0, fArr.length);
    }

    public void insert(int i, float[] fArr, int i2, int i3) {
        int i4 = this._pos;
        if (i == i4) {
            add(fArr, i2, i3);
            return;
        }
        ensureCapacity(i4 + i3);
        float[] fArr2 = this._data;
        System.arraycopy(fArr2, i, fArr2, i + i3, this._pos - i);
        System.arraycopy(fArr, i2, this._data, i, i3);
        this._pos += i3;
    }

    public float get(int i) {
        if (i >= this._pos) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        return this._data[i];
    }

    public float getQuick(int i) {
        return this._data[i];
    }

    public void set(int i, float f) {
        if (i < 0 || i >= this._pos) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        this._data[i] = f;
    }

    public float getSet(int i, float f) {
        if (i < 0 || i >= this._pos) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        float[] fArr = this._data;
        float f2 = fArr[i];
        fArr[i] = f;
        return f2;
    }

    public void set(int i, float[] fArr) {
        set(i, fArr, 0, fArr.length);
    }

    public void set(int i, float[] fArr, int i2, int i3) {
        if (i < 0 || i + i3 > this._pos) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        System.arraycopy(this._data, i, fArr, i2, i3);
    }

    public void setQuick(int i, float f) {
        this._data[i] = f;
    }

    public void clear() {
        this._data = null;
        this._pos = 0;
    }

    public void clear(int i) {
        this._data = new float[i];
        this._pos = 0;
    }

    public void reset() {
        fill(0.0f);
        this._pos = 0;
    }

    public void resetQuick() {
        this._pos = 0;
    }

    public float remove(int i) {
        float f = get(i);
        remove(i, 1);
        return f;
    }

    public void remove(int i, int i2) {
        int i3;
        if (i < 0 || i >= (i3 = this._pos)) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        if (i == 0) {
            float[] fArr = this._data;
            System.arraycopy(fArr, i2, fArr, 0, i3 - i2);
        } else if (i3 - i2 != i) {
            float[] fArr2 = this._data;
            int i4 = i + i2;
            System.arraycopy(fArr2, i4, fArr2, i, i3 - i4);
        }
        this._pos -= i2;
    }

    public void transformValues(TFloatFunction tFloatFunction) {
        int i = this._pos;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return;
            }
            float[] fArr = this._data;
            fArr[i2] = tFloatFunction.execute(fArr[i2]);
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
        float[] fArr = this._data;
        float f = fArr[i];
        fArr[i] = fArr[i2];
        fArr[i2] = f;
    }

    public Object clone() {
        TFloatArrayList tFloatArrayList = null;
        float[] fArr = null;
        try {
            TFloatArrayList tFloatArrayList2 = (TFloatArrayList) super.clone();
            try {
                float[] fArr2 = this._data;
                if (fArr2 != null) {
                    fArr = (float[]) fArr2.clone();
                }
                tFloatArrayList2._data = fArr;
                return tFloatArrayList2;
            } catch (CloneNotSupportedException unused) {
                tFloatArrayList = tFloatArrayList2;
                return tFloatArrayList;
            }
        } catch (CloneNotSupportedException unused2) {
        }
    }

    public float[] toNativeArray() {
        return toNativeArray(0, this._pos);
    }

    public float[] toNativeArray(int i, int i2) {
        float[] fArr = new float[i2];
        toNativeArray(fArr, i, i2);
        return fArr;
    }

    public void toNativeArray(float[] fArr, int i, int i2) {
        if (i2 == 0) {
            return;
        }
        if (i < 0 || i >= this._pos) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        System.arraycopy(this._data, i, fArr, 0, i2);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TFloatArrayList)) {
            return false;
        }
        TFloatArrayList tFloatArrayList = (TFloatArrayList) obj;
        if (tFloatArrayList.size() != size()) {
            return false;
        }
        int i = this._pos;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return true;
            }
            if (this._data[i2] != tFloatArrayList._data[i2]) {
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

    public boolean forEach(TFloatProcedure tFloatProcedure) {
        for (int i = 0; i < this._pos; i++) {
            if (!tFloatProcedure.execute(this._data[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean forEachDescending(TFloatProcedure tFloatProcedure) {
        int i = this._pos;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return true;
            }
            if (!tFloatProcedure.execute(this._data[i2])) {
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

    public void fill(float f) {
        if (isEmpty()) {
            return;
        }
        Arrays.fill(this._data, 0, this._pos, f);
    }

    public void fill(int i, int i2, float f) {
        if (i2 > this._pos) {
            ensureCapacity(i2);
            this._pos = i2;
        }
        if (isEmpty()) {
            return;
        }
        Arrays.fill(this._data, i, i2, f);
    }

    public int binarySearch(float f) {
        return binarySearch(f, 0, this._pos);
    }

    public int binarySearch(float f, int i, int i2) {
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        if (i2 > this._pos) {
            throw new ArrayIndexOutOfBoundsException(i2);
        }
        int i3 = i2 - 1;
        while (i <= i3) {
            int i4 = (i + i3) >> 1;
            float f2 = this._data[i4];
            if (f2 < f) {
                i = i4 + 1;
            } else {
                if (f2 <= f) {
                    return i4;
                }
                i3 = i4 - 1;
            }
        }
        return -(i + 1);
    }

    public int indexOf(float f) {
        return indexOf(0, f);
    }

    public int indexOf(int i, float f) {
        while (i < this._pos) {
            if (this._data[i] == f) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public int lastIndexOf(float f) {
        return lastIndexOf(this._pos, f);
    }

    public int lastIndexOf(int i, float f) {
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return -1;
            }
            if (this._data[i2] == f) {
                return i2;
            }
            i = i2;
        }
    }

    public boolean contains(float f) {
        return lastIndexOf(f) >= 0;
    }

    public TFloatArrayList grep(TFloatProcedure tFloatProcedure) {
        TFloatArrayList tFloatArrayList = new TFloatArrayList();
        for (int i = 0; i < this._pos; i++) {
            if (tFloatProcedure.execute(this._data[i])) {
                tFloatArrayList.add(this._data[i]);
            }
        }
        return tFloatArrayList;
    }

    public TFloatArrayList inverseGrep(TFloatProcedure tFloatProcedure) {
        TFloatArrayList tFloatArrayList = new TFloatArrayList();
        for (int i = 0; i < this._pos; i++) {
            if (!tFloatProcedure.execute(this._data[i])) {
                tFloatArrayList.add(this._data[i]);
            }
        }
        return tFloatArrayList;
    }

    public float max() {
        if (size() == 0) {
            throw new IllegalStateException("cannot find maximum of an empty list");
        }
        float[] fArr = this._data;
        int i = this._pos;
        float fMax = fArr[i - 1];
        int i2 = i - 1;
        while (true) {
            int i3 = i2 - 1;
            if (i2 <= 0) {
                return fMax;
            }
            fMax = Math.max(fMax, this._data[this._pos]);
            i2 = i3;
        }
    }

    public float min() {
        if (size() == 0) {
            throw new IllegalStateException("cannot find minimum of an empty list");
        }
        float[] fArr = this._data;
        int i = this._pos;
        float fMin = fArr[i - 1];
        int i2 = i - 1;
        while (true) {
            int i3 = i2 - 1;
            if (i2 <= 0) {
                return fMin;
            }
            fMin = Math.min(fMin, this._data[this._pos]);
            i2 = i3;
        }
    }

    public String toString() {
        final StringBuffer stringBuffer = new StringBuffer("{");
        forEach(new TFloatProcedure() { // from class: gnu.trove.TFloatArrayList.1
            @Override // gnu.trove.TFloatProcedure
            public boolean execute(float f) {
                stringBuffer.append(f);
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
        this._data = new float[i];
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return;
            }
            add(objectInputStream.readFloat());
            i = i2;
        }
    }
}
