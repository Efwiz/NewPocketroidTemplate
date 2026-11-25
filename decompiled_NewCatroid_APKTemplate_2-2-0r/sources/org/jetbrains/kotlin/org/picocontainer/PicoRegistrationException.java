package org.jetbrains.kotlin.org.picocontainer;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;

/* JADX INFO: loaded from: classes4.dex */
public class PicoRegistrationException extends PicoException {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", CrashHianalyticsData.MESSAGE, "org/jetbrains/kotlin/org/picocontainer/PicoRegistrationException", "<init>"));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PicoRegistrationException(String str) {
        super(str);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }
}
