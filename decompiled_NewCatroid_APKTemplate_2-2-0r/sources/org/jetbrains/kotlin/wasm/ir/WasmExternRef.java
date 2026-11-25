package org.jetbrains.kotlin.wasm.ir;

import com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper;
import kotlin.Metadata;

/* JADX INFO: compiled from: Types.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmExternRef;", "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "()V", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmExternRef extends WasmType {
    public static final WasmExternRef INSTANCE = new WasmExternRef();

    private WasmExternRef() {
        super("externref", ByteSourceJsonBootstrapper.UTF8_BOM_1, null);
    }
}
