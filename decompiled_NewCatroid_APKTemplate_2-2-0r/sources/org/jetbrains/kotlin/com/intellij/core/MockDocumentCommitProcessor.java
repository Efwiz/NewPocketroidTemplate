package org.jetbrains.kotlin.com.intellij.core;

import org.jetbrains.kotlin.com.intellij.openapi.application.ModalityState;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.DocumentCommitProcessor;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiDocumentManagerBase;

/* JADX INFO: loaded from: classes3.dex */
class MockDocumentCommitProcessor implements DocumentCommitProcessor {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
            case 3:
                objArr[0] = "project";
                break;
            case 2:
                objArr[0] = "psiFile";
                break;
            case 4:
                objArr[0] = "documentManager";
                break;
            case 5:
            default:
                objArr[0] = "document";
                break;
            case 6:
                objArr[0] = "reason";
                break;
            case 7:
                objArr[0] = "modality";
                break;
            case 8:
                objArr[0] = "cachedViewProvider";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/core/MockDocumentCommitProcessor";
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                objArr[2] = "commitAsynchronously";
                break;
            default:
                objArr[2] = "commitSynchronously";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.DocumentCommitProcessor
    public void commitAsynchronously(Project project, PsiDocumentManagerBase psiDocumentManagerBase, Document document, Object obj, ModalityState modalityState, FileViewProvider fileViewProvider) {
        if (project == null) {
            $$$reportNull$$$0(3);
        }
        if (psiDocumentManagerBase == null) {
            $$$reportNull$$$0(4);
        }
        if (document == null) {
            $$$reportNull$$$0(5);
        }
        if (obj == null) {
            $$$reportNull$$$0(6);
        }
        if (modalityState == null) {
            $$$reportNull$$$0(7);
        }
        if (fileViewProvider == null) {
            $$$reportNull$$$0(8);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.DocumentCommitProcessor
    public void commitSynchronously(Document document, Project project, PsiFile psiFile) {
        if (document == null) {
            $$$reportNull$$$0(0);
        }
        if (project == null) {
            $$$reportNull$$$0(1);
        }
        if (psiFile == null) {
            $$$reportNull$$$0(2);
        }
    }

    MockDocumentCommitProcessor() {
    }
}
