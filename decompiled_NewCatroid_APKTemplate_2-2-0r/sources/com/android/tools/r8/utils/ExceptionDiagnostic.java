package com.android.tools.r8.utils;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.ResourceException;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class ExceptionDiagnostic implements Diagnostic {
    static final /* synthetic */ boolean d = true;
    private final Throwable a;
    private final Origin b;
    private final Position c;

    public ExceptionDiagnostic(Throwable th, Origin origin, Position position) {
        boolean z = d;
        if (!z && th == null) {
            throw new AssertionError();
        }
        if (!z && origin == null) {
            throw new AssertionError();
        }
        if (!z && position == null) {
            throw new AssertionError();
        }
        this.a = th;
        this.b = origin;
        this.c = position;
    }

    public Throwable getCause() {
        return this.a;
    }

    @Override // com.android.tools.r8.Diagnostic
    public String getDiagnosticMessage() {
        return this.a.toString();
    }

    @Override // com.android.tools.r8.Diagnostic
    public Origin getOrigin() {
        return this.b;
    }

    @Override // com.android.tools.r8.Diagnostic
    public Position getPosition() {
        return this.c;
    }

    public ExceptionDiagnostic(Throwable th) {
        this(th, Origin.unknown(), Position.UNKNOWN);
    }

    public ExceptionDiagnostic(Throwable th, Origin origin) {
        this(th, origin, Position.UNKNOWN);
    }

    public ExceptionDiagnostic(ResourceException resourceException) {
        this(resourceException, resourceException.getOrigin());
    }
}
