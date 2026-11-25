package com.android.tools.r8.errors;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.utils.A1;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class UnsupportedConstDynamicDiagnostic extends UnsupportedFeatureDiagnostic {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UnsupportedConstDynamicDiagnostic(Origin origin, Position position) {
        super("const-dynamic", null, origin, position);
        boolean z = A1.O1;
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        boolean z = A1.O1;
        return UnsupportedFeatureDiagnostic.makeMessage(null, "const-dynamic", getPosition().toString());
    }
}
