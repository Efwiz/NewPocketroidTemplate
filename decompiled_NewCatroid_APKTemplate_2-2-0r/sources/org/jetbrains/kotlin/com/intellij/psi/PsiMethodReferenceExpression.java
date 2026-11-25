package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiMethodReferenceExpression extends PsiFunctionalExpression, PsiReferenceExpression {
    PsiMember getPotentiallyApplicableMember();

    PsiTypeElement getQualifierType();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFunctionalExpression
    boolean isAcceptable(PsiType psiType, PsiMethod psiMethod);

    boolean isConstructor();

    boolean isExact();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFunctionalExpression
    boolean isPotentiallyCompatible(PsiType psiType);
}
