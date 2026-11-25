package org.jetbrains.kotlin.wasm.ir;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: Declarations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010\rR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0012R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmGlobal;", "Lorg/jetbrains/kotlin/wasm/ir/WasmNamedModuleField;", "name", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "isMutable", "", ConfigConstants.CONFIG_INIT_SECTION, "", "Lorg/jetbrains/kotlin/wasm/ir/WasmInstr;", "importPair", "Lorg/jetbrains/kotlin/wasm/ir/WasmImportDescriptor;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/wasm/ir/WasmType;ZLjava/util/List;Lorg/jetbrains/kotlin/wasm/ir/WasmImportDescriptor;)V", "getImportPair", "()Lorg/jetbrains/kotlin/wasm/ir/WasmImportDescriptor;", "getInit", "()Ljava/util/List;", "()Z", "getName", "()Ljava/lang/String;", "getType", "()Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmGlobal extends WasmNamedModuleField {
    private final WasmImportDescriptor importPair;
    private final List<WasmInstr> init;
    private final boolean isMutable;
    private final String name;
    private final WasmType type;

    public /* synthetic */ WasmGlobal(String str, WasmType wasmType, boolean z, List list, WasmImportDescriptor wasmImportDescriptor, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, wasmType, z, list, (i & 16) != 0 ? null : wasmImportDescriptor);
    }

    @Override // org.jetbrains.kotlin.wasm.ir.WasmNamedModuleField
    public String getName() {
        return this.name;
    }

    public final WasmType getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: isMutable, reason: from getter */
    public final boolean getIsMutable() {
        return this.isMutable;
    }

    public final List<WasmInstr> getInit() {
        return this.init;
    }

    public final WasmImportDescriptor getImportPair() {
        return this.importPair;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public WasmGlobal(String name2, WasmType type, boolean z, List<? extends WasmInstr> init, WasmImportDescriptor wasmImportDescriptor) {
        super(null);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(init, "init");
        this.name = name2;
        this.type = type;
        this.isMutable = z;
        this.init = init;
        this.importPair = wasmImportDescriptor;
    }
}
