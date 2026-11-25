package com.android.tools.r8.utils;

import androidx.exifinterface.media.ExifInterface;
import com.android.tools.r8.AbstractC0402a;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.internal.C1023Gs;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.naming.C3605b;
import java.io.File;
import java.nio.file.Path;
import java.util.function.Function;
import net.bytebuddy.description.method.MethodDescription;
import okhttp3.HttpUrl;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: renamed from: com.android.tools.r8.utils.u0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class C3970u0 {
    private static final AbstractC2178hm a = AbstractC2178hm.e().a(PsiKeyword.VOID, ExifInterface.GPS_MEASUREMENT_INTERRUPTED).a("boolean", "Z").a("byte", "B").a("short", ExifInterface.LATITUDE_SOUTH).a(PsiKeyword.CHAR, "C").a("int", "I").a("long", "J").a("float", "F").a("double", "D").a();
    static final /* synthetic */ boolean b = true;
    public static final /* synthetic */ int c = 0;

    public static boolean A(String str) {
        if (z(str)) {
            return true;
        }
        if (str.length() != 1 ? false : a(str.charAt(0))) {
            return true;
        }
        return (str.length() >= 2 && str.charAt(0) == '[') ? A(str.substring(1)) : false;
    }

    public static boolean B(String str) {
        if (str.length() < 3 || str.charAt(0) != 'L' || str.charAt(str.length() - 1) != ';' || str.charAt(1) == '/' || str.charAt(str.length() - 2) == '/') {
            return false;
        }
        int iCharCount = 1;
        while (iCharCount < str.length() - 1) {
            int iCodePointAt = str.codePointAt(iCharCount);
            if (iCodePointAt != 47 && !AbstractC3916j1.d(iCodePointAt)) {
                return false;
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
        return true;
    }

    public static boolean C(String str) {
        boolean zC;
        int i;
        if (str.isEmpty() || str.charAt(0) != '[') {
            zC = false;
        } else {
            int i2 = -1;
            while (true) {
                i = i2 + 1;
                if (i >= str.length() || str.charAt(i) != '[') {
                    break;
                }
                i2 = i;
            }
            zC = C(i2 >= 0 ? str.substring(i) : str);
        }
        if (!zC && !B(str)) {
            if (!(str.length() != 1 ? false : a(str.charAt(0))) && !G(str)) {
                return false;
            }
        }
        return true;
    }

    public static boolean D(String str) {
        int iCharCount;
        if (str.isEmpty()) {
            return false;
        }
        int length = str.length();
        if (str.charAt(0) != '<') {
            iCharCount = 0;
        } else {
            if (str.charAt(length - 1) != '>') {
                return false;
            }
            length--;
            iCharCount = 1;
        }
        while (iCharCount < length) {
            int iCodePointAt = str.codePointAt(iCharCount);
            if (!AbstractC3916j1.d(iCodePointAt)) {
                return false;
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
        return true;
    }

    public static boolean E(String str) {
        if (str.length() == 0) {
            return false;
        }
        int i = 0;
        char c2 = 0;
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt == ';' || cCharAt == '[' || cCharAt == '/') {
                return false;
            }
            if (cCharAt == '.' && (i == 0 || c2 == '.')) {
                return false;
            }
            i++;
            c2 = cCharAt;
        }
        return true;
    }

    public static boolean F(String str) {
        if (str.isEmpty()) {
            return false;
        }
        if (str.charAt(0) == '<' && (str.equals("<init>") || str.equals(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME))) {
            return true;
        }
        int iCharCount = 0;
        while (iCharCount < str.length()) {
            int iCodePointAt = str.codePointAt(iCharCount);
            if (!AbstractC3916j1.d(iCodePointAt)) {
                return false;
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
        return true;
    }

    public static boolean G(String str) {
        return str.length() == 1 && str.charAt(0) == 'V';
    }

    public static String H(String str) {
        if (b || str.indexOf(47) == -1) {
            return a(str, false, false);
        }
        throw new AssertionError();
    }

    public static String I(String str) {
        if (b || str.indexOf(47) == -1) {
            return a(str, false, true);
        }
        throw new AssertionError();
    }

    public static String J(String str) {
        return a(str, true, false);
    }

    public static boolean a(char c2) {
        return c2 == 'Z' || c2 == 'B' || c2 == 'S' || c2 == 'C' || c2 == 'I' || c2 == 'F' || c2 == 'J' || c2 == 'D';
    }

    public static String b(String str) {
        return a(str, (C3605b) null);
    }

    public static String c(Class<?> cls) {
        return H(cls.getTypeName());
    }

    public static int d(String str) {
        int i;
        int length = str.length();
        int i2 = 0;
        int i3 = 1;
        while (i3 < length) {
            int i4 = i3 + 1;
            char cCharAt = str.charAt(i3);
            if (cCharAt == ')') {
                i3 = i4;
                break;
            }
            if (cCharAt == 'L') {
                while (true) {
                    if (i4 >= length) {
                        i = i4;
                        break;
                    }
                    i = i4 + 1;
                    if (str.charAt(i4) == ';') {
                        break;
                    }
                    i4 = i;
                }
                if (i >= length || str.charAt(i - 1) != ';') {
                    throw new C1023Gs(str);
                }
                i2++;
                i3 = i;
            } else {
                if (cCharAt != '[') {
                    i2++;
                }
                i3 = i4;
            }
        }
        if (i3 >= length || str.charAt(i3 - 1) != ')') {
            throw new C1023Gs(str);
        }
        return i2;
    }

    /* JADX WARN: Code duplicated, block: B:41:0x007d  */
    public static String[] e(String str) {
        int i;
        String[] strArr = new String[d(str)];
        int i2 = 0;
        int i3 = 1;
        while (true) {
            char cCharAt = str.charAt(i3);
            if (cCharAt == ')') {
                return strArr;
            }
            if (cCharAt == 'F') {
                i = i2 + 1;
                strArr[i2] = Character.toString(cCharAt);
                i2 = i;
            } else if (cCharAt != 'L') {
                if (cCharAt == 'S') {
                    i = i2 + 1;
                    strArr[i2] = Character.toString(cCharAt);
                } else {
                    if (cCharAt == 'V') {
                        throw new C1023Gs(str);
                    }
                    if (cCharAt == 'I' || cCharAt == 'J' || cCharAt == 'Z') {
                        i = i2 + 1;
                        strArr[i2] = Character.toString(cCharAt);
                    } else if (cCharAt != '[') {
                        switch (cCharAt) {
                            case 'B':
                            case 'C':
                            case 'D':
                                i = i2 + 1;
                                strArr[i2] = Character.toString(cCharAt);
                                break;
                            default:
                                throw new C1023Gs(str);
                        }
                    } else {
                        int i4 = i3;
                        do {
                            i4++;
                        } while (str.charAt(i4) == '[');
                        if (str.charAt(i4) == 'L') {
                            do {
                                i4++;
                            } while (str.charAt(i4) != ';');
                        }
                        i = i2 + 1;
                        strArr[i2] = str.substring(i3, i4 + 1);
                        i3 = i4;
                    }
                }
                i2 = i;
            } else {
                int i5 = i3;
                while (true) {
                    int i6 = i5 + 1;
                    if (str.charAt(i6) != ';') {
                        i5 = i6;
                    } else {
                        strArr[i2] = str.substring(i3, i5 + 2);
                        i2++;
                        i3 = i6;
                    }
                }
            }
            i3++;
        }
    }

    public static String f(String str) {
        if (b || z(str)) {
            return AbstractC0402a.a(str, 1, 1);
        }
        throw new AssertionError();
    }

    public static String g(String str) {
        return str.replace('.', '/');
    }

    public static String h(String str) {
        return k(str).replace('$', '.');
    }

    public static String i(String str) {
        if (b || z(str)) {
            return AbstractC0402a.a(str, 1, 1);
        }
        throw new AssertionError("Invalid class descriptor " + str);
    }

    public static String j(String str) {
        if (!b && (str == null || !z(str))) {
            throw new AssertionError();
        }
        return i(str) + ".class";
    }

    public static String k(String str) {
        return i(str).replace('/', '.');
    }

    public static String l(String str) {
        if (b || str != null) {
            return "L" + str + ";";
        }
        throw new AssertionError();
    }

    public static String m(String str) {
        boolean z = b;
        if (!z && str == null) {
            throw new AssertionError();
        }
        if (!z && str.contains("[")) {
            throw new AssertionError(str);
        }
        return "L" + str.replace('.', '$') + ";";
    }

    public static String n(String str) {
        return str.replace('/', '.');
    }

    public static String o(String str) {
        return str.replace('.', '/');
    }

    public static String p(String str) {
        int iLastIndexOf = str.lastIndexOf(47);
        return iLastIndexOf < 0 ? "" : str.substring(0, iLastIndexOf).replace('/', '.');
    }

    public static String q(String str) {
        return p(i(str));
    }

    public static String r(String str) {
        return p(i(H(str)));
    }

    public static String s(String str) {
        boolean z = b;
        if (!z && !str.startsWith("L")) {
            throw new AssertionError();
        }
        if (!z && !str.endsWith(";")) {
            throw new AssertionError();
        }
        return AbstractC0402a.a(str, 1, 1) + ".class";
    }

    public static String t(String str) {
        if (!b && !E(str)) {
            throw new AssertionError();
        }
        return str.replace('.', '/') + ".class";
    }

    public static String u(String str) {
        if (b || str.indexOf(41) != -1) {
            return str.substring(str.indexOf(41) + 1);
        }
        throw new AssertionError();
    }

    public static String v(String str) {
        return AbstractC0402a.a(str, 1, Integer.max(str.lastIndexOf("/"), 0) + 1);
    }

    public static String w(String str) {
        String strI = i(str);
        int iLastIndexOf = strI.lastIndexOf(47);
        return iLastIndexOf < 0 ? strI : strI.substring(iLastIndexOf + 1);
    }

    public static C3966t0 x(String str) {
        boolean z = b;
        if (!z && str == null) {
            throw new AssertionError();
        }
        if (!z && !str.endsWith(".class")) {
            throw new AssertionError("Name " + str + " must have .class suffix");
        }
        if (!z && !str.startsWith("/modules")) {
            throw new AssertionError("Name " + str + " must have /modules prefix");
        }
        if (!z && str.charAt(8) != '/') {
            throw new AssertionError();
        }
        int iIndexOf = str.indexOf(47, 9);
        String strSubstring = str.substring(9, iIndexOf);
        String strA = AbstractC0402a.a(str, 6, iIndexOf + 1);
        if (strA.indexOf(46) == -1) {
            return new C3966t0(strSubstring, "L" + strA + ";");
        }
        throw new C1367Tb("Unexpected class file name: " + str);
    }

    public static String y(String str) {
        boolean z = b;
        if (!z && str == null) {
            throw new AssertionError();
        }
        if (!z && !str.endsWith(".class")) {
            throw new AssertionError("Name " + str + " must have .class suffix");
        }
        String strA = AbstractC0402a.a(str, 6, 0);
        if (strA.indexOf(46) == -1) {
            return "L" + strA + ";";
        }
        throw new C1367Tb("Unexpected class file name: " + str);
    }

    public static boolean z(String str) {
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        if (length < 3 || charArray[0] != 'L') {
            return false;
        }
        int i = 1;
        while (i < length) {
            int i2 = i + 1;
            char c2 = charArray[i];
            if (c2 == '.' || c2 == '[' || c2 == '/' || c2 == ';') {
                return false;
            }
            while (i2 < length) {
                i = i2 + 1;
                char c3 = charArray[i2];
                if (c3 == '.' || c3 == '[') {
                    return false;
                }
                if (c3 != '/' && c3 != ';') {
                    i2 = i;
                } else if (c3 == ';') {
                    return i == length;
                }
            }
            return false;
        }
        return false;
    }

    private static String a(String str, boolean z, boolean z2) {
        String str2 = !z2 ? (String) a.get(str) : null;
        if (str2 != null) {
            return str2;
        }
        if (z) {
            return "L";
        }
        if (str.endsWith(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI)) {
            return "[" + a(AbstractC0402a.a(str, 2, 0), z, z2);
        }
        return "L" + str.replace('.', '/') + ";";
    }

    public static String b(char c2) {
        if (c2 == 'F') {
            return "java/lang/Float";
        }
        if (c2 == 'S') {
            return "java/lang/Short";
        }
        if (c2 == 'V') {
            return "java/lang/Void";
        }
        if (c2 == 'Z') {
            return "java/lang/Boolean";
        }
        if (c2 == 'I') {
            return "java/lang/Integer";
        }
        if (c2 == 'J') {
            return "java/lang/Long";
        }
        switch (c2) {
            case 'B':
                return "java/lang/Byte";
            case 'C':
                return "java/lang/Character";
            case 'D':
                return "java/lang/Double";
            default:
                throw new C3405y10("Unknown type " + c2);
        }
    }

    public static String c(String str) {
        String strReplace = f(str).replace('$', '.');
        if (!str.startsWith("Lj$/")) {
            return strReplace;
        }
        if (!b && !strReplace.startsWith("j./")) {
            throw new AssertionError();
        }
        return "j$/" + strReplace.substring(3);
    }

    public static String c(char c2) {
        if (c2 == 'F') {
            return "float";
        }
        if (c2 == 'S') {
            return "short";
        }
        if (c2 == 'V') {
            return PsiKeyword.VOID;
        }
        if (c2 == 'Z') {
            return "boolean";
        }
        if (c2 == 'I') {
            return "int";
        }
        if (c2 == 'J') {
            return "long";
        }
        switch (c2) {
            case 'B':
                return "byte";
            case 'C':
                return PsiKeyword.CHAR;
            case 'D':
                return "double";
            default:
                throw new C3405y10("Unknown type " + c2);
        }
    }

    public static String b(String str, String str2) {
        return "L" + str.substring(1, Integer.max(str.lastIndexOf("/"), 0) + 1) + str2 + ";";
    }

    public static String b(Class<?> cls) {
        return t(cls.getTypeName());
    }

    public static String a(Function function, String str) {
        int i = 0;
        for (int length = str.length() - 2; length > 0; length -= 2) {
            if (str.charAt(length) == '[' && str.charAt(length + 1) == ']') {
                i++;
            }
        }
        if (i > 0) {
            str = str.substring(0, str.length() - (i * 2));
        }
        String str2 = (String) function.apply(str);
        if (i == 0) {
            return str2;
        }
        StringBuilder sb = new StringBuilder(str2);
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
        }
        return sb.toString();
    }

    public static String a(String str) {
        char cCharAt = str.charAt(0);
        if (cCharAt == 'L') {
            return AbstractC0402a.a(str, 1, 1);
        }
        if (cCharAt == '[') {
            return str;
        }
        throw new C3405y10("Not array or class type");
    }

    public static String a(String str, C3605b c3605b) {
        char cCharAt = str.charAt(0);
        if (cCharAt != 'L') {
            if (cCharAt != '[') {
                return c(cCharAt);
            }
            return a(str.substring(1), c3605b) + HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        }
        if (!b && str.charAt(str.length() - 1) != ';') {
            throw new AssertionError();
        }
        String strReplace = str.substring(1, str.length() - 1).replace('/', '.');
        return c3605b == null ? strReplace : c3605b.a(strReplace);
    }

    public static String a(Class<?> cls) {
        return g(cls.getTypeName());
    }

    public static String a(C0767v2 c0767v2, C0767v2 c0767v3, C0759u2 c0759u2) {
        if (!b && c0767v3 == null) {
            throw new AssertionError();
        }
        if (c0767v2 != null && c0759u2 != null) {
            return a(c0767v2.E0(), c0767v3.E0(), c0759u2.toString());
        }
        return String.valueOf('$');
    }

    public static String a(String str, String str2, String str3) {
        if (!b && (str3 == null || str3.isEmpty())) {
            throw new AssertionError();
        }
        if (str3.length() + str.length() > str2.length()) {
            return null;
        }
        String strSubstring = str2.substring(str.length(), str2.length() - str3.length());
        if (strSubstring.startsWith(String.valueOf('$'))) {
            return strSubstring;
        }
        return null;
    }

    public static String a(Path path) {
        String string = path.toString();
        char c2 = File.separatorChar;
        if (c2 != '/') {
            string = string.replace(c2, '/');
        }
        return y(string);
    }

    public static String a(String str, String str2) {
        if (str2.length() <= str.length()) {
            return null;
        }
        String str3 = AbstractC0402a.a(str, 1, 0) + "$";
        if (str2.startsWith(str3)) {
            return AbstractC0402a.a(str2, 1, str3.length());
        }
        return null;
    }
}
