package org.jetbrains.kotlin.backend.wasm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.lower.inline.DefaultInlineFunctionResolver;
import org.jetbrains.kotlin.backend.wasm.WasmBackendContext;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: WasmInlineFunctionResolver.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/lower/WasmInlineFunctionResolver;", "Lorg/jetbrains/kotlin/backend/common/lower/inline/DefaultInlineFunctionResolver;", "context", "Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;", "(Lorg/jetbrains/kotlin/backend/wasm/WasmBackendContext;)V", "enumEntriesIntrinsic", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "shouldExcludeFunctionFromInlining", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmInlineFunctionResolver extends DefaultInlineFunctionResolver {
    private final IrSimpleFunctionSymbol enumEntriesIntrinsic;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WasmInlineFunctionResolver(WasmBackendContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.enumEntriesIntrinsic = context.getWasmSymbols().getEnumEntriesIntrinsic();
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.inline.InlineFunctionResolver
    public boolean shouldExcludeFunctionFromInlining(IrFunctionSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return Intrinsics.areEqual(symbol, this.enumEntriesIntrinsic) || super.shouldExcludeFunctionFromInlining(symbol);
    }
}
