package org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events;

import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;

/* JADX INFO: loaded from: classes3.dex */
public final class VFileMoveEvent extends VFileEvent {
    private final VirtualFile myFile;
    private final VirtualFile myNewParent;
    private final VirtualFile myOldParent;

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
            case 8:
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
                objArr[0] = "newParent";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/newvfs/events/VFileMoveEvent";
                break;
            default:
                objArr[0] = "file";
                break;
        }
        switch (i) {
            case 2:
                objArr[1] = "getFile";
                break;
            case 3:
                objArr[1] = "getNewParent";
                break;
            case 4:
                objArr[1] = "getPath";
                break;
            case 5:
                objArr[1] = "computePath";
                break;
            case 6:
                objArr[1] = "getFileSystem";
                break;
            case 7:
                objArr[1] = "getOldPath";
                break;
            case 8:
                objArr[1] = "getNewPath";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/newvfs/events/VFileMoveEvent";
                break;
        }
        switch (i) {
            case 2:
            case 3:
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
            case 2:
            case 3:
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

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileEvent
    public VirtualFile getFile() {
        VirtualFile virtualFile = this.myFile;
        if (virtualFile == null) {
            $$$reportNull$$$0(2);
        }
        return virtualFile;
    }

    public VirtualFile getNewParent() {
        VirtualFile virtualFile = this.myNewParent;
        if (virtualFile == null) {
            $$$reportNull$$$0(3);
        }
        return virtualFile;
    }

    public VirtualFile getOldParent() {
        return this.myOldParent;
    }

    public String toString() {
        return "VfsEvent[move " + this.myFile.getName() + " from " + this.myOldParent + " to " + this.myNewParent + "]";
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileEvent
    public VirtualFileSystem getFileSystem() {
        VirtualFileSystem fileSystem = this.myFile.getFileSystem();
        if (fileSystem == null) {
            $$$reportNull$$$0(6);
        }
        return fileSystem;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        VFileMoveEvent vFileMoveEvent = (VFileMoveEvent) obj;
        return this.myFile.equals(vFileMoveEvent.myFile) && this.myNewParent.equals(vFileMoveEvent.myNewParent) && this.myOldParent.equals(vFileMoveEvent.myOldParent);
    }

    public int hashCode() {
        return (((this.myFile.hashCode() * 31) + this.myOldParent.hashCode()) * 31) + this.myNewParent.hashCode();
    }
}
