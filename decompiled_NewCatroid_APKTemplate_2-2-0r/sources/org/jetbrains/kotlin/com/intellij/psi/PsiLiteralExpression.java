package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiLiteralExpression extends PsiExpression, PsiLiteral {
    default boolean isTextBlock() {
        return false;
    }
}
