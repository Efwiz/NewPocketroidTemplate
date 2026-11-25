package org.jetbrains.kotlin.util.capitalizeDecapitalize;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: capitalizeDecapitalize.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0004\u001a\u001a\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u001a\u0018\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u001a\u0018\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u001a\n\u0010\b\u001a\u00020\u0001*\u00020\u0001\u001a\u0014\u0010\t\u001a\u00020\u0001*\u00020\u00012\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\n\u0010\n\u001a\u00020\u0001*\u00020\u0001\u001a\u0014\u0010\u000b\u001a\u00020\u0001*\u00020\u00012\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\u0014\u0010\f\u001a\u00020\u0001*\u00020\u00012\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010\r\u001a\u00020\u0004*\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u001a\u001c\u0010\u0010\u001a\u00020\u0004*\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u001a\n\u0010\u0011\u001a\u00020\u0001*\u00020\u0001\u001a\n\u0010\u0012\u001a\u00020\u0001*\u00020\u0001¨\u0006\u0013"}, d2 = {"decapitalizeWithUnderscores", "", "str", "asciiOnly", "", "toLowerCase", "string", "toUpperCase", "capitalizeAsciiOnly", "capitalizeFirstWord", "decapitalizeAsciiOnly", "decapitalizeSmart", "decapitalizeSmartForCompiler", "isLowerCaseCharAt", "index", "", "isUpperCaseCharAt", "toLowerCaseAsciiOnly", "toUpperCaseAsciiOnly", "util.runtime"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CapitalizeDecapitalizeKt {
    public static /* synthetic */ String decapitalizeSmartForCompiler$default(String str, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return decapitalizeSmartForCompiler(str, z);
    }

    public static final String decapitalizeSmartForCompiler(String str, boolean z) {
        Integer next;
        Intrinsics.checkNotNullParameter(str, "<this>");
        String str2 = str;
        if (str2.length() == 0 || !isUpperCaseCharAt(str, 0, z)) {
            return str;
        }
        if (str.length() == 1 || !isUpperCaseCharAt(str, 1, z)) {
            if (z) {
                return decapitalizeAsciiOnly(str);
            }
            if (str2.length() <= 0) {
                return str;
            }
            char lowerCase = Character.toLowerCase(str.charAt(0));
            String strSubstring = str.substring(1);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            return lowerCase + strSubstring;
        }
        Iterator<Integer> it = StringsKt.getIndices(str2).iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (isUpperCaseCharAt(str, next.intValue(), z));
        Integer num = next;
        if (num == null) {
            return toLowerCase(str, z);
        }
        int iIntValue = num.intValue() - 1;
        StringBuilder sb = new StringBuilder();
        String strSubstring2 = str.substring(0, iIntValue);
        Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
        StringBuilder sbAppend = sb.append(toLowerCase(strSubstring2, z));
        String strSubstring3 = str.substring(iIntValue);
        Intrinsics.checkNotNullExpressionValue(strSubstring3, "substring(...)");
        return sbAppend.append(strSubstring3).toString();
    }

    public static /* synthetic */ String decapitalizeSmart$default(String str, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return decapitalizeSmart(str, z);
    }

    public static final String decapitalizeSmart(String str, boolean z) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        String strDecapitalizeWithUnderscores = decapitalizeWithUnderscores(str, z);
        return strDecapitalizeWithUnderscores == null ? decapitalizeSmartForCompiler(str, z) : strDecapitalizeWithUnderscores;
    }

    public static /* synthetic */ String capitalizeFirstWord$default(String str, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return capitalizeFirstWord(str, z);
    }

    public static final String capitalizeFirstWord(String str, boolean z) {
        Object next;
        Intrinsics.checkNotNullParameter(str, "<this>");
        Iterator it = CollectionsKt.drop(StringsKt.getIndices(str), 1).iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (isLowerCaseCharAt(str, ((Number) next).intValue(), z));
        Integer num = (Integer) next;
        if (num == null) {
            return toUpperCase(str, z);
        }
        int iIntValue = num.intValue();
        StringBuilder sb = new StringBuilder();
        String strSubstring = str.substring(0, iIntValue);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        StringBuilder sbAppend = sb.append(toUpperCase(strSubstring, z));
        String strSubstring2 = str.substring(iIntValue);
        Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
        return sbAppend.append(strSubstring2).toString();
    }

    private static final String decapitalizeWithUnderscores(String str, boolean z) {
        int i = 0;
        List listSplit$default = StringsKt.split$default((CharSequence) str, new String[]{"_"}, false, 0, 6, (Object) null);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listSplit$default) {
            if (((String) obj).length() > 0) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.size() <= 1) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Object obj2 : arrayList2) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            String str2 = (String) obj2;
            if (i == 0) {
                sb.append(toLowerCase(str2, z));
            } else {
                sb.append(toUpperCase(String.valueOf(StringsKt.first(str2)), z));
                sb.append(toLowerCase(StringsKt.drop(str2, 1), z));
            }
            i = i2;
        }
        return sb.toString();
    }

    private static final boolean isUpperCaseCharAt(String str, int i, boolean z) {
        char cCharAt = str.charAt(i);
        if (z) {
            return 'A' <= cCharAt && cCharAt < '[';
        }
        return Character.isUpperCase(cCharAt);
    }

    private static final boolean isLowerCaseCharAt(String str, int i, boolean z) {
        char cCharAt = str.charAt(i);
        if (z) {
            return 'a' <= cCharAt && cCharAt < '{';
        }
        return Character.isLowerCase(cCharAt);
    }

    private static final String toLowerCase(String str, boolean z) {
        if (z) {
            return toLowerCaseAsciiOnly(str);
        }
        String lowerCase = str.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        return lowerCase;
    }

    private static final String toUpperCase(String str, boolean z) {
        if (z) {
            return toUpperCaseAsciiOnly(str);
        }
        String upperCase = str.toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
        return upperCase;
    }

    public static final String capitalizeAsciiOnly(String str) {
        char cCharAt;
        Intrinsics.checkNotNullParameter(str, "<this>");
        if (str.length() == 0 || 'a' > (cCharAt = str.charAt(0)) || cCharAt >= '{') {
            return str;
        }
        char upperCase = Character.toUpperCase(cCharAt);
        String strSubstring = str.substring(1);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return upperCase + strSubstring;
    }

    public static final String decapitalizeAsciiOnly(String str) {
        char cCharAt;
        Intrinsics.checkNotNullParameter(str, "<this>");
        if (str.length() == 0 || 'A' > (cCharAt = str.charAt(0)) || cCharAt >= '[') {
            return str;
        }
        char lowerCase = Character.toLowerCase(cCharAt);
        String strSubstring = str.substring(1);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return lowerCase + strSubstring;
    }

    public static final String toLowerCaseAsciiOnly(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        StringBuilder sb = new StringBuilder(str.length());
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if ('A' <= cCharAt && cCharAt < '[') {
                cCharAt = Character.toLowerCase(cCharAt);
            }
            sb.append(cCharAt);
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public static final String toUpperCaseAsciiOnly(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        StringBuilder sb = new StringBuilder(str.length());
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if ('a' <= cCharAt && cCharAt < '{') {
                cCharAt = Character.toUpperCase(cCharAt);
            }
            sb.append(cCharAt);
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
