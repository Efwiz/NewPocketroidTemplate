package org.jetbrains.kotlin.com.intellij.util.io.keyStorage;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.IOException;

/* JADX INFO: loaded from: classes3.dex */
public class NoDataException extends IOException {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", CrashHianalyticsData.MESSAGE, "org/jetbrains/kotlin/com/intellij/util/io/keyStorage/NoDataException", "<init>"));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    NoDataException(String str) {
        super(str);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }
}
