package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public final class EmptyResolveResult implements ResolveResult {
    public static final ResolveResult INSTANCE = new EmptyResolveResult();

    @Override // org.jetbrains.kotlin.com.intellij.psi.ResolveResult
    public PsiElement getElement() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.ResolveResult
    public boolean isValidResult() {
        return false;
    }

    private EmptyResolveResult() {
    }
}
