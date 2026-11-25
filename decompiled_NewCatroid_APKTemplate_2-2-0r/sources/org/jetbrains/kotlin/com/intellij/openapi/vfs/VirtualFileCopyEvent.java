package org.jetbrains.kotlin.com.intellij.openapi.vfs;

import org.eclipse.jgit.lib.ReflogEntry;

/* JADX INFO: loaded from: classes3.dex */
public class VirtualFileCopyEvent extends VirtualFileEvent {
    private final VirtualFile myOriginalFile;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i == 1) {
            objArr[0] = ReflogEntry.PREFIX_CREATED;
        } else if (i != 2) {
            objArr[0] = "original";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileCopyEvent";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileCopyEvent";
        } else {
            objArr[1] = "getOriginalFile";
        }
        if (i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VirtualFileCopyEvent(Object obj, VirtualFile virtualFile, VirtualFile virtualFile2) {
        super(obj, virtualFile2, virtualFile2.getParent(), 0L, 0L);
        if (virtualFile == null) {
            $$$reportNull$$$0(0);
        }
        if (virtualFile2 == null) {
            $$$reportNull$$$0(1);
        }
        this.myOriginalFile = virtualFile;
    }

    public VirtualFile getOriginalFile() {
        VirtualFile virtualFile = this.myOriginalFile;
        if (virtualFile == null) {
            $$$reportNull$$$0(2);
        }
        return virtualFile;
    }
}
