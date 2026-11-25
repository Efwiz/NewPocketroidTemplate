package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Segment;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public interface SmartPsiElementPointer<E extends PsiElement> {
    PsiFile getContainingFile();

    E getElement();

    Project getProject();

    Segment getPsiRange();

    Segment getRange();

    VirtualFile getVirtualFile();

    default E dereference() {
        return (E) getElement();
    }
}
