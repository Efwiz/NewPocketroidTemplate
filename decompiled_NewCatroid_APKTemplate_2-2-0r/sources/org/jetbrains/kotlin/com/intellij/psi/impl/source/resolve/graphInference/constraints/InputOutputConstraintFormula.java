package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.kotlin.com.intellij.psi.LambdaUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiConditionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiFunctionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.FunctionalInterfaceParameterizationUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceVariable;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
public abstract class InputOutputConstraintFormula implements ConstraintFormula {
    private PsiType myT;

    protected abstract void collectReturnTypeVariables(InferenceSession inferenceSession, PsiExpression psiExpression, PsiType psiType, Set<? super InferenceVariable> set);

    protected abstract InputOutputConstraintFormula createSelfConstraint(PsiType psiType, PsiExpression psiExpression);

    public abstract PsiExpression getExpression();

    protected InputOutputConstraintFormula(PsiType psiType) {
        this.myT = psiType;
    }

    public Set<InferenceVariable> getInputVariables(final InferenceSession inferenceSession) {
        PsiExpression expression = getExpression();
        final PsiType psiType = this.myT;
        if (expression instanceof PsiFunctionalExpression) {
            InferenceVariable inferenceVariable = inferenceSession.getInferenceVariable(psiType);
            if (inferenceVariable != null) {
                HashSet hashSet = new HashSet();
                hashSet.add(inferenceVariable);
                return hashSet;
            }
            if (LambdaUtil.isFunctionalType(psiType)) {
                boolean z = expression instanceof PsiLambdaExpression;
                PsiClassType.ClassResolveResult classResolveResultResolveGenericsClassInType = PsiUtil.resolveGenericsClassInType(z ? FunctionalInterfaceParameterizationUtil.getGroundTargetType(psiType, (PsiLambdaExpression) expression, false) : psiType);
                PsiMethod functionalInterfaceMethod = LambdaUtil.getFunctionalInterfaceMethod(classResolveResultResolveGenericsClassInType);
                if (functionalInterfaceMethod != null) {
                    HashSet hashSet2 = new HashSet();
                    PsiSubstitutor substitutor = LambdaUtil.getSubstitutor(functionalInterfaceMethod, classResolveResultResolveGenericsClassInType);
                    if ((z && !((PsiLambdaExpression) expression).hasFormalParameterTypes()) || ((expression instanceof PsiMethodReferenceExpression) && !((PsiMethodReferenceExpression) expression).isExact())) {
                        for (PsiParameter psiParameter : functionalInterfaceMethod.getParameterList().getParameters()) {
                            inferenceSession.collectDependencies(substitutor.substitute(psiParameter.getType()), hashSet2);
                        }
                    }
                    PsiType returnType = functionalInterfaceMethod.getReturnType();
                    if (returnType != null) {
                        collectReturnTypeVariables(inferenceSession, expression, substitutor.substitute(returnType), hashSet2);
                    }
                    return hashSet2;
                }
            }
        }
        if (expression instanceof PsiParenthesizedExpression) {
            PsiExpression expression2 = ((PsiParenthesizedExpression) expression).getExpression();
            if (expression2 != null) {
                return createSelfConstraint(psiType, expression2).getInputVariables(inferenceSession);
            }
            return null;
        }
        if (expression instanceof PsiConditionalExpression) {
            PsiConditionalExpression psiConditionalExpression = (PsiConditionalExpression) expression;
            PsiExpression thenExpression = psiConditionalExpression.getThenExpression();
            PsiExpression elseExpression = psiConditionalExpression.getElseExpression();
            Set<InferenceVariable> inputVariables = thenExpression != null ? createSelfConstraint(psiType, thenExpression).getInputVariables(inferenceSession) : null;
            Set<InferenceVariable> inputVariables2 = elseExpression != null ? createSelfConstraint(psiType, elseExpression).getInputVariables(inferenceSession) : null;
            if (inputVariables == null) {
                return inputVariables2;
            }
            if (inputVariables2 == null) {
                return inputVariables;
            }
            inputVariables.addAll(inputVariables2);
            return inputVariables;
        }
        if (!(expression instanceof PsiSwitchExpression)) {
            return null;
        }
        Set<InferenceVariable> set = (Set) PsiUtil.getSwitchResultExpressions((PsiSwitchExpression) expression).stream().flatMap(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.InputOutputConstraintFormula$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m6668x9b25c53a(psiType, inferenceSession, (PsiExpression) obj);
            }
        }).collect(Collectors.toSet());
        if (set.isEmpty()) {
            return null;
        }
        return set;
    }

    /* JADX INFO: renamed from: lambda$getInputVariables$0$org-jetbrains-kotlin-com-intellij-psi-impl-source-resolve-graphInference-constraints-InputOutputConstraintFormula, reason: not valid java name */
    /* synthetic */ Stream m6668x9b25c53a(PsiType psiType, InferenceSession inferenceSession, PsiExpression psiExpression) {
        Set<InferenceVariable> inputVariables = createSelfConstraint(psiType, psiExpression).getInputVariables(inferenceSession);
        return inputVariables != null ? inputVariables.stream() : Stream.empty();
    }

    public Set<InferenceVariable> getOutputVariables(Set<InferenceVariable> set, InferenceSession inferenceSession) {
        HashSet hashSet = new HashSet();
        inferenceSession.collectDependencies(this.myT, hashSet);
        if (set != null) {
            hashSet.removeAll(set);
        }
        if (hashSet.isEmpty()) {
            return null;
        }
        return hashSet;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.ConstraintFormula
    public void apply(PsiSubstitutor psiSubstitutor, boolean z) {
        this.myT = psiSubstitutor.substitute(this.myT);
    }

    public PsiType getCurrentType() {
        return this.myT;
    }

    public String toString() {
        return getExpression().getText() + " -> " + this.myT.getPresentableText();
    }
}
