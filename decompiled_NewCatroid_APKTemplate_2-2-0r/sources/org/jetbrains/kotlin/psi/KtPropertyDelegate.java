package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.lexer.KtTokens;

/* JADX INFO: loaded from: classes6.dex */
public class KtPropertyDelegate extends KtElementImpl {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "visitor";
        } else if (i != 2) {
            objArr[0] = "node";
        } else {
            objArr[0] = "org/jetbrains/kotlin/psi/KtPropertyDelegate";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtPropertyDelegate";
        } else {
            objArr[1] = "getByKeywordNode";
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
    public KtPropertyDelegate(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    public KtExpression getExpression() {
        return (KtExpression) findChildByClass(KtExpression.class);
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImpl, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(1);
        }
        return ktVisitor.visitPropertyDelegate(this, d);
    }

    public ASTNode getByKeywordNode() {
        ASTNode aSTNodeFindChildByType = getNode().findChildByType(KtTokens.BY_KEYWORD);
        if (aSTNodeFindChildByType == null) {
            $$$reportNull$$$0(2);
        }
        return aSTNodeFindChildByType;
    }
}
