package org.jetbrains.kotlin.fir.declarations;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.fir.caches.FirCachesFactory;

/* JADX INFO: compiled from: deprecationUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B!\u0012\u001a\u0010\u0002\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0003¢\u0006\u0002\u0010\u0007J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rR%\u0010\u0002\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/DeprecationAnnotationInfoPerUseSiteStorage;", "", "storage", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationAnnotationInfo;", "(Ljava/util/Map;)V", "getStorage", "()Ljava/util/Map;", "toDeprecationsProvider", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "firCachesFactory", "Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeprecationAnnotationInfoPerUseSiteStorage {
    private final Map<AnnotationUseSiteTarget, List<DeprecationAnnotationInfo>> storage;

    /* JADX WARN: Multi-variable type inference failed */
    public DeprecationAnnotationInfoPerUseSiteStorage(Map<AnnotationUseSiteTarget, ? extends List<? extends DeprecationAnnotationInfo>> storage) {
        Intrinsics.checkNotNullParameter(storage, "storage");
        this.storage = storage;
    }

    public final Map<AnnotationUseSiteTarget, List<DeprecationAnnotationInfo>> getStorage() {
        return this.storage;
    }

    public final DeprecationsProvider toDeprecationsProvider(FirCachesFactory firCachesFactory) {
        Intrinsics.checkNotNullParameter(firCachesFactory, "firCachesFactory");
        if (this.storage.isEmpty()) {
            return EmptyDeprecationsProvider.INSTANCE;
        }
        Map<AnnotationUseSiteTarget, List<DeprecationAnnotationInfo>> map = this.storage;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<AnnotationUseSiteTarget, List<DeprecationAnnotationInfo>> entry : map.entrySet()) {
            if (entry.getKey() != null) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        LinkedHashMap linkedHashMap2 = linkedHashMap;
        List<DeprecationAnnotationInfo> list = this.storage.get(null);
        if (linkedHashMap2.isEmpty()) {
            linkedHashMap2 = null;
        }
        return new DeprecationsProviderImpl(firCachesFactory, list, linkedHashMap2);
    }
}
