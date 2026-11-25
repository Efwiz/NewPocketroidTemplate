package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiParameterList extends PsiElement {
    int getParameterIndex(PsiParameter psiParameter);

    PsiParameter[] getParameters();

    int getParametersCount();

    default PsiParameter getParameter(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("index is negative: " + i);
        }
        PsiParameter[] parameters = getParameters();
        if (i < parameters.length) {
            return parameters[i];
        }
        return null;
    }

    default boolean isEmpty() {
        return getParametersCount() == 0;
    }
}
