package com.reandroid.dex.base;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.Creator;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IntegerReference;
import java.io.IOException;

/* JADX INFO: loaded from: classes7.dex */
public class CountedArray<T extends Block> extends CreatorArray<T> {
    private final IntegerReference itemCount;

    public CountedArray(IntegerReference integerReference, Creator<T> creator) {
        super(creator);
        this.itemCount = integerReference;
    }

    @Override // com.reandroid.arsc.base.BlockArray, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        setSize(this.itemCount.get());
        super.onReadBytes(blockReader);
    }

    @Override // com.reandroid.dex.base.CreatorArray, com.reandroid.arsc.container.BlockList
    protected void onRefreshed() {
        this.itemCount.set(getCount());
    }
}
