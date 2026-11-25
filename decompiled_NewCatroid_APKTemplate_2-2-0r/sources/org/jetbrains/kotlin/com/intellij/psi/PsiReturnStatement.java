package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiReturnStatement extends PsiStatement {
    public static final PsiReturnStatement[] EMPTY_ARRAY = new PsiReturnStatement[0];

    PsiExpression getReturnValue();
}
