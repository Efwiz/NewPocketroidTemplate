package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiReference {
    public static final PsiReference[] EMPTY_ARRAY = new PsiReference[0];
    public static final ArrayFactory<PsiReference> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.PsiReference$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiReference.lambda$static$0(i);
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[2];
        objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/PsiReference";
        if (i != 1) {
            objArr[1] = "getAbsoluteRange";
        } else {
            objArr[1] = "getVariants";
        }
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
    }

    PsiElement bindToElement(PsiElement psiElement) throws IncorrectOperationException;

    String getCanonicalText();

    PsiElement getElement();

    TextRange getRangeInElement();

    PsiElement handleElementRename(String str) throws IncorrectOperationException;

    boolean isReferenceTo(PsiElement psiElement);

    boolean isSoft();

    PsiElement resolve();

    static /* synthetic */ PsiReference[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiReference[i];
    }

    default TextRange getAbsoluteRange() {
        TextRange textRangeShiftRight = getRangeInElement().shiftRight(getElement().getTextRange().getStartOffset());
        if (textRangeShiftRight == null) {
            $$$reportNull$$$0(0);
        }
        return textRangeShiftRight;
    }

    default Object[] getVariants() {
        Object[] objArr = ArrayUtilRt.EMPTY_OBJECT_ARRAY;
        if (objArr == null) {
            $$$reportNull$$$0(1);
        }
        return objArr;
    }
}
