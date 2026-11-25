package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes6.dex */
public abstract class KtUnaryExpression extends KtExpressionImpl implements KtOperationExpression {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/psi/KtUnaryExpression", "getOperationReference"));
    }

    @IfNotParsed
    public abstract KtExpression getBaseExpression();

    public KtUnaryExpression(ASTNode aSTNode) {
        super(aSTNode);
    }

    @Override // org.jetbrains.kotlin.psi.KtOperationExpression
    public KtSimpleNameExpression getOperationReference() {
        KtSimpleNameExpression ktSimpleNameExpression = (KtSimpleNameExpression) findChildByType(KtNodeTypes.OPERATION_REFERENCE);
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(0);
        }
        return ktSimpleNameExpression;
    }

    public IElementType getOperationToken() {
        return getOperationReference().getReferencedNameElementType();
    }
}
