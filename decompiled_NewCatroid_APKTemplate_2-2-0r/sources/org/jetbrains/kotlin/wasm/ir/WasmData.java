package org.jetbrains.kotlin.wasm.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Declarations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmData;", "Lorg/jetbrains/kotlin/wasm/ir/WasmNamedModuleField;", "mode", "Lorg/jetbrains/kotlin/wasm/ir/WasmDataMode;", "bytes", "", "(Lorg/jetbrains/kotlin/wasm/ir/WasmDataMode;[B)V", "getBytes", "()[B", "getMode", "()Lorg/jetbrains/kotlin/wasm/ir/WasmDataMode;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmData extends WasmNamedModuleField {
    private final byte[] bytes;
    private final WasmDataMode mode;

    public final WasmDataMode getMode() {
        return this.mode;
    }

    public final byte[] getBytes() {
        return this.bytes;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WasmData(WasmDataMode mode, byte[] bytes) {
        super(null);
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        this.mode = mode;
        this.bytes = bytes;
    }
}
