package com.android.tools.r8.graph;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class F3 implements Diagnostic {
    private final Origin a;
    private final Position b;
    private final String c;

    F3(Origin origin, Position position, String str) {
        this.a = origin;
        this.b = position;
        this.c = str;
    }

    static F3 a(String str, String str2, Origin origin, C0791w3.a aVar) {
        return a(str, "class", str2, origin, aVar);
    }

    static F3 b(String str, String str2, Origin origin, C0791w3.a aVar) {
        return a(str, "field", str2, origin, aVar);
    }

    static F3 c(String str, String str2, Origin origin, C0791w3.a aVar) {
        return a(str, "method", str2, origin, aVar);
    }

    @Override // com.android.tools.r8.Diagnostic
    public final String getDiagnosticMessage() {
        return this.c;
    }

    @Override // com.android.tools.r8.Diagnostic
    public final Origin getOrigin() {
        return this.a;
    }

    @Override // com.android.tools.r8.Diagnostic
    public final Position getPosition() {
        return this.b;
    }

    private static F3 a(String str, String str2, String str3, Origin origin, C0791w3.a aVar) {
        return new F3(origin, Position.UNKNOWN, "Invalid signature '" + str + "' for " + str2 + " " + str3 + "." + System.lineSeparator() + "Validation error: " + aVar.a() + "." + System.lineSeparator() + "Signature is ignored and will not be present in the output.");
    }
}
