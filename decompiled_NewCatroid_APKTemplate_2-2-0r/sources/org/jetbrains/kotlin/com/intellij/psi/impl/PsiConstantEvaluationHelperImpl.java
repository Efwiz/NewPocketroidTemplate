package org.jetbrains.kotlin.com.intellij.psi.impl;

import java.util.Set;
import org.jetbrains.kotlin.com.intellij.psi.PsiConstantEvaluationHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.util.ConstantExpressionUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiConstantEvaluationHelperImpl extends PsiConstantEvaluationHelper {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 2) {
            objArr[0] = "expression";
        } else {
            objArr[0] = "castTo";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/PsiConstantEvaluationHelperImpl";
        if (i == 1 || i == 2) {
            objArr[2] = "computeCastTo";
        } else {
            objArr[2] = "computeExpression";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiConstantEvaluationHelper
    public Object computeConstantExpression(PsiElement psiElement) {
        return computeConstantExpression(psiElement, false);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiConstantEvaluationHelper
    public Object computeConstantExpression(PsiElement psiElement, boolean z) {
        if (psiElement == null) {
            return null;
        }
        return LanguageConstantExpressionEvaluator.INSTANCE.forLanguage(psiElement.getLanguage()).computeConstantExpression(psiElement, z);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiConstantEvaluationHelper
    public Object computeExpression(PsiExpression psiExpression, boolean z, PsiConstantEvaluationHelper.AuxEvaluator auxEvaluator) {
        if (psiExpression == null) {
            $$$reportNull$$$0(0);
        }
        return LanguageConstantExpressionEvaluator.INSTANCE.forLanguage(psiExpression.getLanguage()).computeExpression(psiExpression, z, auxEvaluator);
    }

    public static Object computeCastTo(PsiExpression psiExpression, PsiType psiType, Set<PsiVariable> set) {
        if (psiExpression == null) {
            $$$reportNull$$$0(1);
        }
        if (psiType == null) {
            $$$reportNull$$$0(2);
        }
        Object objComputeConstantExpression = JavaConstantExpressionEvaluator.computeConstantExpression(psiExpression, set, false);
        if (objComputeConstantExpression == null) {
            return null;
        }
        return ConstantExpressionUtil.computeCastTo(objComputeConstantExpression, psiType);
    }
}
