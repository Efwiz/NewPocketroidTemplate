package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface ResolveResult {
    public static final ResolveResult[] EMPTY_ARRAY = new ResolveResult[0];

    PsiElement getElement();

    boolean isValidResult();
}
