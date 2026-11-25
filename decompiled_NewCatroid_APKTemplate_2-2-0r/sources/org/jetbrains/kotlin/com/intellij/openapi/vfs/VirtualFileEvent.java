package org.jetbrains.kotlin.com.intellij.openapi.vfs;

import java.util.EventObject;

/* JADX INFO: loaded from: classes3.dex */
public class VirtualFileEvent extends EventObject {
    private final VirtualFile myFile;
    private final long myNewModificationStamp;
    private final long myOldModificationStamp;
    private final VirtualFile myParent;
    private final Object myRequestor;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileEvent";
        } else {
            objArr[0] = "file";
        }
        if (i == 1) {
            objArr[1] = "getFile";
        } else if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileEvent";
        } else {
            objArr[1] = "getFileName";
        }
        if (i != 1 && i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VirtualFileEvent(Object obj, VirtualFile virtualFile, VirtualFile virtualFile2, long j, long j2) {
        super(virtualFile);
        if (virtualFile == null) {
            $$$reportNull$$$0(0);
        }
        this.myRequestor = obj;
        this.myFile = virtualFile;
        this.myParent = virtualFile2;
        this.myOldModificationStamp = j;
        this.myNewModificationStamp = j2;
    }

    public VirtualFile getFile() {
        VirtualFile virtualFile = this.myFile;
        if (virtualFile == null) {
            $$$reportNull$$$0(1);
        }
        return virtualFile;
    }

    public VirtualFile getParent() {
        return this.myParent;
    }

    public Object getRequestor() {
        return this.myRequestor;
    }
}
