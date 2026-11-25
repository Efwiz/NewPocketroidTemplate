package org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.SmartPsiElementPointer;

/* JADX INFO: loaded from: classes3.dex */
public interface SmartPointerEx<E extends PsiElement> extends SmartPsiElementPointer<E> {
    PsiElement getCachedElement();
}
