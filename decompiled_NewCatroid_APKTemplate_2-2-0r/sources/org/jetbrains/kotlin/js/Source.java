package org.jetbrains.kotlin.js;

import java.io.IOException;
import java.io.Reader;
import java.net.URI;

/* JADX INFO: loaded from: classes5.dex */
public interface Source {
    boolean exists();

    long getLastModified();

    String getName();

    Reader getSourceReader() throws IOException;

    URI getUri();
}
