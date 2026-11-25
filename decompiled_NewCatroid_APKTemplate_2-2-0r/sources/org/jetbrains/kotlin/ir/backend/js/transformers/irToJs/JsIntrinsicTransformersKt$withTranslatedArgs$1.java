package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.ir.backend.js.utils.JsGenerationContext;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: JsIntrinsicTransformers.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\n¢\u0006\u0002\b\u0006"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/utils/JsGenerationContext;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class JsIntrinsicTransformersKt$withTranslatedArgs$1 extends Lambda implements Function2<IrCall, JsGenerationContext, JsExpression> {
    final /* synthetic */ Function1<List<? extends JsExpression>, JsExpression> $t;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public JsIntrinsicTransformersKt$withTranslatedArgs$1(Function1<? super List<? extends JsExpression>, ? extends JsExpression> function1) {
        super(2);
        this.$t = function1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final JsExpression invoke(IrCall call, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(context, "context");
        return this.$t.invoke(JsIntrinsicTransformersKt.translateCallArguments(call, context));
    }
}
