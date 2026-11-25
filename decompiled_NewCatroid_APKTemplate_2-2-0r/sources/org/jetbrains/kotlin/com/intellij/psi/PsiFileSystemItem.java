package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.search.PsiElementProcessor;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiFileSystemItem extends NavigatablePsiElement, PsiCheckedRenameElement {
    @Override // org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    String getName();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    PsiFileSystemItem getParent();

    VirtualFile getVirtualFile();

    boolean isDirectory();

    boolean processChildren(PsiElementProcessor<? super PsiFileSystemItem> psiElementProcessor);
}
