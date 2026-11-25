package com.reandroid.dex.base;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.Creator;
import com.reandroid.arsc.container.BlockList;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IntegerReference;
import java.io.IOException;

/* JADX INFO: loaded from: classes7.dex */
public class CountedList<T extends Block> extends BlockList<T> {
    private final IntegerReference itemCount;

    public CountedList(IntegerReference integerReference, Creator<T> creator) {
        super(creator);
        this.itemCount = integerReference;
    }

    @Override // com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        setSize(this.itemCount.get());
        super.readChildes(blockReader);
    }

    @Override // com.reandroid.arsc.container.BlockList
    public void setSize(int i) {
        super.setSize(i);
        if (i != this.itemCount.get()) {
            this.itemCount.set(i);
        }
    }

    @Override // com.reandroid.arsc.container.BlockList
    protected void onRefreshed() {
        this.itemCount.set(size());
    }
}
