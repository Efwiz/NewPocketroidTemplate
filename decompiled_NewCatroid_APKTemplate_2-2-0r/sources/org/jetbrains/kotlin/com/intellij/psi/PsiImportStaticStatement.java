package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiImportStaticStatement extends PsiImportStatementBase {
    public static final PsiImportStaticStatement[] EMPTY_ARRAY = new PsiImportStaticStatement[0];

    String getReferenceName();

    PsiClass resolveTargetClass();
}
