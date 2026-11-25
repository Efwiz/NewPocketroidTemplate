package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiUsesStatement extends PsiStatement {
    public static final PsiUsesStatement[] EMPTY_ARRAY = new PsiUsesStatement[0];

    PsiJavaCodeReferenceElement getClassReference();

    PsiClassType getClassType();
}
