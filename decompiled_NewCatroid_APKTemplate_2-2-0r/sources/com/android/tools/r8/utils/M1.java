package com.android.tools.r8.utils;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.StringConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class M1 extends StringConsumer.ForwardingConsumer {
    private final String b;
    private final StringConsumer c;
    private final StringBuilder d;

    public M1(StringConsumer stringConsumer) {
        super(stringConsumer);
        this.d = new StringBuilder();
        this.c = stringConsumer;
        this.b = "\n";
    }

    public StringConsumer a() {
        return this.c;
    }

    @Override // com.android.tools.r8.StringConsumer.ForwardingConsumer, com.android.tools.r8.StringConsumer
    public final void accept(String str, DiagnosticsHandler diagnosticsHandler) {
        if (this.d.length() > 0) {
            this.d.append(this.b);
        }
        this.d.append(str);
    }

    @Override // com.android.tools.r8.StringConsumer.ForwardingConsumer, com.android.tools.r8.J
    public final void finished(DiagnosticsHandler diagnosticsHandler) {
        super.accept(this.d.toString(), diagnosticsHandler);
        super.finished(diagnosticsHandler);
    }
}
