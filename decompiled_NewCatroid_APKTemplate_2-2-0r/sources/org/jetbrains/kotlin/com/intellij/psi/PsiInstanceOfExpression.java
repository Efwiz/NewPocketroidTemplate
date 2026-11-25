package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiInstanceOfExpression extends PsiExpression {
    PsiTypeElement getCheckType();

    PsiExpression getOperand();

    PsiPrimaryPattern getPattern();
}
