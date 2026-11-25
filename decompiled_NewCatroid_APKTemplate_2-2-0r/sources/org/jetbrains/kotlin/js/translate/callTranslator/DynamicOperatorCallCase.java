package org.jetbrains.kotlin.js.translate.callTranslator;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsPostfixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.translate.operation.OperatorTable;
import org.jetbrains.kotlin.js.translate.utils.PsiUtils;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtOperationExpression;
import org.jetbrains.kotlin.psi.KtPostfixExpression;
import org.jetbrains.kotlin.psi.KtPrefixExpression;
import org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallsKt;

/* JADX INFO: compiled from: FunctionCallCases.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\f\u0010\u0007\u001a\u00020\b*\u00020\u0006H\u0014¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/callTranslator/DynamicOperatorCallCase;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/FunctionCallCase;", "()V", "canApply", "", "callInfo", "Lorg/jetbrains/kotlin/js/translate/callTranslator/FunctionCallInfo;", "dispatchReceiver", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DynamicOperatorCallCase extends FunctionCallCase {
    public static final DynamicOperatorCallCase INSTANCE = new DynamicOperatorCallCase();

    private DynamicOperatorCallCase() {
    }

    public final boolean canApply(FunctionCallInfo callInfo) {
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        if (DynamicCallsKt.isDynamic(CallInfoExtensionsKt.getCallableDescriptor(callInfo))) {
            KtElement callElement = callInfo.getResolvedCall().getCall().getCallElement();
            if (callElement instanceof KtOperationExpression) {
                KtToken operationToken = PsiUtils.getOperationToken((KtOperationExpression) callElement);
                if (Intrinsics.areEqual(operationToken, KtTokens.NOT_IN) || OperatorTable.hasCorrespondingOperator(operationToken)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallCase
    public JsExpression dispatchReceiver(FunctionCallInfo functionCallInfo) {
        JsBinaryOperation jsBinaryOperation;
        Intrinsics.checkNotNullParameter(functionCallInfo, "<this>");
        KtElement callElement = functionCallInfo.getResolvedCall().getCall().getCallElement();
        Intrinsics.checkNotNull(callElement, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtOperationExpression");
        KtOperationExpression ktOperationExpression = (KtOperationExpression) callElement;
        KtModifierKeywordToken operationToken = PsiUtils.getOperationToken(ktOperationExpression);
        Intrinsics.checkNotNullExpressionValue(operationToken, "getOperationToken(...)");
        List<JsExpression> translateArguments = functionCallInfo.getArgumentsInfo().getTranslateArguments();
        if (ktOperationExpression instanceof KtBinaryExpression) {
            if (Intrinsics.areEqual(operationToken, KtTokens.NOT_IN)) {
                operationToken = KtTokens.IN_KEYWORD;
            }
            JsBinaryOperator binaryOperator = OperatorTable.getBinaryOperator(operationToken);
            Intrinsics.checkNotNullExpressionValue(binaryOperator, "getBinaryOperator(...)");
            if (Intrinsics.areEqual(operationToken, KtTokens.IN_KEYWORD)) {
                jsBinaryOperation = new JsBinaryOperation(binaryOperator, translateArguments.get(0), functionCallInfo.getDispatchReceiver());
            } else {
                jsBinaryOperation = new JsBinaryOperation(binaryOperator, functionCallInfo.getDispatchReceiver(), translateArguments.get(0));
            }
            return jsBinaryOperation;
        }
        if (ktOperationExpression instanceof KtPrefixExpression) {
            return new JsPrefixOperation(OperatorTable.getUnaryOperator(operationToken), functionCallInfo.getDispatchReceiver());
        }
        if (ktOperationExpression instanceof KtPostfixExpression) {
            return new JsPostfixOperation(OperatorTable.getUnaryOperator(operationToken), functionCallInfo.getDispatchReceiver());
        }
        unsupported(functionCallInfo, "Unsupported callElement type: " + ktOperationExpression.getClass() + ", callElement: " + ktOperationExpression + ", callInfo: " + functionCallInfo);
        throw null;
    }
}
