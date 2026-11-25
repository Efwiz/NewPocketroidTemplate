package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiExpressionList extends PsiElement {
    PsiType[] getExpressionTypes();

    PsiExpression[] getExpressions();

    default int getExpressionCount() {
        return getExpressions().length;
    }

    default boolean isEmpty() {
        return getExpressionCount() == 0;
    }
}
