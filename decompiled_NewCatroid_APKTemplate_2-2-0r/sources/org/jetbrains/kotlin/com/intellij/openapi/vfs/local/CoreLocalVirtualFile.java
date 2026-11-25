package org.jetbrains.kotlin.com.intellij.openapi.vfs.local;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullFactory;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;

/* JADX INFO: loaded from: classes3.dex */
public class CoreLocalVirtualFile extends VirtualFile {
    private VirtualFile[] myChildren;
    private final CoreLocalFileSystem myFileSystem;
    private final File myIoFile;
    private final NotNullLazyValue<Boolean> myIsDirectory;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
                objArr[0] = "ioFile";
                break;
            case 2:
            default:
                objArr[0] = "fileSystem";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/local/CoreLocalVirtualFile";
                break;
        }
        switch (i) {
            case 4:
                objArr[1] = "getName";
                break;
            case 5:
                objArr[1] = "getFileSystem";
                break;
            case 6:
                objArr[1] = "getPath";
                break;
            case 7:
                objArr[1] = "contentsToByteArray";
                break;
            case 8:
                objArr[1] = "getInputStream";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/local/CoreLocalVirtualFile";
                break;
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
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

    public CoreLocalVirtualFile(CoreLocalFileSystem coreLocalFileSystem, final File file) {
        if (coreLocalFileSystem == null) {
            $$$reportNull$$$0(0);
        }
        if (file == null) {
            $$$reportNull$$$0(1);
        }
        this.myFileSystem = coreLocalFileSystem;
        this.myIoFile = file;
        Objects.requireNonNull(file);
        this.myIsDirectory = NotNullLazyValue.createValue(new NotNullFactory() { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.local.CoreLocalVirtualFile$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.NotNullFactory, org.jetbrains.kotlin.com.intellij.openapi.util.Factory
            public final Object create() {
                return Boolean.valueOf(file.isDirectory());
            }
        });
    }

    public CoreLocalVirtualFile(CoreLocalFileSystem coreLocalFileSystem, File file, boolean z) {
        if (coreLocalFileSystem == null) {
            $$$reportNull$$$0(2);
        }
        if (file == null) {
            $$$reportNull$$$0(3);
        }
        this.myFileSystem = coreLocalFileSystem;
        this.myIoFile = file;
        this.myIsDirectory = NotNullLazyValue.createConstantValue(Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public String getName() {
        String name2 = this.myIoFile.getName();
        if (name2 == null) {
            $$$reportNull$$$0(4);
        }
        return name2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public VirtualFileSystem getFileSystem() {
        CoreLocalFileSystem coreLocalFileSystem = this.myFileSystem;
        if (coreLocalFileSystem == null) {
            $$$reportNull$$$0(5);
        }
        return coreLocalFileSystem;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public String getPath() {
        String systemIndependentName = FileUtil.toSystemIndependentName(this.myIoFile.getAbsolutePath());
        if (systemIndependentName == null) {
            $$$reportNull$$$0(6);
        }
        return systemIndependentName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public boolean isDirectory() {
        return this.myIsDirectory.getValue().booleanValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public VirtualFile getParent() {
        File parentFile = this.myIoFile.getParentFile();
        if (parentFile != null) {
            return new CoreLocalVirtualFile(this.myFileSystem, parentFile, true);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public VirtualFile[] getChildren() {
        VirtualFile[] virtualFileArr = this.myChildren;
        if (virtualFileArr == null) {
            File[] fileArrListFiles = this.myIoFile.listFiles();
            if (fileArrListFiles == null || fileArrListFiles.length == 0) {
                virtualFileArr = EMPTY_ARRAY;
            } else {
                VirtualFile[] virtualFileArr2 = new VirtualFile[fileArrListFiles.length];
                for (int i = 0; i < fileArrListFiles.length; i++) {
                    virtualFileArr2[i] = new CoreLocalVirtualFile(this.myFileSystem, fileArrListFiles[i]);
                }
                virtualFileArr = virtualFileArr2;
            }
            this.myChildren = virtualFileArr;
        }
        return virtualFileArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public OutputStream getOutputStream(Object obj, long j, long j2) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public byte[] contentsToByteArray() throws IOException {
        byte[] bArrLoadFileBytes = FileUtil.loadFileBytes(this.myIoFile);
        if (bArrLoadFileBytes == null) {
            $$$reportNull$$$0(7);
        }
        return bArrLoadFileBytes;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public long getTimeStamp() {
        return this.myIoFile.lastModified();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public long getLength() {
        return this.myIoFile.length();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public InputStream getInputStream() throws IOException {
        InputStream inputStreamInputStreamSkippingBOM = VfsUtilCore.inputStreamSkippingBOM(new BufferedInputStream(new FileInputStream(this.myIoFile)), this);
        if (inputStreamInputStreamSkippingBOM == null) {
            $$$reportNull$$$0(8);
        }
        return inputStreamInputStreamSkippingBOM;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.myIoFile.equals(((CoreLocalVirtualFile) obj).myIoFile);
    }

    public int hashCode() {
        return this.myIoFile.hashCode();
    }
}
