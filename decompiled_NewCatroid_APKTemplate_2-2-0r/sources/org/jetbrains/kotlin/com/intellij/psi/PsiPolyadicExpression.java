package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiPolyadicExpression extends PsiExpression {
    PsiExpression[] getOperands();

    IElementType getOperationTokenType();

    PsiJavaToken getTokenBeforeOperand(PsiExpression psiExpression);
}
