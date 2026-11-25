package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.compiled.ClassFileDecompilers;

/* JADX INFO: loaded from: classes3.dex */
public class ClassFileViewProviderFactory implements FileViewProviderFactory {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "manager";
        } else if (i != 2) {
            objArr[0] = "file";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/ClassFileViewProviderFactory";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/ClassFileViewProviderFactory";
        } else {
            objArr[1] = "createFileViewProvider";
        }
        if (i != 2) {
            objArr[2] = "createFileViewProvider";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProviderFactory
    public FileViewProvider createFileViewProvider(VirtualFile virtualFile, Language language, PsiManager psiManager, boolean z) {
        if (virtualFile == null) {
            $$$reportNull$$$0(0);
        }
        if (psiManager == null) {
            $$$reportNull$$$0(1);
        }
        FileViewProvider fileViewProviderCreateFileViewProvider = ((ClassFileDecompilers.Full) ClassFileDecompilers.getInstance().find(virtualFile, ClassFileDecompilers.Full.class)).createFileViewProvider(virtualFile, psiManager, z);
        if (fileViewProviderCreateFileViewProvider == null) {
            $$$reportNull$$$0(2);
        }
        return fileViewProviderCreateFileViewProvider;
    }
}
