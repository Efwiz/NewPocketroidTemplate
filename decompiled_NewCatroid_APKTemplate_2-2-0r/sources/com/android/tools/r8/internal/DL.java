package com.android.tools.r8.internal;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.StringConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class DL implements com.android.tools.r8.utils.V {
    private final StringConsumer a;
    private final DiagnosticsHandler b;

    private DL(StringConsumer stringConsumer, DiagnosticsHandler diagnosticsHandler) {
        this.a = stringConsumer;
        this.b = diagnosticsHandler;
    }

    @Override // com.android.tools.r8.utils.V
    public final com.android.tools.r8.utils.V a(String str) {
        this.a.accept(str, this.b);
        return this;
    }
}
