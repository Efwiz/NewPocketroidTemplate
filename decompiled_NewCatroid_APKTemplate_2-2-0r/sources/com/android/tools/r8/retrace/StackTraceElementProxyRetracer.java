package com.android.tools.r8.retrace;

import com.android.tools.r8.internal.TV;
import com.android.tools.r8.retrace.StackTraceElementProxy;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface StackTraceElementProxyRetracer<T, ST extends StackTraceElementProxy<T, ST>> {
    static <T, ST extends StackTraceElementProxy<T, ST>> StackTraceElementProxyRetracer<T, ST> createDefault(Retracer retracer) {
        return new TV(retracer);
    }

    RetraceStackTraceElementProxyResult<T, ST> retrace(ST st, RetraceStackTraceContext retraceStackTraceContext);
}
