package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

import org.jetbrains.kotlin.com.intellij.psi.PsiLocalVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;

/* JADX INFO: loaded from: classes3.dex */
public interface ControlFlowPolicy {
    PsiVariable getUsedVariable(PsiReferenceExpression psiReferenceExpression);

    boolean isLocalVariableAccepted(PsiLocalVariable psiLocalVariable);

    boolean isParameterAccepted(PsiParameter psiParameter);
}
