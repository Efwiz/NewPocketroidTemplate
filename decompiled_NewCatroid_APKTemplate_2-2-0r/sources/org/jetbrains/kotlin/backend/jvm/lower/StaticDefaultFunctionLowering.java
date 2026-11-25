package org.jetbrains.kotlin.backend.jvm.lower;

import androidx.core.view.PointerIconCompat;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.ir.IrInlineUtilsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;

/* JADX INFO: compiled from: StaticDefaultFunctionLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\tH\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/StaticDefaultFunctionLowering;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "getStaticFunctionWithReceivers", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "function", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class StaticDefaultFunctionLowering extends IrElementTransformerVoid implements FileLoweringPass {
    private final JvmBackendContext context;

    public StaticDefaultFunctionLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        irFile.accept(this, null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitSimpleFunction(IrSimpleFunction declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (Intrinsics.areEqual(declaration.getOrigin(), IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER.INSTANCE) && declaration.getDispatchReceiverParameter() != null) {
            IrSimpleFunction staticFunctionWithReceivers = getStaticFunctionWithReceivers(declaration);
            staticFunctionWithReceivers.setBody(IrInlineUtilsKt.moveBodyTo(declaration, staticFunctionWithReceivers));
            declaration = staticFunctionWithReceivers;
        }
        return super.visitFunction(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitReturn(IrReturn expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrSimpleFunction irSimpleFunction = this.context.getStaticDefaultStubs().get(expression.getReturnTargetSymbol());
        if (irSimpleFunction != null) {
            expression = new IrReturnImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), irSimpleFunction.getSymbol(), expression.getValue());
        }
        return super.visitReturn(expression);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitCall(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrSimpleFunction owner = expression.getSymbol().getOwner();
        if (owner.getOrigin() != IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER.INSTANCE || expression.getDispatchReceiver() == null) {
            return super.visitCall(expression);
        }
        return super.visitCall(IrUtilsKt.irCall(expression, getStaticFunctionWithReceivers(owner), (56 & 4) != 0 ? false : true, (56 & 8) != 0 ? false : false, (56 & 16) != 0 ? null : null, (56 & 32) != 0 ? null : null));
    }

    private final IrSimpleFunction getStaticFunctionWithReceivers(IrSimpleFunction function) {
        IrSimpleFunction irSimpleFunctionPutIfAbsent;
        ConcurrentHashMap<IrSimpleFunctionSymbol, IrSimpleFunction> staticDefaultStubs = this.context.getStaticDefaultStubs();
        IrSimpleFunctionSymbol symbol = function.getSymbol();
        IrSimpleFunction irSimpleFunctionCreateStaticFunctionWithReceivers$default = staticDefaultStubs.get(symbol);
        if (irSimpleFunctionCreateStaticFunctionWithReceivers$default == null && (irSimpleFunctionPutIfAbsent = staticDefaultStubs.putIfAbsent(symbol, (irSimpleFunctionCreateStaticFunctionWithReceivers$default = IrUtilsKt.createStaticFunctionWithReceivers$default(this.context.getIrFactory(), function.getParent(), function.getName(), function, null, null, null, null, false, false, null, new StaticDefaultFunctionLowering$getStaticFunctionWithReceivers$1$1(this.context), PointerIconCompat.TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW, null)))) != null) {
            irSimpleFunctionCreateStaticFunctionWithReceivers$default = irSimpleFunctionPutIfAbsent;
        }
        Intrinsics.checkNotNullExpressionValue(irSimpleFunctionCreateStaticFunctionWithReceivers$default, "getOrPut(...)");
        return irSimpleFunctionCreateStaticFunctionWithReceivers$default;
    }
}
