package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.utils.JsGenerationContext;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsPostfixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsPrefixOperation;
import org.jetbrains.kotlin.js.backend.ast.JsUnaryOperator;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: JsIntrinsicTransformers.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000X\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001ar\u0010\u0007\u001a\u00020\b*3\u0012\u0004\u0012\u00020\n\u0012)\u0012'\u0012\u0004\u0012\u00020\u0004\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00020\u000bj\u0002`\u000e0\t2\u0006\u0010\u000f\u001a\u00020\u00102+\u0010\u0011\u001a'\u0012\u0004\u0012\u00020\u0004\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00020\u000bj\u0002`\u000eH\u0002\u001ar\u0010\u0007\u001a\u00020\b*3\u0012\u0004\u0012\u00020\n\u0012)\u0012'\u0012\u0004\u0012\u00020\u0004\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00020\u000bj\u0002`\u000e0\t2\u0006\u0010\u0012\u001a\u00020\n2+\u0010\u0011\u001a'\u0012\u0004\u0012\u00020\u0004\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00020\u000bj\u0002`\u000eH\u0002\u001at\u0010\u0013\u001a\u00020\b*3\u0012\u0004\u0012\u00020\n\u0012)\u0012'\u0012\u0004\u0012\u00020\u0004\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00020\u000bj\u0002`\u000e0\t2\b\u0010\u0014\u001a\u0004\u0018\u00010\n2+\u0010\u0011\u001a'\u0012\u0004\u0012\u00020\u0004\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00020\u000bj\u0002`\u000eH\u0002\u001aM\u0010\u0015\u001a\u00020\b*3\u0012\u0004\u0012\u00020\n\u0012)\u0012'\u0012\u0004\u0012\u00020\u0004\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00020\u000bj\u0002`\u000e0\t2\u0006\u0010\u000f\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002\u001aM\u0010\u0019\u001a\u00020\b*3\u0012\u0004\u0012\u00020\n\u0012)\u0012'\u0012\u0004\u0012\u00020\u0004\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00020\u000bj\u0002`\u000e0\t2\u0006\u0010\u000f\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u001aH\u0002\u001aM\u0010\u001b\u001a\u00020\b*3\u0012\u0004\u0012\u00020\n\u0012)\u0012'\u0012\u0004\u0012\u00020\u0004\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00020\u000bj\u0002`\u000e0\t2\u0006\u0010\u000f\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u001aH\u0002\u001ab\u0010\u001c\u001a\u00020\b*3\u0012\u0004\u0012\u00020\n\u0012)\u0012'\u0012\u0004\u0012\u00020\u0004\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00020\u000bj\u0002`\u000e0\t2\u0006\u0010\u000f\u001a\u00020\u00162\u001a\b\u0004\u0010\u0011\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u0001\u0012\u0004\u0012\u00020\u00020\u001dH\u0082\b*L\u0010\u001e\"#\u0012\u0004\u0012\u00020\u0004\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00020\u000b2#\u0012\u0004\u0012\u00020\u0004\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00020\u000b¨\u0006\u001f"}, d2 = {"translateCallArguments", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/utils/JsGenerationContext;", ProductAction.ACTION_ADD, "", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/IrCallTransformer;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "t", "functionSymbol", "addIfNotNull", NativeSymbol.TYPE_NAME, "binOp", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "op", "Lorg/jetbrains/kotlin/js/backend/ast/JsBinaryOperator;", "postfixOp", "Lorg/jetbrains/kotlin/js/backend/ast/JsUnaryOperator;", "prefixOp", "withTranslatedArgs", "Lkotlin/Function1;", "IrCallTransformer", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JsIntrinsicTransformersKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final List<JsExpression> translateCallArguments(IrCall irCall, JsGenerationContext jsGenerationContext) {
        return JsAstUtilsKt.translateCallArguments(irCall, jsGenerationContext, new IrElementToJsExpressionTransformer(), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void add(Map<IrSymbol, Function2<IrCall, JsGenerationContext, JsExpression>> map, IrSymbol irSymbol, Function2<? super IrCall, ? super JsGenerationContext, ? extends JsExpression> function2) {
        map.put(irSymbol, function2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void addIfNotNull(Map<IrSymbol, Function2<IrCall, JsGenerationContext, JsExpression>> map, IrSymbol irSymbol, Function2<? super IrCall, ? super JsGenerationContext, ? extends JsExpression> function2) {
        if (irSymbol == null) {
            return;
        }
        map.put(irSymbol, function2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void binOp(Map<IrSymbol, Function2<IrCall, JsGenerationContext, JsExpression>> map, IrSimpleFunctionSymbol irSimpleFunctionSymbol, final JsBinaryOperator jsBinaryOperator) {
        map.put(irSimpleFunctionSymbol, new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformersKt$binOp$$inlined$withTranslatedArgs$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall call, JsGenerationContext context) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(context, "context");
                List listTranslateCallArguments = JsIntrinsicTransformersKt.translateCallArguments(call, context);
                return new JsBinaryOperation(jsBinaryOperator, (JsExpression) listTranslateCallArguments.get(0), (JsExpression) listTranslateCallArguments.get(1));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void prefixOp(Map<IrSymbol, Function2<IrCall, JsGenerationContext, JsExpression>> map, IrSimpleFunctionSymbol irSimpleFunctionSymbol, final JsUnaryOperator jsUnaryOperator) {
        map.put(irSimpleFunctionSymbol, new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformersKt$prefixOp$$inlined$withTranslatedArgs$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall call, JsGenerationContext context) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(context, "context");
                return new JsPrefixOperation(jsUnaryOperator, (JsExpression) JsIntrinsicTransformersKt.translateCallArguments(call, context).get(0));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void postfixOp(Map<IrSymbol, Function2<IrCall, JsGenerationContext, JsExpression>> map, IrSimpleFunctionSymbol irSimpleFunctionSymbol, final JsUnaryOperator jsUnaryOperator) {
        map.put(irSimpleFunctionSymbol, new Function2<IrCall, JsGenerationContext, JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIntrinsicTransformersKt$postfixOp$$inlined$withTranslatedArgs$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final JsExpression invoke(IrCall call, JsGenerationContext context) {
                Intrinsics.checkNotNullParameter(call, "call");
                Intrinsics.checkNotNullParameter(context, "context");
                return new JsPostfixOperation(jsUnaryOperator, (JsExpression) JsIntrinsicTransformersKt.translateCallArguments(call, context).get(0));
            }
        });
    }
}
