package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiQualifiedReference extends PsiReference {
    PsiElement getQualifier();

    String getReferenceName();
}
