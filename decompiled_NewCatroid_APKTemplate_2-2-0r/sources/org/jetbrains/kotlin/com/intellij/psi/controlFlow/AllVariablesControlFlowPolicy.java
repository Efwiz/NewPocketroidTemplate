package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiLocalVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;

/* JADX INFO: loaded from: classes3.dex */
public class AllVariablesControlFlowPolicy implements ControlFlowPolicy {
    private static final AllVariablesControlFlowPolicy INSTANCE = new AllVariablesControlFlowPolicy();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "psiParameter";
        } else if (i != 2) {
            objArr[0] = "refExpr";
        } else {
            objArr[0] = "psiVariable";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/AllVariablesControlFlowPolicy";
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

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowPolicy
    public PsiVariable getUsedVariable(PsiReferenceExpression psiReferenceExpression) {
        if (psiReferenceExpression == null) {
            $$$reportNull$$$0(0);
        }
        PsiElement psiElementResolve = psiReferenceExpression.resolve();
        if (psiElementResolve instanceof PsiVariable) {
            return (PsiVariable) psiElementResolve;
        }
        return null;
    }

    public static AllVariablesControlFlowPolicy getInstance() {
        return INSTANCE;
    }
}
