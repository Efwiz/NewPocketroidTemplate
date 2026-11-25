package com.reandroid.arsc.item;

import com.reandroid.arsc.base.Block;

/* JADX INFO: loaded from: classes10.dex */
public class IndirectInteger extends IndirectItem<BlockItem> implements IntegerReference {
    public IndirectInteger(BlockItem blockItem, int i) {
        super(blockItem, i);
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public int get() {
        return Block.getInteger(getBytesInternal(), getOffset());
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public void set(int i) {
        Block.putInteger(getBytesInternal(), getOffset(), i);
    }

    public boolean isNull() {
        return getBytesInternal().length - getOffset() < 4;
    }

    public String toString() {
        if (isNull()) {
            return "NULL";
        }
        return Integer.toString(get());
    }
}
