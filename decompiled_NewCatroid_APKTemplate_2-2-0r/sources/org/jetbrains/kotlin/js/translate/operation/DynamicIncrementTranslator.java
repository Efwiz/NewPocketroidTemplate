package org.jetbrains.kotlin.js.translate.operation;

import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsPostfixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperator;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.utils.PsiUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtUnaryExpression;

/* JADX INFO: loaded from: classes5.dex */
public class DynamicIncrementTranslator extends IncrementTranslator {
    /* JADX WARN: Code duplicated, block: B:24:0x0036  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 4 ? 3 : 2];
        if (i == 1 || i == 3) {
            objArr[0] = "context";
        } else if (i == 4) {
            objArr[0] = "org/jetbrains/kotlin/js/translate/operation/DynamicIncrementTranslator";
        } else if (i == 5) {
            objArr[0] = "context";
        } else if (i == 6) {
            objArr[0] = "receiver";
        } else if (i != 7) {
            objArr[0] = "expression";
        } else {
            objArr[0] = "leftExpression";
        }
        if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/js/translate/operation/DynamicIncrementTranslator";
        } else {
            objArr[1] = "translate";
        }
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "<init>";
                break;
            case 4:
                break;
            case 5:
            case 6:
                objArr[2] = "operationExpression";
                break;
            case 7:
                objArr[2] = "unaryAsBinary";
                break;
            default:
                objArr[2] = "doTranslate";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 4) {
            throw new IllegalStateException(str2);
        }
    }

    public static JsExpression doTranslate(KtUnaryExpression ktUnaryExpression, TranslationContext translationContext) {
        if (ktUnaryExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(1);
        }
        return new DynamicIncrementTranslator(ktUnaryExpression, translationContext).translate();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private DynamicIncrementTranslator(KtUnaryExpression ktUnaryExpression, TranslationContext translationContext) {
        super(ktUnaryExpression, translationContext);
        if (ktUnaryExpression == null) {
            $$$reportNull$$$0(2);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(3);
        }
    }

    private JsExpression translate() {
        if (TranslationUtils.isSimpleNameExpressionNotDelegatedLocalVar(this.expression.getBaseExpression(), context())) {
            return primitiveExpressionIncrement();
        }
        JsExpression jsExpressionTranslateIncrementExpression = translateIncrementExpression();
        if (jsExpressionTranslateIncrementExpression == null) {
            $$$reportNull$$$0(4);
        }
        return jsExpressionTranslateIncrementExpression;
    }

    private JsExpression primitiveExpressionIncrement() {
        JsUnaryOperator unaryOperator = OperatorTable.getUnaryOperator(PsiUtils.getOperationToken(this.expression));
        JsExpression jsExpressionTranslateAsGet = this.accessTranslator.translateAsGet();
        if (PsiUtils.isPrefix(this.expression)) {
            return new JsPrefixOperation(unaryOperator, jsExpressionTranslateAsGet);
        }
        return new JsPostfixOperation(unaryOperator, jsExpressionTranslateAsGet);
    }

    @Override // org.jetbrains.kotlin.js.translate.operation.IncrementTranslator
    protected JsExpression operationExpression(TranslationContext translationContext, JsExpression jsExpression) {
        if (translationContext == null) {
            $$$reportNull$$$0(5);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(6);
        }
        return unaryAsBinary(jsExpression);
    }

    private JsBinaryOperation unaryAsBinary(JsExpression jsExpression) {
        if (jsExpression == null) {
            $$$reportNull$$$0(7);
        }
        JsIntLiteral jsIntLiteral = new JsIntLiteral(1);
        KtToken operationToken = PsiUtils.getOperationToken(this.expression);
        if (operationToken.equals(KtTokens.PLUSPLUS)) {
            return new JsBinaryOperation(JsBinaryOperator.ADD, jsExpression, jsIntLiteral);
        }
        if (operationToken.equals(KtTokens.MINUSMINUS)) {
            return new JsBinaryOperation(JsBinaryOperator.SUB, jsExpression, jsIntLiteral);
        }
        throw new AssertionError("This method should be called only for increment and decrement operators");
    }
}
