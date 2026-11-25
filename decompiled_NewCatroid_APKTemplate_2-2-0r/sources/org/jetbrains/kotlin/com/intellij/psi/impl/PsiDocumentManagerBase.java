package org.jetbrains.kotlin.com.intellij.psi.impl;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import name.antonsmirnov.firmata.FormatHelper;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.codeWithMe.ClientId;
import org.jetbrains.kotlin.com.intellij.core.CoreBundle;
import org.jetbrains.kotlin.com.intellij.injected.editor.DocumentWindow;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.injection.InjectedLanguageManager;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.application.AppUIExecutor;
import org.jetbrains.kotlin.com.intellij.openapi.application.Application;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState;
import org.jetbrains.kotlin.com.intellij.openapi.application.ReadAction;
import org.jetbrains.kotlin.com.intellij.openapi.application.TransactionGuard;
import org.jetbrains.kotlin.com.intellij.openapi.application.TransactionGuardImpl;
import org.jetbrains.kotlin.com.intellij.openapi.application.WriteAction;
import org.jetbrains.kotlin.com.intellij.openapi.application.ex.ApplicationManagerEx;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Attachment;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.RuntimeExceptionWithAttachments;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.editor.DocumentRunnable;
import org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentEvent;
import org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentListener;
import org.jetbrains.kotlin.com.intellij.openapi.editor.ex.DocumentEx;
import org.jetbrains.kotlin.com.intellij.openapi.editor.ex.PrioritizedDocumentListener;
import org.jetbrains.kotlin.com.intellij.openapi.editor.impl.DocumentImpl;
import org.jetbrains.kotlin.com.intellij.openapi.editor.impl.FrozenDocument;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.impl.FileDocumentManagerBase;
import org.jetbrains.kotlin.com.intellij.openapi.progress.EmptyProgressIndicator;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicatorProvider;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.openapi.util.EmptyRunnable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.ExternalChangeAction;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocumentListener;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.PsiLanguageInjectionHost;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecursiveElementWalkingVisitor;
import org.jetbrains.kotlin.com.intellij.psi.SmartPointerManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.FileManagerImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerManagerImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement;
import org.jetbrains.kotlin.com.intellij.psi.text.BlockSupport;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.ConcurrencyUtil;
import org.jetbrains.kotlin.com.intellij.util.FileContentUtilCore;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.SystemProperties;
import org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable;
import org.jetbrains.kotlin.com.intellij.util.concurrency.Semaphore;
import org.jetbrains.kotlin.com.intellij.util.containers.CollectionFactory;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.ui.EdtInvocationManager;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiDocumentManagerBase extends PsiDocumentManager implements Disposable, DocumentListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Key<UncommittedInfo> FREE_THREADED_UNCOMMITTED_INFO;
    private final Map<Object, Runnable> actionsWhenAllDocumentsAreCommitted;
    private final Map<Document, List<Runnable>> myActionsAfterCommit;
    private final DocumentCommitProcessor myDocumentCommitProcessor;
    private final ThreadLocal<Integer> myIsCommitInProgress;
    private final List<PsiDocumentManager.Listener> myListeners;
    private boolean myPerformBackgroundCommit;
    protected final Project myProject;
    private final PsiManager myPsiManager;
    boolean myStopTrackingDocuments;
    private final PsiToDocumentSynchronizer mySynchronizer;
    final Set<Document> myUncommittedDocuments;
    private final Map<Document, UncommittedInfo> myUncommittedInfos;
    static final Logger LOG = Logger.getInstance((Class<?>) PsiDocumentManagerBase.class);
    private static final Key<Document> HARD_REF_TO_DOCUMENT = Key.create("HARD_REFERENCE_TO_DOCUMENT");
    private static final ThreadLocal<Boolean> ourIsFullReparseInProgress = new ThreadLocal<>();
    private static final Object PERFORM_ALWAYS_KEY = ObjectUtils.sentinel("PERFORM_ALWAYS");

    /* JADX WARN: Code duplicated, block: B:14:0x002b  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 4 && i != 12 && i != 22 && i != 58 && i != 75 && i != 90 && i != 98 && i != 69 && i != 70) {
            switch (i) {
                case 62:
                case 63:
                case 64:
                case 65:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 4 && i != 12 && i != 22 && i != 58 && i != 75 && i != 90 && i != 98 && i != 69 && i != 70) {
            switch (i) {
                case 62:
                case 63:
                case 64:
                case 65:
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
            case 5:
            case 7:
            case 8:
            case 9:
            case 19:
            case 21:
            case 24:
            case 25:
            case 29:
            case 32:
            case 35:
            case 37:
            case 38:
            case 50:
            case 54:
            case 55:
            case 57:
            case 59:
            case 61:
            case 66:
            case 67:
            case 68:
            case 71:
            case 72:
            case 73:
            case 74:
            case 79:
            case 80:
            case 81:
            case 84:
            case 85:
            case 88:
            case 99:
                objArr[0] = "document";
                break;
            case 2:
            case 39:
            case 87:
                objArr[0] = "psiFile";
                break;
            case 3:
                objArr[0] = "debugInfo";
                break;
            case 4:
            case 12:
            case 22:
            case 58:
            case 62:
            case 63:
            case 64:
            case 65:
            case 69:
            case 70:
            case 75:
            case 90:
            case 98:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiDocumentManagerBase";
                break;
            case 6:
            case 13:
            case 14:
            case 56:
            case 60:
            case 91:
                objArr[0] = "file";
                break;
            case 10:
            case 11:
            case 86:
                objArr[0] = "virtualFile";
                break;
            case 15:
            case 23:
            case 52:
            case 53:
                objArr[0] = "doc";
                break;
            case 16:
            case 18:
            case 20:
            case 44:
            case 46:
                objArr[0] = "action";
                break;
            case 17:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 26:
            case 30:
            case 33:
                objArr[0] = "finishProcessors";
                break;
            case 27:
            case 31:
            case 34:
                objArr[0] = "reparseInjectedProcessors";
                break;
            case 28:
            case 36:
            case 82:
                objArr[0] = "reason";
                break;
            case 40:
            case 43:
            case 47:
            case 49:
                objArr[0] = "runnable";
                break;
            case 41:
                objArr[0] = "computation";
                break;
            case 42:
                objArr[0] = "files";
                break;
            case 45:
            case 83:
                objArr[0] = "modality";
                break;
            case 48:
                objArr[0] = "modalityState";
                break;
            case 51:
                objArr[0] = ServiceSpecificExtraArgs.CastExtraArgs.LISTENER;
                break;
            case 76:
            case 78:
                objArr[0] = "event";
                break;
            case 77:
                objArr[0] = "viewProvider";
                break;
            case 89:
                objArr[0] = "parentDisposable";
                break;
            case 92:
                objArr[0] = "hostDocument";
                break;
            case 93:
                objArr[0] = "hostPsiFile";
                break;
            case 94:
                objArr[0] = "range";
                break;
            case 95:
                objArr[0] = "indicator";
                break;
            case 96:
                objArr[0] = "oldRoot";
                break;
            case 97:
                objArr[0] = "newRoot";
                break;
            default:
                objArr[0] = "project";
                break;
        }
        if (i == 4) {
            objArr[1] = "ensureValidFile";
        } else if (i == 12) {
            objArr[1] = "getFileManager";
        } else if (i == 22) {
            objArr[1] = "getAndClearActionsAfterCommit";
        } else if (i == 58) {
            objArr[1] = "getLastCommittedText";
        } else if (i == 75) {
            objArr[1] = "getTopLevelDocument";
        } else if (i == 90) {
            objArr[1] = "getSynchronizer";
        } else if (i == 98) {
            objArr[1] = "reparseChangedInjectedFragments";
        } else if (i == 69) {
            objArr[1] = "getEventsSinceCommit";
        } else if (i != 70) {
            switch (i) {
                case 62:
                case 63:
                case 64:
                case 65:
                    objArr[1] = "getLastCommittedDocument";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiDocumentManagerBase";
                    break;
            }
        } else {
            objArr[1] = "getUncommittedDocuments";
        }
        switch (i) {
            case 1:
            case 11:
                objArr[2] = "getPsiFile";
                break;
            case 2:
            case 3:
                objArr[2] = "ensureValidFile";
                break;
            case 4:
            case 12:
            case 22:
            case 58:
            case 62:
            case 63:
            case 64:
            case 65:
            case 69:
            case 70:
            case 75:
            case 90:
            case 98:
                break;
            case 5:
            case 6:
                objArr[2] = "associatePsi";
                break;
            case 7:
            case 10:
                objArr[2] = "getCachedPsiFile";
                break;
            case 8:
                objArr[2] = "getCachedViewProvider";
                break;
            case 9:
                objArr[2] = "getVirtualFile";
                break;
            case 13:
                objArr[2] = "getDocument";
                break;
            case 14:
                objArr[2] = "getCachedDocument";
                break;
            case 15:
            case 16:
                objArr[2] = "performForCommittedDocument";
                break;
            case 17:
            case 18:
                objArr[2] = "cancelAndRunWhenAllCommitted";
                break;
            case 19:
            case 20:
                objArr[2] = "addRunOnCommit";
                break;
            case 21:
                objArr[2] = "getAndClearActionsAfterCommit";
                break;
            case 23:
                objArr[2] = "commitDocument";
                break;
            case 24:
                objArr[2] = "isEventSystemEnabled";
                break;
            case 25:
            case 26:
            case 27:
            case 28:
                objArr[2] = "finishCommit";
                break;
            case 29:
            case 30:
            case 31:
                objArr[2] = "finishCommitInWriteAction";
                break;
            case 32:
            case 33:
            case 34:
                objArr[2] = "commitToExistingPsi";
                break;
            case 35:
            case 36:
                objArr[2] = "checkAllElementsValid";
                break;
            case 37:
            case 38:
            case 39:
                objArr[2] = "doCommit";
                break;
            case 40:
                objArr[2] = "executeInsideCommit";
                break;
            case 41:
            case 43:
                objArr[2] = "commitAndRunReadAction";
                break;
            case 42:
                objArr[2] = "reparseFiles";
                break;
            case 44:
            case 45:
            case 46:
                objArr[2] = "performWhenAllCommitted";
                break;
            case 47:
            case 48:
            case 49:
                objArr[2] = "performLaterWhenAllCommitted";
                break;
            case 50:
                objArr[2] = "runAfterCommitActions";
                break;
            case 51:
                objArr[2] = "addListener";
                break;
            case 52:
                objArr[2] = "isDocumentBlockedByPsi";
                break;
            case 53:
                objArr[2] = "doPostponedOperationsAndUnblockDocument";
                break;
            case 54:
                objArr[2] = "fireDocumentCreated";
                break;
            case 55:
            case 56:
                objArr[2] = "fireFileCreated";
                break;
            case 57:
                objArr[2] = "getLastCommittedText";
                break;
            case 59:
                objArr[2] = "getLastCommittedStamp";
                break;
            case 60:
            case 61:
                objArr[2] = "getLastCommittedDocument";
                break;
            case 66:
                objArr[2] = "getUncommittedInfo";
                break;
            case 67:
                objArr[2] = "freezeWindow";
                break;
            case 68:
                objArr[2] = "getEventsSinceCommit";
                break;
            case 71:
                objArr[2] = "isInUncommittedSet";
                break;
            case 72:
                objArr[2] = "isUncommited";
                break;
            case 73:
                objArr[2] = "isCommitted";
                break;
            case 74:
                objArr[2] = "getTopLevelDocument";
                break;
            case 76:
                objArr[2] = "beforeDocumentChange";
                break;
            case 77:
                objArr[2] = "beforeDocumentChangeOnUnlockedDocument";
                break;
            case 78:
                objArr[2] = "documentChanged";
                break;
            case 79:
                objArr[2] = "bulkUpdateStarting";
                break;
            case 80:
                objArr[2] = "bulkUpdateFinished";
                break;
            case 81:
            case 82:
            case 83:
                objArr[2] = "retainProviderAndCommitAsync";
                break;
            case 84:
                objArr[2] = "handleCommitWithoutPsi";
                break;
            case 85:
                objArr[2] = "clearUncommittedInfo";
                break;
            case 86:
                objArr[2] = "isRelevant";
                break;
            case 87:
            case 88:
                objArr[2] = "checkConsistency";
                break;
            case 89:
                objArr[2] = "disableBackgroundCommit";
                break;
            case 91:
                objArr[2] = "reparseFileFromText";
                break;
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
                objArr[2] = "reparseChangedInjectedFragments";
                break;
            case 99:
                objArr[2] = "someDocumentDebugInfo";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 12 && i != 22 && i != 58 && i != 75 && i != 90 && i != 98 && i != 69 && i != 70) {
            switch (i) {
                case 62:
                case 63:
                case 64:
                case 65:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    protected void beforeDocumentChangeOnUnlockedDocument(FileViewProvider fileViewProvider) {
        if (fileViewProvider == null) {
            $$$reportNull$$$0(77);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
    public void dispose() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    public void doPostponedOperationsAndUnblockDocument(Document document) {
        if (document == null) {
            $$$reportNull$$$0(53);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    public boolean isDocumentBlockedByPsi(Document document) {
        if (document != null) {
            return false;
        }
        $$$reportNull$$$0(52);
        return false;
    }

    protected PsiDocumentManagerBase(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        this.myActionsAfterCommit = CollectionFactory.createConcurrentWeakMap();
        this.myUncommittedDocuments = Collections.newSetFromMap(CollectionFactory.createConcurrentWeakMap());
        this.myUncommittedInfos = new ConcurrentHashMap();
        this.FREE_THREADED_UNCOMMITTED_INFO = Key.create("FREE_THREADED_UNCOMMITTED_INFO");
        this.myPerformBackgroundCommit = true;
        this.myIsCommitInProgress = new ThreadLocal<>();
        this.myListeners = ContainerUtil.createLockFreeCopyOnWriteList();
        this.actionsWhenAllDocumentsAreCommitted = new LinkedHashMap();
        this.myProject = project;
        this.myPsiManager = PsiManager.getInstance(project);
        this.myDocumentCommitProcessor = (DocumentCommitProcessor) ApplicationManager.getApplication().getService(DocumentCommitProcessor.class);
        this.mySynchronizer = new PsiToDocumentSynchronizer(this, project.getMessageBus());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    public PsiFile getPsiFile(Document document) {
        PsiFile psiFile;
        if (document == null) {
            $$$reportNull$$$0(1);
        }
        if ((document instanceof DocumentWindow) && !((DocumentWindow) document).isValid()) {
            return null;
        }
        PsiFile cachedPsiFile = getCachedPsiFile(document);
        if (cachedPsiFile != null) {
            return ensureValidFile(cachedPsiFile, "Cached PSI");
        }
        VirtualFile file = FileDocumentManager.getInstance().getFile(document);
        if (file == null || !file.isValid() || (psiFile = getPsiFile(file)) == null) {
            return null;
        }
        fireFileCreated(document, psiFile);
        return psiFile;
    }

    private static PsiFile ensureValidFile(PsiFile psiFile, String str) {
        if (psiFile == null) {
            $$$reportNull$$$0(2);
        }
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (!psiFile.isValid()) {
            throw new PsiInvalidElementAccessException(psiFile, str);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(4);
        }
        return psiFile;
    }

    public void associatePsi(Document document, PsiFile psiFile) {
        if (document == null) {
            $$$reportNull$$$0(5);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(6);
        }
        if (psiFile.getProject() != this.myProject) {
            throw new IllegalArgumentException("Method associatePsi() called with file from the wrong project. Expected: " + this.myProject + " but got: " + psiFile.getProject());
        }
        VirtualFile virtualFile = psiFile.getViewProvider().getVirtualFile();
        Document cachedDocument = FileDocumentManager.getInstance().getCachedDocument(virtualFile);
        if (cachedDocument != null && cachedDocument != document) {
            throw new IllegalStateException("Can't replace existing document");
        }
        FileDocumentManagerBase.registerDocument(document, virtualFile);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    public final PsiFile getCachedPsiFile(Document document) {
        if (document == null) {
            $$$reportNull$$$0(7);
        }
        VirtualFile file = FileDocumentManager.getInstance().getFile(document);
        if (file == null || !file.isValid()) {
            return null;
        }
        return getCachedPsiFile(file);
    }

    FileViewProvider getCachedViewProvider(Document document) {
        if (document == null) {
            $$$reportNull$$$0(8);
        }
        VirtualFile virtualFile = getVirtualFile(document);
        if (virtualFile == null) {
            return null;
        }
        return getFileManager().findCachedViewProvider(virtualFile);
    }

    private static VirtualFile getVirtualFile(Document document) {
        if (document == null) {
            $$$reportNull$$$0(9);
        }
        VirtualFile file = FileDocumentManager.getInstance().getFile(document);
        if (file == null || !file.isValid()) {
            return null;
        }
        return file;
    }

    PsiFile getCachedPsiFile(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(10);
        }
        return getFileManager().getCachedPsiFile(virtualFile);
    }

    private PsiFile getPsiFile(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(11);
        }
        return getFileManager().findFile(virtualFile);
    }

    private FileManager getFileManager() {
        FileManager fileManager = ((PsiManagerEx) this.myPsiManager).getFileManager();
        if (fileManager == null) {
            $$$reportNull$$$0(12);
        }
        return fileManager;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    public Document getDocument(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(13);
        }
        Document cachedDocument = getCachedDocument(psiFile);
        if (cachedDocument != null) {
            if (!psiFile.getViewProvider().isPhysical()) {
                PsiUtilCore.ensureValid(psiFile);
                associatePsi(cachedDocument, psiFile);
            }
            return cachedDocument;
        }
        FileViewProvider viewProvider = psiFile.getViewProvider();
        if (!viewProvider.isEventSystemEnabled()) {
            return null;
        }
        Document document = FileDocumentManager.getInstance().getDocument(viewProvider.getVirtualFile());
        if (document != null) {
            if (document.getTextLength() != psiFile.getTextLength()) {
                throw new RuntimeExceptionWithAttachments("Document/PSI mismatch: " + psiFile + " of " + psiFile.getClass() + "; viewProvider=" + viewProvider + "; uncommitted=" + Arrays.toString(getUncommittedDocuments()), new Attachment("document.txt", document.getText()), new Attachment("psi.txt", psiFile.getText()));
            }
            if (!viewProvider.isPhysical()) {
                PsiUtilCore.ensureValid(psiFile);
                associatePsi(document, psiFile);
                psiFile.putUserData(HARD_REF_TO_DOCUMENT, document);
            }
        }
        return document;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    public Document getCachedDocument(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(14);
        }
        if (!psiFile.getIsPhysical()) {
            return null;
        }
        return FileDocumentManager.getInstance().getCachedDocument(psiFile.getViewProvider().getVirtualFile());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    /* JADX INFO: renamed from: commitAllDocuments, reason: merged with bridge method [inline-methods] */
    public void m6553xce7c3507() {
        ApplicationManager.getApplication().assertIsWriteThread();
        ((TransactionGuardImpl) TransactionGuard.getInstance()).assertWriteActionAllowed();
        if (this.myUncommittedDocuments.isEmpty()) {
            return;
        }
        for (Document document : getUncommittedDocuments()) {
            if (isCommitted(document)) {
                if (isEventSystemEnabled(document)) {
                    LOG.error("Committed document in uncommitted set: " + document);
                }
            } else if (!doCommit(document)) {
                LOG.error("Couldn't commit " + document);
            }
        }
        LOG.assertTrue(!hasEventSystemEnabledUncommittedDocuments(), this.myUncommittedDocuments);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    public boolean commitAllDocumentsUnderProgress() throws Throwable {
        Application application = ApplicationManager.getApplication();
        if (application.isWriteThread()) {
            if (application.isWriteAccessAllowed()) {
                m6553xce7c3507();
                if (!application.isUnitTestMode()) {
                    LOG.error("Do not call commitAllDocumentsUnderProgress inside write-action");
                }
                return true;
            }
            if (application.isUnitTestMode()) {
                WriteAction.run(new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase$$ExternalSyntheticLambda20
                    @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
                    public final void run() {
                        this.f$0.m6553xce7c3507();
                    }
                });
                return true;
            }
        }
        return ProgressManager.getInstance().runProcessWithProgressSynchronously(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m6555x85ff2c89();
            }
        }, CoreBundle.message("progress.title.processing.documents", new Object[0]), true, this.myProject);
    }

    /* JADX INFO: renamed from: lambda$commitAllDocumentsUnderProgress$2$org-jetbrains-kotlin-com-intellij-psi-impl-PsiDocumentManagerBase, reason: not valid java name */
    /* synthetic */ void m6555x85ff2c89() {
        final Semaphore semaphore = new Semaphore(1);
        AppUIExecutor.onWriteThread().later().submit(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m6554xaa3db0c8(semaphore);
            }
        });
        while (!semaphore.waitFor(50L)) {
            ProgressManager.checkCanceled();
        }
    }

    /* JADX INFO: renamed from: lambda$commitAllDocumentsUnderProgress$1$org-jetbrains-kotlin-com-intellij-psi-impl-PsiDocumentManagerBase, reason: not valid java name */
    /* synthetic */ void m6554xaa3db0c8(final Semaphore semaphore) {
        PsiDocumentManager.getInstance(this.myProject).performWhenAllCommitted(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase.1
            @Override // java.lang.Runnable
            public void run() {
                semaphore.up();
            }

            public String toString() {
                return "commitAllDocumentsUnderProgress()";
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    public void performForCommittedDocument(Document document, Runnable runnable) {
        if (document == null) {
            $$$reportNull$$$0(15);
        }
        if (runnable == null) {
            $$$reportNull$$$0(16);
        }
        Document topLevelDocument = getTopLevelDocument(document);
        if (isCommitted(topLevelDocument)) {
            runnable.run();
        } else {
            addRunOnCommit(topLevelDocument, runnable);
        }
    }

    public boolean cancelAndRunWhenAllCommitted(Object obj, Runnable runnable) {
        if (obj == null) {
            $$$reportNull$$$0(17);
        }
        if (runnable == null) {
            $$$reportNull$$$0(18);
        }
        ApplicationManager.getApplication().assertIsDispatchThread();
        if (this.myProject.isDisposed()) {
            runnable.run();
            return true;
        }
        if (!hasEventSystemEnabledUncommittedDocuments()) {
            isCommitInProgress();
            runnable.run();
            return true;
        }
        checkWeAreOutsideAfterCommitHandler();
        this.actionsWhenAllDocumentsAreCommitted.put(obj, ClientId.decorateRunnable(runnable));
        return false;
    }

    public void addRunOnCommit(Document document, Runnable runnable) {
        if (document == null) {
            $$$reportNull$$$0(19);
        }
        if (runnable == null) {
            $$$reportNull$$$0(20);
        }
        this.myActionsAfterCommit.computeIfAbsent(document, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ContainerUtil.createConcurrentList();
            }
        }).add(ClientId.decorateRunnable(runnable));
    }

    private Runnable[] getAndClearActionsAfterCommit(Document document) {
        if (document == null) {
            $$$reportNull$$$0(21);
        }
        List<Runnable> listRemove = this.myActionsAfterCommit.remove(document);
        Runnable[] runnableArr = listRemove == null ? ArrayUtil.EMPTY_RUNNABLE_ARRAY : (Runnable[]) listRemove.toArray(ArrayUtil.EMPTY_RUNNABLE_ARRAY);
        if (runnableArr == null) {
            $$$reportNull$$$0(22);
        }
        return runnableArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    public void commitDocument(Document document) {
        if (document == null) {
            $$$reportNull$$$0(23);
        }
        Document topLevelDocument = getTopLevelDocument(document);
        if (isEventSystemEnabled(topLevelDocument)) {
            ((TransactionGuardImpl) TransactionGuard.getInstance()).assertWriteActionAllowed();
        }
        if (isCommitted(topLevelDocument)) {
            return;
        }
        doCommit(topLevelDocument);
    }

    boolean isEventSystemEnabled(Document document) {
        if (document == null) {
            $$$reportNull$$$0(24);
        }
        FileViewProvider cachedViewProvider = getCachedViewProvider(document);
        return cachedViewProvider != null && cachedViewProvider.isEventSystemEnabled();
    }

    boolean finishCommit(final Document document, final List<? extends BooleanRunnable> list, final List<? extends BooleanRunnable> list2, boolean z, Object obj) {
        if (document == null) {
            $$$reportNull$$$0(25);
        }
        if (list == null) {
            $$$reportNull$$$0(26);
        }
        if (list2 == null) {
            $$$reportNull$$$0(27);
        }
        if (obj == null) {
            $$$reportNull$$$0(28);
        }
        if (isEventSystemEnabled(document)) {
            ApplicationManager.getApplication().assertIsWriteThread();
        }
        final boolean[] zArr = {true};
        if (z) {
            zArr[0] = finishCommitInWriteAction(document, list, list2, true);
        } else {
            ApplicationManager.getApplication().runWriteAction(new DocumentRunnable(document, this.myProject) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase.2
                @Override // java.lang.Runnable
                public void run() {
                    zArr[0] = PsiDocumentManagerBase.this.finishCommitInWriteAction(document, list, list2, false);
                }
            });
        }
        if (zArr[0]) {
            runAfterCommitActions(document);
            if (DebugUtil.DO_EXPENSIVE_CHECKS && !ApplicationManagerEx.isInStressTest()) {
                checkAllElementsValid(document, obj);
            }
        }
        return zArr[0];
    }

    protected boolean finishCommitInWriteAction(final Document document, final List<? extends BooleanRunnable> list, final List<? extends BooleanRunnable> list2, final boolean z) {
        if (document == null) {
            $$$reportNull$$$0(29);
        }
        if (list == null) {
            $$$reportNull$$$0(30);
        }
        if (list2 == null) {
            $$$reportNull$$$0(31);
        }
        if (isEventSystemEnabled(document)) {
            ApplicationManager.getApplication().assertIsWriteThread();
        }
        if (this.myProject.isDisposed()) {
            return false;
        }
        final VirtualFile file = FileDocumentManager.getInstance().getFile(document);
        if (file != null) {
            getSmartPointerManager().fastenBelts(file);
        }
        final FileViewProvider cachedViewProvider = getCachedViewProvider(document);
        final AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        executeInsideCommit(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m6562x14e1e8bb(atomicBoolean, cachedViewProvider, document, list, list2, z, file);
            }
        });
        return atomicBoolean.get();
    }

    /* JADX INFO: renamed from: lambda$finishCommitInWriteAction$5$org-jetbrains-kotlin-com-intellij-psi-impl-PsiDocumentManagerBase, reason: not valid java name */
    /* synthetic */ void m6562x14e1e8bb(AtomicBoolean atomicBoolean, final FileViewProvider fileViewProvider, final Document document, final List list, final List list2, final boolean z, final VirtualFile virtualFile) {
        try {
            try {
                atomicBoolean.set(((Boolean) ProgressManager.getInstance().computeInNonCancelableSection(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase$$ExternalSyntheticLambda12
                    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
                    public final Object compute() {
                        return this.f$0.m6561x39206cfa(fileViewProvider, document, list, list2, z, virtualFile);
                    }
                })).booleanValue());
                if (!atomicBoolean.get()) {
                    return;
                }
            } catch (Throwable th) {
                th = th;
                Throwable th2 = th;
                try {
                    try {
                        forceReload(virtualFile, fileViewProvider);
                        LOG.error("Exception while committing " + fileViewProvider + ", eventSystemEnabled=" + isEventSystemEnabled(document), th2);
                        if (!atomicBoolean.get()) {
                            return;
                        }
                    } catch (Throwable th3) {
                        if (atomicBoolean.get()) {
                            this.myUncommittedDocuments.remove(document);
                        }
                        throw th3;
                    }
                } catch (Throwable th4) {
                    LOG.error("Exception while committing " + fileViewProvider + ", eventSystemEnabled=" + isEventSystemEnabled(document), th2);
                    throw th4;
                }
            }
        } catch (Throwable th5) {
            th = th5;
        }
        this.myUncommittedDocuments.remove(document);
    }

    /* JADX INFO: renamed from: lambda$finishCommitInWriteAction$4$org-jetbrains-kotlin-com-intellij-psi-impl-PsiDocumentManagerBase, reason: not valid java name */
    /* synthetic */ Boolean m6561x39206cfa(FileViewProvider fileViewProvider, Document document, List list, List list2, boolean z, VirtualFile virtualFile) throws RuntimeException {
        if (fileViewProvider != null) {
            return Boolean.valueOf(commitToExistingPsi(document, list, list2, z, virtualFile));
        }
        handleCommitWithoutPsi(document);
        return true;
    }

    private boolean commitToExistingPsi(Document document, List<? extends BooleanRunnable> list, List<? extends BooleanRunnable> list2, boolean z, VirtualFile virtualFile) {
        if (document == null) {
            $$$reportNull$$$0(32);
        }
        if (list == null) {
            $$$reportNull$$$0(33);
        }
        if (list2 == null) {
            $$$reportNull$$$0(34);
        }
        Iterator<? extends BooleanRunnable> it = list.iterator();
        while (it.hasNext()) {
            if (!it.next().run()) {
                return false;
            }
        }
        clearUncommittedInfo(document);
        if (virtualFile != null) {
            getSmartPointerManager().updatePointerTargetsAfterReparse(virtualFile);
        }
        FileViewProvider cachedViewProvider = getCachedViewProvider(document);
        if (cachedViewProvider != null) {
            cachedViewProvider.contentsSynchronized();
        }
        Iterator<? extends BooleanRunnable> it2 = list2.iterator();
        while (it2.hasNext()) {
            if (!it2.next().run()) {
                return false;
            }
        }
        return true;
    }

    void forceReload(VirtualFile virtualFile, final FileViewProvider fileViewProvider) throws Throwable {
        if (fileViewProvider != null) {
            DebugUtil.performPsiModification("psi.forceReload", new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase$$ExternalSyntheticLambda17
                @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
                public final void run() {
                    ((AbstractFileViewProvider) fileViewProvider).markInvalidated();
                }
            });
        }
        if (virtualFile != null) {
            ((FileManagerImpl) getFileManager()).forceReload(virtualFile);
        }
    }

    private void checkAllElementsValid(Document document, final Object obj) {
        if (document == null) {
            $$$reportNull$$$0(35);
        }
        if (obj == null) {
            $$$reportNull$$$0(36);
        }
        final PsiFile cachedPsiFile = getCachedPsiFile(document);
        if (cachedPsiFile != null) {
            cachedPsiFile.accept(new PsiRecursiveElementWalkingVisitor() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase.3
                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "org/jetbrains/kotlin/com/intellij/psi/impl/PsiDocumentManagerBase$3", "visitElement"));
                }

                @Override // org.jetbrains.kotlin.com.intellij.psi.PsiRecursiveElementWalkingVisitor, org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
                public void visitElement(PsiElement psiElement) {
                    if (psiElement == null) {
                        $$$reportNull$$$0(0);
                    }
                    if (!psiElement.isValid()) {
                        throw new AssertionError("Commit to '" + cachedPsiFile.getVirtualFile() + "' has led to invalid element: " + psiElement + "; Reason: '" + obj + FormatHelper.QUOTE);
                    }
                }
            });
        }
    }

    private boolean doCommit(final Document document) {
        if (document == null) {
            $$$reportNull$$$0(37);
        }
        if (getSynchronizer().isDocumentAffectedByTransactions(document)) {
            return false;
        }
        final PsiFile psiFile = getPsiFile(document);
        if (psiFile == null) {
            this.myUncommittedDocuments.remove(document);
            runAfterCommitActions(document);
            return true;
        }
        if (ApplicationManager.getApplication().isDispatchThread()) {
            ApplicationManager.getApplication().runWriteAction(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m6559xf3e0d4b3(document, psiFile);
                }
            });
        } else {
            m6559xf3e0d4b3(document, psiFile);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: doCommit, reason: merged with bridge method [inline-methods] */
    public void m6559xf3e0d4b3(final Document document, final PsiFile psiFile) {
        if (document == null) {
            $$$reportNull$$$0(38);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(39);
        }
        executeInsideCommit(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m6560xcfa25074(document, psiFile);
            }
        });
        runAfterCommitActions(document);
    }

    /* JADX INFO: renamed from: lambda$doCommit$8$org-jetbrains-kotlin-com-intellij-psi-impl-PsiDocumentManagerBase, reason: not valid java name */
    /* synthetic */ void m6560xcfa25074(Document document, PsiFile psiFile) {
        this.myDocumentCommitProcessor.commitSynchronously(document, this.myProject, psiFile);
    }

    public boolean isCommitInProgress() {
        return this.myIsCommitInProgress.get() != null || isFullReparseInProgress();
    }

    private void executeInsideCommit(Runnable runnable) {
        if (runnable == null) {
            $$$reportNull$$$0(40);
        }
        Integer num = this.myIsCommitInProgress.get();
        this.myIsCommitInProgress.set(Integer.valueOf(num != null ? 1 + num.intValue() : 1));
        try {
            runnable.run();
        } finally {
            this.myIsCommitInProgress.set(num);
        }
    }

    public static boolean isFullReparseInProgress() {
        return ourIsFullReparseInProgress.get() == Boolean.TRUE;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    public <T> T commitAndRunReadAction(final Computable<T> computable) {
        if (computable == null) {
            $$$reportNull$$$0(41);
        }
        final Ref refCreate = Ref.create(null);
        commitAndRunReadAction(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                refCreate.set(computable.compute());
            }
        });
        return (T) refCreate.get();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    public void reparseFiles(Collection<? extends VirtualFile> collection, boolean z) {
        if (collection == null) {
            $$$reportNull$$$0(42);
        }
        FileContentUtilCore.reparseFiles(collection);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    public void commitAndRunReadAction(final Runnable runnable) {
        if (runnable == null) {
            $$$reportNull$$$0(43);
        }
        Application application = ApplicationManager.getApplication();
        if (application.isDispatchThread()) {
            m6553xce7c3507();
            runnable.run();
            return;
        }
        if (application.isReadAccessAllowed()) {
            LOG.error("Don't call commitAndRunReadAction inside ReadAction, it will cause a deadlock. " + Thread.currentThread());
        }
        while (!((Boolean) ReadAction.compute(new ThrowableComputable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ThrowableComputable
            public final Object compute() {
                return this.f$0.m6556x71bdd2f0(runnable);
            }
        })).booleanValue()) {
            final ModalityState modalityStateDefaultModalityState = ModalityState.defaultModalityState();
            final Semaphore semaphore = new Semaphore(1);
            AppUIExecutor.onWriteThread(ModalityState.any()).submit(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m6557x2940ca72(semaphore, modalityStateDefaultModalityState);
                }
            });
            while (!semaphore.waitFor(10L)) {
                ProgressManager.checkCanceled();
            }
        }
    }

    /* JADX INFO: renamed from: lambda$commitAndRunReadAction$10$org-jetbrains-kotlin-com-intellij-psi-impl-PsiDocumentManagerBase, reason: not valid java name */
    /* synthetic */ Boolean m6556x71bdd2f0(Runnable runnable) throws RuntimeException {
        if (!hasEventSystemEnabledUncommittedDocuments()) {
            runnable.run();
            return true;
        }
        return false;
    }

    /* JADX INFO: renamed from: lambda$commitAndRunReadAction$12$org-jetbrains-kotlin-com-intellij-psi-impl-PsiDocumentManagerBase, reason: not valid java name */
    /* synthetic */ void m6557x2940ca72(final Semaphore semaphore, ModalityState modalityState) {
        if (this.myProject.isDisposed()) {
            semaphore.up();
        } else {
            performWhenAllCommitted(modalityState, new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    semaphore.up();
                }
            });
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    public boolean performWhenAllCommitted(Runnable runnable) {
        if (runnable == null) {
            $$$reportNull$$$0(44);
        }
        return performWhenAllCommitted(ModalityState.defaultModalityState(), runnable);
    }

    private boolean performWhenAllCommitted(ModalityState modalityState, Runnable runnable) {
        if (modalityState == null) {
            $$$reportNull$$$0(45);
        }
        if (runnable == null) {
            $$$reportNull$$$0(46);
        }
        ApplicationManager.getApplication().assertIsDispatchThread();
        checkWeAreOutsideAfterCommitHandler();
        if (!hasEventSystemEnabledUncommittedDocuments()) {
            runnable.run();
            return true;
        }
        Map<Object, Runnable> map = this.actionsWhenAllDocumentsAreCommitted;
        Object obj = PERFORM_ALWAYS_KEY;
        CompositeRunnable compositeRunnable = (CompositeRunnable) map.get(obj);
        if (compositeRunnable == null) {
            compositeRunnable = new CompositeRunnable();
            this.actionsWhenAllDocumentsAreCommitted.put(obj, compositeRunnable);
        }
        compositeRunnable.add(ClientId.decorateRunnable(runnable));
        if (modalityState == ModalityState.NON_MODAL || !TransactionGuard.getInstance().isWriteSafeModality(modalityState)) {
            return false;
        }
        Iterator<Document> it = this.myUncommittedDocuments.iterator();
        while (it.hasNext()) {
            retainProviderAndCommitAsync(it.next(), "re-added because performWhenAllCommitted(" + modalityState + ") was called", modalityState);
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    public void performLaterWhenAllCommitted(Runnable runnable) {
        if (runnable == null) {
            $$$reportNull$$$0(47);
        }
        performLaterWhenAllCommitted(ModalityState.defaultModalityState(), runnable);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase$4, reason: invalid class name */
    class AnonymousClass4 implements Runnable {
        final /* synthetic */ ModalityState val$modalityState;
        final /* synthetic */ Runnable val$runnable;

        AnonymousClass4(Runnable runnable, ModalityState modalityState) {
            this.val$runnable = runnable;
            this.val$modalityState = modalityState;
        }

        @Override // java.lang.Runnable
        public void run() {
            Application application = ApplicationManager.getApplication();
            final Runnable runnable = this.val$runnable;
            application.invokeLater(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m6566xeeea70b(runnable);
                }
            }, this.val$modalityState, PsiDocumentManagerBase.this.myProject.getDisposed());
        }

        /* JADX INFO: renamed from: lambda$run$0$org-jetbrains-kotlin-com-intellij-psi-impl-PsiDocumentManagerBase$4, reason: not valid java name */
        /* synthetic */ void m6566xeeea70b(Runnable runnable) {
            if (PsiDocumentManagerBase.this.hasEventSystemEnabledUncommittedDocuments()) {
                PsiDocumentManagerBase.this.performLaterWhenAllCommitted(runnable);
            } else {
                runnable.run();
            }
        }

        public String toString() {
            return "performLaterWhenAllCommitted()";
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    public void performLaterWhenAllCommitted(ModalityState modalityState, Runnable runnable) {
        if (modalityState == null) {
            $$$reportNull$$$0(48);
        }
        if (runnable == null) {
            $$$reportNull$$$0(49);
        }
        final AnonymousClass4 anonymousClass4 = new AnonymousClass4(runnable, modalityState);
        if (ApplicationManager.getApplication().isDispatchThread() && isInsideCommitHandler()) {
            anonymousClass4.run();
        } else {
            EdtInvocationManager.invokeLaterIfNeeded(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m6563x4660f3aa(anonymousClass4);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$performLaterWhenAllCommitted$13$org-jetbrains-kotlin-com-intellij-psi-impl-PsiDocumentManagerBase, reason: not valid java name */
    /* synthetic */ void m6563x4660f3aa(Runnable runnable) {
        if (this.myProject.isDisposed()) {
            return;
        }
        performWhenAllCommitted(runnable);
    }

    private static class CompositeRunnable extends ArrayList<Runnable> implements Runnable {
        private CompositeRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            Iterator<Runnable> it = iterator();
            while (it.hasNext()) {
                it.next().run();
            }
        }
    }

    private void runAfterCommitActions(final Document document) {
        if (document == null) {
            $$$reportNull$$$0(50);
        }
        Application application = ApplicationManager.getApplication();
        if (!application.isDispatchThread() && isEventSystemEnabled(document)) {
            application.invokeLater(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m6564x5d9fc0f0(document);
                }
            });
            return;
        }
        for (Runnable runnable : getAndClearActionsAfterCommit(document)) {
            runnable.run();
        }
        if (application.isDispatchThread()) {
            m6565x39613cb1();
        } else {
            application.invokeLater(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m6565x39613cb1();
                }
            }, this.myProject.getDisposed());
        }
    }

    /* JADX INFO: renamed from: lambda$runAfterCommitActions$14$org-jetbrains-kotlin-com-intellij-psi-impl-PsiDocumentManagerBase, reason: not valid java name */
    /* synthetic */ void m6564x5d9fc0f0(Document document) {
        if (this.myProject.isDisposed() || !isCommitted(document)) {
            return;
        }
        runAfterCommitActions(document);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: runActionsWhenAllCommitted, reason: merged with bridge method [inline-methods] */
    public void m6565x39613cb1() {
        ApplicationManager.getApplication().assertIsDispatchThread();
        if (mayRunActionsWhenAllCommitted()) {
            ArrayList<Runnable> arrayList = new ArrayList(this.actionsWhenAllDocumentsAreCommitted.values());
            beforeCommitHandler();
            ArrayList<Pair> arrayList2 = new ArrayList();
            try {
                for (Runnable runnable : arrayList) {
                    try {
                        runnable.run();
                    } catch (ProcessCanceledException unused) {
                    } catch (Throwable th) {
                        arrayList2.add(Pair.create(runnable, th));
                    }
                }
                this.actionsWhenAllDocumentsAreCommitted.clear();
                for (Pair pair : arrayList2) {
                    LOG.error("During running " + ((Runnable) pair.getFirst()), (Throwable) pair.getSecond());
                }
            } catch (Throwable th2) {
                this.actionsWhenAllDocumentsAreCommitted.clear();
                throw th2;
            }
        }
    }

    private boolean mayRunActionsWhenAllCommitted() {
        ApplicationManager.getApplication().assertIsDispatchThread();
        return (isCommitInProgress() || this.actionsWhenAllDocumentsAreCommitted.isEmpty() || hasEventSystemEnabledUncommittedDocuments()) ? false : true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    public boolean hasEventSystemEnabledUncommittedDocuments() {
        return ContainerUtil.exists(this.myUncommittedDocuments, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase$$ExternalSyntheticLambda6
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return this.f$0.isEventSystemEnabled((Document) obj);
            }
        });
    }

    private void beforeCommitHandler() {
        ApplicationManager.getApplication().assertIsDispatchThread();
        this.actionsWhenAllDocumentsAreCommitted.put(PERFORM_ALWAYS_KEY, EmptyRunnable.getInstance());
    }

    private void checkWeAreOutsideAfterCommitHandler() {
        if (isInsideCommitHandler()) {
            throw new IncorrectOperationException("You must not call performWhenAllCommitted()/cancelAndRunWhenCommitted() from within after-commit handler");
        }
    }

    private boolean isInsideCommitHandler() {
        ApplicationManager.getApplication().assertIsDispatchThread();
        return this.actionsWhenAllDocumentsAreCommitted.get(PERFORM_ALWAYS_KEY) == EmptyRunnable.getInstance();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    public void addListener(PsiDocumentManager.Listener listener) {
        if (listener == null) {
            $$$reportNull$$$0(51);
        }
        this.myListeners.add(listener);
    }

    private void fireFileCreated(Document document, PsiFile psiFile) {
        if (document == null) {
            $$$reportNull$$$0(55);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(56);
        }
        ((PsiDocumentListener) this.myProject.getMessageBus().syncPublisher(PsiDocumentListener.TOPIC)).fileCreated(psiFile, document);
        Iterator<PsiDocumentManager.Listener> it = this.myListeners.iterator();
        while (it.hasNext()) {
            it.next().fileCreated(psiFile, document);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    public CharSequence getLastCommittedText(Document document) {
        if (document == null) {
            $$$reportNull$$$0(57);
        }
        CharSequence immutableCharSequence = getLastCommittedDocument(document).getImmutableCharSequence();
        if (immutableCharSequence == null) {
            $$$reportNull$$$0(58);
        }
        return immutableCharSequence;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    public long getLastCommittedStamp(Document document) {
        if (document == null) {
            $$$reportNull$$$0(59);
        }
        return getLastCommittedDocument(getTopLevelDocument(document)).getModificationStamp();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    public Document getLastCommittedDocument(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(60);
        }
        Document document = getDocument(psiFile);
        if (document == null) {
            return null;
        }
        return getLastCommittedDocument(document);
    }

    public DocumentEx getLastCommittedDocument(Document document) {
        if (document == null) {
            $$$reportNull$$$0(61);
        }
        if (document instanceof FrozenDocument) {
            DocumentEx documentEx = (DocumentEx) document;
            if (documentEx == null) {
                $$$reportNull$$$0(62);
            }
            return documentEx;
        }
        if (document instanceof DocumentWindow) {
            DocumentWindow documentWindow = (DocumentWindow) document;
            Document delegate = documentWindow.getDelegate();
            if (delegate instanceof FrozenDocument) {
                DocumentEx documentEx2 = (DocumentEx) documentWindow;
                if (documentEx2 == null) {
                    $$$reportNull$$$0(63);
                }
                return documentEx2;
            }
            if (!documentWindow.isValid()) {
                throw new AssertionError("host committed: " + isCommitted(delegate) + ", window=" + documentWindow);
            }
            UncommittedInfo uncommittedInfo = getUncommittedInfo(delegate);
            DocumentWindow documentWindowFreezeWindow = uncommittedInfo == null ? null : (DocumentWindow) uncommittedInfo.myFrozenWindows.get(document);
            if (documentWindowFreezeWindow == null) {
                documentWindowFreezeWindow = freezeWindow(documentWindow);
            }
            if (uncommittedInfo != null) {
                documentWindowFreezeWindow = (DocumentWindow) ConcurrencyUtil.cacheOrGet(uncommittedInfo.myFrozenWindows, documentWindow, documentWindowFreezeWindow);
            }
            DocumentEx documentEx3 = (DocumentEx) documentWindowFreezeWindow;
            if (documentEx3 == null) {
                $$$reportNull$$$0(64);
            }
            return documentEx3;
        }
        UncommittedInfo uncommittedInfo2 = getUncommittedInfo(document);
        FrozenDocument frozenDocumentFreeze = uncommittedInfo2 != null ? uncommittedInfo2.myFrozen : ((DocumentImpl) document).freeze();
        if (frozenDocumentFreeze == null) {
            $$$reportNull$$$0(65);
        }
        return frozenDocumentFreeze;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public UncommittedInfo getUncommittedInfo(Document document) {
        if (document == null) {
            $$$reportNull$$$0(66);
        }
        UncommittedInfo uncommittedInfo = this.myUncommittedInfos.get(document);
        return uncommittedInfo != null ? uncommittedInfo : (UncommittedInfo) document.getUserData(this.FREE_THREADED_UNCOMMITTED_INFO);
    }

    private void associateUncommittedInfo(Document document, UncommittedInfo uncommittedInfo) {
        if (isEventSystemEnabled(document)) {
            this.myUncommittedInfos.put(document, uncommittedInfo);
        } else {
            document.putUserData(this.FREE_THREADED_UNCOMMITTED_INFO, uncommittedInfo);
        }
    }

    protected DocumentWindow freezeWindow(DocumentWindow documentWindow) {
        if (documentWindow == null) {
            $$$reportNull$$$0(67);
        }
        throw new UnsupportedOperationException();
    }

    public List<DocumentEvent> getEventsSinceCommit(Document document) {
        if (document == null) {
            $$$reportNull$$$0(68);
        }
        UncommittedInfo uncommittedInfo = getUncommittedInfo(document);
        if (uncommittedInfo != null) {
            return new ArrayList(uncommittedInfo.myEvents);
        }
        List<DocumentEvent> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(69);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    public Document[] getUncommittedDocuments() {
        ApplicationManager.getApplication().assertReadAccessAllowed();
        Document[] documentArr = (Document[]) ArrayUtil.stripTrailingNulls((Document[]) this.myUncommittedDocuments.toArray(Document.EMPTY_ARRAY));
        if (documentArr == null) {
            $$$reportNull$$$0(70);
        }
        return documentArr;
    }

    boolean isInUncommittedSet(Document document) {
        if (document == null) {
            $$$reportNull$$$0(71);
        }
        return this.myUncommittedDocuments.contains(getTopLevelDocument(document));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    public boolean isUncommited(Document document) {
        if (document == null) {
            $$$reportNull$$$0(72);
        }
        return !isCommitted(document);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    public boolean isCommitted(Document document) {
        if (document == null) {
            $$$reportNull$$$0(73);
        }
        Document topLevelDocument = getTopLevelDocument(document);
        if (getSynchronizer().isInSynchronization(topLevelDocument)) {
            return true;
        }
        return (((topLevelDocument instanceof DocumentEx) && ((DocumentEx) topLevelDocument).isInEventsHandling()) || isInUncommittedSet(topLevelDocument)) ? false : true;
    }

    private static Document getTopLevelDocument(Document document) {
        if (document == null) {
            $$$reportNull$$$0(74);
        }
        if (document instanceof DocumentWindow) {
            document = ((DocumentWindow) document).getDelegate();
        }
        if (document == null) {
            $$$reportNull$$$0(75);
        }
        return document;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager
    public boolean hasUncommitedDocuments() {
        return (isCommitInProgress() || this.myUncommittedDocuments.isEmpty()) ? false : true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentListener
    public void beforeDocumentChange(DocumentEvent documentEvent) {
        if (documentEvent == null) {
            $$$reportNull$$$0(76);
        }
        if (this.myStopTrackingDocuments || this.myProject.isDisposed()) {
            return;
        }
        Document document = documentEvent.getDocument();
        VirtualFile file = FileDocumentManager.getInstance().getFile(document);
        Object[] objArr = file != null && isRelevant(file);
        PsiFile psiFile = null;
        Object[] objArr2 = 0;
        if ((document instanceof DocumentImpl) && getUncommittedInfo(document) == null) {
            associateUncommittedInfo(document, new UncommittedInfo((DocumentImpl) document));
        }
        FileViewProvider cachedViewProvider = getCachedViewProvider(document);
        boolean z = cachedViewProvider != null && cachedViewProvider.getManager() == this.myPsiManager;
        if (objArr == true && z) {
            List<PsiFile> allFiles = cachedViewProvider.getAllFiles();
            for (PsiFile psiFile2 : allFiles) {
                if (psiFile2 == null) {
                    throw new AssertionError("View provider " + cachedViewProvider + " (" + cachedViewProvider.getClass() + ") returned null in its files array: " + allFiles + " for file " + cachedViewProvider.getVirtualFile());
                }
                if (PsiToDocumentSynchronizer.isInsideAtomicChange(psiFile2)) {
                    psiFile = psiFile2;
                }
            }
            if (psiFile == null) {
                beforeDocumentChangeOnUnlockedDocument(cachedViewProvider);
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentListener
    public void documentChanged(DocumentEvent documentEvent) {
        if (documentEvent == null) {
            $$$reportNull$$$0(78);
        }
        if (this.myStopTrackingDocuments || this.myProject.isDisposed()) {
            return;
        }
        Document document = documentEvent.getDocument();
        VirtualFile file = FileDocumentManager.getInstance().getFile(document);
        boolean z = true;
        boolean z2 = file != null && isRelevant(file);
        FileViewProvider cachedViewProvider = getCachedViewProvider(document);
        if (cachedViewProvider == null) {
            handleCommitWithoutPsi(document);
            return;
        }
        boolean z3 = cachedViewProvider.getManager() == this.myPsiManager;
        if (!z2 || !z3) {
            clearUncommittedInfo(document);
            return;
        }
        List<PsiFile> allFiles = cachedViewProvider.getAllFiles();
        if (allFiles.isEmpty()) {
            handleCommitWithoutPsi(document);
            return;
        }
        boolean zExists = ContainerUtil.exists(allFiles, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase$$ExternalSyntheticLambda14
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return PsiDocumentManagerBase.lambda$documentChanged$16((PsiFile) obj);
            }
        });
        Application application = ApplicationManager.getApplication();
        if (!application.hasWriteAction(ExternalChangeAction.class) || (!SystemProperties.getBooleanProperty("idea.force.commit.on.external.change", false) && (!application.isHeadlessEnvironment() || application.isUnitTestMode()))) {
            z = false;
        }
        if (documentEvent.isWholeTextReplaced() && document.getTextLength() > 100000) {
            document.putUserData(BlockSupport.DO_NOT_REPARSE_INCREMENTALLY, Boolean.TRUE);
        }
        if (!zExists) {
            this.myUncommittedDocuments.add(document);
            if (z) {
                commitDocument(document);
                return;
            } else {
                if (document.isInBulkUpdate() || !this.myPerformBackgroundCommit) {
                    return;
                }
                retainProviderAndCommitAsync(document, documentEvent, ModalityState.defaultModalityState());
                return;
            }
        }
        clearUncommittedInfo(document);
    }

    static /* synthetic */ boolean lambda$documentChanged$16(PsiFile psiFile) {
        return PsiToDocumentSynchronizer.isInsideAtomicChange(psiFile) || !(psiFile instanceof PsiFileImpl);
    }

    public void bulkUpdateStarting(Document document) {
        if (document == null) {
            $$$reportNull$$$0(79);
        }
        document.putUserData(BlockSupport.DO_NOT_REPARSE_INCREMENTALLY, Boolean.TRUE);
    }

    public void bulkUpdateFinished(Document document) {
        if (document == null) {
            $$$reportNull$$$0(80);
        }
        retainProviderAndCommitAsync(document, "Bulk update finished", ModalityState.defaultModalityState());
    }

    private void retainProviderAndCommitAsync(Document document, Object obj, ModalityState modalityState) {
        if (document == null) {
            $$$reportNull$$$0(81);
        }
        if (obj == null) {
            $$$reportNull$$$0(82);
        }
        if (modalityState == null) {
            $$$reportNull$$$0(83);
        }
        FileViewProvider cachedViewProvider = getCachedViewProvider(document);
        if (cachedViewProvider == null || !cachedViewProvider.isEventSystemEnabled()) {
            return;
        }
        ApplicationManager.getApplication().assertIsDispatchThread();
        this.myDocumentCommitProcessor.commitAsynchronously(this.myProject, this, document, obj, modalityState, cachedViewProvider);
    }

    public class PriorityEventCollector implements PrioritizedDocumentListener {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "event", "org/jetbrains/kotlin/com/intellij/psi/impl/PsiDocumentManagerBase$PriorityEventCollector", "documentChanged"));
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.ex.PrioritizedDocumentListener
        public int getPriority() {
            return 40;
        }

        public PriorityEventCollector() {
        }

        @Override // org.jetbrains.kotlin.com.intellij.openapi.editor.event.DocumentListener
        public void documentChanged(DocumentEvent documentEvent) {
            if (documentEvent == null) {
                $$$reportNull$$$0(0);
            }
            UncommittedInfo uncommittedInfo = PsiDocumentManagerBase.this.getUncommittedInfo(documentEvent.getDocument());
            if (uncommittedInfo != null) {
                uncommittedInfo.myEvents.add(documentEvent);
            }
        }
    }

    void handleCommitWithoutPsi(Document document) {
        if (document == null) {
            $$$reportNull$$$0(84);
        }
        if (clearUncommittedInfo(document) == null) {
            return;
        }
        this.myUncommittedDocuments.remove(document);
        if (!this.myProject.isInitialized() || this.myProject.isDisposed() || this.myProject.isDefault()) {
            return;
        }
        VirtualFile file = FileDocumentManager.getInstance().getFile(document);
        if (file != null) {
            final FileManager fileManager = getFileManager();
            final FileViewProvider fileViewProviderFindCachedViewProvider = fileManager.findCachedViewProvider(file);
            if (fileViewProviderFindCachedViewProvider != null) {
                ApplicationManager.getApplication().runWriteAction(new ExternalChangeAction() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((AbstractFileViewProvider) fileViewProviderFindCachedViewProvider).onContentReload();
                    }
                });
            } else if (FileIndexFacade.getInstance(this.myProject).isInContent(file)) {
                ApplicationManager.getApplication().runWriteAction(new ExternalChangeAction() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((FileManagerImpl) fileManager).firePropertyChangedForUnloadedPsi();
                    }
                });
            }
        }
        runAfterCommitActions(document);
    }

    private UncommittedInfo clearUncommittedInfo(Document document) {
        if (document == null) {
            $$$reportNull$$$0(85);
        }
        UncommittedInfo uncommittedInfo = getUncommittedInfo(document);
        if (uncommittedInfo != null) {
            this.myUncommittedInfos.remove(document);
            document.putUserData(this.FREE_THREADED_UNCOMMITTED_INFO, null);
            getSmartPointerManager().updatePointers(document, uncommittedInfo.myFrozen, uncommittedInfo.myEvents);
        }
        return uncommittedInfo;
    }

    private SmartPointerManagerImpl getSmartPointerManager() {
        return (SmartPointerManagerImpl) SmartPointerManager.getInstance(this.myProject);
    }

    private boolean isRelevant(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(86);
        }
        return (this.myProject.isDisposed() || virtualFile.getFileType().isBinary()) ? false : true;
    }

    public static boolean checkConsistency(PsiFile psiFile, Document document) {
        if (psiFile == null) {
            $$$reportNull$$$0(87);
        }
        if (document == null) {
            $$$reportNull$$$0(88);
        }
        if (psiFile.getVirtualFile() == null) {
            return true;
        }
        CharSequence charsSequence = document.getCharsSequence();
        int textLength = document.getTextLength();
        if (psiFile.textMatches(charsSequence)) {
            LOG.assertTrue(psiFile.getTextLength() == textLength);
            return true;
        }
        char[] cArrTextToCharArray = psiFile.textToCharArray();
        String str = "File '" + psiFile.getName() + "' text mismatch after reparse. File length=" + cArrTextToCharArray.length + "; Doc length=" + textLength + "\n";
        int i = 0;
        while (i < textLength) {
            if (i >= cArrTextToCharArray.length) {
                str = str + "editorText.length > psiText.length i=" + i + "\n";
                break;
            }
            if (i >= charsSequence.length()) {
                str = str + "editorText.length > psiText.length i=" + i + "\n";
                break;
            }
            if (charsSequence.charAt(i) != cArrTextToCharArray[i]) {
                str = str + "first unequal char i=" + i + "\n";
                break;
            }
            i++;
        }
        String str2 = ((((str + "*********************************************\n") + "Editor Text tail:(" + (textLength - i) + ")\n") + "*********************************************\n") + "Psi Text tail:(" + (cArrTextToCharArray.length - i) + ")\n") + "*********************************************\n";
        if (document instanceof DocumentWindow) {
            String str3 = (((str2 + "doc: '" + document.getText() + "'\n") + "psi: '" + psiFile.getText() + "'\n") + "ast: '" + psiFile.getNode().getText() + "'\n") + psiFile.getLanguage() + "\n";
            PsiLanguageInjectionHost injectionHost = InjectedLanguageManager.getInstance(psiFile.getProject()).getInjectionHost(psiFile);
            if (injectionHost != null) {
                str3 = (str3 + "context: " + injectionHost + "; text: '" + injectionHost.getText() + "'\n") + "context file: " + injectionHost.getContainingFile() + "\n";
            }
            str2 = str3 + "document window ranges: " + Arrays.asList(((DocumentWindow) document).getHostRanges()) + "\n";
        }
        LOG.error(str2);
        return false;
    }

    public void clearUncommittedDocuments() {
        this.myUncommittedInfos.clear();
        this.myUncommittedDocuments.clear();
        this.mySynchronizer.cleanupForNextTest();
    }

    public void disableBackgroundCommit(Disposable disposable) {
        if (disposable == null) {
            $$$reportNull$$$0(89);
        }
        this.myPerformBackgroundCommit = false;
        Disposer.register(disposable, new Disposable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase$$ExternalSyntheticLambda16
            @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
            public final void dispose() {
                this.f$0.m6558x82b3759d();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$disableBackgroundCommit$19$org-jetbrains-kotlin-com-intellij-psi-impl-PsiDocumentManagerBase, reason: not valid java name */
    /* synthetic */ void m6558x82b3759d() {
        this.myPerformBackgroundCommit = true;
    }

    public PsiToDocumentSynchronizer getSynchronizer() {
        PsiToDocumentSynchronizer psiToDocumentSynchronizer = this.mySynchronizer;
        if (psiToDocumentSynchronizer == null) {
            $$$reportNull$$$0(90);
        }
        return psiToDocumentSynchronizer;
    }

    public void reparseFileFromText(PsiFileImpl psiFileImpl) {
        if (psiFileImpl == null) {
            $$$reportNull$$$0(91);
        }
        if (isCommitInProgress()) {
            throw new IllegalStateException("Re-entrant commit is not allowed");
        }
        FileElement fileElementCalcTreeElement = psiFileImpl.calcTreeElement();
        CharSequence chars = fileElementCalcTreeElement.getChars();
        ourIsFullReparseInProgress.set(Boolean.TRUE);
        try {
            BlockSupportImpl.makeFullParse(psiFileImpl, fileElementCalcTreeElement, chars, EmptyProgressIndicator.notNullize(ProgressIndicatorProvider.getGlobalProgressIndicator()), chars).log.doActualPsiChange(psiFileImpl);
            psiFileImpl.getViewProvider().contentsSynchronized();
        } finally {
            ourIsFullReparseInProgress.remove();
        }
    }

    private static final class UncommittedInfo {
        private final List<DocumentEvent> myEvents;
        private final FrozenDocument myFrozen;
        private final ConcurrentMap<DocumentWindow, DocumentWindow> myFrozenWindows;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "original", "org/jetbrains/kotlin/com/intellij/psi/impl/PsiDocumentManagerBase$UncommittedInfo", "<init>"));
        }

        private UncommittedInfo(DocumentImpl documentImpl) {
            if (documentImpl == null) {
                $$$reportNull$$$0(0);
            }
            this.myEvents = new ArrayList();
            this.myFrozenWindows = new ConcurrentHashMap();
            this.myFrozen = documentImpl.freeze();
        }
    }

    List<BooleanRunnable> reparseChangedInjectedFragments(Document document, PsiFile psiFile, TextRange textRange, ProgressIndicator progressIndicator, ASTNode aSTNode, ASTNode aSTNode2) {
        if (document == null) {
            $$$reportNull$$$0(92);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(93);
        }
        if (textRange == null) {
            $$$reportNull$$$0(94);
        }
        if (progressIndicator == null) {
            $$$reportNull$$$0(95);
        }
        if (aSTNode == null) {
            $$$reportNull$$$0(96);
        }
        if (aSTNode2 == null) {
            $$$reportNull$$$0(97);
        }
        List<BooleanRunnable> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(98);
        }
        return listEmptyList;
    }

    public boolean isDefaultProject() {
        return this.myProject.isDefault();
    }

    public String someDocumentDebugInfo(Document document) {
        if (document == null) {
            $$$reportNull$$$0(99);
        }
        return "cachedProvider: " + getCachedViewProvider(document) + "; isEventSystemEnabled: " + isEventSystemEnabled(document);
    }
}
