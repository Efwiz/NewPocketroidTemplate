package com.android.tools.r8.errors;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class AssumeValuesMissingStaticFieldDiagnostic implements Diagnostic {
    private final C0767v2 a;
    private final C0759u2 b;
    private final Origin c;
    private final Position d;

    private AssumeValuesMissingStaticFieldDiagnostic(C0767v2 c0767v2, C0759u2 c0759u2, Origin origin, Position position) {
        this.a = c0767v2;
        this.b = c0759u2;
        this.c = origin;
        this.d = position;
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        return "The field " + this.a.L0() + "." + this.b + " is used as the return value in an -assumenosideeffects or -assumevalues rule, but no such static field exists.";
    }

    @Override // com.android.tools.r8.Diagnostic
    public Origin getOrigin() {
        return this.c;
    }

    @Override // com.android.tools.r8.Diagnostic
    public Position getPosition() {
        return this.d;
    }
}
