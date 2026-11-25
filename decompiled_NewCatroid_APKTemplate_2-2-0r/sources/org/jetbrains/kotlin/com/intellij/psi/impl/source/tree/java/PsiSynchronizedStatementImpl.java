package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiSynchronizedStatement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class PsiSynchronizedStatementImpl extends CompositePsiElement implements PsiSynchronizedStatement, Constants {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiSynchronizedStatementImpl.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "child";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiSynchronizedStatementImpl";
        if (i != 1) {
            objArr[2] = "getChildRole";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public PsiSynchronizedStatementImpl() {
        super(SYNCHRONIZED_STATEMENT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSynchronizedStatement
    public PsiExpression getLockExpression() {
        return (PsiExpression) findChildByRoleAsPsiElement(80);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSynchronizedStatement
    public PsiCodeBlock getBody() {
        return (PsiCodeBlock) findChildByRoleAsPsiElement(60);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 24) {
            return findChildByType(LPARENTH);
        }
        if (i == 25) {
            return findChildByType(RPARENTH);
        }
        if (i == 60) {
            return findChildByType(CODE_BLOCK);
        }
        if (i == 79) {
            return findChildByType(SYNCHRONIZED_KEYWORD);
        }
        if (i != 80) {
            return null;
        }
        return findChildByType(EXPRESSION_BIT_SET);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == SYNCHRONIZED_KEYWORD) {
            return 79;
        }
        if (elementType == LPARENTH) {
            return 24;
        }
        if (elementType == RPARENTH) {
            return 25;
        }
        if (elementType == CODE_BLOCK) {
            return 60;
        }
        return EXPRESSION_BIT_SET.contains(aSTNode.getElementType()) ? 80 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitSynchronizedStatement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiSynchronizedStatement";
    }
}
