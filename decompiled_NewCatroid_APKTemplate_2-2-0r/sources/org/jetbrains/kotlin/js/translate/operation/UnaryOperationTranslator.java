package org.jetbrains.kotlin.js.translate.operation;

import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.callTranslator.CallTranslator;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.PsiUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;

/* JADX INFO: loaded from: classes5.dex */
public final class UnaryOperationTranslator {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 5 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 5 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "context";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                objArr[0] = "org/jetbrains/kotlin/js/translate/operation/UnaryOperationTranslator";
                break;
            case 7:
            default:
                objArr[0] = "expression";
                break;
            case 8:
                objArr[0] = "baseExpression";
                break;
        }
        if (i == 2 || i == 3 || i == 4 || i == 5 || i == 6) {
            objArr[1] = "translate";
        } else {
            objArr[1] = "org/jetbrains/kotlin/js/translate/operation/UnaryOperationTranslator";
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                break;
            case 7:
            case 8:
                objArr[2] = "isExclForBinaryEqualLikeExpr";
                break;
            default:
                objArr[2] = "translate";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 5 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private UnaryOperationTranslator() {
    }

    public static JsExpression translate(KtUnaryExpression ktUnaryExpression, TranslationContext translationContext) {
        if (ktUnaryExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(1);
        }
        IElementType referencedNameElementType = ktUnaryExpression.getOperationReference().getReferencedNameElementType();
        if (referencedNameElementType == KtTokens.EXCLEXCL) {
            KtExpression baseExpression = PsiUtils.getBaseExpression(ktUnaryExpression);
            JsExpression jsExpressionSure = TranslationUtils.sure(baseExpression, Translation.translateAsExpression(baseExpression, translationContext), translationContext);
            if (jsExpressionSure == null) {
                $$$reportNull$$$0(2);
            }
            return jsExpressionSure;
        }
        if (referencedNameElementType == KtTokens.MINUS && (PsiUtils.getBaseExpression(ktUnaryExpression) instanceof KtConstantExpression)) {
            Object compileTimeValue = BindingUtils.getCompileTimeValue(translationContext.bindingContext(), ktUnaryExpression, ConstantExpressionEvaluator.getConstant(ktUnaryExpression, translationContext.bindingContext()));
            if (compileTimeValue instanceof Long) {
                JsExpression jsExpressionNewLong = JsAstUtils.newLong(((Long) compileTimeValue).longValue());
                if (jsExpressionNewLong == null) {
                    $$$reportNull$$$0(3);
                }
                return jsExpressionNewLong;
            }
        }
        if (IncrementTranslator.isIncrement(referencedNameElementType)) {
            JsExpression jsExpressionTranslate = IncrementTranslator.translate(ktUnaryExpression, translationContext);
            if (jsExpressionTranslate == null) {
                $$$reportNull$$$0(4);
            }
            return jsExpressionTranslate;
        }
        JsExpression jsExpressionTranslateBaseExpression = TranslationUtils.translateBaseExpression(translationContext, ktUnaryExpression);
        if (isExclForBinaryEqualLikeExpr(ktUnaryExpression, jsExpressionTranslateBaseExpression)) {
            JsExpression jsExpressionTranslateExclForBinaryEqualLikeExpr = TranslationUtils.translateExclForBinaryEqualLikeExpr((JsBinaryOperation) jsExpressionTranslateBaseExpression);
            if (jsExpressionTranslateExclForBinaryEqualLikeExpr == null) {
                $$$reportNull$$$0(5);
            }
            return jsExpressionTranslateExclForBinaryEqualLikeExpr;
        }
        JsExpression jsExpressionTranslate2 = CallTranslator.translate(translationContext, CallUtilKt.getFunctionResolvedCallWithAssert(ktUnaryExpression, translationContext.bindingContext()), jsExpressionTranslateBaseExpression);
        if (jsExpressionTranslate2 == null) {
            $$$reportNull$$$0(6);
        }
        return jsExpressionTranslate2;
    }

    private static boolean isExclForBinaryEqualLikeExpr(KtUnaryExpression ktUnaryExpression, JsExpression jsExpression) {
        if (ktUnaryExpression == null) {
            $$$reportNull$$$0(7);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(8);
        }
        if (PsiUtils.getOperationToken(ktUnaryExpression).equals(KtTokens.EXCL) && (jsExpression instanceof JsBinaryOperation)) {
            return TranslationUtils.isEqualLikeOperator(((JsBinaryOperation) jsExpression).getOperator());
        }
        return false;
    }
}
