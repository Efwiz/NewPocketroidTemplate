package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.openapi.util.Iconable;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiFunctionalExpression extends Iconable, NavigatablePsiElement, PsiExpression {
    public static final PsiFunctionalExpression[] EMPTY_ARRAY = new PsiFunctionalExpression[0];

    PsiType getFunctionalInterfaceType();

    PsiType getGroundTargetType(PsiType psiType);

    boolean isAcceptable(PsiType psiType, PsiMethod psiMethod);

    boolean isPotentiallyCompatible(PsiType psiType);

    default boolean isAcceptable(PsiType psiType) {
        return isAcceptable(psiType, null);
    }
}
