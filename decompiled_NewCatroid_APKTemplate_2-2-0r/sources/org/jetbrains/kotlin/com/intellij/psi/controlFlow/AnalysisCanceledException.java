package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public class AnalysisCanceledException extends Exception {
    private final PsiElement myErrorElement;

    public AnalysisCanceledException(PsiElement psiElement) {
        this.myErrorElement = psiElement;
    }

    public PsiElement getErrorElement() {
        return this.myErrorElement;
    }
}
