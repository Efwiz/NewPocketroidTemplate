package com.reandroid.dex.header;

import com.reandroid.arsc.base.Block;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.SHA1;

/* JADX INFO: loaded from: classes11.dex */
public class Signature extends HeaderPiece {
    public Signature() {
        super(20);
    }

    public void update(Block block, byte[] bArr) {
        SHA1 sha1 = new SHA1();
        int iCountUpTo = block.countUpTo(this) + countBytes();
        sha1.update(bArr, iCountUpTo, bArr.length - iCountUpTo);
        sha1.digest(getBytesInternal());
    }

    public String getHex() {
        return HexUtil.toHexString(getBytesInternal());
    }

    @Override // com.reandroid.dex.header.HeaderPiece, com.reandroid.arsc.item.ByteArray
    public String toString() {
        return getHex();
    }
}
