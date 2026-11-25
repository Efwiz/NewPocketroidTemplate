package org.jetbrains.kotlin.com.intellij.psi;

import java.util.concurrent.ConcurrentMap;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiConstantEvaluationHelper {

    public interface AuxEvaluator {
        Object computeExpression(PsiExpression psiExpression, AuxEvaluator auxEvaluator);

        ConcurrentMap<PsiElement, Object> getCacheMap(boolean z);
    }

    public abstract Object computeConstantExpression(PsiElement psiElement, boolean z);

    public abstract Object computeExpression(PsiExpression psiExpression, boolean z, AuxEvaluator auxEvaluator);

    public Object computeConstantExpression(PsiElement psiElement) {
        return computeConstantExpression(psiElement, false);
    }
}
