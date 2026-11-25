package org.jetbrains.kotlin.wasm.ir;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Types.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0004*\u00020\u0002\u001a\n\u0010\u0005\u001a\u00020\u0004*\u00020\u0006¨\u0006\u0007"}, d2 = {"getHeapType", "Lorg/jetbrains/kotlin/wasm/ir/WasmHeapType;", "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "referencesTypeDeclaration", "", "referencesTypeDeclarations", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunctionType;", "wasm.ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypesKt {
    public static final WasmHeapType getHeapType(WasmType wasmType) {
        Intrinsics.checkNotNullParameter(wasmType, "<this>");
        if (wasmType instanceof WasmRefType) {
            return ((WasmRefType) wasmType).getHeapType();
        }
        if (wasmType instanceof WasmRefNullType) {
            return ((WasmRefNullType) wasmType).getHeapType();
        }
        if (wasmType instanceof WasmRefNullrefType) {
            return WasmHeapType.Simple.None.INSTANCE;
        }
        if (wasmType instanceof WasmRefNullExternrefType) {
            return WasmHeapType.Simple.NoExtern.INSTANCE;
        }
        if (wasmType instanceof WasmEqRef) {
            return WasmHeapType.Simple.Eq.INSTANCE;
        }
        if (wasmType instanceof WasmAnyRef) {
            return WasmHeapType.Simple.Any.INSTANCE;
        }
        if (wasmType instanceof WasmFuncRef) {
            return WasmHeapType.Simple.Func.INSTANCE;
        }
        if (wasmType instanceof WasmExternRef) {
            return WasmHeapType.Simple.Extern.INSTANCE;
        }
        throw new IllegalStateException(("Unknown heap type for type " + wasmType).toString());
    }

    public static final boolean referencesTypeDeclarations(WasmFunctionType wasmFunctionType) {
        boolean z;
        Intrinsics.checkNotNullParameter(wasmFunctionType, "<this>");
        List<WasmType> parameterTypes = wasmFunctionType.getParameterTypes();
        boolean z2 = true;
        if (!(parameterTypes instanceof Collection) || !parameterTypes.isEmpty()) {
            Iterator<T> it = parameterTypes.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                if (referencesTypeDeclaration((WasmType) it.next())) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
            break;
        }
        List<WasmType> resultTypes = wasmFunctionType.getResultTypes();
        if ((resultTypes instanceof Collection) && resultTypes.isEmpty()) {
            z2 = false;
        } else {
            Iterator<T> it2 = resultTypes.iterator();
            while (it2.hasNext()) {
                if (referencesTypeDeclaration((WasmType) it2.next())) {
                }
            }
            z2 = false;
        }
        return z | z2;
    }

    public static final boolean referencesTypeDeclaration(WasmType wasmType) {
        Intrinsics.checkNotNullParameter(wasmType, "<this>");
        if (!(wasmType instanceof WasmRefNullType) && !(wasmType instanceof WasmRefType)) {
            return false;
        }
        WasmHeapType heapType = getHeapType(wasmType);
        return heapType instanceof WasmHeapType.Type;
    }
}
