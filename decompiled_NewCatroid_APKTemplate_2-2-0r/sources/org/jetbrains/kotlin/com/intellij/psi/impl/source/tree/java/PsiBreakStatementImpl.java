package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiBreakStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiLabeledStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiStatement;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiLabelReference;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class PsiBreakStatementImpl extends CompositePsiElement implements PsiBreakStatement {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiBreakStatementImpl.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "child";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiBreakStatementImpl";
        if (i != 1) {
            objArr[2] = "getChildRole";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public PsiBreakStatementImpl() {
        super(JavaElementType.BREAK_STATEMENT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiBreakStatement
    public PsiIdentifier getLabelIdentifier() {
        return (PsiIdentifier) findPsiChildByType(JavaTokenType.IDENTIFIER);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiBreakStatement
    public PsiStatement findExitedStatement() {
        PsiIdentifier labelIdentifier = getLabelIdentifier();
        if (labelIdentifier != null) {
            PsiLabeledStatement psiLabeledStatementFindEnclosingLabeledStatement = PsiImplUtil.findEnclosingLabeledStatement(this, labelIdentifier.getText());
            if (psiLabeledStatementFindEnclosingLabeledStatement != null) {
                return psiLabeledStatementFindEnclosingLabeledStatement.getStatement();
            }
            return null;
        }
        return PsiImplUtil.findEnclosingSwitchOrLoop(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 22) {
            return TreeUtil.findChildBackward(this, JavaTokenType.SEMICOLON);
        }
        if (i == 81) {
            return findChildByType(JavaTokenType.BREAK_KEYWORD);
        }
        if (i != 83) {
            return null;
        }
        return findChildByType(ElementType.EXPRESSION_BIT_SET);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JavaTokenType.BREAK_KEYWORD) {
            return 81;
        }
        if (ElementType.EXPRESSION_BIT_SET.contains(elementType)) {
            return 83;
        }
        return elementType == JavaTokenType.SEMICOLON ? 22 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitBreakStatement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference getReference() {
        PsiIdentifier labelIdentifier = getLabelIdentifier();
        if (labelIdentifier != null) {
            return new PsiLabelReference(this, labelIdentifier);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiBreakStatement";
    }
}
