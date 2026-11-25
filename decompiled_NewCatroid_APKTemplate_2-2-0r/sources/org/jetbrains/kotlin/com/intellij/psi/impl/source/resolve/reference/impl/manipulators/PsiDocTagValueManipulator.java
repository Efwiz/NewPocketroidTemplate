package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.impl.manipulators;

import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.AbstractElementManipulator;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTag;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiDocTagValueManipulator extends AbstractElementManipulator<PsiDocTag> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "tag";
        } else {
            objArr[0] = "range";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/impl/manipulators/PsiDocTagValueManipulator";
        if (i != 2) {
            objArr[2] = "handleContentChange";
        } else {
            objArr[2] = "getRangeInElement";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.ElementManipulator
    public PsiDocTag handleContentChange(PsiDocTag psiDocTag, TextRange textRange, String str) throws IncorrectOperationException {
        if (psiDocTag == null) {
            $$$reportNull$$$0(0);
        }
        if (textRange == null) {
            $$$reportNull$$$0(1);
        }
        StringBuilder sb = new StringBuilder(psiDocTag.getText());
        sb.replace(textRange.getStartOffset(), textRange.getEndOffset(), str);
        return (PsiDocTag) psiDocTag.replace(JavaPsiFacade.getElementFactory(psiDocTag.getProject()).createDocTagFromText(sb.toString()));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractElementManipulator, org.jetbrains.kotlin.com.intellij.psi.ElementManipulator
    public TextRange getRangeInElement(PsiDocTag psiDocTag) {
        if (psiDocTag == null) {
            $$$reportNull$$$0(2);
        }
        PsiElement[] dataElements = psiDocTag.getDataElements();
        if (dataElements.length == 0) {
            PsiElement nameElement = psiDocTag.getNameElement();
            int startOffsetInParent = nameElement.getStartOffsetInParent() + nameElement.getTextLength();
            return new TextRange(startOffsetInParent, startOffsetInParent);
        }
        PsiElement psiElement = dataElements[0];
        PsiElement psiElement2 = dataElements[dataElements.length - 1];
        return new TextRange(psiElement.getStartOffsetInParent(), psiElement2.getStartOffsetInParent() + psiElement2.getTextLength());
    }
}
