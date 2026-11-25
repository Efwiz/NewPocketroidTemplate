package com.android.tools.r8.internal;

import com.android.tools.r8.retrace.ResultWithContext;
import com.android.tools.r8.retrace.RetraceStackTraceContext;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oT, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2704oT implements ResultWithContext {
    private final List a;
    private final RetraceStackTraceContext b;

    private C2704oT(List list, RetraceStackTraceContext retraceStackTraceContext) {
        this.a = list;
        this.b = retraceStackTraceContext;
    }

    public static C2704oT a(List list, RetraceStackTraceContext retraceStackTraceContext) {
        return new C2704oT(list, retraceStackTraceContext);
    }

    @Override // com.android.tools.r8.retrace.ResultWithContext
    public final void forEach(Consumer consumer) {
        this.a.forEach(consumer);
    }

    @Override // com.android.tools.r8.retrace.ResultWithContext
    public final RetraceStackTraceContext getContext() {
        return this.b;
    }

    @Override // com.android.tools.r8.retrace.ResultWithContext
    public final List getLines() {
        return this.a;
    }

    @Override // com.android.tools.r8.retrace.ResultWithContext
    public final boolean isEmpty() {
        return this.a.isEmpty();
    }
}
