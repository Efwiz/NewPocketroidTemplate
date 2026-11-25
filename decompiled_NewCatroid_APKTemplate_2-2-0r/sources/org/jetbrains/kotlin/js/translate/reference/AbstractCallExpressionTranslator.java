package org.jetbrains.kotlin.js.translate.reference;

import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractCallExpressionTranslator extends AbstractTranslator {
    protected final KtCallExpression expression;
    protected final JsExpression receiver;
    protected final ResolvedCall<? extends FunctionDescriptor> resolvedCall;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "expression";
        } else {
            objArr[0] = "context";
        }
        objArr[1] = "org/jetbrains/kotlin/js/translate/reference/AbstractCallExpressionTranslator";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected AbstractCallExpressionTranslator(KtCallExpression ktCallExpression, JsExpression jsExpression, TranslationContext translationContext) {
        super(translationContext);
        if (ktCallExpression == null) {
            $$$reportNull$$$0(0);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(1);
        }
        this.expression = ktCallExpression;
        this.resolvedCall = CallUtilKt.getFunctionResolvedCallWithAssert(ktCallExpression, bindingContext());
        this.receiver = jsExpression;
    }
}
