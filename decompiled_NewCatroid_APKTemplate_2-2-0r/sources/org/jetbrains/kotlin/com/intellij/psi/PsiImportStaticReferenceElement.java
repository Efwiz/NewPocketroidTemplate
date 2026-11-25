package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiImportStaticReferenceElement extends PsiJavaCodeReferenceElement {
    PsiImportStaticStatement bindToTargetClass(PsiClass psiClass) throws IncorrectOperationException;

    PsiJavaCodeReferenceElement getClassReference();
}
