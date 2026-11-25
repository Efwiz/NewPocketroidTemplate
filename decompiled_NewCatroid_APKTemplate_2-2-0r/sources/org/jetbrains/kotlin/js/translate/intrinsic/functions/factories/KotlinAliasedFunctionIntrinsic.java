package org.jetbrains.kotlin.js.translate.intrinsic.functions.factories;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;

/* JADX INFO: compiled from: KotlinAliasedFunctionIntrinsic.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J(\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/factories/KotlinAliasedFunctionIntrinsic;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/functions/basic/FunctionIntrinsicWithReceiverComputed;", "functionName", "", "(Ljava/lang/String;)V", "apply", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "receiver", "arguments", "", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KotlinAliasedFunctionIntrinsic extends FunctionIntrinsicWithReceiverComputed {
    private final String functionName;

    public KotlinAliasedFunctionIntrinsic(String functionName) {
        Intrinsics.checkNotNullParameter(functionName, "functionName");
        this.functionName = functionName;
    }

    @Override // org.jetbrains.kotlin.js.translate.intrinsic.functions.basic.FunctionIntrinsicWithReceiverComputed
    public JsExpression apply(JsExpression receiver, List<? extends JsExpression> arguments, TranslationContext context) {
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(context, "context");
        JsExpression referenceToIntrinsic = context.getReferenceToIntrinsic(this.functionName);
        Intrinsics.checkNotNullExpressionValue(referenceToIntrinsic, "getReferenceToIntrinsic(...)");
        if (receiver != null) {
            arguments = TranslationUtils.generateInvocationArguments(receiver, arguments);
            Intrinsics.checkNotNullExpressionValue(arguments, "generateInvocationArguments(...)");
        }
        return new JsInvocation(referenceToIntrinsic, arguments);
    }
}
