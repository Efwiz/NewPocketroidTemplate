package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.java.parser.ExpressionParser;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayAccessExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiAssignmentExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiAssignmentExpressionImpl extends ExpressionPsiElement implements PsiAssignmentExpression {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiAssignmentExpressionImpl.class);
    private static final TokenSet OUR_OPERATIONS_BIT_SET = ExpressionParser.ASSIGNMENT_OPS;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 3 || i == 4) ? 3 : 2];
        if (i == 3) {
            objArr[0] = "child";
        } else if (i != 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiAssignmentExpressionImpl";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 1) {
            objArr[1] = "getOperationSign";
        } else if (i == 2) {
            objArr[1] = "getOperationTokenType";
        } else if (i == 3 || i == 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiAssignmentExpressionImpl";
        } else {
            objArr[1] = "getLExpression";
        }
        if (i == 3) {
            objArr[2] = "getChildRole";
        } else if (i == 4) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public PsiAssignmentExpressionImpl() {
        super(JavaElementType.ASSIGNMENT_EXPRESSION);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAssignmentExpression
    public PsiExpression getLExpression() {
        PsiExpression psiExpression = (PsiExpression) findChildByRoleAsPsiElement(61);
        if (psiExpression == null) {
            $$$reportNull$$$0(0);
        }
        return psiExpression;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAssignmentExpression
    public PsiExpression getRExpression() {
        return (PsiExpression) findChildByRoleAsPsiElement(62);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAssignmentExpression
    public PsiJavaToken getOperationSign() {
        PsiJavaToken psiJavaToken = (PsiJavaToken) findChildByRoleAsPsiElement(63);
        if (psiJavaToken == null) {
            $$$reportNull$$$0(1);
        }
        return psiJavaToken;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAssignmentExpression
    public IElementType getOperationTokenType() {
        IElementType tokenType = getOperationSign().getTokenType();
        if (tokenType == null) {
            $$$reportNull$$$0(2);
        }
        return tokenType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpression, org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression
    public PsiType getType() {
        PsiExpression psiExpressionDeparenthesizeExpression = PsiUtil.deparenthesizeExpression(getLExpression());
        if ((psiExpressionDeparenthesizeExpression instanceof PsiReferenceExpression) || (psiExpressionDeparenthesizeExpression instanceof PsiArrayAccessExpression)) {
            return psiExpressionDeparenthesizeExpression.getType();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        switch (i) {
            case 61:
                return getFirstChildNode();
            case 62:
                if (ElementType.EXPRESSION_BIT_SET.contains(getLastChildNode().getElementType())) {
                    return getLastChildNode();
                }
                return null;
            case 63:
                return findChildByType(OUR_OPERATIONS_BIT_SET);
            default:
                return null;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(3);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        if (!ElementType.EXPRESSION_BIT_SET.contains(aSTNode.getElementType())) {
            return OUR_OPERATIONS_BIT_SET.contains(aSTNode.getElementType()) ? 63 : 0;
        }
        if (aSTNode == getFirstChildNode()) {
            return 61;
        }
        return aSTNode == getLastChildNode() ? 62 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitAssignmentExpression(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiAssignmentExpression:" + getText();
    }
}
