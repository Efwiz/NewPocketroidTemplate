package org.jetbrains.kotlin.com.intellij.psi.impl;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.core.CoreBundle;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeRegistry;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public final class CheckUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "file";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/CheckUtil";
        if (i != 1) {
            objArr[2] = "checkWritable";
        } else {
            objArr[2] = "checkDelete";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private CheckUtil() {
    }

    public static void checkWritable(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElement.isWritable()) {
            return;
        }
        if (psiElement instanceof PsiDirectory) {
            throw new IncorrectOperationException(CoreBundle.message("cannot.modify.a.read.only.directory", ((PsiDirectory) psiElement).getVirtualFile().getPresentableUrl()));
        }
        PsiFile containingFile = psiElement.getContainingFile();
        if (containingFile == null) {
            throw new IncorrectOperationException();
        }
        VirtualFile virtualFile = containingFile.getVirtualFile();
        if (virtualFile == null) {
            throw new IncorrectOperationException();
        }
        throw new IncorrectOperationException(CoreBundle.message("cannot.modify.a.read.only.file", virtualFile.getPresentableUrl()));
    }

    public static void checkDelete(VirtualFile virtualFile) throws Throwable {
        if (virtualFile == null) {
            $$$reportNull$$$0(1);
        }
        VfsUtilCore.visitChildrenRecursively(virtualFile, new VirtualFileVisitor<Void>(VirtualFileVisitor.NO_FOLLOW_SYMLINKS) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.CheckUtil.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "file", "org/jetbrains/kotlin/com/intellij/psi/impl/CheckUtil$1", "visitFile"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileVisitor
            public boolean visitFile(VirtualFile virtualFile2) {
                if (virtualFile2 == null) {
                    $$$reportNull$$$0(0);
                }
                if (FileTypeRegistry.getInstance().isFileIgnored(virtualFile2)) {
                    return false;
                }
                if (virtualFile2.isWritable()) {
                    return true;
                }
                throw new IncorrectOperationException(CoreBundle.message("cannot.delete.a.read.only.file", virtualFile2.getPresentableUrl()));
            }
        });
    }
}
