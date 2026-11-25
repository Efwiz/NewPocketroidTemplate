package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.psi.LambdaUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.FunctionalInterfaceParameterizationUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceVariable;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.PsiPolyExpressionUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;

/* JADX INFO: loaded from: classes3.dex */
public class LambdaExpressionCompatibilityConstraint implements ConstraintFormula {
    private final PsiLambdaExpression myExpression;
    private PsiType myT;

    public LambdaExpressionCompatibilityConstraint(PsiLambdaExpression psiLambdaExpression, PsiType psiType) {
        this.myExpression = psiLambdaExpression;
        this.myT = psiType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.ConstraintFormula
    public boolean reduce(InferenceSession inferenceSession, List<? super ConstraintFormula> list) {
        if (!LambdaUtil.isFunctionalType(this.myT)) {
            inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.not.a.functional.interface", inferenceSession.getPresentableText(this.myT)));
            return false;
        }
        PsiType groundTargetType = FunctionalInterfaceParameterizationUtil.getGroundTargetType(this.myT, this.myExpression, false);
        PsiClassType.ClassResolveResult classResolveResultResolveGenericsClassInType = PsiUtil.resolveGenericsClassInType(groundTargetType);
        PsiMethod functionalInterfaceMethod = LambdaUtil.getFunctionalInterfaceMethod(classResolveResultResolveGenericsClassInType);
        if (functionalInterfaceMethod == null) {
            inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.no.valid.function.type.found", inferenceSession.getPresentableText(this.myT)));
            return false;
        }
        PsiSubstitutor substitutor = LambdaUtil.getSubstitutor(functionalInterfaceMethod, classResolveResultResolveGenericsClassInType);
        PsiParameter[] parameters = functionalInterfaceMethod.getParameterList().getParameters();
        PsiParameter[] parameters2 = this.myExpression.getParameterList().getParameters();
        if (parameters2.length != parameters.length) {
            inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.incompatible.parameter.types.in.lambda", new Object[0]));
            return false;
        }
        if (this.myExpression.hasFormalParameterTypes()) {
            for (int i = 0; i < parameters2.length; i++) {
                list.add(new TypeEqualityConstraint(parameters2[i].getType(), inferenceSession.substituteWithInferenceVariables(substitutor.substitute(parameters[i].getType()))));
            }
            list.add(new StrictSubtypingConstraint(this.myT, groundTargetType));
        } else {
            for (PsiParameter psiParameter : parameters) {
                if (!inferenceSession.isProperType(inferenceSession.substituteWithInferenceVariables(substitutor.substitute(psiParameter.getType())))) {
                    return false;
                }
            }
        }
        PsiType returnType = functionalInterfaceMethod.getReturnType();
        if (returnType == null) {
            return true;
        }
        List<PsiExpression> returnExpressions = LambdaUtil.getReturnExpressions(this.myExpression);
        PsiElement body = this.myExpression.getBody();
        if (returnType.equals(PsiType.VOID)) {
            if (((body instanceof PsiCodeBlock) && this.myExpression.isVoidCompatible()) || LambdaUtil.isExpressionStatementExpression(body)) {
                return true;
            }
            inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.incompatible.types.expected.void.lambda", new Object[0]));
            return false;
        }
        if ((body instanceof PsiCodeBlock) && !this.myExpression.isValueCompatible()) {
            inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.expected.value.lambda", new Object[0]));
            return false;
        }
        final PsiSubstitutor psiSubstitutorFindNestedSubstitutor = inferenceSession.getInferenceSessionContainer().findNestedSubstitutor(this.myExpression, inferenceSession.getInferenceSubstitution());
        PsiType psiTypeSubstitute = psiSubstitutorFindNestedSubstitutor.substitute(substitutor.substitute(returnType));
        if (!InferenceSession.collectDependencies(psiTypeSubstitute, null, new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.LambdaExpressionCompatibilityConstraint$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return LambdaExpressionCompatibilityConstraint.lambda$reduce$0(psiSubstitutorFindNestedSubstitutor, (PsiClassType) obj);
            }
        }) || this.myExpression.hasFormalParameterTypes()) {
            for (PsiExpression psiExpression : returnExpressions) {
                if (!InferenceSession.ignoreLambdaConstraintTree(psiExpression)) {
                    list.add(new ExpressionCompatibilityConstraint(psiExpression, psiTypeSubstitute));
                }
            }
            return true;
        }
        for (PsiExpression psiExpression2 : returnExpressions) {
            if (!PsiPolyExpressionUtil.isPolyExpression(psiExpression2) && !TypeConversionUtil.areTypesAssignmentCompatible(psiTypeSubstitute, psiExpression2)) {
                PsiType type = psiExpression2.getType();
                if (type != null) {
                    inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.bad.lambda.return.type", inferenceSession.getPresentableText(type), inferenceSession.getPresentableText(psiTypeSubstitute)));
                } else {
                    inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type", psiExpression2.getText(), inferenceSession.getPresentableText(psiTypeSubstitute)));
                }
                return false;
            }
        }
        return true;
    }

    static /* synthetic */ InferenceVariable lambda$reduce$0(PsiSubstitutor psiSubstitutor, PsiClassType psiClassType) {
        PsiClass psiClassResolveClassInClassTypeOnly = PsiUtil.resolveClassInClassTypeOnly(psiClassType);
        if ((psiClassResolveClassInClassTypeOnly instanceof InferenceVariable) && psiSubstitutor.getSubstitutionMap().containsValue(psiClassType)) {
            return (InferenceVariable) psiClassResolveClassInClassTypeOnly;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.ConstraintFormula
    public void apply(PsiSubstitutor psiSubstitutor, boolean z) {
        this.myT = psiSubstitutor.substitute(this.myT);
    }

    public String toString() {
        return this.myExpression.getText() + " -> " + this.myT.getPresentableText();
    }
}
