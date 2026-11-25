package org.jetbrains.kotlin.com.intellij.psi.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassObjectAccessExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiConditionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiConstantEvaluationHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPolyadicExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrefixExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeCastExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassReferenceType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.ClassUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.ConstantEvaluationOverflowException;
import org.jetbrains.kotlin.com.intellij.psi.util.ConstantExpressionUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.Interner;

/* JADX INFO: loaded from: classes3.dex */
final class ConstantExpressionVisitor extends JavaElementVisitor implements PsiConstantEvaluationHelper.AuxEvaluator {
    private final PsiConstantEvaluationHelper.AuxEvaluator myAuxEvaluator;
    private Object myResult;
    private final boolean myThrowExceptionOnOverflow;
    private Set<PsiVariable> myVisitedVars;
    private final Interner<String> myInterner = Interner.createStringInterner();
    private final Map<PsiElement, Object> myCachedValues = new HashMap();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "expression";
        } else {
            objArr[0] = "auxEvaluator";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/ConstantExpressionVisitor";
        objArr[2] = "computeExpression";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    ConstantExpressionVisitor(Set<PsiVariable> set, boolean z, PsiConstantEvaluationHelper.AuxEvaluator auxEvaluator) {
        this.myVisitedVars = set;
        this.myThrowExceptionOnOverflow = z;
        this.myAuxEvaluator = auxEvaluator;
    }

    Object handle(PsiElement psiElement) {
        this.myResult = null;
        psiElement.accept(this);
        store(psiElement, this.myResult);
        return this.myResult;
    }

    private Object getStoredValue(PsiElement psiElement) {
        return this.myCachedValues.remove(psiElement);
    }

    void store(PsiElement psiElement, Object obj) {
        this.myCachedValues.put(psiElement, obj);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitLiteralExpression(PsiLiteralExpression psiLiteralExpression) {
        Object value = psiLiteralExpression.getValue();
        if (value instanceof String) {
            value = this.myInterner.intern((String) value);
        }
        this.myResult = value;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitTypeCastExpression(PsiTypeCastExpression psiTypeCastExpression) {
        PsiTypeElement castType = psiTypeCastExpression.getCastType();
        Object storedValue = getStoredValue(psiTypeCastExpression.getOperand());
        if (castType == null || storedValue == null) {
            this.myResult = null;
            return;
        }
        PsiType type = castType.getType();
        if (!(type instanceof PsiPrimitiveType) && !type.equalsToText(CommonClassNames.JAVA_LANG_STRING)) {
            this.myResult = null;
        } else {
            this.myResult = ConstantExpressionUtil.computeCastTo(storedValue, type);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitConditionalExpression(PsiConditionalExpression psiConditionalExpression) {
        Object storedValue = getStoredValue(psiConditionalExpression.getThenExpression());
        Object storedValue2 = getStoredValue(psiConditionalExpression.getElseExpression());
        Object storedValue3 = getStoredValue(psiConditionalExpression.getCondition());
        Object obj = null;
        if (storedValue == null || storedValue2 == null) {
            this.myResult = null;
            return;
        }
        if (storedValue3 instanceof Boolean) {
            if (!((Boolean) storedValue3).booleanValue()) {
                storedValue = storedValue2;
            }
            obj = storedValue;
        }
        this.myResult = obj;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitPolyadicExpression(PsiPolyadicExpression psiPolyadicExpression) {
        PsiExpression[] operands = psiPolyadicExpression.getOperands();
        Object storedValue = getStoredValue(operands[0]);
        if (storedValue == null) {
            this.myResult = null;
            return;
        }
        IElementType operationTokenType = psiPolyadicExpression.getOperationTokenType();
        for (int i = 1; i < operands.length; i++) {
            Object storedValue2 = getStoredValue(operands[i]);
            if (storedValue2 == null) {
                this.myResult = null;
                break;
            }
            storedValue = compute(storedValue, storedValue2, operationTokenType, psiPolyadicExpression);
            this.myResult = storedValue;
            if (storedValue == null) {
                break;
            }
        }
        Object obj = this.myResult;
        if (obj instanceof String) {
            this.myResult = this.myInterner.intern((String) obj);
        }
    }

    private Object compute(Object obj, Object obj2, IElementType iElementType, PsiPolyadicExpression psiPolyadicExpression) {
        Object d;
        Object objValueOf;
        boolean z;
        boolean z2;
        Object objValueOf2 = obj;
        Object objValueOf3 = obj2;
        boolean z3 = true;
        if (iElementType == JavaTokenType.PLUS) {
            if ((objValueOf2 instanceof String) || (objValueOf3 instanceof String)) {
                return computeValueToString(obj) + computeValueToString(obj2);
            }
            if (objValueOf2 instanceof Character) {
                objValueOf2 = Integer.valueOf(((Character) objValueOf2).charValue());
            }
            if (objValueOf3 instanceof Character) {
                objValueOf3 = Integer.valueOf(((Character) objValueOf3).charValue());
            }
            if (!(objValueOf2 instanceof Number) || !(objValueOf3 instanceof Number)) {
                return null;
            }
            if ((objValueOf2 instanceof Double) || (objValueOf3 instanceof Double)) {
                Double d2 = new Double(((Number) objValueOf2).doubleValue() + ((Number) objValueOf3).doubleValue());
                checkRealNumberOverflow(d2, objValueOf2, objValueOf3, psiPolyadicExpression);
                return d2;
            }
            if ((objValueOf2 instanceof Float) || (objValueOf3 instanceof Float)) {
                Float f = new Float(((Number) objValueOf2).floatValue() + ((Number) objValueOf3).floatValue());
                checkRealNumberOverflow(f, objValueOf2, objValueOf3, psiPolyadicExpression);
                return f;
            }
            if ((objValueOf2 instanceof Long) || (objValueOf3 instanceof Long)) {
                long jLongValue = ((Number) objValueOf2).longValue();
                long jLongValue2 = ((Number) objValueOf3).longValue();
                Long lValueOf = Long.valueOf(jLongValue + jLongValue2);
                checkAdditionOverflow(lValueOf.longValue() >= 0, jLongValue >= 0, jLongValue2 >= 0, psiPolyadicExpression);
                return lValueOf;
            }
            int iIntValue = ((Number) objValueOf2).intValue();
            int iIntValue2 = ((Number) objValueOf3).intValue();
            Integer numValueOf = Integer.valueOf(iIntValue + iIntValue2);
            checkAdditionOverflow(numValueOf.intValue() >= 0, iIntValue >= 0, iIntValue2 >= 0, psiPolyadicExpression);
            return numValueOf;
        }
        if (iElementType == JavaTokenType.MINUS) {
            if (objValueOf2 instanceof Character) {
                objValueOf2 = Integer.valueOf(((Character) objValueOf2).charValue());
            }
            if (objValueOf3 instanceof Character) {
                objValueOf3 = Integer.valueOf(((Character) objValueOf3).charValue());
            }
            if (!(objValueOf2 instanceof Number) || !(objValueOf3 instanceof Number)) {
                return null;
            }
            if ((objValueOf2 instanceof Double) || (objValueOf3 instanceof Double)) {
                Double d3 = new Double(((Number) objValueOf2).doubleValue() - ((Number) objValueOf3).doubleValue());
                checkRealNumberOverflow(d3, objValueOf2, objValueOf3, psiPolyadicExpression);
                return d3;
            }
            if ((objValueOf2 instanceof Float) || (objValueOf3 instanceof Float)) {
                Float f2 = new Float(((Number) objValueOf2).floatValue() - ((Number) objValueOf3).floatValue());
                checkRealNumberOverflow(f2, objValueOf2, objValueOf3, psiPolyadicExpression);
                return f2;
            }
            if ((objValueOf2 instanceof Long) || (objValueOf3 instanceof Long)) {
                long jLongValue3 = ((Number) objValueOf2).longValue();
                long jLongValue4 = ((Number) objValueOf3).longValue();
                Long lValueOf2 = Long.valueOf(jLongValue3 - jLongValue4);
                checkAdditionOverflow(lValueOf2.longValue() >= 0, jLongValue3 >= 0, jLongValue4 < 0, psiPolyadicExpression);
                return lValueOf2;
            }
            int iIntValue3 = ((Number) objValueOf2).intValue();
            int iIntValue4 = ((Number) objValueOf3).intValue();
            Integer numValueOf2 = Integer.valueOf(iIntValue3 - iIntValue4);
            checkAdditionOverflow(numValueOf2.intValue() >= 0, iIntValue3 >= 0, iIntValue4 < 0, psiPolyadicExpression);
            return numValueOf2;
        }
        if (iElementType == JavaTokenType.ANDAND) {
            boolean z4 = objValueOf2 instanceof Boolean;
            if ((z4 && !((Boolean) objValueOf2).booleanValue()) || (((z2 = objValueOf3 instanceof Boolean)) && !((Boolean) objValueOf3).booleanValue())) {
                return Boolean.FALSE;
            }
            if (z4 && z2) {
                return Boolean.valueOf(((Boolean) objValueOf2).booleanValue() && ((Boolean) objValueOf3).booleanValue());
            }
            return null;
        }
        if (iElementType == JavaTokenType.OROR) {
            boolean z5 = objValueOf2 instanceof Boolean;
            if ((z5 && ((Boolean) objValueOf2).booleanValue()) || (((z = objValueOf3 instanceof Boolean)) && ((Boolean) objValueOf3).booleanValue())) {
                return Boolean.TRUE;
            }
            if (!z5 || !z) {
                return null;
            }
            if (!((Boolean) objValueOf2).booleanValue() && !((Boolean) objValueOf3).booleanValue()) {
                z3 = false;
            }
            return Boolean.valueOf(z3);
        }
        if (iElementType == JavaTokenType.LT || iElementType == JavaTokenType.LE || iElementType == JavaTokenType.GT || iElementType == JavaTokenType.GE) {
            return compareNumbers(obj, obj2, iElementType);
        }
        if (iElementType == JavaTokenType.EQEQ || iElementType == JavaTokenType.NE) {
            return handleEqualityComparison(obj, obj2, iElementType);
        }
        if (iElementType == JavaTokenType.ASTERISK) {
            if (objValueOf2 instanceof Character) {
                objValueOf2 = Integer.valueOf(((Character) objValueOf2).charValue());
            }
            if (objValueOf3 instanceof Character) {
                objValueOf3 = Integer.valueOf(((Character) objValueOf3).charValue());
            }
            if (!(objValueOf2 instanceof Number) || !(objValueOf3 instanceof Number)) {
                return null;
            }
            if ((objValueOf2 instanceof Double) || (objValueOf3 instanceof Double)) {
                Double d4 = new Double(((Number) objValueOf2).doubleValue() * ((Number) objValueOf3).doubleValue());
                checkRealNumberOverflow(d4, objValueOf2, objValueOf3, psiPolyadicExpression);
                return d4;
            }
            if ((objValueOf2 instanceof Float) || (objValueOf3 instanceof Float)) {
                Float f3 = new Float(((Number) objValueOf2).floatValue() * ((Number) objValueOf3).floatValue());
                checkRealNumberOverflow(f3, objValueOf2, objValueOf3, psiPolyadicExpression);
                return f3;
            }
            if ((objValueOf2 instanceof Long) || (objValueOf3 instanceof Long)) {
                long jLongValue5 = ((Number) objValueOf2).longValue();
                long jLongValue6 = ((Number) objValueOf3).longValue();
                Long lValueOf3 = Long.valueOf(jLongValue5 * jLongValue6);
                checkMultiplicationOverflow(lValueOf3.longValue(), jLongValue5, jLongValue6, psiPolyadicExpression);
                return lValueOf3;
            }
            int iIntValue5 = ((Number) objValueOf2).intValue();
            int iIntValue6 = ((Number) objValueOf3).intValue();
            Integer numValueOf3 = Integer.valueOf(iIntValue5 * iIntValue6);
            checkMultiplicationOverflow(numValueOf3.intValue(), iIntValue5, iIntValue6, psiPolyadicExpression);
            return numValueOf3;
        }
        if (iElementType == JavaTokenType.DIV) {
            if (objValueOf2 instanceof Character) {
                objValueOf2 = Integer.valueOf(((Character) objValueOf2).charValue());
            }
            if (objValueOf3 instanceof Character) {
                objValueOf3 = Integer.valueOf(((Character) objValueOf3).charValue());
            }
            if (!(objValueOf2 instanceof Number) || !(objValueOf3 instanceof Number)) {
                return null;
            }
            if ((objValueOf2 instanceof Double) || (objValueOf3 instanceof Double)) {
                Double d5 = new Double(((Number) objValueOf2).doubleValue() / ((Number) objValueOf3).doubleValue());
                checkRealNumberOverflow(d5, objValueOf2, objValueOf3, psiPolyadicExpression);
                return d5;
            }
            if ((objValueOf2 instanceof Float) || (objValueOf3 instanceof Float)) {
                Float f4 = new Float(((Number) objValueOf2).floatValue() / ((Number) objValueOf3).floatValue());
                checkRealNumberOverflow(f4, objValueOf2, objValueOf3, psiPolyadicExpression);
                return f4;
            }
            if ((objValueOf2 instanceof Long) || (objValueOf3 instanceof Long)) {
                long jLongValue7 = ((Number) objValueOf3).longValue();
                long jLongValue8 = ((Number) objValueOf2).longValue();
                checkDivisionOverflow(jLongValue8, jLongValue7, Long.MIN_VALUE, psiPolyadicExpression);
                if (jLongValue7 == 0) {
                    return null;
                }
                objValueOf = Long.valueOf(jLongValue8 / jLongValue7);
            } else {
                int iIntValue7 = ((Number) objValueOf3).intValue();
                int iIntValue8 = ((Number) objValueOf2).intValue();
                checkDivisionOverflow(iIntValue8, iIntValue7, -2147483648L, psiPolyadicExpression);
                if (iIntValue7 == 0) {
                    return null;
                }
                objValueOf = Integer.valueOf(iIntValue8 / iIntValue7);
            }
            return objValueOf;
        }
        if (iElementType == JavaTokenType.PERC) {
            if (objValueOf2 instanceof Character) {
                objValueOf2 = Integer.valueOf(((Character) objValueOf2).charValue());
            }
            if (objValueOf3 instanceof Character) {
                objValueOf3 = Integer.valueOf(((Character) objValueOf3).charValue());
            }
            if (!(objValueOf2 instanceof Number) || !(objValueOf3 instanceof Number)) {
                return null;
            }
            Number number = (Number) objValueOf3;
            double dDoubleValue = number.doubleValue();
            if (this.myThrowExceptionOnOverflow && dDoubleValue == 0.0d) {
                throw new ConstantEvaluationOverflowException(psiPolyadicExpression);
            }
            if ((objValueOf2 instanceof Double) || (objValueOf3 instanceof Double)) {
                d = new Double(((Number) objValueOf2).doubleValue() % number.doubleValue());
                checkRealNumberOverflow(d, objValueOf2, objValueOf3, psiPolyadicExpression);
            } else if ((objValueOf2 instanceof Float) || (objValueOf3 instanceof Float)) {
                d = new Float(((Number) objValueOf2).floatValue() % number.floatValue());
                checkRealNumberOverflow(d, objValueOf2, objValueOf3, psiPolyadicExpression);
            } else {
                if ((objValueOf2 instanceof Long) || (objValueOf3 instanceof Long)) {
                    long jLongValue9 = ((Number) objValueOf2).longValue();
                    long jLongValue10 = number.longValue();
                    checkDivisionOverflow(jLongValue9, jLongValue10, Long.MIN_VALUE, psiPolyadicExpression);
                    if (jLongValue10 == 0) {
                        return null;
                    }
                    return Long.valueOf(jLongValue9 % jLongValue10);
                }
                int iIntValue9 = ((Number) objValueOf2).intValue();
                int iIntValue10 = number.intValue();
                checkDivisionOverflow(iIntValue9, iIntValue10, -2147483648L, psiPolyadicExpression);
                if (iIntValue10 == 0) {
                    return null;
                }
                return Integer.valueOf(iIntValue9 % iIntValue10);
            }
            return d;
        }
        if (iElementType == JavaTokenType.LTLT) {
            if (objValueOf2 instanceof Character) {
                objValueOf2 = Integer.valueOf(((Character) objValueOf2).charValue());
            }
            if (objValueOf3 instanceof Character) {
                objValueOf3 = Integer.valueOf(((Character) objValueOf3).charValue());
            }
            if (!isIntegral(objValueOf2) || !isIntegral(objValueOf3)) {
                return null;
            }
            if (objValueOf2 instanceof Long) {
                long jLongValue11 = ((Number) objValueOf2).longValue();
                long jLongValue12 = ((Number) objValueOf3).longValue();
                Long lValueOf4 = Long.valueOf(jLongValue11 << ((int) jLongValue12));
                checkMultiplicationOverflow(lValueOf4.longValue(), jLongValue11, (long) Math.pow(2.0d, jLongValue12 & 63), psiPolyadicExpression);
                return lValueOf4;
            }
            int iIntValue11 = ((Number) objValueOf2).intValue();
            int iIntValue12 = ((Number) objValueOf3).intValue();
            Integer numValueOf4 = Integer.valueOf(iIntValue11 << iIntValue12);
            checkMultiplicationOverflow(numValueOf4.intValue(), iIntValue11, (long) Math.pow(2.0d, iIntValue12 & 31), psiPolyadicExpression);
            return numValueOf4;
        }
        if (iElementType == JavaTokenType.GTGT) {
            if (objValueOf2 instanceof Character) {
                objValueOf2 = Integer.valueOf(((Character) objValueOf2).charValue());
            }
            if (objValueOf3 instanceof Character) {
                objValueOf3 = Integer.valueOf(((Character) objValueOf3).charValue());
            }
            if (!isIntegral(objValueOf2) || !isIntegral(objValueOf3)) {
                return null;
            }
            if (objValueOf2 instanceof Long) {
                return Long.valueOf(((Number) objValueOf2).longValue() >> ((int) ((Number) objValueOf3).longValue()));
            }
            return Integer.valueOf(((Number) objValueOf2).intValue() >> ((Number) objValueOf3).intValue());
        }
        if (iElementType == JavaTokenType.GTGTGT) {
            if (objValueOf2 instanceof Character) {
                objValueOf2 = Integer.valueOf(((Character) objValueOf2).charValue());
            }
            if (objValueOf3 instanceof Character) {
                objValueOf3 = Integer.valueOf(((Character) objValueOf3).charValue());
            }
            if (!isIntegral(objValueOf2) || !isIntegral(objValueOf3)) {
                return null;
            }
            if (objValueOf2 instanceof Long) {
                return Long.valueOf(((Number) objValueOf2).longValue() >>> ((int) ((Number) objValueOf3).longValue()));
            }
            return Integer.valueOf(((Number) objValueOf2).intValue() >>> ((Number) objValueOf3).intValue());
        }
        if (iElementType == JavaTokenType.AND) {
            if (objValueOf2 instanceof Character) {
                objValueOf2 = Integer.valueOf(((Character) objValueOf2).charValue());
            }
            if (objValueOf3 instanceof Character) {
                objValueOf3 = Integer.valueOf(((Character) objValueOf3).charValue());
            }
            if (isIntegral(objValueOf2) && isIntegral(objValueOf3)) {
                return ((objValueOf2 instanceof Long) || (objValueOf3 instanceof Long)) ? Long.valueOf(((Number) objValueOf3).longValue() & ((Number) objValueOf2).longValue()) : Integer.valueOf(((Number) objValueOf2).intValue() & ((Number) objValueOf3).intValue());
            }
            if ((objValueOf2 instanceof Boolean) && (objValueOf3 instanceof Boolean)) {
                return Boolean.valueOf(((Boolean) objValueOf2).booleanValue() && ((Boolean) objValueOf3).booleanValue());
            }
            return null;
        }
        if (iElementType == JavaTokenType.OR) {
            if (objValueOf2 instanceof Character) {
                objValueOf2 = Integer.valueOf(((Character) objValueOf2).charValue());
            }
            if (objValueOf3 instanceof Character) {
                objValueOf3 = Integer.valueOf(((Character) objValueOf3).charValue());
            }
            if (isIntegral(objValueOf2) && isIntegral(objValueOf3)) {
                return ((objValueOf2 instanceof Long) || (objValueOf3 instanceof Long)) ? Long.valueOf(((Number) objValueOf3).longValue() | ((Number) objValueOf2).longValue()) : Integer.valueOf(((Number) objValueOf2).intValue() | ((Number) objValueOf3).intValue());
            }
            if (!(objValueOf2 instanceof Boolean) || !(objValueOf3 instanceof Boolean)) {
                return null;
            }
            if (!((Boolean) objValueOf2).booleanValue() && !((Boolean) objValueOf3).booleanValue()) {
                z3 = false;
            }
            return Boolean.valueOf(z3);
        }
        if (iElementType != JavaTokenType.XOR) {
            return null;
        }
        if (objValueOf2 instanceof Character) {
            objValueOf2 = Integer.valueOf(((Character) objValueOf2).charValue());
        }
        if (objValueOf3 instanceof Character) {
            objValueOf3 = Integer.valueOf(((Character) objValueOf3).charValue());
        }
        if (isIntegral(objValueOf2) && isIntegral(objValueOf3)) {
            return ((objValueOf2 instanceof Long) || (objValueOf3 instanceof Long)) ? Long.valueOf(((Number) objValueOf3).longValue() ^ ((Number) objValueOf2).longValue()) : Integer.valueOf(((Number) objValueOf2).intValue() ^ ((Number) objValueOf3).intValue());
        }
        if ((objValueOf2 instanceof Boolean) && (objValueOf3 instanceof Boolean)) {
            return Boolean.valueOf(((Boolean) objValueOf2).booleanValue() ^ ((Boolean) objValueOf3).booleanValue());
        }
        return null;
    }

    private static String computeValueToString(Object obj) {
        if (obj instanceof PsiType) {
            boolean z = obj instanceof PsiArrayType;
            String str = ExternalAnnotationProvider.CLASS_PREFIX;
            if (z) {
                return ExternalAnnotationProvider.CLASS_PREFIX + ClassUtil.getClassObjectPresentation((PsiType) obj);
            }
            PsiType psiType = (PsiType) obj;
            PsiClass psiClassResolveClassInType = PsiUtil.resolveClassInType(psiType);
            if (psiClassResolveClassInType == null) {
                str = "";
            } else if (psiClassResolveClassInType.isInterface()) {
                str = "interface ";
            }
            return str + psiType.getCanonicalText();
        }
        return obj.toString();
    }

    private static Boolean handleEqualityComparison(Object obj, Object obj2, IElementType iElementType) {
        if (((obj instanceof String) && (obj2 instanceof String)) || ((obj instanceof Boolean) && (obj2 instanceof Boolean))) {
            return Boolean.valueOf(obj.equals(obj2) == (iElementType == JavaTokenType.EQEQ));
        }
        return compareNumbers(obj, obj2, iElementType);
    }

    /* JADX WARN: Code duplicated, block: B:57:0x00ab  */
    /* JADX WARN: Code duplicated, block: B:58:0x00ad  */
    private static Boolean compareNumbers(Object obj, Object obj2, IElementType iElementType) {
        int iCompare;
        if (obj instanceof Character) {
            obj = Integer.valueOf(((Character) obj).charValue());
        }
        if (obj2 instanceof Character) {
            obj2 = Integer.valueOf(((Character) obj2).charValue());
        }
        if (!(obj instanceof Number) || !(obj2 instanceof Number)) {
            return null;
        }
        Number number = (Number) obj;
        Number number2 = (Number) obj2;
        int i = -1;
        if ((number instanceof Double) || (number2 instanceof Double)) {
            double dDoubleValue = number.doubleValue();
            double dDoubleValue2 = number2.doubleValue();
            if (Double.isNaN(dDoubleValue) || Double.isNaN(dDoubleValue2)) {
                return Boolean.valueOf(iElementType == JavaTokenType.NE);
            }
            if (dDoubleValue >= dDoubleValue2) {
                if (dDoubleValue == dDoubleValue2) {
                    i = 0;
                } else {
                    i = 1;
                }
            }
            iCompare = i;
        } else if ((number instanceof Float) || (number2 instanceof Float)) {
            float fFloatValue = number.floatValue();
            float fFloatValue2 = number2.floatValue();
            if (Float.isNaN(fFloatValue) || Float.isNaN(fFloatValue2)) {
                return Boolean.valueOf(iElementType == JavaTokenType.NE);
            }
            if (fFloatValue >= fFloatValue2) {
                if (fFloatValue == fFloatValue2) {
                    i = 0;
                } else {
                    i = 1;
                }
            }
            iCompare = i;
        } else if ((number instanceof Long) || (number2 instanceof Long)) {
            iCompare = Long.compare(number.longValue(), number2.longValue());
        } else {
            iCompare = Integer.compare(number.intValue(), number2.intValue());
        }
        if (iElementType == JavaTokenType.EQEQ) {
            return Boolean.valueOf(iCompare == 0);
        }
        if (iElementType == JavaTokenType.LT) {
            return Boolean.valueOf(iCompare < 0);
        }
        if (iElementType == JavaTokenType.LE) {
            return Boolean.valueOf(iCompare <= 0);
        }
        if (iElementType == JavaTokenType.GT) {
            return Boolean.valueOf(iCompare > 0);
        }
        if (iElementType == JavaTokenType.GE) {
            return Boolean.valueOf(iCompare >= 0);
        }
        if (iElementType == JavaTokenType.NE) {
            return Boolean.valueOf(iCompare != 0);
        }
        throw new IllegalArgumentException("Unexpected operator: " + iElementType);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitPrefixExpression(PsiPrefixExpression psiPrefixExpression) {
        PsiExpression operand = psiPrefixExpression.getOperand();
        Object storedValue = getStoredValue(operand);
        Object objValueOf = null;
        if (storedValue == null) {
            this.myResult = null;
            return;
        }
        IElementType operationTokenType = psiPrefixExpression.getOperationTokenType();
        if (operationTokenType == JavaTokenType.MINUS) {
            if (storedValue instanceof Character) {
                storedValue = Integer.valueOf(((Character) storedValue).charValue());
            }
            if (storedValue instanceof Number) {
                if (storedValue instanceof Double) {
                    objValueOf = new Double(-((Number) storedValue).doubleValue());
                } else if (storedValue instanceof Float) {
                    objValueOf = new Float(-((Number) storedValue).floatValue());
                } else if (storedValue instanceof Long) {
                    Number number = (Number) storedValue;
                    objValueOf = Long.valueOf(-number.longValue());
                    if (this.myThrowExceptionOnOverflow && !(operand instanceof PsiLiteralExpression) && number.longValue() == Long.MIN_VALUE) {
                        throw new ConstantEvaluationOverflowException(psiPrefixExpression);
                    }
                } else {
                    Number number2 = (Number) storedValue;
                    objValueOf = Integer.valueOf(-number2.intValue());
                    if (this.myThrowExceptionOnOverflow && !(operand instanceof PsiLiteralExpression) && number2.intValue() == Integer.MIN_VALUE) {
                        throw new ConstantEvaluationOverflowException(psiPrefixExpression);
                    }
                }
            }
        } else if (operationTokenType == JavaTokenType.PLUS) {
            if (storedValue instanceof Character) {
                storedValue = Integer.valueOf(((Character) storedValue).charValue());
            }
            if (storedValue instanceof Number) {
                objValueOf = storedValue;
            }
        } else if (operationTokenType == JavaTokenType.TILDE) {
            if (storedValue instanceof Character) {
                storedValue = Integer.valueOf(((Character) storedValue).charValue());
            }
            if (isIntegral(storedValue)) {
                if (storedValue instanceof Long) {
                    objValueOf = Long.valueOf(~((Number) storedValue).longValue());
                } else {
                    objValueOf = Integer.valueOf(~((Number) storedValue).intValue());
                }
            }
        } else if (operationTokenType == JavaTokenType.EXCL && (storedValue instanceof Boolean)) {
            objValueOf = Boolean.valueOf(!((Boolean) storedValue).booleanValue());
        }
        this.myResult = objValueOf;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitParenthesizedExpression(PsiParenthesizedExpression psiParenthesizedExpression) {
        this.myResult = getStoredValue(psiParenthesizedExpression.getExpression());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitMethodCallExpression(PsiMethodCallExpression psiMethodCallExpression) {
        PsiConstantEvaluationHelper.AuxEvaluator auxEvaluator = this.myAuxEvaluator;
        this.myResult = auxEvaluator != null ? auxEvaluator.computeExpression(psiMethodCallExpression, this) : null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitClassObjectAccessExpression(PsiClassObjectAccessExpression psiClassObjectAccessExpression) {
        PsiClassReferenceType psiClassReferenceType;
        PsiClass psiClassResolve;
        PsiType type = psiClassObjectAccessExpression.getOperand().getType();
        if ((type instanceof PsiClassReferenceType) && (psiClassResolve = (psiClassReferenceType = (PsiClassReferenceType) type).resolve()) != null) {
            type = JavaPsiFacade.getElementFactory(psiClassObjectAccessExpression.getProject()).createType(psiClassResolve, psiClassReferenceType.getParameters());
        }
        this.myResult = type;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor
    public void visitReferenceExpression(PsiReferenceExpression psiReferenceExpression) {
        PsiConstantEvaluationHelper.AuxEvaluator auxEvaluator;
        PsiExpression qualifierExpression = psiReferenceExpression.getQualifierExpression();
        while (true) {
            if (qualifierExpression == null) {
                break;
            }
            if (!(qualifierExpression instanceof PsiReferenceExpression)) {
                this.myResult = null;
                return;
            }
            PsiReferenceExpression psiReferenceExpression2 = (PsiReferenceExpression) qualifierExpression;
            PsiElement psiElementResolve = psiReferenceExpression2.resolve();
            if (psiElementResolve instanceof PsiPackage) {
                break;
            }
            if (!(psiElementResolve instanceof PsiClass)) {
                this.myResult = null;
                return;
            }
            qualifierExpression = psiReferenceExpression2.getQualifierExpression();
        }
        PsiElement psiElementResolve2 = psiReferenceExpression.resolve();
        if (psiElementResolve2 instanceof PsiEnumConstant) {
            String name2 = ((PsiEnumConstant) psiElementResolve2).getName();
            PsiReferenceExpression psiReferenceExpression3 = (PsiReferenceExpression) psiReferenceExpression.getQualifier();
            if (psiReferenceExpression3 == null) {
                return;
            }
            PsiElement psiElementResolve3 = psiReferenceExpression3.resolve();
            if (psiElementResolve3 instanceof PsiClass) {
                try {
                    this.myResult = Enum.valueOf(Class.forName(ClassUtil.getJVMClassName((PsiClass) psiElementResolve3)), name2);
                    return;
                } catch (Throwable unused) {
                    return;
                }
            }
            return;
        }
        if (psiElementResolve2 instanceof PsiVariable) {
            PsiVariable psiVariable = (PsiVariable) psiElementResolve2;
            Set<PsiVariable> set = this.myVisitedVars;
            if (set != null && set.contains(psiVariable)) {
                this.myResult = null;
                return;
            }
            Set<PsiVariable> set2 = this.myVisitedVars;
            if (set2 == null) {
                this.myVisitedVars = new HashSet();
            }
            this.myVisitedVars.add(psiVariable);
            try {
                Object objComputeConstantValue = psiVariable instanceof PsiVariableEx ? ((PsiVariableEx) psiVariable).computeConstantValue(this.myVisitedVars) : null;
                this.myResult = objComputeConstantValue;
                if (objComputeConstantValue == null && (auxEvaluator = this.myAuxEvaluator) != null) {
                    this.myResult = auxEvaluator.computeExpression(psiReferenceExpression, this);
                }
                return;
            } finally {
                this.myVisitedVars.remove(psiVariable);
                this.myVisitedVars = set2;
            }
        }
        this.myResult = null;
    }

    private static boolean isIntegral(Object obj) {
        return (obj instanceof Long) || (obj instanceof Integer) || (obj instanceof Short) || (obj instanceof Byte) || (obj instanceof Character);
    }

    private void checkDivisionOverflow(long j, long j2, long j3, PsiElement psiElement) {
        if (this.myThrowExceptionOnOverflow) {
            if (j2 == 0) {
                throw new ConstantEvaluationOverflowException(psiElement);
            }
            if (j2 == -1 && j == j3) {
                throw new ConstantEvaluationOverflowException(psiElement);
            }
        }
    }

    private void checkMultiplicationOverflow(long j, long j2, long j3, PsiElement psiElement) {
        if (!this.myThrowExceptionOnOverflow || j3 == 0 || j2 == 0) {
            return;
        }
        if (j / j3 == j2) {
            if (!((j2 < 0) ^ (((j3 > 0L ? 1 : (j3 == 0L ? 0 : -1)) < 0) != ((j > 0L ? 1 : (j == 0L ? 0 : -1)) < 0)))) {
                return;
            }
        }
        throw new ConstantEvaluationOverflowException(psiElement);
    }

    private void checkAdditionOverflow(boolean z, boolean z2, boolean z3, PsiElement psiElement) {
        if (this.myThrowExceptionOnOverflow && z2 == z3 && z2 != z) {
            throw new ConstantEvaluationOverflowException(psiElement);
        }
    }

    private void checkRealNumberOverflow(Object obj, Object obj2, Object obj3, PsiElement psiElement) {
        if (this.myThrowExceptionOnOverflow) {
            if ((obj2 instanceof Float) && ((Float) obj2).isInfinite()) {
                return;
            }
            if ((obj2 instanceof Double) && ((Double) obj2).isInfinite()) {
                return;
            }
            if ((obj3 instanceof Float) && ((Float) obj3).isInfinite()) {
                return;
            }
            if ((obj3 instanceof Double) && ((Double) obj3).isInfinite()) {
                return;
            }
            if ((obj instanceof Float) && ((Float) obj).isInfinite()) {
                throw new ConstantEvaluationOverflowException(psiElement);
            }
            if ((obj instanceof Double) && ((Double) obj).isInfinite()) {
                throw new ConstantEvaluationOverflowException(psiElement);
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiConstantEvaluationHelper.AuxEvaluator
    public Object computeExpression(PsiExpression psiExpression, PsiConstantEvaluationHelper.AuxEvaluator auxEvaluator) {
        if (psiExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (auxEvaluator == null) {
            $$$reportNull$$$0(1);
        }
        return JavaConstantExpressionEvaluator.computeConstantExpression(psiExpression, this.myVisitedVars, this.myThrowExceptionOnOverflow, auxEvaluator);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiConstantEvaluationHelper.AuxEvaluator
    public ConcurrentMap<PsiElement, Object> getCacheMap(boolean z) {
        throw new AssertionError("should not be called");
    }
}
