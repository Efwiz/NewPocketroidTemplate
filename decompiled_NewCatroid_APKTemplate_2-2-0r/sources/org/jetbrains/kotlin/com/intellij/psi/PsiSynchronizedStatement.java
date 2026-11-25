package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiSynchronizedStatement extends PsiStatement {
    PsiCodeBlock getBody();

    PsiExpression getLockExpression();
}
