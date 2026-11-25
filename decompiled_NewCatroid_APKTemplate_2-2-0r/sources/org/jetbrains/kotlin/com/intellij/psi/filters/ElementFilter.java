package org.jetbrains.kotlin.com.intellij.psi.filters;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public interface ElementFilter {
    public static final ElementFilter[] EMPTY_ARRAY = new ElementFilter[0];

    boolean isAcceptable(Object obj, PsiElement psiElement);

    boolean isClassAcceptable(Class cls);

    String toString();
}
