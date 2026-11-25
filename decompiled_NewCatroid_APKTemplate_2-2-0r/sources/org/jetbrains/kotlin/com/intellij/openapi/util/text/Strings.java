package org.jetbrains.kotlin.com.intellij.openapi.util.text;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.ContainerUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import name.antonsmirnov.firmata.FormatHelper;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.text.CharArrayCharSequence;

/* JADX INFO: loaded from: classes3.dex */
public final class Strings {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final List<String> REPLACES_REFS = Arrays.asList("&lt;", "&gt;", "&amp;", "&#39;", "&quot;");
    private static final List<String> REPLACES_DISP = Arrays.asList("<", ">", ContainerUtils.FIELD_DELIMITER, FormatHelper.QUOTE, "\"");
    public static final CharSequence EMPTY_CHAR_SEQUENCE = new CharArrayCharSequence(ArrayUtilRt.EMPTY_CHAR_ARRAY);

    /* JADX WARN: Code duplicated, block: B:22:0x0049 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 48 && i != 50 && i != 77 && i != 83 && i != 92 && i != 96 && i != 103 && i != 109 && i != 111 && i != 55 && i != 56 && i != 58 && i != 59 && i != 62 && i != 63) {
            switch (i) {
                default:
                    switch (i) {
                        case 44:
                        case 45:
                        case 46:
                            break;
                        default:
                            str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                            break;
                    }
                case 33:
                case 34:
                case 35:
                case 36:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        String str2 = str;
        if (i != 48 && i != 50 && i != 77 && i != 83 && i != 92 && i != 96 && i != 103 && i != 109 && i != 111 && i != 55 && i != 56 && i != 58 && i != 59 && i != 62 && i != 63) {
            switch (i) {
                case 33:
                case 34:
                case 35:
                case 36:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 44:
                        case 45:
                        case 46:
                            i2 = 2;
                            break;
                        default:
                            i2 = 3;
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 12:
            case 14:
            case 16:
                objArr[0] = "infix";
                break;
            case 2:
            case 8:
            case 9:
            case 10:
            case 17:
            case 18:
            case 19:
            case 21:
            case 23:
            case 25:
            case 32:
            case 51:
            case 53:
            case 57:
            case 60:
                objArr[0] = "s";
                break;
            case 3:
            case 4:
            case 6:
                objArr[0] = "value";
                break;
            case 5:
            case 7:
            case 20:
            case 22:
            case 24:
            case 26:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
                objArr[0] = "chars";
                break;
            case 11:
            case 13:
            case 15:
            default:
                objArr[0] = "sequence";
                break;
            case 27:
            case 29:
            case 31:
                objArr[0] = "where";
                break;
            case 28:
            case 30:
                objArr[0] = "what";
                break;
            case 33:
            case 34:
            case 35:
            case 36:
            case 44:
            case 45:
            case 46:
            case 48:
            case 50:
            case 55:
            case 56:
            case 58:
            case 59:
            case 62:
            case 63:
            case 77:
            case 83:
            case 92:
            case 96:
            case 103:
            case 109:
            case 111:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/text/Strings";
                break;
            case 37:
            case 39:
            case 72:
            case 73:
            case 74:
            case 75:
            case 78:
            case 79:
            case 80:
                objArr[0] = "text";
                break;
            case 38:
            case 61:
                objArr[0] = "prefix";
                break;
            case 40:
            case 42:
            case 52:
            case 54:
                objArr[0] = "suffix";
                break;
            case 41:
                objArr[0] = "str";
                break;
            case 43:
            case 47:
                objArr[0] = "word";
                break;
            case 49:
                objArr[0] = "defaultValue";
                break;
            case 76:
                objArr[0] = "builder";
                break;
            case 81:
                objArr[0] = TypedValues.TransitionType.S_FROM;
                break;
            case 82:
                objArr[0] = "to";
                break;
            case 84:
            case 88:
            case 94:
            case 98:
                objArr[0] = "f";
                break;
            case 85:
            case 89:
            case 91:
            case 95:
            case 99:
            case 102:
            case 105:
            case 107:
                objArr[0] = "separator";
                break;
            case 86:
            case 87:
            case 90:
            case 93:
            case 97:
                objArr[0] = FirebaseAnalytics.Param.ITEMS;
                break;
            case 100:
            case 106:
                objArr[0] = "result";
                break;
            case 101:
            case 104:
            case 108:
            case 110:
                objArr[0] = "strings";
                break;
        }
        if (i == 48 || i == 50) {
            objArr[1] = "notNullize";
        } else if (i == 77) {
            objArr[1] = "escapeToRegexp";
        } else if (i == 83) {
            objArr[1] = SchemaSymbols.ATTVAL_REPLACE;
        } else if (i != 92 && i != 96 && i != 103 && i != 109 && i != 111) {
            if (i != 55 && i != 56 && i != 58 && i != 59) {
                if (i != 62 && i != 63) {
                    switch (i) {
                        case 33:
                        case 34:
                        case 35:
                        case 36:
                            objArr[1] = "capitalize";
                            break;
                        default:
                            switch (i) {
                                case 44:
                                case 45:
                                case 46:
                                    objArr[1] = "pluralize";
                                    break;
                                default:
                                    objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/text/Strings";
                                    break;
                            }
                            break;
                    }
                } else {
                    objArr[1] = "trimStart";
                }
            } else {
                objArr[1] = "trimEnd";
            }
        } else {
            objArr[1] = "join";
        }
        switch (i) {
            case 3:
                objArr[2] = "containsChar";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[2] = "containsAnyChar";
                break;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                objArr[2] = "indexOf";
                break;
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
                objArr[2] = "indexOfAny";
                break;
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
                objArr[2] = "indexOfIgnoreCase";
                break;
            case 32:
                objArr[2] = "capitalize";
                break;
            case 33:
            case 34:
            case 35:
            case 36:
            case 44:
            case 45:
            case 46:
            case 48:
            case 50:
            case 55:
            case 56:
            case 58:
            case 59:
            case 62:
            case 63:
            case 77:
            case 83:
            case 92:
            case 96:
            case 103:
            case 109:
            case 111:
                break;
            case 37:
            case 38:
                objArr[2] = "startsWith";
                break;
            case 39:
            case 40:
                objArr[2] = "endsWith";
                break;
            case 41:
            case 42:
                objArr[2] = "endsWithIgnoreCase";
                break;
            case 43:
                objArr[2] = "pluralize";
                break;
            case 47:
                objArr[2] = "unpluralize";
                break;
            case 49:
                objArr[2] = "notNullize";
                break;
            case 51:
            case 52:
            case 53:
            case 54:
            case 57:
                objArr[2] = "trimEnd";
                break;
            case 60:
            case 61:
                objArr[2] = "trimStart";
                break;
            case 64:
            case 65:
            case 66:
            case 67:
                objArr[2] = "stringHashCode";
                break;
            case 68:
            case 69:
            case 70:
            case 71:
                objArr[2] = "stringHashCodeInsensitive";
                break;
            case 72:
            case 73:
            case 74:
                objArr[2] = "countChars";
                break;
            case 75:
            case 76:
                objArr[2] = "escapeToRegexp";
                break;
            case 78:
                objArr[2] = "unescapeXmlEntities";
                break;
            case 79:
                objArr[2] = "escapeXmlEntities";
                break;
            case 80:
            case 81:
            case 82:
                objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                break;
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 93:
            case 94:
            case 95:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 110:
                objArr[2] = "join";
                break;
            default:
                objArr[2] = "contains";
                break;
        }
        String str3 = String.format(str2, objArr);
        if (i != 48 && i != 50 && i != 77 && i != 83 && i != 92 && i != 96 && i != 103 && i != 109 && i != 111 && i != 55 && i != 56 && i != 58 && i != 59 && i != 62 && i != 63) {
            switch (i) {
                case 33:
                case 34:
                case 35:
                case 36:
                    break;
                default:
                    switch (i) {
                        case 44:
                        case 45:
                        case 46:
                            break;
                        default:
                            throw new IllegalArgumentException(str3);
                    }
                    break;
            }
        }
        throw new IllegalStateException(str3);
    }

    public static boolean isAscii(char c) {
        return c < 128;
    }

    public static int compare(char c, char c2, boolean z) {
        int i = c - c2;
        if (i == 0 || !z) {
            return i;
        }
        char upperCase = StringUtilRt.toUpperCase(c);
        char upperCase2 = StringUtilRt.toUpperCase(c2);
        int i2 = upperCase - upperCase2;
        return i2 != 0 ? StringUtilRt.toLowerCase(upperCase) - StringUtilRt.toLowerCase(upperCase2) : i2;
    }

    public static boolean charsMatch(char c, char c2, boolean z) {
        return compare(c, c2, z) == 0;
    }

    public static String toLowerCase(String str) {
        if (str == null) {
            return null;
        }
        return str.toLowerCase(Locale.ENGLISH);
    }

    public static char toLowerCase(char c) {
        return StringUtilRt.toLowerCase(c);
    }

    public static String toUpperCase(String str) {
        if (str == null) {
            return null;
        }
        return str.toUpperCase(Locale.ENGLISH);
    }

    public static char toUpperCase(char c) {
        return StringUtilRt.toUpperCase(c);
    }

    public static boolean contains(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null) {
            $$$reportNull$$$0(0);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(1);
        }
        return indexOf(charSequence, charSequence2) >= 0;
    }

    public static boolean contains(CharSequence charSequence, int i, int i2, char c) {
        if (charSequence == null) {
            $$$reportNull$$$0(2);
        }
        return indexOf(charSequence, c, i, i2) >= 0;
    }

    public static boolean containsChar(String str, char c) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        return str.indexOf(c) >= 0;
    }

    public static boolean containsAnyChar(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        if (str2 == null) {
            $$$reportNull$$$0(5);
        }
        if (str2.length() > str.length()) {
            return containsAnyChar(str, str2, 0, str.length());
        }
        return containsAnyChar(str2, str, 0, str2.length());
    }

    public static boolean containsAnyChar(String str, String str2, int i, int i2) {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        if (str2 == null) {
            $$$reportNull$$$0(7);
        }
        while (i < i2) {
            if (str2.indexOf(str.charAt(i)) >= 0) {
                return true;
            }
            i++;
        }
        return false;
    }

    public static int indexOf(CharSequence charSequence, char c) {
        if (charSequence == null) {
            $$$reportNull$$$0(8);
        }
        return indexOf(charSequence, c, 0, charSequence.length());
    }

    public static int indexOf(CharSequence charSequence, char c, int i) {
        if (charSequence == null) {
            $$$reportNull$$$0(9);
        }
        return indexOf(charSequence, c, i, charSequence.length());
    }

    public static int indexOf(CharSequence charSequence, char c, int i, int i2) {
        if (charSequence == null) {
            $$$reportNull$$$0(10);
        }
        int iMin = Math.min(i2, charSequence.length());
        for (int iMax = Math.max(i, 0); iMax < iMin; iMax++) {
            if (charSequence.charAt(iMax) == c) {
                return iMax;
            }
        }
        return -1;
    }

    public static int indexOf(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null) {
            $$$reportNull$$$0(11);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(12);
        }
        return indexOf(charSequence, charSequence2, 0);
    }

    public static int indexOf(CharSequence charSequence, CharSequence charSequence2, int i) {
        if (charSequence == null) {
            $$$reportNull$$$0(13);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(14);
        }
        return indexOf(charSequence, charSequence2, i, charSequence.length());
    }

    public static int indexOf(CharSequence charSequence, CharSequence charSequence2, int i, int i2) {
        if (charSequence == null) {
            $$$reportNull$$$0(15);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(16);
        }
        while (i <= i2 - charSequence2.length()) {
            if (startsWith(charSequence, i, charSequence2)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static int indexOf(CharSequence charSequence, char c, int i, int i2, boolean z) {
        if (charSequence == null) {
            $$$reportNull$$$0(17);
        }
        int iMin = Math.min(i2, charSequence.length());
        for (int iMax = Math.max(i, 0); iMax < iMin; iMax++) {
            if (charsMatch(charSequence.charAt(iMax), c, !z)) {
                return iMax;
            }
        }
        return -1;
    }

    public static int indexOf(char[] cArr, char c, int i, int i2, boolean z) {
        if (cArr == null) {
            $$$reportNull$$$0(18);
        }
        int iMin = Math.min(i2, cArr.length);
        for (int iMax = Math.max(i, 0); iMax < iMin; iMax++) {
            if (charsMatch(cArr[iMax], c, !z)) {
                return iMax;
            }
        }
        return -1;
    }

    public static int indexOfAny(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(19);
        }
        if (str2 == null) {
            $$$reportNull$$$0(20);
        }
        return indexOfAny(str, str2, 0, str.length());
    }

    public static int indexOfAny(CharSequence charSequence, String str) {
        if (charSequence == null) {
            $$$reportNull$$$0(21);
        }
        if (str == null) {
            $$$reportNull$$$0(22);
        }
        return indexOfAny(charSequence, str, 0, charSequence.length());
    }

    public static int indexOfAny(String str, String str2, int i, int i2) {
        if (str == null) {
            $$$reportNull$$$0(23);
        }
        if (str2 == null) {
            $$$reportNull$$$0(24);
        }
        return indexOfAny((CharSequence) str, str2, i, i2);
    }

    public static int indexOfAny(CharSequence charSequence, String str, int i, int i2) {
        if (charSequence == null) {
            $$$reportNull$$$0(25);
        }
        if (str == null) {
            $$$reportNull$$$0(26);
        }
        if (str.isEmpty()) {
            return -1;
        }
        int iMin = Math.min(i2, charSequence.length());
        for (int iMax = Math.max(i, 0); iMax < iMin; iMax++) {
            if (containsChar(str, charSequence.charAt(iMax))) {
                return iMax;
            }
        }
        return -1;
    }

    public static int indexOfIgnoreCase(String str, String str2, int i) {
        if (str == null) {
            $$$reportNull$$$0(27);
        }
        if (str2 == null) {
            $$$reportNull$$$0(28);
        }
        return indexOfIgnoreCase((CharSequence) str, (CharSequence) str2, i);
    }

    public static int indexOfIgnoreCase(CharSequence charSequence, CharSequence charSequence2, int i) {
        if (charSequence == null) {
            $$$reportNull$$$0(29);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(30);
        }
        int length = charSequence2.length();
        int length2 = charSequence.length();
        if (i >= length2) {
            if (length == 0) {
                return length2;
            }
            return -1;
        }
        if (i < 0) {
            i = 0;
        }
        if (length == 0) {
            return i;
        }
        char cCharAt = charSequence2.charAt(0);
        int i2 = length2 - length;
        while (i <= i2) {
            if (!charsEqualIgnoreCase(charSequence.charAt(i), cCharAt)) {
                do {
                    i++;
                    if (i > i2) {
                        break;
                    }
                } while (!charsEqualIgnoreCase(charSequence.charAt(i), cCharAt));
            }
            if (i <= i2) {
                int i3 = i + 1;
                int i4 = (i3 + length) - 1;
                for (int i5 = 1; i3 < i4 && charsEqualIgnoreCase(charSequence.charAt(i3), charSequence2.charAt(i5)); i5++) {
                    i3++;
                }
                if (i3 == i4) {
                    return i;
                }
            }
            i++;
        }
        return -1;
    }

    public static int indexOfIgnoreCase(String str, char c, int i) {
        if (str == null) {
            $$$reportNull$$$0(31);
        }
        int length = str.length();
        for (int iMax = Math.max(i, 0); iMax < length; iMax++) {
            if (charsEqualIgnoreCase(str.charAt(iMax), c)) {
                return iMax;
            }
        }
        return -1;
    }

    public static boolean charsEqualIgnoreCase(char c, char c2) {
        return charsMatch(c, c2, true);
    }

    public static String capitalize(String str) {
        if (str == null) {
            $$$reportNull$$$0(32);
        }
        if (str.isEmpty()) {
            if (str == null) {
                $$$reportNull$$$0(33);
            }
            return str;
        }
        if (str.length() == 1) {
            String upperCase = toUpperCase(str);
            if (upperCase == null) {
                $$$reportNull$$$0(34);
            }
            return upperCase;
        }
        if (Character.isUpperCase(str.charAt(0))) {
            if (str == null) {
                $$$reportNull$$$0(35);
            }
            return str;
        }
        String str2 = toUpperCase(str.charAt(0)) + str.substring(1);
        if (str2 == null) {
            $$$reportNull$$$0(36);
        }
        return str2;
    }

    public static boolean isCapitalized(String str) {
        return (str == null || str.isEmpty() || !Character.isUpperCase(str.charAt(0))) ? false : true;
    }

    public static boolean startsWith(CharSequence charSequence, int i, CharSequence charSequence2) {
        if (charSequence == null) {
            $$$reportNull$$$0(37);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(38);
        }
        int length = charSequence.length();
        if (i < 0 || i > length) {
            throw new IllegalArgumentException("Index is out of bounds: " + i + ", length: " + length);
        }
        int i2 = length - i;
        int length2 = charSequence2.length();
        if (i2 < length2) {
            return false;
        }
        for (int i3 = 0; i3 < length2; i3++) {
            if (charSequence.charAt(i3 + i) != charSequence2.charAt(i3)) {
                return false;
            }
        }
        return true;
    }

    public static boolean endsWith(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null) {
            $$$reportNull$$$0(39);
        }
        if (charSequence2 == null) {
            $$$reportNull$$$0(40);
        }
        return StringUtilRt.endsWith(charSequence, charSequence2);
    }

    public static boolean endsWithChar(CharSequence charSequence, char c) {
        return StringUtilRt.endsWithChar(charSequence, c);
    }

    public static boolean endsWithIgnoreCase(CharSequence charSequence, String str) {
        if (charSequence == null) {
            $$$reportNull$$$0(41);
        }
        if (str == null) {
            $$$reportNull$$$0(42);
        }
        return StringUtilRt.endsWithIgnoreCase(charSequence, str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return StringUtilRt.isEmpty(charSequence);
    }

    public static String pluralize(String str) {
        if (str == null) {
            $$$reportNull$$$0(43);
        }
        String strPlural = Pluralizer.PLURALIZER.plural(str);
        if (strPlural != null) {
            if (strPlural == null) {
                $$$reportNull$$$0(44);
            }
            return strPlural;
        }
        if (str.endsWith("s")) {
            String strRestoreCase = Pluralizer.restoreCase(str, str + "es");
            if (strRestoreCase == null) {
                $$$reportNull$$$0(45);
            }
            return strRestoreCase;
        }
        String strRestoreCase2 = Pluralizer.restoreCase(str, str + "s");
        if (strRestoreCase2 == null) {
            $$$reportNull$$$0(46);
        }
        return strRestoreCase2;
    }

    public static String unpluralize(String str) {
        if (str == null) {
            $$$reportNull$$$0(47);
        }
        String strSingular = Pluralizer.PLURALIZER.singular(str);
        if (strSingular != null) {
            return strSingular;
        }
        if (str.endsWith("es")) {
            return nullize(trimEnd(str, "es", true));
        }
        if (str.endsWith("s")) {
            return nullize(trimEnd(str, "s", true));
        }
        return null;
    }

    public static String notNullize(String str) {
        String strNotNullize = StringUtilRt.notNullize(str);
        if (strNotNullize == null) {
            $$$reportNull$$$0(48);
        }
        return strNotNullize;
    }

    public static String notNullize(String str, String str2) {
        if (str2 == null) {
            $$$reportNull$$$0(49);
        }
        String strNotNullize = StringUtilRt.notNullize(str, str2);
        if (strNotNullize == null) {
            $$$reportNull$$$0(50);
        }
        return strNotNullize;
    }

    public static String nullize(String str) {
        return nullize(str, false);
    }

    public static String nullize(String str, String str2) {
        if (isEmpty(str) || Objects.equals(str, str2)) {
            return null;
        }
        return str;
    }

    public static String nullize(String str, boolean z) {
        if (z ? isEmptyOrSpaces(str) : isEmpty(str)) {
            return null;
        }
        return str;
    }

    public static boolean isEmptyOrSpaces(CharSequence charSequence) {
        return StringUtilRt.isEmptyOrSpaces(charSequence);
    }

    public static String trim(String str) {
        if (str == null) {
            return null;
        }
        return str.trim();
    }

    public static String trimEnd(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(51);
        }
        if (str2 == null) {
            $$$reportNull$$$0(52);
        }
        return trimEnd(str, str2, false);
    }

    public static String trimEnd(String str, String str2, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(53);
        }
        if (str2 == null) {
            $$$reportNull$$$0(54);
        }
        if (!(z ? endsWithIgnoreCase(str, str2) : str.endsWith(str2))) {
            if (str == null) {
                $$$reportNull$$$0(56);
            }
            return str;
        }
        String strSubstring = str.substring(0, str.length() - str2.length());
        if (strSubstring == null) {
            $$$reportNull$$$0(55);
        }
        return strSubstring;
    }

    public static String trimEnd(String str, char c) {
        if (str == null) {
            $$$reportNull$$$0(57);
        }
        if (!endsWithChar(str, c)) {
            if (str == null) {
                $$$reportNull$$$0(59);
            }
            return str;
        }
        String strSubstring = str.substring(0, str.length() - 1);
        if (strSubstring == null) {
            $$$reportNull$$$0(58);
        }
        return strSubstring;
    }

    public static String trimStart(String str, String str2) {
        if (str == null) {
            $$$reportNull$$$0(60);
        }
        if (str2 == null) {
            $$$reportNull$$$0(61);
        }
        if (!str.startsWith(str2)) {
            if (str == null) {
                $$$reportNull$$$0(63);
            }
            return str;
        }
        String strSubstring = str.substring(str2.length());
        if (strSubstring == null) {
            $$$reportNull$$$0(62);
        }
        return strSubstring;
    }

    public static int stringHashCode(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(64);
        }
        if ((charSequence instanceof String) || (charSequence instanceof CharSequenceWithStringHash)) {
            return charSequence.hashCode();
        }
        return stringHashCode(charSequence, 0, charSequence.length());
    }

    public static int stringHashCode(CharSequence charSequence, int i, int i2) {
        if (charSequence == null) {
            $$$reportNull$$$0(65);
        }
        return stringHashCode(charSequence, i, i2, 0);
    }

    public static int stringHashCode(CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence == null) {
            $$$reportNull$$$0(66);
        }
        while (i < i2) {
            i3 = (i3 * 31) + charSequence.charAt(i);
            i++;
        }
        return i3;
    }

    public static int stringHashCode(char[] cArr, int i, int i2) {
        if (cArr == null) {
            $$$reportNull$$$0(67);
        }
        int i3 = 0;
        while (i < i2) {
            i3 = (i3 * 31) + cArr[i];
            i++;
        }
        return i3;
    }

    public static int stringHashCodeInsensitive(char[] cArr, int i, int i2) {
        if (cArr == null) {
            $$$reportNull$$$0(68);
        }
        int lowerCase = 0;
        while (i < i2) {
            lowerCase = (lowerCase * 31) + toLowerCase(cArr[i]);
            i++;
        }
        return lowerCase;
    }

    public static int stringHashCodeInsensitive(CharSequence charSequence, int i, int i2) {
        if (charSequence == null) {
            $$$reportNull$$$0(69);
        }
        return StringUtilRt.stringHashCodeInsensitive(charSequence, i, i2);
    }

    public static int stringHashCodeInsensitive(CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence == null) {
            $$$reportNull$$$0(70);
        }
        return StringUtilRt.stringHashCodeInsensitive(charSequence, i, i2, i3);
    }

    public static int stringHashCodeInsensitive(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(71);
        }
        return StringUtilRt.stringHashCodeInsensitive(charSequence);
    }

    public static int countChars(CharSequence charSequence, char c) {
        if (charSequence == null) {
            $$$reportNull$$$0(72);
        }
        return countChars(charSequence, c, 0, false);
    }

    public static int countChars(CharSequence charSequence, char c, int i, boolean z) {
        if (charSequence == null) {
            $$$reportNull$$$0(73);
        }
        return countChars(charSequence, c, i, charSequence.length(), z);
    }

    public static int countChars(CharSequence charSequence, char c, int i, int i2, boolean z) {
        if (charSequence == null) {
            $$$reportNull$$$0(74);
        }
        boolean z2 = i <= i2;
        int iMax = z2 ? Math.max(0, i) : Math.min(charSequence.length(), i);
        int iMin = z2 ? Math.min(charSequence.length(), i2) : Math.max(0, i2);
        if (!z2) {
            iMax--;
        }
        int i3 = 0;
        while (true) {
            if (z2 != (iMax < iMin)) {
                break;
            }
            if (charSequence.charAt(iMax) != c) {
                if (z) {
                    break;
                }
            } else {
                i3++;
            }
            iMax += z2 ? 1 : -1;
        }
        return i3;
    }

    public static StringBuilder escapeToRegexp(CharSequence charSequence, StringBuilder sb) {
        if (charSequence == null) {
            $$$reportNull$$$0(75);
        }
        if (sb == null) {
            $$$reportNull$$$0(76);
        }
        for (int i = 0; i < charSequence.length(); i++) {
            char cCharAt = charSequence.charAt(i);
            if (cCharAt == ' ' || Character.isLetter(cCharAt) || Character.isDigit(cCharAt) || cCharAt == '_') {
                sb.append(cCharAt);
            } else if (cCharAt == '\n') {
                sb.append("\\n");
            } else if (cCharAt == '\r') {
                sb.append("\\r");
            } else {
                Character.UnicodeBlock unicodeBlockOf = Character.UnicodeBlock.of(cCharAt);
                if (unicodeBlockOf == Character.UnicodeBlock.HIGH_SURROGATES || unicodeBlockOf == Character.UnicodeBlock.LOW_SURROGATES) {
                    sb.append(cCharAt);
                } else {
                    sb.append('\\').append(cCharAt);
                }
            }
        }
        if (sb == null) {
            $$$reportNull$$$0(77);
        }
        return sb;
    }

    public static String unescapeXmlEntities(String str) {
        if (str == null) {
            $$$reportNull$$$0(78);
        }
        return replace(str, REPLACES_REFS, REPLACES_DISP);
    }

    public static String escapeXmlEntities(String str) {
        if (str == null) {
            $$$reportNull$$$0(79);
        }
        return replace(str, REPLACES_DISP, REPLACES_REFS);
    }

    public static String replace(String str, List<String> list, List<String> list2) {
        if (str == null) {
            $$$reportNull$$$0(80);
        }
        if (list == null) {
            $$$reportNull$$$0(81);
        }
        if (list2 == null) {
            $$$reportNull$$$0(82);
        }
        StringBuilder sb = null;
        int i = 0;
        while (i < str.length()) {
            int i2 = 0;
            while (true) {
                if (i2 >= list.size()) {
                    if (sb == null) {
                        break;
                    }
                    sb.append(str.charAt(i));
                    break;
                }
                String str2 = list.get(i2);
                String str3 = list2.get(i2);
                int length = str2.length();
                if (length != 0 && str.regionMatches(i, str2, 0, length)) {
                    if (sb == null) {
                        sb = new StringBuilder(str.length());
                        sb.append((CharSequence) str, 0, i);
                    }
                    sb.append(str3);
                    i += length - 1;
                    break;
                }
                i2++;
            }
            i++;
        }
        if (sb != null) {
            str = sb.toString();
        }
        if (str == null) {
            $$$reportNull$$$0(83);
        }
        return str;
    }

    public static <T> String join(T[] tArr, Function<? super T, String> function, String str) {
        if (function == null) {
            $$$reportNull$$$0(84);
        }
        if (str == null) {
            $$$reportNull$$$0(85);
        }
        if (tArr == null) {
            $$$reportNull$$$0(86);
        }
        return join((Collection) Arrays.asList(tArr), (Function) function, str);
    }

    public static <T> String join(Collection<? extends T> collection, Function<? super T, String> function, String str) {
        if (collection == null) {
            $$$reportNull$$$0(87);
        }
        if (function == null) {
            $$$reportNull$$$0(88);
        }
        if (str == null) {
            $$$reportNull$$$0(89);
        }
        if (collection.isEmpty()) {
            return "";
        }
        return collection.size() == 1 ? notNullize(function.fun(collection.iterator().next())) : join((Iterable) collection, (Function) function, str);
    }

    public static String join(Iterable<?> iterable, String str) {
        if (iterable == null) {
            $$$reportNull$$$0(90);
        }
        if (str == null) {
            $$$reportNull$$$0(91);
        }
        StringBuilder sb = new StringBuilder();
        Iterator<?> it = iterable.iterator();
        while (it.hasNext()) {
            sb.append(it.next()).append(str);
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - str.length());
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(92);
        }
        return string;
    }

    public static <T> String join(Iterable<? extends T> iterable, Function<? super T, ? extends CharSequence> function, String str) {
        if (iterable == null) {
            $$$reportNull$$$0(93);
        }
        if (function == null) {
            $$$reportNull$$$0(94);
        }
        if (str == null) {
            $$$reportNull$$$0(95);
        }
        StringBuilder sb = new StringBuilder();
        join(iterable, function, str, sb);
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(96);
        }
        return string;
    }

    public static <T> void join(Iterable<? extends T> iterable, Function<? super T, ? extends CharSequence> function, String str, StringBuilder sb) {
        if (iterable == null) {
            $$$reportNull$$$0(97);
        }
        if (function == null) {
            $$$reportNull$$$0(98);
        }
        if (str == null) {
            $$$reportNull$$$0(99);
        }
        if (sb == null) {
            $$$reportNull$$$0(100);
        }
        Iterator<? extends T> it = iterable.iterator();
        boolean z = true;
        while (it.hasNext()) {
            CharSequence charSequenceFun = function.fun(it.next());
            if (!isEmpty(charSequenceFun)) {
                if (z) {
                    z = false;
                } else {
                    sb.append(str);
                }
                sb.append(charSequenceFun);
            }
        }
    }

    public static String join(Collection<String> collection, String str) {
        if (collection == null) {
            $$$reportNull$$$0(101);
        }
        if (str == null) {
            $$$reportNull$$$0(102);
        }
        if (collection.size() <= 1) {
            return notNullize(collection.isEmpty() ? null : collection.iterator().next());
        }
        StringBuilder sb = new StringBuilder();
        join(collection, str, sb);
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(103);
        }
        return string;
    }

    public static void join(Collection<String> collection, String str, StringBuilder sb) {
        if (collection == null) {
            $$$reportNull$$$0(104);
        }
        if (str == null) {
            $$$reportNull$$$0(105);
        }
        if (sb == null) {
            $$$reportNull$$$0(106);
        }
        boolean z = true;
        for (String str2 : collection) {
            if (str2 != null) {
                if (z) {
                    z = false;
                } else {
                    sb.append(str);
                }
                sb.append(str2);
            }
        }
    }

    public static String join(int[] iArr, String str) {
        if (str == null) {
            $$$reportNull$$$0(107);
        }
        if (iArr == null) {
            $$$reportNull$$$0(108);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iArr.length; i++) {
            if (i > 0) {
                sb.append(str);
            }
            sb.append(iArr[i]);
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(109);
        }
        return string;
    }

    public static String join(String... strArr) {
        if (strArr == null) {
            $$$reportNull$$$0(110);
        }
        if (strArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String str : strArr) {
            sb.append(str);
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(111);
        }
        return string;
    }
}
