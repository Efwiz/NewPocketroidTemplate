package com.reandroid.dex.header;

import com.reandroid.arsc.item.ByteArray;

/* JADX INFO: loaded from: classes11.dex */
public class Magic extends HeaderPiece {
    public static final byte[] DEFAULT_BYTES = {100, 101, 120, 10};

    @Override // com.reandroid.dex.header.HeaderPiece, com.reandroid.arsc.item.ByteArray
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public Magic() {
        super.set((byte[]) DEFAULT_BYTES.clone());
    }

    public void resetDefault() {
        super.set((byte[]) DEFAULT_BYTES.clone());
    }

    public boolean isDefault() {
        return ByteArray.equals(getBytesInternal(), DEFAULT_BYTES);
    }
}
