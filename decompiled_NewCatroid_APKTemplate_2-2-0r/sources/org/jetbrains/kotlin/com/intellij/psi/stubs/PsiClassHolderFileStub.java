package org.jetbrains.kotlin.com.intellij.psi.stubs;

import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiClassHolderFileStub<T extends PsiFile> extends PsiFileStub<T> {
    PsiClass[] getClasses();
}
