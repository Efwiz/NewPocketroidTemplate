package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.openapi.util.Segment;

/* JADX INFO: loaded from: classes3.dex */
public interface SmartPsiFileRange extends SmartPsiElementPointer<PsiFile> {
    @Override // org.jetbrains.kotlin.com.intellij.psi.SmartPsiElementPointer
    Segment getRange();
}
