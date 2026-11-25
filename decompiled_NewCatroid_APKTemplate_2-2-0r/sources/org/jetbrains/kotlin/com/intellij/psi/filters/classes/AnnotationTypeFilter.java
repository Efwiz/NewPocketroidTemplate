package org.jetbrains.kotlin.com.intellij.psi.filters.classes;

import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter;
import org.jetbrains.kotlin.com.intellij.util.ReflectionUtil;

/* JADX INFO: loaded from: classes3.dex */
public class AnnotationTypeFilter implements ElementFilter {
    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public boolean isClassAcceptable(Class cls) {
        return ReflectionUtil.isAssignable(PsiClass.class, cls);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public boolean isAcceptable(Object obj, PsiElement psiElement) {
        return (obj instanceof PsiClass) && ((PsiClass) obj).isAnnotationType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public String toString() {
        return "annotationType";
    }
}
