package org.jetbrains.kotlin.backend.wasm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;

/* JADX INFO: compiled from: WasmArrayConstructorReferenceLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/WasmArrayConstructorReferenceTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "context", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "(Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "visitFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class WasmArrayConstructorReferenceTransformer extends IrElementTransformerVoid {
    private final WasmBackendContext context;

    public WasmArrayConstructorReferenceTransformer(WasmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final WasmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitFunctionReference(IrFunctionReference expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        transformChildrenVoid(expression);
        IrFunction owner = expression.getSymbol().getOwner();
        if (!(owner instanceof IrConstructor)) {
            return expression;
        }
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = owner.getValueParameters().size() == 2 ? this.context.getWasmSymbols().getPrimitiveTypeToCreateTypedArray().get(AdditionalIrUtilsKt.getConstructedClass((IrConstructor) owner).getSymbol()) : null;
        if (irSimpleFunctionSymbol == null) {
            return expression;
        }
        IrSimpleFunctionSymbol irSimpleFunctionSymbol2 = irSimpleFunctionSymbol;
        IrFunctionReferenceImpl irFunctionReferenceImpl = new IrFunctionReferenceImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), irSimpleFunctionSymbol2, expression.getTypeArgumentsCount(), expression.getValueArgumentsCount(), irSimpleFunctionSymbol2, expression.getOrigin());
        int typeArgumentsCount = expression.getTypeArgumentsCount();
        for (int i = 0; i < typeArgumentsCount; i++) {
            irFunctionReferenceImpl.putTypeArgument(i, expression.getTypeArgument(i));
        }
        int valueArgumentsCount = expression.getValueArgumentsCount();
        for (int i2 = 0; i2 < valueArgumentsCount; i2++) {
            irFunctionReferenceImpl.putValueArgument(i2, expression.getValueArgument(i2));
        }
        return irFunctionReferenceImpl;
    }
}
