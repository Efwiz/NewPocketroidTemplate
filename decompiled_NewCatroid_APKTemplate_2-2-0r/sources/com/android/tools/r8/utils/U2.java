package com.android.tools.r8.utils;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.StringConsumer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class U2 extends StringConsumer.ForwardingConsumer {
    private final ArrayList b;

    public U2(StringConsumer stringConsumer) {
        super(stringConsumer);
        this.b = new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(DiagnosticsHandler diagnosticsHandler, String str) {
        super.accept(str, diagnosticsHandler);
    }

    @Override // com.android.tools.r8.StringConsumer.ForwardingConsumer, com.android.tools.r8.StringConsumer
    public final void accept(String str, DiagnosticsHandler diagnosticsHandler) {
        this.b.add(str);
    }

    @Override // com.android.tools.r8.StringConsumer.ForwardingConsumer, com.android.tools.r8.J
    public final void finished(final DiagnosticsHandler diagnosticsHandler) {
        Collections.sort(this.b);
        this.b.forEach(new Consumer() { // from class: com.android.tools.r8.utils.U2$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(diagnosticsHandler, (String) obj);
            }
        });
        super.finished(diagnosticsHandler);
    }
}
