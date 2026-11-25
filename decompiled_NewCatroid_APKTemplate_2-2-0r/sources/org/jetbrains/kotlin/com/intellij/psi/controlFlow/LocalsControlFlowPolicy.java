package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLocalVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

/* JADX INFO: loaded from: classes3.dex */
public class LocalsControlFlowPolicy implements ControlFlowPolicy {
    private final PsiElement myCodeFragment;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "refExpr";
        } else if (i == 2) {
            objArr[0] = "refElement";
        } else if (i == 3) {
            objArr[0] = "psiParameter";
        } else if (i != 4) {
            objArr[0] = "codeFragment";
        } else {
            objArr[0] = "psiVariable";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/LocalsControlFlowPolicy";
        if (i == 1) {
            objArr[2] = "getUsedVariable";
        } else if (i == 2) {
            objArr[2] = "checkCodeFragment";
        } else if (i == 3) {
            objArr[2] = "isParameterAccepted";
        } else if (i != 4) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "isLocalVariableAccepted";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public LocalsControlFlowPolicy(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        this.myCodeFragment = psiElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowPolicy
    public PsiVariable getUsedVariable(PsiReferenceExpression psiReferenceExpression) {
        if (psiReferenceExpression == null) {
            $$$reportNull$$$0(1);
        }
        if (psiReferenceExpression.isQualified()) {
            return null;
        }
        PsiElement psiElementResolve = psiReferenceExpression.resolve();
        if ((psiElementResolve instanceof PsiLocalVariable) || (psiElementResolve instanceof PsiParameter)) {
            return checkCodeFragment(psiElementResolve);
        }
        return null;
    }

    private PsiVariable checkCodeFragment(PsiElement psiElement) {
        PsiElement psiElementFindCodeFragment;
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElement instanceof PsiParameter) {
            PsiElement method = ((PsiParameter) psiElement).getMethod();
            if (method instanceof PsiMethod) {
                psiElementFindCodeFragment = ((PsiMethod) method).getBody();
            } else if (method instanceof PsiLambdaExpression) {
                psiElementFindCodeFragment = ((PsiLambdaExpression) method).getBody();
            } else {
                psiElementFindCodeFragment = ControlFlowUtil.findCodeFragment(psiElement);
            }
        } else {
            psiElementFindCodeFragment = ControlFlowUtil.findCodeFragment(psiElement);
        }
        if (psiElementFindCodeFragment == null) {
            return null;
        }
        if (this.myCodeFragment.getContainingFile() != psiElementFindCodeFragment.getContainingFile() || this.myCodeFragment.equals(psiElementFindCodeFragment) || ((this.myCodeFragment.getMyParent() instanceof PsiLambdaExpression) && PsiTreeUtil.isAncestor(PsiTreeUtil.getParentOfType(this.myCodeFragment, PsiClass.class), psiElementFindCodeFragment, false))) {
            return (PsiVariable) psiElement;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowPolicy
    public boolean isParameterAccepted(PsiParameter psiParameter) {
        if (psiParameter == null) {
            $$$reportNull$$$0(3);
        }
        return checkCodeFragment(psiParameter) != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowPolicy
    public boolean isLocalVariableAccepted(PsiLocalVariable psiLocalVariable) {
        if (psiLocalVariable == null) {
            $$$reportNull$$$0(4);
        }
        return checkCodeFragment(psiLocalVariable) != null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.myCodeFragment.equals(((LocalsControlFlowPolicy) obj).myCodeFragment);
    }

    public int hashCode() {
        return this.myCodeFragment.hashCode();
    }
}
