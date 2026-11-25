package org.antlr.runtime;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/* JADX INFO: loaded from: classes12.dex */
public class ANTLRInputStream extends ANTLRReaderStream {
    public ANTLRInputStream() {
    }

    public ANTLRInputStream(InputStream inputStream) throws IOException {
        this(inputStream, (String) null);
    }

    public ANTLRInputStream(InputStream inputStream, int i) throws IOException {
        this(inputStream, i, null);
    }

    public ANTLRInputStream(InputStream inputStream, String str) throws IOException {
        this(inputStream, 1024, str);
    }

    public ANTLRInputStream(InputStream inputStream, int i, String str) throws IOException {
        this(inputStream, i, 1024, str);
    }

    public ANTLRInputStream(InputStream inputStream, int i, int i2, String str) throws IOException {
        InputStreamReader inputStreamReader;
        if (str != null) {
            inputStreamReader = new InputStreamReader(inputStream, str);
        } else {
            inputStreamReader = new InputStreamReader(inputStream);
        }
        load(inputStreamReader, i, i2);
    }
}
