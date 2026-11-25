package org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events;

import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;

/* JADX INFO: loaded from: classes3.dex */
public final class VFileCreateEvent extends VFileEvent {
    private final int myChildNameId;
    private final ChildInfo[] myChildren;
    private VirtualFile myCreatedFile;
    private final boolean myDirectory;
    private final VirtualFile myParent;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "childName";
        } else if (i == 2 || i == 3 || i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/newvfs/events/VFileCreateEvent";
        } else {
            objArr[0] = "parent";
        }
        if (i == 2) {
            objArr[1] = "getChildName";
        } else if (i == 3) {
            objArr[1] = "getParent";
        } else if (i == 4) {
            objArr[1] = "computePath";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/newvfs/events/VFileCreateEvent";
        } else {
            objArr[1] = "getFileSystem";
        }
        if (i != 2 && i != 3 && i != 4 && i != 5) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public String getChildName() {
        String string = VirtualFileManager.getInstance().getVFileName(this.myChildNameId).toString();
        if (string == null) {
            $$$reportNull$$$0(2);
        }
        return string;
    }

    public boolean isDirectory() {
        return this.myDirectory;
    }

    public VirtualFile getParent() {
        VirtualFile virtualFile = this.myParent;
        if (virtualFile == null) {
            $$$reportNull$$$0(3);
        }
        return virtualFile;
    }

    public boolean isEmptyDirectory() {
        ChildInfo[] childInfoArr;
        return isDirectory() && (childInfoArr = this.myChildren) != null && childInfoArr.length == 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileEvent
    public VirtualFile getFile() {
        VirtualFile virtualFile = this.myCreatedFile;
        if (virtualFile != null || !this.myParent.isValid()) {
            return virtualFile;
        }
        VirtualFile virtualFileFindChild = this.myParent.findChild(getChildName());
        this.myCreatedFile = virtualFileFindChild;
        return virtualFileFindChild;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileEvent
    public VirtualFileSystem getFileSystem() {
        VirtualFileSystem fileSystem = this.myParent.getFileSystem();
        if (fileSystem == null) {
            $$$reportNull$$$0(5);
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
        VFileCreateEvent vFileCreateEvent = (VFileCreateEvent) obj;
        return this.myDirectory == vFileCreateEvent.myDirectory && getChildName().equals(vFileCreateEvent.getChildName()) && this.myParent.equals(vFileCreateEvent.myParent);
    }

    public int hashCode() {
        return (((this.myParent.hashCode() * 31) + (this.myDirectory ? 1 : 0)) * 31) + getChildName().hashCode();
    }

    public String toString() {
        String strConcat;
        if (this.myDirectory) {
            strConcat = (isEmptyDirectory() ? "(empty) " : "").concat("dir ");
        } else {
            strConcat = "file ";
        }
        return "VfsEvent[create " + strConcat + FormatHelper.QUOTE + this.myParent.getUrl() + "/" + getChildName() + "']" + (this.myChildren != null ? " with " + this.myChildren.length + " children" : "");
    }
}
