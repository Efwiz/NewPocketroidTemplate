package org.jetbrains.kotlin.com.intellij.psi.filters.position;

import org.jetbrains.kotlin.com.intellij.patterns.ElementPattern;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter;

/* JADX INFO: loaded from: classes3.dex */
public class PatternFilter implements ElementFilter {
    private final ElementPattern<?> myPattern;

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public boolean isClassAcceptable(Class cls) {
        return true;
    }

    public PatternFilter(ElementPattern<?> elementPattern) {
        this.myPattern = elementPattern;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public boolean isAcceptable(Object obj, PsiElement psiElement) {
        return this.myPattern.accepts(obj);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public String toString() {
        return this.myPattern.toString();
    }
}
