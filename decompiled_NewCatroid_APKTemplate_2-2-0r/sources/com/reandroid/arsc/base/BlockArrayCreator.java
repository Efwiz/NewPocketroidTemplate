package com.reandroid.arsc.base;

import com.reandroid.arsc.base.Block;

/* JADX INFO: loaded from: classes10.dex */
public interface BlockArrayCreator<T extends Block> extends BlockCreator<T> {
    T[] newArrayInstance(int i);
}
