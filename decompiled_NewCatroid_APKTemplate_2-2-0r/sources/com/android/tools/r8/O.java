package com.android.tools.r8;

import com.android.tools.r8.errors.DexFileOverflowDiagnostic;
import com.android.tools.r8.utils.StringDiagnostic;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class O implements DiagnosticsHandler {
    protected O() {
    }

    @Override // com.android.tools.r8.DiagnosticsHandler
    public final void error(Diagnostic diagnostic) {
        if (!(diagnostic instanceof DexFileOverflowDiagnostic)) {
            super.error(diagnostic);
        } else {
            super.error(new StringDiagnostic(((DexFileOverflowDiagnostic) diagnostic).getDiagnosticMessage() + ". Library too large. L8 can only produce a single .dex file"));
        }
    }
}
