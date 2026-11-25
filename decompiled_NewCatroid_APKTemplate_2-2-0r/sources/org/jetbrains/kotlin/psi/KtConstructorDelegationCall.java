package org.jetbrains.kotlin.psi;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;

/* JADX INFO: loaded from: classes6.dex */
public class KtConstructorDelegationCall extends KtElementImpl implements KtCallElement {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "visitor";
        } else if (i == 2 || i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtConstructorDelegationCall";
        } else {
            objArr[0] = "node";
        }
        if (i == 2) {
            objArr[1] = "getValueArguments";
        } else if (i == 3) {
            objArr[1] = "getLambdaArguments";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtConstructorDelegationCall";
        } else {
            objArr[1] = "getTypeArguments";
        }
        if (i == 1) {
            objArr[2] = "accept";
        } else if (i != 2 && i != 3 && i != 4) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallElement
    public KtTypeArgumentList getTypeArgumentList() {
        return null;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtConstructorDelegationCall(ASTNode aSTNode) {
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
        return ktVisitor.visitConstructorDelegationCall(this, d);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallElement
    public KtValueArgumentList getValueArgumentList() {
        return (KtValueArgumentList) findChildByType(KtNodeTypes.VALUE_ARGUMENT_LIST);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallElement
    public List<? extends ValueArgument> getValueArguments() {
        KtValueArgumentList valueArgumentList = getValueArgumentList();
        List<KtValueArgument> arguments = valueArgumentList != null ? valueArgumentList.getArguments() : Collections.emptyList();
        if (arguments == null) {
            $$$reportNull$$$0(2);
        }
        return arguments;
    }

    @Override // org.jetbrains.kotlin.psi.KtCallElement
    public List<KtLambdaArgument> getLambdaArguments() {
        List<KtLambdaArgument> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(3);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.psi.KtCallElement
    public List<KtTypeProjection> getTypeArguments() {
        List<KtTypeProjection> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(4);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.psi.KtCallElement
    public KtConstructorDelegationReferenceExpression getCalleeExpression() {
        return (KtConstructorDelegationReferenceExpression) findChildByClass(KtConstructorDelegationReferenceExpression.class);
    }

    public boolean isImplicit() {
        KtConstructorDelegationReferenceExpression calleeExpression = getCalleeExpression();
        return calleeExpression != null && calleeExpression.getFirstChild() == null;
    }

    public boolean isCallToThis() {
        KtConstructorDelegationReferenceExpression calleeExpression = getCalleeExpression();
        return calleeExpression != null && calleeExpression.isThis();
    }
}
