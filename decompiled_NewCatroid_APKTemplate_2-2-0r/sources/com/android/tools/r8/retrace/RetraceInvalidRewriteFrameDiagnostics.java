package com.android.tools.r8.retrace;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class RetraceInvalidRewriteFrameDiagnostics implements Diagnostic {
    private final int a;
    private final String b;

    private RetraceInvalidRewriteFrameDiagnostics(int i, String str) {
        this.a = i;
        this.b = str;
    }

    public static RetraceInvalidRewriteFrameDiagnostics create(int i, String str) {
        return new RetraceInvalidRewriteFrameDiagnostics(i, str);
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        return "Cannot remove " + this.a + " frames from the retraced output of " + this.b + " because it exceeds the number of retraced frames";
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
