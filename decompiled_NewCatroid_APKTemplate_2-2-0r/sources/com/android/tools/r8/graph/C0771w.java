package com.android.tools.r8.graph;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.MethodPosition;
import com.android.tools.r8.position.Position;

/* JADX INFO: renamed from: com.android.tools.r8.graph.w, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0771w implements Diagnostic {
    private final Origin a;
    private final MethodPosition b;
    private final String c;

    public C0771w(Origin origin, C0684j2 c0684j2, String str) {
        this.a = origin;
        this.b = new MethodPosition(c0684j2.E0());
        this.c = str;
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
}
