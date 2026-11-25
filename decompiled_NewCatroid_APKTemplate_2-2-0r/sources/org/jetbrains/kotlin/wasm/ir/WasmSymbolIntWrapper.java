package org.jetbrains.kotlin.wasm.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: Symbol.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006J\b\u0010\f\u001a\u00020\rH\u0016R\u0014\u0010\u0007\u001a\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmSymbolIntWrapper;", "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbolReadOnly;", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "Lorg/jetbrains/kotlin/wasm/ir/WasmNamedModuleField;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;)V", "owner", "getOwner", "()Ljava/lang/Integer;", "getSymbol", "()Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "toString", "", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmSymbolIntWrapper implements WasmSymbolReadOnly<Integer> {
    private final WasmSymbol<WasmNamedModuleField> symbol;

    /* JADX WARN: Multi-variable type inference failed */
    public WasmSymbolIntWrapper(WasmSymbol<? extends WasmNamedModuleField> symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.symbol = symbol;
    }

    public final WasmSymbol<WasmNamedModuleField> getSymbol() {
        return this.symbol;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.wasm.ir.WasmSymbolReadOnly
    public Integer getOwner() {
        Integer id2 = this.symbol.getOwner().getId();
        Intrinsics.checkNotNull(id2);
        return id2;
    }

    public String toString() {
        return String.valueOf(getOwner().intValue());
    }
}
