package org.jetbrains.kotlin.konan.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.properties.KonanPropertiesLoader;
import org.jetbrains.kotlin.konan.properties.PropertiesKt;

/* JADX INFO: compiled from: DependencyProcessor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u001a0\u0010\u0017\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00190\u0018*\u00060\u0005j\u0002`\u00062\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\t0\u0019H\u0002\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u001c\u0010\u0000\u001a\u00020\u0001*\u00060\u0005j\u0002`\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0007\"\u0018\u0010\b\u001a\u00020\t*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b\"\u001c\u0010\b\u001a\u00020\t*\u00060\u0005j\u0002`\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\f\"\u0018\u0010\r\u001a\u00020\u000e*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010\"\u001c\u0010\r\u001a\u00020\u000e*\u00060\u0005j\u0002`\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0011\"\u0018\u0010\u0012\u001a\u00020\u0013*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015\"\u001c\u0010\u0012\u001a\u00020\u0013*\u00060\u0005j\u0002`\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0016¨\u0006\u001c"}, d2 = {"airplaneMode", "", "Lorg/jetbrains/kotlin/konan/properties/KonanPropertiesLoader;", "getAirplaneMode", "(Lorg/jetbrains/kotlin/konan/properties/KonanPropertiesLoader;)Z", "Ljava/util/Properties;", "Lorg/jetbrains/kotlin/konan/properties/Properties;", "(Ljava/util/Properties;)Z", "dependenciesUrl", "", "getDependenciesUrl", "(Lorg/jetbrains/kotlin/konan/properties/KonanPropertiesLoader;)Ljava/lang/String;", "(Ljava/util/Properties;)Ljava/lang/String;", "downloadingAttemptIntervalMs", "", "getDownloadingAttemptIntervalMs", "(Lorg/jetbrains/kotlin/konan/properties/KonanPropertiesLoader;)J", "(Ljava/util/Properties;)J", "downloadingAttempts", "", "getDownloadingAttempts", "(Lorg/jetbrains/kotlin/konan/properties/KonanPropertiesLoader;)I", "(Ljava/util/Properties;)I", "findCandidates", "", "", "Lorg/jetbrains/kotlin/konan/util/DependencySource;", "dependencies", "kotlin-native-utils"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class DependencyProcessorKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final String getDependenciesUrl(Properties properties) {
        String property = properties.getProperty("dependenciesUrl");
        if (property != null) {
            return property;
        }
        throw new IllegalStateException("No such property in konan.properties: dependenciesUrl");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean getAirplaneMode(Properties properties) {
        String property = properties.getProperty("airplaneMode");
        if (property != null) {
            return Boolean.parseBoolean(property);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int getDownloadingAttempts(Properties properties) {
        String property = properties.getProperty("downloadingAttempts");
        if (property != null) {
            return Integer.parseInt(property);
        }
        return 10;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final long getDownloadingAttemptIntervalMs(Properties properties) {
        String property = properties.getProperty("downloadingAttemptPauseMs");
        return property != null ? Long.parseLong(property) : DependencyDownloader.DEFAULT_ATTEMPT_INTERVAL_MS;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Map<String, List<DependencySource>> findCandidates(Properties properties, List<String> list) {
        ArrayList arrayListListOf;
        DependencySource local;
        List<String> listPropertyList$default = PropertiesKt.propertyList$default(properties, "dependencyProfiles", null, false, 6, null);
        List<String> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        for (String str : list2) {
            ArrayList arrayList2 = new ArrayList();
            for (String str2 : listPropertyList$default) {
                List listPropertyList$default2 = PropertiesKt.propertyList$default(properties, str + '.' + str2, null, false, 6, null);
                if (Intrinsics.areEqual(str2, "default") && listPropertyList$default2.isEmpty()) {
                    arrayListListOf = CollectionsKt.listOf(DependencySource.Remote.Public.INSTANCE);
                } else {
                    List<String> list3 = listPropertyList$default2;
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                    for (String str3 : list3) {
                        if (Intrinsics.areEqual(str3, "remote:public")) {
                            local = DependencySource.Remote.Public.INSTANCE;
                        } else {
                            local = Intrinsics.areEqual(str3, "remote:internal") ? DependencySource.Remote.Internal.INSTANCE : new DependencySource.Local(new File(str3));
                        }
                        arrayList3.add(local);
                    }
                    arrayListListOf = arrayList3;
                }
                CollectionsKt.addAll(arrayList2, arrayListListOf);
            }
            arrayList.add(TuplesKt.to(str, arrayList2));
        }
        return MapsKt.toMap(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String getDependenciesUrl(KonanPropertiesLoader konanPropertiesLoader) {
        return getDependenciesUrl(konanPropertiesLoader.getProperties());
    }
}
