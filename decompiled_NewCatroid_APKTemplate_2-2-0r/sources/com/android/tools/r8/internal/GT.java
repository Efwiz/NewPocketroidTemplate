package com.android.tools.r8.internal;

import com.android.tools.r8.retrace.RetraceStackTraceContext;
import com.android.tools.r8.retrace.RetraceStackTraceElementProxyResult;
import java.util.function.Supplier;
import java.util.stream.Stream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class GT implements RetraceStackTraceElementProxyResult {
    private final Stream a;
    private final Supplier b;

    private GT(Stream stream, Supplier supplier) {
        this.a = stream;
        this.b = supplier;
    }

    final FT a() {
        FT ft = new FT();
        ft.a = this.a;
        ft.b = this.b;
        return ft;
    }

    @Override // com.android.tools.r8.retrace.RetraceStackTraceElementProxyResult
    public final RetraceStackTraceContext getResultContext() {
        return (RetraceStackTraceContext) this.b.get();
    }

    @Override // com.android.tools.r8.retrace.RetraceStackTraceElementProxyResult
    public final Stream stream() {
        return this.a;
    }
}
