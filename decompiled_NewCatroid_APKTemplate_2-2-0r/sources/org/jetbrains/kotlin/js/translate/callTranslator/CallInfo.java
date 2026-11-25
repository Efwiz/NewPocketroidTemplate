package org.jetbrains.kotlin.js.translate.callTranslator;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;

/* JADX INFO: compiled from: CallInfo.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u0007H&J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0012\u001a\u00020\u0014H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u0004\u0018\u00010\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u0004\u0018\u00010\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\tR\u001a\u0010\f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000e0\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0015À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;", "", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "getContext", "()Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "dispatchReceiver", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "getDispatchReceiver", "()Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "extensionReceiver", "getExtensionReceiver", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getResolvedCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "constructSafeCallIfNeeded", "result", "constructSuspendSafeCallIfNeeded", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CallInfo {
    JsExpression constructSafeCallIfNeeded(JsExpression result);

    JsStatement constructSuspendSafeCallIfNeeded(JsStatement result);

    TranslationContext getContext();

    JsExpression getDispatchReceiver();

    JsExpression getExtensionReceiver();

    ResolvedCall<? extends CallableDescriptor> getResolvedCall();
}
