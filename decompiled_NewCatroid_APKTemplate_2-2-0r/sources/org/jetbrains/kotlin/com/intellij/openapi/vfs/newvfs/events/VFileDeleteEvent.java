package org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events;

import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;

/* JADX INFO: loaded from: classes3.dex */
public final class VFileDeleteEvent extends VFileEvent {
    private final VirtualFile myFile;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3) ? 2 : 3];
        if (i == 1 || i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/newvfs/events/VFileDeleteEvent";
        } else {
            objArr[0] = "file";
        }
        if (i == 1) {
            objArr[1] = "getFile";
        } else if (i == 2) {
            objArr[1] = "computePath";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/newvfs/events/VFileDeleteEvent";
        } else {
            objArr[1] = "getFileSystem";
        }
        if (i != 1 && i != 2 && i != 3) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileEvent
    public VirtualFile getFile() {
        VirtualFile virtualFile = this.myFile;
        if (virtualFile == null) {
            $$$reportNull$$$0(1);
        }
        return virtualFile;
    }

    public String toString() {
        return "VfsEvent[deleted: " + this.myFile.getUrl() + "]";
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileEvent
    public VirtualFileSystem getFileSystem() {
        VirtualFileSystem fileSystem = this.myFile.getFileSystem();
        if (fileSystem == null) {
            $$$reportNull$$$0(3);
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
        return this.myFile.equals(((VFileDeleteEvent) obj).myFile);
    }

    public int hashCode() {
        return this.myFile.hashCode();
    }
}
