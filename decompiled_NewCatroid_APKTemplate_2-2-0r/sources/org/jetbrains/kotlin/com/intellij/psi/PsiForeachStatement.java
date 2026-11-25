package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiForeachStatement extends PsiLoopStatement {
    PsiExpression getIteratedValue();

    PsiParameter getIterationParameter();

    PsiJavaToken getLParenth();

    PsiJavaToken getRParenth();
}
