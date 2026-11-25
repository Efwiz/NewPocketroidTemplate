package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiCodeBlock extends PsiElement, PsiModifiableCodeBlock {
    public static final PsiCodeBlock[] EMPTY_ARRAY = new PsiCodeBlock[0];

    PsiElement getFirstBodyElement();

    PsiJavaToken getLBrace();

    PsiElement getLastBodyElement();

    PsiJavaToken getRBrace();

    PsiStatement[] getStatements();

    default int getStatementCount() {
        return getStatements().length;
    }

    default boolean isEmpty() {
        return getStatementCount() == 0;
    }
}
