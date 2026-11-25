package org.jetbrains.kotlin.resolve.lazy;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;

/* JADX INFO: loaded from: classes6.dex */
public interface DeclarationScopeProvider {
    DataFlowInfo getOuterDataFlowInfoForDeclaration(PsiElement psiElement);

    LexicalScope getResolutionScopeForDeclaration(PsiElement psiElement);
}
