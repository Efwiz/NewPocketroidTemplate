package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.FileASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.tree.IFileElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiFile extends PsiFileSystemItem {
    public static final PsiFile[] EMPTY_ARRAY = new PsiFile[0];

    default void clearCaches() {
    }

    PsiDirectory getContainingDirectory();

    FileType getFileType();

    long getModificationStamp();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    FileASTNode getNode();

    PsiFile getOriginalFile();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    PsiDirectory getParent();

    @Deprecated
    PsiFile[] getPsiRoots();

    FileViewProvider getViewProvider();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem
    VirtualFile getVirtualFile();

    void subtreeChanged();

    default IFileElementType getFileElementType() {
        return (IFileElementType) ObjectUtils.tryCast(PsiUtilCore.getElementType(getNode()), IFileElementType.class);
    }
}
