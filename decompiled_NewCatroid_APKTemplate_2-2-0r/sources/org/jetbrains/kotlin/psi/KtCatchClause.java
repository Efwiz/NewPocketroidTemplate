package org.jetbrains.kotlin.psi;

import java.util.List;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;

/* JADX INFO: loaded from: classes6.dex */
public class KtCatchClause extends KtElementImpl {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "node";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/psi/KtCatchClause";
        if (i != 1) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtCatchClause(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImpl, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(1);
        }
        return ktVisitor.visitCatchSection(this, d);
    }

    @IfNotParsed
    public KtParameterList getParameterList() {
        return (KtParameterList) findChildByType(KtNodeTypes.VALUE_PARAMETER_LIST);
    }

    @IfNotParsed
    public KtParameter getCatchParameter() {
        KtParameterList parameterList = getParameterList();
        if (parameterList == null) {
            return null;
        }
        List<KtParameter> parameters = parameterList.getParameters();
        if (parameters.size() == 1) {
            return parameters.get(0);
        }
        return null;
    }

    @IfNotParsed
    public KtExpression getCatchBody() {
        return (KtExpression) findChildByClass(KtExpression.class);
    }
}
