package org.jetbrains.kotlin.com.intellij.psi.codeStyle;

import java.util.Iterator;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.util.containers.FList;
import org.jetbrains.kotlin.com.intellij.util.text.Matcher;

/* JADX INFO: loaded from: classes3.dex */
public abstract class MinusculeMatcher implements Matcher {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 6) {
            objArr[0] = "name";
        } else {
            objArr[0] = "fragments";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/MinusculeMatcher";
        switch (i) {
            case 1:
                objArr[2] = "matchingFragments";
                break;
            case 2:
            case 3:
            case 4:
                objArr[2] = "matchingDegree";
                break;
            case 5:
            case 6:
                objArr[2] = "isStartMatch";
                break;
            default:
                objArr[2] = "matches";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public abstract String getPattern();

    protected MinusculeMatcher() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.NameUtil.Matcher
    public boolean matches(String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        return matchingFragments(str) != null;
    }

    public FList<TextRange> matchingFragments(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        throw new UnsupportedOperationException();
    }

    public int matchingDegree(String str, boolean z, FList<? extends TextRange> fList) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        throw new UnsupportedOperationException();
    }

    public int matchingDegree(String str, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        return matchingDegree(str, z, matchingFragments(str));
    }

    public int matchingDegree(String str) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        return matchingDegree(str, false);
    }

    public boolean isStartMatch(String str) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        FList<TextRange> fListMatchingFragments = matchingFragments(str);
        return fListMatchingFragments != null && isStartMatch(fListMatchingFragments);
    }

    public static boolean isStartMatch(Iterable<? extends TextRange> iterable) {
        if (iterable == null) {
            $$$reportNull$$$0(6);
        }
        Iterator<? extends TextRange> it = iterable.iterator();
        return !it.hasNext() || it.next().getStartOffset() == 0;
    }
}
