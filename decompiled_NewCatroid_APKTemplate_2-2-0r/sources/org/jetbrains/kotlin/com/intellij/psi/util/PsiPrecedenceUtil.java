package org.jetbrains.kotlin.com.intellij.psi.util;

import java.util.HashMap;
import java.util.Map;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayAccessExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayInitializerExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiAssignmentExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiConditionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiInstanceOfExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaToken;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiNewExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPolyadicExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPostfixExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrefixExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiSuperExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiThisExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeCastExpression;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiPrecedenceUtil {
    public static final int ADDITIVE_PRECEDENCE = 6;
    public static final int AND_PRECEDENCE = 13;
    public static final int ASSIGNMENT_PRECEDENCE = 16;
    public static final int BINARY_AND_PRECEDENCE = 10;
    public static final int BINARY_OR_PRECEDENCE = 12;
    public static final int BINARY_XOR_PRECEDENCE = 11;
    public static final int CONDITIONAL_PRECEDENCE = 15;
    public static final int EQUALITY_PRECEDENCE = 9;
    public static final int LAMBDA_PRECEDENCE = 17;
    public static final int LITERAL_PRECEDENCE = 0;
    public static final int METHOD_CALL_PRECEDENCE = 1;
    public static final int MULTIPLICATIVE_PRECEDENCE = 5;
    public static final int NUM_PRECEDENCES = 18;
    public static final int OR_PRECEDENCE = 14;
    public static final int PARENTHESIZED_PRECEDENCE = 0;
    public static final int POSTFIX_PRECEDENCE = 2;
    public static final int PREFIX_PRECEDENCE = 3;
    public static final int RELATIONAL_PRECEDENCE = 8;
    public static final int SHIFT_PRECEDENCE = 7;
    public static final int TYPE_CAST_PRECEDENCE = 4;
    private static final Map<IElementType, Integer> s_binaryOperatorPrecedence;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "token";
        } else {
            objArr[0] = "operator";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/util/PsiPrecedenceUtil";
        if (i != 1) {
            objArr[2] = "isCommutativeOperator";
        } else {
            objArr[2] = "getPrecedenceForOperator";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    static {
        HashMap map = new HashMap(18);
        s_binaryOperatorPrecedence = map;
        map.put(JavaTokenType.PLUS, 6);
        map.put(JavaTokenType.MINUS, 6);
        map.put(JavaTokenType.ASTERISK, 5);
        map.put(JavaTokenType.DIV, 5);
        map.put(JavaTokenType.PERC, 5);
        map.put(JavaTokenType.ANDAND, 13);
        map.put(JavaTokenType.OROR, 14);
        map.put(JavaTokenType.AND, 10);
        map.put(JavaTokenType.OR, 12);
        map.put(JavaTokenType.XOR, 11);
        map.put(JavaTokenType.LTLT, 7);
        map.put(JavaTokenType.GTGT, 7);
        map.put(JavaTokenType.GTGTGT, 7);
        map.put(JavaTokenType.GT, 8);
        map.put(JavaTokenType.GE, 8);
        map.put(JavaTokenType.LT, 8);
        map.put(JavaTokenType.LE, 8);
        map.put(JavaTokenType.EQEQ, 9);
        map.put(JavaTokenType.NE, 9);
    }

    public static boolean isCommutativeOperator(IElementType iElementType) {
        if (iElementType == null) {
            $$$reportNull$$$0(0);
        }
        return iElementType == JavaTokenType.PLUS || iElementType == JavaTokenType.ASTERISK || iElementType == JavaTokenType.EQEQ || iElementType == JavaTokenType.NE || iElementType == JavaTokenType.AND || iElementType == JavaTokenType.OR || iElementType == JavaTokenType.XOR;
    }

    public static boolean isCommutativeOperation(PsiPolyadicExpression psiPolyadicExpression) {
        PsiType type;
        return (!isCommutativeOperator(psiPolyadicExpression.getOperationTokenType()) || (type = psiPolyadicExpression.getType()) == null || type.equalsToText(CommonClassNames.JAVA_LANG_STRING)) ? false : true;
    }

    public static boolean isAssociativeOperation(PsiPolyadicExpression psiPolyadicExpression) {
        PsiPrimitiveType unboxedType;
        IElementType operationTokenType = psiPolyadicExpression.getOperationTokenType();
        PsiType type = psiPolyadicExpression.getType();
        if (type instanceof PsiClassType) {
            unboxedType = PsiPrimitiveType.getUnboxedType(type);
            if (unboxedType == null) {
                return false;
            }
        } else {
            if (!(type instanceof PsiPrimitiveType)) {
                return false;
            }
            unboxedType = (PsiPrimitiveType) type;
        }
        if (JavaTokenType.PLUS == operationTokenType || JavaTokenType.ASTERISK == operationTokenType) {
            return (PsiType.FLOAT.equals(unboxedType) || PsiType.DOUBLE.equals(unboxedType)) ? false : true;
        }
        if (JavaTokenType.EQEQ == operationTokenType || JavaTokenType.NE == operationTokenType) {
            return PsiType.BOOLEAN.equals(unboxedType);
        }
        return JavaTokenType.AND == operationTokenType || JavaTokenType.OR == operationTokenType || JavaTokenType.XOR == operationTokenType || JavaTokenType.OROR == operationTokenType || JavaTokenType.ANDAND == operationTokenType;
    }

    public static int getPrecedence(PsiExpression psiExpression) {
        if ((psiExpression instanceof PsiThisExpression) || (psiExpression instanceof PsiLiteralExpression) || (psiExpression instanceof PsiSuperExpression) || (psiExpression instanceof PsiClassObjectAccessExpression) || (psiExpression instanceof PsiArrayAccessExpression) || (psiExpression instanceof PsiArrayInitializerExpression)) {
            return 0;
        }
        if (psiExpression instanceof PsiReferenceExpression) {
            return ((PsiReferenceExpression) psiExpression).getQualifier() != null ? 1 : 0;
        }
        if ((psiExpression instanceof PsiMethodCallExpression) || (psiExpression instanceof PsiNewExpression)) {
            return 1;
        }
        if (psiExpression instanceof PsiTypeCastExpression) {
            return 4;
        }
        if (psiExpression instanceof PsiPrefixExpression) {
            return 3;
        }
        if ((psiExpression instanceof PsiPostfixExpression) || (psiExpression instanceof PsiSwitchExpression)) {
            return 2;
        }
        if (psiExpression instanceof PsiPolyadicExpression) {
            return getPrecedenceForOperator(((PsiPolyadicExpression) psiExpression).getOperationTokenType());
        }
        if (psiExpression instanceof PsiInstanceOfExpression) {
            return 8;
        }
        if (psiExpression instanceof PsiConditionalExpression) {
            return 15;
        }
        if (psiExpression instanceof PsiAssignmentExpression) {
            return 16;
        }
        if (psiExpression instanceof PsiParenthesizedExpression) {
            return 0;
        }
        return psiExpression instanceof PsiLambdaExpression ? 17 : -1;
    }

    public static int getPrecedenceForOperator(IElementType iElementType) {
        if (iElementType == null) {
            $$$reportNull$$$0(1);
        }
        Integer num = s_binaryOperatorPrecedence.get(iElementType);
        if (num == null) {
            throw new IllegalArgumentException("unknown operator: " + iElementType);
        }
        return num.intValue();
    }

    public static boolean areParenthesesNeeded(PsiParenthesizedExpression psiParenthesizedExpression, boolean z) {
        PsiElement parent = psiParenthesizedExpression.getMyParent();
        if (!(parent instanceof PsiExpression)) {
            return false;
        }
        PsiExpression expression = psiParenthesizedExpression.getExpression();
        return expression == null || areParenthesesNeeded(expression, (PsiExpression) parent, z);
    }

    public static boolean areParenthesesNeeded(PsiExpression psiExpression, PsiExpression psiExpression2, boolean z) {
        if ((psiExpression2 instanceof PsiParenthesizedExpression) || (psiExpression2 instanceof PsiArrayInitializerExpression)) {
            return false;
        }
        if (psiExpression2 instanceof PsiArrayAccessExpression) {
            return PsiTreeUtil.isAncestor(((PsiArrayAccessExpression) psiExpression2).getArrayExpression(), psiExpression, false);
        }
        int precedence = getPrecedence(psiExpression2);
        int precedence2 = getPrecedence(psiExpression);
        if (precedence > precedence2) {
            if (z) {
                if (psiExpression instanceof PsiPolyadicExpression) {
                    if ((psiExpression2 instanceof PsiPolyadicExpression) || (psiExpression2 instanceof PsiConditionalExpression) || (psiExpression2 instanceof PsiInstanceOfExpression)) {
                        return true;
                    }
                } else if (psiExpression instanceof PsiInstanceOfExpression) {
                    return true;
                }
            }
            return false;
        }
        if ((psiExpression2 instanceof PsiPolyadicExpression) && (psiExpression instanceof PsiPolyadicExpression)) {
            PsiPolyadicExpression psiPolyadicExpression = (PsiPolyadicExpression) psiExpression2;
            PsiType type = psiPolyadicExpression.getType();
            if (type == null) {
                return true;
            }
            PsiPolyadicExpression psiPolyadicExpression2 = (PsiPolyadicExpression) psiExpression;
            PsiType type2 = psiPolyadicExpression2.getType();
            if (!type.equals(type2)) {
                return true;
            }
            if (type2.equalsToText(CommonClassNames.JAVA_LANG_STRING) && !PsiTreeUtil.isAncestor(psiPolyadicExpression.getOperands()[0], psiPolyadicExpression2, true)) {
                PsiExpression[] operands = psiPolyadicExpression2.getOperands();
                return (type2.equals(operands[0].getType()) || type2.equals(operands[1].getType())) ? false : true;
            }
            if (type2.equals(PsiType.BOOLEAN)) {
                for (PsiExpression psiExpression3 : psiPolyadicExpression2.getOperands()) {
                    PsiType type3 = psiExpression3.getType();
                    if (type3 != null && !PsiType.BOOLEAN.equals(type3)) {
                        return true;
                    }
                }
            }
            IElementType operationTokenType = psiPolyadicExpression.getOperationTokenType();
            IElementType operationTokenType2 = psiPolyadicExpression2.getOperationTokenType();
            if (z && !operationTokenType2.equals(operationTokenType)) {
                return true;
            }
            if (!PsiTreeUtil.isAncestor(psiPolyadicExpression.getOperands()[0], psiExpression, false) && (!isAssociativeOperation(psiPolyadicExpression) || JavaTokenType.DIV == operationTokenType2 || JavaTokenType.PERC == operationTokenType2)) {
                return true;
            }
        } else {
            boolean z2 = psiExpression2 instanceof PsiConditionalExpression;
            if (z2 && (psiExpression instanceof PsiConditionalExpression)) {
                return PsiTreeUtil.isAncestor(((PsiConditionalExpression) psiExpression2).getCondition(), psiExpression, true) || z;
            }
            if (psiExpression instanceof PsiLambdaExpression) {
                if (psiExpression2 instanceof PsiTypeCastExpression) {
                    return false;
                }
                if (z2) {
                    return PsiTreeUtil.isAncestor(((PsiConditionalExpression) psiExpression2).getCondition(), psiExpression, true);
                }
            }
        }
        return precedence < precedence2;
    }

    public static boolean areParenthesesNeeded(PsiJavaToken psiJavaToken, PsiExpression psiExpression) {
        if (!(psiExpression instanceof PsiPolyadicExpression)) {
            return (psiExpression instanceof PsiConditionalExpression) || (psiExpression instanceof PsiAssignmentExpression) || (psiExpression instanceof PsiInstanceOfExpression);
        }
        int precedenceForOperator = getPrecedenceForOperator(((PsiPolyadicExpression) psiExpression).getOperationTokenType());
        IElementType iElementTypeConvertEQtoOperation = TypeConversionUtil.convertEQtoOperation(psiJavaToken.getTokenType());
        return precedenceForOperator >= getPrecedenceForOperator(iElementTypeConvertEQtoOperation) || !isCommutativeOperator(iElementTypeConvertEQtoOperation);
    }
}
