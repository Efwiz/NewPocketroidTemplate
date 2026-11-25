package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference;

import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayInitializerExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiAssignmentExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiBreakStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiConditionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiDiamondType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionList;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFunctionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiReturnStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchLabeledRuleStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiThrowStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiYieldStatement;
import org.jetbrains.kotlin.com.intellij.psi.infos.MethodCandidateInfo;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTypesUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.TypeConversionUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiPolyExpressionUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private enum ConditionalKind {
        BOOLEAN,
        NUMERIC,
        NULL
    }

    static /* synthetic */ PsiMethod lambda$isMethodCallPolyExpression$1(PsiMethod psiMethod, PsiExpression psiExpression) {
        return psiMethod;
    }

    public static boolean hasStandaloneForm(PsiExpression psiExpression) {
        return ((psiExpression instanceof PsiFunctionalExpression) || (psiExpression instanceof PsiParenthesizedExpression) || (psiExpression instanceof PsiConditionalExpression) || (psiExpression instanceof PsiSwitchExpression) || (psiExpression instanceof PsiCallExpression)) ? false : true;
    }

    public static boolean isPolyExpression(PsiExpression psiExpression) {
        if (psiExpression instanceof PsiFunctionalExpression) {
            return true;
        }
        if (psiExpression instanceof PsiParenthesizedExpression) {
            return isPolyExpression(((PsiParenthesizedExpression) psiExpression).getExpression());
        }
        if ((psiExpression instanceof PsiNewExpression) && PsiDiamondType.hasDiamond((PsiNewExpression) psiExpression)) {
            return isInAssignmentOrInvocationContext(psiExpression);
        }
        if (psiExpression instanceof PsiMethodCallExpression) {
            return isMethodCallPolyExpression(psiExpression, (Function<? super PsiExpression, ? extends PsiMethod>) new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.PsiPolyExpressionUtil$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((PsiMethodCallExpression) ((PsiExpression) obj)).resolveMethod();
                }
            });
        }
        if ((psiExpression instanceof PsiConditionalExpression) && isBooleanOrNumeric(psiExpression) == null) {
            return isInAssignmentOrInvocationContext(psiExpression);
        }
        if (psiExpression instanceof PsiSwitchExpression) {
            return isInAssignmentOrInvocationContext(psiExpression);
        }
        return false;
    }

    public static boolean isMethodCallPolyExpression(PsiExpression psiExpression, final PsiMethod psiMethod) {
        return isMethodCallPolyExpression(psiExpression, (Function<? super PsiExpression, ? extends PsiMethod>) new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.graphInference.PsiPolyExpressionUtil$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PsiPolyExpressionUtil.lambda$isMethodCallPolyExpression$1(psiMethod, (PsiExpression) obj);
            }
        });
    }

    private static boolean isMethodCallPolyExpression(PsiExpression psiExpression, Function<? super PsiExpression, ? extends PsiMethod> function) {
        if (!isInAssignmentOrInvocationContext(psiExpression) || ((PsiCallExpression) psiExpression).getTypeArguments().length != 0) {
            return false;
        }
        PsiMethod psiMethodApply = function.apply(psiExpression);
        return psiMethodApply == null || isMethodCallTypeDependsOnInference(psiExpression, psiMethodApply);
    }

    private static boolean isMethodCallTypeDependsOnInference(PsiExpression psiExpression, PsiMethod psiMethod) {
        Set set = ContainerUtil.set(psiMethod.getTypeParameters());
        if (set.isEmpty()) {
            return psiMethod.isConstructor() && (psiExpression instanceof PsiNewExpression) && PsiDiamondType.hasDiamond((PsiNewExpression) psiExpression);
        }
        PsiType returnType = psiMethod.getReturnType();
        if (returnType != null) {
            return PsiTypesUtil.mentionsTypeParameters(returnType, (Set<PsiTypeParameter>) set);
        }
        return false;
    }

    public static boolean isInAssignmentOrInvocationContext(PsiExpression psiExpression) {
        PsiElement psiElementSkipParenthesizedExprUp = PsiUtil.skipParenthesizedExprUp(psiExpression.getMyParent());
        return (psiElementSkipParenthesizedExprUp instanceof PsiExpressionList) || (psiElementSkipParenthesizedExprUp instanceof PsiArrayInitializerExpression) || ((psiElementSkipParenthesizedExprUp instanceof PsiConditionalExpression) && ((psiExpression instanceof PsiCallExpression) || isPolyExpression((PsiExpression) psiElementSkipParenthesizedExprUp))) || isSwitchExpressionAssignmentOrInvocationContext(psiExpression) || isAssignmentContext(psiExpression, psiElementSkipParenthesizedExprUp);
    }

    private static boolean isSwitchExpressionAssignmentOrInvocationContext(PsiExpression psiExpression) {
        PsiSwitchExpression psiSwitchExpression;
        PsiElement lightParent = PsiUtil.skipParenthesizedExprUp(psiExpression).getMyParent();
        return (((lightParent instanceof PsiExpressionStatement) && (lightParent.getMyParent() instanceof PsiSwitchLabeledRuleStatement)) || (lightParent instanceof PsiBreakStatement) || (lightParent instanceof PsiYieldStatement) || (lightParent instanceof PsiThrowStatement)) && (psiSwitchExpression = (PsiSwitchExpression) PsiTreeUtil.getParentOfType((PsiElement) psiExpression, PsiSwitchExpression.class, true, (Class<? extends PsiElement>[]) new Class[]{PsiMember.class, PsiLambdaExpression.class})) != null && PsiUtil.getSwitchResultExpressions(psiSwitchExpression).contains(psiExpression) && isInAssignmentOrInvocationContext(psiSwitchExpression);
    }

    private static boolean isAssignmentContext(PsiExpression psiExpression, PsiElement psiElement) {
        return PsiUtil.isCondition(psiExpression, psiElement) || (psiElement instanceof PsiReturnStatement) || ((psiElement instanceof PsiAssignmentExpression) && ((PsiAssignmentExpression) psiElement).getOperationTokenType() == JavaTokenType.EQ) || (((psiElement instanceof PsiVariable) && !isVarContext((PsiVariable) psiElement)) || (psiElement instanceof PsiLambdaExpression));
    }

    private static boolean isVarContext(PsiVariable psiVariable) {
        PsiTypeElement typeElement;
        return PsiUtil.isLanguageLevel10OrHigher(psiVariable) && (typeElement = psiVariable.getTypeElement()) != null && typeElement.isInferredType();
    }

    public static boolean isExpressionOfPrimitiveType(PsiExpression psiExpression) {
        if (psiExpression != null && !isPolyExpression(psiExpression)) {
            PsiType type = psiExpression.getType();
            return (type instanceof PsiPrimitiveType) && type != PsiType.NULL;
        }
        if (!(psiExpression instanceof PsiNewExpression) && !(psiExpression instanceof PsiFunctionalExpression)) {
            if (psiExpression instanceof PsiParenthesizedExpression) {
                return isExpressionOfPrimitiveType(((PsiParenthesizedExpression) psiExpression).getExpression());
            }
            if (psiExpression instanceof PsiConditionalExpression) {
                return isBooleanOrNumeric(psiExpression) != null;
            }
            if (!(psiExpression instanceof PsiMethodCallExpression)) {
                return (psiExpression instanceof PsiSwitchExpression) && isBooleanOrNumeric(psiExpression) != null;
            }
            PsiMethod psiMethodResolveMethod = ((PsiMethodCallExpression) psiExpression).resolveMethod();
            return psiMethodResolveMethod != null && (psiMethodResolveMethod.getReturnType() instanceof PsiPrimitiveType);
        }
        return false;
    }

    public static boolean sameBooleanOrNumeric(PsiExpression psiExpression, PsiExpression psiExpression2) {
        ConditionalKind conditionalKindIsBooleanOrNumeric = isBooleanOrNumeric(psiExpression);
        ConditionalKind conditionalKindIsBooleanOrNumeric2 = isBooleanOrNumeric(psiExpression2);
        if (conditionalKindIsBooleanOrNumeric == conditionalKindIsBooleanOrNumeric2 || conditionalKindIsBooleanOrNumeric2 == ConditionalKind.NULL) {
            return conditionalKindIsBooleanOrNumeric != null;
        }
        return conditionalKindIsBooleanOrNumeric == ConditionalKind.NULL && conditionalKindIsBooleanOrNumeric2 != null;
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0058  */
    private static ConditionalKind isBooleanOrNumeric(PsiExpression psiExpression) {
        PsiType psiTypeSubstitute;
        ConditionalKind conditionalKindIsBooleanOrNumeric;
        if (psiExpression instanceof PsiParenthesizedExpression) {
            return isBooleanOrNumeric(((PsiParenthesizedExpression) psiExpression).getExpression());
        }
        if (psiExpression == null) {
            return null;
        }
        if (((psiExpression instanceof PsiNewExpression) && !PsiDiamondType.hasDiamond((PsiNewExpression) psiExpression)) || hasStandaloneForm(psiExpression)) {
            psiTypeSubstitute = psiExpression.getType();
        } else if (psiExpression instanceof PsiMethodCallExpression) {
            JavaResolveResult javaResolveResultAdvancedResolve = ((PsiMethodCallExpression) psiExpression).getMethodExpression().advancedResolve(false);
            PsiMethod psiMethod = (PsiMethod) javaResolveResultAdvancedResolve.getElement();
            if (psiMethod != null) {
                PsiType returnType = psiMethod.getReturnType();
                psiTypeSubstitute = javaResolveResultAdvancedResolve instanceof MethodCandidateInfo ? ((MethodCandidateInfo) javaResolveResultAdvancedResolve).getSubstitutorFromQualifier().substitute(returnType) : returnType;
            } else {
                psiTypeSubstitute = null;
            }
        } else {
            psiTypeSubstitute = null;
        }
        ConditionalKind conditionalKindIsBooleanOrNumericType = isBooleanOrNumericType(psiTypeSubstitute);
        if (conditionalKindIsBooleanOrNumericType != null) {
            return conditionalKindIsBooleanOrNumericType;
        }
        if (psiExpression instanceof PsiConditionalExpression) {
            PsiConditionalExpression psiConditionalExpression = (PsiConditionalExpression) psiExpression;
            PsiExpression thenExpression = psiConditionalExpression.getThenExpression();
            PsiExpression elseExpression = psiConditionalExpression.getElseExpression();
            ConditionalKind conditionalKindIsBooleanOrNumeric2 = isBooleanOrNumeric(thenExpression);
            ConditionalKind conditionalKindIsBooleanOrNumeric3 = isBooleanOrNumeric(elseExpression);
            if (conditionalKindIsBooleanOrNumeric2 == conditionalKindIsBooleanOrNumeric3 || conditionalKindIsBooleanOrNumeric3 == ConditionalKind.NULL) {
                return conditionalKindIsBooleanOrNumeric2;
            }
            if (conditionalKindIsBooleanOrNumeric2 == ConditionalKind.NULL) {
                return conditionalKindIsBooleanOrNumeric3;
            }
        }
        if (psiExpression instanceof PsiSwitchExpression) {
            Iterator<PsiExpression> it = PsiUtil.getSwitchResultExpressions((PsiSwitchExpression) psiExpression).iterator();
            ConditionalKind conditionalKind = null;
            while (it.hasNext() && (conditionalKindIsBooleanOrNumeric = isBooleanOrNumeric(it.next())) != null) {
                if (conditionalKind != null) {
                    if (conditionalKind == conditionalKindIsBooleanOrNumeric) {
                        continue;
                    } else if (conditionalKind != ConditionalKind.NULL) {
                        if (conditionalKindIsBooleanOrNumeric != ConditionalKind.NULL) {
                            break;
                        }
                    }
                }
                conditionalKind = conditionalKindIsBooleanOrNumeric;
            }
            return null;
        }
        return null;
    }

    private static ConditionalKind isBooleanOrNumericType(PsiType psiType) {
        if (psiType == PsiType.NULL) {
            return ConditionalKind.NULL;
        }
        if (TypeConversionUtil.isNumericType(psiType)) {
            return ConditionalKind.NUMERIC;
        }
        if (TypeConversionUtil.isBooleanType(psiType)) {
            return ConditionalKind.BOOLEAN;
        }
        return null;
    }
}
