package com.android.tools.r8.retrace;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.position.TextPosition;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class RetraceInvalidStackTraceLineDiagnostics implements Diagnostic {
    private final int a;
    private final String b = "The stack trace line is <null>";

    private RetraceInvalidStackTraceLineDiagnostics(int i) {
        this.a = i;
    }

    public static RetraceInvalidStackTraceLineDiagnostics createNull(int i) {
        return new RetraceInvalidStackTraceLineDiagnostics(i);
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        return this.b;
    }

    @Override // com.android.tools.r8.Diagnostic
    public Origin getOrigin() {
        return Origin.unknown();
    }

    @Override // com.android.tools.r8.Diagnostic
    public Position getPosition() {
        return new TextPosition(0L, this.a, -1);
    }
}
