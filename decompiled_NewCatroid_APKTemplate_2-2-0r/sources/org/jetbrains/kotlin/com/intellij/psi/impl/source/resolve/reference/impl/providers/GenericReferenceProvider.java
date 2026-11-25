package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.impl.providers;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceProvider;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.util.PsiScopesUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class GenericReferenceProvider extends PsiReferenceProvider {
    private boolean mySoft;

    public void handleEmptyContext(PsiScopeProcessor psiScopeProcessor, PsiElement psiElement) {
        PsiScopesUtil.treeWalkUp(psiScopeProcessor, psiElement, null);
    }

    public void setSoft(boolean z) {
        this.mySoft = z;
    }

    public boolean isSoft() {
        return this.mySoft;
    }
}
