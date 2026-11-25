package com.android.tools.r8.utils;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;

/* JADX INFO: renamed from: com.android.tools.r8.utils.b, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class C3870b extends RuntimeException {
    static final /* synthetic */ boolean b = true;
    private final Diagnostic a;

    public C3870b(Diagnostic diagnostic) {
        if (!b && diagnostic == null) {
            throw new AssertionError();
        }
        this.a = diagnostic;
    }

    public final Origin a() {
        Diagnostic diagnostic = this.a;
        return diagnostic != null ? diagnostic.getOrigin() : Origin.unknown();
    }

    public final Position b() {
        Diagnostic diagnostic = this.a;
        return diagnostic != null ? diagnostic.getPosition() : Position.UNKNOWN;
    }

    @Override // java.lang.Throwable
    public synchronized Throwable getCause() {
        Diagnostic diagnostic;
        diagnostic = this.a;
        return diagnostic instanceof ExceptionDiagnostic ? ((ExceptionDiagnostic) diagnostic).getCause() : null;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.a.getDiagnosticMessage();
    }
}
