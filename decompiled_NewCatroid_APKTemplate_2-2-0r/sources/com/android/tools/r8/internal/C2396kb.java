package com.android.tools.r8.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2396kb {
    private int c;
    private final InputStream e;
    private int f;
    private int i;
    private int h = Integer.MAX_VALUE;
    private final byte[] a = new byte[4096];
    private int b = 0;
    private int d = 0;
    private int g = 0;

    private C2396kb(InputStream inputStream) {
        this.e = inputStream;
    }

    public static C2396kb a(InputStream inputStream) {
        return new C2396kb(inputStream);
    }

    private void o() {
        int i = this.b + this.c;
        this.b = i;
        int i2 = this.g + i;
        int i3 = this.h;
        if (i2 <= i3) {
            this.c = 0;
            return;
        }
        int i4 = i2 - i3;
        this.c = i4;
        this.b = i - i4;
    }

    public final boolean b() {
        return k() != 0;
    }

    public final Y5 c() {
        int iJ = j();
        int i = this.b;
        int i2 = this.d;
        if (iJ > i - i2 || iJ <= 0) {
            return iJ == 0 ? Y5.a : new C2904rC(d(iJ));
        }
        byte[] bArr = new byte[iJ];
        System.arraycopy(this.a, i2, bArr, 0, iJ);
        C2904rC c2904rC = new C2904rC(bArr);
        this.d += iJ;
        return c2904rC;
    }

    public final double d() {
        return Double.longBitsToDouble(i());
    }

    public final int e() {
        return j();
    }

    public final float f() {
        return Float.intBitsToFloat(h());
    }

    public final int g() {
        return j();
    }

    public final int h() throws C1054Hs {
        int i = this.d;
        if (this.b - i < 4) {
            e(4);
            i = this.d;
        }
        byte[] bArr = this.a;
        this.d = i + 4;
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    public final long i() throws C1054Hs {
        int i = this.d;
        if (this.b - i < 8) {
            e(8);
            i = this.d;
        }
        byte[] bArr = this.a;
        this.d = i + 8;
        return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
    }

    public final int j() {
        int i;
        int i2 = this.d;
        int i3 = this.b;
        long j = 0;
        if (i3 != i2) {
            byte[] bArr = this.a;
            int i4 = i2 + 1;
            byte b = bArr[i2];
            if (b >= 0) {
                this.d = i4;
                return b;
            }
            if (i3 - i4 >= 9) {
                int i5 = i2 + 2;
                int i6 = (bArr[i4] << 7) ^ b;
                long j2 = i6;
                if (j2 < 0) {
                    i = (int) ((-128) ^ j2);
                } else {
                    int i7 = i2 + 3;
                    int i8 = (bArr[i5] << 14) ^ i6;
                    long j3 = i8;
                    if (j3 >= 0) {
                        i = (int) (16256 ^ j3);
                    } else {
                        int i9 = i2 + 4;
                        int i10 = i8 ^ (bArr[i7] << 21);
                        long j4 = i10;
                        if (j4 < 0) {
                            i = (int) ((-2080896) ^ j4);
                        } else {
                            i7 = i2 + 5;
                            byte b2 = bArr[i9];
                            int i11 = (int) (((long) (i10 ^ (b2 << 28))) ^ 266354560);
                            if (b2 < 0) {
                                i9 = i2 + 6;
                                if (bArr[i7] < 0) {
                                    i7 = i2 + 7;
                                    if (bArr[i9] < 0) {
                                        i9 = i2 + 8;
                                        if (bArr[i7] < 0) {
                                            i7 = i2 + 9;
                                            if (bArr[i9] < 0) {
                                                int i12 = i2 + 10;
                                                if (bArr[i7] >= 0) {
                                                    i5 = i12;
                                                    i = i11;
                                                }
                                            }
                                        }
                                    }
                                }
                                i = i11;
                            }
                            i = i11;
                        }
                        i5 = i9;
                    }
                    i5 = i7;
                }
                this.d = i5;
                return i;
            }
        }
        for (int i13 = 0; i13 < 64; i13 += 7) {
            if (this.d == this.b) {
                e(1);
            }
            byte[] bArr2 = this.a;
            int i14 = this.d;
            this.d = i14 + 1;
            byte b3 = bArr2[i14];
            j |= ((long) (b3 & 127)) << i13;
            if ((b3 & 128) == 0) {
                return (int) j;
            }
        }
        throw new C1054Hs("CodedInputStream encountered a malformed varint.");
    }

    public final long k() throws C1054Hs {
        long j;
        long j2;
        long j3;
        int i = this.d;
        int i2 = this.b;
        long j4 = 0;
        if (i2 != i) {
            byte[] bArr = this.a;
            int i3 = i + 1;
            byte b = bArr[i];
            if (b >= 0) {
                this.d = i3;
                return b;
            }
            if (i2 - i3 >= 9) {
                int i4 = i + 2;
                long j5 = (bArr[i3] << 7) ^ b;
                if (j5 >= 0) {
                    int i5 = i + 3;
                    long j6 = j5 ^ ((long) (bArr[i4] << 14));
                    if (j6 >= 0) {
                        j3 = 16256;
                    } else {
                        i4 = i + 4;
                        j5 = j6 ^ ((long) (bArr[i5] << 21));
                        if (j5 < 0) {
                            j2 = -2080896;
                        } else {
                            i5 = i + 5;
                            j6 = j5 ^ (((long) bArr[i4]) << 28);
                            if (j6 >= 0) {
                                j3 = 266354560;
                            } else {
                                i4 = i + 6;
                                j5 = j6 ^ (((long) bArr[i5]) << 35);
                                if (j5 >= 0) {
                                    i5 = i + 7;
                                    j6 = j5 ^ (((long) bArr[i4]) << 42);
                                    if (j6 >= 0) {
                                        j3 = 4363953127296L;
                                    } else {
                                        i4 = i + 8;
                                        j5 = j6 ^ (((long) bArr[i5]) << 49);
                                        if (j5 < 0) {
                                            j2 = -558586000294016L;
                                        } else {
                                            int i6 = i + 9;
                                            long j7 = (j5 ^ (((long) bArr[i4]) << 56)) ^ 71499008037633920L;
                                            if (j7 < 0) {
                                                int i7 = i + 10;
                                                if (bArr[i6] >= 0) {
                                                    i4 = i7;
                                                }
                                            } else {
                                                i4 = i6;
                                            }
                                            j = j7;
                                        }
                                    }
                                    this.d = i4;
                                    return j;
                                }
                                j2 = -34093383808L;
                            }
                        }
                    }
                    j = j6 ^ j3;
                    i4 = i5;
                    this.d = i4;
                    return j;
                }
                j2 = -128;
                j = j2 ^ j5;
                this.d = i4;
                return j;
            }
        }
        for (int i8 = 0; i8 < 64; i8 += 7) {
            if (this.d == this.b) {
                e(1);
            }
            byte[] bArr2 = this.a;
            int i9 = this.d;
            this.d = i9 + 1;
            byte b2 = bArr2[i9];
            j4 |= ((long) (b2 & 127)) << i8;
            if ((b2 & 128) == 0) {
                return j4;
            }
        }
        throw new C1054Hs("CodedInputStream encountered a malformed varint.");
    }

    public final long l() throws C1054Hs {
        long jK = k();
        return (-(jK & 1)) ^ (jK >>> 1);
    }

    public final String m() {
        int iJ = j();
        int i = this.b;
        int i2 = this.d;
        if (iJ > i - i2 || iJ <= 0) {
            return iJ == 0 ? "" : new String(d(iJ), "UTF-8");
        }
        String str = new String(this.a, i2, iJ, "UTF-8");
        this.d += iJ;
        return str;
    }

    public final int n() throws C1054Hs {
        if (this.d == this.b && !g(1)) {
            this.f = 0;
            return 0;
        }
        int iJ = j();
        this.f = iJ;
        if ((iJ >>> 3) != 0) {
            return iJ;
        }
        throw new C1054Hs("Protocol message contained an invalid tag (zero).");
    }

    private byte[] d(int i) throws C1054Hs {
        if (i <= 0) {
            if (i == 0) {
                return AbstractC3019ss.a;
            }
            throw new C1054Hs("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
        }
        int i2 = this.g;
        int i3 = this.d;
        int i4 = i2 + i3 + i;
        int i5 = this.h;
        if (i4 > i5) {
            f((i5 - i2) - i3);
            throw C1054Hs.b();
        }
        if (i < 4096) {
            byte[] bArr = new byte[i];
            int i6 = this.b - i3;
            System.arraycopy(this.a, i3, bArr, 0, i6);
            this.d = this.b;
            int i7 = i - i6;
            if (i7 > 0) {
                e(i7);
            }
            System.arraycopy(this.a, 0, bArr, i6, i7);
            this.d = i7;
            return bArr;
        }
        int i8 = this.b;
        this.g = i2 + i8;
        this.d = 0;
        this.b = 0;
        int length = i8 - i3;
        int i9 = i - length;
        ArrayList<byte[]> arrayList = new ArrayList();
        while (i9 > 0) {
            int iMin = Math.min(i9, 4096);
            byte[] bArr2 = new byte[iMin];
            int i10 = 0;
            while (i10 < iMin) {
                InputStream inputStream = this.e;
                int i11 = inputStream == null ? -1 : inputStream.read(bArr2, i10, iMin - i10);
                if (i11 == -1) {
                    throw C1054Hs.b();
                }
                this.g += i11;
                i10 += i11;
            }
            i9 -= iMin;
            arrayList.add(bArr2);
        }
        byte[] bArr3 = new byte[i];
        System.arraycopy(this.a, i3, bArr3, 0, length);
        for (byte[] bArr4 : arrayList) {
            System.arraycopy(bArr4, 0, bArr3, length, bArr4.length);
            length += bArr4.length;
        }
        return bArr3;
    }

    private void e(int i) throws C1054Hs {
        if (!g(i)) {
            throw C1054Hs.b();
        }
    }

    private boolean g(int i) throws IOException {
        int i2 = this.d;
        int i3 = i2 + i;
        int i4 = this.b;
        if (i3 <= i4) {
            throw new IllegalStateException(new StringBuilder(77).append("refillBuffer() called when ").append(i).append(" bytes were already available in buffer").toString());
        }
        if (this.g + i2 + i <= this.h && this.e != null) {
            if (i2 > 0) {
                if (i4 > i2) {
                    byte[] bArr = this.a;
                    System.arraycopy(bArr, i2, bArr, 0, i4 - i2);
                }
                this.g += i2;
                this.b -= i2;
                this.d = 0;
            }
            InputStream inputStream = this.e;
            byte[] bArr2 = this.a;
            int i5 = this.b;
            int i6 = inputStream.read(bArr2, i5, bArr2.length - i5);
            if (i6 == 0 || i6 < -1 || i6 > this.a.length) {
                throw new IllegalStateException(new StringBuilder(102).append("InputStream#read(byte[]) returned invalid result: ").append(i6).append("\nThe InputStream implementation is buggy.").toString());
            }
            if (i6 > 0) {
                this.b += i6;
                if ((this.g + i) - 67108864 > 0) {
                    throw new C1054Hs("Protocol message was too large.  May be malicious.  Use CodedInputStream.setSizeLimit() to increase the size limit.");
                }
                o();
                if (this.b >= i) {
                    return true;
                }
                return g(i);
            }
        }
        return false;
    }

    public final void a(int i) throws C1054Hs {
        if (this.f != i) {
            throw new C1054Hs("Protocol message end-group tag did not match expected tag.");
        }
    }

    public final void b(int i) {
        this.h = i;
        o();
    }

    public final void f(int i) throws C1054Hs {
        int i2 = this.b;
        int i3 = this.d;
        int i4 = i2 - i3;
        if (i <= i4 && i >= 0) {
            this.d = i3 + i;
            return;
        }
        if (i < 0) {
            throw new C1054Hs("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
        }
        int i5 = this.g;
        int i6 = i5 + i3 + i;
        int i7 = this.h;
        if (i6 > i7) {
            f((i7 - i5) - i3);
            throw C1054Hs.b();
        }
        this.d = i2;
        e(1);
        while (true) {
            int i8 = i - i4;
            int i9 = this.b;
            if (i8 <= i9) {
                this.d = i8;
                return;
            } else {
                i4 += i9;
                this.d = i9;
                e(1);
            }
        }
    }

    public final boolean a(int i, C2547mb c2547mb) throws IOException {
        int iN;
        int i2 = i & 7;
        if (i2 == 0) {
            long jK = k();
            c2547mb.g(i);
            c2547mb.d(jK);
            return true;
        }
        if (i2 == 1) {
            long jI = i();
            c2547mb.g(i);
            c2547mb.c(jI);
            return true;
        }
        if (i2 == 2) {
            Y5 y5C = c();
            c2547mb.g(i);
            c2547mb.g(((C2904rC) y5C).c.length);
            c2547mb.b(y5C);
            return true;
        }
        if (i2 != 3) {
            if (i2 == 4) {
                return false;
            }
            if (i2 == 5) {
                int iH = h();
                c2547mb.g(i);
                c2547mb.f(iH);
                return true;
            }
            throw new C1054Hs("Protocol message tag had invalid wire type.");
        }
        c2547mb.g(i);
        do {
            iN = n();
            if (iN == 0) {
                break;
            }
        } while (a(iN, c2547mb));
        int iA = N20.a(i >>> 3, 4);
        a(iA);
        c2547mb.g(iA);
        return true;
    }

    public final int c(int i) {
        if (i >= 0) {
            int i2 = this.g + this.d + i;
            int i3 = this.h;
            if (i2 <= i3) {
                this.h = i2;
                o();
                return i3;
            }
            throw C1054Hs.b();
        }
        throw new C1054Hs("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
    }

    public final void a(int i, A0 a0, C2867qh c2867qh) {
        int i2 = this.i;
        if (i2 < 64) {
            this.i = i2 + 1;
            a0.a(this, c2867qh);
            a(N20.a(i, 4));
            this.i--;
            return;
        }
        throw new C1054Hs("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
    }

    public final void a(A0 a0, C2867qh c2867qh) {
        int iJ = j();
        if (this.i < 64) {
            int iC = c(iJ);
            this.i++;
            a0.a(this, c2867qh);
            a(0);
            this.i--;
            this.h = iC;
            o();
            return;
        }
        throw new C1054Hs("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
    }

    public final B0 a(AbstractC1732c1 abstractC1732c1, C2867qh c2867qh) throws C1054Hs {
        int iJ = j();
        if (this.i < 64) {
            int iC = c(iJ);
            this.i++;
            B0 b0 = (B0) abstractC1732c1.a(this, c2867qh);
            a(0);
            this.i--;
            this.h = iC;
            o();
            return b0;
        }
        throw new C1054Hs("Protocol message had too many levels of nesting.  May be malicious.  Use CodedInputStream.setRecursionLimit() to increase the depth limit.");
    }

    public final int a() {
        int i = this.h;
        if (i == Integer.MAX_VALUE) {
            return -1;
        }
        return i - (this.g + this.d);
    }
}
