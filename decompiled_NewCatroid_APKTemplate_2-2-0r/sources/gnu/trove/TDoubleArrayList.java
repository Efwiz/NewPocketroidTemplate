package gnu.trove;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

/* JADX INFO: loaded from: classes11.dex */
public class TDoubleArrayList implements Serializable, Cloneable {
    protected static final int DEFAULT_CAPACITY = 4;
    protected transient double[] _data;
    protected transient int _pos;

    public TDoubleArrayList() {
    }

    public TDoubleArrayList(int i) {
        this._data = new double[i];
        this._pos = 0;
    }

    public TDoubleArrayList(double[] dArr) {
        this(Math.max(dArr.length, 4));
        add(dArr);
    }

    public void ensureCapacity(int i) {
        if (this._data == null) {
            this._data = new double[Math.max(4, i)];
        }
        double[] dArr = this._data;
        if (i > dArr.length) {
            double[] dArr2 = new double[Math.max(dArr.length << 1, i)];
            double[] dArr3 = this._data;
            System.arraycopy(dArr3, 0, dArr2, 0, dArr3.length);
            this._data = dArr2;
        }
    }

    public int size() {
        return this._pos;
    }

    public boolean isEmpty() {
        return this._pos == 0;
    }

    public void trimToSize() {
        double[] dArr = this._data;
        if (dArr == null || dArr.length <= size()) {
            return;
        }
        int size = size();
        double[] dArr2 = new double[size];
        toNativeArray(dArr2, 0, size);
        this._data = dArr2;
    }

    public void add(double d) {
        ensureCapacity(this._pos + 1);
        double[] dArr = this._data;
        int i = this._pos;
        this._pos = i + 1;
        dArr[i] = d;
    }

    public void add(double[] dArr) {
        add(dArr, 0, dArr.length);
    }

    public void add(double[] dArr, int i, int i2) {
        ensureCapacity(this._pos + i2);
        System.arraycopy(dArr, i, this._data, this._pos, i2);
        this._pos += i2;
    }

    public void insert(int i, double d) {
        int i2 = this._pos;
        if (i == i2) {
            add(d);
            return;
        }
        ensureCapacity(i2 + 1);
        double[] dArr = this._data;
        System.arraycopy(dArr, i, dArr, i + 1, this._pos - i);
        this._data[i] = d;
        this._pos++;
    }

    public void insert(int i, double[] dArr) {
        insert(i, dArr, 0, dArr.length);
    }

    public void insert(int i, double[] dArr, int i2, int i3) {
        int i4 = this._pos;
        if (i == i4) {
            add(dArr, i2, i3);
            return;
        }
        ensureCapacity(i4 + i3);
        double[] dArr2 = this._data;
        System.arraycopy(dArr2, i, dArr2, i + i3, this._pos - i);
        System.arraycopy(dArr, i2, this._data, i, i3);
        this._pos += i3;
    }

    public double get(int i) {
        if (i >= this._pos) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        return this._data[i];
    }

    public double getQuick(int i) {
        return this._data[i];
    }

    public void set(int i, double d) {
        if (i < 0 || i >= this._pos) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        this._data[i] = d;
    }

    public double getSet(int i, double d) {
        if (i < 0 || i >= this._pos) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        double[] dArr = this._data;
        double d2 = dArr[i];
        dArr[i] = d;
        return d2;
    }

    public void set(int i, double[] dArr) {
        set(i, dArr, 0, dArr.length);
    }

    public void set(int i, double[] dArr, int i2, int i3) {
        if (i < 0 || i + i3 > this._pos) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        System.arraycopy(this._data, i, dArr, i2, i3);
    }

    public void setQuick(int i, double d) {
        this._data[i] = d;
    }

    public void clear() {
        this._data = null;
        this._pos = 0;
    }

    public void clear(int i) {
        this._data = new double[i];
        this._pos = 0;
    }

    public void reset() {
        fill(0.0d);
        this._pos = 0;
    }

    public void resetQuick() {
        this._pos = 0;
    }

    public double remove(int i) {
        double d = get(i);
        remove(i, 1);
        return d;
    }

    public void remove(int i, int i2) {
        int i3;
        if (i < 0 || i >= (i3 = this._pos)) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        if (i == 0) {
            double[] dArr = this._data;
            System.arraycopy(dArr, i2, dArr, 0, i3 - i2);
        } else if (i3 - i2 != i) {
            double[] dArr2 = this._data;
            int i4 = i + i2;
            System.arraycopy(dArr2, i4, dArr2, i, i3 - i4);
        }
        this._pos -= i2;
    }

    public void transformValues(TDoubleFunction tDoubleFunction) {
        int i = this._pos;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return;
            }
            double[] dArr = this._data;
            dArr[i2] = tDoubleFunction.execute(dArr[i2]);
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
        double[] dArr = this._data;
        double d = dArr[i];
        dArr[i] = dArr[i2];
        dArr[i2] = d;
    }

    public Object clone() {
        TDoubleArrayList tDoubleArrayList = null;
        double[] dArr = null;
        try {
            TDoubleArrayList tDoubleArrayList2 = (TDoubleArrayList) super.clone();
            try {
                double[] dArr2 = this._data;
                if (dArr2 != null) {
                    dArr = (double[]) dArr2.clone();
                }
                tDoubleArrayList2._data = dArr;
                return tDoubleArrayList2;
            } catch (CloneNotSupportedException unused) {
                tDoubleArrayList = tDoubleArrayList2;
                return tDoubleArrayList;
            }
        } catch (CloneNotSupportedException unused2) {
        }
    }

    public double[] toNativeArray() {
        return toNativeArray(0, this._pos);
    }

    public double[] toNativeArray(int i, int i2) {
        double[] dArr = new double[i2];
        toNativeArray(dArr, i, i2);
        return dArr;
    }

    public void toNativeArray(double[] dArr, int i, int i2) {
        if (i2 == 0) {
            return;
        }
        if (i < 0 || i >= this._pos) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        System.arraycopy(this._data, i, dArr, 0, i2);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TDoubleArrayList)) {
            return false;
        }
        TDoubleArrayList tDoubleArrayList = (TDoubleArrayList) obj;
        if (tDoubleArrayList.size() != size()) {
            return false;
        }
        int i = this._pos;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return true;
            }
            if (this._data[i2] != tDoubleArrayList._data[i2]) {
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

    public boolean forEach(TDoubleProcedure tDoubleProcedure) {
        for (int i = 0; i < this._pos; i++) {
            if (!tDoubleProcedure.execute(this._data[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean forEachDescending(TDoubleProcedure tDoubleProcedure) {
        int i = this._pos;
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return true;
            }
            if (!tDoubleProcedure.execute(this._data[i2])) {
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

    public void fill(double d) {
        if (isEmpty()) {
            return;
        }
        Arrays.fill(this._data, 0, this._pos, d);
    }

    public void fill(int i, int i2, double d) {
        if (i2 > this._pos) {
            ensureCapacity(i2);
            this._pos = i2;
        }
        if (isEmpty()) {
            return;
        }
        Arrays.fill(this._data, i, i2, d);
    }

    public int binarySearch(double d) {
        return binarySearch(d, 0, this._pos);
    }

    public int binarySearch(double d, int i, int i2) {
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        if (i2 > this._pos) {
            throw new ArrayIndexOutOfBoundsException(i2);
        }
        int i3 = i2 - 1;
        while (i <= i3) {
            int i4 = (i + i3) >> 1;
            double d2 = this._data[i4];
            if (d2 < d) {
                i = i4 + 1;
            } else {
                if (d2 <= d) {
                    return i4;
                }
                i3 = i4 - 1;
            }
        }
        return -(i + 1);
    }

    public int indexOf(double d) {
        return indexOf(0, d);
    }

    public int indexOf(int i, double d) {
        while (i < this._pos) {
            if (this._data[i] == d) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public int lastIndexOf(double d) {
        return lastIndexOf(this._pos, d);
    }

    public int lastIndexOf(int i, double d) {
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return -1;
            }
            if (this._data[i2] == d) {
                return i2;
            }
            i = i2;
        }
    }

    public boolean contains(double d) {
        return lastIndexOf(d) >= 0;
    }

    public TDoubleArrayList grep(TDoubleProcedure tDoubleProcedure) {
        TDoubleArrayList tDoubleArrayList = new TDoubleArrayList();
        for (int i = 0; i < this._pos; i++) {
            if (tDoubleProcedure.execute(this._data[i])) {
                tDoubleArrayList.add(this._data[i]);
            }
        }
        return tDoubleArrayList;
    }

    public TDoubleArrayList inverseGrep(TDoubleProcedure tDoubleProcedure) {
        TDoubleArrayList tDoubleArrayList = new TDoubleArrayList();
        for (int i = 0; i < this._pos; i++) {
            if (!tDoubleProcedure.execute(this._data[i])) {
                tDoubleArrayList.add(this._data[i]);
            }
        }
        return tDoubleArrayList;
    }

    public double max() {
        if (size() == 0) {
            throw new IllegalStateException("cannot find maximum of an empty list");
        }
        double[] dArr = this._data;
        int i = this._pos;
        double dMax = dArr[i - 1];
        int i2 = i - 1;
        while (true) {
            int i3 = i2 - 1;
            if (i2 <= 0) {
                return dMax;
            }
            dMax = Math.max(dMax, this._data[this._pos]);
            i2 = i3;
        }
    }

    public double min() {
        if (size() == 0) {
            throw new IllegalStateException("cannot find minimum of an empty list");
        }
        double[] dArr = this._data;
        int i = this._pos;
        double dMin = dArr[i - 1];
        int i2 = i - 1;
        while (true) {
            int i3 = i2 - 1;
            if (i2 <= 0) {
                return dMin;
            }
            dMin = Math.min(dMin, this._data[this._pos]);
            i2 = i3;
        }
    }

    public String toString() {
        final StringBuffer stringBuffer = new StringBuffer("{");
        forEach(new TDoubleProcedure() { // from class: gnu.trove.TDoubleArrayList.1
            @Override // gnu.trove.TDoubleProcedure
            public boolean execute(double d) {
                stringBuffer.append(d);
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
        this._data = new double[i];
        while (true) {
            int i2 = i - 1;
            if (i <= 0) {
                return;
            }
            add(objectInputStream.readDouble());
            i = i2;
        }
    }
}
