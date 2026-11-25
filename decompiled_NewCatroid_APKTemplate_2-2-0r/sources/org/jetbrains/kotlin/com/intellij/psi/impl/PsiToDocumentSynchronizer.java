package org.jetbrains.kotlin.com.intellij.psi.impl;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.injected.editor.DocumentWindow;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.editor.ex.DocumentEx;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.pom.core.impl.PomModelImpl;
import org.jetbrains.kotlin.com.intellij.pom.tree.events.impl.ChangeInfoImpl;
import org.jetbrains.kotlin.com.intellij.pom.tree.events.impl.TreeChangeEventImpl;
import org.jetbrains.kotlin.com.intellij.pom.tree.events.impl.TreeChangeImpl;
import org.jetbrains.kotlin.com.intellij.psi.IgnorePsiEventsMarker;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolder;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ForeignLeafPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.kotlin.com.intellij.util.ExceptionUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.messages.MessageBus;
import org.jetbrains.kotlin.com.intellij.util.text.CharArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.text.ImmutableCharSequence;

/* JADX INFO: loaded from: classes3.dex */
public class PsiToDocumentSynchronizer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiToDocumentSynchronizer.class);
    private static final Key<Boolean> PSI_DOCUMENT_ATOMIC_ACTION = Key.create("PSI_DOCUMENT_ATOMIC_ACTION");
    private final MessageBus myBus;
    private boolean myIgnorePsiEvents;
    private final PsiDocumentManagerBase myPsiDocumentManager;
    private volatile Document mySyncDocument;
    private final Map<Document, Pair<DocumentChangeTransaction, Integer>> myTransactionsMap;

    /* JADX INFO: Access modifiers changed from: private */
    @FunctionalInterface
    interface DocSyncAction {
        void syncDocument(Document document, PsiTreeChangeEventImpl psiTreeChangeEventImpl);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "bus";
                break;
            case 2:
            case 3:
            case 10:
            case 12:
            case 17:
            case 18:
            case 21:
                objArr[0] = "document";
                break;
            case 4:
            case 5:
                objArr[0] = "event";
                break;
            case 6:
                objArr[0] = "syncAction";
                break;
            case 7:
            case 8:
            case 23:
                objArr[0] = "file";
                break;
            case 9:
                objArr[0] = "runnable";
                break;
            case 11:
            case 13:
                objArr[0] = "s";
                break;
            case 14:
                objArr[0] = "project";
                break;
            case 15:
            case 20:
                objArr[0] = "doc";
                break;
            case 16:
                objArr[0] = "scope";
                break;
            case 19:
                objArr[0] = "documentChangeTransaction";
                break;
            case 22:
                objArr[0] = "changeSet";
                break;
            default:
                objArr[0] = "psiDocumentManager";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiToDocumentSynchronizer";
        switch (i) {
            case 2:
                objArr[2] = "getTransaction";
                break;
            case 3:
                objArr[2] = "isInSynchronization";
                break;
            case 4:
                objArr[2] = "checkPsiModificationAllowed";
                break;
            case 5:
            case 6:
                objArr[2] = "doSync";
                break;
            case 7:
                objArr[2] = "isInsideAtomicChange";
                break;
            case 8:
            case 9:
                objArr[2] = "performAtomically";
                break;
            case 10:
            case 11:
                objArr[2] = "replaceString";
                break;
            case 12:
            case 13:
                objArr[2] = "insertString";
                break;
            case 14:
            case 15:
            case 16:
                objArr[2] = "startTransaction";
                break;
            case 17:
                objArr[2] = "commitTransaction";
                break;
            case 18:
            case 19:
                objArr[2] = "doCommitTransaction";
                break;
            case 20:
                objArr[2] = "removeTransaction";
                break;
            case 21:
                objArr[2] = "isDocumentAffectedByTransactions";
                break;
            case 22:
            case 23:
                objArr[2] = "processEvents";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    PsiToDocumentSynchronizer(PsiDocumentManagerBase psiDocumentManagerBase, MessageBus messageBus) {
        if (psiDocumentManagerBase == null) {
            $$$reportNull$$$0(0);
        }
        if (messageBus == null) {
            $$$reportNull$$$0(1);
        }
        this.myTransactionsMap = new ConcurrentHashMap();
        this.myPsiDocumentManager = psiDocumentManagerBase;
        this.myBus = messageBus;
    }

    public DocumentChangeTransaction getTransaction(Document document) {
        if (document == null) {
            $$$reportNull$$$0(2);
        }
        return (DocumentChangeTransaction) Pair.getFirst(this.myTransactionsMap.get(document));
    }

    public boolean isInSynchronization(Document document) {
        if (document == null) {
            $$$reportNull$$$0(3);
        }
        return this.mySyncDocument == document;
    }

    void cleanupForNextTest() {
        this.myTransactionsMap.clear();
        this.mySyncDocument = null;
    }

    private void checkPsiModificationAllowed(PsiTreeChangeEvent psiTreeChangeEvent) {
        Document cachedDocument;
        if (psiTreeChangeEvent == null) {
            $$$reportNull$$$0(4);
        }
        if (toProcessPsiEvent()) {
            PsiFile file = psiTreeChangeEvent.getFile();
            if ((file instanceof PsiFileEx) && ((PsiFileEx) file).isContentsLoaded() && (cachedDocument = this.myPsiDocumentManager.getCachedDocument(file)) != null && this.myPsiDocumentManager.isUncommited(cachedDocument)) {
                throw new IllegalStateException("Attempt to modify PSI for non-committed Document!");
            }
        }
    }

    private DocumentEx getCachedDocument(PsiFile psiFile, boolean z) {
        DocumentEx documentEx = (DocumentEx) FileDocumentManager.getInstance().getCachedDocument(psiFile.getViewProvider().getVirtualFile());
        if (documentEx == null || (documentEx instanceof DocumentWindow)) {
            return null;
        }
        if (z || getTransaction(documentEx) != null) {
            return documentEx;
        }
        return null;
    }

    private void doSync(final PsiTreeChangeEvent psiTreeChangeEvent, final DocSyncAction docSyncAction) {
        final DocumentEx cachedDocument;
        if (psiTreeChangeEvent == null) {
            $$$reportNull$$$0(5);
        }
        if (docSyncAction == null) {
            $$$reportNull$$$0(6);
        }
        if (toProcessPsiEvent()) {
            PsiFile file = psiTreeChangeEvent.getFile();
            if ((file instanceof PsiFileEx) && ((PsiFileEx) file).isContentsLoaded() && (cachedDocument = getCachedDocument(file, true)) != null) {
                performAtomically(file, new Runnable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiToDocumentSynchronizer$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        docSyncAction.syncDocument(cachedDocument, (PsiTreeChangeEventImpl) psiTreeChangeEvent);
                    }
                });
                if (this.myTransactionsMap.containsKey(cachedDocument)) {
                    return;
                }
                cachedDocument.setModificationStamp(file.getViewProvider().getModificationStamp());
            }
        }
    }

    static boolean isInsideAtomicChange(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(7);
        }
        return psiFile.getUserData(PSI_DOCUMENT_ATOMIC_ACTION) == Boolean.TRUE;
    }

    public static void performAtomically(PsiFile psiFile, Runnable runnable) {
        if (psiFile == null) {
            $$$reportNull$$$0(8);
        }
        if (runnable == null) {
            $$$reportNull$$$0(9);
        }
        PsiUtilCore.ensureValid(psiFile);
        psiFile.putUserData(PSI_DOCUMENT_ATOMIC_ACTION, Boolean.TRUE);
        try {
            runnable.run();
        } finally {
            psiFile.putUserData(PSI_DOCUMENT_ATOMIC_ACTION, null);
        }
    }

    public void setIgnorePsiEvents(boolean z) {
        this.myIgnorePsiEvents = z;
    }

    public boolean isIgnorePsiEvents() {
        return this.myIgnorePsiEvents;
    }

    public boolean toProcessPsiEvent() {
        return (this.myIgnorePsiEvents || this.myPsiDocumentManager.isCommitInProgress() || ApplicationManager.getApplication().hasWriteAction(IgnorePsiEventsMarker.class)) ? false : true;
    }

    public void replaceString(Document document, int i, int i2, String str) {
        if (document == null) {
            $$$reportNull$$$0(10);
        }
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        DocumentChangeTransaction transaction = getTransaction(document);
        if (transaction != null) {
            transaction.replace(i, i2 - i, str, null);
        }
    }

    public void insertString(Document document, int i, String str) {
        if (document == null) {
            $$$reportNull$$$0(12);
        }
        if (str == null) {
            $$$reportNull$$$0(13);
        }
        DocumentChangeTransaction transaction = getTransaction(document);
        if (transaction != null) {
            transaction.replace(i, 0, str, null);
        }
    }

    public void startTransaction(Project project, Document document, PsiFile psiFile) {
        Pair<DocumentChangeTransaction, Integer> pair;
        if (project == null) {
            $$$reportNull$$$0(14);
        }
        if (document == null) {
            $$$reportNull$$$0(15);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(16);
        }
        Logger logger = LOG;
        logger.assertTrue(!project.isDisposed());
        Pair<DocumentChangeTransaction, Integer> pair2 = this.myTransactionsMap.get(document);
        if (pair2 == null) {
            PsiFile containingFile = psiFile.getContainingFile();
            pair = new Pair<>(new DocumentChangeTransaction(document, containingFile), 0);
            if (psiFile.getIsPhysical()) {
                ((PsiDocumentTransactionListener) this.myBus.syncPublisher(PsiDocumentTransactionListener.TOPIC)).transactionStarted(document, containingFile);
            }
        } else {
            pair = new Pair<>(pair2.getFirst(), Integer.valueOf(pair2.getSecond().intValue() + 1));
        }
        logger.assertTrue(this.myTransactionsMap.put(document, pair) == pair2);
    }

    public boolean commitTransaction(Document document) {
        if (document == null) {
            $$$reportNull$$$0(17);
        }
        final DocumentChangeTransaction documentChangeTransactionRemoveTransaction = removeTransaction(document);
        if (documentChangeTransactionRemoveTransaction == null) {
            return false;
        }
        PsiFile psiFile = documentChangeTransactionRemoveTransaction.myChangeScope;
        try {
            this.mySyncDocument = document;
            PsiTreeChangeEventImpl psiTreeChangeEventImpl = new PsiTreeChangeEventImpl(psiFile.getManager());
            psiTreeChangeEventImpl.setParent(psiFile);
            psiTreeChangeEventImpl.setFile(psiFile);
            checkPsiModificationAllowed(psiTreeChangeEventImpl);
            doSync(psiTreeChangeEventImpl, new DocSyncAction() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiToDocumentSynchronizer$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiToDocumentSynchronizer.DocSyncAction
                public final void syncDocument(Document document2, PsiTreeChangeEventImpl psiTreeChangeEventImpl2) {
                    PsiToDocumentSynchronizer.doCommitTransaction(document2, documentChangeTransactionRemoveTransaction);
                }
            });
            if (PomModelImpl.shouldFirePhysicalPsiEvents(psiFile)) {
                ((PsiDocumentTransactionListener) this.myBus.syncPublisher(PsiDocumentTransactionListener.TOPIC)).transactionCompleted(document, psiFile);
            }
        } catch (Throwable th) {
            try {
                this.myPsiDocumentManager.forceReload(psiFile.getViewProvider().getVirtualFile(), psiFile.getViewProvider());
                ExceptionUtil.rethrowAllAsUnchecked(th);
            } finally {
                this.mySyncDocument = null;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void doCommitTransaction(Document document, DocumentChangeTransaction documentChangeTransaction) {
        if (document == null) {
            $$$reportNull$$$0(18);
        }
        if (documentChangeTransaction == null) {
            $$$reportNull$$$0(19);
        }
        DocumentEx documentEx = (DocumentEx) document;
        documentEx.suppressGuardedExceptions();
        try {
            boolean z = !document.isWritable();
            documentEx.setReadOnly(false);
            for (Map.Entry entry : documentChangeTransaction.myAffectedFragments.descendingMap().entrySet()) {
                documentEx.replaceString(((TextRange) entry.getKey()).getStartOffset(), ((TextRange) entry.getKey()).getEndOffset(), (CharSequence) entry.getValue());
            }
            documentEx.setReadOnly(z);
        } finally {
            documentEx.unSuppressGuardedExceptions();
        }
    }

    private DocumentChangeTransaction removeTransaction(Document document) {
        if (document == null) {
            $$$reportNull$$$0(20);
        }
        Pair<DocumentChangeTransaction, Integer> pair = this.myTransactionsMap.get(document);
        if (pair == null) {
            return null;
        }
        int iIntValue = pair.getSecond().intValue();
        if (iIntValue > 0) {
            this.myTransactionsMap.put(document, Pair.create(pair.getFirst(), Integer.valueOf(iIntValue - 1)));
            return null;
        }
        this.myTransactionsMap.remove(document);
        return pair.getFirst();
    }

    public boolean isDocumentAffectedByTransactions(Document document) {
        if (document == null) {
            $$$reportNull$$$0(21);
        }
        return this.myTransactionsMap.containsKey(document);
    }

    public void processEvents(TreeChangeEventImpl treeChangeEventImpl, PsiFile psiFile) {
        if (treeChangeEventImpl == null) {
            $$$reportNull$$$0(22);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(23);
        }
        if ((psiFile instanceof DummyHolder) || !toProcessPsiEvent()) {
            return;
        }
        DocumentEx cachedDocument = getCachedDocument(psiFile, false);
        DocumentChangeTransaction transaction = cachedDocument == null ? null : getTransaction(cachedDocument);
        if (transaction == null) {
            return;
        }
        for (TreeChangeImpl treeChangeImpl : treeChangeEventImpl.getSortedChanges()) {
            int startOffset = treeChangeImpl.getChangedParent().getStartOffset();
            for (ASTNode aSTNode : treeChangeImpl.getAffectedChildren()) {
                ChangeInfoImpl changeByChild = treeChangeImpl.getChangeByChild(aSTNode);
                ASTNode newChild = changeByChild.getNewChild();
                PsiElement psi = newChild == null ? null : newChild.getPsi();
                if (!(psi instanceof ForeignLeafPsiElement)) {
                    transaction.replace(changeByChild.getOffsetInParent() + startOffset, changeByChild.getOldLength(), newChild == null ? "" : newChild.getText(), psi);
                }
            }
        }
    }

    public static class DocumentChangeTransaction {
        private final TreeMap<TextRange, CharSequence> myAffectedFragments;
        private final PsiFile myChangeScope;
        private ImmutableCharSequence myPsiText;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "scope";
            } else if (i == 2) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiToDocumentSynchronizer$DocumentChangeTransaction";
            } else if (i == 3 || i == 4) {
                objArr[0] = SchemaSymbols.ATTVAL_REPLACE;
            } else {
                objArr[0] = "doc";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiToDocumentSynchronizer$DocumentChangeTransaction";
            } else {
                objArr[1] = "getAffectedFragments";
            }
            if (i != 2) {
                if (i == 3) {
                    objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                } else if (i != 4) {
                    objArr[2] = "<init>";
                } else {
                    objArr[2] = "updateFragments";
                }
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalStateException(str2);
            }
        }

        DocumentChangeTransaction(Document document, PsiFile psiFile) {
            if (document == null) {
                $$$reportNull$$$0(0);
            }
            if (psiFile == null) {
                $$$reportNull$$$0(1);
            }
            this.myAffectedFragments = new TreeMap<>(Comparator.comparingInt(new ToIntFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiToDocumentSynchronizer$DocumentChangeTransaction$$ExternalSyntheticLambda0
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    return ((TextRange) obj).getStartOffset();
                }
            }));
            this.myChangeScope = psiFile;
            this.myPsiText = CharArrayUtil.createImmutableCharSequence(document.getImmutableCharSequence());
        }

        public Map<TextRange, CharSequence> getAffectedFragments() {
            TreeMap<TextRange, CharSequence> treeMap = this.myAffectedFragments;
            if (treeMap == null) {
                $$$reportNull$$$0(2);
            }
            return treeMap;
        }

        void replace(int i, int i2, String str, PsiElement psiElement) {
            if (str == null) {
                $$$reportNull$$$0(3);
            }
            CharSequence charSequenceSubSequence = this.myPsiText.subSequence(i, i + i2);
            if (StringUtil.equals(charSequenceSubSequence, str)) {
                return;
            }
            int length = str.length();
            int i3 = 0;
            int i4 = 0;
            while (i3 < length && i4 < i2 && str.charAt(i3) == charSequenceSubSequence.charAt(i4)) {
                i4++;
                i3++;
            }
            int i5 = length;
            while (i4 < i2 && i3 < i5 && str.charAt(i5 - 1) == charSequenceSubSequence.charAt(i2 - 1)) {
                i5--;
                i2--;
            }
            if (psiElement != null && (i3 > 0 || i5 < length)) {
                PsiElement psiElementFindElementAt = psiElement.findElementAt(i3);
                PsiElement psiElementFindElementAt2 = psiElement.findElementAt(i5 - 1);
                if (psiElementFindElementAt != null && psiElementFindElementAt2 != null) {
                    int startOffset = psiElementFindElementAt.getTextRange().getStartOffset() - psiElement.getTextRange().getStartOffset();
                    int endOffset = psiElementFindElementAt2.getTextRange().getEndOffset() - psiElement.getTextRange().getStartOffset();
                    i4 += startOffset - i3;
                    i2 += endOffset - i5;
                    i3 = startOffset;
                    i5 = endOffset;
                }
            }
            if (i3 == i5 && i4 > 0 && i4 < i2 && StringUtil.indexOf(charSequenceSubSequence, '\n', i4, i2) != -1) {
                while (i4 > 0 && i3 > 0) {
                    int i6 = i2 - 1;
                    if (charSequenceSubSequence.charAt(i4 - 1) != charSequenceSubSequence.charAt(i6) || charSequenceSubSequence.charAt(i6) == '\n') {
                        break;
                    }
                    i4--;
                    i2--;
                    i3--;
                    i5--;
                }
            }
            updateFragments(i4 + i, i2 + i, str.substring(i3, i5));
        }

        private void updateFragments(int i, int i2, String str) {
            if (str == null) {
                $$$reportNull$$$0(4);
            }
            int iPsiToDocumentOffset = psiToDocumentOffset(i);
            int iPsiToDocumentOffset2 = psiToDocumentOffset(i2);
            TextRange textRangeFindFragment = findFragment(iPsiToDocumentOffset);
            TextRange textRangeFindFragment2 = findFragment(iPsiToDocumentOffset2);
            this.myPsiText = this.myPsiText.replace(i, i2, str);
            if (textRangeFindFragment != null) {
                iPsiToDocumentOffset = textRangeFindFragment.getStartOffset();
            }
            if (textRangeFindFragment2 != null) {
                iPsiToDocumentOffset2 = textRangeFindFragment2.getEndOffset();
            }
            final TextRange textRange = new TextRange(iPsiToDocumentOffset, iPsiToDocumentOffset2);
            CharSequence charSequenceSubSequence = this.myPsiText.subSequence(documentToPsiOffset(textRange.getStartOffset(), false), (documentToPsiOffset(textRange.getEndOffset(), true) + str.length()) - (i2 - i));
            this.myAffectedFragments.keySet().removeIf(new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiToDocumentSynchronizer$DocumentChangeTransaction$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((TextRange) obj).intersects(textRange);
                }
            });
            this.myAffectedFragments.put(textRange, charSequenceSubSequence);
        }

        private TextRange findFragment(final int i) {
            return (TextRange) ContainerUtil.find(this.myAffectedFragments.keySet(), new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.PsiToDocumentSynchronizer$DocumentChangeTransaction$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return ((TextRange) obj).containsOffset(i);
                }
            });
        }

        private int psiToDocumentOffset(int i) {
            for (Map.Entry<TextRange, CharSequence> entry : this.myAffectedFragments.entrySet()) {
                int length = entry.getValue().length();
                TextRange key = entry.getKey();
                if (key.getStartOffset() + length < i) {
                    i += key.getLength() - length;
                } else {
                    return Math.min(key.getStartOffset(), i);
                }
            }
            return i;
        }

        private int documentToPsiOffset(int i, boolean z) {
            int length = 0;
            for (Map.Entry<TextRange, CharSequence> entry : this.myAffectedFragments.entrySet()) {
                int length2 = entry.getValue().length();
                TextRange key = entry.getKey();
                if (key.containsOffset(i)) {
                    return key.getStartOffset() + length + (z ? length2 : 0);
                }
                if (key.getStartOffset() > i) {
                    break;
                }
                length += length2 - key.getLength();
            }
            return i + length;
        }
    }
}
