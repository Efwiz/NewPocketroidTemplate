package com.android.tools.r8.utils;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.references.MethodReference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class UnverifiableCfCodeDiagnostic implements Diagnostic {
    private final MethodReference a;
    private final int b;
    private final String c;
    private final Origin d;

    public UnverifiableCfCodeDiagnostic(MethodReference methodReference, int i, String str, Origin origin) {
        this.a = methodReference;
        this.b = i;
        this.c = str;
        this.d = origin;
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        StringBuilder sbAppend = new StringBuilder("Unverifiable code in `").append(C3889e2.b(this.a)).append("`");
        if (this.b >= 0) {
            sbAppend.append(" at instruction ").append(this.b);
        }
        return sbAppend.append(": ").append(this.c).append(".").toString();
    }

    @Override // com.android.tools.r8.Diagnostic
    public Origin getOrigin() {
        return this.d;
    }

    @Override // com.android.tools.r8.Diagnostic
    public Position getPosition() {
        return Position.UNKNOWN;
    }
}
