package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiBlockStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;

/* JADX INFO: loaded from: classes3.dex */
public class PsiBlockStatementImpl extends CompositePsiElement implements PsiBlockStatement {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiBlockStatementImpl.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 3 : 2];
        if (i == 1) {
            objArr[0] = "child";
        } else if (i != 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiBlockStatementImpl";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 1 || i == 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiBlockStatementImpl";
        } else {
            objArr[1] = "getCodeBlock";
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

    public PsiBlockStatementImpl() {
        super(JavaElementType.BLOCK_STATEMENT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiBlockStatement
    public PsiCodeBlock getCodeBlock() {
        PsiCodeBlock psiCodeBlock = (PsiCodeBlock) findChildByRoleAsPsiElement(60);
        if (psiCodeBlock == null) {
            $$$reportNull$$$0(0);
        }
        return psiCodeBlock;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 60) {
            return findChildByType(JavaElementType.CODE_BLOCK);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        return aSTNode.getElementType() == JavaElementType.CODE_BLOCK ? 60 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitBlockStatement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiBlockStatement";
    }
}
