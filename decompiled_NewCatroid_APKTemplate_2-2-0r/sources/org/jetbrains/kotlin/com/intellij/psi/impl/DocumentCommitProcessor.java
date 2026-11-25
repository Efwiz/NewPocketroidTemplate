package org.jetbrains.kotlin.com.intellij.psi.impl;

import org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;

/* JADX INFO: loaded from: classes3.dex */
public interface DocumentCommitProcessor {
    void commitAsynchronously(Project project, PsiDocumentManagerBase psiDocumentManagerBase, Document document, Object obj, ModalityState modalityState, FileViewProvider fileViewProvider);

    void commitSynchronously(Document document, Project project, PsiFile psiFile);
}
