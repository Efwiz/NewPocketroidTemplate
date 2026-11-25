package org.jetbrains.kotlin.backend.wasm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: compiler.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\n\u0018\u00002\u00020\u0001B3\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000b¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/WasmCompilerResult;", "", "wat", "", "jsUninstantiatedWrapper", "jsWrapper", "wasm", "", "sourceMap", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[BLjava/lang/String;)V", "getJsUninstantiatedWrapper", "()Ljava/lang/String;", "getJsWrapper", "getSourceMap", "getWasm", "()[B", "getWat", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmCompilerResult {
    private final String jsUninstantiatedWrapper;
    private final String jsWrapper;
    private final String sourceMap;
    private final byte[] wasm;
    private final String wat;

    public WasmCompilerResult(String str, String str2, String jsWrapper, byte[] wasm, String str3) {
        Intrinsics.checkNotNullParameter(jsWrapper, "jsWrapper");
        Intrinsics.checkNotNullParameter(wasm, "wasm");
        this.wat = str;
        this.jsUninstantiatedWrapper = str2;
        this.jsWrapper = jsWrapper;
        this.wasm = wasm;
        this.sourceMap = str3;
    }

    public final String getWat() {
        return this.wat;
    }

    public final String getJsUninstantiatedWrapper() {
        return this.jsUninstantiatedWrapper;
    }

    public final String getJsWrapper() {
        return this.jsWrapper;
    }

    public final byte[] getWasm() {
        return this.wasm;
    }

    public final String getSourceMap() {
        return this.sourceMap;
    }
}
