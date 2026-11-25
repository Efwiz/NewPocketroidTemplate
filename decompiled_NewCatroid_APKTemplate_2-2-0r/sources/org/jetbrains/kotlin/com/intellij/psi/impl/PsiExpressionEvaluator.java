package org.jetbrains.kotlin.com.intellij.psi.impl;

import org.jetbrains.kotlin.com.intellij.psi.PsiConstantEvaluationHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;

/* JADX INFO: loaded from: classes3.dex */
public class PsiExpressionEvaluator implements ConstantExpressionEvaluator {
    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ConstantExpressionEvaluator
    public Object computeConstantExpression(PsiElement psiElement, boolean z) {
        if (psiElement instanceof PsiExpression) {
            return JavaConstantExpressionEvaluator.computeConstantExpression((PsiExpression) psiElement, z);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ConstantExpressionEvaluator
    public Object computeExpression(PsiElement psiElement, boolean z, PsiConstantEvaluationHelper.AuxEvaluator auxEvaluator) {
        if (psiElement instanceof PsiExpression) {
            return JavaConstantExpressionEvaluator.computeConstantExpression((PsiExpression) psiElement, null, z, auxEvaluator);
        }
        return null;
    }
}
