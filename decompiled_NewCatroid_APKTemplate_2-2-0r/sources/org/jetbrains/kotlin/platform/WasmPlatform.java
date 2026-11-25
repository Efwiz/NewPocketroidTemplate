package org.jetbrains.kotlin.platform;

import kotlin.Metadata;

/* JADX INFO: compiled from: WasmPlatform.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/platform/WasmPlatform;", "Lorg/jetbrains/kotlin/platform/SimplePlatform;", "()V", "oldFashionedDescription", "", "getOldFashionedDescription", "()Ljava/lang/String;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmPlatform extends SimplePlatform {
    public static final WasmPlatform INSTANCE = new WasmPlatform();

    private WasmPlatform() {
        super("Wasm");
    }

    @Override // org.jetbrains.kotlin.platform.SimplePlatform
    public String getOldFashionedDescription() {
        return "Wasm ";
    }
}
