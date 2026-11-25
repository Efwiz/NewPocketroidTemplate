package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface HintedReferenceHost extends PsiElement {
    PsiReference[] getReferences(PsiReferenceService.Hints hints);

    boolean shouldAskParentForReferences(PsiReferenceService.Hints hints);
}
