package org.jetbrains.kotlin.com.intellij.psi.impl.file.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.com.intellij.injected.editor.VirtualFileWindow;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageUtil;
import org.jetbrains.kotlin.com.intellij.model.ModelBranch;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.project.DumbService;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.LowMemoryWatcher;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.StackOverflowPreventedException;
import org.jetbrains.kotlin.com.intellij.openapi.util.registry.Registry;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.InvalidVirtualFileAccessException;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileVisitor;
import org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.FileTypeFileViewProviders;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProviderFactory;
import org.jetbrains.kotlin.com.intellij.psi.LanguageFileViewProviders;
import org.jetbrains.kotlin.com.intellij.psi.LanguageSubstitutors;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.psi.SingleRootFileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.FreeThreadedFileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiTreeChangeEventImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiDirectoryFactory;
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.kotlin.com.intellij.util.ConcurrencyUtil;
import org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.messages.MessageBusConnection;

/* JADX INFO: loaded from: classes3.dex */
public final class FileManagerImpl implements FileManager {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Key<Boolean> IN_COMA = Key.create("IN_COMA");
    private static final Logger LOG = Logger.getInstance((Class<?>) FileManagerImpl.class);
    private static final VirtualFile NULL = new LightVirtualFile();
    private final MessageBusConnection myConnection;
    private final NotNullLazyValue<? extends FileIndexFacade> myFileIndex;
    private final PsiManagerImpl myManager;
    private boolean myProcessingFileTypesChange;
    private final Key<FileViewProvider> myPsiHardRefKey;
    private final ThreadLocal<Map<VirtualFile, FileViewProvider>> myTempProviders;
    private final AtomicReference<ConcurrentMap<VirtualFile, PsiDirectory>> myVFileToPsiDirMap;
    private final AtomicReference<ConcurrentMap<VirtualFile, FileViewProvider>> myVFileToViewProviderMap;

    /* JADX WARN: Code duplicated, block: B:9:0x0014  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 2 && i != 3 && i != 16 && i != 28) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 2 && i != 3 && i != 16 && i != 28) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "fileIndex";
                break;
            case 2:
            case 3:
            case 7:
            case 8:
            case 9:
            case 10:
            case 16:
            case 28:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/file/impl/FileManagerImpl";
                break;
            case 4:
                objArr[0] = Constants.EXTRA_PROVIDER;
                break;
            case 5:
            case 17:
            case 18:
            case 19:
            case 20:
            case 22:
            case 23:
            case 24:
            case 34:
                objArr[0] = "vFile";
                break;
            case 6:
            case 11:
            case 12:
            case 13:
            case 15:
            case 27:
            case 32:
            case 35:
            case 38:
            case 39:
                objArr[0] = "file";
                break;
            case 14:
                objArr[0] = "virtualFile";
                break;
            case 21:
                objArr[0] = "psiDirMap";
                break;
            case 25:
            case 26:
            case 33:
            case 36:
            case 37:
                objArr[0] = "viewProvider";
                break;
            case 29:
                objArr[0] = "view1";
                break;
            case 30:
                objArr[0] = "view2";
                break;
            case 31:
                objArr[0] = "originalFileToPsiFileMap";
                break;
            default:
                objArr[0] = "manager";
                break;
        }
        if (i == 2) {
            objArr[1] = "getVFileToViewProviderMap";
        } else if (i == 3) {
            objArr[1] = "getVFileToPsiDirMap";
        } else if (i == 16) {
            objArr[1] = "createFileViewProvider";
        } else if (i != 28) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                    objArr[1] = "findViewProvider";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/file/impl/FileManagerImpl";
                    break;
            }
        } else {
            objArr[1] = "getAllCachedFiles";
        }
        switch (i) {
            case 2:
            case 3:
            case 7:
            case 8:
            case 9:
            case 10:
            case 16:
            case 28:
                break;
            case 4:
                objArr[2] = "clearPsiCaches";
                break;
            case 5:
                objArr[2] = "forceReload";
                break;
            case 6:
                objArr[2] = "findViewProvider";
                break;
            case 11:
                objArr[2] = "checkHasNoOtherPsi";
                break;
            case 12:
                objArr[2] = "findCachedViewProvider";
                break;
            case 13:
                objArr[2] = "getRawCachedViewProvider";
                break;
            case 14:
                objArr[2] = "setViewProvider";
                break;
            case 15:
                objArr[2] = "createFileViewProvider";
                break;
            case 17:
                objArr[2] = "findFile";
                break;
            case 18:
                objArr[2] = "getCachedPsiFile";
                break;
            case 19:
                objArr[2] = "findDirectory";
                break;
            case 20:
            case 21:
                objArr[2] = "findDirectoryImpl";
                break;
            case 22:
                objArr[2] = "isExcludedOrIgnored";
                break;
            case 23:
                objArr[2] = "getCachedDirectory";
                break;
            case 24:
                objArr[2] = "removeFilesAndDirsRecursively";
                break;
            case 25:
                objArr[2] = "markInvalidated";
                break;
            case 26:
                objArr[2] = "markPossiblyInvalidated";
                break;
            case 27:
                objArr[2] = "getCachedPsiFileInner";
                break;
            case 29:
            case 30:
                objArr[2] = "areViewProvidersEquivalent";
                break;
            case 31:
                objArr[2] = "markInvalidations";
                break;
            case 32:
                objArr[2] = "reloadFromDisk";
                break;
            case 33:
            case 34:
                objArr[2] = "reloadPsiAfterTextChange";
                break;
            case 35:
            case 36:
                objArr[2] = "evaluateValidity";
                break;
            case 37:
            case 38:
                objArr[2] = "shouldResurrect";
                break;
            case 39:
                objArr[2] = "hasInvalidOriginal";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 16 && i != 28) {
            switch (i) {
                case 7:
                case 8:
                case 9:
                case 10:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    public static /* synthetic */ HashMap $r8$lambda$zL9zIj_AuRar_VZKE_7we4Hn4rk() {
        return new HashMap();
    }

    public FileManagerImpl(PsiManagerImpl psiManagerImpl, NotNullLazyValue<? extends FileIndexFacade> notNullLazyValue) {
        if (psiManagerImpl == null) {
            $$$reportNull$$$0(0);
        }
        if (notNullLazyValue == null) {
            $$$reportNull$$$0(1);
        }
        this.myPsiHardRefKey = Key.create("HARD_REFERENCE_TO_PSI");
        this.myVFileToPsiDirMap = new AtomicReference<>();
        this.myVFileToViewProviderMap = new AtomicReference<>();
        this.myTempProviders = ThreadLocal.withInitial(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManagerImpl$$ExternalSyntheticLambda9
            @Override // java.util.function.Supplier
            public final Object get() {
                return FileManagerImpl.$r8$lambda$zL9zIj_AuRar_VZKE_7we4Hn4rk();
            }
        });
        this.myManager = psiManagerImpl;
        this.myFileIndex = notNullLazyValue;
        MessageBusConnection messageBusConnectionConnect = psiManagerImpl.getProject().getMessageBus().connect(psiManagerImpl);
        this.myConnection = messageBusConnectionConnect;
        LowMemoryWatcher.register(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManagerImpl$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.processQueue();
            }
        }, psiManagerImpl);
        messageBusConnectionConnect.subscribe(DumbService.DUMB_MODE, new DumbService.DumbModeListener() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManagerImpl.1
            public void enteredDumbMode() throws Throwable {
                FileManagerImpl.this.processFileTypesChanged(false);
            }

            public void exitDumbMode() throws Throwable {
                FileManagerImpl.this.processFileTypesChanged(false);
            }
        });
    }

    public void processQueue() {
        ConcurrentMap<VirtualFile, FileViewProvider> concurrentMap = this.myVFileToViewProviderMap.get();
        if (concurrentMap != null) {
            concurrentMap.remove(NULL);
        }
    }

    public ConcurrentMap<VirtualFile, FileViewProvider> getVFileToViewProviderMap() {
        ConcurrentMap<VirtualFile, FileViewProvider> concurrentMap = this.myVFileToViewProviderMap.get();
        if (concurrentMap == null) {
            concurrentMap = (ConcurrentMap) ConcurrencyUtil.cacheOrGet(this.myVFileToViewProviderMap, ContainerUtil.createConcurrentWeakValueMap());
        }
        if (concurrentMap == null) {
            $$$reportNull$$$0(2);
        }
        return concurrentMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ConcurrentMap<VirtualFile, PsiDirectory> getVFileToPsiDirMap() {
        ConcurrentMap<VirtualFile, PsiDirectory> concurrentMap = this.myVFileToPsiDirMap.get();
        if (concurrentMap == null) {
            concurrentMap = (ConcurrentMap) ConcurrencyUtil.cacheOrGet(this.myVFileToPsiDirMap, ContainerUtil.createConcurrentSoftValueMap());
        }
        if (concurrentMap == null) {
            $$$reportNull$$$0(3);
        }
        return concurrentMap;
    }

    public static void clearPsiCaches(FileViewProvider fileViewProvider) {
        if (fileViewProvider == null) {
            $$$reportNull$$$0(4);
        }
        ((AbstractFileViewProvider) fileViewProvider).getCachedPsiFiles().forEach(new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManagerImpl$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((PsiFile) obj).clearCaches();
            }
        });
    }

    public void forceReload(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(5);
        }
        LanguageSubstitutors.cancelReparsing(virtualFile);
        FileViewProvider fileViewProviderFindCachedViewProvider = findCachedViewProvider(virtualFile);
        if (fileViewProviderFindCachedViewProvider == null) {
            return;
        }
        if (!fileViewProviderFindCachedViewProvider.isEventSystemEnabled()) {
            setViewProvider(virtualFile, null);
            return;
        }
        ApplicationManager.getApplication().assertWriteAccessAllowed();
        VirtualFile parent = virtualFile.getParent();
        PsiDirectory cachedDirectory = parent == null ? null : getCachedDirectory(parent);
        PsiTreeChangeEventImpl psiTreeChangeEventImpl = new PsiTreeChangeEventImpl(this.myManager);
        if (cachedDirectory == null) {
            psiTreeChangeEventImpl.setPropertyName(PsiTreeChangeEvent.PROP_UNLOADED_PSI);
            this.myManager.beforePropertyChange(psiTreeChangeEventImpl);
            setViewProvider(virtualFile, null);
            this.myManager.propertyChanged(psiTreeChangeEventImpl);
            return;
        }
        psiTreeChangeEventImpl.setParent(cachedDirectory);
        this.myManager.beforeChildrenChange(psiTreeChangeEventImpl);
        setViewProvider(virtualFile, null);
        this.myManager.childrenChanged(psiTreeChangeEventImpl);
    }

    public void firePropertyChangedForUnloadedPsi() {
        PsiTreeChangeEventImpl psiTreeChangeEventImpl = new PsiTreeChangeEventImpl(this.myManager);
        psiTreeChangeEventImpl.setPropertyName(PsiTreeChangeEvent.PROP_UNLOADED_PSI);
        this.myManager.beforePropertyChange(psiTreeChangeEventImpl);
        this.myManager.propertyChanged(psiTreeChangeEventImpl);
    }

    public void dispose() {
        clearViewProviders();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearViewProviders() throws Throwable {
        ApplicationManager.getApplication().assertWriteAccessAllowed();
        DebugUtil.performPsiModification("clearViewProviders", new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManagerImpl$$ExternalSyntheticLambda7
            @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
            public final void run() {
                this.f$0.m6617xb21208d8();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$clearViewProviders$0$org-jetbrains-kotlin-com-intellij-psi-impl-file-impl-FileManagerImpl, reason: not valid java name */
    /* synthetic */ void m6617xb21208d8() throws RuntimeException {
        ConcurrentMap<VirtualFile, FileViewProvider> concurrentMap = this.myVFileToViewProviderMap.get();
        if (concurrentMap != null) {
            Iterator<FileViewProvider> it = concurrentMap.values().iterator();
            while (it.hasNext()) {
                m6620x6f934a28(it.next());
            }
        }
        this.myVFileToViewProviderMap.set(null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManager
    public void cleanupForNextTest() {
        ApplicationManager.getApplication().runWriteAction(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManagerImpl$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.clearViewProviders();
            }
        });
        this.myVFileToPsiDirMap.set(null);
        this.myManager.dropPsiCaches();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManager
    public FileViewProvider findViewProvider(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(6);
        }
        FileViewProvider fileViewProviderFindCachedViewProvider = findCachedViewProvider(virtualFile);
        if (fileViewProviderFindCachedViewProvider != null) {
            if (fileViewProviderFindCachedViewProvider == null) {
                $$$reportNull$$$0(7);
            }
            return fileViewProviderFindCachedViewProvider;
        }
        if (virtualFile instanceof VirtualFileWindow) {
            throw new IllegalStateException("File " + virtualFile + " is invalid");
        }
        Map<VirtualFile, FileViewProvider> map = this.myTempProviders.get();
        if (map.containsKey(virtualFile)) {
            FileViewProvider fileViewProvider = (FileViewProvider) Objects.requireNonNull(map.get(virtualFile), "Recursive file view provider creation");
            if (fileViewProvider == null) {
                $$$reportNull$$$0(8);
            }
            return fileViewProvider;
        }
        FileViewProvider fileViewProviderCreateFileViewProvider = createFileViewProvider(virtualFile, ModelBranch.getFileBranch(virtualFile) == null);
        if (virtualFile instanceof LightVirtualFile) {
            checkHasNoOtherPsi(virtualFile);
            FileViewProvider fileViewProvider2 = (FileViewProvider) virtualFile.putUserDataIfAbsent(this.myPsiHardRefKey, fileViewProviderCreateFileViewProvider);
            if (fileViewProvider2 == null) {
                $$$reportNull$$$0(9);
            }
            return fileViewProvider2;
        }
        FileViewProvider fileViewProvider3 = (FileViewProvider) ConcurrencyUtil.cacheOrGet(getVFileToViewProviderMap(), virtualFile, fileViewProviderCreateFileViewProvider);
        if (fileViewProvider3 == null) {
            $$$reportNull$$$0(10);
        }
        return fileViewProvider3;
    }

    private void checkHasNoOtherPsi(VirtualFile virtualFile) {
        Project project;
        if (virtualFile == null) {
            $$$reportNull$$$0(11);
        }
        FileViewProvider fileViewProviderFindCachedPsiInAnyProject = FileDocumentManager.getInstance().findCachedPsiInAnyProject(virtualFile);
        if (fileViewProviderFindCachedPsiInAnyProject == null || (project = fileViewProviderFindCachedPsiInAnyProject.getManager().getProject()) == this.myManager.getProject()) {
            return;
        }
        LOG.error("Light files should have PSI only in one project, existing=" + fileViewProviderFindCachedPsiInAnyProject + " in " + project + ", requested in " + this.myManager.getProject() + "; psiFiles: " + ((String) fileViewProviderFindCachedPsiInAnyProject.getAllFiles().stream().map(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManagerImpl$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return FileManagerImpl.lambda$checkHasNoOtherPsi$1((PsiFile) obj);
            }
        }).collect(Collectors.joining(", "))));
    }

    static /* synthetic */ String lambda$checkHasNoOtherPsi$1(PsiFile psiFile) {
        return psiFile.getClass() + " [" + psiFile.getLanguage() + "]";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManager
    public FileViewProvider findCachedViewProvider(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(12);
        }
        FileViewProvider rawCachedViewProvider = getRawCachedViewProvider(virtualFile);
        if ((rawCachedViewProvider instanceof AbstractFileViewProvider) && rawCachedViewProvider.getUserData(IN_COMA) != null) {
            FileViewProvider fileViewProvider = this.myTempProviders.get().get(virtualFile);
            if (fileViewProvider != null) {
                return fileViewProvider;
            }
            if (!evaluateValidity((AbstractFileViewProvider) rawCachedViewProvider)) {
                return null;
            }
        }
        return rawCachedViewProvider;
    }

    private FileViewProvider getRawCachedViewProvider(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(13);
        }
        ConcurrentMap<VirtualFile, FileViewProvider> concurrentMap = this.myVFileToViewProviderMap.get();
        FileViewProvider fileViewProvider = concurrentMap == null ? null : concurrentMap.get(virtualFile);
        return fileViewProvider == null ? (FileViewProvider) virtualFile.getUserData(this.myPsiHardRefKey) : fileViewProvider;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManager
    public void setViewProvider(VirtualFile virtualFile, FileViewProvider fileViewProvider) throws Throwable {
        if (virtualFile == null) {
            $$$reportNull$$$0(14);
        }
        final FileViewProvider rawCachedViewProvider = getRawCachedViewProvider(virtualFile);
        if (rawCachedViewProvider == fileViewProvider) {
            return;
        }
        if (rawCachedViewProvider != null) {
            DebugUtil.performPsiModification((String) null, new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManagerImpl$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
                public final void run() {
                    this.f$0.m6620x6f934a28(rawCachedViewProvider);
                }
            });
        }
        if (fileViewProvider == null) {
            getVFileToViewProviderMap().remove(virtualFile);
        } else if (virtualFile instanceof LightVirtualFile) {
            checkHasNoOtherPsi(virtualFile);
            virtualFile.putUserData(this.myPsiHardRefKey, fileViewProvider);
        } else {
            getVFileToViewProviderMap().put(virtualFile, fileViewProvider);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManager
    public FileViewProvider createFileViewProvider(VirtualFile virtualFile, boolean z) {
        FileViewProviderFactory fileViewProviderFactoryForLanguage;
        if (virtualFile == null) {
            $$$reportNull$$$0(15);
        }
        FileType fileType = virtualFile.getFileType();
        Language languageForPsi = LanguageUtil.getLanguageForPsi(this.myManager.getProject(), virtualFile, fileType);
        if (languageForPsi == null) {
            fileViewProviderFactoryForLanguage = FileTypeFileViewProviders.INSTANCE.forFileType(fileType);
        } else {
            fileViewProviderFactoryForLanguage = LanguageFileViewProviders.INSTANCE.forLanguage(languageForPsi);
        }
        FileViewProvider fileViewProviderCreateFileViewProvider = fileViewProviderFactoryForLanguage == null ? null : fileViewProviderFactoryForLanguage.createFileViewProvider(virtualFile, languageForPsi, this.myManager, z);
        if (fileViewProviderCreateFileViewProvider == null) {
            fileViewProviderCreateFileViewProvider = new SingleRootFileViewProvider(this.myManager, virtualFile, z, fileType);
        }
        if (fileViewProviderCreateFileViewProvider == null) {
            $$$reportNull$$$0(16);
        }
        return fileViewProviderCreateFileViewProvider;
    }

    void processFileTypesChanged(final boolean z) throws Throwable {
        if (this.myProcessingFileTypesChange) {
            return;
        }
        this.myProcessingFileTypesChange = true;
        DebugUtil.performPsiModification((String) null, new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManagerImpl$$ExternalSyntheticLambda5
            @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
            public final void run() {
                this.f$0.m6619x5878f7b8(z);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$processFileTypesChanged$4$org-jetbrains-kotlin-com-intellij-psi-impl-file-impl-FileManagerImpl, reason: not valid java name */
    /* synthetic */ void m6619x5878f7b8(final boolean z) throws RuntimeException {
        try {
            ApplicationManager.getApplication().runWriteAction(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManagerImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() throws Throwable {
                    this.f$0.m6618x1f989719(z);
                }
            });
        } finally {
            this.myProcessingFileTypesChange = false;
        }
    }

    /* JADX INFO: renamed from: lambda$processFileTypesChanged$3$org-jetbrains-kotlin-com-intellij-psi-impl-file-impl-FileManagerImpl, reason: not valid java name */
    /* synthetic */ void m6618x1f989719(boolean z) throws Throwable {
        PsiTreeChangeEventImpl psiTreeChangeEventImpl = new PsiTreeChangeEventImpl(this.myManager);
        psiTreeChangeEventImpl.setPropertyName(PsiTreeChangeEvent.PROP_FILE_TYPES);
        this.myManager.beforePropertyChange(psiTreeChangeEventImpl);
        possiblyInvalidatePhysicalPsi();
        if (z) {
            clearViewProviders();
        }
        this.myManager.propertyChanged(psiTreeChangeEventImpl);
    }

    void possiblyInvalidatePhysicalPsi() {
        ApplicationManager.getApplication().assertWriteAccessAllowed();
        removeInvalidDirs();
        Iterator<FileViewProvider> it = getVFileToViewProviderMap().values().iterator();
        while (it.hasNext()) {
            markPossiblyInvalidated(it.next());
        }
    }

    void dispatchPendingEvents() {
        Project project = this.myManager.getProject();
        if (project.isDisposed()) {
            LOG.error("Project is already disposed: " + project);
        }
        this.myConnection.deliverImmediately();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManager
    public PsiFile findFile(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(17);
        }
        if (virtualFile.isDirectory()) {
            return null;
        }
        ApplicationManager.getApplication().assertReadAccessAllowed();
        if (!virtualFile.isValid()) {
            LOG.error("Invalid file: " + virtualFile);
            return null;
        }
        dispatchPendingEvents();
        FileViewProvider fileViewProviderFindViewProvider = findViewProvider(virtualFile);
        return fileViewProviderFindViewProvider.getPsi(fileViewProviderFindViewProvider.getBaseLanguage());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManager
    public PsiFile getCachedPsiFile(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(18);
        }
        ApplicationManager.getApplication().assertReadAccessAllowed();
        if (!virtualFile.isValid()) {
            throw new InvalidVirtualFileAccessException(virtualFile);
        }
        Project project = this.myManager.getProject();
        if (project.isDisposed()) {
            LOG.error("Project is already disposed: " + project);
        }
        dispatchPendingEvents();
        return getCachedPsiFileInner(virtualFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManager
    public PsiDirectory findDirectory(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(19);
        }
        Project project = this.myManager.getProject();
        if (project.isDisposed()) {
            LOG.error("Access to psi files should not be performed after project disposal: " + project);
        }
        ApplicationManager.getApplication().assertReadAccessAllowed();
        if (!virtualFile.isValid()) {
            LOG.error("File is not valid:" + virtualFile);
            return null;
        }
        if (!virtualFile.isDirectory()) {
            return null;
        }
        dispatchPendingEvents();
        return findDirectoryImpl(virtualFile, getVFileToPsiDirMap());
    }

    private PsiDirectory findDirectoryImpl(VirtualFile virtualFile, ConcurrentMap<VirtualFile, PsiDirectory> concurrentMap) {
        if (virtualFile == null) {
            $$$reportNull$$$0(20);
        }
        if (concurrentMap == null) {
            $$$reportNull$$$0(21);
        }
        PsiDirectory psiDirectory = concurrentMap.get(virtualFile);
        if (psiDirectory != null) {
            return psiDirectory;
        }
        if (isExcludedOrIgnored(virtualFile)) {
            return null;
        }
        VirtualFile parent = virtualFile.getParent();
        if (parent != null) {
            findDirectoryImpl(parent, concurrentMap);
        }
        return (PsiDirectory) ConcurrencyUtil.cacheOrGet(concurrentMap, virtualFile, PsiDirectoryFactory.getInstance(this.myManager.getProject()).createDirectory(virtualFile));
    }

    private boolean isExcludedOrIgnored(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(22);
        }
        if (this.myManager.getProject().isDefault()) {
            return false;
        }
        FileIndexFacade value = this.myFileIndex.getValue();
        return Registry.is("ide.hide.excluded.files") ? value.isExcludedFile(virtualFile) : value.isUnderIgnored(virtualFile);
    }

    public PsiDirectory getCachedDirectory(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(23);
        }
        return getVFileToPsiDirMap().get(virtualFile);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManagerImpl$2, reason: invalid class name */
    class AnonymousClass2 extends VirtualFileVisitor<Void> {
        final /* synthetic */ FileManagerImpl this$0;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "file", "org/jetbrains/kotlin/com/intellij/psi/impl/file/impl/FileManagerImpl$2", "visitFile"));
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileVisitor
        public boolean visitFile(VirtualFile virtualFile) {
            if (virtualFile == null) {
                $$$reportNull$$$0(0);
            }
            if (virtualFile.isDirectory()) {
                this.this$0.getVFileToPsiDirMap().remove(virtualFile);
                return true;
            }
            FileViewProvider fileViewProviderRemove = this.this$0.getVFileToViewProviderMap().remove(virtualFile);
            if (fileViewProviderRemove == null) {
                return true;
            }
            this.this$0.m6620x6f934a28(fileViewProviderRemove);
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: markInvalidated, reason: merged with bridge method [inline-methods] */
    public void m6620x6f934a28(FileViewProvider fileViewProvider) {
        if (fileViewProvider == null) {
            $$$reportNull$$$0(25);
        }
        fileViewProvider.putUserData(IN_COMA, null);
        ((AbstractFileViewProvider) fileViewProvider).markInvalidated();
        fileViewProvider.getVirtualFile().putUserData(this.myPsiHardRefKey, null);
    }

    public static void markPossiblyInvalidated(FileViewProvider fileViewProvider) {
        if (fileViewProvider == null) {
            $$$reportNull$$$0(26);
        }
        LOG.assertTrue(!(fileViewProvider instanceof FreeThreadedFileViewProvider));
        fileViewProvider.putUserData(IN_COMA, true);
        ((AbstractFileViewProvider) fileViewProvider).markPossiblyInvalidated();
        clearPsiCaches(fileViewProvider);
    }

    PsiFile getCachedPsiFileInner(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(27);
        }
        FileViewProvider fileViewProviderFindCachedViewProvider = findCachedViewProvider(virtualFile);
        if (fileViewProviderFindCachedViewProvider != null) {
            return ((AbstractFileViewProvider) fileViewProviderFindCachedViewProvider).getCachedPsi(fileViewProviderFindCachedViewProvider.getBaseLanguage());
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManager
    public List<PsiFile> getAllCachedFiles() {
        ArrayList arrayList = new ArrayList();
        Iterator it = new ArrayList(getVFileToViewProviderMap().keySet()).iterator();
        while (it.hasNext()) {
            FileViewProvider fileViewProviderFindCachedViewProvider = findCachedViewProvider((VirtualFile) it.next());
            if (fileViewProviderFindCachedViewProvider != null) {
                ContainerUtil.addAllNotNull(arrayList, ((AbstractFileViewProvider) fileViewProviderFindCachedViewProvider).getCachedPsiFiles());
            }
        }
        return arrayList;
    }

    private void removeInvalidDirs() {
        this.myVFileToPsiDirMap.set(null);
    }

    static boolean areViewProvidersEquivalent(FileViewProvider fileViewProvider, FileViewProvider fileViewProvider2) {
        Language baseLanguage;
        if (fileViewProvider == null) {
            $$$reportNull$$$0(29);
        }
        if (fileViewProvider2 == null) {
            $$$reportNull$$$0(30);
        }
        if (fileViewProvider.getClass() != fileViewProvider2.getClass() || fileViewProvider.getFileType() != fileViewProvider2.getFileType() || (baseLanguage = fileViewProvider.getBaseLanguage()) != fileViewProvider2.getBaseLanguage() || !fileViewProvider.getLanguages().equals(fileViewProvider2.getLanguages())) {
            return false;
        }
        PsiFile psi = fileViewProvider.getPsi(baseLanguage);
        PsiFile psi2 = fileViewProvider2.getPsi(baseLanguage);
        if (psi == null || psi2 == null) {
            return psi == psi2;
        }
        return psi.getClass() == psi2.getClass();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManager
    public void reloadFromDisk(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(32);
        }
        ApplicationManager.getApplication().assertWriteAccessAllowed();
        VirtualFile virtualFile = psiFile.getVirtualFile();
        Document cachedDocument = FileDocumentManager.getInstance().getCachedDocument(virtualFile);
        if (cachedDocument != null) {
            FileDocumentManager.getInstance().reloadFromDisk(cachedDocument);
        } else {
            reloadPsiAfterTextChange(psiFile.getViewProvider(), virtualFile);
        }
    }

    void reloadPsiAfterTextChange(FileViewProvider fileViewProvider, VirtualFile virtualFile) {
        if (fileViewProvider == null) {
            $$$reportNull$$$0(33);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(34);
        }
        if (!areViewProvidersEquivalent(fileViewProvider, createFileViewProvider(virtualFile, false))) {
            forceReload(virtualFile);
        } else {
            ((AbstractFileViewProvider) fileViewProvider).onContentReload();
        }
    }

    public boolean evaluateValidity(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(35);
        }
        AbstractFileViewProvider abstractFileViewProvider = (AbstractFileViewProvider) psiFile.getViewProvider();
        return evaluateValidity(abstractFileViewProvider) && abstractFileViewProvider.getCachedPsiFiles().contains(psiFile);
    }

    private boolean evaluateValidity(AbstractFileViewProvider abstractFileViewProvider) {
        if (abstractFileViewProvider == null) {
            $$$reportNull$$$0(36);
        }
        ApplicationManager.getApplication().assertReadAccessAllowed();
        VirtualFile virtualFile = abstractFileViewProvider.getVirtualFile();
        if (getRawCachedViewProvider(virtualFile) != abstractFileViewProvider) {
            return false;
        }
        Key<Boolean> key = IN_COMA;
        if (abstractFileViewProvider.getUserData(key) == null) {
            return true;
        }
        if (shouldResurrect(abstractFileViewProvider, virtualFile)) {
            abstractFileViewProvider.putUserData(key, null);
            LOG.assertTrue(getRawCachedViewProvider(virtualFile) == abstractFileViewProvider);
            for (PsiFile psiFile : abstractFileViewProvider.getCachedPsiFiles()) {
                if (!psiFile.isValid()) {
                    LOG.error((Throwable) new PsiInvalidElementAccessException(psiFile));
                }
            }
            return true;
        }
        getVFileToViewProviderMap().remove(virtualFile, abstractFileViewProvider);
        virtualFile.replace(this.myPsiHardRefKey, abstractFileViewProvider, null);
        abstractFileViewProvider.putUserData(key, null);
        return false;
    }

    private boolean shouldResurrect(FileViewProvider fileViewProvider, VirtualFile virtualFile) throws Throwable {
        if (fileViewProvider == null) {
            $$$reportNull$$$0(37);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(38);
        }
        boolean z = false;
        if (!virtualFile.isValid()) {
            return false;
        }
        Map<VirtualFile, FileViewProvider> map = this.myTempProviders.get();
        if (map.containsKey(virtualFile)) {
            LOG.error((Throwable) new StackOverflowPreventedException("isValid leads to endless recursion in " + fileViewProvider.getClass() + ": " + new ArrayList(fileViewProvider.getLanguages())));
        }
        map.put(virtualFile, null);
        try {
            FileViewProvider fileViewProviderCreateFileViewProvider = createFileViewProvider(virtualFile, true);
            map.put(virtualFile, fileViewProviderCreateFileViewProvider);
            if (areViewProvidersEquivalent(fileViewProvider, fileViewProviderCreateFileViewProvider) && !ContainerUtil.exists(((AbstractFileViewProvider) fileViewProvider).getCachedPsiFiles(), new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManagerImpl$$ExternalSyntheticLambda2
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return FileManagerImpl.hasInvalidOriginal((PsiFile) obj);
                }
            })) {
                z = true;
            }
            return z;
        } finally {
            FileViewProvider fileViewProviderRemove = map.remove(virtualFile);
            if (fileViewProviderRemove != null) {
                final AbstractFileViewProvider abstractFileViewProvider = (AbstractFileViewProvider) fileViewProviderRemove;
                Objects.requireNonNull(abstractFileViewProvider);
                DebugUtil.performPsiModification("invalidate temp view provider", new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManagerImpl$$ExternalSyntheticLambda3
                    @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
                    public final void run() {
                        abstractFileViewProvider.markInvalidated();
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean hasInvalidOriginal(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(39);
        }
        PsiFile originalFile = psiFile.getOriginalFile();
        return (originalFile == psiFile || originalFile.isValid()) ? false : true;
    }
}
