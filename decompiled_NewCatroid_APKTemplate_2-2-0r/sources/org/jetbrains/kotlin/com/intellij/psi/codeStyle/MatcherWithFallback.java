package org.jetbrains.kotlin.com.intellij.psi.codeStyle;

import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.util.containers.FList;

/* JADX INFO: loaded from: classes3.dex */
class MatcherWithFallback extends MinusculeMatcher {
    private final MinusculeMatcher myFallbackMatcher;
    private final MinusculeMatcher myMainMatcher;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/MatcherWithFallback";
        } else if (i == 2 || i == 3 || i == 4 || i == 5) {
            objArr[0] = "name";
        } else {
            objArr[0] = "mainMatcher";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/MatcherWithFallback";
        } else {
            objArr[1] = "getPattern";
        }
        if (i != 1) {
            if (i == 2) {
                objArr[2] = "matches";
            } else if (i == 3) {
                objArr[2] = "matchingFragments";
            } else if (i == 4 || i == 5) {
                objArr[2] = "matchingDegree";
            } else {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    MatcherWithFallback(MinusculeMatcher minusculeMatcher, MinusculeMatcher minusculeMatcher2) {
        if (minusculeMatcher == null) {
            $$$reportNull$$$0(0);
        }
        this.myMainMatcher = minusculeMatcher;
        this.myFallbackMatcher = minusculeMatcher2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher
    public String getPattern() {
        String pattern = this.myMainMatcher.getPattern();
        if (pattern == null) {
            $$$reportNull$$$0(1);
        }
        return pattern;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher, org.jetbrains.kotlin.com.intellij.psi.codeStyle.NameUtil.Matcher
    public boolean matches(String str) {
        MinusculeMatcher minusculeMatcher;
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return this.myMainMatcher.matches(str) || ((minusculeMatcher = this.myFallbackMatcher) != null && minusculeMatcher.matches(str));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher
    public FList<TextRange> matchingFragments(String str) {
        MinusculeMatcher minusculeMatcher;
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        FList<TextRange> fListMatchingFragments = this.myMainMatcher.matchingFragments(str);
        return ((fListMatchingFragments == null || fListMatchingFragments.isEmpty()) && (minusculeMatcher = this.myFallbackMatcher) != null) ? minusculeMatcher.matchingFragments(str) : fListMatchingFragments;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher
    public int matchingDegree(String str, boolean z) {
        MinusculeMatcher minusculeMatcher;
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        FList<TextRange> fListMatchingFragments = this.myMainMatcher.matchingFragments(str);
        if ((fListMatchingFragments != null && !fListMatchingFragments.isEmpty()) || (minusculeMatcher = this.myFallbackMatcher) == null) {
            return this.myMainMatcher.matchingDegree(str, z, fListMatchingFragments);
        }
        return minusculeMatcher.matchingDegree(str, z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher
    public int matchingDegree(String str, boolean z, FList<? extends TextRange> fList) {
        MinusculeMatcher minusculeMatcher;
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        FList<TextRange> fListMatchingFragments = this.myMainMatcher.matchingFragments(str);
        if ((fListMatchingFragments != null && !fListMatchingFragments.isEmpty()) || (minusculeMatcher = this.myFallbackMatcher) == null) {
            return this.myMainMatcher.matchingDegree(str, z, fList);
        }
        return minusculeMatcher.matchingDegree(str, z, fList);
    }

    public String toString() {
        return "MatcherWithFallback{myMainMatcher=" + this.myMainMatcher + ", myFallbackMatcher=" + this.myFallbackMatcher + '}';
    }
}
