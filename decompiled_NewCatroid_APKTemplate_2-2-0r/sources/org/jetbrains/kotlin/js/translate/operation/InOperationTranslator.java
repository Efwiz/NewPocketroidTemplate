package org.jetbrains.kotlin.js.translate.operation;

import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.patterns.DescriptorPredicate;
import org.jetbrains.kotlin.js.patterns.PatternBuilder;
import org.jetbrains.kotlin.js.translate.callTranslator.CallTranslator;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;

/* JADX INFO: loaded from: classes5.dex */
public class InOperationTranslator extends AbstractTranslator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final JsExpression left;
    private final boolean negated;
    private final KtSimpleNameExpression operation;
    private final KtExpression right;
    private static final DescriptorPredicate INT_SPECIALIZATION_TEST = PatternBuilder.pattern("ranges.IntRange.contains");
    private static final DescriptorPredicate INT_RANGE_TEST = PatternBuilder.pattern("Int.rangeTo");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 7 || i == 10 || i == 11) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 7 || i == 10 || i == 11) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "left";
                break;
            case 2:
                objArr[0] = "right";
                break;
            case 3:
                objArr[0] = "operation";
                break;
            case 4:
            case 7:
            case 10:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/js/translate/operation/InOperationTranslator";
                break;
            case 5:
                objArr[0] = "call";
                break;
            case 6:
                objArr[0] = "rightTranslated";
                break;
            case 8:
                objArr[0] = "lowerExpression";
                break;
            case 9:
                objArr[0] = "upperExpression";
                break;
            default:
                objArr[0] = "context";
                break;
        }
        if (i == 4) {
            objArr[1] = "translate";
        } else if (i == 7) {
            objArr[1] = "translateGeneral";
        } else if (i == 10 || i == 11) {
            objArr[1] = "translateInt";
        } else {
            objArr[1] = "org/jetbrains/kotlin/js/translate/operation/InOperationTranslator";
        }
        switch (i) {
            case 4:
            case 7:
            case 10:
            case 11:
                break;
            case 5:
            case 6:
                objArr[2] = "translateGeneral";
                break;
            case 8:
            case 9:
                objArr[2] = "translateInt";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 7 && i != 10 && i != 11) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InOperationTranslator(TranslationContext translationContext, JsExpression jsExpression, KtExpression ktExpression, KtSimpleNameExpression ktSimpleNameExpression, boolean z) {
        super(translationContext);
        if (translationContext == null) {
            $$$reportNull$$$0(0);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(1);
        }
        if (ktExpression == null) {
            $$$reportNull$$$0(2);
        }
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(3);
        }
        this.left = jsExpression;
        this.right = ktExpression;
        this.operation = ktSimpleNameExpression;
        this.negated = z;
    }

    public JsExpression translate() {
        JsExpression jsExpressionTranslateInt;
        ResolvedCall<? extends FunctionDescriptor> functionResolvedCallWithAssert = CallUtilKt.getFunctionResolvedCallWithAssert(this.operation, bindingContext());
        if (!INT_SPECIALIZATION_TEST.test(functionResolvedCallWithAssert.getResultingDescriptor()) || (jsExpressionTranslateInt = translateInt()) == null) {
            return translateGeneral(functionResolvedCallWithAssert, Translation.translateAsExpression(this.right, context()));
        }
        if (jsExpressionTranslateInt == null) {
            $$$reportNull$$$0(4);
        }
        return jsExpressionTranslateInt;
    }

    private JsExpression translateGeneral(ResolvedCall<? extends FunctionDescriptor> resolvedCall, JsExpression jsExpression) {
        if (resolvedCall == null) {
            $$$reportNull$$$0(5);
        }
        if (jsExpression == null) {
            $$$reportNull$$$0(6);
        }
        JsExpression jsExpressionTranslate = CallTranslator.translate(context(), resolvedCall, jsExpression);
        if (this.negated) {
            jsExpressionTranslate = JsAstUtils.not(jsExpressionTranslate);
        }
        if (jsExpressionTranslate == null) {
            $$$reportNull$$$0(7);
        }
        return jsExpressionTranslate;
    }

    private JsExpression translateInt() {
        ResolvedCall<? extends CallableDescriptor> resolvedCallWithAssert = CallUtilKt.getResolvedCallWithAssert(this.right, bindingContext());
        if (!(resolvedCallWithAssert.getResultingDescriptor() instanceof FunctionDescriptor)) {
            return null;
        }
        if (INT_RANGE_TEST.test((FunctionDescriptor) resolvedCallWithAssert.getResultingDescriptor()) && (resolvedCallWithAssert.getDispatchReceiver() instanceof ExpressionReceiver)) {
            return translateInt(((ExpressionReceiver) resolvedCallWithAssert.getDispatchReceiver()).getExpression(), resolvedCallWithAssert.getCall().getValueArguments().get(0).getArgumentExpression());
        }
        return null;
    }

    private JsExpression translateInt(KtExpression ktExpression, KtExpression ktExpression2) {
        if (ktExpression == null) {
            $$$reportNull$$$0(8);
        }
        if (ktExpression2 == null) {
            $$$reportNull$$$0(9);
        }
        JsExpression jsExpressionTranslateAsExpression = Translation.translateAsExpression(ktExpression, context());
        JsExpression jsExpressionTranslateAsExpression2 = Translation.translateAsExpression(ktExpression2, context());
        if (!this.negated) {
            JsBinaryOperation jsBinaryOperationAnd = JsAstUtils.and(JsAstUtils.greaterThanEq(this.left, jsExpressionTranslateAsExpression), JsAstUtils.lessThanEq(this.left, jsExpressionTranslateAsExpression2));
            if (jsBinaryOperationAnd == null) {
                $$$reportNull$$$0(10);
            }
            return jsBinaryOperationAnd;
        }
        JsBinaryOperation jsBinaryOperationOr = JsAstUtils.or(JsAstUtils.lessThan(this.left, jsExpressionTranslateAsExpression), JsAstUtils.greaterThan(this.left, jsExpressionTranslateAsExpression2));
        if (jsBinaryOperationOr == null) {
            $$$reportNull$$$0(11);
        }
        return jsBinaryOperationOr;
    }
}
