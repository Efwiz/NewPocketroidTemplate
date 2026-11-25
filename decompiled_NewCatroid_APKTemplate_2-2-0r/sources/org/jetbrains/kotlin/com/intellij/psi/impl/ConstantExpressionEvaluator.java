package org.jetbrains.kotlin.com.intellij.psi.impl;

import org.jetbrains.kotlin.com.intellij.psi.PsiConstantEvaluationHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public interface ConstantExpressionEvaluator {
    Object computeConstantExpression(PsiElement psiElement, boolean z);

    Object computeExpression(PsiElement psiElement, boolean z, PsiConstantEvaluationHelper.AuxEvaluator auxEvaluator);
}
