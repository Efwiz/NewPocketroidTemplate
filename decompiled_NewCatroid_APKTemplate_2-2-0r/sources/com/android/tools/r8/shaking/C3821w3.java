package com.android.tools.r8.shaking;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.w3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3821w3 {
    private final String a;

    public C3821w3(String str) {
        this.a = str;
    }

    public final boolean a(String str) {
        return a(0, 0, this.a, str);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof C3821w3) {
            return this.a.equals(((C3821w3) obj).a);
        }
        return false;
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    private static boolean a(int i, int i2, String str, String str2) {
        while (true) {
            if (i >= str.length()) {
                return i2 == str2.length();
            }
            char cCharAt = str.charAt(i);
            if (cCharAt == '*') {
                int i3 = i + 1;
                boolean z = str.length() > i3 && str.charAt(i3) == '*';
                if (z) {
                    i3 = i + 2;
                }
                if (i3 == str.length()) {
                    if (z) {
                        return true;
                    }
                    return !(str2.indexOf(46, i2) != -1);
                }
                while (i2 < str2.length()) {
                    if (!z && str2.charAt(i2) == '.') {
                        return a(i3, i2, str, str2);
                    }
                    if (a(i3, i2, str, str2)) {
                        return true;
                    }
                    i2++;
                }
                return a(i3, str2.length(), str, str2);
            }
            if (cCharAt != '?') {
                if (i2 != str2.length()) {
                    int i4 = i2 + 1;
                    if (cCharAt == str2.charAt(i2)) {
                        i2 = i4;
                    }
                }
                return false;
            }
            if (i2 == str2.length() || str2.charAt(i2) == '.') {
                return false;
            }
            i2++;
            i++;
        }
    }
}
