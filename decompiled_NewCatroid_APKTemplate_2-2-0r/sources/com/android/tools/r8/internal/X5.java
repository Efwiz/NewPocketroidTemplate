package com.android.tools.r8.internal;

import java.io.OutputStream;
import java.util.ArrayList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class X5 extends OutputStream {
    private static final byte[] f = new byte[0];
    private int c;
    private int e;
    private final int a = 128;
    private final ArrayList b = new ArrayList();
    private byte[] d = new byte[128];

    X5() {
    }

    public final synchronized Y5 c() {
        int i = this.e;
        byte[] bArr = this.d;
        if (i >= bArr.length) {
            this.b.add(new C2904rC(bArr));
            this.d = f;
        } else if (i > 0) {
            byte[] bArr2 = new byte[i];
            System.arraycopy(bArr, 0, bArr2, 0, Math.min(bArr.length, i));
            this.b.add(new C2904rC(bArr2));
        }
        this.c += this.e;
        this.e = 0;
        return Y5.a(this.b);
    }

    public final String toString() {
        int i;
        String hexString = Integer.toHexString(System.identityHashCode(this));
        synchronized (this) {
            i = this.c + this.e;
        }
        return String.format("<ByteString.Output@%s size=%d>", hexString, Integer.valueOf(i));
    }

    @Override // java.io.OutputStream
    public final synchronized void write(int i) {
        int i2 = this.e;
        byte[] bArr = this.d;
        if (i2 == bArr.length) {
            this.b.add(new C2904rC(bArr));
            int length = this.c + this.d.length;
            this.c = length;
            this.d = new byte[Math.max(this.a, Math.max(1, length >>> 1))];
            this.e = 0;
        }
        byte[] bArr2 = this.d;
        int i3 = this.e;
        this.e = i3 + 1;
        bArr2[i3] = (byte) i;
    }

    @Override // java.io.OutputStream
    public final synchronized void write(byte[] bArr, int i, int i2) {
        byte[] bArr2 = this.d;
        int length = bArr2.length;
        int i3 = this.e;
        if (i2 <= length - i3) {
            System.arraycopy(bArr, i, bArr2, i3, i2);
            this.e += i2;
        } else {
            int length2 = bArr2.length - i3;
            System.arraycopy(bArr, i, bArr2, i3, length2);
            int i4 = i2 - length2;
            this.b.add(new C2904rC(this.d));
            int length3 = this.c + this.d.length;
            this.c = length3;
            byte[] bArr3 = new byte[Math.max(this.a, Math.max(i4, length3 >>> 1))];
            this.d = bArr3;
            this.e = 0;
            System.arraycopy(bArr, i + length2, bArr3, 0, i4);
            this.e = i4;
        }
    }
}
