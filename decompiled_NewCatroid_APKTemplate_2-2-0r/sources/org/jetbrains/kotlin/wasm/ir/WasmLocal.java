package org.jetbrains.kotlin.wasm.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: Declarations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmLocal;", "", "id", "", "name", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "isParameter", "", "(ILjava/lang/String;Lorg/jetbrains/kotlin/wasm/ir/WasmType;Z)V", "getId", "()I", "()Z", "getName", "()Ljava/lang/String;", "getType", "()Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmLocal {
    private final int id;
    private final boolean isParameter;
    private final String name;
    private final WasmType type;

    public WasmLocal(int i, String name2, WasmType type, boolean z) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        this.id = i;
        this.name = name2;
        this.type = type;
        this.isParameter = z;
    }

    public final int getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final WasmType getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: isParameter, reason: from getter */
    public final boolean getIsParameter() {
        return this.isParameter;
    }
}
