package org.jetbrains.kotlin.psi;

import java.util.List;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.lexer.KtTokens;

/* JADX INFO: loaded from: classes6.dex */
public class KtWhenExpression extends KtExpressionImpl {
    /* JADX WARN: Code duplicated, block: B:18:0x0028  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtWhenExpression";
        } else if (i == 2) {
            objArr[0] = "visitor";
        } else if (i != 3) {
            objArr[0] = "node";
        } else {
            objArr[0] = "org/jetbrains/kotlin/psi/KtWhenExpression";
        }
        if (i == 1) {
            objArr[1] = "getEntries";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtWhenExpression";
        } else {
            objArr[1] = "getWhenKeyword";
        }
        if (i != 1) {
            if (i == 2) {
                objArr[2] = "accept";
            } else if (i != 3) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtWhenExpression(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    public List<KtWhenEntry> getEntries() {
        List<KtWhenEntry> listFindChildrenByType = findChildrenByType(KtNodeTypes.WHEN_ENTRY);
        if (listFindChildrenByType == null) {
            $$$reportNull$$$0(1);
        }
        return listFindChildrenByType;
    }

    public KtProperty getSubjectVariable() {
        return (KtProperty) findChildByClass(KtProperty.class);
    }

    public KtExpression getSubjectExpression() {
        return (KtExpression) findChildByClass(KtExpression.class);
    }

    @Override // org.jetbrains.kotlin.psi.KtExpressionImpl, org.jetbrains.kotlin.psi.KtElementImpl, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(2);
        }
        return ktVisitor.visitWhenExpression(this, d);
    }

    public PsiElement getWhenKeyword() {
        PsiElement psiElementFindChildByType = findChildByType(KtTokens.WHEN_KEYWORD);
        if (psiElementFindChildByType == null) {
            $$$reportNull$$$0(3);
        }
        return psiElementFindChildByType;
    }

    public PsiElement getCloseBrace() {
        return findChildByType(KtTokens.RBRACE);
    }

    public PsiElement getOpenBrace() {
        return findChildByType(KtTokens.LBRACE);
    }

    public PsiElement getLeftParenthesis() {
        return findChildByType(KtTokens.LPAR);
    }

    public PsiElement getRightParenthesis() {
        return findChildByType(KtTokens.RPAR);
    }

    public KtExpression getElseExpression() {
        for (KtWhenEntry ktWhenEntry : getEntries()) {
            if (ktWhenEntry.isElse()) {
                return ktWhenEntry.getExpression();
            }
        }
        return null;
    }
}
