package org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.jar;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.BufferExposingByteArrayInputStream;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;

/* JADX INFO: loaded from: classes3.dex */
final class CoreJarVirtualFile extends VirtualFile {
    private VirtualFile[] myChildren;
    private final CoreJarHandler myHandler;
    private final long myLength;
    private final CharSequence myName;
    private final VirtualFile myParent;
    private final long myTimestamp;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "name";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/impl/jar/CoreJarVirtualFile";
                break;
            default:
                objArr[0] = "handler";
                break;
        }
        switch (i) {
            case 2:
                objArr[1] = "getName";
                break;
            case 3:
                objArr[1] = "getNameSequence";
                break;
            case 4:
                objArr[1] = "getFileSystem";
                break;
            case 5:
            case 6:
                objArr[1] = "getPath";
                break;
            case 7:
                objArr[1] = "contentsToByteArray";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/impl/jar/CoreJarVirtualFile";
                break;
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public long getModificationStamp() {
        return 0L;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public boolean isValid() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public boolean isWritable() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public void refresh(boolean z, boolean z2, Runnable runnable) {
    }

    CoreJarVirtualFile(CoreJarHandler coreJarHandler, CharSequence charSequence, long j, long j2, CoreJarVirtualFile coreJarVirtualFile) {
        if (coreJarHandler == null) {
            $$$reportNull$$$0(0);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(1);
        }
        this.myChildren = VirtualFile.EMPTY_ARRAY;
        this.myHandler = coreJarHandler;
        this.myName = charSequence;
        this.myLength = j;
        this.myTimestamp = j2;
        this.myParent = coreJarVirtualFile;
    }

    void setChildren(VirtualFile[] virtualFileArr) {
        this.myChildren = virtualFileArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public String getName() {
        String string = this.myName.toString();
        if (string == null) {
            $$$reportNull$$$0(2);
        }
        return string;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public CharSequence getNameSequence() {
        CharSequence charSequence = this.myName;
        if (charSequence == null) {
            $$$reportNull$$$0(3);
        }
        return charSequence;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public VirtualFileSystem getFileSystem() {
        CoreJarFileSystem fileSystem = this.myHandler.getFileSystem();
        if (fileSystem == null) {
            $$$reportNull$$$0(4);
        }
        return fileSystem;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public String getPath() {
        VirtualFile virtualFile = this.myParent;
        if (virtualFile == null) {
            String str = FileUtil.toSystemIndependentName(this.myHandler.getFile().getPath()) + "!/";
            if (str == null) {
                $$$reportNull$$$0(5);
            }
            return str;
        }
        String path = virtualFile.getPath();
        StringBuilder sb = new StringBuilder(path.length() + 1 + this.myName.length());
        sb.append(path);
        if (sb.charAt(sb.length() - 1) != '/') {
            sb.append('/');
        }
        sb.append(this.myName);
        String string = sb.toString();
        if (string == null) {
            $$$reportNull$$$0(6);
        }
        return string;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public boolean isDirectory() {
        return this.myLength < 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public VirtualFile getParent() {
        return this.myParent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public VirtualFile[] getChildren() {
        return this.myChildren;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public OutputStream getOutputStream(Object obj, long j, long j2) throws IOException {
        throw new UnsupportedOperationException("JarFileSystem is read-only");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public byte[] contentsToByteArray() throws IOException {
        byte[] bArrContentsToByteArray = this.myHandler.contentsToByteArray((String) CoreJarFileSystem.splitPath(getPath()).second);
        if (bArrContentsToByteArray == null) {
            $$$reportNull$$$0(7);
        }
        return bArrContentsToByteArray;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public long getTimeStamp() {
        return this.myTimestamp;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public long getLength() {
        return this.myLength;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public InputStream getInputStream() throws IOException {
        return new BufferExposingByteArrayInputStream(contentsToByteArray());
    }
}
