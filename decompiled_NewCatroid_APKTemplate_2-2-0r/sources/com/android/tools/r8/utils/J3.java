package com.android.tools.r8.utils;

import com.android.tools.r8.TextInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class J3 implements TextInputStream {
    private final InputStream a;

    public J3(Path path) throws IOException {
        this(Files.newInputStream(path, new OpenOption[0]));
    }

    @Override // com.android.tools.r8.TextInputStream
    public final Charset getCharset() {
        return StandardCharsets.UTF_8;
    }

    @Override // com.android.tools.r8.TextInputStream
    public final InputStream getInputStream() {
        return this.a;
    }

    public J3(String str) {
        this(new ByteArrayInputStream(str.getBytes()));
    }

    public J3(InputStream inputStream) {
        this.a = inputStream;
    }
}
