package org.jetbrains.kotlin.backend.wasm.ir2wasm;

import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.wasm.ir.WasmSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: WasmCompiledModuleFragment.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001\u001aN\u0010\u0004\u001a\u00020\u0005\"\u0004\b\u0000\u0010\u0006\"\b\b\u0001\u0010\u0007*\u00020\b\"\u000e\b\u0002\u0010\t*\b\u0012\u0004\u0012\u0002H\u00070\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u0002H\t0\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u0002H\u00070\f\u001a\u0012\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\bH\u0002¨\u0006\u0011"}, d2 = {"alignUp", "", "x", "alignment", "bind", "", "IrSymbolType", "WasmDeclarationType", "", "WasmSymbolType", "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbol;", "unbound", "", "defined", "irSymbolDebugDump", "", NativeSymbol.TYPE_NAME, "backend.wasm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class WasmCompiledModuleFragmentKt {
    public static final int alignUp(int i, int i2) {
        return ((i + i2) - 1) & (~(i2 - 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String irSymbolDebugDump(Object obj) {
        if (obj instanceof IrFunctionSymbol) {
            return "function " + IrUtilsKt.getFqNameWhenAvailable(((IrFunctionSymbol) obj).getOwner());
        }
        return obj instanceof IrClassSymbol ? ExternalAnnotationProvider.CLASS_PREFIX + IrUtilsKt.getFqNameWhenAvailable(((IrClassSymbol) obj).getOwner()) : String.valueOf(obj);
    }

    public static final <IrSymbolType, WasmDeclarationType, WasmSymbolType extends WasmSymbol<? extends WasmDeclarationType>> void bind(Map<IrSymbolType, ? extends WasmSymbolType> unbound, Map<IrSymbolType, ? extends WasmDeclarationType> defined) {
        Intrinsics.checkNotNullParameter(unbound, "unbound");
        Intrinsics.checkNotNullParameter(defined, "defined");
        for (Map.Entry<IrSymbolType, ? extends WasmSymbolType> entry : unbound.entrySet()) {
            IrSymbolType key = entry.getKey();
            WasmSymbolType value = entry.getValue();
            if (!defined.containsKey(key)) {
                throw new IllegalStateException(("Can't link symbol " + irSymbolDebugDump(key)).toString());
            }
            value.bind(MapsKt.getValue(defined, key));
        }
    }
}
