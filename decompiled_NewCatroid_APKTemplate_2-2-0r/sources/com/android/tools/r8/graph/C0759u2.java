package com.android.tools.r8.graph;

import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.EnumC3891f;
import com.android.tools.r8.utils.InterfaceC3900g3;
import java.io.UTFDataFormatException;
import java.util.Arrays;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;

/* JADX INFO: renamed from: com.android.tools.r8.graph.u2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0759u2 extends I3 implements V4 {
    public static final C0759u2[] f = new C0759u2[0];
    static final /* synthetic */ boolean g = true;
    public final int d;
    public final byte[] e;

    C0759u2(int i, byte[] bArr) {
        this.d = i;
        this.e = bArr;
    }

    private String t0() {
        char[] cArr = new char[this.d];
        return new String(cArr, 0, a(cArr));
    }

    public final InterfaceC3900g3 A0() {
        return new C0750t2(this);
    }

    public String B0() {
        try {
            return com.android.tools.r8.utils.Y2.h(t0());
        } catch (UTFDataFormatException e) {
            throw new RuntimeException("Bad format", e);
        }
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final int a(com.android.tools.r8.utils.structural.x xVar, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a(this, (C0759u2) xVar);
    }

    public boolean b(String str) {
        return a(a(str));
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    public final boolean c(Object obj) {
        if (!(obj instanceof C0759u2)) {
            return false;
        }
        C0759u2 c0759u2 = (C0759u2) obj;
        return this.d == c0759u2.d && Arrays.equals(this.e, c0759u2.e);
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    public final int s0() {
        return Arrays.hashCode(this.e) + (this.d * 7);
    }

    public String toString() {
        try {
            return t0();
        } catch (UTFDataFormatException e) {
            throw new RuntimeException("Bad format", e);
        }
    }

    public final int u0() throws UTFDataFormatException {
        byte b;
        int i;
        int i2;
        int i3 = 0;
        if (this.d == 0) {
            if (g || t0().hashCode() == 0) {
                return 0;
            }
            throw new AssertionError();
        }
        int i4 = 0;
        while (true) {
            byte[] bArr = this.e;
            int i5 = i3 + 1;
            char c = (char) (bArr[i3] & 255);
            if (c == 0) {
                if (g || i4 == t0().hashCode()) {
                    return i4;
                }
                throw new AssertionError();
            }
            if (c < 128) {
                i4 = (i4 * 31) + c;
                i3 = i5;
            } else {
                if ((c & 224) == 192) {
                    i3 += 2;
                    b = bArr[i5];
                    if ((b & 192) != 128) {
                        throw new UTFDataFormatException("bad second byte");
                    }
                    i = i4 * 31;
                    i2 = (c & 31) << 6;
                } else {
                    if ((c & 240) != 224) {
                        throw new UTFDataFormatException("bad byte");
                    }
                    int i6 = i3 + 2;
                    byte b2 = bArr[i5];
                    i3 += 3;
                    b = bArr[i6];
                    if ((b2 & 192) != 128 || (b & 192) != 128) {
                        throw new UTFDataFormatException("bad second or third byte");
                    }
                    i = i4 * 31;
                    i2 = ((b2 & 63) << 6) | ((c & 15) << 12);
                }
                i4 = i + ((char) ((b & 63) | i2));
            }
        }
    }

    public String v0() {
        StringBuilder sb = new StringBuilder();
        sb.append(toString());
        sb.append(" [");
        for (int i = 0; i < this.e.length; i++) {
            if (i > 0) {
                sb.append(" ");
            }
            sb.append(Integer.toHexString(this.e[i] & 255));
        }
        sb.append("]");
        return sb.toString();
    }

    public final char w0() {
        return (char) this.e[0];
    }

    public final boolean x0() {
        try {
            return C3970u0.B(t0());
        } catch (UTFDataFormatException unused) {
            return false;
        }
    }

    public final boolean y0() {
        try {
            return C3970u0.D(t0());
        } catch (UTFDataFormatException unused) {
            return false;
        }
    }

    public final boolean z0() {
        try {
            return C3970u0.F(t0());
        } catch (UTFDataFormatException unused) {
            return false;
        }
    }

    public final boolean b(C0759u2 c0759u2) {
        byte[] bArr = this.e;
        int length = bArr.length;
        byte[] bArr2 = c0759u2.e;
        if (length < bArr2.length) {
            return false;
        }
        int length2 = bArr.length - bArr2.length;
        int i = 0;
        while (true) {
            byte[] bArr3 = this.e;
            if (length2 >= bArr3.length) {
                return true;
            }
            if (bArr3[length2] != c0759u2.e[i]) {
                return false;
            }
            length2++;
            i++;
        }
    }

    C0759u2(String str) {
        this.d = str.length();
        this.e = a(str);
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this);
    }

    public final boolean c(EnumC3891f enumC3891f) {
        if (!enumC3891f.e(EnumC3891f.R)) {
            return true;
        }
        try {
            return a(enumC3891f, t0());
        } catch (UTFDataFormatException unused) {
            return false;
        }
    }

    public final int a(char[] cArr) throws UTFDataFormatException {
        int length = cArr.length;
        int i = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr = this.e;
            int i3 = i + 1;
            char c = (char) (bArr[i] & 255);
            if (c == 0) {
                return i2;
            }
            cArr[i2] = c;
            if (c < 128) {
                i2++;
                if (i2 == length) {
                    return i2;
                }
                i = i3;
            } else if ((c & 224) == 192) {
                i += 2;
                byte b = bArr[i3];
                if ((b & 192) == 128) {
                    cArr[i2] = (char) ((b & 63) | ((c & 31) << 6));
                    i2++;
                    if (i2 == length) {
                        return i2;
                    }
                } else {
                    throw new UTFDataFormatException("bad second byte");
                }
            } else if ((c & 240) == 224) {
                int i4 = i + 2;
                byte b2 = bArr[i3];
                i += 3;
                byte b3 = bArr[i4];
                if ((b2 & 192) == 128 && (b3 & 192) == 128) {
                    cArr[i2] = (char) ((b3 & 63) | ((b2 & 63) << 6) | ((c & 15) << 12));
                    i2++;
                    if (i2 == length) {
                        return i2;
                    }
                } else {
                    throw new UTFDataFormatException("bad second or third byte");
                }
            } else {
                throw new UTFDataFormatException("bad byte");
            }
        }
    }

    public static byte[] a(String str) {
        int i = 1;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt = str.charAt(i2);
            i += (cCharAt == 0 || cCharAt > 127) ? cCharAt <= 2047 ? 2 : 3 : 1;
            if (!g && i <= 0) {
                throw new AssertionError();
            }
        }
        byte[] bArr = new byte[i];
        int iA = 0;
        for (int i3 = 0; i3 < str.length(); i3++) {
            iA = a(str.charAt(i3), bArr, iA);
        }
        bArr[iA] = 0;
        return bArr;
    }

    public static int a(char c, byte[] bArr, int i) {
        if (c != 0 && c <= 127) {
            int i2 = i + 1;
            bArr[i] = (byte) c;
            return i2;
        }
        if (c <= 2047) {
            int i3 = i + 1;
            bArr[i] = (byte) (((c >> 6) & 31) | 192);
            int i4 = i + 2;
            bArr[i3] = (byte) ((c & MangleConstant.Q_MARK) | 128);
            return i4;
        }
        bArr[i] = (byte) (((c >> '\f') & 15) | 224);
        bArr[i + 1] = (byte) (((c >> 6) & 63) | 128);
        int i5 = i + 3;
        bArr[i + 2] = (byte) ((c & MangleConstant.Q_MARK) | 128);
        return i5;
    }

    @Override // com.android.tools.r8.graph.I3
    public final int a(C0645e5 c0645e5) {
        return c0645e5.a(this);
    }

    @Override // com.android.tools.r8.utils.structural.x, com.android.tools.r8.utils.structural.s, java.lang.Comparable
    public int compareTo(C0759u2 c0759u2) {
        int i = 0;
        while (true) {
            byte[] bArr = this.e;
            char c = (char) (bArr[i] & 255);
            byte[] bArr2 = c0759u2.e;
            char c2 = (char) (bArr2[i] & 255);
            int i2 = c - c2;
            if (i2 != 0) {
                if (c == 0 || c2 == 0 || !((c == 192 && (bArr[i + 1] & 255) == 128) || (c2 == 192 && (bArr2[i + 1] & 255) == 128))) {
                    return i2;
                }
                return (c == 192 && (bArr[i + 1] & 255) == 128) ? -1 : 1;
            }
            if (c == 0) {
                return 0;
            }
            i++;
        }
    }

    public static boolean a(EnumC3891f enumC3891f, String str) {
        if (!enumC3891f.e(EnumC3891f.R)) {
            return true;
        }
        int iCharCount = 0;
        while (iCharCount < str.length()) {
            int iCodePointAt = str.codePointAt(iCharCount);
            if (iCodePointAt == 32 || iCodePointAt == 160 || iCodePointAt == 5760 || ((8192 <= iCodePointAt && iCodePointAt <= 8202) || iCodePointAt == 8239 || iCodePointAt == 8287 || iCodePointAt == 12288)) {
                return false;
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
        return true;
    }

    public final boolean a(byte[] bArr) {
        if (this.e.length < bArr.length) {
            return false;
        }
        for (int i = 0; i < bArr.length - 1; i++) {
            if (this.e[i] != bArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final C0759u2 a(C0759u2 c0759u2, C0759u2 c0759u3, C0722p1 c0722p1) {
        byte[] bArr;
        int i = 0;
        while (true) {
            bArr = this.e;
            if (bArr[i] != 91) {
                break;
            }
            i++;
        }
        int i2 = (c0759u3.d + this.d) - c0759u2.d;
        byte[] bArr2 = new byte[(c0759u3.e.length + bArr.length) - c0759u2.e.length];
        for (int i3 = 0; i3 < i; i3++) {
            bArr2[i3] = 91;
        }
        byte[] bArr3 = c0759u3.e;
        System.arraycopy(bArr3, 0, bArr2, i, bArr3.length - 1);
        byte[] bArr4 = this.e;
        byte[] bArr5 = c0759u2.e;
        System.arraycopy(bArr4, bArr5.length - 1, bArr2, c0759u3.e.length - 1, (bArr4.length - bArr5.length) + 1);
        return c0722p1.b(i2, bArr2);
    }

    public final C0759u2 a(C0722p1 c0722p1) {
        byte[] bArr;
        int i = 0;
        while (true) {
            bArr = this.e;
            if (bArr[i] != 91) {
                break;
            }
            i++;
        }
        if (i == 0) {
            return this;
        }
        int length = bArr.length - i;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, i, bArr2, 0, length);
        return c0722p1.b(this.d - i, bArr2);
    }
}
