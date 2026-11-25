package com.reandroid.jcommand.utils;

import io.netty.util.internal.StringUtil;

/* JADX INFO: loaded from: classes11.dex */
public class CommandUtil {
    public static String of(String str) {
        return str;
    }

    public static String quoteString(String str) {
        return needsQuote(str) ? "\"" + str + "\"" : str;
    }

    private static boolean needsQuote(String str) {
        int length = str.length();
        if (length == 0) {
            return true;
        }
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == ' ' || cCharAt == '\n' || cCharAt == '\t' || cCharAt == '\"' || cCharAt == '\'') {
                return true;
            }
        }
        return false;
    }

    public static boolean containsIgnoreCase(String[] strArr, String str) {
        for (String str2 : strArr) {
            if (str.equalsIgnoreCase(str2)) {
                return true;
            }
        }
        return false;
    }

    public static String asString(String[] strArr) {
        StringBuilder sb = new StringBuilder("[");
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(strArr[i]);
        }
        sb.append(']');
        return sb.toString();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static void printTwoColumns(StringBuilder sb, String str, int i, String[][] strArr) {
        printTwoColumns(sb, str, "  ", i, strArr);
    }

    public static void printTwoColumns(StringBuilder sb, String str, String str2, int i, String[][] strArr) {
        ensureNunNull(strArr);
        int i2 = 0;
        for (String[] strArr2 : strArr) {
            int length = strArr2[0].length();
            if (length > i2) {
                i2 = length;
            }
        }
        int i3 = i - i2;
        for (int i4 = 0; i4 < strArr.length; i4++) {
            String[] strArr3 = strArr[i4];
            if (i4 != 0) {
                sb.append("\n");
            }
            printRow(true, sb, str, i2, i3, strArr3[0], str2, strArr3[1]);
        }
    }

    private static void printRow(boolean z, StringBuilder sb, String str, int i, int i2, String str2, String str3, String str4) {
        sb.append(str);
        if (z) {
            sb.append(str2);
        }
        fillSpace(sb, i - str2.length());
        if (!z) {
            sb.append(str2);
        }
        sb.append(str3);
        char[] charArray = str4.toCharArray();
        int i3 = 0;
        boolean z2 = false;
        for (int i4 = 0; i4 < charArray.length; i4++) {
            char c = charArray[i4];
            if (i4 == 0) {
                sb.append(c);
            } else {
                if (c == '\n' || (i3 > 0 && i3 % i2 == 0)) {
                    sb.append('\n');
                    sb.append(str);
                    fillSpace(sb, str3.length() + i);
                    i3 = 0;
                    z2 = false;
                }
                if (c != '\n') {
                    if (i3 == 0 && c == ' ' && !z2) {
                        z2 = true;
                    } else {
                        sb.append(c);
                    }
                }
            }
            i3++;
        }
    }

    private static void fillSpace(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(StringUtil.SPACE);
        }
    }

    private static void ensureNunNull(String[][] strArr) {
        for (String[] strArr2 : strArr) {
            int length = strArr2.length;
            for (int i = 0; i < length; i++) {
                if (strArr2[i] == null) {
                    strArr2[i] = "null";
                }
            }
        }
    }
}
