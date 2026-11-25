package org.jetbrains.kotlin.analyzer;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleCapability;

/* JADX INFO: compiled from: AnalyzerFacade.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0002\u001a%\u0010\u0003\u001a\u0004\u0018\u0001H\u0004\"\u0004\b\u0000\u0010\u0004*\u00020\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0006¢\u0006\u0002\u0010\u0007\u001a\n\u0010\b\u001a\u00020\u0002*\u00020\u0002¨\u0006\t"}, d2 = {"flatten", "", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "getCapability", ExifInterface.GPS_DIRECTION_TRUE, "capability", "Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "(Lorg/jetbrains/kotlin/analyzer/ModuleInfo;Lorg/jetbrains/kotlin/descriptors/ModuleCapability;)Ljava/lang/Object;", "unwrapPlatform", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AnalyzerFacadeKt {
    public static final List<ModuleInfo> flatten(ModuleInfo moduleInfo) {
        Intrinsics.checkNotNullParameter(moduleInfo, "<this>");
        return moduleInfo instanceof CombinedModuleInfo ? CollectionsKt.plus((Collection) CollectionsKt.listOf(moduleInfo), (Iterable) ((CombinedModuleInfo) moduleInfo).getContainedModules()) : CollectionsKt.listOf(moduleInfo);
    }

    public static final ModuleInfo unwrapPlatform(ModuleInfo moduleInfo) {
        Intrinsics.checkNotNullParameter(moduleInfo, "<this>");
        return moduleInfo instanceof CombinedModuleInfo ? ((CombinedModuleInfo) moduleInfo).getPlatformModule() : moduleInfo;
    }

    public static final <T> T getCapability(ModuleInfo moduleInfo, ModuleCapability<T> capability) {
        Intrinsics.checkNotNullParameter(moduleInfo, "<this>");
        Intrinsics.checkNotNullParameter(capability, "capability");
        T t = (T) moduleInfo.getCapabilities().get(capability);
        if (t == null) {
            return null;
        }
        return t;
    }
}
