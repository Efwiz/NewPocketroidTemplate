package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiNamedElement extends PsiElement {
    public static final PsiNamedElement[] EMPTY_ARRAY = new PsiNamedElement[0];

    String getName();

    PsiElement setName(String str) throws IncorrectOperationException;
}
