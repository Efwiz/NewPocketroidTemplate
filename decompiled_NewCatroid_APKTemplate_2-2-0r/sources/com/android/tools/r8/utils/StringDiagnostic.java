package com.android.tools.r8.utils;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class StringDiagnostic implements Diagnostic {
    static final /* synthetic */ boolean d = true;
    private final Origin a;
    private final Position b;
    private final String c;

    public StringDiagnostic(String str) {
        this(str, Origin.unknown());
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        return this.c;
    }

    @Override // com.android.tools.r8.Diagnostic
    public Origin getOrigin() {
        return this.a;
    }

    @Override // com.android.tools.r8.Diagnostic
    public Position getPosition() {
        return this.b;
    }

    public StringDiagnostic(String str, Origin origin) {
        this(str, origin, Position.UNKNOWN);
    }

    public StringDiagnostic(String str, Origin origin, Position position) {
        boolean z = d;
        if (!z && str == null) {
            throw new AssertionError();
        }
        if (!z && origin == null) {
            throw new AssertionError();
        }
        if (!z && position == null) {
            throw new AssertionError();
        }
        this.a = origin;
        this.b = position;
        this.c = str;
    }
}
