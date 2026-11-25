package org.jetbrains.kotlin.js.translate.callTranslator;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.reference.CallArgumentTranslator;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;

/* JADX INFO: compiled from: CallInfo.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0011\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u000eH\u0096\u0001J\u0011\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0019\u001a\u00020\u001bH\u0096\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u000eX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0010R\u001a\u0010\u0013\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00150\u0014X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/callTranslator/FunctionCallInfo;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/AbstractCallInfo;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;", "callInfo", "argumentsInfo", "Lorg/jetbrains/kotlin/js/translate/reference/CallArgumentTranslator$ArgumentsInfo;", "(Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;Lorg/jetbrains/kotlin/js/translate/reference/CallArgumentTranslator$ArgumentsInfo;)V", "getArgumentsInfo", "()Lorg/jetbrains/kotlin/js/translate/reference/CallArgumentTranslator$ArgumentsInfo;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "getContext", "()Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "dispatchReceiver", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "extensionReceiver", "getExtensionReceiver", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getResolvedCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "constructSafeCallIfNeeded", "result", "constructSuspendSafeCallIfNeeded", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionCallInfo extends AbstractCallInfo implements CallInfo {
    private final /* synthetic */ CallInfo $$delegate_0;
    private final CallArgumentTranslator.ArgumentsInfo argumentsInfo;

    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallInfo
    public JsExpression constructSafeCallIfNeeded(JsExpression result) {
        Intrinsics.checkNotNullParameter(result, "result");
        return this.$$delegate_0.constructSafeCallIfNeeded(result);
    }

    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallInfo
    public JsStatement constructSuspendSafeCallIfNeeded(JsStatement result) {
        Intrinsics.checkNotNullParameter(result, "result");
        return this.$$delegate_0.constructSuspendSafeCallIfNeeded(result);
    }

    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallInfo
    public TranslationContext getContext() {
        return this.$$delegate_0.getContext();
    }

    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallInfo
    public JsExpression getDispatchReceiver() {
        return this.$$delegate_0.getDispatchReceiver();
    }

    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallInfo
    public JsExpression getExtensionReceiver() {
        return this.$$delegate_0.getExtensionReceiver();
    }

    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallInfo
    public ResolvedCall<? extends CallableDescriptor> getResolvedCall() {
        return this.$$delegate_0.getResolvedCall();
    }

    public final CallArgumentTranslator.ArgumentsInfo getArgumentsInfo() {
        return this.argumentsInfo;
    }

    public FunctionCallInfo(CallInfo callInfo, CallArgumentTranslator.ArgumentsInfo argumentsInfo) {
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        Intrinsics.checkNotNullParameter(argumentsInfo, "argumentsInfo");
        this.argumentsInfo = argumentsInfo;
        this.$$delegate_0 = callInfo;
    }
}
