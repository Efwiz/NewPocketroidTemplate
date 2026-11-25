package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiClassOwner extends PsiFile {
    PsiClass[] getClasses();

    String getPackageName();

    void setPackageName(String str) throws IncorrectOperationException;
}
