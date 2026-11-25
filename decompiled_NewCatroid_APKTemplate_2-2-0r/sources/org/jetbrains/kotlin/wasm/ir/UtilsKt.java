package org.jetbrains.kotlin.wasm.ir;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Utils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"calculateIds", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmModule;", "wasm.ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilsKt {
    static /* synthetic */ void calculateIds$calculateIds$default(List list, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        calculateIds$calculateIds(list, i);
    }

    private static final void calculateIds$calculateIds(List<? extends WasmNamedModuleField> list, int i) {
        Iterator<? extends WasmNamedModuleField> it = list.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            it.next().setId(Integer.valueOf(i2 + i));
            i2++;
        }
    }

    public static final void calculateIds(WasmModule wasmModule) {
        Intrinsics.checkNotNullParameter(wasmModule, "<this>");
        calculateIds$calculateIds$default(wasmModule.getFunctionTypes(), 0, 1, null);
        calculateIds$calculateIds(wasmModule.getRecGroupTypes(), wasmModule.getFunctionTypes().size());
        calculateIds$calculateIds$default(wasmModule.getImportedFunctions(), 0, 1, null);
        calculateIds$calculateIds$default(wasmModule.getImportedMemories(), 0, 1, null);
        calculateIds$calculateIds$default(wasmModule.getImportedTables(), 0, 1, null);
        calculateIds$calculateIds$default(wasmModule.getImportedGlobals(), 0, 1, null);
        calculateIds$calculateIds$default(wasmModule.getImportedTags(), 0, 1, null);
        calculateIds$calculateIds$default(wasmModule.getElements(), 0, 1, null);
        calculateIds$calculateIds(wasmModule.getDefinedFunctions(), wasmModule.getImportedFunctions().size());
        calculateIds$calculateIds(wasmModule.getGlobals(), wasmModule.getImportedGlobals().size());
        calculateIds$calculateIds(wasmModule.getMemories(), wasmModule.getImportedMemories().size());
        calculateIds$calculateIds(wasmModule.getTables(), wasmModule.getImportedTables().size());
        calculateIds$calculateIds(wasmModule.getTags(), wasmModule.getImportedTags().size());
    }
}
