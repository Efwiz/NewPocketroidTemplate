package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiForeachStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiStatement;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public class PsiForeachStatementImpl extends PsiLoopStatementImpl implements PsiForeachStatement, Constants {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiForeachStatementImpl.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5 || i == 6) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5 || i == 6) ? 3 : 2];
        if (i == 2) {
            objArr[0] = "child";
        } else if (i == 3) {
            objArr[0] = "processor";
        } else if (i == 4) {
            objArr[0] = "state";
        } else if (i == 5) {
            objArr[0] = "place";
        } else if (i != 6) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiForeachStatementImpl";
        } else {
            objArr[0] = "visitor";
        }
        switch (i) {
            case 1:
                objArr[1] = "getLParenth";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiForeachStatementImpl";
                break;
            default:
                objArr[1] = "getIterationParameter";
                break;
        }
        if (i == 2) {
            objArr[2] = "getChildRole";
        } else if (i == 3 || i == 4 || i == 5) {
            objArr[2] = "processDeclarations";
        } else if (i == 6) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5 && i != 6) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiLoopStatementImpl, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public /* bridge */ /* synthetic */ void deleteChildInternal(ASTNode aSTNode) {
        super.deleteChildInternal(aSTNode);
    }

    public PsiForeachStatementImpl() {
        super(FOREACH_STATEMENT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiForeachStatement
    public PsiParameter getIterationParameter() {
        PsiParameter psiParameter = (PsiParameter) findChildByRoleAsPsiElement(125);
        if (psiParameter == null) {
            $$$reportNull$$$0(0);
        }
        return psiParameter;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiForeachStatement
    public PsiExpression getIteratedValue() {
        return (PsiExpression) findChildByRoleAsPsiElement(124);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLoopStatement
    public PsiStatement getBody() {
        return (PsiStatement) findChildByRoleAsPsiElement(38);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiForeachStatement
    public PsiJavaToken getLParenth() {
        PsiJavaToken psiJavaToken = (PsiJavaToken) findChildByRoleAsPsiElement(24);
        if (psiJavaToken == null) {
            $$$reportNull$$$0(1);
        }
        return psiJavaToken;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiForeachStatement
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
        if (i == 37) {
            return getFirstChildNode();
        }
        if (i == 38) {
            return PsiImplUtil.findStatementChild(this);
        }
        if (i == 87) {
            return findChildByType(COLON);
        }
        if (i == 124) {
            return findChildByType(EXPRESSION_BIT_SET);
        }
        if (i != 125) {
            return null;
        }
        return findChildByType(PARAMETER);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
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
        if (elementType == PARAMETER) {
            return 125;
        }
        if (elementType == COLON) {
            return 87;
        }
        if (EXPRESSION_BIT_SET.contains(aSTNode.getElementType())) {
            return 124;
        }
        return aSTNode.getPsi() instanceof PsiStatement ? 38 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiForeachStatement";
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
        if (psiElement == null || psiElement.getLightParent() != this || psiElement == getIteratedValue()) {
            return true;
        }
        return psiScopeProcessor.execute(getIterationParameter(), resolveState);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(6);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitForeachStatement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }
}
