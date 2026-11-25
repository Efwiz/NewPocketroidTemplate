package org.jetbrains.kotlin.com.intellij.openapi.fileEditor.impl;

import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.NonPhysicalFileSystem;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile;

/* JADX INFO: loaded from: classes3.dex */
public abstract class FileDocumentManagerBase extends FileDocumentManager {
    public static final Key<Document> HARD_REF_TO_DOCUMENT_KEY = Key.create("HARD_REF_TO_DOCUMENT_KEY");
    public static final Key<Boolean> TRACK_NON_PHYSICAL = Key.create("TRACK_NON_PHYSICAL");
    private static final Key<VirtualFile> FILE_KEY = Key.create("FILE_KEY");
    private static final Key<Boolean> BIG_FILE_PREVIEW = Key.create("BIG_FILE_PREVIEW");
    private static final Object lock = new Object();

    /* JADX WARN: Code duplicated, block: B:28:0x003a  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
        if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/fileEditor/impl/FileDocumentManagerBase";
        } else if (i == 5) {
            objArr[0] = "document";
        } else if (i == 6) {
            objArr[0] = "virtualFile";
        } else if (i == 7 || i == 8 || i == 10 || i == 14) {
            objArr[0] = "document";
        } else {
            objArr[0] = "file";
        }
        if (i == 2 || i == 3) {
            objArr[1] = "loadText";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/fileEditor/impl/FileDocumentManagerBase";
        }
        switch (i) {
            case 1:
                objArr[2] = "loadText";
                break;
            case 2:
            case 3:
                break;
            case 4:
                objArr[2] = "getCachedDocument";
                break;
            case 5:
            case 6:
                objArr[2] = "registerDocument";
                break;
            case 7:
                objArr[2] = "getFile";
                break;
            case 8:
                objArr[2] = "isPartialPreviewOfALargeFile";
                break;
            case 9:
            case 10:
                objArr[2] = "unbindFileFromDocument";
                break;
            case 11:
                objArr[2] = "isBinaryWithoutDecompiler";
                break;
            case 12:
                objArr[2] = "getPreviewCharCount";
                break;
            case 13:
            case 14:
                objArr[2] = "cacheDocument";
                break;
            case 15:
                objArr[2] = "removeDocumentFromCache";
                break;
            case 16:
                objArr[2] = "getDocumentFromCache";
                break;
            default:
                objArr[2] = "getDocument";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static void registerDocument(Document document, VirtualFile virtualFile) {
        if (document == null) {
            $$$reportNull$$$0(5);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(6);
        }
        if (!(virtualFile instanceof LightVirtualFile) && !(virtualFile.getFileSystem() instanceof NonPhysicalFileSystem)) {
            throw new IllegalArgumentException("Hard-coding file<->document association is permitted for non-physical files only (see ViewProvider.isPhysical()) to avoid memleaks. virtualFile=" + virtualFile);
        }
        synchronized (lock) {
            document.putUserData(FILE_KEY, virtualFile);
            virtualFile.putUserData(HARD_REF_TO_DOCUMENT_KEY, document);
        }
    }
}
