package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiJavaFile extends PsiClassOwner, PsiImportHolder {
    PsiJavaCodeReferenceElement findImportReferenceTo(PsiClass psiClass);

    PsiJavaCodeReferenceElement[] getImplicitlyImportedPackageReferences();

    String[] getImplicitlyImportedPackages();

    PsiImportList getImportList();

    LanguageLevel getLanguageLevel();

    PsiJavaModule getModuleDeclaration();

    PsiElement[] getOnDemandImports(boolean z, @Deprecated boolean z2);

    String getPackageName();

    PsiPackageStatement getPackageStatement();

    PsiClass[] getSingleClassImports(@Deprecated boolean z);
}
