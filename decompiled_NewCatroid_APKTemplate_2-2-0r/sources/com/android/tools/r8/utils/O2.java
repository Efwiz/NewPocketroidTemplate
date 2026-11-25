package com.android.tools.r8.utils;

import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.DiagnosticsLevel;
import com.android.tools.r8.internal.C3405y10;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class O2 implements DiagnosticsHandler {
    private final DiagnosticsHandler a;
    private final ArrayList b;
    private C3870b c;

    public O2() {
        this(new L2());
    }

    private void a(DiagnosticsLevel diagnosticsLevel, Diagnostic diagnostic) {
        if (diagnosticsLevel != null) {
            DiagnosticsLevel diagnosticsLevelModifyDiagnosticsLevel = this.a.modifyDiagnosticsLevel(diagnosticsLevel, diagnostic);
            if (diagnosticsLevelModifyDiagnosticsLevel != null) {
                diagnosticsLevel = diagnosticsLevelModifyDiagnosticsLevel;
            }
            Iterator it = this.b.iterator();
            while (it.hasNext()) {
                diagnosticsLevel = ((N2) it.next()).a(diagnosticsLevel, diagnostic);
            }
        } else {
            diagnosticsLevel = DiagnosticsLevel.ERROR;
        }
        int i = M2.a[diagnosticsLevel.ordinal()];
        if (i == 1) {
            this.a.info(diagnostic);
            return;
        }
        if (i == 2) {
            this.a.warning(diagnostic);
        } else if (i != 3) {
            if (i != 4) {
                throw new C3405y10();
            }
        } else {
            this.c = new C3870b(diagnostic);
            this.a.error(diagnostic);
        }
    }

    public RuntimeException b(String str) {
        a(new StringDiagnostic(str));
        throw null;
    }

    public void c(String str) {
        warning(new StringDiagnostic(str));
    }

    @Override // com.android.tools.r8.DiagnosticsHandler
    public synchronized void error(Diagnostic diagnostic) {
        a(DiagnosticsLevel.ERROR, diagnostic);
    }

    @Override // com.android.tools.r8.DiagnosticsHandler
    public final synchronized void info(Diagnostic diagnostic) {
        a(DiagnosticsLevel.INFO, diagnostic);
    }

    @Override // com.android.tools.r8.DiagnosticsHandler
    public synchronized void warning(Diagnostic diagnostic) {
        a(DiagnosticsLevel.WARNING, diagnostic);
    }

    public O2(DiagnosticsHandler diagnosticsHandler) {
        this.b = new ArrayList();
        this.c = null;
        this.a = diagnosticsHandler;
    }

    public void a(String str) {
        error(new StringDiagnostic(str));
    }

    public final void a(Diagnostic diagnostic) {
        a(null, diagnostic);
        throw this.c;
    }

    public synchronized void a() {
        if (this.c != null) {
            throw new RuntimeException(this.c);
        }
    }

    public final void a(DiagnosticsLevel diagnosticsLevel, DiagnosticsLevel diagnosticsLevel2, String str) {
        this.b.add(new N2(diagnosticsLevel, diagnosticsLevel2, str));
    }
}
