package org.jetbrains.kotlin.js.facade.exceptions;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;

/* JADX INFO: loaded from: classes5.dex */
public class TranslationException extends Exception {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = CrashHianalyticsData.MESSAGE;
        } else {
            objArr[0] = "cause";
        }
        objArr[1] = "org/jetbrains/kotlin/js/facade/exceptions/TranslationException";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TranslationException(String str, Exception exc) {
        super(str, exc);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (exc == null) {
            $$$reportNull$$$0(1);
        }
    }
}
