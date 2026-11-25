package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeExtension;

/* JADX INFO: loaded from: classes3.dex */
public final class FileTypeFileViewProviders extends FileTypeExtension<FileViewProviderFactory> {
    public static final FileTypeFileViewProviders INSTANCE = new FileTypeFileViewProviders();

    private FileTypeFileViewProviders() {
        super("org.jetbrains.kotlin.com.intellij.fileType.fileViewProviderFactory");
    }
}
