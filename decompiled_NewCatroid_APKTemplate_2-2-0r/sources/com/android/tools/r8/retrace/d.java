package com.android.tools.r8.retrace;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.DiagnosticsHandler;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class d implements DiagnosticsHandler {
    private final DiagnosticsHandler a;
    private final boolean b;

    public d(boolean z, DiagnosticsHandler diagnosticsHandler) {
        this.a = diagnosticsHandler;
        this.b = z;
    }

    @Override // com.android.tools.r8.DiagnosticsHandler
    public final void error(Diagnostic diagnostic) {
        this.a.error(diagnostic);
    }

    @Override // com.android.tools.r8.DiagnosticsHandler
    public final void info(Diagnostic diagnostic) {
        if (this.b) {
            this.a.info(diagnostic);
        }
    }

    @Override // com.android.tools.r8.DiagnosticsHandler
    public final void warning(Diagnostic diagnostic) {
        this.a.warning(diagnostic);
    }
}
