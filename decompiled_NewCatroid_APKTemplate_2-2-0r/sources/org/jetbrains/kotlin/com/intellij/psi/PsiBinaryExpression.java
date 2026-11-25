package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiBinaryExpression extends PsiPolyadicExpression {
    public static final TokenSet BOOLEAN_OPERATION_TOKENS = TokenSet.create(JavaTokenType.EQEQ, JavaTokenType.NE, JavaTokenType.LT, JavaTokenType.GT, JavaTokenType.LE, JavaTokenType.GE, JavaTokenType.OROR, JavaTokenType.ANDAND);

    PsiExpression getLOperand();

    PsiJavaToken getOperationSign();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPolyadicExpression
    IElementType getOperationTokenType();

    PsiExpression getROperand();
}
