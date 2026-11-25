package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public interface SourceJavaCodeReference {
    void fullyQualify(PsiClass psiClass);

    String getClassNameText();

    PsiElement getQualifier();

    boolean isQualified();
}
