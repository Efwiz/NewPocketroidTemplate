package org.jetbrains.kotlin.com.intellij.util.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import org.jetbrains.kotlin.com.intellij.util.Function;

/* JADX INFO: loaded from: classes3.dex */
public final class VersionComparatorUtil {
    private static final Pattern WORDS_SPLITTER = Pattern.compile("\\d+|[^\\d]+");
    private static final Pattern ZERO_PATTERN = Pattern.compile("0+");
    private static final Pattern DIGITS_PATTERN = Pattern.compile("\\d+");
    private static final VersionTokenType[] VALUES = VersionTokenType.values();
    public static final Comparator<String> COMPARATOR = new Comparator<String>() { // from class: org.jetbrains.kotlin.com.intellij.util.text.VersionComparatorUtil.1
        @Override // java.util.Comparator
        public int compare(String str, String str2) {
            return VersionComparatorUtil.compare(str, str2);
        }
    };
    public static final Function<String, Integer> DEFAULT_TOKEN_PRIORITY_PROVIDER = new Function<String, Integer>() { // from class: org.jetbrains.kotlin.com.intellij.util.text.VersionComparatorUtil.2
        @Override // org.jetbrains.kotlin.com.intellij.util.Function
        public Integer fun(String str) {
            return Integer.valueOf(VersionTokenType.lookup(str).getPriority());
        }
    };

    public enum VersionTokenType {
        SNAP(10),
        SNAPSHOT(10),
        M(20),
        EAP(25),
        PRE(25),
        PREVIEW(25),
        ALPHA(30),
        A(30),
        BETA(40),
        BETTA(40),
        B(40),
        RC(50),
        _WS(60),
        SP(70),
        REL(80),
        RELEASE(80),
        R(80),
        FINAL(80),
        _WORD(90),
        _DIGITS(100),
        BUNDLED(666);

        private final int myPriority;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/util/text/VersionComparatorUtil$VersionTokenType", "lookup"));
        }

        VersionTokenType(int i) {
            this.myPriority = i;
        }

        public static VersionTokenType lookup(String str) {
            if (str == null) {
                VersionTokenType versionTokenType = _WS;
                if (versionTokenType == null) {
                    $$$reportNull$$$0(0);
                }
                return versionTokenType;
            }
            String strTrim = str.trim();
            if (strTrim.length() != 0) {
                for (VersionTokenType versionTokenType2 : VersionComparatorUtil.VALUES) {
                    String strName = versionTokenType2.name();
                    if (strName.charAt(0) != '_' && strName.equalsIgnoreCase(strTrim)) {
                        if (versionTokenType2 == null) {
                            $$$reportNull$$$0(2);
                        }
                        return versionTokenType2;
                    }
                }
                if (!VersionComparatorUtil.ZERO_PATTERN.matcher(strTrim).matches()) {
                    if (VersionComparatorUtil.DIGITS_PATTERN.matcher(strTrim).matches()) {
                        VersionTokenType versionTokenType3 = _DIGITS;
                        if (versionTokenType3 == null) {
                            $$$reportNull$$$0(4);
                        }
                        return versionTokenType3;
                    }
                    VersionTokenType versionTokenType4 = _WORD;
                    if (versionTokenType4 == null) {
                        $$$reportNull$$$0(5);
                    }
                    return versionTokenType4;
                }
                VersionTokenType versionTokenType5 = _WS;
                if (versionTokenType5 == null) {
                    $$$reportNull$$$0(3);
                }
                return versionTokenType5;
            }
            VersionTokenType versionTokenType6 = _WS;
            if (versionTokenType6 == null) {
                $$$reportNull$$$0(1);
            }
            return versionTokenType6;
        }

        public int getPriority() {
            return this.myPriority;
        }
    }

    static List<String> splitVersionString(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str.trim(), "()._-;:/, +~");
        ArrayList arrayList = new ArrayList();
        while (stringTokenizer.hasMoreTokens()) {
            java.util.regex.Matcher matcher = WORDS_SPLITTER.matcher(stringTokenizer.nextToken());
            while (matcher.find()) {
                arrayList.add(matcher.group());
            }
        }
        return arrayList;
    }

    public static int compare(String str, String str2) {
        return compare(str, str2, DEFAULT_TOKEN_PRIORITY_PROVIDER);
    }

    public static int compare(String str, String str2, Function<? super String, Integer> function) {
        if (str == null) {
            return str2 == null ? 0 : -1;
        }
        if (str2 == null) {
            return 1;
        }
        String lowerCase = str.toLowerCase(Locale.ENGLISH);
        String lowerCase2 = str2.toLowerCase(Locale.ENGLISH);
        List<String> listSplitVersionString = splitVersionString(lowerCase);
        List<String> listSplitVersionString2 = splitVersionString(lowerCase2);
        padWithNulls(listSplitVersionString, listSplitVersionString2);
        for (int i = 0; i < listSplitVersionString.size(); i++) {
            String str3 = listSplitVersionString.get(i);
            String str4 = listSplitVersionString2.get(i);
            VersionTokenType versionTokenTypeLookup = VersionTokenType.lookup(str3);
            int iComparePriorities = comparePriorities(str3, str4, function);
            if (iComparePriorities != 0) {
                return iComparePriorities;
            }
            if (versionTokenTypeLookup == VersionTokenType._WORD) {
                iComparePriorities = str3.compareTo(str4);
            } else if (versionTokenTypeLookup == VersionTokenType._DIGITS) {
                iComparePriorities = compareNumbers(str3, str4);
            }
            if (iComparePriorities != 0) {
                return iComparePriorities;
            }
        }
        return 0;
    }

    public static int comparePriorities(String str, String str2, Function<? super String, Integer> function) {
        return Integer.signum(function.fun(str).intValue() - function.fun(str2).intValue());
    }

    private static int compareNumbers(String str, String str2) {
        int length;
        int length2;
        while (str.length() > 0 && str2.length() > 0 && str.charAt(0) == '0' && str2.charAt(0) == '0') {
            str = str.substring(1);
            str2 = str2.substring(1);
        }
        if (str.length() > 0 && str.charAt(0) == '0') {
            return -1;
        }
        if ((str2.length() > 0 && str2.charAt(0) == '0') || (length = str.length()) > (length2 = str2.length())) {
            return 1;
        }
        if (length2 > length) {
            return -1;
        }
        return str.compareTo(str2);
    }

    private static void padWithNulls(Collection<String> collection, Collection<String> collection2) {
        if (collection.size() != collection2.size()) {
            while (collection.size() < collection2.size()) {
                collection.add(null);
            }
            while (collection.size() > collection2.size()) {
                collection2.add(null);
            }
        }
    }
}
