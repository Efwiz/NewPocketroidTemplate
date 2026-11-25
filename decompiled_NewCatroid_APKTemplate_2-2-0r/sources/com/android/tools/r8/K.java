package com.android.tools.r8;

import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class K extends StringConsumer.ForwardingConsumer {
    final /* synthetic */ List b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    K(StringConsumer stringConsumer, List list) {
        super(stringConsumer);
        this.b = list;
    }

    @Override // com.android.tools.r8.StringConsumer.ForwardingConsumer, com.android.tools.r8.StringConsumer
    public final void accept(String str, DiagnosticsHandler diagnosticsHandler) {
        this.b.add(str);
        super.accept(str, diagnosticsHandler);
    }
}
