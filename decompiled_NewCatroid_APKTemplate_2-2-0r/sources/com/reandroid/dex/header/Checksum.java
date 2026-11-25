package com.reandroid.dex.header;

import com.reandroid.arsc.base.Block;
import com.reandroid.utils.HexUtil;
import java.util.zip.Adler32;

/* JADX INFO: loaded from: classes11.dex */
public class Checksum extends HeaderPiece {
    public Checksum() {
        super(4);
    }

    public int getValue() {
        return getInteger(0);
    }

    public void setValue(long j) {
        setSize(4);
        putInteger(0, (int) j);
    }

    public void update(Block block, byte[] bArr) {
        int iCountUpTo = block.countUpTo(this) + countBytes();
        Adler32 adler32 = new Adler32();
        adler32.update(bArr, iCountUpTo, bArr.length - iCountUpTo);
        setValue(adler32.getValue());
    }

    @Override // com.reandroid.dex.header.HeaderPiece, com.reandroid.arsc.item.ByteArray
    public String toString() {
        return HexUtil.toHex8(getValue());
    }
}
