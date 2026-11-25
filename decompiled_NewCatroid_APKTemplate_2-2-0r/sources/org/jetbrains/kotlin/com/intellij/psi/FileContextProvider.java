package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Collection;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;

/* JADX INFO: loaded from: classes3.dex */
public abstract class FileContextProvider {
    public static final ExtensionPointName<FileContextProvider> EP_NAME = new ExtensionPointName<>("org.jetbrains.kotlin.com.intellij.fileContextProvider");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "file", "org/jetbrains/kotlin/com/intellij/psi/FileContextProvider", "getProvider"));
    }

    public abstract PsiFile getContextFile(PsiFile psiFile);

    public abstract Collection<PsiFileSystemItem> getContextFolders(PsiFile psiFile);

    protected abstract boolean isAvailable(PsiFile psiFile);

    public static FileContextProvider getProvider(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(0);
        }
        for (FileContextProvider fileContextProvider : EP_NAME.getExtensions(psiFile.getProject())) {
            if (fileContextProvider.isAvailable(psiFile)) {
                return fileContextProvider;
            }
        }
        return null;
    }
}
