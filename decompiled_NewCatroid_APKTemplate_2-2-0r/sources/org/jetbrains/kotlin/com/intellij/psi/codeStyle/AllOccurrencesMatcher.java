package org.jetbrains.kotlin.com.intellij.psi.codeStyle;

import java.util.ArrayList;
import java.util.Iterator;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.util.containers.FList;

/* JADX INFO: loaded from: classes3.dex */
public final class AllOccurrencesMatcher extends MinusculeMatcher {
    private final MinusculeMatcher delegate;

    /* JADX WARN: Code duplicated, block: B:19:0x002d  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "options";
        } else if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/AllOccurrencesMatcher";
        } else if (i == 3 || i == 4) {
            objArr[0] = "name";
        } else if (i != 6) {
            objArr[0] = "pattern";
        } else {
            objArr[0] = "options";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/codeStyle/AllOccurrencesMatcher";
        } else {
            objArr[1] = "getPattern";
        }
        if (i != 2) {
            if (i == 3) {
                objArr[2] = "matchingDegree";
            } else if (i == 4) {
                objArr[2] = "matchingFragments";
            } else if (i == 5 || i == 6) {
                objArr[2] = CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME;
            } else {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    private AllOccurrencesMatcher(String str, NameUtil.MatchingCaseSensitivity matchingCaseSensitivity, String str2) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (matchingCaseSensitivity == null) {
            $$$reportNull$$$0(1);
        }
        this.delegate = new FixingLayoutMatcher(str, matchingCaseSensitivity, str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher
    public String getPattern() {
        String pattern = this.delegate.getPattern();
        if (pattern == null) {
            $$$reportNull$$$0(2);
        }
        return pattern;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher
    public int matchingDegree(String str, boolean z, FList<? extends TextRange> fList) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        return this.delegate.matchingDegree(str, z, fList);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.codeStyle.MinusculeMatcher
    public FList<TextRange> matchingFragments(String str) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        FList<TextRange> fListMatchingFragments = this.delegate.matchingFragments(str);
        if (fListMatchingFragments == null || fListMatchingFragments.isEmpty()) {
            return fListMatchingFragments;
        }
        ArrayList arrayList = new ArrayList();
        int endOffset = 0;
        while (fListMatchingFragments != null && !fListMatchingFragments.isEmpty()) {
            FList fListEmptyList = FList.emptyList();
            Iterator<TextRange> it = fListMatchingFragments.iterator();
            while (it.hasNext()) {
                fListEmptyList = fListEmptyList.prepend(it.next().shiftRight(endOffset));
            }
            arrayList.add(fListEmptyList);
            endOffset = ((TextRange) fListEmptyList.get(0)).getEndOffset();
            fListMatchingFragments = this.delegate.matchingFragments(str.substring(endOffset));
        }
        FList<TextRange> fListEmptyList2 = FList.emptyList();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            Iterator it2 = ((FList) arrayList.get(size)).iterator();
            while (it2.hasNext()) {
                fListEmptyList2 = fListEmptyList2.prepend((TextRange) it2.next());
            }
        }
        return fListEmptyList2;
    }

    public String toString() {
        return "AllOccurrencesMatcher{delegate=" + this.delegate + '}';
    }

    public static MinusculeMatcher create(String str, NameUtil.MatchingCaseSensitivity matchingCaseSensitivity, String str2) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        if (matchingCaseSensitivity == null) {
            $$$reportNull$$$0(6);
        }
        return new AllOccurrencesMatcher(str, matchingCaseSensitivity, str2);
    }
}
