package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve;

import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.psi.ConstraintType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;

/* JADX INFO: loaded from: classes3.dex */
public class DefaultParameterTypeInferencePolicy extends ParameterTypeInferencePolicy {
    public static final DefaultParameterTypeInferencePolicy INSTANCE = new DefaultParameterTypeInferencePolicy();

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ParameterTypeInferencePolicy
    public PsiType adjustInferredType(PsiManager psiManager, PsiType psiType, ConstraintType constraintType) {
        return psiType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ParameterTypeInferencePolicy
    public Pair<PsiType, ConstraintType> inferTypeConstraintFromCallContext(PsiExpression psiExpression, PsiExpressionList psiExpressionList, PsiCallExpression psiCallExpression, PsiTypeParameter psiTypeParameter) {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ParameterTypeInferencePolicy
    public PsiType getDefaultExpectedType(PsiCallExpression psiCallExpression) {
        return PsiType.getJavaLangObject(psiCallExpression.getManager(), psiCallExpression.getResolveScope());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ParameterTypeInferencePolicy
    public Pair<PsiType, ConstraintType> getInferredTypeWithNoConstraint(PsiManager psiManager, PsiType psiType) {
        return Pair.create(psiType, ConstraintType.SUBTYPE);
    }
}
