package org.jetbrains.kotlin.js.translate.callTranslator;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsArrayAccess;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.resolve.calls.tasks.DynamicCallsKt;

/* JADX INFO: compiled from: FunctionCallCases.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\f\u0010\u0007\u001a\u00020\b*\u00020\u0006H\u0014¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/callTranslator/DynamicInvokeAndBracketAccessCallCase;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/FunctionCallCase;", "()V", "canApply", "", "callInfo", "Lorg/jetbrains/kotlin/js/translate/callTranslator/FunctionCallInfo;", "dispatchReceiver", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DynamicInvokeAndBracketAccessCallCase extends FunctionCallCase {
    public static final DynamicInvokeAndBracketAccessCallCase INSTANCE = new DynamicInvokeAndBracketAccessCallCase();

    /* JADX INFO: compiled from: FunctionCallCases.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Call.CallType.values().length];
            try {
                iArr[Call.CallType.INVOKE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Call.CallType.ARRAY_GET_METHOD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Call.CallType.ARRAY_SET_METHOD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private DynamicInvokeAndBracketAccessCallCase() {
    }

    public final boolean canApply(FunctionCallInfo callInfo) {
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        if (!DynamicCallsKt.isDynamic(CallInfoExtensionsKt.getCallableDescriptor(callInfo))) {
            return false;
        }
        Call.CallType callType = callInfo.getResolvedCall().getCall().getCallType();
        Intrinsics.checkNotNullExpressionValue(callType, "getCallType(...)");
        return callType == Call.CallType.ARRAY_GET_METHOD || callType == Call.CallType.ARRAY_SET_METHOD || callType == Call.CallType.INVOKE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallCase
    public JsExpression dispatchReceiver(FunctionCallInfo functionCallInfo) {
        Intrinsics.checkNotNullParameter(functionCallInfo, "<this>");
        List<JsExpression> translateArguments = functionCallInfo.getArgumentsInfo().getTranslateArguments();
        Call.CallType callType = functionCallInfo.getResolvedCall().getCall().getCallType();
        Intrinsics.checkNotNullExpressionValue(callType, "getCallType(...)");
        int i = WhenMappings.$EnumSwitchMapping$0[callType.ordinal()];
        if (i == 1) {
            JsExpression dispatchReceiver = functionCallInfo.getDispatchReceiver();
            Intrinsics.checkNotNull(dispatchReceiver);
            return new JsInvocation(dispatchReceiver, translateArguments);
        }
        if (i == 2) {
            return new JsArrayAccess(functionCallInfo.getDispatchReceiver(), translateArguments.get(0));
        }
        if (i == 3) {
            JsBinaryOperation jsBinaryOperationAssignment = JsAstUtils.assignment(new JsArrayAccess(functionCallInfo.getDispatchReceiver(), translateArguments.get(0)), translateArguments.get(1));
            Intrinsics.checkNotNullExpressionValue(jsBinaryOperationAssignment, "assignment(...)");
            return jsBinaryOperationAssignment;
        }
        unsupported(functionCallInfo, "Unsupported call type: " + callType + ", callInfo: " + functionCallInfo);
        throw null;
    }
}
