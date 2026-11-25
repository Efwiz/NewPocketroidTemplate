package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiContinueStatement extends PsiStatement {
    PsiStatement findContinuedStatement();

    PsiIdentifier getLabelIdentifier();
}
