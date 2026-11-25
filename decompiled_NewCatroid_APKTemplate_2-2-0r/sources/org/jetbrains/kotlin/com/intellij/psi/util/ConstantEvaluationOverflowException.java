package org.jetbrains.kotlin.com.intellij.psi.util;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public class ConstantEvaluationOverflowException extends RuntimeException {
    private final PsiElement myOverflowingExpression;

    public ConstantEvaluationOverflowException(PsiElement psiElement) {
        this.myOverflowingExpression = psiElement;
    }

    public PsiElement getOverflowingExpression() {
        return this.myOverflowingExpression;
    }
}
