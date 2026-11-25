package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.openapi.project.Project;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JavaCodeFragmentFactory {
    public static final int ALLOW_DISJUNCTION = 4;
    public static final int ALLOW_ELLIPSIS = 2;
    public static final int ALLOW_INTERSECTION = 8;
    public static final int ALLOW_VOID = 1;

    public abstract JavaCodeFragment createCodeBlockCodeFragment(String str, PsiElement psiElement, boolean z);

    public abstract PsiExpressionCodeFragment createExpressionCodeFragment(String str, PsiElement psiElement, PsiType psiType, boolean z);

    public abstract JavaCodeFragment createMemberCodeFragment(String str, PsiElement psiElement, boolean z);

    public abstract PsiJavaCodeReferenceCodeFragment createReferenceCodeFragment(String str, PsiElement psiElement, boolean z, boolean z2);

    public abstract PsiTypeCodeFragment createTypeCodeFragment(String str, PsiElement psiElement, boolean z);

    public abstract PsiTypeCodeFragment createTypeCodeFragment(String str, PsiElement psiElement, boolean z, int i);

    public static JavaCodeFragmentFactory getInstance(Project project) {
        return (JavaCodeFragmentFactory) project.getService(JavaCodeFragmentFactory.class);
    }
}
