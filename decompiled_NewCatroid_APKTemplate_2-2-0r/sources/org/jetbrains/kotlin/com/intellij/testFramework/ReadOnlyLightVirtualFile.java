package org.jetbrains.kotlin.com.intellij.testFramework;

import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes3.dex */
public class ReadOnlyLightVirtualFile extends LightVirtualFile {
    @Override // org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFileBase, org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public final void setWritable(boolean z) {
        if (z) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile, org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public OutputStream getOutputStream(Object obj, long j, long j2) throws IOException {
        throw new UnsupportedOperationException();
    }
}
