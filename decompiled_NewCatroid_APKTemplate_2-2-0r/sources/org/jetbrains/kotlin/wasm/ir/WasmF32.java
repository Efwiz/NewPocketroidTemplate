package org.jetbrains.kotlin.wasm.ir;

import kotlin.Metadata;

/* JADX INFO: compiled from: Types.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmF32;", "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "()V", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmF32 extends WasmType {
    public static final WasmF32 INSTANCE = new WasmF32();

    private WasmF32() {
        super("f32", (byte) -3, null);
    }
}
