package org.jetbrains.kotlin.cli.js.klib;

import kotlin.Metadata;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;
import org.jetbrains.kotlin.wasm.resolve.WasmWasiPlatformAnalyzerServices;

/* JADX INFO: compiled from: TopDownAnalyzerFacadeForWasm.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/cli/js/klib/TopDownAnalyzerFacadeForWasmWasi;", "Lorg/jetbrains/kotlin/cli/js/klib/TopDownAnalyzerFacadeForWasm;", "()V", "analyzerServices", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "getAnalyzerServices", "()Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "cli-js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TopDownAnalyzerFacadeForWasmWasi extends TopDownAnalyzerFacadeForWasm {
    public static final TopDownAnalyzerFacadeForWasmWasi INSTANCE = new TopDownAnalyzerFacadeForWasmWasi();
    private static final PlatformDependentAnalyzerServices analyzerServices = WasmWasiPlatformAnalyzerServices.INSTANCE;

    private TopDownAnalyzerFacadeForWasmWasi() {
    }

    @Override // org.jetbrains.kotlin.js.analyze.AbstractTopDownAnalyzerFacadeForWeb
    public PlatformDependentAnalyzerServices getAnalyzerServices() {
        return analyzerServices;
    }
}
