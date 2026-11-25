package org.jetbrains.kotlin.com.intellij.psi.filters;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public class NotFilter implements ElementFilter {
    private final ElementFilter myFilter;

    public NotFilter(ElementFilter elementFilter) {
        this.myFilter = elementFilter;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public boolean isClassAcceptable(Class cls) {
        return this.myFilter.isClassAcceptable(cls);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public boolean isAcceptable(Object obj, PsiElement psiElement) {
        return !this.myFilter.isAcceptable(obj, psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public String toString() {
        return "!" + this.myFilter;
    }
}
