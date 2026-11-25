package org.jetbrains.kotlin.fir.deserialization;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;

/* JADX INFO: compiled from: ModuleDataProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\u0006J\u0014\u0010\u0013\u001a\u0004\u0018\u00010\u00042\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u00020\u0010X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/deserialization/MultipleModuleDataProvider;", "Lorg/jetbrains/kotlin/fir/deserialization/ModuleDataProvider;", "moduleDataWithFilters", "", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "Lorg/jetbrains/kotlin/fir/deserialization/LibraryPathFilter;", "(Ljava/util/Map;)V", "allModuleData", "", "getAllModuleData", "()Ljava/util/Collection;", "analyzerServices", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "getAnalyzerServices", "()Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getPlatform", "()Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getModuleData", "path", "Ljava/nio/file/Path;", "fir-deserialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MultipleModuleDataProvider extends ModuleDataProvider {
    private final PlatformDependentAnalyzerServices analyzerServices;
    private final Map<FirModuleData, LibraryPathFilter> moduleDataWithFilters;
    private final TargetPlatform platform;

    /* JADX WARN: Multi-variable type inference failed */
    public MultipleModuleDataProvider(Map<FirModuleData, ? extends LibraryPathFilter> moduleDataWithFilters) {
        Intrinsics.checkNotNullParameter(moduleDataWithFilters, "moduleDataWithFilters");
        this.moduleDataWithFilters = moduleDataWithFilters;
        if (moduleDataWithFilters.isEmpty()) {
            throw new IllegalArgumentException("ModuleDataProvider must contain at least one module data".toString());
        }
        Iterator it = moduleDataWithFilters.keySet().iterator();
        TargetPlatform platform = ((FirModuleData) it.next()).getPlatform();
        while (it.hasNext()) {
            if (!Intrinsics.areEqual(((FirModuleData) it.next()).getPlatform(), platform)) {
                throw new IllegalArgumentException(("All module data should have same target platform, but was: " + CollectionsKt.joinToString$default(this.moduleDataWithFilters.keySet(), null, null, null, 0, null, new Function1<FirModuleData, CharSequence>() { // from class: org.jetbrains.kotlin.fir.deserialization.MultipleModuleDataProvider$3$1
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(FirModuleData it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        return it2.getName().asString() + ": " + it2.getPlatform();
                    }
                }, 31, null)).toString());
            }
        }
        Iterator<T> it2 = this.moduleDataWithFilters.keySet().iterator();
        PlatformDependentAnalyzerServices analyzerServices = ((FirModuleData) it2.next()).getAnalyzerServices();
        while (it2.hasNext()) {
            if (!Intrinsics.areEqual(((FirModuleData) it2.next()).getAnalyzerServices(), analyzerServices)) {
                throw new IllegalArgumentException(("All module data should have same analyzerServices, but was: " + CollectionsKt.joinToString$default(this.moduleDataWithFilters.keySet(), null, null, null, 0, null, new Function1<FirModuleData, CharSequence>() { // from class: org.jetbrains.kotlin.fir.deserialization.MultipleModuleDataProvider$5$1
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(FirModuleData it3) {
                        Intrinsics.checkNotNullParameter(it3, "it");
                        return it3.getName().asString() + ": " + Reflection.getOrCreateKotlinClass(it3.getAnalyzerServices().getClass()).getSimpleName();
                    }
                }, 31, null)).toString());
            }
        }
        this.platform = ((FirModuleData) CollectionsKt.first(getAllModuleData())).getPlatform();
        this.analyzerServices = ((FirModuleData) CollectionsKt.first(getAllModuleData())).getAnalyzerServices();
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.ModuleDataProvider
    public TargetPlatform getPlatform() {
        return this.platform;
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.ModuleDataProvider
    public PlatformDependentAnalyzerServices getAnalyzerServices() {
        return this.analyzerServices;
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.ModuleDataProvider
    public Collection<FirModuleData> getAllModuleData() {
        return this.moduleDataWithFilters.keySet();
    }

    @Override // org.jetbrains.kotlin.fir.deserialization.ModuleDataProvider
    public FirModuleData getModuleData(Path path) {
        for (Map.Entry<FirModuleData, LibraryPathFilter> entry : this.moduleDataWithFilters.entrySet()) {
            FirModuleData key = entry.getKey();
            if (entry.getValue().accepts(path)) {
                return key;
            }
        }
        return null;
    }
}
