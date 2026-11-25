package com.android.tools.r8.naming;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.StringConsumer;
import com.android.tools.r8.utils.Y2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class I0 extends com.android.tools.r8.T implements com.android.tools.r8.utils.V {
    static final /* synthetic */ boolean c = true;
    public static final /* synthetic */ int d = 0;
    private final StringConsumer a;
    private final DiagnosticsHandler b;

    private I0(StringConsumer stringConsumer, DiagnosticsHandler diagnosticsHandler) {
        boolean z = c;
        if (!z && stringConsumer == null) {
            throw new AssertionError();
        }
        if (!z && diagnosticsHandler == null) {
            throw new AssertionError();
        }
        this.a = stringConsumer;
        this.b = diagnosticsHandler;
    }

    @Override // com.android.tools.r8.utils.V
    public final com.android.tools.r8.utils.V a(String str) {
        this.a.accept(str, this.b);
        return this;
    }

    @Override // com.android.tools.r8.J
    public final void finished(DiagnosticsHandler diagnosticsHandler) {
        this.a.finished(diagnosticsHandler);
    }

    public StringConsumer a() {
        return this.a;
    }

    @Override // com.android.tools.r8.T
    public final void a(y0 y0Var, C3605b c3605b) {
        a(Y2.a(y0Var.a()));
        c3605b.a(this);
    }
}
