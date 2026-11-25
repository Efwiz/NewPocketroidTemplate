package org.jetbrains.kotlin.com.intellij.psi.codeStyle;

import org.jetbrains.kotlin.com.intellij.util.containers.FList;
import org.jetbrains.kotlin.com.intellij.util.text.KeyboardLayoutUtil;

/* JADX INFO: loaded from: classes3.dex */
public class FixingLayoutMatcher extends MatcherWithFallback {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 3) {
            objArr[0] = "options";
        } else {
            objArr[0] = "pattern";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/FixingLayoutMatcher";
        if (i == 2 || i == 3) {
            objArr[2] = "withFixedLayout";
        } else {
            objArr[2] = "<init>";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MatcherWithFallback, org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher
    public /* bridge */ /* synthetic */ String getPattern() {
        return super.getPattern();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MatcherWithFallback, org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher, org.jetbrains.kotlin.com.intellij.psi.codeStyle.NameUtil.Matcher
    public /* bridge */ /* synthetic */ boolean matches(String str) {
        return super.matches(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MatcherWithFallback, org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher
    public /* bridge */ /* synthetic */ int matchingDegree(String str, boolean z) {
        return super.matchingDegree(str, z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MatcherWithFallback, org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher
    public /* bridge */ /* synthetic */ int matchingDegree(String str, boolean z, FList fList) {
        return super.matchingDegree(str, z, fList);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MatcherWithFallback, org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher
    public /* bridge */ /* synthetic */ FList matchingFragments(String str) {
        return super.matchingFragments(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MatcherWithFallback
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FixingLayoutMatcher(String str, NameUtil.MatchingCaseSensitivity matchingCaseSensitivity, String str2) {
        super(new MinusculeMatcherImpl(str, matchingCaseSensitivity, str2), withFixedLayout(str, matchingCaseSensitivity, str2));
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (matchingCaseSensitivity == null) {
            $$$reportNull$$$0(1);
        }
    }

    public static String fixLayout(String str) {
        boolean z;
        int i = 0;
        boolean z2 = false;
        while (true) {
            z = true;
            if (i >= str.length()) {
                break;
            }
            char cCharAt = str.charAt(i);
            if (Character.isLetter(cCharAt)) {
                if (cCharAt <= 127) {
                    z2 = true;
                    z = false;
                    break;
                }
                z2 = true;
            }
            i++;
        }
        if (!z2 || !z) {
            return null;
        }
        char[] cArr = new char[str.length()];
        for (int i2 = 0; i2 < str.length(); i2++) {
            char cCharAt2 = str.charAt(i2);
            Character asciiForChar = KeyboardLayoutUtil.getAsciiForChar(cCharAt2);
            if (asciiForChar != null) {
                cCharAt2 = asciiForChar.charValue();
            }
            cArr[i2] = cCharAt2;
        }
        return new String(cArr);
    }

    private static MinusculeMatcher withFixedLayout(String str, NameUtil.MatchingCaseSensitivity matchingCaseSensitivity, String str2) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        if (matchingCaseSensitivity == null) {
            $$$reportNull$$$0(3);
        }
        String strFixLayout = fixLayout(str);
        if (strFixLayout == null || strFixLayout.equals(str)) {
            return null;
        }
        return new MinusculeMatcherImpl(strFixLayout, matchingCaseSensitivity, str2);
    }
}
