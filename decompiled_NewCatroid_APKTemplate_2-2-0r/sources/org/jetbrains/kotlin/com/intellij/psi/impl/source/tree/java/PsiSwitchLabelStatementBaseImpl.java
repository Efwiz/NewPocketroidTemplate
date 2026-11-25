package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCaseLabelElementList;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchLabelStatementBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassFilter;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.processor.FilterScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiSwitchLabelStatementBaseImpl extends CompositePsiElement implements PsiSwitchLabelStatementBase {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "state";
        } else if (i != 2) {
            objArr[0] = "processor";
        } else {
            objArr[0] = "place";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiSwitchLabelStatementBaseImpl";
        objArr[2] = "processDeclarations";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    protected PsiSwitchLabelStatementBaseImpl(IElementType iElementType) {
        super(iElementType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSwitchLabelStatementBase
    public boolean isDefaultCase() {
        return findChildByType(JavaTokenType.DEFAULT_KEYWORD) != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSwitchLabelStatementBase
    public PsiExpressionList getCaseValues() {
        PsiCaseLabelElementList caseLabelElementList = getCaseLabelElementList();
        if (caseLabelElementList == null) {
            return null;
        }
        PsiExpression[] psiExpressionArr = (PsiExpression[]) PsiTreeUtil.getChildrenOfType(caseLabelElementList, PsiExpression.class);
        if (psiExpressionArr == null) {
            psiExpressionArr = PsiExpression.EMPTY_ARRAY;
        }
        return new LightExpressionList(getManager(), getLanguage(), psiExpressionArr, caseLabelElementList, caseLabelElementList.getTextRange());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSwitchLabelStatementBase
    public PsiSwitchBlock getEnclosingSwitchBlock() {
        PsiElement parent = getMyParent();
        if (parent == null) {
            return null;
        }
        PsiElement myParent = parent.getMyParent();
        if (myParent instanceof PsiSwitchBlock) {
            return (PsiSwitchBlock) myParent;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        PsiSwitchBlock enclosingSwitchBlock;
        PsiExpression expression;
        PsiClass psiClassResolve;
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(0);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(2);
        }
        if ((psiElement instanceof PsiCaseLabelElementList) && (enclosingSwitchBlock = getEnclosingSwitchBlock()) != null && (expression = enclosingSwitchBlock.getExpression()) != null) {
            PsiType type = expression.getType();
            if ((type instanceof PsiClassType) && (psiClassResolve = ((PsiClassType) type).resolve()) != null) {
                psiClassResolve.processDeclarations(new FilterScopeProcessor(ElementClassFilter.ENUM_CONST, psiScopeProcessor), resolveState, this, psiElement2);
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiSwitchLabelStatementBase
    public PsiCaseLabelElementList getCaseLabelElementList() {
        return (PsiCaseLabelElementList) PsiTreeUtil.getChildOfType(this, PsiCaseLabelElementList.class);
    }
}
