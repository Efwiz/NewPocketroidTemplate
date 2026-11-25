package org.jetbrains.kotlin.ir.backend.js.lower.calls;

import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.JsIntrinsics;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: PrimitiveContainerMemberCallTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u0007\u001a\u001e\u0012\u0004\u0012\u00020\t\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\n0\bj\u0002`\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/PrimitiveContainerMemberCallTransformer;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/CallsTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "intrinsics", "Lorg/jetbrains/kotlin/ir/backend/js/JsIntrinsics;", "symbolToTransformer", "", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/SymbolToTransformer;", "transformFunctionAccess", "call", "doNotIntrinsify", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PrimitiveContainerMemberCallTransformer implements CallsTransformer {
    private final JsIrBackendContext context;
    private final JsIntrinsics intrinsics;
    private final Map<IrFunctionSymbol, Function1<IrFunctionAccessExpression, IrExpression>> symbolToTransformer;

    public PrimitiveContainerMemberCallTransformer(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.intrinsics = context.getIntrinsics();
        HashMap map = new HashMap();
        this.symbolToTransformer = map;
        CallsLoweringUtilsKt.add((Map<IrFunctionSymbol, Function1<IrFunctionAccessExpression, IrExpression>>) map, (IrFunctionSymbol) PrimitiveContainerMemberCallTransformerKt.getSizeProperty(context.getIntrinsics().getArray()), context.getIntrinsics().getJsArrayLength(), true);
        CallsLoweringUtilsKt.add((Map<IrFunctionSymbol, Function1<IrFunctionAccessExpression, IrExpression>>) map, (IrFunctionSymbol) PrimitiveContainerMemberCallTransformerKt.getGetFunction(context.getIntrinsics().getArray()), context.getIntrinsics().getJsArrayGet(), true);
        CallsLoweringUtilsKt.add((Map<IrFunctionSymbol, Function1<IrFunctionAccessExpression, IrExpression>>) map, (IrFunctionSymbol) PrimitiveContainerMemberCallTransformerKt.getSetFunction(context.getIntrinsics().getArray()), context.getIntrinsics().getJsArraySet(), true);
        CallsLoweringUtilsKt.add((Map<IrFunctionSymbol, Function1<IrFunctionAccessExpression, IrExpression>>) map, (IrFunctionSymbol) PrimitiveContainerMemberCallTransformerKt.getIterator(context.getIntrinsics().getArray()), context.getIntrinsics().getJsArrayIteratorFunction(), true);
        for (Map.Entry<IrClassSymbol, org.jetbrains.kotlin.builtins.PrimitiveType> entry : context.getIntrinsics().getPrimitiveArrays().entrySet()) {
            IrClassSymbol key = entry.getKey();
            final org.jetbrains.kotlin.builtins.PrimitiveType value = entry.getValue();
            CallsLoweringUtilsKt.add((Map<IrFunctionSymbol, Function1<IrFunctionAccessExpression, IrExpression>>) map, (IrFunctionSymbol) PrimitiveContainerMemberCallTransformerKt.getSizeProperty(key), this.context.getIntrinsics().getJsArrayLength(), true);
            CallsLoweringUtilsKt.add((Map<IrFunctionSymbol, Function1<IrFunctionAccessExpression, IrExpression>>) map, (IrFunctionSymbol) PrimitiveContainerMemberCallTransformerKt.getGetFunction(key), this.context.getIntrinsics().getJsArrayGet(), true);
            CallsLoweringUtilsKt.add((Map<IrFunctionSymbol, Function1<IrFunctionAccessExpression, IrExpression>>) map, (IrFunctionSymbol) PrimitiveContainerMemberCallTransformerKt.getSetFunction(key), this.context.getIntrinsics().getJsArraySet(), true);
            IrSimpleFunctionSymbol iterator = PrimitiveContainerMemberCallTransformerKt.getIterator(key);
            IrSimpleFunctionSymbol irSimpleFunctionSymbol = this.context.getIntrinsics().getJsPrimitiveArrayIteratorFunctions().get(value);
            Intrinsics.checkNotNull(irSimpleFunctionSymbol);
            CallsLoweringUtilsKt.add((Map<IrFunctionSymbol, Function1<IrFunctionAccessExpression, IrExpression>>) map, (IrFunctionSymbol) iterator, irSimpleFunctionSymbol, true);
            CallsLoweringUtilsKt.add(map, PrimitiveContainerMemberCallTransformerKt.getSizeConstructor(key), new Function1<IrFunctionAccessExpression, IrExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.calls.PrimitiveContainerMemberCallTransformer$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrExpression invoke(IrFunctionAccessExpression call) {
                    Intrinsics.checkNotNullParameter(call, "call");
                    int startOffset = call.getStartOffset();
                    int endOffset = call.getEndOffset();
                    IrType type = call.getType();
                    IrSimpleFunctionSymbol irSimpleFunctionSymbol2 = this.this$0.context.getIntrinsics().getPrimitiveToSizeConstructor().get(value);
                    Intrinsics.checkNotNull(irSimpleFunctionSymbol2);
                    IrCallImpl irCallImpl = new IrCallImpl(startOffset, endOffset, type, irSimpleFunctionSymbol2, 0, 1, null, null, 192, null);
                    irCallImpl.putValueArgument(0, call.getValueArgument(0));
                    return irCallImpl;
                }
            });
        }
        CallsLoweringUtilsKt.add((Map<IrFunctionSymbol, Function1<IrFunctionAccessExpression, IrExpression>>) map, (IrFunctionSymbol) PrimitiveContainerMemberCallTransformerKt.getHashCodeFunction(this.context.getIrBuiltIns().getStringClass()), this.intrinsics.getJsGetStringHashCode(), true);
        CallsLoweringUtilsKt.add((Map<IrFunctionSymbol, Function1<IrFunctionAccessExpression, IrExpression>>) map, (IrFunctionSymbol) PrimitiveContainerMemberCallTransformerKt.getLengthProperty(this.context.getIrBuiltIns().getStringClass()), this.context.getIntrinsics().getJsArrayLength(), true);
        CallsLoweringUtilsKt.add((Map<IrFunctionSymbol, Function1<IrFunctionAccessExpression, IrExpression>>) map, (IrFunctionSymbol) PrimitiveContainerMemberCallTransformerKt.getGetFunction(this.context.getIrBuiltIns().getStringClass()), this.intrinsics.getJsCharSequenceGet(), true);
        CallsLoweringUtilsKt.add((Map<IrFunctionSymbol, Function1<IrFunctionAccessExpression, IrExpression>>) map, (IrFunctionSymbol) PrimitiveContainerMemberCallTransformerKt.getSubSequence(this.context.getIrBuiltIns().getStringClass()), this.intrinsics.getJsCharSequenceSubSequence(), true);
        CallsLoweringUtilsKt.add((Map<IrFunctionSymbol, Function1<IrFunctionAccessExpression, IrExpression>>) map, this.intrinsics.getCharSequenceLengthPropertyGetterSymbol(), this.intrinsics.getJsCharSequenceLength(), true);
        CallsLoweringUtilsKt.add((Map<IrFunctionSymbol, Function1<IrFunctionAccessExpression, IrExpression>>) map, this.intrinsics.getCharSequenceGetFunctionSymbol(), this.intrinsics.getJsCharSequenceGet(), true);
        CallsLoweringUtilsKt.add((Map<IrFunctionSymbol, Function1<IrFunctionAccessExpression, IrExpression>>) map, this.intrinsics.getCharSequenceSubSequenceFunctionSymbol(), this.intrinsics.getJsCharSequenceSubSequence(), true);
        CallsLoweringUtilsKt.add$default(map, this.context.getIrBuiltIns().getDataClassArrayMemberHashCodeSymbol(), this.context.getIntrinsics().getJsHashCode(), false, 4, null);
        CallsLoweringUtilsKt.add$default(map, this.context.getIrBuiltIns().getDataClassArrayMemberToStringSymbol(), this.context.getIntrinsics().getJsToString(), false, 4, null);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.calls.CallsTransformer
    public IrExpression transformFunctionAccess(IrFunctionAccessExpression call, boolean doNotIntrinsify) {
        Intrinsics.checkNotNullParameter(call, "call");
        if (doNotIntrinsify) {
            return call;
        }
        Function1<IrFunctionAccessExpression, IrExpression> function1 = this.symbolToTransformer.get(call.getSymbol());
        if (function1 != null) {
            return function1.invoke(call);
        }
        return call;
    }
}
