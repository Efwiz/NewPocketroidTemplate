package org.jetbrains.kotlin.com.intellij.psi.filters;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: loaded from: classes3.dex */
public class ConstructorFilter extends ClassFilter {
    public ConstructorFilter() {
        super(PsiMethod.class);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ClassFilter, org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public boolean isAcceptable(Object obj, PsiElement psiElement) {
        return (obj instanceof PsiMethod) && ((PsiMethod) obj).isConstructor();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ClassFilter, org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public String toString() {
        return JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME;
    }
}
