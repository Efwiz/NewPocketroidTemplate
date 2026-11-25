package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiBreakStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiConditionalLoopStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiPatternVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhileStatement;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.NameHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.PatternResolveState;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiWhileStatementImpl extends PsiLoopStatementImpl implements PsiWhileStatement, Constants {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiWhileStatementImpl.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "visitor";
                break;
            case 2:
            case 5:
                objArr[0] = "processor";
                break;
            case 3:
            case 6:
                objArr[0] = "state";
                break;
            case 4:
            case 7:
                objArr[0] = "place";
                break;
            case 8:
                objArr[0] = "loop";
                break;
            default:
                objArr[0] = "child";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiWhileStatementImpl";
        switch (i) {
            case 1:
                objArr[2] = "accept";
                break;
            case 2:
            case 3:
            case 4:
                objArr[2] = "processDeclarations";
                break;
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[2] = "processDeclarationsInLoopCondition";
                break;
            default:
                objArr[2] = "getChildRole";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiLoopStatementImpl, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public /* bridge */ /* synthetic */ void deleteChildInternal(ASTNode aSTNode) {
        super.deleteChildInternal(aSTNode);
    }

    public PsiWhileStatementImpl() {
        super(WHILE_STATEMENT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiConditionalLoopStatement
    public PsiExpression getCondition() {
        return (PsiExpression) findChildByRoleAsPsiElement(32);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiLoopStatement
    public PsiStatement getBody() {
        return (PsiStatement) findChildByRoleAsPsiElement(38);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiWhileStatement
    public PsiJavaToken getLParenth() {
        return (PsiJavaToken) findChildByRoleAsPsiElement(24);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiWhileStatement
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
        if (i == 32) {
            return findChildByType(EXPRESSION_BIT_SET);
        }
        if (i == 35) {
            return findChildByType(WHILE_KEYWORD);
        }
        if (i != 38) {
            return null;
        }
        return PsiImplUtil.findStatementChild(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == WHILE_KEYWORD) {
            return 35;
        }
        if (elementType == LPARENTH) {
            return 24;
        }
        if (elementType == RPARENTH) {
            return 25;
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
            ((JavaElementVisitor) psiElementVisitor).visitWhileStatement(this);
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
        ElementClassHint elementClassHint = (ElementClassHint) psiScopeProcessor.getHint(ElementClassHint.KEY);
        if (elementClassHint != null && !elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.VARIABLE)) {
            return true;
        }
        if (psiElement == null) {
            return processDeclarationsInLoopCondition(psiScopeProcessor, resolveState, psiElement2, this);
        }
        PsiExpression condition = getCondition();
        if (condition == null || psiElement != getBody()) {
            return true;
        }
        return condition.processDeclarations(psiScopeProcessor, PatternResolveState.WHEN_TRUE.putInto(resolveState), null, psiElement2);
    }

    static boolean processDeclarationsInLoopCondition(final PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, final PsiConditionalLoopStatement psiConditionalLoopStatement) {
        final PsiExpression condition;
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(5);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(6);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(7);
        }
        if (psiConditionalLoopStatement == null) {
            $$$reportNull$$$0(8);
        }
        if (resolveState.get(PatternResolveState.KEY) == PatternResolveState.WHEN_NONE || (condition = psiConditionalLoopStatement.getCondition()) == null) {
            return true;
        }
        return condition.processDeclarations(new PsiScopeProcessor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiWhileStatementImpl$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
            public final boolean execute(PsiElement psiElement2, ResolveState resolveState2) {
                return PsiWhileStatementImpl.lambda$processDeclarationsInLoopCondition$1(psiScopeProcessor, condition, psiConditionalLoopStatement, psiElement2, resolveState2);
            }
        }, PatternResolveState.WHEN_BOTH.putInto(resolveState), null, psiElement);
    }

    static /* synthetic */ boolean lambda$processDeclarationsInLoopCondition$1(PsiScopeProcessor psiScopeProcessor, PsiExpression psiExpression, final PsiConditionalLoopStatement psiConditionalLoopStatement, PsiElement psiElement, ResolveState resolveState) {
        NameHint nameHint = (NameHint) psiScopeProcessor.getHint(NameHint.KEY);
        if ((nameHint == null || ((PsiPatternVariable) psiElement).getName().equals(nameHint.getName(resolveState))) && PatternResolveState.stateAtParent((PsiPatternVariable) psiElement, psiExpression) != PatternResolveState.WHEN_TRUE && PsiTreeUtil.processElements(psiConditionalLoopStatement, (PsiElementProcessor<? super PsiElement>) new PsiElementProcessor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiWhileStatementImpl$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor
            public final boolean execute(PsiElement psiElement2) {
                return PsiWhileStatementImpl.lambda$processDeclarationsInLoopCondition$0(psiConditionalLoopStatement, psiElement2);
            }
        })) {
            return psiScopeProcessor.execute(psiElement, resolveState);
        }
        return true;
    }

    static /* synthetic */ boolean lambda$processDeclarationsInLoopCondition$0(PsiConditionalLoopStatement psiConditionalLoopStatement, PsiElement psiElement) {
        return ((psiElement instanceof PsiBreakStatement) && ((PsiBreakStatement) psiElement).findExitedStatement() == psiConditionalLoopStatement) ? false : true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiWhileStatement";
    }
}
