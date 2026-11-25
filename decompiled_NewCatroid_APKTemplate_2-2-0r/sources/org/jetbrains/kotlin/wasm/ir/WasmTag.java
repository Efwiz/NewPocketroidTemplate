package org.jetbrains.kotlin.wasm.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: Declarations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmTag;", "Lorg/jetbrains/kotlin/wasm/ir/WasmNamedModuleField;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/wasm/ir/WasmFunctionType;", "importPair", "Lorg/jetbrains/kotlin/wasm/ir/WasmImportDescriptor;", "(Lorg/jetbrains/kotlin/wasm/ir/WasmFunctionType;Lorg/jetbrains/kotlin/wasm/ir/WasmImportDescriptor;)V", "getImportPair", "()Lorg/jetbrains/kotlin/wasm/ir/WasmImportDescriptor;", "getType", "()Lorg/jetbrains/kotlin/wasm/ir/WasmFunctionType;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmTag extends WasmNamedModuleField {
    private final WasmImportDescriptor importPair;
    private final WasmFunctionType type;

    public /* synthetic */ WasmTag(WasmFunctionType wasmFunctionType, WasmImportDescriptor wasmImportDescriptor, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(wasmFunctionType, (i & 2) != 0 ? null : wasmImportDescriptor);
    }

    public final WasmFunctionType getType() {
        return this.type;
    }

    public final WasmImportDescriptor getImportPair() {
        return this.importPair;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WasmTag(WasmFunctionType type, WasmImportDescriptor wasmImportDescriptor) {
        super(null);
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
        this.importPair = wasmImportDescriptor;
        type.getResultTypes().isEmpty();
    }
}
