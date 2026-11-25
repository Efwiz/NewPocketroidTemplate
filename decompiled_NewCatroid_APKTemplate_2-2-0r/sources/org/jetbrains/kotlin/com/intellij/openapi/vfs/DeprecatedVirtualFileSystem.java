package org.jetbrains.kotlin.com.intellij.openapi.vfs;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.io.IOException;
import java.util.function.Predicate;
import org.jetbrains.kotlin.com.intellij.openapi.application.Application;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPoint;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.BulkVirtualFileListenerAdapter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.util.EventDispatcher;
import org.jetbrains.kotlin.com.intellij.util.KeyedLazyInstance;
import org.jetbrains.kotlin.com.intellij.util.messages.MessageBusConnection;

/* JADX INFO: loaded from: classes3.dex */
public abstract class DeprecatedVirtualFileSystem extends VirtualFileSystem {
    private final EventDispatcher<VirtualFileListener> myEventDispatcher = EventDispatcher.create(VirtualFileListener.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 2:
            case 4:
            case 5:
            case 6:
            case 8:
            case 11:
            case 13:
            case 14:
            case 15:
                objArr[0] = "file";
                break;
            case 3:
            case 12:
                objArr[0] = "propertyName";
                break;
            case 7:
            case 22:
                objArr[0] = PsiTreeChangeEvent.PROP_FILE_NAME;
                break;
            case 9:
                objArr[0] = "originalFile";
                break;
            case 10:
                objArr[0] = "createdFile";
                break;
            case 16:
            case 17:
            case 19:
            case 25:
                objArr[0] = "vFile";
                break;
            case 18:
            case 26:
                objArr[0] = "newParent";
                break;
            case 20:
                objArr[0] = "newName";
                break;
            case 21:
            case 23:
                objArr[0] = "vDir";
                break;
            case 24:
                objArr[0] = "dirName";
                break;
            case 27:
                objArr[0] = "copyName";
                break;
            default:
                objArr[0] = ServiceSpecificExtraArgs.CastExtraArgs.LISTENER;
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/DeprecatedVirtualFileSystem";
        switch (i) {
            case 1:
                objArr[2] = "removeVirtualFileListener";
                break;
            case 2:
            case 3:
                objArr[2] = "firePropertyChanged";
                break;
            case 4:
                objArr[2] = "fireContentsChanged";
                break;
            case 5:
                objArr[2] = "fireFileCreated";
                break;
            case 6:
            case 7:
                objArr[2] = "fireFileDeleted";
                break;
            case 8:
                objArr[2] = "fireFileMoved";
                break;
            case 9:
            case 10:
                objArr[2] = "fireFileCopied";
                break;
            case 11:
            case 12:
                objArr[2] = "fireBeforePropertyChange";
                break;
            case 13:
                objArr[2] = "fireBeforeContentsChange";
                break;
            case 14:
                objArr[2] = "fireBeforeFileDeletion";
                break;
            case 15:
                objArr[2] = "fireBeforeFileMovement";
                break;
            case 16:
                objArr[2] = "deleteFile";
                break;
            case 17:
            case 18:
                objArr[2] = "moveFile";
                break;
            case 19:
            case 20:
                objArr[2] = "renameFile";
                break;
            case 21:
            case 22:
                objArr[2] = "createChildFile";
                break;
            case 23:
            case 24:
                objArr[2] = "createChildDirectory";
                break;
            case 25:
            case 26:
            case 27:
                objArr[2] = "copyFile";
                break;
            default:
                objArr[2] = "addVirtualFileListener";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    public boolean isReadOnly() {
        return true;
    }

    protected void startEventPropagation() {
        MessageBusConnection messageBusConnectionConnect;
        Application application = ApplicationManager.getApplication();
        if (application != null) {
            ExtensionPoint extensionPointIfRegistered = application.getExtensionArea().getExtensionPointIfRegistered(VirtualFileSystem.EP_NAME.getName());
            if (extensionPointIfRegistered != null) {
                messageBusConnectionConnect = application.getMessageBus().connect(ExtensionPointUtil.createExtensionDisposable(this, extensionPointIfRegistered, new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.DeprecatedVirtualFileSystem$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return this.f$0.m6507x4e829fdd((KeyedLazyInstance) obj);
                    }
                }));
            } else {
                messageBusConnectionConnect = application.getMessageBus().connect();
            }
            messageBusConnectionConnect.subscribe(VirtualFileManager.VFS_CHANGES, new BulkVirtualFileListenerAdapter((VirtualFileListener) this.myEventDispatcher.getMulticaster(), this));
        }
    }

    /* JADX INFO: renamed from: lambda$startEventPropagation$0$org-jetbrains-kotlin-com-intellij-openapi-vfs-DeprecatedVirtualFileSystem, reason: not valid java name */
    /* synthetic */ boolean m6507x4e829fdd(KeyedLazyInstance keyedLazyInstance) {
        return keyedLazyInstance.getKey().equals(getProtocol());
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    public void addVirtualFileListener(VirtualFileListener virtualFileListener) {
        if (virtualFileListener == null) {
            $$$reportNull$$$0(0);
        }
        this.myEventDispatcher.addListener(virtualFileListener);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    /* JADX INFO: renamed from: removeVirtualFileListener */
    public void m6510xbb63dbb0(VirtualFileListener virtualFileListener) {
        if (virtualFileListener == null) {
            $$$reportNull$$$0(1);
        }
        this.myEventDispatcher.removeListener(virtualFileListener);
    }

    protected void firePropertyChanged(Object obj, VirtualFile virtualFile, String str, Object obj2, Object obj3) {
        if (virtualFile == null) {
            $$$reportNull$$$0(2);
        }
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        assertWriteAccessAllowed();
        ((VirtualFileListener) this.myEventDispatcher.getMulticaster()).propertyChanged(new VirtualFilePropertyEvent(obj, virtualFile, str, obj2, obj3));
    }

    protected void fireContentsChanged(Object obj, VirtualFile virtualFile, long j) {
        if (virtualFile == null) {
            $$$reportNull$$$0(4);
        }
        assertWriteAccessAllowed();
        ((VirtualFileListener) this.myEventDispatcher.getMulticaster()).contentsChanged(new VirtualFileEvent(obj, virtualFile, virtualFile.getParent(), j, virtualFile.getModificationStamp()));
    }

    protected void fireFileCreated(Object obj, VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(5);
        }
        assertWriteAccessAllowed();
        ((VirtualFileListener) this.myEventDispatcher.getMulticaster()).fileCreated(new VirtualFileEvent(obj, virtualFile, virtualFile.getParent(), 0L, 0L));
    }

    protected void fireFileDeleted(Object obj, VirtualFile virtualFile, String str, VirtualFile virtualFile2) {
        if (virtualFile == null) {
            $$$reportNull$$$0(6);
        }
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        assertWriteAccessAllowed();
        ((VirtualFileListener) this.myEventDispatcher.getMulticaster()).fileDeleted(new VirtualFileEvent(obj, virtualFile, virtualFile2, 0L, 0L));
    }

    protected void fireFileMoved(Object obj, VirtualFile virtualFile, VirtualFile virtualFile2) {
        if (virtualFile == null) {
            $$$reportNull$$$0(8);
        }
        assertWriteAccessAllowed();
        ((VirtualFileListener) this.myEventDispatcher.getMulticaster()).fileMoved(new VirtualFileMoveEvent(obj, virtualFile, virtualFile2, virtualFile.getParent()));
    }

    protected void fireFileCopied(Object obj, VirtualFile virtualFile, VirtualFile virtualFile2) {
        if (virtualFile == null) {
            $$$reportNull$$$0(9);
        }
        if (virtualFile2 == null) {
            $$$reportNull$$$0(10);
        }
        assertWriteAccessAllowed();
        ((VirtualFileListener) this.myEventDispatcher.getMulticaster()).fileCopied(new VirtualFileCopyEvent(obj, virtualFile, virtualFile2));
    }

    protected void fireBeforePropertyChange(Object obj, VirtualFile virtualFile, String str, Object obj2, Object obj3) {
        if (virtualFile == null) {
            $$$reportNull$$$0(11);
        }
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        assertWriteAccessAllowed();
        ((VirtualFileListener) this.myEventDispatcher.getMulticaster()).beforePropertyChange(new VirtualFilePropertyEvent(obj, virtualFile, str, obj2, obj3));
    }

    protected void fireBeforeContentsChange(Object obj, VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(13);
        }
        assertWriteAccessAllowed();
        ((VirtualFileListener) this.myEventDispatcher.getMulticaster()).beforeContentsChange(new VirtualFileEvent(obj, virtualFile, virtualFile.getParent(), 0L, 0L));
    }

    protected void fireBeforeFileDeletion(Object obj, VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(14);
        }
        assertWriteAccessAllowed();
        ((VirtualFileListener) this.myEventDispatcher.getMulticaster()).beforeFileDeletion(new VirtualFileEvent(obj, virtualFile, virtualFile.getParent(), 0L, 0L));
    }

    protected void fireBeforeFileMovement(Object obj, VirtualFile virtualFile, VirtualFile virtualFile2) {
        if (virtualFile == null) {
            $$$reportNull$$$0(15);
        }
        assertWriteAccessAllowed();
        ((VirtualFileListener) this.myEventDispatcher.getMulticaster()).beforeFileMovement(new VirtualFileMoveEvent(obj, virtualFile, virtualFile.getParent(), virtualFile2));
    }

    protected void assertWriteAccessAllowed() {
        ApplicationManager.getApplication().assertWriteAccessAllowed();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    protected void deleteFile(Object obj, VirtualFile virtualFile) throws IOException {
        if (virtualFile == null) {
            $$$reportNull$$$0(16);
        }
        throw unsupported("deleteFile", virtualFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    protected void moveFile(Object obj, VirtualFile virtualFile, VirtualFile virtualFile2) throws IOException {
        if (virtualFile == null) {
            $$$reportNull$$$0(17);
        }
        if (virtualFile2 == null) {
            $$$reportNull$$$0(18);
        }
        throw unsupported("move", virtualFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    protected void renameFile(Object obj, VirtualFile virtualFile, String str) throws IOException {
        if (virtualFile == null) {
            $$$reportNull$$$0(19);
        }
        if (str == null) {
            $$$reportNull$$$0(20);
        }
        throw unsupported("renameFile", virtualFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    public VirtualFile createChildFile(Object obj, VirtualFile virtualFile, String str) throws IOException {
        if (virtualFile == null) {
            $$$reportNull$$$0(21);
        }
        if (str == null) {
            $$$reportNull$$$0(22);
        }
        throw unsupported("createChildFile", virtualFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    public VirtualFile createChildDirectory(Object obj, VirtualFile virtualFile, String str) throws IOException {
        if (virtualFile == null) {
            $$$reportNull$$$0(23);
        }
        if (str == null) {
            $$$reportNull$$$0(24);
        }
        throw unsupported("createChildDirectory", virtualFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem
    public VirtualFile copyFile(Object obj, VirtualFile virtualFile, VirtualFile virtualFile2, String str) throws IOException {
        if (virtualFile == null) {
            $$$reportNull$$$0(25);
        }
        if (virtualFile2 == null) {
            $$$reportNull$$$0(26);
        }
        if (str == null) {
            $$$reportNull$$$0(27);
        }
        throw unsupported("copyFile", virtualFile);
    }

    private UnsupportedOperationException unsupported(String str, VirtualFile virtualFile) {
        return new UnsupportedOperationException(str + '(' + virtualFile + ") not supported by " + getClass().getName());
    }
}
