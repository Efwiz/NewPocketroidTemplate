package com.reandroid.dex.header;

import com.reandroid.arsc.item.ByteArray;
import com.reandroid.utils.HexUtil;
import io.netty.util.internal.StringUtil;

/* JADX INFO: loaded from: classes11.dex */
class HeaderPiece extends ByteArray {
    private static boolean isPrintable(char c) {
        if (c <= 'z' && c >= 'a') {
            return true;
        }
        if (c > 'Z' || c < 'A') {
            return c <= '9' && c >= '0';
        }
        return true;
    }

    private static char toChar(byte b) {
        return (char) (b & 255);
    }

    HeaderPiece() {
    }

    HeaderPiece(int i) {
        super(i);
    }

    @Override // com.reandroid.arsc.item.ByteArray
    public String toString() {
        return printChars(getBytesInternal());
    }

    static String printHex(byte[] bArr) {
        if (bArr == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bArr.length; i++) {
            if (i != 0) {
                sb.append(StringUtil.SPACE);
            }
            sb.append(HexUtil.toHex2(null, bArr[i]));
        }
        return sb.toString();
    }

    static String printChars(byte[] bArr) {
        if (bArr == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            char c = toChar(b);
            if (isPrintable(c)) {
                sb.append(c);
            } else {
                sb.append(HexUtil.toHex2(b));
            }
        }
        return sb.toString();
    }
}
