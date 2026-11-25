package org.jetbrains.kotlin.com.intellij.util.execution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntOpenHashSet;

/* JADX INFO: loaded from: classes3.dex */
public final class ParametersListUtil {
    public static final Function<String, List<String>> DEFAULT_LINE_PARSER = new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.execution.ParametersListUtil$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public final Object fun(Object obj) {
            return ParametersListUtil.parse((String) obj, true);
        }
    };
    public static final Function<List<String>, String> DEFAULT_LINE_JOINER = new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.execution.ParametersListUtil$$ExternalSyntheticLambda1
        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public final Object fun(Object obj) {
            return StringUtil.join((Collection<String>) ((List) obj), " ");
        }
    };
    public static final Function<String, List<String>> COLON_LINE_PARSER = new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.execution.ParametersListUtil$$ExternalSyntheticLambda2
        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public final Object fun(Object obj) {
            return ParametersListUtil.lambda$static$2((String) obj);
        }
    };
    public static final Function<List<String>, String> COLON_LINE_JOINER = new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.execution.ParametersListUtil$$ExternalSyntheticLambda3
        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public final Object fun(Object obj) {
            return StringUtil.join((Collection<String>) ((List) obj), ";");
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 5 || i == 13 || i == 10 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 5 || i == 13 || i == 10 || i == 11) ? 2 : 3];
        switch (i) {
            case 2:
                objArr[0] = "escapeFunction";
                break;
            case 3:
            case 5:
            case 10:
            case 11:
            case 13:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/execution/ParametersListUtil";
                break;
            case 4:
                objArr[0] = "string";
                break;
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[0] = "parameterString";
                break;
            case 12:
                objArr[0] = "argument";
                break;
            default:
                objArr[0] = "parameters";
                break;
        }
        if (i == 3) {
            objArr[1] = "join";
        } else if (i == 5) {
            objArr[1] = "parseToArray";
        } else if (i == 13) {
            objArr[1] = "escape";
        } else if (i == 10 || i == 11) {
            objArr[1] = "parse";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/execution/ParametersListUtil";
        }
        switch (i) {
            case 3:
            case 5:
            case 10:
            case 11:
            case 13:
                break;
            case 4:
                objArr[2] = "parseToArray";
                break;
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[2] = "parse";
                break;
            case 12:
                objArr[2] = "escape";
                break;
            default:
                objArr[2] = "join";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 5 && i != 13 && i != 10 && i != 11) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ List lambda$static$2(String str) {
        ArrayList arrayList = new ArrayList();
        StringTokenizer stringTokenizer = new StringTokenizer(str, ";", false);
        while (stringTokenizer.hasMoreTokens()) {
            arrayList.add(stringTokenizer.nextToken());
        }
        return arrayList;
    }

    public static List<String> parse(String str) {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        return parse(str, false);
    }

    public static List<String> parse(String str, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        return parse(str, z, false);
    }

    public static List<String> parse(String str, boolean z, boolean z2) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        return parse(str, z, z2, false);
    }

    /* JADX WARN: Code duplicated, block: B:47:0x0096 A[PHI: r4 r5 r6 r7
      0x0096: PHI (r4v4 boolean) = (r4v2 boolean), (r4v1 boolean), (r4v1 boolean), (r4v1 boolean), (r4v1 boolean), (r4v1 boolean), (r4v1 boolean) binds: [B:25:0x0056, B:35:0x0078, B:37:0x007f, B:43:0x0090, B:45:0x0093, B:40:0x0089, B:28:0x005e] A[DONT_GENERATE, DONT_INLINE]
      0x0096: PHI (r5v4 char) = (r5v2 char), (r5v1 char), (r5v1 char), (r5v1 char), (r5v1 char), (r5v1 char), (r5v1 char) binds: [B:25:0x0056, B:35:0x0078, B:37:0x007f, B:43:0x0090, B:45:0x0093, B:40:0x0089, B:28:0x005e] A[DONT_GENERATE, DONT_INLINE]
      0x0096: PHI (r6v4 boolean) = (r6v2 boolean), (r6v1 boolean), (r6v1 boolean), (r6v1 boolean), (r6v1 boolean), (r6v1 boolean), (r6v1 boolean) binds: [B:25:0x0056, B:35:0x0078, B:37:0x007f, B:43:0x0090, B:45:0x0093, B:40:0x0089, B:28:0x005e] A[DONT_GENERATE, DONT_INLINE]
      0x0096: PHI (r7v3 boolean) = (r7v2 boolean), (r7v1 boolean), (r7v1 boolean), (r7v1 boolean), (r7v5 boolean), (r7v1 boolean), (r7v1 boolean) binds: [B:25:0x0056, B:35:0x0078, B:37:0x007f, B:43:0x0090, B:45:0x0093, B:40:0x0089, B:28:0x005e] A[DONT_GENERATE, DONT_INLINE]] */
    public static List<String> parse(String str, boolean z, boolean z2, boolean z3) {
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        if (!z3) {
            str = str.trim();
        }
        ArrayList arrayList = new ArrayList();
        if (str.isEmpty()) {
            return arrayList;
        }
        StringBuilder sb = new StringBuilder(128);
        IntOpenHashSet intOpenHashSet = new IntOpenHashSet();
        intOpenHashSet.add(34);
        if (z2) {
            intOpenHashSet.add(39);
        }
        boolean z4 = false;
        char c = 0;
        boolean z5 = false;
        boolean z6 = false;
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (!z4 ? !intOpenHashSet.contains(cCharAt) : c != cCharAt) {
                if (Character.isWhitespace(cCharAt)) {
                    if (!z4) {
                        if (z3 || sb.length() > 0 || z5) {
                            arrayList.add(sb.toString());
                            sb.setLength(0);
                            z5 = false;
                        }
                    } else {
                        sb.append(cCharAt);
                    }
                } else if (cCharAt == '\\' && i < str.length() - 1) {
                    char cCharAt2 = str.charAt(i + 1);
                    if (!z4 ? !intOpenHashSet.contains(cCharAt2) : c != cCharAt2) {
                        sb.append(cCharAt);
                    } else {
                        z6 = true;
                        if (z) {
                            sb.append(cCharAt);
                        }
                    }
                } else {
                    sb.append(cCharAt);
                }
            } else {
                if (!z6) {
                    z4 = !z4;
                    c = cCharAt;
                    z5 = true;
                    if (!z) {
                    }
                }
                z6 = false;
                sb.append(cCharAt);
            }
        }
        if (z3 || sb.length() > 0 || z5) {
            arrayList.add(sb.toString());
        }
        return arrayList;
    }
}
