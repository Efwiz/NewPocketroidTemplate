package org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events;

import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;

/* JADX INFO: loaded from: classes3.dex */
public final class VFileContentChangeEvent extends VFileEvent {
    private final VirtualFile myFile;
    private final long myNewLength;
    private final long myNewModificationStamp;
    private final long myNewTimestamp;
    private final long myOldLength;
    private final long myOldModificationStamp;
    private final long myOldTimestamp;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4) ? 2 : 3];
        if (i == 2 || i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/newvfs/events/VFileContentChangeEvent";
        } else {
            objArr[0] = "file";
        }
        if (i == 2) {
            objArr[1] = "getFile";
        } else if (i == 3) {
            objArr[1] = "computePath";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/newvfs/events/VFileContentChangeEvent";
        } else {
            objArr[1] = "getFileSystem";
        }
        if (i != 2 && i != 3 && i != 4) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileEvent
    public VirtualFile getFile() {
        VirtualFile virtualFile = this.myFile;
        if (virtualFile == null) {
            $$$reportNull$$$0(2);
        }
        return virtualFile;
    }

    public long getModificationStamp() {
        return this.myNewModificationStamp;
    }

    public long getOldModificationStamp() {
        return this.myOldModificationStamp;
    }

    public String toString() {
        return "VfsEvent[update: " + this.myFile.getPresentableUrl() + ", oldTimestamp:" + this.myOldTimestamp + ", newTimestamp:" + this.myNewTimestamp + ", oldModificationStamp:" + this.myOldModificationStamp + ", newModificationStamp:" + this.myNewModificationStamp + ", oldLength:" + this.myOldLength + ", newLength:" + this.myNewLength + "]";
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileEvent
    public VirtualFileSystem getFileSystem() {
        VirtualFileSystem fileSystem = this.myFile.getFileSystem();
        if (fileSystem == null) {
            $$$reportNull$$$0(4);
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
        VFileContentChangeEvent vFileContentChangeEvent = (VFileContentChangeEvent) obj;
        return this.myNewModificationStamp == vFileContentChangeEvent.myNewModificationStamp && this.myOldModificationStamp == vFileContentChangeEvent.myOldModificationStamp && this.myFile.equals(vFileContentChangeEvent.myFile);
    }

    public int hashCode() {
        int iHashCode = this.myFile.hashCode() * 31;
        long j = this.myOldModificationStamp;
        int i = (iHashCode + ((int) (j ^ (j >>> 32)))) * 31;
        long j2 = this.myNewModificationStamp;
        return i + ((int) (j2 ^ (j2 >>> 32)));
    }
}
