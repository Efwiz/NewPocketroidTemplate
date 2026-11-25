package org.jetbrains.kotlin.wasm.ir;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: Declarations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0006J\u000f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J)\u0010\f\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmFunctionType;", "Lorg/jetbrains/kotlin/wasm/ir/WasmTypeDeclaration;", "parameterTypes", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "resultTypes", "(Ljava/util/List;Ljava/util/List;)V", "getParameterTypes", "()Ljava/util/List;", "getResultTypes", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class WasmFunctionType extends WasmTypeDeclaration {
    private final List<WasmType> parameterTypes;
    private final List<WasmType> resultTypes;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ WasmFunctionType copy$default(WasmFunctionType wasmFunctionType, List list, List list2, int i, Object obj) {
        if ((i & 1) != 0) {
            list = wasmFunctionType.parameterTypes;
        }
        if ((i & 2) != 0) {
            list2 = wasmFunctionType.resultTypes;
        }
        return wasmFunctionType.copy(list, list2);
    }

    public final List<WasmType> component1() {
        return this.parameterTypes;
    }

    public final List<WasmType> component2() {
        return this.resultTypes;
    }

    public final WasmFunctionType copy(List<? extends WasmType> parameterTypes, List<? extends WasmType> resultTypes) {
        Intrinsics.checkNotNullParameter(parameterTypes, "parameterTypes");
        Intrinsics.checkNotNullParameter(resultTypes, "resultTypes");
        return new WasmFunctionType(parameterTypes, resultTypes);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof WasmFunctionType)) {
            return false;
        }
        WasmFunctionType wasmFunctionType = (WasmFunctionType) other;
        return Intrinsics.areEqual(this.parameterTypes, wasmFunctionType.parameterTypes) && Intrinsics.areEqual(this.resultTypes, wasmFunctionType.resultTypes);
    }

    public int hashCode() {
        return (this.parameterTypes.hashCode() * 31) + this.resultTypes.hashCode();
    }

    public String toString() {
        return "WasmFunctionType(parameterTypes=" + this.parameterTypes + ", resultTypes=" + this.resultTypes + ')';
    }

    public final List<WasmType> getParameterTypes() {
        return this.parameterTypes;
    }

    public final List<WasmType> getResultTypes() {
        return this.resultTypes;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public WasmFunctionType(List<? extends WasmType> parameterTypes, List<? extends WasmType> resultTypes) {
        super("", null);
        Intrinsics.checkNotNullParameter(parameterTypes, "parameterTypes");
        Intrinsics.checkNotNullParameter(resultTypes, "resultTypes");
        this.parameterTypes = parameterTypes;
        this.resultTypes = resultTypes;
    }
}
