package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.codeInsight.ExceptionUtil;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.psi.LambdaUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiConditionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.FunctionalInterfaceParameterizationUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceVariable;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.PsiPolyExpressionUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class CheckedExceptionCompatibilityConstraint extends InputOutputConstraintFormula {
    private final PsiExpression myExpression;

    public CheckedExceptionCompatibilityConstraint(PsiExpression psiExpression, PsiType psiType) {
        super(psiType);
        this.myExpression = psiExpression;
    }

    /* JADX WARN: Code duplicated, block: B:42:0x00b8  */
    /* JADX WARN: Code duplicated, block: B:44:0x00c4  */
    /* JADX WARN: Code duplicated, block: B:48:0x00e6 A[LOOP:0: B:43:0x00c2->B:48:0x00e6, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:94:0x00d4 A[SYNTHETIC] */
    /* JADX WARN: Instruction removed from duplicated block: B:42:0x00b8, please report this as an issue */
    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.ConstraintFormula
    public boolean reduce(final InferenceSession inferenceSession, List<? super ConstraintFormula> list) {
        int i;
        PsiType psiTypeSubstitute;
        if (!PsiPolyExpressionUtil.isPolyExpression(this.myExpression)) {
            return true;
        }
        PsiType currentType = getCurrentType();
        PsiExpression psiExpression = this.myExpression;
        if (psiExpression instanceof PsiParenthesizedExpression) {
            list.add(new CheckedExceptionCompatibilityConstraint(((PsiParenthesizedExpression) psiExpression).getExpression(), currentType));
            return true;
        }
        if (psiExpression instanceof PsiConditionalExpression) {
            PsiExpression thenExpression = ((PsiConditionalExpression) psiExpression).getThenExpression();
            if (thenExpression != null) {
                list.add(new CheckedExceptionCompatibilityConstraint(thenExpression, currentType));
            }
            PsiExpression elseExpression = ((PsiConditionalExpression) this.myExpression).getElseExpression();
            if (elseExpression != null) {
                list.add(new CheckedExceptionCompatibilityConstraint(elseExpression, currentType));
            }
            return true;
        }
        if ((psiExpression instanceof PsiLambdaExpression) || (psiExpression instanceof PsiMethodReferenceExpression)) {
            if (!LambdaUtil.isFunctionalType(currentType)) {
                inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.not.a.functional.interface", inferenceSession.getPresentableText(currentType)));
                return false;
            }
            PsiExpression psiExpression2 = this.myExpression;
            PsiType groundTargetType = psiExpression2 instanceof PsiLambdaExpression ? FunctionalInterfaceParameterizationUtil.getGroundTargetType(currentType, (PsiLambdaExpression) psiExpression2, false) : FunctionalInterfaceParameterizationUtil.getGroundTargetType(currentType);
            PsiMethod functionalInterfaceMethod = LambdaUtil.getFunctionalInterfaceMethod(groundTargetType);
            if (functionalInterfaceMethod == null) {
                inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.no.valid.function.type.found", inferenceSession.getPresentableText(currentType)));
                return false;
            }
            final PsiSubstitutor substitutor = LambdaUtil.getSubstitutor(functionalInterfaceMethod, PsiUtil.resolveGenericsClassInType(groundTargetType));
            PsiExpression psiExpression3 = this.myExpression;
            if (!(psiExpression3 instanceof PsiLambdaExpression) || ((PsiLambdaExpression) psiExpression3).hasFormalParameterTypes()) {
                PsiExpression psiExpression4 = this.myExpression;
                if ((psiExpression4 instanceof PsiMethodReferenceExpression) && !((PsiMethodReferenceExpression) psiExpression4).isExact()) {
                    for (PsiParameter psiParameter : functionalInterfaceMethod.getParameterList().getParameters()) {
                        psiTypeSubstitute = substitutor.substitute(psiParameter.getType());
                        if (!inferenceSession.isProperType(psiTypeSubstitute)) {
                            inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.parameter.type.is.not.yet.inferred", inferenceSession.getPresentableText(psiTypeSubstitute)));
                            return false;
                        }
                    }
                }
            } else {
                while (i < r5) {
                    psiTypeSubstitute = substitutor.substitute(psiParameter.getType());
                    if (!inferenceSession.isProperType(psiTypeSubstitute)) {
                        inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.parameter.type.is.not.yet.inferred", inferenceSession.getPresentableText(psiTypeSubstitute)));
                        return false;
                    }
                }
            }
            PsiType returnType = functionalInterfaceMethod.getReturnType();
            PsiExpression psiExpression5 = this.myExpression;
            if ((psiExpression5 instanceof PsiLambdaExpression) || !((PsiMethodReferenceExpression) psiExpression5).isExact()) {
                PsiType psiTypeSubstitute2 = substitutor.substitute(returnType);
                if (!inferenceSession.isProperType(psiTypeSubstitute2)) {
                    inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.return.type.is.not.yet.inferred", inferenceSession.getPresentableText(psiTypeSubstitute2)));
                    return false;
                }
            }
            List<PsiType> map = ContainerUtil.map(functionalInterfaceMethod.getThrowsList().getReferencedTypes(), new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.CheckedExceptionCompatibilityConstraint$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return inferenceSession.substituteWithInferenceVariables(substitutor.substitute((PsiType) obj));
                }
            });
            ArrayList arrayList = new ArrayList();
            for (PsiType psiType : map) {
                if (!inferenceSession.isProperType(psiType)) {
                    arrayList.add(psiType);
                }
            }
            ArrayList<PsiType> arrayList2 = new ArrayList();
            PsiElement body = this.myExpression;
            if (body instanceof PsiLambdaExpression) {
                body = ((PsiLambdaExpression) body).getBody();
            }
            if (body != null) {
                arrayList2.addAll(ContainerUtil.filter(ExceptionUtil.getUnhandledExceptions(new PsiElement[]{body}), new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.CheckedExceptionCompatibilityConstraint$$ExternalSyntheticLambda1
                    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                    public final boolean value(Object obj) {
                        return CheckedExceptionCompatibilityConstraint.lambda$reduce$1((PsiClassType) obj);
                    }
                }));
            }
            if (arrayList.isEmpty()) {
                for (PsiType psiType2 : arrayList2) {
                    if (!isAddressed(map, psiType2)) {
                        inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.unhandled.exception", inferenceSession.getPresentableText(psiType2)));
                        return false;
                    }
                }
            } else {
                ArrayList arrayList3 = new ArrayList(map);
                arrayList3.removeAll(arrayList);
                for (PsiType psiType3 : arrayList2) {
                    if (!isAddressed(arrayList3, psiType3)) {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            list.add(new StrictSubtypingConstraint((PsiType) it.next(), psiType3));
                        }
                    }
                }
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    InferenceVariable inferenceVariable = inferenceSession.getInferenceVariable((PsiType) it2.next());
                    if (inferenceVariable != null) {
                        inferenceVariable.setThrownBound();
                    }
                }
            }
        }
        return true;
    }

    static /* synthetic */ boolean lambda$reduce$1(PsiClassType psiClassType) {
        return !ExceptionUtil.isUncheckedException(psiClassType);
    }

    private static boolean isAddressed(List<PsiType> list, PsiType psiType) {
        Iterator<PsiType> it = list.iterator();
        while (it.hasNext()) {
            if (TypeConversionUtil.isAssignable(it.next(), psiType)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.InputOutputConstraintFormula
    public PsiExpression getExpression() {
        return this.myExpression;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.InputOutputConstraintFormula
    protected InputOutputConstraintFormula createSelfConstraint(PsiType psiType, PsiExpression psiExpression) {
        return new CheckedExceptionCompatibilityConstraint(psiExpression, psiType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.InputOutputConstraintFormula
    protected void collectReturnTypeVariables(InferenceSession inferenceSession, PsiExpression psiExpression, PsiType psiType, Set<? super InferenceVariable> set) {
        inferenceSession.collectDependencies(psiType, set);
    }
}
