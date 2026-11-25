package com.reandroid.arsc.item;

import com.reandroid.utils.HexUtil;

/* JADX INFO: loaded from: classes10.dex */
public class LongItem extends BlockItem {
    private long mCache;

    public LongItem() {
        super(8);
    }

    public void set(long j) {
        if (j == this.mCache) {
            return;
        }
        this.mCache = j;
        putLong(getBytesInternal(), 0, j);
    }

    public long get() {
        return this.mCache;
    }

    public String toHex() {
        return HexUtil.toHex(get(), 16);
    }

    @Override // com.reandroid.arsc.item.BlockItem
    protected void onBytesChanged() {
        this.mCache = getLong(getBytesInternal(), 0);
    }

    public String toString() {
        return String.valueOf(get());
    }
}
