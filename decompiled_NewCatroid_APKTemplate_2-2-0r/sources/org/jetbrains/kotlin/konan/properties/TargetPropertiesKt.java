package org.jetbrains.kotlin.konan.properties;

import java.util.List;
import java.util.Properties;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.target.KonanTarget;
import org.jetbrains.kotlin.konan.target.TargetManagerKt;

/* JADX INFO: compiled from: TargetProperties.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u001a$\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00060\u0003j\u0002`\u00042\u0006\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007\u001a \u0010\b\u001a\u0004\u0018\u00010\u0002*\u00060\u0003j\u0002`\u00042\u0006\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007\u001a,\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00060\u0003j\u0002`\u00042\u0006\u0010\u0005\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0007\u001a(\u0010\u000b\u001a\u0004\u0018\u00010\u0002*\u00060\u0003j\u0002`\u00042\u0006\u0010\u0005\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0007\u001a$\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00060\u0003j\u0002`\u00042\u0006\u0010\u0005\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u0007\u001a \u0010\r\u001a\u0004\u0018\u00010\u0002*\u00060\u0003j\u0002`\u00042\u0006\u0010\u0005\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u0007¨\u0006\u000e"}, d2 = {"hostList", "", "", "Ljava/util/Properties;", "Lorg/jetbrains/kotlin/konan/properties/Properties;", "name", "host", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "hostString", "hostTargetList", "target", "hostTargetString", "targetList", "targetString", "kotlin-native-utils"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class TargetPropertiesKt {
    public static final String hostString(Properties properties, String name2, KonanTarget host) {
        Intrinsics.checkNotNullParameter(properties, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(host, "host");
        return PropertiesKt.resolvablePropertyString$default(properties, name2, host.getName(), null, 4, null);
    }

    public static final List<String> hostList(Properties properties, String name2, KonanTarget host) {
        Intrinsics.checkNotNullParameter(properties, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(host, "host");
        return PropertiesKt.resolvablePropertyList$default(properties, name2, host.getName(), false, null, 12, null);
    }

    public static final String targetString(Properties properties, String name2, KonanTarget target) {
        Intrinsics.checkNotNullParameter(properties, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(target, "target");
        return PropertiesKt.resolvablePropertyString$default(properties, name2, target.getName(), null, 4, null);
    }

    public static final List<String> targetList(Properties properties, String name2, KonanTarget target) {
        Intrinsics.checkNotNullParameter(properties, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(target, "target");
        return PropertiesKt.resolvablePropertyList$default(properties, name2, target.getName(), false, null, 12, null);
    }

    public static final String hostTargetString(Properties properties, String name2, KonanTarget target, KonanTarget host) {
        Intrinsics.checkNotNullParameter(properties, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(host, "host");
        return PropertiesKt.resolvablePropertyString$default(properties, name2, TargetManagerKt.hostTargetSuffix(host, target), null, 4, null);
    }

    public static final List<String> hostTargetList(Properties properties, String name2, KonanTarget target, KonanTarget host) {
        Intrinsics.checkNotNullParameter(properties, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(host, "host");
        return PropertiesKt.resolvablePropertyList$default(properties, name2, TargetManagerKt.hostTargetSuffix(host, target), false, null, 12, null);
    }
}
