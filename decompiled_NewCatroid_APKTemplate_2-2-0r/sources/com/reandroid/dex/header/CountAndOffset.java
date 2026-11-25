package com.reandroid.dex.header;

import com.reandroid.dex.base.BlockIntegerPair;

/* JADX INFO: loaded from: classes11.dex */
public class CountAndOffset extends BlockIntegerPair {
    public int getCount() {
        return getFirst().get();
    }

    public void setCount(int i) {
        getFirst().set(i);
    }

    public int getOffset() {
        return getSecond().get();
    }

    public void setOffset(int i) {
        getSecond().set(i);
    }
}
