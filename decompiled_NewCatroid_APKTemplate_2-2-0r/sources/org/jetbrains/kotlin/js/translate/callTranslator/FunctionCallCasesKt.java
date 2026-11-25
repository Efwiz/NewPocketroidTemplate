package org.jetbrains.kotlin.js.translate.callTranslator;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.reference.CallArgumentTranslator;

/* JADX INFO: compiled from: FunctionCallCases.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0002\u001a\n\u0010\u0005\u001a\u00020\u0002*\u00020\u0006¨\u0006\u0007"}, d2 = {"argsWithReceiver", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "Lorg/jetbrains/kotlin/js/translate/reference/CallArgumentTranslator$ArgumentsInfo;", "receiver", "translateFunctionCall", "Lorg/jetbrains/kotlin/js/translate/callTranslator/FunctionCallInfo;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FunctionCallCasesKt {
    public static final List<JsExpression> argsWithReceiver(CallArgumentTranslator.ArgumentsInfo argumentsInfo, JsExpression receiver) {
        Intrinsics.checkNotNullParameter(argumentsInfo, "<this>");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        ArrayList arrayList = new ArrayList(argumentsInfo.getReifiedArguments().size() + 1 + argumentsInfo.getValueArguments().size());
        arrayList.addAll(argumentsInfo.getReifiedArguments());
        arrayList.add(receiver);
        arrayList.addAll(argumentsInfo.getValueArguments());
        return arrayList;
    }

    public static final JsExpression translateFunctionCall(FunctionCallInfo functionCallInfo) {
        Intrinsics.checkNotNullParameter(functionCallInfo, "<this>");
        FunctionCallInfo functionCallInfo2 = functionCallInfo;
        JsExpression jsExpressionIntrinsic = DelegateFunctionIntrinsic.INSTANCE.intrinsic(functionCallInfo2, functionCallInfo.getContext());
        if (jsExpressionIntrinsic != null) {
            return jsExpressionIntrinsic;
        }
        if (NativeInvokeCallCase.INSTANCE.canApply(functionCallInfo)) {
            return NativeInvokeCallCase.INSTANCE.translate(functionCallInfo2);
        }
        if (NativeGetterCallCase.INSTANCE.canApply(functionCallInfo)) {
            return NativeGetterCallCase.INSTANCE.translate(functionCallInfo2);
        }
        if (NativeSetterCallCase.INSTANCE.canApply(functionCallInfo)) {
            return NativeSetterCallCase.INSTANCE.translate(functionCallInfo2);
        }
        if (InvokeIntrinsic.INSTANCE.canApply(functionCallInfo)) {
            return InvokeIntrinsic.INSTANCE.translate(functionCallInfo2);
        }
        if (ConstructorCallCase.INSTANCE.canApply(functionCallInfo)) {
            return ConstructorCallCase.INSTANCE.translate(functionCallInfo2);
        }
        if (SuperCallCase.INSTANCE.canApply(functionCallInfo)) {
            return SuperCallCase.INSTANCE.translate(functionCallInfo2);
        }
        if (DynamicInvokeAndBracketAccessCallCase.INSTANCE.canApply(functionCallInfo)) {
            return DynamicInvokeAndBracketAccessCallCase.INSTANCE.translate(functionCallInfo2);
        }
        if (DynamicOperatorCallCase.INSTANCE.canApply(functionCallInfo)) {
            return DynamicOperatorCallCase.INSTANCE.translate(functionCallInfo2);
        }
        return DefaultFunctionCallCase.INSTANCE.translate(functionCallInfo2);
    }
}
