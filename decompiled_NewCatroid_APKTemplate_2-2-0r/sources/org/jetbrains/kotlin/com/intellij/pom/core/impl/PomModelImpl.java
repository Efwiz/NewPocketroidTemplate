package org.jetbrains.kotlin.com.intellij.pom.core.impl;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode;
import org.jetbrains.kotlin.com.intellij.model.ModelBranch;
import org.jetbrains.kotlin.com.intellij.openapi.command.CommandProcessor;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.openapi.progress.EmptyProgressIndicator;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicatorProvider;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.pom.PomModel;
import org.jetbrains.kotlin.com.intellij.pom.PomModelAspect;
import org.jetbrains.kotlin.com.intellij.pom.PomTransaction;
import org.jetbrains.kotlin.com.intellij.pom.event.PomModelEvent;
import org.jetbrains.kotlin.com.intellij.pom.event.PomModelListener;
import org.jetbrains.kotlin.com.intellij.pom.impl.PomTransactionBase;
import org.jetbrains.kotlin.com.intellij.pom.tree.TreeAspect;
import org.jetbrains.kotlin.com.intellij.pom.wrappers.PsiEventWrapperAspect;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.SmartPointerManager;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.CodeStyleManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.ChangedPsiRangeUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.DebugUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.DiffLog;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiToDocumentSynchronizer;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiTreeChangeEventImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerManagerImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolder;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.text.BlockSupport;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable;
import org.jetbrains.kotlin.com.intellij.util.containers.Stack;
import org.jetbrains.kotlin.com.intellij.util.lang.CompoundRuntimeException;

/* JADX INFO: loaded from: classes3.dex */
public class PomModelImpl extends UserDataHolderBase implements PomModel {
    private static volatile boolean allowPsiModification = true;
    private final Collection<PomModelListener> myListeners;
    private final Project myProject;
    private final PsiEventWrapperAspect myPsiAspect;
    private final ThreadLocal<Stack<PomTransaction>> myTransactionStack;
    private final TreeAspect myTreeAspect;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 17 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 17 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "aClass";
                break;
            case 2:
            case 3:
            case 5:
                objArr[0] = ServiceSpecificExtraArgs.CastExtraArgs.LISTENER;
                break;
            case 4:
                objArr[0] = "parentDisposable";
                break;
            case 6:
            case 11:
                objArr[0] = "transaction";
                break;
            case 7:
                objArr[0] = "containingFileByTree";
                break;
            case 8:
                objArr[0] = "file";
                break;
            case 9:
                objArr[0] = "treeElement";
                break;
            case 10:
                objArr[0] = "newText";
                break;
            case 12:
                objArr[0] = "changeScope";
                break;
            case 13:
                objArr[0] = "runnable";
                break;
            case 14:
            case 15:
            case 16:
                objArr[0] = "scope";
                break;
            case 17:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/pom/core/impl/PomModelImpl";
                break;
            default:
                objArr[0] = "project";
                break;
        }
        if (i != 17) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/pom/core/impl/PomModelImpl";
        } else {
            objArr[1] = "getPsiManager";
        }
        switch (i) {
            case 1:
                objArr[2] = "getModelAspect";
                break;
            case 2:
            case 3:
            case 4:
                objArr[2] = "addModelListener";
                break;
            case 5:
                objArr[2] = "removeModelListener";
                break;
            case 6:
                objArr[2] = "runTransaction";
                break;
            case 7:
                objArr[2] = "commitTransaction";
                break;
            case 8:
            case 9:
            case 10:
                objArr[2] = "reparseFile";
                break;
            case 11:
                objArr[2] = "startTransaction";
                break;
            case 12:
                objArr[2] = "getContainingFileByTree";
                break;
            case 13:
                objArr[2] = "guardPsiModificationsIn";
                break;
            case 14:
                objArr[2] = "sendBeforeChildrenChangeEvent";
                break;
            case 15:
                objArr[2] = "shouldFirePhysicalPsiEvents";
                break;
            case 16:
                objArr[2] = "sendAfterChildrenChangedEvent";
                break;
            case 17:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 17) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.pom.PomModel
    public <T extends PomModelAspect> T getModelAspect(Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(1);
        }
        if (this.myTreeAspect.getClass().equals(cls)) {
            return this.myTreeAspect;
        }
        if (this.myPsiAspect.getClass().equals(cls)) {
            return this.myPsiAspect;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.pom.PomModel
    public void runTransaction(final PomTransaction pomTransaction) throws IncorrectOperationException {
        if (pomTransaction == null) {
            $$$reportNull$$$0(6);
        }
        if (!isAllowPsiModification()) {
            throw new IncorrectOperationException("Must not modify PSI inside save listener");
        }
        ProgressManager.getInstance().executeNonCancelableSection(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.pom.core.impl.PomModelImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.m6517xdd58f8fd(pomTransaction);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$runTransaction$2$org-jetbrains-kotlin-com-intellij-pom-core-impl-PomModelImpl, reason: not valid java name */
    /* synthetic */ void m6517xdd58f8fd(final PomTransaction pomTransaction) throws Throwable {
        PsiElement changeScope = pomTransaction.getChangeScope();
        final PsiFile containingFileByTree = getContainingFileByTree(changeScope);
        final Document documentStartTransaction = startTransaction(pomTransaction, containingFileByTree);
        final PomTransaction blockingTransaction = getBlockingTransaction(changeScope);
        if (blockingTransaction != null) {
            blockingTransaction.getAccumulatedEvent().beforeNestedTransaction();
        }
        final ArrayList arrayList = new ArrayList(0);
        DebugUtil.performPsiModification((String) null, new ThrowableRunnable() { // from class: org.jetbrains.kotlin.com.intellij.pom.core.impl.PomModelImpl$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.ThrowableRunnable
            public final void run() {
                this.f$0.m6516x491a895e(pomTransaction, arrayList, blockingTransaction, containingFileByTree, documentStartTransaction);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$runTransaction$1$org-jetbrains-kotlin-com-intellij-pom-core-impl-PomModelImpl, reason: not valid java name */
    /* synthetic */ void m6516x491a895e(PomTransaction pomTransaction, List list, PomTransaction pomTransaction2, PsiFile psiFile, Document document) throws RuntimeException {
        try {
            try {
                Stack<PomTransaction> stack = this.myTransactionStack.get();
                stack.push(pomTransaction);
                try {
                    try {
                        pomTransaction.run();
                        PomModelEvent accumulatedEvent = pomTransaction.getAccumulatedEvent();
                        stack.pop();
                        if (pomTransaction2 != null) {
                            pomTransaction2.getAccumulatedEvent().merge(accumulatedEvent);
                            if (psiFile != null) {
                                try {
                                    commitTransaction(psiFile, document);
                                } catch (ProcessCanceledException e) {
                                    throw e;
                                } catch (Throwable th) {
                                    list.add(th);
                                }
                            }
                            if (list.isEmpty()) {
                                return;
                            }
                            CompoundRuntimeException.throwIfNotEmpty(list);
                            return;
                        }
                        if (accumulatedEvent.getChangedAspects().contains(this.myTreeAspect)) {
                            updateDependentAspects(accumulatedEvent);
                        }
                        for (PomModelListener pomModelListener : this.myListeners) {
                            Iterator<PomModelAspect> it = accumulatedEvent.getChangedAspects().iterator();
                            while (it.hasNext()) {
                                if (pomModelListener.isAspectChangeInteresting(it.next())) {
                                    pomModelListener.modelChanged(accumulatedEvent);
                                    break;
                                }
                            }
                        }
                        if (psiFile != null) {
                            try {
                                commitTransaction(psiFile, document);
                            } catch (ProcessCanceledException e2) {
                                throw e2;
                            } catch (Throwable th2) {
                                list.add(th2);
                            }
                        }
                        if (list.isEmpty()) {
                            return;
                        }
                        CompoundRuntimeException.throwIfNotEmpty(list);
                    } catch (Throwable th3) {
                        stack.pop();
                        throw th3;
                    }
                } catch (ProcessCanceledException e3) {
                    throw e3;
                } catch (Exception e4) {
                    list.add(e4);
                    stack.pop();
                    if (psiFile != null) {
                        try {
                            commitTransaction(psiFile, document);
                        } catch (ProcessCanceledException e5) {
                            throw e5;
                        } catch (Throwable th4) {
                            list.add(th4);
                        }
                    }
                    if (list.isEmpty()) {
                        return;
                    }
                    CompoundRuntimeException.throwIfNotEmpty(list);
                }
            } catch (Throwable th5) {
                if (psiFile != null) {
                    try {
                        commitTransaction(psiFile, document);
                    } catch (ProcessCanceledException e6) {
                        throw e6;
                    } catch (Throwable th6) {
                        list.add(th6);
                    }
                }
                if (!list.isEmpty()) {
                    CompoundRuntimeException.throwIfNotEmpty(list);
                }
                throw th5;
            }
        } catch (ProcessCanceledException e7) {
            throw e7;
        } catch (Throwable th7) {
            list.add(th7);
            if (psiFile != null) {
                try {
                    commitTransaction(psiFile, document);
                } catch (ProcessCanceledException e8) {
                    throw e8;
                } catch (Throwable th8) {
                    list.add(th8);
                }
            }
            if (list.isEmpty()) {
            }
        }
    }

    protected void updateDependentAspects(PomModelEvent pomModelEvent) {
        this.myPsiAspect.update(pomModelEvent);
    }

    private PomTransaction getBlockingTransaction(PsiElement psiElement) {
        Stack<PomTransaction> stack = this.myTransactionStack.get();
        ListIterator<PomTransaction> listIterator = stack.listIterator(stack.size());
        while (listIterator.hasPrevious()) {
            PomTransaction pomTransactionPrevious = listIterator.previous();
            if (PsiTreeUtil.isAncestor(getContainingFileByTree(pomTransactionPrevious.getChangeScope()), psiElement, false)) {
                return pomTransactionPrevious;
            }
        }
        return null;
    }

    private void commitTransaction(PsiFile psiFile, Document document) {
        if (psiFile == null) {
            $$$reportNull$$$0(7);
        }
        PsiDocumentManagerBase psiDocumentManagerBase = (PsiDocumentManagerBase) PsiDocumentManager.getInstance(this.myProject);
        PsiToDocumentSynchronizer synchronizer = psiDocumentManagerBase.getSynchronizer();
        boolean z = false;
        boolean z2 = (psiDocumentManagerBase.isCommitInProgress() || synchronizer.isIgnorePsiEvents()) ? false : true;
        if (z2) {
            reparseParallelTrees(psiFile, synchronizer);
        }
        if (document != null) {
            int textLength = psiFile.getTextLength();
            boolean zCommitTransaction = synchronizer.commitTransaction(document);
            if (zCommitTransaction) {
                sendAfterChildrenChangedEvent(psiFile, textLength);
            }
            z = zCommitTransaction;
        }
        if (z2 && z) {
            psiFile.getViewProvider().contentsSynchronized();
        }
    }

    private void reparseParallelTrees(PsiFile psiFile, PsiToDocumentSynchronizer psiToDocumentSynchronizer) {
        List<PsiFile> allFiles = psiFile.getViewProvider().getAllFiles();
        if (allFiles.size() <= 1) {
            return;
        }
        CharSequence chars = psiFile.getNode().getChars();
        Iterator<PsiFile> it = allFiles.iterator();
        while (it.hasNext()) {
            PsiFile next = it.next();
            FileElement treeElement = next == psiFile ? null : ((PsiFileImpl) next).getTreeElement();
            Runnable runnableReparseFile = treeElement != null ? reparseFile(next, treeElement, chars) : null;
            if (runnableReparseFile != null) {
                psiToDocumentSynchronizer.setIgnorePsiEvents(true);
                try {
                    CodeStyleManager.getInstance(next.getProject()).performActionWithFormatterDisabled(runnableReparseFile);
                    psiToDocumentSynchronizer.setIgnorePsiEvents(false);
                } catch (Throwable th) {
                    psiToDocumentSynchronizer.setIgnorePsiEvents(false);
                    throw th;
                }
            }
        }
    }

    public Runnable reparseFile(final PsiFile psiFile, FileElement fileElement, CharSequence charSequence) {
        if (psiFile == null) {
            $$$reportNull$$$0(8);
        }
        if (fileElement == null) {
            $$$reportNull$$$0(9);
        }
        if (charSequence == null) {
            $$$reportNull$$$0(10);
        }
        TextRange changedPsiRange = ChangedPsiRangeUtil.getChangedPsiRange(psiFile, fileElement, charSequence);
        if (changedPsiRange == null) {
            return null;
        }
        final DiffLog diffLogReparseRange = BlockSupport.getInstance(this.myProject).reparseRange(psiFile, fileElement, changedPsiRange, charSequence, EmptyProgressIndicator.notNullize(ProgressIndicatorProvider.getGlobalProgressIndicator()), fileElement.getText());
        return new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.pom.core.impl.PomModelImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m6515x5b277bed(psiFile, diffLogReparseRange);
            }
        };
    }

    /* JADX INFO: renamed from: lambda$reparseFile$3$org-jetbrains-kotlin-com-intellij-pom-core-impl-PomModelImpl, reason: not valid java name */
    /* synthetic */ void m6515x5b277bed(final PsiFile psiFile, final DiffLog diffLog) {
        runTransaction(new PomTransactionBase(psiFile) { // from class: org.jetbrains.kotlin.com.intellij.pom.core.impl.PomModelImpl.1
            @Override // org.jetbrains.kotlin.com.intellij.pom.impl.PomTransactionBase
            public PomModelEvent runInner() throws IncorrectOperationException {
                return new PomModelEvent(PomModelImpl.this, diffLog.performActualPsiChange(psiFile));
            }
        });
    }

    private Document startTransaction(PomTransaction pomTransaction, PsiFile psiFile) {
        if (pomTransaction == null) {
            $$$reportNull$$$0(11);
        }
        PsiDocumentManagerBase psiDocumentManagerBase = (PsiDocumentManagerBase) PsiDocumentManager.getInstance(this.myProject);
        PsiToDocumentSynchronizer synchronizer = psiDocumentManagerBase.getSynchronizer();
        PsiElement changeScope = pomTransaction.getChangeScope();
        if (psiFile != null && !(psiFile instanceof DummyHolder) && !psiDocumentManagerBase.isCommitInProgress()) {
            PsiUtilCore.ensureValid(psiFile);
        }
        boolean isPhysical = changeScope.getIsPhysical();
        if (synchronizer.toProcessPsiEvent()) {
            if (isDocumentUncommitted(psiFile)) {
                throw new IllegalStateException("Attempt to modify PSI for non-committed Document!");
            }
            CommandProcessor commandProcessor = CommandProcessor.getInstance();
            if (isPhysical && !commandProcessor.isUndoTransparentActionInProgress() && commandProcessor.getCurrentCommand() == null) {
                throw new IncorrectOperationException("Must not change PSI outside command or undo-transparent action. See com.intellij.openapi.command.WriteCommandAction or com.intellij.openapi.command.CommandProcessor");
            }
        }
        Document document = null;
        VirtualFile virtualFile = psiFile == null ? null : psiFile.getViewProvider().getVirtualFile();
        if (psiFile != null) {
            ((SmartPointerManagerImpl) SmartPointerManager.getInstance(this.myProject)).fastenBelts(virtualFile);
            if (psiFile instanceof PsiFileImpl) {
                ((PsiFileImpl) psiFile).beforeAstChange();
            }
        }
        sendBeforeChildrenChangeEvent(changeScope);
        if (psiFile != null && !(psiFile instanceof DummyHolder)) {
            if (isPhysical || ModelBranch.getPsiBranch(psiFile) != null) {
                document = FileDocumentManager.getInstance().getDocument(virtualFile);
            } else {
                document = FileDocumentManager.getInstance().getCachedDocument(virtualFile);
            }
        }
        if (document != null) {
            synchronizer.startTransaction(this.myProject, document, psiFile);
        }
        return document;
    }

    private boolean isDocumentUncommitted(PsiFile psiFile) {
        PsiDocumentManager psiDocumentManager;
        Document cachedDocument;
        return (psiFile == null || (cachedDocument = (psiDocumentManager = PsiDocumentManager.getInstance(this.myProject)).getCachedDocument(psiFile)) == null || !psiDocumentManager.isUncommited(cachedDocument)) ? false : true;
    }

    private static PsiFile getContainingFileByTree(PsiElement psiElement) {
        PsiFile containingFile;
        if (psiElement == null) {
            $$$reportNull$$$0(12);
        }
        ASTNode node = psiElement.getNode();
        if (node == null) {
            containingFile = psiElement.getContainingFile();
        } else {
            FileASTNode fileElement = TreeUtil.getFileElement(node);
            if (fileElement == null) {
                return null;
            }
            containingFile = (PsiFile) fileElement.getPsi();
        }
        if (containingFile.getNode() != null) {
            return containingFile;
        }
        return null;
    }

    public static boolean isAllowPsiModification() {
        return allowPsiModification;
    }

    private void sendBeforeChildrenChangeEvent(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(14);
        }
        if (!shouldFirePhysicalPsiEvents(psiElement)) {
            getPsiManager().beforeChange(false);
            return;
        }
        PsiTreeChangeEventImpl psiTreeChangeEventImpl = new PsiTreeChangeEventImpl(getPsiManager());
        psiTreeChangeEventImpl.setParent(psiElement);
        psiTreeChangeEventImpl.setFile(psiElement.getContainingFile());
        TextRange textRange = psiElement.getTextRange();
        psiTreeChangeEventImpl.setOffset(textRange != null ? textRange.getStartOffset() : 0);
        psiTreeChangeEventImpl.setOldLength(psiElement.getTextLength());
        psiTreeChangeEventImpl.setGenericChange(true);
        getPsiManager().beforeChildrenChange(psiTreeChangeEventImpl);
    }

    public static boolean shouldFirePhysicalPsiEvents(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(15);
        }
        return psiElement.getIsPhysical() && ModelBranch.getPsiBranch(psiElement) == null;
    }

    private void sendAfterChildrenChangedEvent(PsiFile psiFile, int i) {
        if (psiFile == null) {
            $$$reportNull$$$0(16);
        }
        if (!shouldFirePhysicalPsiEvents(psiFile)) {
            getPsiManager().afterChange(false);
            return;
        }
        PsiTreeChangeEventImpl psiTreeChangeEventImpl = new PsiTreeChangeEventImpl(getPsiManager());
        psiTreeChangeEventImpl.setParent(psiFile);
        psiTreeChangeEventImpl.setFile(psiFile);
        psiTreeChangeEventImpl.setOffset(0);
        psiTreeChangeEventImpl.setOldLength(i);
        psiTreeChangeEventImpl.setGenericChange(true);
        getPsiManager().childrenChanged(psiTreeChangeEventImpl);
    }

    private PsiManagerImpl getPsiManager() {
        PsiManagerImpl psiManagerImpl = (PsiManagerImpl) PsiManager.getInstance(this.myProject);
        if (psiManagerImpl == null) {
            $$$reportNull$$$0(17);
        }
        return psiManagerImpl;
    }
}
