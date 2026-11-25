package com.android.tools.r8;

import java.nio.file.Path;

/* JADX INFO: renamed from: com.android.tools.r8.d, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0405d extends StringConsumer.FileConsumer {
    C0405d(Path path) {
        super(path);
    }

    @Override // com.android.tools.r8.StringConsumer.FileConsumer, com.android.tools.r8.StringConsumer.ForwardingConsumer, com.android.tools.r8.StringConsumer
    public final void accept(String str, DiagnosticsHandler diagnosticsHandler) {
        super.accept(str, diagnosticsHandler);
        super.accept(System.lineSeparator(), diagnosticsHandler);
    }
}
