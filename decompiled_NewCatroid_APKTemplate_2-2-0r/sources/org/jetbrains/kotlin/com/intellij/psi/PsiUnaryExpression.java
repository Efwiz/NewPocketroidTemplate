package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiUnaryExpression extends PsiExpression {
    PsiExpression getOperand();

    PsiJavaToken getOperationSign();

    IElementType getOperationTokenType();
}
