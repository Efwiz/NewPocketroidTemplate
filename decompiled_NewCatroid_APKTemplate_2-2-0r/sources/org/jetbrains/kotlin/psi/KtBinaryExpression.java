package org.jetbrains.kotlin.psi;

import java.util.Arrays;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes6.dex */
public class KtBinaryExpression extends KtExpressionImpl implements KtOperationExpression {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "visitor";
        } else if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtBinaryExpression";
        } else {
            objArr[0] = "node";
        }
        if (i == 2) {
            objArr[1] = "getOperationReference";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtBinaryExpression";
        } else {
            objArr[1] = "getOperationToken";
        }
        if (i == 1) {
            objArr[2] = "accept";
        } else if (i != 2 && i != 3) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtBinaryExpression(ASTNode aSTNode) {
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
        return ktVisitor.visitBinaryExpression(this, d);
    }

    @IfNotParsed
    public KtExpression getLeft() {
        for (ASTNode treePrev = getOperationReference().getNode().getTreePrev(); treePrev != null; treePrev = treePrev.getTreePrev()) {
            PsiElement psi = treePrev.getPsi();
            if (psi instanceof KtExpression) {
                return (KtExpression) psi;
            }
        }
        return null;
    }

    @IfNotParsed
    public KtExpression getRight() {
        for (ASTNode treeNext = getOperationReference().getNode().getTreeNext(); treeNext != null; treeNext = treeNext.getTreeNext()) {
            PsiElement psi = treeNext.getPsi();
            if (psi instanceof KtExpression) {
                return (KtExpression) psi;
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtOperationExpression
    public KtOperationReferenceExpression getOperationReference() {
        PsiElement psiElementFindChildByType = findChildByType(KtNodeTypes.OPERATION_REFERENCE);
        if (psiElementFindChildByType == null) {
            throw new NullPointerException("No operation reference for binary expression: " + Arrays.toString(getChildren()));
        }
        KtOperationReferenceExpression ktOperationReferenceExpression = (KtOperationReferenceExpression) psiElementFindChildByType;
        if (ktOperationReferenceExpression == null) {
            $$$reportNull$$$0(2);
        }
        return ktOperationReferenceExpression;
    }

    public IElementType getOperationToken() {
        IElementType referencedNameElementType = getOperationReference().getReferencedNameElementType();
        if (referencedNameElementType == null) {
            $$$reportNull$$$0(3);
        }
        return referencedNameElementType;
    }
}
