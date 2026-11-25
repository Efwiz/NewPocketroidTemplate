package org.jetbrains.kotlin.js.translate.intrinsic.functions.factories;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.patterns.PatternBuilder;
import org.jetbrains.kotlin.js.translate.callTranslator.CallInfo;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: compiled from: AsDynamicFIF.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/AsDynamicFIF;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/CompositeFIF;", "()V", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AsDynamicFIF extends CompositeFIF {
    public static final AsDynamicFIF INSTANCE;

    private AsDynamicFIF() {
    }

    static {
        AsDynamicFIF asDynamicFIF = new AsDynamicFIF();
        INSTANCE = asDynamicFIF;
        asDynamicFIF.add(PatternBuilder.pattern("kotlin.js.asDynamic()"), new FunctionIntrinsic() { // from class: org.jetbrains.kotlin.js.translate.intrinsic.functions.factories.AsDynamicFIF.1
            @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsic
            public JsExpression apply(CallInfo callInfo, List<? extends JsExpression> arguments, TranslationContext context) {
                Intrinsics.checkNotNullParameter(callInfo, "callInfo");
                Intrinsics.checkNotNullParameter(arguments, "arguments");
                Intrinsics.checkNotNullParameter(context, "context");
                JsExpression extensionReceiver = callInfo.getExtensionReceiver();
                Intrinsics.checkNotNull(extensionReceiver);
                ReceiverValue extensionReceiver2 = callInfo.getResolvedCall().getExtensionReceiver();
                Intrinsics.checkNotNull(extensionReceiver2);
                JsExpression jsExpressionCoerce = TranslationUtils.coerce(context, extensionReceiver, extensionReceiver2.getType());
                Intrinsics.checkNotNullExpressionValue(jsExpressionCoerce, "coerce(...)");
                return jsExpressionCoerce;
            }
        });
    }
}
