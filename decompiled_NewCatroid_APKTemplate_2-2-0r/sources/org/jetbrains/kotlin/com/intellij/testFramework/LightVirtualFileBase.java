package org.jetbrains.kotlin.com.intellij.testFramework;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.IOException;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.DeprecatedVirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.NonPhysicalFileSystem;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class LightVirtualFileBase extends VirtualFile {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final MyVirtualFileSystem ourFileSystem = new MyVirtualFileSystem();
    private FileType myFileType;
    private long myModStamp;
    private String myName;
    private VirtualFile myOriginalFile;
    private boolean myIsWritable = true;
    private boolean myValid = true;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 3:
            case 4:
            case 6:
            case 8:
            case 9:
            case 10:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 5:
            case 7:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 6:
            case 8:
            case 9:
            case 10:
                i2 = 3;
                break;
            case 5:
            case 7:
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 3:
                objArr[0] = "newName";
                break;
            case 4:
            case 6:
                objArr[0] = "name";
                break;
            case 5:
            case 7:
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/testFramework/LightVirtualFileBase";
                break;
            case 8:
                objArr[0] = "newParent";
                break;
            case 9:
            case 10:
                objArr[0] = FirebaseAnalytics.Param.CONTENT;
                break;
        }
        switch (i) {
            case 1:
                objArr[1] = "getPath";
                break;
            case 2:
                objArr[1] = "getName";
                break;
            case 3:
            case 4:
            case 6:
            case 8:
            case 9:
            case 10:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/testFramework/LightVirtualFileBase";
                break;
            case 5:
                objArr[1] = "createChildDirectory";
                break;
            case 7:
                objArr[1] = "createChildData";
                break;
            default:
                objArr[1] = "getFileSystem";
                break;
        }
        switch (i) {
            case 3:
                objArr[2] = "rename";
                break;
            case 4:
                objArr[2] = "createChildDirectory";
                break;
            case 6:
                objArr[2] = "createChildData";
                break;
            case 8:
                objArr[2] = "move";
                break;
            case 9:
            case 10:
                objArr[2] = "setBinaryContent";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 3:
            case 4:
            case 6:
            case 8:
            case 9:
            case 10:
                throw new IllegalArgumentException(str2);
            case 5:
            case 7:
            default:
                throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public VirtualFile getParent() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public long getTimeStamp() {
        return 0L;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public boolean isDirectory() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public void refresh(boolean z, boolean z2, Runnable runnable) {
    }

    public LightVirtualFileBase(String str, FileType fileType, long j) {
        this.myName = str;
        this.myFileType = fileType;
        this.myModStamp = j;
    }

    public void setFileType(FileType fileType) {
        this.myFileType = fileType;
    }

    public VirtualFile getOriginalFile() {
        return this.myOriginalFile;
    }

    public void setOriginalFile(VirtualFile virtualFile) {
        this.myOriginalFile = virtualFile;
    }

    private static final class MyVirtualFileSystem extends DeprecatedVirtualFileSystem implements NonPhysicalFileSystem {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            objArr[0] = "path";
            objArr[1] = "org/jetbrains/kotlin/com/intellij/testFramework/LightVirtualFileBase$MyVirtualFileSystem";
            if (i != 1) {
                objArr[2] = "findFileByPath";
            } else {
                objArr[2] = "refreshAndFindFileByPath";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
        public VirtualFile findFileByPath(String str) {
            if (str != null) {
                return null;
            }
            $$$reportNull$$$0(0);
            return null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
        public void refresh(boolean z) {
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
        public VirtualFile refreshAndFindFileByPath(String str) {
            if (str != null) {
                return null;
            }
            $$$reportNull$$$0(1);
            return null;
        }

        private MyVirtualFileSystem() {
            startEventPropagation();
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
        public String getProtocol() {
            return "mock";
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public VirtualFileSystem getFileSystem() {
        MyVirtualFileSystem myVirtualFileSystem = ourFileSystem;
        if (myVirtualFileSystem == null) {
            $$$reportNull$$$0(0);
        }
        return myVirtualFileSystem;
    }

    public FileType getAssignedFileType() {
        return this.myFileType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public String getPath() {
        VirtualFile parent = getParent();
        String str = (parent == null ? "" : parent.getPath()) + "/" + getName();
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public String getName() {
        String str = this.myName;
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public boolean isWritable() {
        return this.myIsWritable;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public boolean isValid() {
        return this.myValid;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public VirtualFile[] getChildren() {
        return EMPTY_ARRAY;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public long getModificationStamp() {
        return this.myModStamp;
    }

    protected void setModificationStamp(long j) {
        this.myModStamp = j;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public long getLength() {
        try {
            return contentsToByteArray().length;
        } catch (IOException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public void setWritable(boolean z) {
        this.myIsWritable = z;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public void rename(Object obj, String str) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        assertWritable();
        this.myName = str;
    }

    void assertWritable() {
        if (!isWritable()) {
            throw new IncorrectOperationException("File is not writable: " + this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public VirtualFile createChildDirectory(Object obj, String str) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        assertWritable();
        VirtualFile virtualFileCreateChildDirectory = super.createChildDirectory(obj, str);
        if (virtualFileCreateChildDirectory == null) {
            $$$reportNull$$$0(5);
        }
        return virtualFileCreateChildDirectory;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public VirtualFile createChildData(Object obj, String str) throws IOException {
        if (str == null) {
            $$$reportNull$$$0(6);
        }
        assertWritable();
        VirtualFile virtualFileCreateChildData = super.createChildData(obj, str);
        if (virtualFileCreateChildData == null) {
            $$$reportNull$$$0(7);
        }
        return virtualFileCreateChildData;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public void delete(Object obj) throws IOException {
        assertWritable();
        super.delete(obj);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public void move(Object obj, VirtualFile virtualFile) throws Throwable {
        if (virtualFile == null) {
            $$$reportNull$$$0(8);
        }
        assertWritable();
        super.move(obj, virtualFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public void setBinaryContent(byte[] bArr, long j, long j2) throws IOException {
        if (bArr == null) {
            $$$reportNull$$$0(9);
        }
        assertWritable();
        super.setBinaryContent(bArr, j, j2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile
    public void setBinaryContent(byte[] bArr, long j, long j2, Object obj) throws IOException {
        if (bArr == null) {
            $$$reportNull$$$0(10);
        }
        assertWritable();
        super.setBinaryContent(bArr, j, j2, obj);
    }
}
