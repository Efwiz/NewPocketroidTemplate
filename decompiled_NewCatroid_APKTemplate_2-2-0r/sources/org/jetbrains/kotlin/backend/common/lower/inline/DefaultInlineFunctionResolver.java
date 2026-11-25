package org.jetbrains.kotlin.backend.common.lower.inline;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FunctionInlining.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\bH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/inline/DefaultInlineFunctionResolver;", "Lorg/jetbrains/kotlin/backend/common/lower/inline/InlineFunctionResolver;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "getFunctionDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "getFunctionSymbol", "irFunction", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class DefaultInlineFunctionResolver implements InlineFunctionResolver {
    private final CommonBackendContext context;

    public DefaultInlineFunctionResolver(CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public CommonBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.inline.InlineFunctionResolver
    public IrFunction getFunctionDeclaration(IrFunctionSymbol symbol) {
        IrSimpleFunction irSimpleFunction;
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        if (FunctionInliningKt.isBuiltInSuspendCoroutineUninterceptedOrReturn(symbol.getOwner())) {
            return getContext().getIr().getSymbols().getSuspendCoroutineUninterceptedOrReturn().getOwner();
        }
        if (Intrinsics.areEqual(symbol, getContext().getIr().getSymbols().getCoroutineContextGetter())) {
            return getContext().getIr().getSymbols().getCoroutineGetContext().getOwner();
        }
        IrFunction owner = symbol.getOwner();
        IrSimpleFunction irSimpleFunction2 = owner instanceof IrSimpleFunction ? (IrSimpleFunction) owner : null;
        return (irSimpleFunction2 == null || (irSimpleFunction = (IrSimpleFunction) IrFakeOverrideUtilsKt.resolveFakeOverride$default(irSimpleFunction2, false, null, 3, null)) == null) ? symbol.getOwner() : irSimpleFunction;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.inline.InlineFunctionResolver
    public IrFunctionSymbol getFunctionSymbol(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        return irFunction.getSymbol();
    }
}
