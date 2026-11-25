package org.jetbrains.kotlin.build.report.statistics;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: formattingUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0002\u001a\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u0010\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u0007H\u0000\u001a\u0014\u0010\n\u001a\u00020\u0005*\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0001H\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"gbSize", "", "kbSize", "mbSize", "formatSize", "", "sizeInBytes", "", "formatTime", LanguageCodeUtil.MS, "asString", "", "decPoints", "kotlin-build-statistics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FormattingUtilsKt {
    public static final String formatTime(long j) {
        return asString(j / ((double) 1000), 2) + " s";
    }

    public static final String formatSize(long j) {
        if (j / ((long) 1073741824) >= 1) {
            return asString(j / ((double) 1073741824), 1) + " GB";
        }
        if (j / ((long) 1048576) >= 1) {
            return asString(j / ((double) 1048576), 1) + " MB";
        }
        return j / ((long) 1024) >= 1 ? asString(j / ((double) 1024), 1) + " KB" : j + " B";
    }

    public static final String asString(double d, int i) {
        String str = String.format("%,." + i + 'f', Arrays.copyOf(new Object[]{Double.valueOf(d)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }
}
