package com.android.tools.r8.errors;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.EnumC3891f;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class UnsupportedInvokePolymorphicMethodHandleDiagnostic extends UnsupportedFeatureDiagnostic {
    public UnsupportedInvokePolymorphicMethodHandleDiagnostic(Origin origin, Position position) {
        super("invoke-polymorphic-method-handle", A1.R0(), origin, position);
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        boolean z = A1.O1;
        return UnsupportedFeatureDiagnostic.makeMessage(EnumC3891f.O, "MethodHandle.invoke and MethodHandle.invokeExact", getPosition().toString());
    }
}
