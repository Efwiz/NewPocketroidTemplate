package com.reandroid.arsc.item;

import java.util.AbstractList;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class IntegerArrayBlock extends ShortArrayBlock {
    public final List<Integer> toList() {
        return new AbstractList<Integer>() { // from class: com.reandroid.arsc.item.IntegerArrayBlock.1
            @Override // java.util.AbstractList, java.util.List
            public Integer get(int i) {
                return Integer.valueOf(IntegerArrayBlock.this.get(i));
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return IntegerArrayBlock.this.size();
            }
        };
    }

    @Override // com.reandroid.arsc.item.ShortArrayBlock, com.reandroid.common.IntegerArray
    public void setSize(int i) {
        if (i < 0) {
            i = 0;
        }
        int i2 = i * 4;
        if (i2 < 0) {
            throw new IndexOutOfBoundsException("Huge integers size = " + i + ", parent = " + getParent());
        }
        setBytesLength(i2);
    }

    @Override // com.reandroid.arsc.item.ShortArrayBlock, com.reandroid.common.IntegerArray
    public int get(int i) {
        if (i < 0 || i >= size()) {
            return 0;
        }
        return getInteger(getBytesInternal(), i * 4);
    }

    @Override // com.reandroid.arsc.item.ShortArrayBlock, com.reandroid.common.IntegerArray
    public int size() {
        return getBytesLength() / 4;
    }

    @Override // com.reandroid.arsc.item.ShortArrayBlock, com.reandroid.common.IntegerArray
    public void put(int i, int i2) {
        putInteger(getBytesInternal(), i * 4, i2);
    }
}
