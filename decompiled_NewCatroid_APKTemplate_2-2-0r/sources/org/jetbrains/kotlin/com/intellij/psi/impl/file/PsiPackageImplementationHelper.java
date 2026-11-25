package org.jetbrains.kotlin.com.intellij.psi.impl.file;

import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiPackageImplementationHelper {
    public abstract GlobalSearchScope adjustAllScope(PsiPackage psiPackage, GlobalSearchScope globalSearchScope);

    public abstract Object[] getDirectoryCachedValueDependencies(PsiPackage psiPackage);

    public abstract void handleQualifiedNameChange(PsiPackage psiPackage, String str);

    public abstract void navigate(PsiPackage psiPackage, boolean z);

    public abstract VirtualFile[] occursInPackagePrefixes(PsiPackage psiPackage);

    public abstract boolean packagePrefixExists(PsiPackage psiPackage);

    public static PsiPackageImplementationHelper getInstance() {
        return (PsiPackageImplementationHelper) ApplicationManager.getApplication().getService(PsiPackageImplementationHelper.class);
    }
}
