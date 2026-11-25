package com.android.tools.r8;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.origin.PathOrigin;
import com.android.tools.r8.utils.ExceptionDiagnostic;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class c0 extends StringConsumer.ForwardingConsumer {
    private final Origin b;
    private Writer c;

    public c0(PrintWriter printWriter) {
        this(null, printWriter);
    }

    @Override // com.android.tools.r8.StringConsumer.ForwardingConsumer, com.android.tools.r8.StringConsumer
    public final void accept(String str, DiagnosticsHandler diagnosticsHandler) {
        super.accept(str, diagnosticsHandler);
        try {
            this.c.write(str);
        } catch (IOException e) {
            diagnosticsHandler.error(new ExceptionDiagnostic(e, this.b));
        }
    }

    @Override // com.android.tools.r8.StringConsumer.ForwardingConsumer, com.android.tools.r8.J
    public final void finished(DiagnosticsHandler diagnosticsHandler) {
        super.finished(diagnosticsHandler);
        try {
            this.c.close();
        } catch (IOException e) {
            diagnosticsHandler.error(new ExceptionDiagnostic(e, this.b));
        }
    }

    public c0(PathOrigin pathOrigin, Writer writer) {
        super(null);
        this.b = pathOrigin;
        this.c = writer;
    }
}
