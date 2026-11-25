package com.reandroid.dex.base;

import com.reandroid.arsc.array.IntegerOffsetArray;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IntegerReference;
import java.io.IOException;

/* JADX INFO: loaded from: classes7.dex */
public class DexOffsetArray extends IntegerOffsetArray {
    private final IntegerReference itemCount;

    public DexOffsetArray(IntegerReference integerReference) {
        this.itemCount = integerReference;
    }

    @Override // com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        setSize(this.itemCount.get());
        super.onReadBytes(blockReader);
    }
}
