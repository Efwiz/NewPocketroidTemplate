package com.android.tools.r8;

import java.util.Arrays;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class ByteDataView {
    static final /* synthetic */ boolean d = true;
    private byte[] a;
    private final int b;
    private final int c;

    public ByteDataView(byte[] bArr, int i, int i2) {
        boolean z = d;
        if (!z && i < 0) {
            throw new AssertionError();
        }
        if (!z && i2 < 0) {
            throw new AssertionError();
        }
        if (!z && i + i2 > bArr.length) {
            throw new AssertionError();
        }
        this.a = bArr;
        this.b = i;
        this.c = i2;
    }

    public static ByteDataView of(byte[] bArr) {
        return new ByteDataView(bArr, 0, bArr.length);
    }

    public byte[] copyByteData() {
        byte[] bArr = this.a;
        int i = this.b;
        return Arrays.copyOfRange(bArr, i, this.c + i);
    }

    public byte[] getBuffer() {
        if (d || this.a != null) {
            return this.a;
        }
        throw new AssertionError();
    }

    public int getLength() {
        if (d || this.a != null) {
            return this.c;
        }
        throw new AssertionError();
    }

    public int getOffset() {
        if (d || this.a != null) {
            return this.b;
        }
        throw new AssertionError();
    }

    public void invalidate() {
        this.a = null;
    }
}
