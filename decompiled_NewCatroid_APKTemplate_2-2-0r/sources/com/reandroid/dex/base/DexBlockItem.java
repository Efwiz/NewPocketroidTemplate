package com.reandroid.dex.base;

import com.reandroid.arsc.item.BlockItem;
import com.reandroid.dex.common.SectionTool;
import com.reandroid.dex.io.ByteReader;
import com.reandroid.dex.io.StreamUtil;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes7.dex */
public abstract class DexBlockItem extends BlockItem implements SectionTool {
    public DexBlockItem(int i) {
        super(i);
    }

    public static int writeUleb128(byte[] bArr, int i, int i2) {
        int i3 = 0;
        while ((((long) i2) & 4294967295L) > 127) {
            bArr[i + i3] = (byte) ((i2 & 127) | 128);
            i2 >>>= 7;
            i3++;
        }
        bArr[i + i3] = (byte) i2;
        return i3 + 1;
    }

    public static int readUleb128(InputStream inputStream) throws IOException {
        return readUleb128(StreamUtil.createByteReader(inputStream));
    }

    public static int readUleb128(byte[] bArr, int i) throws IOException {
        return readUleb128(StreamUtil.createByteReader(bArr, i));
    }

    public static int readUleb128(ByteReader byteReader) throws IOException {
        return readUleb128(byteReader, 4);
    }

    public static int readUleb128Large(ByteReader byteReader) throws IOException {
        return readUleb128(byteReader, 5);
    }

    public static int readUleb128(ByteReader byteReader, int i) throws IOException {
        int i2 = 0;
        int i3 = 128;
        int i4 = 0;
        while (i3 > 127 && i2 < i) {
            i3 = byteReader.read();
            i4 |= (i3 & 127) << (i2 * 7);
            i2++;
        }
        if (i3 > 127) {
            throw new IOException("Invalid uleb128 integer, size = " + i);
        }
        if (i2 != i || (i3 & 15) <= 7) {
            return i4;
        }
        throw new IOException("Encountered valid uleb128 that is out of range, size = " + i);
    }

    public static int readSleb128(ByteReader byteReader) throws IOException {
        int i = 128;
        int i2 = 0;
        int i3 = 0;
        while (i > 127 && i2 < 5) {
            i = byteReader.read();
            i3 |= (i & 127) << (i2 * 7);
            i2++;
        }
        if (i2 == 5 && i > 127) {
            throw new IOException("Invalid sleb128 integer");
        }
        int i4 = 32 - (i2 * 7);
        return (i3 << i4) >> i4;
    }

    public static int writeSleb128(byte[] bArr, int i, int i2) {
        int i3 = 0;
        if (i2 >= 0) {
            while (i2 > 63) {
                bArr[i + i3] = (byte) ((i2 & 127) | 128);
                i3++;
                i2 >>>= 7;
            }
            bArr[i + i3] = (byte) (i2 & 127);
        } else {
            while (i2 < -64) {
                bArr[i + i3] = (byte) ((i2 & 127) | 128);
                i3++;
                i2 >>= 7;
            }
            bArr[i + i3] = (byte) (i2 & 127);
        }
        return i3 + 1;
    }

    protected static long getSignedNumber(byte[] bArr, int i, int i2) {
        int i3 = i + i2;
        long j = 0;
        if (i3 > bArr.length) {
            return 0L;
        }
        int i4 = i3 - 1;
        int i5 = bArr[i4] & 255;
        while (i4 >= i) {
            j = (j << 8) | ((long) (bArr[i4] & 255));
            i4--;
        }
        return i5 > 127 ? (j - ((-1) >>> (64 - (i2 * 8)))) - 1 : j;
    }

    protected static long getUnsignedNumber(byte[] bArr, int i, int i2) {
        int i3 = i2 + i;
        long j = 0;
        if (i3 > bArr.length) {
            return 0L;
        }
        for (int i4 = i3 - 1; i4 >= i; i4--) {
            j = (j << 8) | ((long) (bArr[i4] & 255));
        }
        return j;
    }

    protected static void putNumber(byte[] bArr, int i, int i2, long j) {
        int i3 = i2 + i;
        if (i3 > bArr.length) {
            return;
        }
        while (i < i3) {
            bArr[i] = (byte) (255 & j);
            j >>>= 8;
            i++;
        }
    }
}
