package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiIfStatement extends PsiStatement {
    PsiExpression getCondition();

    PsiStatement getElseBranch();

    PsiKeyword getElseElement();

    PsiJavaToken getLParenth();

    PsiJavaToken getRParenth();

    PsiStatement getThenBranch();

    void setElseBranch(PsiStatement psiStatement) throws IncorrectOperationException;

    void setThenBranch(PsiStatement psiStatement) throws IncorrectOperationException;
}
