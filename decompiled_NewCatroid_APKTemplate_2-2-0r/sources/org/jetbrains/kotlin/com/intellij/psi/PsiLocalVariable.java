package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiLocalVariable extends PsiVariable {
    @Override // org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    String getName();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    PsiTypeElement getTypeElement();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    /* JADX INFO: renamed from: setInitializer */
    void mo6096setInitializer(PsiExpression psiExpression) throws IncorrectOperationException;
}
