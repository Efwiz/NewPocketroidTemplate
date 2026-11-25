package org.jetbrains.kotlin.com.intellij.openapi.fileEditor;

import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;

/* JADX INFO: loaded from: classes3.dex */
public abstract class FileDocumentManager {
    /* JADX WARN: Code duplicated, block: B:33:0x004f  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 4 || i == 5) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 4 || i == 5) ? 3 : 2];
        if (i == 1 || i == 3) {
            objArr[0] = "document";
        } else if (i == 4) {
            objArr[0] = "project";
        } else if (i != 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/fileEditor/FileDocumentManager";
        } else {
            objArr[0] = "file";
        }
        if (i == 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/fileEditor/FileDocumentManager";
        } else if (i == 2) {
            objArr[1] = "requestWritingStatus";
        } else if (i == 3 || i == 4 || i == 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/fileEditor/FileDocumentManager";
        } else {
            objArr[1] = "getInstance";
        }
        if (i == 1) {
            objArr[2] = "requestWritingStatus";
        } else if (i == 3 || i == 4) {
            objArr[2] = "fileForDocumentCheckedOutSuccessfully";
        } else if (i == 5) {
            objArr[2] = "findCachedPsiInAnyProject";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 4 && i != 5) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public FileViewProvider findCachedPsiInAnyProject(VirtualFile virtualFile) {
        if (virtualFile != null) {
            return null;
        }
        $$$reportNull$$$0(5);
        return null;
    }

    public abstract Document getCachedDocument(VirtualFile virtualFile);

    public abstract Document getDocument(VirtualFile virtualFile);

    public abstract VirtualFile getFile(Document document);

    public abstract String getLineSeparator(VirtualFile virtualFile, Project project);

    public abstract boolean isDocumentUnsaved(Document document);

    public void reloadBinaryFiles() {
    }

    public abstract void reloadFromDisk(Document document);

    public abstract void saveDocument(Document document);

    public abstract void saveDocumentAsIs(Document document);

    public static FileDocumentManager getInstance() {
        FileDocumentManager fileDocumentManager = (FileDocumentManager) ApplicationManager.getApplication().getService(FileDocumentManager.class);
        if (fileDocumentManager == null) {
            $$$reportNull$$$0(0);
        }
        return fileDocumentManager;
    }
}
