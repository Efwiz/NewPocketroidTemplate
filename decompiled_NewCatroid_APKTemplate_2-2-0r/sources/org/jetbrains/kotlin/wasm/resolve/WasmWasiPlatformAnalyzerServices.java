package org.jetbrains.kotlin.wasm.resolve;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.DefaultBuiltIns;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.resolve.ImportPath;
import org.jetbrains.kotlin.resolve.PlatformConfigurator;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: WasmPlatformAnalyzerServices.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0016R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/wasm/resolve/WasmWasiPlatformAnalyzerServices;", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "()V", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "platformConfigurator", "Lorg/jetbrains/kotlin/resolve/PlatformConfigurator;", "getPlatformConfigurator", "()Lorg/jetbrains/kotlin/resolve/PlatformConfigurator;", "computePlatformSpecificDefaultImports", "", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "result", "", "Lorg/jetbrains/kotlin/resolve/ImportPath;", "wasm.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmWasiPlatformAnalyzerServices extends PlatformDependentAnalyzerServices {
    public static final WasmWasiPlatformAnalyzerServices INSTANCE = new WasmWasiPlatformAnalyzerServices();
    private static final PlatformConfigurator platformConfigurator = WasmWasiPlatformConfigurator.INSTANCE;

    private WasmWasiPlatformAnalyzerServices() {
    }

    @Override // org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices
    public void computePlatformSpecificDefaultImports(StorageManager storageManager, List<ImportPath> result) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(result, "result");
        result.add(ImportPath.INSTANCE.fromString("kotlin.wasm.*"));
    }

    @Override // org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices
    public PlatformConfigurator getPlatformConfigurator() {
        return platformConfigurator;
    }

    public final KotlinBuiltIns getBuiltIns() {
        return DefaultBuiltIns.INSTANCE.getInstance();
    }
}
