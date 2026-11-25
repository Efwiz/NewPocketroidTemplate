package org.jetbrains.kotlin.wasm.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Declarations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0003\u0007\b\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmTypeDeclaration;", "Lorg/jetbrains/kotlin/wasm/ir/WasmNamedModuleField;", "name", "", "(Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "Lorg/jetbrains/kotlin/wasm/ir/WasmArrayDeclaration;", "Lorg/jetbrains/kotlin/wasm/ir/WasmFunctionType;", "Lorg/jetbrains/kotlin/wasm/ir/WasmStructDeclaration;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class WasmTypeDeclaration extends WasmNamedModuleField {
    private final String name;

    public /* synthetic */ WasmTypeDeclaration(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    @Override // org.jetbrains.kotlin.wasm.ir.WasmNamedModuleField
    public String getName() {
        return this.name;
    }

    private WasmTypeDeclaration(String str) {
        super(null);
        this.name = str;
    }
}
