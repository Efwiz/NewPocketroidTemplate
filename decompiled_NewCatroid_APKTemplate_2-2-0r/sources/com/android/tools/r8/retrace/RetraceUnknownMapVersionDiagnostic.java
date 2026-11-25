package com.android.tools.r8.retrace;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.Version;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class RetraceUnknownMapVersionDiagnostic implements Diagnostic {
    private final String a;

    private RetraceUnknownMapVersionDiagnostic(String str) {
        this.a = str;
    }

    public static RetraceUnknownMapVersionDiagnostic create(String str) {
        return new RetraceUnknownMapVersionDiagnostic(str);
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        return String.format("Map version '%s' is unknown or introduced later than retrace version '%s'.", this.a, Version.getVersionString());
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
