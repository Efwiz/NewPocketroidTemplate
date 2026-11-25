package org.jetbrains.kotlin.com.intellij.util.indexing;

import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: loaded from: classes3.dex */
public interface IndexedFile extends UserDataHolder {
    VirtualFile getFile();

    String getFileName();

    FileType getFileType();
}
