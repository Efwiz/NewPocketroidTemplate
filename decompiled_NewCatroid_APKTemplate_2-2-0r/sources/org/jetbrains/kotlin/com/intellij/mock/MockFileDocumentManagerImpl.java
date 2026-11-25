package org.jetbrains.kotlin.com.intellij.mock;

import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.impl.LoadTextUtil;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.BinaryFileTypeDecompilers;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.util.Function;

/* JADX INFO: loaded from: classes3.dex */
public class MockFileDocumentManagerImpl extends FileDocumentManager {
    private final Key<Document> myCachedDocumentKey;
    private final Function<? super CharSequence, ? extends Document> myFactory;
    private static final Key<VirtualFile> MOCK_VIRTUAL_FILE_KEY = Key.create("MockVirtualFile");
    private static final Key<Document> MOCK_DOC_KEY = Key.create("MOCK_DOC_KEY");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 7 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 7 ? 3 : 2];
        switch (i) {
            case 1:
            case 2:
            case 9:
                objArr[0] = "file";
                break;
            case 3:
            case 5:
            case 6:
            case 8:
            case 10:
            case 11:
            case 13:
                objArr[0] = "document";
                break;
            case 4:
                objArr[0] = "filter";
                break;
            case 7:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/mock/MockFileDocumentManagerImpl";
                break;
            case 12:
                objArr[0] = "files";
                break;
            default:
                objArr[0] = "factory";
                break;
        }
        if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/mock/MockFileDocumentManagerImpl";
        } else {
            objArr[1] = "getUnsavedDocuments";
        }
        switch (i) {
            case 1:
                objArr[2] = "getDocument";
                break;
            case 2:
                objArr[2] = "getCachedDocument";
                break;
            case 3:
                objArr[2] = "getFile";
                break;
            case 4:
                objArr[2] = "saveDocuments";
                break;
            case 5:
                objArr[2] = "saveDocument";
                break;
            case 6:
                objArr[2] = "saveDocumentAsIs";
                break;
            case 7:
                break;
            case 8:
                objArr[2] = "isDocumentUnsaved";
                break;
            case 9:
                objArr[2] = "isFileModified";
                break;
            case 10:
                objArr[2] = "isPartialPreviewOfALargeFile";
                break;
            case 11:
                objArr[2] = "reloadFromDisk";
                break;
            case 12:
                objArr[2] = "reloadFiles";
                break;
            case 13:
                objArr[2] = "requestWriting";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 7) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager
    public boolean isDocumentUnsaved(Document document) {
        if (document != null) {
            return false;
        }
        $$$reportNull$$$0(8);
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager
    public void reloadFromDisk(Document document) {
        if (document == null) {
            $$$reportNull$$$0(11);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager
    public void saveDocument(Document document) {
        if (document == null) {
            $$$reportNull$$$0(5);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager
    public void saveDocumentAsIs(Document document) {
        if (document == null) {
            $$$reportNull$$$0(6);
        }
    }

    public MockFileDocumentManagerImpl(Key<Document> key, Function<? super CharSequence, ? extends Document> function) {
        if (function == null) {
            $$$reportNull$$$0(0);
        }
        this.myFactory = function;
        this.myCachedDocumentKey = key;
    }

    private static boolean isBinaryWithoutDecompiler(VirtualFile virtualFile) {
        FileType fileType = virtualFile.getFileType();
        return fileType.isBinary() && BinaryFileTypeDecompilers.getInstance().forFileType(fileType) == null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager
    public Document getDocument(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(1);
        }
        Key<Document> key = MOCK_DOC_KEY;
        Document document = (Document) virtualFile.getUserData(key);
        if (document != null) {
            return document;
        }
        if (virtualFile.isDirectory() || isBinaryWithoutDecompiler(virtualFile)) {
            return null;
        }
        Document documentFun = this.myFactory.fun(LoadTextUtil.loadText(virtualFile));
        documentFun.putUserData(MOCK_VIRTUAL_FILE_KEY, virtualFile);
        return (Document) virtualFile.putUserDataIfAbsent(key, documentFun);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager
    public Document getCachedDocument(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(2);
        }
        Key<Document> key = this.myCachedDocumentKey;
        if (key != null) {
            return (Document) virtualFile.getUserData(key);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager
    public VirtualFile getFile(Document document) {
        if (document == null) {
            $$$reportNull$$$0(3);
        }
        return (VirtualFile) document.getUserData(MOCK_VIRTUAL_FILE_KEY);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager
    public String getLineSeparator(VirtualFile virtualFile, Project project) {
        return "";
    }
}
