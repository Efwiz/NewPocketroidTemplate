package org.jetbrains.kotlin.com.intellij.openapi.util.text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes3.dex */
public class StringUtilRt {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 10 || i == 13 || i == 31 || i == 33 || i == 39 || i == 41 || i == 19 || i == 20 || i == 35 || i == 36) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 10 || i == 13 || i == 31 || i == 33 || i == 39 || i == 41 || i == 19 || i == 20 || i == 35 || i == 36) ? 2 : 3];
        switch (i) {
            case 3:
            case 5:
            case 7:
            case 9:
            case 12:
                objArr[0] = "newSeparator";
                break;
            case 4:
            case 6:
            case 8:
            case 11:
            case 21:
            case 27:
            case 42:
            default:
                objArr[0] = "text";
                break;
            case 10:
            case 13:
            case 19:
            case 20:
            case 31:
            case 33:
            case 35:
            case 36:
            case 39:
            case 41:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/text/StringUtilRt";
                break;
            case 14:
                objArr[0] = "string";
                break;
            case 15:
                objArr[0] = "clazz";
                break;
            case 16:
                objArr[0] = "aClass";
                break;
            case 17:
            case 18:
                objArr[0] = "fqName";
                break;
            case 22:
            case 28:
                objArr[0] = "suffix";
                break;
            case 23:
            case 25:
                objArr[0] = "str";
                break;
            case 24:
            case 26:
            case 43:
                objArr[0] = "prefix";
                break;
            case 29:
            case 32:
            case 37:
            case 38:
            case 40:
                objArr[0] = "s";
                break;
            case 30:
                objArr[0] = "defaultValue";
                break;
            case 34:
                objArr[0] = "unitSeparator";
                break;
            case 44:
            case 45:
            case 46:
                objArr[0] = "chars";
                break;
        }
        if (i == 10) {
            objArr[1] = "convertLineSeparators";
        } else if (i == 13) {
            objArr[1] = "unifyLineSeparators";
        } else if (i == 31) {
            objArr[1] = "notNullize";
        } else if (i == 33) {
            objArr[1] = "splitHonorQuotes";
        } else if (i == 39 || i == 41) {
            objArr[1] = "unquoteString";
        } else if (i == 19 || i == 20) {
            objArr[1] = "getShortName";
        } else if (i == 35 || i == 36) {
            objArr[1] = "formatFileSize";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/text/StringUtilRt";
        }
        switch (i) {
            case 10:
            case 13:
            case 19:
            case 20:
            case 31:
            case 33:
            case 35:
            case 36:
            case 39:
            case 41:
                break;
            case 11:
            case 12:
                objArr[2] = "unifyLineSeparators";
                break;
            case 14:
            case 15:
                objArr[2] = "parseEnum";
                break;
            case 16:
            case 17:
            case 18:
                objArr[2] = "getShortName";
                break;
            case 21:
            case 22:
                objArr[2] = "endsWith";
                break;
            case 23:
            case 24:
            case 25:
            case 26:
                objArr[2] = "startsWithIgnoreCase";
                break;
            case 27:
            case 28:
                objArr[2] = "endsWithIgnoreCase";
                break;
            case 29:
                objArr[2] = "lastIndexOf";
                break;
            case 30:
                objArr[2] = "notNullize";
                break;
            case 32:
                objArr[2] = "splitHonorQuotes";
                break;
            case 34:
                objArr[2] = "formatFileSize";
                break;
            case 37:
                objArr[2] = "isQuotedString";
                break;
            case 38:
            case 40:
                objArr[2] = "unquoteString";
                break;
            case 42:
            case 43:
                objArr[2] = "startsWith";
                break;
            case 44:
            case 45:
            case 46:
                objArr[2] = "stringHashCodeInsensitive";
                break;
            default:
                objArr[2] = "convertLineSeparators";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 10 && i != 13 && i != 31 && i != 33 && i != 39 && i != 41 && i != 19 && i != 20 && i != 35 && i != 36) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static boolean equal(CharSequence charSequence, CharSequence charSequence2, boolean z) {
        if (charSequence == charSequence2) {
            return true;
        }
        if (charSequence == null || charSequence2 == null || charSequence.length() != charSequence2.length()) {
            return false;
        }
        if (z) {
            for (int i = 0; i < charSequence.length(); i++) {
                if (charSequence.charAt(i) != charSequence2.charAt(i)) {
                    return false;
                }
            }
        } else {
            for (int i2 = 0; i2 < charSequence.length(); i2++) {
                if (!charsEqualIgnoreCase(charSequence.charAt(i2), charSequence2.charAt(i2))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean charsEqualIgnoreCase(char c, char c2) {
        return c == c2 || toUpperCase(c) == toUpperCase(c2) || toLowerCase(c) == toLowerCase(c2);
    }

    public static char toUpperCase(char c) {
        if (c < 'a') {
            return c;
        }
        return c <= 'z' ? (char) (c - ' ') : Character.toUpperCase(c);
    }

    public static char toLowerCase(char c) {
        if (c <= 'z') {
            return (c < 'A' || c > 'Z') ? c : (char) (c + io.netty.util.internal.StringUtil.SPACE);
        }
        return Character.toLowerCase(c);
    }

    public static String convertLineSeparators(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        return convertLineSeparators(str, false);
    }

    public static String convertLineSeparators(String str, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return convertLineSeparators(str, "\n", null, z);
    }

    public static String convertLineSeparators(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        if (str2 == null) {
            $$$reportNull$$$0(3);
        }
        return convertLineSeparators(str, str2, null);
    }

    public static CharSequence convertLineSeparators(CharSequence charSequence, String str) {
        if (charSequence == null) {
            $$$reportNull$$$0(4);
        }
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        return unifyLineSeparators(charSequence, str, null, false);
    }

    public static String convertLineSeparators(String str, String str2, int[] iArr) {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        if (str2 == null) {
            $$$reportNull$$$0(7);
        }
        return convertLineSeparators(str, str2, iArr, false);
    }

    public static String convertLineSeparators(String str, String str2, int[] iArr, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        if (str2 == null) {
            $$$reportNull$$$0(9);
        }
        String string = unifyLineSeparators(str, str2, iArr, z).toString();
        if (string == null) {
            $$$reportNull$$$0(10);
        }
        return string;
    }

    /* JADX WARN: Code duplicated, block: B:37:0x009e  */
    private static CharSequence unifyLineSeparators(CharSequence charSequence, String str, int[] iArr, boolean z) {
        if (charSequence == null) {
            $$$reportNull$$$0(11);
        }
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        boolean zEquals = "\n".equals(str);
        StringBuilder sb = null;
        int i = 0;
        int i2 = 0;
        while (i < charSequence.length()) {
            char cCharAt = charSequence.charAt(i);
            if (cCharAt == '\n') {
                if (!zEquals) {
                    if (sb == null) {
                        sb = new StringBuilder(charSequence.length());
                        sb.append(charSequence, 0, i2);
                    }
                    sb.append(str);
                    shiftOffsets(iArr, sb.length(), 1, str.length());
                } else if (sb == null) {
                    i2++;
                } else {
                    sb.append('\n');
                }
            } else if (cCharAt == '\r') {
                boolean z2 = i < charSequence.length() - 1 && charSequence.charAt(i + 1) == '\n';
                if (z2 || !z) {
                    if (sb == null) {
                        sb = new StringBuilder(charSequence.length());
                        sb.append(charSequence, 0, i2);
                    }
                    sb.append(str);
                    if (z2) {
                        i++;
                        shiftOffsets(iArr, sb.length(), 2, str.length());
                    } else {
                        shiftOffsets(iArr, sb.length(), 1, str.length());
                    }
                } else if (sb == null) {
                    i2++;
                } else {
                    sb.append('\r');
                }
            } else if (sb == null) {
                i2++;
            } else {
                sb.append(cCharAt);
            }
            i++;
        }
        if (sb != null) {
            charSequence = sb;
        }
        if (charSequence == null) {
            $$$reportNull$$$0(13);
        }
        return charSequence;
    }

    private static void shiftOffsets(int[] iArr, int i, int i2, int i3) {
        int i4;
        if (iArr == null || (i4 = i3 - i2) == 0) {
            return;
        }
        for (int i5 = 0; i5 < iArr.length; i5++) {
            int i6 = iArr[i5];
            if (i6 >= i + i2) {
                iArr[i5] = i6 + i4;
            }
        }
    }

    public static int parseInt(String str, int i) {
        if (str != null) {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException unused) {
            }
        }
        return i;
    }

    public static long parseLong(String str, long j) {
        if (str != null) {
            try {
                return Long.parseLong(str);
            } catch (NumberFormatException unused) {
            }
        }
        return j;
    }

    public static double parseDouble(String str, double d) {
        if (str != null) {
            try {
                return Double.parseDouble(str);
            } catch (NumberFormatException unused) {
            }
        }
        return d;
    }

    static <E extends Enum<E>> E parseEnum(String str, E e, Class<E> cls) {
        if (str == null) {
            $$$reportNull$$$0(14);
        }
        if (cls == null) {
            $$$reportNull$$$0(15);
        }
        try {
            return (E) Enum.valueOf(cls, str);
        } catch (Exception unused) {
            return e;
        }
    }

    public static String getShortName(Class<?> cls) {
        if (cls == null) {
            $$$reportNull$$$0(16);
        }
        return getShortName(cls.getName());
    }

    public static String getShortName(String str) {
        if (str == null) {
            $$$reportNull$$$0(17);
        }
        return getShortName(str, '.');
    }

    public static String getShortName(String str, char c) {
        if (str == null) {
            $$$reportNull$$$0(18);
        }
        int iLastIndexOf = str.lastIndexOf(c);
        if (iLastIndexOf < 0) {
            if (str == null) {
                $$$reportNull$$$0(20);
            }
            return str;
        }
        String strSubstring = str.substring(iLastIndexOf + 1);
        if (strSubstring == null) {
            $$$reportNull$$$0(19);
        }
        return strSubstring;
    }

    public static boolean endsWithChar(CharSequence charSequence, char c) {
        return (charSequence == null || charSequence.length() == 0 || charSequence.charAt(charSequence.length() - 1) != c) ? false : true;
    }

    public static boolean endsWith(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null) {
            $$$reportNull$$$0(21);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(22);
        }
        int length = charSequence.length();
        int length2 = charSequence2.length();
        if (length < length2) {
            return false;
        }
        for (int i = length - 1; i >= length - length2; i--) {
            if (charSequence.charAt(i) != charSequence2.charAt((i + length2) - length)) {
                return false;
            }
        }
        return true;
    }

    public static boolean startsWithIgnoreCase(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(23);
        }
        if (str2 == null) {
            $$$reportNull$$$0(24);
        }
        return startsWithIgnoreCase(str, 0, str2);
    }

    public static boolean startsWithIgnoreCase(String str, int i, String str2) {
        if (str == null) {
            $$$reportNull$$$0(25);
        }
        if (str2 == null) {
            $$$reportNull$$$0(26);
        }
        int length = str.length();
        int length2 = str2.length();
        return length >= length2 && str.regionMatches(true, i, str2, 0, length2);
    }

    public static boolean endsWithIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null) {
            $$$reportNull$$$0(27);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(28);
        }
        int length = charSequence.length();
        int length2 = charSequence2.length();
        if (length < length2) {
            return false;
        }
        for (int i = length - 1; i >= length - length2; i--) {
            if (!charsEqualIgnoreCase(charSequence.charAt(i), charSequence2.charAt((i + length2) - length))) {
                return false;
            }
        }
        return true;
    }

    public static int lastIndexOf(CharSequence charSequence, char c, int i, int i2) {
        if (charSequence == null) {
            $$$reportNull$$$0(29);
        }
        int iMax = Math.max(i, 0);
        for (int iMin = Math.min(i2, charSequence.length()) - 1; iMin >= iMax; iMin--) {
            if (charSequence.charAt(iMin) == c) {
                return iMin;
            }
        }
        return -1;
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static boolean isEmptyOrSpaces(CharSequence charSequence) {
        if (isEmpty(charSequence)) {
            return true;
        }
        for (int i = 0; i < charSequence.length(); i++) {
            if (charSequence.charAt(i) > ' ') {
                return false;
            }
        }
        return true;
    }

    public static String notNullize(String str) {
        return notNullize(str, "");
    }

    public static String notNullize(String str, String str2) {
        if (str2 == null) {
            $$$reportNull$$$0(30);
        }
        if (str == null) {
            str = str2;
        }
        if (str == null) {
            $$$reportNull$$$0(31);
        }
        return str;
    }

    public static List<String> splitHonorQuotes(String str, char c) {
        if (str == null) {
            $$$reportNull$$$0(32);
        }
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder(str.length());
        boolean z = false;
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == c && !z) {
                if (sb.length() > 0) {
                    arrayList.add(sb.toString());
                    sb.setLength(0);
                }
            } else {
                if ((cCharAt == '\"' || cCharAt == '\'') && (i <= 0 || str.charAt(i - 1) != '\\')) {
                    z = !z;
                }
                sb.append(cCharAt);
            }
        }
        if (sb.length() > 0) {
            arrayList.add(sb.toString());
        }
        return arrayList;
    }

    public static String formatFileSize(long j) {
        return formatFileSize(j, " ");
    }

    public static String formatFileSize(long j, String str) {
        if (str == null) {
            $$$reportNull$$$0(34);
        }
        if (j < 0) {
            throw new IllegalArgumentException("Invalid value: " + j);
        }
        if (j == 0) {
            String str2 = "0" + str + Util.C_BYTE;
            if (str2 == null) {
                $$$reportNull$$$0(35);
            }
            return str2;
        }
        double d = j;
        int iLog10 = (int) ((Math.log10(d) + 2.1714778384307465E-6d) / 3.0d);
        String str3 = new DecimalFormat("0.##").format(d / Math.pow(1000.0d, iLog10)) + str + new String[]{"B", "kB", "MB", "GB", "TB", "PB", "EB"}[iLog10];
        if (str3 == null) {
            $$$reportNull$$$0(36);
        }
        return str3;
    }

    public static boolean isQuotedString(String str) {
        if (str == null) {
            $$$reportNull$$$0(37);
        }
        return str.length() > 1 && (str.charAt(0) == '\'' || str.charAt(0) == '\"') && str.charAt(0) == str.charAt(str.length() - 1);
    }

    public static String unquoteString(String str) {
        if (str == null) {
            $$$reportNull$$$0(38);
        }
        if (isQuotedString(str)) {
            str = str.substring(1, str.length() - 1);
        }
        if (str == null) {
            $$$reportNull$$$0(39);
        }
        return str;
    }

    public static String unquoteString(String str, char c) {
        if (str == null) {
            $$$reportNull$$$0(40);
        }
        if (str.length() > 1 && c == str.charAt(0) && c == str.charAt(str.length() - 1)) {
            str = str.substring(1, str.length() - 1);
        }
        if (str == null) {
            $$$reportNull$$$0(41);
        }
        return str;
    }

    public static boolean startsWith(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null) {
            $$$reportNull$$$0(42);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(43);
        }
        int length = charSequence.length();
        int length2 = charSequence2.length();
        if (length < length2) {
            return false;
        }
        for (int i = 0; i < length2; i++) {
            if (charSequence.charAt(i) != charSequence2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static int stringHashCodeInsensitive(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(44);
        }
        return stringHashCodeInsensitive(charSequence, 0, charSequence.length());
    }

    public static int stringHashCodeInsensitive(CharSequence charSequence, int i, int i2) {
        if (charSequence == null) {
            $$$reportNull$$$0(45);
        }
        return stringHashCodeInsensitive(charSequence, i, i2, 0);
    }

    public static int stringHashCodeInsensitive(CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence == null) {
            $$$reportNull$$$0(46);
        }
        while (i < i2) {
            i3 = (i3 * 31) + toLowerCase(charSequence.charAt(i));
            i++;
        }
        return i3;
    }
}
