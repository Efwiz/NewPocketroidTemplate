package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiCheckedRenameElement extends PsiNamedElement {
    void checkSetName(String str) throws IncorrectOperationException;
}
