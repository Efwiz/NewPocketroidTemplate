package com.reandroid.arsc.item;

import com.reandroid.utils.HexUtil;

/* JADX INFO: loaded from: classes10.dex */
public class ShortItem extends BlockItem implements IntegerReference {
    private int mCache;

    public ShortItem() {
        super(2);
    }

    public ShortItem(short s) {
        this();
        set(s);
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public void set(int i) {
        if (i == this.mCache) {
            return;
        }
        this.mCache = i;
        byte[] bytesInternal = getBytesInternal();
        bytesInternal[1] = (byte) ((i >>> 8) & 255);
        bytesInternal[0] = (byte) (i & 255);
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public int get() {
        return this.mCache;
    }

    public void set(short s) {
        set(s & 65535);
    }

    public int unsignedInt() {
        return get();
    }

    public short getShort() {
        return (short) this.mCache;
    }

    public String toHex() {
        return HexUtil.toHex4(getShort());
    }

    @Override // com.reandroid.arsc.item.BlockItem
    protected void onBytesChanged() {
        this.mCache = readShortBytes();
    }

    private int readShortBytes() {
        byte[] bytesInternal = getBytesInternal();
        return ((bytesInternal[1] & 255) << 8) | (bytesInternal[0] & 255);
    }

    public String toString() {
        return String.valueOf(get());
    }
}
