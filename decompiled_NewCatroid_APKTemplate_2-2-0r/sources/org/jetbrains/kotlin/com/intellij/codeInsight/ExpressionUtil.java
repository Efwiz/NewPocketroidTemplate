package org.jetbrains.kotlin.com.intellij.codeInsight;

import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiSuperExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiThisExpression;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class ExpressionUtil {
    public static boolean isEffectivelyUnqualified(PsiReferenceExpression psiReferenceExpression) {
        PsiExpression psiExpressionDeparenthesizeExpression = PsiUtil.deparenthesizeExpression(psiReferenceExpression.getQualifierExpression());
        if (psiExpressionDeparenthesizeExpression == null) {
            return true;
        }
        if (!(psiExpressionDeparenthesizeExpression instanceof PsiThisExpression) && !(psiExpressionDeparenthesizeExpression instanceof PsiSuperExpression)) {
            return false;
        }
        PsiJavaCodeReferenceElement qualifier = ((PsiQualifiedExpression) psiExpressionDeparenthesizeExpression).getQualifier();
        return qualifier == null || ((PsiClass) PsiTreeUtil.getParentOfType(psiReferenceExpression, PsiClass.class)) == qualifier.resolve();
    }
}
