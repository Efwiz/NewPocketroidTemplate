package org.jetbrains.kotlin.konan.target;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TargetManager.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003¨\u0006\u0005"}, d2 = {"hostTargetSuffix", "", "host", "Lorg/jetbrains/kotlin/konan/target/KonanTarget;", "target", "kotlin-native-utils"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class TargetManagerKt {
    public static final String hostTargetSuffix(KonanTarget host, KonanTarget target) {
        Intrinsics.checkNotNullParameter(host, "host");
        Intrinsics.checkNotNullParameter(target, "target");
        return Intrinsics.areEqual(target, host) ? host.getName() : host.getName() + '-' + target.getName();
    }
}
