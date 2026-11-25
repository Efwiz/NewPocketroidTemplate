package com.reandroid.dex.base;

import com.reandroid.arsc.array.OffsetArray;
import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.utils.StringsUtil;
import java.io.IOException;

/* JADX INFO: loaded from: classes7.dex */
public class IndexAndOffsetArray extends DexBlockItem implements OffsetArray {
    private final IntegerReference itemCount;

    public IndexAndOffsetArray(IntegerReference integerReference) {
        super(0);
        this.itemCount = integerReference;
    }

    public int[] getOffsetsForIndex(int i) {
        int size = size();
        int[] iArr = new int[size];
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            if (i == getIndexEntry(i3)) {
                iArr[i2] = getOffset(i3);
                i2++;
            }
        }
        int[] iArr2 = new int[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            iArr2[i4] = iArr[i4];
        }
        return iArr2;
    }

    public IntegerPair[] toIntegerPairArray() {
        int size = size();
        IntegerPair[] integerPairArr = new IntegerPair[size];
        for (int i = 0; i < size; i++) {
            integerPairArr[i] = get(i);
        }
        return integerPairArr;
    }

    public IntegerPair get(int i) {
        return new IndirectBlockIntegerPair(this, i * 8);
    }

    private int getIndexEntry(int i) {
        return Block.getInteger(getBytesInternal(), i * 8);
    }

    public void setIndexEntry(int i, int i2) {
        Block.putInteger(getBytesInternal(), i * 8, i2);
    }

    @Override // com.reandroid.arsc.array.OffsetArray
    public int getOffset(int i) {
        return Block.getInteger(getBytesInternal(), (i * 8) + 4);
    }

    @Override // com.reandroid.arsc.array.OffsetArray
    public void setOffset(int i, int i2) {
        Block.putInteger(getBytesInternal(), (i * 8) + 4, i2);
    }

    @Override // com.reandroid.arsc.array.OffsetArray
    public int[] getOffsets() {
        int size = size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = getOffset(i);
        }
        return iArr;
    }

    public int[] getIndexEntries() {
        int size = size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = getIndexEntry(i);
        }
        return iArr;
    }

    @Override // com.reandroid.arsc.array.OffsetArray
    public int size() {
        return countBytes() / 8;
    }

    @Override // com.reandroid.arsc.array.OffsetArray
    public void setSize(int i) {
        if (i < 0) {
            i = 0;
        }
        setBytesLength(i * 8, false);
        this.itemCount.set(i);
    }

    @Override // com.reandroid.arsc.array.OffsetArray
    public void clear() {
        setSize(0);
    }

    @Override // com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        setBytesLength(this.itemCount.get() * 8, false);
        super.onReadBytes(blockReader);
    }

    public String toString() {
        return StringsUtil.toString(toIntegerPairArray());
    }
}
