package org.jetbrains.kotlin.util;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: exceptionUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\b\u0002\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0001¨\u0006\u0007"}, d2 = {"getExceptionMessage", "", "subsystemName", CrashHianalyticsData.MESSAGE, "cause", "", FirebaseAnalytics.Param.LOCATION, "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ExceptionUtilKt {
    /* JADX WARN: Code duplicated, block: B:13:0x0091  */
    public static final String getExceptionMessage(String subsystemName, String message, Throwable th, String str) {
        String string;
        Intrinsics.checkNotNullParameter(subsystemName, "subsystemName");
        Intrinsics.checkNotNullParameter(message, "message");
        StringBuilder sb = new StringBuilder();
        StringBuilder sbAppend = sb.append(subsystemName).append(" Internal error: ");
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        StringBuilder sbAppend2 = sbAppend.append(message);
        Intrinsics.checkNotNullExpressionValue(sbAppend2, "append(...)");
        Intrinsics.checkNotNullExpressionValue(sbAppend2.append('\n'), "append(...)");
        if (str != null) {
            StringBuilder sbAppend3 = sb.append("File being compiled: ");
            Intrinsics.checkNotNullExpressionValue(sbAppend3, "append(...)");
            StringBuilder sbAppend4 = sbAppend3.append(str);
            Intrinsics.checkNotNullExpressionValue(sbAppend4, "append(...)");
            Intrinsics.checkNotNullExpressionValue(sbAppend4.append('\n'), "append(...)");
        } else {
            StringBuilder sbAppend5 = sb.append("File is unknown");
            Intrinsics.checkNotNullExpressionValue(sbAppend5, "append(...)");
            Intrinsics.checkNotNullExpressionValue(sbAppend5.append('\n'), "append(...)");
        }
        if (th != null) {
            sb.append("The root cause " + th.getClass().getName() + " was thrown at: ");
            StackTraceElement[] stackTrace = th.getStackTrace();
            if (stackTrace != null) {
                Intrinsics.checkNotNull(stackTrace);
                StackTraceElement stackTraceElement = (StackTraceElement) ArraysKt.firstOrNull(stackTrace);
                if (stackTraceElement == null || (string = stackTraceElement.toString()) == null) {
                    string = "unknown";
                }
            } else {
                string = "unknown";
            }
            sb.append(string);
        }
        String string2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
        return string2;
    }
}
