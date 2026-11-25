package com.reandroid.arsc.item;

import com.reandroid.utils.HexUtil;

/* JADX INFO: loaded from: classes10.dex */
public class ByteItem extends BlockItem {
    public ByteItem() {
        super(1);
    }

    public boolean getBit(int i) {
        return ((get() >> i) & 1) == 1;
    }

    public void putBit(int i, boolean z) {
        byte b = get();
        int i2 = b >> i;
        set((byte) (((255 >> (8 - i)) & b) | ((z ? i2 | 1 : i2 & 254) << i)));
    }

    public void set(byte b) {
        getBytesInternal()[0] = b;
    }

    public byte get() {
        return getBytesInternal()[0];
    }

    public int unsignedInt() {
        return get() & 255;
    }

    public String toHex() {
        return HexUtil.toHex2(get());
    }

    public String toString() {
        return String.valueOf((int) get());
    }
}
