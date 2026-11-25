package org.jetbrains.kotlin.backend.wasm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;

/* JADX INFO: compiled from: WasmArrayConstructorLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/WasmArrayConstructorTransformer;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "context", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "(Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;)V", "getContainer", "()Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "getContext", "()Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "visitConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class WasmArrayConstructorTransformer extends IrElementTransformerVoidWithContext {
    private final IrSymbolOwner container;
    private final WasmBackendContext context;

    public final WasmBackendContext getContext() {
        return this.context;
    }

    public final IrSymbolOwner getContainer() {
        return this.container;
    }

    public WasmArrayConstructorTransformer(WasmBackendContext context, IrSymbolOwner container) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(container, "container");
        this.context = context;
        this.container = container;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitConstructorCall(IrConstructorCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrConstructor owner = expression.getSymbol().getOwner();
        transformChildrenVoid(expression);
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = owner.getValueParameters().size() == 2 ? this.context.getWasmSymbols().getPrimitiveTypeToCreateTypedArray().get(AdditionalIrUtilsKt.getConstructedClass(owner).getSymbol()) : null;
        if (irSimpleFunctionSymbol == null) {
            return expression;
        }
        ScopeWithIr currentScope = getCurrentScope();
        if (currentScope == null) {
            currentScope = createScope(this.container);
        }
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, currentScope.getScope().getScopeOwnerSymbol(), 0, 0, 6, (Object) null);
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), expression.getStartOffset(), expression.getEndOffset(), null, null, false, 64, null);
        IrCall irCallIrCall$default = ExpressionHelpersKt.irCall$default(irBlockBuilder, irSimpleFunctionSymbol, expression.getType(), 0, 0, null, 28, null);
        int typeArgumentsCount = expression.getTypeArgumentsCount();
        for (int i = 0; i < typeArgumentsCount; i++) {
            irCallIrCall$default.putTypeArgument(i, expression.getTypeArgument(i));
        }
        int valueArgumentsCount = expression.getValueArgumentsCount();
        for (int i2 = 0; i2 < valueArgumentsCount; i2++) {
            irCallIrCall$default.putValueArgument(i2, expression.getValueArgument(i2));
        }
        irBlockBuilder.unaryPlus(irCallIrCall$default);
        return irBlockBuilder.getIrBlockBody();
    }
}
