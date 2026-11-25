package org.jetbrains.kotlin.fir.declarations;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactory;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo;

/* JADX INFO: compiled from: DeprecationsProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u0012\u001a\u0010\u0007\u001a\u0016\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0005\u0018\u00010\b¢\u0006\u0002\u0010\nJ\u0010\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\rH\u0016J\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013*\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0014\u001a\u00020\rH\u0002R\u0016\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\u0007\u001a\u0016\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0005\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\u000b\u001a\u0016\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProviderImpl;", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "firCachesFactory", "Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;", DevModeOverwritingStrategies.ALL, "", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationAnnotationInfo;", "bySpecificSite", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "(Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;Ljava/util/List;Ljava/util/Map;)V", ResponseCacheMiddleware.CACHE, "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsPerUseSite;", "", "getDeprecationsInfo", "languageVersionSettings", "computeDeprecationInfoOrNull", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationInfo;", "version", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeprecationsProviderImpl extends DeprecationsProvider {
    private final List<DeprecationAnnotationInfo> all;
    private final Map<AnnotationUseSiteTarget, List<DeprecationAnnotationInfo>> bySpecificSite;
    private final FirCache cache;

    /* JADX WARN: Multi-variable type inference failed */
    public DeprecationsProviderImpl(FirCachesFactory firCachesFactory, List<? extends DeprecationAnnotationInfo> list, Map<AnnotationUseSiteTarget, ? extends List<? extends DeprecationAnnotationInfo>> map) {
        Intrinsics.checkNotNullParameter(firCachesFactory, "firCachesFactory");
        this.all = list;
        this.bySpecificSite = map;
        this.cache = firCachesFactory.createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.declarations.DeprecationsProviderImpl$special$$inlined$createCache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((LanguageVersionSettings) obj, (Void) obj2);
            }

            public final DeprecationsPerUseSite invoke(LanguageVersionSettings key, Void r7) {
                Intrinsics.checkNotNullParameter(key, "key");
                LanguageVersionSettings languageVersionSettings = key;
                List list2 = this.this$0.all;
                LinkedHashMap linkedHashMap = null;
                DeprecationInfo deprecationInfoComputeDeprecationInfoOrNull = list2 != null ? this.this$0.computeDeprecationInfoOrNull(list2, languageVersionSettings) : null;
                Map map2 = this.this$0.bySpecificSite;
                if (map2 != null) {
                    LinkedHashMap linkedHashMap2 = new LinkedHashMap(MapsKt.mapCapacity(map2.size()));
                    for (Map.Entry entry : map2.entrySet()) {
                        linkedHashMap2.put(entry.getKey(), this.this$0.computeDeprecationInfoOrNull((List) entry.getValue(), languageVersionSettings));
                    }
                    LinkedHashMap linkedHashMap3 = new LinkedHashMap();
                    for (Map.Entry entry2 : linkedHashMap2.entrySet()) {
                        if (((DeprecationInfo) entry2.getValue()) != null) {
                            linkedHashMap3.put(entry2.getKey(), entry2.getValue());
                        }
                    }
                    linkedHashMap = linkedHashMap3;
                }
                return new DeprecationsPerUseSite(deprecationInfoComputeDeprecationInfoOrNull, linkedHashMap);
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.declarations.DeprecationsProvider
    public DeprecationsPerUseSite getDeprecationsInfo(LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        return (DeprecationsPerUseSite) this.cache.getValue(languageVersionSettings, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final DeprecationInfo computeDeprecationInfoOrNull(List<? extends DeprecationAnnotationInfo> list, LanguageVersionSettings languageVersionSettings) {
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            DeprecationInfo deprecationInfoComputeDeprecationInfo = ((DeprecationAnnotationInfo) it.next()).computeDeprecationInfo(languageVersionSettings);
            if (deprecationInfoComputeDeprecationInfo != null) {
                return deprecationInfoComputeDeprecationInfo;
            }
        }
        return null;
    }
}
