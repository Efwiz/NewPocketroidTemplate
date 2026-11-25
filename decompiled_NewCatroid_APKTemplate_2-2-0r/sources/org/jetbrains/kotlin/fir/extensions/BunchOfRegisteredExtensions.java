package org.jetbrains.kotlin.fir.extensions;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KClass;

/* JADX INFO: compiled from: FirExtensionRegistrar.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \r2\u00020\u0001:\u0001\rB/\b\u0007\u0012&\u0010\u0002\u001a\"\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00070\u00060\u0003¢\u0006\u0002\u0010\bJ\u0011\u0010\u000b\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u0000H\u0086\u0002R1\u0010\u0002\u001a\"\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00070\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/BunchOfRegisteredExtensions;", "", "extensions", "", "Lkotlin/reflect/KClass;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension;", "", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension$Factory;", "(Ljava/util/Map;)V", "getExtensions", "()Ljava/util/Map;", "plus", "other", "Companion", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BunchOfRegisteredExtensions {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Map<KClass<? extends FirExtension>, List<FirExtension.Factory<FirExtension>>> extensions;

    /* JADX WARN: Multi-variable type inference failed */
    @PluginServicesInitialization
    public BunchOfRegisteredExtensions(Map<KClass<? extends FirExtension>, ? extends List<? extends FirExtension.Factory<? extends FirExtension>>> extensions) {
        Intrinsics.checkNotNullParameter(extensions, "extensions");
        this.extensions = extensions;
    }

    public final Map<KClass<? extends FirExtension>, List<FirExtension.Factory<FirExtension>>> getExtensions() {
        return this.extensions;
    }

    /* JADX INFO: compiled from: FirExtensionRegistrar.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/BunchOfRegisteredExtensions$Companion;", "", "()V", "empty", "Lorg/jetbrains/kotlin/fir/extensions/BunchOfRegisteredExtensions;", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final BunchOfRegisteredExtensions empty() {
            List<KClass<? extends FirExtension>> aVAILABLE_EXTENSIONS$entrypoint = FirExtensionRegistrar.INSTANCE.getAVAILABLE_EXTENSIONS$entrypoint();
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(aVAILABLE_EXTENSIONS$entrypoint, 10)), 16));
            for (Object obj : aVAILABLE_EXTENSIONS$entrypoint) {
                linkedHashMap.put(obj, CollectionsKt.emptyList());
            }
            return new BunchOfRegisteredExtensions(linkedHashMap);
        }
    }

    public final BunchOfRegisteredExtensions plus(BunchOfRegisteredExtensions other) {
        Intrinsics.checkNotNullParameter(other, "other");
        Map mapCreateMapBuilder = MapsKt.createMapBuilder();
        for (KClass<? extends FirExtension> kClass : FirExtensionRegistrar.INSTANCE.getAVAILABLE_EXTENSIONS$entrypoint()) {
            mapCreateMapBuilder.put(kClass, CollectionsKt.plus((Collection) MapsKt.getValue(this.extensions, kClass), (Iterable) MapsKt.getValue(other.extensions, kClass)));
        }
        return new BunchOfRegisteredExtensions(MapsKt.build(mapCreateMapBuilder));
    }
}
