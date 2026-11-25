package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes6.dex */
public interface KtPureElement {
    KtFile getContainingKtFile();

    PsiElement getParent();

    KtElement getPsiOrParent();
}
