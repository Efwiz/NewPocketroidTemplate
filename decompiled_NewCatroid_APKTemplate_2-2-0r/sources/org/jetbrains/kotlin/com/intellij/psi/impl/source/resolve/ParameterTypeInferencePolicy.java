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
public abstract class ParameterTypeInferencePolicy {
    public abstract PsiType adjustInferredType(PsiManager psiManager, PsiType psiType, ConstraintType constraintType);

    public abstract PsiType getDefaultExpectedType(PsiCallExpression psiCallExpression);

    public abstract Pair<PsiType, ConstraintType> getInferredTypeWithNoConstraint(PsiManager psiManager, PsiType psiType);

    public boolean inferLowerBoundForFreshVariables() {
        return false;
    }

    public boolean inferRuntimeExceptionForThrownBoundWithNoConstraints() {
        return true;
    }

    public abstract Pair<PsiType, ConstraintType> inferTypeConstraintFromCallContext(PsiExpression psiExpression, PsiExpressionList psiExpressionList, PsiCallExpression psiCallExpression, PsiTypeParameter psiTypeParameter);

    public boolean isVarargsIgnored() {
        return false;
    }

    public boolean requestForBoxingExplicitly() {
        return false;
    }
}
