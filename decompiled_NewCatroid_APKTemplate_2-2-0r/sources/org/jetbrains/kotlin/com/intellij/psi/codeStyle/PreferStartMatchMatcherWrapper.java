package org.jetbrains.kotlin.com.intellij.psi.codeStyle;

import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.util.containers.FList;

/* JADX INFO: loaded from: classes3.dex */
public class PreferStartMatchMatcherWrapper extends MinusculeMatcher {
    public static final int START_MATCH_WEIGHT = 10000;
    private final MinusculeMatcher myDelegateMatcher;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/PreferStartMatchMatcherWrapper";
        } else if (i == 2 || i == 3) {
            objArr[0] = "name";
        } else {
            objArr[0] = "matcher";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/PreferStartMatchMatcherWrapper";
        } else {
            objArr[1] = "getPattern";
        }
        if (i != 1) {
            if (i == 2) {
                objArr[2] = "matchingFragments";
            } else if (i != 3) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "matchingDegree";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public PreferStartMatchMatcherWrapper(MinusculeMatcher minusculeMatcher) {
        if (minusculeMatcher == null) {
            $$$reportNull$$$0(0);
        }
        this.myDelegateMatcher = minusculeMatcher;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher
    public String getPattern() {
        String pattern = this.myDelegateMatcher.getPattern();
        if (pattern == null) {
            $$$reportNull$$$0(1);
        }
        return pattern;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher
    public FList<TextRange> matchingFragments(String str) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return this.myDelegateMatcher.matchingFragments(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher
    public int matchingDegree(String str, boolean z, FList<? extends TextRange> fList) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        int iMatchingDegree = this.myDelegateMatcher.matchingDegree(str, z, fList);
        return (fList == null || fList.isEmpty() || fList.getHead().getStartOffset() != 0) ? iMatchingDegree : iMatchingDegree + 10000;
    }
}
