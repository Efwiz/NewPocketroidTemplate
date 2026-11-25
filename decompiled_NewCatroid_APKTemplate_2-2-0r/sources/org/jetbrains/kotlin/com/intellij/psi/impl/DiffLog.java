package org.jetbrains.kotlin.com.intellij.psi.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.FileASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressIndicatorProvider;
import org.jetbrains.kotlin.com.intellij.pom.PomManager;
import org.jetbrains.kotlin.com.intellij.pom.PomModel;
import org.jetbrains.kotlin.com.intellij.pom.event.PomModelEvent;
import org.jetbrains.kotlin.com.intellij.pom.impl.PomTransactionBase;
import org.jetbrains.kotlin.com.intellij.pom.tree.TreeAspect;
import org.jetbrains.kotlin.com.intellij.pom.tree.events.impl.TreeChangeEventImpl;
import org.jetbrains.kotlin.com.intellij.psi.AbstractFileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiInvalidElementAccessException;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.CodeStyleManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.FileElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeUtil;
import org.jetbrains.kotlin.com.intellij.util.diff.DiffTreeChangeBuilder;

/* JADX INFO: loaded from: classes3.dex */
public class DiffLog implements DiffTreeChangeBuilder<ASTNode, ASTNode> {
    private final List<LogEntry> myEntries = new ArrayList();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/DiffLog";
                break;
            case 2:
            case 6:
            case 9:
                objArr[0] = "oldNode";
                break;
            case 3:
            case 7:
            case 11:
                objArr[0] = "newNode";
                break;
            case 4:
                objArr[0] = "oldRoot";
                break;
            case 5:
                objArr[0] = "newRoot";
                break;
            case 8:
            case 10:
                objArr[0] = "oldParent";
                break;
            default:
                objArr[0] = "file";
                break;
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/DiffLog";
        } else {
            objArr[1] = "performActualPsiChange";
        }
        switch (i) {
            case 1:
                break;
            case 2:
            case 3:
                objArr[2] = "nodeReplaced";
                break;
            case 4:
            case 5:
                objArr[2] = "appendReplaceElementWithEvents";
                break;
            case 6:
            case 7:
                objArr[2] = "appendReplaceFileElement";
                break;
            case 8:
            case 9:
                objArr[2] = "nodeDeleted";
                break;
            case 10:
            case 11:
                objArr[2] = "nodeInserted";
                break;
            case 12:
                objArr[2] = "doActualPsiChange";
                break;
            default:
                objArr[2] = "performActualPsiChange";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    private static abstract class LogEntry {
        abstract void doActualPsiChange(PsiFile psiFile, TreeChangeEventImpl treeChangeEventImpl);

        LogEntry() {
            ProgressIndicatorProvider.checkCanceled();
        }
    }

    public TreeChangeEventImpl performActualPsiChange(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(0);
        }
        TreeChangeEventImpl treeChangeEventImpl = new TreeChangeEventImpl((TreeAspect) PomManager.getModel(psiFile.getProject()).getModelAspect(TreeAspect.class), psiFile.getNode());
        Iterator<LogEntry> it = this.myEntries.iterator();
        while (it.hasNext()) {
            it.next().doActualPsiChange(psiFile, treeChangeEventImpl);
        }
        psiFile.subtreeChanged();
        return treeChangeEventImpl;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.diff.DiffTreeChangeBuilder
    public void nodeReplaced(ASTNode aSTNode, ASTNode aSTNode2) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        if (aSTNode2 == null) {
            $$$reportNull$$$0(3);
        }
        if ((aSTNode instanceof FileASTNode) && (aSTNode2 instanceof FileASTNode)) {
            appendReplaceFileElement((FileASTNode) aSTNode, (FileASTNode) aSTNode2);
        } else {
            this.myEntries.add(new ReplaceEntry(aSTNode, aSTNode2));
        }
    }

    void appendReplaceElementWithEvents(CompositeElement compositeElement, CompositeElement compositeElement2) {
        if (compositeElement == null) {
            $$$reportNull$$$0(4);
        }
        if (compositeElement2 == null) {
            $$$reportNull$$$0(5);
        }
        this.myEntries.add(new ReplaceElementWithEvents(compositeElement, compositeElement2));
    }

    void appendReplaceFileElement(FileASTNode fileASTNode, FileASTNode fileASTNode2) {
        if (fileASTNode == null) {
            $$$reportNull$$$0(6);
        }
        if (fileASTNode2 == null) {
            $$$reportNull$$$0(7);
        }
        this.myEntries.add(new ReplaceFileElement(fileASTNode, fileASTNode2));
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.diff.DiffTreeChangeBuilder
    public void nodeDeleted(ASTNode aSTNode, ASTNode aSTNode2) {
        if (aSTNode == null) {
            $$$reportNull$$$0(8);
        }
        if (aSTNode2 == null) {
            $$$reportNull$$$0(9);
        }
        this.myEntries.add(new DeleteEntry(aSTNode, aSTNode2));
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.diff.DiffTreeChangeBuilder
    public void nodeInserted(ASTNode aSTNode, ASTNode aSTNode2, int i) {
        if (aSTNode == null) {
            $$$reportNull$$$0(10);
        }
        if (aSTNode2 == null) {
            $$$reportNull$$$0(11);
        }
        this.myEntries.add(new InsertEntry(aSTNode, aSTNode2, i));
    }

    private static final class ReplaceEntry extends LogEntry {
        private final ASTNode myNewChild;
        private final ASTNode myOldChild;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 4 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "newNode";
            } else if (i == 2) {
                objArr[0] = "file";
            } else if (i == 3) {
                objArr[0] = "changeEvent";
            } else if (i != 4) {
                objArr[0] = "oldNode";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/DiffLog$ReplaceEntry";
            }
            if (i != 4) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/DiffLog$ReplaceEntry";
            } else {
                objArr[1] = "ensureOldParent";
            }
            if (i == 2 || i == 3) {
                objArr[2] = "doActualPsiChange";
            } else if (i != 4) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 4) {
                throw new IllegalStateException(str2);
            }
        }

        private ReplaceEntry(ASTNode aSTNode, ASTNode aSTNode2) {
            if (aSTNode == null) {
                $$$reportNull$$$0(0);
            }
            if (aSTNode2 == null) {
                $$$reportNull$$$0(1);
            }
            this.myOldChild = aSTNode;
            this.myNewChild = aSTNode2;
            ensureOldParent();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.DiffLog.LogEntry
        void doActualPsiChange(PsiFile psiFile, TreeChangeEventImpl treeChangeEventImpl) {
            if (psiFile == null) {
                $$$reportNull$$$0(2);
            }
            if (treeChangeEventImpl == null) {
                $$$reportNull$$$0(3);
            }
            ASTNode aSTNodeEnsureOldParent = ensureOldParent();
            PsiElement psi = aSTNodeEnsureOldParent.getPsi();
            PsiElement psi2 = psiFile.getIsPhysical() ? this.myOldChild.getPsi() : null;
            if (psi != null && psi2 != null) {
                PsiTreeChangeEventImpl psiTreeChangeEventImpl = new PsiTreeChangeEventImpl(psiFile.getManager());
                psiTreeChangeEventImpl.setParent(psi);
                psiTreeChangeEventImpl.setFile(psiFile);
                psiTreeChangeEventImpl.setOldChild(psi2);
                psiTreeChangeEventImpl.setNewChild(DiffLog.getPsi(this.myNewChild, psiFile));
                ((PsiManagerEx) psiFile.getManager()).beforeChildReplacement(psiTreeChangeEventImpl);
            }
            ASTNode aSTNode = this.myOldChild;
            if (!(aSTNode instanceof FileElement) || !(this.myNewChild instanceof FileElement)) {
                treeChangeEventImpl.addElementaryChange(aSTNode.getTreeParent());
            }
            ((ReparseableASTNode) this.myOldChild).applyReplaceOnReparse(this.myNewChild);
            DebugUtil.checkTreeStructure(aSTNodeEnsureOldParent);
        }

        private ASTNode ensureOldParent() {
            ASTNode treeParent = this.myOldChild.getTreeParent();
            if (treeParent == null) {
                throw PsiInvalidElementAccessException.createByNode(this.myOldChild, "new:" + this.myNewChild);
            }
            if (treeParent == null) {
                $$$reportNull$$$0(4);
            }
            return treeParent;
        }
    }

    private static final class DeleteEntry extends LogEntry {
        private final ASTNode myOldNode;
        private final ASTNode myOldParent;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "oldNode";
            } else if (i == 2) {
                objArr[0] = "file";
            } else if (i != 3) {
                objArr[0] = "oldParent";
            } else {
                objArr[0] = "changeEvent";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/DiffLog$DeleteEntry";
            if (i == 2 || i == 3) {
                objArr[2] = "doActualPsiChange";
            } else {
                objArr[2] = "<init>";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private DeleteEntry(ASTNode aSTNode, ASTNode aSTNode2) {
            if (aSTNode == null) {
                $$$reportNull$$$0(0);
            }
            if (aSTNode2 == null) {
                $$$reportNull$$$0(1);
            }
            this.myOldParent = aSTNode;
            this.myOldNode = aSTNode2;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.DiffLog.LogEntry
        void doActualPsiChange(PsiFile psiFile, TreeChangeEventImpl treeChangeEventImpl) {
            if (psiFile == null) {
                $$$reportNull$$$0(2);
            }
            if (treeChangeEventImpl == null) {
                $$$reportNull$$$0(3);
            }
            PsiElement psi = this.myOldParent.getPsi();
            PsiElement psi2 = psiFile.getIsPhysical() ? this.myOldNode.getPsi() : null;
            if (psi != null && psi2 != null) {
                PsiTreeChangeEventImpl psiTreeChangeEventImpl = new PsiTreeChangeEventImpl(psiFile.getManager());
                psiTreeChangeEventImpl.setParent(psi);
                psiTreeChangeEventImpl.setChild(psi2);
                psiTreeChangeEventImpl.setFile(psiFile);
                ((PsiManagerEx) psiFile.getManager()).beforeChildRemoval(psiTreeChangeEventImpl);
            }
            treeChangeEventImpl.addElementaryChange(this.myOldParent);
            ((ReparseableASTNode) this.myOldParent).applyDeleteOnReparse(this.myOldNode);
            DebugUtil.checkTreeStructure(this.myOldParent);
        }
    }

    private static final class InsertEntry extends LogEntry {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final ASTNode myNewNode;
        private final ASTNode myOldParent;
        private final int myPos;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "newNode";
            } else if (i == 2) {
                objArr[0] = "file";
            } else if (i != 3) {
                objArr[0] = "oldParent";
            } else {
                objArr[0] = "changeEvent";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/DiffLog$InsertEntry";
            if (i == 2 || i == 3) {
                objArr[2] = "doActualPsiChange";
            } else {
                objArr[2] = "<init>";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private InsertEntry(ASTNode aSTNode, ASTNode aSTNode2, int i) {
            if (aSTNode == null) {
                $$$reportNull$$$0(0);
            }
            if (aSTNode2 == null) {
                $$$reportNull$$$0(1);
            }
            this.myOldParent = aSTNode;
            this.myNewNode = aSTNode2;
            this.myPos = i;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.DiffLog.LogEntry
        void doActualPsiChange(PsiFile psiFile, TreeChangeEventImpl treeChangeEventImpl) {
            if (psiFile == null) {
                $$$reportNull$$$0(2);
            }
            if (treeChangeEventImpl == null) {
                $$$reportNull$$$0(3);
            }
            ASTNode firstChildNode = this.myOldParent.getFirstChildNode();
            ASTNode treeNext = null;
            for (int i = 0; i < this.myPos; i++) {
                treeNext = treeNext == null ? firstChildNode : treeNext.getTreeNext();
            }
            PsiElement psi = this.myOldParent.getPsi();
            PsiElement psi2 = DiffLog.getPsi(this.myNewNode, psiFile);
            if (psi != null && psi2 != null) {
                PsiTreeChangeEventImpl psiTreeChangeEventImpl = new PsiTreeChangeEventImpl(psiFile.getManager());
                psiTreeChangeEventImpl.setParent(psi);
                psiTreeChangeEventImpl.setChild(psi2);
                psiTreeChangeEventImpl.setFile(psiFile);
                ((PsiManagerEx) psiFile.getManager()).beforeChildAddition(psiTreeChangeEventImpl);
            }
            treeChangeEventImpl.addElementaryChange(this.myOldParent);
            ((ReparseableASTNode) this.myOldParent).applyInsertOnReparse(this.myNewNode, treeNext);
            DebugUtil.checkTreeStructure(this.myOldParent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PsiElement getPsi(ASTNode aSTNode, PsiFile psiFile) {
        aSTNode.putUserData(TreeUtil.CONTAINING_FILE_KEY_AFTER_REPARSE, ((PsiFileImpl) psiFile).getTreeElement());
        PsiElement psi = psiFile.getIsPhysical() ? aSTNode.getPsi() : null;
        aSTNode.putUserData(TreeUtil.CONTAINING_FILE_KEY_AFTER_REPARSE, null);
        return psi;
    }

    private static final class ReplaceFileElement extends LogEntry {
        private final FileASTNode myNewNode;
        private final FileASTNode myOldNode;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "newNode";
            } else if (i == 2) {
                objArr[0] = "file";
            } else if (i != 3) {
                objArr[0] = "oldNode";
            } else {
                objArr[0] = "event";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/DiffLog$ReplaceFileElement";
            if (i == 2 || i == 3) {
                objArr[2] = "doActualPsiChange";
            } else {
                objArr[2] = "<init>";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private ReplaceFileElement(FileASTNode fileASTNode, FileASTNode fileASTNode2) {
            if (fileASTNode == null) {
                $$$reportNull$$$0(0);
            }
            if (fileASTNode2 == null) {
                $$$reportNull$$$0(1);
            }
            this.myOldNode = fileASTNode;
            this.myNewNode = fileASTNode2;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.DiffLog.LogEntry
        void doActualPsiChange(PsiFile psiFile, TreeChangeEventImpl treeChangeEventImpl) {
            if (psiFile == null) {
                $$$reportNull$$$0(2);
            }
            if (treeChangeEventImpl == null) {
                $$$reportNull$$$0(3);
            }
            treeChangeEventImpl.addElementaryChange(this.myOldNode);
            ((ReparseableASTNode) this.myOldNode).applyReplaceFileOnReparse(psiFile, this.myNewNode);
        }
    }

    private static final class ReplaceElementWithEvents extends LogEntry {
        private final CompositeElement myNewRoot;
        private final CompositeElement myOldRoot;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "newRoot";
            } else if (i == 2) {
                objArr[0] = "file";
            } else if (i != 3) {
                objArr[0] = "oldRoot";
            } else {
                objArr[0] = "event";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/DiffLog$ReplaceElementWithEvents";
            if (i == 2 || i == 3) {
                objArr[2] = "doActualPsiChange";
            } else {
                objArr[2] = "<init>";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        private ReplaceElementWithEvents(CompositeElement compositeElement, CompositeElement compositeElement2) {
            if (compositeElement == null) {
                $$$reportNull$$$0(0);
            }
            if (compositeElement2 == null) {
                $$$reportNull$$$0(1);
            }
            this.myOldRoot = compositeElement;
            this.myNewRoot = compositeElement2;
            TreeUtil.ensureParsed(compositeElement.getFirstChildNode());
            TreeUtil.ensureParsed(compositeElement2.getFirstChildNode());
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.impl.DiffLog.LogEntry
        void doActualPsiChange(PsiFile psiFile, TreeChangeEventImpl treeChangeEventImpl) {
            if (psiFile == null) {
                $$$reportNull$$$0(2);
            }
            if (treeChangeEventImpl == null) {
                $$$reportNull$$$0(3);
            }
            this.myOldRoot.replaceAllChildrenToChildrenOf(this.myNewRoot);
        }
    }

    public void doActualPsiChange(final PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(12);
        }
        CodeStyleManager.getInstance(psiFile.getProject()).performActionWithFormatterDisabled(new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.DiffLog$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m6543x4b2fb9cf(psiFile);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$doActualPsiChange$0$org-jetbrains-kotlin-com-intellij-psi-impl-DiffLog, reason: not valid java name */
    /* synthetic */ void m6543x4b2fb9cf(final PsiFile psiFile) {
        FileViewProvider viewProvider = psiFile.getViewProvider();
        synchronized (((AbstractFileViewProvider) viewProvider).getFilePsiLock()) {
            viewProvider.beforeContentsSynchronized();
            if (((PsiDocumentManagerBase) PsiDocumentManager.getInstance(psiFile.getProject())).getSynchronizer().getTransaction(viewProvider.getDocument()) == null) {
                final PomModel model = PomManager.getModel(psiFile.getProject());
                model.runTransaction(new PomTransactionBase(psiFile) { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.DiffLog.1
                    @Override // org.jetbrains.kotlin.com.intellij.pom.impl.PomTransactionBase
                    public PomModelEvent runInner() {
                        return new PomModelEvent(model, DiffLog.this.performActualPsiChange(psiFile));
                    }
                });
            } else {
                performActualPsiChange(psiFile);
            }
        }
    }
}
