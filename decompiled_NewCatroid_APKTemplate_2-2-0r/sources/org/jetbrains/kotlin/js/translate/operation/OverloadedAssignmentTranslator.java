package org.jetbrains.kotlin.js.translate.operation;

import java.util.HashMap;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.callTranslator.CallTranslator;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.reference.AccessTranslationUtils;
import org.jetbrains.kotlin.js.translate.reference.AccessTranslator;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;

/* JADX INFO: loaded from: classes5.dex */
public final class OverloadedAssignmentTranslator extends AssignmentTranslator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final ResolvedCall<? extends FunctionDescriptor> resolvedCall;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5) ? 2 : 3];
        if (i == 1 || i == 3) {
            objArr[0] = "context";
        } else if (i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/js/translate/operation/OverloadedAssignmentTranslator";
        } else {
            objArr[0] = "expression";
        }
        if (i == 4) {
            objArr[1] = "reassignment";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/js/translate/operation/OverloadedAssignmentTranslator";
        } else {
            objArr[1] = "overloadedMethodInvocation";
        }
        if (i == 2 || i == 3) {
            objArr[2] = "<init>";
        } else if (i != 4 && i != 5) {
            objArr[2] = "doTranslate";
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5) {
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
        return new OverloadedAssignmentTranslator(ktBinaryExpression, translationContext).translate();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private OverloadedAssignmentTranslator(KtBinaryExpression ktBinaryExpression, TranslationContext translationContext) {
        super(ktBinaryExpression, translationContext);
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(2);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(3);
        }
        this.resolvedCall = CallUtilKt.getFunctionResolvedCallWithAssert(ktBinaryExpression, translationContext.bindingContext());
    }

    private JsExpression translate() {
        if (this.isVariableReassignment) {
            return reassignment();
        }
        return overloadedMethodInvocation(AccessTranslationUtils.getAccessTranslator(this.expression.getLeft(), context()));
    }

    private JsExpression reassignment() {
        AccessTranslator cached = AccessTranslationUtils.getAccessTranslator(this.expression.getLeft(), context()).getCached();
        JsExpression jsExpressionTranslateAsSet = cached.translateAsSet(overloadedMethodInvocation(cached));
        if (jsExpressionTranslateAsSet == null) {
            $$$reportNull$$$0(4);
        }
        return jsExpressionTranslateAsSet;
    }

    private JsExpression overloadedMethodInvocation(AccessTranslator accessTranslator) {
        JsBlock jsBlock = new JsBlock();
        TranslationContext translationContextInnerBlock = context().innerBlock(jsBlock);
        JsExpression jsExpressionTranslateAsGet = accessTranslator.translateAsGet();
        JsBlock jsBlock2 = new JsBlock();
        TranslationContext translationContextInnerBlock2 = translationContextInnerBlock.innerBlock(jsBlock2);
        KtExpression right = this.expression.getRight();
        JsExpression jsExpressionTranslateAsExpression = Translation.translateAsExpression(right, translationContextInnerBlock2);
        if (!jsBlock2.isEmpty()) {
            jsExpressionTranslateAsGet = translationContextInnerBlock.defineTemporary(jsExpressionTranslateAsGet);
            translationContextInnerBlock.addStatementsToCurrentBlockFrom(jsBlock2);
        }
        HashMap map = new HashMap();
        map.put(right, jsExpressionTranslateAsExpression);
        JsExpression jsExpressionTranslate = CallTranslator.translate(translationContextInnerBlock.innerContextWithAliasesForExpressions(map), this.resolvedCall, jsExpressionTranslateAsGet);
        context().addStatementsToCurrentBlockFrom(jsBlock);
        if (jsExpressionTranslate == null) {
            $$$reportNull$$$0(5);
        }
        return jsExpressionTranslate;
    }
}
