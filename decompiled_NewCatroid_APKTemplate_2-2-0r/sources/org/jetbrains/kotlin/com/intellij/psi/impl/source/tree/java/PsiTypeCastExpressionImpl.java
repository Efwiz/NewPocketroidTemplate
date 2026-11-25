package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeCastExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiTypeCastExpressionImpl extends ExpressionPsiElement implements PsiTypeCastExpression, Constants {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiTypeCastExpressionImpl.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "child";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiTypeCastExpressionImpl";
        if (i != 1) {
            objArr[2] = "getChildRole";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public PsiTypeCastExpressionImpl() {
        super(TYPE_CAST_EXPRESSION);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeCastExpression
    public PsiTypeElement getCastType() {
        return (PsiTypeElement) findChildByRoleAsPsiElement(10);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeCastExpression
    public PsiExpression getOperand() {
        return (PsiExpression) findChildByRoleAsPsiElement(67);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpression, org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        PsiTypeElement castType = getCastType();
        if (castType == null) {
            return null;
        }
        return PsiUtil.captureToplevelWildcards(castType.getType(), this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 10) {
            return findChildByType(TYPE);
        }
        if (i == 67) {
            return findChildByType(EXPRESSION_BIT_SET);
        }
        if (i == 24) {
            return findChildByType(LPARENTH);
        }
        if (i != 25) {
            return null;
        }
        return findChildByType(RPARENTH);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        IElementType elementType = aSTNode.getElementType();
        if (elementType == LPARENTH) {
            return 24;
        }
        if (elementType == RPARENTH) {
            return 25;
        }
        if (elementType == TYPE) {
            return 10;
        }
        return EXPRESSION_BIT_SET.contains(aSTNode.getElementType()) ? 67 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitTypeCastExpression(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiTypeCastExpression:" + getText();
    }
}
