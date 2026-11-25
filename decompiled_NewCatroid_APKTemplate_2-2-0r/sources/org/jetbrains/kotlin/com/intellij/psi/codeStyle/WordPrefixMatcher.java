package org.jetbrains.kotlin.com.intellij.psi.codeStyle;

import java.util.Arrays;
import java.util.function.Predicate;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtilRt;
import org.jetbrains.kotlin.com.intellij.util.text.Matcher;

/* JADX INFO: loaded from: classes3.dex */
public class WordPrefixMatcher implements Matcher {
    private final String[] myFallbackPatternWords;
    private final String[] myPatternWords;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "string";
        } else if (i != 2) {
            objArr[0] = "name";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/WordPrefixMatcher";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/WordPrefixMatcher";
        } else {
            objArr[1] = "splitToWords";
        }
        if (i == 1) {
            objArr[2] = "splitToWords";
        } else if (i != 2) {
            objArr[2] = "matches";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    public WordPrefixMatcher(String str) {
        this.myPatternWords = splitToWords(str);
        String strFixLayout = FixingLayoutMatcher.fixLayout(str);
        this.myFallbackPatternWords = (strFixLayout == null || strFixLayout.equals(str)) ? null : NameUtil.nameToWords(strFixLayout);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.NameUtil.Matcher
    public boolean matches(String str) {
        String[] strArr;
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        String[] strArrSplitToWords = splitToWords(str);
        return matches(this.myPatternWords, strArrSplitToWords) || ((strArr = this.myFallbackPatternWords) != null && matches(strArr, strArrSplitToWords));
    }

    private static String[] splitToWords(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        String[] strArrSplit = str.split("[\\s-/]");
        if (strArrSplit == null) {
            $$$reportNull$$$0(2);
        }
        return strArrSplit;
    }

    private static boolean matches(String[] strArr, final String[] strArr2) {
        return Arrays.stream(strArr).allMatch(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.codeStyle.WordPrefixMatcher$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Arrays.stream(strArr2).anyMatch(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.codeStyle.WordPrefixMatcher$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj2) {
                        return StringUtilRt.startsWithIgnoreCase((String) obj2, str);
                    }
                });
            }
        });
    }
}
