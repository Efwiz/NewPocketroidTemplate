package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiForStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiStatement;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.PatternResolveState;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class PsiForStatementImpl extends PsiLoopStatementImpl implements PsiForStatement, Constants {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiForStatementImpl.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "visitor";
        } else if (i == 3) {
            objArr[0] = "processor";
        } else if (i == 4) {
            objArr[0] = "state";
        } else if (i != 5) {
            objArr[0] = "child";
        } else {
            objArr[0] = "place";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiForStatementImpl";
        if (i == 1) {
            objArr[2] = "accept";
        } else if (i == 2) {
            objArr[2] = "deleteChildInternal";
        } else if (i == 3 || i == 4 || i == 5) {
            objArr[2] = "processDeclarations";
        } else {
            objArr[2] = "getChildRole";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public PsiForStatementImpl() {
        super(FOR_STATEMENT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiForStatement
    public PsiStatement getInitialization() {
        return (PsiStatement) findChildByRoleAsPsiElement(39);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiConditionalLoopStatement
    public PsiExpression getCondition() {
        return (PsiExpression) findChildByRoleAsPsiElement(32);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiForStatement
    public PsiStatement getUpdate() {
        return (PsiStatement) findChildByRoleAsPsiElement(40);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLoopStatement
    public PsiStatement getBody() {
        return (PsiStatement) findChildByRoleAsPsiElement(38);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiForStatement
    public PsiJavaToken getLParenth() {
        return (PsiJavaToken) findChildByRoleAsPsiElement(24);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiForStatement
    public PsiJavaToken getRParenth() {
        return (PsiJavaToken) findChildByRoleAsPsiElement(25);
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
        if (i != 32) {
            switch (i) {
                case 37:
                    return findChildByType(FOR_KEYWORD);
                case 38:
                    for (ASTNode aSTNodeFindChildByRole = findChildByRole(25); aSTNodeFindChildByRole != null; aSTNodeFindChildByRole = aSTNodeFindChildByRole.getTreeNext()) {
                        if (aSTNodeFindChildByRole.getPsi() instanceof PsiStatement) {
                            return aSTNodeFindChildByRole;
                        }
                    }
                    return null;
                case 39:
                    ASTNode aSTNodeFindStatementChild = PsiImplUtil.findStatementChild(this);
                    for (ASTNode aSTNodeFindChildByRole2 = findChildByRole(24); aSTNodeFindChildByRole2 != null; aSTNodeFindChildByRole2 = aSTNodeFindChildByRole2.getTreeNext()) {
                        if (aSTNodeFindChildByRole2 == aSTNodeFindStatementChild) {
                            return aSTNodeFindStatementChild;
                        }
                        if (aSTNodeFindChildByRole2.getElementType() == RPARENTH) {
                            return null;
                        }
                    }
                    return null;
                case 40:
                    for (ASTNode aSTNodeFindChildByRole3 = findChildByRole(41); aSTNodeFindChildByRole3 != null; aSTNodeFindChildByRole3 = aSTNodeFindChildByRole3.getTreeNext()) {
                        if (aSTNodeFindChildByRole3.getPsi() instanceof PsiStatement) {
                            return aSTNodeFindChildByRole3;
                        }
                        if (aSTNodeFindChildByRole3.getElementType() == RPARENTH) {
                            return null;
                        }
                    }
                    return null;
                case 41:
                    return findChildByType(SEMICOLON);
                default:
                    return null;
            }
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
        if (elementType == FOR_KEYWORD) {
            return 37;
        }
        if (elementType == LPARENTH) {
            return 24;
        }
        if (elementType == RPARENTH) {
            return 25;
        }
        if (elementType == SEMICOLON) {
            return 41;
        }
        if (EXPRESSION_BIT_SET.contains(aSTNode.getElementType())) {
            return 32;
        }
        if (!(aSTNode.getPsi() instanceof PsiStatement)) {
            return 0;
        }
        int childRole = getChildRole(aSTNode, 39);
        if (childRole != 0) {
            return childRole;
        }
        int childRole2 = getChildRole(aSTNode, 40);
        if (childRole2 != 0) {
            return childRole2;
        }
        return 38;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitForStatement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiForStatement";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiLoopStatementImpl, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        if (getChildRole(aSTNode) == 39) {
            replaceChildInternal(aSTNode, (TreeElement) JavaPsiFacade.getElementFactory(getProject()).createStatementFromText(";", null));
        } else {
            super.deleteChildInternal(aSTNode);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(3);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(5);
        }
        psiScopeProcessor.handleEvent(PsiScopeProcessor.Event.SET_DECLARATION_HOLDER, this);
        ElementClassHint elementClassHint = (ElementClassHint) psiScopeProcessor.getHint(ElementClassHint.KEY);
        if (elementClassHint != null && !elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.VARIABLE)) {
            return true;
        }
        if (psiElement == null) {
            return PsiWhileStatementImpl.processDeclarationsInLoopCondition(psiScopeProcessor, resolveState, psiElement2, this);
        }
        if (psiElement.getLightParent() != this) {
            return true;
        }
        PsiStatement initialization = getInitialization();
        if (initialization != null && psiElement != initialization && !initialization.processDeclarations(psiScopeProcessor, resolveState, null, psiElement2)) {
            return false;
        }
        PsiExpression condition = getCondition();
        if (condition == null || !(psiElement == getBody() || psiElement == getUpdate())) {
            return true;
        }
        return condition.processDeclarations(psiScopeProcessor, PatternResolveState.WHEN_TRUE.putInto(resolveState), null, psiElement2);
    }
}
