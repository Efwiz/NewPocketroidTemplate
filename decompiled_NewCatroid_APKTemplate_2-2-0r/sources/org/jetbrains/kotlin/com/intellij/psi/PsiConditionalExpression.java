package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiConditionalExpression extends PsiExpression {
    PsiExpression getCondition();

    PsiExpression getElseExpression();

    PsiExpression getThenExpression();
}
