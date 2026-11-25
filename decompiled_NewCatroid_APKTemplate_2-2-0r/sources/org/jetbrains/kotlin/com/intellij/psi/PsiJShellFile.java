package org.jetbrains.kotlin.com.intellij.psi;

import java.util.Collection;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiJShellFile extends PsiFile {
    Collection<PsiElement> getExecutableSnippets();

    PsiImportList getImportList();
}
