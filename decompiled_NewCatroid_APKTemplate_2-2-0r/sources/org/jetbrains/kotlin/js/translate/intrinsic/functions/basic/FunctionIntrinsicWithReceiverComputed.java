package org.jetbrains.kotlin.js.translate.intrinsic.functions.basic;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.callTranslator.CallInfo;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;

/* JADX INFO: compiled from: FunctionIntrinsicWithReceiverComputed.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u00072\u0006\u0010\b\u001a\u00020\tH&J&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u00072\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/FunctionIntrinsicWithReceiverComputed;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/FunctionIntrinsic;", "()V", "apply", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "receiver", "arguments", "", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "callInfo", "Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FunctionIntrinsicWithReceiverComputed extends FunctionIntrinsic {
    public abstract JsExpression apply(JsExpression receiver, List<? extends JsExpression> arguments, TranslationContext context);

    @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic
    public JsExpression apply(CallInfo callInfo, List<? extends JsExpression> arguments, TranslationContext context) {
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(context, "context");
        JsExpression dispatchReceiver = callInfo.getDispatchReceiver();
        if (dispatchReceiver == null) {
            dispatchReceiver = callInfo.getExtensionReceiver();
        }
        return apply(dispatchReceiver, arguments, context);
    }
}
