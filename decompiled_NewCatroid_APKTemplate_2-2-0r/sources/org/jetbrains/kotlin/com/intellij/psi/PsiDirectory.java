package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiDirectory extends PsiFileSystemItem {
    public static final PsiDirectory[] EMPTY_ARRAY = new PsiDirectory[0];

    void checkCreateFile(String str) throws IncorrectOperationException;

    void checkCreateSubdirectory(String str) throws IncorrectOperationException;

    PsiFile copyFileFrom(String str, PsiFile psiFile) throws IncorrectOperationException;

    PsiFile createFile(String str) throws IncorrectOperationException;

    PsiDirectory createSubdirectory(String str) throws IncorrectOperationException;

    PsiFile findFile(String str);

    PsiDirectory findSubdirectory(String str);

    PsiFile[] getFiles();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    String getName();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    PsiDirectory getParent();

    PsiDirectory getParentDirectory();

    PsiDirectory[] getSubdirectories();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem
    VirtualFile getVirtualFile();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    PsiElement setName(String str) throws IncorrectOperationException;
}
