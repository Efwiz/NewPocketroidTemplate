package org.eclipse.jgit.util;

import io.netty.util.internal.StringUtil;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Iterator;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jgit.internal.JGitText;

/* JADX INFO: loaded from: classes14.dex */
public final class StringUtils {
    private static final char[] LC = new char[91];

    static {
        char c = 0;
        while (true) {
            char[] cArr = LC;
            if (c >= cArr.length) {
                break;
            }
            cArr[c] = c;
            c = (char) (c + 1);
        }
        for (char c2 = 'A'; c2 <= 'Z'; c2 = (char) (c2 + 1)) {
            LC[c2] = (char) (c2 + StringUtil.SPACE);
        }
    }

    public static char toLowerCase(char c) {
        return c <= 'Z' ? LC[c] : c;
    }

    public static String toLowerCase(String str) {
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            sb.append(toLowerCase(str.charAt(i)));
        }
        return sb.toString();
    }

    public static String capitalize(String str) {
        int length;
        return (str == null || (length = str.length()) == 0) ? str : new StringBuilder(length).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    public static boolean equalsIgnoreCase(String str, String str2) {
        if (References.isSameObject(str, str2)) {
            return true;
        }
        if (str.length() != str2.length()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (toLowerCase(str.charAt(i)) != toLowerCase(str2.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static int compareIgnoreCase(String str, String str2) {
        for (int i = 0; i < str.length() && i < str2.length(); i++) {
            int lowerCase = toLowerCase(str.charAt(i)) - toLowerCase(str2.charAt(i));
            if (lowerCase != 0) {
                return lowerCase;
            }
        }
        return str.length() - str2.length();
    }

    public static int compareWithCase(String str, String str2) {
        for (int i = 0; i < str.length() && i < str2.length(); i++) {
            int iCharAt = str.charAt(i) - str2.charAt(i);
            if (iCharAt != 0) {
                return iCharAt;
            }
        }
        return str.length() - str2.length();
    }

    public static boolean toBoolean(String str) {
        if (str == null) {
            throw new NullPointerException(JGitText.get().expectedBooleanStringValue);
        }
        Boolean booleanOrNull = toBooleanOrNull(str);
        if (booleanOrNull == null) {
            throw new IllegalArgumentException(MessageFormat.format(JGitText.get().notABoolean, str));
        }
        return booleanOrNull.booleanValue();
    }

    public static Boolean toBooleanOrNull(String str) {
        if (str == null) {
            return null;
        }
        if (equalsIgnoreCase("yes", str) || equalsIgnoreCase("true", str) || equalsIgnoreCase(SchemaSymbols.ATTVAL_TRUE_1, str) || equalsIgnoreCase("on", str)) {
            return Boolean.TRUE;
        }
        if (equalsIgnoreCase("no", str) || equalsIgnoreCase("false", str) || equalsIgnoreCase("0", str) || equalsIgnoreCase("off", str)) {
            return Boolean.FALSE;
        }
        return null;
    }

    public static String join(Collection<String> collection, String str) {
        return join(collection, str, str);
    }

    public static String join(Collection<String> collection, String str, String str2) {
        StringBuilder sb = new StringBuilder();
        int size = collection.size();
        int i = size - 1;
        Iterator<String> it = collection.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            sb.append(it.next());
            if (i2 == size - 2) {
                sb.append(str2);
            } else if (i2 != i) {
                sb.append(str);
            }
            i2++;
        }
        return sb.toString();
    }

    private StringUtils() {
    }

    public static boolean isEmptyOrNull(String str) {
        return str == null || str.length() == 0;
    }

    public static String replaceLineBreaksWithSpace(String str) {
        int i;
        int length = str.length();
        char[] cArr = new char[length];
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            char cCharAt = str.charAt(i2);
            if (cCharAt != '\n') {
                if (cCharAt == '\r') {
                    int i4 = i2 + 1;
                    if (i4 < length && str.charAt(i4) == '\n') {
                        cArr[i3] = StringUtil.SPACE;
                        i3++;
                        i2 = i4;
                    } else {
                        i = i3 + 1;
                        cArr[i3] = StringUtil.SPACE;
                    }
                } else {
                    cArr[i3] = cCharAt;
                    i3++;
                }
                i2++;
            } else {
                i = i3 + 1;
                cArr[i3] = StringUtil.SPACE;
            }
            i3 = i;
            i2++;
        }
        return new String(cArr, 0, i3);
    }
}
