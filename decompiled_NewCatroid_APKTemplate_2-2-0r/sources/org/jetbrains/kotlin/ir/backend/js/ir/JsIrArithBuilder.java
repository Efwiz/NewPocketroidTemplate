package org.jetbrains.kotlin.ir.backend.js.ir;

import com.android.dx.rop.code.RegisterSpec;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.ir.Symbols;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.utils.OperatorNames;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: IrArithBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\fJ\u0016\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\fJ\u0016\u0010\u0010\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\fJ \u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\fH\u0002J\u0018\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\fH\u0002J\u0016\u0010\u0017\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\fJ\u000e\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\fJ\u0016\u0010\u0019\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\fJ\u0016\u0010\u001a\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\fJ\u000e\u0010\u001b\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\fJ\u0016\u0010\u001c\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\fJ\u0016\u0010\u001d\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\fJ\u0016\u0010\u001e\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\fJ\u0016\u0010\u001f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\fJ\u0016\u0010 \u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\fJ\u0016\u0010!\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ir/JsIrArithBuilder;", "", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "symbols", "Lorg/jetbrains/kotlin/backend/common/ir/Symbols;", "getSymbols", "()Lorg/jetbrains/kotlin/backend/common/ir/Symbols;", ProductAction.ACTION_ADD, "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "l", "r", "and", "andand", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "buildBinaryOperator", "name", "Lorg/jetbrains/kotlin/name/Name;", "buildUnaryOperator", RegisterSpec.PREFIX, "div", "inv", "mod", "mul", "not", "or", "oror", "shl", "shr", "shru", "sub", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsIrArithBuilder {
    private final JsIrBackendContext context;
    private final Symbols symbols;

    public JsIrArithBuilder(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.symbols = context.getIr().getSymbols();
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    public final Symbols getSymbols() {
        return this.symbols;
    }

    private final IrExpression buildBinaryOperator(Name name2, IrExpression l, IrExpression r) {
        JsIrBackendContext jsIrBackendContext = this.context;
        IrType type = l.getType();
        Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        IrType type2 = r.getType();
        Intrinsics.checkNotNull(type2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        IrSimpleFunctionSymbol operatorByName = jsIrBackendContext.getOperatorByName(name2, (IrSimpleType) type, (IrSimpleType) type2);
        JsIrBuilder jsIrBuilder = JsIrBuilder.INSTANCE;
        Intrinsics.checkNotNull(operatorByName);
        IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(jsIrBuilder, operatorByName, null, null, null, null, 30, null);
        irCallBuildCall$default.setDispatchReceiver(l);
        irCallBuildCall$default.putValueArgument(0, r);
        return irCallBuildCall$default;
    }

    private final IrExpression buildUnaryOperator(Name name2, IrExpression v) {
        JsIrBackendContext jsIrBackendContext = this.context;
        IrType type = v.getType();
        Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        IrSimpleFunctionSymbol operatorByName = jsIrBackendContext.getOperatorByName(name2, (IrSimpleType) type, null);
        Intrinsics.checkNotNull(operatorByName);
        IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, operatorByName, null, null, null, null, 30, null);
        irCallBuildCall$default.setDispatchReceiver(v);
        return irCallBuildCall$default;
    }

    public final IrExpression add(IrExpression l, IrExpression r) {
        Intrinsics.checkNotNullParameter(l, "l");
        Intrinsics.checkNotNullParameter(r, "r");
        return buildBinaryOperator(OperatorNames.INSTANCE.getADD(), l, r);
    }

    public final IrExpression sub(IrExpression l, IrExpression r) {
        Intrinsics.checkNotNullParameter(l, "l");
        Intrinsics.checkNotNullParameter(r, "r");
        return buildBinaryOperator(OperatorNames.INSTANCE.getSUB(), l, r);
    }

    public final IrExpression mul(IrExpression l, IrExpression r) {
        Intrinsics.checkNotNullParameter(l, "l");
        Intrinsics.checkNotNullParameter(r, "r");
        return buildBinaryOperator(OperatorNames.INSTANCE.getMUL(), l, r);
    }

    public final IrExpression div(IrExpression l, IrExpression r) {
        Intrinsics.checkNotNullParameter(l, "l");
        Intrinsics.checkNotNullParameter(r, "r");
        return buildBinaryOperator(OperatorNames.INSTANCE.getDIV(), l, r);
    }

    public final IrExpression mod(IrExpression l, IrExpression r) {
        Intrinsics.checkNotNullParameter(l, "l");
        Intrinsics.checkNotNullParameter(r, "r");
        return buildBinaryOperator(OperatorNames.INSTANCE.getMOD(), l, r);
    }

    public final IrExpression and(IrExpression l, IrExpression r) {
        Intrinsics.checkNotNullParameter(l, "l");
        Intrinsics.checkNotNullParameter(r, "r");
        return buildBinaryOperator(OperatorNames.INSTANCE.getAND(), l, r);
    }

    public final IrExpression or(IrExpression l, IrExpression r) {
        Intrinsics.checkNotNullParameter(l, "l");
        Intrinsics.checkNotNullParameter(r, "r");
        return buildBinaryOperator(OperatorNames.INSTANCE.getOR(), l, r);
    }

    public final IrExpression shl(IrExpression l, IrExpression r) {
        Intrinsics.checkNotNullParameter(l, "l");
        Intrinsics.checkNotNullParameter(r, "r");
        return buildBinaryOperator(OperatorNames.INSTANCE.getSHL(), l, r);
    }

    public final IrExpression shr(IrExpression l, IrExpression r) {
        Intrinsics.checkNotNullParameter(l, "l");
        Intrinsics.checkNotNullParameter(r, "r");
        return buildBinaryOperator(OperatorNames.INSTANCE.getSHR(), l, r);
    }

    public final IrExpression shru(IrExpression l, IrExpression r) {
        Intrinsics.checkNotNullParameter(l, "l");
        Intrinsics.checkNotNullParameter(r, "r");
        return buildBinaryOperator(OperatorNames.INSTANCE.getSHRU(), l, r);
    }

    public final IrExpression not(IrExpression v) {
        Intrinsics.checkNotNullParameter(v, "v");
        return buildUnaryOperator(OperatorNames.INSTANCE.getNOT(), v);
    }

    public final IrExpression inv(IrExpression v) {
        Intrinsics.checkNotNullParameter(v, "v");
        return buildUnaryOperator(OperatorNames.INSTANCE.getINV(), v);
    }

    public final IrWhen andand(IrExpression l, IrExpression r) {
        Intrinsics.checkNotNullParameter(l, "l");
        Intrinsics.checkNotNullParameter(r, "r");
        return JsIrBuilder.buildIfElse$default(JsIrBuilder.INSTANCE, this.context.getIrBuiltIns().getBooleanType(), l, r, JsIrBuilder.INSTANCE.buildBoolean(this.context.getIrBuiltIns().getBooleanType(), false), 0, 0, 0, 0, 240, null);
    }

    public final IrWhen oror(IrExpression l, IrExpression r) {
        Intrinsics.checkNotNullParameter(l, "l");
        Intrinsics.checkNotNullParameter(r, "r");
        return JsIrBuilder.buildIfElse$default(JsIrBuilder.INSTANCE, this.context.getIrBuiltIns().getBooleanType(), l, JsIrBuilder.INSTANCE.buildBoolean(this.context.getIrBuiltIns().getBooleanType(), true), r, 0, 0, 0, 0, 240, null);
    }
}
