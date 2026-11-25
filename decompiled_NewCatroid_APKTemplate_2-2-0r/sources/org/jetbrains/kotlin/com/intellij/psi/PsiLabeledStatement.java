package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiLabeledStatement extends PsiNameIdentifierOwner, PsiStatement {
    PsiIdentifier getLabelIdentifier();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    String getName();

    PsiStatement getStatement();
}
