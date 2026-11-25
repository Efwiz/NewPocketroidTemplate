package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiPostfixExpression extends PsiUnaryExpression {
    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiUnaryExpression
    PsiExpression getOperand();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiUnaryExpression
    PsiJavaToken getOperationSign();
}
