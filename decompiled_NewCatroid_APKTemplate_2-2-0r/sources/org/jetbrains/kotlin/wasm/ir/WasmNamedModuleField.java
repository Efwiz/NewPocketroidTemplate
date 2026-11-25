package org.jetbrains.kotlin.wasm.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Declarations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002R\u001e\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\t\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0014\u0010\n\u001a\u00020\u000bX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u0082\u0001\b\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmNamedModuleField;", "", "()V", "id", "", "getId", "()Ljava/lang/Integer;", "setId", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "name", "", "getName", "()Ljava/lang/String;", "Lorg/jetbrains/kotlin/wasm/ir/WasmData;", "Lorg/jetbrains/kotlin/wasm/ir/WasmElement;", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunction;", "Lorg/jetbrains/kotlin/wasm/ir/WasmGlobal;", "Lorg/jetbrains/kotlin/wasm/ir/WasmMemory;", "Lorg/jetbrains/kotlin/wasm/ir/WasmTable;", "Lorg/jetbrains/kotlin/wasm/ir/WasmTag;", "Lorg/jetbrains/kotlin/wasm/ir/WasmTypeDeclaration;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class WasmNamedModuleField {
    private Integer id;
    private final String name;

    public /* synthetic */ WasmNamedModuleField(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private WasmNamedModuleField() {
        this.name = "";
    }

    public final Integer getId() {
        return this.id;
    }

    public final void setId(Integer num) {
        this.id = num;
    }

    public String getName() {
        return this.name;
    }
}
