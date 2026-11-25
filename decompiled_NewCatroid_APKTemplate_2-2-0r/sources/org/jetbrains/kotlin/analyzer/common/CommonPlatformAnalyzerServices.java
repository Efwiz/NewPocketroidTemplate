package org.jetbrains.kotlin.analyzer.common;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analyzer.ModuleInfo;
import org.jetbrains.kotlin.resolve.ImportPath;
import org.jetbrains.kotlin.resolve.PlatformConfigurator;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: CommonPlatform.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/common/CommonPlatformAnalyzerServices;", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "()V", "platformConfigurator", "Lorg/jetbrains/kotlin/resolve/PlatformConfigurator;", "getPlatformConfigurator", "()Lorg/jetbrains/kotlin/resolve/PlatformConfigurator;", "computePlatformSpecificDefaultImports", "", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "result", "", "Lorg/jetbrains/kotlin/resolve/ImportPath;", "dependencyOnBuiltIns", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo$DependencyOnBuiltIns;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CommonPlatformAnalyzerServices extends PlatformDependentAnalyzerServices {
    public static final CommonPlatformAnalyzerServices INSTANCE = new CommonPlatformAnalyzerServices();
    private static final PlatformConfigurator platformConfigurator = CommonPlatformConfigurator.INSTANCE;

    @Override // org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices
    public void computePlatformSpecificDefaultImports(StorageManager storageManager, List<ImportPath> result) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(result, "result");
    }

    private CommonPlatformAnalyzerServices() {
    }

    @Override // org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices
    public PlatformConfigurator getPlatformConfigurator() {
        return platformConfigurator;
    }

    @Override // org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices
    public ModuleInfo.DependencyOnBuiltIns dependencyOnBuiltIns() {
        return ModuleInfo.DependencyOnBuiltIns.AFTER_SDK;
    }
}
