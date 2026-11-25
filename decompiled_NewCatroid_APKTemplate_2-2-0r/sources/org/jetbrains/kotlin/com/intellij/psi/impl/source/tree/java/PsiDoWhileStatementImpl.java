package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiDoWhileStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiStatement;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class PsiDoWhileStatementImpl extends PsiLoopStatementImpl implements PsiDoWhileStatement, Constants {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiDoWhileStatementImpl.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "visitor";
        } else if (i == 2) {
            objArr[0] = "processor";
        } else if (i == 3) {
            objArr[0] = "state";
        } else if (i != 4) {
            objArr[0] = "child";
        } else {
            objArr[0] = "place";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiDoWhileStatementImpl";
        if (i == 1) {
            objArr[2] = "accept";
        } else if (i == 2 || i == 3 || i == 4) {
            objArr[2] = "processDeclarations";
        } else {
            objArr[2] = "getChildRole";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiLoopStatementImpl, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public /* bridge */ /* synthetic */ void deleteChildInternal(ASTNode aSTNode) {
        super.deleteChildInternal(aSTNode);
    }

    public PsiDoWhileStatementImpl() {
        super(DO_WHILE_STATEMENT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiConditionalLoopStatement
    public PsiExpression getCondition() {
        return (PsiExpression) findChildByRoleAsPsiElement(32);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLoopStatement
    public PsiStatement getBody() {
        return (PsiStatement) findChildByRoleAsPsiElement(38);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDoWhileStatement
    public PsiKeyword getWhileKeyword() {
        return (PsiKeyword) findChildByRoleAsPsiElement(35);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDoWhileStatement
    public PsiJavaToken getLParenth() {
        return (PsiJavaToken) findChildByRoleAsPsiElement(24);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDoWhileStatement
    public PsiJavaToken getRParenth() {
        return (PsiJavaToken) findChildByRoleAsPsiElement(25);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 22) {
            return TreeUtil.findChildBackward(this, SEMICOLON);
        }
        if (i == 32) {
            return findChildByType(EXPRESSION_BIT_SET);
        }
        if (i == 38) {
            return PsiImplUtil.findStatementChild(this);
        }
        if (i == 24) {
            return findChildByType(LPARENTH);
        }
        if (i == 25) {
            return findChildByType(RPARENTH);
        }
        if (i == 35) {
            return findChildByType(WHILE_KEYWORD);
        }
        if (i != 36) {
            return null;
        }
        return findChildByType(DO_KEYWORD);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == DO_KEYWORD) {
            return 36;
        }
        if (elementType == WHILE_KEYWORD) {
            return 35;
        }
        if (elementType == LPARENTH) {
            return 24;
        }
        if (elementType == RPARENTH) {
            return 25;
        }
        if (elementType == SEMICOLON) {
            return 22;
        }
        if (EXPRESSION_BIT_SET.contains(aSTNode.getElementType())) {
            return 32;
        }
        return aSTNode.getPsi() instanceof PsiStatement ? 38 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitDoWhileStatement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(2);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(3);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElement != null) {
            return true;
        }
        ElementClassHint elementClassHint = (ElementClassHint) psiScopeProcessor.getHint(ElementClassHint.KEY);
        if (elementClassHint == null || elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.VARIABLE)) {
            return PsiWhileStatementImpl.processDeclarationsInLoopCondition(psiScopeProcessor, resolveState, psiElement2, this);
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiDoWhileStatement";
    }
}
