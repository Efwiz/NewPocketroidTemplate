package org.jetbrains.kotlin.com.intellij.psi.filters;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ElementFilterBase<T> implements ElementFilter {
    private final Class<T> myClass;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "aClass", "org/jetbrains/kotlin/com/intellij/psi/filters/ElementFilterBase", "<init>"));
    }

    protected abstract boolean isElementAcceptable(T t, PsiElement psiElement);

    public ElementFilterBase(Class<T> cls) {
        if (cls == null) {
            $$$reportNull$$$0(0);
        }
        this.myClass = cls;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public final boolean isAcceptable(Object obj, PsiElement psiElement) {
        return isClassAcceptable(obj.getClass()) && isElementAcceptable(obj, psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public boolean isClassAcceptable(Class cls) {
        return this.myClass.isAssignableFrom(cls);
    }
}
