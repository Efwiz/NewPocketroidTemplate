package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.util.messages.Topic;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiDocumentListener {
    public static final Topic<PsiDocumentListener> TOPIC = new Topic<>(PsiDocumentListener.class, Topic.BroadcastDirection.NONE);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "file";
        } else {
            objArr[0] = "document";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiDocumentListener";
        objArr[2] = "fileCreated";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    void documentCreated(Document document, PsiFile psiFile, Project project);

    default void fileCreated(PsiFile psiFile, Document document) {
        if (psiFile == null) {
            $$$reportNull$$$0(0);
        }
        if (document == null) {
            $$$reportNull$$$0(1);
        }
    }
}
