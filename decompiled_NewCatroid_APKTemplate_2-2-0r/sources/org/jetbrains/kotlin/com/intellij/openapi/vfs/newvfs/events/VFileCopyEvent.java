package org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events;

import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;

/* JADX INFO: loaded from: classes3.dex */
public final class VFileCopyEvent extends VFileEvent {
    private final VirtualFile myFile;
    private final String myNewChildName;
    private final VirtualFile myNewParent;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 5 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 5 || i == 6 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "newParent";
                break;
            case 2:
                objArr[0] = "newChildName";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/newvfs/events/VFileCopyEvent";
                break;
            default:
                objArr[0] = "file";
                break;
        }
        if (i == 3) {
            objArr[1] = "getFile";
        } else if (i == 4) {
            objArr[1] = "getNewParent";
        } else if (i == 5) {
            objArr[1] = "getNewChildName";
        } else if (i == 6) {
            objArr[1] = "computePath";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/newvfs/events/VFileCopyEvent";
        } else {
            objArr[1] = "getFileSystem";
        }
        if (i != 3 && i != 4 && i != 5 && i != 6 && i != 7) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileEvent
    public VirtualFile getFile() {
        VirtualFile virtualFile = this.myFile;
        if (virtualFile == null) {
            $$$reportNull$$$0(3);
        }
        return virtualFile;
    }

    public VirtualFile getNewParent() {
        VirtualFile virtualFile = this.myNewParent;
        if (virtualFile == null) {
            $$$reportNull$$$0(4);
        }
        return virtualFile;
    }

    public String getNewChildName() {
        String str = this.myNewChildName;
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        return str;
    }

    public String toString() {
        return "VfsEvent[copy " + this.myFile + " to " + this.myNewParent + " as " + this.myNewChildName + "]";
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileEvent
    public VirtualFileSystem getFileSystem() {
        VirtualFileSystem fileSystem = this.myFile.getFileSystem();
        if (fileSystem == null) {
            $$$reportNull$$$0(7);
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
        VFileCopyEvent vFileCopyEvent = (VFileCopyEvent) obj;
        return this.myFile.equals(vFileCopyEvent.myFile) && this.myNewChildName.equals(vFileCopyEvent.myNewChildName) && this.myNewParent.equals(vFileCopyEvent.myNewParent);
    }

    public int hashCode() {
        return (((this.myFile.hashCode() * 31) + this.myNewParent.hashCode()) * 31) + this.myNewChildName.hashCode();
    }
}
