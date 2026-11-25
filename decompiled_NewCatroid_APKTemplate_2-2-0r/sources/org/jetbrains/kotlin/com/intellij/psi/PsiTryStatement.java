package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiTryStatement extends PsiStatement {
    PsiParameter[] getCatchBlockParameters();

    PsiCodeBlock[] getCatchBlocks();

    PsiCatchSection[] getCatchSections();

    PsiCodeBlock getFinallyBlock();

    PsiResourceList getResourceList();

    PsiCodeBlock getTryBlock();
}
