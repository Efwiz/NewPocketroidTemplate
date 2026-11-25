package org.jetbrains.kotlin.js.translate.operation;

import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.reference.AccessTranslator;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.PsiUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;

/* JADX INFO: loaded from: classes5.dex */
public final class IntrinsicAssignmentTranslator extends AssignmentTranslator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final AccessTranslator accessTranslator;
    private final JsExpression right;
    private final JsBlock rightBlock;
    private final boolean rightExpressionTrivial;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5 || i == 6 || i == 7) ? 2 : 3];
        if (i == 1 || i == 3) {
            objArr[0] = "context";
        } else if (i == 4 || i == 5 || i == 6 || i == 7) {
            objArr[0] = "org/jetbrains/kotlin/js/translate/operation/IntrinsicAssignmentTranslator";
        } else {
            objArr[0] = "expression";
        }
        if (i == 4) {
            objArr[1] = "translateAsAssignToCounterpart";
        } else if (i == 5) {
            objArr[1] = "getCounterpartOperator";
        } else if (i == 6) {
            objArr[1] = "getAssignmentOperator";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/js/translate/operation/IntrinsicAssignmentTranslator";
        } else {
            objArr[1] = "translateAsPlainAssignment";
        }
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "<init>";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
                break;
            default:
                objArr[2] = "doTranslate";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static JsExpression doTranslate(KtBinaryExpression ktBinaryExpression, TranslationContext translationContext) {
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(1);
        }
        return new IntrinsicAssignmentTranslator(ktBinaryExpression, translationContext).translate();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private IntrinsicAssignmentTranslator(KtBinaryExpression ktBinaryExpression, TranslationContext translationContext) {
        super(ktBinaryExpression, translationContext);
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(2);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(3);
        }
        JsBlock jsBlock = new JsBlock();
        this.rightBlock = jsBlock;
        this.right = translateRightExpression(translationContext, ktBinaryExpression);
        boolean zIsEmpty = jsBlock.isEmpty();
        this.rightExpressionTrivial = zIsEmpty;
        this.accessTranslator = createAccessTranslator(ktBinaryExpression.getLeft(), !zIsEmpty);
    }

    private JsExpression translateRightExpression(TranslationContext translationContext, KtBinaryExpression ktBinaryExpression) {
        JsExpression jsExpressionTranslateRightExpression = TranslationUtils.translateRightExpression(translationContext, ktBinaryExpression, this.rightBlock);
        KotlinType type = translationContext.bindingContext().getType(ktBinaryExpression.getLeft());
        KotlinType type2 = translationContext.bindingContext().getType(ktBinaryExpression.getRight());
        if (type2 == null || !KotlinBuiltIns.isCharOrNullableChar(type2)) {
            return jsExpressionTranslateRightExpression;
        }
        if (type == null || !KotlinBuiltIns.isStringOrNullableString(type)) {
            return (type == null || KotlinBuiltIns.isCharOrNullableChar(type)) ? jsExpressionTranslateRightExpression : TranslationUtils.charToBoxedChar(translationContext, jsExpressionTranslateRightExpression);
        }
        return JsAstUtils.charToString(jsExpressionTranslateRightExpression);
    }

    private JsExpression translate() {
        if (PsiUtils.isAssignment(PsiUtils.getOperationToken(this.expression))) {
            return translateAsPlainAssignment();
        }
        return translateAsAssignmentOperation();
    }

    private JsExpression translateAsAssignmentOperation() {
        if (TranslationUtils.isSimpleNameExpressionNotDelegatedLocalVar(this.expression.getLeft(), context()) && this.rightExpressionTrivial) {
            return translateAsPlainAssignmentOperation();
        }
        return translateAsAssignToCounterpart();
    }

    private JsExpression translateAsAssignToCounterpart() {
        JsBinaryOperator counterpartOperator = getCounterpartOperator();
        JsExpression jsExpressionTranslateAsGet = this.accessTranslator.translateAsGet();
        if (!this.rightExpressionTrivial) {
            jsExpressionTranslateAsGet = context().defineTemporary(jsExpressionTranslateAsGet);
        }
        JsBinaryOperation jsBinaryOperation = new JsBinaryOperation(counterpartOperator, jsExpressionTranslateAsGet, this.right);
        context().addStatementsToCurrentBlockFrom(this.rightBlock);
        JsExpression jsExpressionTranslateAsSet = this.accessTranslator.translateAsSet(jsBinaryOperation);
        if (jsExpressionTranslateAsSet == null) {
            $$$reportNull$$$0(4);
        }
        return jsExpressionTranslateAsSet;
    }

    private JsBinaryOperator getCounterpartOperator() {
        JsBinaryOperator binaryOperator = OperatorTable.getBinaryOperator(OperatorConventions.ASSIGNMENT_OPERATION_COUNTERPARTS.get(PsiUtils.getOperationToken(this.expression)));
        if (binaryOperator == null) {
            $$$reportNull$$$0(5);
        }
        return binaryOperator;
    }

    private JsExpression translateAsPlainAssignmentOperation() {
        context().addStatementsToCurrentBlockFrom(this.rightBlock);
        return new JsBinaryOperation(getAssignmentOperator(), this.accessTranslator.translateAsGet(), this.right);
    }

    private JsBinaryOperator getAssignmentOperator() {
        JsBinaryOperator binaryOperator = OperatorTable.getBinaryOperator(PsiUtils.getOperationToken(this.expression));
        if (binaryOperator == null) {
            $$$reportNull$$$0(6);
        }
        return binaryOperator;
    }

    private JsExpression translateAsPlainAssignment() {
        context().addStatementsToCurrentBlockFrom(this.rightBlock);
        JsExpression jsExpressionTranslateAsSet = this.accessTranslator.translateAsSet(this.right);
        if (jsExpressionTranslateAsSet == null) {
            $$$reportNull$$$0(7);
        }
        return jsExpressionTranslateAsSet;
    }
}
