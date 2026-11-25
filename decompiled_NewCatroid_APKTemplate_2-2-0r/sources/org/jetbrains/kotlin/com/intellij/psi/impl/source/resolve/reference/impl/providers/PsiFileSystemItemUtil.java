package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.impl.providers;

import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiFileSystemItemUtil {
    public static String findRelativePath(PsiFileSystemItem psiFileSystemItem, PsiFileSystemItem psiFileSystemItem2) {
        VirtualFile virtualFile = psiFileSystemItem != null ? psiFileSystemItem.getVirtualFile() : null;
        VirtualFile virtualFile2 = psiFileSystemItem2 != null ? psiFileSystemItem2.getVirtualFile() : null;
        if (virtualFile == null || virtualFile2 == null) {
            return null;
        }
        return VfsUtilCore.findRelativePath(virtualFile, virtualFile2, '/');
    }

    public static String getRelativePathFromAncestor(PsiFileSystemItem psiFileSystemItem, PsiFileSystemItem psiFileSystemItem2) {
        VirtualFile virtualFile = psiFileSystemItem != null ? psiFileSystemItem.getVirtualFile() : null;
        VirtualFile virtualFile2 = psiFileSystemItem2 != null ? psiFileSystemItem2.getVirtualFile() : null;
        if (virtualFile == null || virtualFile2 == null) {
            return null;
        }
        return VfsUtilCore.getRelativePath(virtualFile, virtualFile2, '/');
    }
}
