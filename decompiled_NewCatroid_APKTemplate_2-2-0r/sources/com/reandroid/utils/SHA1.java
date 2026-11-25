package com.reandroid.utils;

/* JADX INFO: loaded from: classes11.dex */
public class SHA1 {
    private static final int round1_kt = 1518500249;
    private static final int round2_kt = 1859775393;
    private static final int round3_kt = -1894007588;
    private static final int round4_kt = -899497514;
    private int bufferOffset;
    private long bytesProcessed;
    private byte[] oneByte;
    private final byte[] padding;
    private final byte[] buffer = new byte[64];
    private final int[] WORD = new int[80];
    private final int[] state = new int[5];

    public SHA1() {
        byte[] bArr = new byte[136];
        this.padding = bArr;
        bArr[0] = -128;
        resetState();
    }

    public void update(byte b) {
        if (this.oneByte == null) {
            this.oneByte = new byte[1];
        }
        byte[] bArr = this.oneByte;
        bArr[0] = b;
        update(bArr, 0, 1);
    }

    public void update(byte[] bArr, int i, int i2) {
        if (this.bytesProcessed < 0) {
            reset();
        }
        this.bytesProcessed += (long) i2;
        int i3 = this.bufferOffset;
        if (i3 != 0) {
            int iMin = NumbersUtil.min(i2, 64 - i3);
            System.arraycopy(bArr, i, this.buffer, this.bufferOffset, iMin);
            int i4 = this.bufferOffset + iMin;
            this.bufferOffset = i4;
            i += iMin;
            i2 -= iMin;
            if (i4 >= 64) {
                compressBytes(this.buffer, 0);
                this.bufferOffset = 0;
            }
        }
        if (i2 >= 64) {
            int i5 = i2 + i;
            i = compressMultiBlock(bArr, i, i5 - 64);
            i2 = i5 - i;
        }
        if (i2 > 0) {
            System.arraycopy(bArr, i, this.buffer, 0, i2);
            this.bufferOffset = i2;
        }
    }

    public void reset() {
        if (this.bytesProcessed != 0) {
            resetState();
            fillZero(this.WORD);
            this.bufferOffset = 0;
            this.bytesProcessed = 0L;
            fillZero(this.buffer);
        }
    }

    public byte[] digest() {
        byte[] bArr = new byte[20];
        digest(bArr);
        return bArr;
    }

    public void digest(byte[] bArr) {
        digest(bArr, 0);
    }

    public void digest(byte[] bArr, int i) {
        if (this.bytesProcessed < 0) {
            reset();
        }
        digestBytes(bArr, i);
        this.bytesProcessed = -1L;
    }

    private void digestBytes(byte[] bArr, int i) {
        long j = this.bytesProcessed;
        long j2 = j << 3;
        int i2 = ((int) j) & 63;
        update(this.padding, 0, i2 < 56 ? 56 - i2 : 120 - i2);
        writeBigInt((int) (j2 >>> 32), this.buffer, 56);
        writeBigInt((int) j2, this.buffer, 60);
        compressBytes(this.buffer, 0);
        writeBigInt(this.state, bArr, i);
    }

    private void resetState() {
        int[] iArr = this.state;
        iArr[0] = 1732584193;
        iArr[1] = -271733879;
        iArr[2] = -1732584194;
        iArr[3] = 271733878;
        iArr[4] = -1009589776;
    }

    private int compressMultiBlock(byte[] bArr, int i, int i2) {
        while (i <= i2) {
            compressBytes(bArr, i);
            i += 64;
        }
        return i;
    }

    private void compressBytes(byte[] bArr, int i) {
        int[] iArr = this.WORD;
        for (int i2 = 0; i2 < 16; i2++) {
            iArr[i2] = getReversedInteger(bArr, (i2 * 4) + i);
        }
        compressWord(iArr);
    }

    private void compressWord(int[] iArr) {
        int i;
        int i2;
        int i3;
        for (int i4 = 16; i4 <= 79; i4++) {
            int i5 = ((iArr[i4 - 3] ^ iArr[i4 - 8]) ^ iArr[i4 - 14]) ^ iArr[i4 - 16];
            iArr[i4] = (i5 >>> 31) | (i5 << 1);
        }
        int[] iArr2 = this.state;
        int i6 = iArr2[0];
        int i7 = iArr2[1];
        int i8 = iArr2[2];
        int i9 = iArr2[3];
        int i10 = iArr2[4];
        int i11 = 0;
        while (true) {
            i = 20;
            if (i11 >= 20) {
                break;
            }
            int i12 = round1_kt + ((i6 << 5) | (i6 >>> 27)) + ((i7 & i8) | ((~i7) & i9)) + i10 + iArr[i11];
            int i13 = (i7 >>> 2) | (i7 << 30);
            i11++;
            i7 = i6;
            i6 = i12;
            i10 = i9;
            i9 = i8;
            i8 = i13;
        }
        while (true) {
            i2 = 40;
            if (i >= 40) {
                break;
            }
            int i14 = round2_kt + ((i6 << 5) | (i6 >>> 27)) + ((i7 ^ i8) ^ i9) + i10 + iArr[i];
            int i15 = (i7 >>> 2) | (i7 << 30);
            i++;
            i7 = i6;
            i6 = i14;
            i10 = i9;
            i9 = i8;
            i8 = i15;
        }
        while (true) {
            i3 = 60;
            if (i2 >= 60) {
                break;
            }
            int i16 = round3_kt + ((i6 << 5) | (i6 >>> 27)) + (((i8 | i9) & i7) | (i8 & i9)) + i10 + iArr[i2];
            int i17 = (i7 >>> 2) | (i7 << 30);
            i2++;
            i7 = i6;
            i6 = i16;
            i10 = i9;
            i9 = i8;
            i8 = i17;
        }
        while (i3 < 80) {
            int i18 = round4_kt + ((i6 << 5) | (i6 >>> 27)) + ((i7 ^ i8) ^ i9) + i10 + iArr[i3];
            int i19 = (i7 >>> 2) | (i7 << 30);
            i3++;
            i7 = i6;
            i6 = i18;
            i10 = i9;
            i9 = i8;
            i8 = i19;
        }
        iArr2[0] = iArr2[0] + i6;
        iArr2[1] = iArr2[1] + i7;
        iArr2[2] = iArr2[2] + i8;
        iArr2[3] = iArr2[3] + i9;
        iArr2[4] = iArr2[4] + i10;
    }

    private void writeBigInt(int[] iArr, byte[] bArr, int i) {
        int i2 = i + 20;
        int i3 = 0;
        while (i < i2) {
            putReversedInteger(bArr, i, iArr[i3]);
            i += 4;
            i3++;
        }
    }

    private void writeBigInt(int i, byte[] bArr, int i2) {
        putReversedInteger(bArr, i2, i);
    }

    private int getReversedInteger(byte[] bArr, int i) {
        return ((bArr[i] & 255) << 24) | (bArr[i + 3] & 255) | ((bArr[i + 2] & 255) << 8) | ((bArr[i + 1] & 255) << 16);
    }

    private void putReversedInteger(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) (i2 >>> 24);
        bArr[i + 1] = (byte) (i2 >>> 16);
        bArr[i + 2] = (byte) (i2 >>> 8);
        bArr[i + 3] = (byte) (i2 & 255);
    }

    private void fillZero(int[] iArr) {
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
            iArr[i] = 0;
        }
    }

    private void fillZero(byte[] bArr) {
        int length = bArr.length;
        for (int i = 0; i < length; i++) {
            bArr[i] = 0;
        }
    }
}
