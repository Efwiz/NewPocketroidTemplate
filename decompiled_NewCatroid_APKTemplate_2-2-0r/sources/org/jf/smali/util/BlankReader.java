package org.jf.smali.util;

import java.io.IOException;
import java.io.Reader;

/* JADX INFO: loaded from: classes6.dex */
public class BlankReader extends Reader {
    public static final BlankReader INSTANCE = new BlankReader();

    @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
    }

    @Override // java.io.Reader
    public int read(char[] cArr, int i, int i2) throws IOException {
        return -1;
    }
}
