package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public interface ElementManipulator<T extends PsiElement> {
    TextRange getRangeInElement(T t);

    T handleContentChange(T t, String str) throws IncorrectOperationException;

    T handleContentChange(T t, TextRange textRange, String str) throws IncorrectOperationException;
}
