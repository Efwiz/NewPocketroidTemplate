package com.android.tools.r8.errors;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.shaking.S2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class UnusedProguardKeepRuleDiagnostic implements ProguardKeepRuleDiagnostic {
    private final S2 a;

    public UnusedProguardKeepRuleDiagnostic(S2 s2) {
        this.a = s2;
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        return "Proguard configuration rule does not match anything: `" + this.a + "`";
    }

    @Override // com.android.tools.r8.Diagnostic
    public Origin getOrigin() {
        return this.a.l();
    }

    @Override // com.android.tools.r8.Diagnostic
    public Position getPosition() {
        return this.a.m();
    }
}
