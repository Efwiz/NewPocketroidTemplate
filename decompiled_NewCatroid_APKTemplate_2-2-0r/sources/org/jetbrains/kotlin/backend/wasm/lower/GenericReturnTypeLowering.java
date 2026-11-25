package org.jetbrains.kotlin.backend.wasm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.ir.backend.js.utils.IrResolveUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.TypeTranformerKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: GenericReturnTypeLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\f\u0010\u0011\u001a\u00020\u0012*\u00020\u0012H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/GenericReturnTypeLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "(Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "transformGenericCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "scopeOwnerSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "eraseUpperBoundType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class GenericReturnTypeLowering implements FileLoweringPass {
    private final WasmBackendContext context;

    public GenericReturnTypeLowering(WasmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final WasmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        IrElementTransformerVoidKt.transformChildrenVoid(irFile, new IrElementTransformerVoidWithContext() { // from class: org.jetbrains.kotlin.backend.wasm.lower.GenericReturnTypeLowering.lower.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitCall(IrCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                GenericReturnTypeLowering genericReturnTypeLowering = GenericReturnTypeLowering.this;
                IrExpression irExpressionVisitCall = super.visitCall(expression);
                Intrinsics.checkNotNull(irExpressionVisitCall, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrCall");
                ScopeWithIr currentScope = getCurrentScope();
                Intrinsics.checkNotNull(currentScope);
                return genericReturnTypeLowering.transformGenericCall((IrCall) irExpressionVisitCall, currentScope.getScope().getScopeOwnerSymbol());
            }
        });
    }

    private final IrType eraseUpperBoundType(IrType irType) {
        IrSimpleType defaultType;
        IrClass erasedUpperBound = TypeTranformerKt.getErasedUpperBound(irType);
        if (erasedUpperBound == null || (defaultType = IrUtilsKt.getDefaultType(erasedUpperBound)) == null) {
            return this.context.getIrBuiltIns().getAnyNType();
        }
        if (IrTypeUtilsKt.isNullable(irType)) {
            return IrTypesKt.makeNullable(defaultType);
        }
        return defaultType;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression transformGenericCall(IrCall call, IrSymbol scopeOwnerSymbol) {
        IrSimpleFunction owner = call.getSymbol().getOwner();
        IrSimpleFunction irSimpleFunction = owner instanceof IrSimpleFunction ? owner : null;
        if (irSimpleFunction == null) {
            return call;
        }
        IrType irTypeEraseUpperBoundType = eraseUpperBoundType(IrResolveUtilsKt.getRealOverrideTarget(irSimpleFunction).getReturnType());
        IrType type = call.getType();
        if (!Intrinsics.areEqual(irTypeEraseUpperBoundType, call.getType())) {
            if (!IrTypePredicatesKt.isNothing(type) && !IrTypeUtilsKt.isSubtypeOf(irTypeEraseUpperBoundType, type, this.context.getTypeSystem())) {
                return ExpressionHelpersKt.irImplicitCast(LowerUtilsKt.createIrBuilder$default(this.context, scopeOwnerSymbol, 0, 0, 6, (Object) null), IrUtilsKt.irCall(call, irSimpleFunction.getSymbol(), (12 & 4) != 0 ? false : false, (12 & 8) != 0 ? false : false, (12 & 16) != 0 ? null : call.getSuperQualifierSymbol(), (12 & 32) != 0 ? null : irTypeEraseUpperBoundType), call.getType());
            }
            return call;
        }
        return call;
    }
}
