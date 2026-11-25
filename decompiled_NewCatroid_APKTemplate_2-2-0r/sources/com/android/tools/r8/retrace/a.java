package com.android.tools.r8.retrace;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class a implements ProguardMapProducer {
    final /* synthetic */ Path a;

    a(Path path) {
        this.a = path;
    }

    @Override // com.android.tools.r8.retrace.ProguardMapProducer
    public final InputStream get() {
        return new BufferedInputStream(new FileInputStream(this.a.toFile()));
    }

    @Override // com.android.tools.r8.retrace.ProguardMapProducer
    public final Path getPath() {
        return this.a;
    }

    @Override // com.android.tools.r8.retrace.ProguardMapProducer
    public final boolean isFileBacked() {
        return true;
    }
}
