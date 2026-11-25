package com.reandroid.arsc.item;

import com.reandroid.arsc.base.Block;

/* JADX INFO: loaded from: classes10.dex */
public class ReferenceBlock<T extends Block> implements ReferenceItem {
    private final T block;
    private final int offset;

    public ReferenceBlock(T t, int i) {
        this.block = t;
        this.offset = i;
    }

    public T getBlock() {
        return this.block;
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public void set(int i) {
        BlockItem.putInteger(this.block.getBytes(), this.offset, i);
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public int get() {
        return BlockItem.getInteger(this.block.getBytes(), this.offset);
    }

    @Override // com.reandroid.arsc.item.ReferenceItem
    public <T1 extends Block> T1 getReferredParent(Class<T1> cls) {
        T1 t1 = (T1) getBlock();
        return cls.isInstance(t1) ? t1 : (T1) getBlock().getParentInstance(cls);
    }

    public String toString() {
        return get() + ":" + this.block;
    }
}
