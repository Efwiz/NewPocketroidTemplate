package org.jetbrains.kotlin.js.translate.operation;

import java.util.Collections;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.callTranslator.CallInfoKt;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;

/* JADX INFO: loaded from: classes5.dex */
public final class IntrinsicIncrementTranslator extends IncrementTranslator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
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
            objArr[0] = "org/jetbrains/kotlin/js/translate/operation/IntrinsicIncrementTranslator";
        }
        if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/js/translate/operation/IntrinsicIncrementTranslator";
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
    public IntrinsicIncrementTranslator(KtUnaryExpression ktUnaryExpression, TranslationContext translationContext) {
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
        JsExpression jsExpressionApply = translationContext.intrinsics().getFunctionIntrinsic((FunctionDescriptor) this.resolvedCall.getResultingDescriptor(), translationContext).apply(CallInfoKt.getCallInfo(translationContext, this.resolvedCall, jsExpression), Collections.emptyList(), translationContext);
        if (jsExpressionApply == null) {
            $$$reportNull$$$0(4);
        }
        return jsExpressionApply;
    }
}
