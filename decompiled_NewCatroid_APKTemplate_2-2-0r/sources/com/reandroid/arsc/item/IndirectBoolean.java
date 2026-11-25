package com.reandroid.arsc.item;

/* JADX INFO: loaded from: classes10.dex */
public class IndirectBoolean implements BooleanReference {
    private final int bitIndex;
    private final BlockItem blockItem;
    private final int byteOffset;

    public IndirectBoolean(BlockItem blockItem, int i, int i2) {
        this.blockItem = blockItem;
        this.byteOffset = i;
        this.bitIndex = i2;
    }

    @Override // com.reandroid.arsc.item.BooleanReference
    public boolean get() {
        return BlockItem.getBit(this.blockItem.getBytesInternal(), this.byteOffset, this.bitIndex);
    }

    @Override // com.reandroid.arsc.item.BooleanReference
    public void set(boolean z) {
        BlockItem.putBit(this.blockItem.getBytesInternal(), this.byteOffset, this.bitIndex, z);
    }

    public String toString() {
        return Boolean.toString(get());
    }
}
