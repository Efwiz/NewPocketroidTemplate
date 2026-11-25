package org.jetbrains.kotlin.wasm.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: Declarations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmImportDescriptor;", "", "moduleName", "", "declarationName", "(Ljava/lang/String;Ljava/lang/String;)V", "getDeclarationName", "()Ljava/lang/String;", "getModuleName", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class WasmImportDescriptor {
    private final String declarationName;
    private final String moduleName;

    public static /* synthetic */ WasmImportDescriptor copy$default(WasmImportDescriptor wasmImportDescriptor, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = wasmImportDescriptor.moduleName;
        }
        if ((i & 2) != 0) {
            str2 = wasmImportDescriptor.declarationName;
        }
        return wasmImportDescriptor.copy(str, str2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getModuleName() {
        return this.moduleName;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getDeclarationName() {
        return this.declarationName;
    }

    public final WasmImportDescriptor copy(String moduleName, String declarationName) {
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(declarationName, "declarationName");
        return new WasmImportDescriptor(moduleName, declarationName);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof WasmImportDescriptor)) {
            return false;
        }
        WasmImportDescriptor wasmImportDescriptor = (WasmImportDescriptor) other;
        return Intrinsics.areEqual(this.moduleName, wasmImportDescriptor.moduleName) && Intrinsics.areEqual(this.declarationName, wasmImportDescriptor.declarationName);
    }

    public int hashCode() {
        return (this.moduleName.hashCode() * 31) + this.declarationName.hashCode();
    }

    public String toString() {
        return "WasmImportDescriptor(moduleName=" + this.moduleName + ", declarationName=" + this.declarationName + ')';
    }

    public WasmImportDescriptor(String moduleName, String declarationName) {
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(declarationName, "declarationName");
        this.moduleName = moduleName;
        this.declarationName = declarationName;
    }

    public final String getModuleName() {
        return this.moduleName;
    }

    public final String getDeclarationName() {
        return this.declarationName;
    }
}
