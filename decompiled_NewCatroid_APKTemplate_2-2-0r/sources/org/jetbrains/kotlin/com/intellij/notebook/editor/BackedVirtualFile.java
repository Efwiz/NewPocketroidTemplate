package org.jetbrains.kotlin.com.intellij.notebook.editor;

import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: loaded from: classes3.dex */
public interface BackedVirtualFile {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/notebook/editor/BackedVirtualFile";
        } else {
            objArr[0] = "file";
        }
        if (i == 1 || i == 2) {
            objArr[1] = "getOriginFileIfBacked";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/notebook/editor/BackedVirtualFile";
        }
        if (i != 1 && i != 2) {
            objArr[2] = "getOriginFileIfBacked";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    VirtualFile getOriginFile();

    /* JADX WARN: Multi-variable type inference failed */
    static VirtualFile getOriginFileIfBacked(VirtualFile virtualFile) {
        if (virtualFile == 0) {
            $$$reportNull$$$0(0);
        }
        if (!(virtualFile instanceof BackedVirtualFile)) {
            if (virtualFile == 0) {
                $$$reportNull$$$0(2);
            }
            return virtualFile;
        }
        VirtualFile originFile = ((BackedVirtualFile) virtualFile).getOriginFile();
        if (originFile == null) {
            $$$reportNull$$$0(1);
        }
        return originFile;
    }
}
