package org.jetbrains.kotlin.wasm.ir;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Declarations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\b¢\u0006\u0002\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0019\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmStructDeclaration;", "Lorg/jetbrains/kotlin/wasm/ir/WasmTypeDeclaration;", "name", "", "fields", "", "Lorg/jetbrains/kotlin/wasm/ir/WasmStructFieldDeclaration;", "superType", "Lorg/jetbrains/kotlin/wasm/ir/WasmSymbolReadOnly;", "(Ljava/lang/String;Ljava/util/List;Lorg/jetbrains/kotlin/wasm/ir/WasmSymbolReadOnly;)V", "getFields", "()Ljava/util/List;", "getSuperType", "()Lorg/jetbrains/kotlin/wasm/ir/WasmSymbolReadOnly;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmStructDeclaration extends WasmTypeDeclaration {
    private final List<WasmStructFieldDeclaration> fields;
    private final WasmSymbolReadOnly<WasmTypeDeclaration> superType;

    public final List<WasmStructFieldDeclaration> getFields() {
        return this.fields;
    }

    public final WasmSymbolReadOnly<WasmTypeDeclaration> getSuperType() {
        return this.superType;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public WasmStructDeclaration(String name2, List<WasmStructFieldDeclaration> fields, WasmSymbolReadOnly<? extends WasmTypeDeclaration> wasmSymbolReadOnly) {
        super(name2, null);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(fields, "fields");
        this.fields = fields;
        this.superType = wasmSymbolReadOnly;
    }
}
