package org.jetbrains.kotlin.wasm.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: Declarations.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/WasmStructFieldDeclaration;", "", "name", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "isMutable", "", "(Ljava/lang/String;Lorg/jetbrains/kotlin/wasm/ir/WasmType;Z)V", "()Z", "getName", "()Ljava/lang/String;", "getType", "()Lorg/jetbrains/kotlin/wasm/ir/WasmType;", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmStructFieldDeclaration {
    private final boolean isMutable;
    private final String name;
    private final WasmType type;

    public WasmStructFieldDeclaration(String name2, WasmType type, boolean z) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        this.name = name2;
        this.type = type;
        this.isMutable = z;
    }

    public final String getName() {
        return this.name;
    }

    public final WasmType getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: isMutable, reason: from getter */
    public final boolean getIsMutable() {
        return this.isMutable;
    }
}
