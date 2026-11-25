package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiJShellImportHolder extends PsiElement, PsiJShellSyntheticElement {
    public static final PsiJShellImportHolder[] EMPTY_ARRAY = new PsiJShellImportHolder[0];

    PsiImportStatement getImportStatement();
}
