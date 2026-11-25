package org.jetbrains.kotlin.com.intellij.ide.util;

import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.pom.Navigatable;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiNavigationSupport {
    public abstract boolean canNavigate(PsiElement psiElement);

    public abstract Navigatable createNavigatable(Project project, VirtualFile virtualFile, int i);

    public abstract Navigatable getDescriptor(PsiElement psiElement);

    public abstract void navigateToDirectory(PsiDirectory psiDirectory, boolean z);

    public static PsiNavigationSupport getInstance() {
        return (PsiNavigationSupport) ApplicationManager.getApplication().getService(PsiNavigationSupport.class);
    }
}
