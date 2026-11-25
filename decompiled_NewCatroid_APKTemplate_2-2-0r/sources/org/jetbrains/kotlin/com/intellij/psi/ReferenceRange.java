package org.jetbrains.kotlin.com.intellij.psi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;

/* JADX INFO: loaded from: classes3.dex */
public final class ReferenceRange {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 4) ? 2 : 3];
        if (i == 1 || i == 2 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/ReferenceRange";
        } else if (i != 8) {
            objArr[0] = "ref";
        } else {
            objArr[0] = "rangeInElement";
        }
        if (i == 1 || i == 2) {
            objArr[1] = "getRanges";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/ReferenceRange";
        } else {
            objArr[1] = "getAbsoluteRanges";
        }
        switch (i) {
            case 1:
            case 2:
            case 4:
                break;
            case 3:
                objArr[2] = "getAbsoluteRanges";
                break;
            case 5:
                objArr[2] = "getRange";
                break;
            case 6:
                objArr[2] = "containsOffsetInElement";
                break;
            case 7:
            case 8:
                objArr[2] = "containsRangeInElement";
                break;
            default:
                objArr[2] = "getRanges";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private ReferenceRange() {
    }

    public static List<TextRange> getRanges(PsiReference psiReference) {
        if (psiReference == null) {
            $$$reportNull$$$0(0);
        }
        if (psiReference instanceof MultiRangeReference) {
            List<TextRange> ranges = ((MultiRangeReference) psiReference).getRanges();
            if (ranges == null) {
                $$$reportNull$$$0(1);
            }
            return ranges;
        }
        List<TextRange> listSingletonList = Collections.singletonList(psiReference.getRangeInElement());
        if (listSingletonList == null) {
            $$$reportNull$$$0(2);
        }
        return listSingletonList;
    }

    public static List<TextRange> getAbsoluteRanges(PsiReference psiReference) {
        if (psiReference == null) {
            $$$reportNull$$$0(3);
        }
        PsiElement element = psiReference.getElement();
        List<TextRange> ranges = getRanges(psiReference);
        ArrayList arrayList = new ArrayList(ranges.size());
        int startOffset = element.getTextRange().getStartOffset();
        Iterator<TextRange> it = ranges.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().shiftRight(startOffset));
        }
        return arrayList;
    }

    public static TextRange getRange(PsiReference psiReference) {
        if (psiReference == null) {
            $$$reportNull$$$0(5);
        }
        if (psiReference instanceof MultiRangeReference) {
            List<TextRange> ranges = ((MultiRangeReference) psiReference).getRanges();
            return new TextRange(ranges.get(0).getStartOffset(), ranges.get(ranges.size() - 1).getEndOffset());
        }
        return psiReference.getRangeInElement();
    }

    public static boolean containsOffsetInElement(PsiReference psiReference, int i) {
        if (psiReference == null) {
            $$$reportNull$$$0(6);
        }
        if (psiReference instanceof MultiRangeReference) {
            Iterator<TextRange> it = ((MultiRangeReference) psiReference).getRanges().iterator();
            while (it.hasNext()) {
                if (it.next().containsOffset(i)) {
                    return true;
                }
            }
            return false;
        }
        return psiReference.getRangeInElement().containsOffset(i);
    }

    public static boolean containsRangeInElement(PsiReference psiReference, TextRange textRange) {
        if (psiReference == null) {
            $$$reportNull$$$0(7);
        }
        if (textRange == null) {
            $$$reportNull$$$0(8);
        }
        if (psiReference instanceof MultiRangeReference) {
            Iterator<TextRange> it = ((MultiRangeReference) psiReference).getRanges().iterator();
            while (it.hasNext()) {
                if (it.next().contains(textRange)) {
                    return true;
                }
            }
            return false;
        }
        return psiReference.getRangeInElement().contains(textRange);
    }
}
