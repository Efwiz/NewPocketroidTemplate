package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.lexer.KtTokens;

/* JADX INFO: loaded from: classes6.dex */
public class KtWhenConditionInRange extends KtWhenCondition {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "visitor";
        } else if (i != 2) {
            objArr[0] = "node";
        } else {
            objArr[0] = "org/jetbrains/kotlin/psi/KtWhenConditionInRange";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtWhenConditionInRange";
        } else {
            objArr[1] = "getOperationReference";
        }
        if (i == 1) {
            objArr[2] = "accept";
        } else if (i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtWhenConditionInRange(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    public boolean isNegated() {
        return getOperationReference().getNode().findChildByType(KtTokens.NOT_IN) != null;
    }

    @IfNotParsed
    public KtExpression getRangeExpression() {
        for (ASTNode treeNext = getOperationReference().getNode().getTreeNext(); treeNext != null; treeNext = treeNext.getTreeNext()) {
            PsiElement psi = treeNext.getPsi();
            if (psi instanceof KtExpression) {
                return (KtExpression) psi;
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImpl, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(1);
        }
        return ktVisitor.visitWhenConditionInRange(this, d);
    }

    public KtOperationReferenceExpression getOperationReference() {
        KtOperationReferenceExpression ktOperationReferenceExpression = (KtOperationReferenceExpression) findChildByType(KtNodeTypes.OPERATION_REFERENCE);
        if (ktOperationReferenceExpression == null) {
            $$$reportNull$$$0(2);
        }
        return ktOperationReferenceExpression;
    }
}
