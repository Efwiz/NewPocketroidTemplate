package org.jetbrains.kotlin.wasm.ir;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: Operators.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"WASM_OP_PSEUDO_OPCODE", "", "opcodesToOp", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmOp;", "getOpcodesToOp", "()Ljava/util/Map;", "wasm.ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class OperatorsKt {
    public static final int WASM_OP_PSEUDO_OPCODE = 65535;
    private static final Map<Integer, WasmOp> opcodesToOp;

    public static final Map<Integer, WasmOp> getOpcodesToOp() {
        return opcodesToOp;
    }

    static {
        WasmOp[] wasmOpArrValues = WasmOp.values();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(wasmOpArrValues.length), 16));
        for (WasmOp wasmOp : wasmOpArrValues) {
            linkedHashMap.put(Integer.valueOf(wasmOp.getOpcode()), wasmOp);
        }
        opcodesToOp = linkedHashMap;
    }
}
