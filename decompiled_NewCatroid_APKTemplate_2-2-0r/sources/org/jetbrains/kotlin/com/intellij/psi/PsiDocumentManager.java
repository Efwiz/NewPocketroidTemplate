package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Collection;
import java.util.EventListener;
import org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiDocumentManager {

    public interface Listener extends EventListener {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "file";
            } else {
                objArr[0] = "document";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiDocumentManager$Listener";
            objArr[2] = "fileCreated";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        void documentCreated(Document document, PsiFile psiFile);

        default void fileCreated(PsiFile psiFile, Document document) {
            if (psiFile == null) {
                $$$reportNull$$$0(0);
            }
            if (document == null) {
                $$$reportNull$$$0(1);
            }
        }
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "project", "org/jetbrains/kotlin/com/intellij/psi/PsiDocumentManager", "getInstance"));
    }

    @Deprecated
    public abstract void addListener(Listener listener);

    public abstract void commitAllDocuments();

    public abstract boolean commitAllDocumentsUnderProgress();

    public abstract <T> T commitAndRunReadAction(Computable<T> computable);

    public abstract void commitAndRunReadAction(Runnable runnable);

    public abstract void commitDocument(Document document);

    public abstract void doPostponedOperationsAndUnblockDocument(Document document);

    public abstract Document getCachedDocument(PsiFile psiFile);

    public abstract PsiFile getCachedPsiFile(Document document);

    public abstract Document getDocument(PsiFile psiFile);

    public abstract Document getLastCommittedDocument(PsiFile psiFile);

    public abstract long getLastCommittedStamp(Document document);

    public abstract CharSequence getLastCommittedText(Document document);

    public abstract PsiFile getPsiFile(Document document);

    public abstract Document[] getUncommittedDocuments();

    public abstract boolean hasUncommitedDocuments();

    public abstract boolean isCommitted(Document document);

    public abstract boolean isDocumentBlockedByPsi(Document document);

    public abstract boolean isUncommited(Document document);

    public abstract void performForCommittedDocument(Document document, Runnable runnable);

    public abstract void performLaterWhenAllCommitted(Runnable runnable);

    public abstract void performLaterWhenAllCommitted(ModalityState modalityState, Runnable runnable);

    public abstract boolean performWhenAllCommitted(Runnable runnable);

    public abstract void reparseFiles(Collection<? extends VirtualFile> collection, boolean z);

    public static PsiDocumentManager getInstance(Project project) {
        if (project == null) {
            $$$reportNull$$$0(0);
        }
        return (PsiDocumentManager) project.getService(PsiDocumentManager.class);
    }

    public boolean hasEventSystemEnabledUncommittedDocuments() {
        return hasUncommitedDocuments();
    }
}
