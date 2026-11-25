package com.reandroid.arsc.base;

import com.reandroid.arsc.base.Block;

/* JADX INFO: loaded from: classes10.dex */
public interface Creator<T extends Block> extends BlockArrayCreator<T>, BlockCreator<T> {
    @Override // com.reandroid.arsc.base.BlockArrayCreator
    T[] newArrayInstance(int i);

    @Override // com.reandroid.arsc.base.BlockCreator
    T newInstance();

    default T newInstanceAt(int i) {
        return (T) newInstance();
    }
}
