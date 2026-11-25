package com.android.tools.r8.shaking;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class F3 extends Exception implements Diagnostic {
    private final String a;
    private final String b;
    private final Origin c;
    private final Position d;

    public F3(String str, String str2, Origin origin, Position position) {
        this.a = str;
        this.b = str2;
        this.c = origin;
        this.d = position;
    }

    @Override // com.android.tools.r8.Diagnostic
    public final String getDiagnosticMessage() {
        return this.a + " at " + this.b;
    }

    @Override // java.lang.Throwable
    public final String getMessage() {
        return this.a + " at " + this.b;
    }

    @Override // com.android.tools.r8.Diagnostic
    public final Origin getOrigin() {
        return this.c;
    }

    @Override // com.android.tools.r8.Diagnostic
    public final Position getPosition() {
        return this.d;
    }
}
