package org.jetbrains.kotlin.com.intellij.openapi.vfs;

/* JADX INFO: loaded from: classes3.dex */
public class VirtualFileMoveEvent extends VirtualFileEvent {
    private final VirtualFile myNewParent;
    private final VirtualFile myOldParent;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "oldParent";
        } else if (i == 2) {
            objArr[0] = "newParent";
        } else if (i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileMoveEvent";
        } else {
            objArr[0] = "file";
        }
        if (i == 3) {
            objArr[1] = "getOldParent";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileMoveEvent";
        } else {
            objArr[1] = "getNewParent";
        }
        if (i != 3 && i != 4) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VirtualFileMoveEvent(Object obj, VirtualFile virtualFile, VirtualFile virtualFile2, VirtualFile virtualFile3) {
        super(obj, virtualFile, virtualFile.getParent(), 0L, 0L);
        if (virtualFile == null) {
            $$$reportNull$$$0(0);
        }
        if (virtualFile2 == null) {
            $$$reportNull$$$0(1);
        }
        if (virtualFile3 == null) {
            $$$reportNull$$$0(2);
        }
        this.myOldParent = virtualFile2;
        this.myNewParent = virtualFile3;
    }

    public VirtualFile getOldParent() {
        VirtualFile virtualFile = this.myOldParent;
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
}
