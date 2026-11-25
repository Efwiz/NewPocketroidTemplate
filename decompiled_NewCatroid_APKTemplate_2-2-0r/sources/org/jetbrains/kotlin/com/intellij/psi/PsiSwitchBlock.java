package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiSwitchBlock extends PsiElement {
    PsiCodeBlock getBody();

    PsiExpression getExpression();

    PsiJavaToken getLParenth();

    PsiJavaToken getRParenth();
}
