package org.jetbrains.kotlin.com.intellij.codeInsight.runner;

import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;

/* JADX INFO: loaded from: classes3.dex */
public interface JavaMainMethodProvider {
    public static final ExtensionPointName<JavaMainMethodProvider> EP_NAME = ExtensionPointName.create("org.jetbrains.kotlin.com.intellij.javaMainMethodProvider");

    PsiMethod findMainInClass(PsiClass psiClass);

    boolean hasMainMethod(PsiClass psiClass);

    boolean isApplicable(PsiClass psiClass);
}
