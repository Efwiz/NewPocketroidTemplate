package com.reandroid.dex.base;

import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IntegerReference;
import java.io.IOException;

/* JADX INFO: loaded from: classes7.dex */
public abstract class Le128 extends DexBlockItem implements IntegerReference {
    private int value;

    protected abstract int readLe128(BlockReader blockReader) throws IOException;

    protected abstract void writeValue(int i);

    public Le128() {
        super(1);
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public int get() {
        return this.value;
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public void set(int i) {
        if (i == this.value) {
            return;
        }
        this.value = i;
        writeValue(i);
    }

    @Override // com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        this.value = readLe128(blockReader);
    }

    public String toString() {
        return Integer.toString(get());
    }
}
