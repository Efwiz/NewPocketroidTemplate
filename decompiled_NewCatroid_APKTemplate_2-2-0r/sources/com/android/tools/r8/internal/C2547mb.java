package com.android.tools.r8.internal;

import java.io.IOException;
import java.io.OutputStream;
import org.luaj.vm2.Lua;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2547mb {
    private final byte[] a;
    private final int b;
    private int c;
    private final OutputStream d;

    private C2547mb(byte[] bArr, int i) {
        this.d = null;
        this.a = bArr;
        this.c = 0;
        this.b = i;
    }

    public static int a(long j) {
        if (((-128) & j) == 0) {
            return 1;
        }
        if (((-16384) & j) == 0) {
            return 2;
        }
        if (((-2097152) & j) == 0) {
            return 3;
        }
        if (((-268435456) & j) == 0) {
            return 4;
        }
        if (((-34359738368L) & j) == 0) {
            return 5;
        }
        if (((-4398046511104L) & j) == 0) {
            return 6;
        }
        if (((-562949953421312L) & j) == 0) {
            return 7;
        }
        if (((-72057594037927936L) & j) == 0) {
            return 8;
        }
        return (j & Long.MIN_VALUE) == 0 ? 9 : 10;
    }

    public static int b(int i) {
        if ((i & (-128)) == 0) {
            return 1;
        }
        if ((i & Lua.MASK_Bx) == 0) {
            return 2;
        }
        if (((-2097152) & i) == 0) {
            return 3;
        }
        return (i & (-268435456)) == 0 ? 4 : 5;
    }

    public static long b(long j) {
        return (j >> 63) ^ (j << 1);
    }

    public final void c(int i, int i2) throws IOException {
        g(N20.a(i, 0));
        d(i2);
    }

    public final void d(int i) throws IOException {
        if (i >= 0) {
            g(i);
        } else {
            d(i);
        }
    }

    public final void e(int i) throws IOException {
        byte b = (byte) i;
        int i2 = this.c;
        if (i2 == this.b) {
            OutputStream outputStream = this.d;
            if (outputStream == null) {
                throw new C2473lb();
            }
            outputStream.write(this.a, 0, i2);
            this.c = 0;
        }
        byte[] bArr = this.a;
        int i3 = this.c;
        this.c = i3 + 1;
        bArr[i3] = b;
    }

    public final void f(int i) throws IOException {
        e(i & 255);
        e((i >> 8) & 255);
        e((i >> 16) & 255);
        e((i >> 24) & 255);
    }

    public final void g(int i) throws IOException {
        while ((i & (-128)) != 0) {
            e((i & 127) | 128);
            i >>>= 7;
        }
        e(i);
    }

    public static C2547mb a(OutputStream outputStream, int i) {
        return new C2547mb(outputStream, new byte[i]);
    }

    public static C2547mb a(byte[] bArr) {
        return new C2547mb(bArr, bArr.length);
    }

    public static int c(int i) {
        return b(N20.a(i, 0));
    }

    public final void b(B0 b0) throws IOException {
        g(b0.c());
        b0.a(this);
    }

    public final void c(long j) throws IOException {
        e(((int) j) & 255);
        e(((int) (j >> 8)) & 255);
        e(((int) (j >> 16)) & 255);
        e(((int) (j >> 24)) & 255);
        e(((int) (j >> 32)) & 255);
        e(((int) (j >> 40)) & 255);
        e(((int) (j >> 48)) & 255);
        e(((int) (j >> 56)) & 255);
    }

    public static int a(int i, B0 b0) {
        int iC = c(i);
        int iC2 = b0.c();
        return b(iC2) + iC2 + iC;
    }

    public static int b(int i, int i2) {
        return a(i2) + c(i);
    }

    public final void d(int i, int i2) throws IOException {
        g(N20.a(i, 0));
        d(i2);
    }

    private C2547mb(OutputStream outputStream, byte[] bArr) {
        this.d = outputStream;
        this.a = bArr;
        this.c = 0;
        this.b = bArr.length;
    }

    public final void b() throws IOException {
        OutputStream outputStream = this.d;
        if (outputStream != null) {
            if (outputStream != null) {
                outputStream.write(this.a, 0, this.c);
                this.c = 0;
                return;
            }
            throw new C2473lb();
        }
    }

    public final void d(long j) throws IOException {
        while (((-128) & j) != 0) {
            e((((int) j) & 127) | 128);
            j >>>= 7;
        }
        e((int) j);
    }

    public static int a(int i, int i2) {
        return a(i2) + c(i);
    }

    public static int a(int i) {
        if (i >= 0) {
            return b(i);
        }
        return 10;
    }

    public final void b(Y5 y5) throws IOException {
        int size = y5.size();
        int i = this.b;
        int i2 = this.c;
        int i3 = i - i2;
        if (i3 >= size) {
            y5.a(0, i2, size, this.a);
            this.c += size;
            return;
        }
        y5.a(0, i2, i3, this.a);
        int i4 = size - i3;
        int i5 = this.b;
        this.c = i5;
        OutputStream outputStream = this.d;
        if (outputStream != null) {
            outputStream.write(this.a, 0, i5);
            this.c = 0;
            if (i4 <= this.b) {
                y5.a(i3, 0, i4, this.a);
                this.c = i4;
                return;
            }
            OutputStream outputStream2 = this.d;
            if (i3 < 0) {
                throw new IndexOutOfBoundsException(new StringBuilder(30).append("Source offset < 0: ").append(i3).toString());
            }
            if (i4 >= 0) {
                int i6 = i3 + i4;
                if (i6 > y5.size()) {
                    throw new IndexOutOfBoundsException(new StringBuilder(39).append("Source end offset exceeded: ").append(i6).toString());
                }
                if (i4 > 0) {
                    y5.a(outputStream2, i3, i4);
                    return;
                }
                return;
            }
            throw new IndexOutOfBoundsException(new StringBuilder(23).append("Length < 0: ").append(i4).toString());
        }
        throw new C2473lb();
    }

    public static int a(B0 b0) {
        int iC = b0.c();
        return b(iC) + iC;
    }

    public static int a(Y5 y5) {
        return y5.size() + b(y5.size());
    }

    public final void e(long j) throws IOException {
        g(N20.a(2, 0));
        d(b(j));
    }

    public final void a() {
        if (this.d == null) {
            if (this.b - this.c != 0) {
                throw new IllegalStateException("Did not write as much data as expected.");
            }
            return;
        }
        throw new UnsupportedOperationException("spaceLeft() can only be called on CodedOutputStreams that are writing to a flat array.");
    }

    public final void a(boolean z) throws IOException {
        g(N20.a(3, 0));
        e(z ? 1 : 0);
    }

    public final void a(int i, Y5 y5) throws IOException {
        g(N20.a(i, 2));
        g(y5.size());
        b(y5);
    }

    public final void a(double d) throws IOException {
        g(N20.a(4, 1));
        c(Double.doubleToRawLongBits(d));
    }

    public final void a(float f) throws IOException {
        g(N20.a(3, 5));
        f(Float.floatToRawIntBits(f));
    }

    public final void b(byte[] bArr) throws IOException {
        int length = bArr.length;
        int i = this.b;
        int i2 = this.c;
        int i3 = i - i2;
        if (i3 >= length) {
            System.arraycopy(bArr, 0, this.a, i2, length);
            this.c += length;
            return;
        }
        System.arraycopy(bArr, 0, this.a, i2, i3);
        int i4 = length - i3;
        int i5 = this.b;
        this.c = i5;
        OutputStream outputStream = this.d;
        if (outputStream != null) {
            outputStream.write(this.a, 0, i5);
            this.c = 0;
            if (i4 <= this.b) {
                System.arraycopy(bArr, i3, this.a, 0, i4);
                this.c = i4;
                return;
            } else {
                this.d.write(bArr, i3, i4);
                return;
            }
        }
        throw new C2473lb();
    }

    public final void b(int i, B0 b0) throws IOException {
        g(N20.a(i, 2));
        g(b0.c());
        b0.a(this);
    }
}
