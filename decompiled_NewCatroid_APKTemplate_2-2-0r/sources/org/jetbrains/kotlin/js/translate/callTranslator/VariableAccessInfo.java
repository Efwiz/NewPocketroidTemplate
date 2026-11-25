package org.jetbrains.kotlin.js.translate.callTranslator;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;

/* JADX INFO: compiled from: CallInfo.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0019\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0011\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u0005H\u0096\u0001J\u0011\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0017\u001a\u00020\u0019H\u0096\u0001R\u0012\u0010\u0007\u001a\u00020\bX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u0004\u0018\u00010\u0005X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u0004\u0018\u00010\u0005X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000f\u0010\rR\u001a\u0010\u0010\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00120\u0011X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\r¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/callTranslator/VariableAccessInfo;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/AbstractCallInfo;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;", "callInfo", "value", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "(Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)V", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "getContext", "()Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "dispatchReceiver", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "extensionReceiver", "getExtensionReceiver", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getResolvedCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "getValue", "constructSafeCallIfNeeded", "result", "constructSuspendSafeCallIfNeeded", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VariableAccessInfo extends AbstractCallInfo implements CallInfo {
    private final /* synthetic */ CallInfo $$delegate_0;
    private final JsExpression value;

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

    public VariableAccessInfo(CallInfo callInfo, JsExpression jsExpression) {
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        this.value = jsExpression;
        this.$$delegate_0 = callInfo;
    }

    public /* synthetic */ VariableAccessInfo(CallInfo callInfo, JsExpression jsExpression, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(callInfo, (i & 2) != 0 ? null : jsExpression);
    }

    public final JsExpression getValue() {
        return this.value;
    }
}
