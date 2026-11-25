package org.jetbrains.kotlin.com.intellij.psi.search;

import org.jetbrains.kotlin.com.intellij.psi.PsiFile;

/* JADX INFO: loaded from: classes3.dex */
@FunctionalInterface
public interface PsiNonJavaFileReferenceProcessor {
    boolean process(PsiFile psiFile, int i, int i2);
}
