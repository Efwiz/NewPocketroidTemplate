package com.android.tools.r8.utils;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.StringConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.utils.q, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3950q implements StringConsumer {
    StringBuilder a = null;
    final /* synthetic */ C3989y b;

    C3950q(C3989y c3989y) {
        this.b = c3989y;
    }

    @Override // com.android.tools.r8.StringConsumer
    public final void accept(String str, DiagnosticsHandler diagnosticsHandler) {
        if (this.a == null) {
            this.a = new StringBuilder();
        }
        this.a.append(str);
    }

    @Override // com.android.tools.r8.J
    public final void finished(DiagnosticsHandler diagnosticsHandler) {
        StringBuilder sb = this.a;
        if (sb != null) {
            this.b.a.a(sb.toString());
        }
    }
}
