package org.jetbrains.kotlin.com.intellij.psi.stubs;

import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeExtension;

/* JADX INFO: loaded from: classes3.dex */
public class BinaryFileStubBuilders extends FileTypeExtension<BinaryFileStubBuilder> {
    public static final BinaryFileStubBuilders INSTANCE = new BinaryFileStubBuilders();

    public BinaryFileStubBuilders() {
        super("org.jetbrains.kotlin.com.intellij.filetype.stubBuilder");
    }
}
