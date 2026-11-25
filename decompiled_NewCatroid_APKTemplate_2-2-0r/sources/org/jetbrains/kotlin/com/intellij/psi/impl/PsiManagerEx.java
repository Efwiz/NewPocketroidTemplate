package org.jetbrains.kotlin.com.intellij.psi.impl;

import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileFilter;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManager;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiManagerEx extends PsiManager {
    public abstract void afterChange(boolean z);

    public abstract void beforeChange(boolean z);

    public abstract void beforeChildAddition(PsiTreeChangeEventImpl psiTreeChangeEventImpl);

    public abstract void beforeChildRemoval(PsiTreeChangeEventImpl psiTreeChangeEventImpl);

    public abstract void beforeChildReplacement(PsiTreeChangeEventImpl psiTreeChangeEventImpl);

    public abstract FileManager getFileManager();

    public abstract boolean isAssertOnFileLoading(VirtualFile virtualFile);

    public abstract boolean isBatchFilesProcessingMode();

    public abstract void setAssertOnFileLoadingFilter(VirtualFileFilter virtualFileFilter, Disposable disposable);

    public static PsiManagerEx getInstanceEx(Project project) {
        return (PsiManagerEx) getInstance(project);
    }
}
