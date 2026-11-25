package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oY, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2710oY extends AbstractC3139uY {
    public static String a(String str, char c, char c2) {
        AbstractC0936Ds.c(str, "<this>");
        String strReplace = str.replace(c, c2);
        AbstractC0936Ds.b(strReplace, "this as java.lang.String…replace(oldChar, newChar)");
        return strReplace;
    }

    public static String b(String str) {
        AbstractC0936Ds.c(str, "<this>");
        int iA = AbstractC3068tY.a((CharSequence) str, '/');
        if (iA == -1) {
            return "";
        }
        String strSubstring = str.substring(0, iA);
        AbstractC0936Ds.b(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
        return strSubstring;
    }

    public static String a(String str, char c) {
        AbstractC0936Ds.c(str, "<this>");
        AbstractC0936Ds.c(str, "missingDelimiterValue");
        int iA = AbstractC3068tY.a(str, c);
        if (iA == -1) {
            return str;
        }
        String strSubstring = str.substring(iA + 1, str.length());
        AbstractC0936Ds.b(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
        return strSubstring;
    }
}
