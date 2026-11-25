package com.reandroid.arsc.array;

import com.reandroid.arsc.base.BlockArray;
import com.reandroid.arsc.chunk.SpecBlock;

/* JADX INFO: loaded from: classes10.dex */
public class SpecBlockArray extends BlockArray<SpecBlock> {
    @Override // com.reandroid.arsc.container.BlockList
    protected void onRefreshed() {
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
    public SpecBlock newInstance() {
        return new SpecBlock();
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
    public SpecBlock[] newArrayInstance(int i) {
        return new SpecBlock[i];
    }
}
