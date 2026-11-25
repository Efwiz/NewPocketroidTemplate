package org.jetbrains.kotlin.js.translate.operation;

import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.callTranslator.CallTranslator;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;

/* JADX INFO: loaded from: classes5.dex */
public final class OverloadedIncrementTranslator extends IncrementTranslator {
    private final ResolvedCall<? extends FunctionDescriptor> resolvedCall;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 4 ? 3 : 2];
        if (i == 1 || i == 2) {
            objArr[0] = "context";
        } else if (i == 3) {
            objArr[0] = "receiver";
        } else if (i != 4) {
            objArr[0] = "expression";
        } else {
            objArr[0] = "org/jetbrains/kotlin/js/translate/operation/OverloadedIncrementTranslator";
        }
        if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/js/translate/operation/OverloadedIncrementTranslator";
        } else {
            objArr[1] = "operationExpression";
        }
        if (i == 2 || i == 3) {
            objArr[2] = "operationExpression";
        } else if (i != 4) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 4) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    OverloadedIncrementTranslator(KtUnaryExpression ktUnaryExpression, TranslationContext translationContext) {
        super(ktUnaryExpression, translationContext);
        if (ktUnaryExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(1);
        }
        this.resolvedCall = CallUtilKt.getFunctionResolvedCallWithAssert(ktUnaryExpression, translationContext.bindingContext());
    }

    @Override // org.jetbrains.kotlin.js.translate.operation.IncrementTranslator
    protected JsExpression operationExpression(TranslationContext translationContext, JsExpression jsExpression) {
        if (translationContext == null) {
            $$$reportNull$$$0(2);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(3);
        }
        JsExpression jsExpressionTranslate = CallTranslator.translate(translationContext, this.resolvedCall, jsExpression);
        if (jsExpressionTranslate == null) {
            $$$reportNull$$$0(4);
        }
        return jsExpressionTranslate;
    }
}
