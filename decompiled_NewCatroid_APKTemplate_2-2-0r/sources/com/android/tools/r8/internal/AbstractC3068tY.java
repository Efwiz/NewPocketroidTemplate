package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tY, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC3068tY extends AbstractC2997sY {
    public static boolean a(CharSequence charSequence) {
        int iIndexOf;
        AbstractC0936Ds.c(charSequence, "<this>");
        boolean z = charSequence instanceof String;
        if (z) {
            iIndexOf = ((String) charSequence).indexOf(36, 0);
        } else {
            char[] cArr = {'$'};
            if (z) {
                iIndexOf = ((String) charSequence).indexOf(36, 0);
            } else {
                C1220Nr it = new C1247Or(0, charSequence.length() - 1).iterator();
                while (it.hasNext()) {
                    int iT = it.t();
                    if (AbstractC2518m9.a(cArr[0], charSequence.charAt(iT), false)) {
                        iIndexOf = iT;
                    }
                }
                iIndexOf = -1;
            }
        }
        return iIndexOf >= 0;
    }

    public static int a(CharSequence charSequence, char c) {
        AbstractC0936Ds.c(charSequence, "<this>");
        int length = charSequence.length() - 1;
        AbstractC0936Ds.c(charSequence, "<this>");
        boolean z = charSequence instanceof String;
        if (!z) {
            char[] cArr = {c};
            if (z) {
                return ((String) charSequence).lastIndexOf(c, length);
            }
            int length2 = charSequence.length() - 1;
            if (length > length2) {
                length = length2;
            }
            while (-1 < length) {
                if (AbstractC2518m9.a(cArr[0], charSequence.charAt(length), false)) {
                    return length;
                }
                length--;
            }
            return -1;
        }
        return ((String) charSequence).lastIndexOf(c, length);
    }

    public static final boolean a(String str, boolean z) {
        AbstractC0936Ds.c(str, "<this>");
        if (!z) {
            return str.startsWith(".");
        }
        if (!z) {
            return str.regionMatches(0, ".", 0, 1);
        }
        return str.regionMatches(z, 0, ".", 0, 1);
    }
}
