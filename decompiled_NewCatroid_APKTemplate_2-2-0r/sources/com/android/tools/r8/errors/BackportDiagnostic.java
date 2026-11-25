package com.android.tools.r8.errors;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class BackportDiagnostic implements DesugarDiagnostic {
    private final C0684j2 a;
    private final Origin b;
    private final Position c;

    public BackportDiagnostic(C0684j2 c0684j2, Origin origin, Position position) {
        this.a = c0684j2;
        this.b = origin;
        this.c = position;
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        return "Attempt to backport " + this.a.r0();
    }

    @Override // com.android.tools.r8.Diagnostic
    public Origin getOrigin() {
        return this.b;
    }

    @Override // com.android.tools.r8.Diagnostic
    public Position getPosition() {
        return this.c;
    }
}
