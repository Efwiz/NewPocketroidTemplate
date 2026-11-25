package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import java.util.concurrent.atomic.AtomicBoolean;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCaseLabelElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiCaseLabelElementList;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchLabelStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchLabelStatementBase;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.AnalysisCanceledException;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlow;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowFactory;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowOptions;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.LocalsControlFlowPolicy;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChildRole;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.PairProcessor;

/* JADX INFO: loaded from: classes3.dex */
public class PsiSwitchLabelStatementImpl extends PsiSwitchLabelStatementBaseImpl implements PsiSwitchLabelStatement {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiSwitchLabelStatementImpl.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "visitor";
                break;
            case 2:
                objArr[0] = "processor";
                break;
            case 3:
                objArr[0] = "state";
                break;
            case 4:
            case 5:
                objArr[0] = "place";
                break;
            case 6:
                objArr[0] = "immediateSwitchLabel";
                break;
            case 7:
                objArr[0] = "switchCaseLabel";
                break;
            default:
                objArr[0] = "child";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiSwitchLabelStatementImpl";
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
                objArr[2] = "shouldAnalyzePatternVariablesInCaseLabel";
                break;
            case 6:
                objArr[2] = "isFallthrough";
                break;
            case 7:
                objArr[2] = "isCaseNull";
                break;
            default:
                objArr[2] = "getChildRole";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public PsiSwitchLabelStatementImpl() {
        super(JavaElementType.SWITCH_LABEL_STATEMENT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public ASTNode findChildByRole(int i) {
        LOG.assertTrue(ChildRole.isUnique(i));
        switch (i) {
            case 84:
                return findChildByType(JavaTokenType.CASE_KEYWORD);
            case 85:
                return findChildByType(JavaTokenType.DEFAULT_KEYWORD);
            case 86:
                return findChildByType(ElementType.EXPRESSION_BIT_SET);
            case 87:
                return findChildByType(JavaTokenType.COLON);
            default:
                return null;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
        LOG.assertTrue(aSTNode.getTreeParent() == this);
        IElementType elementType = aSTNode.getElementType();
        if (elementType == JavaTokenType.CASE_KEYWORD) {
            return 84;
        }
        if (elementType == JavaTokenType.DEFAULT_KEYWORD) {
            return 85;
        }
        if (elementType == JavaTokenType.COLON) {
            return 87;
        }
        return ElementType.EXPRESSION_BIT_SET.contains(aSTNode.getElementType()) ? 86 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitSwitchLabelStatement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiSwitchLabelStatement";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiSwitchLabelStatementBaseImpl, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        PsiCaseLabelElementList caseLabelElementList;
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(2);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(3);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(4);
        }
        if (!super.processDeclarations(psiScopeProcessor, resolveState, psiElement, psiElement2)) {
            return false;
        }
        if (shouldAnalyzePatternVariablesInCaseLabel(psiElement2) && (caseLabelElementList = getCaseLabelElementList()) != null) {
            return caseLabelElementList.processDeclarations(psiScopeProcessor, resolveState, null, psiElement2);
        }
        return true;
    }

    private boolean shouldAnalyzePatternVariablesInCaseLabel(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        if (psiElement instanceof PsiCodeBlock) {
            return true;
        }
        final AtomicBoolean atomicBoolean = new AtomicBoolean();
        PsiTreeUtil.treeWalkUp(psiElement, getLightParent(), new PairProcessor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiSwitchLabelStatementImpl$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.PairProcessor
            public final boolean process(Object obj, Object obj2) {
                return this.f$0.m6687x74507553(atomicBoolean, (PsiElement) obj, (PsiElement) obj2);
            }
        });
        return atomicBoolean.get();
    }

    /* JADX INFO: renamed from: lambda$shouldAnalyzePatternVariablesInCaseLabel$0$org-jetbrains-kotlin-com-intellij-psi-impl-source-tree-java-PsiSwitchLabelStatementImpl, reason: not valid java name */
    /* synthetic */ boolean m6687x74507553(AtomicBoolean atomicBoolean, PsiElement psiElement, PsiElement psiElement2) {
        PsiSwitchLabelStatementBase psiSwitchLabelStatementBase;
        if (psiElement instanceof PsiSwitchLabelStatementBase) {
            psiSwitchLabelStatementBase = (PsiSwitchLabelStatementBase) psiElement;
        } else {
            psiSwitchLabelStatementBase = (PsiSwitchLabelStatementBase) PsiTreeUtil.getPrevSiblingOfType(psiElement, PsiSwitchLabelStatementBase.class);
        }
        while (psiSwitchLabelStatementBase != null && isFallthrough(psiSwitchLabelStatementBase) && isCaseNull(psiSwitchLabelStatementBase)) {
            psiSwitchLabelStatementBase = (PsiSwitchLabelStatementBase) PsiTreeUtil.getPrevSiblingOfType(psiSwitchLabelStatementBase, PsiSwitchLabelStatementBase.class);
        }
        if (psiSwitchLabelStatementBase != this) {
            return true;
        }
        atomicBoolean.set(true);
        return false;
    }

    private static boolean isFallthrough(PsiSwitchLabelStatementBase psiSwitchLabelStatementBase) {
        if (psiSwitchLabelStatementBase == null) {
            $$$reportNull$$$0(6);
        }
        PsiStatement psiStatement = (PsiStatement) PsiTreeUtil.getPrevSiblingOfType(psiSwitchLabelStatementBase, PsiStatement.class);
        if (psiStatement == null) {
            return false;
        }
        if (psiStatement instanceof PsiSwitchLabelStatementBase) {
            return true;
        }
        try {
            ControlFlow controlFlow = ControlFlowFactory.getControlFlow(psiStatement, new LocalsControlFlowPolicy(psiStatement), ControlFlowOptions.NO_CONST_EVALUATE);
            return ControlFlowUtil.canCompleteNormally(controlFlow, 0, controlFlow.getSize());
        } catch (AnalysisCanceledException unused) {
            return false;
        }
    }

    private static boolean isCaseNull(PsiSwitchLabelStatementBase psiSwitchLabelStatementBase) {
        if (psiSwitchLabelStatementBase == null) {
            $$$reportNull$$$0(7);
        }
        if (psiSwitchLabelStatementBase.getCaseLabelElementList() == null) {
            return false;
        }
        PsiCaseLabelElement[] elements = psiSwitchLabelStatementBase.getCaseLabelElementList().getElements();
        return elements.length == 1 && elements[0].getNode().getFirstChildNode().getElementType() == JavaTokenType.NULL_KEYWORD;
    }
}
