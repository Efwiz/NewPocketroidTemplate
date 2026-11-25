package org.jetbrains.kotlin.com.intellij.psi.codeStyle;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.Strings;
import org.jetbrains.kotlin.com.intellij.util.text.NameUtilCore;

/* JADX INFO: loaded from: classes3.dex */
public final class NameUtil {

    @Deprecated
    public interface Matcher {
        boolean matches(String str);
    }

    public enum MatchingCaseSensitivity {
        NONE,
        FIRST_LETTER,
        ALL
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 4 || i == 8 || i == 12 || i == 14 || i == 16 || i == 21 || i == 28) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 4 || i == 8 || i == 12 || i == 14 || i == 16 || i == 21 || i == 28) ? 2 : 3];
        switch (i) {
            case 1:
            case 4:
            case 8:
            case 12:
            case 14:
            case 16:
            case 21:
            case 28:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/NameUtil";
                break;
            case 2:
            case 3:
            case 17:
            case 18:
            case 19:
            case 22:
                objArr[0] = "pattern";
                break;
            case 5:
            case 13:
            case 15:
            case 25:
            default:
                objArr[0] = "name";
                break;
            case 6:
            case 9:
                objArr[0] = "prefix";
                break;
            case 7:
                objArr[0] = "suffix";
                break;
            case 10:
                objArr[0] = "startWord";
                break;
            case 11:
                objArr[0] = "words";
                break;
            case 20:
            case 24:
                objArr[0] = "options";
                break;
            case 23:
                objArr[0] = "fallbackPattern";
                break;
            case 26:
                objArr[0] = "text";
                break;
            case 27:
                objArr[0] = "transformWord";
                break;
        }
        if (i == 1) {
            objArr[1] = "nameToWordsLowerCase";
        } else if (i == 4) {
            objArr[1] = "buildRegexp";
        } else if (i == 8) {
            objArr[1] = "getSuggestionsByName";
        } else if (i == 12) {
            objArr[1] = "compoundSuggestion";
        } else if (i == 14) {
            objArr[1] = "splitNameIntoWords";
        } else if (i == 16) {
            objArr[1] = "nameToWords";
        } else if (i == 21) {
            objArr[1] = "buildMatcher";
        } else if (i != 28) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/NameUtil";
        } else {
            objArr[1] = "splitWords";
        }
        switch (i) {
            case 1:
            case 4:
            case 8:
            case 12:
            case 14:
            case 16:
            case 21:
            case 28:
                break;
            case 2:
            case 3:
                objArr[2] = "buildRegexp";
                break;
            case 5:
            case 6:
            case 7:
                objArr[2] = "getSuggestionsByName";
                break;
            case 9:
            case 10:
            case 11:
                objArr[2] = "compoundSuggestion";
                break;
            case 13:
                objArr[2] = "splitNameIntoWords";
                break;
            case 15:
                objArr[2] = "nameToWords";
                break;
            case 17:
            case 18:
            case 19:
            case 20:
                objArr[2] = "buildMatcher";
                break;
            case 22:
            case 23:
            case 24:
                objArr[2] = "buildMatcherWithFallback";
                break;
            case 25:
                objArr[2] = "capitalizeAndUnderscore";
                break;
            case 26:
            case 27:
                objArr[2] = "splitWords";
                break;
            default:
                objArr[2] = "nameToWordsLowerCase";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 4 && i != 8 && i != 12 && i != 14 && i != 16 && i != 21 && i != 28) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private NameUtil() {
    }

    public static List<String> nameToWordsLowerCase(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        List<String> list = (List) Arrays.stream(NameUtilCore.nameToWords(str)).map(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.codeStyle.NameUtil$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Strings.toLowerCase((String) obj);
            }
        }).collect(Collectors.toList());
        if (list == null) {
            $$$reportNull$$$0(1);
        }
        return list;
    }

    public static String buildRegexp(String str, int i, boolean z, boolean z2) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return buildRegexp(str, i, z, z2, false, false);
    }

    /* JADX WARN: Code duplicated, block: B:26:0x005a  */
    public static String buildRegexp(String str, int i, boolean z, boolean z2, boolean z3, boolean z4) {
        boolean z5;
        String strTrim = str;
        if (strTrim == null) {
            $$$reportNull$$$0(3);
        }
        int iIndexOf = str.indexOf(10);
        if (iIndexOf != -1) {
            strTrim = str.substring(0, iIndexOf);
        }
        if (strTrim.length() >= 40) {
            strTrim = strTrim.substring(0, 40);
        }
        StringBuilder sb = new StringBuilder();
        boolean z6 = !z4 && Strings.endsWithChar(strTrim, StringUtil.SPACE);
        if (!z4) {
            strTrim = strTrim.trim();
        }
        int iMin = Math.min(i, strTrim.length());
        if (iMin > 0) {
            char cCharAt = strTrim.charAt(iMin - 1);
            if (Character.isUpperCase(cCharAt) || Character.isDigit(cCharAt)) {
                z5 = true;
            } else {
                z5 = false;
            }
        } else {
            z5 = false;
        }
        for (int i2 = 0; i2 != iMin; i2++) {
            char cCharAt2 = strTrim.charAt(i2);
            if (Character.isLetterOrDigit(cCharAt2)) {
                sb.append(cCharAt2);
            } else {
                sb.append("\\").append(cCharAt2);
            }
        }
        if (iMin == 0) {
            sb.append("_*");
        }
        boolean z7 = iMin == 0;
        boolean z8 = false;
        while (iMin < strTrim.length()) {
            char cCharAt3 = strTrim.charAt(iMin);
            if (Character.isLetterOrDigit(cCharAt3)) {
                if (Character.isUpperCase(cCharAt3) || Character.isDigit(cCharAt3)) {
                    sb.append('(');
                    if (!z7) {
                        sb.append("[a-z\\s0-9\\$]*");
                    }
                    sb.append(cCharAt3);
                    if (z2) {
                        sb.append('|');
                        sb.append(Character.toLowerCase(cCharAt3));
                    }
                    if (!z7) {
                        sb.append("|[A-Za-z\\s0-9\\$]*[_-]+[");
                        sb.append(cCharAt3);
                        sb.append(Character.toLowerCase(cCharAt3));
                        sb.append("]");
                    }
                    sb.append(')');
                    z5 = true;
                } else {
                    if (Character.isLowerCase(cCharAt3) && z) {
                        sb.append('[');
                        sb.append(cCharAt3);
                        sb.append(Character.toUpperCase(cCharAt3));
                        sb.append(']');
                        if (z3) {
                            sb.append("([a-z\\s0-9\\$]*[-_]+)?");
                        }
                    } else {
                        sb.append(cCharAt3);
                    }
                    z5 = false;
                }
                z8 = z5;
                z7 = false;
            } else {
                if (cCharAt3 == '*') {
                    sb.append(".*");
                } else if (cCharAt3 == '.') {
                    if (!z7) {
                        sb.append("[a-z\\s0-9\\$]*\\.");
                    } else {
                        sb.append("\\.");
                    }
                } else if (cCharAt3 == ' ') {
                    sb.append("([a-z\\s0-9\\$_-]*[\\ _-]+)+");
                } else {
                    if (cCharAt3 == ':' || z5) {
                        sb.append("[A-Za-z\\s0-9\\$]*");
                    }
                    sb.append("\\").append(cCharAt3);
                }
                z8 = false;
                z7 = true;
            }
            iMin++;
        }
        if (!z6) {
            sb.append(".*");
        } else if (z8) {
            sb.append("[a-z\\s0-9\\$]*");
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(4);
        }
        return string;
    }

    public static List<String> getSuggestionsByName(String str, String str2, String str3, boolean z, boolean z2, boolean z3) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        if (str2 == null) {
            $$$reportNull$$$0(6);
        }
        if (str3 == null) {
            $$$reportNull$$$0(7);
        }
        ArrayList arrayList = new ArrayList();
        String[] strArrNameToWords = NameUtilCore.nameToWords(str);
        int i = 0;
        int i2 = 0;
        while (i2 < strArrNameToWords.length) {
            int length = z2 ? strArrNameToWords.length - i2 : i2 + 1;
            String str4 = strArrNameToWords[strArrNameToWords.length - length];
            char cCharAt = str4.charAt(i);
            if (cCharAt != '_' && Character.isJavaIdentifierStart(cCharAt)) {
                arrayList.add(compoundSuggestion(str2, z, strArrNameToWords, length, str4, cCharAt, z3, false) + str3);
                arrayList.add(compoundSuggestion(str2, z, strArrNameToWords, length, str4, cCharAt, z3, true) + str3);
            }
            i2++;
            i = 0;
        }
        return arrayList;
    }

    private static String compoundSuggestion(String str, boolean z, String[] strArr, int i, String str2, char c, boolean z2, boolean z3) {
        String lowerCase;
        if (str == null) {
            $$$reportNull$$$0(9);
        }
        if (str2 == null) {
            $$$reportNull$$$0(10);
        }
        if (strArr == null) {
            $$$reportNull$$$0(11);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        if (z) {
            lowerCase = Strings.toUpperCase(str2);
        } else if (str.isEmpty() || Strings.endsWithChar(str, '_')) {
            lowerCase = Strings.toLowerCase(str2);
        } else {
            lowerCase = Character.toUpperCase(c) + str2.substring(1);
        }
        sb.append(lowerCase);
        for (int length = (strArr.length - i) + 1; length < strArr.length; length++) {
            String strCapitalize = strArr[length];
            String str3 = strArr[length - 1];
            if (z) {
                strCapitalize = Strings.toUpperCase(strCapitalize);
                if (str3.charAt(str3.length() - 1) != '_' && strCapitalize.charAt(0) != '_') {
                    strCapitalize = "_" + strCapitalize;
                }
            } else {
                if (str3.charAt(str3.length() - 1) == '_') {
                    strCapitalize = Strings.toLowerCase(strCapitalize);
                }
                if (z3) {
                    if (!strCapitalize.equals("_")) {
                        if (str3.equals("_")) {
                            strCapitalize = Strings.capitalize(strCapitalize);
                        }
                    }
                }
            }
            sb.append(strCapitalize);
        }
        String string = sb.toString();
        if (z2) {
            string = Strings.pluralize(string);
            if (z) {
                string = Strings.toUpperCase(string);
            }
        }
        if (string == null) {
            $$$reportNull$$$0(12);
        }
        return string;
    }

    public static String[] splitNameIntoWords(String str) {
        if (str == null) {
            $$$reportNull$$$0(13);
        }
        String[] strArrSplitNameIntoWords = NameUtilCore.splitNameIntoWords(str);
        if (strArrSplitNameIntoWords == null) {
            $$$reportNull$$$0(14);
        }
        return strArrSplitNameIntoWords;
    }

    public static String[] nameToWords(String str) {
        if (str == null) {
            $$$reportNull$$$0(15);
        }
        String[] strArrNameToWords = NameUtilCore.nameToWords(str);
        if (strArrNameToWords == null) {
            $$$reportNull$$$0(16);
        }
        return strArrNameToWords;
    }

    public static org.jetbrains.kotlin.com.intellij.util.text.Matcher buildMatcher(String str, int i, boolean z, boolean z2) {
        MatchingCaseSensitivity matchingCaseSensitivity;
        if (str == null) {
            $$$reportNull$$$0(17);
        }
        if (!z2 && !z) {
            matchingCaseSensitivity = MatchingCaseSensitivity.ALL;
        } else if (i > 0) {
            matchingCaseSensitivity = MatchingCaseSensitivity.FIRST_LETTER;
        } else {
            matchingCaseSensitivity = MatchingCaseSensitivity.NONE;
        }
        return buildMatcher(str, matchingCaseSensitivity);
    }

    public static class MatcherBuilder {
        private final String pattern;
        private String separators = "";
        private MatchingCaseSensitivity caseSensitivity = MatchingCaseSensitivity.NONE;
        private boolean typoTolerant = false;
        private boolean preferStartMatches = false;

        public MatcherBuilder(String str) {
            this.pattern = str;
        }

        public MatcherBuilder withCaseSensitivity(MatchingCaseSensitivity matchingCaseSensitivity) {
            this.caseSensitivity = matchingCaseSensitivity;
            return this;
        }

        public MatcherBuilder withSeparators(String str) {
            this.separators = str;
            return this;
        }

        public MatcherBuilder typoTolerant() {
            this.typoTolerant = true;
            return this;
        }

        public MatcherBuilder preferringStartMatches() {
            this.preferStartMatches = true;
            return this;
        }

        public MinusculeMatcher build() {
            MinusculeMatcher minusculeMatcherCreate = this.typoTolerant ? FixingLayoutTypoTolerantMatcher.create(this.pattern, this.caseSensitivity, this.separators) : new FixingLayoutMatcher(this.pattern, this.caseSensitivity, this.separators);
            return this.preferStartMatches ? new PreferStartMatchMatcherWrapper(minusculeMatcherCreate) : minusculeMatcherCreate;
        }
    }

    public static MatcherBuilder buildMatcher(String str) {
        if (str == null) {
            $$$reportNull$$$0(18);
        }
        return new MatcherBuilder(str);
    }

    public static MinusculeMatcher buildMatcher(String str, MatchingCaseSensitivity matchingCaseSensitivity) {
        if (str == null) {
            $$$reportNull$$$0(19);
        }
        if (matchingCaseSensitivity == null) {
            $$$reportNull$$$0(20);
        }
        MinusculeMatcher minusculeMatcherBuild = buildMatcher(str).withCaseSensitivity(matchingCaseSensitivity).build();
        if (minusculeMatcherBuild == null) {
            $$$reportNull$$$0(21);
        }
        return minusculeMatcherBuild;
    }

    public static MinusculeMatcher buildMatcherWithFallback(String str, String str2, MatchingCaseSensitivity matchingCaseSensitivity) {
        if (str == null) {
            $$$reportNull$$$0(22);
        }
        if (str2 == null) {
            $$$reportNull$$$0(23);
        }
        if (matchingCaseSensitivity == null) {
            $$$reportNull$$$0(24);
        }
        if (str.equals(str2)) {
            return buildMatcher(str, matchingCaseSensitivity);
        }
        return new MatcherWithFallback(buildMatcher(str, matchingCaseSensitivity), buildMatcher(str2, matchingCaseSensitivity));
    }

    public static String capitalizeAndUnderscore(String str) {
        if (str == null) {
            $$$reportNull$$$0(25);
        }
        return splitWords(str, '_', new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.codeStyle.NameUtil$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Strings.toUpperCase((String) obj);
            }
        });
    }

    public static String splitWords(String str, char c, Function<? super String, String> function) {
        if (str == null) {
            $$$reportNull$$$0(26);
        }
        if (function == null) {
            $$$reportNull$$$0(27);
        }
        String[] strArrNameToWords = NameUtilCore.nameToWords(str);
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        for (String str2 : strArrNameToWords) {
            if (!Character.isLetterOrDigit(str2.charAt(0))) {
                sb.append(c);
                z = false;
            } else {
                if (z) {
                    sb.append(c);
                } else {
                    z = true;
                }
                sb.append(function.apply(str2));
            }
        }
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(28);
        }
        return string;
    }
}
