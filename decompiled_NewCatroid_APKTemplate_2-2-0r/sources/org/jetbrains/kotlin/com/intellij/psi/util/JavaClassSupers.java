package org.jetbrains.kotlin.com.intellij.psi.util;

import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JavaClassSupers {
    public abstract PsiSubstitutor getSuperClassSubstitutor(PsiClass psiClass, PsiClass psiClass2, GlobalSearchScope globalSearchScope, PsiSubstitutor psiSubstitutor);

    public abstract void reportHierarchyInconsistency(PsiClass psiClass, PsiClass psiClass2);

    public static JavaClassSupers getInstance() {
        return (JavaClassSupers) ApplicationManager.getApplication().getService(JavaClassSupers.class);
    }
}
