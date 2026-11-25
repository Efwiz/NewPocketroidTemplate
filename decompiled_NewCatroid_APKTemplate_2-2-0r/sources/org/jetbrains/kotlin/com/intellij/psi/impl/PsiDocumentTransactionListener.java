package org.jetbrains.kotlin.com.intellij.psi.impl;

import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.util.messages.Topic;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiDocumentTransactionListener {
    public static final Topic<PsiDocumentTransactionListener> TOPIC = new Topic<>("psi.DocumentTransactionListener", PsiDocumentTransactionListener.class, Topic.BroadcastDirection.TO_PARENT);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "document";
        } else {
            objArr[0] = "file";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiDocumentTransactionListener";
        objArr[2] = "transactionCompleted";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    default void transactionCompleted(Document document, PsiFile psiFile) {
        if (document == null) {
            $$$reportNull$$$0(0);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(1);
        }
    }

    void transactionStarted(Document document, PsiFile psiFile);
}
