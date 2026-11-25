package com.android.tools.r8.profile.art.diagnostic;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class HumanReadableArtProfileParserErrorDiagnostic implements Diagnostic {
    private final String a;
    private final int b;
    private final Origin c;

    public HumanReadableArtProfileParserErrorDiagnostic(String str, int i, Origin origin) {
        this.a = str;
        this.b = i;
        this.c = origin;
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        return "Unable to parse rule at line " + this.b + " from ART profile: " + this.a;
    }

    @Override // com.android.tools.r8.Diagnostic
    public Origin getOrigin() {
        return this.c;
    }

    @Override // com.android.tools.r8.Diagnostic
    public Position getPosition() {
        return Position.UNKNOWN;
    }
}
