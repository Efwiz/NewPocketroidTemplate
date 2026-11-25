package org.jetbrains.kotlin.com.intellij.psi.impl.file;

import java.io.IOException;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeRegistry;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.UnknownFileType;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerImpl;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiFileImplUtil {
    /* JADX WARN: Code duplicated, block: B:9:0x0019  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 3) {
            objArr[0] = "file";
        } else if (i == 4) {
            objArr[0] = "name";
        } else if (i != 5) {
            objArr[0] = "newName";
        } else {
            objArr[0] = "file";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/file/PsiFileImplUtil";
        if (i == 1 || i == 2) {
            objArr[2] = "setName";
        } else if (i == 3 || i == 4) {
            objArr[2] = "checkSetName";
        } else if (i != 5) {
            objArr[2] = "saveDocumentIfFileWillBecomeBinary";
        } else {
            objArr[2] = "doDelete";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private PsiFileImplUtil() {
    }

    public static void saveDocumentIfFileWillBecomeBinary(VirtualFile virtualFile, String str) {
        FileDocumentManager fileDocumentManager;
        Document cachedDocument;
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        FileType fileTypeByFileName = FileTypeRegistry.getInstance().getFileTypeByFileName(str);
        if ((UnknownFileType.INSTANCE.equals(fileTypeByFileName) || fileTypeByFileName.isBinary()) && (cachedDocument = (fileDocumentManager = FileDocumentManager.getInstance()).getCachedDocument(virtualFile)) != null) {
            fileDocumentManager.saveDocumentAsIs(cachedDocument);
        }
    }

    public static PsiFile setName(PsiFile psiFile, String str) throws IncorrectOperationException {
        if (psiFile == null) {
            $$$reportNull$$$0(1);
        }
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        VirtualFile virtualFile = psiFile.getViewProvider().getVirtualFile();
        PsiManagerImpl psiManagerImpl = (PsiManagerImpl) psiFile.getManager();
        try {
            saveDocumentIfFileWillBecomeBinary(virtualFile, str);
            virtualFile.rename(psiManagerImpl, str);
            return psiFile.getViewProvider().isPhysical() ? psiManagerImpl.findFile(virtualFile) : psiFile;
        } catch (IOException e) {
            throw new IncorrectOperationException(e);
        }
    }

    public static void checkSetName(PsiFile psiFile, String str) throws IncorrectOperationException {
        VirtualFile virtualFileFindChild;
        if (psiFile == null) {
            $$$reportNull$$$0(3);
        }
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        VirtualFile virtualFile = psiFile.getVirtualFile();
        VirtualFile parent = virtualFile.getParent();
        if (parent != null && (virtualFileFindChild = parent.findChild(str)) != null && !virtualFileFindChild.equals(virtualFile)) {
            throw new IncorrectOperationException("File " + virtualFileFindChild.getPresentableUrl() + " already exists.");
        }
    }

    public static void doDelete(PsiFile psiFile) throws IncorrectOperationException {
        if (psiFile == null) {
            $$$reportNull$$$0(5);
        }
        try {
            psiFile.getVirtualFile().delete((PsiManagerImpl) psiFile.getManager());
        } catch (IOException e) {
            throw new IncorrectOperationException(e);
        }
    }
}
