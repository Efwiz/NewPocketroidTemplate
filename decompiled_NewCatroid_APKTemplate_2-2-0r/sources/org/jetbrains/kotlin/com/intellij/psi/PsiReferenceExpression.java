package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiReferenceExpression extends PsiExpression, PsiJavaCodeReferenceElement {
    PsiElement bindToElementViaStaticImport(PsiClass psiClass) throws IncorrectOperationException;

    PsiExpression getQualifierExpression();

    void setQualifierExpression(PsiExpression psiExpression) throws IncorrectOperationException;
}
