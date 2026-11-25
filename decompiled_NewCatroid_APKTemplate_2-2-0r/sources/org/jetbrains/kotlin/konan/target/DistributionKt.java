package org.jetbrains.kotlin.konan.target;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Distribution.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u001a\u001a\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u001a\u001a\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003¨\u0006\u0006"}, d2 = {"buildDistribution", "Lorg/jetbrains/kotlin/konan/target/Distribution;", "konanHome", "", "konanDataDir", "customerDistribution", "kotlin-native-utils"}, k = 2, mv = {1, 5, 1}, xi = 48)
public final class DistributionKt {
    public static final Distribution buildDistribution(String konanHome, String str) {
        Intrinsics.checkNotNullParameter(konanHome, "konanHome");
        return new Distribution(konanHome, true, null, null, str, 8, null);
    }

    public static /* synthetic */ Distribution buildDistribution$default(String str, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = null;
        }
        return buildDistribution(str, str2);
    }

    public static final Distribution customerDistribution(String konanHome, String str) {
        Intrinsics.checkNotNullParameter(konanHome, "konanHome");
        return new Distribution(konanHome, false, null, null, str, 8, null);
    }

    public static /* synthetic */ Distribution customerDistribution$default(String str, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = null;
        }
        return customerDistribution(str, str2);
    }
}
