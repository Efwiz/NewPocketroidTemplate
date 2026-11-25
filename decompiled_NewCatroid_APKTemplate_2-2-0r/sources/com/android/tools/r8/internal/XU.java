package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class XU {
    private final String a;

    public XU(String str) {
        this.a = str;
    }

    public final void a(C2545mZ c2545mZ) {
        char cCharAt;
        String str = this.a;
        int length = str.length();
        int i = 0;
        if (str.charAt(0) == '<') {
            i = 2;
            do {
                int iIndexOf = str.indexOf(58, i);
                c2545mZ.b(str.substring(i - 1, iIndexOf));
                int iA = iIndexOf + 1;
                char cCharAt2 = str.charAt(iA);
                if (cCharAt2 == 'L' || cCharAt2 == '[' || cCharAt2 == 'T') {
                    iA = a(str, iA, c2545mZ.g());
                }
                while (true) {
                    i = iA + 1;
                    cCharAt = str.charAt(iA);
                    if (cCharAt != ':') {
                        break;
                    } else {
                        iA = a(str, i, c2545mZ.k());
                    }
                }
            } while (cCharAt != '>');
        }
        if (str.charAt(i) != '(') {
            int iA2 = a(str, i, c2545mZ.n());
            while (iA2 < length) {
                iA2 = a(str, iA2, c2545mZ.j());
            }
        } else {
            int iA3 = i + 1;
            while (str.charAt(iA3) != ')') {
                iA3 = a(str, iA3, c2545mZ.l());
            }
            int iA4 = a(str, iA3 + 1, c2545mZ.m());
            while (iA4 < length) {
                iA4 = a(str, iA4 + 1, c2545mZ.i());
            }
        }
    }

    private static int a(String str, int i, C2545mZ c2545mZ) {
        int i2;
        char cCharAt;
        int iA = i + 1;
        char cCharAt2 = str.charAt(i);
        if (cCharAt2 != 'F') {
            if (cCharAt2 == 'L') {
                boolean z = false;
                boolean z2 = false;
                while (true) {
                    int i3 = iA;
                    while (true) {
                        i2 = iA + 1;
                        cCharAt = str.charAt(iA);
                        if (cCharAt == '.' || cCharAt == ';') {
                            break;
                        }
                        if (cCharAt == '<') {
                            String strSubstring = str.substring(i3, iA);
                            if (z2) {
                                c2545mZ.c(strSubstring);
                            } else {
                                c2545mZ.a(strSubstring);
                            }
                            iA = i2;
                            while (true) {
                                char cCharAt3 = str.charAt(iA);
                                if (cCharAt3 == '>') {
                                    break;
                                }
                                if (cCharAt3 == '*') {
                                    iA++;
                                    c2545mZ.o();
                                } else if (cCharAt3 != '+' && cCharAt3 != '-') {
                                    iA = a(str, iA, c2545mZ.b('='));
                                } else {
                                    iA = a(str, iA + 1, c2545mZ.b(cCharAt3));
                                }
                            }
                            z = true;
                        } else {
                            iA = i2;
                        }
                    }
                    if (!z) {
                        String strSubstring2 = str.substring(i3, iA);
                        if (z2) {
                            c2545mZ.c(strSubstring2);
                        } else {
                            c2545mZ.a(strSubstring2);
                        }
                    }
                    if (cCharAt == ';') {
                        c2545mZ.h();
                        return i2;
                    }
                    z = false;
                    z2 = true;
                    iA = i2;
                }
            } else if (cCharAt2 != 'V' && cCharAt2 != 'I' && cCharAt2 != 'J' && cCharAt2 != 'S') {
                if (cCharAt2 == 'T') {
                    int iIndexOf = str.indexOf(59, iA);
                    c2545mZ.d(str.substring(iA, iIndexOf));
                    return iIndexOf + 1;
                }
                if (cCharAt2 != 'Z') {
                    if (cCharAt2 != '[') {
                        switch (cCharAt2) {
                            case 'B':
                            case 'C':
                            case 'D':
                                break;
                            default:
                                throw new IllegalArgumentException();
                        }
                    } else {
                        return a(str, iA, c2545mZ.f());
                    }
                }
            }
        }
        c2545mZ.a(cCharAt2);
        return iA;
    }
}
