package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmCachedDeclarations;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: InheritedDefaultMethodsOnClassesLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u000eH\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/InterfaceSuperCallsLowering;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "postprocessMovedThis", "irCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class InterfaceSuperCallsLowering extends IrElementTransformerVoid implements FileLoweringPass {
    private final JvmBackendContext context;

    public InterfaceSuperCallsLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        IrElementTransformerVoidKt.transformChildrenVoid(irFile, this);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitCall(IrCall expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrClassSymbol superQualifierSymbol = expression.getSuperQualifierSymbol();
        IrClass owner = superQualifierSymbol != null ? superQualifierSymbol.getOwner() : null;
        if (owner == null || !IrUtilsKt.isInterface(owner) || IrUtilsKt.isSuperToAny(expression)) {
            return super.visitCall(expression);
        }
        IrSimpleFunction owner2 = expression.getSymbol().getOwner();
        if (InheritedDefaultMethodsOnClassesLoweringKt.isDefinitelyNotDefaultImplsMethod$default(owner2, this.context.getConfig().getJvmDefaultMode(), null, 2, null)) {
            return super.visitCall(expression);
        }
        IrCall irCallCreateDelegatingCallWithPlaceholderTypeArguments = JvmIrUtilsKt.createDelegatingCallWithPlaceholderTypeArguments(expression, JvmCachedDeclarations.getDefaultImplsFunction$default(this.context.getCachedDeclarations(), owner2, false, 2, null), this.context.getIrBuiltIns());
        postprocessMovedThis(irCallCreateDelegatingCallWithPlaceholderTypeArguments);
        return super.visitCall(irCallCreateDelegatingCallWithPlaceholderTypeArguments);
    }

    private final void postprocessMovedThis(IrCall irCall) {
        Object next;
        Iterator<T> it = irCall.getSymbol().getOwner().getValueParameters().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(((IrValueParameter) next).getOrigin(), IrDeclarationOrigin.MOVED_DISPATCH_RECEIVER.INSTANCE));
        IrValueParameter irValueParameter = (IrValueParameter) next;
        if (irValueParameter == null) {
            return;
        }
        int index = irValueParameter.getIndex();
        IrExpression valueArgument = irCall.getValueArgument(index);
        irCall.putValueArgument(index, valueArgument != null ? InheritedDefaultMethodsOnClassesLoweringKt.reinterpretAsDispatchReceiverOfType(valueArgument, irValueParameter.getType()) : null);
    }
}
