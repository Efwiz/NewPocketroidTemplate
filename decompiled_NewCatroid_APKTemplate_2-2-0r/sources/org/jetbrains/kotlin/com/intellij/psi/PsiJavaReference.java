package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiJavaReference extends PsiPolyVariantReference {
    JavaResolveResult advancedResolve(boolean z);

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiPolyVariantReference
    JavaResolveResult[] multiResolve(boolean z);

    void processVariants(PsiScopeProcessor psiScopeProcessor);
}
