package com.android.tools.r8.utils.positions;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class NotSupportedMapVersionForMappingComposeDiagnostic implements Diagnostic {
    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        return "The input map file version is either unknown or less than version 2.2. For having line numbers optimized by D8 you would need to pass a mapping file of version 2.2. or later.";
    }

    @Override // com.android.tools.r8.Diagnostic
    public Origin getOrigin() {
        return Origin.unknown();
    }

    @Override // com.android.tools.r8.Diagnostic
    public Position getPosition() {
        return Position.UNKNOWN;
    }
}
