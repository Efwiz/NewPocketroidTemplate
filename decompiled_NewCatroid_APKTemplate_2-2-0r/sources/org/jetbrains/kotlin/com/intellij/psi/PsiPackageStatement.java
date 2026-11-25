package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiPackageStatement extends PsiElement {
    PsiModifierList getAnnotationList();

    String getPackageName();

    PsiJavaCodeReferenceElement getPackageReference();
}
