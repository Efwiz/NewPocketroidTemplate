package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCaseLabelElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiCaseLabelElementList;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchLabeledRuleStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeTestPattern;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiSwitchLabeledRuleStatementImpl extends PsiSwitchLabelStatementBaseImpl implements PsiSwitchLabeledRuleStatement {
    private static final TokenSet BODY_STATEMENTS = TokenSet.create(JavaElementType.BLOCK_STATEMENT, JavaElementType.THROW_STATEMENT, JavaElementType.EXPRESSION_STATEMENT);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "processor";
        } else if (i == 2) {
            objArr[0] = "state";
        } else if (i == 3) {
            objArr[0] = "place";
        } else if (i == 4) {
            objArr[0] = "second";
        } else if (i != 5) {
            objArr[0] = "visitor";
        } else {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiSwitchLabeledRuleStatementImpl";
        if (i == 1 || i == 2 || i == 3) {
            objArr[2] = "processDeclarations";
        } else if (i == 4) {
            objArr[2] = "firstPatternVariableSecondNull";
        } else if (i != 5) {
            objArr[2] = "accept";
        } else {
            objArr[2] = "stripParensIfNecessary";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public PsiSwitchLabeledRuleStatementImpl() {
        super(JavaElementType.SWITCH_LABELED_RULE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSwitchLabeledRuleStatement
    public PsiStatement getBody() {
        return (PsiStatement) findPsiChildByType(BODY_STATEMENTS);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitSwitchLabeledRuleStatement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiSwitchLabeledRule";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiSwitchLabelStatementBaseImpl, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        PsiCaseLabelElementList caseLabelElementList;
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(1);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(3);
        }
        if (!super.processDeclarations(psiScopeProcessor, resolveState, psiElement, psiElement2)) {
            return false;
        }
        if (psiElement == null || (psiElement instanceof PsiCaseLabelElementList) || !shouldProcess() || (caseLabelElementList = getCaseLabelElementList()) == null) {
            return true;
        }
        return caseLabelElementList.processDeclarations(psiScopeProcessor, resolveState, null, psiElement2);
    }

    private boolean shouldProcess() {
        PsiCaseLabelElementList caseLabelElementList = getCaseLabelElementList();
        if (caseLabelElementList == null) {
            return false;
        }
        PsiCaseLabelElement[] elements = caseLabelElementList.getElements();
        if (elements.length == 1) {
            return true;
        }
        if (elements.length > 2 || elements.length == 0) {
            return false;
        }
        PsiElement psiElementStripParensIfNecessary = stripParensIfNecessary(elements[0]);
        PsiElement psiElementStripParensIfNecessary2 = stripParensIfNecessary(elements[1]);
        if (psiElementStripParensIfNecessary == null || psiElementStripParensIfNecessary2 == null) {
            return true;
        }
        return firstPatternVariableSecondNull(psiElementStripParensIfNecessary, psiElementStripParensIfNecessary2) || firstPatternVariableSecondNull(psiElementStripParensIfNecessary2, psiElementStripParensIfNecessary);
    }

    private static boolean firstPatternVariableSecondNull(PsiElement psiElement, PsiElement psiElement2) {
        if (psiElement2 == null) {
            $$$reportNull$$$0(4);
        }
        return (psiElement instanceof PsiTypeTestPattern) && psiElement2.getNode().getFirstChildNode().getElementType() == JavaTokenType.NULL_KEYWORD;
    }

    private static PsiElement stripParensIfNecessary(PsiCaseLabelElement psiCaseLabelElement) {
        if (psiCaseLabelElement == null) {
            $$$reportNull$$$0(5);
        }
        return psiCaseLabelElement instanceof PsiExpression ? PsiUtil.skipParenthesizedExprDown((PsiExpression) psiCaseLabelElement) : psiCaseLabelElement;
    }
}
