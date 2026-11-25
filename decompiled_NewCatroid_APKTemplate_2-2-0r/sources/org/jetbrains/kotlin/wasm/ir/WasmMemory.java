package org.jetbrains.kotlin.wasm.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Declarations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmMemory;", "Lorg/jetbrains/kotlin/wasm/ir/WasmNamedModuleField;", "limits", "Lorg/jetbrains/kotlin/wasm/ir/WasmLimits;", "importPair", "Lorg/jetbrains/kotlin/wasm/ir/WasmImportDescriptor;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmLimits;Lorg/jetbrains/kotlin/wasm/ir/WasmImportDescriptor;)V", "getImportPair", "()Lorg/jetbrains/kotlin/wasm/ir/WasmImportDescriptor;", "getLimits", "()Lorg/jetbrains/kotlin/wasm/ir/WasmLimits;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmMemory extends WasmNamedModuleField {
    private final WasmImportDescriptor importPair;
    private final WasmLimits limits;

    public /* synthetic */ WasmMemory(WasmLimits wasmLimits, WasmImportDescriptor wasmImportDescriptor, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(wasmLimits, (i & 2) != 0 ? null : wasmImportDescriptor);
    }

    public final WasmLimits getLimits() {
        return this.limits;
    }

    public final WasmImportDescriptor getImportPair() {
        return this.importPair;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WasmMemory(WasmLimits limits, WasmImportDescriptor wasmImportDescriptor) {
        super(null);
        Intrinsics.checkNotNullParameter(limits, "limits");
        this.limits = limits;
        this.importPair = wasmImportDescriptor;
    }
}
