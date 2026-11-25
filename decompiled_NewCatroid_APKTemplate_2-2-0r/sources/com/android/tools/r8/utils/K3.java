package com.android.tools.r8.utils;

import com.android.tools.r8.TextOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class K3 implements TextOutputStream {
    private final OutputStream a;

    public K3(Path path) {
        this(Files.newOutputStream(path, new OpenOption[0]));
    }

    @Override // com.android.tools.r8.TextOutputStream
    public final Charset getCharset() {
        return StandardCharsets.UTF_8;
    }

    @Override // com.android.tools.r8.TextOutputStream
    public final OutputStream getOutputStream() {
        return this.a;
    }

    public K3(OutputStream outputStream) {
        this.a = outputStream;
    }
}
