package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionStatement;
import org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiExpressionStatementImpl extends CompositePsiElement implements PsiExpressionStatement {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiExpressionStatementImpl.class);

    /* JADX WARN: Code duplicated, block: B:26:0x003d  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5 || i == 6) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5 || i == 6) ? 3 : 2];
        if (i == 2) {
            objArr[0] = "child";
        } else if (i == 3) {
            objArr[0] = "visitor";
        } else if (i == 4 || i == 5) {
            objArr[0] = "child";
        } else if (i != 6) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiExpressionStatementImpl";
        } else {
            objArr[0] = "newElement";
        }
        if (i == 2 || i == 3 || i == 4 || i == 5 || i == 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiExpressionStatementImpl";
        } else {
            objArr[1] = "getExpression";
        }
        if (i == 2) {
            objArr[2] = "getChildRole";
        } else if (i == 3) {
            objArr[2] = "accept";
        } else if (i == 4) {
            objArr[2] = "deleteChildInternal";
        } else if (i == 5 || i == 6) {
            objArr[2] = "replaceChildInternal";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5 && i != 6) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public PsiExpressionStatementImpl() {
        super(JavaElementType.EXPRESSION_STATEMENT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiExpressionStatement
    public PsiExpression getExpression() {
        PsiExpression psiExpression = (PsiExpression) SourceTreeToPsiMap.treeElementToPsi(findChildByType(ElementType.EXPRESSION_BIT_SET));
        if (psiExpression != null) {
            if (psiExpression == null) {
                $$$reportNull$$$0(0);
            }
            return psiExpression;
        }
        LOG.error("Illegal PSI: \n" + DebugUtil.psiToString(getLightParent(), true));
        $$$reportNull$$$0(1);
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 22) {
            return TreeUtil.findChildBackward(this, JavaTokenType.SEMICOLON);
        }
        if (i != 64) {
            return null;
        }
        return findChildByType(ElementType.EXPRESSION_BIT_SET);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        if (aSTNode.getElementType() == JavaTokenType.SEMICOLON) {
            return 22;
        }
        return ElementType.EXPRESSION_BIT_SET.contains(aSTNode.getElementType()) ? 64 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(3);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitExpressionStatement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiExpressionStatement";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(4);
        }
        if (getChildRole(aSTNode) == 64) {
            getTreeParent().deleteChildInternal(this);
        } else {
            super.deleteChildInternal(aSTNode);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void replaceChildInternal(ASTNode aSTNode, TreeElement treeElement) {
        if (aSTNode == null) {
            $$$reportNull$$$0(5);
        }
        if (treeElement == null) {
            $$$reportNull$$$0(6);
        }
        if (ElementType.EXPRESSION_BIT_SET.contains(aSTNode.getElementType()) && !ElementType.EXPRESSION_BIT_SET.contains(treeElement.getElementType())) {
            throw new IncorrectOperationException("Expression expected; got: " + treeElement.getElementType());
        }
        super.replaceChildInternal(aSTNode, treeElement);
    }
}
