package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLocalVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiThisExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;

/* JADX INFO: loaded from: classes3.dex */
public final class LocalsOrMyInstanceFieldsControlFlowPolicy implements ControlFlowPolicy {
    private static final LocalsOrMyInstanceFieldsControlFlowPolicy INSTANCE = new LocalsOrMyInstanceFieldsControlFlowPolicy();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "psiParameter";
        } else if (i != 2) {
            objArr[0] = "refExpr";
        } else {
            objArr[0] = "psiVariable";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/LocalsOrMyInstanceFieldsControlFlowPolicy";
        if (i == 1) {
            objArr[2] = "isParameterAccepted";
        } else if (i != 2) {
            objArr[2] = "getUsedVariable";
        } else {
            objArr[2] = "isLocalVariableAccepted";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowPolicy
    public boolean isLocalVariableAccepted(PsiLocalVariable psiLocalVariable) {
        if (psiLocalVariable != null) {
            return true;
        }
        $$$reportNull$$$0(2);
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowPolicy
    public boolean isParameterAccepted(PsiParameter psiParameter) {
        if (psiParameter == null) {
            $$$reportNull$$$0(1);
        }
        return true;
    }

    private LocalsOrMyInstanceFieldsControlFlowPolicy() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowPolicy
    public PsiVariable getUsedVariable(PsiReferenceExpression psiReferenceExpression) {
        if (psiReferenceExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (isLocalOrMyInstanceReference(psiReferenceExpression)) {
            return (PsiVariable) ObjectUtils.tryCast(psiReferenceExpression.resolve(), PsiVariable.class);
        }
        return null;
    }

    public static LocalsOrMyInstanceFieldsControlFlowPolicy getInstance() {
        return INSTANCE;
    }

    public static boolean isLocalOrMyInstanceReference(PsiReferenceExpression psiReferenceExpression) {
        PsiExpression psiExpressionSkipParenthesizedExprDown = PsiUtil.skipParenthesizedExprDown(psiReferenceExpression.getQualifierExpression());
        return psiExpressionSkipParenthesizedExprDown == null || ((psiExpressionSkipParenthesizedExprDown instanceof PsiThisExpression) && ((PsiThisExpression) psiExpressionSkipParenthesizedExprDown).getQualifier() == null);
    }
}
