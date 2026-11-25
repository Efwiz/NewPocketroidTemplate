package org.jetbrains.kotlin.cli.common;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: performanceMeasurements.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0002¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"formatMeasurement", "", "name", "time", "", "lines", "", "(Ljava/lang/String;JLjava/lang/Integer;)Ljava/lang/String;", "cli-base"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PerformanceMeasurementsKt {
    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:6:0x0048  */
    public static final String formatMeasurement(String str, long j, Integer num) {
        String str2;
        StringBuilder sb = new StringBuilder();
        String str3 = String.format("%15s%8s ms", Arrays.copyOf(new Object[]{str, Long.valueOf(j)}, 2));
        Intrinsics.checkNotNullExpressionValue(str3, "format(...)");
        StringBuilder sbAppend = sb.append(str3);
        if (num != null) {
            str2 = String.format("%12.3f loc/s", Arrays.copyOf(new Object[]{Double.valueOf((((double) num.intValue()) * ((double) 1000)) / j)}, 1));
            Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
            if (str2 == null) {
                str2 = "";
            }
        } else {
            str2 = "";
        }
        return sbAppend.append(str2).toString();
    }
}
