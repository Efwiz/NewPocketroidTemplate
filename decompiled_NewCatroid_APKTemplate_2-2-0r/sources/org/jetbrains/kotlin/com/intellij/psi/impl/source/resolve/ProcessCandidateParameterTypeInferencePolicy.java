package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve;

import java.util.Arrays;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.psi.ConstraintType;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.scope.MethodProcessorSetupFailedException;
import org.jetbrains.kotlin.com.intellij.psi.scope.processor.MethodCandidatesProcessor;
import org.jetbrains.kotlin.com.intellij.psi.scope.util.PsiScopesUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;

/* JADX INFO: loaded from: classes3.dex */
public class ProcessCandidateParameterTypeInferencePolicy extends DefaultParameterTypeInferencePolicy {
    public static final ProcessCandidateParameterTypeInferencePolicy INSTANCE = new ProcessCandidateParameterTypeInferencePolicy();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i != 2) {
            objArr[0] = "contextCall";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/ProcessCandidateParameterTypeInferencePolicy";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/ProcessCandidateParameterTypeInferencePolicy";
        } else {
            objArr[1] = "getResults";
        }
        if (i == 1) {
            objArr[2] = "getResults";
        } else if (i != 2) {
            objArr[2] = "inferTypeConstraintFromCallContext";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.DefaultParameterTypeInferencePolicy, org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ParameterTypeInferencePolicy
    public Pair<PsiType, ConstraintType> inferTypeConstraintFromCallContext(PsiExpression psiExpression, PsiExpressionList psiExpressionList, PsiCallExpression psiCallExpression, PsiTypeParameter psiTypeParameter) {
        PsiMethod psiMethod;
        Pair<PsiType, ConstraintType> pairInferConstraint;
        if (psiCallExpression == null) {
            $$$reportNull$$$0(0);
        }
        PsiExpression[] expressions = psiExpressionList.getExpressions();
        PsiElement lightParent = psiExpression;
        while (lightParent.getLightParent() instanceof PsiParenthesizedExpression) {
            lightParent = lightParent.getLightParent();
        }
        int iFind = ArrayUtilRt.find(expressions, lightParent);
        if (iFind < 0 || (psiMethod = (PsiMethod) psiTypeParameter.getOwner()) == null) {
            return null;
        }
        try {
            JavaResolveResult[] results = getResults(psiCallExpression, iFind);
            PsiType returnType = psiMethod.getReturnType();
            for (JavaResolveResult javaResolveResult : results) {
                if (javaResolveResult != null && (pairInferConstraint = inferConstraint(psiTypeParameter, psiExpression, iFind, returnType, javaResolveResult, getSubstitutor(psiCallExpression, expressions, iFind, javaResolveResult))) != null) {
                    return pairInferConstraint;
                }
            }
        } catch (MethodProcessorSetupFailedException unused) {
        }
        return null;
    }

    protected PsiSubstitutor getSubstitutor(PsiCallExpression psiCallExpression, PsiExpression[] psiExpressionArr, int i, JavaResolveResult javaResolveResult) {
        if (javaResolveResult instanceof MethodCandidateInfo) {
            return ((MethodCandidateInfo) javaResolveResult).inferSubstitutorFromArgs(this, (PsiExpression[]) getExpressions(psiExpressionArr, i).toArray(PsiExpression.EMPTY_ARRAY));
        }
        return javaResolveResult.getSubstitutor();
    }

    protected List<PsiExpression> getExpressions(PsiExpression[] psiExpressionArr, int i) {
        return Arrays.asList(psiExpressionArr).subList(0, i);
    }

    protected static Pair<PsiType, ConstraintType> inferConstraint(PsiTypeParameter psiTypeParameter, PsiExpression psiExpression, int i, PsiType psiType, JavaResolveResult javaResolveResult, final PsiSubstitutor psiSubstitutor) {
        final PsiParameter psiParameter;
        PsiElement element = javaResolveResult.getElement();
        if (element instanceof PsiMethod) {
            PsiMethod psiMethod = (PsiMethod) element;
            PsiParameter[] parameters = psiMethod.getParameterList().getParameters();
            if (parameters.length > i) {
                psiParameter = parameters[i];
            } else {
                psiParameter = psiMethod.isVarArgs() ? parameters[parameters.length - 1] : null;
            }
            if (psiParameter != null) {
                Pair<PsiType, ConstraintType> substitutionForTypeParameterConstraint = new PsiOldInferenceHelper(element.getManager()).getSubstitutionForTypeParameterConstraint(psiTypeParameter, psiType, (PsiType) PsiResolveHelper.ourGuard.doPreventingRecursion(psiExpression, true, new Computable() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ProcessCandidateParameterTypeInferencePolicy$$ExternalSyntheticLambda0
                    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
                    public final Object compute() {
                        return psiSubstitutor.substitute(psiParameter.getType());
                    }
                }), false, PsiUtil.getLanguageLevel(psiParameter));
                if (substitutionForTypeParameterConstraint != null) {
                    return substitutionForTypeParameterConstraint;
                }
            }
        }
        return null;
    }

    protected JavaResolveResult[] getResults(PsiCallExpression psiCallExpression, int i) throws MethodProcessorSetupFailedException {
        if (psiCallExpression == null) {
            $$$reportNull$$$0(1);
        }
        MethodCandidatesProcessor methodCandidatesProcessor = new MethodCandidatesProcessor(psiCallExpression, psiCallExpression.getContainingFile());
        PsiScopesUtil.setupAndRunProcessor(methodCandidatesProcessor, psiCallExpression, false);
        JavaResolveResult[] result = methodCandidatesProcessor.getResult();
        if (result == null) {
            $$$reportNull$$$0(2);
        }
        return result;
    }
}
