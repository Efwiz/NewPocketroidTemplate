package com.android.tools.r8.errors;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class ConstantDynamicDesugarDiagnostic implements DesugarDiagnostic {
    private final Origin a;
    private final Position b;
    private final String c;

    public ConstantDynamicDesugarDiagnostic(Origin origin, Position position, String str) {
        this.a = origin;
        this.b = position;
        this.c = str;
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        return this.c;
    }

    @Override // com.android.tools.r8.Diagnostic
    public Origin getOrigin() {
        return this.a;
    }

    @Override // com.android.tools.r8.Diagnostic
    public Position getPosition() {
        return this.b;
    }
}
