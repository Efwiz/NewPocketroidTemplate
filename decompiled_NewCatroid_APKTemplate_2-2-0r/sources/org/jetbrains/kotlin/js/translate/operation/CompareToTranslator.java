package org.jetbrains.kotlin.js.translate.operation;

import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsIntLiteral;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.JsDescriptorUtils;
import org.jetbrains.kotlin.js.translate.utils.PsiUtils;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;

/* JADX INFO: loaded from: classes5.dex */
public final class CompareToTranslator extends AbstractTranslator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final KtBinaryExpression expression;

    /* JADX WARN: Code duplicated, block: B:8:0x0014  */
    /* JADX WARN: Code duplicated, block: B:9:0x0019  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "expression";
        } else if (i == 2) {
            objArr[0] = "context";
        } else if (i == 3) {
            objArr[0] = "expression";
        } else if (i != 4) {
            objArr[0] = "operationToken";
        } else {
            objArr[0] = "context";
        }
        objArr[1] = "org/jetbrains/kotlin/js/translate/operation/CompareToTranslator";
        if (i == 1 || i == 2) {
            objArr[2] = "translate";
        } else if (i == 3 || i == 4) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "isCompareToCall";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public static boolean isCompareToCall(KtToken ktToken, CallableDescriptor callableDescriptor) {
        if (ktToken == null) {
            $$$reportNull$$$0(0);
        }
        if (!OperatorConventions.COMPARISON_OPERATIONS.contains(ktToken) || callableDescriptor == null) {
            return false;
        }
        return JsDescriptorUtils.isCompareTo(callableDescriptor);
    }

    public static JsExpression translate(KtBinaryExpression ktBinaryExpression, TranslationContext translationContext) {
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(1);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(2);
        }
        return new CompareToTranslator(ktBinaryExpression, translationContext).translate();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private CompareToTranslator(KtBinaryExpression ktBinaryExpression, TranslationContext translationContext) {
        super(translationContext);
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(3);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(4);
        }
        this.expression = ktBinaryExpression;
        BindingUtils.getCallableDescriptorForOperationExpression(translationContext.bindingContext(), ktBinaryExpression);
    }

    private JsExpression translate() {
        return new JsBinaryOperation(OperatorTable.getBinaryOperator(PsiUtils.getOperationToken(this.expression)), BinaryOperationTranslator.translateAsOverloadedCall(this.expression, context()), new JsIntLiteral(0));
    }
}
