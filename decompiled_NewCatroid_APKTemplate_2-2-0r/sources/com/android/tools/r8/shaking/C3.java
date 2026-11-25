package com.android.tools.r8.shaking;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3 {
    public final boolean a;
    public final String b;

    C3(String str, boolean z) {
        this.a = z;
        this.b = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(int i, int i2, String str, String str2) {
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt != '*') {
                if (cCharAt != '?') {
                    if (i2 != str2.length()) {
                        int i3 = i2 + 1;
                        if (cCharAt == str2.charAt(i2)) {
                            i2 = i3;
                        }
                    }
                    return false;
                }
                if (i2 != str2.length()) {
                    int i4 = i2 + 1;
                    if (str2.charAt(i2) != '/') {
                        i2 = i4;
                    }
                }
                return false;
            }
            int i5 = i + 1;
            boolean z = str.length() > i5 && str.charAt(i5) == '*';
            int i6 = (z ? 2 : 1) + i;
            if (i6 == str.length()) {
                return z || str2.indexOf(47, i2) == -1;
            }
            for (int i7 = i2; i7 < str2.length(); i7++) {
                if (!z && str2.charAt(i7) == '/') {
                    return a(i6, i7, str, str2);
                }
                if (a(i6, i7, str, str2)) {
                    return true;
                }
            }
            i++;
        }
        return i2 == str2.length();
    }
}
