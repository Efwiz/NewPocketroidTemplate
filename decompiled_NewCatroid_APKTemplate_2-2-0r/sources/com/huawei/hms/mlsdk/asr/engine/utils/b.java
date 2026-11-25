package com.huawei.hms.mlsdk.asr.engine.utils;

/* JADX INFO: compiled from: Buffer.java */
/* JADX INFO: loaded from: classes10.dex */
public class b {
    private int b = 0;
    private byte[] a = new byte[64];

    private void b(int i) {
        byte[] bArr = this.a;
        int length = bArr.length;
        if (i > length) {
            int i2 = length << 1;
            if (i2 >= i) {
                i = i2;
            }
            byte[] bArr2 = new byte[i];
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            this.a = bArr2;
        }
    }

    public void a(int i) {
        b(this.b + 4);
        byte[] bArr = this.a;
        int i2 = this.b;
        int i3 = i2 + 1;
        this.b = i3;
        bArr[i2] = (byte) (i >>> 24);
        int i4 = i2 + 2;
        this.b = i4;
        bArr[i3] = (byte) (i >> 16);
        int i5 = i2 + 3;
        this.b = i5;
        bArr[i4] = (byte) (i >> 8);
        this.b = i2 + 4;
        bArr[i5] = (byte) i;
    }

    public void a(long j) {
        b(this.b + 8);
        byte[] bArr = this.a;
        int i = this.b;
        int i2 = i + 1;
        this.b = i2;
        bArr[i] = (byte) (j >>> 56);
        int i3 = i + 2;
        this.b = i3;
        bArr[i2] = (byte) (j >> 48);
        int i4 = i + 3;
        this.b = i4;
        bArr[i3] = (byte) (j >> 40);
        int i5 = i + 4;
        this.b = i5;
        bArr[i4] = (byte) (j >> 32);
        int i6 = i + 5;
        this.b = i6;
        bArr[i5] = (byte) (j >> 24);
        int i7 = i + 6;
        this.b = i7;
        bArr[i6] = (byte) (j >> 16);
        int i8 = i + 7;
        this.b = i8;
        bArr[i7] = (byte) (j >> 8);
        this.b = i + 8;
        bArr[i8] = (byte) j;
    }

    public void a(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return;
        }
        b(this.b + bArr.length);
        System.arraycopy(bArr, 0, this.a, this.b, bArr.length);
        this.b += bArr.length;
    }

    public byte[] a() {
        int i = this.b;
        byte[] bArr = new byte[i];
        System.arraycopy(this.a, 0, bArr, 0, i);
        return bArr;
    }
}
