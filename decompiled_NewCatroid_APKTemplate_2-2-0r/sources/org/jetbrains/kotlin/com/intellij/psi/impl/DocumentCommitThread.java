package org.jetbrains.kotlin.com.intellij.psi.impl;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import org.jetbrains.kotlin.com.intellij.diagnostic.PluginException;
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState;
import org.jetbrains.kotlin.com.intellij.openapi.application.ReadAction;
import org.jetbrains.kotlin.com.intellij.openapi.application.TransactionGuard;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.editor.ex.DocumentEx;
import org.jetbrains.kotlin.com.intellij.openapi.progress.EmptyProgressIndicator;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicator;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicatorProvider;
import org.jetbrains.kotlin.com.intellij.openapi.progress.util.StandardProgressIndicatorBase;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.openapi.util.ProperTextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.SingleRootFileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.text.BlockSupport;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.concurrency.AppExecutorUtil;
import org.jetbrains.kotlin.com.intellij.util.concurrency.BoundedTaskExecutor;
import org.jetbrains.kotlin.com.intellij.util.ui.EdtInvocationManager;

/* JADX INFO: loaded from: classes3.dex */
public final class DocumentCommitThread implements Disposable, DocumentCommitProcessor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) DocumentCommitThread.class);
    private final ExecutorService executor = AppExecutorUtil.createBoundedApplicationPoolExecutor("Document Commit Pool", AppExecutorUtil.getAppExecutorService(), 1, this);
    private volatile boolean isDisposed;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 11 || i == 14 || i == 22 || i == 23) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 11 || i == 14 || i == 22 || i == 23) ? 2 : 3];
        switch (i) {
            case 1:
            case 10:
            case 13:
            case 21:
                objArr[0] = "documentManager";
                break;
            case 2:
            case 6:
            case 24:
                objArr[0] = "document";
                break;
            case 3:
                objArr[0] = "reason";
                break;
            case 4:
                objArr[0] = "modality";
                break;
            case 5:
                objArr[0] = "cachedViewProvider";
                break;
            case 7:
            default:
                objArr[0] = "project";
                break;
            case 8:
                objArr[0] = "psiFile";
                break;
            case 9:
            case 12:
            case 16:
                objArr[0] = "task";
                break;
            case 11:
            case 14:
            case 22:
            case 23:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/DocumentCommitThread";
                break;
            case 15:
                objArr[0] = "timeUnit";
                break;
            case 17:
            case 25:
                objArr[0] = "file";
                break;
            case 18:
            case 26:
                objArr[0] = "oldFileNode";
                break;
            case 19:
                objArr[0] = "changedPsiRange";
                break;
            case 20:
                objArr[0] = "outReparseInjectedProcessors";
                break;
        }
        if (i == 11) {
            objArr[1] = "commitUnderProgress";
        } else if (i == 14) {
            objArr[1] = "handleCommitWithoutPsi";
        } else if (i == 22 || i == 23) {
            objArr[1] = "doCommit";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/DocumentCommitThread";
        }
        switch (i) {
            case 6:
            case 7:
            case 8:
                objArr[2] = "commitSynchronously";
                break;
            case 9:
            case 10:
                objArr[2] = "commitUnderProgress";
                break;
            case 11:
            case 14:
            case 22:
            case 23:
                break;
            case 12:
            case 13:
                objArr[2] = "handleCommitWithoutPsi";
                break;
            case 15:
                objArr[2] = "waitForAllCommits";
                break;
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                objArr[2] = "doCommit";
                break;
            case 24:
            case 25:
            case 26:
                objArr[2] = "assertAfterCommit";
                break;
            default:
                objArr[2] = "commitAsynchronously";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 11 && i != 14 && i != 22 && i != 23) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    DocumentCommitThread() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
    public void dispose() {
        this.isDisposed = true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.DocumentCommitProcessor
    public void commitAsynchronously(final Project project, final PsiDocumentManagerBase psiDocumentManagerBase, final Document document, Object obj, ModalityState modalityState, FileViewProvider fileViewProvider) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        if (psiDocumentManagerBase == null) {
            $$$reportNull$$$0(1);
        }
        if (document == null) {
            $$$reportNull$$$0(2);
        }
        if (obj == null) {
            $$$reportNull$$$0(3);
        }
        if (modalityState == null) {
            $$$reportNull$$$0(4);
        }
        if (fileViewProvider == null) {
            $$$reportNull$$$0(5);
        }
        if (project.isInitialized()) {
            TransactionGuard.getInstance().assertWriteSafeContext(modalityState);
            final CommitTask commitTask = new CommitTask(project, document, obj, modalityState, psiDocumentManagerBase.getLastCommittedText(document), fileViewProvider);
            ReadAction.nonBlocking(new Callable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.DocumentCommitThread$$ExternalSyntheticLambda3
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return this.f$0.m6544xdfdb52f5(commitTask, psiDocumentManagerBase);
                }
            }).expireWhen(new BooleanSupplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.DocumentCommitThread$$ExternalSyntheticLambda4
                @Override // java.util.function.BooleanSupplier
                public final boolean getAsBoolean() {
                    return this.f$0.m6545x2d9acaf6(project, psiDocumentManagerBase, document, commitTask);
                }
            }).coalesceBy(commitTask).finishOnUiThread(modalityState, new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.DocumentCommitThread$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj2) {
                    ((Runnable) obj2).run();
                }
            }).submit(this.executor);
        }
    }

    /* JADX INFO: renamed from: lambda$commitAsynchronously$0$org-jetbrains-kotlin-com-intellij-psi-impl-DocumentCommitThread, reason: not valid java name */
    /* synthetic */ Runnable m6544xdfdb52f5(CommitTask commitTask, PsiDocumentManagerBase psiDocumentManagerBase) throws Exception {
        return commitUnderProgress(commitTask, false, psiDocumentManagerBase);
    }

    /* JADX INFO: renamed from: lambda$commitAsynchronously$1$org-jetbrains-kotlin-com-intellij-psi-impl-DocumentCommitThread, reason: not valid java name */
    /* synthetic */ boolean m6545x2d9acaf6(Project project, PsiDocumentManagerBase psiDocumentManagerBase, Document document, CommitTask commitTask) {
        return project.isDisposed() || this.isDisposed || !psiDocumentManagerBase.isInUncommittedSet(document) || !commitTask.isStillValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.DocumentCommitProcessor
    public void commitSynchronously(Document document, Project project, PsiFile psiFile) {
        if (document == null) {
            $$$reportNull$$$0(6);
        }
        if (project == null) {
            $$$reportNull$$$0(7);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(8);
        }
        if (!project.isInitialized() && !project.isDefault()) {
            String str = project + "; Disposed: " + project.isDisposed() + "; Open: " + project.isOpen();
            try {
                Disposer.dispose(project);
            } catch (Throwable unused) {
            }
            throw new RuntimeException(str);
        }
        PsiDocumentManagerBase psiDocumentManagerBase = (PsiDocumentManagerBase) PsiDocumentManager.getInstance(project);
        commitUnderProgress(new CommitTask(project, document, "Sync commit", ModalityState.defaultModalityState(), psiDocumentManagerBase.getLastCommittedText(document), psiFile.getViewProvider()), true, psiDocumentManagerBase).run();
    }

    private Runnable commitUnderProgress(final CommitTask commitTask, final boolean z, final PsiDocumentManagerBase psiDocumentManagerBase) {
        if (commitTask == null) {
            $$$reportNull$$$0(9);
        }
        if (psiDocumentManagerBase == null) {
            $$$reportNull$$$0(10);
        }
        final Document document = commitTask.document;
        final Project project = commitTask.project;
        final SmartList smartList = new SmartList();
        final SmartList smartList2 = new SmartList();
        final FileViewProvider cachedViewProvider = psiDocumentManagerBase.getCachedViewProvider(document);
        if (cachedViewProvider == null) {
            smartList.add(handleCommitWithoutPsi(commitTask, psiDocumentManagerBase));
        } else {
            commitTask.cachedViewProvider = cachedViewProvider;
            for (PsiFile psiFile : cachedViewProvider.getAllFiles()) {
                FileASTNode node = psiFile.getNode();
                if (node == null) {
                    throw new AssertionError("No node for " + psiFile.getClass() + " in " + psiFile.getViewProvider().getClass() + " of size " + StringUtil.formatFileSize(document.getTextLength()) + " (is too large = " + SingleRootFileViewProvider.isTooLargeForIntelligence(cachedViewProvider.getVirtualFile(), Long.valueOf(document.getTextLength())) + Constant.AFTER_QUTO);
                }
                ProperTextRange changedPsiRange = ChangedPsiRangeUtil.getChangedPsiRange(psiFile, document, commitTask.myLastCommittedText, document.getImmutableCharSequence());
                if (changedPsiRange != null) {
                    smartList.add(doCommit(commitTask, psiFile, node, changedPsiRange, smartList2, psiDocumentManagerBase));
                }
            }
        }
        return new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.DocumentCommitThread$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m6546x870d841f(project, psiDocumentManagerBase, document, smartList, smartList2, z, commitTask, cachedViewProvider);
            }
        };
    }

    /* JADX INFO: renamed from: lambda$commitUnderProgress$2$org-jetbrains-kotlin-com-intellij-psi-impl-DocumentCommitThread, reason: not valid java name */
    /* synthetic */ void m6546x870d841f(Project project, PsiDocumentManagerBase psiDocumentManagerBase, Document document, List list, List list2, boolean z, CommitTask commitTask, FileViewProvider fileViewProvider) {
        if (project.isDisposed() || psiDocumentManagerBase.finishCommit(document, list, list2, z, commitTask.reason) || fileViewProvider == null || !fileViewProvider.isEventSystemEnabled()) {
            return;
        }
        commitAsynchronously(project, psiDocumentManagerBase, document, "Re-added back", commitTask.myCreationModality, fileViewProvider);
    }

    private static BooleanRunnable handleCommitWithoutPsi(final CommitTask commitTask, final PsiDocumentManagerBase psiDocumentManagerBase) {
        if (commitTask == null) {
            $$$reportNull$$$0(12);
        }
        if (psiDocumentManagerBase == null) {
            $$$reportNull$$$0(13);
        }
        return new BooleanRunnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.DocumentCommitThread$$ExternalSyntheticLambda6
            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.BooleanRunnable
            public final boolean run() {
                return DocumentCommitThread.lambda$handleCommitWithoutPsi$3(commitTask, psiDocumentManagerBase);
            }
        };
    }

    static /* synthetic */ boolean lambda$handleCommitWithoutPsi$3(CommitTask commitTask, PsiDocumentManagerBase psiDocumentManagerBase) {
        if (!commitTask.isStillValid() || psiDocumentManagerBase.getCachedViewProvider(commitTask.document) != null) {
            return false;
        }
        psiDocumentManagerBase.handleCommitWithoutPsi(commitTask.document);
        return true;
    }

    public String toString() {
        return "Document commit thread; application: " + ApplicationManager.getApplication() + "; isDisposed: " + this.isDisposed;
    }

    public void waitForAllCommits(long j, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        if (timeUnit == null) {
            $$$reportNull$$$0(15);
        }
        ApplicationManager.getApplication().assertIsDispatchThread();
        EdtInvocationManager.dispatchAllInvocationEvents();
        while (!((BoundedTaskExecutor) this.executor).isEmpty()) {
            ((BoundedTaskExecutor) this.executor).waitAllTasksExecuted(j, timeUnit);
            EdtInvocationManager.dispatchAllInvocationEvents();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class CommitTask {
        private volatile FileViewProvider cachedViewProvider;
        private final Document document;
        private final int modificationSequence;
        final ModalityState myCreationModality;
        private final CharSequence myLastCommittedText;
        final Project project;
        final Object reason;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "document";
            } else if (i == 2) {
                objArr[0] = "reason";
            } else if (i == 3) {
                objArr[0] = "modality";
            } else if (i == 4) {
                objArr[0] = "lastCommittedText";
            } else if (i != 5) {
                objArr[0] = "project";
            } else {
                objArr[0] = "cachedViewProvider";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/DocumentCommitThread$CommitTask";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        CommitTask(Project project, Document document, Object obj, ModalityState modalityState, CharSequence charSequence, FileViewProvider fileViewProvider) {
            if (project == null) {
                $$$reportNull$$$0(0);
            }
            if (document == null) {
                $$$reportNull$$$0(1);
            }
            if (obj == null) {
                $$$reportNull$$$0(2);
            }
            if (modalityState == null) {
                $$$reportNull$$$0(3);
            }
            if (charSequence == null) {
                $$$reportNull$$$0(4);
            }
            if (fileViewProvider == null) {
                $$$reportNull$$$0(5);
            }
            this.document = document;
            this.project = project;
            this.reason = obj;
            this.myCreationModality = modalityState;
            this.myLastCommittedText = charSequence;
            this.modificationSequence = ((DocumentEx) document).getModificationSequence();
            this.cachedViewProvider = fileViewProvider;
        }

        public String toString() {
            return System.identityHashCode(this) + "; " + (" modality: " + this.myCreationModality) + (" task reason: " + StringUtil.first(String.valueOf(this.reason), 180, true) + (isStillValid() ? "" : "; changed: old seq=" + this.modificationSequence + ", new seq=" + ((DocumentEx) this.document).getModificationSequence()));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CommitTask)) {
                return false;
            }
            CommitTask commitTask = (CommitTask) obj;
            return Comparing.equal(this.document, commitTask.document) && this.project.equals(commitTask.project);
        }

        public int hashCode() {
            return (this.document.hashCode() * 31) + this.project.hashCode();
        }

        boolean isStillValid() {
            return ((DocumentEx) this.document).getModificationSequence() == this.modificationSequence;
        }
    }

    private static BooleanRunnable doCommit(final CommitTask commitTask, final PsiFile psiFile, final FileASTNode fileASTNode, ProperTextRange properTextRange, List<? super BooleanRunnable> list, final PsiDocumentManagerBase psiDocumentManagerBase) {
        if (commitTask == null) {
            $$$reportNull$$$0(16);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(17);
        }
        if (fileASTNode == null) {
            $$$reportNull$$$0(18);
        }
        if (properTextRange == null) {
            $$$reportNull$$$0(19);
        }
        if (list == null) {
            $$$reportNull$$$0(20);
        }
        if (psiDocumentManagerBase == null) {
            $$$reportNull$$$0(21);
        }
        final Document document = commitTask.document;
        CharSequence immutableCharSequence = document.getImmutableCharSequence();
        Boolean bool = (Boolean) document.getUserData(BlockSupport.DO_NOT_REPARSE_INCREMENTALLY);
        if (bool != null) {
            document.putUserData(BlockSupport.DO_NOT_REPARSE_INCREMENTALLY, null);
            psiFile.putUserData(BlockSupport.DO_NOT_REPARSE_INCREMENTALLY, bool);
        }
        ProgressIndicator globalProgressIndicator = ProgressIndicatorProvider.getGlobalProgressIndicator();
        if (globalProgressIndicator == null) {
            globalProgressIndicator = new EmptyProgressIndicator();
        }
        ProgressIndicator progressIndicator = globalProgressIndicator;
        try {
            BlockSupportImpl.ReparseResult reparseResultReparse = BlockSupportImpl.reparse(psiFile, fileASTNode, properTextRange, immutableCharSequence, progressIndicator, commitTask.myLastCommittedText);
            final DiffLog diffLog = reparseResultReparse.log;
            list.addAll(psiDocumentManagerBase.reparseChangedInjectedFragments(document, psiFile, properTextRange, progressIndicator, reparseResultReparse.oldRoot, reparseResultReparse.newRoot));
            return new BooleanRunnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.DocumentCommitThread$$ExternalSyntheticLambda2
                @Override // org.jetbrains.kotlin.com.intellij.psi.impl.BooleanRunnable
                public final boolean run() {
                    return DocumentCommitThread.lambda$doCommit$5(psiFile, commitTask, psiDocumentManagerBase, document, diffLog, fileASTNode);
                }
            };
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (Throwable th) {
            LOG.error(th);
            return new BooleanRunnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.DocumentCommitThread$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.psi.impl.BooleanRunnable
                public final boolean run() {
                    return DocumentCommitThread.lambda$doCommit$4(psiDocumentManagerBase, psiFile);
                }
            };
        }
    }

    static /* synthetic */ boolean lambda$doCommit$4(PsiDocumentManagerBase psiDocumentManagerBase, PsiFile psiFile) throws Throwable {
        psiDocumentManagerBase.forceReload(psiFile.getViewProvider().getVirtualFile(), psiFile.getViewProvider());
        return true;
    }

    static /* synthetic */ boolean lambda$doCommit$5(PsiFile psiFile, CommitTask commitTask, PsiDocumentManagerBase psiDocumentManagerBase, Document document, DiffLog diffLog, FileASTNode fileASTNode) {
        FileViewProvider viewProvider = psiFile.getViewProvider();
        if (!commitTask.isStillValid() || psiDocumentManagerBase.getCachedViewProvider(document) != viewProvider) {
            return false;
        }
        if (!ApplicationManager.getApplication().isWriteAccessAllowed() && psiDocumentManagerBase.isEventSystemEnabled(document)) {
            VirtualFile virtualFile = viewProvider.getVirtualFile();
            LOG.error("Write action expected; document=" + document + "; file=" + psiFile + " of " + psiFile.getClass() + "; file.valid=" + psiFile.isValid() + "; file.eventSystemEnabled=" + viewProvider.isEventSystemEnabled() + "; viewProvider=" + viewProvider + " of " + viewProvider.getClass() + "; language=" + psiFile.getLanguage() + "; vFile=" + virtualFile + " of " + virtualFile.getClass() + "; free-threaded=" + AbstractFileViewProvider.isFreeThreaded(viewProvider));
        }
        diffLog.doActualPsiChange(psiFile);
        assertAfterCommit(document, psiFile, fileASTNode);
        ObjectUtils.reachabilityFence(commitTask.cachedViewProvider);
        return true;
    }

    private static void assertAfterCommit(Document document, PsiFile psiFile, FileASTNode fileASTNode) {
        if (document == null) {
            $$$reportNull$$$0(24);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(25);
        }
        if (fileASTNode == null) {
            $$$reportNull$$$0(26);
        }
        if (fileASTNode.getTextLength() != document.getTextLength()) {
            String text = document.getText();
            String str = "commitDocument() left PSI inconsistent: " + DebugUtil.diagnosePsiDocumentInconsistency(psiFile, document) + "; node.length=" + fileASTNode.getTextLength() + "; doc.text" + (Objects.equals(psiFile.getText(), text) ? "==" : "!=") + "file.text; file name:" + psiFile.getName() + "; type:" + psiFile.getFileType() + "; lang:" + psiFile.getLanguage();
            Logger logger = LOG;
            PluginException.logPluginError(logger, str, null, psiFile.getLanguage().getClass());
            psiFile.putUserData(BlockSupport.DO_NOT_REPARSE_INCREMENTALLY, Boolean.TRUE);
            try {
                BlockSupport.getInstance(psiFile.getProject()).reparseRange(psiFile, psiFile.getNode(), new TextRange(0, text.length()), text, new StandardProgressIndicatorBase(), fileASTNode.getText()).doActualPsiChange(psiFile);
                if (fileASTNode.getTextLength() != document.getTextLength()) {
                    PluginException.logPluginError(logger, "PSI is broken beyond repair in: " + psiFile, null, psiFile.getLanguage().getClass());
                }
            } finally {
                psiFile.putUserData(BlockSupport.DO_NOT_REPARSE_INCREMENTALLY, null);
            }
        }
    }
}
