package com.reandroid.utils;

import com.beust.jcommander.Parameters;
import com.reandroid.utils.collection.ArrayIterator;
import io.netty.util.internal.StringUtil;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX INFO: loaded from: classes11.dex */
public class StringsUtil {
    public static final String EMPTY = ObjectsUtil.of("");
    private static final int MAX_STRING_APPEND = 5;

    public static boolean isAz(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    }

    public static boolean isAzOrDigits(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9');
    }

    public static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    public static boolean isLowerAZ(char c) {
        return c >= 'a' && c <= 'z';
    }

    public static boolean isUpperAZ(char c) {
        return c >= 'A' && c <= 'Z';
    }

    public static boolean isWhiteSpace(char c) {
        return c == '\t' || c == '\n' || c == '\r' || c == ' ';
    }

    public static char toLowercase(char c) {
        return (c > 'Z' || c < 'A') ? c : (char) (c + StringUtil.SPACE);
    }

    public static char toUpperCase(char c) {
        return (c > 'z' || c < 'a') ? c : (char) (c - ' ');
    }

    public static byte[] getASCII(String str) {
        int length = str.length();
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr[i] = (byte) str.charAt(i);
        }
        return bArr;
    }

    public static boolean isDigits(String str) {
        if (isEmpty(str)) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAzOrDigitsOr(String str, char[] cArr) {
        if (isEmpty(str)) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!isAzOrDigitsOr(str.charAt(i), cArr)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAzOrDigits(String str) {
        if (isEmpty(str)) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!isAzOrDigits(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAz(String str) {
        if (isEmpty(str)) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!isAz(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isLowerAZ(String str) {
        if (isEmpty(str)) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!isLowerAZ(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isUpperAZ(String str) {
        if (isEmpty(str)) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!isUpperAZ(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsUpperAZ(String str) {
        if (isEmpty(str)) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (isUpperAZ(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAzOrDigitsOr(char c, char[] cArr) {
        return isAzOrDigits(c) || contains(cArr, c);
    }

    public static boolean isWhiteSpace(String str) {
        return str == null || skipWhitespace(str) == str.length();
    }

    public static int skipWhitespace(String str) {
        return skipWhitespace(0, str);
    }

    public static int skipWhitespace(int i, String str) {
        int length = str.length();
        while (i < length) {
            if (!isWhiteSpace(str.charAt(i))) {
                return i;
            }
            i++;
        }
        return length;
    }

    public static int compare(String[] strArr, String[] strArr2) {
        if (strArr == strArr2) {
            return 0;
        }
        if (strArr == null) {
            return 1;
        }
        if (strArr2 == null) {
            return -1;
        }
        int length = strArr.length;
        int length2 = strArr2.length;
        if (length == 0 && length2 == 0) {
            return 0;
        }
        if (length == 0) {
            return 1;
        }
        if (length2 == 0) {
            return -1;
        }
        int i = length > length2 ? length2 : length;
        for (int i2 = 0; i2 < i; i2++) {
            int iCompareStrings = compareStrings(strArr[i2], strArr2[i2]);
            if (iCompareStrings != 0 && (length == length2 || i2 < i - 1)) {
                return iCompareStrings;
            }
        }
        return Integer.compare(length, length2);
    }

    public static String remove(String str, char[] cArr) {
        int length;
        if (str == null || (length = str.length()) == 0) {
            return null;
        }
        int iCountChar = countChar(str, cArr, false);
        if (iCountChar == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder(length - iCountChar);
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (!contains(cArr, cCharAt)) {
                sb.append(cCharAt);
            }
        }
        if (sb.length() == 0) {
            return null;
        }
        return sb.toString();
    }

    public static String[] removeEmpty(String[] strArr) {
        if (strArr == null) {
            return null;
        }
        int length = strArr.length;
        if (length == 0) {
            return strArr;
        }
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (isEmpty(strArr[i2])) {
                strArr[i2] = null;
            } else {
                i++;
            }
        }
        if (i == length) {
            return strArr;
        }
        String[] strArr2 = new String[i];
        int i3 = 0;
        for (String str : strArr) {
            if (str != null) {
                strArr2[i3] = str;
                i3++;
            }
        }
        return strArr2;
    }

    public static String[] split(String str, char c) {
        return split(str, c, true);
    }

    public static String[] split(String str, char[] cArr) {
        return split(str, cArr, true);
    }

    public static String[] split(String str, char[] cArr, boolean z) {
        if (str == null || str.length() == 0) {
            return new String[0];
        }
        int iCountChar = countChar(str, cArr, z);
        if (iCountChar == 0) {
            return new String[]{str};
        }
        int i = iCountChar + 1;
        String[] strArr = new String[i];
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        int i2 = 0;
        boolean z2 = false;
        for (int i3 = 0; i3 < length; i3++) {
            char cCharAt = str.charAt(i3);
            if (!contains(cArr, cCharAt)) {
                sb.append(cCharAt);
                z2 = false;
            } else if (!z2 || !z) {
                strArr[i2] = sb.toString();
                sb = new StringBuilder();
                i2++;
                z2 = true;
            }
        }
        if (i2 < i) {
            strArr[i2] = sb.toString();
        }
        return strArr;
    }

    public static String[] split(String str, char c, boolean z) {
        if (str == null || str.length() == 0) {
            return new String[0];
        }
        int iCountChar = countChar(str, c, z);
        if (iCountChar == 0) {
            return new String[]{str};
        }
        int i = iCountChar + 1;
        String[] strArr = new String[i];
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        int i2 = 0;
        boolean z2 = false;
        for (int i3 = 0; i3 < length; i3++) {
            char cCharAt = str.charAt(i3);
            if (cCharAt != c) {
                sb.append(cCharAt);
                z2 = false;
            } else if (!z2 || !z) {
                strArr[i2] = sb.toString();
                sb = new StringBuilder();
                i2++;
                z2 = true;
            }
        }
        if (i2 < i) {
            strArr[i2] = sb.toString();
        }
        return strArr;
    }

    public static int countChar(String str, char[] cArr, boolean z) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int length = str.length();
        int i = 0;
        boolean z2 = false;
        for (int i2 = 0; i2 < length; i2++) {
            if (!contains(cArr, str.charAt(i2))) {
                z2 = false;
            } else if (!z2 || !z) {
                i++;
                z2 = true;
            }
        }
        return i;
    }

    public static int countChar(String str, char c, boolean z) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int length = str.length();
        int i = 0;
        boolean z2 = false;
        for (int i2 = 0; i2 < length; i2++) {
            if (str.charAt(i2) != c) {
                z2 = false;
            } else if (!z2 || !z) {
                i++;
                z2 = true;
            }
        }
        return i;
    }

    private static boolean contains(char[] cArr, char c) {
        for (char c2 : cArr) {
            if (c2 == c) {
                return true;
            }
        }
        return false;
    }

    public static String toString(Collection<?> collection) {
        return toString(collection, 5);
    }

    public static String toString(Collection<?> collection, int i) {
        return toString(collection.iterator(), i, collection.size());
    }

    public static String toString(Iterator<?> it, int i, int i2) {
        return toString(", ", it, i, i2);
    }

    public static String toString(String str, Iterator<?> it, int i, int i2) {
        if (it == null) {
            return "null";
        }
        if (i < 0 && i2 >= 0) {
            i = i2;
        }
        StringBuilder sb = new StringBuilder();
        int i3 = 0;
        while (it.hasNext() && (i < 0 || i3 < i)) {
            if (i3 != 0) {
                sb.append(str);
            }
            sb.append(it.next());
            i3++;
        }
        if (i < 0) {
            i2 = i3;
        }
        StringBuilder sb2 = new StringBuilder(sb.length() + 20);
        if (i2 >= 0) {
            sb2.append("size=");
            sb2.append(i2);
            sb2.append(StringUtil.SPACE);
        }
        sb2.append('[');
        sb2.append(sb.toString());
        if (i3 < i2) {
            sb2.append(" ... ");
        }
        sb2.append(']');
        return sb2.toString();
    }

    public static String toString(Object[] objArr) {
        return toString(objArr, 5);
    }

    public static String toString(Object[] objArr, int i) {
        if (objArr == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder("length=");
        sb.append(objArr.length);
        sb.append(" [");
        if (i < 0 || i > objArr.length) {
            i = objArr.length;
        }
        int i2 = 0;
        while (i2 < i) {
            if (i2 != 0) {
                sb.append(", ");
            }
            sb.append(objArr[i2]);
            i2++;
        }
        if (i2 < objArr.length) {
            sb.append(" ... ");
        }
        sb.append(']');
        return sb.toString();
    }

    public static String emptyToNull(String str) {
        if (isEmpty(str)) {
            return null;
        }
        return str;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static String toUpperCase(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        char[] charArray = str.toCharArray();
        boolean z = false;
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            char upperCase = toUpperCase(c);
            if (c != upperCase) {
                charArray[i] = upperCase;
                z = true;
            }
        }
        return !z ? str : new String(charArray);
    }

    public static String toLowercase(String str) {
        char[] charArray = str.toCharArray();
        boolean z = false;
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            char lowercase = toLowercase(c);
            if (c != lowercase) {
                charArray[i] = lowercase;
                z = true;
            }
        }
        return !z ? str : new String(charArray);
    }

    public static void toStringSort(List<?> list) {
        if (list == null || list.size() < 2) {
            return;
        }
        list.sort(CompareUtil.getToStringComparator());
    }

    public static String formatNumber(long j, long j2) {
        return trailZeros(j, Long.toString(j2).length());
    }

    public static String trailZeros(long j, int i) {
        boolean z;
        if (j < 0) {
            j = -j;
            z = true;
        } else {
            z = false;
        }
        String string = Long.toString(j);
        String strAppend = append(string, ExternalAnnotationProvider.NULLABLE, i - string.length(), true);
        return z ? Parameters.DEFAULT_OPTION_PREFIXES + strAppend : strAppend;
    }

    public static String appendPostfix(String str, char c, int i) {
        return append(str, c, i, false);
    }

    public static String append(String str, char c, int i, boolean z) {
        StringBuilder sb = new StringBuilder(str.length() + i);
        if (!z) {
            sb.append(str);
        }
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(c);
        }
        if (z) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static int compareToString(Object obj, Object obj2) {
        return compareStrings(obj == null ? null : obj.toString(), obj2 != null ? obj2.toString() : null);
    }

    public static int compareStrings(String str, String str2) {
        return CompareUtil.compare(str, str2);
    }

    public static String join(Iterable<?> iterable, Object obj) {
        return join(iterable.iterator(), obj);
    }

    public static String join(Object[] objArr, Object obj) {
        return join((Iterator<?>) ArrayIterator.of(objArr), obj);
    }

    public static String join(Iterator<?> it, Object obj) {
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        while (it.hasNext()) {
            if (z) {
                sb.append(obj);
            }
            sb.append(it.next());
            z = true;
        }
        if (z) {
            return sb.toString();
        }
        return EMPTY;
    }

    public static int diffStart(String str, String str2) {
        if (!isEmpty(str) && !isEmpty(str2) && !str.equals(str2)) {
            int iMin = NumbersUtil.min(str.length(), str2.length());
            for (int i = 0; i < iMin; i++) {
                if (str.charAt(i) != str2.charAt(i)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static String trimStart(String str, char c) {
        if (str == null) {
            return null;
        }
        int i = 0;
        while (str.charAt(i) == c) {
            i++;
        }
        return i == 0 ? str : str.substring(i);
    }

    public static int indexOfFrom(String str, int i, char c) {
        if (str != null && i >= 0) {
            int length = str.length();
            while (i < length) {
                if (str.charAt(i) == c) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }

    public static boolean endsWith(String str, char c) {
        int length;
        return (str == null || (length = str.length()) == 0 || str.charAt(length - 1) != c) ? false : true;
    }

    public static boolean startsWith(String str, char c) {
        return !isEmpty(str) && str.charAt(0) == c;
    }
}
