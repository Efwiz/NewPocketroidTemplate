package org.jetbrains.kotlin.com.intellij.openapi.vfs.impl;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.application.AppUIExecutor;
import org.jetbrains.kotlin.com.intellij.openapi.application.Application;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState;
import org.jetbrains.kotlin.com.intellij.openapi.application.WriteAction;
import org.jetbrains.kotlin.com.intellij.openapi.application.ex.ApplicationManagerEx;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionPointImpl;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionsAreaImpl;
import org.jetbrains.kotlin.com.intellij.openapi.util.KeyedExtensionCollector;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileCopyEvent;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileEvent;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileListener;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManagerListener;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileMoveEvent;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFilePropertyEvent;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.ex.VirtualFileManagerEx;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.BulkFileListener;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.CachingVirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFilePropertyChangeEvent;
import org.jetbrains.kotlin.com.intellij.util.EventDispatcher;
import org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.messages.MessageBus;

/* JADX INFO: loaded from: classes3.dex */
public class VirtualFileManagerImpl extends VirtualFileManagerEx implements Disposable {
    protected static final Logger LOG = Logger.getInstance((Class<?>) VirtualFileManagerImpl.class);
    private static final ExtensionPointImpl<VirtualFileManagerListener> MANAGER_LISTENER_EP = ((ExtensionsAreaImpl) ApplicationManager.getApplication().getExtensionArea()).getExtensionPoint("org.jetbrains.kotlin.com.intellij.virtualFileManagerListener");
    private final List<Object> myAsyncFileListeners;
    private final KeyedExtensionCollector<VirtualFileSystem, String> myCollector;
    private final List<? extends VirtualFileSystem> myPreCreatedFileSystems;
    private final EventDispatcher<VirtualFileListener> myVirtualFileListenerMulticaster;
    private final List<VirtualFileManagerListener> myVirtualFileManagerListeners;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 5) ? 2 : 3];
        switch (i) {
            case 2:
                objArr[0] = "bus";
                break;
            case 3:
            case 5:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/impl/VirtualFileManagerImpl";
                break;
            case 4:
                objArr[0] = ConfigConstants.CONFIG_PROTOCOL_SECTION;
                break;
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
                objArr[0] = ServiceSpecificExtraArgs.CastExtraArgs.LISTENER;
                break;
            case 8:
            case 12:
            case 15:
                objArr[0] = "parentDisposable";
                break;
            case 16:
                objArr[0] = "listeners";
                break;
            case 17:
                objArr[0] = "virtualFile";
                break;
            case 18:
                objArr[0] = "property";
                break;
            case 19:
                objArr[0] = "name";
                break;
            case 20:
            case 21:
            case 22:
                objArr[0] = "url";
                break;
            case 23:
            case 24:
                objArr[0] = "path";
                break;
            case 25:
                objArr[0] = "nioPath";
                break;
            default:
                objArr[0] = "preCreatedFileSystems";
                break;
        }
        if (i == 3) {
            objArr[1] = "getPhysicalFileSystems";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/impl/VirtualFileManagerImpl";
        } else {
            objArr[1] = "getFileSystemsForProtocol";
        }
        switch (i) {
            case 3:
            case 5:
                break;
            case 4:
                objArr[2] = "getFileSystemsForProtocol";
                break;
            case 6:
            case 7:
            case 8:
                objArr[2] = "addVirtualFileListener";
                break;
            case 9:
                objArr[2] = "removeVirtualFileListener";
                break;
            case 10:
            case 11:
            case 12:
                objArr[2] = "addVirtualFileManagerListener";
                break;
            case 13:
                objArr[2] = "removeVirtualFileManagerListener";
                break;
            case 14:
            case 15:
                objArr[2] = "addAsyncFileListener";
                break;
            case 16:
                objArr[2] = "addAsyncFileListenersTo";
                break;
            case 17:
            case 18:
                objArr[2] = "notifyPropertyChanged";
                break;
            case 19:
                objArr[2] = "storeName";
                break;
            case 20:
                objArr[2] = "findFileByUrl";
                break;
            case 21:
                objArr[2] = "refreshAndFindFileByUrl";
                break;
            case 22:
                objArr[2] = "findByUrl";
                break;
            case 23:
                objArr[2] = "findFileByNioPath";
                break;
            case 24:
                objArr[2] = "refreshAndFindFileByNioPath";
                break;
            case 25:
                objArr[2] = "findByNioPath";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
    public void dispose() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker
    public long getModificationCount() {
        return 0L;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager
    public long getStructureModificationCount() {
        return 0L;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public VirtualFileManagerImpl(List<? extends VirtualFileSystem> list) {
        this(list, ApplicationManager.getApplication().getMessageBus());
        if (list == null) {
            $$$reportNull$$$0(0);
        }
    }

    public VirtualFileManagerImpl(List<? extends VirtualFileSystem> list, MessageBus messageBus) {
        if (list == null) {
            $$$reportNull$$$0(1);
        }
        if (messageBus == null) {
            $$$reportNull$$$0(2);
        }
        this.myCollector = new KeyedExtensionCollector<>(VirtualFileSystem.EP_NAME);
        this.myVirtualFileListenerMulticaster = EventDispatcher.create(VirtualFileListener.class);
        this.myVirtualFileManagerListeners = ContainerUtil.createLockFreeCopyOnWriteList();
        this.myAsyncFileListeners = ContainerUtil.createLockFreeCopyOnWriteList();
        this.myPreCreatedFileSystems = new ArrayList(list);
        for (VirtualFileSystem virtualFileSystem : list) {
            this.myCollector.addExplicitExtension(virtualFileSystem.getProtocol(), virtualFileSystem);
            if (!(virtualFileSystem instanceof CachingVirtualFileSystem)) {
                virtualFileSystem.addVirtualFileListener((VirtualFileListener) this.myVirtualFileListenerMulticaster.getMulticaster());
            }
        }
        if (LOG.isDebugEnabled() && !ApplicationManagerEx.isInStressTest()) {
            addVirtualFileListener(new LoggingListener());
        }
        messageBus.connect().subscribe(VFS_CHANGES, new BulkVirtualFileListenerAdapter((VirtualFileListener) this.myVirtualFileListenerMulticaster.getMulticaster()));
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager
    public VirtualFileSystem getFileSystem(String str) {
        List<? extends VirtualFileSystem> fileSystemsForProtocol;
        int size;
        if (str == null || (size = (fileSystemsForProtocol = getFileSystemsForProtocol(str)).size()) == 0) {
            return null;
        }
        if (size > 1) {
            LOG.error(str + ": " + fileSystemsForProtocol);
        }
        return fileSystemsForProtocol.get(0);
    }

    protected List<? extends VirtualFileSystem> getFileSystemsForProtocol(String str) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        List<VirtualFileSystem> listForKey = this.myCollector.forKey(str);
        if (listForKey == null) {
            $$$reportNull$$$0(5);
        }
        return listForKey;
    }

    public void addVirtualFileListener(VirtualFileListener virtualFileListener) {
        if (virtualFileListener == null) {
            $$$reportNull$$$0(6);
        }
        this.myVirtualFileListenerMulticaster.addListener(virtualFileListener);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager
    public void notifyPropertyChanged(final VirtualFile virtualFile, final String str, final Object obj, final Object obj2) {
        if (virtualFile == null) {
            $$$reportNull$$$0(17);
        }
        if (str == null) {
            $$$reportNull$$$0(18);
        }
        final Application application = ApplicationManager.getApplication();
        AppUIExecutor.onWriteThread(ModalityState.NON_MODAL).later().expireWith(application).submit(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.VirtualFileManagerImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.m6512x48504ff2(virtualFile, str, obj, obj2, application);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$notifyPropertyChanged$3$org-jetbrains-kotlin-com-intellij-openapi-vfs-impl-VirtualFileManagerImpl, reason: not valid java name */
    /* synthetic */ void m6512x48504ff2(final VirtualFile virtualFile, final String str, final Object obj, final Object obj2, final Application application) throws Throwable {
        if (virtualFile.isValid()) {
            WriteAction.run(new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.VirtualFileManagerImpl$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
                public final void run() {
                    this.f$0.m6511x14a22531(virtualFile, str, obj, obj2, application);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$notifyPropertyChanged$2$org-jetbrains-kotlin-com-intellij-openapi-vfs-impl-VirtualFileManagerImpl, reason: not valid java name */
    /* synthetic */ void m6511x14a22531(VirtualFile virtualFile, String str, Object obj, Object obj2, Application application) throws RuntimeException {
        List<? extends VFileEvent> listSingletonList = Collections.singletonList(new VFilePropertyChangeEvent(this, virtualFile, str, obj, obj2, false));
        BulkFileListener bulkFileListener = (BulkFileListener) application.getMessageBus().syncPublisher(VirtualFileManager.VFS_CHANGES);
        bulkFileListener.before(listSingletonList);
        bulkFileListener.after(listSingletonList);
    }

    private static class LoggingListener implements VirtualFileListener {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            objArr[0] = "event";
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/impl/VirtualFileManagerImpl$LoggingListener";
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
                    objArr[2] = "beforeContentsChange";
                    break;
                case 7:
                    objArr[2] = "beforePropertyChange";
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

        private LoggingListener() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileListener
        public void propertyChanged(VirtualFilePropertyEvent virtualFilePropertyEvent) {
            if (virtualFilePropertyEvent == null) {
                $$$reportNull$$$0(0);
            }
            VirtualFileManagerImpl.LOG.debug("propertyChanged: file = " + virtualFilePropertyEvent.getFile() + ", propertyName = " + virtualFilePropertyEvent.getPropertyName() + ", oldValue = " + virtualFilePropertyEvent.getOldValue() + ", newValue = " + virtualFilePropertyEvent.getNewValue() + ", requestor = " + virtualFilePropertyEvent.getRequestor());
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileListener
        public void contentsChanged(VirtualFileEvent virtualFileEvent) {
            if (virtualFileEvent == null) {
                $$$reportNull$$$0(1);
            }
            VirtualFileManagerImpl.LOG.debug("contentsChanged: file = " + virtualFileEvent.getFile() + ", requestor = " + virtualFileEvent.getRequestor());
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileListener
        public void fileCreated(VirtualFileEvent virtualFileEvent) {
            if (virtualFileEvent == null) {
                $$$reportNull$$$0(2);
            }
            VirtualFileManagerImpl.LOG.debug("fileCreated: file = " + virtualFileEvent.getFile() + ", requestor = " + virtualFileEvent.getRequestor());
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileListener
        public void fileDeleted(VirtualFileEvent virtualFileEvent) {
            if (virtualFileEvent == null) {
                $$$reportNull$$$0(3);
            }
            VirtualFileManagerImpl.LOG.debug("fileDeleted: file = " + virtualFileEvent.getFile() + ", parent = " + virtualFileEvent.getParent() + ", requestor = " + virtualFileEvent.getRequestor());
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileListener
        public void fileMoved(VirtualFileMoveEvent virtualFileMoveEvent) {
            if (virtualFileMoveEvent == null) {
                $$$reportNull$$$0(4);
            }
            VirtualFileManagerImpl.LOG.debug("fileMoved: file = " + virtualFileMoveEvent.getFile() + ", oldParent = " + virtualFileMoveEvent.getOldParent() + ", newParent = " + virtualFileMoveEvent.getNewParent() + ", requestor = " + virtualFileMoveEvent.getRequestor());
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileListener
        public void fileCopied(VirtualFileCopyEvent virtualFileCopyEvent) {
            if (virtualFileCopyEvent == null) {
                $$$reportNull$$$0(5);
            }
            VirtualFileManagerImpl.LOG.debug("fileCopied: file = " + virtualFileCopyEvent.getFile() + ", originalFile = " + virtualFileCopyEvent.getOriginalFile() + ", requestor = " + virtualFileCopyEvent.getRequestor());
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileListener
        public void beforeContentsChange(VirtualFileEvent virtualFileEvent) {
            if (virtualFileEvent == null) {
                $$$reportNull$$$0(6);
            }
            VirtualFileManagerImpl.LOG.debug("beforeContentsChange: file = " + virtualFileEvent.getFile() + ", requestor = " + virtualFileEvent.getRequestor());
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileListener
        public void beforePropertyChange(VirtualFilePropertyEvent virtualFilePropertyEvent) {
            if (virtualFilePropertyEvent == null) {
                $$$reportNull$$$0(7);
            }
            VirtualFileManagerImpl.LOG.debug("beforePropertyChange: file = " + virtualFilePropertyEvent.getFile() + ", propertyName = " + virtualFilePropertyEvent.getPropertyName() + ", oldValue = " + virtualFilePropertyEvent.getOldValue() + ", newValue = " + virtualFilePropertyEvent.getNewValue() + ", requestor = " + virtualFilePropertyEvent.getRequestor());
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileListener
        public void beforeFileDeletion(VirtualFileEvent virtualFileEvent) {
            if (virtualFileEvent == null) {
                $$$reportNull$$$0(8);
            }
            VirtualFileManagerImpl.LOG.debug("beforeFileDeletion: file = " + virtualFileEvent.getFile() + ", requestor = " + virtualFileEvent.getRequestor());
            VirtualFileManagerImpl.LOG.assertTrue(virtualFileEvent.getFile().isValid());
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileListener
        public void beforeFileMovement(VirtualFileMoveEvent virtualFileMoveEvent) {
            if (virtualFileMoveEvent == null) {
                $$$reportNull$$$0(9);
            }
            VirtualFileManagerImpl.LOG.debug("beforeFileMovement: file = " + virtualFileMoveEvent.getFile() + ", oldParent = " + virtualFileMoveEvent.getOldParent() + ", newParent = " + virtualFileMoveEvent.getNewParent() + ", requestor = " + virtualFileMoveEvent.getRequestor());
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager
    public CharSequence getVFileName(int i) {
        throw new AbstractMethodError();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager
    public VirtualFile findFileByUrl(String str) {
        if (str == null) {
            $$$reportNull$$$0(20);
        }
        return findByUrl(str, false);
    }

    private VirtualFile findByUrl(String str, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(22);
        }
        int iIndexOf = str.indexOf("://");
        VirtualFileSystem fileSystem = iIndexOf < 0 ? null : getFileSystem(str.substring(0, iIndexOf));
        if (fileSystem == null) {
            return null;
        }
        String strSubstring = str.substring(iIndexOf + "://".length());
        return z ? fileSystem.refreshAndFindFileByPath(strSubstring) : fileSystem.findFileByPath(strSubstring);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager
    public VirtualFile findFileByNioPath(Path path) {
        if (path == null) {
            $$$reportNull$$$0(23);
        }
        return findByNioPath(path, false);
    }

    private VirtualFile findByNioPath(Path path, boolean z) {
        VirtualFileSystem fileSystem;
        if (path == null) {
            $$$reportNull$$$0(25);
        }
        if (!FileSystems.getDefault().equals(path.getFileSystem()) || (fileSystem = getFileSystem("file")) == null) {
            return null;
        }
        String string = path.toString();
        return z ? fileSystem.refreshAndFindFileByPath(string) : fileSystem.findFileByPath(string);
    }
}
