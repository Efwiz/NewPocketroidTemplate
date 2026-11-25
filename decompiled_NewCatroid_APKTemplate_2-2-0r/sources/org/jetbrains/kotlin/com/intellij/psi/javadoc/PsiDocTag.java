package org.jetbrains.kotlin.com.intellij.psi.javadoc;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiDocTag extends PsiElement, PsiNamedElement {
    public static final PsiDocTag[] EMPTY_ARRAY = new PsiDocTag[0];

    PsiDocComment getContainingComment();

    PsiElement[] getDataElements();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    String getName();

    PsiElement getNameElement();

    PsiDocTagValue getValueElement();
}
