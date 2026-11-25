package com.android.tools.r8;

import com.android.tools.r8.errors.DexFileOverflowDiagnostic;
import com.android.tools.r8.utils.StringDiagnostic;

/* JADX INFO: renamed from: com.android.tools.r8.n, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3602n implements DiagnosticsHandler {
    private C3602n() {
    }

    @Override // com.android.tools.r8.DiagnosticsHandler
    public final void error(Diagnostic diagnostic) {
        if (diagnostic instanceof DexFileOverflowDiagnostic) {
            DexFileOverflowDiagnostic dexFileOverflowDiagnostic = (DexFileOverflowDiagnostic) diagnostic;
            if (!dexFileOverflowDiagnostic.hasMainDexSpecification()) {
                super.error(new StringDiagnostic(dexFileOverflowDiagnostic.getDiagnosticMessage() + ". Try supplying a main-dex list"));
                return;
            }
        }
        super.error(diagnostic);
    }
}
