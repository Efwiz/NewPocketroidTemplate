package com.reandroid.dex.header;

/* JADX INFO: loaded from: classes11.dex */
public class Endian extends HeaderPiece {
    public static final int BIG = 2018915346;
    public static final int LITTLE = 305419896;

    public Endian() {
        super(12);
        putInteger(0, 305419896);
    }

    public int getType() {
        return getInteger(0);
    }

    public void setType(int i) {
        setSize(12);
        putInteger(0, i);
        putInteger(4, 0);
        putInteger(8, 0);
    }

    public boolean isBig() {
        return getType() == 2018915346;
    }

    public boolean isLittle() {
        return getType() == 305419896;
    }

    @Override // com.reandroid.dex.header.HeaderPiece, com.reandroid.arsc.item.ByteArray
    public String toString() {
        if (isBig()) {
            return "BIG";
        }
        if (isLittle()) {
            return "LITTLE";
        }
        return printHex(getBytesInternal());
    }
}
