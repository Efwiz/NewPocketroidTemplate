package org.jetbrains.kotlin.com.intellij.psi.impl.file;

import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectoryContainer;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiDirectoryFactory {
    public abstract PsiDirectory createDirectory(VirtualFile virtualFile);

    public abstract PsiDirectoryContainer getDirectoryContainer(PsiDirectory psiDirectory);

    public abstract String getQualifiedName(PsiDirectory psiDirectory, boolean z);

    public abstract boolean isPackage(PsiDirectory psiDirectory);

    public abstract boolean isValidPackageName(String str);

    public static PsiDirectoryFactory getInstance(Project project) {
        return (PsiDirectoryFactory) project.getService(PsiDirectoryFactory.class);
    }
}
