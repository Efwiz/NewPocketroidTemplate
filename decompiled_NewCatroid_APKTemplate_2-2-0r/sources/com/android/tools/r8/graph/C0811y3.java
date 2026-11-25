package com.android.tools.r8.graph;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import java.lang.reflect.GenericSignatureFormatError;

/* JADX INFO: renamed from: com.android.tools.r8.graph.y3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0811y3 implements Diagnostic {
    private final Origin a;
    private final Position b;
    private final String c;

    C0811y3(Origin origin, Position position, String str) {
        this.a = origin;
        this.b = position;
        this.c = str;
    }

    static C0811y3 a(String str, String str2, Origin origin, GenericSignatureFormatError genericSignatureFormatError) {
        return a(str, "class", str2, origin, genericSignatureFormatError);
    }

    static C0811y3 b(String str, String str2, Origin origin, GenericSignatureFormatError genericSignatureFormatError) {
        return a(str, "field", str2, origin, genericSignatureFormatError);
    }

    static C0811y3 c(String str, String str2, Origin origin, GenericSignatureFormatError genericSignatureFormatError) {
        return a(str, "method", str2, origin, genericSignatureFormatError);
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

    private static C0811y3 a(String str, String str2, String str3, Origin origin, GenericSignatureFormatError genericSignatureFormatError) {
        return new C0811y3(origin, Position.UNKNOWN, "Invalid signature '" + str + "' for " + str2 + " " + str3 + "." + System.lineSeparator() + "Signature is ignored and will not be present in the output." + System.lineSeparator() + "Parser error: " + genericSignatureFormatError.getMessage());
    }
}
