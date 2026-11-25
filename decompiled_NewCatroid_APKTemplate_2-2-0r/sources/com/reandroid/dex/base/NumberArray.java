package com.reandroid.dex.base;

import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.StringsUtil;
import java.io.IOException;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes7.dex */
public class NumberArray extends DexBlockItem {
    private final IntegerReference itemCount;
    private List<Number> numberList;
    private final IntegerReference widthReference;

    public NumberArray(IntegerReference integerReference, IntegerReference integerReference2) {
        super(0);
        this.widthReference = integerReference;
        this.itemCount = integerReference2;
    }

    public Iterator<IntegerReference> getReferences() {
        return new Iterator<IntegerReference>() { // from class: com.reandroid.dex.base.NumberArray.1
            private int mIndex;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.mIndex < NumberArray.this.size();
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.Iterator
            public IntegerReference next() {
                IntegerReference reference = NumberArray.this.getReference(this.mIndex);
                this.mIndex++;
                return reference;
            }
        };
    }

    public IntegerReference getReference(int i) {
        if (i >= size()) {
            return null;
        }
        return new Data(this, i);
    }

    public short[] getShortArray() {
        int width = getWidth();
        int size = size();
        short[] sArr = new short[size];
        byte[] bytesInternal = getBytesInternal();
        for (int i = 0; i < size; i++) {
            sArr[i] = getShort(bytesInternal, i * width);
        }
        return sArr;
    }

    public int[] getByteUnsignedArray() {
        int size = size();
        int[] iArr = new int[size];
        byte[] bytesInternal = getBytesInternal();
        for (int i = 0; i < size; i++) {
            iArr[i] = bytesInternal[i] & 255;
        }
        return iArr;
    }

    public int[] getShortUnsignedArray() {
        int width = getWidth();
        int size = size();
        int[] iArr = new int[size];
        byte[] bytesInternal = getBytesInternal();
        for (int i = 0; i < size; i++) {
            iArr[i] = getShortUnsigned(bytesInternal, i * width);
        }
        return iArr;
    }

    public int[] getIntArray() {
        int width = getWidth();
        int size = size();
        int[] iArr = new int[size];
        byte[] bytesInternal = getBytesInternal();
        for (int i = 0; i < size; i++) {
            iArr[i] = getInteger(bytesInternal, i * width);
        }
        return iArr;
    }

    public long[] getLongArray() {
        int width = getWidth();
        int size = size();
        long[] jArr = new long[size];
        byte[] bytesInternal = getBytesInternal();
        for (int i = 0; i < size; i++) {
            jArr[i] = getLong(bytesInternal, i * width);
        }
        return jArr;
    }

    public int getByteUnsigned(int i) {
        return getBytesInternal()[i * getWidth()] & 255;
    }

    public void put(byte[] bArr) {
        int size = size();
        int length = bArr.length;
        ensureSize(size + length);
        for (int i = 0; i < length; i++) {
            put(size + i, bArr[i] & 255);
        }
    }

    public void put(short[] sArr) {
        int size = size();
        int length = sArr.length;
        ensureSize(size + length);
        for (int i = 0; i < length; i++) {
            put(size + i, sArr[i] & 65535);
        }
    }

    public void put(int[] iArr) {
        int size = size();
        int length = iArr.length;
        ensureSize(size + length);
        for (int i = 0; i < length; i++) {
            put(size + i, iArr[i]);
        }
    }

    public void put(int i, int i2) {
        validateValueRange(i2);
        ensureSize(i + 1);
        int width = getWidth();
        int i3 = i * width;
        byte[] bytesInternal = getBytesInternal();
        if (width < 2) {
            getBytesInternal()[i3] = (byte) i2;
            return;
        }
        if (width < 4) {
            putShort(bytesInternal, i3, i2);
        } else if (width == 4) {
            putInteger(bytesInternal, i3, i2);
        } else {
            putLong(bytesInternal, i3, 4294967295L & ((long) i2));
        }
    }

    private void validateValueRange(int i) {
        int i2 = 0;
        int i3 = i;
        while (i3 != 0) {
            i3 >>>= 8;
            i2++;
        }
        if (i2 > getWidth()) {
            throw new DexException("Value out of range width = " + getWidth() + ", value = " + HexUtil.toHex(i, 1));
        }
    }

    public void putLong(long[] jArr) {
        int size = size();
        int length = jArr.length;
        ensureSize(size + length);
        for (int i = 0; i < length; i++) {
            putLong(size + i, jArr[i]);
        }
    }

    public void putLong(int i, long j) {
        ensureSize(i + 1);
        putLong(getBytesInternal(), i * getWidth(), j);
    }

    public int getShortUnsigned(int i) {
        return getShortUnsigned(getBytesInternal(), i * getWidth());
    }

    public byte getByte(int i) {
        return getBytesInternal()[i * getWidth()];
    }

    public short getShort(int i) {
        return getShort(getBytesInternal(), i * getWidth());
    }

    public int getInteger(int i) {
        return getInteger(getBytesInternal(), i * getWidth());
    }

    public void setInteger(int i, int i2) {
        putInteger(getBytesInternal(), i * getWidth(), i2);
    }

    public long getLong(int i) {
        return getLong(getBytesInternal(), i * getWidth());
    }

    public List<Number> toList() {
        if (this.numberList == null) {
            this.numberList = new AbstractList<Number>() { // from class: com.reandroid.dex.base.NumberArray.2
                @Override // java.util.AbstractList, java.util.List
                public Number get(int i) {
                    return NumberArray.this.getNumber(i);
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                public int size() {
                    return NumberArray.this.size();
                }
            };
        }
        return this.numberList;
    }

    public Number getNumber(int i) {
        int width = getWidth();
        if (width == 1) {
            return Byte.valueOf(getBytesInternal()[i]);
        }
        int i2 = i * width;
        if (width == 2) {
            return Integer.valueOf(getShortUnsigned(getBytesInternal(), i2));
        }
        if (width == 4) {
            return Integer.valueOf(getInteger(getBytesInternal(), i2));
        }
        return Long.valueOf(getLong(getBytesInternal(), i2));
    }

    public int getAsInteger(int i) {
        int width = getWidth();
        if (width < 2) {
            return getByteUnsigned(i);
        }
        if (width < 4) {
            return getShortUnsigned(i);
        }
        if (width == 4) {
            return getInteger(i);
        }
        return (int) getLong(i);
    }

    public int[] getAsIntegers() {
        int size = size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = getAsInteger(i);
        }
        return iArr;
    }

    public int size() {
        return countBytes() / getWidth();
    }

    public void ensureSize(int i) {
        if (i > size()) {
            setSize(i);
        }
    }

    public void setSize(int i) {
        setBytesLength(getWidth() * i, false);
        this.itemCount.set(i);
    }

    public int getWidth() {
        int i = this.widthReference.get();
        if (i == 0) {
            return 1;
        }
        return i;
    }

    public void setWidth(int i) {
        if (i == this.widthReference.get()) {
            return;
        }
        if (size() == 0) {
            this.widthReference.set(i);
        } else if (getWidth() > 4) {
            changeWidthOfLong(i);
        } else {
            changeWidthOfInt(i);
        }
    }

    private void changeWidthOfInt(int i) {
        int[] asIntegers = getAsIntegers();
        setSize(0);
        this.widthReference.set(i);
        ensureSize(asIntegers.length);
        put(asIntegers);
    }

    private void changeWidthOfLong(int i) {
        long[] longArray = getLongArray();
        setSize(0);
        this.widthReference.set(i);
        ensureSize(longArray.length);
        putLong(longArray);
    }

    @Override // com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        int i = this.itemCount.get();
        int i2 = this.widthReference.get();
        if (i2 == 0) {
            this.itemCount.set(0);
            i2 = 1;
            i = 0;
        }
        setBytesLength(i * i2, false);
        super.onReadBytes(blockReader);
    }

    public void merge(NumberArray numberArray) {
        setWidth(numberArray.getWidth());
        setSize(numberArray.size());
        byte[] bytesInternal = numberArray.getBytesInternal();
        byte[] bytesInternal2 = getBytesInternal();
        int length = bytesInternal.length;
        for (int i = 0; i < length; i++) {
            bytesInternal2[i] = bytesInternal[i];
        }
    }

    public String toString() {
        return "width=" + getWidth() + ", " + StringsUtil.toString(toList());
    }

    static class Data implements IntegerReference {
        private final int index;
        private final NumberArray numberArray;

        public Data(NumberArray numberArray, int i) {
            this.numberArray = numberArray;
            this.index = i;
        }

        @Override // com.reandroid.arsc.item.IntegerReference
        public int get() {
            return this.numberArray.getAsInteger(this.index);
        }

        @Override // com.reandroid.arsc.item.IntegerReference
        public void set(int i) {
            this.numberArray.put(this.index, i);
        }
    }
}
