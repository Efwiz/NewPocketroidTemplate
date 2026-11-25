package com.android.tools.r8.internal;

import okhttp3.HttpUrl;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class CZ {
    public static final CZ e = new CZ(0, 0, 1, "VZCBSIFJD");
    public static final CZ f = new CZ(1, 1, 2, "VZCBSIFJD");
    public static final CZ g = new CZ(2, 2, 3, "VZCBSIFJD");
    public static final CZ h = new CZ(3, 3, 4, "VZCBSIFJD");
    public static final CZ i = new CZ(4, 4, 5, "VZCBSIFJD");
    public static final CZ j = new CZ(5, 5, 6, "VZCBSIFJD");
    public static final CZ k = new CZ(6, 6, 7, "VZCBSIFJD");
    public static final CZ l = new CZ(7, 7, 8, "VZCBSIFJD");
    public static final CZ m = new CZ(8, 8, 9, "VZCBSIFJD");
    private final int a;
    private final String b;
    private final int c;
    private final int d;

    private CZ(int i2, int i3, int i4, String str) {
        this.a = i2;
        this.b = str;
        this.c = i3;
        this.d = i4;
    }

    public static CZ[] a(String str) {
        int i2 = 0;
        int i3 = 0;
        int iMax = 1;
        while (str.charAt(iMax) != ')') {
            while (str.charAt(iMax) == '[') {
                iMax++;
            }
            int i4 = iMax + 1;
            iMax = str.charAt(iMax) == 'L' ? Math.max(i4, str.indexOf(59, i4) + 1) : i4;
            i3++;
        }
        CZ[] czArr = new CZ[i3];
        int i5 = 1;
        while (str.charAt(i5) != ')') {
            int i6 = i5;
            while (str.charAt(i6) == '[') {
                i6++;
            }
            int iMax2 = i6 + 1;
            if (str.charAt(i6) == 'L') {
                iMax2 = Math.max(iMax2, str.indexOf(59, iMax2) + 1);
            }
            czArr[i2] = a(str, i5, iMax2);
            i2++;
            i5 = iMax2;
        }
        return czArr;
    }

    public static CZ c(String str) {
        return new CZ(11, 0, str.length(), str);
    }

    public static CZ d(String str) {
        return new CZ(str.charAt(0) == '[' ? 9 : 12, 0, str.length(), str);
    }

    public static CZ e(String str) {
        int iMax = 1;
        while (str.charAt(iMax) != ')') {
            while (str.charAt(iMax) == '[') {
                iMax++;
            }
            int i2 = iMax + 1;
            iMax = str.charAt(iMax) == 'L' ? Math.max(i2, str.indexOf(59, i2) + 1) : i2;
        }
        return a(str, iMax + 1, str.length());
    }

    public static CZ f(String str) {
        return a(str, 0, str.length());
    }

    public final String b() {
        int i2 = this.a;
        if (i2 == 10) {
            return this.b.substring(this.c - 1, this.d + 1);
        }
        return i2 == 12 ? "L" + this.b.substring(this.c, this.d) + ';' : this.b.substring(this.c, this.d);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CZ)) {
            return false;
        }
        CZ cz = (CZ) obj;
        int i2 = this.a;
        if (i2 == 12) {
            i2 = 10;
        }
        int i3 = cz.a;
        if (i2 != (i3 != 12 ? i3 : 10)) {
            return false;
        }
        int i4 = this.c;
        int i5 = this.d;
        int i6 = cz.c;
        if (i5 - i4 != cz.d - i6) {
            return false;
        }
        while (i4 < i5) {
            if (this.b.charAt(i4) != cz.b.charAt(i6)) {
                return false;
            }
            i4++;
            i6++;
        }
        return true;
    }

    public final int hashCode() {
        int i2 = this.a;
        int iCharAt = (i2 == 12 ? 10 : i2) * 13;
        if (i2 >= 9) {
            int i3 = this.d;
            for (int i4 = this.c; i4 < i3; i4++) {
                iCharAt = (this.b.charAt(i4) + iCharAt) * 17;
            }
        }
        return iCharAt;
    }

    public final String toString() {
        return b();
    }

    public final String c() {
        return this.b.substring(this.c, this.d);
    }

    public final int d() {
        switch (this.a) {
            case 0:
                return 0;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 9:
            case 10:
            case 12:
                return 1;
            case 7:
            case 8:
                return 2;
            case 11:
            default:
                throw new AssertionError();
        }
    }

    public static int b(String str) {
        char cCharAt = str.charAt(1);
        int i2 = 1;
        int i3 = 1;
        while (cCharAt != ')') {
            if (cCharAt == 'J' || cCharAt == 'D') {
                i2++;
                i3 += 2;
            } else {
                while (str.charAt(i2) == '[') {
                    i2++;
                }
                int iMax = i2 + 1;
                if (str.charAt(i2) == 'L') {
                    iMax = Math.max(iMax, str.indexOf(59, iMax) + 1);
                }
                i3++;
                i2 = iMax;
            }
            cCharAt = str.charAt(i2);
        }
        char cCharAt2 = str.charAt(i2 + 1);
        if (cCharAt2 == 'V') {
            return i3 << 2;
        }
        return (i3 << 2) | ((cCharAt2 == 'J' || cCharAt2 == 'D') ? 2 : 1);
    }

    public final int e() {
        int i2 = this.a;
        if (i2 == 12) {
            return 10;
        }
        return i2;
    }

    private static CZ a(String str, int i2, int i3) {
        char cCharAt = str.charAt(i2);
        if (cCharAt == '(') {
            return new CZ(11, i2, i3, str);
        }
        if (cCharAt == 'F') {
            return k;
        }
        if (cCharAt == 'L') {
            return new CZ(10, i2 + 1, i3 - 1, str);
        }
        if (cCharAt == 'S') {
            return i;
        }
        if (cCharAt == 'V') {
            return e;
        }
        if (cCharAt == 'I') {
            return j;
        }
        if (cCharAt == 'J') {
            return l;
        }
        if (cCharAt == 'Z') {
            return f;
        }
        if (cCharAt == '[') {
            return new CZ(9, i2, i3, str);
        }
        switch (cCharAt) {
            case 'B':
                return h;
            case 'C':
                return g;
            case 'D':
                return m;
            default:
                throw new IllegalArgumentException("Invalid descriptor: ".concat(str));
        }
    }

    public final String a() {
        switch (this.a) {
            case 0:
                return PsiKeyword.VOID;
            case 1:
                return "boolean";
            case 2:
                return PsiKeyword.CHAR;
            case 3:
                return "byte";
            case 4:
                return "short";
            case 5:
                return "int";
            case 6:
                return "float";
            case 7:
                return "long";
            case 8:
                return "double";
            case 9:
                int i2 = 1;
                int i3 = 1;
                while (this.b.charAt(this.c + i3) == '[') {
                    i3++;
                }
                StringBuilder sb = new StringBuilder(a(this.b, this.c + i3, this.d).a());
                while (this.b.charAt(this.c + i2) == '[') {
                    i2++;
                }
                while (i2 > 0) {
                    sb.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
                    i2--;
                }
                return sb.toString();
            case 10:
            case 12:
                return this.b.substring(this.c, this.d).replace('/', '.');
            case 11:
            default:
                throw new AssertionError();
        }
    }
}
