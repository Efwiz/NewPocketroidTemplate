package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiLambdaExpression extends PsiFunctionalExpression, PsiParameterListOwner {
    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
    PsiElement getBody();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameterListOwner
    PsiParameterList getParameterList();

    boolean hasFormalParameterTypes();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFunctionalExpression
    boolean isPotentiallyCompatible(PsiType psiType);

    boolean isValueCompatible();

    boolean isVoidCompatible();
}
