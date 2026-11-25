package org.jetbrains.kotlin.com.intellij.openapi.vfs.local;

import java.io.File;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.DeprecatedVirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: loaded from: classes3.dex */
public class CoreLocalFileSystem extends DeprecatedVirtualFileSystem {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 2) {
            objArr[0] = "path";
        } else {
            objArr[0] = "ioFile";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/local/CoreLocalFileSystem";
        if (i == 1) {
            objArr[2] = "findFileByPath";
        } else if (i != 2) {
            objArr[2] = "findFileByIoFile";
        } else {
            objArr[2] = "refreshAndFindFileByPath";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    public void refresh(boolean z) {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    public String getProtocol() {
        return "file";
    }

    public VirtualFile findFileByIoFile(File file) {
        if (file == null) {
            $$$reportNull$$$0(0);
        }
        if (file.exists()) {
            return new CoreLocalVirtualFile(this, file);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    public VirtualFile findFileByPath(String str) {
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return findFileByIoFile(new File(str));
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    public VirtualFile refreshAndFindFileByPath(String str) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return findFileByPath(str);
    }
}
