package org.jetbrains.kotlin.com.intellij.psi.impl;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilderFactory;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.WriteAction;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicatorProvider;
import org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase;
import org.jetbrains.kotlin.com.intellij.openapi.progress.util.ProgressWrapper;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullFactory;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.NonPhysicalFileSystem;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileFilter;
import org.jetbrains.kotlin.com.intellij.openapi.wm.ex.ProgressIndicatorEx;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectoryContainer;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeListener;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManagerImpl;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.messages.Topic;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiManagerImpl extends PsiManagerEx implements Disposable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private VirtualFileFilter myAssertOnFileLoadingFilter;
    private final AtomicInteger myBatchFilesProcessingModeCount;
    private final NotNullLazyValue<? extends FileIndexFacade> myFileIndex;
    private final FileManagerImpl myFileManager;
    private final PsiModificationTracker myModificationTracker;
    private final Project myProject;
    private boolean myTreeChangeEventIsFiring;
    private final List<PsiTreeChangeListener> myTreeChangeListeners;
    private final List<PsiTreeChangePreprocessor> myTreeChangePreprocessors;
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiManagerImpl.class);
    public static final Topic<AnyPsiChangeListener> ANY_PSI_CHANGE_TOPIC = new Topic<>(AnyPsiChangeListener.class, Topic.BroadcastDirection.TO_PARENT);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 6 || i == 9 || i == 32) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 6 || i == 9 || i == 32) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 2:
                objArr[0] = "filter";
                break;
            case 3:
            case 14:
                objArr[0] = "parentDisposable";
                break;
            case 4:
            case 7:
            case 8:
            case 10:
            case 11:
                objArr[0] = "file";
                break;
            case 5:
            case 6:
            case 9:
            case 32:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiManagerImpl";
                break;
            case 12:
            case 13:
            case 15:
                objArr[0] = ServiceSpecificExtraArgs.CastExtraArgs.LISTENER;
                break;
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 30:
            case 31:
                objArr[0] = "event";
                break;
            case 28:
            case 29:
                objArr[0] = "preprocessor";
                break;
            case 33:
                objArr[0] = "indicator";
                break;
            default:
                objArr[0] = "project";
                break;
        }
        if (i == 5) {
            objArr[1] = "getProject";
        } else if (i == 6) {
            objArr[1] = "getFileManager";
        } else if (i == 9) {
            objArr[1] = "findViewProvider";
        } else if (i != 32) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiManagerImpl";
        } else {
            objArr[1] = "getModificationTracker";
        }
        switch (i) {
            case 1:
                objArr[2] = "isInProject";
                break;
            case 2:
            case 3:
                objArr[2] = "setAssertOnFileLoadingFilter";
                break;
            case 4:
                objArr[2] = "isAssertOnFileLoading";
                break;
            case 5:
            case 6:
            case 9:
            case 32:
                break;
            case 7:
                objArr[2] = "findFile";
                break;
            case 8:
                objArr[2] = "findViewProvider";
                break;
            case 10:
                objArr[2] = "findDirectory";
                break;
            case 11:
                objArr[2] = "reloadFromDisk";
                break;
            case 12:
            case 13:
            case 14:
                objArr[2] = "addPsiTreeChangeListener";
                break;
            case 15:
                objArr[2] = "removePsiTreeChangeListener";
                break;
            case 16:
                objArr[2] = "beforeChildAddition";
                break;
            case 17:
                objArr[2] = "beforeChildRemoval";
                break;
            case 18:
                objArr[2] = "beforeChildReplacement";
                break;
            case 19:
                objArr[2] = "beforeChildrenChange";
                break;
            case 20:
                objArr[2] = "beforeChildMovement";
                break;
            case 21:
                objArr[2] = "beforePropertyChange";
                break;
            case 22:
                objArr[2] = "childAdded";
                break;
            case 23:
                objArr[2] = "childRemoved";
                break;
            case 24:
                objArr[2] = "childReplaced";
                break;
            case 25:
                objArr[2] = "childMoved";
                break;
            case 26:
                objArr[2] = "childrenChanged";
                break;
            case 27:
                objArr[2] = "propertyChanged";
                break;
            case 28:
                objArr[2] = "addTreeChangePreprocessor";
                break;
            case 29:
                objArr[2] = "removeTreeChangePreprocessor";
                break;
            case 30:
                objArr[2] = "fireEvent";
                break;
            case 31:
                objArr[2] = "notifyPsiTreeChangeListener";
                break;
            case 33:
                objArr[2] = "dropResolveCacheRegularly";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 6 && i != 9 && i != 32) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public PsiManagerImpl(final Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        List<PsiTreeChangePreprocessor> listCreateLockFreeCopyOnWriteList = ContainerUtil.createLockFreeCopyOnWriteList();
        this.myTreeChangePreprocessors = listCreateLockFreeCopyOnWriteList;
        this.myTreeChangeListeners = ContainerUtil.createLockFreeCopyOnWriteList();
        this.myAssertOnFileLoadingFilter = VirtualFileFilter.NONE;
        this.myBatchFilesProcessingModeCount = new AtomicInteger(0);
        PsiBuilderFactory.getInstance();
        this.myProject = project;
        NotNullLazyValue<? extends FileIndexFacade> notNullLazyValueCreateValue = NotNullLazyValue.createValue(new NotNullFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerImpl$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.NotNullFactory, org.jetbrains.kotlin.com.intellij.openapi.util.Factory
            public final Object create() {
                return FileIndexFacade.getInstance(project);
            }
        });
        this.myFileIndex = notNullLazyValueCreateValue;
        PsiModificationTracker service = PsiModificationTracker.SERVICE.getInstance(project);
        this.myModificationTracker = service;
        this.myFileManager = new FileManagerImpl(this, notNullLazyValueCreateValue);
        listCreateLockFreeCopyOnWriteList.add((PsiTreeChangePreprocessor) service);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
    public void dispose() {
        this.myFileManager.dispose();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiManager
    public boolean isDisposed() {
        return this.myProject.isDisposed();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiManager
    public void dropResolveCaches() {
        this.myFileManager.processQueue();
        beforeChange(true);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiManager
    public void dropPsiCaches() throws Throwable {
        dropResolveCaches();
        final FileManagerImpl fileManagerImpl = this.myFileManager;
        Objects.requireNonNull(fileManagerImpl);
        WriteAction.run(new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerImpl$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
            public final void run() {
                fileManagerImpl.firePropertyChangedForUnloadedPsi();
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiManager
    public boolean isInProject(PsiElement psiElement) {
        VirtualFile virtualFile;
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElement instanceof PsiDirectoryContainer) {
            for (PsiDirectory psiDirectory : ((PsiDirectoryContainer) psiElement).getDirectories()) {
                if (!isInProject(psiDirectory)) {
                    return false;
                }
            }
            return true;
        }
        PsiFile containingFile = psiElement.getContainingFile();
        if (containingFile != null) {
            virtualFile = containingFile.getViewProvider().getVirtualFile();
        } else {
            virtualFile = psiElement instanceof PsiFileSystemItem ? ((PsiFileSystemItem) psiElement).getVirtualFile() : null;
        }
        if (containingFile != null && containingFile.getIsPhysical() && (virtualFile.getFileSystem() instanceof NonPhysicalFileSystem)) {
            return true;
        }
        return virtualFile != null && this.myFileIndex.getValue().isInContent(virtualFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx
    public void setAssertOnFileLoadingFilter(VirtualFileFilter virtualFileFilter, Disposable disposable) {
        if (virtualFileFilter == null) {
            $$$reportNull$$$0(2);
        }
        if (disposable == null) {
            $$$reportNull$$$0(3);
        }
        this.myAssertOnFileLoadingFilter = virtualFileFilter;
        Disposer.register(disposable, new Disposable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerImpl$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
            public final void dispose() {
                this.f$0.m6569x6b9a9926();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setAssertOnFileLoadingFilter$1$org-jetbrains-kotlin-com-intellij-psi-impl-PsiManagerImpl, reason: not valid java name */
    /* synthetic */ void m6569x6b9a9926() {
        this.myAssertOnFileLoadingFilter = VirtualFileFilter.NONE;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx
    public boolean isAssertOnFileLoading(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(4);
        }
        return this.myAssertOnFileLoadingFilter.accept(virtualFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiManager
    public Project getProject() {
        Project project = this.myProject;
        if (project == null) {
            $$$reportNull$$$0(5);
        }
        return project;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx
    public FileManager getFileManager() {
        FileManagerImpl fileManagerImpl = this.myFileManager;
        if (fileManagerImpl == null) {
            $$$reportNull$$$0(6);
        }
        return fileManagerImpl;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiManager
    public boolean areElementsEquivalent(PsiElement psiElement, PsiElement psiElement2) {
        ProgressIndicatorProvider.checkCanceled();
        if (psiElement == psiElement2) {
            return true;
        }
        if (psiElement == null || psiElement2 == null) {
            return false;
        }
        return psiElement.equals(psiElement2) || psiElement.isEquivalentTo(psiElement2) || psiElement2.isEquivalentTo(psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiManager
    public PsiFile findFile(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(7);
        }
        ProgressIndicatorProvider.checkCanceled();
        return this.myFileManager.findFile(virtualFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiManager
    public FileViewProvider findViewProvider(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(8);
        }
        ProgressIndicatorProvider.checkCanceled();
        FileViewProvider fileViewProviderFindViewProvider = this.myFileManager.findViewProvider(virtualFile);
        if (fileViewProviderFindViewProvider == null) {
            $$$reportNull$$$0(9);
        }
        return fileViewProviderFindViewProvider;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiManager
    public PsiDirectory findDirectory(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(10);
        }
        ProgressIndicatorProvider.checkCanceled();
        return this.myFileManager.findDirectory(virtualFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiManager
    public void reloadFromDisk(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(11);
        }
        this.myFileManager.reloadFromDisk(psiFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiManager
    public void addPsiTreeChangeListener(PsiTreeChangeListener psiTreeChangeListener) {
        if (psiTreeChangeListener == null) {
            $$$reportNull$$$0(12);
        }
        this.myTreeChangeListeners.add(psiTreeChangeListener);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiManager
    public void addPsiTreeChangeListener(final PsiTreeChangeListener psiTreeChangeListener, Disposable disposable) {
        if (psiTreeChangeListener == null) {
            $$$reportNull$$$0(13);
        }
        if (disposable == null) {
            $$$reportNull$$$0(14);
        }
        addPsiTreeChangeListener(psiTreeChangeListener);
        Disposer.register(disposable, new Disposable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerImpl$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
            public final void dispose() {
                this.f$0.m6568x7b03823d(psiTreeChangeListener);
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiManager
    /* JADX INFO: renamed from: removePsiTreeChangeListener, reason: merged with bridge method [inline-methods] */
    public void m6568x7b03823d(PsiTreeChangeListener psiTreeChangeListener) {
        if (psiTreeChangeListener == null) {
            $$$reportNull$$$0(15);
        }
        this.myTreeChangeListeners.remove(psiTreeChangeListener);
    }

    private static String logPsi(PsiElement psiElement) {
        return psiElement == null ? " null" : psiElement.getClass().getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx
    public void beforeChildAddition(PsiTreeChangeEventImpl psiTreeChangeEventImpl) {
        if (psiTreeChangeEventImpl == null) {
            $$$reportNull$$$0(16);
        }
        beforeChange(true);
        psiTreeChangeEventImpl.setCode(PsiTreeChangeEventImpl.PsiEventType.BEFORE_CHILD_ADDITION);
        Logger logger = LOG;
        if (logger.isDebugEnabled()) {
            logger.debug("beforeChildAddition: event = " + psiTreeChangeEventImpl);
        }
        fireEvent(psiTreeChangeEventImpl);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx
    public void beforeChildRemoval(PsiTreeChangeEventImpl psiTreeChangeEventImpl) {
        if (psiTreeChangeEventImpl == null) {
            $$$reportNull$$$0(17);
        }
        beforeChange(true);
        psiTreeChangeEventImpl.setCode(PsiTreeChangeEventImpl.PsiEventType.BEFORE_CHILD_REMOVAL);
        Logger logger = LOG;
        if (logger.isDebugEnabled()) {
            logger.debug("beforeChildRemoval: child = " + logPsi(psiTreeChangeEventImpl.getChild()) + ", parent = " + logPsi(psiTreeChangeEventImpl.getParent()));
        }
        fireEvent(psiTreeChangeEventImpl);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx
    public void beforeChildReplacement(PsiTreeChangeEventImpl psiTreeChangeEventImpl) {
        if (psiTreeChangeEventImpl == null) {
            $$$reportNull$$$0(18);
        }
        beforeChange(true);
        psiTreeChangeEventImpl.setCode(PsiTreeChangeEventImpl.PsiEventType.BEFORE_CHILD_REPLACEMENT);
        Logger logger = LOG;
        if (logger.isDebugEnabled()) {
            logger.debug("beforeChildReplacement: oldChild = " + logPsi(psiTreeChangeEventImpl.getOldChild()));
        }
        fireEvent(psiTreeChangeEventImpl);
    }

    public void beforeChildrenChange(PsiTreeChangeEventImpl psiTreeChangeEventImpl) {
        if (psiTreeChangeEventImpl == null) {
            $$$reportNull$$$0(19);
        }
        beforeChange(true);
        psiTreeChangeEventImpl.setCode(PsiTreeChangeEventImpl.PsiEventType.BEFORE_CHILDREN_CHANGE);
        Logger logger = LOG;
        if (logger.isDebugEnabled()) {
            logger.debug("beforeChildrenChange: parent = " + logPsi(psiTreeChangeEventImpl.getParent()));
        }
        fireEvent(psiTreeChangeEventImpl);
    }

    public void beforeChildMovement(PsiTreeChangeEventImpl psiTreeChangeEventImpl) {
        if (psiTreeChangeEventImpl == null) {
            $$$reportNull$$$0(20);
        }
        beforeChange(true);
        psiTreeChangeEventImpl.setCode(PsiTreeChangeEventImpl.PsiEventType.BEFORE_CHILD_MOVEMENT);
        Logger logger = LOG;
        if (logger.isDebugEnabled()) {
            logger.debug("beforeChildMovement: child = " + logPsi(psiTreeChangeEventImpl.getChild()) + ", oldParent = " + logPsi(psiTreeChangeEventImpl.getOldParent()) + ", newParent = " + logPsi(psiTreeChangeEventImpl.getNewParent()));
        }
        fireEvent(psiTreeChangeEventImpl);
    }

    public void beforePropertyChange(PsiTreeChangeEventImpl psiTreeChangeEventImpl) {
        if (psiTreeChangeEventImpl == null) {
            $$$reportNull$$$0(21);
        }
        beforeChange(true);
        psiTreeChangeEventImpl.setCode(PsiTreeChangeEventImpl.PsiEventType.BEFORE_PROPERTY_CHANGE);
        Logger logger = LOG;
        if (logger.isDebugEnabled()) {
            logger.debug("beforePropertyChange: element = " + logPsi(psiTreeChangeEventImpl.getElement()) + ", propertyName = " + psiTreeChangeEventImpl.getPropertyName() + ", oldValue = " + arrayToString(psiTreeChangeEventImpl.getOldValue()));
        }
        fireEvent(psiTreeChangeEventImpl);
    }

    private static Object arrayToString(Object obj) {
        return obj instanceof Object[] ? Arrays.deepToString((Object[]) obj) : obj;
    }

    public void childAdded(PsiTreeChangeEventImpl psiTreeChangeEventImpl) {
        if (psiTreeChangeEventImpl == null) {
            $$$reportNull$$$0(22);
        }
        psiTreeChangeEventImpl.setCode(PsiTreeChangeEventImpl.PsiEventType.CHILD_ADDED);
        Logger logger = LOG;
        if (logger.isDebugEnabled()) {
            logger.debug("childAdded: child = " + logPsi(psiTreeChangeEventImpl.getChild()) + ", parent = " + logPsi(psiTreeChangeEventImpl.getParent()));
        }
        fireEvent(psiTreeChangeEventImpl);
        afterChange(true);
    }

    public void childRemoved(PsiTreeChangeEventImpl psiTreeChangeEventImpl) {
        if (psiTreeChangeEventImpl == null) {
            $$$reportNull$$$0(23);
        }
        psiTreeChangeEventImpl.setCode(PsiTreeChangeEventImpl.PsiEventType.CHILD_REMOVED);
        Logger logger = LOG;
        if (logger.isDebugEnabled()) {
            logger.debug("childRemoved: child = " + logPsi(psiTreeChangeEventImpl.getChild()) + ", parent = " + logPsi(psiTreeChangeEventImpl.getParent()));
        }
        fireEvent(psiTreeChangeEventImpl);
        afterChange(true);
    }

    public void childReplaced(PsiTreeChangeEventImpl psiTreeChangeEventImpl) {
        if (psiTreeChangeEventImpl == null) {
            $$$reportNull$$$0(24);
        }
        psiTreeChangeEventImpl.setCode(PsiTreeChangeEventImpl.PsiEventType.CHILD_REPLACED);
        Logger logger = LOG;
        if (logger.isDebugEnabled()) {
            logger.debug("childReplaced: oldChild = " + logPsi(psiTreeChangeEventImpl.getOldChild()) + ", newChild = " + logPsi(psiTreeChangeEventImpl.getNewChild()) + ", parent = " + logPsi(psiTreeChangeEventImpl.getParent()));
        }
        fireEvent(psiTreeChangeEventImpl);
        afterChange(true);
    }

    public void childMoved(PsiTreeChangeEventImpl psiTreeChangeEventImpl) {
        if (psiTreeChangeEventImpl == null) {
            $$$reportNull$$$0(25);
        }
        psiTreeChangeEventImpl.setCode(PsiTreeChangeEventImpl.PsiEventType.CHILD_MOVED);
        Logger logger = LOG;
        if (logger.isDebugEnabled()) {
            logger.debug("childMoved: child = " + logPsi(psiTreeChangeEventImpl.getChild()) + ", oldParent = " + logPsi(psiTreeChangeEventImpl.getOldParent()) + ", newParent = " + logPsi(psiTreeChangeEventImpl.getNewParent()));
        }
        fireEvent(psiTreeChangeEventImpl);
        afterChange(true);
    }

    public void childrenChanged(PsiTreeChangeEventImpl psiTreeChangeEventImpl) {
        if (psiTreeChangeEventImpl == null) {
            $$$reportNull$$$0(26);
        }
        psiTreeChangeEventImpl.setCode(PsiTreeChangeEventImpl.PsiEventType.CHILDREN_CHANGED);
        Logger logger = LOG;
        if (logger.isDebugEnabled()) {
            logger.debug("childrenChanged: parent = " + logPsi(psiTreeChangeEventImpl.getParent()));
        }
        fireEvent(psiTreeChangeEventImpl);
        afterChange(true);
    }

    public void propertyChanged(PsiTreeChangeEventImpl psiTreeChangeEventImpl) {
        if (psiTreeChangeEventImpl == null) {
            $$$reportNull$$$0(27);
        }
        psiTreeChangeEventImpl.setCode(PsiTreeChangeEventImpl.PsiEventType.PROPERTY_CHANGED);
        Logger logger = LOG;
        if (logger.isDebugEnabled()) {
            logger.debug("propertyChanged: element = " + logPsi(psiTreeChangeEventImpl.getElement()) + ", propertyName = " + psiTreeChangeEventImpl.getPropertyName() + ", oldValue = " + arrayToString(psiTreeChangeEventImpl.getOldValue()) + ", newValue = " + arrayToString(psiTreeChangeEventImpl.getNewValue()));
        }
        fireEvent(psiTreeChangeEventImpl);
        afterChange(true);
    }

    public void addTreeChangePreprocessor(PsiTreeChangePreprocessor psiTreeChangePreprocessor) {
        if (psiTreeChangePreprocessor == null) {
            $$$reportNull$$$0(28);
        }
        this.myTreeChangePreprocessors.add(psiTreeChangePreprocessor);
    }

    public void removeTreeChangePreprocessor(PsiTreeChangePreprocessor psiTreeChangePreprocessor) {
        if (psiTreeChangePreprocessor == null) {
            $$$reportNull$$$0(29);
        }
        this.myTreeChangePreprocessors.remove(psiTreeChangePreprocessor);
    }

    private void fireEvent(PsiTreeChangeEventImpl psiTreeChangeEventImpl) {
        if (psiTreeChangeEventImpl == null) {
            $$$reportNull$$$0(30);
        }
        boolean z = (psiTreeChangeEventImpl.getCode() == PsiTreeChangeEventImpl.PsiEventType.PROPERTY_CHANGED || psiTreeChangeEventImpl.getCode() == PsiTreeChangeEventImpl.PsiEventType.BEFORE_PROPERTY_CHANGE) ? false : true;
        PsiFile file = psiTreeChangeEventImpl.getFile();
        if (file == null || file.getIsPhysical()) {
            ApplicationManager.getApplication().assertWriteAccessAllowed();
        }
        if (z) {
            LOG.assertTrue(!this.myTreeChangeEventIsFiring, "Changes to PSI are not allowed inside event processing");
            this.myTreeChangeEventIsFiring = true;
        }
        try {
            Iterator<PsiTreeChangePreprocessor> it = this.myTreeChangePreprocessors.iterator();
            while (it.hasNext()) {
                it.next().treeChanged(psiTreeChangeEventImpl);
            }
            Iterator<PsiTreeChangePreprocessor> it2 = PsiTreeChangePreprocessor.EP.getExtensions(this.myProject).iterator();
            while (it2.hasNext()) {
                try {
                    it2.next().treeChanged(psiTreeChangeEventImpl);
                } catch (Throwable th) {
                    LOG.error(th);
                }
            }
            Iterator<PsiTreeChangeListener> it3 = this.myTreeChangeListeners.iterator();
            while (it3.hasNext()) {
                notifyPsiTreeChangeListener(psiTreeChangeEventImpl, it3.next());
            }
            Iterator<PsiTreeChangeListener> it4 = PsiTreeChangeListener.EP.getExtensions(this.myProject).iterator();
            while (it4.hasNext()) {
                notifyPsiTreeChangeListener(psiTreeChangeEventImpl, it4.next());
            }
            if (z) {
                this.myTreeChangeEventIsFiring = false;
            }
        } catch (Throwable th2) {
            if (z) {
                this.myTreeChangeEventIsFiring = false;
            }
            throw th2;
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerImpl$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType;

        static {
            int[] iArr = new int[PsiTreeChangeEventImpl.PsiEventType.values().length];
            $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType = iArr;
            try {
                iArr[PsiTreeChangeEventImpl.PsiEventType.BEFORE_CHILD_ADDITION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType[PsiTreeChangeEventImpl.PsiEventType.BEFORE_CHILD_REMOVAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType[PsiTreeChangeEventImpl.PsiEventType.BEFORE_CHILD_REPLACEMENT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType[PsiTreeChangeEventImpl.PsiEventType.BEFORE_CHILD_MOVEMENT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType[PsiTreeChangeEventImpl.PsiEventType.BEFORE_CHILDREN_CHANGE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType[PsiTreeChangeEventImpl.PsiEventType.BEFORE_PROPERTY_CHANGE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType[PsiTreeChangeEventImpl.PsiEventType.CHILD_ADDED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType[PsiTreeChangeEventImpl.PsiEventType.CHILD_REMOVED.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType[PsiTreeChangeEventImpl.PsiEventType.CHILD_REPLACED.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType[PsiTreeChangeEventImpl.PsiEventType.CHILD_MOVED.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType[PsiTreeChangeEventImpl.PsiEventType.CHILDREN_CHANGED.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType[PsiTreeChangeEventImpl.PsiEventType.PROPERTY_CHANGED.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    private static void notifyPsiTreeChangeListener(PsiTreeChangeEventImpl psiTreeChangeEventImpl, PsiTreeChangeListener psiTreeChangeListener) {
        if (psiTreeChangeEventImpl == null) {
            $$$reportNull$$$0(31);
        }
        try {
            switch (AnonymousClass2.$SwitchMap$com$intellij$psi$impl$PsiTreeChangeEventImpl$PsiEventType[psiTreeChangeEventImpl.getCode().ordinal()]) {
                case 1:
                    psiTreeChangeListener.beforeChildAddition(psiTreeChangeEventImpl);
                    break;
                case 2:
                    psiTreeChangeListener.beforeChildRemoval(psiTreeChangeEventImpl);
                    break;
                case 3:
                    psiTreeChangeListener.beforeChildReplacement(psiTreeChangeEventImpl);
                    break;
                case 4:
                    psiTreeChangeListener.beforeChildMovement(psiTreeChangeEventImpl);
                    break;
                case 5:
                    psiTreeChangeListener.beforeChildrenChange(psiTreeChangeEventImpl);
                    break;
                case 6:
                    psiTreeChangeListener.beforePropertyChange(psiTreeChangeEventImpl);
                    break;
                case 7:
                    psiTreeChangeListener.childAdded(psiTreeChangeEventImpl);
                    break;
                case 8:
                    psiTreeChangeListener.childRemoved(psiTreeChangeEventImpl);
                    break;
                case 9:
                    psiTreeChangeListener.childReplaced(psiTreeChangeEventImpl);
                    break;
                case 10:
                    psiTreeChangeListener.childMoved(psiTreeChangeEventImpl);
                    break;
                case 11:
                    psiTreeChangeListener.childrenChanged(psiTreeChangeEventImpl);
                    break;
                case 12:
                    psiTreeChangeListener.propertyChanged(psiTreeChangeEventImpl);
                    break;
                default:
                    break;
            }
        } catch (Throwable th) {
            LOG.error(th);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx
    public void beforeChange(boolean z) {
        ((AnyPsiChangeListener) this.myProject.getMessageBus().syncPublisher(ANY_PSI_CHANGE_TOPIC)).beforePsiChanged(z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx
    public void afterChange(boolean z) {
        ((AnyPsiChangeListener) this.myProject.getMessageBus().syncPublisher(ANY_PSI_CHANGE_TOPIC)).afterPsiChanged(z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiManager
    public PsiModificationTracker getModificationTracker() {
        PsiModificationTracker psiModificationTracker = this.myModificationTracker;
        if (psiModificationTracker == null) {
            $$$reportNull$$$0(32);
        }
        return psiModificationTracker;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiManager
    public void startBatchFilesProcessingMode() {
        this.myBatchFilesProcessingModeCount.incrementAndGet();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiManager
    public void finishBatchFilesProcessingMode() {
        LOG.assertTrue(this.myBatchFilesProcessingModeCount.decrementAndGet() >= 0);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx
    public boolean isBatchFilesProcessingMode() {
        return this.myBatchFilesProcessingModeCount.get() > 0;
    }

    public void cleanupForNextTest() throws Throwable {
        this.myFileManager.cleanupForNextTest();
        dropPsiCaches();
    }

    public void dropResolveCacheRegularly(ProgressIndicator progressIndicator) {
        if (progressIndicator == null) {
            $$$reportNull$$$0(33);
        }
        ProgressIndicator progressIndicatorUnwrap = ProgressWrapper.unwrap(progressIndicator);
        if (progressIndicatorUnwrap instanceof ProgressIndicatorEx) {
            ((ProgressIndicatorEx) progressIndicatorUnwrap).addStateDelegate(new AbstractProgressIndicatorExBase() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerImpl.1
                private final AtomicLong lastClearedTimeStamp = new AtomicLong();

                @Override // org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorExBase, org.jetbrains.kotlin.com.intellij.openapi.progress.util.AbstractProgressIndicatorBase, org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator
                public void setFraction(double d) {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    long j = this.lastClearedTimeStamp.get();
                    if (jCurrentTimeMillis - j < 500 || !this.lastClearedTimeStamp.compareAndSet(j, jCurrentTimeMillis)) {
                        return;
                    }
                    PsiManagerImpl.this.dropResolveCaches();
                }
            });
        }
    }
}
