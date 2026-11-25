package org.jetbrains.kotlin.com.intellij.psi.util;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
@FunctionalInterface
public interface PsiElementFilter {
    boolean isAccepted(PsiElement psiElement);
}
