package org.jetbrains.kotlin.com.intellij.psi.stubs;

import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;

/* JADX INFO: loaded from: classes3.dex */
class IndexingStampInfo {
    final long indexingByteLength;
    final int indexingCharLength;
    final long indexingFileStamp;
    final boolean isBinary;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "file";
        } else {
            objArr[0] = "psi";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/stubs/IndexingStampInfo";
        objArr[2] = "isUpToDate";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public String toString() {
        return "indexing timestamp = " + this.indexingFileStamp + ", binary = " + this.isBinary + ", byte size = " + this.indexingByteLength + ", char size = " + this.indexingCharLength;
    }

    public boolean isUpToDate(Document document, VirtualFile virtualFile, PsiFile psiFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(0);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(1);
        }
        if (document == null || FileDocumentManager.getInstance().isDocumentUnsaved(document) || !PsiDocumentManager.getInstance(psiFile.getProject()).isCommitted(document)) {
            return false;
        }
        return this.indexingFileStamp == virtualFile.getTimeStamp() && this.isBinary == virtualFile.getFileType().isBinary() && contentLengthMatches(virtualFile.getLength(), document.getTextLength());
    }

    public boolean contentLengthMatches(long j, int i) {
        int i2 = this.indexingCharLength;
        return i2 < 0 || i < 0 || i2 == i;
    }
}
