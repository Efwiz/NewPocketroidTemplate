package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;

/* JADX INFO: loaded from: classes6.dex */
public abstract class KtInstanceExpressionWithLabel extends KtExpressionWithLabel {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "node";
        } else {
            objArr[0] = "org/jetbrains/kotlin/psi/KtInstanceExpressionWithLabel";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtInstanceExpressionWithLabel";
        } else {
            objArr[1] = "getInstanceReference";
        }
        if (i != 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtInstanceExpressionWithLabel(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    public KtReferenceExpression getInstanceReference() {
        KtReferenceExpression ktReferenceExpression = (KtReferenceExpression) findChildByType(KtNodeTypes.REFERENCE_EXPRESSION);
        if (ktReferenceExpression == null) {
            $$$reportNull$$$0(1);
        }
        return ktReferenceExpression;
    }
}
