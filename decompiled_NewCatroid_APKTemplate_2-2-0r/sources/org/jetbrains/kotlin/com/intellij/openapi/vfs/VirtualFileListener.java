package org.jetbrains.kotlin.com.intellij.openapi.vfs;

import java.util.EventListener;

/* JADX INFO: loaded from: classes3.dex */
public interface VirtualFileListener extends EventListener {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "event";
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileListener";
        switch (i) {
            case 1:
                objArr[2] = "contentsChanged";
                break;
            case 2:
                objArr[2] = "fileCreated";
                break;
            case 3:
                objArr[2] = "fileDeleted";
                break;
            case 4:
                objArr[2] = "fileMoved";
                break;
            case 5:
                objArr[2] = "fileCopied";
                break;
            case 6:
                objArr[2] = "beforePropertyChange";
                break;
            case 7:
                objArr[2] = "beforeContentsChange";
                break;
            case 8:
                objArr[2] = "beforeFileDeletion";
                break;
            case 9:
                objArr[2] = "beforeFileMovement";
                break;
            default:
                objArr[2] = "propertyChanged";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    default void beforeContentsChange(VirtualFileEvent virtualFileEvent) {
        if (virtualFileEvent == null) {
            $$$reportNull$$$0(7);
        }
    }

    default void beforeFileDeletion(VirtualFileEvent virtualFileEvent) {
        if (virtualFileEvent == null) {
            $$$reportNull$$$0(8);
        }
    }

    default void beforeFileMovement(VirtualFileMoveEvent virtualFileMoveEvent) {
        if (virtualFileMoveEvent == null) {
            $$$reportNull$$$0(9);
        }
    }

    default void beforePropertyChange(VirtualFilePropertyEvent virtualFilePropertyEvent) {
        if (virtualFilePropertyEvent == null) {
            $$$reportNull$$$0(6);
        }
    }

    default void contentsChanged(VirtualFileEvent virtualFileEvent) {
        if (virtualFileEvent == null) {
            $$$reportNull$$$0(1);
        }
    }

    default void fileCreated(VirtualFileEvent virtualFileEvent) {
        if (virtualFileEvent == null) {
            $$$reportNull$$$0(2);
        }
    }

    default void fileDeleted(VirtualFileEvent virtualFileEvent) {
        if (virtualFileEvent == null) {
            $$$reportNull$$$0(3);
        }
    }

    default void fileMoved(VirtualFileMoveEvent virtualFileMoveEvent) {
        if (virtualFileMoveEvent == null) {
            $$$reportNull$$$0(4);
        }
    }

    default void propertyChanged(VirtualFilePropertyEvent virtualFilePropertyEvent) {
        if (virtualFilePropertyEvent == null) {
            $$$reportNull$$$0(0);
        }
    }

    default void fileCopied(VirtualFileCopyEvent virtualFileCopyEvent) {
        if (virtualFileCopyEvent == null) {
            $$$reportNull$$$0(5);
        }
        fileCreated(virtualFileCopyEvent);
    }
}
