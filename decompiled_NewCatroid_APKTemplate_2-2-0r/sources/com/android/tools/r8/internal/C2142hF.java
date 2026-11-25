package com.android.tools.r8.internal;

import com.android.tools.r8.errors.DesugarDiagnostic;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hF, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2142hF implements DesugarDiagnostic {
    private final String a;

    public C2142hF(String str) {
        this.a = str;
    }

    @Override // com.android.tools.r8.Diagnostic
    public final String getDiagnosticMessage() {
        return "Invalid build configuration. Attempt to create a global synthetic for '" + this.a + "' without a global-synthetics consumer.";
    }

    @Override // com.android.tools.r8.Diagnostic
    public final Origin getOrigin() {
        return Origin.unknown();
    }

    @Override // com.android.tools.r8.Diagnostic
    public final Position getPosition() {
        return Position.UNKNOWN;
    }
}
