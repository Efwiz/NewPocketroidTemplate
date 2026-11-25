package org.jetbrains.kotlin.ir.backend.js.lower.calls;

import java.util.HashMap;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.backend.js.JsIntrinsics;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.utils.ConversionNames;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: NumberConversionCallsTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\rH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\t\u001a\u001e\u0012\u0004\u0012\u00020\u000b\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\f0\nj\u0002`\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/NumberConversionCallsTransformer;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/CallsTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "intrinsics", "Lorg/jetbrains/kotlin/ir/backend/js/JsIntrinsics;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "memberToTransformer", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/SimpleMemberKey;", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/calls/MemberToTransformer;", "transformFunctionAccess", "call", "doNotIntrinsify", "", "useDispatchReceiver", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NumberConversionCallsTransformer implements CallsTransformer {
    private final JsIntrinsics intrinsics;
    private final IrBuiltIns irBuiltIns;
    private final HashMap<SimpleMemberKey, Function1<IrFunctionAccessExpression, IrExpression>> memberToTransformer;

    public NumberConversionCallsTransformer(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        JsIntrinsics intrinsics = context.getIntrinsics();
        this.intrinsics = intrinsics;
        IrBuiltIns irBuiltIns = context.getIrBuiltIns();
        this.irBuiltIns = irBuiltIns;
        HashMap<SimpleMemberKey, Function1<IrFunctionAccessExpression, IrExpression>> map = new HashMap<>();
        IrType byteType = irBuiltIns.getByteType();
        CallsLoweringUtilsKt.add(map, byteType, ConversionNames.INSTANCE.getTO_BYTE(), new NumberConversionCallsTransformer$memberToTransformer$1$1$1(this));
        CallsLoweringUtilsKt.add(map, byteType, ConversionNames.INSTANCE.getTO_DOUBLE(), new NumberConversionCallsTransformer$memberToTransformer$1$1$2(this));
        CallsLoweringUtilsKt.add(map, byteType, ConversionNames.INSTANCE.getTO_FLOAT(), new NumberConversionCallsTransformer$memberToTransformer$1$1$3(this));
        CallsLoweringUtilsKt.add(map, byteType, ConversionNames.INSTANCE.getTO_INT(), new NumberConversionCallsTransformer$memberToTransformer$1$1$4(this));
        CallsLoweringUtilsKt.add(map, byteType, ConversionNames.INSTANCE.getTO_SHORT(), new NumberConversionCallsTransformer$memberToTransformer$1$1$5(this));
        CallsLoweringUtilsKt.add(map, byteType, ConversionNames.INSTANCE.getTO_LONG(), intrinsics.getJsToLong());
        for (IrType irType : CollectionsKt.listOf((Object[]) new IrType[]{irBuiltIns.getFloatType(), irBuiltIns.getDoubleType()})) {
            CallsLoweringUtilsKt.add(map, irType, ConversionNames.INSTANCE.getTO_BYTE(), this.intrinsics.getJsNumberToByte());
            CallsLoweringUtilsKt.add(map, irType, ConversionNames.INSTANCE.getTO_DOUBLE(), new NumberConversionCallsTransformer$memberToTransformer$1$2(this));
            CallsLoweringUtilsKt.add(map, irType, ConversionNames.INSTANCE.getTO_FLOAT(), new NumberConversionCallsTransformer$memberToTransformer$1$3(this));
            CallsLoweringUtilsKt.add(map, irType, ConversionNames.INSTANCE.getTO_INT(), this.intrinsics.getJsNumberToInt());
            CallsLoweringUtilsKt.add(map, irType, ConversionNames.INSTANCE.getTO_SHORT(), this.intrinsics.getJsNumberToShort());
            CallsLoweringUtilsKt.add(map, irType, ConversionNames.INSTANCE.getTO_LONG(), this.intrinsics.getJsNumberToLong());
        }
        IrType intType = this.irBuiltIns.getIntType();
        CallsLoweringUtilsKt.add(map, intType, ConversionNames.INSTANCE.getTO_BYTE(), this.intrinsics.getJsToByte());
        CallsLoweringUtilsKt.add(map, intType, ConversionNames.INSTANCE.getTO_DOUBLE(), new NumberConversionCallsTransformer$memberToTransformer$1$4$1(this));
        CallsLoweringUtilsKt.add(map, intType, ConversionNames.INSTANCE.getTO_FLOAT(), new NumberConversionCallsTransformer$memberToTransformer$1$4$2(this));
        CallsLoweringUtilsKt.add(map, intType, ConversionNames.INSTANCE.getTO_INT(), new NumberConversionCallsTransformer$memberToTransformer$1$4$3(this));
        CallsLoweringUtilsKt.add(map, intType, ConversionNames.INSTANCE.getTO_SHORT(), this.intrinsics.getJsToShort());
        CallsLoweringUtilsKt.add(map, intType, ConversionNames.INSTANCE.getTO_LONG(), this.intrinsics.getJsToLong());
        IrType shortType = this.irBuiltIns.getShortType();
        CallsLoweringUtilsKt.add(map, shortType, ConversionNames.INSTANCE.getTO_BYTE(), this.intrinsics.getJsToByte());
        CallsLoweringUtilsKt.add(map, shortType, ConversionNames.INSTANCE.getTO_DOUBLE(), new NumberConversionCallsTransformer$memberToTransformer$1$5$1(this));
        CallsLoweringUtilsKt.add(map, shortType, ConversionNames.INSTANCE.getTO_FLOAT(), new NumberConversionCallsTransformer$memberToTransformer$1$5$2(this));
        CallsLoweringUtilsKt.add(map, shortType, ConversionNames.INSTANCE.getTO_INT(), new NumberConversionCallsTransformer$memberToTransformer$1$5$3(this));
        CallsLoweringUtilsKt.add(map, shortType, ConversionNames.INSTANCE.getTO_SHORT(), new NumberConversionCallsTransformer$memberToTransformer$1$5$4(this));
        CallsLoweringUtilsKt.add(map, shortType, ConversionNames.INSTANCE.getTO_LONG(), this.intrinsics.getJsToLong());
        IrType numberType = this.irBuiltIns.getNumberType();
        CallsLoweringUtilsKt.add(map, numberType, ConversionNames.INSTANCE.getTO_BYTE(), this.intrinsics.getJsNumberToByte());
        CallsLoweringUtilsKt.add(map, numberType, ConversionNames.INSTANCE.getTO_DOUBLE(), this.intrinsics.getJsNumberToDouble());
        CallsLoweringUtilsKt.add(map, numberType, ConversionNames.INSTANCE.getTO_FLOAT(), this.intrinsics.getJsNumberToDouble());
        CallsLoweringUtilsKt.add(map, numberType, ConversionNames.INSTANCE.getTO_INT(), this.intrinsics.getJsNumberToInt());
        CallsLoweringUtilsKt.add(map, numberType, ConversionNames.INSTANCE.getTO_SHORT(), this.intrinsics.getJsNumberToShort());
        CallsLoweringUtilsKt.add(map, numberType, ConversionNames.INSTANCE.getTO_LONG(), this.intrinsics.getJsNumberToLong());
        IrType[] irTypeArr = {this.irBuiltIns.getByteType(), this.irBuiltIns.getShortType(), this.irBuiltIns.getIntType()};
        for (int i = 0; i < 3; i++) {
            CallsLoweringUtilsKt.add(map, irTypeArr[i], ConversionNames.INSTANCE.getTO_CHAR(), this.intrinsics.getJsNumberToChar());
        }
        IrType[] irTypeArr2 = {this.irBuiltIns.getFloatType(), this.irBuiltIns.getDoubleType(), this.irBuiltIns.getNumberType()};
        for (int i2 = 0; i2 < 3; i2++) {
            CallsLoweringUtilsKt.add(map, irTypeArr2[i2], ConversionNames.INSTANCE.getTO_CHAR(), this.intrinsics.getJsNumberToChar());
        }
        this.memberToTransformer = map;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.calls.CallsTransformer
    public IrExpression transformFunctionAccess(IrFunctionAccessExpression call, boolean doNotIntrinsify) {
        Intrinsics.checkNotNullParameter(call, "call");
        IrFunction owner = call.getSymbol().getOwner();
        IrValueParameter dispatchReceiverParameter = owner.getDispatchReceiverParameter();
        if (dispatchReceiverParameter != null) {
            Function1<IrFunctionAccessExpression, IrExpression> function1 = this.memberToTransformer.get(new SimpleMemberKey(dispatchReceiverParameter.getType(), owner.getName()));
            if (function1 != null) {
                return function1.invoke(call);
            }
        }
        return call;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression useDispatchReceiver(IrFunctionAccessExpression call) {
        JsIrBuilder jsIrBuilder = JsIrBuilder.INSTANCE;
        IrExpression dispatchReceiver = call.getDispatchReceiver();
        Intrinsics.checkNotNull(dispatchReceiver);
        return jsIrBuilder.buildReinterpretCast(dispatchReceiver, call.getType());
    }
}
