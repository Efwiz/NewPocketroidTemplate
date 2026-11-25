package org.jetbrains.kotlin.com.intellij.psi;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import org.catrobat.catroid.common.Constants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.injected.editor.DocumentWindow;
import org.jetbrains.kotlin.com.intellij.injected.editor.VirtualFileWindow;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageParserDefinitions;
import org.jetbrains.kotlin.com.intellij.lang.ParserDefinition;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.ex.ApplicationManagerEx;
import org.jetbrains.kotlin.com.intellij.openapi.command.undo.UndoUtil;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Attachment;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.impl.LoadTextUtil;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeRegistry;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.NonPhysicalFileSystem;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VFileProperty;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileWithId;
import org.jetbrains.kotlin.com.intellij.psi.impl.FreeThreadedFileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiFileEx;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerEx;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiTreeChangeEventImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiLargeBinaryFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiLargeTextFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManagerImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiPlainTextFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.LocalTimeCounter;
import org.jetbrains.kotlin.com.intellij.util.containers.CollectionFactory;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.JBIterable;
import org.jetbrains.kotlin.com.intellij.util.containers.JBTreeTraverser;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AbstractFileViewProvider extends UserDataHolderBase implements FileViewProvider {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private volatile Content myContent;
    private volatile Reference<Document> myDocument;
    private final boolean myEventSystemEnabled;
    private final PsiManagerEx myManager;
    private final boolean myPhysical;
    private final PsiLock myPsiLock;
    private final VirtualFile myVirtualFile;
    private static final Logger LOG = Logger.getInstance((Class<?>) AbstractFileViewProvider.class);
    public static final Key<Object> FREE_THREADED = Key.create("FREE_THREADED");
    private static final Key<Set<AbstractFileViewProvider>> KNOWN_COPIES = Key.create("KNOWN_COPIES");

    private interface Content {
        long getModificationStamp();

        CharSequence getText();

        int getTextLength();
    }

    /* JADX WARN: Code duplicated, block: B:11:0x001d  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 3 && i != 10 && i != 20 && i != 24 && i != 26 && i != 27) {
            switch (i) {
                case 12:
                case 13:
                case 14:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 3 && i != 10 && i != 20 && i != 24 && i != 26 && i != 27) {
            switch (i) {
                case 12:
                case 13:
                case 14:
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
                objArr[0] = "virtualFile";
                break;
            case 2:
                objArr[0] = Constants.EXTRA_PROVIDER;
                break;
            case 3:
            case 10:
            case 12:
            case 13:
            case 14:
            case 20:
            case 24:
            case 26:
            case 27:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/AbstractFileViewProvider";
                break;
            case 4:
                objArr[0] = "project";
                break;
            case 5:
            case 7:
                objArr[0] = "file";
                break;
            case 6:
            case 8:
                objArr[0] = "fileType";
                break;
            case 9:
            case 16:
            case 17:
                objArr[0] = "language";
                break;
            case 11:
                objArr[0] = "lang";
                break;
            case 15:
                objArr[0] = "target";
                break;
            case 18:
                objArr[0] = "psiFile";
                break;
            case 19:
                objArr[0] = "rootLanguage";
                break;
            case 21:
                objArr[0] = FirebaseAnalytics.Param.CONTENT;
                break;
            case 22:
                objArr[0] = ConfigConstants.CONFIG_RENAMELIMIT_COPY;
                break;
            case 23:
            case 25:
                objArr[0] = "document";
                break;
            default:
                objArr[0] = "manager";
                break;
        }
        if (i == 3) {
            objArr[1] = "getFilePsiLock";
        } else if (i == 10) {
            objArr[1] = "createFile";
        } else if (i == 20) {
            objArr[1] = "getContent";
        } else if (i == 24) {
            objArr[1] = "getLastCommittedText";
        } else if (i == 26) {
            objArr[1] = "getStubBindingRoot";
        } else if (i != 27) {
            switch (i) {
                case 12:
                    objArr[1] = "getManager";
                    break;
                case 13:
                    objArr[1] = "getContents";
                    break;
                case 14:
                    objArr[1] = "getVirtualFile";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/AbstractFileViewProvider";
                    break;
            }
        } else {
            objArr[1] = "getFileType";
        }
        switch (i) {
            case 2:
                objArr[2] = "isFreeThreaded";
                break;
            case 3:
            case 10:
            case 12:
            case 13:
            case 14:
            case 20:
            case 24:
            case 26:
            case 27:
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
                objArr[2] = "createFile";
                break;
            case 15:
                objArr[2] = "getPsi";
                break;
            case 16:
                objArr[2] = "findElementAt";
                break;
            case 17:
                objArr[2] = "findReferenceAt";
                break;
            case 18:
                objArr[2] = "rootChanged";
                break;
            case 19:
                objArr[2] = "supportsIncrementalReparse";
                break;
            case 21:
                objArr[2] = "setContent";
                break;
            case 22:
                objArr[2] = "registerAsCopy";
                break;
            case 23:
                objArr[2] = "getLastCommittedText";
                break;
            case 25:
                objArr[2] = "getLastCommittedStamp";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 10 && i != 20 && i != 24 && i != 26 && i != 27) {
            switch (i) {
                case 12:
                case 13:
                case 14:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public void beforeContentsSynchronized() {
    }

    public abstract PsiFile getCachedPsi(Language language);

    public abstract List<PsiFile> getCachedPsiFiles();

    public abstract List<FileASTNode> getKnownTreeRoots();

    protected abstract PsiFile getPsiInner(Language language);

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public boolean supportsIncrementalReparse(Language language) {
        if (language != null) {
            return true;
        }
        $$$reportNull$$$0(19);
        return true;
    }

    protected AbstractFileViewProvider(PsiManager psiManager, VirtualFile virtualFile, boolean z) {
        boolean z2 = false;
        if (psiManager == null) {
            $$$reportNull$$$0(0);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(1);
        }
        this.myPsiLock = new PsiLock();
        this.myManager = (PsiManagerEx) psiManager;
        this.myVirtualFile = virtualFile;
        this.myEventSystemEnabled = z;
        setContent(new VirtualFileContent());
        if (isEventSystemEnabled() && !(virtualFile instanceof LightVirtualFile) && !(virtualFile.getFileSystem() instanceof NonPhysicalFileSystem)) {
            z2 = true;
        }
        this.myPhysical = z2;
        virtualFile.putUserData(FREE_THREADED, Boolean.valueOf(isFreeThreaded(this)));
        if ((virtualFile instanceof VirtualFileWindow) && !(this instanceof FreeThreadedFileViewProvider) && !isFreeThreaded(this)) {
            throw new IllegalArgumentException("Must not create " + getClass() + " for injected file " + virtualFile + "; InjectedFileViewProvider must be used instead");
        }
    }

    protected boolean shouldCreatePsi() {
        if (isIgnored()) {
            return false;
        }
        VirtualFile virtualFile = getVirtualFile();
        if (!isPhysical() || !virtualFile.isInLocalFileSystem()) {
            return true;
        }
        VirtualFile parent = virtualFile.getParent();
        if (parent == null) {
            return false;
        }
        if (getManager().findDirectory(parent) != null) {
            return true;
        }
        FileIndexFacade fileIndexFacade = FileIndexFacade.getInstance(getManager().getProject());
        return fileIndexFacade.isInLibrarySource(virtualFile) || fileIndexFacade.isInLibraryClasses(virtualFile);
    }

    public static boolean isFreeThreaded(FileViewProvider fileViewProvider) {
        if (fileViewProvider == null) {
            $$$reportNull$$$0(2);
        }
        return (fileViewProvider.getVirtualFile() instanceof LightVirtualFile) && !fileViewProvider.isEventSystemEnabled();
    }

    public PsiLock getFilePsiLock() {
        PsiLock psiLock = this.myPsiLock;
        if (psiLock == null) {
            $$$reportNull$$$0(3);
        }
        return psiLock;
    }

    protected final boolean isIgnored() {
        VirtualFile virtualFile = getVirtualFile();
        return !(virtualFile instanceof LightVirtualFile) && FileTypeRegistry.getInstance().isFileIgnored(virtualFile);
    }

    protected PsiFile createFile(Project project, VirtualFile virtualFile, FileType fileType) {
        if (project == null) {
            $$$reportNull$$$0(4);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(5);
        }
        if (fileType == null) {
            $$$reportNull$$$0(6);
        }
        return createFile(virtualFile, fileType, getBaseLanguage());
    }

    protected PsiFile createFile(VirtualFile virtualFile, FileType fileType, Language language) {
        PsiFile psiFileCreateFile;
        if (virtualFile == null) {
            $$$reportNull$$$0(7);
        }
        if (fileType == null) {
            $$$reportNull$$$0(8);
        }
        if (language == null) {
            $$$reportNull$$$0(9);
        }
        if (fileType.isBinary() || virtualFile.is(VFileProperty.SPECIAL)) {
            if (SingleRootFileViewProvider.isTooLargeForContentLoading(virtualFile)) {
                return new PsiLargeBinaryFileImpl((PsiManagerImpl) getManager(), this);
            }
            return new PsiBinaryFileImpl((PsiManagerImpl) getManager(), this);
        }
        if (SingleRootFileViewProvider.isTooLargeForIntelligence(virtualFile) || (psiFileCreateFile = createFile(language)) == null) {
            if (SingleRootFileViewProvider.isTooLargeForContentLoading(virtualFile)) {
                return new PsiLargeTextFileImpl(this);
            }
            return new PsiPlainTextFileImpl(this);
        }
        if (psiFileCreateFile == null) {
            $$$reportNull$$$0(10);
        }
        return psiFileCreateFile;
    }

    protected PsiFile createFile(Language language) {
        ParserDefinition parserDefinitionForLanguage;
        if (language == null) {
            $$$reportNull$$$0(11);
        }
        if (language == getBaseLanguage() && (parserDefinitionForLanguage = LanguageParserDefinitions.INSTANCE.forLanguage(language)) != null) {
            return parserDefinitionForLanguage.createFile(this);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public final PsiManagerEx getManager() {
        PsiManagerEx psiManagerEx = this.myManager;
        if (psiManagerEx == null) {
            $$$reportNull$$$0(12);
        }
        return psiManagerEx;
    }

    public CharSequence getContents() {
        CharSequence text = getContent().getText();
        if (text == null) {
            $$$reportNull$$$0(13);
        }
        return text;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public VirtualFile getVirtualFile() {
        VirtualFile virtualFile = this.myVirtualFile;
        if (virtualFile == null) {
            $$$reportNull$$$0(14);
        }
        return virtualFile;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Document getCachedDocument() {
        Document document = (Document) SoftReference.dereference(this.myDocument);
        return document != null ? document : FileDocumentManager.getInstance().getCachedDocument(getVirtualFile());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public Document getDocument() {
        Document document = (Document) SoftReference.dereference(this.myDocument);
        if (document == null) {
            document = FileDocumentManager.getInstance().getDocument(getVirtualFile());
            this.myDocument = document == null ? null : new java.lang.ref.SoftReference(document);
        }
        return document;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public final PsiFile getPsi(Language language) {
        if (language == null) {
            $$$reportNull$$$0(15);
        }
        if (!isPhysical()) {
            FileManager fileManager = getManager().getFileManager();
            VirtualFile virtualFile = getVirtualFile();
            if (fileManager.findCachedViewProvider(virtualFile) == null && getCachedPsiFiles().isEmpty()) {
                fileManager.setViewProvider(virtualFile, this);
            }
        }
        return getPsiInner(language);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase
    public FileViewProvider clone() {
        VirtualFile virtualFile = getVirtualFile();
        LightVirtualFile lightVirtualFile = new LightVirtualFile(virtualFile.getName(), virtualFile.getFileType(), getContents(), virtualFile.getCharset(), getModificationStamp());
        virtualFile.copyCopyableDataTo(lightVirtualFile);
        lightVirtualFile.setOriginalFile(virtualFile);
        UndoUtil.disableUndoFor(lightVirtualFile);
        lightVirtualFile.setCharset(virtualFile.getCharset());
        return createCopy(lightVirtualFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public PsiElement findElementAt(int i, Language language) {
        if (language == null) {
            $$$reportNull$$$0(16);
        }
        PsiFile psi = getPsi(language);
        if (psi != null) {
            return findElementAt(psi, i);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public PsiReference findReferenceAt(int i, Language language) {
        if (language == null) {
            $$$reportNull$$$0(17);
        }
        PsiFile psi = getPsi(language);
        if (psi != null) {
            return findReferenceAt(psi, i);
        }
        return null;
    }

    protected static PsiReference findReferenceAt(PsiFile psiFile, int i) {
        if (psiFile == null) {
            return null;
        }
        for (PsiElement firstChild = psiFile.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            int textLength = firstChild.getTextLength();
            if (textLength <= i) {
                i -= textLength;
            } else {
                return firstChild.findReferenceAt(i);
            }
        }
        return null;
    }

    public static PsiElement findElementAt(PsiElement psiElement, int i) {
        ASTNode node = psiElement == null ? null : psiElement.getNode();
        if (node == null) {
            return null;
        }
        return SourceTreeToPsiMap.treeElementToPsi(node.findLeafElementAt(i));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public void contentsSynchronized() {
        if (this.myContent instanceof PsiFileContent) {
            setContent(new VirtualFileContent());
        }
        checkLengthConsistency();
    }

    public final void onContentReload() {
        List<PsiFile> cachedPsiFiles = getCachedPsiFiles();
        ArrayList arrayList = new ArrayList(cachedPsiFiles.size());
        ArrayList arrayList2 = new ArrayList(cachedPsiFiles.size());
        for (PsiFile psiFile : cachedPsiFiles) {
            arrayList2.add(createChildrenChangeEvent(psiFile, true));
            arrayList.add(createChildrenChangeEvent(psiFile, false));
        }
        beforeContentsSynchronized();
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            ((PsiManagerImpl) getManager()).beforeChildrenChange((PsiTreeChangeEventImpl) it.next());
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            ((PsiManagerImpl) getManager()).beforeChildrenChange((PsiTreeChangeEventImpl) it2.next());
        }
        for (PsiFile psiFile2 : cachedPsiFiles) {
            if (psiFile2 instanceof PsiFileEx) {
                ((PsiFileEx) psiFile2).onContentReload();
            }
        }
        contentsSynchronized();
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            ((PsiManagerImpl) getManager()).childrenChanged((PsiTreeChangeEventImpl) it3.next());
        }
        Iterator it4 = arrayList2.iterator();
        while (it4.hasNext()) {
            ((PsiManagerImpl) getManager()).childrenChanged((PsiTreeChangeEventImpl) it4.next());
        }
    }

    private PsiTreeChangeEventImpl createChildrenChangeEvent(PsiFile psiFile, boolean z) {
        PsiTreeChangeEventImpl psiTreeChangeEventImpl = new PsiTreeChangeEventImpl(this.myManager);
        psiTreeChangeEventImpl.setParent(psiFile);
        psiTreeChangeEventImpl.setFile(psiFile);
        psiTreeChangeEventImpl.setGenericChange(z);
        if ((psiFile instanceof PsiFileImpl) && ((PsiFileImpl) psiFile).isContentsLoaded()) {
            psiTreeChangeEventImpl.setOffset(0);
            psiTreeChangeEventImpl.setOldLength(psiFile.getTextLength());
        }
        return psiTreeChangeEventImpl;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public void rootChanged(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(18);
        }
        if (psiFile instanceof PsiFileImpl) {
            PsiFileImpl psiFileImpl = (PsiFileImpl) psiFile;
            if (psiFileImpl.isContentsLoaded() && psiFile.isValid()) {
                setContent(new PsiFileContent(psiFileImpl.calcTreeElement(), LocalTimeCounter.currentTime()));
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public boolean isEventSystemEnabled() {
        return this.myEventSystemEnabled;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public boolean isPhysical() {
        return this.myPhysical;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public long getModificationStamp() {
        return getContent().getModificationStamp();
    }

    private Content getContent() {
        Content content = this.myContent;
        if (content == null) {
            $$$reportNull$$$0(20);
        }
        return content;
    }

    private void setContent(Content content) {
        if (content == null) {
            $$$reportNull$$$0(21);
        }
        this.myContent = content;
    }

    private void checkLengthConsistency() {
        Document cachedDocument = getCachedDocument();
        if (cachedDocument instanceof DocumentWindow) {
            return;
        }
        if (cachedDocument == null || !((PsiDocumentManagerBase) PsiDocumentManager.getInstance(this.myManager.getProject())).getSynchronizer().isInSynchronization(cachedDocument)) {
            List<FileASTNode> knownTreeRoots = getKnownTreeRoots();
            if (knownTreeRoots.isEmpty()) {
                return;
            }
            int textLength = this.myContent.getTextLength();
            for (FileASTNode fileASTNode : knownTreeRoots) {
                int textLength2 = fileASTNode.getTextLength();
                if (!isDocumentConsistentWithPsi(textLength, fileASTNode, textLength2)) {
                    PsiUtilCore.ensureValid(fileASTNode.getPsi());
                    ArrayList arrayListNewArrayList = ContainerUtil.newArrayList(new Attachment(this.myVirtualFile.getName(), this.myContent.getText().toString()), new Attachment(this.myVirtualFile.getNameWithoutExtension() + ".tree.txt", fileASTNode.getText()));
                    if (cachedDocument != null) {
                        arrayListNewArrayList.add(new Attachment(this.myVirtualFile.getNameWithoutExtension() + ".document.txt", cachedDocument.getText()));
                    }
                    LOG.error("Inconsistent " + fileASTNode.getElementType() + " tree in " + this + "; nodeLength=" + textLength2 + "; fileLength=" + textLength, (Attachment[]) arrayListNewArrayList.toArray(Attachment.EMPTY_ARRAY));
                }
            }
        }
    }

    private boolean isDocumentConsistentWithPsi(int i, FileASTNode fileASTNode, int i2) {
        if (i2 != i) {
            return false;
        }
        if (!ApplicationManager.getApplication().isUnitTestMode() || ApplicationManagerEx.isInStressTest()) {
            return true;
        }
        return fileASTNode.getPsi().textMatches(this.myContent.getText());
    }

    @Override // java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return getClass().getName() + "{vFile=" + this.myVirtualFile + (this.myVirtualFile instanceof VirtualFileWithId ? ", vFileId=" + ((VirtualFileWithId) this.myVirtualFile).getId() : "") + ", content=" + getContent() + ", eventSystemEnabled=" + isEventSystemEnabled() + '}';
    }

    public final void markInvalidated() {
        invalidateCachedPsi();
        Iterator<AbstractFileViewProvider> it = getKnownCopies().iterator();
        while (it.hasNext()) {
            this.myManager.getFileManager().setViewProvider(it.next().getVirtualFile(), null);
        }
    }

    public final void markPossiblyInvalidated() {
        invalidateCachedPsi();
        Iterator<AbstractFileViewProvider> it = getKnownCopies().iterator();
        while (it.hasNext()) {
            FileManagerImpl.markPossiblyInvalidated(it.next());
        }
    }

    private void invalidateCachedPsi() {
        for (PsiFile psiFile : getCachedPsiFiles()) {
            if (psiFile instanceof PsiFileEx) {
                ((PsiFileEx) psiFile).markInvalidated();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Iterable<AbstractFileViewProvider> getKnownCopies() {
        Set set = (Set) getUserData(KNOWN_COPIES);
        if (set != null) {
            return JBIterable.from(set).filter(new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return this.f$0.m6519x94054293((AbstractFileViewProvider) obj);
                }
            });
        }
        return Collections.emptySet();
    }

    /* JADX INFO: renamed from: lambda$getKnownCopies$0$org-jetbrains-kotlin-com-intellij-psi-AbstractFileViewProvider, reason: not valid java name */
    /* synthetic */ boolean m6518x15a43eb4(PsiFile psiFile) {
        return psiFile.getOriginalFile().getViewProvider() == this;
    }

    /* JADX INFO: renamed from: lambda$getKnownCopies$1$org-jetbrains-kotlin-com-intellij-psi-AbstractFileViewProvider, reason: not valid java name */
    /* synthetic */ boolean m6519x94054293(AbstractFileViewProvider abstractFileViewProvider) {
        return abstractFileViewProvider.getCachedPsiFiles().stream().anyMatch(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.m6518x15a43eb4((PsiFile) obj);
            }
        });
    }

    public final void registerAsCopy(AbstractFileViewProvider abstractFileViewProvider) {
        if (abstractFileViewProvider == null) {
            $$$reportNull$$$0(22);
        }
        if (abstractFileViewProvider instanceof FreeThreadedFileViewProvider) {
            LOG.assertTrue(this instanceof FreeThreadedFileViewProvider, "Injected file can't have non-injected original file");
        }
        Key<Set<AbstractFileViewProvider>> key = KNOWN_COPIES;
        Set set = (Set) getUserData(key);
        if (set == null) {
            set = (Set) putUserDataIfAbsent(key, Collections.newSetFromMap(CollectionFactory.createConcurrentWeakMap()));
        }
        if (abstractFileViewProvider.getUserData(key) != null && JBTreeTraverser.from(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return ((AbstractFileViewProvider) obj).getKnownCopies();
            }
        }).withRoot(abstractFileViewProvider).toList().contains(this)) {
            throw new IllegalStateException("An attempted cycle in view provider copy graph involving " + this + " and " + abstractFileViewProvider);
        }
        set.add(abstractFileViewProvider);
    }

    private class VirtualFileContent implements Content {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/AbstractFileViewProvider$VirtualFileContent", "getText"));
        }

        private VirtualFileContent() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider.Content
        public CharSequence getText() {
            VirtualFile virtualFile = AbstractFileViewProvider.this.getVirtualFile();
            if (virtualFile instanceof LightVirtualFile) {
                Document cachedDocument = AbstractFileViewProvider.this.getCachedDocument();
                if (cachedDocument != null) {
                    CharSequence lastCommittedText = AbstractFileViewProvider.this.getLastCommittedText(cachedDocument);
                    if (lastCommittedText == null) {
                        $$$reportNull$$$0(0);
                    }
                    return lastCommittedText;
                }
                CharSequence content = ((LightVirtualFile) virtualFile).getContent();
                if (content == null) {
                    $$$reportNull$$$0(1);
                }
                return content;
            }
            Document document = AbstractFileViewProvider.this.getDocument();
            if (document != null) {
                CharSequence lastCommittedText2 = AbstractFileViewProvider.this.getLastCommittedText(document);
                if (lastCommittedText2 == null) {
                    $$$reportNull$$$0(3);
                }
                return lastCommittedText2;
            }
            CharSequence charSequenceLoadText = LoadTextUtil.loadText(virtualFile);
            if (charSequenceLoadText == null) {
                $$$reportNull$$$0(2);
            }
            return charSequenceLoadText;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider.Content
        public int getTextLength() {
            return getText().length();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider.Content
        public long getModificationStamp() {
            Document cachedDocument = AbstractFileViewProvider.this.getCachedDocument();
            if (cachedDocument != null) {
                return AbstractFileViewProvider.this.getLastCommittedStamp(cachedDocument);
            }
            return AbstractFileViewProvider.this.getVirtualFile().getModificationStamp();
        }

        public String toString() {
            return "VirtualFileContent{size=" + AbstractFileViewProvider.this.getVirtualFile().getLength() + "}";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CharSequence getLastCommittedText(Document document) {
        if (document == null) {
            $$$reportNull$$$0(23);
        }
        CharSequence lastCommittedText = PsiDocumentManager.getInstance(this.myManager.getProject()).getLastCommittedText(document);
        if (lastCommittedText == null) {
            $$$reportNull$$$0(24);
        }
        return lastCommittedText;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getLastCommittedStamp(Document document) {
        if (document == null) {
            $$$reportNull$$$0(25);
        }
        return PsiDocumentManager.getInstance(this.myManager.getProject()).getLastCommittedStamp(document);
    }

    private static class PsiFileContent implements Content {
        private final FileElement myFileElement;
        private final long myModificationStamp;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 1 ? 3 : 2];
            if (i != 1) {
                objArr[0] = "fileElement";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/AbstractFileViewProvider$PsiFileContent";
            }
            if (i != 1) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/AbstractFileViewProvider$PsiFileContent";
            } else {
                objArr[1] = "getText";
            }
            if (i != 1) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 1) {
                throw new IllegalStateException(str2);
            }
        }

        PsiFileContent(FileElement fileElement, long j) {
            if (fileElement == null) {
                $$$reportNull$$$0(0);
            }
            this.myModificationStamp = j;
            this.myFileElement = fileElement;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider.Content
        public CharSequence getText() {
            String text = this.myFileElement.getText();
            if (text == null) {
                $$$reportNull$$$0(1);
            }
            return text;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider.Content
        public int getTextLength() {
            return this.myFileElement.getTextLength();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider.Content
        public long getModificationStamp() {
            return this.myModificationStamp;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public PsiFile getStubBindingRoot() {
        PsiFile psi = getPsi(getBaseLanguage());
        if (psi == null) {
            $$$reportNull$$$0(26);
        }
        return psi;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.FileViewProvider
    public final FileType getFileType() {
        FileType fileType = this.myVirtualFile.getFileType();
        if (fileType == null) {
            $$$reportNull$$$0(27);
        }
        return fileType;
    }
}
