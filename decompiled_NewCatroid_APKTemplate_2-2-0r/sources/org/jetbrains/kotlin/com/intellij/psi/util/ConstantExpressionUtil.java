package org.jetbrains.kotlin.com.intellij.psi.util;

import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;

/* JADX INFO: loaded from: classes3.dex */
public final class ConstantExpressionUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "operand", "org/jetbrains/kotlin/com/intellij/psi/util/ConstantExpressionUtil", "computeCastTo"));
    }

    public static Object computeCastTo(PsiExpression psiExpression, PsiType psiType) {
        Object objComputeConstantExpression;
        if (psiExpression == null || (objComputeConstantExpression = JavaPsiFacade.getInstance(psiExpression.getProject()).getConstantEvaluationHelper().computeConstantExpression(psiExpression, false)) == null) {
            return null;
        }
        return computeCastTo(objComputeConstantExpression, psiType);
    }

    public static Object computeCastTo(Object obj, PsiType psiType) {
        if (obj == null) {
            $$$reportNull$$$0(0);
        }
        return TypeConversionUtil.computeCastTo(obj, psiType);
    }
}
