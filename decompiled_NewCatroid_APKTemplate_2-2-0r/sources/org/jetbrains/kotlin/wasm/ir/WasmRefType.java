package org.jetbrains.kotlin.wasm.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: Types.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmRefType;", "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "heapType", "Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType;)V", "getHeapType", "()Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class WasmRefType extends WasmType {
    private final WasmHeapType heapType;

    public static /* synthetic */ WasmRefType copy$default(WasmRefType wasmRefType, WasmHeapType wasmHeapType, int i, Object obj) {
        if ((i & 1) != 0) {
            wasmHeapType = wasmRefType.heapType;
        }
        return wasmRefType.copy(wasmHeapType);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final WasmHeapType getHeapType() {
        return this.heapType;
    }

    public final WasmRefType copy(WasmHeapType heapType) {
        Intrinsics.checkNotNullParameter(heapType, "heapType");
        return new WasmRefType(heapType);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof WasmRefType) && Intrinsics.areEqual(this.heapType, ((WasmRefType) other).heapType);
    }

    public int hashCode() {
        return this.heapType.hashCode();
    }

    @Override // org.jetbrains.kotlin.wasm.ir.WasmType
    public String toString() {
        return "WasmRefType(heapType=" + this.heapType + ')';
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WasmRefType(WasmHeapType heapType) {
        super("ref", (byte) -28, null);
        Intrinsics.checkNotNullParameter(heapType, "heapType");
        this.heapType = heapType;
    }

    public final WasmHeapType getHeapType() {
        return this.heapType;
    }
}
