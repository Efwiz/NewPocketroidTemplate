package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiForStatement extends PsiConditionalLoopStatement {
    PsiStatement getInitialization();

    PsiJavaToken getLParenth();

    PsiJavaToken getRParenth();

    PsiStatement getUpdate();
}
