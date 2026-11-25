package org.jetbrains.kotlin.backend.wasm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.backend.wasm.ir2wasm.TypeTransformerKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.lower.AbstractValueUsageLowering;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: ExplicitlyCastExternalTypesLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0014J\u001c\u0010\n\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0016¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/ExplicitlyCastExternalTypesLowering;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/AbstractValueUsageLowering;", "wasmContext", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "(Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;)V", "useAsVarargElement", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "useExpressionAsType", "actualType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "expectedType", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExplicitlyCastExternalTypesLowering extends AbstractValueUsageLowering {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExplicitlyCastExternalTypesLowering(WasmBackendContext wasmContext) {
        super(wasmContext, false, 2, null);
        Intrinsics.checkNotNullParameter(wasmContext, "wasmContext");
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.AbstractValueUsageLowering
    public IrExpression useExpressionAsType(IrExpression irExpression, IrType actualType, IrType expectedType) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(actualType, "actualType");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        return TypeTransformerKt.isExternalType(expectedType) != TypeTransformerKt.isExternalType(actualType) ? JsIrBuilder.INSTANCE.buildImplicitCast(irExpression, expectedType) : irExpression;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.AbstractValueUsageLowering, org.jetbrains.kotlin.backend.common.lower.AbstractValueUsageTransformer
    protected IrExpression useAsVarargElement(IrExpression element, IrVararg expression) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(expression, "expression");
        if (TypeTransformerKt.isExternalType(element.getType())) {
            return useAs(element, getIrBuiltIns().getAnyNType());
        }
        return super.useAsVarargElement(element, expression);
    }
}
