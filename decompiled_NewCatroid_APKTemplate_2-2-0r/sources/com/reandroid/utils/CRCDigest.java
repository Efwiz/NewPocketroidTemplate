package com.reandroid.utils;

import java.io.OutputStream;

/* JADX INFO: loaded from: classes11.dex */
public class CRCDigest extends OutputStream {
    private static final long[] CRC_TABLE = makeCrcTable();
    private long mCrc = 4294967295L;
    private long mLength;
    private byte[] oneByte;

    public long getValue() {
        return this.mCrc ^ 4294967295L;
    }

    public long getLength() {
        return this.mLength;
    }

    @Override // java.io.OutputStream
    public void write(int i) {
        write(oneByte(i), 0, 1);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) {
        long j = this.mCrc;
        int i3 = i + i2;
        while (i < i3) {
            j = (j >> 8) ^ CRC_TABLE[(int) ((((long) (bArr[i] & 255)) ^ j) & 255)];
            i++;
        }
        this.mCrc = j;
        this.mLength += (long) i2;
    }

    private byte[] oneByte(int i) {
        byte[] bArr = this.oneByte;
        if (bArr == null) {
            bArr = new byte[1];
            this.oneByte = bArr;
        }
        bArr[0] = (byte) i;
        return bArr;
    }

    public void update(int i) {
        write(oneByte(i), 0, 1);
    }

    public void update(byte[] bArr) {
        write(bArr, 0, bArr.length);
    }

    public void update(byte[] bArr, int i, int i2) {
        write(bArr, i, i2);
    }

    public String toString() {
        return HexUtil.toHex((String) null, getValue(), 8);
    }

    private static long[] makeCrcTable() {
        long[] jArr = new long[256];
        for (int i = 0; i < 256; i++) {
            long j = i;
            for (int i2 = 0; i2 < 8; i2++) {
                j = (j & 1) == 1 ? (j >> 1) ^ 3988292384L : j >> 1;
            }
            jArr[i] = j;
        }
        return jArr;
    }
}
