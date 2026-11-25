package org.jetbrains.kotlin.fir.deserialization;

import java.nio.file.Path;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;

/* JADX INFO: compiled from: ModuleDataProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0011\u001a\u00020\u00032\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0016R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/deserialization/SingleModuleDataProvider;", "Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "(Lorg/jetbrains/kotlin/fir/FirModuleData;)V", "allModuleData", "", "getAllModuleData", "()Ljava/util/Collection;", "analyzerServices", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "getAnalyzerServices", "()Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getModuleData", "path", "Ljava/nio/file/Path;", "fir-deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SingleModuleDataProvider extends ModuleDataProvider {
    private final FirModuleData moduleData;

    public SingleModuleDataProvider(FirModuleData moduleData) {
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        this.moduleData = moduleData;
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.ModuleDataProvider
    public TargetPlatform getPlatform() {
        return this.moduleData.getPlatform();
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.ModuleDataProvider
    public PlatformDependentAnalyzerServices getAnalyzerServices() {
        return this.moduleData.getAnalyzerServices();
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.ModuleDataProvider
    public Collection<FirModuleData> getAllModuleData() {
        return CollectionsKt.listOf(this.moduleData);
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.ModuleDataProvider
    public FirModuleData getModuleData(Path path) {
        return this.moduleData;
    }
}
