package org.jetbrains.kotlin.com.intellij.psi.impl.file.impl;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;

/* JADX INFO: loaded from: classes3.dex */
public interface FileManager {
    void cleanupForNextTest();

    FileViewProvider createFileViewProvider(VirtualFile virtualFile, boolean z);

    FileViewProvider findCachedViewProvider(VirtualFile virtualFile);

    PsiDirectory findDirectory(VirtualFile virtualFile);

    PsiFile findFile(VirtualFile virtualFile);

    FileViewProvider findViewProvider(VirtualFile virtualFile);

    List<PsiFile> getAllCachedFiles();

    PsiFile getCachedPsiFile(VirtualFile virtualFile);

    void reloadFromDisk(PsiFile psiFile);

    void setViewProvider(VirtualFile virtualFile, FileViewProvider fileViewProvider);
}
