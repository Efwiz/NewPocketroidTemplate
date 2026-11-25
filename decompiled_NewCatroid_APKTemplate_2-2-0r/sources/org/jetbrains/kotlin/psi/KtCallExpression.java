package org.jetbrains.kotlin.psi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;

/* JADX INFO: loaded from: classes6.dex */
public class KtCallExpression extends KtExpressionImpl implements KtCallElement, KtReferenceExpression {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "visitor";
        } else if (i == 2 || i == 3 || i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtCallExpression";
        } else {
            objArr[0] = "node";
        }
        if (i == 2) {
            objArr[1] = "getLambdaArguments";
        } else if (i == 3 || i == 4) {
            objArr[1] = "getValueArguments";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtCallExpression";
        } else {
            objArr[1] = "getTypeArguments";
        }
        if (i == 1) {
            objArr[2] = "accept";
        } else if (i != 2 && i != 3 && i != 4 && i != 5) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtCallExpression(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtExpressionImpl, org.jetbrains.kotlin.psi.KtElementImpl, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(1);
        }
        return ktVisitor.visitCallExpression(this, d);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallElement
    public KtExpression getCalleeExpression() {
        return (KtExpression) findChildByClass(KtExpression.class);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallElement
    public KtValueArgumentList getValueArgumentList() {
        return (KtValueArgumentList) findChildByType(KtNodeTypes.VALUE_ARGUMENT_LIST);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallElement
    public KtTypeArgumentList getTypeArgumentList() {
        return (KtTypeArgumentList) findChildByType(KtNodeTypes.TYPE_ARGUMENT_LIST);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallElement
    public List<KtLambdaArgument> getLambdaArguments() {
        List<KtLambdaArgument> listFindChildrenByType = findChildrenByType(KtNodeTypes.LAMBDA_ARGUMENT);
        if (listFindChildrenByType == null) {
            $$$reportNull$$$0(2);
        }
        return listFindChildrenByType;
    }

    @Override // org.jetbrains.kotlin.psi.KtCallElement
    public List<KtValueArgument> getValueArguments() {
        KtValueArgumentList valueArgumentList = getValueArgumentList();
        List<KtValueArgument> arguments = valueArgumentList != null ? valueArgumentList.getArguments() : Collections.emptyList();
        List<KtLambdaArgument> lambdaArguments = getLambdaArguments();
        if (lambdaArguments.isEmpty()) {
            if (arguments == null) {
                $$$reportNull$$$0(3);
            }
            return arguments;
        }
        ArrayList arrayListNewArrayList = Lists.newArrayList();
        arrayListNewArrayList.addAll(arguments);
        arrayListNewArrayList.addAll(lambdaArguments);
        if (arrayListNewArrayList == null) {
            $$$reportNull$$$0(4);
        }
        return arrayListNewArrayList;
    }

    @Override // org.jetbrains.kotlin.psi.KtCallElement
    public List<KtTypeProjection> getTypeArguments() {
        KtTypeArgumentList typeArgumentList = getTypeArgumentList();
        List<KtTypeProjection> arguments = typeArgumentList != null ? typeArgumentList.getArguments() : Collections.emptyList();
        if (arguments == null) {
            $$$reportNull$$$0(5);
        }
        return arguments;
    }
}
