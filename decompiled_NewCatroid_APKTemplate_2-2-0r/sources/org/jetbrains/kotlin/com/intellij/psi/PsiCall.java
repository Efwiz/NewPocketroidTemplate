package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiCall extends PsiElement {
    PsiExpressionList getArgumentList();

    PsiMethod resolveMethod();

    JavaResolveResult resolveMethodGenerics();
}
