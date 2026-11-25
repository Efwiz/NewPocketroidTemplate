package com.reandroid.archive.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes10.dex */
public interface WriteOnlyStream extends RandomStream {
    OutputStream getOutputStream() throws IOException;

    void write(InputStream inputStream) throws IOException;
}
