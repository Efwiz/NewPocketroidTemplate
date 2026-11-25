package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiContinueStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiLabeledStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiStatement;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiLabelReference;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class PsiContinueStatementImpl extends CompositePsiElement implements PsiContinueStatement {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiContinueStatementImpl.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "child";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiContinueStatementImpl";
        if (i != 1) {
            objArr[2] = "getChildRole";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public PsiContinueStatementImpl() {
        super(JavaElementType.CONTINUE_STATEMENT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiContinueStatement
    public PsiIdentifier getLabelIdentifier() {
        return (PsiIdentifier) findPsiChildByType(JavaTokenType.IDENTIFIER);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiContinueStatement
    public PsiStatement findContinuedStatement() {
        PsiIdentifier labelIdentifier = getLabelIdentifier();
        if (labelIdentifier != null) {
            PsiLabeledStatement psiLabeledStatementFindEnclosingLabeledStatement = PsiImplUtil.findEnclosingLabeledStatement(this, labelIdentifier.getText());
            if (psiLabeledStatementFindEnclosingLabeledStatement != null) {
                return psiLabeledStatementFindEnclosingLabeledStatement.getStatement();
            }
            return null;
        }
        return PsiImplUtil.findEnclosingLoop(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 22) {
            return TreeUtil.findChildBackward(this, JavaTokenType.SEMICOLON);
        }
        if (i == 82) {
            return findChildByType(JavaTokenType.CONTINUE_KEYWORD);
        }
        if (i != 83) {
            return null;
        }
        return findChildByType(JavaTokenType.IDENTIFIER);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JavaTokenType.CONTINUE_KEYWORD) {
            return 82;
        }
        if (elementType == JavaTokenType.IDENTIFIER) {
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
            ((JavaElementVisitor) psiElementVisitor).visitContinueStatement(this);
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
        return "PsiContinueStatement";
    }
}
