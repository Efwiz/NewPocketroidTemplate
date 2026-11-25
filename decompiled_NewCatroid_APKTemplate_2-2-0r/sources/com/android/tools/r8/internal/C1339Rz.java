package com.android.tools.r8.internal;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Rz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1339Rz implements Diagnostic {
    private final Origin a;
    private final Position b;
    private final String c;

    public C1339Rz(Origin origin, Position position, String str) {
        this.a = origin;
        this.b = position;
        this.c = str;
    }

    static C1339Rz a(AbstractC0727q0 abstractC0727q0, String str) {
        return new C1339Rz(abstractC0727q0.getOrigin(), Position.UNKNOWN, "The companion object " + str + " could not be found in class " + abstractC0727q0.d.F0());
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

    static C1339Rz a(String str) {
        return new C1339Rz(Origin.unknown(), Position.UNKNOWN, "The classifier " + str + " is unknown and cannot be parsed");
    }

    static C1339Rz a(C0767v2 c0767v2, Throwable th) {
        return new C1339Rz(Origin.unknown(), Position.UNKNOWN, "Unexpected error during rewriting of Kotlin metadata for class '" + c0767v2.r0() + "':" + com.android.tools.r8.utils.Y2.c + com.android.tools.r8.utils.Y2.a(th));
    }

    static C1339Rz a() {
        return new C1339Rz(Origin.unknown(), Position.UNKNOWN, "An error occurred when parsing kotlin metadata. This normally happens when using a newer version of kotlin than the kotlin version released when this version of R8 was created. To find compatible kotlin versions, please see: https://developer.android.com/studio/build/kotlin-d8-r8-versions");
    }
}
