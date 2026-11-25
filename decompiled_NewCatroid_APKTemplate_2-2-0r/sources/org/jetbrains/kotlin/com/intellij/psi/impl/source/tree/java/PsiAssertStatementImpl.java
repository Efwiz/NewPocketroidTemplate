package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiAssertStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class PsiAssertStatementImpl extends CompositePsiElement implements PsiAssertStatement, Constants {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiAssertStatementImpl.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "child";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiAssertStatementImpl";
        if (i != 1) {
            objArr[2] = "getChildRole";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public PsiAssertStatementImpl() {
        super(ASSERT_STATEMENT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAssertStatement
    public PsiExpression getAssertCondition() {
        return (PsiExpression) findChildByRoleAsPsiElement(32);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAssertStatement
    public PsiExpression getAssertDescription() {
        return (PsiExpression) findChildByRoleAsPsiElement(117);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        ASTNode aSTNodeFindChildByRole;
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 22) {
            return findChildByType(SEMICOLON);
        }
        if (i == 32) {
            return findChildByType(EXPRESSION_BIT_SET);
        }
        if (i == 87) {
            return findChildByType(COLON);
        }
        if (i == 116) {
            return findChildByType(ASSERT_KEYWORD);
        }
        if (i != 117 || (aSTNodeFindChildByRole = findChildByRole(87)) == null) {
            return null;
        }
        do {
            aSTNodeFindChildByRole = aSTNodeFindChildByRole.getTreeNext();
            if (aSTNodeFindChildByRole == null) {
                break;
            }
        } while (!EXPRESSION_BIT_SET.contains(aSTNodeFindChildByRole.getElementType()));
        return aSTNodeFindChildByRole;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == ASSERT_KEYWORD) {
            return 116;
        }
        if (elementType == COLON) {
            return 87;
        }
        if (elementType == SEMICOLON) {
            return 22;
        }
        if (!EXPRESSION_BIT_SET.contains(aSTNode.getElementType())) {
            return 0;
        }
        int childRole = getChildRole(aSTNode, 32);
        if (childRole != 0) {
            return childRole;
        }
        return 117;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitAssertStatement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiAssertStatement";
    }
}
