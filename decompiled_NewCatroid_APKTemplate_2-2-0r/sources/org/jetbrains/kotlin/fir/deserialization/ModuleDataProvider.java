package org.jetbrains.kotlin.fir.deserialization;

import java.nio.file.Path;
import java.util.Collection;
import kotlin.Metadata;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;

/* JADX INFO: compiled from: ModuleDataProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0010\u001a\u0004\u0018\u00010\u00052\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H&R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;", "", "()V", "allModuleData", "", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getAllModuleData", "()Ljava/util/Collection;", "analyzerServices", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "getAnalyzerServices", "()Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getModuleData", "path", "Ljava/nio/file/Path;", "fir-deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ModuleDataProvider {
    public abstract Collection<FirModuleData> getAllModuleData();

    public abstract PlatformDependentAnalyzerServices getAnalyzerServices();

    public abstract FirModuleData getModuleData(Path path);

    public abstract TargetPlatform getPlatform();
}
