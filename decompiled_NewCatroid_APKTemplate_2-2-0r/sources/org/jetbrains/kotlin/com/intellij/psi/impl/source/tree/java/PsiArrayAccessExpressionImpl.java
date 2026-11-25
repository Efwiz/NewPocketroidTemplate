package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayAccessExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiArrayAccessExpressionImpl extends ExpressionPsiElement implements PsiArrayAccessExpression, Constants {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiArrayAccessExpressionImpl.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 3 : 2];
        if (i == 1) {
            objArr[0] = "child";
        } else if (i != 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiArrayAccessExpressionImpl";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 1 || i == 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiArrayAccessExpressionImpl";
        } else {
            objArr[1] = "getArrayExpression";
        }
        if (i == 1) {
            objArr[2] = "getChildRole";
        } else if (i == 2) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public PsiArrayAccessExpressionImpl() {
        super(ARRAY_ACCESS_EXPRESSION);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiArrayAccessExpression
    public PsiExpression getArrayExpression() {
        PsiExpression psiExpression = (PsiExpression) findChildByRoleAsPsiElement(88);
        if (psiExpression == null) {
            $$$reportNull$$$0(0);
        }
        return psiExpression;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiArrayAccessExpression
    public PsiExpression getIndexExpression() {
        return (PsiExpression) findChildByRoleAsPsiElement(89);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpression, org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        PsiType type = getArrayExpression().getType();
        if (!(type instanceof PsiArrayType)) {
            return null;
        }
        PsiType componentType = ((PsiArrayType) type).getComponentType();
        return PsiUtil.isAccessedForWriting(this) ? componentType : PsiUtil.captureToplevelWildcards(componentType, this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        ASTNode aSTNodeFindChildByRole;
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 74) {
            return findChildByType(LBRACKET);
        }
        if (i == 75) {
            return findChildByType(RBRACKET);
        }
        if (i == 88) {
            return getFirstChildNode();
        }
        if (i != 89 || (aSTNodeFindChildByRole = findChildByRole(74)) == null) {
            return null;
        }
        do {
            aSTNodeFindChildByRole = aSTNodeFindChildByRole.getTreeNext();
            if (aSTNodeFindChildByRole == null) {
                return null;
            }
        } while (!EXPRESSION_BIT_SET.contains(aSTNodeFindChildByRole.getElementType()));
        return aSTNodeFindChildByRole;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == LBRACKET) {
            return 74;
        }
        if (elementType == RBRACKET) {
            return 75;
        }
        if (EXPRESSION_BIT_SET.contains(aSTNode.getElementType())) {
            return aSTNode == getFirstChildNode() ? 88 : 89;
        }
        return 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitArrayAccessExpression(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiArrayAccessExpression:" + getText();
    }
}
