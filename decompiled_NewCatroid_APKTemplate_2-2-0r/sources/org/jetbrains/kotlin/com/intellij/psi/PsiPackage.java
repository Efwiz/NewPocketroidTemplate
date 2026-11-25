package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.jvm.JvmPackage;
import org.jetbrains.kotlin.com.intellij.navigation.NavigationItem;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiPackage extends JvmPackage, NavigationItem, PsiCheckedRenameElement, PsiDirectoryContainer, PsiJvmModifiersOwner, PsiQualifiedNamedElement {
    public static final PsiPackage[] EMPTY_ARRAY = new PsiPackage[0];
    public static final String PACKAGE_INFO_CLASS = "package-info";
    public static final String PACKAGE_INFO_CLS_FILE = "package-info.class";
    public static final String PACKAGE_INFO_FILE = "package-info.java";

    boolean containsClassNamed(String str);

    PsiClass[] findClassByShortName(String str, GlobalSearchScope globalSearchScope);

    PsiModifierList getAnnotationList();

    PsiClass[] getClasses();

    PsiClass[] getClasses(GlobalSearchScope globalSearchScope);

    PsiFile[] getFiles(GlobalSearchScope globalSearchScope);

    @Override // org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    String getName();

    @Override // 
    PsiPackage getParentPackage();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    String getQualifiedName();

    PsiPackage[] getSubPackages();

    PsiPackage[] getSubPackages(GlobalSearchScope globalSearchScope);

    void handleQualifiedNameChange(String str);

    VirtualFile[] occursInPackagePrefixes();
}
