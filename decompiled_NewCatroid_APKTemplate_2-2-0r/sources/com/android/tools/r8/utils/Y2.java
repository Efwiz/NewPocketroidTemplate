package com.android.tools.r8.utils;

import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.retrace.StringRetrace$$ExternalSyntheticLambda3;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.SystemProperties;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiLiteralUtil;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class Y2 {
    public static char[] a = new char[0];
    public static final String[] b = new String[0];
    public static final String c = System.getProperty(SystemProperties.LINE_SEPARATOR);
    static final /* synthetic */ boolean d = true;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public enum a {
        PARENS,
        SQUARE,
        TUBORG,
        NONE;

        public final String a() {
            int i = X2.a[ordinal()];
            if (i == 1) {
                return "(";
            }
            if (i == 2) {
                return "[";
            }
            if (i == 3) {
                return "{";
            }
            if (i == 4) {
                return "";
            }
            throw new C3405y10("Invalid brace type: " + this);
        }

        public final String b() {
            int i = X2.a[ordinal()];
            if (i == 1) {
                return Constant.AFTER_QUTO;
            }
            if (i == 2) {
                return "]";
            }
            if (i == 3) {
                return "}";
            }
            if (i == 4) {
                return "";
            }
            throw new C3405y10("Invalid brace type: " + this);
        }
    }

    public static boolean a(int i) {
        return i == 65279;
    }

    public static void b(StringBuilder sb, String str, int i) {
        sb.append(str);
        for (int length = str.length(); length < i; length++) {
            sb.append(" ");
        }
    }

    public static String c(String... strArr) {
        List listAsList = Arrays.asList(strArr);
        StringBuilder sb = new StringBuilder();
        Iterator it = listAsList.iterator();
        while (it.hasNext()) {
            sb.append((String) it.next()).append("\n");
        }
        return sb.toString();
    }

    public static boolean d(String str) {
        return str.equals(SchemaSymbols.ATTVAL_TRUE_1) || str.toLowerCase().equals("true");
    }

    public static String e(String str) {
        return "\"" + str + "\"";
    }

    public static List<String> f(String str) {
        return a(str, false);
    }

    public static String g(String str) {
        return (str.length() <= 0 || str.charAt(0) != 65279) ? str : str.substring(1);
    }

    public static String h(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (31 >= cCharAt || cCharAt >= 127) {
                sb.append("\\u").append(a((int) cCharAt, 4, false));
            } else {
                sb.append(cCharAt);
            }
        }
        return sb.toString();
    }

    public static String i(String str) {
        return str.toLowerCase(Locale.ROOT);
    }

    public static String j(String str) {
        int length = str.length();
        int i = 0;
        while (i < length && b(str.charAt(i))) {
            i++;
        }
        while (true) {
            int i2 = length - 1;
            if (i2 <= i || !b(str.charAt(i2))) {
                break;
            }
            length--;
        }
        return (i > 0 || length < str.length()) ? str.substring(i, length) : str;
    }

    public static String k(String str) {
        String strReplace = str.replace("\r\n", "\n");
        String str2 = c;
        if (str2.equals("\r\n")) {
            return strReplace.replace("\n", "\r\n");
        }
        if (d || str2.equals("\n")) {
            return strReplace;
        }
        throw new AssertionError();
    }

    public static void a(String str, char c2, Consumer consumer) {
        int length = str.length();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (str.charAt(i2) == c2) {
                consumer.accept(str.substring(i, i2));
                i = i2 + 1;
            }
        }
        consumer.accept(str.substring(i));
    }

    public static String b(String... strArr) {
        return a((List<String>) Arrays.asList(strArr));
    }

    public static String b(int i, int i2) {
        return a(i2, Integer.toString(i));
    }

    public static boolean b(int i) {
        return Character.isWhitespace(i) || a(i);
    }

    public static int b(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!b(str.charAt(i))) {
                return i;
            }
        }
        return str.length();
    }

    public static boolean c(String str) {
        return str.equals("0") || str.toLowerCase().equals("false");
    }

    public static List<String> a(String str, char c2) {
        ArrayList arrayList = new ArrayList();
        a(str, c2, new StringRetrace$$ExternalSyntheticLambda3(arrayList));
        return arrayList;
    }

    public static String[] a(String str, char c2, final int i) {
        if (!d && i <= 1) {
            throw new AssertionError();
        }
        final String[] strArr = new String[i];
        final C3932m1 c3932m1 = new C3932m1(0);
        a(str, c2, new Consumer() { // from class: com.android.tools.r8.utils.Y2$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Y2.a(c3932m1, i, strArr, (String) obj);
            }
        });
        if (i == c3932m1.a()) {
            return strArr;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C3932m1 c3932m1, int i, String[] strArr, String str) {
        int iB = c3932m1.b();
        if (iB < i) {
            strArr[iB] = str;
        }
    }

    public static boolean a(StringBuilder sb, String str, Object obj) {
        if (obj == null) {
            return false;
        }
        String string = obj.toString();
        if (string.isEmpty()) {
            return false;
        }
        if (str != null) {
            sb.append(str);
        }
        sb.append(string);
        return true;
    }

    public static void a(StringBuilder sb, String str, int i) {
        for (int length = str.length(); length < i; length++) {
            sb.append(" ");
        }
        sb.append(str);
    }

    public static <T> StringBuilder a(StringBuilder sb, Collection<T> collection) {
        return a(sb, collection, ", ", a.PARENS);
    }

    public static <T> StringBuilder a(StringBuilder sb, Iterable<T> iterable, String str, a aVar) {
        sb.append(aVar.a());
        boolean z = true;
        for (T t : iterable) {
            if (z) {
                z = false;
            } else {
                sb.append(str);
            }
            sb.append(t);
        }
        sb.append(aVar.b());
        return sb;
    }

    public static void a(StringBuilder sb, String... strArr) {
        for (String str : strArr) {
            sb.append(str).append(c);
        }
    }

    public static String a(String str, String... strArr) {
        return a(str, Arrays.asList(strArr));
    }

    public static <T> String a(String str, Iterable<T> iterable) {
        return a(str, iterable, a.NONE);
    }

    public static <T> String a(String str, Iterable<T> iterable, Function<T, String> function) {
        return a(str, iterable, function, a.NONE);
    }

    public static String a(Stream stream, Function function) {
        return a(" <OR> ", (Iterable) stream.collect(Collectors.toList()), function, a.NONE);
    }

    public static String a(Object[] objArr, Function function, a aVar) {
        return a(",", Arrays.asList(objArr), function, aVar);
    }

    public static <T> String a(String str, Iterable<T> iterable, a aVar) {
        return a(str, iterable, new Function() { // from class: com.android.tools.r8.utils.Y2$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return obj.toString();
            }
        }, aVar);
    }

    public static <T> String a(String str, Iterable<T> iterable, Function<T, String> function, a aVar) {
        StringBuilder sb = new StringBuilder();
        a(sb, I1.b(iterable, function), str, aVar);
        return sb.toString();
    }

    public static String a(List<String> list) {
        String str = c;
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            sb.append(it.next()).append(str);
        }
        return sb.toString();
    }

    public static String a(ArrayList arrayList) {
        StringBuilder sb = new StringBuilder();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            sb.append((String) it.next()).append("\n");
        }
        return sb.toString();
    }

    public static String a(String... strArr) {
        return a(c, strArr);
    }

    public static <T> String a(Collection<T> collection) {
        return a(c, collection, a.NONE);
    }

    public static List<String> a(String str, boolean z) {
        int i;
        int length = str.length();
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '\r' && (i = i2 + 1) < length && str.charAt(i) == '\n') {
                arrayList.add(str.substring(i3, i2));
                i3 = i + 1;
                i2 = i;
            } else if (cCharAt == '\n') {
                i = i2;
                arrayList.add(str.substring(i3, i2));
                i3 = i + 1;
                i2 = i;
            }
            i2++;
        }
        if (i3 < length) {
            String strSubstring = str.substring(i3);
            if (z || !strSubstring.isEmpty()) {
                arrayList.add(strSubstring);
            }
        }
        return arrayList;
    }

    private static String a(int i, String str) {
        if (!d && i > 16) {
            throw new AssertionError();
        }
        int length = i - str.length();
        if (length <= 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append((CharSequence) "0000000000000000", 0, length);
        sb.append(str);
        return sb.toString();
    }

    public static String a(int i, int i2) {
        return a(i, i2, true);
    }

    public static String a(int i, int i2, boolean z) {
        boolean z2 = d;
        if (!z2 && (i2 < 0 || i2 > 8)) {
            throw new AssertionError();
        }
        String str = z ? PsiLiteralUtil.HEX_PREFIX : "";
        String hexString = Integer.toHexString(i);
        if (i >= 0) {
            return str + a(i2, hexString);
        }
        if (z2 || hexString.length() == 8) {
            return str + hexString;
        }
        throw new AssertionError();
    }

    public static String a(long j) {
        return a(j, 16);
    }

    public static String a(long j, int i) {
        boolean z = d;
        if (!z && (i < 0 || i > 16)) {
            throw new AssertionError();
        }
        String hexString = Long.toHexString(j);
        if (j >= 0) {
            return PsiLiteralUtil.HEX_PREFIX + a(i, hexString);
        }
        if (z || hexString.length() == 16) {
            return PsiLiteralUtil.HEX_PREFIX + hexString;
        }
        throw new AssertionError();
    }

    public static String a(String str, int i) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            i--;
            if (i >= 0) {
                sb.append(str);
            } else {
                return sb.toString();
            }
        }
    }

    public static String a(String str, Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            str = a(str, entry.getKey(), entry.getValue());
        }
        return str;
    }

    public static String a(String str, String str2, String str3) {
        return str.replaceAll(Pattern.quote(str2), Matcher.quoteReplacement(str3));
    }

    public static String a(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static String a(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
