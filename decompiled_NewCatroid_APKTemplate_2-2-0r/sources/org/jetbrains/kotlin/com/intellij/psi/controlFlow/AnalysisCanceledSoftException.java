package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
class AnalysisCanceledSoftException extends RuntimeException {
    private final PsiElement myErrorElement;

    AnalysisCanceledSoftException(PsiElement psiElement) {
        this.myErrorElement = psiElement;
    }

    public PsiElement getErrorElement() {
        return this.myErrorElement;
    }
}
