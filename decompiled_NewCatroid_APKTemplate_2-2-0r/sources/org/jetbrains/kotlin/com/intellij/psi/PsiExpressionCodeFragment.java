package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiExpressionCodeFragment extends JavaCodeFragment {
    PsiType getExpectedType();

    PsiExpression getExpression();

    void setExpectedType(PsiType psiType);
}
