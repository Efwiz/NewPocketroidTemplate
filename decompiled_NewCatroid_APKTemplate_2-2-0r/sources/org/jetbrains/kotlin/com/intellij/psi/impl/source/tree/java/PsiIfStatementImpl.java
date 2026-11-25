package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiIfStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiPatternVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiStatement;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.AnalysisCanceledException;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlow;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowFactory;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowOptions;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.LocalsControlFlowPolicy;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.NameHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.PatternResolveState;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiIfStatementImpl extends CompositePsiElement implements PsiIfStatement, Constants {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiIfStatementImpl.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 2) {
            objArr[0] = "statement";
        } else if (i == 4) {
            objArr[0] = "visitor";
        } else if (i == 5) {
            objArr[0] = "processor";
        } else if (i == 6) {
            objArr[0] = "state";
        } else if (i != 7) {
            objArr[0] = "child";
        } else {
            objArr[0] = "place";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiIfStatementImpl";
        switch (i) {
            case 1:
                objArr[2] = "setElseBranch";
                break;
            case 2:
                objArr[2] = "setThenBranch";
                break;
            case 3:
                objArr[2] = "getChildRole";
                break;
            case 4:
                objArr[2] = "accept";
                break;
            case 5:
            case 6:
            case 7:
                objArr[2] = "processDeclarations";
                break;
            default:
                objArr[2] = "deleteChildInternal";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public PsiIfStatementImpl() {
        super(IF_STATEMENT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiIfStatement
    public PsiExpression getCondition() {
        return (PsiExpression) findChildByRoleAsPsiElement(32);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        ASTNode aSTNodeFindChildByRole;
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        if (aSTNode == getThenBranch()) {
            replaceChildInternal(aSTNode, (TreeElement) JavaPsiFacade.getElementFactory(getProject()).createStatementFromText("{}", null));
            return;
        }
        if (aSTNode == getElseBranch() && (aSTNodeFindChildByRole = findChildByRole(31)) != null) {
            super.deleteChildInternal(aSTNodeFindChildByRole);
        }
        super.deleteChildInternal(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiIfStatement
    public PsiStatement getThenBranch() {
        return (PsiStatement) findChildByRoleAsPsiElement(33);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiIfStatement
    public PsiStatement getElseBranch() {
        return (PsiStatement) findChildByRoleAsPsiElement(34);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiIfStatement
    public PsiJavaToken getLParenth() {
        return (PsiJavaToken) findChildByRoleAsPsiElement(24);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiIfStatement
    public PsiJavaToken getRParenth() {
        return (PsiJavaToken) findChildByRoleAsPsiElement(25);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiIfStatement
    public PsiKeyword getElseElement() {
        return (PsiKeyword) findChildByRoleAsPsiElement(31);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiIfStatement
    public void setElseBranch(PsiStatement psiStatement) throws IncorrectOperationException {
        if (psiStatement == null) {
            $$$reportNull$$$0(1);
        }
        PsiStatement elseBranch = getElseBranch();
        if (elseBranch != null) {
            elseBranch.delete();
        }
        PsiKeyword elseElement = getElseElement();
        if (elseElement != null) {
            elseElement.delete();
        }
        PsiIfStatement psiIfStatement = (PsiIfStatement) JavaPsiFacade.getElementFactory(getProject()).createStatementFromText("if (true) {} else {}", this);
        psiIfStatement.getElseBranch().replace(psiStatement);
        addRange(psiIfStatement.getElseElement(), psiIfStatement.getLastChild());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiIfStatement
    public void setThenBranch(PsiStatement psiStatement) throws IncorrectOperationException {
        if (psiStatement == null) {
            $$$reportNull$$$0(2);
        }
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(getProject());
        ASTNode aSTNodeFindChildByRole = findChildByRole(30);
        LOG.assertTrue(aSTNodeFindChildByRole != null);
        PsiIfStatement psiIfStatement = (PsiIfStatement) elementFactory.createStatementFromText("if (){}", this);
        if (getLParenth() == null) {
            addAfter(psiIfStatement.getLParenth(), aSTNodeFindChildByRole.getPsi());
        }
        if (getRParenth() == null) {
            addAfter(psiIfStatement.getRParenth(), getCondition() == null ? getLParenth() : getCondition());
        }
        PsiStatement thenBranch = getThenBranch();
        if (thenBranch == null) {
            addAfter(psiStatement, getRParenth());
        } else {
            thenBranch.replace(psiStatement);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        if (i == 24) {
            return findChildByType(LPARENTH);
        }
        if (i != 25) {
            switch (i) {
                case 30:
                    return findChildByType(IF_KEYWORD);
                case 31:
                    return findChildByType(ELSE_KEYWORD);
                case 32:
                    return findChildByType(EXPRESSION_BIT_SET);
                case 33:
                    return PsiImplUtil.findStatementChild(this);
                case 34:
                    ASTNode aSTNodeFindChildByRole = findChildByRole(31);
                    if (aSTNodeFindChildByRole == null) {
                        return null;
                    }
                    do {
                        aSTNodeFindChildByRole = aSTNodeFindChildByRole.getTreeNext();
                        if (aSTNodeFindChildByRole == null) {
                            return null;
                        }
                    } while (!(aSTNodeFindChildByRole.getPsi() instanceof PsiStatement));
                    return aSTNodeFindChildByRole;
                default:
                    return null;
            }
        }
        return findChildByType(RPARENTH);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(3);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == IF_KEYWORD) {
            return 30;
        }
        if (elementType == ELSE_KEYWORD) {
            return 31;
        }
        if (elementType == LPARENTH) {
            return 24;
        }
        if (elementType == RPARENTH) {
            return 25;
        }
        if (ElementType.EXPRESSION_BIT_SET.contains(aSTNode.getElementType())) {
            return 32;
        }
        if (aSTNode.getPsi() instanceof PsiStatement) {
            return findChildByRoleAsPsiElement(33) == aSTNode ? 33 : 34;
        }
        return 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitIfStatement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(final PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        final PsiExpression condition;
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(5);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(6);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(7);
        }
        ElementClassHint elementClassHint = (ElementClassHint) psiScopeProcessor.getHint(ElementClassHint.KEY);
        if ((elementClassHint == null || elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.VARIABLE)) && (condition = getCondition()) != null) {
            final PsiStatement thenBranch = getThenBranch();
            final PsiStatement elseBranch = getElseBranch();
            if (psiElement == null) {
                if (resolveState.get(PatternResolveState.KEY) == PatternResolveState.WHEN_NONE) {
                    return true;
                }
                if (resolveState.get(PatternResolveState.KEY) != PatternResolveState.WHEN_BOTH) {
                    psiScopeProcessor = new PsiScopeProcessor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiIfStatementImpl$$ExternalSyntheticLambda0
                        @Override // org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor
                        public final boolean execute(PsiElement psiElement3, ResolveState resolveState2) {
                            return this.f$0.m6680x783b963f(psiScopeProcessor, thenBranch, elseBranch, condition, psiElement3, resolveState2);
                        }
                    };
                }
                return condition.processDeclarations(psiScopeProcessor, PatternResolveState.WHEN_BOTH.putInto(resolveState), null, psiElement2);
            }
            if (psiElement == thenBranch) {
                return condition.processDeclarations(psiScopeProcessor, PatternResolveState.WHEN_TRUE.putInto(resolveState), null, psiElement2);
            }
            if (psiElement == elseBranch) {
                return condition.processDeclarations(psiScopeProcessor, PatternResolveState.WHEN_FALSE.putInto(resolveState), null, psiElement2);
            }
        }
        return true;
    }

    /* JADX INFO: renamed from: lambda$processDeclarations$0$org-jetbrains-kotlin-com-intellij-psi-impl-source-tree-java-PsiIfStatementImpl, reason: not valid java name */
    /* synthetic */ boolean m6680x783b963f(PsiScopeProcessor psiScopeProcessor, PsiStatement psiStatement, PsiStatement psiStatement2, PsiExpression psiExpression, PsiElement psiElement, ResolveState resolveState) {
        LOG.assertTrue(psiElement instanceof PsiPatternVariable);
        NameHint nameHint = (NameHint) psiScopeProcessor.getHint(NameHint.KEY);
        if (nameHint != null && !((PsiPatternVariable) psiElement).get$name().equals(nameHint.getName(resolveState))) {
            return true;
        }
        try {
            ControlFlow controlFlow = ControlFlowFactory.getControlFlow(this, new LocalsControlFlowPolicy(this), ControlFlowOptions.NO_CONST_EVALUATE);
            boolean zCanCompleteNormally = canCompleteNormally(psiStatement, controlFlow);
            if (zCanCompleteNormally != canCompleteNormally(psiStatement2, controlFlow) && PatternResolveState.fromBoolean(zCanCompleteNormally) == PatternResolveState.stateAtParent((PsiPatternVariable) psiElement, psiExpression)) {
                return psiScopeProcessor.execute(psiElement, resolveState);
            }
        } catch (AnalysisCanceledException unused) {
        }
        return true;
    }

    private static boolean canCompleteNormally(PsiStatement psiStatement, ControlFlow controlFlow) {
        if (psiStatement == null) {
            return true;
        }
        int startOffset = controlFlow.getStartOffset(psiStatement);
        int endOffset = controlFlow.getEndOffset(psiStatement);
        return (startOffset == -1 || endOffset == -1 || !ControlFlowUtil.canCompleteNormally(controlFlow, startOffset, endOffset)) ? false : true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiIfStatement";
    }
}
