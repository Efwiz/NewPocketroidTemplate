package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.lexer.KtTokens;

/* JADX INFO: loaded from: classes6.dex */
public abstract class KtLoopExpression extends KtExpressionImpl implements KtStatementExpression {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "node", "org/jetbrains/kotlin/psi/KtLoopExpression", "<init>"));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLoopExpression(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    public KtExpression getBody() {
        return findExpressionUnder(KtNodeTypes.BODY);
    }

    @IfNotParsed
    public PsiElement getLeftParenthesis() {
        return findChildByType(KtTokens.LPAR);
    }

    @IfNotParsed
    public PsiElement getRightParenthesis() {
        return findChildByType(KtTokens.RPAR);
    }
}
