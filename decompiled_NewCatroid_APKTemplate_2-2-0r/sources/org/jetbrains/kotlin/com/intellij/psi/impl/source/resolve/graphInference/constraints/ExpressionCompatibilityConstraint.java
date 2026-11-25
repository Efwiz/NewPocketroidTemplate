package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.codeInsight.daemon.impl.analysis.JavaGenericsUtil;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.LambdaUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiCall;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiConditionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType;
import org.jetbrains.kotlin.com.intellij.psi.PsiDisjunctionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaParameterType;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.ResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceSession;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.InferenceVariable;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.PsiPolyExpressionUtil;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;

/* JADX INFO: loaded from: classes3.dex */
public class ExpressionCompatibilityConstraint extends InputOutputConstraintFormula {
    private final PsiExpression myExpression;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "expression";
        } else {
            objArr[0] = ModuleXmlParser.TYPE;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/graphInference/constraints/ExpressionCompatibilityConstraint";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExpressionCompatibilityConstraint(PsiExpression psiExpression, PsiType psiType) {
        super(psiType);
        if (psiExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (psiType == null) {
            $$$reportNull$$$0(1);
        }
        this.myExpression = psiExpression;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.ConstraintFormula
    public boolean reduce(InferenceSession inferenceSession, final List<? super ConstraintFormula> list) {
        PsiExpression expression;
        final PsiType currentType = getCurrentType();
        if (!PsiPolyExpressionUtil.isPolyExpression(this.myExpression)) {
            PsiType type = this.myExpression.getType();
            if (inferenceSession.isProperType(currentType)) {
                boolean z = type == null || TypeConversionUtil.isAssignable(currentType, type);
                if (!z) {
                    PsiType type2 = this.myExpression.getType();
                    inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type", type2 != null ? type2.getPresentableText() : this.myExpression.getText(), inferenceSession.getPresentableText(currentType)));
                } else if (TypeCompatibilityConstraint.isUncheckedConversion(currentType, type, inferenceSession) && !JavaGenericsUtil.isReifiableType(currentType)) {
                    inferenceSession.setErased();
                }
                return z;
            }
            if (type instanceof PsiLambdaParameterType) {
                return false;
            }
            if ((!(type instanceof PsiClassType) || ((PsiClassType) type).resolve() != null) && type != null && type != PsiType.NULL) {
                if (type instanceof PsiDisjunctionType) {
                    type = ((PsiDisjunctionType) type).getLeastUpperBound();
                }
                list.add(new TypeCompatibilityConstraint(currentType, type));
            }
            return true;
        }
        PsiExpression psiExpression = this.myExpression;
        if ((psiExpression instanceof PsiParenthesizedExpression) && (expression = ((PsiParenthesizedExpression) psiExpression).getExpression()) != null && !InferenceSession.ignoreLambdaConstraintTree(expression)) {
            list.add(new ExpressionCompatibilityConstraint(expression, currentType));
            return true;
        }
        PsiExpression psiExpression2 = this.myExpression;
        if (psiExpression2 instanceof PsiConditionalExpression) {
            PsiExpression thenExpression = ((PsiConditionalExpression) psiExpression2).getThenExpression();
            if (thenExpression != null && !InferenceSession.ignoreLambdaConstraintTree(thenExpression)) {
                list.add(new ExpressionCompatibilityConstraint(thenExpression, currentType));
            }
            PsiExpression elseExpression = ((PsiConditionalExpression) this.myExpression).getElseExpression();
            if (elseExpression != null && !InferenceSession.ignoreLambdaConstraintTree(elseExpression)) {
                list.add(new ExpressionCompatibilityConstraint(elseExpression, currentType));
            }
            return true;
        }
        if (psiExpression2 instanceof PsiSwitchExpression) {
            PsiUtil.getSwitchResultExpressions((PsiSwitchExpression) psiExpression2).forEach(new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.ExpressionCompatibilityConstraint$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ExpressionCompatibilityConstraint.lambda$reduce$0(list, currentType, (PsiExpression) obj);
                }
            });
            return true;
        }
        if (psiExpression2 instanceof PsiCall) {
            InferenceSession inferenceSessionReduceExpressionCompatibilityConstraint = reduceExpressionCompatibilityConstraint(inferenceSession, psiExpression2, currentType, true);
            if (inferenceSessionReduceExpressionCompatibilityConstraint == null) {
                return false;
            }
            if (inferenceSessionReduceExpressionCompatibilityConstraint != inferenceSession) {
                inferenceSession.getInferenceSessionContainer().registerNestedSession(inferenceSessionReduceExpressionCompatibilityConstraint);
                inferenceSession.propagateVariables(inferenceSessionReduceExpressionCompatibilityConstraint);
                for (Pair<InferenceVariable[], PsiClassType> pair : inferenceSessionReduceExpressionCompatibilityConstraint.myIncorporationPhase.getCaptures()) {
                    inferenceSession.myIncorporationPhase.addCapture(pair.first, pair.second);
                }
                ResolveResult currentMethod = inferenceSession.getCurrentMethod(((PsiCall) this.myExpression).getArgumentList());
                if (currentMethod == null) {
                    currentMethod = PsiDiamondType.getDiamondsAwareResolveResult((PsiCall) this.myExpression);
                }
                if ((currentMethod instanceof MethodCandidateInfo) && inferenceSessionReduceExpressionCompatibilityConstraint.isErased()) {
                    ((MethodCandidateInfo) currentMethod).setErased();
                }
            }
            return true;
        }
        if (psiExpression2 instanceof PsiMethodReferenceExpression) {
            list.add(new PsiMethodReferenceCompatibilityConstraint((PsiMethodReferenceExpression) this.myExpression, currentType));
            return true;
        }
        if (psiExpression2 instanceof PsiLambdaExpression) {
            list.add(new LambdaExpressionCompatibilityConstraint((PsiLambdaExpression) this.myExpression, currentType));
        }
        return true;
    }

    static /* synthetic */ void lambda$reduce$0(List list, PsiType psiType, PsiExpression psiExpression) {
        if (InferenceSession.ignoreLambdaConstraintTree(psiExpression)) {
            return;
        }
        list.add(new ExpressionCompatibilityConstraint(psiExpression, psiType));
    }

    /* JADX WARN: Code duplicated, block: B:22:0x004a  */
    /* JADX WARN: Code duplicated, block: B:23:0x004c A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:24:0x004e  */
    /* JADX WARN: Code duplicated, block: B:25:0x0053  */
    /* JADX WARN: Code duplicated, block: B:27:0x005b  */
    /* JADX WARN: Code duplicated, block: B:29:0x006f  */
    /* JADX WARN: Code duplicated, block: B:32:0x0080  */
    /* JADX WARN: Code duplicated, block: B:35:0x0086  */
    /* JADX WARN: Code duplicated, block: B:37:0x00a3  */
    /* JADX WARN: Code duplicated, block: B:40:0x00bc  */
    /* JADX WARN: Code duplicated, block: B:42:0x00c4 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:43:0x00c5 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:44:0x00c7  */
    /* JADX WARN: Code duplicated, block: B:47:0x00d4  */
    /* JADX WARN: Code duplicated, block: B:49:0x00da  */
    /* JADX WARN: Code duplicated, block: B:57:0x00f0  */
    /* JADX WARN: Code duplicated, block: B:60:0x00fa A[LOOP:0: B:58:0x00f4->B:60:0x00fa, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:61:0x0104 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Code duplicated, block: B:63:0x0106  */
    /* JADX WARN: Code duplicated, block: B:65:0x0109  */
    public static InferenceSession reduceExpressionCompatibilityConstraint(InferenceSession inferenceSession, PsiExpression psiExpression, PsiType psiType, boolean z) {
        PsiCall psiCall;
        PsiExpressionList argumentList;
        PsiMethod psiMethod;
        PsiMethod element;
        PsiClass containingClass;
        PsiTypeParameter[] typeParameters;
        PsiClassType psiClassTypeCreateType;
        PsiType psiType2;
        PsiTypeParameter[] typeParameters2;
        PsiSubstitutor psiSubstitutorChooseSiteSubstitutor;
        InferenceSession inferenceSession2;
        List<String> incompatibleErrorMessages;
        Iterator<String> it;
        if (PsiPolyExpressionUtil.isPolyExpression(psiExpression) && (argumentList = (psiCall = (PsiCall) psiExpression).getArgumentList()) != null) {
            MethodCandidateInfo currentMethod = inferenceSession.getCurrentMethod(argumentList);
            JavaResolveResult diamondsAwareResolveResult = currentMethod != null ? null : PsiDiamondType.getDiamondsAwareResolveResult(psiCall);
            if (currentMethod != null) {
                element = currentMethod.getElement();
            } else {
                if (diamondsAwareResolveResult instanceof MethodCandidateInfo) {
                    element = ((MethodCandidateInfo) diamondsAwareResolveResult).getElement();
                } else {
                    psiMethod = null;
                }
                if (psiMethod == null && !psiMethod.isConstructor()) {
                    PsiType returnType = psiMethod.getReturnType();
                    typeParameters2 = psiMethod.getTypeParameters();
                    psiType2 = returnType;
                } else if (diamondsAwareResolveResult != null) {
                    if (psiMethod != null) {
                        containingClass = psiMethod.getContainingClass();
                    } else {
                        containingClass = (PsiClass) diamondsAwareResolveResult.getElement();
                    }
                    if (containingClass != null) {
                        psiClassTypeCreateType = JavaPsiFacade.getElementFactory(argumentList.getProject()).createType(containingClass, PsiSubstitutor.EMPTY);
                        typeParameters = containingClass.getTypeParameters();
                        if (psiMethod != null && psiMethod.hasTypeParameters()) {
                            typeParameters = (PsiTypeParameter[]) ArrayUtil.mergeArrays(typeParameters, psiMethod.getTypeParameters());
                        }
                    } else {
                        typeParameters = null;
                        psiClassTypeCreateType = null;
                    }
                    psiType2 = psiClassTypeCreateType;
                    typeParameters2 = typeParameters;
                }
                if (typeParameters2 != null) {
                    psiSubstitutorChooseSiteSubstitutor = InferenceSession.chooseSiteSubstitutor(currentMethod, diamondsAwareResolveResult, psiMethod);
                    inferenceSession2 = new InferenceSession(typeParameters2, psiSubstitutorChooseSiteSubstitutor, psiExpression.getManager(), psiExpression, inferenceSession.getInferencePolicy());
                    inferenceSession2.propagateVariables(inferenceSession);
                    if (psiMethod != null) {
                        inferenceSession2.initExpressionConstraints(psiMethod.getParameterList().getParameters(), argumentList.getExpressions(), psiMethod, InferenceSession.chooseVarargsMode(currentMethod, diamondsAwareResolveResult));
                    }
                    if (inferenceSession2.repeatInferencePhases()) {
                        if (PsiType.VOID.equals(psiType)) {
                            return inferenceSession2;
                        }
                        if (psiType2 != null) {
                            inferenceSession2.registerReturnTypeConstraints(psiSubstitutorChooseSiteSubstitutor.substitute(psiType2), psiType, psiExpression);
                        }
                        if (inferenceSession2.repeatInferencePhases()) {
                            if (inferenceSession2.isErased() && !JavaGenericsUtil.isReifiableType(psiType) && inferenceSession.getInferenceVariable(psiType) == null) {
                                inferenceSession.setErased();
                            }
                            return inferenceSession2;
                        }
                    }
                    incompatibleErrorMessages = inferenceSession2.getIncompatibleErrorMessages();
                    if (incompatibleErrorMessages == null) {
                        return null;
                    }
                    it = incompatibleErrorMessages.iterator();
                    while (it.hasNext()) {
                        inferenceSession.registerIncompatibleErrorMessage(it.next());
                    }
                    return null;
                }
                if (z) {
                    inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.failed.to.resolve.argument", new Object[0]));
                    return null;
                }
            }
            psiMethod = element;
            if (psiMethod == null) {
                if (diamondsAwareResolveResult != null) {
                    if (psiMethod != null) {
                        containingClass = psiMethod.getContainingClass();
                    } else {
                        containingClass = (PsiClass) diamondsAwareResolveResult.getElement();
                    }
                    if (containingClass != null) {
                        psiClassTypeCreateType = JavaPsiFacade.getElementFactory(argumentList.getProject()).createType(containingClass, PsiSubstitutor.EMPTY);
                        typeParameters = containingClass.getTypeParameters();
                        if (psiMethod != null) {
                            typeParameters = (PsiTypeParameter[]) ArrayUtil.mergeArrays(typeParameters, psiMethod.getTypeParameters());
                        }
                    } else {
                        typeParameters = null;
                        psiClassTypeCreateType = null;
                    }
                    psiType2 = psiClassTypeCreateType;
                    typeParameters2 = typeParameters;
                    if (typeParameters2 != null) {
                        psiSubstitutorChooseSiteSubstitutor = InferenceSession.chooseSiteSubstitutor(currentMethod, diamondsAwareResolveResult, psiMethod);
                        inferenceSession2 = new InferenceSession(typeParameters2, psiSubstitutorChooseSiteSubstitutor, psiExpression.getManager(), psiExpression, inferenceSession.getInferencePolicy());
                        inferenceSession2.propagateVariables(inferenceSession);
                        if (psiMethod != null) {
                            inferenceSession2.initExpressionConstraints(psiMethod.getParameterList().getParameters(), argumentList.getExpressions(), psiMethod, InferenceSession.chooseVarargsMode(currentMethod, diamondsAwareResolveResult));
                        }
                        if (inferenceSession2.repeatInferencePhases()) {
                            if (PsiType.VOID.equals(psiType)) {
                                return inferenceSession2;
                            }
                            if (psiType2 != null) {
                                inferenceSession2.registerReturnTypeConstraints(psiSubstitutorChooseSiteSubstitutor.substitute(psiType2), psiType, psiExpression);
                            }
                            if (inferenceSession2.repeatInferencePhases()) {
                                if (inferenceSession2.isErased()) {
                                    inferenceSession.setErased();
                                }
                                return inferenceSession2;
                            }
                        }
                        incompatibleErrorMessages = inferenceSession2.getIncompatibleErrorMessages();
                        if (incompatibleErrorMessages == null) {
                            return null;
                        }
                        it = incompatibleErrorMessages.iterator();
                        while (it.hasNext()) {
                            inferenceSession.registerIncompatibleErrorMessage(it.next());
                        }
                        return null;
                    }
                    if (z) {
                        inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.failed.to.resolve.argument", new Object[0]));
                        return null;
                    }
                }
            } else if (diamondsAwareResolveResult != null) {
                if (psiMethod != null) {
                    containingClass = psiMethod.getContainingClass();
                } else {
                    containingClass = (PsiClass) diamondsAwareResolveResult.getElement();
                }
                if (containingClass != null) {
                    psiClassTypeCreateType = JavaPsiFacade.getElementFactory(argumentList.getProject()).createType(containingClass, PsiSubstitutor.EMPTY);
                    typeParameters = containingClass.getTypeParameters();
                    if (psiMethod != null) {
                        typeParameters = (PsiTypeParameter[]) ArrayUtil.mergeArrays(typeParameters, psiMethod.getTypeParameters());
                    }
                } else {
                    typeParameters = null;
                    psiClassTypeCreateType = null;
                }
                psiType2 = psiClassTypeCreateType;
                typeParameters2 = typeParameters;
                if (typeParameters2 != null) {
                    psiSubstitutorChooseSiteSubstitutor = InferenceSession.chooseSiteSubstitutor(currentMethod, diamondsAwareResolveResult, psiMethod);
                    inferenceSession2 = new InferenceSession(typeParameters2, psiSubstitutorChooseSiteSubstitutor, psiExpression.getManager(), psiExpression, inferenceSession.getInferencePolicy());
                    inferenceSession2.propagateVariables(inferenceSession);
                    if (psiMethod != null) {
                        inferenceSession2.initExpressionConstraints(psiMethod.getParameterList().getParameters(), argumentList.getExpressions(), psiMethod, InferenceSession.chooseVarargsMode(currentMethod, diamondsAwareResolveResult));
                    }
                    if (inferenceSession2.repeatInferencePhases()) {
                        if (PsiType.VOID.equals(psiType)) {
                            return inferenceSession2;
                        }
                        if (psiType2 != null) {
                            inferenceSession2.registerReturnTypeConstraints(psiSubstitutorChooseSiteSubstitutor.substitute(psiType2), psiType, psiExpression);
                        }
                        if (inferenceSession2.repeatInferencePhases()) {
                            if (inferenceSession2.isErased()) {
                                inferenceSession.setErased();
                            }
                            return inferenceSession2;
                        }
                    }
                    incompatibleErrorMessages = inferenceSession2.getIncompatibleErrorMessages();
                    if (incompatibleErrorMessages == null) {
                        return null;
                    }
                    it = incompatibleErrorMessages.iterator();
                    while (it.hasNext()) {
                        inferenceSession.registerIncompatibleErrorMessage(it.next());
                    }
                    return null;
                }
                if (z) {
                    inferenceSession.registerIncompatibleErrorMessage(JavaPsiBundle.message("error.incompatible.type.failed.to.resolve.argument", new Object[0]));
                    return null;
                }
            }
        }
        return inferenceSession;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.myExpression.equals(((ExpressionCompatibilityConstraint) obj).myExpression);
    }

    public int hashCode() {
        return this.myExpression.hashCode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.InputOutputConstraintFormula
    public PsiExpression getExpression() {
        return this.myExpression;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.InputOutputConstraintFormula
    protected InputOutputConstraintFormula createSelfConstraint(PsiType psiType, PsiExpression psiExpression) {
        return new ExpressionCompatibilityConstraint(psiExpression, psiType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.constraints.InputOutputConstraintFormula
    protected void collectReturnTypeVariables(InferenceSession inferenceSession, PsiExpression psiExpression, PsiType psiType, Set<? super InferenceVariable> set) {
        if (!(psiExpression instanceof PsiLambdaExpression) || PsiType.VOID.equals(psiType)) {
            return;
        }
        Iterator<PsiExpression> it = LambdaUtil.getReturnExpressions((PsiLambdaExpression) psiExpression).iterator();
        while (it.hasNext()) {
            Set<InferenceVariable> inputVariables = createSelfConstraint(psiType, it.next()).getInputVariables(inferenceSession);
            if (inputVariables != null) {
                set.addAll(inputVariables);
            }
        }
    }
}
