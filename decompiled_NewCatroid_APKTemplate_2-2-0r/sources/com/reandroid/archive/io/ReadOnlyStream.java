package com.reandroid.archive.io;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes10.dex */
public interface ReadOnlyStream extends RandomStream {
    InputStream getInputStream(long j, long j2) throws IOException;

    long getLength() throws IOException;
}
