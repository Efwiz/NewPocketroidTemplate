package com.android.tools.r8.errors;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.EnumC3891f;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class UnsupportedInvokePolymorphicVarHandleDiagnostic extends UnsupportedFeatureDiagnostic {
    public UnsupportedInvokePolymorphicVarHandleDiagnostic(Origin origin, Position position) {
        super("invoke-polymorphic-var-handle", A1.S0(), origin, position);
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        boolean z = A1.O1;
        return UnsupportedFeatureDiagnostic.makeMessage(EnumC3891f.P, "Call to polymorphic signature of VarHandle", getPosition().toString());
    }
}
