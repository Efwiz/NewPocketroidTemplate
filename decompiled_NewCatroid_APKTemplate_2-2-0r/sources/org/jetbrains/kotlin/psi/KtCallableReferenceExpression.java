package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.lexer.KtTokens;

/* JADX INFO: loaded from: classes6.dex */
public class KtCallableReferenceExpression extends KtExpressionImpl implements KtDoubleColonExpression {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtCallableReferenceExpression";
        } else if (i != 2) {
            objArr[0] = "node";
        } else {
            objArr[0] = "visitor";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtCallableReferenceExpression";
        } else {
            objArr[1] = "getCallableReference";
        }
        if (i != 1) {
            if (i != 2) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "accept";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtCallableReferenceExpression(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    public KtSimpleNameExpression getCallableReference() {
        for (PsiElement doubleColonTokenReference = getDoubleColonTokenReference(); doubleColonTokenReference != null; doubleColonTokenReference = doubleColonTokenReference.getNextSibling()) {
            if (doubleColonTokenReference instanceof KtSimpleNameExpression) {
                KtSimpleNameExpression ktSimpleNameExpression = (KtSimpleNameExpression) doubleColonTokenReference;
                if (ktSimpleNameExpression == null) {
                    $$$reportNull$$$0(1);
                }
                return ktSimpleNameExpression;
            }
        }
        throw new IllegalStateException("Callable reference simple name shouldn't be parsed to null");
    }

    @Override // org.jetbrains.kotlin.psi.KtDoubleColonExpression
    public PsiElement findColonColon() {
        return findChildByType(KtTokens.COLONCOLON);
    }

    @Override // org.jetbrains.kotlin.psi.KtExpressionImpl, org.jetbrains.kotlin.psi.KtElementImpl, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(2);
        }
        return ktVisitor.visitCallableReferenceExpression(this, d);
    }
}
