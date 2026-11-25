package com.android.tools.r8.relocator;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class b implements Diagnostic {
    private final String a;

    public b(String str) {
        this.a = str;
    }

    static b a(C0767v2 c0767v2) {
        return new b("Type '" + c0767v2.r0() + "' can be relocated by multiple mappings.");
    }

    @Override // com.android.tools.r8.Diagnostic
    public final String getDiagnosticMessage() {
        return this.a;
    }

    @Override // com.android.tools.r8.Diagnostic
    public final Origin getOrigin() {
        return Origin.unknown();
    }

    @Override // com.android.tools.r8.Diagnostic
    public final Position getPosition() {
        return Position.UNKNOWN;
    }
}
