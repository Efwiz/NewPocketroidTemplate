package org.jetbrains.kotlin.com.intellij.psi.codeStyle;

import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: loaded from: classes3.dex */
final class FixingLayoutTypoTolerantMatcher {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "pattern";
        } else {
            objArr[0] = "options";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/FixingLayoutTypoTolerantMatcher";
        objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    FixingLayoutTypoTolerantMatcher() {
    }

    static MinusculeMatcher create(String str, NameUtil.MatchingCaseSensitivity matchingCaseSensitivity, String str2) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (matchingCaseSensitivity == null) {
            $$$reportNull$$$0(1);
        }
        TypoTolerantMatcher typoTolerantMatcher = new TypoTolerantMatcher(str, matchingCaseSensitivity, str2);
        String strFixLayout = FixingLayoutMatcher.fixLayout(str);
        return (strFixLayout == null || strFixLayout.equals(str)) ? typoTolerantMatcher : new MatcherWithFallback(typoTolerantMatcher, new TypoTolerantMatcher(strFixLayout, matchingCaseSensitivity, str2));
    }
}
