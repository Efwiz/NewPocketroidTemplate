package org.jetbrains.kotlin.konan.target;

import com.beust.jcommander.Parameters;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TargetTriple.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0012\u0010\u0004\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006\u001a\n\u0010\u0007\u001a\u00020\u0006*\u00020\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\b"}, d2 = {"isSimulator", "", "Lorg/jetbrains/kotlin/konan/target/TargetTriple;", "(Lorg/jetbrains/kotlin/konan/target/TargetTriple;)Z", "withOSVersion", "osVersion", "", "withoutVendor", "kotlin-native-utils"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class TargetTripleKt {
    public static final boolean isSimulator(TargetTriple targetTriple) {
        Intrinsics.checkNotNullParameter(targetTriple, "<this>");
        return Intrinsics.areEqual(targetTriple.getEnvironment(), "simulator");
    }

    public static final TargetTriple withOSVersion(TargetTriple targetTriple, String osVersion) {
        Intrinsics.checkNotNullParameter(targetTriple, "<this>");
        Intrinsics.checkNotNullParameter(osVersion, "osVersion");
        return TargetTriple.copy$default(targetTriple, null, null, targetTriple.getOs() + osVersion, null, 11, null);
    }

    public static final String withoutVendor(TargetTriple targetTriple) {
        String str;
        Intrinsics.checkNotNullParameter(targetTriple, "<this>");
        if (targetTriple.getEnvironment() == null || (str = Parameters.DEFAULT_OPTION_PREFIXES + targetTriple.getEnvironment()) == null) {
            str = "";
        }
        return targetTriple.getArchitecture() + '-' + targetTriple.getOs() + str;
    }
}
