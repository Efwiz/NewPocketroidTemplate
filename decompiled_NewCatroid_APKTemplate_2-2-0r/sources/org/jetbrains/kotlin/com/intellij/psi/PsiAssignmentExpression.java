package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiAssignmentExpression extends PsiExpression {
    PsiExpression getLExpression();

    PsiJavaToken getOperationSign();

    IElementType getOperationTokenType();

    PsiExpression getRExpression();
}
