package com.android.tools.r8.internal;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.TextOutputStream;
import com.android.tools.r8.profile.art.ArtProfileConsumer;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

/* JADX INFO: renamed from: com.android.tools.r8.internal.x3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3331x3 implements ArtProfileConsumer {
    final /* synthetic */ Path a;

    C3331x3(Path path) {
        this.a = path;
    }

    @Override // com.android.tools.r8.profile.art.ArtProfileConsumer
    public final void finished(DiagnosticsHandler diagnosticsHandler) {
    }

    @Override // com.android.tools.r8.profile.art.ArtProfileConsumer
    public final TextOutputStream getHumanReadableArtProfileConsumer() {
        try {
            return new com.android.tools.r8.utils.K3(this.a);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
