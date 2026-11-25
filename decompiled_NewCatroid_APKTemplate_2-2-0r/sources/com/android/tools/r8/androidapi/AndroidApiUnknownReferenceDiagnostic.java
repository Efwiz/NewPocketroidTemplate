package com.android.tools.r8.androidapi;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.internal.AbstractC1734c2;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class AndroidApiUnknownReferenceDiagnostic extends AbstractC1734c2 {
    private final AbstractC0744s2 a;

    AndroidApiUnknownReferenceDiagnostic(AbstractC0744s2 abstractC0744s2) {
        this.a = abstractC0744s2;
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        return this.a.r0() + " cannot be found in the api database.";
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
