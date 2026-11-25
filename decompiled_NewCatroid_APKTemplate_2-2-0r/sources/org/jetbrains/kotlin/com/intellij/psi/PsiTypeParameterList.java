package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiTypeParameterList extends PsiElement {
    int getTypeParameterIndex(PsiTypeParameter psiTypeParameter);

    PsiTypeParameter[] getTypeParameters();
}
