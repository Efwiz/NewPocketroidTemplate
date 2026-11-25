package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiMethodCallExpression extends PsiCallExpression {
    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCall
    PsiExpressionList getArgumentList();

    PsiReferenceExpression getMethodExpression();
}
