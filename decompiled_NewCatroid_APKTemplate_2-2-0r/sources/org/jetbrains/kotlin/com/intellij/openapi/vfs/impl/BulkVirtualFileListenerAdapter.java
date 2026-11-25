package org.jetbrains.kotlin.com.intellij.openapi.vfs.impl;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileCopyEvent;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileEvent;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileListener;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileMoveEvent;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFilePropertyEvent;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.BulkFileListener;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileContentChangeEvent;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileCopyEvent;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileCreateEvent;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileDeleteEvent;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileMoveEvent;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFilePropertyChangeEvent;

/* JADX INFO: loaded from: classes3.dex */
public class BulkVirtualFileListenerAdapter implements BulkFileListener {
    private final VirtualFileListener myAdapted;
    private final VirtualFileSystem myFileSystem;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 2 || i == 3) {
            objArr[0] = "events";
        } else if (i == 5 || i == 7) {
            objArr[0] = "event";
        } else {
            objArr[0] = "adapted";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/impl/BulkVirtualFileListenerAdapter";
        switch (i) {
            case 2:
                objArr[2] = "before";
                break;
            case 3:
                objArr[2] = "after";
                break;
            case 4:
            case 5:
                objArr[2] = "fireAfter";
                break;
            case 6:
            case 7:
                objArr[2] = "fireBefore";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BulkVirtualFileListenerAdapter(VirtualFileListener virtualFileListener) {
        this(virtualFileListener, null);
        if (virtualFileListener == null) {
            $$$reportNull$$$0(0);
        }
    }

    public BulkVirtualFileListenerAdapter(VirtualFileListener virtualFileListener, VirtualFileSystem virtualFileSystem) {
        if (virtualFileListener == null) {
            $$$reportNull$$$0(1);
        }
        this.myAdapted = virtualFileListener;
        this.myFileSystem = virtualFileSystem;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.BulkFileListener
    public void before(List<? extends VFileEvent> list) {
        if (list == null) {
            $$$reportNull$$$0(2);
        }
        for (VFileEvent vFileEvent : list) {
            VirtualFileSystem virtualFileSystem = this.myFileSystem;
            if (virtualFileSystem == null || virtualFileSystem == vFileEvent.getFileSystem()) {
                fireBefore(this.myAdapted, vFileEvent);
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.BulkFileListener
    public void after(List<? extends VFileEvent> list) {
        if (list == null) {
            $$$reportNull$$$0(3);
        }
        for (VFileEvent vFileEvent : list) {
            VirtualFileSystem virtualFileSystem = this.myFileSystem;
            if (virtualFileSystem == null || virtualFileSystem == vFileEvent.getFileSystem()) {
                fireAfter(this.myAdapted, vFileEvent);
            }
        }
    }

    public static void fireAfter(VirtualFileListener virtualFileListener, VFileEvent vFileEvent) {
        if (virtualFileListener == null) {
            $$$reportNull$$$0(4);
        }
        if (vFileEvent == null) {
            $$$reportNull$$$0(5);
        }
        if (vFileEvent instanceof VFileContentChangeEvent) {
            VFileContentChangeEvent vFileContentChangeEvent = (VFileContentChangeEvent) vFileEvent;
            VirtualFile file = vFileContentChangeEvent.getFile();
            virtualFileListener.contentsChanged(new VirtualFileEvent(vFileEvent.getRequestor(), file, file.getParent(), vFileContentChangeEvent.getOldModificationStamp(), vFileContentChangeEvent.getModificationStamp()));
            return;
        }
        if (vFileEvent instanceof VFileCopyEvent) {
            VFileCopyEvent vFileCopyEvent = (VFileCopyEvent) vFileEvent;
            VirtualFile file2 = vFileCopyEvent.getFile();
            VirtualFile virtualFileFindChild = vFileCopyEvent.getNewParent().findChild(vFileCopyEvent.getNewChildName());
            if (virtualFileFindChild != null) {
                virtualFileListener.fileCopied(new VirtualFileCopyEvent(vFileEvent.getRequestor(), file2, virtualFileFindChild));
                return;
            }
            return;
        }
        if (vFileEvent instanceof VFileCreateEvent) {
            VFileCreateEvent vFileCreateEvent = (VFileCreateEvent) vFileEvent;
            VirtualFile file3 = vFileCreateEvent.getFile();
            if (file3 != null) {
                virtualFileListener.fileCreated(new VirtualFileEvent(vFileEvent.getRequestor(), file3, vFileCreateEvent.getParent(), 0L, 0L));
                return;
            }
            return;
        }
        if (vFileEvent instanceof VFileDeleteEvent) {
            VFileDeleteEvent vFileDeleteEvent = (VFileDeleteEvent) vFileEvent;
            virtualFileListener.fileDeleted(new VirtualFileEvent(vFileEvent.getRequestor(), vFileDeleteEvent.getFile(), vFileDeleteEvent.getFile().getParent(), 0L, 0L));
        } else if (vFileEvent instanceof VFileMoveEvent) {
            VFileMoveEvent vFileMoveEvent = (VFileMoveEvent) vFileEvent;
            virtualFileListener.fileMoved(new VirtualFileMoveEvent(vFileEvent.getRequestor(), vFileMoveEvent.getFile(), vFileMoveEvent.getOldParent(), vFileMoveEvent.getNewParent()));
        } else if (vFileEvent instanceof VFilePropertyChangeEvent) {
            VFilePropertyChangeEvent vFilePropertyChangeEvent = (VFilePropertyChangeEvent) vFileEvent;
            virtualFileListener.propertyChanged(new VirtualFilePropertyEvent(vFileEvent.getRequestor(), vFilePropertyChangeEvent.getFile(), vFilePropertyChangeEvent.getPropertyName(), vFilePropertyChangeEvent.getOldValue(), vFilePropertyChangeEvent.getNewValue()));
        }
    }

    public static void fireBefore(VirtualFileListener virtualFileListener, VFileEvent vFileEvent) {
        if (virtualFileListener == null) {
            $$$reportNull$$$0(6);
        }
        if (vFileEvent == null) {
            $$$reportNull$$$0(7);
        }
        if (vFileEvent instanceof VFileContentChangeEvent) {
            VFileContentChangeEvent vFileContentChangeEvent = (VFileContentChangeEvent) vFileEvent;
            VirtualFile file = vFileContentChangeEvent.getFile();
            virtualFileListener.beforeContentsChange(new VirtualFileEvent(vFileEvent.getRequestor(), file, file.getParent(), vFileContentChangeEvent.getOldModificationStamp(), vFileContentChangeEvent.getModificationStamp()));
        } else if (vFileEvent instanceof VFileDeleteEvent) {
            VFileDeleteEvent vFileDeleteEvent = (VFileDeleteEvent) vFileEvent;
            virtualFileListener.beforeFileDeletion(new VirtualFileEvent(vFileEvent.getRequestor(), vFileDeleteEvent.getFile(), vFileDeleteEvent.getFile().getParent(), 0L, 0L));
        } else if (vFileEvent instanceof VFileMoveEvent) {
            VFileMoveEvent vFileMoveEvent = (VFileMoveEvent) vFileEvent;
            virtualFileListener.beforeFileMovement(new VirtualFileMoveEvent(vFileEvent.getRequestor(), vFileMoveEvent.getFile(), vFileMoveEvent.getOldParent(), vFileMoveEvent.getNewParent()));
        } else if (vFileEvent instanceof VFilePropertyChangeEvent) {
            VFilePropertyChangeEvent vFilePropertyChangeEvent = (VFilePropertyChangeEvent) vFileEvent;
            virtualFileListener.beforePropertyChange(new VirtualFilePropertyEvent(vFileEvent.getRequestor(), vFilePropertyChangeEvent.getFile(), vFilePropertyChangeEvent.getPropertyName(), vFilePropertyChangeEvent.getOldValue(), vFilePropertyChangeEvent.getNewValue()));
        }
    }
}
