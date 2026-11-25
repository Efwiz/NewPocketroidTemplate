package org.jetbrains.kotlin.com.intellij.psi;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiYieldStatement extends PsiStatement {
    PsiSwitchExpression findEnclosingExpression();

    PsiExpression getExpression();
}
