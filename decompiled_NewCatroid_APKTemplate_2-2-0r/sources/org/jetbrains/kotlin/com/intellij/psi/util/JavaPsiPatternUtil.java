package org.jetbrains.kotlin.com.intellij.psi.util;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiConditionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiConditionalLoopStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiGuardedPattern;
import org.jetbrains.kotlin.com.intellij.psi.PsiIfStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiInstanceOfExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedPattern;
import org.jetbrains.kotlin.com.intellij.psi.PsiPattern;
import org.jetbrains.kotlin.com.intellij.psi.PsiPatternVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiPolyadicExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrefixExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimaryPattern;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeTestPattern;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;

/* JADX INFO: loaded from: classes3.dex */
public final class JavaPsiPatternUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 4) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 4:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/util/JavaPsiPatternUtil";
                break;
            case 3:
            default:
                objArr[0] = "expression";
                break;
            case 5:
                objArr[0] = "variable";
                break;
            case 6:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 7:
                objArr[0] = "overWhom";
                break;
            case 8:
                objArr[0] = "who";
                break;
            case 9:
                objArr[0] = "scope";
                break;
        }
        if (i == 1 || i == 2) {
            objArr[1] = "getExposedPatternVariables";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/JavaPsiPatternUtil";
        } else {
            objArr[1] = "getExposedPatternVariablesIgnoreParent";
        }
        switch (i) {
            case 1:
            case 2:
            case 4:
                break;
            case 3:
                objArr[2] = "getExposedPatternVariablesIgnoreParent";
                break;
            case 5:
                objArr[2] = "getEffectiveInitializerText";
                break;
            case 6:
                objArr[2] = "isTotalForType";
                break;
            case 7:
            case 8:
                objArr[2] = "dominates";
                break;
            case 9:
            case 10:
                objArr[2] = "collectPatternVariableCandidates";
                break;
            default:
                objArr[2] = "getExposedPatternVariables";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static List<PsiPatternVariable> getExposedPatternVariables(PsiExpression psiExpression) {
        boolean z;
        if (psiExpression == null) {
            $$$reportNull$$$0(0);
        }
        PsiElement psiElementSkipParenthesizedExprUp = PsiUtil.skipParenthesizedExprUp(psiExpression.getLightParent());
        if ((!(psiElementSkipParenthesizedExprUp instanceof PsiPrefixExpression) || !((PsiPrefixExpression) psiElementSkipParenthesizedExprUp).getOperationTokenType().equals(JavaTokenType.EXCL)) && ((!((z = psiElementSkipParenthesizedExprUp instanceof PsiPolyadicExpression)) || !((PsiPolyadicExpression) psiElementSkipParenthesizedExprUp).getOperationTokenType().equals(JavaTokenType.ANDAND)) && ((!z || !((PsiPolyadicExpression) psiElementSkipParenthesizedExprUp).getOperationTokenType().equals(JavaTokenType.OROR)) && !(psiElementSkipParenthesizedExprUp instanceof PsiConditionalExpression) && !(psiElementSkipParenthesizedExprUp instanceof PsiIfStatement) && !(psiElementSkipParenthesizedExprUp instanceof PsiConditionalLoopStatement)))) {
            List<PsiPatternVariable> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(1);
            }
            return listEmptyList;
        }
        ArrayList arrayList = new ArrayList();
        collectPatternVariableCandidates(psiExpression, psiExpression, arrayList, false);
        return arrayList;
    }

    public static List<PsiPatternVariable> getExposedPatternVariablesIgnoreParent(PsiExpression psiExpression) {
        if (psiExpression == null) {
            $$$reportNull$$$0(3);
        }
        ArrayList arrayList = new ArrayList();
        collectPatternVariableCandidates(psiExpression, psiExpression, arrayList, true);
        return arrayList;
    }

    public static String getEffectiveInitializerText(PsiPatternVariable psiPatternVariable) {
        if (psiPatternVariable == null) {
            $$$reportNull$$$0(5);
        }
        PsiPattern pattern = psiPatternVariable.getPattern();
        PsiInstanceOfExpression psiInstanceOfExpression = (PsiInstanceOfExpression) ObjectUtils.tryCast(pattern.getLightParent(), PsiInstanceOfExpression.class);
        if (psiInstanceOfExpression == null || !(pattern instanceof PsiTypeTestPattern)) {
            return null;
        }
        PsiExpression operand = psiInstanceOfExpression.getOperand();
        PsiTypeElement checkType = ((PsiTypeTestPattern) pattern).getCheckType();
        if (checkType == null) {
            return null;
        }
        if (checkType.getType().equals(operand.getType())) {
            return operand.getText();
        }
        return "(" + checkType.getText() + Constant.AFTER_QUTO + operand.getText();
    }

    public static PsiPattern skipParenthesizedPatternDown(PsiPattern psiPattern) {
        while (psiPattern instanceof PsiParenthesizedPattern) {
            psiPattern = ((PsiParenthesizedPattern) psiPattern).getPattern();
        }
        return psiPattern;
    }

    public static PsiElement skipParenthesizedPatternUp(PsiElement psiElement) {
        while (psiElement instanceof PsiParenthesizedPattern) {
            psiElement = psiElement.getLightParent();
        }
        return psiElement;
    }

    public static PsiPatternVariable getPatternVariable(PsiPattern psiPattern) {
        if (psiPattern instanceof PsiGuardedPattern) {
            return getPatternVariable(((PsiGuardedPattern) psiPattern).getPrimaryPattern());
        }
        if (psiPattern instanceof PsiParenthesizedPattern) {
            return getPatternVariable(((PsiParenthesizedPattern) psiPattern).getPattern());
        }
        if (psiPattern instanceof PsiTypeTestPattern) {
            return ((PsiTypeTestPattern) psiPattern).getPatternVariable();
        }
        return null;
    }

    public static PsiType getPatternType(PsiPattern psiPattern) {
        PsiTypeElement checkType;
        if (psiPattern == null) {
            return null;
        }
        if (psiPattern instanceof PsiGuardedPattern) {
            return getPatternType(((PsiGuardedPattern) psiPattern).getPrimaryPattern());
        }
        if (psiPattern instanceof PsiParenthesizedPattern) {
            return getPatternType(((PsiParenthesizedPattern) psiPattern).getPattern());
        }
        if (!(psiPattern instanceof PsiTypeTestPattern) || (checkType = ((PsiTypeTestPattern) psiPattern).getCheckType()) == null) {
            return null;
        }
        return checkType.getType();
    }

    public static boolean isTotalForType(PsiPattern psiPattern, PsiType psiType) {
        if (psiType == null) {
            $$$reportNull$$$0(6);
        }
        if (psiPattern == null) {
            return false;
        }
        if (psiPattern instanceof PsiGuardedPattern) {
            PsiGuardedPattern psiGuardedPattern = (PsiGuardedPattern) psiPattern;
            return isTotalForType(psiGuardedPattern.getPrimaryPattern(), psiType) && Boolean.TRUE.equals(evaluateConstant(psiGuardedPattern.getGuardingExpression()));
        }
        if (psiPattern instanceof PsiParenthesizedPattern) {
            return isTotalForType(((PsiParenthesizedPattern) psiPattern).getPattern(), psiType);
        }
        if (!(psiPattern instanceof PsiTypeTestPattern)) {
            return false;
        }
        PsiType psiTypeErasure = TypeConversionUtil.erasure(psiType);
        PsiType psiTypeErasure2 = TypeConversionUtil.erasure(getPatternType(psiPattern));
        if ((psiTypeErasure instanceof PsiArrayType) || (psiTypeErasure2 instanceof PsiArrayType)) {
            return psiTypeErasure2 != null && TypeConversionUtil.isAssignable(psiTypeErasure2, psiTypeErasure);
        }
        PsiClass psiClass = PsiTypesUtil.getPsiClass(psiTypeErasure);
        PsiClass psiClass2 = PsiTypesUtil.getPsiClass(psiTypeErasure2);
        return (psiClass == null || psiClass2 == null || !InheritanceUtil.isInheritorOrSelf(psiClass, psiClass2, true)) ? false : true;
    }

    public static boolean dominates(PsiPattern psiPattern, PsiPattern psiPattern2) {
        PsiType patternType;
        if (psiPattern2 == null) {
            $$$reportNull$$$0(7);
        }
        if (psiPattern == null) {
            return false;
        }
        if (psiPattern2 instanceof PsiGuardedPattern) {
            if (psiPattern instanceof PsiTypeTestPattern) {
                PsiType patternType2 = getPatternType(psiPattern);
                PsiType patternType3 = getPatternType(psiPattern2);
                if (patternType2 == null || patternType3 == null || !patternType2.equalsToText(patternType3.getCanonicalText())) {
                    return dominates(psiPattern, ((PsiGuardedPattern) psiPattern2).getPrimaryPattern());
                }
                return true;
            }
            if (psiPattern instanceof PsiParenthesizedPattern) {
                return dominates(((PsiParenthesizedPattern) psiPattern).getPattern(), psiPattern2);
            }
            if (!(psiPattern instanceof PsiGuardedPattern)) {
                return false;
            }
            PsiGuardedPattern psiGuardedPattern = (PsiGuardedPattern) psiPattern;
            if (dominates(psiGuardedPattern.getPrimaryPattern(), psiPattern2)) {
                return Boolean.TRUE.equals(evaluateConstant(psiGuardedPattern.getGuardingExpression()));
            }
            return false;
        }
        if (!(psiPattern2 instanceof PsiParenthesizedPattern)) {
            return (psiPattern2 instanceof PsiTypeTestPattern) && (patternType = getPatternType(psiPattern2)) != null && isTotalForType(psiPattern, patternType);
        }
        PsiPattern pattern = ((PsiParenthesizedPattern) psiPattern2).getPattern();
        if (pattern == null) {
            return false;
        }
        return dominates(psiPattern, pattern);
    }

    public static boolean dominates(PsiPattern psiPattern, PsiType psiType) {
        PsiType psiTypeErasure;
        if (psiPattern == null) {
            $$$reportNull$$$0(8);
        }
        if (psiType == null || (psiTypeErasure = TypeConversionUtil.erasure(getPatternType(psiPattern))) == null) {
            return false;
        }
        if (psiType instanceof PsiPrimitiveType) {
            psiType = ((PsiPrimitiveType) psiType).getBoxedType(psiPattern);
        } else if (!(psiType instanceof PsiClassType)) {
            psiType = null;
        }
        return psiType != null && TypeConversionUtil.areTypesConvertible(psiType, psiTypeErasure);
    }

    private static void collectPatternVariableCandidates(PsiExpression psiExpression, PsiExpression psiExpression2, Collection<PsiPatternVariable> collection, boolean z) {
        PsiPatternVariable patternVariable;
        if (psiExpression == null) {
            $$$reportNull$$$0(9);
        }
        if (psiExpression2 == null) {
            $$$reportNull$$$0(10);
        }
        while (true) {
            if (psiExpression2 instanceof PsiParenthesizedExpression) {
                psiExpression2 = ((PsiParenthesizedExpression) psiExpression2).getExpression();
            } else {
                if (!(psiExpression2 instanceof PsiPrefixExpression)) {
                    break;
                }
                PsiPrefixExpression psiPrefixExpression = (PsiPrefixExpression) psiExpression2;
                if (!psiPrefixExpression.getOperationTokenType().equals(JavaTokenType.EXCL)) {
                    break;
                } else {
                    psiExpression2 = psiPrefixExpression.getOperand();
                }
            }
        }
        if (psiExpression2 instanceof PsiInstanceOfExpression) {
            PsiPrimaryPattern pattern = ((PsiInstanceOfExpression) psiExpression2).getPattern();
            if ((pattern instanceof PsiTypeTestPattern) && (patternVariable = ((PsiTypeTestPattern) pattern).getPatternVariable()) != null && !PsiTreeUtil.isAncestor(psiExpression, patternVariable.getMethod(), z)) {
                collection.add(patternVariable);
            }
        }
        if (psiExpression2 instanceof PsiPolyadicExpression) {
            PsiPolyadicExpression psiPolyadicExpression = (PsiPolyadicExpression) psiExpression2;
            IElementType operationTokenType = psiPolyadicExpression.getOperationTokenType();
            if (operationTokenType.equals(JavaTokenType.ANDAND) || operationTokenType.equals(JavaTokenType.OROR)) {
                for (PsiExpression psiExpression3 : psiPolyadicExpression.getOperands()) {
                    collectPatternVariableCandidates(psiExpression, psiExpression3, collection, z);
                }
            }
        }
    }

    private static Object evaluateConstant(PsiExpression psiExpression) {
        if (psiExpression == null) {
            return null;
        }
        return JavaPsiFacade.getInstance(psiExpression.getProject()).getConstantEvaluationHelper().computeConstantExpression(psiExpression, false);
    }
}
