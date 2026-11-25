package com.reandroid.dex.base;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.BlockArray;
import com.reandroid.arsc.base.Creator;

/* JADX INFO: loaded from: classes7.dex */
public class CreatorArray<T extends Block> extends BlockArray<T> {
    @Override // com.reandroid.arsc.container.BlockList
    protected void onRefreshed() {
    }

    public CreatorArray(Creator<T> creator) {
        super(creator);
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
    public T[] newArrayInstance(int i) {
        return (T[]) getCreator().newArrayInstance(i);
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
    public T newInstance() {
        return (T) getCreator().newInstance();
    }
}
